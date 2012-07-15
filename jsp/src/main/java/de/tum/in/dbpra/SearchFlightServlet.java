package de.tum.in.dbpra;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tum.in.dbpra.model.bean.*;
import de.tum.in.dbpra.model.dao.*;



@SuppressWarnings("serial")
public class SearchFlightServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//AirportDAO to get all airport.
    		AirportDAO apDAO = new AirportDAO();
        	ArrayList apList = apDAO.getAirportList();
        	request.setAttribute("airportlist", apList);
        	
    	} catch (Throwable e) {
    		request.setAttribute("error", true);

    	}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/bookTicket.jsp");
		dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	try {
    		//get sourceCity and destinationCity attr. from jsp
			String sourceCity = request.getParameter("sourcecity");
			String destinationCity = request.getParameter("destinationcity");
    		Date departureDate = Date.valueOf(request.getParameter("departuredate"));
    		//Date arrivalDate = Date.valueOf(request.getParameter("arrivaldate"));
			
    		//create flightBean
    		FlightBean flightBean = new FlightBean();
    		flightBean.setSourceCity(sourceCity);
    		flightBean.setDestinationCity(destinationCity);
    		flightBean.setDepartureDate(departureDate);
    		//flightBean.setArrivalDate(arrivalDate);
    		//FlightDao - searchFlight
    		FlightDAO flightDAO = new FlightDAO();
    		List<FlightBean> flightList = flightDAO.searchFlight(flightBean);
    		
    		//create FlightConsistOfDAO
    		FlightConsistsOfDAO fcoDAO = new FlightConsistsOfDAO();
    		//create FlightSegmentDAO
    		FlightSegmentDAO fsDAO = new FlightSegmentDAO();
    		//create flightMap to store (FlightBean,List<FlightSegment>)
    		HashMap<FlightBean,List<FlightSegmentBean>> flightMap = new HashMap<FlightBean,List<FlightSegmentBean>>();
   
    		//put flightSegment related to each flight to flightMap
    		
    		for(FlightBean flight : flightList){
    			//get the list of related flightSegment (only flightNr)
    			List<FlightSegmentBean> segmentList = fcoDAO.getListOfSegment(flight.getFlightID());
    			//this loop is to fill up the rest information of each segment
    			for(FlightSegmentBean segment : segmentList){
    				fsDAO.searchSegmentByFlightNR(segment);
    			}
    			flightMap.put(flight, segmentList);
    			
    		}
    		
    		request.setAttribute("flightmap", flightMap);
    	
    	
        	
    	} catch (Throwable e) {
    		
    		e.printStackTrace();
    		request.setAttribute("error", true);
    		System.out.println("Error---------------------+"+e.getLocalizedMessage()+"+-----------");

    	}
    	
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/DisplayRoutes.jsp");
		dispatcher.forward(request, response);
    }
}
