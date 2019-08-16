package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicReparetype;

@Repository
public class DicReparetypeDao extends _GenericDao<DicReparetype> {

	public List<DicReparetype> findDicReparetypes(final String suitkind, final String rtypeid) {
		return (List<DicReparetype>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(DicReparetype.class);
				if (null != suitkind && !"".equals(suitkind)) {
					criteria.add(Restrictions.eq("suitkind", suitkind));
				}
				if (null != rtypeid && !"".equals(rtypeid)) {
					criteria.add(Restrictions.eq("rtypeid", rtypeid));
				}
				criteria.addOrder(Order.asc("suitkind"));
				criteria.addOrder(Order.desc("rtypeid"));
				return criteria.list();
			}
		});
	}
}
