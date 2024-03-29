package main.java.de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import main.java.de.tum.in.dbpra.model.bean.*;
import main.java.de.tum.in.dbpra.model.dao.*;

public class FlightSegmentDAO extends AbstractDAO{
	
	
	public int  createNewFlightSegment(String arrivalDate, String departureDate, String arrivalTime, String departureTime, 
			int routeID) throws FlightSegmentInsertException{
		
		int flightSegmentId = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement stmt = null;
	
		String query = new StringBuilder()
		.append("INSERT INTO FLIGHTSEGMENT(ARRIVAL_TIME, ARRIVAL_DATE,	DEPARTURE_TIME,	DEPARTURE_DATE, ROUTEID)")
		.append("VALUES('"+arrivalTime+"','"+arrivalDate+"','"+departureTime+"','"+departureDate+"',"+routeID+")")
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
				   flightSegmentId = Integer.parseInt(resultSet.getString(1));
				
			    }
	    	
	    	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightSegmentInsertException();
		}
	   return flightSegmentId;	
		
		
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
					flightSeg.setFlightNr(resultSet.getInt(1));
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
	
	
	public FlightSegmentBean searchSegmentByFlightNR(FlightSegmentBean f) 
			throws FlightSegmentNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT FLIGHTNR, ARRIVAL_TIME, ARRIVAL_DATE, DEPARTURE_TIME, DEPARTURE_DATE, ROUTEID,SOURCE_CITY,DESTINATION_CITY ")
		.append("FROM FLIGHTSEGMENT f ")
		.append("WHERE ")
		.toString();
		
		query=query.concat("FLIGHTNR = '");
		query=query.concat(f.getFlightNr()+"' ");
		
		//List<FlightSegmentBean> flightSegs = new LinkedList<FlightSegmentBean>();
		FlightSegmentBean flightSeg;
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
					resultSet.next();
					//flightSeg = new FlightSegmentBean();
					f.setFlightNr(resultSet.getInt(1));
					f.setArrivalTime(resultSet.getTime(2));
					f.setArrivalDate(resultSet.getDate(3));
					f.setDepartureTime(resultSet.getTime(4));
					f.setDepartureDate(resultSet.getDate(5));
					f.setRouteId(resultSet.getInt(6));
					f.setSourceCity(resultSet.getString(7));
					f.setDestinationCity(resultSet.getString(8));
					resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new FlightSegmentNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightSegmentNotFoundException();
		}
		return f;
	}
	
	
	@SuppressWarnings("serial")
	public class FlightSegmentInsertException extends Throwable{
	}
	
	@SuppressWarnings("serial")
	public class FlightSegmentNotFoundException extends Throwable{
	}
}
