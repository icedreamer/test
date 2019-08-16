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
import com.tlys.dic.model.DicCartype;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicCartypeDao extends _GenericDao<DicCartype> {

	public DicCartype load(String id) {
		DicCartype dicCartype = super.load(id);
		return dicCartype;
	}
	
	public List<DicCartype> findByParentid(String pid){
		return this.findByProperty("parentid", pid);
	}
	
	public List<DicCartype> find(final DicCartype dicCartype) {
		List<DicCartype> dicCartypes = (List<DicCartype>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicCartype> doInHibernate(Session session)
					throws HibernateException {
				List dacs = session.createCriteria(
						DicCartype.class).add(Example.create(dicCartype).enableLike(MatchMode.ANYWHERE))
						.list();
				return dacs;
			}
		});

		return dicCartypes;
	}

}
