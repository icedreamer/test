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

import com.tlys.sys.dao.SysGroupDao;
import com.tlys.sys.dao.SysGrouprolesDao;
import com.tlys.sys.model.SysGroup;
import com.tlys.sys.model.SysGrouproles;

/**
 * @author fengym
 *
 */
@Service
public class SysGroupService{
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	SysGroupDao sysGroupDao;
	@Autowired
	SysGrouprolesDao sysGrouprolesDao;
	
	public String getSeqStr(){
		String seq = sysGroupDao.getSeq();
		int seqLen = seq.length();
		if(6>seq.length()){
			for (int i = 0; i < 3-seqLen; i++) {
				seq = "0"+seq;
			}
		}
		return seq;
	}
	
	/**
	 * 将当前用户所属组，未属于的组用map组装起来
	 * @param userid
	 * @return
	 */
	public Map<String,List<SysGroup>> findUsergroupsSel(String userid){
		HashMap<String,List<SysGroup>> gmap = new HashMap<String,List<SysGroup>>();
		gmap.put("gNoSel", sysGroupDao.findUsergroupsNoSel(userid));
		gmap.put("gSel", sysGroupDao.findUsergroupsSel(userid));

		return gmap;
	}
	
	/**
	 * 更新group-role表中的记录
	 * @param grsel
	 */
	public void updateGroupRoles(String[] grsel,String groupid){
		//先删除所有此用户的组信息
		sysGrouprolesDao.delByGroupid(groupid);
		//再插入新的用户组信息
		ArrayList<SysGrouproles> grList = new ArrayList<SysGrouproles>();
		for (int i = 0; i < grsel.length; i++) {
			String rs = grsel[i];
			SysGrouproles gr = new SysGrouproles();
			gr.setRoleid(rs);
			gr.setGroupid(groupid);
			grList.add(gr);
		}
		sysGrouprolesDao.saveOrUpdateAll(grList);
		
	}
	/**
	 * 查找当前角色包含的组 
	 * @param roleid
	 * @return
	 */
	public List<SysGroup> findRoleGroups(String roleid){
		return sysGroupDao.findRoleGroups(roleid);
	}
	
	public List<SysGroup> findAll(){
		String extHql = " order by groupname";
		return sysGroupDao.findAll(extHql);
	}
	public SysGroup load(String id) {
		return sysGroupDao.load(id);
	}
	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * @param sysGroup,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(SysGroup sysGroup,boolean isNew) {
		if(true==isNew){
			sysGroupDao.saveOrUpdate(sysGroup);
		}else{
			sysGroupDao.updateEntity(sysGroup, sysGroup.getGroupid());
		}
		
		
	}
	
	public void delete(String id) {
		sysGroupDao.delete(id);
		
	}
	/**
	 * 根据用户ID获取所属于的组
	 * @param userid
	 * @return
	 */
	public List<SysGroup> findGroupByUserId(String userid){
		return sysGroupDao.findUsergroupsSel(userid);
	}
}
