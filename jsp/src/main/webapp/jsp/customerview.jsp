<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="de.tum.in.dbpra.model.bean.CustomerBean" %>
<jsp:useBean id="customer" scope="request" class="de.tum.in.dbpra.model.bean.CustomerBean" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Customer view</title>
    </head>
    <body>
    	<% if (request.getAttribute("error") != null) { %>
        	<h1>Customer not found!</h1>
        <% } else { %>
        	<h1>Customer found!</h1>
        	<p>Custkey: <%= customer.getId() %></p>
        	<p>Name: <%= customer.getName() %></p>
        	<p>Address: <%= customer.getAddress() %></p>
        <% } %>
    </body>
</html>