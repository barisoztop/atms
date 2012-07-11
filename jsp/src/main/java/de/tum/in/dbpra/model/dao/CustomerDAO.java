package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.CustomerBean;

public class CustomerDAO extends AbstractDAO{
	

	public void createNewCustomer(CustomerBean c) throws CustomerInsertException{
		
		String query = new StringBuilder()
		.append("INSERT INTO CUSTOMER(CUSTOMERID,FNAME,LNAME,ADDRESS,COUNTRY,PASSPORTNO,DOB,SEX)")
		.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?)")
		.toString();
		
		try (Connection connection = getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setInt(1, c.getCustomerID());
			preparedStatement.setString(2, c.getFName());
			preparedStatement.setString(3, c.getLName());
			preparedStatement.setString(4, c.getAddress());
			preparedStatement.setString(5, c.getCountry());
			preparedStatement.setString(6, c.getPassportNO());
			preparedStatement.setDate(7, c.getDOB());
			preparedStatement.setString(8, c.getSex());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomerInsertException();
		}
	}
	
	
	
	
	//require CustomerBean with FName and LName
	public List<CustomerBean> findCustomerByFullName(CustomerBean c) 
			throws CustomerNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT CUSTOMERID,FNAME,LNAME,ADDRESS,COUNTRY,PASSPORTNO,DOB,SEX ")
		.append("FROM CUSTOMER c ")
		.append("WHERE FNAME = ? AND LNAME = ? ")
		.toString();
		
		List<CustomerBean> myCustomers = new LinkedList<CustomerBean>();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setString(1, c.getFName());
			preparedStatement.setString(2, c.getLName());
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					CustomerBean customer = new CustomerBean();
					customer.setCustomerID(resultSet.getInt(1));
					customer.setFName(resultSet.getString(2));
					customer.setLName(resultSet.getString(3));
					customer.setAddress(resultSet.getString(4));
					customer.setCountry(resultSet.getString(5));
					customer.setPassportNO(resultSet.getString(6));
					myCustomers.add(customer);
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CustomerNotFoundException();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomerNotFoundException();
		}
		return myCustomers;
	}
	
	@SuppressWarnings("serial")
	public class CustomerInsertException extends Throwable{
	}
	
	@SuppressWarnings("serial")
	public class CustomerNotFoundException extends Throwable{
	}
}
