/**
 * 
 */
package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.equ.model.EquCarCertificate;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class EquCarCertificateDao extends _GenericDao<EquCarCertificate> {

	public EquCarCertificate load(String id) {
		EquCarCertificate equCarCertificate = super.load(id);
		return equCarCertificate;
	}

	public List<EquCarCertificate> find(final EquCarCertificate equCarCertificate) {
		return (List<EquCarCertificate>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<EquCarCertificate> doInHibernate(Session session) throws HibernateException {
				Criteria ca = session.createCriteria(EquCarCertificate.class);
				if (null != equCarCertificate) {
					String carnos = equCarCertificate.getCarnos();
					String trainno = equCarCertificate.getTrainno();
					String isvalid = equCarCertificate.getIsvalid();
					if (null != carnos && carnos.trim().length() > 0) {
						ca.add(Restrictions.sqlRestriction(" trim(trainno) in (" + carnos + ")"));
					}
					if (null != trainno && !"".equals(trainno)) {
						ca.add(Restrictions.sqlRestriction("trim(trainno) = '" + trainno + "'"));
					}
					if (null != isvalid && !"".equals(isvalid)) {
						ca.add(Restrictions.eq("isvalid", isvalid));
					}
				}
				ca.addOrder(Order.asc("trainno"));
				return ca.list();
			}
		});
	}

	public void delete(final List<String> carnos) {
		final String hql = "delete from EquCarCertificate a where a.trainno in (:carnos) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("carnos", carnos);
				return query.executeUpdate();
			}
		});
	}

	public List<EquCarCertificate> findEquCarCertificate(final String corpid) {
		final String hql = "from EquCarCertificate a where a.trainno in "
				+ "(select distinct b.carno from EquCar b where b.corpid = ? and b.isexpire = 0 ) and a.isvalid = 1 "
				+ " order by a.trainno ,certdate desc ";
		return (List<EquCarCertificate>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, corpid);
				return query.list();
			}
		});
	}

}
