package com.revature.dao;

import org.hibernate.Session;

import com.revature.models.ReimType;
import com.revature.utils.HibernateUtil;

public class TypeDAO {
	public static final ReimType selectById(int id) {
		Session ses = HibernateUtil.getSession();
		ReimType rt = ses.get(ReimType.class, id);
		return rt;
	}
}
