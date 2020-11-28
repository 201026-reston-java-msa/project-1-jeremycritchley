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
	
	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		
		String[] URI = request.getRequestURI().replace("/project-1/", "").split("/");
		EmployeeServiceImpl es = new EmployeeServiceImpl();
		
		if (URI.length > 1) {
			if (URI[1].equals("reims")) { 	// portal/reims/
				if (URI.length == 2) {		// portal/reims
					if ("POST".equals(request.getMethod())) {	//portal/reims method=POST
						try {
							ReimDTO rdto = om.readValue(request.getReader(), ReimDTO.class);
							int reim_id = es.submitReim(rdto, (String) session.getAttribute("user_id"));
							
							if (reim_id != 0) {
								rdto.setReimId(Integer.toString(reim_id));
								response.setContentType("application/json");
								response.getWriter().println(om.writeValueAsString(rdto));
							} else {
								response.setStatus(500);
							}
							
						} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (URI[2].equals("resolved")) {	// portal/reims/resolved
					List<ReimDTO> reims = es.viewRiemsByStatus((String) session.getAttribute("user_id"), true);
					if (reims != null) {
						response.setContentType("application/json");
						try {
							response.getWriter().println(om.writeValueAsString(reims));
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						response.setStatus(500);
					}
				} else if (URI[2].equals("pending")) {	// portal/reims/pending
					List<ReimDTO> reims = es.viewRiemsByStatus((String) session.getAttribute("user_id"), false);
					if (reims != null) {
						response.setContentType("application/json");
						try {
							response.getWriter().println(om.writeValueAsString(reims));
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						response.setStatus(500);
					}
				} else if (URI[2].equals("all")) {		// portal/reims/all
					List<ReimDTO> reims = es.viewReimsByEmployee((String) session.getAttribute("user_id"));
					if (reims != null) {
						response.setContentType("application/json");
						try {
							response.getWriter().println(om.writeValueAsString(reims));
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						response.setStatus(500);
					}
				}
			} else if (URI[1].equals("users")) {	// portal/users
				if ("PUT".equals(request.getMethod())) {
					try {
						UserDTO userUpdate = om.readValue(request.getReader(), UserDTO.class);
						if (es.updateInfo(userUpdate)) {
							session.setAttribute("username", userUpdate.getUsername());
							response.setStatus(200);
						} else {
							response.setStatus(500);
						}
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("GET".equals(request.getMethod())){
					UserDTO udto = es.viewByUser(request.getParameter("user_id"));
					if (udto != null) {
						try {
							response.getWriter().println(om.writeValueAsString(udto));
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
}
