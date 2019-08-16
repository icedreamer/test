package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitMsinoctamount;
import com.tlys.comm.bas._GenericDao;

@Repository
public class BitMsinoctamountDao extends _GenericDao<BitMsinoctamount> {
	/**
	 * 铁路运量 总部用户
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinoctamount(String month) {
		String sql = "select a.month, sum(a.mtamount), sum(a.linkrelative), sum(a.yoy) from BitMsinoctamount a where a.month between to_char(add_months(to_date('"
				+ month
				+ "', 'yyyymm'), -11), 'yyyymm') and '"
				+ month
				+ "' and (a.kind = 0 or a.kind = 1) group by a.month order by a.month ";
		return (List<Object[]>) getHibernateTemplate().find(sql);
	}

	/**
	 * 铁路运量 构成数据 总部用户权限
	 * 
	 * @param monthList
	 * @return
	 */
	public List<Object[]> findBitMsinoctamountByKind(final List<String> monthList) {
		final String sql = "select a.month,a.kind,sum(a.mtamount) from BitMsinoctamount a where a.month in (:monthList) and (a.kind = 0 or a.kind = 1) group by a.month,a.kind ";
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("monthList", monthList);
				return query.list();
			}
		});
	}
}
