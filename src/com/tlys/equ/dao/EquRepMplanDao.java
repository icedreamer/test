package com.tlys.equ.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquRepMplan;

/**
 * 检修计划
 * 
 * @author ccsong 2012-4-5 下午1:22:34
 */
@Repository
public class EquRepMplanDao extends _GenericDao<EquRepMplan> {
	@Autowired
	DicMap dicMap;

	public void findMonths(final EquRepMplan equRepPlan, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepMplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.distinct(Projections.property("month")));
				criteria.setProjection(projectionList);
				buildCriteria(equRepPlan, criteria);
				criteria.addOrder(Order.asc("month"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	/**
	 * 根据企业代码和月份查询相应检修计划(大修，中修，段修，厂修)
	 * 
	 * @param equRepPlan
	 * @param pageCtr
	 * @return
	 */
	public List<Object[]> findEquRepMplan(final EquRepMplan equRepPlan) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepMplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("corpid"));
				projectionList.add(Projections.property("corpshortname"));
				projectionList.add(Projections.property("rtypeid"));
				projectionList.add(Projections.property("datatype"));
				projectionList.add(Projections.sum("amount"));
				projectionList.add(Projections.groupProperty("corpid"));
				projectionList.add(Projections.groupProperty("corpshortname"));
				projectionList.add(Projections.groupProperty("rtypeid"));
				projectionList.add(Projections.groupProperty("datatype"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepPlan, criteria);
				return criteria.list();
			}
		});
	}

	public List<Object[]> findMonthsPlan(final EquRepMplan equRepPlan) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepMplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("corpid"));
				projectionList.add(Projections.property("corpshortname"));
				projectionList.add(Projections.property("rtypeid"));
				projectionList.add(Projections.property("datatype"));
				projectionList.add(Projections.property("month"));
				projectionList.add(Projections.sum("amount"));
				projectionList.add(Projections.groupProperty("rtypeid"));
				projectionList.add(Projections.groupProperty("datatype"));
				projectionList.add(Projections.groupProperty("month"));
				projectionList.add(Projections.groupProperty("corpid"));
				projectionList.add(Projections.groupProperty("corpshortname"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepPlan, criteria);
				return criteria.list();
			}
		});
	}

	public int getCorpsCount(final EquRepMplan equRepPlan) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepMplan.class);
				criteria.setProjection(Projections.countDistinct("corpid"));
				buildCriteria(equRepPlan, criteria);
				return criteria.uniqueResult();
			}
		});
	}

	public int getMonthsCount(final EquRepMplan equRepPlan) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepMplan.class);
				criteria.setProjection(Projections.countDistinct("month"));
				buildCriteria(equRepPlan, criteria);
				return criteria.uniqueResult();
			}
		});
	}

	private void buildCriteria(EquRepMplan equRepMplan, Criteria criteria) {
		String corpid = null;
		String[] corpids = null;
		String rtypeid = null;
		String month = null;
		String datatype = null;
		String year = null;
		if (null != equRepMplan) {
			corpids = equRepMplan.getCorpids();
			rtypeid = equRepMplan.getRtypeid();
			month = equRepMplan.getMonth();
			corpid = equRepMplan.getCorpid();
			datatype = equRepMplan.getDatatype();
			year = equRepMplan.getYear();
		}

		if (null != corpid && !"".equals(corpid)) {
			criteria.add(Restrictions.eq("corpid", corpid));
		}

		if (null != corpids && corpids.length > 0) {
			criteria.add(Restrictions.in("corpid", corpids));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != rtypeid && !"".equals(rtypeid)) {
			criteria.add(Restrictions.eq("rtypeid", rtypeid));
		}
		if (null != month && !"".equals(month)) {
			criteria.add(Restrictions.eq("month", month));
		}

		if (null != datatype && !"".equals(datatype)) {
			criteria.add(Restrictions.eq("datatype", datatype));
		}
		if (null != year && !"".equals(year)) {
			criteria.add(Restrictions.like("month", year, MatchMode.START));
		}
	}

	public Object[] call_P_ZBC_REP_GENPLAN(final String year) {
		return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Object[] o = new Object[2];
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
					String procedure = "{call P_ZBC_REP_GENPLAN(?,?,?)}";
					cstmt = conn.prepareCall(procedure);
					cstmt.setString(1, year);
					cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
					cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					cstmt.execute();
					o[0] = cstmt.getInt(2);// 返回值代码
					o[1] = cstmt.getString(3);// 返回值描述
					return o;
				} catch (Exception e) {
					logger.error("call P_ZBC_REP_GENCAR error.", e);
					return null;
				} finally {
					try {
						if (cstmt != null) {
							cstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (Exception e) {

					}
				}

			}
		});
	}

	public List<Object[]> findTotalAmountByRtypeidAndDatatype(final EquRepMplan equRepPlan) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepMplan.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("rtypeid"));
				projectionList.add(Projections.property("datatype"));
				projectionList.add(Projections.sum("amount"));
				projectionList.add(Projections.groupProperty("rtypeid"));
				projectionList.add(Projections.groupProperty("datatype"));
				criteria.setProjection(projectionList);
				buildCriteria(equRepPlan, criteria);
				return criteria.list();
			}
		});
	}

	/**
	 * 本月待检，超期未检柱状图
	 * 
	 * @return
	 */
	public List<Object[]> findThisMonthReps() {
		String corpids = CommUtils.getCorpIds();
		if (null != corpids && !"".equals(corpids)) {
			corpids = " and a.corpid in (" + CommUtils.getCorpIds() + ")";
		}
		final String hql = "select distinct b.shrinkname,a.datatype,sum(a.amount),b.corpid  "
				+ "from EquRepMplan a, DicSinocorp b where a.corpid = b.corpid AND a.month = to_char(sysdate,'yyyymm') "
				+ corpids
				+ " AND a.datatype in ('MP','EP') group by b.shrinkname,a.datatype,b.corpid having sum(a.amount) > 0 order by b.shrinkname ";

		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				return query.list();
			}
		});

	}
}
