package de.tum.in.dbpra;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tum.in.dbpra.model.bean.TransactionBean;
import de.tum.in.dbpra.model.dao.TransactionDAO;
import de.tum.in.dbpra.model.bean.ContactBean;
import de.tum.in.dbpra.model.dao.ContactDAO;
import de.tum.in.dbpra.model.bean.TicketBean;
import de.tum.in.dbpra.model.dao.TicketDAO;



@SuppressWarnings("serial")
public class SearchFlightServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/exercise73.jsp");
		dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	try {
    		TransactionDAO trDAO = new TransactionDAO();
        	TransactionBean transaction = new TransactionBean();
        	transaction.setAgentID(Integer.parseInt(request.getParameter("agentid")));
        	trDAO.createNewTransaction(transaction);
        	
        	ContactDAO cDAO = new ContactDAO();
        	ContactBean contact = new ContactBean();
        	
        	TicketDAO tkDAO = new TicketDAO();
        	TicketBean ticket = new TicketBean();
        	
    	} catch (Throwable e) {
    		request.setAttribute("error", true);

    	}
    	
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/exercise73.jsp");
		dispatcher.forward(request, response);
    }
}
