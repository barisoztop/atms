package main.java.de.tum.in.dbpra.model.bean;

public class RoutePairBean {
	
	private int firstRouteID;
	private int secondRouteID;
	private String firstSourceAirport;
	private String firstAirportDestination;
	private String secondSourceAirport;
	private String secondAirportDestination;
	/**
	 * @return the firstRouteID
	 */
	public int getFirstRouteID() {
		return firstRouteID;
	}
	/**
	 * @param firstRouteID the firstRouteID to set
	 */
	public void setFirstRouteID(int firstRouteID) {
		this.firstRouteID = firstRouteID;
	}
	/**
	 * @return the secondRouteID
	 */
	public int getSecondRouteID() {
		return secondRouteID;
	}
	/**
	 * @param secondRouteID the secondRouteID to set
	 */
	public void setSecondRouteID(int secondRouteID) {
		this.secondRouteID = secondRouteID;
	}
	public String getFirstSourceAirport() {
		return firstSourceAirport;
	}
	public void setFirstSourceAirport(String firstSourceAirport) {
		this.firstSourceAirport = firstSourceAirport;
	}
	public String getFirstAirportDestination() {
		return firstAirportDestination;
	}
	public void setFirstAirportDestination(String firstAirportDestination) {
		this.firstAirportDestination = firstAirportDestination;
	}
	public String getSecondSourceAirport() {
		return secondSourceAirport;
	}
	public void setSecondSourceAirport(String secondSourceAirport) {
		this.secondSourceAirport = secondSourceAirport;
	}
	public String getSecondAirportDestination() {
		return secondAirportDestination;
	}
	public void setSecondAirportDestination(String secondAirportDestination) {
		this.secondAirportDestination = secondAirportDestination;
	}

}
