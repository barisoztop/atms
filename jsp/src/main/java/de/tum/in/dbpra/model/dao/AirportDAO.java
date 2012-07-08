package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO extends AbstractDAO{

	public List<String> getAirportNames() {

		List<String> airportNames = new ArrayList<String>();
		
		String query = new StringBuilder()
		.append("SELECT apname ")
		.append("FROM airport ")
		.toString();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {
					airportNames.add(resultSet.getString(1));
				} 

			} 
			catch (SQLException e) {

				e.printStackTrace();
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
		
		return airportNames;

	}

	public String getAirportCode(String airportName) {

		String apcode = null;

		String query = new StringBuilder()
		.append("SELECT apcode ")
		.append("FROM airport ")
		.append("WHERE apname = ?")
		.toString();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setString(1, airportName);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {
					apcode = resultSet.getString(1);
				} 

			} 
			catch (SQLException e) {

				e.printStackTrace();
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
		return apcode;
	}


}
