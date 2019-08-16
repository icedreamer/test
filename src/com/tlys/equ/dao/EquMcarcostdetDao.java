package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquMcarcostdet;

@Repository
public class EquMcarcostdetDao extends _GenericDao<EquMcarcostdet> {

	public void findEquMcarcostdet(final EquMcarcostdet equMcarcostdet,
			final PageCtr<EquMcarcostdet> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquMcarcostdet.class, "det");
				// buildCriteria(equMcarcostdet, criteria);
				criteria.addOrder(Order.asc("det.dicSinocorp.corpid"));
				criteria.addOrder(Order.asc("det.equCar.carno"));
				criteria.addOrder(Order.asc("det.month"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	private String buildCriteria(EquMcarcostdet equMcarcostdet, String sql) {
		if (null == equMcarcostdet) {
			return "";
		}
		String corpid = equMcarcostdet.getCorpid();
		String carno = equMcarcostdet.getCarno();
		String carnos = equMcarcostdet.getCarnos();
		String cartypeids = equMcarcostdet.getCartypeids();
		String year = equMcarcostdet.getYear();
		String rentcorpids = equMcarcostdet.getRentcorpids();
		String startDate = equMcarcostdet.getStartdate();
		String endDate = equMcarcostdet.getEnddate();
		if (null != corpid && !"".equals(corpid)) {
			sql += " and a.corpid = '" + corpid + "'";
		} else {
			sql += " and " + CommUtils.getCorpIds("a.corpid");
		}
		if (null != carno && !"".equals(carno)) {
			sql += " and a.carno = '" + carno + "'";
		}
		if (null != carnos && !"".equals(carnos)) {
			sql += " and a.carno in (" + CommUtils.StringJoinQuotation(carnos) + ")";
		}
		if (null != cartypeids && !"".equals(cartypeids)) {
			sql += " and b.cartypeid in (" + CommUtils.StringJoinQuotation(cartypeids) + ")";
		}
		if (null != year && !"".equals(year)) {
			sql += " and substr(a.month,0,4) = '" + year + "'";
		}
		if (null != rentcorpids && !"".equals(rentcorpids)) {
			sql += " and a.rentcorpid in (" + CommUtils.StringJoinQuotation(rentcorpids) + ")";
		} else {
			sql += " and " + CommUtils.getCorpIds("a.rentcorpid");
		}
		if (null != startDate && !"".equals(startDate)) {
			sql += " and a.month >= '" + startDate + "'";
		}
		if (null != endDate && !"".equals(endDate)) {
			sql += " and a.month <= '" + endDate + "'";
		}
		return sql;
	}

	public int getEquMcarcostdetCount(final EquMcarcostdet equMcarcostdet) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquMcarcostdet.class);
				// buildCriteria(equMcarcostdet, criteria);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	public void findEquMcarcostdets(final EquMcarcostdet equMcarcostdet,
			final PageCtr<Object[]> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				String sql = "select distinct a.corpid,a.carno,a.rentcorpid from EquMcarcostdet a,EquCar b where a.carno = b.carno ";
				sql = buildCriteria(equMcarcostdet, sql);
				sql += " order by a.corpid,a.carno,a.rentcorpid ";
				Query query = s.createQuery(sql);
				pageCtr.buildList(query);
				return null;
			}
		});
	}

	public List<Object[]> findEquMcarcostdets(final EquMcarcostdet equMcarcostdet) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<Object[]> doInHibernate(Session s) throws HibernateException, SQLException {
				String sql = "select distinct a.corpid,a.carno,a.rentcorpid from EquMcarcostdet a,EquCar b where a.carno = b.carno ";
				sql = buildCriteria(equMcarcostdet, sql);
				sql += " order by a.corpid,a.carno,a.rentcorpid ";
				Query query = s.createQuery(sql);
				return query.list();
			}
		});
	}

	public int getEquMcarcostdetsCount(final EquMcarcostdet equMcarcostdet) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				String sql = "select count(distinct a.corpid || a.carno || a.rentcorpid) from TB_ZBC_EQU_MCARCOSTDET a,TB_ZBC_EQU_CAR b where a.carno = b.carno  ";
				sql = buildCriteria(equMcarcostdet, sql);
				SQLQuery query = s.createSQLQuery(sql);
				// query.addEntity("a", EquMcarcostdet.class);
				Object o = query.uniqueResult();
				if (null != o) {
					return Integer.parseInt(o.toString());
				}
				return 0;
			}
		});
	}

	public List<String> findCars(final EquMcarcostdet equMcarcostdet) {
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquMcarcostdet.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("carno"));
				criteria.setProjection(projectionList);
				if (null != equMcarcostdet) {
					String corpid = equMcarcostdet.getCorpid();
					String month = equMcarcostdet.getMonth();
					if (null != corpid && !"".equals(corpid)) {
						criteria.add(Restrictions.eq("corpid", corpid));
					}
					if (null != month && !"".equals(month)) {
						criteria.add(Restrictions.eq("month", month));
					}
				}
				return criteria.list();
			}
		});
	}
}
