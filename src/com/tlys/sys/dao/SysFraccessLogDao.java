package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysFraccessLog;
import com.tlys.sys.model.SysSession;

@Repository
public class SysFraccessLogDao extends _GenericDao<SysFraccessLog> {

	public List<SysFraccessLog> findSysFraccessLog(String sessionid) {
		return getHibernateTemplate().find("from SysFraccessLog where sessionid = ? ", sessionid);
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
		buffer.append("delete from SysFraccessLog where 1=1 ");
		if (null != startTime && !"".equals(startTime)) {
			buffer.append(" and to_char(oprtime,'yyyy-MM-dd HH:mm:ss') >= '" + startTime + "'");
		}
		if (null != endTime && !"".equals(endTime)) {
			buffer.append("and to_char(oprtime,'yyyy-MM-dd HH:mm:ss') < '" + endTime + "' ");
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(buffer.toString());
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 更新会话过程中的操作，在关闭浏览器访问时将更新原来的sessionid为当前sessionid
	 * 
	 * @param fromSessionID
	 * @param toSessionID
	 */
	public void updateSysFraccessLogBySessionID(final String fromSessionID, final String toSessionID) {
		final String hql = "update SysFraccessLog set sessionid = ? where sessionid = ? ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, toSessionID);
				query.setString(1, fromSessionID);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 根据会话ID的列表查询会话操作
	 * 
	 * @param sessionidList
	 * @return
	 */
	public List<SysFraccessLog> findSysFraccessLog(final String startTime, final String endTime) {

		// final String hql =
		// "from SysFraccessLog where sessionid in (:sessionidList) ";
		return (List<SysFraccessLog>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<SysFraccessLog> doInHibernate(Session s) throws HibernateException, SQLException {

				// query.setParameterList("sessionidList", sessionidList);
				// List<SysFraccessLog> list = query.list();
				// return list;
				String hql = "select a from SysFraccessLog a ,SysSession b where a.sessionid = b.sessionid ";
				StringBuilder buffer = new StringBuilder();
				buffer.append(hql);
				if (null != startTime && !"".equals(startTime)) {
					buffer.append(" and b.starttime >= to_date('" + startTime + "','yyyy-mm-dd HH24:mi:ss') ");
				}
				if (null != endTime && !"".equals(endTime)) {
					buffer.append(" and b.starttime < to_date('" + endTime + "','yyyy-mm-dd HH24:mi:ss') ");
				}
				Query query = s.createQuery(buffer.toString());
				List<SysFraccessLog> list = query.list();
				return list;
			}
		});
	}
}
