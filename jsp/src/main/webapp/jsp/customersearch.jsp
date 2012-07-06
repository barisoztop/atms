<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Customer search</title>
    </head>
    <body>
    	<h1>Search customer by key:</h1>
		<form action="/customer" method="post">
		  Custkey: <input type="text" name="custkey" placeholder="Search..." /><br />
		  <input type="submit" value="Submit" />
		</form>
    </body>
</html>