package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import de.tum.in.dbpra.model.bean.FlightSegmentBean;

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
	
	/*public List<FlightSegmentBean> findSegsForPotentialFlight(List<RoutePair> routePairs){
		
	}*/
	
	@SuppressWarnings("serial")
	public class FlightSegmentInsertException extends Throwable{
	}
}
