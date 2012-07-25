package de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.tum.in.dbpra.model.bean.RoutePairBean;
import de.tum.in.dbpra.model.dao.FlightSegmentDAO.FlightSegmentNotFoundException;

public class RouteDAO extends AbstractDAO {
	
	public int getRouteID(String apcodeSource, String apcodeDestination) throws RouteNotFoundException {
		String query = new StringBuilder()
		.append("SELECT ROUTEID ")
		.append("FROM ROUTE ")
		.append("WHERE APCODE_SRC = ? AND APCODE_DST = ?")
		.toString();
		
		int id = -1;
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
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
	
	@Deprecated
	public ArrayList getRoutes (String apcodeSource, String apcodeDestination) throws RouteNotFoundException{
		Connection connection = null;
		Statement stmtforRoutePair = null;
		Statement stmtforDirectRoute = null;
		RoutePairBean routePairs;
		//AirportBean airport;
		ArrayList routeList = new ArrayList();
		String queryforRoutePair = new StringBuilder()
	        .append("select r1.routeid, r1.APCODE_SRC,r1.APCODE_DST,r2.routeid,r2.APCODE_SRC,r2.APCODE_DST from route r1, route r2 ")
			.append(" where r1.apcode_src like "+"'%"+apcodeSource+"%'"+" and  r2.apcode_dst like "+"'%"+apcodeDestination+"%'"+" and ")
			.append(" r1.apcode_dst = r2.apcode_src")
			.toString();
		
		String queryforDirectRoute = new StringBuilder()
		.append("select r1.routeid,r1.APCODE_SRC,r1.APCODE_DST from route r1 ")
		.append(" where r1.apcode_src like "+"'%"+apcodeSource+"%'"+"and  r1.apcode_dst like "+"'%"+apcodeDestination+"%'")
		.toString();
		
		//System.out.println("Query for route list = "+ queryforDirectRoute);

		try{
			connection = getConnection();
			connection.setAutoCommit(false);
			stmtforRoutePair = connection.createStatement();
			stmtforDirectRoute = connection.createStatement();
			ResultSet resultSet = stmtforRoutePair.executeQuery(queryforRoutePair);
			ResultSet resultSet1 = stmtforDirectRoute.executeQuery(queryforDirectRoute);
          
            while (resultSet.next()) {
		         routePairs = new  RoutePairBean();
		         routePairs.setFirstRouteID(Integer.parseInt(resultSet.getString(1)));
		         routePairs.setFirstSourceAirport(resultSet.getString(2));
		         routePairs.setFirstAirportDestination(resultSet.getString(3));
		         routePairs.setSecondRouteID(Integer.parseInt(resultSet.getString(4)));
		         routePairs.setSecondSourceAirport(resultSet.getString(5));
		         routePairs.setSecondAirportDestination(resultSet.getString(6));
				 routeList.add(routePairs);
			}
            while (resultSet1.next()) {
		         routePairs = new  RoutePairBean();
		       //  System.out.println("first route here2 = "+resultSet1.getString(1));
		         routePairs.setFirstRouteID(Integer.parseInt(resultSet1.getString(1)));
		         routePairs.setFirstSourceAirport(resultSet1.getString(2));
		         routePairs.setFirstAirportDestination(resultSet1.getString(3));
				 routeList.add(routePairs);
			}

				resultSet.close();
				resultSet1.close();
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
			if(stmtforRoutePair != null || stmtforDirectRoute != null){
				try {
					stmtforRoutePair.close();
					stmtforDirectRoute.close();
				} catch (SQLException e) {
					System.err.print("SQL error occurs : "+e.getMessage());
					e.printStackTrace();
				}

			}
		}


		return routeList;
		
	}
	
	/**
	 * finds pair(s) of routes which can be used(combined) to get from source airport to destination airport
	 * 
	 * @param apcodeSource airport code of the source
	 * @param apcodeDestination airport code of the destination
	 * @return List of route pairs
	 * @throws RouteNotFoundException 
	 */
	public List<RoutePair> getRoutePairs(String apcodeSource, String apcodeDestination) throws RouteNotFoundException {
		String query = new StringBuilder()
		.append("SELECT r1.ROUTEID, r2.ROUTEID ")
		.append("FROM ROUTE r1, ROUTE r2 ")
		.append("WHERE r1.APCODE_SRC = ? AND r2.APCODE_DST = ? AND r1.APCODE_DST = r2.APCODE_SRC")
		.toString();
		
		List<RoutePair> routePairs = new LinkedList<RoutePair>();
		
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			
			preparedStatement.setString(1, apcodeSource);
			preparedStatement.setString(2, apcodeDestination);
			
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					RoutePair routePair = new RoutePair();
					routePair.setFirstRouteID(resultSet.getInt(1));
					routePair.setSecondRouteID(resultSet.getInt(2));
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
	
	public class RoutePair {
		
		private int firstRouteID;
		private int secondRouteID;
		public int getFirstRouteID() {
			return firstRouteID;
		}
		public void setFirstRouteID(int firstRouteID) {
			this.firstRouteID = firstRouteID;
		}
		public int getSecondRouteID() {
			return secondRouteID;
		}
		public void setSecondRouteID(int secondRouteID) {
			this.secondRouteID = secondRouteID;
		}

	}
	
	public static class RouteNotFoundException extends Throwable {
	}

}
