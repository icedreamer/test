package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitMitamount;
import com.tlys.comm.bas._GenericDao;

@Repository
public class BitMitamountDao extends _GenericDao<BitMitamount> {
	/**
	 * 铁路运量 企业入厂数据
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMitamount(String month, String corpid) {
		String sql = "select a.month, sum(a.mtamount), sum(a.linkrelative), sum(a.yoy) from BitMitamount a where a.month between to_char(add_months(to_date('"
				+ month
				+ "', 'yyyymm'), -11), 'yyyymm') and '"
				+ month
				+ "' and a.corpid = '"
				+ corpid + "' and (a.kind = 0 or a.kind = 1) group by a.month order by a.month ";
		return (List<Object[]>) getHibernateTemplate().find(sql);
	}

	/**
	 * 铁路运量 明细数据 企业入厂数据
	 * 
	 * @param monthList
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMitamountByKind(final List<String> monthList, String corpid) {
		final String sql = "select a.month,a.kind, a.mtamount from BitMitamount a where a.month in (:monthList) and (a.kind = 0 or a.kind = 1) and a.corpid = '"
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
