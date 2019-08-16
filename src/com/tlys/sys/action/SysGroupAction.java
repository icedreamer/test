/**
 * 
 */
package com.tlys.sys.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.sys.model.SysGroup;
import com.tlys.sys.model.SysRole;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysGroupService;
import com.tlys.sys.service.SysRoleService;
import com.tlys.sys.service.SysUserService;

/**
 * @author ������
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/sys")
public class SysGroupAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private SysGroup sysGroup;
	private List<SysGroup> sysGroups;
	private String id;
	
	/**
	 * ��ǰ�����������û�
	 */
	private List<SysUser> sysUsers;
	private Map<String,List<SysUser>> sysUsersMap;
	
	private String[] grsel;
	private String[] gusel;
	
	/**
	 * ��������ѡ��ɫ��δѡ��ɫ�б�
	 */
	private Map<String,List<SysRole>> grmap;
	
	@Autowired
	SysGroupService sysGroupService;
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	SysUserService sysUserService;

	
	@Action(value="sysGroup",results=
	{@Result(name = "list", location="/sys/sys-rgu-left.jsp")}) 
	public String list(){
		sysGroups = sysGroupService.findAll();
		return "list";
	} 
	
	public String edit(){
		if(null!=id){
			detail();
		}else{
			sysGroup=null;
		}
		return "input";
	} 
    
    public String detail(){
    	this.sysGroup = sysGroupService.load(id);
    	grmap = sysRoleService.findGrouprolesSel(id);
    	//sysUsers = sysUserService.findGroupUsers(id);
    	sysUsersMap = sysUserService.findGroupUsersInAll(id);
    	
    	
    	//System.out.println("SysGroupAction.detail->rmap=="+rmap);
    	return "detail";
    } 

    public String save() throws Exception{
    	if(null==sysGroup){
    		throw new Exception("������δ���յ���");
		} else {
			boolean isNew = false;
			/**
			 * ��������ʱ
			 */
			if(null==sysGroup.getGroupid() || "".equals(sysGroup.getGroupid())){
				String seqStr = sysGroupService.getSeqStr();
				sysGroup.setGroupid(seqStr);
				isNew = true;
			}
			sysGroupService.save(sysGroup,isNew);
			
			//��������msgҳ����ʾ������
			
			msg = new Msg(Msg.SUCCESS,
					"���/�༭��ɹ���",
					false,
					"parent.leftFrame.location.reload();",
					new String[]{"curTitle","��"});
		}
    	return MSG;
    } 
    
    /**
     * ���µ�ǰ�������Ľ�ɫ��Ϣ
     * @return
     */
    public String updgr() throws Exception{
    	sysGroupService.updateGroupRoles(grsel,id);
    	msg= new Msg(Msg.SUCCESS,"���³ɹ���");
    	return MSG;
    }
    
    /**
     * ���µ�ǰ�����������û�
     * @return
     */
    public String updUsers(){
    	try{
    		sysUserService.updateUserGroup(gusel,id,"groupid");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	msg= new Msg(Msg.SUCCESS,"���³ɹ�����","$('#userlist-dialog').dialog('close');window.location.reload()");
    	return MSG;
    }
    
    public String delete() throws Exception{
    	if(null==id){
    		throw new Exception("����ȷ��Ҫɾ���Ķ���");
    	}else{
    		sysGroupService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"ɾ������ɹ���",
					false,
					"parent.leftFrame.location.reload();",
					new String[]{"curTitle","��"});
    	}
    	return MSG;
    }
    
    
    
    //=================================  

	/**
	 * @return the sysGroup
	 */
	public SysGroup getSysGroup() {
		return sysGroup;
	}

	/**
	 * @param sysGroup the sysGroup to set
	 */
	public void setSysGroup(SysGroup sysGroup) {
		this.sysGroup = sysGroup;
	}

	/**
	 * @return the sysGroups
	 */
	public List<SysGroup> getSysGroups() {
		return sysGroups;
	}

	/**
	 * @param sysGroups the sysGroups to set
	 */
	public void setSysGroups(List<SysGroup> sysGroups) {
		this.sysGroups = sysGroups;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the grmap
	 */
	public Map<String, List<SysRole>> getGrmap() {
		return grmap;
	}

	/**
	 * @param grmap the grmap to set
	 */
	public void setGrmap(Map<String, List<SysRole>> grmap) {
		this.grmap = grmap;
	}

	/**
	 * @return the grsel
	 */
	public String[] getGrsel() {
		return grsel;
	}

	/**
	 * @param grsel the grsel to set
	 */
	public void setGrsel(String[] grsel) {
		this.grsel = grsel;
	}

	/**
	 * @return the sysUsers
	 */
	public List<SysUser> getSysUsers() {
		return sysUsers;
	}

	/**
	 * @param sysUsers the sysUsers to set
	 */
	public void setSysUsers(List<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	/**
	 * @return the sysUsersMap
	 */
	public Map<String, List<SysUser>> getSysUsersMap() {
		return sysUsersMap;
	}

	/**
	 * @param sysUsersMap the sysUsersMap to set
	 */
	public void setSysUsersMap(Map<String, List<SysUser>> sysUsersMap) {
		this.sysUsersMap = sysUsersMap;
	}

	/**
	 * @return the gusel
	 */
	public String[] getGusel() {
		return gusel;
	}

	/**
	 * @param gusel the gusel to set
	 */
	public void setGusel(String[] gusel) {
		this.gusel = gusel;
	}
	
	@Override
	public void prepare() throws Exception {
		initOpraMap("SYS-RGU");
	}

	
}
