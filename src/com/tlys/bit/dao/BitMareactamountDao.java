package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitMareactamount;
import com.tlys.comm.bas._GenericDao;

@Repository
public class BitMareactamountDao extends _GenericDao<BitMareactamount> {
	/**
	 * 铁路运量 区域公司数据
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareactamount(String month, String areaid) {
		String sql = "select a.month, sum(a.mtamount), sum(a.linkrelative), sum(a.yoy) from BitMareactamount a where a.month between to_char(add_months(to_date('"
				+ month
				+ "', 'YYYYMM'), -11), 'YYYYMM') AND '"
				+ month
				+ "' and a.areaid = '"
				+ areaid + "' and (a.kind = 0 or a.kind = 1) group by a.month order by a.month ";
		return (List<Object[]>) getHibernateTemplate().find(sql);
	}

	/**
	 * 铁路运量 构成数据 区域公司权限
	 * 
	 * @param monthList
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareactamountByKind(final List<String> monthList, String areaid) {
		final String sql = "select a.month,a.kind, sum(a.mtamount)	from BitMareactamount a where a.month in(:monthList) and (a.kind = 0 or a.kind = 1) and a.areaid = '"
				+ areaid + "' group by a.month, a.kind order by a.month ";
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("monthList", monthList);
				return query.list();
			}
		});
	}
}
