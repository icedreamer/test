package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.*;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitCarcu;
import com.tlys.comm.bas._GenericDao;

/**
 * 
 * @author Administrator
 * 
 */
@Repository
public class BitCarcuDao extends _GenericDao<BitCarcu> {

	public BitCarcu getBitCarcu(final String corpid, final String corptype,
			final String datadate) {
		if (log.isDebugEnabled()) {
			log.debug("corpid : " + corpid);
			log.debug("corptype : " + corptype);
			log.debug("datadate : " + datadate);
		}
		return (BitCarcu) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session
								.createCriteria(BitCarcu.class);

						criteria.add(Restrictions.eq("corpid", corpid));
						criteria.add(Restrictions.eq("corptype", corptype));
						criteria.add(Restrictions.le("datadate", datadate));
						Criterion criterionDu = Restrictions.gt("dcu", 0d);
						Criterion criterionQcu = Restrictions.gt("qcu", 0d);
						Criterion criterionMcu = Restrictions.gt("mcu", 0d);
						Criterion criterionYcu = Restrictions.gt("ycu", 0d);
						criteria.add(Restrictions.or(Restrictions.or(
								criterionDu, criterionQcu), Restrictions.or(
								criterionMcu, criterionYcu)));
						criteria.addOrder(Order.desc("datadate"));
						List<BitCarcu> list = criteria.list();
						if (list != null && !list.isEmpty()) {
							log.debug("list :  " + list);
							return list.get(0);
						}
						return null;
					}
				});
	}
}
