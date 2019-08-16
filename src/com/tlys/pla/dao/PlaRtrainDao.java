package com.tlys.pla.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


/**
 * 此dao服务于请车及批复计划模块，用来调用存储过程
 * P_ZBC_PLAN_GENMUPRTRAIN、P_ZBC_PLAN_GENMSPRTRAIN、P_ZBC_PLAN_GENMOPRTRAIN
 * @author fengym
 *
 */
@Repository
public class PlaRtrainDao extends HibernateDaoSupport{
	protected final Log log = LogFactory.getLog(this.getClass());

	public Object syncPlaRtrain(final String nowMonth) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Object o[][] = new Object[3][2];
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = SessionFactoryUtils.getDataSource(
							getSessionFactory()).getConnection();
					String[] pnames = new String[]
							{"P_ZBC_PLAN_GENMUPRTRAIN",
							"P_ZBC_PLAN_GENMSPRTRAIN",
							"P_ZBC_PLAN_GENMOPRTRAIN"};
					for (int i = 0; i < pnames.length; i++) {
						String procedure = "{Call "+pnames[i]+"(?,?,?)}";
						cstmt = conn.prepareCall(procedure);
						cstmt.setString(1, nowMonth);
						cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
						cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
						cstmt.execute();
						o[i][0] = cstmt.getInt(2);// 返回值代码
						o[i][1] = cstmt.getString(3);// 返回值描述
						if (log.isDebugEnabled()) {
							log.debug("PlaRtrainDao.syncPlaRtrain().new HibernateCallback() {...}.doInHibernate->o[i][0]=="+o[i][0]);
							log.debug(pnames[i]+"PlaRtrainDao.syncPlaRtrain().new HibernateCallback() {...}.doInHibernate->o[i][0]=="+o[i][1]);
						}
						cstmt.close();
					}
					
					
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
						e.printStackTrace();
					}
				}

			}
		});
	}
}
