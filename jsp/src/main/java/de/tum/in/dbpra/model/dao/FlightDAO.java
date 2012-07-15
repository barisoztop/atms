package de.tum.in.dbpra.model.dao;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.AirportBean;
import de.tum.in.dbpra.model.bean.FlightBean;
import de.tum.in.dbpra.model.bean.FlightSegmentBean;
import de.tum.in.dbpra.model.dao.FlightSegmentDAO.FlightSegmentNotFoundException;
import de.tum.in.dbpra.model.dao.RouteDAO.RoutePair;

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
	
	public List<FlightBean> searchFlight(FlightBean f) 
			throws FlightNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT FLIGHTID,SOURCE_CITY,DESTINATION_CITY,ARRIVAL_TIME,ARRIVAL_DATE,DEPARTURE_TIME,DEPARTURE_DATE ")
		.append("FROM FLIGHT f ")
		.append("WHERE ")
		.toString();
		
		query=query.concat("SOURCE_CITY = '");
		query=query.concat(f.getSourceCity()+"' and " );
		query=query.concat("DESTINATION_CITY = '");
		query=query.concat(f.getDestinationCity()+"'  and ");
		query=query.concat("DEPARTURE_DATE = '");
		//Maybe date.toString() has different format with the database
		query=query.concat(f.getDepartureDate().toString()+"' ");
		//query.concat("ARRIVAL_DATE = '");
		//query.concat(f.getArrivalDate().toString()+"' ");
		
		List<FlightBean> flightList = new LinkedList<FlightBean>();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					FlightBean flight = new FlightBean();
					flight.setFlightID(resultSet.getInt(1));
					flight.setSourceCity(resultSet.getString(2));
					flight.setDestinationCity(resultSet.getString(3));
					flight.setArrivalTime(resultSet.getTime(4));
					flight.setArrivalDate(resultSet.getDate(5));
					flight.setDepartureTime(resultSet.getTime(6));
					flight.setDepartureDate(resultSet.getDate(7));
					flightList.add(flight);
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new FlightNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightNotFoundException();
		}
		return flightList;
	}
	
	public FlightBean getFlightDetail(FlightBean f) 
			throws FlightNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT FLIGHTID,SOURCE_CITY,DESTINATION_CITY,ARRIVAL_TIME,ARRIVAL_DATE,DEPARTURE_TIME,DEPARTURE_DATE ")
		.append("FROM FLIGHT f ")
		.append("WHERE ")
		.toString();
		
		query = query.concat("FLIGHTID = ");
		query = query.concat(f.getFlightID()+" ");
		
		
		//List<FlightBean> flightList = new LinkedList<FlightBean>();
		FlightBean flight;
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
					resultSet.next();
					//flight = new FlightBean();
					f.setFlightID(resultSet.getInt(1));
					f.setSourceCity(resultSet.getString(2));
					f.setDestinationCity(resultSet.getString(3));
					f.setArrivalTime(resultSet.getTime(4));
					f.setArrivalDate(resultSet.getDate(5));
					f.setDepartureTime(resultSet.getTime(6));
					f.setDepartureDate(resultSet.getDate(7));
					
					resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new FlightNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightNotFoundException();
		}
		return f;
	}
	
	@SuppressWarnings("serial")
	public class FlightInsertException extends Throwable{
	}
	
	@SuppressWarnings("serial")
	public class FlightNotFoundException extends Throwable{
	}
}

