package com.kollu.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Customer_Details")
public class Customer {

	@Id
	@GenericGenerator(name = "Cust_Id_id", strategy = "com.kollu.customer.util.CustomerIdGenerator")
	@GeneratedValue(generator = "Cust_Id_id")  
	@Column(name="Customer_Id")
	private long customerId;
	
	@Column(name="First_Name")
	@NotBlank(message = "Customer First Name is mandatory")
	private String firstName;
	
	@Column(name="Last_Name")
	@NotBlank(message = "Customer Last Name is mandatory")
	private String lastName;
	
	@Column(name="Mobile_Number")
	@NotBlank(message = "Customer Mobile no is mandatory")
	private String mobileNumber;
	
	@Column(name="Gender")
	@NotBlank(message = "Customer Gender Type is mandatory")
	private String gender;
	
	@Column(name="Address")
	@NotBlank(message = "Customer Address is mandatory")
	private String address;

	//default constructor
	public Customer() {
	}

	//parameterized constructor
	public Customer(String firstName, String lastName, String mobileNumber, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.address = address;
	}

	//Setters & getters
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	//toString
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", mobileNumber=" + mobileNumber + ", gender=" + gender + ", address=" + address + "]";
	}
	
	
}
