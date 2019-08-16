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
import com.tlys.dic.model.DicGoodscategory;

/**
 * @author guojj
 * 
 */
@Repository
public class DicGoodscategoryDao extends _GenericDao<DicGoodscategory> {

	public DicGoodscategory load(String id) {
		DicGoodscategory DicGoodscategory = super.load(id);
		return DicGoodscategory;
	}

	public List<DicGoodscategory> find(final DicGoodscategory DicGoodscategory) {
		List<DicGoodscategory> DicGoodscategorys = (List<DicGoodscategory>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicGoodscategory> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(DicGoodscategory.class).add(
								Example.create(DicGoodscategory).enableLike(MatchMode.ANYWHERE))
								.list();
						return dacs;
					}
				});

		return DicGoodscategorys;
	}

	public List<DicGoodscategory> findDicGoodscategory(final List<String> goodsidList) {
		if (null == goodsidList || goodsidList.isEmpty()) {
			return null;
		}
		return (List<DicGoodscategory>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(DicGoodscategory.class);
				criteria.add(Restrictions.in("goodsid", goodsidList));
				return criteria.list();
			}
		});
	}

}
