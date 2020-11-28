package com.revature.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerHelper implements Helper {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		String[] URI = request.getRequestURI().replace("/project-1/", "").split("/");
		
		if (URI.length > 1) {
			
		} else {
			// serve employee home page
		}
		
	}
	
}
