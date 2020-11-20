package com.revature.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.revature.dao.GenericDAO;
import com.revature.dao.ReimDAO;
import com.revature.dao.UserDAO;
import com.revature.models.ReimStatus;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.User;

public class HibernateInitializer {
	
	public static void main(String[] args) {
		initializeValues();
		getValues();
		
	}

	private static void getValues() {
		// TODO Auto-generated method stub
		GenericDAO<User> userd = new UserDAO();
		System.out.println(userd.selectAll("first_name", "Jeremy"));
		System.out.println(userd.selectById(1));
		System.out.println(userd.selectAll("Role_FK", "2"));
		
		GenericDAO<Reimbursement> reimd = new ReimDAO();
		System.out.println(reimd.selectAll("Status_FK", "1"));
		System.out.println(reimd.selectById(6));
		
		
	}

	private static void initializeValues() {
		Role man = new Role(1, "MANAGER");
		Role emp = new Role(2, "EMPLOYEE");
		
		ReimType lodge = new ReimType(1, "LODGE");
		ReimType food = new ReimType(2, "FOOD");
		ReimType travel = new ReimType(3, "TRAVEL");
		ReimType other = new ReimType(4, "OTHER");
		
		ReimStatus pend = new ReimStatus(1, "PENDING");
		ReimStatus approved = new ReimStatus(2, "APPROVED");
		ReimStatus denied = new ReimStatus(3, "DENIED");
		
		User manager = new User("jeremy", "passvvord", "Jeremy", "Critchley", "jeremy@email.com", man);
		User employ = new User("employee", "passvvord", "Good", "Employee", "goodemployee@email.com", emp);
		
		GenericDAO<User> udao = new UserDAO();
		udao.insert(manager);
		udao.insert(employ);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
	    
		Reimbursement reimLodge = new Reimbursement(500.00, formatter.format(date), null, "base lodging reim", employ, null, pend, lodge);
		Reimbursement reimFood = new Reimbursement(500.00, formatter.format(date), null, "base food reim", employ, null, pend, food);
		
		Date dateRes = new Date();
		Reimbursement reimTravel = new Reimbursement(500.0, formatter.format(date), formatter.format(dateRes), "base travel reim", employ, manager, approved, travel);
		Reimbursement reimOther = new Reimbursement(500.0, formatter.format(date), formatter.format(dateRes), "base other reim", employ, manager, denied, other);
		
		GenericDAO<Reimbursement> reimd = new ReimDAO();
		
		reimd.insert(reimLodge);
		reimd.insert(reimFood);
		reimd.insert(reimTravel);
		reimd.insert(reimOther);
		
		
	}
	
}
