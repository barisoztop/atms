
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="main.java.de.tum.in.dbpra.model.bean.AirportBean"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="home1/css/main.css" />
<script type="text/javascript"  src="home1/jS/jquery-1.6.4.min.js"></script>
<link    href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" ></script>

<jsp:useBean id="airport" scope="request"
	class="main.java.de.tum.in.dbpra.model.bean.AirportBean" />
<script type="text/javascript">
	$(document).ready(function() {
		$(".datepicker").datepicker({ dateFormat: 'yy-mm-dd' });
	});
</script>

</head>

<div id="container">
	<jsp:include page="public/TopContent.jsp"></jsp:include>

	<div id="content-container1">
		<jsp:include page="public/LeftContent.jsp"></jsp:include>

		<center>
			<div id="content">
				<h2>Book New Flight</h2>
				<div id="formdiv">

					<form  method="post" action="/FinalDb/SearchFlightServlet">
						<%
							ArrayList apList = (ArrayList) request.getAttribute("airportlist");
						%>
						<table>
							<tr>
								<td><label class="input_label"> Departure Airport</label></td>
								<td width="30px"><select name="sourcecity"
									class="input_select">
										<%
											for (int i = 0; i < apList.size(); i++) {

												airport = (AirportBean) apList.get(i);
										%><option value=<%=airport.getApcode()%>><%=airport.getApname()%></option>
										<%
											}
										%>
										<option>
								</select></td>
							</tr>
							<tr>
								<td><label class="input_label"> Destination Airport</label></td>
								<td width="30px"><select name="destinationcity"
									class="input_select">
										<%
											for (int i = 0; i < apList.size(); i++) {

												airport = (AirportBean) apList.get(i);
										%><option value=<%=airport.getApcode()%>><%=airport.getApname()%></option>
										<%
											}
										%>
									
									</select></td>
							</tr>
							<tr>
								<td><label class="input_label"> Date of Travel</label></td>
								<td width="30px"><input type="text" class="datepicker" name="departuredate"></td>
							</tr>
							</tr>
							
						</table>
						<br> <input type=submit name=checkFlights class="input_label"
							value="Check for Flights">
					</form>

				</div>
			</div>
		</center>


	</div>
	<jsp:include page="public/BottomContent.jsp"></jsp:include>
</div>
</body>
</html>