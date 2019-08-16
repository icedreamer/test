package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquRepYplan;

@Repository
public class EquRepYplanDao extends _GenericDao<EquRepYplan> {

	@Autowired
	DicMap dicMap;

	/**
	 * 根据企业代码和月份查询相应检修计划(大修，中修，段修，厂修)
	 * 
	 * @param equRepPlan
	 * @param pageCtr
	 * @return
	 */
	public List<Object[]> findEquRepYplan(final EquRepYplan equRepYplan) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepYplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("corpid"));
				projectionList.add(Projections.property("corpshortname"));
				projectionList.add(Projections.property("rtypeid"));
				projectionList.add(Projections.sum("amount"));
				projectionList.add(Projections.groupProperty("corpid"));
				projectionList.add(Projections.groupProperty("corpshortname"));
				projectionList.add(Projections.groupProperty("rtypeid"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepYplan, criteria);
				return criteria.list();
			}
		});
	}

	public List<Object[]> findCorps(final EquRepYplan equRepYplan) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepYplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.distinct(Projections.property("areaid")));
				projectionList.add(Projections.property("corpid"));
				projectionList.add(Projections.property("corpshortname"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepYplan, criteria);
				criteria.addOrder(Order.asc("areaid"));
				criteria.addOrder(Order.asc("corpshortname"));
				return criteria.list();
			}
		});
	}

	public void findYears(final EquRepYplan equRepYplan, final PageCtr<String> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepYplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.distinct(Projections.property("year")));
				criteria.setProjection(projectionList);
				buildCriteria(equRepYplan, criteria);
				criteria.addOrder(Order.asc("year"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	public int getYearsCount(final EquRepYplan equRepYplan) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepYplan.class);
				criteria.setProjection(Projections.countDistinct("year"));
				buildCriteria(equRepYplan, criteria);
				Object o = criteria.uniqueResult();
				if (null != o) {
					return Integer.parseInt(o.toString());
				}
				return 0;
			}
		});
	}

	private void buildCriteria(EquRepYplan equRepYplan, Criteria criteria) throws HibernateException, SQLException {
		String corpid = null;
		String corpids = null;
		String rtypeid = null;
		String year = null;
		String areaids = null;
		if (null != equRepYplan) {
			corpids = equRepYplan.getCorpids();
			rtypeid = equRepYplan.getRtypeid();
			corpid = equRepYplan.getCorpid();
			year = equRepYplan.getYear();
			areaids = equRepYplan.getAreaids();
		}

		if (null != corpid && !"".equals(corpid)) {
			criteria.add(Restrictions.eq("corpid", corpid));
		}
		if (null != areaids && !"".equals(areaids)) {
			criteria.add(Restrictions.in("areaid", areaids.split(",")));
		}
		if (null != corpids && !"".equals(corpids)) {
			criteria.add(Restrictions.in("corpid", corpids.split(",")));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != rtypeid && !"".equals(rtypeid)) {
			criteria.add(Restrictions.eq("rtypeid", rtypeid));
		}
		if (null != year && !"".equals(year)) {
			criteria.add(Restrictions.eq("year", year));
		}
	}

	public List<Object[]> findTotalAmountByRtypeid(final EquRepYplan equRepYplan) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepYplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("rtypeid"));
				projectionList.add(Projections.sum("amount"));
				projectionList.add(Projections.groupProperty("rtypeid"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepYplan, criteria);
				return criteria.list();
			}
		});
	}

	public List<Object[]> findYearsPlan(final EquRepYplan equRepYplan) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepYplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("corpid"));
				projectionList.add(Projections.property("corpshortname"));
				projectionList.add(Projections.property("rtypeid"));
				projectionList.add(Projections.property("year"));
				projectionList.add(Projections.sum("amount"));
				projectionList.add(Projections.groupProperty("rtypeid"));
				projectionList.add(Projections.groupProperty("year"));
				projectionList.add(Projections.groupProperty("corpid"));
				projectionList.add(Projections.groupProperty("corpshortname"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepYplan, criteria);
				return criteria.list();
			}
		});
	}
}
