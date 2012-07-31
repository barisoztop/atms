package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.FlightSegmentBean;

public class FlightConsistsOfDAO extends AbstractDAO {
	public void associateSegmentToFlight(int flightID, int flightNr)
			throws FlightSegAssocInsertException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = new StringBuilder()
				.append("INSERT INTO FLIGHT_CONSISTS_OF(FLIGHTID, FLIGHTNR) ")
				.append("VALUES(?, ?)").toString();

		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, flightID);
			preparedStatement.setInt(2, flightNr);

			preparedStatement.executeUpdate();

			connection.commit();
			connection.close();

		} catch (SQLException e) {
			if (connection != null) {
				try {
					System.err
							.print("Associating segment to flight is being rolled back");
					connection.rollback();
					connection.close();
				} catch (SQLException excep) {
					System.err
							.print("SQL error occurs : " + excep.getMessage());
				}
			}

		}
	}

	public List<FlightSegmentBean> getListOfSegment(int flightID)
			throws FlightSegmentNotFoundException {

		String query = new StringBuilder().append("SELECT FLIGHTID, FLIGHTNR ")
				.append("FROM FLIGHT_CONSISTS_OF f ").append("WHERE ")
				.append("FLIGHTID = ? ").toString();

		List<FlightSegmentBean> flightSegs = new LinkedList<FlightSegmentBean>();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setInt(1, flightID);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					FlightSegmentBean flightSeg = new FlightSegmentBean();
					flightSeg.setFlightNr(resultSet.getInt(2));
					flightSegs.add(flightSeg);
				}
				resultSet.close();
			} catch (SQLException e) {
				if (connection != null) {
					try {
						System.err
								.print("Getting list of segment could not be performed");
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
		return flightSegs;
	}

	@SuppressWarnings("serial")
	public class FlightSegAssocInsertException extends Throwable {
	}

	@SuppressWarnings("serial")
	public class FlightSegmentNotFoundException extends Throwable {
	}
}
