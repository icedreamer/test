package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.pla.model.PlaMupstransport;

@Repository
public class PlaMupstransportDao extends _GenericDao<PlaMupstransport> {

	public List find(final PlaMupstransport plaMupstransport) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransport.class);
				buildCrit(ca, plaMupstransport);
				ca.addOrder(Order.desc("month"));
				ca.addOrder(Order.asc("areaid"));
				if (log.isDebugEnabled()) {
					log
							.debug("##############################################Criteria=="
									+ ca);
				}
				return ca.list();
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param plaMupstransport
	 */
	private void buildCrit(Criteria crit, PlaMupstransport plaMupstransport) {
		if (null != plaMupstransport) {
			String statuss = plaMupstransport.getStatuss();
			String expdateStart = plaMupstransport.getMonthStart();
			String expdateEnd = plaMupstransport.getMonthEnd();
			String areaid = plaMupstransport.getAreaid();
			String plannos = plaMupstransport.getPlannos();
			if (null != statuss && statuss.length() > 0) {
				crit.add(Restrictions.in("status", statuss.split(",")));
			} else {
				crit.add(Restrictions.eq("status", "-1"));
			}
			if (null != areaid && !"".equals(areaid)) {
				crit.add(Restrictions.eq("areaid", areaid));
			}
			if (null != expdateStart && !"".equals(expdateStart)) {
				expdateStart = expdateStart.replace("-", "");
				crit.add(Restrictions.ge("month", expdateStart));
			}
			if (null != expdateEnd && !expdateEnd.equals("")) {
				expdateEnd = expdateEnd.replace("-", "");
				crit.add(Restrictions.le("month", expdateEnd));
			}
			if (null != plannos && plannos.length() > 0) {
				crit.add(Restrictions.in("planno", plannos.split(",")));
			}
		}
	}

}
