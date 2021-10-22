package com.kollu.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;
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
	
	@Bean
	public Sampler defaultSampler() {
		System.out.println("Console:: CustomerModuleApplication - defaultSampler method");
		logger.info("CustomerModuleApplication - defaultSampler method");
	    return Sampler.ALWAYS_SAMPLE;
	}
}
