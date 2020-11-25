package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.GenericDAO;
import com.revature.dao.ReimDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.models.ReimStatus;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.utils.DateStringify;

public class ManagerServiceImpl extends EmployeeServiceImpl implements ManagerService {
	
	private GenericDAO<User> userd;
	private GenericDAO<Reimbursement> reimd;
	
	public ManagerServiceImpl() {
		super();
	}
	
	public ManagerServiceImpl(ReimDAO r) {
		super();
		reimd = r;
	}

	public ManagerServiceImpl(UserDAO d) {
		super();
		userd = d;
	}
	
	public ManagerServiceImpl(UserDAO d, ReimDAO r) {
		super();
		userd = d;
		reimd = r;
	}
	
	
	
	@Override
	public boolean approveReim(ReimDTO rdto, int resolver) {
		return updateReim(rdto, resolver, 2);
	}

	@Override
	public boolean denyReim(ReimDTO rdto, int resolver) {
		return updateReim(rdto, resolver, 3);
	}
	
	private boolean updateReim(ReimDTO rdto, int resolver, int status) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		if (userd == null) {
			userd = new UserDAO();
		}
		
		boolean ret = false;
		Reimbursement reim = rdto.getReimInstance(reimd);
		User u = userd.selectById(resolver);
		if (u != null) {
			reim.setResolver(u);
			reim.setResolvedTime(DateStringify.stringifyNow());
			reim.setStatus(new ReimStatus(status));
			ret = reimd.update(reim);
		}

		return ret;
	}

	@Override
	public List<UserDTO> viewAllEmployees() {
		if (userd == null) {
			userd = new UserDAO();
		}
		
		List<User> employees = userd.selectAll("Role_FK", "2");
		List<UserDTO> dtos = null;
		if (employees != null) {
			dtos = new ArrayList<UserDTO>();
			for (User e: employees) {
				dtos.add(new UserDTO(e));
			}
		}
		return dtos;
	}
	
	@Override
	public List<ReimDTO> viewRiemsByStatus(int ownerId, boolean resolved) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		String param = "Status_FK";
		
		if (!resolved)
			param = "Status_FK!";
		
		List<Reimbursement> reims = reimd.selectAll(param, "1");
		List<ReimDTO> dtos = null;
		
		if (reims != null) {
			dtos = new ArrayList<ReimDTO>();
			for (Reimbursement r: reims) {
				dtos.add(new ReimDTO(r));
			}
		}
		
		return dtos;
	}

	@Override
	public UserDTO viewByUser(String user_id) {
		if (userd == null) {
			userd = new UserDAO();
		}
		UserDTO dto = null;
		try {
			User u = userd.selectById(Integer.parseInt(user_id));
			
			if (u != null) {
				dto = new UserDTO(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
}
