package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitMsinosctamount;
import com.tlys.comm.bas._GenericDao;

@Repository
public class BitMsinosctamountDao extends _GenericDao<BitMsinosctamount> {
	/**
	 * 总部用户自备车运量情况
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinosctamounts(String month) {
		final String hql = "select a.month, sum(a.mtamount), sum(a.linkrelative), sum(a.yoy) from BitMsinosctamount a where a.month between to_char(add_months(to_date('"
				+ month
				+ "', 'yyyymm'), -11), 'yyyymm') and '"
				+ month
				+ "' and (a.kind = 0 or a.kind = 1) group by a.month order by a.month ";
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				return query.list();
			}
		});
	}

	/**
	 * 总部用户自备车运量情况(新页面)
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinosctamount(String month) {

		final String hql = "select a.month,sum(a.mtamount), sum(a.linkrelative), sum(a.yoy) "
				+ "from BitMsinosctamount a "
				+ "where a.month between to_char(add_months(to_date('" + month
				+ "', 'yyyymm'), -11), 'yyyymm') and '" + month
				+ "' and (a.kind = 0 or a.kind = 1) group by a.month order by a.month ";

		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				return query.list();
			}
		});
	}

	/**
	 * 自备车运量构成数据 总部权限使用
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinosctamountByKind(final List<String> monthList) {
		final String sql = "select a.month,a.kind,sum(a.mtamount) from BitMsinosctamount a where a.month in (:monthList) and (a.kind = 0 or a.kind = 1) group by a.month,a.kind order by a.month ";

		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("monthList", monthList);
				return query.list();
			}
		});
	}
}
