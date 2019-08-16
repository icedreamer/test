package com.tlys.spe.dao;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpeLaw;

@Repository
public class SpeLawDao extends _GenericDao<SpeLaw> {

	public void findSpeLaw(final SpeLaw speLaw, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SpeLaw.class);
				buildCrit(ca, speLaw);
				ca.addOrder(Order.desc("createdtime"));
				pageCtr.buildList(ca);

				return null;
			}
		});
	}

	public int getSpeLawCount(final SpeLaw speLaw) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Criteria ca = s.createCriteria(SpeLaw.class);
						buildCrit(ca, speLaw);
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
	 * @param speLaw
	 */
	private void buildCrit(Criteria crit, SpeLaw speLaw) {
		if (null != speLaw) {
			String expdateStart = speLaw.getExpiredateStart();
			String expdateEnd = speLaw.getExpiredateEnd();
			crit.add(Example.create(speLaw).enableLike(MatchMode.ANYWHERE));
			
			if (null != expdateStart && !"".equals(expdateStart)) {
				crit.add(Restrictions.ge("issuedate", expdateStart));
			}
			if (null != expdateEnd && !expdateEnd.equals("")) {
				crit.add(Restrictions.le("issuedate", expdateEnd));
			}
		}
	}
}
