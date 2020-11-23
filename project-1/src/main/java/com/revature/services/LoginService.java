package com.revature.services;

import com.revature.dto.UserDTO;

public interface LoginService {
	
	public UserDTO login(String username, String password);
	
	public UserDTO insert(UserDTO u);
	
}
