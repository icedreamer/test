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
import com.tlys.dic.model.DicCarkind;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicCarkindDao extends _GenericDao<DicCarkind> {

	public DicCarkind load(String id) {
		DicCarkind dicCarkind = super.load(id);
		return dicCarkind;
	}
	
	public List<DicCarkind> findByParentid(String pid){
		return this.findByProperty("parentid", pid);
	}
	
	public List<DicCarkind> find(final DicCarkind dicCarkind) {
		List<DicCarkind> dicCarkinds = (List<DicCarkind>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicCarkind> doInHibernate(Session session)
					throws HibernateException {
				List dacs = session.createCriteria(
						DicCarkind.class).add(Example.create(dicCarkind).enableLike(MatchMode.ANYWHERE))
						.list();
				return dacs;
			}
		});

		return dicCarkinds;
	}

}
