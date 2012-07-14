package main.java.de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.de.tum.in.dbpra.model.bean.*;
import main.java.de.tum.in.dbpra.model.dao.*;

public class TicketDAO extends AbstractDAO{
	

	public void createNewTicket(TicketBean t) throws TicketInsertException{
		
		String query = new StringBuilder()
		.append("INSERT INTO TICKET(TICKETID,FLIGHTID,TOTALFARE,NOOFCHILDREN,DEPARTURETIME,DEPARTUREDATE,DEPARTUREAIRPORTCODE,CURRENCY,ARRIVALAIRPORTCODE,ARRIVALTIME,ARRIVALDATE,CUSTOMERID)")
		.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
		.toString();
		
		try (Connection connection = getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setInt(1, t.getTicketID());
			preparedStatement.setInt(2, t.getFlightID());
			preparedStatement.setDouble(3, t.getTotalFare());
			preparedStatement.setInt(4, t.getNoOfChildren());
			preparedStatement.setTime(5, t.getDepartureTime());
			preparedStatement.setDate(6, t.getDepartureDate());
			preparedStatement.setString(7, t.getDepartureAirportCode());
			preparedStatement.setString(8, t.getCurrency());
			preparedStatement.setString(9, t.getArrivalAirportCode());
			preparedStatement.setTime(10, t.getArrivalTime());
			preparedStatement.setDate(11, t.getArrivalDate());
			preparedStatement.setInt(12, t.getCustomerID());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TicketInsertException();
		}
	}
	
	
	
	
	
	public TicketBean findTicket(TicketBean t) 
			throws TicketNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT TICKETID,FLIGHTID,TOTALFARE,NOOFCHILDREN,DEPARTURETIME,DEPARTUREDATE,DEPARTUREAIRPORTCODE,CURRENCY,ARRIVALAIRPORTCODE,ARRIVALTIME,ARRIVALDATE,CUSTOMERID ")
		.append("FROM TICKET t ")
		.append("WHERE TICKETID = ? ")
		.toString();
		
		TicketBean myTicket;
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setInt(1, t.getTicketID());
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				resultSet.next();
	
				myTicket = new TicketBean();
				myTicket.setTicketID(resultSet.getInt(1));
				myTicket.setFlightID(resultSet.getInt(2));
				myTicket.setTotalFare(resultSet.getDouble(3));
				myTicket.setNoOfChildren(resultSet.getInt(4));
				myTicket.setDepartureTime(resultSet.getTime(5));
				myTicket.setDepartureDate(resultSet.getDate(6));
				myTicket.setDepartureAirportCode(resultSet.getString(7));
				myTicket.setCurrency(resultSet.getString(8));
				myTicket.setArrivalAirportCode(resultSet.getString(9));
				myTicket.setArrivalTime(resultSet.getTime(10));
				myTicket.setArrivalDate(resultSet.getDate(11));
				myTicket.setCustomerID(resultSet.getInt(12));
				
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new TicketNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TicketNotFoundException();
		}
		return myTicket;
	}
	
	@SuppressWarnings("serial")
	public class TicketInsertException extends Throwable{
	}
	
	@SuppressWarnings("serial")
	public class TicketNotFoundException extends Throwable{
	}
}
