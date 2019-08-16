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
import com.tlys.pla.model.PlaDsprtrain;

@Repository
public class PlaDsprtrainDao extends _GenericDao<PlaDsprtrain> {

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param ca
	 * @param PlaDsprtrain
	 */
	private void buildCrit(Criteria ca, PlaDsprtrain plaDsprtrain) {
		String acceptcarno = null;
		String acceptcarnos = null;
		if (null != plaDsprtrain) {
			acceptcarno = plaDsprtrain.getAcceptcarno();
			acceptcarnos = plaDsprtrain.getAcceptcarnos();
		}
		if (null != acceptcarno && !"".equals(acceptcarno)) {
			ca.add(Restrictions.eq("acceptcarno", acceptcarno));
		}
		if (null != acceptcarnos && acceptcarnos.length() > 0) {
			ca.add(Restrictions.in("acceptcarno", acceptcarnos.split(",")));
		}
	}

	public void find(final PlaDsprtrain plaDsprtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaDsprtrain.class);
				buildCrit(ca, plaDsprtrain);
				ca.addOrder(Order.desc("requestdate"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaDsprtrainCount(final PlaDsprtrain plaDsprtrain) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaDsprtrain.class);
						buildCrit(ca, plaDsprtrain);
						ca.setProjection(Projections.rowCount());
						Object o = ca.uniqueResult();
						return Integer.parseInt(o.toString());
					}
				});
	}
	
	public List<PlaDsprtrain> find(final PlaDsprtrain PlaDsprtrain) {
		return (List<PlaDsprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaDsprtrain> plaDsprtrains = new ArrayList<PlaDsprtrain>();
						Criteria ca = session
								.createCriteria(PlaDsprtrain.class);
						buildCrit(ca, PlaDsprtrain);
						ca.addOrder(Order.desc("requestdate"));// 排序
						return ca.list();
					}
				});
	}
}
