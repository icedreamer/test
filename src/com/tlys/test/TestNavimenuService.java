package com.tlys.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.annotations.Check;
import org.json.simple.JSONArray;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.sys.dao.SysNavimenuDao;
import com.tlys.sys.model.SysGroup;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.service.SysGroupService;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysRoleService;

public class TestNavimenuService extends BaseTest {

	@Autowired
	SysNavimenuDao navimenuDao;
	@Autowired
	SysMenuService menuService;

	@Autowired
	SysGroupService sysGroupService;
	@Autowired
	SysRoleService sysRoleService;

	@Test()
	@Check(constraints = "testJsonTree")
	public void testJsonTree() {
		try {
			logger.debug("testJsonTree start...");
			List<SysNavimenu> navimenuList = navimenuDao.findNavimenu();
			if (navimenuList == null || navimenuList.isEmpty()) {
				return;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("navimenuList.size : " + navimenuList.size());
			}
			// 根据父节点获取其子节点的MAP
			Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap = new TreeMap<Integer, List<SysNavimenu>>();
			// 根据父ID获取该ID对应的菜单记录
			Map<Integer, SysNavimenu> navimenuByMenuIdMap = new TreeMap<Integer, SysNavimenu>();
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
				Set<Integer> pmenuidSet = navimenusByPmenuIdMap.keySet();
				List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
				int i = 0;
				for (int pmenuid : pmenuidSet) {
					if (i > 0) {
						break;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("pmenuid : " + pmenuid);
					}
					Map<String, Object> jsonMap = new HashMap<String, Object>();
					SysNavimenu tempNavimenu = navimenuByMenuIdMap.get(pmenuid);

					jsonMap.put("text", tempNavimenu == null ? "所有菜单" : tempNavimenu.getMenuname());
					jsonMap.put("id", tempNavimenu == null ? "0" : tempNavimenu.getMenuid());
					// 根据父节点获取其子节点列表
					List<SysNavimenu> naviList = navimenusByPmenuIdMap.get(pmenuid);
					if (naviList == null || naviList.isEmpty()) {
						continue;
					}
					// jsonMap.put("children", naviList);
					List<Map<String, Object>> jsonMapList2 = new ArrayList<Map<String, Object>>();
					for (SysNavimenu sysNavimenu : naviList) {
						Map<String, Object> jsonMap2 = new HashMap<String, Object>();
						int menuid = sysNavimenu.getMenuid();
						if (logger.isDebugEnabled()) {
							logger.debug("menuid :------- " + menuid);
						}
						jsonMap2.put("text", sysNavimenu.getMenuname());
						jsonMap2.put("id", sysNavimenu.getMenuid());
						List<SysNavimenu> naviList2 = navimenusByPmenuIdMap.get(sysNavimenu.getMenuid());

						if (naviList2 != null && !naviList2.isEmpty()) {
							List<Map<String, Object>> jsonMapList3 = new ArrayList<Map<String, Object>>();
							for (SysNavimenu sysNavimenu2 : naviList2) {
								if (logger.isDebugEnabled()) {
									logger.debug("menuid : " + sysNavimenu2.getMenuid());
								}
								Map<String, Object> jsonMap3 = new HashMap<String, Object>();
								SysNavimenu tempNavimenu3 = navimenuByMenuIdMap.get(sysNavimenu2.getMenuid());
								jsonMap3.put("text", tempNavimenu3.getMenuname());
								jsonMap3.put("id", tempNavimenu3.getMenuid());

								List<SysNavimenu> naviList3 = navimenusByPmenuIdMap.get(sysNavimenu2.getMenuid());

								if (naviList3 != null && !naviList3.isEmpty()) {
									List<Map<String, Object>> jsonMapList4 = new ArrayList<Map<String, Object>>();
									for (SysNavimenu sysNavimenu3 : naviList3) {
										Map<String, Object> jsonMap4 = new HashMap<String, Object>();
										if (logger.isDebugEnabled()) {
											logger.debug("menuid ----- " + sysNavimenu3.getMenuid());
										}
										SysNavimenu tempNavimenu4 = navimenuByMenuIdMap.get(sysNavimenu3.getMenuid());
										jsonMap4.put("text", tempNavimenu4.getMenuname());
										jsonMap4.put("id", tempNavimenu4.getMenuid());
										List<SysNavimenu> naviList4 = navimenusByPmenuIdMap.get(sysNavimenu3
												.getMenuid());
										List<Map<String, Object>> jsonMapList5 = new ArrayList<Map<String, Object>>();
										if (naviList4 != null && !naviList4.isEmpty()) {
											for (SysNavimenu sysNavimenu4 : naviList4) {
												Map<String, Object> jsonMap5 = new HashMap<String, Object>();
												SysNavimenu tempNavimenu5 = navimenuByMenuIdMap.get(sysNavimenu4
														.getMenuid());
												jsonMap5.put("text", tempNavimenu5.getMenuname());
												jsonMap5.put("id", tempNavimenu5.getMenuid());
												List<SysNavimenu> naviList5 = navimenusByPmenuIdMap.get(sysNavimenu4
														.getMenuid());
												List<Map<String, Object>> jsonMapList6 = new ArrayList<Map<String, Object>>();
												if (naviList5 != null && !naviList5.isEmpty()) {
													for (SysNavimenu sysNavimenu5 : naviList5) {
														Map<String, Object> jsonMap6 = new HashMap<String, Object>();
														SysNavimenu tempNavimenu6 = navimenuByMenuIdMap
																.get(sysNavimenu5.getMenuid());
														jsonMap6.put("text", tempNavimenu6.getMenuname());
														jsonMap6.put("id", tempNavimenu6.getMenuid());
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
				if (logger.isDebugEnabled()) {
					logger.debug("jsonList : " + jsonList);
				}
				String jsonTree = JSONArray.toJSONString(jsonList);
				if (logger.isDebugEnabled()) {
					logger.debug("jsonTree : " + jsonTree);
				}
			}
			logger.debug("testJsonTree end.");
		} catch (Exception e) {
			logger.error("testJsonTree error.", e);
		}
	}

	private static void getNextMenuId() {
		String menuid = "AZZZZ";
		char[] ch = menuid.toCharArray();
		StringBuffer buffer = new StringBuffer();
		boolean isUp = false;
		for (int i = ch.length - 1; i >= 0; i--) {
			if (i == ch.length - 1) {
				char fch = (char) (ch[i] + 1);
				if ((int) fch > 90) {
					buffer.append("A");
					isUp = true;
				} else {
					buffer.append((char) (ch[i] + 1));
					isUp = false;
				}
			} else {
				if (isUp) {
					if ((int) (ch[i] + 1) > 90) {
						buffer.append("A");
						isUp = true;
					} else {
						buffer.append((char) (ch[i] + 1));
						isUp = false;
					}
				} else {
					buffer.append(ch[i]);
					isUp = false;
				}
			}
		}
		System.out.println("nextMenuId : " + buffer.reverse().toString());
	}

	@Test()
	@Check(constraints = "testGetMaxMenuId")
	public void testGetMaxMenuId() {
		int maxMenuid = navimenuDao.getMaxMenuId();
		logger.debug("maxMenuid : " + maxMenuid);
	}

	@Test()
	@Check(constraints = "testGetJsonTree")
	public void testGetJsonTree() {
		try {
			List<SysNavimenu> navimenuList = navimenuDao.findNavimenu();
			if (navimenuList == null || navimenuList.isEmpty()) {
				return;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("navimenuList.size : " + navimenuList.size());
			}
			// 根据父节点获取其子节点的MAP
			TreeMap<Integer, List<SysNavimenu>> navimenusByPmenuIdMap = new TreeMap<Integer, List<SysNavimenu>>();
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
				Map<String, Object> treeNodesMap = menuService.getTreeNode(navimenusByPmenuIdMap, null, null, null);
				logger.debug("treeStr : " + net.sf.json.JSONArray.fromObject(treeNodesMap));
			}
		} catch (Exception e) {
			logger.error("error.", e);
		}
	}

	@Test
	@Check(constraints = "")
	public void testTree() {
		try {
			List<SysNavimenu> navimenuList = navimenuDao.findNavimenu();
			if (navimenuList == null || navimenuList.isEmpty()) {
				return;
			}
			Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap = new TreeMap<Integer, List<SysNavimenu>>();
			Map<Integer, SysNavimenu> navimenuByIdMap = menuService.findSysMenuMap();
			List<SysNavimenu> removeList = new ArrayList<SysNavimenu>();
			for (SysNavimenu sysNavimenu : navimenuList) {
				int menuid = sysNavimenu.getMenuid();
				int pmenuid = sysNavimenu.getPmenuid();
				List<SysNavimenu> list = navimenusByPmenuIdMap.get(pmenuid);
				if (null == list) {
					list = new ArrayList<SysNavimenu>();
				}
				list.add(sysNavimenu);
				navimenusByPmenuIdMap.put(pmenuid, list);

				navimenuByIdMap.put(menuid, sysNavimenu);
				if (sysNavimenu.getPmenuid() == 0) {
					removeList.add(sysNavimenu);
				}
			}
			TreeMap<Integer, Object> treeMap = getNavimenuList(navimenuByIdMap, navimenusByPmenuIdMap, removeList);
			logger.debug("treeMap : " + treeMap);
		} catch (Exception e) {
			logger.error("error.", e);
		}
	}

	private TreeMap<Integer, Object> treeMap = new TreeMap<Integer, Object>();

	private TreeMap<Integer, Object> getNavimenuList(Map<Integer, SysNavimenu> sysMenuByIdMap,
			Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap, List<SysNavimenu> navimenuList) {

		if (null != navimenuList && !navimenuList.isEmpty()) {
			for (SysNavimenu sysNavimenu : navimenuList) {
				int menuid = sysNavimenu.getMenuid();
				treeMap.put(menuid, sysNavimenu);
				List<SysNavimenu> tmpList = navimenusByPmenuIdMap.get(menuid);
				if (tmpList == null || tmpList.isEmpty()) {
					continue;
				} else {
					treeMap.put(menuid, tmpList);
					getNavimenuList(sysMenuByIdMap, navimenusByPmenuIdMap, tmpList);
				}
			}
		}
		return treeMap;
	}

	@Test
	@Check(constraints = "testMenuListFromRole")
	public void testMenuListFromRole() {
		String userid = "000082";
		// 用户所属组的列表
		List<SysGroup> groupList = sysGroupService.findGroupByUserId(userid);
		// 用户所属组的ID列表
		List<String> groupIdList = null;
		if (null != groupList && !groupList.isEmpty()) {
			groupIdList = new ArrayList<String>();
			for (SysGroup group : groupList) {
				groupIdList.add(group.getGroupid());
			}
			Set<String> rolesThroughGroupList = null;
			if (null != groupIdList && !groupIdList.isEmpty()) {
				// rolesThroughGroupList =
				// sysRoleService.findRoleIdByGroupIdList(groupIdList);
			}
			// 根据用户ID获取角色
			List<String> roleIdList = sysRoleService.findRoleIdByUserId(userid);
			// if (null != rolesThroughGroupList &&
			// !rolesThroughGroupList.isEmpty()) {
			// rolesThroughGroupList.addAll(roleIdList);
			// }
			// 用户可以操作的菜单列表
			List<SysNavimenu> menuList = menuService.findSysNavimenuByRoles(rolesThroughGroupList);
			for (SysNavimenu sysNavimenu : menuList) {
				logger.debug("sys.name : " + sysNavimenu.getMenuname());
			}
			// 用户所能操作的菜单对应的功能操作有哪些
			List<SysMenuopra> opraList = menuService.findSysMenuopraByRoles(rolesThroughGroupList);

			List<SysNavimenu> navimenuList = menuService.findSysNavimenu();
			List<SysNavimenu> willMenuList = getMenuList(navimenuList, menuList);
			for (SysNavimenu sysNavimenu : willMenuList) {
				logger.debug("menu.name : " + sysNavimenu.getMenuname());
			}
			logger.debug("willMenuList.size : " + willMenuList.size());
		}
	}

	private List<SysNavimenu> getMenuList(List<SysNavimenu> menuList, List<SysNavimenu> omenuList) {
		logger.debug("omenuList.size : " + omenuList.size());
		Map<Integer, SysNavimenu> sysMenuByIdMap = new HashMap<Integer, SysNavimenu>();
		for (SysNavimenu sysNavimenu : menuList) {
			sysMenuByIdMap.put(sysNavimenu.getMenuid(), sysNavimenu);
		}

		List<SysNavimenu> fList = getParentMenu(sysMenuByIdMap, omenuList);
		fList.addAll(omenuList);
		if (logger.isDebugEnabled()) {
			logger.debug("fList : " + fList);
		}
		return fList;
	}

	private List<SysNavimenu> tList = new ArrayList<SysNavimenu>();

	private List<SysNavimenu> getParentMenu(Map<Integer, SysNavimenu> sysMenuByIdMap, List<SysNavimenu> newList) {
		for (SysNavimenu sysNavimenu : newList) {
			SysNavimenu menu = sysMenuByIdMap.get(sysNavimenu.getPmenuid());
			if (menu != null) {
				tList.add(menu);
				getParentMenu(sysMenuByIdMap, tList);
			} else {
				return tList;
			}
		}
		return tList;
	}

	public static void main(String[] args) {
	}

	@Test()
	@Check(constraints = "testGetParentMenu")
	public void testGetParentMenu() {
		SysNavimenu sysNavimenu = menuService.getSysNavimenu("BI_CAR");
		logger.debug("sysNavimenu.mentuid : " + sysNavimenu.getMenuid());
		SysNavimenu parentSysMenu = menuService.getParentSysnavimenu(sysNavimenu.getMenuid());
		logger.debug("parentSysMenu : " + parentSysMenu);
		logger.debug("parentSysMenu : " + parentSysMenu.getMenuid());
	}

}
