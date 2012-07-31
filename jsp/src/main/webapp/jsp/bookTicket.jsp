<!DOCTYPE html>
<%@page import="de.tum.in.dbpra.model.bean.AirportBean"%>
<%@page import="java.util.ArrayList"%>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/css/main.css" />
		<link type="text/css" rel="stylesheet" href="/css/ui-lightness/jquery-ui-1.8.22.custom.css" />
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
	</head>
	<body>
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
									<td>Departure Airport:
									<select name="sourcecity"
										class="input_select"  required="required" >
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
									<td>Destination Airport: 
									<select name="destinationcity"
										class="input_select" required="required" >
											<option value="select">Select</option>
											<%
												for (int i = 0; i < apList.size(); i++) {
	
													airport = (AirportBean) apList.get(i);
											%><option value=<%=airport.getApcode()%>><%=airport.getApname()%></option>
											<%
												}
											%>
	
									</select></td>
									<td>Date of Travel:
									<input type="text" class="datepicker"
										name="departuredate"></td>
								</tr>
							</table>
							<br> <input type=submit name=checkFlights value="Check for Flights">
						</form>
					</div>
				</div>
			</div>
			<jsp:include page="/public/BottomContent.jsp"></jsp:include>
		</div>
	</body>
</html>