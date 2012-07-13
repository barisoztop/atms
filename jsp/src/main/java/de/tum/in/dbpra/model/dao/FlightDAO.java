package de.tum.in.dbpra.model.dao;

import java.sql.*;
import java.util.Date;

import de.tum.in.dbpra.model.bean.AirportBean;

public class FlightDAO extends AbstractDAO{
	
	
	
	public int createNewFlight(String arrivalDate,String departureDate,String arrivalTime, String departureTime)
			throws FlightInsertException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement stmt = null;
		int flightId = 0;
		
		String query = new StringBuilder()
		.append("INSERT INTO FLIGHT(ARRIVAL_TIME, ARRIVAL_DATE,	DEPARTURE_TIME,	DEPARTURE_DATE) ")
		.append("VALUES('"+arrivalTime+"','"+arrivalDate+"','"+departureTime+"','"+departureDate+"')")
		.toString();
		
		String query2 =  new StringBuilder()
		.append("SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1").toString();
		
		
		try  {
			    connection = getConnection();
		        stmt = connection.createStatement();
			    preparedStatement = connection.prepareStatement(query);
			    preparedStatement.executeUpdate();
			
			    ResultSet resultSet = stmt.executeQuery(query2);
			    while (resultSet.next()) {
				       flightId = Integer.parseInt(resultSet.getString(1));
				      
			    }
			    
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightInsertException();
		}
		
	return flightId;	

	}
	
	@SuppressWarnings("serial")
	public class FlightInsertException extends Throwable{
	}
}

