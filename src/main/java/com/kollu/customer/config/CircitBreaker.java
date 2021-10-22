package com.kollu.customer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircitBreaker {

	private Logger logger = LoggerFactory.getLogger(CircitBreaker.class);
	@GetMapping("/kollu")
	
	//retry 5 times and each between request waiting duration 300 sec's
	@Retry(name="kollu-retry", fallbackMethod="fallBackMethodResponse")
	
	//once threshoul reach to 50% status will update from CLOSE to OPEN
	//@CircuitBreaker(name="kollu-retry", fallbackMethod="fallBackMethodResponse")
	
	//2 requests in 300 sec's of time
	//@RateLimiter(name="kollu-retry", fallbackMethod="fallBackMethodResponse")
	
	//at a time 10 users can send requests
	//@Bulkhead(name="kollu-retry", fallbackMethod="fallBackMethodResponse")
	
	public String getResponse() {
		System.out.println("Console:: CircitBreaker - getResponse method");
		logger.info("CircitBreaker - getResponse method"); 
		ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:87611/", String.class);
		logger.debug("Entity Response Body :: "+ entity.getBody()); 
		return entity.getBody(); 
	}//end
	
	public String fallBackMethodResponse(Exception ex) {
		logger.info("CircitBreaker - fallBackMethodResponse method"); 
		System.out.println("Console:: CircitBreaker - fallBackMethodResponse method");
		
		logger.error("CircitBreaker - fallBackMethodResponse :: "+ex.getMessage()); 
		System.out.println("Console:: CircitBreaker - fallBackMethodResponse :: "+ex.getMessage());
		return "fallBack";
	}
}
