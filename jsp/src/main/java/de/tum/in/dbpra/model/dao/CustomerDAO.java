package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.tum.in.dbpra.model.bean.CustomerBean;

public class CustomerDAO extends AbstractDAO {

	public CustomerBean getCustomerByID(CustomerBean customer) throws CustomerNotFoundException {
		String query = new StringBuilder()
			.append("SELECT name, address ")
			.append("FROM customer ")
			.append("WHERE custkey = ?")
			.toString();

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, customer.getId());

			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					customer.setName(resultSet.getString(1));
					customer.setAddress(resultSet.getString(2));
				} else {
					throw new CustomerNotFoundException();
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
		return customer;
	}
	
	@SuppressWarnings("serial")
	public static class CustomerNotFoundException extends Throwable {
	}
}
