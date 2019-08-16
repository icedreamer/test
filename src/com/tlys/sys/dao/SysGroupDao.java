/**
 * 
 */
package com.tlys.sys.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysGroup;


/**
 * @author 冯彦明
 * 
 */
@Repository
public class SysGroupDao extends _GenericDao<SysGroup> {

	public SysGroup load(Integer id) {
		SysGroup sysUser = super.load(id);
		return sysUser;
	}

	public List findSysGroups() {
		return super.findAll();
	}
	
	public String getSeq() {
		String sql = "select SEQ_TB_ZBC_SYS_GROUP.nextval from dual";
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		Object o = query.uniqueResult();
		this.releaseSession(session);
		return o.toString();
	}

	public List<SysGroup> find(SysGroup sysGroup) {
		final Example exUser = Example.create(sysGroup).ignoreCase();
		List<SysGroup> sysGroups = super.findByExample(sysGroup);
		return sysGroups;
	}

	/**
	 * 查找当前用户未属于的组
	 * @param userid
	 * @return
	 */
	public List<SysGroup> findUsergroupsNoSel(String userid){
		String hql = "from SysGroup sg where sg.groupid not in" +
				"(select sug.groupid from SysUsergroups sug where sug.userid='"+userid+"') order by groupname";
		
		//List rlist = this.find(hql, userid);
		List rlist = this.getHibernateTemplate().find(hql);
		
		return rlist;
	}
	
	/**
	 * 查找当前用户属于的组
	 * @param userid
	 * @return
	 */
	public List<SysGroup> findUsergroupsSel(String userid){
		String hql = "from SysGroup sg where sg.groupid in" +
				"(select sug.groupid from SysUsergroups sug where sug.userid='"+userid+"') order by groupname";
		
		//List rlist = this.find(hql, userid);
		List rlist = this.getHibernateTemplate().find(hql);
		
		return rlist;
	}
	
	/**
	 * 查找当前角色所包含的组
	 * @param roleid
	 * @return
	 */
	public List<SysGroup> findRoleGroups(String roleid){
		String hql = "from SysGroup sg where sg.groupid in" +
				"(select sgr.groupid from SysGrouproles sgr where sgr.roleid='"+roleid+"')";
	
		List<SysGroup> rlist = this.getHibernateTemplate().find(hql);
		
		return rlist;
	}

}
