package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysSession;

@Repository
public class SysSessionDao extends _GenericDao<SysSession> {
	public SysSession getSysSession(String sessionID) {
		String hql = "from SysSession where sessionid = ? and sessionstatus = 'A'";
		List<SysSession> sessionList = getHibernateTemplate().find(hql, sessionID);
		if (sessionList != null && !sessionList.isEmpty()) {
			return sessionList.get(0);
		}
		return null;
	}

	public void findSysSession(final SysSession sysSession, final PageCtr<SysSession> pageCtr) {

		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysSession.class);
				buildCriteria(sysSession, criteria);
				criteria.addOrder(Order.desc("starttime"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	private void buildCriteria(SysSession sysSession, Criteria criteria) {
		String startTime = null;
		String endTime = null;
		if (null != sysSession) {
			startTime = sysSession.getStartDateStr();
			endTime = sysSession.getEndDateStr();
		}
		if (null != startTime && !startTime.equals("")) {
			// criteria.add(Restrictions.sqlRestriction("to_char(starttime,'yyyy-MM-dd HH24:mm:ss') >= ? ",
			// startTime,
			// Hibernate.STRING));
			criteria.add(Restrictions.sqlRestriction("starttime >= to_date('" + startTime
					+ "','yyyy-mm-dd HH24:mi:ss')"));
		}
		if (null != endTime && !endTime.equals("")) {
			// criteria.add(Restrictions.sqlRestriction("to_char(starttime,'yyyy-MM-dd HH24:mm:ss') < ? ",
			// endTime,
			// Hibernate.STRING));
			criteria.add(Restrictions.sqlRestriction("starttime < to_date('" + endTime + "','yyyy-mm-dd HH24:mi:ss')"));
			// criteria.add(Restrictions.lt("starttime", "to_date('" + endTime +
			// "','yyyy-mm-dd HH24:mi:ss')"));
		}
	}

	public int getSysSessionCount(final SysSession sysSession) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysSession.class);
				buildCriteria(sysSession, criteria);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	public List<SysSession> findSysSession(final String startTime, final String endTime) {
		return (List<SysSession>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(SysSession.class);
				if (null != startTime && !startTime.equals("")) {
					// ca.add(Restrictions.sqlRestriction("to_char(starttime,'yyyy-MM-dd HH24:mm:ss') >= ? ",
					// startTime,
					// Hibernate.STRING));
					// ca.add(Restrictions.ge("starttime", "to_date('" +
					// startTime + "','yyyy-mm-dd HH24:mi:ss')"));
					ca.add(Restrictions.sqlRestriction("starttime >= to_date('" + startTime
							+ "','yyyy-mm-dd HH24:mi:ss')"));
				}
				if (null != endTime && !endTime.equals("")) {
					// ca.add(Restrictions.sqlRestriction("to_char(starttime,'yyyy-MM-dd HH24:mm:ss') < ? ",
					// endTime,
					// Hibernate.STRING));
					// ca.add(Restrictions.lt("starttime", "to_date('" + endTime
					// + "','yyyy-mm-dd HH24:mi:ss')"));
					ca.add(Restrictions
							.sqlRestriction("starttime < to_date('" + endTime + "','yyyy-mm-dd HH24:mi:ss')"));
				}
				ca.addOrder(Order.desc("starttime"));
				List<SysSession> list = ca.list();
				return list;
			}
		});

	}

	/**
	 * 根据时间段删除访问日志
	 * 
	 * @param sysSession
	 */
	public void deleteAccessLog(final SysSession sysSession) {
		final String startTime = sysSession.getStartDateStr();
		final String endTime = sysSession.getEndDateStr();
		final StringBuffer buffer = new StringBuffer();
		buffer.append("delete from SysSession where 1=1 ");
		if (null != startTime && !"".equals(startTime)) {
			// buffer.append("and to_char(starttime,'yyyy-MM-dd HH24:mm:ss') >= '"
			// + startTime + "'");
			buffer.append(" and starttime >= to_date('" + startTime + "','yyyy-mm-dd HH24:mi:ss')");
		}
		if (null != endTime && !"".equals(endTime)) {
			// buffer.append(" and to_char(starttime,'yyyy-MM-dd HH24:mm:ss') < '"
			// + endTime + "' ");
			buffer.append(" and starttime < to_date('" + endTime + "','yyyy-mm-dd HH24:mi:ss')");
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(buffer.toString());
				return query.executeUpdate();
			}
		});
	}

	public List<SysSession> findSysSession(final List<String> sessionIdList) {
		if (null == sessionIdList || sessionIdList.isEmpty()) {
			return null;
		}
		final String hql = "from SysSession where sessionid in (:sessionIdList)";
		return (List<SysSession>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("sessionIdList", sessionIdList);
				return query.list();
			}
		});
	}
	
	public Integer getLoginCount(String userid,Date stTime) {
		DetachedCriteria decrit = DetachedCriteria.forClass(SysSession.class);
		
		decrit.add(Restrictions.eq("userid", userid));
		decrit.add(Restrictions.gt("starttime", stTime));
		decrit.setProjection(Projections.rowCount());
		//decrit.add(Restrictions.in("status", statuss.split(",")));
		List cntList = getHibernateTemplate().findByCriteria(decrit);
		Integer cnt=0;
		if(cntList.size()>0){
			 cnt = (Integer)cntList.get(0);
		}
		return cnt;
	}
}
