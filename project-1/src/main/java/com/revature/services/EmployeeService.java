package com.revature.services;

import java.util.List;

import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;

public interface EmployeeService {
	
	public UserDTO updateInfo(UserDTO udto);
	
	public ReimDTO submitReim(ReimDTO rdto);
	
	public List<ReimDTO> viewRiemsByStatus(int ownerId, boolean resolved);
	
}
