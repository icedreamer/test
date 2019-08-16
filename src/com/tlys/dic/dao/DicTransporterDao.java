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
import com.tlys.dic.model.DicTransporter;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class DicTransporterDao extends _GenericDao<DicTransporter> {

	public DicTransporter load(String id) {
		DicTransporter dicTransporter = super.load(id);
		return dicTransporter;
	}

	public List<DicTransporter> findByParentid(String pid) {
		return this.findByProperty("parentid", pid);
	}

	public List<DicTransporter> find(final DicTransporter dicTransporter) {
		List<DicTransporter> dicTransporters = (List<DicTransporter>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public List<DicTransporter> doInHibernate(Session session) throws HibernateException {
						List dacs = session.createCriteria(DicTransporter.class)
								.add(Example.create(dicTransporter).enableLike(MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return dicTransporters;
	}

	/**
	 * 根据企业、客户和承运人的记录数数控制表使用频率获取承运人列表
	 * 
	 * @param customerid
	 * @param corpid
	 * @return
	 */
	public List<DicTransporter> findTransporter(final String customerid, final String corpid) {
		final String sql = "SELECT A.* FROM TB_ZBC_DIC_TRANSPORTER A LEFT JOIN TB_ZBC_CTL_CORPTRANSPORT B ON A.TRANSPORTID = B.TRANSPORTID "
				+ "AND TRIM(B.CORPID) = '" + corpid + "' AND TRIM(B.CUSTOMERID) = '" + customerid + "' ORDER BY B.RECORDS DESC ";
		return (List<DicTransporter>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				query.addEntity("A", DicTransporter.class);
				return query.list();
			}
		});
	}

}
