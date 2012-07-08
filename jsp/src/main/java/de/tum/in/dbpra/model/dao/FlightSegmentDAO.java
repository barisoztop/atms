package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.FlightSegmentBean;
import de.tum.in.dbpra.model.dao.RouteDAO.RoutePair;

public class FlightSegmentDAO extends AbstractDAO{
	public void createNewFlightSegment(Date arrivalDate, Date departureDate, Time arrivalTime, Time departureTime, 
			int routeID) throws FlightSegmentInsertException{
		
		String query = new StringBuilder()
		.append("INSERT INTO FLIGHTSEGMENT(ARRIVAL_TIME, ARRIVAL_DATE,	DEPARTURE_TIME,	DEPARTURE_DATE, ROUTEID)")
		.append("VALUES(?, ?, ?, ?, ?)")
		.toString();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setTime(1, arrivalTime);
			preparedStatement.setDate(2, arrivalDate);
			preparedStatement.setTime(3, departureTime);
			preparedStatement.setDate(4, departureDate);
			preparedStatement.setInt(5, routeID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightSegmentInsertException();
		}
	}
	
	public List<FlightSegmentBean> findSegsForPotentialFlight(List<RoutePair> routePairs) 
			throws FlightSegmentNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT FLIGHTNR, ARRIVAL_TIME, ARRIVAL_DATE, DEPARTURE_TIME, DEPARTURE_DATE, ROUTEID ")
		.append("FROM FLIGHTSEGMENT f1, FLIGHTSEGMENT f2 ")
		.append("WHERE ")
		.toString();
		
		for (RoutePair routePair : routePairs) {
			query.concat("(");
			query.concat("f1.ROUTEID = " + routePair.getFirstRouteID() + " AND " + 
					"f2.ROUTEID = " + routePair.getSecondRouteID());
			query.concat(") ");
			if((routePairs.size() > 1) && (routePairs.indexOf(routePair) < (routePairs.size() - 1))){
				query.concat(" OR ");
			}
		}
		
		List<FlightSegmentBean> flightSegs = new LinkedList<FlightSegmentBean>();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					FlightSegmentBean flightSeg = new FlightSegmentBean();
					flightSeg.setFilghtNr(resultSet.getInt(1));
					flightSeg.setArrivalTime(resultSet.getTime(2));
					flightSeg.setArrivalDate(resultSet.getDate(3));
					flightSeg.setDepartureTime(resultSet.getTime(4));
					flightSeg.setDepartureDate(resultSet.getDate(5));
					flightSeg.setRouteId(resultSet.getInt(6));
					flightSegs.add(flightSeg);
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new FlightSegmentNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightSegmentNotFoundException();
		}
		return flightSegs;
	}
	
	@SuppressWarnings("serial")
	public class FlightSegmentInsertException extends Throwable{
	}
	
	@SuppressWarnings("serial")
	public class FlightSegmentNotFoundException extends Throwable{
	}
}
