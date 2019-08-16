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
import com.tlys.dic.model.DicRwdepartype;

@Repository
public class DicRwdepartypeDao extends _GenericDao<DicRwdepartype> {

	public List<DicRwdepartype> find(final DicRwdepartype dicRwdepartype) {
		List<DicRwdepartype> dicRwdepartypes = (List<DicRwdepartype>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicRwdepartype> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(DicRwdepartype.class)
								.add(
										Example.create(dicRwdepartype).enableLike(
												MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return dicRwdepartypes;
	}
}
