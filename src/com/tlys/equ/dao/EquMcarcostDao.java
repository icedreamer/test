package com.tlys.equ.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.equ.model.EquMcarcost;
import com.tlys.equ.model.EquMcarcostdet;

@Repository
public class EquMcarcostDao extends _GenericDao<EquMcarcost> {

	public Object[] call_P_INIT_MCARCOST(final String corpid, final String month,
			final String creator) {
		return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Object[] o = new Object[2];
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
					String procedure = "{call P_INIT_MCARCOST(?,?,?,?,?)}";
					cstmt = conn.prepareCall(procedure);

					cstmt.setString(1, corpid);
					cstmt.setString(2, month);
					cstmt.setString(3, creator);
					cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
					cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
					cstmt.execute();
					o[0] = cstmt.getInt(4);// 返回值代码
					o[1] = cstmt.getString(5);// 返回值描述
					return o;
				} catch (Exception e) {
					logger.error("call P_INIT_MCARCOST error.", e);
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

	public List<String> findMonthsOfCorp(final Set<String> corpidSet) {
		final String sql = "select DISTINCT a.month from EquMcarcost a where (a.status = 0 or a.status = 2) and a.corpid in (:corpidSet) order by a.month ";
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("corpidSet", corpidSet);
				return query.list();
			}
		});

	}

	public List<EquMcarcost> findEquMcarcost(final List<String> monthlyidList) {
		final String sql = "from EquMcarcost a where a.monthlyid in (:monthlyidList)";
		return (List<EquMcarcost>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("monthlyidList", monthlyidList);
				return query.list();
			}
		});
	}

	public List<String> findMonths(final EquMcarcostdet equMcarcostdet) {
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				String year = equMcarcostdet.getYear();
				String startDate = equMcarcostdet.getStartdate();
				String endDate = equMcarcostdet.getEnddate();
				String corpid = equMcarcostdet.getCorpid();
				Criteria criteria = s.createCriteria(EquMcarcostdet.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.distinct(Projections.property("month")));
				criteria.setProjection(projectionList);
				if (null != corpid && !"".equals(corpid)) {
					criteria.add(Restrictions.eq("corpid", corpid));
				}
				if (null != year && !"".equals(year)) {
					criteria.add(Restrictions.sqlRestriction("substr(month,0,4) = '" + year + "'"));
				}
				if (null != startDate && !"".equals(startDate)) {
					criteria.add(Restrictions.ge("month", startDate));
				}
				if (null != endDate && !"".equals(endDate)) {
					criteria.add(Restrictions.le("month", endDate));
				}
				criteria.addOrder(Order.asc("month"));
				return criteria.list();
			}
		});
	}

	public List<String> findMonthsByCorpid(String corpid) {
		return getHibernateTemplate().find(
				"select distinct a.month from EquMcarcost a where a.corpid = ? ", corpid);
	}

	/**
	 * 查询已经存在的出租企业
	 * 
	 * @param equMcarcostdet
	 * @return
	 */
	public List<Object[]> findCorpids() {
		final String sql = "select distinct b.corpid,b.shortname from EquMcarcost a,DicSinocorp b where a.corpid = b.corpid and "
				+ CommUtils.getCorpIds("corpid");
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				return query.list();
			}
		});
	}

}
