package de.tum.in.dbpra.model.bean;

import java.sql.Time;
import java.sql.Date;

public class TicketBean {

private int ticketID;
private int flightID;
private Double totalFare;
private int noOfChildren;
private Time departureTime;
private Date departureDate;
private Time arrivalTime;
private Date arrivalDate;
private String departureAirportCode;
private String arrivalAirportCode;
private String currency;
private int customerID;

public int getTicketID() {
	return ticketID;
}
public void setTicketID(int ticketID) {
	this.ticketID = ticketID;
}

public int getFlightID() {
	return flightID;
}
public void setFlightID(int flightID) {
	this.flightID = flightID;
}

public Double getTotalFare() {
	return totalFare;
}
public void setTotalFare(Double totalFare) {
	this.totalFare = totalFare;
}
	
public int getNoOfChildren() {
	return noOfChildren;
}
public void setNoOfChildren(int noOfChildren) {
	this.noOfChildren = noOfChildren;
}

public Time getDepartureTime() {
	return departureTime;
}
public void setDepartureTime(Time departureTime) {
	this.departureTime = departureTime;
}

public Date getDepartureDate() {
	return departureDate;
}
public void setDepartureDate(Date departureDate) {
	this.departureDate = departureDate;
}

public Time getArrivalTime() {
	return arrivalTime;
}
public void setArrivalTime(Time arrivalTime) {
	this.arrivalTime = arrivalTime;
}

public Date getArrivalDate() {
	return arrivalDate;
}
public void setArrivalDate(Date arrivalDate) {
	this.arrivalDate = arrivalDate;
}

public String getDepartureAirportCode() {
	return departureAirportCode;
}
public void setDepartureAirportCode(String departureAirportCode) {
	this.departureAirportCode = departureAirportCode;
}

public String getArrivalAirportCode() {
	return arrivalAirportCode;
}
public void setArrivalAirportCode(String arrivalAirportCode) {
	this.arrivalAirportCode = arrivalAirportCode;
}

public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}

public int getCustomerID() {
	return customerID;
}
public void setCustomerID(int customerID) {
	this.customerID = customerID;
}

}
