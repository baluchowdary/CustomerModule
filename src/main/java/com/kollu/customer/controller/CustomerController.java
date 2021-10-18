package com.kollu.customer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kollu.customer.model.Customer;
import com.kollu.customer.service.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	/*Fetch All customer details*/
	
	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<Customer>> getCustomerDetails(){
		System.out.println("getCustomerDetails method");
		try {
			
		List<Customer> customers = new ArrayList<Customer>();  
		customerRepository.findAll().forEach(customers::add);
		
		if (customers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(customers, HttpStatus.OK); 
		
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	} //method end
	
	
	/*Save customer details*/
	
	@PostMapping("/savecustomer")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
		try {
			System.out.println("saveCustomer method");
			Customer customerObj = customerRepository
					.save(new Customer(customer.getFirstName(), customer.getLastName(), customer.getMobileNumber(), customer.getGender(), 
							customer.getAddress()));
			
			return new ResponseEntity<>(customerObj, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Update customer details based on {id}*/
	
	@PutMapping("/updatecustomer/{customerId}")
	public ResponseEntity<Customer> updateCustomerDetails(@PathVariable("customerId") long custId, @RequestBody Customer customer) {
		System.out.println("updateCustomerDetails");
		
		Optional<Customer> customerData = customerRepository.findById(custId);

		if (customerData.isPresent()) {
			Customer customerObj = customerData.get();
			customerObj.setFirstName(customer.getFirstName());
			customerObj.setLastName(customer.getLastName());
			customerObj.setMobileNumber(customer.getMobileNumber());
			customerObj.setGender(customer.getGender());
			customerObj.setAddress(customer.getAddress()); 
			
			return new ResponseEntity<>(customerRepository.save(customerObj), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*Delete customer based on {id}*/
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("customerId") long custId) {
		try {
			System.out.println("deleteCustomer");
			customerRepository.deleteById(custId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Deleteing all the customers */
	
	@DeleteMapping("/deleteallcustomers")
	public ResponseEntity<HttpStatus> deleteAllCustomersDetails() {
		try {
			System.out.println("deleteAllCustomersDetails");
			customerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
