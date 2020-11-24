package com.revature.services;

import com.revature.dao.GenericDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.UserDTO;
import com.revature.models.User;

public class LoginServiceImpl implements LoginService{

	private GenericDAO<User> userd;
	
	public LoginServiceImpl(GenericDAO<User> userd) {
		this.userd = userd;
	}
	
	public LoginServiceImpl() {
		super();
		this.userd = new UserDAO();
	}
	
	@Override
	public UserDTO login(String username, String password) {

		if (username == null || password == null) {
			return null;
		}
		
		
		String un = "username";
		
		User user = userd.selectByParam(un, username);
		UserDTO ret = null;

		if (user != null) {
			if (password.equals(user.getPassword())) {
				ret = new UserDTO(user);
			}
		}
		
		
		
		return ret;
	}

	@Override
	public UserDTO insert(UserDTO u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
