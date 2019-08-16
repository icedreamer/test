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
	 * ����ǰ�û������飬δ���ڵ�����map��װ����
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
	 * ����group-role���еļ�¼
	 * @param grsel
	 */
	public void updateGroupRoles(String[] grsel,String groupid){
		//��ɾ�����д��û�������Ϣ
		sysGrouprolesDao.delByGroupid(groupid);
		//�ٲ����µ��û�����Ϣ
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
	 * ���ҵ�ǰ��ɫ�������� 
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
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * @param sysGroup,isNew:��־��ǰ���������������޸Ĳ���
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
	 * �����û�ID��ȡ�����ڵ���
	 * @param userid
	 * @return
	 */
	public List<SysGroup> findGroupByUserId(String userid){
		return sysGroupDao.findUsergroupsSel(userid);
	}
}
