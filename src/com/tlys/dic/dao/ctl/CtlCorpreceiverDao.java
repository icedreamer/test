package com.tlys.dic.dao.ctl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.ctl.CtlCorpreceiver;

/**
 * 內部企業收貨人
 * 
 * @author ccsong 2012-2-28 下午5:34:51
 */
@Repository
public class CtlCorpreceiverDao extends _GenericDao<CtlCorpreceiver> {

	public List<CtlCorpreceiver> findCtlCorpreceiverByCorpid(final String corpid) {
		return (List<CtlCorpreceiver>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(CtlCorpreceiver.class);
				if (null != corpid && !"".equals(corpid)) {
					criteria.add(Restrictions.eq("corpid", corpid));
				}
				return criteria.list();
			}
		});
	}

	public CtlCorpreceiver getCtlCorpreceiver(final String receiverid) {
		final String hql = "from CtlCorpreceiver where receiverid = ? ";
		return (CtlCorpreceiver) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, receiverid);
				query.setMaxResults(1);
				return query.uniqueResult();
			}
		});
	}

	public String getMaxReq() {
		final String sql = "select max(receiverid) from CtlCorpreceiver where substr(receiverid,0,2) = 'ZB'";
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				return query.uniqueResult();
			}
		});
	}
}
