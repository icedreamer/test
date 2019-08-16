package com.tlys.bit.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.bit.model.BitMareasctamount;
import com.tlys.comm.bas._GenericDao;

@Repository
public class BitMareasctamountDao extends _GenericDao<BitMareasctamount> {
	/**
	 * ����˾Ȩ�޵��û� ��ȡ��������
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamounts(String month, String areaid) {
		String sql = "select a.month, sum(a.mtamount), sum(a.linkrelative), sum(a.yoy) from BitMareasctamount a where a.month between to_char(add_months(to_date('"
				+ month
				+ "','yyyymm'),-11),'yyyymm') and '"
				+ month
				+ "' and a.areaid = '"
				+ areaid + "' group by a.month order by a.month ";

		return (List<Object[]>) getHibernateTemplate().find(sql);
	}

	/**
	 * ����˾Ȩ�޵��û� �Ա�����������
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamount(String month, String areaid) {
		String sql = "select a.month, sum(a.mtamount), sum(a.linkrelative), sum(a.yoy) from BitMareasctamount a where a.month between to_char(add_months(to_date('"
				+ month
				+ "', 'yyyymm'), -11), 'yyyymm') and '"
				+ month
				+ "' and a.areaid = '"
				+ areaid + "' and (a.kind = 0 or a.kind = 1) group by a.month order by a.month ";

		return (List<Object[]>) getHibernateTemplate().find(sql);
	}

	/**
	 * ����˾Ȩ�޵��û� �Ա���������������
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamountByKind(final List<String> monthList,
			final String areaid) {
		final String sql = "select a.month,a.kind,sum(a.mtamount) from BitMareasctamount a where a.month in (:monthList) and (a.kind = 0 or a.kind = 1) and a.areaid = '"
				+ areaid + "' group by a.month,a.kind order by a.month ";
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("monthList", monthList);
				return query.list();
			}
		});
	}
}
