<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="de.tum.in.dbpra.model.bean.AirportBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@page import="de.tum.in.dbpra.model.bean.RoutePairBean"%>

<html>
<head>

<link type="text/css" rel="stylesheet" href="/css/main.css" />
<link type="text/css" rel="stylesheet" href="/css/ui-lightness/jquery-ui-1.8.22.custom.css" />
<script type="text/javascript" src="/jS/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/jS/jquery-ui-1.8.22.custom.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
</script>
<script type="text/javascript">
			$(document).ready(function() {
				    $('form input').data('val',  $('form input').val() ); // save value
				    $('form input').change(function() { // works when input will be blured and the value was changed
				        // console.log('input value changed');
				        $('.log').append(' change');
				    });
				    $('form input').keyup(function() { // works immediately when user press button inside of the input
				        if( $('form input').val() != $('form input').data('val') ){ // check if value changed
				            $('form input').data('val',  $('form input').val() ); // save new value
				            $(this).change(); // simulate "change" event
				        }
				    });
				});
</script>
<jsp:useBean id="airport" scope="request"
	class="de.tum.in.dbpra.model.bean.AirportBean" />

<script>
       function retainPreviousData(){
    	   
    	   <%if (request.getParameter("departureDate") != null) {%>
    	      document.forms[0].departureDate.value= <%=request.getParameter("departureDate")%>;
    	   <%}%>
    	   <%if (request.getParameter("arrivalDate") != null) {%>
    	     document.forms[0].arrivalDate.value= <%=request.getParameter("arrivalDate")%>;
    	   
    	   <%}%>
    	   <%if (request.getParameter("departureAirport") != null) {%>
  	         document.forms[0].departureAirport.value= <%=request.getParameter("departureAirport")%>;
  	   
  	        <%}%>
  	      <%if (request.getParameter("arrivalTime") != null) {%>
	         document.forms[0].arrivalTime.value= <%=request.getParameter("arrivalTime")%>;
	   
	        <%}%>
	        <%if (request.getParameter("arrivalAirport") != null) {%>
	         document.forms[0].arrivalAirport.value= <%=request.getParameter("arrivalAirport")%>;
	   
	        <%}%>
	        <%if (request.getParameter("departureTime") != null) {%>
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
    	   
    	   if(document.forms[0].departureTime.value != null){
    		   var timeVal = document.forms[0].departureTime.value;
    		   
    		   if(timeVal.length > 8){
    			   alert("Time format is wrong! Follow right format hh:mm:ss for Departure Time");
    			   return false;
    		   } 
    		   
    		   if(timeVal.length = 8 ) {   
    			   
    			   if(timeVal.charAt(2) == ":" && timeVal.charAt(5) == ":"){
        			 //  alert("right format");
    				   var hour = Number(timeVal.substring(0, 2));
    	    		   var minute = Number(timeVal.substring(3, 5));
    	    		   var second = Number(timeVal.substring(6, 8));
    	    		   if(hour > 24){
    	    			   alert ("Hour cannot be greater than 24 : Error in Departure Time");
    	    			   return false;
    	    		   }
    	    		   if(minute > 59){
    	    			   alert("Minutes cannot be greater than 59 : Error in Departure Time");
    	    			   return false;
    	    		   }
    	    		   if(second > 59){
    	    			   alert("Seconds cannot be greater than 59 : Error in Departure Time");
    	    			   return false;
    	    		   }
        		   }else {
        			   alert("Time format is wrong! Follow right format hh:mm:ss for Departure Time");
        			   return false;
        		   }
    		   }
    			   
    		   
    	 
    	   }
    	   
    	   
		   if(document.forms[0].arrivalTime.value == ""){
    		   alert("Enter the arrival time");
    		   return false;
    	   }
		   
		   if(document.forms[0].arrivalTime.value != null){
    		   var timeVal = document.forms[0].arrivalTime.value;
    		   
    		   if(timeVal.length > 8){
    			   alert("Time format is wrong! Follow right format hh:mm:ss for Arrival Time");
    			   return false;
    		   } 
    		   
    		   if(timeVal.length = 8 ) {   
    			   
    			   if(timeVal.charAt(2) == ":" && timeVal.charAt(5) == ":"){
        			 //  alert("right format");
    				   var hour = Number(timeVal.substring(0, 2));
    	    		   var minute = Number(timeVal.substring(3, 5));
    	    		   var second = Number(timeVal.substring(6, 8));
    	    		   if(hour > 24){
    	    			   alert ("Hour cannot be greater than 24 : Error in Arrival Time");
    	    			   return false;
    	    		   }
    	    		   if(minute > 59){
    	    			   alert("Minutes cannot be greater than 59 : Error in Arrival Time");
    	    			   return false;
    	    		   }
    	    		   if(second > 59){
    	    			   alert("Seconds cannot be greater than 59 : Error in Arrival Time");
    	    			   return false;
    	    		   }
        		   }else {
        			   alert("Time format is wrong! Follow right format hh:mm:ss for Arrival Time");
        			   return false;
        		   }
    		   }
    			   
    		   
    	 
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
</head>

<body onload='retainPreviousData();' onSubmit="return validate();">
	<div id="container">

		<jsp:include page="/public/TopContent.jsp"></jsp:include>

		<div id="content-container1">

			<jsp:include page="/public/LeftContent.jsp"></jsp:include>

			<div id="content">
				<h2>Flight Creation</h2>
				<div id="formdiv">
					<form action="/flight" method="post">
						<%
							List<AirportBean> airportList = new ArrayList<AirportBean>();
							if (request.getAttribute("airportList") != null) {
								airportList = (List<AirportBean>) request.getAttribute("airportList");
							}
						%>
						<table class="form_details">

							<%
								if (request.getParameter("successMessage") != null
										&& request.getParameter("successMessage").length() > 2) {
							%>
							<strong style="color: red;"><%=request.getParameter("successMessage")%></strong>
							<%
								}
							%>

							<INPUT TYPE="HIDDEN" NAME="selectedDate" ID="selectedDate">
							<tr>
								<td>From : <select name="departureAirport"
									id="departureAirport" onChange="">
										<option value=0>Select Airport</option>
										<%
											for (int a = 0; a < airportList.size(); a++) {
										%>

										<%
											airport = (AirportBean) airportList.get(a);
										%>
										<option value=<%=airport.getApcode()%>><%=airport.getApname() + "-" + airport.getApcode()%></option>
										<%
											}
										%>

								</select>
								</td>
								<td>Departure Date : 
									<input type="text" class="datepicker" name="departureDate">
								</td>
								<td>Departure Time : <input type="text"
									name="departureTime" id="dtime"  placeholder="hh:mm:ss">
								</td>
							</tr>

							<tr>
								<td>To :  <select name="arrivalAirport"
									id="arrivalAirport" onChange="">
										<option value=0>Select Airport</option>
										<%
											for (int a = 0; a < airportList.size(); a++) {
										%>

										<%
											airport = (AirportBean) airportList.get(a);
										%>
										<option value=<%=airport.getApcode()%>><%=airport.getApname() + "-" + airport.getApcode()%></option>
										<%
											}
										%>

								</select>
								</td>
								<td>Arrival Date :
									<input type="text" name="arrivalDate" class="datepicker">
								</td>
								<td>Arrival Time : <input
									type="text" name="arrivalTime" id="atime"  placeholder="hh:mm:ss">
								</td>
							</tr>
						</table>
						<br>

						<table border="1">

							<%
								if (request.getAttribute("routeId") != null) {
									int routeId = (Integer) request.getAttribute("routeId");
									if (routeId < 0) {
							%>
							<b><i>Route is not available....</i></b>
							<%
								} else {
							%>
							<b> Flight Segments</b>

							<tr>
								<td width=2%><input TYPE=checkbox name=routes
									VALUE="<%=routeId%>"></td>
								<td width=10%><%=request.getAttribute("srcAirport")%></td>
								<td width=10%><%=request.getAttribute("dstAirport")%></td>
							</tr>
							<%
								}
							%>
							<%
								}
							%>

						</table>

						<br> <input type="submit" name="createFlight"
							value="Create Flight" onClick="" />

					</form>
				</div>
			</div>

		</div>

		<jsp:include page="/public/BottomContent.jsp"></jsp:include>

	</div>
</body>
</html>