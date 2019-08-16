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
import com.tlys.pla.model.PlaMupstransportdet2;

@Repository
public class PlaMupstransportdet2Dao extends _GenericDao<PlaMupstransportdet2> {

	public void find(final PlaMupstransportdet2 plaMupstransportdet2, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransportdet2.class);
				buildCrit(ca, plaMupstransportdet2);
				ca.addOrder(Order.asc("corpshortname"));
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMupstransportdet2Count(final PlaMupstransportdet2 plaMupstransportdet2) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransportdet2.class);
				buildCrit(ca, plaMupstransportdet2);

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
	 * @param plaMupstransportdet2
	 */
	private void buildCrit(Criteria crit, PlaMupstransportdet2 plaMupstransportdet2) {
		String planno = null;
		String corpid = null;
		String isa = null;
		String[] corpids = null;
		String[] categories = null;
		String[] secondkinds = null;
		// 收货人
		String receiverids = null;
		if (null != plaMupstransportdet2) {
			isa = plaMupstransportdet2.getIsactive();
			planno = plaMupstransportdet2.getPlanno();
			corpid = plaMupstransportdet2.getCorpid();
			receiverids = plaMupstransportdet2.getReceiverids();
			corpids = plaMupstransportdet2.getCorpids();
			categories = plaMupstransportdet2.getCategories();
			secondkinds = plaMupstransportdet2.getSecondkinds();
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

	public List<PlaMupstransportdet2> find(final PlaMupstransportdet2 PlaMupstransportdet2) {
		return (List<PlaMupstransportdet2>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransportdet2.class);
				buildCrit(ca, PlaMupstransportdet2);
				return ca.list();
			}
		});
	}

	public List<PlaMupstransportdet2> findPlaMupstransportdet2ByIds(final List<Long> planIds) {
		final String hql = "from PlaMupstransportdet2 where id in (:planIds) ";
		return (List<PlaMupstransportdet2>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("planIds", planIds);
				return query.list();
			}
		});
	}
}
