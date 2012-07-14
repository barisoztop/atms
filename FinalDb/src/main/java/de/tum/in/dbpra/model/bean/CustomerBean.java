package main.java.de.tum.in.dbpra.model.bean;

import java.sql.Date;

public class CustomerBean {

	private int customerID;
	private String fName;
	private String lName;
	private String address;
	private String country;
	private String passportNO;
	private Date DOB;
	private String sex;

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getFName() {
		return fName;
	}

	public void setFName(String fName) {
		this.fName = fName;
	}

	public String getLName() {
		return lName;
	}

	public void setLName(String lName) {
		this.lName = lName;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getPassportNO() {
		return passportNO;
	}

	public void setPassportNO(String passportNO) {
		this.passportNO = passportNO;
	}	

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date DOB) {
		this.DOB = DOB;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}	
}
