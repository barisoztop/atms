package de.tum.in.dbpra;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tum.in.dbpra.model.bean.TransactionBean;
import de.tum.in.dbpra.model.dao.AirportDAO;
import de.tum.in.dbpra.model.dao.TransactionDAO;
import de.tum.in.dbpra.model.bean.ContactBean;
import de.tum.in.dbpra.model.dao.ContactDAO;
import de.tum.in.dbpra.model.bean.TicketBean;
import de.tum.in.dbpra.model.dao.TicketDAO;



@SuppressWarnings("serial")
public class BookTicketServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getSession().setAttribute("flightid", request.getParameter("flightid"));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/page3.jsp");
		dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	//add new customer
    	
    	//get the customerID
    	//@@@mock-up customerID
    	int customerID = 0;
    	
    	try {
    		TransactionDAO trDAO = new TransactionDAO();
        	TransactionBean transaction = new TransactionBean();
        	//set transactionBean by using input from jsp
        	//AGENTID,FLIGHTID,T_TIMESTAMP,CURRENCY,T_STATUS,MODEOFPAYMENT,AMOUNT,TYPEOFTRANSACTION
        	//@@@skip T_TIMESTAMP 
        	transaction.setAgentID(Integer.parseInt(request.getParameter("agentid")));
        	transaction.setFlightID(Integer.parseInt((String) request.getSession().getAttribute("flightid")));
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
        	ticket.setFlightID(Integer.parseInt((String) request.getSession().getAttribute("flightid")));
        	//@@@assume totalFare = amount
        	ticket.setTotalFare(Double.parseDouble(request.getParameter("amount")));
        	ticket.setNoOfChildren(Integer.parseInt(request.getParameter("noofchildren")));
        	ticket.setCurrency(request.getParameter("currency"));
        	//@@@the other attr. dep time/date/city arr time/date/city need to use FlightDAO to get it by using flightID
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
