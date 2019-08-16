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
import com.tlys.dic.model.DicGoods;

/**
 * @author ¹ù½¨¾ü
 * 
 */
@Repository
public class DicGoodsDao extends _GenericDao<DicGoods> {
	public List<DicGoods> find(final DicGoods DicGoods) {
		List<DicGoods> DicGoodss = (List<DicGoods>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicGoods> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(
								DicGoods.class).add(
								Example.create(DicGoods).enableLike(
										MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return DicGoodss;
	}
}
