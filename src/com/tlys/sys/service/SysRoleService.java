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
	 * ����ǰ�û�������ɫ��δ���ڵĽ�ɫ��map��װ����
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
	 * ����ǰ��������ɫ��δ���ڵĽ�ɫ��map��װ����
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
	 * ����roleperm���еļ�¼
	 * @param rpOpraids
	 */
	public void updateRoleperm(String rpOpraids,String roleid){
		//��ɾ�����д˽�ɫ��Ȩ��
		sysRolepermDao.delByRoleid(roleid);
		//�ٲ����ɫȨ����Ϣ
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
	 * ������������������޸ĵ��ò�ͬ��DAO
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
	 * �����û�ID��ȡ��ɫID�б�
	 * @param userid
	 * @return
	 */
	public List<String> findRoleIdByUserId(String userid){
		return sysRoleDao.findRoleIdByUserId(userid);
	}
	/**
	 * �������id�б��ȡ��Щ�鱻����Ľ�ɫID�б�
	 * @param groupIdList
	 * @return
	 */
	public List<String> findRoleIdByGroupIdList(List<String> groupIdList){
		return sysRoleDao.findRoleIdByGroupIdList(groupIdList);
	}
}
