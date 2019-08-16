package com.tlys.spe.dao;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpeCertishipping;

@Repository
public class SpeCertishippingDao extends _GenericDao<SpeCertishipping> {

	public void findSpeCertishipping(final SpeCertishipping speCertishipping, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SpeCertishipping.class);
				buildCrit(ca, speCertishipping);
				ca.addOrder(Order.desc("createdtime"));
				pageCtr.buildList(ca);

				return null;
			}
		});
	}

	public int getSpeCertishippingCount(final SpeCertishipping speCertishipping) {

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(SpeCertishipping.class);
				buildCrit(ca, speCertishipping);

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
	 * @param speCertishipping
	 */
	private void buildCrit(Criteria crit, SpeCertishipping speCertishipping) {
		String[] corpids = null;
		if (null != speCertishipping) {
			corpids = speCertishipping.getCorpids();
			String expdateStart = speCertishipping.getExpiredateStart();
			String expdateEnd = speCertishipping.getExpiredateEnd();
			String name = speCertishipping.getName();

			if (null != name && !"".equals(name)) {
				crit.add(Restrictions.like("name", "%" + name + "%"));
			}

			if (null != expdateStart && !"".equals(expdateStart)) {
				crit.add(Restrictions.sqlRestriction("to_char(expiredate,'yyyy-MM-dd') >= ? ",
						expdateStart, Hibernate.STRING));
			}

			if (null != expdateEnd && !expdateEnd.equals("")) {
				crit.add(Restrictions.sqlRestriction("to_char(expiredate,'yyyy-MM-dd') <= ? ",
						expdateEnd, Hibernate.STRING));
			}
		}
		if (corpids != null && corpids.length > 0) {
			crit.add(Restrictions.in("corpid", corpids));
		} else {
			crit.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}

	}

	public Long getNextSpeCertishippingSeqId() {
		final String sql = "select SEQ_TB_ZBC_SPE_CERTISHIPPING.nextval from dual";
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				Object o = query.uniqueResult();
				return o == null || o.toString().equals("") ? 0l : Long.valueOf(o.toString());
			}
		});
	}
}
