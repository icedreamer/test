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
import com.tlys.pla.model.PlaMoprtrain;

@Repository
public class PlaMoprtrainDao extends _GenericDao<PlaMoprtrain> {

	public void find(final PlaMoprtrain plaMoprtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMoprtrain.class);
				buildCrit(ca, plaMoprtrain);
				// ca.addOrder(Order.desc("month"));// 排序
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				ca.addOrder(Order.desc("checktime"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMoprtrainCount(final PlaMoprtrain plaMoprtrain) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMoprtrain.class);
				buildCrit(ca, plaMoprtrain);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}


	public List<PlaMoprtrain> find(final PlaMoprtrain plaMoprtrain) {
		return (List<PlaMoprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaMoprtrain> plaMoprtrains = new ArrayList<PlaMoprtrain>();
						Criteria ca = session
								.createCriteria(PlaMoprtrain.class);
						buildCrit(ca, plaMoprtrain);
						return ca.list();
					}
				});
	}

	private void buildCrit(Criteria ca, PlaMoprtrain plaMoprtrain) {
		if (null == plaMoprtrain)
			return;
		sp2null(plaMoprtrain);

		ca.add(Example.create(plaMoprtrain).enableLike(MatchMode.ANYWHERE));

		String checkstatusids = plaMoprtrain.getCheckstatusids();
		String corpids = plaMoprtrain.getCorpids();

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
	 * @param plaMoprtrain
	 */
	private void sp2null(PlaMoprtrain pmst) {
		if (null != pmst.getAcceptcarno() && "".equals(pmst.getAcceptcarno())) {
			pmst.setAcceptcarno(null);
		}

	}
}
