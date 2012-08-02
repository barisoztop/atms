package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.RoutePair;

public class RouteDAO extends AbstractDAO {

	public int getRouteID(String apcodeSource, String apcodeDestination)
			throws RouteNotFoundException {
		String query = new StringBuilder().append("SELECT ROUTEID ")
				.append("FROM ROUTE ")
				.append("WHERE APCODE_SRC = ? AND APCODE_DST = ?").toString();

		int id = -1;

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setString(1, apcodeSource);
			preparedStatement.setString(2, apcodeDestination);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					id = resultSet.getInt(1);
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RouteNotFoundException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RouteNotFoundException();
		}

		return id;
	}

	/**
	 * finds pair(s) of routes which can be used(combined) to get from source
	 * airport to destination airport
	 * 
	 * @param apcodeSource
	 *            airport code of the source
	 * @param apcodeDestination
	 *            airport code of the destination
	 * @return List of route pairs
	 * @throws RouteNotFoundException
	 */
	public List<RoutePair> getRoutePairs(String apcodeSource,
			String apcodeDestination) throws RouteNotFoundException {
		String query = new StringBuilder()
				.append("SELECT r1.ROUTEID, r1.APCODE_SRC, r1.APCODE_DST, r2.ROUTEID, r2.APCODE_SRC, r2.APCODE_DST  ")
				.append("FROM ROUTE r1, ROUTE r2 ")
				.append("WHERE r1.APCODE_SRC = ? AND r2.APCODE_DST = ? AND r1.APCODE_DST = r2.APCODE_SRC")
				.toString();

		List<RoutePair> routePairs = new LinkedList<RoutePair>();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setString(1, apcodeSource);
			preparedStatement.setString(2, apcodeDestination);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					RoutePair routePair = new RoutePair();
					routePair.setFirstRouteID(resultSet.getInt(1));
					routePair.setFirstSourceAirportCode(resultSet.getString(2));
					routePair.setFirstDestinationAirportCode(resultSet
							.getString(3));
					routePair.setSecondRouteID(resultSet.getInt(4));
					routePair
							.setSecondSourceAirportCode(resultSet.getString(5));
					routePair.setSecondDestinationAirportCode(resultSet
							.getString(6));

					routePairs.add(routePair);
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RouteNotFoundException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RouteNotFoundException();
		}
		return routePairs;
	}

	@SuppressWarnings("serial")
	public static class RouteNotFoundException extends Throwable {
	}

}
