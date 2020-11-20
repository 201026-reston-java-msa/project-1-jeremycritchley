package com.revature.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public class UserDAO extends GenericDAO<User>{

	@Override
	public List<User> selectAll(String param, String val) {
		Session ses = HibernateUtil.getSession();
		List<User> userList;
		String sql;
		try  {
			int n = Integer.parseInt(val);
			sql = "from User where "+ param + "=" + val;
		} catch (Exception e) {
			sql = "from User where "+ param + "='" + val + "'";
		}
		
		userList = ses.createQuery(sql, User.class).list();
		return userList;
	}

	@Override
	public User selectById(int id) {
		Session ses = HibernateUtil.getSession();
		User u = ses.get(User.class, id);
		return u;
	}

	@Override
	public User selectByParam(String param, String val) {
		List<User> uses = selectAll(param, val);
		
		if (uses.size() == 0) {
			// log a warning
			return null;
		}
		
		return uses.get(0);
	}

}
