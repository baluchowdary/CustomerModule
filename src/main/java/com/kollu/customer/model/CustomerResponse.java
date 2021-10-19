package com.kollu.customer.model;

import java.io.Serializable;


public class CustomerResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//private long customerBankId;
	
	
	private long bankCustId;

	private String bankCustFirstName;
	
	private String bankCustLastName; 
	
	private String bankCustMobileNumber;
	
	private String bankCustGender;
	
	//private String bankCustAddress;
	
	
	
	
	//private long custBankId;
	
	private String custBankName;
	
	private String custBankIfscCode;
	
	private String custBankBranchAddress;
	
	private String custBankAccountNo;

	public CustomerResponse() {
	}

	public CustomerResponse(long bankCustId, String bankCustFirstName, String bankCustLastName,
			String bankCustMobileNumber, String bankCustGender, String custBankName, String custBankIfscCode,
			String custBankBranchAddress, String custBankAccountNo) {
		super();
		this.bankCustId = bankCustId;
		this.bankCustFirstName = bankCustFirstName;
		this.bankCustLastName = bankCustLastName;
		this.bankCustMobileNumber = bankCustMobileNumber;
		this.bankCustGender = bankCustGender;
		this.custBankName = custBankName;
		this.custBankIfscCode = custBankIfscCode;
		this.custBankBranchAddress = custBankBranchAddress;
		this.custBankAccountNo = custBankAccountNo;
	}

	public long getBankCustId() {
		return bankCustId;
	}

	public void setBankCustId(long bankCustId) {
		this.bankCustId = bankCustId;
	}

	public String getBankCustFirstName() {
		return bankCustFirstName;
	}

	public void setBankCustFirstName(String bankCustFirstName) {
		this.bankCustFirstName = bankCustFirstName;
	}

	public String getBankCustLastName() {
		return bankCustLastName;
	}

	public void setBankCustLastName(String bankCustLastName) {
		this.bankCustLastName = bankCustLastName;
	}

	public String getBankCustMobileNumber() {
		return bankCustMobileNumber;
	}

	public void setBankCustMobileNumber(String bankCustMobileNumber) {
		this.bankCustMobileNumber = bankCustMobileNumber;
	}

	public String getBankCustGender() {
		return bankCustGender;
	}

	public void setBankCustGender(String bankCustGender) {
		this.bankCustGender = bankCustGender;
	}

	public String getCustBankName() {
		return custBankName;
	}

	public void setCustBankName(String custBankName) {
		this.custBankName = custBankName;
	}

	public String getCustBankIfscCode() {
		return custBankIfscCode;
	}

	public void setCustBankIfscCode(String custBankIfscCode) {
		this.custBankIfscCode = custBankIfscCode;
	}

	public String getCustBankBranchAddress() {
		return custBankBranchAddress;
	}

	public void setCustBankBranchAddress(String custBankBranchAddress) {
		this.custBankBranchAddress = custBankBranchAddress;
	}

	public String getCustBankAccountNo() {
		return custBankAccountNo;
	}

	public void setCustBankAccountNo(String custBankAccountNo) {
		this.custBankAccountNo = custBankAccountNo;
	}

	@Override
	public String toString() {
		return "CustomerResponse [bankCustId=" + bankCustId + ", bankCustFirstName=" + bankCustFirstName
				+ ", bankCustLastName=" + bankCustLastName + ", bankCustMobileNumber=" + bankCustMobileNumber
				+ ", bankCustGender=" + bankCustGender + ", custBankName=" + custBankName + ", custBankIfscCode="
				+ custBankIfscCode + ", custBankBranchAddress=" + custBankBranchAddress + ", custBankAccountNo="
				+ custBankAccountNo + "]";
	}

	
}
