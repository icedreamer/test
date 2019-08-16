package com.tlys.pla.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.pla.model.PlaAmupstransport;

@Repository
public class PlaAmupstransportDao extends _GenericDao<PlaAmupstransport> {
	public List find(final PlaAmupstransport PlaAmupstransport) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaAmupstransport.class);
				buildCrit(ca, PlaAmupstransport);
				ca.addOrder(Order.desc("month"));
				ca.addOrder(Order.asc("areaid"));
				return ca.list();
			}
		});
	}

	public Object syncPlaAmup(final String monthStr) {
		
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// Connection conn
				// =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
				Object o[] = new Object[2];
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = SessionFactoryUtils.getDataSource(
							getSessionFactory()).getConnection();

					String procedure = "{Call P_ZBC_PLAN_GENAMUP_OLDPALNID(?,?,?)}";
					cstmt = conn.prepareCall(procedure);
					//cstmt.setString(1, "201202");
					cstmt.setString(1, monthStr);
					cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
					cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					cstmt.execute();
					o[0] = cstmt.getInt(2);// 返回值代码
					o[1] = cstmt.getString(3);// 返回值描述
					return o;
				} catch (Exception e) {
					e.printStackTrace();
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

	/**
	 * 试图使用session直接执行存储过程，未成功
	 * 
	 * @return
	 */
	public Object syncPlaAmup2() {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				SQLQuery query = session
						.createSQLQuery("{Call P_ZBC_PLAN_GENAMUP_OLDPALNID(?)}");
				query.setString(0, "201202");
				List reList = query.list();
				// query.executeUpdate();

				return null;

			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param PlaAmupstransport
	 */
	private void buildCrit(Criteria crit, PlaAmupstransport PlaAmupstransport) {
		if (null != PlaAmupstransport) {
			String statuss = PlaAmupstransport.getStatuss();
			String expdateStart = PlaAmupstransport.getMonthStart();
			String expdateEnd = PlaAmupstransport.getMonthEnd();
			String areaid = PlaAmupstransport.getAreaid();
			String plannos = PlaAmupstransport.getPlannos();
			if (null != statuss && statuss.length() > 0) {
				crit.add(Restrictions.in("status", statuss.split(",")));
			}
			if (null != areaid && !"".equals(areaid)) {
				crit.add(Restrictions.eq("areaid", areaid));
			}
			if (null != expdateStart && !"".equals(expdateStart)) {
				expdateStart = expdateStart.replace("-", "");
				crit.add(Restrictions.ge("month", expdateStart));
			}
			if (null != expdateEnd && !expdateEnd.equals("")) {
				expdateEnd = expdateEnd.replace("-", "");
				crit.add(Restrictions.le("month", expdateEnd));
			}
			if (null != plannos && plannos.length() > 0) {
				crit.add(Restrictions.in("planno", plannos.split(",")));
			}
		}
	}

}
