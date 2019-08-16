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
import com.tlys.sys.model.SysUserroles;


/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class SysUserrolesDao extends _GenericDao<SysUserroles> {
	public List findSysRoles() {
		return super.findAll();
	}
	
	public void delByUserid(String userid){
		final String hql = "delete SysUserroles su where su.userid='"+userid+"'";
		Object o = getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql);
						return query.executeUpdate();
					}
				});
	}
	
	public void saveOrUpdateAll(List rsList) {
		getHibernateTemplate().saveOrUpdateAll(rsList);
	}


}
