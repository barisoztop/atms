package main.java.de.tum.in.dbpra;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.de.tum.in.dbpra.model.bean.*;
import main.java.de.tum.in.dbpra.model.dao.CustomerDAO;
import main.java.de.tum.in.dbpra.model.bean.*;
import main.java.de.tum.in.dbpra.model.dao.*;



@SuppressWarnings("serial")
public class BookTicketServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getSession().setAttribute("flightid", request.getParameter("flightid"));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/home1/CustomerData.jsp");
		dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	try {
    		 		
    		//add new customer
        	String fName = request.getParameter("fname");
        	String lName = request.getParameter("lname");
        	String passportNO = request.getParameter("passportno");
        	Date dob = Date.valueOf(request.getParameter("dob"));
        	String sex = request.getParameter("sex");
        	//get the customerID
        	//@@@ check if the customer exist
        	CustomerBean c = new CustomerBean();
        	c.setFName(fName);
        	c.setLName(lName);
        	c.setPassportNO(passportNO);
        	c.setDOB(dob);
        	c.setSex(sex);
        	CustomerDAO custDAO = new CustomerDAO();
        	custDAO.createNewCustomer(c);
        	//@@@mock-up customerID
        	int customerID = 0;
    		
    		
    		//create FlightDAO
        	FlightDAO fDAO = new FlightDAO();
        	FlightBean flightBean = new FlightBean();
        	flightBean.setFlightID(Integer.parseInt((String) request.getSession().getAttribute("flightid")));
        	fDAO.getFlightDetail(flightBean);
    		
    		TransactionDAO trDAO = new TransactionDAO();
        	TransactionBean transaction = new TransactionBean();
        	//set transactionBean by using input from jsp
        	//AGENTID,FLIGHTID,T_TIMESTAMP,CURRENCY,T_STATUS,MODEOFPAYMENT,AMOUNT,TYPEOFTRANSACTION
        	transaction.setAgentID(Integer.parseInt(request.getParameter("agentid")));
        	transaction.setFlightID(flightBean.getFlightID());
        	transaction.setCurrency(request.getParameter("currency"));
        	transaction.sett_status(request.getParameter("t_status"));
        	transaction.setModeOfPayment(request.getParameter("modeofpayment"));
        	transaction.setAmount(Double.parseDouble(request.getParameter("amount")));
        	transaction.setTypeOfTransaction(request.getParameter("typeoftransaction"));
        	trDAO.createNewTransaction(transaction);
        	
        	ContactDAO cDAO = new ContactDAO();
        	ContactBean contact = new ContactBean();
        	//set ContactBean by using input from jsp
        	//CUSTOMERID,AGENTID,CURRENCY,STATUS,MODEOFPAYMENT,AMOUNT
        	contact.setCustomerID(customerID);
        	contact.setAgentID(Integer.parseInt(request.getParameter("agentid")));
        	contact.setCurrency(request.getParameter("currency"));
        	contact.setStatus(request.getParameter("status"));
        	contact.setModeOfPayment(request.getParameter("modeofpayment"));
        	contact.setAmount(Double.parseDouble(request.getParameter("amount")));
        	cDAO.createNewContact(contact);
        	
        	TicketDAO tkDAO = new TicketDAO();
        	TicketBean ticket = new TicketBean();
        	//set TicketBean by using input from jsp
        	//TICKETID,FLIGHTID,TOTALFARE,NOOFCHILDREN,DEPARTURETIME,DEPARTUREDATE,DEPARTUREAIRPORTCODE,CURRENCY,ARRIVALAIRPORTCODE,ARRIVALTIME,ARRIVALDATE,CUSTOMERID
        	//@@@ticketID need to be further implemented
        	ticket.setFlightID(flightBean.getFlightID());
        	//assume totalFare = amount
        	ticket.setTotalFare(Double.parseDouble(request.getParameter("amount")));
        	ticket.setNoOfChildren(Integer.parseInt(request.getParameter("noofchildren")));
        	ticket.setCurrency(request.getParameter("currency"));
        	ticket.setCustomerID(customerID);
        	ticket.setArrivalAirportCode(flightBean.getDestinationCity());
        	ticket.setArrivalDate(flightBean.getArrivalDate());
        	ticket.setArrivalTime(flightBean.getArrivalTime());
        	ticket.setDepartureAirportCode(flightBean.getSourceCity());
        	ticket.setDepartureDate(flightBean.getDepartureDate());
        	ticket.setDepartureTime(flightBean.getDepartureTime());
        	tkDAO.createNewTicket(ticket);
        	
        	
    	} catch (Throwable e) {
    		request.setAttribute("error", true);
    		//roll back all three transactions here
    		
    	}
    	
    	//commit all three transactions here if there is no error
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/page4.jsp");
		dispatcher.forward(request, response);
    }
}
