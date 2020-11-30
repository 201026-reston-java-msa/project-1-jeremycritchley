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
import com.revature.utils.DateStringify;

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
	
	public EmployeeServiceImpl(UserDAO d, ReimDAO r) {
		super();
		userd = d;
		reimd = r;
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
			u.setUsername(udto.getUsername());
			ret = userd.update(u);
		}
		
		return ret;
	}

	@Override
	public int submitReim(ReimDTO rdto, String author) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		if (userd == null) {
			userd = new UserDAO();
		}
		
		int ret = 0;
		
		if (rdto != null) {
			rdto.setAuthor(author);
			rdto.setSubmittedTime(DateStringify.stringifyNow());
			Reimbursement reim = rdto.createReimInstance((UserDAO) userd);
			if (reim.getAmount() > 0) {
				if (reim.getAuthor() != null) {
					ret = reimd.insert(reim);
					System.out.println(ret);
				}
			}
		}
		return ret;
	}

	@Override
	public List<ReimDTO> viewRiemsByStatus(String ownerId, boolean resolved) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		List<Reimbursement> reims = null;
		List<ReimDTO> reimdtos = null;
		
		String param = "Author_FK";
		String val = ownerId;
		
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
	
	@Override
	public List<ReimDTO> viewReimsByEmployee(String ownerId) {
		if (reimd == null) {
			reimd = new ReimDAO();
		}
		
		List<Reimbursement> reims = null;
		List<ReimDTO> dtos = null;
		
		String param = "Author_FK";
		String val = ownerId;
		
		reims = reimd.selectAll(param, val);
		
		if (reims != null) {
			dtos = new ArrayList<ReimDTO>();
			for (Reimbursement r: reims) {
				dtos.add(new ReimDTO(r));
			}
		}
		
		
		return dtos;
	}
	

}
