/**
 * 
 */
package com.tlys.sys.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysDatafillinfo;


/**
 * @author 冯彦明
 * 
 */
@Repository
public class SysDatafillinfoDao extends _GenericDao<SysDatafillinfo> {

	public SysDatafillinfo load(Integer id) {
		SysDatafillinfo dfinfo = super.load(id);
		return dfinfo;
	}

	public List find() {
		return super.findAll();
	}
	
	

	/**
	 * 
	 * @param dfinfo
	 * @return
	 */
	public List<SysDatafillinfo> findAreaStList(SysDatafillinfo dfinfo) {
		DetachedCriteria dcr = DetachedCriteria.forClass(SysDatafillinfo.class);
		Example exDfinfo = Example.create(dfinfo);
		dcr.add(exDfinfo.excludeZeroes());
		
		List<SysDatafillinfo> dfinfos = getHibernateTemplate().findByCriteria(dcr);
		return dfinfos;
	}
	
	
	/**
	 * 调用存储过程
	 * @param nowDateStr
	 * @return
	 */
	public Object syncDatafill(final String nowDateStr) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = SessionFactoryUtils.getDataSource(
							getSessionFactory()).getConnection();
					String pname = "P_ZBC_SYS_GEN_DATAFILLINFO";
					String procedure = "{Call " + pname + "(?,?,?)}";
					cstmt = conn.prepareCall(procedure);
					cstmt.setString(1, nowDateStr);
					cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
					cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					cstmt.execute();
					int recode = cstmt.getInt(2);// 返回值代码
					String redesc = cstmt.getString(3);// 返回值描述
					if (log.isDebugEnabled()) {
						log.debug("PlaRtrainDao.syncPlaRtrain().new HibernateCallback() {...}.doInHibernate->recode=="
								+ recode);
						log.debug("PlaRtrainDao.syncPlaRtrain().new HibernateCallback() {...}.doInHibernate->redesc=="
								+ redesc);
					}
					cstmt.close();

					return redesc;
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
