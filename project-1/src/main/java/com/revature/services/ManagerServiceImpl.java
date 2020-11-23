package com.revature.services;

import java.util.List;

import com.revature.dao.GenericDAO;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;

public class ManagerServiceImpl extends EmployeeServiceImpl implements ManagerService {
	
	private GenericDAO<User> userd;
	private GenericDAO<Reimbursement> reimd;
	
	@Override
	public boolean approveReim(ReimDTO rdto, int resolver) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean denyReim(ReimDTO rdto, int resolver) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserDTO> viewAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ReimDTO> viewRiemsByStatus(int ownerId, boolean resolved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO viewByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
