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
import com.tlys.dic.model.DicRentMode;

/**
 * @author guojj
 * 
 */
@Repository
public class DicRentmodeDao extends _GenericDao<DicRentMode> {

	public DicRentMode load(String id) {
		DicRentMode DicRentmode = super.load(id);
		return DicRentmode;
	}

	public List<DicRentMode> find(final DicRentMode DicRentmode) {
		List<DicRentMode> DicRentmodes = (List<DicRentMode>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicRentMode> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(DicRentMode.class)
								.add(
										Example.create(DicRentmode).enableLike(
												MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return DicRentmodes;
	}

}
