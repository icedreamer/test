package com.tlys.sys.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysOprationtype;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysRoleService;

/**
 * 菜单管理
 * 
 * @author ccsong
 * 
 */
@Controller
// by struts2，定义action为原型模式
@Scope("prototype")
// by struts2，定义此pachage继承tlys-default(已在struts.xml中定义)
@ParentPackage("tlys-default")
// by struts2，定义action的namespace
@Namespace("/sys")
public class SysMenuAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9075859094313973983L;
	private Log logger = LogFactory.getLog(this.getClass());
	// 根据menuid排序读出菜单列表
	List<SysNavimenu> navimenuList;
	// 节点数组，更新节点关系时是试用
	String[] menuids;
	// JSON格式的树状结构
	String jsonTree;
	String[] operids;
	String[] opraids;

	public String[] getOpraids() {
		return opraids;
	}

	public void setOpraids(String[] opraids) {
		this.opraids = opraids;
	}

	String[] buttonTypes;
	String[] dbtnames;
	String[] dispnames;
	String[] menuopercodes;
	SysMenuopra sysMenuopra;

	boolean leaf;

	SysNavimenu sysNavimenu;

	// 所有操作功能列表
	List<SysOprationtype> operationTypeList;

	// 根据菜单ID获取菜单的操作功能SysMenuopra对象
	Map<String, SysOprationtype> sysMenuOpraByMenuIdAndOperIdMap;

	List<SysMenuopra> sysMenuOpraList;

	@Autowired
	SysMenuService menuService;

	@Autowired
	SysRoleService roleService;

	public String delete() {
		if (null != sysMenuopra) {
			menuService.delete(sysMenuopra.getOpraids());
		}
		msg = new Msg(Msg.SUCCESS, "删除成功！");
		return MSG;
	}

	/**
	 * 菜单详细页面
	 * 
	 * @return
	 */
	public String detail() {
		int menuid = sysNavimenu.getMenuid();
		// 根据节点获取菜单对象
		sysNavimenu = menuService.getSysNavimenu(menuid);
		if (logger.isDebugEnabled()) {
			logger.debug("isLeaf : " + leaf);
		}
		if (!leaf) {
			return "detail";
		}

		// 获取资源库中所有操作功能
		operationTypeList = menuService.findAllSysOperationTyp();
		// 获取该节点所具有的所有操作功能
		sysMenuOpraList = menuService.findSysMenuopraByMenuId(menuid);
		sysMenuOpraByMenuIdAndOperIdMap = menuService.findSysOprationtypeByIdMap();
		return "detail";
	}

	public String[] getButtonTypes() {
		return buttonTypes;
	}

	public String[] getDbtnames() {
		return dbtnames;
	}

	public String[] getDispnames() {
		return dispnames;
	}

	public String getJsonTree() {
		return jsonTree;
	}

	public String[] getMenuids() {
		return menuids;
	}

	public String[] getMenuopercodes() {
		return menuopercodes;
	}

	public List<SysNavimenu> getNavimenuList() {
		return navimenuList;
	}

	public List<SysOprationtype> getOperationTypeList() {
		return operationTypeList;
	}

	public String[] getOperids() {
		return operids;
	}

	public SysMenuopra getSysMenuopra() {
		return sysMenuopra;
	}

	public Map<String, SysOprationtype> getSysMenuOpraByMenuIdAndOperIdMap() {
		return sysMenuOpraByMenuIdAndOperIdMap;
	}

	public List<SysMenuopra> getSysMenuOpraList() {
		return sysMenuOpraList;
	}

	public SysNavimenu getSysNavimenu() {
		return sysNavimenu;
	}

	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * 左侧树状菜单
	 * 
	 * @return
	 */
	public String left() {
		return "left";
	}

	public String list() {
		return "list";
	}

	/*
	 * 获取下一节节点
	 */
	public String menus() {
		// 获取所有菜单列表
		navimenuList = menuService.findSysNavimenu();
		// 生成树
		jsonTree = menuService.getTree(navimenuList);
		if (logger.isDebugEnabled()) {
			logger.debug("jsonTree : " + jsonTree);
		}
		return "nodes";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("SYSMENU");
	}

	public void setButtonTypes(String[] buttonTypes) {
		this.buttonTypes = buttonTypes;
	}

	public void setDbtnames(String[] dbtnames) {
		this.dbtnames = dbtnames;
	}

	public void setDispnames(String[] dispnames) {
		this.dispnames = dispnames;
	}

	public void setJsonTree(String jsonTree) {
		this.jsonTree = jsonTree;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public void setMenuids(String[] menuids) {
		this.menuids = menuids;
	}

	public void setMenuopercodes(String[] menuopercodes) {
		this.menuopercodes = menuopercodes;
	}

	public void setNavimenuList(List<SysNavimenu> navimenuList) {
		this.navimenuList = navimenuList;
	}

	public void setOperationTypeList(List<SysOprationtype> operationTypeList) {
		this.operationTypeList = operationTypeList;
	}

	public void setOperids(String[] operids) {
		this.operids = operids;
	}

	public void setSysMenuopra(SysMenuopra sysMenuopra) {
		this.sysMenuopra = sysMenuopra;
	}

	public void setSysMenuOpraByMenuIdAndOperIdMap(Map<String, SysOprationtype> sysMenuOpraByMenuIdAndOperIdMap) {
		this.sysMenuOpraByMenuIdAndOperIdMap = sysMenuOpraByMenuIdAndOperIdMap;
	}

	public void setSysMenuOpraList(List<SysMenuopra> sysMenuOpraList) {
		this.sysMenuOpraList = sysMenuOpraList;
	}

	public void setSysNavimenu(SysNavimenu sysNavimenu) {
		this.sysNavimenu = sysNavimenu;
	}

	/**
	 * 更新菜单详细信息，以及授予的操作功能
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMenuInfo() throws Exception {
		if (null == sysNavimenu) {
			msg = new Msg(Msg.FAILURE, "用户数据未接收到！");
			return MSG;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("operids : " + operids);
		}
		// 更新菜单操作功能
		// String[][] menuProperties = { operids, buttonTypes, dbtnames,
		// dispnames, menuopercodes, opraids };
		// menuService.updateMenuOpera(sysNavimenu.getMenuid(), menuProperties);
		// 更新菜单基本信息
		sysNavimenu.setMenuname(CommUtils.decode(sysNavimenu.getMenuname()));
		sysNavimenu.setDescription(CommUtils.decode(sysNavimenu.getDescription()));
		sysNavimenu.setUrlpath(CommUtils.decode(sysNavimenu.getUrlpath()));
		menuService.update(sysNavimenu);
		menuService.reload();
		msg = new Msg(Msg.SUCCESS, "更新成功!");
		return MSG;
	}

	/**
	 * 更新调整后的树状结构菜单,参数为数组 nodeids
	 * 
	 * @return
	 */
	public String updateTree() {
		Map<Integer, SysNavimenu> navimenuByMenuIdMap = menuService.findSysMenuMap();
		if (null != menuids && 0 != menuids.length) {
			// 定义一个列表，存放前端传过来的menuID
			List<Integer> hasMenuIdList = new ArrayList<Integer>();
			for (String nodeid : menuids) {
				// 数组格式为childrenID_parentID_orderIndex_menuName
				String[] arry = nodeid.split(",");
				// 子菜单
				String childrenID = arry[0];
				String parentID = arry[1];
				// 整体排序
				String orderIndex = arry[2];
				String menuName = "";
				try {
					menuName = URLDecoder.decode(arry[3], "UTF-8");
				} catch (Exception e) {
					logger.error("decoder menuname error.", e);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("childrenID : " + childrenID + "_" + parentID + "_" + orderIndex + "_" + menuName);
				}
				// 根据menuID（childrenID)获取菜单对象
				SysNavimenu navimenu = navimenuByMenuIdMap.get(Integer.parseInt(childrenID));
				if (navimenu == null) {
					// 当前数据库中最大菜单ID
					int maxMenuId = menuService.getMaxMenuId();
					if (logger.isDebugEnabled()) {
						logger.debug("maxMenuId : " + maxMenuId);
					}
					int nextMenuId = maxMenuId + 1;
					navimenu = new SysNavimenu();
					navimenu.setDescription("");
					navimenu.setLevelorder(Integer.parseInt(orderIndex));
					navimenu.setMenuid(nextMenuId);
					navimenu.setMenuname(menuName);
					navimenu.setPmenuid(Integer.parseInt(parentID));
					navimenu.setUrlpath("");
					menuService.save(navimenu);
				} else {
					boolean needUpdate = false;
					if (null != parentID && (navimenu.getPmenuid() != Integer.parseInt(parentID))) {
						navimenu.setPmenuid(Integer.parseInt(parentID));
						needUpdate = true;
					}
					if (navimenu.getLevelorder() != Integer.parseInt(orderIndex)) {
						navimenu.setLevelorder(Integer.parseInt(orderIndex));
						needUpdate = true;
					}
					if (!navimenu.getMenuname().equals(menuName)) {
						navimenu.setMenuname(menuName);
						needUpdate = true;
					}
					// 判断如果有字段已经更新过，则修改已经更新的属性
					if (needUpdate) {
						menuService.update(navimenu);
					}
				}
				hasMenuIdList.add(navimenu.getMenuid());
			}
			if (!hasMenuIdList.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug("hasMenuIdList : " + hasMenuIdList);
				}
				// 更加菜单ID列表删除菜单对应操作功能
				List<String> opraIDList = menuService.findOpraIdByNotInMenuIds(hasMenuIdList);
				roleService.deleteSysRolePermByOpraIds(opraIDList);
				menuService.deleteSysmenuopraByNotInMenuIds(hasMenuIdList);
				// 删除列表中已经被删除的菜单记录
				menuService.deleteSysNavimenuByNotInMenuIds(hasMenuIdList);
			}
		}
		menuService.reload();
		msg = new Msg(Msg.SUCCESS, "修改成功");
		return MSG;
	}

}
