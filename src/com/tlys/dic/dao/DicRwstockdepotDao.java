package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicRwstockdepot;

@Repository
public class DicRwstockdepotDao extends _GenericDao<DicRwstockdepot> {

	public List<DicRwstockdepot> findDicRwstockdepot() {
		return getHibernateTemplate().find("from DicRwstockdepot order by shortname ");
	}

	public DicRwstockdepot getFirstDicRwstockdepot() {
		final String hql = "select a from DicRwstockdepot a order by a.shortname ";
		return (DicRwstockdepot) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setMaxResults(1);

				return query.uniqueResult();
			}
		});
	}
}
