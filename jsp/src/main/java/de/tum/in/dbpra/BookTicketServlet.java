package de.tum.in.dbpra;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tum.in.dbpra.model.bean.*;
import de.tum.in.dbpra.model.dao.CustomerDAO;
import de.tum.in.dbpra.model.dao.*;

@SuppressWarnings("serial")
public class BookTicketServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getSession().setAttribute("flightid",
				request.getParameter("flightid"));

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/jsp/CustomerData.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TransactionDAO trDAO = new TransactionDAO();
		ContactDAO cDAO = new ContactDAO();
		TicketDAO tkDAO = new TicketDAO();

		try {

			// add new customer
			String fName = request.getParameter("fname");
			String lName = request.getParameter("lname");
			String passportNO = request.getParameter("passportno");
			Date dob = Date.valueOf(request.getParameter("dob"));
			String sex = request.getParameter("sex");
			// get the customerID
			CustomerBean c = new CustomerBean();
			c.setFName(fName);
			c.setLName(lName);
			c.setPassportNO(passportNO);
			c.setDOB(dob);
			c.setSex(sex);
			CustomerDAO custDAO = new CustomerDAO();

			// create new customer and get the customer id
			int customerID = custDAO.createNewCustomer(c);
			System.out.println("customerID = " + customerID);
			// agentID
			int agentID = 1;

			// create FlightDAO
			FlightDAO fDAO = new FlightDAO();
			FlightBean flightBean = new FlightBean();
			flightBean.setFlightID(Integer.parseInt((String) request
					.getSession().getAttribute("flightid")));
			fDAO.getFlightDetail(flightBean);
			System.out.println("FLIGHTDAO is Successful");

			TransactionBean transaction = new TransactionBean();
			// set transactionBean by using input from jsp
			// AGENTID,FLIGHTID,T_TIMESTAMP,CURRENCY,T_STATUS,MODEOFPAYMENT,AMOUNT,TYPEOFTRANSACTION
			transaction.setAgentID(agentID);
			transaction.setFlightID(flightBean.getFlightID());
			transaction.setCurrency(request.getParameter("currency"));
			transaction.sett_status(request.getParameter("t_status"));
			transaction.setModeOfPayment(request.getParameter("modeofpayment"));
			// transaction.setAmount(Double.parseDouble(request.getParameter("amount")));
			transaction.setTypeOfTransaction(request
					.getParameter("typeoftransaction"));
			transaction.setCustomerID(customerID);
			trDAO.createNewTransaction(transaction);
			System.out.println("TRDAO is Successful");

			ContactBean contact = new ContactBean();
			// set ContactBean by using input from jsp
			// CUSTOMERID,AGENTID,CURRENCY,STATUS,MODEOFPAYMENT,AMOUNT
			contact.setCustomerID(customerID);
			contact.setAgentID(agentID);
			contact.setCurrency(request.getParameter("currency"));
			contact.setStatus(request.getParameter("status"));
			contact.setModeOfPayment(request.getParameter("modeofpayment"));
			// contact.setAmount(Double.parseDouble(request.getParameter("amount")));
			cDAO.createNewContact(contact);
			System.out.println("CDAO is Successful");

			TicketBean ticket = new TicketBean();
			// set TicketBean by using input from jsp
			// TICKETID,FLIGHTID,TOTALFARE,NOOFCHILDREN,DEPARTURETIME,DEPARTUREDATE,DEPARTUREAIRPORTCODE,CURRENCY,ARRIVALAIRPORTCODE,ARRIVALTIME,ARRIVALDATE,CUSTOMERID
			ticket.setFlightID(flightBean.getFlightID());
			// assume totalFare = amount
			// ticket.setTotalFare(Double.parseDouble(request.getParameter("amount")));
			// ticket.setNoOfChildren(Integer.parseInt(request.getParameter("noofchildren")));
			ticket.setCurrency(request.getParameter("currency"));
			ticket.setCustomerID(customerID);
			ticket.setArrivalAirportCode(flightBean.getDestinationCity());
			ticket.setArrivalDate(flightBean.getArrivalDate());
			ticket.setArrivalTime(flightBean.getArrivalTime());
			ticket.setDepartureAirportCode(flightBean.getSourceCity());
			ticket.setDepartureDate(flightBean.getDepartureDate());
			ticket.setDepartureTime(flightBean.getDepartureTime());
			tkDAO.createNewTicket(ticket);

			System.out.println("Transaction is Successful");

			tkDAO.connection.commit();
			cDAO.connection.commit();
			trDAO.connection.commit();

			tkDAO.connection.close();
			cDAO.connection.close();
			trDAO.connection.close();
		} catch (Throwable e) {
			request.setAttribute("error", true);
			// roll back all three transactions here
			try {
				tkDAO.connection.rollback();

				cDAO.connection.rollback();
				trDAO.connection.rollback();

				tkDAO.connection.close();
				cDAO.connection.close();
				trDAO.connection.close();

				System.out.println("Rollback!!");
			} catch (Exception e1) {
				System.out.println("Rollback FAILED");
			}
			e.printStackTrace();
			System.out.println("Transaction FAIL");
		}

		// commit all three transactions here if there is no error

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/jsp/bookResult.jsp");
		dispatcher.forward(request, response);
	}
}
