/**
 * 
 */
package com.tlys.sys.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysUsergroups;


/**
 * @author 冯彦明
 * 
 */
@Repository
public class SysUsergroupsDao extends _GenericDao<SysUsergroups> {
	public List findSysGroups() {
		return super.findAll();
	}
	
	/**
	 * 根据外键进行删除
	 * @param userid
	 */
	public void delByFk(String fkname,String fkval){
		final String hql = "delete SysUsergroups su where su."+fkname+"='"+fkval+"'";
		Object o = getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql);
						return query.executeUpdate();
					}
				});
	}
	
	public void saveOrUpdateAll(List gsList) {
		getHibernateTemplate().saveOrUpdateAll(gsList);
	}


}
