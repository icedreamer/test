/**
 * @author 郭建军
 */
package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicCorprailway;

@Repository
public class DicCorprailwayDao extends _GenericDao<DicCorprailway> {
	public List<DicCorprailway> find(final DicCorprailway DicCorprailway) {
		List<DicCorprailway> DicCorprailways = (List<DicCorprailway>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicCorprailway> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session
								.createCriteria(DicCorprailway.class)
								.add(
										Example.create(DicCorprailway)
												.enableLike(MatchMode.ANYWHERE))
								.list();
						return dacs;
					}
				});

		return DicCorprailways;
	}

	public void findPageCtr(final DicCorprailway obj, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(DicCorprailway.class);
				ca.add(Example.create(obj).enableLike(MatchMode.ANYWHERE));
				ca.addOrder(Order.asc("bureauid"));
				pageCtr.buildList(ca);
				return null;
			}
		});
	}

	public int getObjCount(final DicCorprailway obj) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Criteria ca = s.createCriteria(DicCorprailway.class);
						ca.add(Example.create(obj).enableLike(MatchMode.ANYWHERE));
						ca.setProjection(Projections.rowCount());
						Object o = ca.uniqueResult();
						return Integer.parseInt(o.toString());
					}
				});

	}
	/**
	 * 查询表 TB_ZBC_DIC_CORPRAILWAY 中的CRWID 最大值
	 * @return
	 */
	public String getMaxCrwid(){
		
		return (String)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				
				Criteria criteria = s.createCriteria(DicCorprailway.class);
				
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.max("crwid"));
				criteria.setProjection(projectionList);
				
				return criteria.uniqueResult();
			}
		});
	}
	
}
