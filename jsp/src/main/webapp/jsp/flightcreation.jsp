<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="de.tum.in.dbpra.model.bean.AirportBean"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="de.tum.in.dbpra.model.bean.RoutePairBean"%>
<jsp:useBean id="airport" scope="request"
	class="de.tum.in.dbpra.model.bean.AirportBean" />

<!DOCTYPE html>

<html lang="en">
<head>



<link rel="stylesheet" type="text/css" href="/css/datepicker.css" />
<script type="text/javascript"  src="/jS/jquery-1.6.4.min.js"></script>
<link    href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" ></script>
<script type="text/javascript" src="/jS/datepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/css/main.css" />


<script>
       function retainPreviousData(){
    	   
    	   <%if(request.getParameter("departureDate")!= null){ %>
    	      document.forms[0].departureDate.value= <%=request.getParameter("departureDate")%>;
    	   <%}%>
    	   <%if(request.getParameter("arrivalDate")!= null){ %>
    	     document.forms[0].arrivalDate.value= <%=request.getParameter("arrivalDate")%>;
    	   
    	   <%}%>
    	   <%if(request.getParameter("departureAirport")!= null){ %>
  	         document.forms[0].departureAirport.value= <%=request.getParameter("departureAirport")%>;
  	   
  	        <%}%>
  	      <%if(request.getParameter("arrivalTime")!= null){ %>
	         document.forms[0].arrivalTime.value= <%=request.getParameter("arrivalTime")%>;
	   
	        <%}%>
	        <%if(request.getParameter("arrivalAirport")!= null){ %>
	         document.forms[0].arrivalAirport.value= <%=request.getParameter("arrivalAirport")%>;
	   
	        <%}%>
	        <%if(request.getParameter("departureTime")!= null){ %>
	         document.forms[0].departureTime.value= <%=request.getParameter("departureTime")%>;
	   
	        <%}%>
        }
       
       function validate(){
    	   if(document.forms[0].departureAirport.value == 0){
    		   alert("Select the Departure Airport");
    		   return false;
    	   }
    	   if(document.forms[0].arrivalAirport.value == 0){
    		   alert("Select the Arrival Airport");
    		   return false;
    	   }
    	   if(document.forms[0].departureTime.value == ""){
    		   alert("Enter the departure time");
    		   return false;
    	   }
    	   if(document.forms[0].arrivalTime.value == ""){
    		   alert("Enter the arrival time");
    		   return false;
    	   }
    	   if(document.forms[0].departureDate.value == ""){
    		   alert("Enter the departure date");
    		   return false;
    	   }
    	   if(document.forms[0].arrivalDate.value == ""){
    		   alert("Enter the arrival date");
    		   return false;
    	   }
   	  
       }
       
    </script>
<meta charset="utf-8">
<title>Flight creation</title>
</head>
<%
       ArrayList airportList = new ArrayList();
       if(request.getAttribute("airportList")!= null){
    	   airportList=(ArrayList)request.getAttribute("airportList");
   	   }   
    %>
<body onload='retainPreviousData();'>
<div id="container">

	<jsp:include page="/public/TopContent.jsp"></jsp:include>


	<div id="content-container1">

		<jsp:include page="/public/LeftContent.jsp"></jsp:include>


	<div id="content">
				<h2>Flight Creation</h2>
				
				<div id="formdiv" >
				<form action="/flight" method="post">
					<table>

						<% if (request.getParameter("successMessage")!= null){ %>
						<strong style="color: red;"><%=request.getParameter("successMessage")%></strong>
						<%} %>
						
							<INPUT TYPE="HIDDEN" NAME="selectedDate" ID="selectedDate">
							<tr>
								<td>From : <select name="departureAirport"
									id="departureAirport" onChange="">
										<option value=0>Select Airport</option>
										<% for(int a = 0; a < airportList.size() ; a++){%>

										<%    airport = (AirportBean)airportList.get(a);%>
										<option value=<%=airport.getApcode() %>><%=airport.getApname()+"-"+airport.getApcode() %></option>
										<% } %>

								</select>
								</td>
								<td>Departure Date : <input type="text"
									name="departureDate" id="ddate"><input type=button
									value="Select Date"
									onclick="displayDatePicker('departureDate', this);">
								</td>
								<td>Departure Time : <input type="text"
									name="departureTime" id="dtime"> hh:mm:ss
								</td>
							</tr>

							<tr>
								<td>To : &nbsp;&nbsp;&nbsp; <select name="arrivalAirport"
									id="arrivalAirport" onChange="">
										<option value=0>Select Airport</option>
										<% for(int a = 0; a < airportList.size() ; a++){%>

										<%    airport = (AirportBean)airportList.get(a);%>
										<option value=<%=airport.getApcode() %>><%=airport.getApname()+"-"+airport.getApcode() %></option>
										<% } %>

								</select>
								</td>
								<td>Arrival Date : &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="text" name="arrivalDate" id="adate"><input
									type=button value="Select Date"
									onclick="displayDatePicker('arrivalDate', this);">
								</td>
								<td>Arrival Time : &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="text" name="arrivalTime" id="atime"> hh:mm:ss
								</td>
							</tr>
					</table>
					<br> <br>

					<table  border="1" >

						<%
						if(request.getAttribute("routeId") != null){
                	    	int routeId = (Integer)request.getAttribute("routeId");
        	            	if(routeId < 0){%>
							<b><i>Route is not available....</i></b>
							<%} else {%>
							<b> Flight Segments</b>
						
							<tr>
								<td width=2%><input TYPE=checkbox name=routes
									VALUE="<%=routeId%>"></td>
								<td width=10%><%=request.getAttribute("srcAirport")%></td>
								<td width=10%><%=request.getAttribute("dstAirport") %></td>
							</tr>
                        	<%}%>
                        <%}%>

					</table>

					<br> <input type="submit" name="createFlight"
						value="Create Flight" onClick="validate();" />

					</form>


				</div>
			</div>


	</div>

	<jsp:include page="/public/BottomContent.jsp"></jsp:include>

</div>
</body>
</html>