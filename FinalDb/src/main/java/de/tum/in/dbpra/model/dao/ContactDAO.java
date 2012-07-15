package main.java.de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.de.tum.in.dbpra.model.bean.*;

public class ContactDAO extends AbstractDAO {

	public void createNewContact(ContactBean c) throws ContactInsertException {

		try {
			// IF it already exists, we don't have to create a new one
			findContact(c);
			System.out.println("Use existing contact");
		} catch (ContactNotFoundException ce) {

			String query = new StringBuilder()
					.append("INSERT INTO CONTACTS(CUSTOMERID,AGENTID,CURRENCY,STATUS,MODEOFPAYMENT,AMOUNT)")
					.append("VALUES(?, ?, ?, ?, ?, ?)").toString();

			// mock-up data
			c.setAmount(0.0);

			try (Connection connection = getConnection();

					PreparedStatement preparedStatement = connection
							.prepareStatement(query);) {

				preparedStatement.setInt(1, c.getCustomerID());
				preparedStatement.setInt(2, c.getAgentID());
				preparedStatement.setString(3, c.getCurrency());
				preparedStatement.setString(4, c.getStatus());
				preparedStatement.setString(5, c.getModeOfPayment());
				preparedStatement.setDouble(6, c.getAmount());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new ContactInsertException();
			}

		}
	}

	public ContactBean findContact(ContactBean c)
			throws ContactNotFoundException {

		String query = new StringBuilder()
				.append("SELECT CUSTOMERID,AGENTID,CURRENCY,STATUS,MODEOFPAYMENT,AMOUNT ")
				.append("FROM CONTACTS c ")
				.append("WHERE CUSTOMERID = ? AND AGENTID = ?").toString();

		ContactBean myContact;

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setInt(1, c.getCustomerID());
			preparedStatement.setInt(2, c.getAgentID());

			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				resultSet.next();

				myContact = new ContactBean();
				myContact.setCustomerID(resultSet.getInt(1));
				myContact.setAgentID(resultSet.getInt(2));
				myContact.setCurrency(resultSet.getString(3));
				myContact.setStatus(resultSet.getString(4));
				myContact.setModeOfPayment(resultSet.getString(5));
				myContact.setAmount(resultSet.getDouble(6));

				resultSet.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new ContactNotFoundException();
			}

		} catch (SQLException e) {
			//e.printStackTrace();
			throw new ContactNotFoundException();
		}
		return myContact;
	}

	@SuppressWarnings("serial")
	public class ContactInsertException extends Throwable {
	}

	@SuppressWarnings("serial")
	public class ContactNotFoundException extends Throwable {
	}
}
