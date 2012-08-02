<!DOCTYPE html>
<html>
<head>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="de.tum.in.dbpra.model.bean.FlightSegmentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="de.tum.in.dbpra.model.bean.FlightBean"%>
<%@page import="java.util.HashMap"%>
<jsp:useBean id="flight" scope="request"
	class="de.tum.in.dbpra.model.bean.FlightBean" />
<jsp:useBean id="flightSegment" scope="request"
	class="de.tum.in.dbpra.model.bean.FlightSegmentBean" />




<link type="text/css" rel="stylesheet" href="/css/main.css" />
<link type="text/css" rel="stylesheet"
	href="/css/ui-lightness/jquery-ui-1.8.22.custom.css" />
<script type="text/javascript" src="/jS/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/jS/jquery-ui-1.8.22.custom.min.js"></script>

<title>Display Routes</title>
</head>
<body>

	<div id="container">

		<jsp:include page="/public/TopContent.jsp"></jsp:include>

		<div id="content-container1">

			<jsp:include page="/public/LeftContent.jsp"></jsp:include>


			<div id="content">

				<div id="formdiv">

					<%
						HashMap flights = (HashMap) request.getAttribute("flightmap");

						if (flights.size() == 0) { // if no routes
					%>
					<p>
					<center>
						No Flights Available in the selected route for the date <a
							href="#" onclick="history.go(-1)"> Change Flight Selection</a>
					</center>

					<%
						} else {
							Set flightSet = flights.entrySet();
							Iterator itr = flightSet.iterator();
							// Display elements
					%><table>
						<thead>Flight
						</thead>
						<thead>Flight Segments
						</thead>
						<%
							while (itr.hasNext()) {

									Map.Entry mapE = (Map.Entry) itr.next();
									flight = (FlightBean) mapE.getKey();
						%>
						<tr>
							<td>=====================</td>
							<td>=================================================================================</td>
						</tr>
						<tr>
							<td class="flight_namecoloumn"><a
								href="/bookticket?flightid=<%=flight.getFlightID()%>">Book
									this Flight</a></td>
							<%
								List<FlightSegmentBean> flightSegs = (List) (mapE
												.getValue());

										for (int ii = 0; ii < flightSegs.size(); ii++) {
											if (ii > 0) {
							%>
						
						<tr>
							<td></td>
							<%
								}
											flightSegment = flightSegs.get(ii);
							%>
							<td class="flight_detailscoloumn">Flight Number <label><%=flightSegment.getFlightNr()%>
							</label> &nbsp;Depart from &nbsp;<label><%=flightSegment.getSourceCity()%></label>
								&nbsp;<label><%=flightSegment.getDepartureDate()%> </label>&nbsp;<label><%=flightSegment.getDepartureTime()%>
							</label> &nbsp;-->&nbsp;Arrive to &nbsp;<label><%=flightSegment.getDestinationCity()%></label>&nbsp;<label><%=flightSegment.getArrivalDate()%>
							</label>&nbsp;<label><%=flightSegment.getArrivalTime()%> </label></td>

							<%
								if (ii > 0) {
							%>
						</tr>
						<%
							}
									}
						%>

						<%
							}
						%>
					</table>
					<table>
						<thead>
						</thead>
						<tbody></tbody>

					</table>




					<div>
						<center>
							<a href="#" onclick="history.go(-1)"> Change Flight Selection</a>
						</center>
					</div>
					<%
						}
					%>


				</div>
			</div>



		</div>
		<jsp:include page="/public/BottomContent.jsp"></jsp:include>
	</div>
</body>
</html>