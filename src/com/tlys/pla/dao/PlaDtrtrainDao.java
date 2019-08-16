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
import com.tlys.pla.model.PlaDtrtrain;

@Repository
public class PlaDtrtrainDao extends _GenericDao<PlaDtrtrain> {

	public List<PlaDtrtrain> find(final PlaDtrtrain plaDtrtrain) {
		return (List<PlaDtrtrain>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List<PlaDtrtrain> plaDtrtrains = new ArrayList<PlaDtrtrain>();
				Criteria ca = session.createCriteria(PlaDtrtrain.class);
				buildCrit(ca, plaDtrtrain);
				// ca.addOrder(Order.desc("month"));// 排序
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				ca.addOrder(Order.desc("loadtdate"));// 排序
				return ca.list();
			}
		});
	}

	/**
	 * 查找指定hql的实体集合
	 * 
	 * @param hql
	 * @return List
	 */
	public List<PlaDtrtrain> find(final String hql) {
		List<PlaDtrtrain> Objects = (List<PlaDtrtrain>) getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException {
				List dacs = session.createQuery(hql).list();
				return dacs;
			}
		});
		return Objects;
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param ca
	 * @param plaDtrtrain
	 */
	private void buildCrit(Criteria ca, PlaDtrtrain plaDtrtrain) {
		if (null == plaDtrtrain)
			return;
		ca.add(Example.create(plaDtrtrain).enableLike(MatchMode.ANYWHERE));
		String corpids = plaDtrtrain.getCorpids();
		String exPrkinds = plaDtrtrain.getExPrkind();
		if (null != corpids && !"".equals(corpids)) {
			ca.add(Restrictions.in("corpid", corpids.split(",")));
		}
		if (null != exPrkinds && !"".equals(exPrkinds)) {
			ca.add(Restrictions.not(Restrictions.in("productkind", exPrkinds.split(","))));
		}
		
	}

	public void find(final PlaDtrtrain plaDtrtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaDtrtrain.class);
				buildCrit(ca, plaDtrtrain);
				ca.addOrder(Order.desc("month"));// 排序
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaDtrtrainCount(final PlaDtrtrain plaDtrtrain) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaDtrtrain.class);
				buildCrit(ca, plaDtrtrain);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}
}
