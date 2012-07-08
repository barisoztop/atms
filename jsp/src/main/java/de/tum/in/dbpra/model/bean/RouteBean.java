package de.tum.in.dbpra.model.bean;

public class RouteBean {
	private int routeID;
	private String apcodeSource;
	private String apcodeDestination;
	/**
	 * @return the routeID
	 */
	public int getRouteID() {
		return routeID;
	}
	/**
	 * @param routeID the routeID to set
	 */
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	/**
	 * @return the apcodeSource
	 */
	public String getApcodeSource() {
		return apcodeSource;
	}
	/**
	 * @param apcodeSource the apcodeSource to set
	 */
	public void setApcodeSource(String apcodeSource) {
		this.apcodeSource = apcodeSource;
	}
	/**
	 * @return the apcodeDestination
	 */
	public String getApcodeDestination() {
		return apcodeDestination;
	}
	/**
	 * @param apcodeDestination the apcodeDestination to set
	 */
	public void setApcodeDestination(String apcodeDestination) {
		this.apcodeDestination = apcodeDestination;
	}
	

}
