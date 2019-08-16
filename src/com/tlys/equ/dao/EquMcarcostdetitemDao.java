package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.equ.model.EquMcarcostdet;
import com.tlys.equ.model.EquMcarcostdetitem;

@Repository
public class EquMcarcostdetitemDao extends _GenericDao<EquMcarcostdetitem> {

	public List<EquMcarcostdetitem> findEquMcarcostdetitem(final EquMcarcostdet equMcarcostdet,
			final List<String> carnoList) {

		return (List<EquMcarcostdetitem>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquMcarcostdetitem.class);
				if (null != carnoList && !carnoList.isEmpty()) {
					criteria.add(Restrictions.in("carno", carnoList));
				}
				String costids = equMcarcostdet.getCostids();
				String costid = equMcarcostdet.getCostid();
				String corpid = equMcarcostdet.getCorpid();
				String year = equMcarcostdet.getYear();
				String startDate = equMcarcostdet.getStartdate();
				String endDate = equMcarcostdet.getEnddate();
				if (null != costids && !"".equals(costids)) {
					criteria.add(Restrictions.in("costid", costids.split(",")));
				}
				if (null != costid && !"".equals(costid)) {
					criteria.add(Restrictions.eq("costid", costid));
				}
				if (null != corpid && !"".equals(corpid)) {
					criteria.add(Restrictions.eq("dicSinocorp.corpid", corpid));
				}

				if (null != startDate && !"".equals(startDate)) {
					// sql += " and a.month >= '" + startDate + "'";
					criteria.add(Restrictions.ge("month", startDate));
				}
				if (null != endDate && !"".equals(endDate)) {
					// sql += " and a.month <= '" + endDate + "'";
					criteria.add(Restrictions.le("month", endDate));
				}
				if (null != year && !"".equals(year)) {
					criteria.add(Restrictions.sqlRestriction("substr(month,0,4) = '"
							+ equMcarcostdet.getYear() + "'"));
				}

				criteria.addOrder(Order.asc("dicSinocorp.corpid"));
				criteria.addOrder(Order.asc("carno"));
				criteria.addOrder(Order.asc("month"));
				criteria.addOrder(Order.asc("costid"));
				return criteria.list();
			}
		});
	}

	public List<EquMcarcostdetitem> findEquMcarcostdetitem(final List<String> detidList,
			final String costids) {
		if (null == detidList || detidList.isEmpty()) {
			return null;
		}
		return (List<EquMcarcostdetitem>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session s) throws HibernateException, SQLException {
				final Criteria criteria = s.createCriteria(EquMcarcostdetitem.class);
				criteria.add(Restrictions.in("detid", detidList));
				if (null != costids && !"".equals(costids)) {
					criteria.add(Restrictions.in("costid", costids.split(",")));
				}
				criteria.addOrder(Order.asc("dicSinocorp.corpid"));
				criteria.addOrder(Order.asc("carno"));
				criteria.addOrder(Order.asc("month"));
				criteria.addOrder(Order.asc("costid"));
				return criteria.list();
			}
		});
	}

	public List<EquMcarcostdetitem> findEquMcarcostdetitem() {
		final String sql = "select a from EquMcarcostdetitem a ,EquMcarcostdet b where a.detid = b.detid and ("
				+ CommUtils.getCorpIds("b.dicSinocorp.corpid ")
				+ " or "
				+ CommUtils.getCorpIds("b.rentcorpid") + ") order by a.detid,a.costid ";
		return (List<EquMcarcostdetitem>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				return query.list();
			}
		});
	}

	public List<EquMcarcostdetitem> findEquMcarcostdetitem(String detid) {
		return getHibernateTemplate().find("from EquMcarcostdetitem a where a.detid = ? ", detid);
	}

	public EquMcarcostdetitem getEquMcarcostdetitem(final String detid, final String costid) {
		return (EquMcarcostdetitem) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquMcarcostdetitem.class);
				criteria.add(Restrictions.eq("detid", detid));
				criteria.add(Restrictions.eq("costid", costid));
				List<EquMcarcostdetitem> list = criteria.list();
				if (null != list && !list.isEmpty()) {
					return (EquMcarcostdetitem) list.get(0);
				}
				return null;
			}
		});
	}
}
