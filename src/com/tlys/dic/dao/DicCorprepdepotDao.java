/**
 * @author ¹ù½¨¾ü
 */
package com.tlys.dic.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicCorprepdepot;

@Repository
public class DicCorprepdepotDao extends _GenericDao<DicCorprepdepot> {
	public List<DicCorprepdepot> find(final DicCorprepdepot DicCorprepdepot) {
		List<DicCorprepdepot> DicCorprepdepots = (List<DicCorprepdepot>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicCorprepdepot> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(DicCorprepdepot.class)
								.add(
										Example.create(DicCorprepdepot).enableLike(
												MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return DicCorprepdepots;
	}
}
