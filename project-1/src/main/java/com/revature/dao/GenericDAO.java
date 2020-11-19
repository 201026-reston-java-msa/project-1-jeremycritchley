package com.revature.dao;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.utils.HibernateUtil;

public abstract class GenericDAO<T> {
	
	protected boolean insert(T t) {
		boolean ret = true;
		
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ses.save(t);
		
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
	
	protected boolean update(T t) {
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
}
