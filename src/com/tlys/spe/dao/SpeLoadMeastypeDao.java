package com.tlys.spe.dao;

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
import com.tlys.spe.model.SpeLoadMeastype;

@Repository
public class SpeLoadMeastypeDao extends _GenericDao<SpeLoadMeastype> {
	public String getSeq() {
		final String sql = "select SEQ_TB_ZBC_SPE_LOAD_MEASTYPE.nextval from dual";
		Object o = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				return query.uniqueResult();
			}
		});
		return o.toString();
	}

	public List<SpeLoadMeastype> find(final SpeLoadMeastype SpeLoadMeastype) {
		List<SpeLoadMeastype> SpeLoadMeastypes = (List<SpeLoadMeastype>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<SpeLoadMeastype> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(
								SpeLoadMeastype.class).add(
								Example.create(SpeLoadMeastype).enableLike(
										MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});
		return SpeLoadMeastypes;
	}
}
