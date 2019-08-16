package com.tlys.dic.dao.ctl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.ctl.CtlCorpsender;

@Repository
public class CtlCorpsenderDao extends _GenericDao<CtlCorpsender> {

	public List<CtlCorpsender> findCtlCorpsender(final String corpid) {
		return (List<CtlCorpsender>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(CtlCorpsender.class);
				criteria.addOrder(Order.asc("sendershortname"));
				if (null != corpid && !"".equals(corpid)) {
					criteria.add(Restrictions.eq("corpid", corpid));
				}
				return criteria.list();
			}
		});
	}

	public CtlCorpsender getCtlCorpsender(final String senderid) {
		final String hql = "from CtlCorpsender where senderid = ? ";
		return (CtlCorpsender) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, senderid);
				query.setMaxResults(1);
				return query.uniqueResult();
			}
		});
	}

	public String getMaxReq() {
		final String sql = "select max(senderid) from CtlCorpsender where substr(senderid,0,2) = 'ZB'";
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				return query.uniqueResult();
			}
		});
	}
}
