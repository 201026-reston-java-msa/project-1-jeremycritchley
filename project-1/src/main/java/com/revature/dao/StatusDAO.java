package com.revature.dao;

import org.hibernate.Session;

import com.revature.models.ReimStatus;
import com.revature.utils.HibernateUtil;

public class StatusDAO {
	public static final ReimStatus selectById(int id) {
		Session ses = HibernateUtil.getSession();
		ReimStatus rs = ses.get(ReimStatus.class, id);
		return rs;
	}
}
