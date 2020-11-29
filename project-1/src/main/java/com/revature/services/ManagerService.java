package com.revature.services;

import java.util.List;

import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;

public interface ManagerService {
	
	public boolean approveReim(ReimDTO rdto, String resolver);
	
	public boolean denyReim(ReimDTO rdto, String resolver);
	
	public List<UserDTO> viewAllEmployees();
	
	
}
