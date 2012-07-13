package de.tum.in.dbpra.model.bean;

import java.sql.Time;
import java.util.Date;

public class FlightSegmentBean {
	private int filghtNr;
	private int routeID;
	private Date arrivalDate;
	private Date departureDate;
	private Time arrivalTime;
	private Time departureTime;
	
	public int getFlightNr() {
		return filghtNr;
	}
	public void setFlightNr(int filghtNr) {
		this.filghtNr = filghtNr;
	}
	public int getRouteId() {
		return routeID;
	}
	public void setRouteId(int routeId) {
		this.routeID = routeId;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
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
	public Time getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}
	
}
