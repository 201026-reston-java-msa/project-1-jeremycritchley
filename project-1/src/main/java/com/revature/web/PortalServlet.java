package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PortalServlet
 */
public class PortalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println("In portal Servlet");
		if (session == null) {
			response.sendRedirect("login");
		} else if (session.getAttribute("role").equals("EMPLOYEE")) {
			System.out.println("Employee");
			EmployeeHelper eh = new EmployeeHelper();
			eh.processRequest(request, response);
		} else if (session.getAttribute("role").equals("MANAGER")) {
			System.out.println("Manager");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
