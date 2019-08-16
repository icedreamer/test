package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.tlys.pla.model.PlaMsprtrain;

@Repository
public class PlaMsprtrainDao extends _GenericDao<PlaMsprtrain> {

	public void find(final PlaMsprtrain plaMsprtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMsprtrain.class);
				buildCrit(ca, plaMsprtrain);
				// ca.addOrder(Order.desc("month"));// 排序
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				ca.addOrder(Order.desc("checktime"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMsprtrainCount(final PlaMsprtrain plaMsprtrain) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMsprtrain.class);
				buildCrit(ca, plaMsprtrain);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}


	public List<PlaMsprtrain> find(final PlaMsprtrain plaMsprtrain) {
		return (List<PlaMsprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaMsprtrain> plaMsprtrains = new ArrayList<PlaMsprtrain>();
						Criteria ca = session
								.createCriteria(PlaMsprtrain.class);
						buildCrit(ca, plaMsprtrain);
						return ca.list();
					}
				});
	}

	private void buildCrit(Criteria ca, PlaMsprtrain plaMsprtrain) {
		if (null == plaMsprtrain)
			return;
		sp2null(plaMsprtrain);

		ca.add(Example.create(plaMsprtrain).enableLike(MatchMode.ANYWHERE));

		String checkstatusids = plaMsprtrain.getCheckstatusids();
		String corpids = plaMsprtrain.getCorpids();

		if (null != checkstatusids && checkstatusids.length() > 0) {
			String[] csArr = checkstatusids.split(",");
			if (csArr.length > 1) {
				ca.add(Restrictions.in("checkstatus", checkstatusids.split(",")));
			} else {
				ca.add(Restrictions.eq("checkstatus", checkstatusids));
			}
		}
		if (null != corpids && !"".equals(corpids)) {
			ca.add(Restrictions.in("corpid", corpids.split(",")));
		}
	}

	/**
	 * 将查询字段的值从空值变为null,目前acceptcarno这个值可能有null，先加上
	 * 
	 * @param plaMsprtrain
	 */
	private void sp2null(PlaMsprtrain pmst) {
		if (null != pmst.getAcceptcarno() && "".equals(pmst.getAcceptcarno())) {
			pmst.setAcceptcarno(null);
		}

	}
}
