
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.tum.in.dbpra.model.bean.AirportBean"%>
<%@page import="java.util.ArrayList"%>
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

					<form  method="post" action="/bookticket">
						<%
							ArrayList apList = (ArrayList) request.getAttribute("airportlist");
						%>
						<table class="form_details">
							<tr>
								<td>Last Name: </td>
								<td><input type="text" name=lname></td>
							</tr>
							<tr>
								<td>First Name: </td>
								<td><input type="text" name=fname></td>
							</tr>
							<tr>
								<td>Dob</td>
									<td><input type="text" class="datepicker"
										name="dob"></td>
							</tr>
							<tr>
								<td>Sex</td>
									<td><input type="radio" name=sex value=m>Male
									<input type="radio" name=sex value=f>Female</td>


							</tr>

						</table>
						<br> <input type=submit name=checkFlights value="Confirm Booking">
					</form>

				</div>
			</div>
		</center>


	</div>

	<jsp:include page="/public/BottomContent.jsp"></jsp:include>

</div>
</body>
</html>