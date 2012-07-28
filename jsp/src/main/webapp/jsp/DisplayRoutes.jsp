<%@page import="de.tum.in.dbpra.model.bean.FlightSegmentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="de.tum.in.dbpra.model.bean.FlightBean"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="/css/main.css" />
<script type="text/javascript"  src="/jS/jquery-1.6.4.min.js"></script>
<link    href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" ></script>

<jsp:useBean id="flight" scope="request"
	class="de.tum.in.dbpra.model.bean.FlightBean" />
<jsp:useBean id="flightSegment" scope="request"
	class="de.tum.in.dbpra.model.bean.FlightSegmentBean" />

<html>
<head>
<title>Display Routes</title>
</head>
<body>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<div id="container">

	<jsp:include page="/public/TopContent.jsp"></jsp:include>

	<div id="content-container1">

		<jsp:include page="/public/LeftContent.jsp"></jsp:include>


			<div id="content">
				<h2></h2>
				<div id="formdiv">

					<%
					HashMap flights = (HashMap) request.getAttribute("flightmap");

					if (flights.size()==0)
					{ // if no routes
					%>
					<p>
                        <center>
						No Flights Available in the selected route for the date
                      <br>
						<a href="#" onclick="history.go(-1)"> Change Flight Selection</a>
						</center>
					</p>
					<%
						}
					else
					{
					   Set flightSet = flights.entrySet();
					   Iterator itr = flightSet.iterator();
								// Display elements
					%><table>
						<th>Flight</th>
						<th>Flight Segments</th>
						<%
							while (itr.hasNext()) {

										Map.Entry mapE = (Map.Entry) itr.next();
										flight = (FlightBean) mapE.getKey();
						%>
						<tr>
							<td>=====================</br></td>
							<td>=================================================================================</br></td>
						</tr>
						<tr>
							<td class="flight_namecoloumn"><a href="/bookticket?flightid=<%=flight.getFlightID()%>" >Book this Flight</a> </label></td>
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
							</label>&nbsp;Departure <label><%=flightSegment.getDepartureDate()%>
							</label>&nbsp;<label><%=flightSegment.getDepartureTime()%> </label>
								&nbsp;-->&nbsp;Arrival <label><%=flightSegment.getArrivalDate()%>
							</label>&nbsp;<label><%=flightSegment.getArrivalTime()%> </label></td>

							<%
								if (ii > 0) {
							%>
						</tr>
						<%
							}
										}
						%>
						</tr>
						<%
							}
						%>
					</table>
					<table>
					<thead>
					<th></th>
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
</html>