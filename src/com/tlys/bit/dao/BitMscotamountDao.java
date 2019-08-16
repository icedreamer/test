package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitMscotamount;
import com.tlys.comm.bas._GenericDao;

@Repository
public class BitMscotamountDao extends _GenericDao<BitMscotamount> {

	/**
	 * 获取铁路出厂数据
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamounts(String month, String corpid) {
		String sql = "select a.month,a.mtamount, a.linkrelative, a.yoy from BitMscotamount a "
				+ "where a.month between to_char(add_months(to_date('" + month
				+ "', 'yyyymm'), -11), 'yyyymm') and '" + month + "' and a.corpid = '" + corpid
				+ "' order by a.month ";
		return getHibernateTemplate().find(sql);
	}

	/**
	 * 获取铁路出厂数据 新加
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamount(String month, String corpid) {
		String sql = "select a.month,a.mtamount,a.linkrelative,  a.yoy from BitMscotamount a where a.month between to_char(add_months(to_date('"
				+ month
				+ "', 'yyyymm'), -11), 'yyyymm') and '"
				+ month
				+ "' and a.corpid = '"
				+ corpid + "' and (a.kind = 0 or a.kind = 1) order by a.month ";

		return getHibernateTemplate().find(sql);
	}

	/**
	 * 出厂数据构成 企业权限
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamountByKind(final List<String> monthList, String corpid) {
		final String sql = "select a.month,a.kind, a.mtamount from BitMscotamount a where a.month in (:monthList) and (a.kind = 1 or a.kind = 0) and a.corpid = '"
				+ corpid + "' order by a.month ";

		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("monthList", monthList);
				return query.list();
			}
		});
	}
}
