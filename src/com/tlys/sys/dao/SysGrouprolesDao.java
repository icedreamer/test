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
import com.tlys.sys.model.SysGrouproles;


/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class SysGrouprolesDao extends _GenericDao<SysGrouproles> {
	
	public void delByGroupid(String groupid){
		final String hql = "delete SysGrouproles gr where gr.groupid='"+groupid+"'";
		Object o = getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql);
						return query.executeUpdate();
					}
				});
	}
	
	public void saveOrUpdateAll(List<SysGrouproles> rsList) {
		getHibernateTemplate().saveOrUpdateAll(rsList);
	}


}
