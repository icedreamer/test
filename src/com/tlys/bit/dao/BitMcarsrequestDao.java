package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitMcarsrequest;
import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.sys.model.SysUser;

@Repository
public class BitMcarsrequestDao extends _GenericDao<BitMcarsrequest> {
	/**
	 * 计划及批复情况
	 * 
	 * @return
	 */
	public List<Object[]> findBitMcarsrequests(SysUser sysUser, final String datamonth) {
		if (null == sysUser) {
			return null;
		}
		final String corptab = sysUser.getCorptab();
		if (null == corptab) {
			return null;
		}
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				String hql = "";
				if ("3".equals(corptab)) {
					hql = "select kind, sum(requestcars), sum(acceptcars), sum(loadcars) ,corpid "
							+ " from BitMcarsrequest where "
							+ CommUtils.getCorpIds("corpid")
							+ " and datamonth = '"
							+ datamonth
							+ "' "
							+ "group by kind,corpid having sum(requestcars) > 0 or sum(acceptcars) > 0 or sum(loadcars) > 0 ";
				} else {
					hql = "select corpshrinkname,sum(requestcars),sum(acceptcars),sum(loadcars) ,corpid "
							+ " from BitMcarsrequest   where trim(datamonth) = '"
							+ datamonth
							+ "' and "
							+ CommUtils.getCorpIds("corpid")
							+ " group by corpshrinkname,corpid having sum(requestcars) > 0 or sum(acceptcars) > 0 or sum(loadcars) > 0 order by corpshrinkname ";
				}
				Query query = s.createQuery(hql);
				return query.list();
			}
		});
	}

	public String getFixMonth() {
		final String sql = "select trim(max(a.datamonth)) from BitMcarsrequest a ";
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				return query.uniqueResult();
			}
		});
	}
}
