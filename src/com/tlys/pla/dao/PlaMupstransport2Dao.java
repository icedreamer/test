package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaMupstransport2;

@Repository
public class PlaMupstransport2Dao extends _GenericDao<PlaMupstransport2> {

	public void find(final PlaMupstransport2 plaMupstransport2,
			final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransport2.class);
				buildCrit(ca, plaMupstransport2);
				ca.addOrder(Order.desc("month"));// 排序
				ca.addOrder(Order.asc("areaid"));
				pageCtr.buildList(ca);
				return null;
			}
		});
	}

	public int getCount(final PlaMupstransport2 plaMupstransport2) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaMupstransport2.class);
						buildCrit(ca, plaMupstransport2);
						
						//ca.setProjection(Projections.rowCount());//当采用分组时，这里有问题，留待后面探讨
						//Object o = ca.uniqueResult();
						//return Integer.parseInt(o.toString());
						List planList = ca.list();
						int cnt = 0;
						if(null != planList){
							cnt = planList.size();
						}
						ca.list().size();
						return cnt;
					}
				});
	}

	private void buildCrit(Criteria crit, PlaMupstransport2 plaMupstransport2) {
		if (null == plaMupstransport2)
			return;
		crit.add(Example.create(plaMupstransport2).enableLike(
				MatchMode.ANYWHERE));

		String areaids = plaMupstransport2.getAreaids();
		String statuss = plaMupstransport2.getStatuss();
		String expdateStart = plaMupstransport2.getMonthStart();
		String expdateEnd = plaMupstransport2.getMonthEnd();
		String plannos = plaMupstransport2.getPlannos();
		
		//add by fengym
		ProjectionList projectionList = Projections.projectionList();
		
		//*
		projectionList.add(Projections.property("areaid"));
		projectionList.add(Projections.property("month"));
		
		crit.setProjection(Projections.distinct(projectionList));
		//*/
		
		//采用下述方法和上面注掉的方法效果是一样的
		/*
		projectionList.add(Projections.groupProperty("areaid")); 
		projectionList.add(Projections.groupProperty("month"));
		
		crit.setProjection(projectionList);
		*/
		//end of add 
		
		
		//System.out.println("PlaMupstransport2Dao.buildCrit->statuss=="+statuss);
		if (null != statuss && statuss.length() > 0) {
			crit.add(Restrictions.in("status", statuss.split(",")));
		}
		if (null != areaids && areaids.length() > 0) {
			crit.add(Restrictions.in("areaid", areaids.split(",")));
		}
		if (null != expdateStart && !"".equals(expdateStart)) {
			expdateStart = expdateStart.replace("-", "");
			crit.add(Restrictions.ge("month", expdateStart));
		}
		if (null != expdateEnd && !expdateEnd.equals("")) {
			expdateEnd = expdateEnd.replace("-", "");
			crit.add(Restrictions.le("month", expdateEnd));
		}
		if (null != plannos && plannos.length() > 0) {
			crit.add(Restrictions.in("planno", plannos.split(",")));
		}
		
		
		
	}

	public List find(final PlaMupstransport2 PlaMupstransport2) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMupstransport2.class);
				buildCrit(ca, PlaMupstransport2);
				return ca.list();
			}
		});
	}

	/**
	 * 查询调整计划
	 * 
	 * @param PlaMupstransport2
	 * @return
	 */
	public List<PlaMupstransport2> findByAdjust(
			final PlaMupstransport2 plaMupstransport2) {
		return (List<PlaMupstransport2>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaMupstransport2.class);
						// 包含企业ID、月份两个查询条件
						ca.add(Example.create(plaMupstransport2).enableLike(
								MatchMode.ANYWHERE));
						
						String statuss = plaMupstransport2.getStatuss();
						if(null!= statuss && !"".equals(statuss)){
							ca.add(Restrictions.in("status", statuss.split(",")));
						}
						// 按调整次数倒序排列显示
						ca.addOrder(Order.desc("adjustnumber"));
						
						
						return ca.list();
					}
				});
	}
	
	
	/**
	 * 检查本计划之前是否有指定状态的计划
	 * @param plano
	 * @param statuss：指定的状态，逗号分开的列表
	 * @return
	 */
	public boolean hasPrePlaStatus(String plano,String statuss){
		boolean reFlag = false;
		String pnSch = plano.substring(0,plano.length()-3);
		//System.out.println("PlaMupstransport2Dao.getMaxPlano->pnSch=="+pnSch);
		DetachedCriteria decrit = DetachedCriteria.forClass(PlaMupstransport2.class);
		decrit.add(Restrictions.like("planno", pnSch, MatchMode.START));
		decrit.add(Restrictions.lt("planno", plano));
		decrit.add(Restrictions.in("status", statuss.split(",")));
		
		//decrit.setProjection(Projections.max("planno"));
		
		List mplist = getHibernateTemplate().findByCriteria(decrit);
		if(null!=mplist && mplist.size()>0){
			reFlag = true;
		}
		
		return reFlag;
	}
	
	/**
	 * 根据当前所给的计划编号，查找当前计划中最大调整次数的计划编号
	 * @param plano
	 * @return
	 */
	public String getMaxPlano(String plano){
		String rePlano = "";
		String pnSch = plano.substring(0,plano.length()-3);
		//System.out.println("PlaMupstransport2Dao.getMaxPlano->pnSch=="+pnSch);
		DetachedCriteria decrit = DetachedCriteria.forClass(PlaMupstransport2.class);
		decrit.add(Restrictions.like("planno", pnSch, MatchMode.START));
		decrit.setProjection(Projections.max("planno"));
		
		List mplist = getHibernateTemplate().findByCriteria(decrit);
		
		//System.out.println("PlaMupstransport2Dao.getMaxPlano->mplist.size()=="+mplist.size());
		
		Object ro = mplist.size()>0?mplist.get(0):null;
		
		//Object[] roArr = (Object[])ro;
		
		//System.out.println("PlaMupstransport2Dao.getMaxPlano->ro[]=="+ro);
		
		return ro.toString();
	}
	
}
