package main.java.de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


import main.java.de.tum.in.dbpra.model.bean.*;
import main.java.de.tum.in.dbpra.model.dao.*;


public class TransactionDAO extends AbstractDAO{
	
	//AGENTID , FLIGHTID and T_TIMESTAMP need to be known prior??
	public void createNewTransaction(TransactionBean t) throws TransactionInsertException{
		
		String query = new StringBuilder()
		.append("INSERT INTO TRANSACTIONS(AGENTID,FLIGHTID,T_TIMESTAMP,CURRENCY,T_STATUS,MODEOFPAYMENT,AMOUNT,TYPEOFTRANSACTION,CUSTOMERID )")
		.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")
		.toString();
		
		//set current time
		java.util.Date today = new java.util.Date();
	    Timestamp timeStamp = new java.sql.Timestamp(today.getTime());
		
	    //mock-up data
	    t.setAmount(0.0);
	    
//	    System.out.println(t.getAgentID());
//	    System.out.println(t.getFlightID());
//	    System.out.println(timeStamp.toString());
//	    System.out.println(t.getCurrency());
//	    System.out.println(t.gett_status());
//	    System.out.println(t.getModeOfPayment());
//	    System.out.println(t.getAmount());
//	    System.out.println(t.getTypeOfTransaction());
	    
		try (Connection connection = getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setInt(1, t.getAgentID());
			preparedStatement.setInt(2, t.getFlightID());
			preparedStatement.setTimestamp(3, timeStamp);
			preparedStatement.setString(4, t.getCurrency());
			preparedStatement.setString(5, t.gett_status());
			preparedStatement.setString(6, t.getModeOfPayment());
			preparedStatement.setDouble(7, t.getAmount());
			preparedStatement.setString(8, t.getTypeOfTransaction());
			preparedStatement.setInt(9, t.getCustomerID());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TransactionInsertException();
		}
	}
	
	
	
	
	//findTransaction by using AGENTID and FLIGHTID
	public TransactionBean findTransaction(TransactionBean t) 
			throws TransactionNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT AGENTID,FLIGHTID,T_TIMESTAMP,CURRENCY,T_STATUS,MODEOFPAYMENT,AMOUNT,TYPEOFTRANSACTION,CUSTOMERID ")
		.append("FROM TRANSACTIONS t ")
		.append("WHERE AGENTID = ? AND FLIGHTID = ?")
		.toString();
		
		TransactionBean myTransaction;
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setInt(1, t.getAgentID());
			preparedStatement.setInt(2, t.getFlightID());
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				resultSet.next();
	
				myTransaction = new TransactionBean();
				myTransaction.setAgentID(resultSet.getInt(1));
				myTransaction.setFlightID(resultSet.getInt(2));
				myTransaction.sett_timestamp(resultSet.getTimestamp(3));
				myTransaction.setCurrency(resultSet.getString(4));
				myTransaction.sett_status(resultSet.getString(5));
				myTransaction.setModeOfPayment(resultSet.getString(6));
				myTransaction.setAmount(resultSet.getDouble(7));
				myTransaction.setTypeOfTransaction(resultSet.getString(8));
				myTransaction.setCustomerID(resultSet.getInt(9));
				
				resultSet.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new TransactionNotFoundException();
			}
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new TransactionNotFoundException();
		}
		return myTransaction;
	}
	
	@SuppressWarnings("serial")
	public class TransactionInsertException extends Throwable{
	}
	
	@SuppressWarnings("serial")
	public class TransactionNotFoundException extends Throwable{
	}
}
