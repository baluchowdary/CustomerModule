package com.kollu.customer.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kollu.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	
}
