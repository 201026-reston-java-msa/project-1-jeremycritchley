package com.revature.services;

import java.util.List;

import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;

public interface EmployeeService {
	
	public UserDTO viewByUser(String user_id);
	
	public boolean updateInfo(UserDTO udto);
	
	public int submitReim(ReimDTO rdto, String author);
	
	public List<ReimDTO> viewRiemsByStatus(String ownerId, boolean resolved);
	
	public List<ReimDTO> viewReimsByEmployee(String ownerId);
	
}
