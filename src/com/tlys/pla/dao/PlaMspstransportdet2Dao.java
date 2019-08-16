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
import com.tlys.pla.model.PlaMspstransportdet2;

@Repository
public class PlaMspstransportdet2Dao extends _GenericDao<PlaMspstransportdet2> {

	public void find(final PlaMspstransportdet2 plaMspstransportdet2, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransportdet2.class);
				buildCrit(ca, plaMspstransportdet2);
				ca.addOrder(Order.asc("coustomershortname"));
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMspstransportdet2Count(final PlaMspstransportdet2 plaMspstransportdet2) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransportdet2.class);
				buildCrit(ca, plaMspstransportdet2);

				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	public List<PlaMspstransportdet2> find(final PlaMspstransportdet2 plaMspstransportdet2) {
		return (List<PlaMspstransportdet2>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransportdet2.class);
				buildCrit(ca, plaMspstransportdet2);
				return ca.list();
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param PlaMspstransportdet2
	 */
	private void buildCrit(Criteria crit, PlaMspstransportdet2 plaMspstransportdet2) {
		String isa = null;
		String planno = null;
		String corpid = null;
		String[] corpids = null;
		String[] categories = null;
		String[] secondkinds = null;
		// 收货人
		String receiverids = null;
		if (null != plaMspstransportdet2) {
			isa = plaMspstransportdet2.getIsactive();
			planno = plaMspstransportdet2.getPlanno();
			corpid = plaMspstransportdet2.getCorpid();
			receiverids = plaMspstransportdet2.getReceiverids();
			corpids = plaMspstransportdet2.getCorpids();
			categories = plaMspstransportdet2.getCategories();
			secondkinds = plaMspstransportdet2.getSecondkinds();
		}
		if (null != isa && !"".equals(isa)) {
			crit.add(Restrictions.eq("isactive", isa));
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

	public List<PlaMspstransportdet2> findPlaMspstransportdet2ByIds(final List<Long> planIds) {
		final String hql = "from PlaMspstransportdet2 where id in (:planIds) ";
		return (List<PlaMspstransportdet2>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("planIds", planIds);
				return query.list();
			}
		});
	}
}
