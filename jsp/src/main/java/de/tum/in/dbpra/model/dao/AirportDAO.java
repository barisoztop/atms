package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import de.tum.in.dbpra.model.bean.AirportBean;


public class AirportDAO extends AbstractDAO{
	
	
	public ArrayList getAirportList() throws AirportNotFoundException {
		
		Connection connection = null;
		Statement stmt = null;
		
		AirportBean airport;
		ArrayList list = new ArrayList();
		
		String query = new StringBuilder()
	        .append("SELECT APCODE, APNAME")
			.append("  FROM airport ")
			.toString();
		

		try{
			connection = getConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);

				while (resultSet.next()) {
			     		
				 airport = new AirportBean();
				 airport.setApcode(resultSet.getString(1));
				 airport.setApname(resultSet.getString(2));
				 list.add(airport);
			}

				resultSet.close();
				connection.commit();
		}catch(SQLException e ){
			if (connection != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                connection.rollback();
	            } catch(SQLException excep) {
	            	System.err.print("SQL error occurs : "+excep.getMessage());
	            }

			}
		} finally {
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.print("SQL error occurs : "+e.getMessage());
					e.printStackTrace();
				}

			}
		}


 return list;

}	
	

	
	// Useless methods - need to remove
	
	
	
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

	
public static class AirportNotFoundException extends Throwable {
   }


}

