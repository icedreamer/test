/**
 * 
 */
package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.equ.model.EquCarNpinfo;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class EquCarNpinfoDao extends _GenericDao<EquCarNpinfo> {
	private final Logger log = Logger.getLogger(this.getClass());

	public EquCarNpinfo load(String id) {
		List nlist = this.getHibernateTemplate().find(
				"from EquCarNpinfo where carno='" + id + "'");
		EquCarNpinfo equCarNpinfo = null;
		if (nlist.size() > 0) {
			equCarNpinfo = (EquCarNpinfo) nlist.get(0);
		}
		return equCarNpinfo;
	}
	public List<EquCarNpinfo> find(final EquCarNpinfo obj) {
		return (List<EquCarNpinfo>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(EquCarNpinfo.class);
						if (null != obj) {
							if (null != obj.getCarnos()
									&& obj.getCarnos().trim().length() > 0) {
								ca.add(Restrictions.sqlRestriction(" carno in (" + obj.getCarnos() + ")"));
							}
							if (null != obj.getCarno()
									&& obj.getCarno().length() > 0) {
								ca
										.add(Restrictions.eq("carno", obj
												.getCarno()));
							}
						}
						ca.addOrder(Order.asc("carno"));
						return ca.list();
					}
				});
	}

	// updateEntity
	public void updateEntity(EquCarNpinfo object) {
		try {
			this.getHibernateTemplate().update(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
