package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaAmspstransportdet;

@Repository
public class PlaAmspstransportdetDao extends _GenericDao<PlaAmspstransportdet> {

	public void find(final PlaAmspstransportdet plaAmspstransportdet,
			final PageCtr<PlaAmspstransportdet> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session
						.createCriteria(PlaAmspstransportdet.class);
				buildCrit(ca, plaAmspstransportdet);
				ca.addOrder(Order.asc("corpid"));
				if (log.isDebugEnabled()) {
					log.debug("################################查询条件CA：" + ca);
				}
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaAmspstransportdetCount(
			final PlaAmspstransportdet plaAmspstransportdet) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaAmspstransportdet.class);
						buildCrit(ca, plaAmspstransportdet);

						ca.setProjection(Projections.rowCount());
						Object o = ca.uniqueResult();
						return Integer.parseInt(o.toString());
					}
				});
	}

	public List<PlaAmspstransportdet> find(
			final PlaAmspstransportdet plaAmspstransportdet) {
		return (List<PlaAmspstransportdet>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaAmspstransportdet.class);
						buildCrit(ca, plaAmspstransportdet);
						return ca.list();
					}
				});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param PlaAmspstransportdet
	 */
	private void buildCrit(Criteria crit,
			PlaAmspstransportdet plaAmspstransportdet) {
		String planno = null;
		String corpid = null;
		String[] corpids = null;
		String[] categories = null;
		String[] secondkinds = null;
		// 收货人
		String receiverids = null;
		if (null != plaAmspstransportdet) {
			planno = plaAmspstransportdet.getPlanno();
			corpid = plaAmspstransportdet.getCorpid();
			receiverids = plaAmspstransportdet.getReceiverids();
			corpids = plaAmspstransportdet.getCorpids();
			categories = plaAmspstransportdet.getCategories();
			secondkinds = plaAmspstransportdet.getSecondkinds();
		}
		if (null != planno && !"".equals(planno)) {
			crit.add(Restrictions.eq("planno", planno));
		}
		if (null != corpid && !"".equals(corpid)) {
			crit.add(Restrictions.eq("corpid", corpid));
		} 
		if (null != corpids && corpids.length > 0) {
			crit.add(Restrictions.in("corpid", corpids));
		}
		if (null != categories && categories.length > 0) {
			crit.add(Restrictions.in("productcategoryid", categories));
		}
		if (null != secondkinds && secondkinds.length > 0) {
			crit.add(Restrictions.in("productsecondid", secondkinds));
		}
		if (null != receiverids && receiverids.length() > 0) {
			crit.add(Restrictions.in("receiverid", receiverids.split(",")));
		}
	}

}
