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
import com.tlys.pla.model.PlaMspstransport;

@Repository
public class PlaMspstransportDao extends _GenericDao<PlaMspstransport> {

	public List find(final PlaMspstransport PlaMspstransport) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransport.class);
				buildCrit(ca, PlaMspstransport);
				ca.addOrder(Order.asc("corpid"));
				ca.addOrder(Order.desc("month"));
//				if(log.isDebugEnabled()){
//					log.debug("################################查询条件："+ca);
//				}
				return ca.list();
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param PlaMspstransport
	 */
	private void buildCrit(Criteria crit, PlaMspstransport PlaMspstransport) {
		if (null != PlaMspstransport) {
			String statuss = PlaMspstransport.getStatuss();
			String expdateStart = PlaMspstransport.getMonthStart();
			String expdateEnd = PlaMspstransport.getMonthEnd();
			String corpid = PlaMspstransport.getCorpid();
			String corpids = PlaMspstransport.getCorpids();
			String plannos = PlaMspstransport.getPlannos();
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
