package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquYcheck;

@Repository
public class EquYcheckDao extends _GenericDao<EquYcheck> {
	public void findEquYcheck(final EquYcheck equYcheck, final PageCtr<EquYcheck> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(EquYcheck.class);
				buildCriteria(criteria, equYcheck);
				criteria.addOrder(Order.asc("corpfullname"));
				criteria.addOrder(Order.desc("createdtime"));
				criteria.addOrder(Order.desc("yinspectionno"));
				pageCtr.buildList(criteria);

				return null;
			}
		});
	}

	public void buildCriteria(Criteria criteria, EquYcheck equYcheck) {
		String corpid = null;
		String year = null;
		String regiternumber = null;
		String corpcode = null;
		String status = null;
		String areaid = null;
		if (null != equYcheck) {
			corpid = equYcheck.getCorpid();
			year = equYcheck.getYear();
			regiternumber = equYcheck.getRegiternumber();
			corpcode = equYcheck.getCorpcode();
			status = equYcheck.getStatus();
			areaid = equYcheck.getAreaid();
		}
		if (null != areaid && !"".equals(areaid)) {
			criteria.add(Restrictions.eq("areaid", areaid));
		}
		if (null != corpid && !"".equals(corpid)) {
			criteria.add(Restrictions.eq("corpid", corpid));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != year && !"".equals(year)) {
			criteria.add(Restrictions.eq("year", year));
		}
		if (null != regiternumber && !"".equals(regiternumber)) {
			criteria.add(Restrictions.eq("regiternumber", regiternumber));
		}
		if (null != corpcode && !"".equals(corpcode)) {
			criteria.add(Restrictions.eq("corpcode", corpcode));
		}
		if (null != status && !"".equals(status)) {
			criteria.add(Restrictions.eq("status", status));
		}
	}

	public int getEquYcheckCount(final EquYcheck equYcheck) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(EquYcheck.class);
				buildCriteria(criteria, equYcheck);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				if (null != o && !"".equals(o.toString())) {
					return Integer.parseInt(o.toString());
				}
				return 0;
			}
		});
	}

	public List<String> getYears() {
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select distinct a.year from EquYcheck a where "
						+ CommUtils.getCorpIds("a.corpid");
				Query query = session.createQuery(sql);
				return query.list();
			}
		});
	}
}
