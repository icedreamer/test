package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaYuprtamount;

@Repository
public class PlaYuprtamountDao extends _GenericDao<PlaYuprtamount> {

	public void find(final PlaYuprtamount plaYuprtamount, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaYuprtamount.class);
				buildCrit(ca, plaYuprtamount);
				ca.addOrder(Order.desc("year"));// 排序
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				ca.addOrder(Order.asc("productcategoryid"));// 排序
				ca.addOrder(Order.asc("productsecondid"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaYuprtamountCount(final PlaYuprtamount plaYuprtamount) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaYuprtamount.class);
				buildCrit(ca, plaYuprtamount);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	public List findSum(final PlaYuprtamount plaYuprtamount) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaYuprtamount.class);
				ca.setProjection(Projections.projectionList().add(Projections.sum("amount"))
						.add(Projections.sum("lyamount")).add(Projections.sum("varyamount")));

				buildCrit(ca, plaYuprtamount);

				List alist = ca.list();

				return alist;
			}
		});

	}

	public Object[] findSum(final String year) {
		return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaYuprtamount.class);
				ca.setProjection(Projections.projectionList().add(Projections.sum("amount"))
						.add(Projections.sum("lyamount")).add(Projections.sum("varyamount")));
				ca.add(Restrictions.eq("year", year));

				List<Object[]> alist = ca.list();
				if (alist != null && !alist.isEmpty()) {
					return alist.get(0);
				}
				return alist;
			}
		});
	}

	public List<Object[]> findYuprtAmountSum(final PlaYuprtamount plaYuprtamount) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(PlaYuprtamount.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.groupProperty("areaid"));
				projectionList.add(Projections.groupProperty("corpid"));
				projectionList.add(Projections.sum("amount")).add(Projections.sum("lyamount"))
						.add(Projections.sum("varyamount"));
				projectionList.add(Projections.groupProperty("corpshortname"));
				criteria.setProjection(projectionList);
				buildCrit(criteria, plaYuprtamount);
				criteria.addOrder(Order.asc("areaid"));
				criteria.addOrder(Order.asc("corpshortname"));
				return criteria.list();
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param ca
	 * @param plaYuprtamount
	 */
	private void buildCrit(Criteria ca, PlaYuprtamount plaYuprtamount) {
		if (null == plaYuprtamount) {
			return;
		}
		String year = plaYuprtamount.getYear();
		String areaids = plaYuprtamount.getAreaids();
		String corpids = plaYuprtamount.getCorpids();
		String productcategoryids = plaYuprtamount.getProductcategoryids();
		String productsecondids = plaYuprtamount.getProductsecondids();

		if (null != year && !"".equals(year)) {
			ca.add(Restrictions.eq("year", year));
		}
		if (null != areaids && !"".equals(areaids)) {
			ca.add(Restrictions.in("areaid", areaids.split(",")));
		}
		if (null != corpids && !"".equals(corpids)) {
			ca.add(Restrictions.in("corpid", corpids.split(",")));
		}

		if (null != productcategoryids && !"".equals(productcategoryids)) {
			ca.add(Restrictions.in("productcategoryid", productcategoryids.split(",")));
		}
		if (null != productsecondids && !"".equals(productsecondids)) {
			ca.add(Restrictions.in("productsecondid", productsecondids.split(",")));
		}

		ca.add(Example.create(plaYuprtamount).enableLike(MatchMode.ANYWHERE));

	}

	public List<PlaYuprtamount> find(final PlaYuprtamount plaYuprtamount) {
		return (List<PlaYuprtamount>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List<PlaYuprtamount> list = new ArrayList<PlaYuprtamount>();
				Criteria ca = session.createCriteria(PlaYuprtamount.class);
				buildCrit(ca, plaYuprtamount);
				ca.addOrder(Order.desc("year"));// 排序
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				ca.addOrder(Order.asc("productcategoryid"));// 排序
				ca.addOrder(Order.asc("productsecondid"));// 排序
				return ca.list();
			}
		});
	}

	public List<PlaYuprtamount> findPlaYuprtamount(final String year, final List<String> corpIdList,
			final List<String> productCategoryIdList, final List<String> productSecondIdList,
			final List<String> rwkindIdList) {
		if (corpIdList == null || corpIdList.isEmpty() || productCategoryIdList == null
				|| productCategoryIdList.isEmpty() || productSecondIdList == null || productSecondIdList.isEmpty()
				|| rwkindIdList == null || rwkindIdList.isEmpty()) {
			return null;
		}

		return (List<PlaYuprtamount>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(PlaYuprtamount.class);

				criteria.add(Restrictions.in("productcategoryid", productCategoryIdList));
				criteria.add(Restrictions.in("productsecondid", productSecondIdList));
				// criteria.add(Restrictions.in("rwkindid", rwkindIdList));
				criteria.add(Restrictions.eq("year", year));
				return criteria.list();
			}
		});
	}

	public List<Object[]> findPlaYuprtamountSubTotal(final String year) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(PlaYuprtamount.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.groupProperty("areaid"));
				projectionList.add(Projections.groupProperty("corpid"));
				projectionList.add(Projections.sum("amount")).add(Projections.sum("lyamount"))
						.add(Projections.sum("varyamount"));
				projectionList.add(Projections.groupProperty("corpshortname"));
				criteria.setProjection(projectionList);
				criteria.add(Restrictions.eq("year", year));
				criteria.addOrder(Order.asc("areaid"));
				criteria.addOrder(Order.asc("corpshortname"));
				return criteria.list();
			}
		});
	}

}
