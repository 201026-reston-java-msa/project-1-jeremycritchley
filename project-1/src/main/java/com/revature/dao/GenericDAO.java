package com.revature.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.utils.HibernateUtil;


public abstract class GenericDAO<T> {
	
	private static Logger log = Logger.getLogger(GenericDAO.class);
	
	public int insert(T t) {
		int ret = 0;
		
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ret = (Integer) ses.save(t);
		
		try {
			tx.commit();
		} catch (IllegalStateException e) {
			log.warn("EXCEPTION INSERTING REIM \n" + e.getMessage());
			e.printStackTrace();
			ret = 0;
		} catch (RollbackException e) {
			e.printStackTrace();
			log.warn("EXCEPTION INSERTING REIM \n" + e.getMessage());
			ret = 0;
		} catch (Exception e) {
			ret = 0;
			log.warn("EXCEPTION INSERTING REIM \n" + e.getMessage());
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
			log.warn("EXCEPTION UPDATING\n " + e.getMessage());
			ret = false;
		} catch (RollbackException e) {
			e.printStackTrace();
			log.warn("EXCEPTION UPDATING \n" + e.getMessage());
			ret = false;
		} catch (Exception e) {
			ret = false;
			log.warn("EXCEPTION UPDATING \n" + e.getMessage());
		}
		return ret;
	}
	
	public abstract T selectById(int id);
	
	public abstract List<T> selectAll(String param, String val);
	
	public abstract T selectByParam(String param, String val);
}
