package com.revature.web;

import java.io.IOException;
import java.util.List;

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
			throws JsonParseException, JsonMappingException, IOException {
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
			} else if (URI[1].equals("users")) { // portal/users
				if ("PUT".equals(request.getMethod())) {

					UserDTO userUpdate = om.readValue(request.getReader(), UserDTO.class);
					if (es.updateInfo(userUpdate)) {
						session.setAttribute("username", userUpdate.getUsername());
						response.setStatus(200);
					} else {
						response.setStatus(500);
					}
				} else if ("GET".equals(request.getMethod())) {
					UserDTO udto = es.viewByUser(request.getParameter("user_id"));
					if (udto != null) {

						response.getWriter().println(om.writeValueAsString(udto));

					} else {
						response.setStatus(400);
					}
				}
			} else {
				// serve employee home page
			}
		} else {
			// serve employee home page
		}
	}
	
	private void reimsHelper(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		String status = request.getParameter("status");
		if (status.equals("resolved")) { // portal/reims/resolved
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
		} else if (status.equals("pending")) { // portal/reims/pending
			List<ReimDTO> reims = es.viewRiemsByStatus((String) session.getAttribute("user_id"), false);
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		} else if (status.equals("all")) { // portal/reims/all
			List<ReimDTO> reims = es.viewReimsByEmployee((String) session.getAttribute("user_id"));
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		}
	}
}
