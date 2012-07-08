package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;

public class FlightDAO extends AbstractDAO{
	public void createNewFlight(Date arrivalDate, Date departureDate, Time arrivalTime, Time departureTime)
			throws FlightInsertException{
		
		String query = new StringBuilder()
		.append("INSERT INTO FLIGHT(ARRIVAL_TIME, ARRIVAL_DATE,	DEPARTURE_TIME,	DEPARTURE_DATE)")
		.append("VALUES(?, ?, ?, ?)")
		.toString();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setTime(1, arrivalTime);
			preparedStatement.setDate(2, arrivalDate);
			preparedStatement.setTime(3, departureTime);
			preparedStatement.setDate(4, departureDate);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightInsertException();
		}

	}
	
	@SuppressWarnings("serial")
	public class FlightInsertException extends Throwable{
	}
}

