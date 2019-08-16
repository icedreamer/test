/**
 * 
 */
package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicCustomer;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicCustomerDao extends _GenericDao<DicCustomer> {

	public DicCustomer load(String id) {
		DicCustomer dicCustomer = super.load(id);
		return dicCustomer;
	}

	public List<DicCustomer> findByParentid(String pid) {
		return this.findByProperty("parentid", pid);
	}

	public List<DicCustomer> find(final DicCustomer dicCustomer) {
		List<DicCustomer> dicCustomers = (List<DicCustomer>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicCustomer> doInHibernate(Session session) throws HibernateException {
				List dacs = session.createCriteria(DicCustomer.class)
						.add(Example.create(dicCustomer).enableLike(MatchMode.ANYWHERE)).list();
				return dacs;
			}
		});

		return dicCustomers;
	}

	public List<DicCustomer> find4view(String viewname) {
		String hql = "from DicCustomer d where d.isactive='1' and d.is" + viewname + "='0'";
		return this.getHibernateTemplate().find(hql);
	}

	public void saveOrUpdateAll(List customers) {
		getHibernateTemplate().saveOrUpdateAll(customers);
	}

	public List findDicCustomer(final String corpid) {
		final String sql = "SELECT A.* FROM TB_ZBC_DIC_CUSTOMER A LEFT JOIN TB_ZBC_CTL_CORPCUSTOMER B "
				+ "ON TRIM(B.CUSTOMERID) = TRIM(A.CUSTOMERID) " + " AND TRIM(B.CORPID) = '" + corpid
				+ "' ORDER BY B.RECORDS DESC ";
		return (List<DicCustomer>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {

				SQLQuery query = s.createSQLQuery(sql);
				query.addEntity("A", DicCustomer.class);
				// query.addEntity("B", CtlCorpcustomer.class);
				return query.list();
			}
		});
	}

	public DicCustomer getDicCustomerByName(final String customerName) {
		final String hql = "from DicCustomer where fullname = ? ";
		return (DicCustomer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, customerName);
				List<DicCustomer> list = query.list();
				if (null != list && !list.isEmpty()) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	public String getSeq() {
		final String sql = "select SEQ_TB_ZBC_DIC_CUSTOMER.nextval from dual";
		Object o = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				return query.uniqueResult();
			}
		});
		return o.toString();
	}
}
