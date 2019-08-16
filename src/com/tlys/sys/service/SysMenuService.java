package com.tlys.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.sys.dao.SysMenuopraDao;
import com.tlys.sys.dao.SysNavimenuDao;
import com.tlys.sys.dao.SysOprationtypeDao;
import com.tlys.sys.dao.SysRolepermDao;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysOprationtype;

@Service
public class SysMenuService {
	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	SysNavimenuDao sysNavimenuDao;
	@Autowired
	SysOprationtypeDao sysOprationtypeDao;
	@Autowired
	SysMenuopraDao sysMenuopraDao;
	@Autowired
	SysRolepermDao sysRolepermDao;

	private List<SysNavimenu> sysNavimenus;
	private Map<Integer, SysNavimenu> sysMenuByIdMap = new HashMap<Integer, SysNavimenu>();
	private Map<String, SysNavimenu> sysMenuByMenuCodeMap = new HashMap<String, SysNavimenu>();

	/**
	 * 获取所有功能菜单
	 * 
	 * @return
	 */
	public List<SysNavimenu> findSysNavimenu() {
		if (null == sysNavimenus || sysNavimenus.isEmpty()) {
			sysNavimenus = sysNavimenuDao.findNavimenu();
		}
		return sysNavimenus;
	}

	/**
	 * 获取所有操作类型
	 * 
	 * @return
	 */
	public List<SysOprationtype> findSysOprationType() {
		return sysOprationtypeDao.findSysOprationType();
	}

	/**
	 * 生产树 递归遍历树
	 * 
	 * @param navimenuList
	 * @return
	 */

	public String getTree(List<SysNavimenu> navimenuList) {
		// 根据父节点获取其子节点的MAP
		Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap = new TreeMap<Integer, List<SysNavimenu>>();
		// 根据父ID获取该ID对应的菜单记录
		TreeMap<Integer, SysNavimenu> navimenuByMenuIdMap = new TreeMap<Integer, SysNavimenu>();
		if (navimenuList != null && !navimenuList.isEmpty()) {
			for (SysNavimenu navimenu : navimenuList) {
				int pmenuid = navimenu.getPmenuid();
				int menuid = navimenu.getMenuid();
				List<SysNavimenu> list = navimenusByPmenuIdMap.get(pmenuid);
				if (list == null) {
					list = new ArrayList<SysNavimenu>();
				}
				list.add(navimenu);
				navimenusByPmenuIdMap.put(pmenuid, list);
				navimenuByMenuIdMap.put(menuid, navimenu);
			}
			Map<String, Object> treeNodesMap = getTreeNode(navimenusByPmenuIdMap, null, null, null);
			return net.sf.json.JSONArray.fromObject(treeNodesMap).toString();
		}
		return null;
	}

	public Map<String, Object> getTreeNode(Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap,
			Map<String, Object> childrenMap, SysNavimenu parentSysNavimenu, Map<String, Object> treeNodesMap) {
		List<Object> treeNodes = new ArrayList<Object>();
		if (null == treeNodesMap) {
			treeNodesMap = new HashMap<String, Object>();
		}
		if (treeNodesMap.isEmpty()) {
			treeNodesMap.put("text", "功能菜单");
			treeNodesMap.put("id", "0");
			treeNodesMap.put("children", treeNodes);
		}
		List<Map<String, Object>> subList = null;
		if (childrenMap != null && !childrenMap.isEmpty()) {
			subList = (List<Map<String, Object>>) childrenMap.get("children");
		}

		int pmenuid = 0;
		if (parentSysNavimenu != null) {
			pmenuid = parentSysNavimenu.getMenuid();
		}
		List<SysNavimenu> navimenuList = navimenusByPmenuIdMap.get(pmenuid);

		for (int j = 0; j < navimenuList.size(); j++) {
			Map<String, Object> listMap = null;
			if (null != subList && !subList.isEmpty()) {
				listMap = subList.get(j);
			} else {
				listMap = new HashMap<String, Object>();
			}
			SysNavimenu sysNavimenu = navimenuList.get(j);
			int menuid = sysNavimenu.getMenuid();
			listMap.put("text", sysNavimenu.getMenuname());
			listMap.put("id", sysNavimenu.getMenuid());
			List<SysNavimenu> menus = navimenusByPmenuIdMap.get(menuid);
			if (menus != null && !menus.isEmpty()) {
				List<Object> subTreeList = new ArrayList<Object>();
				for (int i = 0; i < menus.size(); i++) {
					Map<String, Object> subTreeMap = new HashMap<String, Object>();
					SysNavimenu navimenu = menus.get(i);
					subTreeMap.put("id", navimenu.getMenuid());
					subTreeMap.put("text", navimenu.getMenuname());
					subTreeList.add(subTreeMap);
				}
				listMap.put("children", subTreeList);
				treeNodes.add(listMap);
				Map<String, Object> children = new HashMap<String, Object>();
				children.put("children", subTreeList);
				getTreeNode(navimenusByPmenuIdMap, children, sysNavimenu, null);
			} else {
				treeNodes.add(listMap);
				continue;
			}
		}
		return treeNodesMap;
	}

