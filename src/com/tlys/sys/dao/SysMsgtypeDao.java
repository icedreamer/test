package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysMsgtype;

@Repository
public class SysMsgtypeDao extends _GenericDao<SysMsgtype> {

	public List<SysMsgtype> findSysMsgtype() {
		final String hql = "from SysMsgtype where isactive = 1 order by msgtypename ";
		return (List<SysMsgtype>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);

				return query.list();
			}
		});
	}

}
