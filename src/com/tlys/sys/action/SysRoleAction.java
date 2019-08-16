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
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysRole;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysGroupService;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysRoleService;
import com.tlys.sys.service.SysUserService;

/**
 * @author 冯彦明
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/sys")
public class SysRoleAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private static Logger logger = Logger.getLogger(SysRoleAction.class);
	

	private SysRole sysRole;
	private List<SysRole> sysRoles;
	private String id;
	
	private List<SysUser> sysUsers;
	private List<SysGroup> sysGroups;
	private Map<SysNavimenu,List> menusMapAll;
	private Map<SysNavimenu,List> menusMapSel;
	
	
	
	private String opraids;
	private String jsonTree;
	
	
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	SysMenuService sysMenuService;
	
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	SysGroupService sysGroupService;

	
	@Action(value="sysRgu",results=
	{@Result(name = "list", location="/sys/sys-rgu-left.jsp")}) 
	public String list() throws Exception{
		sysRoles = sysRoleService.findAll();
		return "list";
	} 
	
	public String edit() throws Exception{
		if(null!=id){
			detail();
		}else{
			sysRole=null;
		}
		return "input";
	} 
    
	/**
	 * 角色详细信息，包括角色与菜单的授权信息，角色与用户、组的关系
	 * @return
	 */
    public String detail() throws Exception{
    	
    	
    	sysRole = sysRoleService.load(id);
		// 生成树
		//jsonTree = sysMenuService.getTree(sysMenuService.findSysNavimenu());
		
		Map menusMap = sysMenuService.findSysNavimenusMap(id);
		
		menusMapAll = (Map<SysNavimenu,List>)menusMap.get("menusAll");
		menusMapSel = (Map<SysNavimenu,List>)menusMap.get("menusSel");
		
    	sysUsers = sysUserService.findRoleUsers(id);
    	sysGroups = sysGroupService.findRoleGroups(id);
    	
    	
    	//List l = sysMenuService.findSysNavimenusAndOperSel(id);
    	
    	
    	
    	return "detail";
    } 

    public String save() throws Exception{
    	boolean isAddNew = false;
    	if(null==sysRole){
    		throw new Exception("角色数据未接收到！");
		} else {
			/**
			 * 当新增角色时
			 */
			if(null==sysRole.getRoleid() || "".equals(sysRole.getRoleid())){
				String seqStr = sysRoleService.getSeqStr();
				sysRole.setRoleid(seqStr);
				isAddNew = true;
			}
			
			sysRoleService.save(sysRole,isAddNew);
			
			//以下用于msg页面显示的设置
			msg = new Msg(Msg.SUCCESS,
					"添加/编辑角色成功！",
					false,
					"parent.leftFrame.location.reload();",
					new String[]{"curTitle","角色"});
		}
    	
    	if(isAddNew){
    		sysRole = null;
    	}
    	
    	return "input";
    } 
    
    public String delete() throws Exception{
    	if(null==id || "".equals(id)){
    		throw new Exception("不能确定要删除的对象！");
    	}else{
    		sysRoleService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"当前角色已成功删除！",
					false,
					"parent.leftFrame.location.reload();",
					new String[]{"curTitle","角色"});
    	}
    	return MSG;
    } 
    
    public String updRoleperm() throws Exception{
    	if(null==opraids){
    		throw new Exception("没接收到数据。当前角色的权限被清空？");
    	}
    	
    	sysRoleService.updateRoleperm(opraids,id);
    	msg = new Msg(Msg.SUCCESS);
    	return MSG;
    }
    
    
    
    //=================================  

	/**
	 * @return the sysRole
	 */
	public SysRole getSysRole() {
		return sysRole;
	}

	/**
	 * @param sysRole the sysRole to set
	 */
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	/**
	 * @return the sysRoles
	 */
	public List<SysRole> getSysRoles() {
		return sysRoles;
	}

	/**
	 * @param sysRoles the sysRoles to set
	 */
	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
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
	 * @return the jsonTree
	 */
	public String getJsonTree() {
		return jsonTree;
	}

	/**
	 * @param jsonTree the jsonTree to set
	 */
	public void setJsonTree(String jsonTree) {
		this.jsonTree = jsonTree;
	}

	/**
	 * @return the menusMapAll
	 */
	public Map<SysNavimenu, List> getMenusMapAll() {
		return menusMapAll;
	}

	/**
	 * @param menusMapAll the menusMapAll to set
	 */
	public void setMenusMapAll(Map<SysNavimenu, List> menusMapAll) {
		this.menusMapAll = menusMapAll;
	}

	/**
	 * @return the menusMapSel
	 */
	public Map<SysNavimenu, List> getMenusMapSel() {
		return menusMapSel;
	}

	/**
	 * @param menusMapSel the menusMapSel to set
	 */
	public void setMenusMapSel(Map<SysNavimenu, List> menusMapSel) {
		this.menusMapSel = menusMapSel;
	}

	
	/**
	 * @param opraids the opraids to set
	 */
	public void setOpraids(String opraids) {
		this.opraids = opraids;
	}

	/**
	 * @return the opraids
	 */
	public String getOpraids() {
		return opraids;
	}

	/* (non-Javadoc)
	 * @see com.tlys.comm.bas._BaseAction#prepare()
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("SYS-RGU");
	}

	
}
