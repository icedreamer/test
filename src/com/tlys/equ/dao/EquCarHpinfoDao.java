/**
 * 
 */
package com.tlys.equ.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.equ.model.EquCarHpinfo;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class EquCarHpinfoDao extends _GenericDao<EquCarHpinfo> {

	public EquCarHpinfo load(String id) {
		List nlist = this.getHibernateTemplate().find(
				"from EquCarHpinfo where carno='" + id + "'");
		EquCarHpinfo equCarHpinfo = null;
		if (nlist.size() > 0) {
			equCarHpinfo = (EquCarHpinfo) nlist.get(0);
		}
		// log.error("fengym--EquCarNpinfoDao.load->equCarNpinfo=="+equCarNpinfo);
		return equCarHpinfo;
	}

	public List<EquCarHpinfo> find(final EquCarHpinfo obj) {
		List<EquCarHpinfo> equCarHpinfos = (List<EquCarHpinfo>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<EquCarHpinfo> doInHibernate(Session session)
							throws HibernateException {
						Criteria ca = session
								.createCriteria(EquCarHpinfo.class);
						if (null != obj) {
							if (null != obj.getCarnos()
									&& obj.getCarnos().trim().length() > 0) {
								ca.add(Restrictions
										.sqlRestriction(" carno in ("
												+ obj.getCarnos() + ")"));
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

		return equCarHpinfos;
	}

}
