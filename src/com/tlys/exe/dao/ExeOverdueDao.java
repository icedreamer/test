package com.tlys.exe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.exe.model.ExeOverdue;

/**
 * ��ʱԤ��Dao
 * 
 * @author �״���
 * 
 */
@Repository
public class ExeOverdueDao extends _GenericDao<ExeOverdue> {

	/**
	 * 
	 * @param entpr_id����ҵid
	 * @param medium_id����װ����id
	 * @param over_id����ʱʱ���id
	 * @param over_type����ʱ��ʶ0/1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeOverdue> listOverdue(final String entpr_id, final String medium_id, final String over_id,
			final String over_type) {
		return (List<ExeOverdue>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeOverdue.class);

				// ��ʱ��ʶ
				if (null != over_type && !over_type.equals("")) {
					ca.add(Restrictions.eq("id.over_type", over_type));
				}
				// ��ҵid
				if (null != entpr_id && !entpr_id.equals("")) {
					String[] entpr_idArr = entpr_id.split(",");
					ca.add(Restrictions.in("id.entpr_id", entpr_idArr));
				}
				// ��װ����id
				if (null != medium_id && !medium_id.equals("")) {
					String[] medium_idArr = medium_id.split(",");
					ca.add(Restrictions.in("id.medium_id", medium_idArr));
				}
				// ��ʱʱ���id
				if (null != over_id && !over_id.equals("")) {
					String[] over_idArr = over_id.split(",");
					ca.add(Restrictions.in("id.over_id", over_idArr));
				}
				ca.addOrder(Order.asc("id.entpr_id"));
				ca.addOrder(Order.asc("id.medium_id"));
				ca.addOrder(Order.asc("id.over_id"));
				return ca.list();
			}
		});
	}

	/**
	 * 
	 * @param entpr_id����ҵid
	 * @param medium_id����װ����id
	 * @param over_id����ʱʱ���id
	 * @param over_type����ʱ��ʶ0/1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeOverdue> listOverdue(final String entpr_id, final String over_id, final String over_type) {
		return (List<ExeOverdue>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeOverdue.class);
				// ��ʱ��ʶ
				if (null != over_type && !over_type.equals("")) {
					ca.add(Restrictions.eq("id.over_type", over_type));
				}
				 // ��ҵid
				if (null != entpr_id && !entpr_id.equals("")) {
					String[] entpr_idArr = entpr_id.split(",");
					ca.add(Restrictions.in("id.entpr_id", entpr_idArr));
				}

				// ��ʱʱ���id
				 if (null != over_id && !over_id.equals("")) {
					String[] over_idArr = over_id.split(",");
					ca.add(Restrictions.in("id.over_id", over_idArr));
				}
				ca.addOrder(Order.asc("id.entpr_id"));
				ca.addOrder(Order.asc("id.over_id"));
				return ca.list();
			}
		});
	}

}
