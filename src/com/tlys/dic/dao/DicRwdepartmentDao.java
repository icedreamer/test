/**
 * @author ¹ù½¨¾ü
 */
package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicRwdepartment;

@Repository
public class DicRwdepartmentDao extends _GenericDao<DicRwdepartment> {

	public List<DicRwdepartment> find(final DicRwdepartment dicRwdepartment) {
		List<DicRwdepartment> dicRwdepartments = (List<DicRwdepartment>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public List<DicRwdepartment> doInHibernate(Session session) throws HibernateException {
						List dacs = session.createCriteria(DicRwdepartment.class)
								.add(Example.create(dicRwdepartment).enableLike(MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return dicRwdepartments;
	}

	public List<DicRwdepartment> findByBureauid(String pid) {
		return this.findByProperty("bureauid", pid);
	}

	public List<DicRwdepartment> findByTypeid(String pid) {
		return this.findByProperty("tpyeid", pid);
	}

	public List<DicRwdepartment> findByPycode(String pycode) {
		return getHibernateTemplate().find("from DicRwdepartment a where a.pycode like '%" + pycode + "%'");
	}

	public List<DicRwdepartment> findRwDepartment(final String pycode, final String typeid) {
		return (List<DicRwdepartment>) getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(DicRwdepartment.class);
				if (null != typeid && !"".equals(typeid)) {
					criteria.add(Restrictions.eq("tpyeid", typeid));
				}
				if (null != pycode && !"".equals(pycode)) {
					criteria.add(Restrictions.like("pycode", "%" + pycode.toUpperCase() + "%"));
				}
				criteria.addOrder(Order.asc("shortname"));
				return criteria.list();
			}
		});
	}
}
