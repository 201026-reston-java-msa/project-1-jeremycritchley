package com.revature.dto;

import com.revature.dao.GenericDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;

public class UserDTO {
	
	private String userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	
	public UserDTO() {
		super();
	}

	public UserDTO(String userId, String username, String password, String firstName, String lastName, String email,
			String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}
	
	public UserDTO(User u) {
		super();
		this.userId = Integer.toString(u.getUserId());
		this.username = u.getUsername();
		this.password = u.getPassword();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.role = u.getRole().getRole();
	}
	
	public User getUserInstance() {
		GenericDAO<User> userd = new UserDAO();
		return userd.selectById(Integer.parseInt(this.userId));
	}
	
	public User createUserInstance() {
		User u = new User();
		u.setUsername(username);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setPassword(password);
		u.setEmail(email);
		Role role = new Role(this.role);
		u.setRole(role);
		return u;
	}
	
}
