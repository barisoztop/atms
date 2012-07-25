/**
 * 
 */
package de.tum.in.dbpra;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import de.tum.in.dbpra.model.bean.AirportBean;
import de.tum.in.dbpra.model.dao.AirportDAO;
import de.tum.in.dbpra.model.dao.RouteDAO;
import de.tum.in.dbpra.model.dao.FlightDAO;
import de.tum.in.dbpra.model.dao.FlightSegmentDAO;
import de.tum.in.dbpra.model.bean.RoutePairBean;
import de.tum.in.dbpra.model.dao.FlightConsistsOfDAO;

import java.io.IOException;

/**
 * @author hafsa
 *
 */
@SuppressWarnings("serial")
public class FlightServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try{
              
			  AirportBean airportbean = null;
			  AirportDAO airportDAO = new AirportDAO();
		      
	          ArrayList<AirportBean> airportList = new ArrayList<AirportBean>() ;
 	        
	          airportList = airportDAO.getAirportList();
 	          request.setAttribute("airport", airportbean);
       	      request.setAttribute("airportList", airportList);
   	
		    } 
		
		catch (Throwable e) {
	    		request.setAttribute("error", true);
	    }

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/flightcreation.jsp");
		dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	int index = 0;
    	int routeId1 = 0;
    	int routeId2 = 0;
    	int flightId = 0;
    	int flightSegmentId = 0;
    	
    	String departureDate = "";
    	String arrivalDate = "";
    	String departureTime = "";
    	String arrivalTime = "";
    	String departureAirport = "";
    	String arrivalAirport = "";
    	String successMessage = "";
    	
    	ArrayList routeList = new ArrayList();
		ArrayList airportList = new ArrayList() ;
    	
    	try {
    		
    		AirportBean airportbean = null;
    		RouteDAO  rootDao = new RouteDAO();
    		FlightDAO flightDAO = new FlightDAO();
    		AirportDAO airportDAO = new AirportDAO();
    		FlightSegmentDAO flightsegDAO = new FlightSegmentDAO();
    		RoutePairBean routePair = new RoutePairBean();
    		FlightConsistsOfDAO flightConsistsOf = new FlightConsistsOfDAO();
    		
    		if(request.getParameter("departureDate")!= null){
    		   departureDate = request.getParameter("departureDate");
    		}
    		if(request.getParameter("arrivalDate")!= null){
    		   arrivalDate = request.getParameter("arrivalDate");
    		}
    		if(request.getParameter("departureTime") != null){
    		   departureTime = request.getParameter("departureTime");
    		}
    		if(request.getParameter("arrivalTime")!= null){
    		   arrivalTime = request.getParameter("arrivalTime");
    		}
    		if(request.getParameter("departureAirport")!=null){
    		   departureAirport = request.getParameter("departureAirport").toString();
    		}
    		if(request.getParameter("arrivalAirport")!= null){
    		   arrivalAirport = request.getParameter("arrivalAirport").toString();
    		}
    	    
    	
    		
    	    if(request.getParameterValues("routes") != null){
    		String[] selectedRouteIds = request.getParameterValues("routes");
    		
    		
    		for(String s: selectedRouteIds){
    			
    		    index = s.indexOf("-");
    			routeId1 = Integer.parseInt(s.substring(0, index));
    			routeId2 = Integer.parseInt(s.substring(index, s.length())) ;
    	              
    	               if(routeId1 != 0){
    	            	   flightId = flightDAO.createNewFlight(arrivalDate, departureDate, arrivalTime, departureTime);
    	            	   flightSegmentId = flightsegDAO.createNewFlightSegment(arrivalDate, departureDate, arrivalTime, departureTime, routeId1);
    	            	   flightConsistsOf.associateSegmentToFlight(flightId, flightSegmentId);
    	               } 
    	               if(routeId2 != 0){
    	            	   flightSegmentId =  flightsegDAO.createNewFlightSegment(arrivalDate, departureDate, arrivalTime, departureTime, routeId2);
    	           	       flightConsistsOf.associateSegmentToFlight(flightId, flightSegmentId);
    	               }
    	            successMessage = "Flight created Successfully";
    	    		
    		    }
    	    }
    		
    		routeList = rootDao.getRoutes(departureAirport, arrivalAirport);
    	    airportList = airportDAO.getAirportList();
	        
    	    request.setAttribute("airport", airportbean);
     	    request.setAttribute("airportList", airportList);
     	    request.setAttribute("routePair", routePair);
         	request.setAttribute("routeList", routeList);
         	
         	System.out.println("Success message = "+successMessage);
    		
    	} catch (Throwable e) {
    		successMessage = "Problem occuring during flight creation";
    		request.setAttribute("error", true);
    	}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/flightcreation.jsp?departureDate="+"'"+departureDate+"'"+"&arrivalDate="+"'"+arrivalDate+"'"+"&departureAirport="+"'"+departureAirport+"'"+"&arrivalAirport="+"'"+arrivalAirport+"'"+"&arrivalTime="+"'"+arrivalTime+"'"+"&departureTime="+"'"+departureTime+"'"+"&successMessage="+"'"+successMessage+"'");
		dispatcher.forward(request, response);
		
    }

}
