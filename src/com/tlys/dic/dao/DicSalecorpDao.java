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
import com.tlys.dic.model.DicSalecorp;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicSalecorpDao extends _GenericDao<DicSalecorp> {

	public DicSalecorp load(String id) {
		DicSalecorp dicSalecorp = super.load(id);
		return dicSalecorp;
	}

	public List<DicSalecorp> find(final DicSalecorp dicSalecorp) {
		List<DicSalecorp> dicSalecorps = (List<DicSalecorp>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicSalecorp> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session
								.createCriteria(DicSalecorp.class)
								.add(Example.create(dicSalecorp).enableLike(
										MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return dicSalecorps;
	}

}
