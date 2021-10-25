package com.kollu.customer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kollu.customer.model.CustomerResponse;

//@FeignClient(name="BankModule", url="localhost:9096") 
//commented above line to test rest call to bank module through Eureka server
@FeignClient(name="bankmodule")
public interface CustomerBankFeignProxy {
	
	@GetMapping("/custBank/bankCustByIdd/{bankCustId}")
	//public CustomerResponse getCustomerDetailsById(@PathVariable("bankCustId") long cid);
	
	public ResponseEntity<CustomerResponse> getCustomerDetailsById(@PathVariable("bankCustId") long cid);

}
