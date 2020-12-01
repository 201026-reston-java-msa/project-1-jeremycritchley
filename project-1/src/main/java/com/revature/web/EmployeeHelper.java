package com.revature.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.services.EmployeeServiceImpl;

public class EmployeeHelper implements Helper {

	ObjectMapper om = new ObjectMapper();
	HttpSession session;
	EmployeeServiceImpl es;
	
	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException, ServletException {
		session = request.getSession(false);

		String[] URI = request.getRequestURI().replace("/project-1/", "").split("/");
		es = new EmployeeServiceImpl();
		for (String s: URI) {
			System.out.println(s);
		}
		if (URI.length > 1) {
			if (URI[1].equals("reims")) { // portal/reims/
				// portal/reims
				if ("POST".equals(request.getMethod())) { // portal/reims method=POST
					System.out.println("in post for reims");
					ReimDTO rdto = om.readValue(request.getReader(), ReimDTO.class);
					int reim_id = es.submitReim(rdto, (String) session.getAttribute("user_id"));

					if (reim_id != 0) {
						rdto.setReimId(Integer.toString(reim_id));
						response.setContentType("application/json");
						response.getWriter().println(om.writeValueAsString(rdto));
					} else {
						response.setStatus(500);
					}

				} else {
					reimsHelper(request, response);
				}
			} else if (URI[1].equals("users") && URI.length==3) { // portal/users
				
				if ("POST".equals(request.getMethod()) && URI[2].equals(session.getAttribute("user_id"))) {
					System.out.println("in PUT");
					UserDTO userUpdate = om.readValue(request.getReader(), UserDTO.class);
					if (es.updateInfo(userUpdate)) {
						System.out.println("supposedly updated user");
						session.setAttribute("username", userUpdate.getUsername());
						response.setStatus(200);
					} else {
						response.setStatus(204);
					}
				} else if ("GET".equals(request.getMethod()) && URI[2].equals(session.getAttribute("user_id"))) {
					String user_id = URI[2];
					System.out.println("MAYBE IN GET???");
					if (user_id != null) {
						UserDTO udto = es.viewByUser(user_id);
						if (udto != null) {
							response.getWriter().println(om.writeValueAsString(udto));
						} else {
							response.setStatus(400);
						}
					} else {
						request.getRequestDispatcher("portal.html").forward(request, response);
						
					}
				}
			} else if (URI[1].equals("users")){
				// serve employee home page
				System.out.println("Serving personal page");
				request.getRequestDispatcher("/personal.html").forward(request, response);
			}
		} else {
			// serve employee home page
			
			request.getRequestDispatcher("portal.html").forward(request, response);
			
		}
	}
	
	private void reimsHelper(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException, ServletException {
		String status = request.getParameter("status");
		System.out.println("Status = " + status );
		if (status == null) {
			System.out.println("Trying to show reim-view");
			request.getRequestDispatcher("/reim-view.html").forward(request, response);
		} else if ("resolved".equals(status)) { // portal/reims/resolved
			List<ReimDTO> reims = es.viewRiemsByStatus((String) session.getAttribute("user_id"), true);
			for (ReimDTO r: reims ) {
				System.out.println(r.getDescription());
			}
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		} else if ("pending".equals(status)) { // portal/reims/pending
			List<ReimDTO> reims = es.viewRiemsByStatus((String) session.getAttribute("user_id"), false);
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		} else if ("all".equals(status)) { // portal/reims/all
			List<ReimDTO> reims = es.viewReimsByEmployee((String) session.getAttribute("user_id"));
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		} else if ("new".equals(status)) {
			System.out.println("in new");
			request.getRequestDispatcher("/new-reim.html").forward(request, response);
		}
	}
}
