package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.FlightSegmentBean;
import de.tum.in.dbpra.model.dao.RouteDAO.RoutePair;

public class FlightSegmentDAO extends AbstractDAO {

	public int createNewFlightSegment(String arrivalDate, String departureDate,
			String arrivalTime, String departureTime, int routeID)
			throws FlightSegmentInsertException {

		int flightSegmentId = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement stmt = null;

		String query = new StringBuilder()
				.append("INSERT INTO FLIGHTSEGMENT(ARRIVAL_TIME, ARRIVAL_DATE,	DEPARTURE_TIME,	DEPARTURE_DATE, ROUTEID)")
				.append("VALUES(?, ?, ?, ?, ?)").toString();

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
			preparedStatement.setInt(5, routeID);

			preparedStatement.executeUpdate();
			ResultSet resultSet = stmt.executeQuery(query2);

			while (resultSet.next()) {
				flightSegmentId = Integer.parseInt(resultSet.getString(1));

			}

			resultSet.close();
			stmt.close();
			connection.commit();
			connection.close();

		} catch (SQLException e) {
			if (connection != null) {
				try {
					System.err
							.print("Creation of new flight segment is being rolled back");
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
		return flightSegmentId;

	}

	public class SegmentPair {
		private FlightSegmentBean firstSegment;
		private FlightSegmentBean secondSegmentNr;

		public FlightSegmentBean getFirstSegment() {
			return firstSegment;
		}

		public void setFirstSegment(FlightSegmentBean firstSegment) {
			this.firstSegment = firstSegment;
		}

		public FlightSegmentBean getSecondSegmentNr() {
			return secondSegmentNr;
		}

		public void setSecondSegmentNr(FlightSegmentBean secondSegmentNr) {
			this.secondSegmentNr = secondSegmentNr;
		}
	}

	/**
	 * Finds the segments(if they exist) that corresponds to the route pairs.
	 * Right now only handles one route pair for each flight. So it only find the segments 
	 * for the flights possible with two segments.
	 * 
	 * @param routePairs
	 *            the pairs containing the routes
	 * @return a list of SegmentPairs which can be combined to create a flight
	 *         satisfying routes needed
	 * @throws FlightSegmentNotFoundException
	 */
	public List<SegmentPair> findSegsForPotentialFlight(
			List<RoutePair> routePairs) throws FlightSegmentNotFoundException {

		List<SegmentPair> segPairs = new LinkedList<SegmentPair>();
		for (RoutePair routePair : routePairs) {
			String query = new StringBuilder()
					.append("SELECT f1.FLIGHTNR, f1.ARRIVAL_TIME, f1.ARRIVAL_DATE, f1.DEPARTURE_TIME, f1.DEPARTURE_DATE, f1.ROUTEID, f2.FLIGHTNR, f2.ARRIVAL_TIME, f2.ARRIVAL_DATE, f2.DEPARTURE_TIME, f2.DEPARTURE_DATE, f2.ROUTEID ")
					.append("FROM FLIGHTSEGMENT f1, FLIGHTSEGMENT f2 ")
					.append("WHERE f1.ROUTEID = ? AND f2.ROUTEID = ? AND f1.ARRIVAL_DATE <= f2.DEPARTURE_DATE AND f1.ARRIVAL_TIME < f2.DEPARTURE_TIME")
					.toString();

			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection
							.prepareStatement(query);) {

				preparedStatement.setInt(1, routePair.getFirstRouteID());
				preparedStatement.setInt(2, routePair.getSecondRouteID());

				try (ResultSet resultSet = preparedStatement.executeQuery();) {
					while (resultSet.next()) {
						FlightSegmentBean flightSeg1 = new FlightSegmentBean();
						flightSeg1.setFlightNr(resultSet.getInt(1));
						flightSeg1.setArrivalTime(resultSet.getTime(2));
						flightSeg1.setArrivalDate(resultSet.getDate(3));
						flightSeg1.setDepartureTime(resultSet.getTime(4));
						flightSeg1.setDepartureDate(resultSet.getDate(5));
						flightSeg1.setRouteId(resultSet.getInt(6));
						FlightSegmentBean flightSeg2 = new FlightSegmentBean();
						flightSeg2.setFlightNr(resultSet.getInt(7));
						flightSeg2.setArrivalTime(resultSet.getTime(8));
						flightSeg2.setArrivalDate(resultSet.getDate(9));
						flightSeg2.setDepartureTime(resultSet.getTime(10));
						flightSeg2.setDepartureDate(resultSet.getDate(11));
						flightSeg2.setRouteId(resultSet.getInt(12));
						SegmentPair segPair = new SegmentPair();
						segPair.setFirstSegment(flightSeg1);
						segPair.setSecondSegmentNr(flightSeg2);
						segPairs.add(segPair);
					}
					resultSet.close();
				} catch (SQLException e) {
					if (connection != null) {
						try {
							System.err
									.print("Flight segment search could not be performed");
							connection.close();
						} catch (SQLException excep) {
							System.err.print("SQL error occurs : "
									+ excep.getMessage());
						}
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new FlightSegmentNotFoundException();
			}
		}

		return segPairs;
	}

	public FlightSegmentBean searchSegmentByFlightNR(FlightSegmentBean f)
			throws FlightSegmentNotFoundException {

		String query = new StringBuilder()
				.append("SELECT f.FLIGHTNR, f.ARRIVAL_TIME, f.ARRIVAL_DATE, f.DEPARTURE_TIME, f.DEPARTURE_DATE, f.ROUTEID, r.apcode_src, r.apcode_dst ")
				.append("FROM FLIGHTSEGMENT f, ROUTE r ")
				.append("WHERE f.ROUTEID = r.ROUTEID AND ")
				.append("FLIGHTNR = ? ").toString();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setInt(1, f.getFlightNr());

			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				resultSet.next();
				f.setFlightNr(resultSet.getInt(1));
				f.setArrivalTime(resultSet.getTime(2));
				f.setArrivalDate(resultSet.getDate(3));
				f.setDepartureTime(resultSet.getTime(4));
				f.setDepartureDate(resultSet.getDate(5));
				f.setRouteId(resultSet.getInt(6));
				f.setSourceCity(resultSet.getString(7));
				f.setDestinationCity(resultSet.getString(8));
				resultSet.close();
			} catch (SQLException e) {
				if (connection != null) {
					try {
						System.err
								.print("Flight segment search by flight number could not be performed");
						connection.close();
					} catch (SQLException excep) {
						System.err.print("SQL error occurs : "
								+ excep.getMessage());
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new FlightSegmentNotFoundException();
		}
		return f;
	}

	@SuppressWarnings("serial")
	public class FlightSegmentInsertException extends Throwable {
	}

	@SuppressWarnings("serial")
	public class FlightSegmentNotFoundException extends Throwable {
	}
}
