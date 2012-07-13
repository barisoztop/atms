package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlightConsistsOfDAO extends AbstractDAO{
	public void associateSegmentToFlight(int flightID, int flightNr) throws FlightSegAssocInsertException{
		
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = new StringBuilder()
		.append("INSERT INTO FLIGHT_CONSISTS_OF(FLIGHTID, FLIGHTNR) ")
		.append("VALUES("+flightID+","+flightNr+")")
		.toString();
		
		try 
		{
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightSegAssocInsertException();
		}

	}
	
	@SuppressWarnings("serial")
	public class FlightSegAssocInsertException extends Throwable{
	}
}
