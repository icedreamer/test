package com.tlys.dic.dao.ctl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.ctl.CtlCustomerreceiver;

@Repository
public class CtlCustomerreceiverDao extends _GenericDao<CtlCustomerreceiver> {

	public List<CtlCustomerreceiver> findAll() {
		return super.findAll();
	}

	public List<CtlCustomerreceiver> findCtlCustomerreceiver(final String customerid) {
		final String hql = "from CtlCustomerreceiver a where a.customerid = ? order by receivername ";
		return (List<CtlCustomerreceiver>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, customerid);
				return query.list();
			}
		});
	}

	public CtlCustomerreceiver getCtlCustomerreceiver(final String receiverid) {
		final String hql = "from CtlCustomerreceiver where receiverid = ? ";
		return (CtlCustomerreceiver) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, receiverid);
				query.setMaxResults(1);
				return query.uniqueResult();
			}
		});
	}
}
