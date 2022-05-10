package com.kollu.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kollu.customer.exception.RecordNotFoundException;
import com.kollu.customer.feign.CustomerBankFeignProxy;
import com.kollu.customer.model.Customer;
import com.kollu.customer.model.CustomerResponse;
import com.kollu.customer.security.entity.AuthRequest;
import com.kollu.customer.security.util.JwtUtil;
import com.kollu.customer.service.CustomerRepository;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Value("${spring.bankmoduleURL}")
	private String bankModuleURL;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerBankFeignProxy customerBankFeignProxy;
	
	/*JWT Security*/
	@Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    /*JWT Security*/
	@PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
	
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
			throw new RecordNotFoundException("Customer details not avilable.");
		}
		return new ResponseEntity<>(customers, HttpStatus.OK); 
		
	} catch (Exception e) {
		System.out.println("Console:: CustomerController - getCustomerDetails - Error ::" +e.getMessage());
		logger.error("CustomerController - getCustomerDetails - Error :: " +e.getMessage()); 
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	} 
	
	/*Save customer details*/
	
	@PostMapping("/savecustomer")
	public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
		
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
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Update customer details based on {id}*/
	
	@PutMapping("/updatecustomer/{customerId}")
	public ResponseEntity<Customer> updateCustomerDetails(@Valid @PathVariable("customerId") long custId, @Valid @RequestBody Customer customer) {
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
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*Delete customer based on {id}*/
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<HttpStatus> deleteCustomer(@Valid @PathVariable("customerId") long custId) {
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
	public ResponseEntity<Customer> getCustomerById(@Valid @PathVariable("custId") long cid) {
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
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			throw new RecordNotFoundException("Customer id not avilable.");
		}
	}
		
	/*	Get the customer details by using {custId}*/
	
	@GetMapping("/custByIdd/{custId}")
	
	/*Added fault tolerance feature*/
	@Retry(name="custRetryFallback", fallbackMethod="fallBackCustomerByIdd")
	public ResponseEntity<CustomerResponse> getCustomerByIdd(@Valid @PathVariable("custId") long cid) throws Exception {
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
		} else {
			System.out.println("Console:: CustomerController - getCustomerByIdd - custByID :: "+custByID);
			logger.debug("CustomerController - getCustomerByIdd - custByID :: "+custByID);
			throw new RecordNotFoundException("Customer id not avilable.");
		}
		
		Map<String, Long> uriVariables = new HashMap<>();
		uriVariables.put("bankCustId", custByID);
		
		responseEntity =new RestTemplate().getForEntity(bankModuleURL, 
						CustomerResponse.class, uriVariables);
		
		/*responseEntity =new RestTemplate().getForEntity("http://172.20.10.3:9096/custBank/bankCustByIdd/{bankCustId}", 
				CustomerResponse.class, uriVariables);*/
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
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*	Get the customer details by using {custId}*/
	@GetMapping("/custByIddFeign/{custId}")
	
	/*Added fault tolerance feature*/
		@Retry(name="custRetryFallback", fallbackMethod="fallBackCustomerByIddFeign")
	public ResponseEntity<CustomerResponse> getCustomerByIddFeign(@Valid @PathVariable("custId") long cid) throws Exception {
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
			throw new RecordNotFoundException("Customer id not avilable.");
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
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/*All FallBack methods*/ 
	
	public ResponseEntity<Object> fallBackCustomerByIdd(Exception ex) throws Exception { 
		System.out.println("Console:: CustomerController - fallBackCustomerByIdd method");
		logger.info("CustomerController - fallBackCustomerByIdd method");
		
		String msg ="Micro Service Down!!, Please wait some time.";
		
		System.out.println("Console:: CustomerController - fallBackCustomerByIdd Error :: "+ex.getMessage()); 
		logger.error("CustomerController - fallBackCustomerByIdd Error :: "+ex.getMessage());
		//return new ResponseEntity<Object>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		throw new Exception(msg);
}

	public ResponseEntity<Object> fallBackCustomerByIddFeign(Exception ex) throws Exception { 
		System.out.println("Console:: CustomerController - fallBackCustomerByIddFeign method");
		logger.info("CustomerController - fallBackCustomerByIddFeign method");
		
		String msg ="Micro Service Down!!, Please wait some time.";
		
		System.out.println("Console:: CustomerController - fallBackCustomerByIddFeign Error :: "+ex.getMessage()); 
		logger.error("CustomerController - fallBackCustomerByIddFeign Error :: "+ex.getMessage());
		//return new ResponseEntity<Object>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		throw new Exception(msg);
	}
}