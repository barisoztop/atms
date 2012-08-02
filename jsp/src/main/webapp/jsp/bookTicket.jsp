<!DOCTYPE html>
<%@page import="de.tum.in.dbpra.model.bean.AirportBean"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/main.css" />
<link type="text/css" rel="stylesheet"
	href="/css/ui-lightness/jquery-ui-1.8.22.custom.css" />
<script type="text/javascript" src="/jS/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/jS/jquery-ui-1.8.22.custom.min.js"></script>

<jsp:useBean id="airport" scope="request"
	class="de.tum.in.dbpra.model.bean.AirportBean" />
<script type="text/javascript">
			$(document).ready(function() {
				$(".datepicker").datepicker({
					dateFormat : 'yy-mm-dd'
				});
			});
		</script>

<script>
		
		 function retainPreviousData(){
			 
			 <%if (request.getParameter("sourcecity") != null) {%>
  	         document.forms[0].departureAirport.value= <%=request.getParameter("sourcecity")%>;
  	       <%}%>
  	       
	        <%if (request.getParameter("destinationcity") != null) {%>
	         document.forms[0].arrivalAirport.value= <%=request.getParameter("destinationcity")%>;
	   
	        <%}%>
	        
	    	   
	    	   <%if (request.getParameter("departuredate") != null) {%>
	    	      document.forms[0].departureDate.value= <%=request.getParameter("departuredate")%>;
	    	   <%}%>
	    	   
	    	    	   
	    	   
	        }
		
		function validate(){
	    	   if(document.forms[0].sourcecity.value == 0 || document.forms[0].sourcecity.value == null){
	    		   alert("Select the source airport");
	    		   return false;
	    	   }
	    	   if(document.forms[0].destinationcity.value == 0 || document.forms[0].destinationcity.value == null ){
	    		   alert("Select the destination airport");
	    		   return false;
	    	   }
			   
			   if (document.forms[0].sourcecity.value == document.forms[0].destinationcity.value) {
				alert("Arrival And Departure Airports cannot be the Same.");
				return false;
			}
			   if(document.forms[0].departuredate.value == "" || document.forms[0].departuredate.value == null }){
	    		   alert("Enter the departure date");
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
				<h2>Book New Flight</h2>
				<div id="formdiv">

					<form method="post" action="/searchflight">
						<%
								ArrayList apList = (ArrayList) request.getAttribute("airportlist");
							%>
						<table class="form_details">
							<tr>
								<td>Departure Airport: <select name="sourcecity"
									id="sourcecity" class="input_select" required="required">
										<option value="select">Select</option>
										<%
												for (int i = 0; i < apList.size(); i++) {
	
													airport = (AirportBean) apList.get(i);
											%><option value=<%=airport.getApcode()%>><%=airport.getApname()%></option>
										<%
												}
											%>
										<option>
								</select></td>
								<td>Destination Airport: <select name="destinationcity"
									id="destinationcity" class="input_select" required="required">
										<option value="select">Select</option>
										<%
												for (int i = 0; i < apList.size(); i++) {
	
													airport = (AirportBean) apList.get(i);
											%><option value=<%=airport.getApcode()%>><%=airport.getApname()%></option>
										<%
												}
											%>

								</select></td>
								<td>Date of Travel: <input type="text" class="datepicker"
									name="departuredate" required="required"></td>
							</tr>
						</table>
						<br> <input type=submit name=checkFlights
							value="Check for Flights">
					</form>
				</div>
			</div>
		</div>
		<jsp:include page="/public/BottomContent.jsp"></jsp:include>
	</div>
</body>
</html>