/**
 * 
 */
package de.tum.in.dbpra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tum.in.dbpra.model.bean.AirportBean;
import de.tum.in.dbpra.model.dao.AirportDAO;
import de.tum.in.dbpra.model.dao.FlightConsistsOfDAO;
import de.tum.in.dbpra.model.dao.FlightDAO;
import de.tum.in.dbpra.model.dao.FlightSegmentDAO;
import de.tum.in.dbpra.model.dao.RouteDAO;
import de.tum.in.dbpra.model.dao.FlightSegmentDAO.SegmentPair;
import de.tum.in.dbpra.model.dao.RouteDAO.RouteNotFoundException;
import de.tum.in.dbpra.model.bean.RoutePair;

@SuppressWarnings("serial")
public class FlightServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			AirportDAO airportDAO = new AirportDAO();

			List<AirportBean> airportList = new ArrayList<AirportBean>();

			airportList = airportDAO.getAirportList();
			request.setAttribute("airportList", airportList);

		}

		catch (Throwable e) {
			request.setAttribute("error", true);
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/jsp/flightcreation.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int flightId = 0;
		int flightSegmentId = 0;
		int routeId = 0;

		String departureDate = "";
		String arrivalDate = "";
		String departureTime = "";
		String arrivalTime = "";
		String departureAirport = "";
		String arrivalAirport = "";
		StringBuilder successMessage = new StringBuilder();
		StringBuilder failureMessage = new StringBuilder();

		List<AirportBean> airportList = new ArrayList<AirportBean>();
		List<RoutePair> routePairs = null;

		try {

			RouteDAO rootDao = new RouteDAO();
			FlightDAO flightDAO = new FlightDAO();
			AirportDAO airportDAO = new AirportDAO();
			FlightSegmentDAO flightsegDAO = new FlightSegmentDAO();
			FlightConsistsOfDAO flightConsistsOf = new FlightConsistsOfDAO();

			if (request.getParameter("departureDate") != null) {
				departureDate = request.getParameter("departureDate");
			}
			if (request.getParameter("arrivalDate") != null) {
				arrivalDate = request.getParameter("arrivalDate");
			}
			if (request.getParameter("departureTime") != null) {
				departureTime = request.getParameter("departureTime");
			}
			if (request.getParameter("arrivalTime") != null) {
				arrivalTime = request.getParameter("arrivalTime");
			}
			if (request.getParameter("departureAirport") != null) {
				departureAirport = request.getParameter("departureAirport")
						.toString();
			}
			if (request.getParameter("arrivalAirport") != null) {
				arrivalAirport = request.getParameter("arrivalAirport")
						.toString();
			}

			try {
				routeId = rootDao.getRouteID(departureAirport, arrivalAirport);
				if (routeId > 0) {
					if (request.getParameter("route") != null) {
						if (Integer.parseInt(request.getParameter("route")) == routeId) {

							flightId = flightDAO.createNewFlight(arrivalDate,
									departureDate, arrivalTime, departureTime);

							flightSegmentId = flightsegDAO
									.createNewFlightSegment(arrivalDate,
											departureDate, arrivalTime,
											departureTime, routeId);

							flightConsistsOf.associateSegmentToFlight(flightId,
									flightSegmentId);
							successMessage.append("\n").append(
									"Flight created Successfully.");

						}

					}
				}

				else {
					failureMessage.append("\n").append(
							"No possible direct route available.");
				}

				routePairs = rootDao.getRoutePairs(departureAirport,
						arrivalAirport);

				List<SegmentPair> segmentPairs = null;

				if (routePairs != null && !routePairs.isEmpty()) {
					if (request.getParameter("routePair") != null) {
						String s = (String) request.getParameter("routePair");
						int index = s.indexOf("-");
						int routeId1 = Integer.parseInt(s.substring(0, index));
						int routeId2 = Integer.parseInt(s.substring(index + 1,
								s.length()));

						for (RoutePair rPair : routePairs) {
							if (routeId1 == rPair.getFirstRouteID()
									&& routeId2 == rPair.getSecondRouteID()) {
								List<RoutePair> r = new ArrayList<RoutePair>();
								r.add(rPair);
								segmentPairs = flightsegDAO
										.findSegsForPotentialFlight(r);
							}
						}

						if (segmentPairs != null && !segmentPairs.isEmpty()) {
							for (SegmentPair sPair : segmentPairs) {

								flightId = flightDAO.createNewFlight(
										arrivalDate, departureDate,
										arrivalTime, departureTime);
								if (flightId > 0) {
									flightConsistsOf.associateSegmentToFlight(
											flightId, sPair.getFirstSegment()
													.getFlightNr());
									flightConsistsOf.associateSegmentToFlight(
											flightId, sPair
													.getSecondSegmentNr()
													.getFlightNr());

									successMessage
											.append("\n")
											.append("Flight created successfully with associated segments.");
								}
							}

						} else {
							failureMessage
									.append("\n")
									.append("Route exists but no segment flight has been created for the available routes.");

						}
					}
				} else {
					failureMessage.append("\n").append(
							"No possible route pair available.");

				}

			} catch (RouteNotFoundException e) {
				routeId = -1;
			}

			airportList = airportDAO.getAirportList();

			request.setAttribute("airportList", airportList);
			request.setAttribute("routePair", routePairs);
			request.setAttribute("srcAirport", departureAirport);
			request.setAttribute("dstAirport", arrivalAirport);
			request.setAttribute("routeId", routeId);
			request.setAttribute("successMessage", successMessage);
			request.setAttribute("failureMessage", failureMessage);

			System.out
					.println("Success message = " + successMessage.toString());
			System.out
					.println("Failure message = " + failureMessage.toString());

		} catch (Throwable e) {
			successMessage = failureMessage = new StringBuilder(
					"Problem occuring during flight creation.");
			request.setAttribute("error", true);
		}
		RequestDispatcher dispatcher;
		if (!successMessage.toString().equals("")) {
			dispatcher = request
					.getRequestDispatcher("/jsp/flightResult.jsp?successMessage='"
							+ successMessage + "'");

		} else {
			dispatcher = request
					.getRequestDispatcher("/jsp/flightcreation.jsp?departureDate="
							+ "'"
							+ departureDate
							+ "'"
							+ "&arrivalDate="
							+ "'"
							+ arrivalDate
							+ "'"
							+ "&departureAirport="
							+ "'"
							+ departureAirport
							+ "'"
							+ "&arrivalAirport="
							+ "'"
							+ arrivalAirport
							+ "'"
							+ "&arrivalTime="
							+ "'"
							+ arrivalTime
							+ "'"
							+ "&departureTime="
							+ "'"
							+ departureTime
							+ "'"
							+ "&failureMessage="
							+ "'"
							+ failureMessage + "'");

		}
		dispatcher.forward(request, response);

	}

}
