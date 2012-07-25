
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.tum.in.dbpra.model.bean.AirportBean"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/main.css" />
<script type="text/javascript"  src="/jS/jquery-1.6.4.min.js"></script>
<link    href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" ></script>

<jsp:useBean id="airport" scope="request"
	class="de.tum.in.dbpra.model.bean.AirportBean" />

</head>

<div id="container">

	<jsp:include page="/public/TopContent.jsp"></jsp:include>

	<div id="content-container1">
	
		<jsp:include page="/public/LeftContent.jsp"></jsp:include>

		<center>
			<div id="content">
				<h2>Customer Information</h2>
				<div id="formdiv">

					<form  method="post" action="/searchflight">
						<%
							ArrayList apList = (ArrayList) request.getAttribute("airportlist");
						%>
						<table>
							<tr>
								<td><label class="input_label"> Last Name</label></td>
								<td><input type="text" name=lname></td>
							</tr>
							<tr>
								<td><label class="input_label"> First Name</label></td>
								<td><input type="text" name=fname></td>
							</tr>
							<tr>
								<td><label class="input_label"> Dob</label></td>
									<td><input type="text" name=dob></td>
							</tr>
							<tr>
								<td><label class="input_label">Sex</label></td>
									<td><input type="radio" name=sex value=m>Male</td>
									<td><input type="radio" name=sex value=f>Female</td>
							
								
							</tr>
							
						</table>
						<br> <input type=submit name=checkFlights class="input_label"
							value="Confirm Booking">
					</form>

				</div>
			</div>
		</center>


	</div>
	
	<jsp:include page="/public/BottomContent.jsp"></jsp:include>
	
</div>
</body>
</html>