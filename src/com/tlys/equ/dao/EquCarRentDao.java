/**
 * 
 */
package com.tlys.equ.dao;

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
import com.tlys.comm.util.CommUtils;
import com.tlys.equ.model.EquCarRent;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class EquCarRentDao extends _GenericDao<EquCarRent> {

	public EquCarRent load(String id) {
		EquCarRent equCarRent = super.load(id);
		return equCarRent;
	}

	public List<EquCarRent> find(final EquCarRent obj) {
		List<EquCarRent> equCarRents = (List<EquCarRent>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<EquCarRent> doInHibernate(Session session) throws HibernateException {
				Criteria ca = session.createCriteria(EquCarRent.class);
				if (null != obj) {
					if (null != obj.getCarnos() && obj.getCarnos().trim().length() > 0) {
						ca.add(Restrictions.sqlRestriction(" carno in (" + obj.getCarnos() + ")"));
					}
					if (null != obj.getCarno() && obj.getCarno().length() > 0) {
						ca.add(Restrictions.eq("carno", obj.getCarno()));
					}
				}
				ca.addOrder(Order.asc("carno"));
				return ca.list();
			}
		});

		return equCarRents;
	}

	/**
	 * 获取租赁车辆的数量
	 * 
	 * @return
	 */
	public int getEquRentCars() {
		final String sql = "select count(a.carno) from EquCarRent a where " + CommUtils.getCorpIds("a.rentcorpid");
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				Object o = query.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * 获取出租车辆的数量
	 * 
	 * @return
	 */
	public int getEquCorpCars() {
		final String sql = "select count(b.carno) from EquCar a, EquCarRent b where a.carno = b.carno and "
				+ CommUtils.getCorpIds("a.corpid");
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				Object o = query.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

}
