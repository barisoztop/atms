package de.tum.in.dbpra.model.bean;

public class RoutePair {

	private int firstRouteID;
	private int secondRouteID;
	private String firstSourceAirportCode;
	private String firstDestinationAirportCode;
	private String secondSourceAirportCode;
	private String secondDestinationAirportCode;

	/**
	 * @return the firstSourceAirportCode
	 */
	public String getFirstSourceAirportCode() {
		return firstSourceAirportCode;
	}

	/**
	 * @param firstSourceAirportCode
	 *            the firstSourceAirportCode to set
	 */
	public void setFirstSourceAirportCode(String firstSourceAirportCode) {
		this.firstSourceAirportCode = firstSourceAirportCode;
	}

	/**
	 * @return the firstDestinationAirportCode
	 */
	public String getFirstDestinationAirportCode() {
		return firstDestinationAirportCode;
	}

	/**
	 * @param firstDestinationAirportCode
	 *            the firstDestinationAirportCode to set
	 */
	public void setFirstDestinationAirportCode(
			String firstDestinationAirportCode) {
		this.firstDestinationAirportCode = firstDestinationAirportCode;
	}

	/**
	 * @return the secondSourceAirportCode
	 */
	public String getSecondSourceAirportCode() {
		return secondSourceAirportCode;
	}

	/**
	 * @param secondSourceAirportCode
	 *            the secondSourceAirportCode to set
	 */
	public void setSecondSourceAirportCode(String secondSourceAirportCode) {
		this.secondSourceAirportCode = secondSourceAirportCode;
	}

	/**
	 * @return the secondDestinationAirportCode
	 */
	public String getSecondDestinationAirportCode() {
		return secondDestinationAirportCode;
	}

	/**
	 * @param secondDestinationAirportCode
	 *            the secondDestinationAirportCode to set
	 */
	public void setSecondDestinationAirportCode(
			String secondDestinationAirportCode) {
		this.secondDestinationAirportCode = secondDestinationAirportCode;
	}

	/**
	 * @return the firstRouteID
	 */
	public int getFirstRouteID() {
		return firstRouteID;
	}

	/**
	 * @param firstRouteID
	 *            the firstRouteID to set
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
	 * @param secondRouteID
	 *            the secondRouteID to set
	 */
	public void setSecondRouteID(int secondRouteID) {
		this.secondRouteID = secondRouteID;
	}

}
