package com.revature.services;

import com.revature.models.User;

public interface LoginService {
	
	public User login(String username, String password);
	
	public User insert();
	
}
