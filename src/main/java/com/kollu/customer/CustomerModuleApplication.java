package com.kollu.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.kollu.customer")
public class CustomerModuleApplication {

	public static void main(String[] args) {
		System.out.println("i am from mail clazz");
		SpringApplication.run(CustomerModuleApplication.class, args);
	}

}
