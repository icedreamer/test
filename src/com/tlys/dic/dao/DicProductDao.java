/**
 * @author 郭建军
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
import com.tlys.dic.model.DicProduct;

@Repository
public class DicProductDao extends _GenericDao<DicProduct> {
	public List<DicProduct> find(final DicProduct DicProduct) {
		List<DicProduct> DicProducts = (List<DicProduct>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicProduct> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(DicProduct.class)
								.add(
										Example.create(DicProduct).enableLike(
												MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return DicProducts;
	}

	/**
	 * 查找指定sql(Tree:Oracle start with connect by prior)的实体集合
	 * 
	 * @param sql
	 * @return List
	 */
	public List<DicProduct> findTreeAll(final String sql) {
		List<DicProduct> Objects = (List<DicProduct>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List doInHibernate(Session session)
							throws HibernateException {
						List<DicProduct> dacs = session.createSQLQuery(sql)
								.addEntity("obj", DicProduct.class).list();
						return dacs;
					}
				});
		return Objects;
	}
}
