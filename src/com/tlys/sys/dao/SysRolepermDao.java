package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysRoleperm;

@Repository
public class SysRolepermDao extends _GenericDao<SysRoleperm> {
	public void deleteSysRolePermByOpraIds(final List<String> opraIdList) {
		if (null == opraIdList || opraIdList.isEmpty()) {
			return;
		}
		final String sql = "delete from SysRoleperm a where a.opraid in (:opraIdList) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("opraIdList", opraIdList);
				return query.executeUpdate();
			}
		});
	}
	
	public void delByRoleid(final String roleid){
		final String hql = "delete from SysRoleperm a where a.roleid=?";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, roleid);
				return query.executeUpdate();
			}
		});
	}
	
	public void saveOrUpdateAll(List rpList){
		getHibernateTemplate().saveOrUpdateAll(rpList);
	}
	
	public void deleteById(final String opraid){
		final String hql = "delete from SysRoleperm where opraid = ? ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, opraid);
				return query.executeUpdate();
			}
		});
	}
}
