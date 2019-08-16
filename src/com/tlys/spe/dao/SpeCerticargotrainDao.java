package com.tlys.spe.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.spe.model.SpeCerticargotrain;

@Repository
public class SpeCerticargotrainDao extends _GenericDao<SpeCerticargotrain> {

	public SpeCerticargotrain load(String id) {
		SpeCerticargotrain speCerticargotrain = super.load(id);
		return speCerticargotrain;
	}

	public List<SpeCerticargotrain> find(final SpeCerticargotrain speCerticargotrain) {
		List<SpeCerticargotrain> ctList = (List<SpeCerticargotrain>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<SpeCerticargotrain> doInHibernate(Session session)
					throws HibernateException {
				List<SpeCerticargotrain> dacs = session.createCriteria(
						SpeCerticargotrain.class).add(Example.create(speCerticargotrain))
						.list();
				return dacs;
			}
		});

		return ctList;
	}

	
}
