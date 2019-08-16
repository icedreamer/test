/**
 * 
 */
package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicProvince;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicProvinceDao extends _GenericDao<DicProvince> {

	public DicProvince load(String id) {
		DicProvince dicProvince = super.load(id);
		return dicProvince;
	}

	public List<DicProvince> find(DicProvince dicProvince) {
		final Example exUser = Example.create(dicProvince).ignoreCase();
		List<DicProvince> dicProvinces = super.findByExample(dicProvince);
		return dicProvinces;
	}

	public List<DicProvince> findDicProvince(final String customerid) {
		final String sql = "SELECT a.* FROM TB_ZBC_DIC_PROVINCE a LEFT JOIN TB_ZBC_CTL_CUSTOMERRECEIVER b ON a.PROVINCEID = b.GOALPROVINCEID "
				+ "AND b.CUSTOMERID = '" + customerid + "' ORDER BY b.RECORDS DESC ";
		return (List<DicProvince>) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				query.addEntity("a", DicProvince.class);
				return query.list();
			}
		});
	}

}
