package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlightConsistsOfDAO extends AbstractDAO{
	public void associateSegmentToFlight(int flightID, int flightNr) throws FlightSegAssocInsertException{
		
		String query = new StringBuilder()
		.append("INSERT INTO FLIGHT_CONSISTS_OF(FLIGHTID, FLIGHTNR) ")
		.append("VALUES(?, ?")
		.toString();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, flightID);
			preparedStatement.setInt(2, flightNr);
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
