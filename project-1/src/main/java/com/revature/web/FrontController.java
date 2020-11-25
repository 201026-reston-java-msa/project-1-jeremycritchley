package com.revature.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
    	final String[] URI = request.getRequestURI().replace("/project-1/", "").split("/");
		System.out.println(URI);
		
		switch(URI[0]) {
		case "login":
			break;
		case "logout":
			break;
		case "portal":
			if (URI.length > 1) {
				if (URI[1].equals("users")) {
					// dispatch to users servlet
				} else if(URI[1].equals("reims")) {
					// dispatch to reims servlet
				}
			} else {
				// show home page
			}
			break;
		}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);
	}

}
