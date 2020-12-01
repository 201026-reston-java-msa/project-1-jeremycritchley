package com.revature.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.utils.HibernateUtil;

public abstract class GenericDAO<T> {
	
	public int insert(T t) {
		int ret = 0;
		
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ret = (Integer) ses.save(t);
		
		try {
			tx.commit();
		} catch (IllegalStateException e) {
			System.out.println("hmmm");
			e.printStackTrace();
			ret = 0;
		} catch (RollbackException e) {
			System.out.println("hhhhhhhhhm");
			e.printStackTrace();
			ret = 0;
		}
		
		
		return ret;
	}
	
	public boolean update(T t) {
		boolean ret = true;
		
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ses.update(t);
		
		try {
			tx.commit();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			ret = false;
		} catch (RollbackException e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}
	
	public abstract T selectById(int id);
	
	public abstract List<T> selectAll(String param, String val);
	
	public abstract T selectByParam(String param, String val);
}
