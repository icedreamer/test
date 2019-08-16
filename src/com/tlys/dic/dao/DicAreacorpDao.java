/**
 * 
 */
package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicAreacorp;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicAreacorpDao extends _GenericDao<DicAreacorp> {

	public DicAreacorp load(String id) {
		DicAreacorp dicAreacorp = super.load(id);
		return dicAreacorp;
	}

	
	public List<DicAreacorp> find(final DicAreacorp dicAreacorp) {
		List<DicAreacorp> dicAreacorps = (List<DicAreacorp>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicAreacorp> doInHibernate(Session session)
					throws HibernateException {
				List dacs = session.createCriteria(
						DicAreacorp.class).add(Example.create(dicAreacorp).enableLike(MatchMode.ANYWHERE))
						.list();
				return dacs;
			}
		});

		return dicAreacorps;
	}
	public List<DicAreacorp> findById(final DicAreacorp dicAreacorp) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(DicAreacorp.class);
				String areaid = dicAreacorp.getAreaid();
				if (null != areaid && !"".equals(areaid))
					ca.add(Restrictions.eq("areaid", areaid));
				return ca.list();
			}
		});
	}

}
