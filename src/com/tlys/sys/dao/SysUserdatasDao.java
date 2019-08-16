/**
 * 
 */
package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysUserdatas;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class SysUserdatasDao extends _GenericDao<SysUserdatas> {
	public List findAll() {
		return super.findAll();
	}

	/**
	 * 根据外键进行删除
	 * 
	 * @param userid
	 */
	public void delByFk(String fkname, String fkval) {
		final String hql = "delete SysUserdatas su where su." + fkname + "='" + fkval + "'";
		Object o = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				return query.executeUpdate();
			}
		});
	}
	
	public void delete(final String userid,final int menuid){
		final String hql = "delete from SysUserdatas where userid = ? and menuid = ? ";
		getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, userid);
				query.setInteger(1, menuid);
				return query.executeUpdate();
			}
		});
	}

	public void saveOrUpdateAll(List gsList) {
		getHibernateTemplate().saveOrUpdateAll(gsList);
	}

	public List<SysUserdatas> findSysUserdatas(String userid) {
		return getHibernateTemplate().find("from SysUserdatas where userid = ? ", userid);
	}

	public List<String> findSysUserdataCorpIds(String userid) {
		return getHibernateTemplate().find("select datacorpid from SysUserdatas where userid = ? ", userid);
	}

	public List<SysUserdatas> findSysUserdatas(String userid, int menuid) {
		return getHibernateTemplate().find("from SysUserdatas where userid = ? and menuid = ? ",
				new Object[] { userid, menuid });
	}

}
