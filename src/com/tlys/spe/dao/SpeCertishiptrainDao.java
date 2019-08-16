package com.tlys.spe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.spe.model.SpeCertishiptrain;

@Repository
public class SpeCertishiptrainDao extends _GenericDao<SpeCertishiptrain> {

	public SpeCertishiptrain load(String id) {
		SpeCertishiptrain speCertishiptrain = super.load(id);
		return speCertishiptrain;
	}

	public List<SpeCertishiptrain> find(final SpeCertishiptrain speCertishiptrain) {
		List<SpeCertishiptrain> ctList = (List<SpeCertishiptrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public List<SpeCertishiptrain> doInHibernate(Session session)
							throws HibernateException {
						List<SpeCertishiptrain> dacs = session.createCriteria(
								SpeCertishiptrain.class).add(Example.create(speCertishiptrain))
								.list();
						return dacs;
					}
				});

		return ctList;
	}

	public void delete(final Long regid) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query query = session.createQuery("delete from SpeCertishiptrain where regid = ? ");
				query.setLong(0, regid);
				return query.executeUpdate();
			}
		});
	}

}