	public String getTree1(List<SysNavimenu> navimenuList) {

		try {
			logger.debug("testJsonTree start...");
			if (null == navimenuList || navimenuList.isEmpty()) {
				return null;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("navimenuList.size : " + navimenuList.size());
			}
			// 根据父节点获取其子节点的MAP
			Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap = new TreeMap<Integer, List<SysNavimenu>>();
			// 根据父ID获取该ID对应的菜单记录
			Map<Integer, SysNavimenu> navimenuByMenuIdMap = new TreeMap<Integer, SysNavimenu>();
			if (null != navimenuList && !navimenuList.isEmpty()) {
				for (SysNavimenu navimenu : navimenuList) {
					int pmenuid = navimenu.getPmenuid();
					int menuid = navimenu.getMenuid();
					List<SysNavimenu> list = navimenusByPmenuIdMap.get(pmenuid);
					if (null == list) {
						list = new ArrayList<SysNavimenu>();
					}
					list.add(navimenu);
					navimenusByPmenuIdMap.put(pmenuid, list);
					navimenuByMenuIdMap.put(menuid, navimenu);
				}
				Set<Integer> pmenuidSet = navimenusByPmenuIdMap.keySet();
				List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
				int i = 0;
				for (int pmenuid : pmenuidSet) {
					if (0 < i) {
						break;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("pmenuid : " + pmenuid);
					}
					Map<String, Object> jsonMap = new HashMap<String, Object>();
					SysNavimenu tempNavimenu = navimenuByMenuIdMap.get(pmenuid);

					jsonMap.put("text", tempNavimenu == null ? "所有菜单" : tempNavimenu.getMenuname());
					jsonMap.put("id", tempNavimenu == null ? "0" : tempNavimenu.getMenuid());
					// jsonMap.put("state", "open");
					// 根据父节点获取其子节点列表
					List<SysNavimenu> naviList = navimenusByPmenuIdMap.get(pmenuid);
					if (null == naviList || naviList.isEmpty()) {
						continue;
					}
					// jsonMap.put("children", naviList);
					List<Map<String, Object>> jsonMapList2 = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < naviList.size(); j++) {
						SysNavimenu sysNavimenu = naviList.get(j);
						Map<String, Object> jsonMap2 = new HashMap<String, Object>();
						int menuid = sysNavimenu.getMenuid();
						jsonMap2.put("text", sysNavimenu.getMenuname());
						jsonMap2.put("id", sysNavimenu.getMenuid());
						// jsonMap2.put("state", "closed");
						// if (j == 0) {
						// jsonMap2.put("checked", true);
						// Map<String, Object> tmpMap = new HashMap<String,
						// Object>();
						// tmpMap.put("url", "/sys-menu!detail.action?nodeid=" +
						// sysNavimenu.getMenuid());
						// jsonMap2.put("attributes", tmpMap);
						// }
						List<SysNavimenu> naviList2 = navimenusByPmenuIdMap.get(sysNavimenu.getMenuid());
						if (null != naviList2 && !naviList2.isEmpty()) {
							List<Map<String, Object>> jsonMapList3 = new ArrayList<Map<String, Object>>();
							for (SysNavimenu sysNavimenu2 : naviList2) {
								Map<String, Object> jsonMap3 = new HashMap<String, Object>();
								SysNavimenu tempNavimenu3 = navimenuByMenuIdMap.get(sysNavimenu2.getMenuid());
								jsonMap3.put("text", tempNavimenu3.getMenuname());
								jsonMap3.put("id", tempNavimenu3.getMenuid());
								// jsonMap3.put("state", "closed");
								List<SysNavimenu> naviList3 = navimenusByPmenuIdMap.get(sysNavimenu2.getMenuid());
								if (null != naviList3 && !naviList3.isEmpty()) {
									List<Map<String, Object>> jsonMapList4 = new ArrayList<Map<String, Object>>();
									for (SysNavimenu sysNavimenu3 : naviList3) {
										Map<String, Object> jsonMap4 = new HashMap<String, Object>();
										if (logger.isDebugEnabled()) {
											logger.debug("menuid ----- " + sysNavimenu3.getMenuid());
										}
										SysNavimenu tempNavimenu4 = navimenuByMenuIdMap.get(sysNavimenu3.getMenuid());
										jsonMap4.put("text", tempNavimenu4.getMenuname());
										jsonMap4.put("id", tempNavimenu4.getMenuid());
										// jsonMap4.put("state", "closed");
										List<SysNavimenu> naviList4 = navimenusByPmenuIdMap.get(sysNavimenu3
												.getMenuid());
										List<Map<String, Object>> jsonMapList5 = new ArrayList<Map<String, Object>>();
										if (null != naviList4 && !naviList4.isEmpty()) {
											for (SysNavimenu sysNavimenu4 : naviList4) {
												Map<String, Object> jsonMap5 = new HashMap<String, Object>();
												SysNavimenu tempNavimenu5 = navimenuByMenuIdMap.get(sysNavimenu4
														.getMenuid());
												jsonMap5.put("text", tempNavimenu5.getMenuname());
												jsonMap5.put("id", tempNavimenu5.getMenuid());
												// jsonMap5.put("state",
												// "closed");
												List<SysNavimenu> naviList5 = navimenusByPmenuIdMap.get(sysNavimenu4
														.getMenuid());
												List<Map<String, Object>> jsonMapList6 = new ArrayList<Map<String, Object>>();
												if (null != naviList5 && !naviList5.isEmpty()) {
													for (SysNavimenu sysNavimenu5 : naviList5) {
														Map<String, Object> jsonMap6 = new HashMap<String, Object>();
														SysNavimenu tempNavimenu6 = navimenuByMenuIdMap
																.get(sysNavimenu5.getMenuid());
														jsonMap6.put("text", tempNavimenu6.getMenuname());
														jsonMap6.put("id", tempNavimenu6.getMenuid());
														// jsonMap6.put("state",
														// "closed");
														jsonMapList6.add(jsonMap6);
													}
													jsonMap5.put("children", jsonMapList6);
												}
												jsonMapList5.add(jsonMap5);
											}
											jsonMap4.put("children", jsonMapList5);
										}
										jsonMapList4.add(jsonMap4);
									}
									jsonMap3.put("children", jsonMapList4);
								}

								jsonMapList3.add(jsonMap3);
							}
							jsonMap2.put("children", jsonMapList3);
						}
						jsonMapList2.add(jsonMap2);
					}
					jsonMap.put("children", jsonMapList2);
					jsonList.add(jsonMap);
					i++;
				}
				return JSONArray.toJSONString(jsonList);
			}
			logger.debug("testJsonTree end.");
			return "";
		} catch (Exception e) {
			logger.error("testJsonTree error.", e);
		}
		return "";
	}

	/**
	 * 根据功能菜单ID获取菜单对象
	 * 
	 * @param menuid
	 * @return
	 */
	public SysNavimenu getSysNavimenu(int menuid) {
		return sysNavimenuDao.load(menuid);
	}

	/**
	 * 根据菜单代码获取菜单
	 * 
	 * @param menuCode
	 * @return
	 */
	public SysNavimenu getSysNavimenu(String menuCode) {
		return sysNavimenuDao.getSysNavimenu(menuCode);
	}

	public SysNavimenu getSysNavimenuByMenuCode(String menuCode) {
		SysNavimenu menu = sysMenuByMenuCodeMap.get(menuCode);
		if (null == menu) {
			reload();
			menu = sysMenuByMenuCodeMap.get(menuCode);
		}
		return menu;
	}

	/**
	 * 根据菜单地址获取菜单
	 * 
	 * @param uri
	 * @return
	 */
	public SysNavimenu getSysNavimenuByUri(String uri) {
		return sysNavimenuDao.getSysNavimenuByUri(uri);
	}

	/**
	 * 保存菜单对象
	 * 
	 * @param navimenu
	 */
	public void save(SysNavimenu navimenu) {
		sysNavimenuDao.save(navimenu);
	}

	public void save(SysMenuopra sysMenuopra) {
		sysMenuopraDao.save(sysMenuopra);
	}

	/**
	 * 更新菜单对象
	 * 
	 * @param navimenu
	 */
	public void update(SysNavimenu navimenu) {
		sysNavimenuDao.updateEntity(navimenu, navimenu.getMenuid());
	}

	/**
	 * 查找授予角色权限的菜单项(包括已授予的和全部可授予的菜单项)
	 * 
	 * @return
	 */

	public HashMap<String, Map<SysNavimenu, List>> findSysNavimenusMap(String roleid) {
		HashMap<String, Map<SysNavimenu, List>> menusMap = new HashMap<String, Map<SysNavimenu, List>>();
		List<SysNavimenu> msList = sysNavimenuDao.findSysNavimenusSel(roleid);

		Map menuMapSel = this.findSysNavimenusAndOperSel(roleid);

		/**
		 * 所有可授予角色的菜单项及其所有可操作项 这里面menu有重复，Menu中有多少个操作项就有多少个Menu 所以要在下面进行整理
		 */
		List menuListAllOri = sysNavimenuDao.findMenusWithOper();

		LinkedHashMap menuMapAll = new LinkedHashMap();
		for (Iterator iter = menuListAllOri.iterator(); iter.hasNext();) {
			Object[] objArr = (Object[]) iter.next();
			SysNavimenu menu = (SysNavimenu) objArr[0];

			SysMenuopra menuop = (SysMenuopra) objArr[1];

			SysOprationtype opt = (SysOprationtype) objArr[2];

			// opt.setCurOpraid(menuop.getOpraid());

			menuop.setOpername(opt.getOpername());

			// 给已授予角色的菜单补上未选的操作项
			List menuopListSel = (List) menuMapSel.get(menu);
			if (null != menuopListSel && !menuopListSel.contains(menuop)) {
				menuopListSel.add(menuop);
			}

			List menuopList = (List) menuMapAll.get(menu);
			if (menuopList == null) {
				menuopList = new ArrayList();
				menuMapAll.put(menu, menuopList);
			}
			menuopList.add(menuop);
		}

		menusMap.put("menusSel", menuMapSel);
		menusMap.put("menusAll", menuMapAll);

		return menusMap;
	}

	/**
	 * 查找当前授予某角色的菜单及授予的操作
	 * 
	 * @param roleid
	 * @return Map结构为：menu:list<SysOprationtype>
	 */
	public Map findSysNavimenusAndOperSel(String roleid) {
		List l = this.sysNavimenuDao.findSysNavimenusAndOperSel(roleid);
		Map mMap = new LinkedHashMap();
		for (Object o : l) {
			Object[] oArr = (Object[]) o;
			SysNavimenu menu = (SysNavimenu) oArr[0];
			SysMenuopra menuop = (SysMenuopra) oArr[1];
			// SysOprationtype opt = ((SysOprationtype) oArr[2]).clone();
			SysOprationtype opt = (SysOprationtype) oArr[2];

			// opt.setCurOpraid(menuop.getOpraid());
			// opt.setChecked(true);

			menuop.setOpername(opt.getOpername());
			menuop.setChecked(true);
			List menuopList = (List) mMap.get(menu);
			if (menuopList == null) {
				menuopList = new ArrayList();
				mMap.put(menu, menuopList);
			}
			menuopList.add(menuop);
		}
		return mMap;
	}

	/**
	 * 查找授予角色权限的菜单项(包括授予和未授予) 此方法有升级版，暂不用，留作备份
	 * 
	 * @return
	 */
	public HashMap<String, List<SysNavimenu>> findSysNavimenusMap_bak(String roleid) {
		HashMap<String, List<SysNavimenu>> menusMap = new HashMap<String, List<SysNavimenu>>();

		List<SysNavimenu> msnList = sysNavimenuDao.findSysNavimenusNotSel(roleid);
		List<SysNavimenu> msList = sysNavimenuDao.findSysNavimenusSel(roleid);

		/**
		 * 为当前角色未授予的每个菜单项加上所有允许的操作
		 */
		for (Iterator it = msnList.iterator(); it.hasNext();) {
			SysNavimenu sysNavimenu = (SysNavimenu) it.next();
			int menuid = sysNavimenu.getMenuid();
			List<SysOprationtype> opListAll = sysOprationtypeDao.findSysOprationtypeMenu(menuid);
			sysNavimenu.setOpListAll(opListAll);
		}

		/**
		 * 为当前角色授予的每个菜单项加上所授予的操作
		 */
		for (Iterator iter = msList.iterator(); iter.hasNext();) {
			SysNavimenu sysNavimenu = (SysNavimenu) iter.next();
			int menuid = sysNavimenu.getMenuid();
			List<SysOprationtype> opList = sysOprationtypeDao.findSysOprationtypeRoleMenu(roleid, menuid);
			List<SysOprationtype> opListAll = sysOprationtypeDao.findSysOprationtypeMenu(menuid);
			sysNavimenu.setOpListSelected(opList);
			sysNavimenu.setOpListAll(opListAll);
		}

		menusMap.put("mSelected", msList);
		menusMap.put("mNotSelected", msnList);

		return menusMap;
	}

	/**
	 * 查找某个菜单所有允许的操作类型
	 * 
	 * @param menuid
	 * @return
	 */
	public List<SysOprationtype> findSysOprationtypeMenu(int menuid) {
		return this.sysOprationtypeDao.findSysOprationtypeMenu(menuid);
	}

	/**
	 * 获取最大的菜单ID
	 * 
	 * @return
	 */
	public int getMaxMenuId() {
		return sysNavimenuDao.getMaxMenuId();
	}

	/**
	 * 根据菜单ID的列表删除菜单对象
	 * 
	 * @param menuIds
	 */
	public void deleteSysNavimenuByNotInMenuIds(List<Integer> menuIds) {
		sysNavimenuDao.deleteSysNavimenuByNotInMenuIds(menuIds);
		reload();
	}

	/**
	 * 更加菜单ID列表删除菜单对应操作功能
	 * 
	 * @param menuIdList
	 */
	public void deleteSysmenuopraByNotInMenuIds(List<Integer> menuIds) {
		sysMenuopraDao.deleteSysmenuopraByNotInMenuIds(menuIds);
	}

	/**
	 * 查询所有操作功能列表
	 * 
	 * @return
	 */
	public List<SysOprationtype> findAllSysOperationTyp() {
		return sysOprationtypeDao.findAll();
	}

	public List<String> findOpraIdByNotInMenuIds(List<Integer> menuIdList) {
		return sysMenuopraDao.findOpraIdByNotInMenuIds(menuIdList);
	}

	public String getSeqOperaId() {
		String operaId = sysMenuopraDao.getSeqOperaId();
		int length = operaId.length();
		if (6 > length) {
			for (int i = 0; i < 6 - length; i++) {
				operaId = "0" + operaId;
			}
		}
		return operaId;
	}

	/**
	 * 更新菜单功能项
	 * 
	 * @param menuId
	 *            菜单操作ID数组
	 * @param operids
	 *            菜单操作按钮类型
	 * @param buttonTypes
	 */
	public void updateMenuOpera(Integer menuId, String[][] menuProperties) {
		// 根据菜单ID获取该菜单之前被分配的菜单功能列表
		List<SysOprationtype> operationTypeList = sysOprationtypeDao.findSysOprationtypeMenu(menuId);
		// 定义HashMap，键为操作功能ID，值为操作功能对象
		Map<String, SysOprationtype> oprationTypeByIdMap = new HashMap<String, SysOprationtype>();
		// 该菜单之前已经被授予的功能的ID列表
		List<String> operIDList = null;
		if (null != operationTypeList && !operationTypeList.isEmpty()) {
			operIDList = new ArrayList<String>();
			for (SysOprationtype oprationtype : operationTypeList) {
				oprationTypeByIdMap.put(oprationtype.getOperid(), oprationtype);
				operIDList.add(oprationtype.getOperid());
			}
		}
		String[] operids = menuProperties[0];
		String[] buttonTypes = menuProperties[1];
		String[] dbtnames = menuProperties[2];
		String[] dispnames = menuProperties[3];
		String[] menuopercodes = menuProperties[4];
		String[] opraids = menuProperties[5];
		// 定义列表，存放 在当前选中的功能项和之前已经被授予的功能重复的操作功能ID列表
		// List<String> hasOperaIdList = new ArrayList<String>();
		if (null != opraids && opraids.length > 0) {
			for (int i = 0; i < opraids.length; i++) {
				String operid = operids[i];
				String buttonType = buttonTypes[i];
				String dbtname = dbtnames[i];
				String dispname = dispnames[i];
				String opra = opraids[i];
				// 菜单操作代码
				String menuopercode = menuopercodes[i];
				SysOprationtype oprationtype = oprationTypeByIdMap.get(operid);
				// 如果操作功能为null，则新增菜单操作功能，插入TB_SYS_MENU_OPRA表中
				if (logger.isDebugEnabled()) {
					logger.debug("buttonTypes[" + i + "] : " + buttonType);
				}
				if (null == oprationtype) {
					continue;
				}
				// 将重合的操作功能ID加入到hasOperaIdList
				SysMenuopra sysMenuopra = getSysMenuOpraByOpraId(opra);
				String dbButtonType = sysMenuopra.getButtontype();
				String dbDispname = sysMenuopra.getDispname();
				dispname = null == dispname ? "" : dispname;
				dbDispname = null == dbDispname ? "" : dbDispname;
				dbButtonType = null == dbButtonType ? "" : dbButtonType;
				boolean needUpdate = false;
				if (null != buttonType && !buttonType.equals(dbButtonType)) {
					sysMenuopra.setButtontype(buttonType);
					needUpdate = true;
				}
				if (!dispname.equals(dbDispname)) {
					sysMenuopra.setDispname(dispnames[i]);
					needUpdate = true;
				}
				if (null != dbtnames && !dbtnames.equals(sysMenuopra.getDbtname())) {
					sysMenuopra.setDbtname(dbtname);
					needUpdate = true;
				}
				if (null != menuopercode && !menuopercode.equals(sysMenuopra.getMenuopercode())) {
					sysMenuopra.setMenuopercode(menuopercode);
					needUpdate = true;
				}
				if (needUpdate) {
					sysMenuopraDao.updateEntity(sysMenuopra, sysMenuopra.getOpraid());
				}
				// hasOperaIdList.add(operid);
			}
		}
		// if (null != hasOperaIdList && null != operIDList) {
		// // 将重合的菜单操作功能号ID的列表从原始授予的列表中移除，
		// boolean hasRemove = operIDList.removeAll(hasOperaIdList);
		// if (logger.isDebugEnabled()) {
		// logger.debug("operaIDList.size : " + operIDList.size());
		// }
		// if (!operIDList.isEmpty()) {
		// // 将该次授予功能号没有出现的ID列表从数据库中删除
		// // sysMenuopraDao.deleteSysmenuopraByOpraIds(operIDList, menuId);
		// }
		// }
	}

	/**
	 * 根据角色ID列表查询授予的菜单列表
	 * 
	 * @param roleIdList
	 * @return
	 */
	public List<SysNavimenu> findSysNavimenuByRoles(Set<String> roleIdList) {
		return sysNavimenuDao.findSysNavimenuByRoles(roleIdList);
	}

	/**
	 * 根据角色ID列表查询授予的菜单列表对应的操作项
	 * 
	 * @param roleIdList
	 * @return
	 */
	public List<SysMenuopra> findSysMenuopraByRoles(Set<String> roleIdList) {
		return sysNavimenuDao.findSysMenuopraByRoles(roleIdList);
	}

	/**
	 * 将菜单列表按照LevelOrder字段升序排列，然后将排列好的列表,去重，直接返回
	 * 
	 * @param navimentList
	 * @return
	 */
	public List<SysNavimenu> orderMenuByLevel(List<SysNavimenu> navimenuList) {
		if (null != navimenuList && !navimenuList.isEmpty()) {
			Collections.sort(navimenuList, new ToSort());
		}
		Set<Integer> keySet = new HashSet<Integer>();
		List<SysNavimenu> newMenuList = new ArrayList<SysNavimenu>();
		for (SysNavimenu navimenu : navimenuList) {
			int menuid = navimenu.getMenuid();
			if (!keySet.contains(menuid)) {
				keySet.add(menuid);
				newMenuList.add(navimenu);
			}
		}
		return newMenuList;
	}

	/**
	 * 排序使用,按照LevelOrder字段升序排列
	 * 
	 * @author CCSONG
	 * 
	 */
	class ToSort implements Comparator<SysNavimenu> {

		public int compare(SysNavimenu menu1, SysNavimenu menu2) {
			// TODO Auto-generated method stub
			Integer level1 = menu1.getLevelorder();
			Integer level2 = menu2.getLevelorder();
			int flag = level1.compareTo(level2);
			return flag;
		}
	}

	/**
	 * 从上层获取的菜单操作列表，定义HashMap,键为菜单ID，值为操作列表
	 * 
	 * @param opraList
	 * @return
	 */
	public Map<Integer, List<SysMenuopra>> findOpraByMenuIdMap(List<SysMenuopra> opraList) {
		Map<Integer, List<SysMenuopra>> hasOpraByMenuIdMap = new HashMap<Integer, List<SysMenuopra>>();
		if (opraList != null && !opraList.isEmpty()) {
			for (SysMenuopra sysMenuopra : opraList) {
				String operid = sysMenuopra.getOperid();
				int menuId = sysMenuopra.getMenuid();
				List<SysMenuopra> opras = hasOpraByMenuIdMap.get(menuId);
				if (null == opras) {
					opras = new ArrayList<SysMenuopra>();
				}
				opras.add(sysMenuopra);
				hasOpraByMenuIdMap.put(menuId, opras);
			}
		}
		return hasOpraByMenuIdMap;
	}

	/**
	 * 将所有菜单对象根据菜单ID存入HashMap中
	 * 
	 * @return
	 */
	// public void findSysNavimenuByIdMap() {
	// if (null == sysNavimenus || sysNavimenus.isEmpty()) {
	// sysNavimenus = sysNavimenuDao.findNavimenu();
	// }
	// if (null == sysNavimenus || sysNavimenus.isEmpty()) {
	// return null;
	// }
	// for (SysNavimenu sysNavimenu : sysNavimenus) {
	// sysMenuByIdMap.put(sysNavimenu.getMenuid(), sysNavimenu);
	// }
	// return sysMenuByIdMap;
	// }
	/**
	 * 将所有菜单对象根据菜单ID存入HashMap中
	 * 
	 * @return
	 */
	public Map<Integer, SysNavimenu> findSysMenuMap() {
		if (null == sysNavimenus || sysNavimenus.isEmpty()) {
			reload();
		}
		return sysMenuByIdMap;
	}

	public Map<String, SysNavimenu> findSysMenuByMenuCodeMap() {
		if (null == sysNavimenus || sysNavimenus.isEmpty()) {
			reload();
		}
		return sysMenuByMenuCodeMap;
	}

	/**
	 * 根据父节点获取亲子节点
	 * 
	 * @param pmenuid
	 * @return
	 */
	public List<SysNavimenu> findSysNavimenuByPmenuId(int pmenuid) {
		return sysNavimenuDao.findSysNavimenuByPmenuId(pmenuid);
	}

	public void save(SysOprationtype sysOprationtype) {
		sysOprationtypeDao.save(sysOprationtype);
	}

	public void saveOrUpdate(SysMenuopra sysMenuopra) {
		sysMenuopraDao.saveOrUpdate(sysMenuopra);
	}

	/**
	 * 删除菜单操作
	 * 
	 * @param sysMenuopra
	 */
	public void deleteOpraType(SysOprationtype sysOprationtype) {
		sysOprationtypeDao.delete(sysOprationtype);
	}

	/**
	 * 将所有操作功能存入Map中，
	 * 
	 * @return
	 */
	public Map<String, SysOprationtype> findSysOprationtypeByIdMap() {
		Map<String, SysOprationtype> oprationTypByIdMap = new HashMap<String, SysOprationtype>();
		// 所有操作功能
		List<SysOprationtype> oprationTypeList = sysOprationtypeDao.findAll();
		if (null != oprationTypeList && !oprationTypeList.isEmpty()) {
			for (SysOprationtype sysOprationtype : oprationTypeList) {
				oprationTypByIdMap.put(sysOprationtype.getOperid(), sysOprationtype);
			}
		}
		return oprationTypByIdMap;
	}

	/**
	 * 根据菜单ID和操作ID获取菜单操作对象记录
	 * 
	 * @param menuId
	 * @param operid
	 * @return
	 */
	public SysMenuopra getSysMenuOpraByMenuIdAndOperId(int menuId, String menuopercode) {
		return sysMenuopraDao.getSysMenuOpraByMenuIdAndOperId(menuId, menuopercode);
	}

	public SysMenuopra getSysMenuOpraByOpraId(String opraid) {
		return sysMenuopraDao.load(opraid);
	}

	/**
	 * 根据菜单ID获取所有菜单操作对象SysMenuopra
	 * 
	 * @param menuId
	 * @return
	 */
	public List<SysMenuopra> findSysMenuopraByMenuId(int menuId) {
		return sysMenuopraDao.findSysMenuopraByMenuId(menuId);
	}

	/**
	 * 根据菜单id的列表查询菜单列表
	 * 
	 * @param menuIdList
	 * @return
	 */
	public List<SysNavimenu> findSysNavimenuByMenuIdList(List<Integer> menuIdList) {
		return sysNavimenuDao.findSysNavimenuByMenuIdList(menuIdList);
	}

	public SysMenuopra getSysMenuopra(String opraid) {
		return sysMenuopraDao.load(opraid);
	}

	public SysMenuopra getSysMenuopraByMenuopercode(String menuopercode) {
		return sysMenuopraDao.getSysMenuopraByMenuopercode(menuopercode);
	}

	public void delete(String[] opraids) {
		if (null == opraids || opraids.length == 0) {
			return;
		}
		sysRolepermDao.deleteSysRolePermByOpraIds(Arrays.asList(opraids));
		sysMenuopraDao.delete(Arrays.asList(opraids));
	}

	// public void delete(SysMenuopra sysMenuopra) {
	// if (null == sysMenuopra) {
	// return;
	// }
	// sysRolepermDao.deleteById(sysMenuopra.getOpraid());
	// sysMenuopraDao.delete(sysMenuopra);
	// }

	/**
	 * 重新加载菜单对应菜单ID进入永久内存
	 */
	public void reload() {
		sysMenuByIdMap.clear();
		sysMenuByMenuCodeMap.clear();
		sysNavimenus = sysNavimenuDao.findNavimenu();
		if (null != sysNavimenus && !sysNavimenus.isEmpty()) {
			for (SysNavimenu sysNavimenu : sysNavimenus) {
				sysMenuByIdMap.put(sysNavimenu.getMenuid(), sysNavimenu);
				String menuCode = sysNavimenu.getMenucode();
				if (null != menuCode && !"".equals(menuCode)) {
					sysMenuByMenuCodeMap.put(menuCode, sysNavimenu);
				}
			}
		}
	}

	/**
	 * 根据底层菜单id获取最上层菜单id(顶层节点ID)
	 * 
	 * @param menuId
	 * @return
	 */
	public SysNavimenu getParentSysnavimenu(int menuId) {
		SysNavimenu sysNavimenu = sysMenuByIdMap.get(menuId);
		if (null == sysNavimenu) {
			return null;
		}
		int pmenuid = sysNavimenu.getPmenuid();
		if (pmenuid != 0) {
			sysNavimenu = getParentSysnavimenu(pmenuid);
		}
		return sysNavimenu;
	}

	/**
	 * 根据菜单代码获取顶层菜单Map
	 * 
	 * @param menuCode
	 * @return
	 */
	public Map<String, SysNavimenu> findParentSysnavimenuByMenucodeMap() {
		Map<String, SysNavimenu> parentNaviMenuMap = new HashMap<String, SysNavimenu>();
		Map<Integer, SysNavimenu> sysMenuByIdMap = findSysMenuMap();
		Set<String> menuCodeSet = sysMenuByMenuCodeMap.keySet();
		for (Iterator<String> iterator = menuCodeSet.iterator(); iterator.hasNext();) {
			String menuCode = iterator.next();
			SysNavimenu sysNavimenu = sysMenuByMenuCodeMap.get(menuCode);
			int menuid = 0;
			if (null != sysNavimenu) {
				menuid = sysNavimenu.getMenuid();
			}
			SysNavimenu parentNavimenu = getParentSysnavimenu(menuid);
			parentNaviMenuMap.put(menuCode, parentNavimenu);
		}
		return parentNaviMenuMap;
	}

	/**
	 * 根据用户所能操作的菜单解析所有一级菜单
	 * 
	 * @param userOfAllMenuList
	 * @return
	 */
	public List<SysNavimenu> findUserOfParentMenus(List<SysNavimenu> userOfAllMenuList) {
		List<SysNavimenu> navimenuList = new ArrayList<SysNavimenu>();
		if (null == userOfAllMenuList || userOfAllMenuList.isEmpty()) {
			return null;
		}
		for (SysNavimenu sysNavimenu : userOfAllMenuList) {
			int pmenuId = sysNavimenu.getPmenuid();
			if (pmenuId == 0) {
				navimenuList.add(sysNavimenu);
			}
		}
		return navimenuList;
	}

	/**
	 * 根据用户所能操作的菜单列表解析父菜单下的所有子菜单
	 * 
	 * @param userOfAllMenuList
	 * @return
	 */
	public Map<Integer, List<SysNavimenu>> getSysNavimenuListByPmenuIdMap(List<SysNavimenu> userOfAllMenuList) {
		if (null == userOfAllMenuList || userOfAllMenuList.isEmpty()) {
			return null;
		}
		Map<Integer, List<SysNavimenu>> sysNavimenuListByPmenuIdMap = new HashMap<Integer, List<SysNavimenu>>();
		for (SysNavimenu sysNavimenu : userOfAllMenuList) {
			int menuid = sysNavimenu.getMenuid();
			List<SysNavimenu> sysMenuList = findSysNavimenuByPmenuId(menuid);
			List<SysNavimenu> menus = new ArrayList<SysNavimenu>();
			for (SysNavimenu sysMenu : sysMenuList) {
				if (userOfAllMenuList.contains(sysMenu)) {
					menus.add(sysMenu);
				}
			}
			sysNavimenuListByPmenuIdMap.put(menuid, menus);
		}
		return sysNavimenuListByPmenuIdMap;
	}

	/**
	 * 根据菜单代码获取用户可操作的一级菜单ID和二级菜单的序号 返回菜单ID和序号
	 * 
	 * @param menuCode
	 * @return
	 */
	public int[] getMenusPositions(String menuCode) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Map<Integer, List<SysNavimenu>> sysNavimenuListByPmenuIdMap = (Map<Integer, List<SysNavimenu>>) session
				.getAttribute("sysNavimenuListByPmenuIdMap");
		SysNavimenu sysNavimenu = getSysNavimenuByMenuCode(menuCode);
		List<SysNavimenu> navimenuList = (List<SysNavimenu>) session.getAttribute("navimenuList");
		if (null == navimenuList || navimenuList.isEmpty()) {
			return null;
		}
		SysNavimenu parentNavimenu = getParentSysnavimenu(sysNavimenu.getMenuid());
		if (logger.isDebugEnabled()) {
			logger.debug("parentNavimenu : " + parentNavimenu);
		}
		if (null == parentNavimenu) {
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("parentNavimenu.menucode : " + parentNavimenu.getMenucode());
			logger.debug("navimenuList.indexOf(parentNavimenu) : " + navimenuList.indexOf(parentNavimenu));
		}
		if (navimenuList.indexOf(parentNavimenu) == -1) {
			return null;
		}
		int[] positions = new int[2];
		List<SysNavimenu> navimenuLists = sysNavimenuListByPmenuIdMap.get(sysNavimenu.getPmenuid());
		positions[0] = parentNavimenu.getMenuid();
		if (null != navimenuLists && !navimenuLists.isEmpty()) {
			int index = navimenuLists.indexOf(sysNavimenu);
			positions[1] = index + 1;
		}
		return positions;
	}

	/**
	 * add by fengym 根据传入的菜单代码数组及当前用户，生成当前用户对对应菜单的权限情况
	 * 
	 * @param menuCodeArr
	 * @param curuser
	 * @return
	 */
	public Map<String, String> bdMenushowidMap(String[] menuCodeArr) {
		HashMap authMap = new HashMap();

		// 以下代码从session中得到当前用户有操作权限的按钮列表，如果此列表长度不为0，则表明当前用户有权限
		// 参考：SysUserService.initMenuByRoles(SysUser sysuser)这个方法里的相关内容
		// HttpServletRequest request = ServletActionContext.getRequest();
		// HttpSession session = request.getSession();
		// Map<Integer, List<SysMenuopra>> oprMap = (Map<Integer,
		// List<SysMenuopra>>)session.getAttribute("hasOpraByMenuIdMap");

		/*
		 * String[] mcshowArr = new String[]{ "17_1","17_2","17_3","22_2","35_1"
		 * };
		 */
		for (int i = 0; i < menuCodeArr.length; i++) {
			/*
			 * SysNavimenu menu = getSysNavimenuByMenuCode(menuCodeArr[i]);
			 * Integer menuid = menu.getMenuid(); List<SysMenuopra> opList =
			 * oprMap.get(menuid); String menushowid = "noauth";
			 * 
			 * if(null!=opList && opList.size()>0){ menushowid =
			 * "22_2";//此处调用宋提供的方法（根据menucode计算其显示顺序ID) }
			 */
			// authMap.put(menuCodeArr[i], menushowid);
			int[] mpArr = getMenusPositions(menuCodeArr[i]);
			String menushowid = "noauth";
			if (null != mpArr) {
				menushowid = mpArr[0] + "_" + mpArr[1];
			}

			authMap.put(menuCodeArr[i], menushowid);
		}

		return authMap;
	}
}
