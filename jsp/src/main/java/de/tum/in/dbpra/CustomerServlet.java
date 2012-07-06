package de.tum.in.dbpra;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.tum.in.dbpra.model.bean.CustomerBean;
import de.tum.in.dbpra.model.dao.CustomerDAO;

import java.io.IOException;

@SuppressWarnings("serial")
public class CustomerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/customersearch.jsp");
		dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	try {
        	CustomerDAO dao = new CustomerDAO();
        	CustomerBean customer = new CustomerBean();
        	customer.setId(Integer.parseInt(request.getParameter("custkey")));
        	dao.getCustomerByID(customer);
        	request.setAttribute("customer", customer);
    	} catch (Throwable e) {
    		request.setAttribute("error", true);
    	}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/customerview.jsp");
		dispatcher.forward(request, response);
    }
}
