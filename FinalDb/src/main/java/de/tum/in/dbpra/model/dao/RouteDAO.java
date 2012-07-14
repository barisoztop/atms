package main.java.de.tum.in.dbpra.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.java.de.tum.in.dbpra.model.bean.*;
import main.java.de.tum.in.dbpra.model.dao.*;

public class RouteDAO extends AbstractDAO {
	
	public int getRouteID(String apcodeSource, String apcodeDestination) {
		
		
		return 0;
		
	}
	
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
	
	
	public List<RoutePair> getRoutePairs(String apcodeSource, String apcodeDestination) {
		return null;
		
	}
	
	public class RoutePair {
		
		private int firstRouteID;
		private int secondRouteID;
		/**
		 * @return the firstRouteID
		 */
		public int getFirstRouteID() {
			return firstRouteID;
		}
		/**
		 * @param firstRouteID the firstRouteID to set
		 */
		public void setFirstRouteID(int firstRouteID) {
			this.firstRouteID = firstRouteID;
		}
		/**
		 * @return the secondRouteID
		 */
		public int getSecondRouteID() {
			return secondRouteID;
		}
		/**
		 * @param secondRouteID the secondRouteID to set
		 */
		public void setSecondRouteID(int secondRouteID) {
			this.secondRouteID = secondRouteID;
		}

	}
	
	public static class RouteNotFoundException extends Throwable {
	}

}
