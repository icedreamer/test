package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysFraccessLogOpr;
import com.tlys.sys.model.SysSession;

@Repository
public class SysFraccessLogOprDao extends _GenericDao<SysFraccessLogOpr> {
	public List<SysFraccessLogOpr> findSysFraccessLogOpr(final List<Long> logIdList) {
		if (null == logIdList || logIdList.isEmpty()) {
			return null;
		}
		final String hql = "from SysFraccessLogOpr a where a.logid in (:logIdList) ";
		return (List<SysFraccessLogOpr>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = s.createQuery(hql);
				query.setParameterList("logIdList", logIdList);
				return query.list();
			}
		});
	}

	public List<SysFraccessLogOpr> findSysFraccessLogOpr(final String startTime, final String endTime) {

		return (List<SysFraccessLogOpr>) getHibernateTemplate().execute(new HibernateCallback() {
			// String hql =
			// "from SysFraccessLogOpr a where a.logid in (:logIdList) ";
			String hql = "select c from SysSession a ,SysFraccessLog b,SysFraccessLogOpr c where a.sessionid = b.sessionid and b.id = c.logid ";
			StringBuilder buffer = new StringBuilder(hql);

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				if (null != startTime && !"".equals(startTime)) {
					buffer.append(" and a.starttime >= to_date('" + startTime + "','yyyy-mm-dd HH24:mi:ss') ");
				}
				if (null != endTime && !"".equals(endTime)) {
					buffer.append(" and a.starttime < to_date('" + endTime + "','yyyy-mm-dd HH24:mi:ss') ");
				}
				Query query = s.createQuery(buffer.toString());
				// query.setParameterList("logIdList", logIdList);

				return query.list();
			}
		});
	}

	/**
	 * 根据时间段删除访问操作日志
	 * 
	 * @param sysSession
	 */
	public void deleteAccessLog(final SysSession sysSession) {
		final String startTime = sysSession.getStartDateStr();
		final String endTime = sysSession.getEndDateStr();
		final StringBuffer buffer = new StringBuffer();
		buffer.append("delete from SysFraccessLogOpr a where a.logid in (select b.id from SysFraccessLog b where 1=1 ");
		if (null != startTime && !"".equals(startTime)) {
			buffer.append(" and to_char(b.oprtime,'yyyy-MM-dd HH:mm:ss') >= '" + startTime + "'");
		}
		if (null != endTime && !"".equals(endTime)) {
			buffer.append("and to_char(b.oprtime,'yyyy-MM-dd HH:mm:ss') < '" + endTime + "' ");
		}
		buffer.append(")");
		if (logger.isDebugEnabled()) {
			logger.debug("hql : " + buffer.toString());
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(buffer.toString());
				return query.executeUpdate();
			}
		});
	}
}
