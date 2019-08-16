/**
 * 
 */
package com.tlys.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.sys.dao.SysRoleDao;
import com.tlys.sys.dao.SysRolepermDao;
import com.tlys.sys.model.SysRole;
import com.tlys.sys.model.SysRoleperm;

/**
 * @author fengym
 *
 */
@Service
public class SysRoleService{
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	SysRoleDao sysRoleDao;
	@Autowired
	SysRolepermDao sysRolepermDao;
	
	
	
	public String getSeqStr(){
		String seq = sysRoleDao.getSeq();
		int seqLen = seq.length();
		if(6>seq.length()){
			for (int i = 0; i < 6-seqLen; i++) {
				seq = "0"+seq;
			}
		}
		return seq;
	}
	
	/**
	 * 将当前用户所属角色，未属于的角色用map组装起来
	 * @param userid
	 * @return
	 */
	public Map<String,List<SysRole>> findUserrolesSel(String userid){
		HashMap<String,List<SysRole>> rmap = new HashMap<String,List<SysRole>>();
		rmap.put("rNoSel", sysRoleDao.findUserrolesNoSel(userid));
		rmap.put("rSel", sysRoleDao.findUserrolesSel(userid));
		
		return rmap;
	}
	
	/**
	 * 将当前组所属角色，未属于的角色用map组装起来
	 * @param groupid
	 * @return
	 */
	public Map<String,List<SysRole>> findGrouprolesSel(String groupid){
		HashMap<String,List<SysRole>> rmap = new HashMap<String,List<SysRole>>();
		rmap.put("grNoSel", sysRoleDao.findGrouprolesNoSel(groupid));
		rmap.put("grSel", sysRoleDao.findGrouprolesSel(groupid));
		
		return rmap;
	}
	
	
	/**
	 * 更新roleperm表中的记录
	 * @param rpOpraids
	 */
	public void updateRoleperm(String rpOpraids,String roleid){
		//先删除所有此角色的权限
		sysRolepermDao.delByRoleid(roleid);
		//再插入角色权限信息
		String[] rpArr = rpOpraids.split(", ");
		ArrayList<SysRoleperm> rpList = new ArrayList<SysRoleperm>();
		for (int i = 0; i < rpArr.length; i++) {
			String opraid = rpArr[i];
			SysRoleperm rp = new SysRoleperm();
			rp.setOpraid(opraid);
			rp.setRoleid(roleid);
			rpList.add(rp);
		}
		sysRolepermDao.saveOrUpdateAll(rpList);
	}
	
	
	
	
	
	public List<SysRole> findAll(){
		String extHql = " order by rolename";
		return sysRoleDao.findAll(extHql);
	}
	public SysRole load(String id) {
		return sysRoleDao.load(id);
	}
	
	/**
	 * 保存操作，分新增和修改调用不同的DAO
	 * @param sysRole
	 */
	public void save(SysRole sysRole,boolean isNew) {
		if(true == isNew){
			sysRoleDao.saveOrUpdate(sysRole);
		}else{
			sysRoleDao.updateEntity(sysRole, sysRole.getRoleid());
		}
		
	}
	
	public void delete(String id) {
		sysRoleDao.delete(id);
		
	}
	
	public void deleteSysRolePermByOpraIds(List<String> opraIdList){
		sysRolepermDao.deleteSysRolePermByOpraIds(opraIdList);
	}
	/**
	 * 根据用户ID获取角色ID列表
	 * @param userid
	 * @return
	 */
	public List<String> findRoleIdByUserId(String userid){
		return sysRoleDao.findRoleIdByUserId(userid);
	}
	/**
	 * 根据组的id列表获取这些组被授予的角色ID列表
	 * @param groupIdList
	 * @return
	 */
	public List<String> findRoleIdByGroupIdList(List<String> groupIdList){
		return sysRoleDao.findRoleIdByGroupIdList(groupIdList);
	}
}
