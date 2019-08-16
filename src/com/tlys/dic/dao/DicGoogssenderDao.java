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
import com.tlys.dic.model.DicGoogssender;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicGoogssenderDao extends _GenericDao<DicGoogssender> {

	public DicGoogssender load(String id) {
		DicGoogssender dicGoogssender = super.load(id);
		return dicGoogssender;
	}
	
	public List<DicGoogssender> findByParentid(String pid){
		return this.findByProperty("parentid", pid);
	}
	
	public List<DicGoogssender> find(final DicGoogssender dicGoogssender) {
		List<DicGoogssender> dicGoogssenders = (List<DicGoogssender>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicGoogssender> doInHibernate(Session session)
					throws HibernateException {
				List dacs = session.createCriteria(
						DicGoogssender.class).add(Example.create(dicGoogssender).enableLike(MatchMode.ANYWHERE))
						.list();
				return dacs;
			}
		});

		return dicGoogssenders;
	}

}
