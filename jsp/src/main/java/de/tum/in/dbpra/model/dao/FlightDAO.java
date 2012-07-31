package de.tum.in.dbpra.model.dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.FlightBean;

public class FlightDAO extends AbstractDAO {

	public int createNewFlight(String arrivalDate, String departureDate,
			String arrivalTime, String departureTime)
			throws FlightInsertException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement stmt = null;
		int flightId = 0;

		String query = new StringBuilder()
				.append("INSERT INTO FLIGHT(ARRIVAL_TIME, ARRIVAL_DATE,	DEPARTURE_TIME,	DEPARTURE_DATE) ")
				.append("VALUES(?, ?, ?, ?)").toString();

		String query2 = new StringBuilder().append(
				"SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1").toString();

		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, arrivalTime);
			preparedStatement.setString(2, arrivalDate);
			preparedStatement.setString(3, departureTime);
			preparedStatement.setString(4, departureDate);

			preparedStatement.executeUpdate();

			ResultSet resultSet = stmt.executeQuery(query2);
			while (resultSet.next()) {
				flightId = Integer.parseInt(resultSet.getString(1));

			}

			resultSet.close();
			stmt.close();
			connection.commit();
			connection.close();

		} catch (SQLException e) {
			if (connection != null) {
				try {
					System.err
							.print("Creation of new flight is being rolled back");
					connection.rollback();
					connection.close();
				} catch (SQLException excep) {
					System.err
							.print("SQL error occurs : " + excep.getMessage());
				}
			}

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.print("SQL error occurs : " + e.getMessage());
					e.printStackTrace();
				}

			}
		}

		return flightId;

	}

	public List<FlightBean> searchFlight(FlightBean f) 
			throws FlightNotFoundException{
		
		String query = new StringBuilder()
		.append("SELECT DISTINCT f.flightid, f.ARRIVAL_TIME, f.ARRIVAL_DATE, f.DEPARTURE_TIME, f.DEPARTURE_DATE FROM FLIGHT f, FLIGHT_CONSISTS_OF fco, FLIGHTSEGMENT fs, ROUTE r ")
		.append("WHERE f.flightid = fco.flightid AND fco.flightnr = fs.flightnr AND fs.routeid = r.routeid ")
		.append("AND f.DEPARTURE_DATE = ? AND r.apcode_src = ? AND f.flightid IN ")
		.append("( SELECT f2.flightid FROM FLIGHT f2, FLIGHT_CONSISTS_OF fco2, FLIGHTSEGMENT fs2, ROUTE r2 ")
		.append("WHERE f2.flightid = fco2.flightid AND fco2.flightnr = fs2.flightnr AND fs2.routeid = r2.routeid ")
		.append("AND r2.apcode_dst = ?) ")
		.toString();
		
		
		List<FlightBean> flightList = new LinkedList<FlightBean>();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setDate(1, f.getDepartureDate());
			preparedStatement.setString(2, f.getSourceCity());
			preparedStatement.setString(3, f.getDestinationCity());
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					FlightBean flight = new FlightBean();
					flight.setFlightID(resultSet.getInt(1));
					//flight.setSourceCity(resultSet.getString(2));
					//flight.setDestinationCity(resultSet.getString(3));
					flight.setArrivalTime(resultSet.getTime(2));
					flight.setArrivalDate(resultSet.getDate(3));
					flight.setDepartureTime(resultSet.getTime(4));
					flight.setDepartureDate(resultSet.getDate(5));
					flightList.add(flight);
				}
				resultSet.close();
			} catch (SQLException e) {
				if (connection != null) {
					try {
						System.err
								.print("Flight search could not be performed");
						connection.close();
					} catch (SQLException excep) {
						System.err.print("SQL error occurs : "
								+ excep.getMessage());
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightNotFoundException();
		}
		return flightList;
	}
	
	public FlightBean getFlightDetail(FlightBean f)
			throws FlightNotFoundException {

		String query = new StringBuilder()
				.append("SELECT f.flightid, r.apcode_src, r.apcode_dst, f.ARRIVAL_TIME, f.ARRIVAL_DATE, f.DEPARTURE_TIME, f.DEPARTURE_DATE ")
				.append("FROM FLIGHT f, FLIGHT_CONSISTS_OF fco, FLIGHTSEGMENT fs, ROUTE r ")
				.append("WHERE f.flightid = fco.flightid AND fco.flightnr = fs.flightnr AND fs.routeid = r.routeid AND ")
				.append("f.flightid =  ? ").toString();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setInt(1, f.getFlightID());
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				resultSet.next();
				f.setFlightID(resultSet.getInt(1));
				f.setSourceCity(resultSet.getString(2));
				f.setDestinationCity(resultSet.getString(3));
				f.setArrivalTime(resultSet.getTime(4));
				f.setArrivalDate(resultSet.getDate(5));
				f.setDepartureTime(resultSet.getTime(6));
				f.setDepartureDate(resultSet.getDate(7));

				resultSet.close();
			} catch (SQLException e) {
				if (connection != null) {
					try {
						System.err
								.print("Get flight detail could not be performed");
						connection.close();
					} catch (SQLException excep) {
						System.err.print("SQL error occurs : "
								+ excep.getMessage());
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightNotFoundException();
		}
		return f;
	}

	@SuppressWarnings("serial")
	public class FlightInsertException extends Throwable {
	}

	@SuppressWarnings("serial")
	public class FlightNotFoundException extends Throwable {
	}
}
