package com.revature.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.services.ManagerServiceImpl;

public class ManagerHelper implements Helper {

	ObjectMapper om = new ObjectMapper();
	ManagerServiceImpl ms = new ManagerServiceImpl();
	HttpSession session;
	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		String[] URI = request.getRequestURI().replace("/project-1/", "").split("/");
		session = request.getSession(false);
		if (URI.length > 1) {
			if (URI[1].equals("reims")) {
				if (URI.length == 2) {
					reimsHelper(request, response);
				} else if (URI[2].equals("approve") || URI[2].equals("deny")) {
					ReimDTO rdto = om.readValue(request.getReader(), ReimDTO.class);
					if (URI[2].equals("approve")) {
						ms.approveReim(rdto, (String) session.getAttribute("user_id"));
					} else {
						ms.denyReim(rdto, (String) session.getAttribute("user_id"));
					}
				
				}
			} else if (URI[1].equals("users")) {
				usersHelper(request, response);
			} else {
				// serve manage home page
			}
		} else {
			// serve manager home page
		}
		
	}

	private void usersHelper(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		// TODO Auto-generated method stub
		String param = null;
		if ((param = request.getParameter("id")) != null) {
			// get user by ID
			// if user is manager, don't include a response
			
			// else, get the users reims and include them in response
			UserDTO user = ms.viewByUser(param);
			if (user != null) {
				response.getWriter().write(om.writeValueAsString(user));
			}
			
			if (user.getRole().equals("EMPLOYEE")) {
				response.getWriter().write(om.writeValueAsString(ms.viewReimsByEmployee(param)));
			}
			
			
		} else if ("GET".equals(request.getMethod())) {
			// get all employees
			response.getWriter().write(om.writeValueAsString(ms.viewAllEmployees()));
		}
		
	}

	private void reimsHelper(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		// TODO Auto-generated method stub
		String param = null;
		if ((param = request.getParameter("status")) != null) {
			// get reims by status
			if (param.equals("resolved")) {
				response.getWriter().write(om.writeValueAsString(ms.viewRiemsByStatus("0", true)));
			} else {
				response.getWriter().write(om.writeValueAsString(ms.viewRiemsByStatus("0", false)));
			}
			
		} else if ((param = request.getParameter("id")) != null) {
			// get reim by ID
			response.getWriter().write(om.writeValueAsString(ms.viewReimsById(param)));
		} else if ("GET".equals(request.getMethod())) {
			// get all reims
			response.getWriter().write(om.writeValueAsString(ms.viewAllReims()));
		}
		
	}
	
}
