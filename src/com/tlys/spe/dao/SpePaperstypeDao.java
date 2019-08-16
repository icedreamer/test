package com.tlys.spe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.spe.model.SpePaperstype;

@Repository
public class SpePaperstypeDao extends _GenericDao<SpePaperstype> {

	public List<SpePaperstype> findSpePaperstype() {
		final String hql = " order by name ";
		return findAll(hql);
	}
	
	public List<SpePaperstype> findByTpid(String tpid) {
		String hql = " order by name ";
		if(null!=tpid){
			hql = " where ptypeid="+tpid+hql;
		}
		
		return findAll(hql);
	}

	public String getMaxPtypeId() {
		final String hql = "select max(ptypeid) from SpePaperstype ";
		return (String) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				Object o = query.uniqueResult();
				return o == null ? "0" : o.toString();
			}
		});
	}
}
