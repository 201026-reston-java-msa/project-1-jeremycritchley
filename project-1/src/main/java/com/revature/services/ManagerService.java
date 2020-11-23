package com.revature.services;

import java.util.List;

import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;

public interface ManagerService {
	
	public ReimDTO approveReim(ReimDTO rdto);
	
	public ReimDTO denyReim(ReimDTO rdto);
	
	public List<UserDTO> viewAllEmployees();
}
