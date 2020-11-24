package com.revature.services;

import java.util.List;

import com.revature.dao.GenericDAO;
import com.revature.dao.ReimDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;

public class ManagerServiceImpl extends EmployeeServiceImpl implements ManagerService {
	
	private GenericDAO<User> userd;
	private GenericDAO<Reimbursement> reimd;
	
	public ManagerServiceImpl() {
		super();
		reimd = new ReimDAO();
		userd = new UserDAO();
	}
	
	public ManagerServiceImpl(ReimDAO d) {
		super();
		reimd = d;
		userd = new UserDAO();
	}

	public ManagerServiceImpl(UserDAO d) {
		super();
		reimd = new ReimDAO();
		userd = d;
	}
	
	@Override
	public boolean approveReim(ReimDTO rdto, int resolver) {
		
		//Reimbursement reim = rdto.getReimInstance();

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
