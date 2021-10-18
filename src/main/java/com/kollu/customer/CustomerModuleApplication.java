package com.kollu.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerModuleApplication {

	public static void main(String[] args) {
		System.out.println("i am from mail clazz");
		SpringApplication.run(CustomerModuleApplication.class, args);
	}

}
