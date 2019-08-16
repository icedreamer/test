package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.dic.model.DicReparetype;
import com.tlys.equ.model.EquRepRecord;

@Repository
public class EquRepRecordDao extends _GenericDao<EquRepRecord> {

	public List<EquRepRecord> findEquRepRecord() {
		final String hql = "select a from EquRepRecord a ,EquCar b , DicReparetype c "
				+ "where trim(a.carno) = trim(b.carno) and a.dicReparetype.rtypeid = c.rtypeid and "
				+ CommUtils.getCorpIds("corpid")
				+ " order by b.corpid,c.suitkind,a.dicReparetype.rtypeid,trim(a.carno)";
		return (List<EquRepRecord>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				List<EquRepRecord> list = query.list();
				return list;
			}
		});
	}

	public List<EquRepRecord> findEquRepRecord(final String carno) {
		final String hql = "select a from EquRepRecord a,DicReparetype b "
				+ "where a.dicReparetype.rtypeid = b.rtypeid and TRIM(a.carno) = ? order by b.suitkind,b.rtypeid,a.active,a.nextrepdate desc ";
		return (List<EquRepRecord>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, carno);
				return query.list();
			}
		});
	}

	public List<EquRepRecord> getEquRepRecord(final String carno, final String rtypeid) {
		final String hql = "select a from EquRepRecord a where a.dicReparetype.rtypeid = ? and TRIM(a.carno) = ? order by a.nextrepdate desc,a.active ";
		return (List<EquRepRecord>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, rtypeid);
				query.setString(1, carno);
				return query.list();
			}
		});
	}

	public List<EquRepRecord> findEquRepRecord(final String carno, final String suitkind) {
		final String hql = "select a from EquRepRecord a,DicReparetype b "
				+ "where b.rtypeid = a.dicReparetype.rtypeid and TRIM(a.carno) = ? and b.suitkind = ? order by a.dicReparetype.rtypeid ";
		return (List<EquRepRecord>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, carno);
				query.setString(1, suitkind);
				return query.list();
			}
		});
	}

	public EquRepRecord getEquRepRecord(final String rtypeid, final String carno, final String repflag) {
		return (EquRepRecord) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				StringBuilder buffer = new StringBuilder();
				buffer.append("select a from EquRepRecord a where trim(a.carno) = ? and a.dicReparetype.rtypeid = ? ");
				if (null != repflag && !"".equals(repflag)) {
					String condition = "";
					if (repflag.equals("00")) {
						condition = " and to_char(a.nextrepdate,'yyyy-mm') > to_char(sysdate,'yyyy-mm') and a.active = 1 ";
					} else if (repflag.equals("01")) {
						condition = "";
					} else if (repflag.equals("02")) {

					}
					buffer.append(condition);
				}
				buffer.append(" order by a.active,a.nextrepdate desc ");
				Query query = s.createQuery(buffer.toString());
				query.setString(0, carno);
				query.setString(1, rtypeid);
				query.setMaxResults(1);
				return (EquRepRecord) query.uniqueResult();
			}
		});
	}

	public EquRepRecord getPrevEquRepRecord(final String rtypeid, final String carno) {
		return (EquRepRecord) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepRecord.class);
				if (null != rtypeid && !"".equals(rtypeid)) {
					criteria.add(Restrictions.eq("dicReparetype.rtypeid", rtypeid));
				}
				criteria.add(Restrictions.eq("active", "1"));
				criteria.add(Restrictions.sqlRestriction("trim(carno) = ? ", carno, Hibernate.STRING));
				criteria.addOrder(Order.desc("nextrepdate"));
				criteria.setMaxResults(1);
				return (EquRepRecord) criteria.uniqueResult();
			}
		});
	}

	public List<Object> findCarnos(final EquRepRecord equRepRecord) {
		return (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepRecord.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("carno"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepRecord, criteria);
				return criteria.list();
			}
		});
	}

	private void buildCriteria(EquRepRecord equRepRecord, Criteria criteria) {
		String corpid = null;
		String month = null;
		String rtypeid = null;
		String year = null;
		String repkind = null;
		if (null != equRepRecord) {
			corpid = equRepRecord.getCorpid();
			month = equRepRecord.getMonth();
			DicReparetype dicReparetype = equRepRecord.getDicReparetype();
			if (null != dicReparetype) {
				rtypeid = dicReparetype.getRtypeid();
			}
			year = equRepRecord.getYear();
			repkind = equRepRecord.getRepkind();
		}
		if (null != corpid && !"".equals(corpid)) {
			criteria.add(Restrictions.eq("corpid", corpid));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != rtypeid && !"".equals(rtypeid)) {
			criteria.add(Restrictions.eq("dicReparetype.rtypeid", rtypeid));
		}
		String month_sql = "";
		String year_sql = "";
		if (null != repkind && !"".equals(repkind)) {
			if (repkind.equals("MP")) {
				// 本月待检
				month_sql = "to_char(nextrepdate,'yyyymm') = '" + month + "'";
				year_sql = "to_char(nextrepdate,'yyyy') = '" + year + "'";
			} else if (repkind.equals("MF")) {
				// 本月完成
				month_sql = "to_char(finishdate,'yyyymm') = '" + month + "'";
				year_sql = "to_char(finishdate,'yyyy') = '" + year + "'";
			} else if (repkind.equals("EP")) {
				// 超期未检
				month_sql = "to_char(nextrepdate,'yyyymm') < '" + month + "'";
				year_sql = "to_char(nextrepdate,'yyyy') < '" + year + "'";
			}
		}
		if (null != month && !"".equals(month)) {
			criteria.add(Restrictions.sqlRestriction(month_sql));
		}
		if (null != year && !"".equals(year)) {
			criteria.add(Restrictions.sqlRestriction(year_sql));
		}
		criteria.add(Restrictions.eq("active", "1"));
		criteria.add(Restrictions.eq("isrecently", "1"));
	}

	public List<Object[]> findRepRecord(final String corpid, final String year) {
		final String hql = "select a.corpid,a.carno,a.finishdate from EquRepRecord a where a.corpid = ? and to_char(a.finishdate,'yyyy') = ? and a.isrecently = 1 and a.active = 1 order by a.carno,a.finishdate";
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = s.createQuery(hql);
				query.setString(0, corpid);
				query.setString(1, year);
				return query.list();
			}
		});
	}
}