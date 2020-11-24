package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.GenericDAO;
import com.revature.dao.ReimDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;

public class EmployeeServiceImpl implements EmployeeService {
	
	private GenericDAO<Reimbursement> reimd;
	private GenericDAO<User> userd;
	
	public EmployeeServiceImpl() {
		super();
	}
	
	public EmployeeServiceImpl(ReimDAO d) {
		super();
		reimd = d;
	}

	public EmployeeServiceImpl(UserDAO d) {
		super();
		userd = d;
	}

	
	@Override
	public boolean updateInfo(UserDTO udto) {
		if (userd == null) {
			userd = new UserDAO();
		}
		
		boolean ret = false;
		
		if (udto != null) {
			//ret = userd.update(udto.getUserInstance(userd));
			User u = udto.getUserInstance(userd);
			u.setEmail(udto.getEmail());
			u.setFirstName(udto.getFirstName());
			u.setLastName(udto.getLastName());
			u.setPassword(udto.getPassword());
			u.setUsername(udto.getUsername());
			ret = userd.update(u);
		}
		
		return ret;
	}

	@Override
	public int submitReim(ReimDTO rdto) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		int ret = 0;
		if (rdto != null) {
			Reimbursement reim = rdto.getReimInstance(reimd);
			if (reim.getAmount() > 0) {
				if (reim.getAuthor() != null) {
					ret = reimd.insert(reim);
				}
			}
		}
		return ret;
	}

	@Override
	public List<ReimDTO> viewRiemsByStatus(int ownerId, boolean resolved) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		List<Reimbursement> reims = null;
		List<ReimDTO> reimdtos = null;
		
		String param = "Author_FK";
		String val = Integer.toString(ownerId);
		
		reims = reimd.selectAll(param, val);
		
		if (reims != null) {
			reimdtos = new ArrayList<ReimDTO>();
			for (Reimbursement r: reims) {
				if (resolved) {
					if (r.getStatus().getStatusId() != 1) {
						reimdtos.add(new ReimDTO(r));
					}
				} else {
					if (r.getStatus().getStatusId() == 1) {
						reimdtos.add(new ReimDTO(r));
					}
				}
			}
		}
		
		return reimdtos;
	}

}
