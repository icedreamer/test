package com.tlys.dic.dao.ctl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.ctl.CtlCorpsendwarehouse;

@Repository
public class CtlCorpsendwarehouseDao extends _GenericDao<CtlCorpsendwarehouse> {

	public List<CtlCorpsendwarehouse> findCtlCorpsendwarehouse(final String corpid) {
		final String hql = "from CtlCorpsendwarehouse a where TRIM(a.id.corpid) = ? order by a.id.records desc ";
		return (List<CtlCorpsendwarehouse>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, corpid);
				return query.list();
			}
		});
	}
}
