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
	</head>
	<body>
		<div id="container">
			<jsp:include page="/public/TopContent.jsp"></jsp:include>
	
			<div id="content-container1">
				<jsp:include page="/public/LeftContent.jsp"></jsp:include>
				<div id="content">
					<h2>The flight has been booked.</h2>
				</div>
			</div>
			<jsp:include page="/public/BottomContent.jsp"></jsp:include>
		</div>
	</body>
</html>