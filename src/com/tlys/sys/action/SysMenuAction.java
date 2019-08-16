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
 * �˵�����
 * 
 * @author ccsong
 * 
 */
@Controller
// by struts2������actionΪԭ��ģʽ
@Scope("prototype")
// by struts2�������pachage�̳�tlys-default(����struts.xml�ж���)
@ParentPackage("tlys-default")
// by struts2������action��namespace
@Namespace("/sys")
public class SysMenuAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9075859094313973983L;
	private Log logger = LogFactory.getLog(this.getClass());
	// ����menuid��������˵��б�
	List<SysNavimenu> navimenuList;
	// �ڵ����飬���½ڵ��ϵʱ������
	String[] menuids;
	// JSON��ʽ����״�ṹ
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

	// ���в��������б�
	List<SysOprationtype> operationTypeList;

	// ���ݲ˵�ID��ȡ�˵��Ĳ�������SysMenuopra����
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
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ���");
		return MSG;
	}

	/**
	 * �˵���ϸҳ��
	 * 
	 * @return
	 */
	public String detail() {
		int menuid = sysNavimenu.getMenuid();
		// ���ݽڵ��ȡ�˵�����
		sysNavimenu = menuService.getSysNavimenu(menuid);
		if (logger.isDebugEnabled()) {
			logger.debug("isLeaf : " + leaf);
		}
		if (!leaf) {
			return "detail";
		}

		// ��ȡ��Դ�������в�������
		operationTypeList = menuService.findAllSysOperationTyp();
		// ��ȡ�ýڵ������е����в�������
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
	 * �����״�˵�
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
	 * ��ȡ��һ�ڽڵ�
	 */
	public String menus() {
		// ��ȡ���в˵��б�
		navimenuList = menuService.findSysNavimenu();
		// ������
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
	 * ���²˵���ϸ��Ϣ���Լ�����Ĳ�������
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMenuInfo() throws Exception {
		if (null == sysNavimenu) {
			msg = new Msg(Msg.FAILURE, "�û�����δ���յ���");
			return MSG;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("operids : " + operids);
		}
		// ���²˵���������
		// String[][] menuProperties = { operids, buttonTypes, dbtnames,
		// dispnames, menuopercodes, opraids };
		// menuService.updateMenuOpera(sysNavimenu.getMenuid(), menuProperties);
		// ���²˵�������Ϣ
		sysNavimenu.setMenuname(CommUtils.decode(sysNavimenu.getMenuname()));
		sysNavimenu.setDescription(CommUtils.decode(sysNavimenu.getDescription()));
		sysNavimenu.setUrlpath(CommUtils.decode(sysNavimenu.getUrlpath()));
		menuService.update(sysNavimenu);
		menuService.reload();
		msg = new Msg(Msg.SUCCESS, "���³ɹ�!");
		return MSG;
	}

	/**
	 * ���µ��������״�ṹ�˵�,����Ϊ���� nodeids
	 * 
	 * @return
	 */
	public String updateTree() {
		Map<Integer, SysNavimenu> navimenuByMenuIdMap = menuService.findSysMenuMap();
		if (null != menuids && 0 != menuids.length) {
			// ����һ���б����ǰ�˴�������menuID
			List<Integer> hasMenuIdList = new ArrayList<Integer>();
			for (String nodeid : menuids) {
				// �����ʽΪchildrenID_parentID_orderIndex_menuName
				String[] arry = nodeid.split(",");
				// �Ӳ˵�
				String childrenID = arry[0];
				String parentID = arry[1];
				// ��������
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
				// ����menuID��childrenID)��ȡ�˵�����
				SysNavimenu navimenu = navimenuByMenuIdMap.get(Integer.parseInt(childrenID));
				if (navimenu == null) {
					// ��ǰ���ݿ������˵�ID
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
					// �ж�������ֶ��Ѿ����¹������޸��Ѿ����µ�����
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
				// ���Ӳ˵�ID�б�ɾ���˵���Ӧ��������
				List<String> opraIDList = menuService.findOpraIdByNotInMenuIds(hasMenuIdList);
				roleService.deleteSysRolePermByOpraIds(opraIDList);
				menuService.deleteSysmenuopraByNotInMenuIds(hasMenuIdList);
				// ɾ���б����Ѿ���ɾ���Ĳ˵���¼
				menuService.deleteSysNavimenuByNotInMenuIds(hasMenuIdList);
			}
		}
		menuService.reload();
		msg = new Msg(Msg.SUCCESS, "�޸ĳɹ�");
		return MSG;
	}

}
