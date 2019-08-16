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
import com.tlys.pla.model.PlaDuprtrain;

@Repository
public class PlaDuprtrainDao extends _GenericDao<PlaDuprtrain> {

	public List<PlaDuprtrain> find(final PlaDuprtrain PlaDuprtrain) {
		return (List<PlaDuprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaDuprtrain> plaDuprtrains = new ArrayList<PlaDuprtrain>();
						Criteria ca = session
								.createCriteria(PlaDuprtrain.class);
						buildCrit(ca, PlaDuprtrain);
						ca.addOrder(Order.desc("requestdate"));// 排序
						return ca.list();
					}
				});
	}

	public List<PlaDuprtrain> find(final String acceptcarno) {
		return (List<PlaDuprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaDuprtrain> plaDuprtrains = new ArrayList<PlaDuprtrain>();
						Criteria ca = session
								.createCriteria(PlaDuprtrain.class);
						ca.add(Restrictions.eq("acceptcarno", acceptcarno));
						ca.addOrder(Order.desc("requestdate"));// 排序
						return ca.list();
					}
				});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param ca
	 * @param PlaDuprtrain
	 */
	private void buildCrit(Criteria ca, PlaDuprtrain PlaDuprtrain) {
		String acceptcarno = null;
		String acceptcarnos = null;
		if (null != PlaDuprtrain) {
			acceptcarno = PlaDuprtrain.getAcceptcarno();
			acceptcarnos = PlaDuprtrain.getAcceptcarnos();
		}
		if (null != acceptcarno && !"".equals(acceptcarno)) {
			ca.add(Restrictions.eq("acceptcarno", acceptcarno));
		}
		if (null != acceptcarnos && acceptcarnos.length() > 0) {
			ca.add(Restrictions.in("acceptcarno", acceptcarnos.split(",")));
		}
	}

	public void find(final PlaDuprtrain plaDuprtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaDuprtrain.class);
				buildCrit(ca, plaDuprtrain);
				ca.addOrder(Order.desc("requestdate"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaDuprtrainCount(final PlaDuprtrain plaDuprtrain) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaDuprtrain.class);
						buildCrit(ca, plaDuprtrain);
						ca.setProjection(Projections.rowCount());
						Object o = ca.uniqueResult();
						return Integer.parseInt(o.toString());
					}
				});
	}
}
