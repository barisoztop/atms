package main.java.de.tum.in.dbpra.model.bean;

import java.sql.Timestamp;

public class TransactionBean {

private int agentID;
private int flightID;
private Timestamp t_timestamp;
private String currency;
private String t_status;
private String modeOfPayment;
private Double amount;
private String typeOfTransaction;
private int customerID;

public int getAgentID() {
	return agentID;
}
public void setAgentID(int agentID) {
	this.agentID = agentID;
}

public int getCustomerID() {
	return customerID;
}
public void setCustomerID(int customerID) {
	this.customerID = customerID;
}


public int getFlightID() {
	return flightID;
}
public void setFlightID(int flightID) {
	this.flightID = flightID;
}

public Timestamp gett_timestamp() {
	return t_timestamp;
}
public void sett_timestamp(Timestamp t_timestamp) {
	this.t_timestamp = t_timestamp;
}
	
public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}

public String gett_status() {
	return t_status;
}
public void sett_status(String t_status) {
	this.t_status = t_status;
}

public String getModeOfPayment() {
	return modeOfPayment;
}
public void setModeOfPayment(String modeOfPayment) {
	this.modeOfPayment = modeOfPayment;
}

public Double getAmount() {
	return amount;
}
public void setAmount(Double amount) {
	this.amount = amount;
}

public String getTypeOfTransaction() {
	return typeOfTransaction;
}
public void setTypeOfTransaction(String typeOfTransaction) {
	this.typeOfTransaction = typeOfTransaction;
}

}
