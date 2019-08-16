package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysBkerrorlog;

@Repository
public class SysBkerrorlogDao extends _GenericDao<SysBkerrorlog> {
	/**
	 * 获取错误日志列表
	 * 
	 * @param off
	 * @param max
	 * @return
	 */
	public void findBkErrorLog(final SysBkerrorlog sysBkerrorlog, final PageCtr<SysBkerrorlog> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysBkerrorlog.class);
				buildCriteria(sysBkerrorlog, criteria);
				criteria.addOrder(Order.desc("rectime"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	private void buildCriteria(SysBkerrorlog sysBkerrorlog, Criteria criteria) {
		String startTime = null;
		String endTime = null;
		String objtype = null;
		if (null != sysBkerrorlog) {
			startTime = sysBkerrorlog.getStartTime();
			endTime = sysBkerrorlog.getEndTime();
			objtype = sysBkerrorlog.getObjtype();
		}
		if (null != startTime && !startTime.equals("")) {
			criteria.add(Restrictions.sqlRestriction("rectime >= to_date('" + startTime + "','yyyy-mm-dd')"));
		}
		if (null != endTime && !endTime.equals("")) {
			criteria.add(Restrictions.sqlRestriction("rectime < to_date('" + endTime + "','yyyy-mm-dd')"));
		}
		if (null != objtype && !"".equals(objtype)) {
			criteria.add(Restrictions.eq("objtype", objtype));
		}
	}

	public int getSysBkErrorLogCount(final SysBkerrorlog sysBkerrorlog) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysBkerrorlog.class);
				buildCriteria(sysBkerrorlog, criteria);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});

	}

	public List<SysBkerrorlog> findBkErrorLog(final String startTime, final String endTime) {
		return (List<SysBkerrorlog>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(SysBkerrorlog.class);
				if (null != startTime && !startTime.equals("")) {
					ca.add(Restrictions.sqlRestriction("rectime >= to_date('" + startTime
							+ "','yyyy-mm-dd HH24:mi:ss')"));
				}
				if (null != endTime && !endTime.equals("")) {
					ca.add(Restrictions.sqlRestriction("rectime < to_date('" + endTime + "','yyyy-mm-dd')"));
				}
				ca.addOrder(Order.desc("rectime"));
				List<SysBkerrorlog> list = ca.list();
				return list;
			}
		});
	}

	/**
	 * 根据时间段删除错误日志
	 * 
	 * @param sysBkerrorlog
	 */
	public void deleteErrorLog(final SysBkerrorlog sysBkerrorlog) {
		final String startTime = sysBkerrorlog.getStartTime();
		final String endTime = sysBkerrorlog.getEndTime();
		final StringBuffer buffer = new StringBuffer();
		buffer.append("delete from SysBkerrorlog where 1=1 ");
		if (null != startTime && !"".equals(startTime)) {
			buffer.append(" and rectime >= to_date('" + startTime + "','yyyy-mm-dd')");
		}
		if (null != endTime && !"".equals(endTime)) {
			buffer.append(" and rectime < to_date('" + endTime + "','yyyy-mm-dd')");
		}
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(buffer.toString());
				return query.executeUpdate();
			}
		});
	}
}
