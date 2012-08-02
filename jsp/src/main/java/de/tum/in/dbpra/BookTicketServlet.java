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

			String fName = request.getParameter("fname");
			String lName = request.getParameter("lname");
			String passportNO = request.getParameter("passportno");
			Date dob = Date.valueOf(request.getParameter("dob"));
			String sex = request.getParameter("sex");

			CustomerBean c = new CustomerBean();
			c.setFName(fName);
			c.setLName(lName);
			c.setPassportNO(passportNO);
			c.setDOB(dob);
			c.setSex(sex);
			CustomerDAO custDAO = new CustomerDAO();

			int customerID = custDAO.createNewCustomer(c);
			System.out.println("customerID = " + customerID);

			int agentID = 1; // set default agent, assuming only one agent can
								// perform transactions on behalf of customer

			FlightDAO fDAO = new FlightDAO();
			FlightBean flightBean = new FlightBean();
			flightBean.setFlightID(Integer.parseInt((String) request
					.getSession().getAttribute("flightid")));
			fDAO.getFlightDetail(flightBean);

			TransactionBean transaction = new TransactionBean();
			transaction.setAgentID(agentID);
			transaction.setFlightID(flightBean.getFlightID());
			transaction.setCurrency(request.getParameter("currency"));
			transaction.sett_status(request.getParameter("t_status"));
			transaction.setModeOfPayment(request.getParameter("modeofpayment"));
			transaction.setTypeOfTransaction(request
					.getParameter("typeoftransaction"));
			transaction.setCustomerID(customerID);
			trDAO.createNewTransaction(transaction);

			ContactBean contact = new ContactBean();
			contact.setCustomerID(customerID);
			contact.setAgentID(agentID);
			contact.setCurrency(request.getParameter("currency"));
			contact.setStatus(request.getParameter("status"));
			contact.setModeOfPayment(request.getParameter("modeofpayment"));
			cDAO.createNewContact(contact);

			TicketBean ticket = new TicketBean();
			ticket.setFlightID(flightBean.getFlightID());
			ticket.setCurrency(request.getParameter("currency"));
			ticket.setCustomerID(customerID);
			ticket.setArrivalAirportCode(flightBean.getDestinationCity());
			ticket.setArrivalDate(flightBean.getArrivalDate());
			ticket.setArrivalTime(flightBean.getArrivalTime());
			ticket.setDepartureAirportCode(flightBean.getSourceCity());
			ticket.setDepartureDate(flightBean.getDepartureDate());
			ticket.setDepartureTime(flightBean.getDepartureTime());
			tkDAO.createNewTicket(ticket);

			trDAO.connection.commit();
			cDAO.connection.commit();
			tkDAO.connection.commit();

			trDAO.connection.close();
			cDAO.connection.close();
			tkDAO.connection.close();

		} catch (Throwable e) {
			request.setAttribute("error", true);
			try {
				trDAO.connection.rollback();
				cDAO.connection.rollback();
				tkDAO.connection.rollback();

				trDAO.connection.close();
				cDAO.connection.close();
				tkDAO.connection.close();

				System.out.println("Rollback!!");
			} catch (Exception e1) {
				System.out.println("Rollback!!");
			}
			e.printStackTrace();
			System.out.println("Transaction FAIL");
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/jsp/bookResult.jsp");
		dispatcher.forward(request, response);
	}
}
