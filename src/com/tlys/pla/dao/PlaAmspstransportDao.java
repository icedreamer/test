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
import com.tlys.pla.model.PlaAmspstransport;

@Repository
public class PlaAmspstransportDao extends _GenericDao<PlaAmspstransport> {

	public List find(final PlaAmspstransport PlaAmspstransport) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaAmspstransport.class);
				buildCrit(ca, PlaAmspstransport);
				ca.addOrder(Order.asc("corpid"));
				ca.addOrder(Order.desc("month"));
				return ca.list();
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param PlaAmspstransport
	 */
	private void buildCrit(Criteria crit, PlaAmspstransport PlaAmspstransport) {
		if (null != PlaAmspstransport) {
			String statuss = PlaAmspstransport.getStatuss();
			String expdateStart = PlaAmspstransport.getMonthStart();
			String expdateEnd = PlaAmspstransport.getMonthEnd();
			String corpid = PlaAmspstransport.getCorpid();
			String corpids = PlaAmspstransport.getCorpids();
			String plannos = PlaAmspstransport.getPlannos();
			if (null != statuss && statuss.length() > 0) {
				crit.add(Restrictions.in("status", statuss.split(",")));
			}
			if (null != corpids && corpids.length() > 0) {
				crit.add(Restrictions.in("corpid", corpids.split(",")));
			}
			if (null != corpid && !"".equals(corpid)) {
				crit.add(Restrictions.eq("corpid", corpid));
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
