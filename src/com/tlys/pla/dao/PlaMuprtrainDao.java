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
import com.tlys.pla.model.PlaMuprtrain;

@Repository
public class PlaMuprtrainDao extends _GenericDao<PlaMuprtrain> {

	public List<PlaMuprtrain> find(final PlaMuprtrain PlaMuprtrain) {
		return (List<PlaMuprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaMuprtrain> plaMuprtrains = new ArrayList<PlaMuprtrain>();
						Criteria ca = session
								.createCriteria(PlaMuprtrain.class);
						buildCrit(ca, PlaMuprtrain);
						
						ca.addOrder(Order.asc("areaid"));// 排序
						ca.addOrder(Order.asc("corpshortname"));// 排序
						//ca.addOrder(Order.desc("month"));// 排序
						ca.addOrder(Order.desc("checktime"));// 排序

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
	public List<PlaMuprtrain> find(final String hql) {
		List<PlaMuprtrain> Objects = (List<PlaMuprtrain>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List doInHibernate(Session session)
							throws HibernateException {
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
	 * @param plaMuprtrain
	 */
	private void buildCrit(Criteria ca, PlaMuprtrain plaMuprtrain) {
		if(null == plaMuprtrain) return;
		sp2null(plaMuprtrain);
		
		ca.add(Example.create(plaMuprtrain).enableLike(MatchMode.ANYWHERE));
		
		String checkstatusids = plaMuprtrain.getCheckstatusids();
		String corpids = plaMuprtrain.getCorpids();
		
		if (null != checkstatusids && checkstatusids.length() > 0) {
			ca.add(Restrictions.in("checkstatus", checkstatusids.split(",")));
		}
		if (null != corpids && !"".equals(corpids)) {
			ca.add(Restrictions.in("corpid", corpids.split(",")));
		}
	}
	
	
	
	
	public void find(final PlaMuprtrain plaMuprtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMuprtrain.class);
				buildCrit(ca, plaMuprtrain);
				ca.addOrder(Order.desc("month"));// 排序
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMuprtrainCount(final PlaMuprtrain plaMuprtrain) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaMuprtrain.class);
						buildCrit(ca, plaMuprtrain);
						ca.setProjection(Projections.rowCount());
						Object o = ca.uniqueResult();
						return Integer.parseInt(o.toString());
					}
				});
	}
	
	
	/**
	 * 将查询字段的值从空值变为null,目前acceptcarno这个值可能有null，先加上
	 * @param plaMsprtrain
	 */
	private void sp2null(PlaMuprtrain pmst){
		if(null!=pmst.getAcceptcarno() && "".equals(pmst.getAcceptcarno())){
			pmst.setAcceptcarno(null);
		}

		
	}
}
