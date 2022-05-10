package com.kollu.customer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.kollu.customer.security.entity.User;
import com.kollu.customer.security.repository.UserRepository;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.kollu.customer")
@EnableSwagger2
public class CustomerModuleApplication {

	private static Logger logger = LoggerFactory.getLogger(CustomerModuleApplication.class);
	
	/*JWT Security*/
	@Autowired
    private UserRepository repository;
	
	public static void main(String[] args) {
		System.out.println("Console:: i am from Customer module");
		logger.info("i am from Customer module");
		SpringApplication.run(CustomerModuleApplication.class, args);
	}

	/*Swagger-api configuration*/
	
	@Bean
	   public Docket productApi() {
		System.out.println("Console:: CustomerModuleApplication - Swagger - productApi method");
		logger.info("CustomerModuleApplication - Swagger - productApi method");
	      return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
	         .apis(RequestHandlerSelectors.basePackage("com.kollu.customer")).build();
	   }
	
	private ApiInfo apiInfo() {
		System.out.println("Console:: CustomerModuleApplication - Swagger - apiInfo method");
		logger.info("CustomerModuleApplication - Swagger - apiInfo method");
		return new ApiInfoBuilder().title("Welcome! Customer Micro Service")
				.description("Customer Micro Service Swagger Document")
				.build();
	}
	
/*Distributed Tracing/Zipkin*/
	
	/*@Bean
	public Sampler defaultSampler() {
		System.out.println("Console:: CustomerModuleApplication - defaultSampler method");
		logger.info("CustomerModuleApplication - defaultSampler method");
	    return Sampler.ALWAYS_SAMPLE;
	}*/
	
	/*JWT Security*/
	List<User> users = null;
	@PostConstruct
    public void initUsers() {
        users = Stream.of(
                new User(101, "kollu", "pass"),
                new User(102, "user1", "pwd1"),
                new User(103, "user2", "pwd2"),
                new User(104, "user3", "pwd3")
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }
	
	@PreDestroy
	public void destory() {
		//repository.deleteAll();
		repository.deleteAll(users);
	}
}
