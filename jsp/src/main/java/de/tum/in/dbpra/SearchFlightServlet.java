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
			AirportDAO apDAO = new AirportDAO();
			ArrayList apList = apDAO.getAirportList();
			request.setAttribute("airportlist", apList);

		} catch (Throwable e) {
			request.setAttribute("error", true);

		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/jsp/bookTicket.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String sourceCity = request.getParameter("sourcecity");
			String destinationCity = request.getParameter("destinationcity");
			Date departureDate = Date.valueOf(request
					.getParameter("departuredate"));
			FlightBean flightBean = new FlightBean();
			flightBean.setSourceCity(sourceCity);
			flightBean.setDestinationCity(destinationCity);
			flightBean.setDepartureDate(departureDate);
			FlightDAO flightDAO = new FlightDAO();
			List<FlightBean> flightList = flightDAO.searchFlight(flightBean);

			FlightConsistsOfDAO fcoDAO = new FlightConsistsOfDAO();
			FlightSegmentDAO fsDAO = new FlightSegmentDAO();
			HashMap<FlightBean, List<FlightSegmentBean>> flightMap = new HashMap<FlightBean, List<FlightSegmentBean>>();

			for (FlightBean flight : flightList) {
				List<FlightSegmentBean> segmentList = fcoDAO
						.getListOfSegment(flight.getFlightID());
				for (FlightSegmentBean segment : segmentList) {
					fsDAO.searchSegmentByFlightNR(segment);
				}
				flightMap.put(flight, segmentList);

			}

			request.setAttribute("flightmap", flightMap);

		} catch (Throwable e) {

			e.printStackTrace();
			request.setAttribute("error", true);
			System.out.println("Error---------------------+"
					+ e.getLocalizedMessage() + "+-----------");

		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/jsp/DisplayRoutes.jsp");
		dispatcher.forward(request, response);
	}
}
