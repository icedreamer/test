/**
 * 
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
import com.tlys.dic.model.DicCarmaker;

/**
 * @author guojj
 * 
 */
@Repository
public class DicCarmakerDao extends _GenericDao<DicCarmaker> {

	public DicCarmaker load(String id) {
		DicCarmaker dicCarmarker = super.load(id);
		return dicCarmarker;
	}

	public List<DicCarmaker> find(final DicCarmaker dicCarmarker) {
		List<DicCarmaker> dicCarmarkers = (List<DicCarmaker>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicCarmaker> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session
								.createCriteria(DicCarmaker.class)
								.add(
										Example.create(dicCarmarker)
												.enableLike(MatchMode.ANYWHERE))
								.list();
						return dacs;
					}
				});

		return dicCarmarkers;
	}

}
