package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import de.tum.in.dbpra.model.bean.*;

public class CustomerDAO extends AbstractDAO {

	public int createNewCustomer(CustomerBean c) throws CustomerInsertException {

		try {
			return findCustomer(c);

		} catch (CustomerNotFoundException ce) {
			System.out.println("Create a new Customer");
			String query = new StringBuilder()
					.append("INSERT INTO CUSTOMER(CUSTOMERID,FNAME,LNAME,ADDRESS,COUNTRY,PASSPORTNO,DOB,SEX)")
					.append("VALUES(case when (SELECT MAX(CUSTOMERID) FROM CUSTOMER)+1 is null then 1 else (SELECT MAX(CUSTOMERID) FROM CUSTOMER)+1 end, ?, ?, ?, ?, ?, ?, ?)")
					.toString();

			try (Connection connection = getConnection();

					PreparedStatement preparedStatement = connection
							.prepareStatement(query);) {

				preparedStatement.setString(1, c.getFName());
				preparedStatement.setString(2, c.getLName());
				preparedStatement.setString(3, c.getAddress());
				preparedStatement.setString(4, c.getCountry());
				preparedStatement.setString(5, c.getPassportNO());
				preparedStatement.setDate(6, c.getDOB());
				preparedStatement.setString(7, c.getSex());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new CustomerInsertException();
			}

			int myCustomerID;

			String query2 = new StringBuilder().append("SELECT CUSTOMERID ")
					.append("FROM CUSTOMER ")
					.append("WHERE FNAME = ? AND LNAME = ? AND DOB = ? ")
					.toString();

			try (Connection connection = getConnection();

					PreparedStatement preparedStatement2 = connection
							.prepareStatement(query2);) {

				preparedStatement2.setString(1, c.getFName());
				preparedStatement2.setString(2, c.getLName());
				preparedStatement2.setDate(3, c.getDOB());

				try (ResultSet resultSet = preparedStatement2.executeQuery();) {
					resultSet.next();
					myCustomerID = resultSet.getInt(1);

					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new CustomerInsertException();
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new CustomerInsertException();
			}
			return myCustomerID;

		}
	}

	public int findCustomer(CustomerBean c) throws CustomerNotFoundException {

		String query = new StringBuilder().append("SELECT CUSTOMERID ")
				.append("FROM CUSTOMER ")
				.append("WHERE FNAME = ? AND LNAME = ? AND DOB = ? ")
				.toString();

		int myCustomerID;

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setString(1, c.getFName());
			preparedStatement.setString(2, c.getLName());
			preparedStatement.setDate(3, c.getDOB());

			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					myCustomerID = resultSet.getInt(1);
				} else {
					throw new CustomerNotFoundException();
				}
				resultSet.close();
			} catch (SQLException e) {
				throw new CustomerNotFoundException();
			}

		} catch (SQLException e) {
			throw new CustomerNotFoundException();
		}
		return myCustomerID;
	}

	@SuppressWarnings("serial")
	public class CustomerInsertException extends Throwable {
	}

	@SuppressWarnings("serial")
	public class CustomerNotFoundException extends Throwable {
	}
}
