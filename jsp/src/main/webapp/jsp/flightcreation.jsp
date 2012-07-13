<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="de.tum.in.dbpra.model.bean.AirportBean" %>
<%@ page import = "java.util.ArrayList" %>
<%@page import="de.tum.in.dbpra.model.bean.RoutePairBean" %>
<jsp:useBean id="routePair" scope="request" class="de.tum.in.dbpra.model.bean.RoutePairBean" />
<jsp:useBean id="airport" scope="request" class="de.tum.in.dbpra.model.bean.AirportBean" />
<!DOCTYPE html>

<html lang="en">
    <head>
  
<style>
A:hover {text-decoration: none;
   
    border: 0px;
    font-size:14pt;
    color: #2d2b2b; }
</style>
    
    <link rel="stylesheet" type="text/css" href="../datepicker.css"/>
   
    <script type="text/javascript" src="../datepicker.js"></script>
   
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
    <body onload='retainPreviousData();' >
    <table>
    	<h1>Flight Creation</h1>
    	<% if (request.getParameter("successMessage")!= null){ %>
    	   <strong style="color: red;"><%=request.getParameter("successMessage")%></strong>
    	<%} %>
		<form action="/flight" method="post">
		<INPUT TYPE="HIDDEN" NAME="selectedDate" ID="selectedDate">
		<tr>
		<td>
		  From : <select name="departureAirport" id="departureAirport" onChange="">
		         <option value=0>Select Airport</option>
		         <% for(int a = 0; a < airportList.size() ; a++){%>
		              
	                  <%    airport = (AirportBean)airportList.get(a);%> 
                             <option value=<%=airport.getApcode() %>><%=airport.getApname()+"-"+airport.getApcode() %></option>
                      <% } %>       
                 
                 </select>
         </td>
         <td>
             Departure Date :   <input  type="text" name="departureDate" id="ddate"><input type=button value="Select Date" onclick="displayDatePicker('departureDate', this);">  
         </td>
         <td>  
		     Departure Time :   <input type="text" name="departureTime" id="dtime"> hh:mm:ss
		 </td>
	</tr> 
		 
    <tr>
		<td>
		  To :  &nbsp;&nbsp;&nbsp;    <select name="arrivalAirport" id="arrivalAirport" onChange="">
		         <option value=0>Select Airport</option>
		         <% for(int a = 0; a < airportList.size() ; a++){%>
		              
	                  <%    airport = (AirportBean)airportList.get(a);%> 
                             <option value=<%=airport.getApcode() %>><%=airport.getApname()+"-"+airport.getApcode() %></option>
                      <% } %>       
                 
                 </select>
         </td>
         <td>         
            Arrival Date :  &nbsp;&nbsp;&nbsp;&nbsp;     <input  type="text" name="arrivalDate" id="adate"><input type=button value="Select Date" onclick="displayDatePicker('arrivalDate', this);">  
         </td>
         <td>  
		    Arrival Time :   &nbsp;&nbsp;&nbsp;&nbsp;      <input type="text" name="arrivalTime" id="atime"> hh:mm:ss
		 </td>
    </tr>    
		
	</table>
	<br>
	<br>	
	
	<table width=80% border="1" cellspacing="2" cellpadding="2">
	  
	         <% 
                   ArrayList list = new ArrayList();
                   if(request.getAttribute("routeList")!= null){
        	            list=(ArrayList)request.getAttribute("routeList");
        	            if(list.size() == 0 ){%>
        	            	<b><i>Route is not available....</i></b>
        	           <%} else {%>
        	                <b> Flight Segments</b>
        	           <%} %>
        	                
        	
                            <%   for(int i=0 ; i< list.size(); i++){
    	                          routePair = (RoutePairBean)list.get(i); %>
    	                       <tr>
    	                      
    	                          <td width=2%><input TYPE=checkbox name=routes VALUE="<%=routePair.getFirstRouteID()+"-"+routePair.getSecondRouteID()%>"></td> <td width=10%> <%=routePair.getFirstRouteID() %> </td> 
    	                          <td width=10%><%=routePair.getFirstSourceAirport()%></td> 
    	                          <td width=10%><%=routePair.getFirstAirportDestination() %></td>   
    	                                
    	                               <% if(routePair.getSecondRouteID()==0) {
             								} else {%>
             					                <td width=1%><%=routePair.getSecondRouteID() %></td>
             				                    <td width=10%><%=routePair.getSecondSourceAirport() %> </td>
             				                    <td width=10%><%=routePair.getSecondAirportDestination() %></td>
    	                              <% }%>
    	                             
    	                          </td>
    	                         
    	                       </tr>  
    	                       
			                          
                                <% }
                       }%>
	     
	</table>
	
		       <br>
		       <input type="submit" name="createFlight" value="Create Flight" onClick="validate();" />
		 
	 </form>	 
    </body>
   
</html>