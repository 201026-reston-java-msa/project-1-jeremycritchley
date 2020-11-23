package com.revature.services;

import java.util.List;

import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;

public interface EmployeeService {
	
	public boolean updateInfo(UserDTO udto);
	
	public int submitReim(ReimDTO rdto);
	
	public List<ReimDTO> viewRiemsByStatus(int ownerId, boolean resolved);
	
}
