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
import com.tlys.dic.model.DicProdsecondkind;

@Repository
public class DicProdsecondkindDao extends _GenericDao<DicProdsecondkind> {
	public List<DicProdsecondkind> find(final DicProdsecondkind dicProdsecondkind) {
		List<DicProdsecondkind> dicProdsecondkinds = (List<DicProdsecondkind>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public List<DicProdsecondkind> doInHibernate(Session session) throws HibernateException {
						List dacs = session.createCriteria(DicProdsecondkind.class)
								.add(Example.create(dicProdsecondkind).enableLike(MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});
		return dicProdsecondkinds;
	}

	public List<DicProdsecondkind> findDicProdsecondkind(final String[] categoryids) {
		return (List<DicProdsecondkind>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(DicProdsecondkind.class);
				if (null != categoryids && categoryids.length > 0) {
					criteria.add(Restrictions.in("categoryid", categoryids));
				}
				criteria.addOrder(Order.asc("shortname"));
				return criteria.list();
			}
		});
	}
}
