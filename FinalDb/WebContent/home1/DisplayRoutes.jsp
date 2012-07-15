<%@page import="main.java.de.tum.in.dbpra.model.bean.FlightSegmentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="main.java.de.tum.in.dbpra.model.bean.FlightBean"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="home1/css/main.css" />
<script type="text/javascript"  src="home1/jS/jquery-1.6.4.min.js"></script>
<link    href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" ></script>

<jsp:useBean id="flight" scope="request"
	class="main.java.de.tum.in.dbpra.model.bean.FlightBean" />
<jsp:useBean id="flightSegment" scope="request"
	class="main.java.de.tum.in.dbpra.model.bean.FlightSegmentBean" />

<html>
<head>
<title>Display Routes</title>
</head>
<body>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<div id="container">
	<jsp:include page="public/TopContent.jsp"></jsp:include>

	<div id="content-container1">
		<jsp:include page="public/LeftContent.jsp"></jsp:include>


			<div id="content">
				<h2>Book New Flight</h2>
				<div id="formdiv">

<% if(1>1){ // if no routes%>
<p>
   
   No Flights Available in the selected route for the data= $date;
   Please Select An Alternate Route
   <a href="Javacript:history.back();" >Select Alternate Route</a>
</p>
<%}
 else
 {
	 HashMap flights=(HashMap)request.getAttribute("flightmap");
	 if(flights!=null){
	
		Set flightSet=flights.entrySet();
		Iterator itr = flightSet.iterator(); 
		// Display elements 
		%><table><th>Flight</th><th>FLight Segments</th><%
		while(itr.hasNext()) { 
	   
		Map.Entry mapE = (Map.Entry)itr.next(); 
		 flight =(FlightBean)mapE.getKey();
		%>
		<tr><td>=====================</br></td><td>=================================================================================</br></td></tr>
		<tr><td><a href="/FinalDb/BookTicketServlet?flightid=<%=flight.getFlightID()%>" >Book this Flight</a> </label></td>
		<%  List <FlightSegmentBean> flightSegs=(List)(mapE.getValue());  
		
		for(int ii=0;ii<flightSegs.size();ii++)
		{
			if(ii>0){
				%>
				<tr><td></td>
				<%
			}
			flightSegment=flightSegs.get(ii);
		%><td>flightNR: <label><%=flightSegment.getFlightNr() %>  </label>&nbsp;departure: <label><%=flightSegment.getDepartureDate() %>   </label>&nbsp;<label><%=flightSegment.getDepartureTime() %>   </label>
		&nbsp;-->&nbsp;arrival: <label><%=flightSegment.getArrivalDate() %>   </label>&nbsp;<label><%=flightSegment.getArrivalTime() %>   </label></td>	
		
		<%
		 	if(ii>0){
				%>
				</tr>
				<%
			}
		}
		%>
		</tr>
		<%
		}
		%></table>
		 <% 
	 }
	 
	 else
	 {
		  %>
		  <label>hello eroor </label>
		  <%
	 }
 // Routes are Available . Prompt the user to select one route
 // We should information like , flight number, price of the journey, complete details of the flight(time,airport)both dest and arrival
 // this will be populated in a table , and user can select anyone to check further details.
%>

<table>
<thead>
<th>

</th>
</thead>
<tbody></tbody>

</table>
 
 
	<%
	
 }
 
 %>
 
<div><center>
<a href="Javacript:history.back();">
 Change Flight Selection</a>
</center>
</div>
</body>
</html>