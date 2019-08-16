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
import com.tlys.equ.model.EquCarHpreginfo;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class EquCarHpreginfoDao extends _GenericDao<EquCarHpreginfo> {

	public EquCarHpreginfo load(String id) {
		List nlist = this.getHibernateTemplate().find(
				"from EquCarHpreginfo where carno='" + id + "'");
		EquCarHpreginfo equCarHpregininfo = null;
		if (nlist.size() > 0) {
			equCarHpregininfo = (EquCarHpreginfo) nlist.get(0);
		}
		// log.error("fengym--EquCarNpinfoDao.load->equCarNpinfo=="+equCarNpinfo);
		return equCarHpregininfo;
	}

	public List<EquCarHpreginfo> find(final EquCarHpreginfo obj) {
		List<EquCarHpreginfo> equCarHpreginfos = (List<EquCarHpreginfo>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<EquCarHpreginfo> doInHibernate(Session session)
							throws HibernateException {
						Criteria ca = session
								.createCriteria(EquCarHpreginfo.class);
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

		return equCarHpreginfos;
	}

}
