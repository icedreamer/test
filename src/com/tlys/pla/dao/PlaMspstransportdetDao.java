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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaMspstransportdet;

@Repository
public class PlaMspstransportdetDao extends _GenericDao<PlaMspstransportdet> {

	public void find(final PlaMspstransportdet plaMspstransportdet, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransportdet.class);
				buildCrit(ca, plaMspstransportdet);
				ca.addOrder(Order.asc("coustomershortname"));
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMspstransportdetCount(final PlaMspstransportdet plaMspstransportdet) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransportdet.class);
				buildCrit(ca, plaMspstransportdet);

				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	public List<PlaMspstransportdet> find(final PlaMspstransportdet plaMspstransportdet) {
		return (List<PlaMspstransportdet>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransportdet.class);
				buildCrit(ca, plaMspstransportdet);
				return ca.list();
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param PlaMspstransportdet
	 */
	private void buildCrit(Criteria crit, PlaMspstransportdet plaMspstransportdet) {
		String planno = null;
		String corpid = null;
		String[] corpids = null;
		String[] categories = null;
		String[] secondkinds = null;
		// 收货人
		String receiverids = null;
		if (null != plaMspstransportdet) {
			planno = plaMspstransportdet.getPlanno();
			corpid = plaMspstransportdet.getCorpid();
			receiverids = plaMspstransportdet.getReceiverids();
			corpids = plaMspstransportdet.getCorpids();
			categories = plaMspstransportdet.getCategories();
			secondkinds = plaMspstransportdet.getSecondkinds();
		}
		if (null != planno && !"".equals(planno)) {
			crit.add(Restrictions.eq("planno", planno));
		}
		if (null != corpid && !"".equals(corpid)) {
			crit.add(Restrictions.eq("corpid", corpid));
		} else {
			// crit.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
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

	public List<PlaMspstransportdet> findPlaMspstransportdetByIds(final List<Long> planIds) {
		final String hql = "from PlaMspstransportdet where id in (:planIds) ";
		return (List<PlaMspstransportdet>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("planIds", planIds);
				return query.list();
			}
		});
	}
}
