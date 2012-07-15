package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.FlightSegmentBean;
import de.tum.in.dbpra.model.dao.FlightSegmentDAO.FlightSegmentNotFoundException;

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
	
	public List<FlightSegmentBean> getListOfSegment(int flightID) 
			throws FlightSegmentNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT FLIGHTID, FLIGHTNR ")
		.append("FROM FLIGHT_CONSISTS_OF f ")
		.append("WHERE ")
		.toString();
		
		query=query.concat("FLIGHTID = ");
		query=query.concat(Integer.toString(flightID)+" ");
		
		List<FlightSegmentBean> flightSegs = new LinkedList<FlightSegmentBean>();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					FlightSegmentBean flightSeg = new FlightSegmentBean();
					flightSeg.setFlightNr(resultSet.getInt(2));
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
	public class FlightSegAssocInsertException extends Throwable{
	}
	
	@SuppressWarnings("serial")
	public class FlightSegmentNotFoundException extends Throwable{
	}
}
