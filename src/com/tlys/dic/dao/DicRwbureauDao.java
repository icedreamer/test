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
import com.tlys.dic.model.DicRwbureau;

@Repository
public class DicRwbureauDao extends _GenericDao<DicRwbureau> {

	public int findCount(String condition) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<DicRwbureau> find(final DicRwbureau dicRwbureau) {
		List<DicRwbureau> dicRwbureaus = (List<DicRwbureau>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicRwbureau> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(DicRwbureau.class)
								.add(
										Example.create(dicRwbureau).enableLike(
												MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return dicRwbureaus;
	}
}
