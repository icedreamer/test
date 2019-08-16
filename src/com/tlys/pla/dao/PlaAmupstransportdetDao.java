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
import com.tlys.pla.model.PlaAmupstransportdet;

@Repository
public class PlaAmupstransportdetDao extends _GenericDao<PlaAmupstransportdet> {
	public void find(final PlaAmupstransportdet plaAmupstransportdet, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaAmupstransportdet.class);
				buildCrit(ca, plaAmupstransportdet);
				ca.addOrder(Order.asc("corpshortname"));
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaAmupstransportdetCount(final PlaAmupstransportdet plaAmupstransportdet) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaAmupstransportdet.class);
				buildCrit(ca, plaAmupstransportdet);

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
	 * @param PlaAmupstransportdet
	 */
	private void buildCrit(Criteria crit, PlaAmupstransportdet plaAmupstransportdet) {
		String planno = null;
		String corpid = null;
		// 收货人
		String receiverids = null;
		String[] corpids = null;
		String[] categories = null;
		String[] secondkinds = null;
		if (null != plaAmupstransportdet) {
			planno = plaAmupstransportdet.getPlanno();
			corpid = plaAmupstransportdet.getCorpid();
			receiverids = plaAmupstransportdet.getReceiverids();
			corpids = plaAmupstransportdet.getCorpids();
			categories = plaAmupstransportdet.getCategories();
			secondkinds = plaAmupstransportdet.getSecondkinds();
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

	public List<PlaAmupstransportdet> find(final PlaAmupstransportdet PlaAmupstransportdet) {
		return (List<PlaAmupstransportdet>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaAmupstransportdet.class);
				buildCrit(ca, PlaAmupstransportdet);
				return ca.list();
			}
		});
	}

}
