/**
 * 
 */
package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysRole;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class SysRoleDao extends _GenericDao<SysRole> {

	public SysRole load(Integer id) {
		SysRole sysUser = super.load(id);
		return sysUser;
	}

	public List findSysRoles() {
		return super.findAll();
	}

	public String getSeq() {
		String sql = "select SEQ_TB_ZBC_SYS_ROLE.nextval from dual";
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		Object o = query.uniqueResult();
		this.releaseSession(session);
		return o.toString();
	}

	public List<SysRole> find(SysRole sysUser) {
		final Example exUser = Example.create(sysUser).ignoreCase();
		List<SysRole> sysUsers = super.findByExample(sysUser);
		return sysUsers;
	}

	/**
	 * 根据用户ID，查找用户未授予角色
	 * 
	 * @param userid
	 * @return
	 */
	public List<SysRole> findUserrolesNoSel(String userid) {
		String hql = "from SysRole sr where sr.roleid not in"
				+ "(select sur.roleid from SysUserroles sur where sur.userid='" + userid + "') order by rolename";

		// List rlist = this.find(hql, userid);
		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * 根据用户ID，查找用户所授予角色
	 * 
	 * @param userid
	 * @return
	 */
	public List<SysRole> findUserrolesSel(String userid) {
		String hql = "from SysRole sr where sr.roleid in"
				+ "(select sur.roleid from SysUserroles sur where sur.userid='" + userid + "') order by rolename";

		// List rlist = this.find(hql, userid);
		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * 根据用户ID，查找用户所授予的角色ID列表
	 * 
	 * @param userid
	 * @return
	 */
	public List<String> findRoleIdByUserId(String userid) {
		String hql = "select sur.roleid from SysUserroles sur where sur.userid='" + userid + "'";

		// List rlist = this.find(hql, userid);
		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * 查找当前组未授予角色
	 * 
	 * @param groupid
	 * @return
	 */
	public List<SysRole> findGrouprolesNoSel(String groupid) {
		String hql = "from SysRole sr where sr.roleid not in"
				+ "(select sgr.roleid from SysGrouproles sgr where sgr.groupid='" + groupid + "')";

		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * 查找当前组授予角色
	 * 
	 * @param groupid
	 * @return
	 */
	public List<SysRole> findGrouprolesSel(String groupid) {
		String hql = "from SysRole sr where sr.roleid in"
				+ "(select sgr.roleid from SysGrouproles sgr where sgr.groupid='" + groupid + "')";

		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	public List<String> findRoleIdByGroupIdList(final List<String> groupIdList){
		final String hql = "select a.roleid from SysRole a,SysGrouproles b where a.roleid = b.roleid and b.groupid in (:groupIdList) ";
		return (List<String>)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = s.createQuery(hql);
				query.setParameterList("groupIdList", groupIdList);
				return query.list();
			}
		});
	}
}
