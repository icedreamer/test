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
import com.tlys.dic.model.DicProdcategory;

@Repository
public class DicProdcategoryDao extends _GenericDao<DicProdcategory> {
	public List<DicProdcategory> find(final DicProdcategory DicProdcategory) {
		List<DicProdcategory> DicProdcategorys = (List<DicProdcategory>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicProdcategory> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(DicProdcategory.class)
								.add(
										Example.create(DicProdcategory).enableLike(
												MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return DicProdcategorys;
	}
}
