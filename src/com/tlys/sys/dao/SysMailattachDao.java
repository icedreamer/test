package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysMailattach;

@Repository
public class SysMailattachDao extends _GenericDao<SysMailattach> {
	public List<SysMailattach> findSysMailattach(final List<Long> mailids) {
		return (List<SysMailattach>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMailattach.class);
				criteria.add(Restrictions.in("mailid", mailids));
				return criteria.list();
			}
		});

	}
}
