package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysContact;

public class SysContactDao extends _GenericDao<SysContact> {
	public List<SysContact> findSysContact(final SysContact sysContact) {

		return (List<SysContact>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(SysContact.class);
				String areaname = sysContact.getAreaname();
				String areaid = sysContact.getAreaid();
				String department = sysContact.getDepartment();
				String name = sysContact.getName();
				if (null != areaname && !"".equals(areaname)) {
					criteria.add(Restrictions.eq("areaname", areaname));
				}
				if (null != areaid && !"".equals(areaid)) {
					criteria.add(Restrictions.eq("areaid", areaid));
				}
				if (null != department && !"".equals(department)) {
					criteria.add(Restrictions.eq("department", department));
				}
				if (null != name && !"".equals(name)) {
					criteria.add(Restrictions.like("name", "%" + name + "%"));
				}
				criteria.addOrder(Order.asc("areaid"));
				criteria.addOrder(Order.asc("corpshortname"));
				return criteria.list();
			}
		});

	}

	public List<Object[]> findAreas() throws Exception {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select distinct a.areaid,a.areaname from SysContact a order by a.areaid ";
				Query query = session.createQuery(sql);
				return query.list();
			}
		});
	}
}
