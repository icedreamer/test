package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.tlys.pla.model.PlaDoprtrain;

@Repository
public class PlaDoprtrainDao extends _GenericDao<PlaDoprtrain> {

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param ca
	 * @param PlaDoprtrain
	 */
	private void buildCrit(Criteria ca, PlaDoprtrain plaDoprtrain) {
		String acceptcarno = null;
		String acceptcarnos = null;
		if (null != plaDoprtrain) {
			acceptcarno = plaDoprtrain.getAcceptcarno();
			acceptcarnos = plaDoprtrain.getAcceptcarnos();
		}
		if (null != acceptcarno && !"".equals(acceptcarno)) {
			ca.add(Restrictions.eq("acceptcarno", acceptcarno));
		}
		if (null != acceptcarnos && acceptcarnos.length() > 0) {
			ca.add(Restrictions.in("acceptcarno", acceptcarnos.split(",")));
		}
	}

	public void find(final PlaDoprtrain plaDoprtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaDoprtrain.class);
				buildCrit(ca, plaDoprtrain);
				ca.addOrder(Order.desc("requestdate"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaDoprtrainCount(final PlaDoprtrain plaDoprtrain) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaDoprtrain.class);
						buildCrit(ca, plaDoprtrain);
						ca.setProjection(Projections.rowCount());
						Object o = ca.uniqueResult();
						return Integer.parseInt(o.toString());
					}
				});
	}
	
	public List<PlaDoprtrain> find(final PlaDoprtrain PlaDoprtrain) {
		return (List<PlaDoprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaDoprtrain> plaDoprtrains = new ArrayList<PlaDoprtrain>();
						Criteria ca = session
								.createCriteria(PlaDoprtrain.class);
						buildCrit(ca, PlaDoprtrain);
						ca.addOrder(Order.desc("requestdate"));// 排序
						return ca.list();
					}
				});
	}
}
