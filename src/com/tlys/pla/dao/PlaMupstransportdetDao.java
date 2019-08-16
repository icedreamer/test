package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaMupstransportdet;

@Repository
public class PlaMupstransportdetDao extends _GenericDao<PlaMupstransportdet> {

	public void find(final PlaMupstransportdet plaMupstransportdet, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransportdet.class);
				buildCrit(ca, plaMupstransportdet);
				ca.addOrder(Order.asc("corpshortname"));
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMupstransportdetCount(final PlaMupstransportdet plaMupstransportdet) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransportdet.class);
				buildCrit(ca, plaMupstransportdet);

				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param plaMupstransportdet
	 */
	private void buildCrit(Criteria crit, PlaMupstransportdet plaMupstransportdet) {
		String planno = null;
		String corpid = null;
		String[] corpids = null;
		String[] categories = null;
		String[] secondkinds = null;
		// 收货人
		String receiverids = null;
		if (null != plaMupstransportdet) {
			planno = plaMupstransportdet.getPlanno();
			corpid = plaMupstransportdet.getCorpid();
			receiverids = plaMupstransportdet.getReceiverids();
			corpids = plaMupstransportdet.getCorpids();
			categories = plaMupstransportdet.getCategories();
			secondkinds = plaMupstransportdet.getSecondkinds();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("corpids : " + corpids);
		}
		if (null != planno && !"".equals(planno)) {
			crit.add(Restrictions.eq("planno", planno));
		}
		if (null != corpid && !"".equals(corpid)) {
			crit.add(Restrictions.eq("corpid", corpid));
		} else {
			crit.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
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

	public List<PlaMupstransportdet> find(final PlaMupstransportdet PlaMupstransportdet) {
		return (List<PlaMupstransportdet>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransportdet.class);
				buildCrit(ca, PlaMupstransportdet);
				return ca.list();
			}
		});
	}

	public List<PlaMupstransportdet> findPlaMupstransportdetByIds(final List<Long> planIds) {
		final String hql = "from PlaMupstransportdet where id in (:planIds) ";
		return (List<PlaMupstransportdet>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("planIds", planIds);
				return query.list();
			}
		});
	}
}
