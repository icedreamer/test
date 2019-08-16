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
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicRwstation;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class DicRwstationDao extends _GenericDao<DicRwstation> {

	public DicRwstation load(String id) {
		DicRwstation dicRwstation = super.load(id);
		return dicRwstation;
	}

	public List<DicRwstation> find(final DicRwstation dicRwstation) {
		List<DicRwstation> dicRwstations = (List<DicRwstation>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicRwstation> doInHibernate(Session session) throws HibernateException {
				List dacs = session.createCriteria(DicRwstation.class)
						.add(Example.create(dicRwstation).enableLike(MatchMode.ANYWHERE)).list();
				return dacs;
			}
		});

		return dicRwstations;
	}

	/**
	 * 根据企业和发货站的记录数控制表取得发站
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicRwstation> findDicRwstation(final String corpid) {
		final String sql = "SELECT A.* FROM TB_ZBC_DIC_RWSTATION A LEFT JOIN TB_ZBC_CTL_CORPSTARTSTATION B "
				+ "ON TRIM(B.STARTSTATIONID) = TRIM(A.STATIONID) " + " AND TRIM(B.CORPID) = '" + corpid
				+ "' ORDER BY B.RECORDS DESC ";
		return (List<DicRwstation>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				query.addEntity("A", DicRwstation.class);
				return query.list();
			}
		});
	}

	/**
	 * 根据客户和到站的记录数控制表 取得 到站
	 * 
	 * @param customerid
	 * @return
	 */
	public List<DicRwstation> findCustomerDicRwstation(final String customerid) {
		final String sql = "SELECT * FROM TB_ZBC_DIC_RWSTATION a LEFT JOIN  TB_ZBC_CTL_CUSTOMERENDSTATION b ON b.ENDSTATIONID = a.stationid "
				+ "AND b.CUSTOMERID = '100001' ORDER BY b.RECORDS DESC ";
		return (List<DicRwstation>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				query.addEntity("A", DicRwstation.class);
				return query.list();
			}
		});
	}

}
