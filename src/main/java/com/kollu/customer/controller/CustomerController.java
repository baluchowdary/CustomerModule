package com.kollu.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.RestTemplate;

import com.kollu.customer.feign.CustomerBankFeignProxy;
import com.kollu.customer.model.Customer;
import com.kollu.customer.model.CustomerResponse;
import com.kollu.customer.service.CustomerRepository;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerBankFeignProxy customerBankFeignProxy;
	/*Fetch All customer details*/
	
	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<Customer>> getCustomerDetails(){
		System.out.println("Console:: CustomerController - getCustomerDetails method");
		logger.info("CustomerController - getCustomerDetails method"); 
		try {
			
		List<Customer> customers = new ArrayList<Customer>();  
		customerRepository.findAll().forEach(customers::add);
		
		if (customers.isEmpty()) {
			System.out.println("Console:: customers data size :: "+ customers.size());
			logger.info("Banks customers size :: "+ customers.size()); 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(customers, HttpStatus.OK); 
		
	} catch (Exception e) {
		System.out.println("Console:: CustomerController - getCustomerDetails - Error ::" +e.getMessage());
		logger.error("CustomerController - getCustomerDetails - Error :: " +e.getMessage()); 
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	} 
	
	/*Save customer details*/
	
	@PostMapping("/savecustomer")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
		
		System.out.println("Console:: CustomerController - saveCustomer method");
		logger.info("CustomerController - saveCustomer method"); 
		
		try {
			Customer customerObj = customerRepository
					.save(new Customer(customer.getFirstName(), customer.getLastName(), customer.getMobileNumber(), customer.getGender(), 
							customer.getAddress()));
			
			System.out.println("Console:: CustomerController - saveCustomer customerObj data ::"+customerObj);
			logger.debug("CustomerController - saveCustomer customerObj data :: "+customerObj); 
			
			return new ResponseEntity<>(customerObj, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Console:: CustomerController - saveCustomer - Error ::" +e.getMessage());
			logger.error("CustomerController - saveCustomer - Error :: " +e.getMessage()); 
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Update customer details based on {id}*/
	
	@PutMapping("/updatecustomer/{customerId}")
	public ResponseEntity<Customer> updateCustomerDetails(@PathVariable("customerId") long custId, @RequestBody Customer customer) {
		System.out.println("Console:: CustomerController - updateCustomerDetails method");
		logger.info("CustomerController - updateCustomerDetails method"); 
		logger.info("CustomerController - updateCustomerDetails method - custId :: "+custId); 
		
		Optional<Customer> customerData = customerRepository.findById(custId);

		if (customerData.isPresent()) {
			Customer customerObj = customerData.get();
			customerObj.setFirstName(customer.getFirstName());
			customerObj.setLastName(customer.getLastName());
			customerObj.setMobileNumber(customer.getMobileNumber());
			customerObj.setGender(customer.getGender());
			customerObj.setAddress(customer.getAddress()); 
			
			System.out.println("Console:: CustomerController - updateCustomerDetails customerData ::" +customerData);
			logger.debug("CustomerController - updateCustomerDetails customerData - :: "+customerData); 
			return new ResponseEntity<>(customerRepository.save(customerObj), HttpStatus.OK);
		} else {
			System.out.println("Console:: CustomerController - updateCustomerDetails customerData ::" +customerData);
			logger.debug("CustomerController - updateCustomerDetails customerData - :: "+customerData);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*Delete customer based on {id}*/
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("customerId") long custId) {
		System.out.println("Console:: CustomerController - deleteCustomer method");
		logger.info("CustomerController - deleteCustomer method"); 
		logger.info("CustomerController - deleteCustomer method - custId :: "+custId); 
		
		try {
			customerRepository.deleteById(custId);
			System.out.println("Console:: CustomerController - deleteCustomer delete");
			logger.info("CustomerController - deleteCustomer delete"); 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println("Console:: CustomerController - deleteCustomer - Error ::" +e.getMessage());
			logger.error("CustomerController - deleteCustomer - Error :: " +e.getMessage()); 
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Deleteing all the customers */
	
	@DeleteMapping("/deleteallcustomers")
	public ResponseEntity<HttpStatus> deleteAllCustomersDetails() {
		System.out.println("Console:: CustomerController - deleteAllCustomersDetails method");
		logger.info("CustomerController - deleteAllCustomersDetails method"); 
		
		try {
			customerRepository.deleteAll();
			System.out.println("Console:: CustomerController - deleteAllCustomersDetails delete");
			logger.info("CustomerController - deleteAllCustomersDetails delete");
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println("Console:: CustomerController - deleteAllCustomersDetails - Error ::" +e.getMessage());
			logger.error("CustomerController - deleteAllCustomersDetails - Error :: " +e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
/*	Get the customer details by using {custId}*/
	@GetMapping("/custById/{custId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("custId") long cid) {
		System.out.println("Console:: CustomerController - getCustomerById method");
		logger.info("CustomerController - getCustomerById method"); 
		logger.info("CustomerController - getCustomerById method - cid :: "+cid);
		
		Optional<Customer> custData = customerRepository.findById(cid);

		if (custData.isPresent()) {
			System.out.println("Console:: CustomerController - getCustomerById custData ::" +custData);
			logger.debug("CustomerController - getCustomerById custData - :: "+custData); 
			return new ResponseEntity<>(custData.get(), HttpStatus.OK);
		} else {
			System.out.println("Console:: CustomerController - getCustomerById custData ::" +custData);
			logger.debug("CustomerController - getCustomerById custData - :: "+custData);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*	Get the customer details by using {custId}*/
	/*@GetMapping("/custByIdd/{custId}")
	@Retry(name="custRetryFallback", fallbackMethod="fallBackMethodResponse")
	public CustomerResponse getCustomerByIdd(@PathVariable("custId") long cid) {
		System.out.println("Customer getCustomerByIdd method");
		
		long custByID = 0; 
		CustomerResponse custResponse = null;
		ResponseEntity<CustomerResponse> responseEntity = null; 
		
		try {
		Optional<Customer> custData = customerRepository.findById(cid);

		if(custData.isPresent()) {
			custByID = custData.get().getCustomerId();
			System.out.println("custByID : "+custByID);
		}
		
		Map<String, Long> uriVariables = new HashMap<>();
		uriVariables.put("bankCustId", custByID);
		
		
		responseEntity =new RestTemplate().getForEntity("http://localhost:9096/custBank/bankCustByIdd/{bankCustId}", 
						CustomerResponse.class, uriVariables);
		System.out.println("responseEntity.getBody() : "+responseEntity.getBody()); 
		custResponse = responseEntity.getBody();
		
		
		if (custResponse != null) {	
			return new CustomerResponse(
					custResponse.getBankCustId(), 
					custResponse.getBankCustFirstName(), custResponse.getBankCustLastName(), 
					custResponse.getBankCustMobileNumber(), custResponse.getBankCustGender(),
					custResponse.getCustBankName(), 
					custResponse.getCustBankIfscCode(), custResponse.getCustBankBranchAddress(), custResponse.getCustBankAccountNo());
				} else {
					System.out.println("customer else block");
					return new CustomerResponse();
				}
		
		} catch (Exception e) {
			System.out.println("e ::"+e); 
		}
		//return responseEntity.getBody(); 
		return custResponse;
	} kollu commented*/
	
	
	
	/*	Get the customer details by using {custId}*/
	
	@GetMapping("/custByIdd/{custId}")
	
	/*Added fault tolerance feature*/
	@Retry(name="custRetryFallback", fallbackMethod="fallBackCustomerByIdd")
	public ResponseEntity<CustomerResponse> getCustomerByIdd(@PathVariable("custId") long cid) {
		System.out.println("Console:: CustomerController - getCustomerByIdd method");
		logger.info("CustomerController - getCustomerByIdd method"); 
		logger.info("CustomerController - getCustomerByIdd method - cid :: "+cid);
		
		long custByID = 0; 
		CustomerResponse custResponse = null;
		ResponseEntity<CustomerResponse> responseEntity = null; 
		
		Optional<Customer> custData = customerRepository.findById(cid);
		
		if(custData.isPresent()) {
			custByID = custData.get().getCustomerId();
			
			System.out.println("Console:: CustomerController - getCustomerByIdd - custByID :: "+custByID);
			logger.debug("CustomerController - getCustomerByIdd - custByID :: "+custByID);
		}
		
		Map<String, Long> uriVariables = new HashMap<>();
		uriVariables.put("bankCustId", custByID);
		
		responseEntity =new RestTemplate().getForEntity("http://localhost:9096/custBank/bankCustByIdd/{bankCustId}", 
						CustomerResponse.class, uriVariables);
		custResponse = responseEntity.getBody();
		
		System.out.println("Console:: CustomerController - getCustomerByIdd - entity Response status code :: "+responseEntity.getStatusCode());
		logger.debug("CustomerController - getCustomerByIdd - entity Response status code :: "+responseEntity.getStatusCode());
		
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			System.out.println("Console:: CustomerController - getCustomerByIdd - entity Response Body :: "+responseEntity.getBody());
			logger.debug("CustomerController - getCustomerByIdd - entity Response Body :: "+responseEntity.getBody());
			return new ResponseEntity<>(custResponse, HttpStatus.OK);
		} else {
			System.out.println("Console:: CustomerController - getCustomerByIdd - entity Response Body :: "+responseEntity.getBody());
			logger.debug("CustomerController - getCustomerByIdd - entity Response Body :: "+responseEntity.getBody());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*	Get the customer details by using {custId}
	@GetMapping("/custByIddFeign/{custId}")
	public CustomerResponse getCustomerByIddFeign(@PathVariable("custId") long cid) {
		System.out.println("Customer getCustomerByIddFeign method");
		
		long custByID = 0; 
		CustomerResponse custResponse = null;
		//ResponseEntity<CustomerResponse> responseEntity;
		
		Optional<Customer> custData = customerRepository.findById(cid);

		if(custData.isPresent()) {
			custByID = custData.get().getCustomerId();
			System.out.println("custByID : "+custByID);
		}
		
		custResponse =customerBankFeignProxy.getCustomerDetailsById(custByID);
		
		if (custResponse != null) {	
			return new CustomerResponse(
					custResponse.getBankCustId(), 
					custResponse.getBankCustFirstName(), custResponse.getBankCustLastName(), 
					custResponse.getBankCustMobileNumber(), custResponse.getBankCustGender(),
					custResponse.getCustBankName(), 
					custResponse.getCustBankIfscCode(), custResponse.getCustBankBranchAddress(), custResponse.getCustBankAccountNo());
			
		} else {
			System.out.println("customer else block");
			return new CustomerResponse();
		} 
		
		//return null;
		
	} kollu commented*/
	
	
	/*	Get the customer details by using {custId}*/
	@GetMapping("/custByIddFeign/{custId}")
	
	/*Added fault tolerance feature*/
		@Retry(name="custRetryFallback", fallbackMethod="fallBackCustomerByIddFeign")
	public ResponseEntity<CustomerResponse> getCustomerByIddFeign(@PathVariable("custId") long cid) {
		System.out.println("Console:: CustomerController - getCustomerByIddFeign method");
		logger.info("CustomerController - getCustomerByIddFeign method"); 
		logger.info("CustomerController - getCustomerByIddFeign method - cid :: "+cid);
		
		long custByID = 0; 
		//CustomerResponse custResponse = null;
		ResponseEntity<CustomerResponse> responseEntity = null;
		Optional<Customer> custData = customerRepository.findById(cid);
		
		if(custData.isPresent()) {
			custByID = custData.get().getCustomerId();
			System.out.println("Console:: CustomerController - getCustomerByIddFeign - custByID :: "+custByID);
			logger.debug("CustomerController - getCustomerByIddFeign - custByID :: "+custByID);
			
		} else {
			System.out.println("Console:: CustomerController - getCustomerByIddFeign - custByID :: "+custByID);
			logger.debug("CustomerController - getCustomerByIddFeign - custByID :: "+custByID);
		}
		
		responseEntity =customerBankFeignProxy.getCustomerDetailsById(custByID);
		
		System.out.println("Console:: CustomerController - getCustomerByIddFeign - entity Response status code :: "+responseEntity.getStatusCode());
		logger.debug("CustomerController - getCustomerByIddFeign - entity Response status code :: "+responseEntity.getStatusCode());
		
		
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			System.out.println("Console:: CustomerController - getCustomerByIddFeign - entity Response Body :: "+responseEntity.getBody());
			logger.debug("CustomerController - getCustomerByIddFeign - entity Response Body :: "+responseEntity.getBody());
			return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
		} else {
			System.out.println("Console:: CustomerController - getCustomerByIddFeign - entity Response Body :: "+responseEntity.getBody());
			logger.debug("CustomerController - getCustomerByIddFeign - entity Response Body :: "+responseEntity.getBody());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	/*All FallBack methods*/ 
	
	public ResponseEntity<Object> fallBackCustomerByIdd(Exception ex) {
		System.out.println("Console:: CustomerController - fallBackCustomerByIdd method");
		logger.info("CustomerController - fallBackCustomerByIdd method");
		
		String msg ="Service down, Please wait some time.";
		
		System.out.println("Console:: CustomerController - fallBackCustomerByIdd Error :: "+ex.getMessage()); 
		logger.error("CustomerController - fallBackCustomerByIdd Error :: "+ex.getMessage());
		return new ResponseEntity<Object>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
}

	public ResponseEntity<Object> fallBackCustomerByIddFeign(Exception ex) {
		System.out.println("Console:: CustomerController - fallBackCustomerByIddFeign method");
		logger.info("CustomerController - fallBackCustomerByIddFeign method");
		
		String msg ="Service down, Please wait some time.";
		
		System.out.println("Console:: CustomerController - fallBackCustomerByIddFeign Error :: "+ex.getMessage()); 
		logger.error("CustomerController - fallBackCustomerByIddFeign Error :: "+ex.getMessage());
		return new ResponseEntity<Object>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}