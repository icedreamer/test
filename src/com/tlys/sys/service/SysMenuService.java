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
	 * ��ȡ���й��ܲ˵�
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
	 * ��ȡ���в�������
	 * 
	 * @return
	 */
	public List<SysOprationtype> findSysOprationType() {
		return sysOprationtypeDao.findSysOprationType();
	}

	/**
	 * ������ �ݹ������
	 * 
	 * @param navimenuList
	 * @return
	 */

	public String getTree(List<SysNavimenu> navimenuList) {
		// ���ݸ��ڵ��ȡ���ӽڵ��MAP
		Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap = new TreeMap<Integer, List<SysNavimenu>>();
		// ���ݸ�ID��ȡ��ID��Ӧ�Ĳ˵���¼
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
			treeNodesMap.put("text", "���ܲ˵�");
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
			// ���ݸ��ڵ��ȡ���ӽڵ��MAP
			Map<Integer, List<SysNavimenu>> navimenusByPmenuIdMap = new TreeMap<Integer, List<SysNavimenu>>();
			// ���ݸ�ID��ȡ��ID��Ӧ�Ĳ˵���¼
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

					jsonMap.put("text", tempNavimenu == null ? "���в˵�" : tempNavimenu.getMenuname());
					jsonMap.put("id", tempNavimenu == null ? "0" : tempNavimenu.getMenuid());
					// jsonMap.put("state", "open");
					// ���ݸ��ڵ��ȡ���ӽڵ��б�
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
	 * ���ݹ��ܲ˵�ID��ȡ�˵�����
	 * 
	 * @param menuid
	 * @return
	 */
	public SysNavimenu getSysNavimenu(int menuid) {
		return sysNavimenuDao.load(menuid);
	}

	/**
	 * ���ݲ˵������ȡ�˵�
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
	 * ���ݲ˵���ַ��ȡ�˵�
	 * 
	 * @param uri
	 * @return
	 */
	public SysNavimenu getSysNavimenuByUri(String uri) {
		return sysNavimenuDao.getSysNavimenuByUri(uri);
	}

	/**
	 * ����˵�����
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
	 * ���²˵�����
	 * 
	 * @param navimenu
	 */
	public void update(SysNavimenu navimenu) {
		sysNavimenuDao.updateEntity(navimenu, navimenu.getMenuid());
	}

	/**
	 * ���������ɫȨ�޵Ĳ˵���(����������ĺ�ȫ��������Ĳ˵���)
	 * 
	 * @return
	 */

	public HashMap<String, Map<SysNavimenu, List>> findSysNavimenusMap(String roleid) {
		HashMap<String, Map<SysNavimenu, List>> menusMap = new HashMap<String, Map<SysNavimenu, List>>();
		List<SysNavimenu> msList = sysNavimenuDao.findSysNavimenusSel(roleid);

		Map menuMapSel = this.findSysNavimenusAndOperSel(roleid);

		/**
		 * ���п������ɫ�Ĳ˵�������пɲ����� ������menu���ظ���Menu���ж��ٸ���������ж��ٸ�Menu ����Ҫ�������������
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

			// ���������ɫ�Ĳ˵�����δѡ�Ĳ�����
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
	 * ���ҵ�ǰ����ĳ��ɫ�Ĳ˵�������Ĳ���
	 * 
	 * @param roleid
	 * @return Map�ṹΪ��menu:list<SysOprationtype>
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
	 * ���������ɫȨ�޵Ĳ˵���(���������δ����) �˷����������棬�ݲ��ã���������
	 * 
	 * @return
	 */
	public HashMap<String, List<SysNavimenu>> findSysNavimenusMap_bak(String roleid) {
		HashMap<String, List<SysNavimenu>> menusMap = new HashMap<String, List<SysNavimenu>>();

		List<SysNavimenu> msnList = sysNavimenuDao.findSysNavimenusNotSel(roleid);
		List<SysNavimenu> msList = sysNavimenuDao.findSysNavimenusSel(roleid);

		/**
		 * Ϊ��ǰ��ɫδ�����ÿ���˵��������������Ĳ���
		 */
		for (Iterator it = msnList.iterator(); it.hasNext();) {
			SysNavimenu sysNavimenu = (SysNavimenu) it.next();
			int menuid = sysNavimenu.getMenuid();
			List<SysOprationtype> opListAll = sysOprationtypeDao.findSysOprationtypeMenu(menuid);
			sysNavimenu.setOpListAll(opListAll);
		}

		/**
		 * Ϊ��ǰ��ɫ�����ÿ���˵������������Ĳ���
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
	 * ����ĳ���˵���������Ĳ�������
	 * 
	 * @param menuid
	 * @return
	 */
	public List<SysOprationtype> findSysOprationtypeMenu(int menuid) {
		return this.sysOprationtypeDao.findSysOprationtypeMenu(menuid);
	}

	/**
	 * ��ȡ���Ĳ˵�ID
	 * 
	 * @return
	 */
	public int getMaxMenuId() {
		return sysNavimenuDao.getMaxMenuId();
	}

	/**
	 * ���ݲ˵�ID���б�ɾ���˵�����
	 * 
	 * @param menuIds
	 */
	public void deleteSysNavimenuByNotInMenuIds(List<Integer> menuIds) {
		sysNavimenuDao.deleteSysNavimenuByNotInMenuIds(menuIds);
		reload();
	}

	/**
	 * ���Ӳ˵�ID�б�ɾ���˵���Ӧ��������
	 * 
	 * @param menuIdList
	 */
	public void deleteSysmenuopraByNotInMenuIds(List<Integer> menuIds) {
		sysMenuopraDao.deleteSysmenuopraByNotInMenuIds(menuIds);
	}

	/**
	 * ��ѯ���в��������б�
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
	 * ���²˵�������
	 * 
	 * @param menuId
	 *            �˵�����ID����
	 * @param operids
	 *            �˵�������ť����
	 * @param buttonTypes
	 */
	public void updateMenuOpera(Integer menuId, String[][] menuProperties) {
		// ���ݲ˵�ID��ȡ�ò˵�֮ǰ������Ĳ˵������б�
		List<SysOprationtype> operationTypeList = sysOprationtypeDao.findSysOprationtypeMenu(menuId);
		// ����HashMap����Ϊ��������ID��ֵΪ�������ܶ���
		Map<String, SysOprationtype> oprationTypeByIdMap = new HashMap<String, SysOprationtype>();
		// �ò˵�֮ǰ�Ѿ�������Ĺ��ܵ�ID�б�
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
		// �����б���� �ڵ�ǰѡ�еĹ������֮ǰ�Ѿ�������Ĺ����ظ��Ĳ�������ID�б�
		// List<String> hasOperaIdList = new ArrayList<String>();
		if (null != opraids && opraids.length > 0) {
			for (int i = 0; i < opraids.length; i++) {
				String operid = operids[i];
				String buttonType = buttonTypes[i];
				String dbtname = dbtnames[i];
				String dispname = dispnames[i];
				String opra = opraids[i];
				// �˵���������
				String menuopercode = menuopercodes[i];
				SysOprationtype oprationtype = oprationTypeByIdMap.get(operid);
				// �����������Ϊnull���������˵��������ܣ�����TB_SYS_MENU_OPRA����
				if (logger.isDebugEnabled()) {
					logger.debug("buttonTypes[" + i + "] : " + buttonType);
				}
				if (null == oprationtype) {
					continue;
				}
				// ���غϵĲ�������ID���뵽hasOperaIdList
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
		// // ���غϵĲ˵��������ܺ�ID���б��ԭʼ������б����Ƴ���
		// boolean hasRemove = operIDList.removeAll(hasOperaIdList);
		// if (logger.isDebugEnabled()) {
		// logger.debug("operaIDList.size : " + operIDList.size());
		// }
		// if (!operIDList.isEmpty()) {
		// // ���ô����蹦�ܺ�û�г��ֵ�ID�б�����ݿ���ɾ��
		// // sysMenuopraDao.deleteSysmenuopraByOpraIds(operIDList, menuId);
		// }
		// }
	}

	/**
	 * ���ݽ�ɫID�б��ѯ����Ĳ˵��б�
	 * 
	 * @param roleIdList
	 * @return
	 */
	public List<SysNavimenu> findSysNavimenuByRoles(Set<String> roleIdList) {
		return sysNavimenuDao.findSysNavimenuByRoles(roleIdList);
	}

	/**
	 * ���ݽ�ɫID�б��ѯ����Ĳ˵��б��Ӧ�Ĳ�����
	 * 
	 * @param roleIdList
	 * @return
	 */
	public List<SysMenuopra> findSysMenuopraByRoles(Set<String> roleIdList) {
		return sysNavimenuDao.findSysMenuopraByRoles(roleIdList);
	}

	/**
	 * ���˵��б���LevelOrder�ֶ��������У�Ȼ�����кõ��б�,ȥ�أ�ֱ�ӷ���
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
	 * ����ʹ��,����LevelOrder�ֶ���������
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
	 * ���ϲ��ȡ�Ĳ˵������б�����HashMap,��Ϊ�˵�ID��ֵΪ�����б�
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
	 * �����в˵�������ݲ˵�ID����HashMap��
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
	 * �����в˵�������ݲ˵�ID����HashMap��
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
	 * ���ݸ��ڵ��ȡ���ӽڵ�
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
	 * ɾ���˵�����
	 * 
	 * @param sysMenuopra
	 */
	public void deleteOpraType(SysOprationtype sysOprationtype) {
		sysOprationtypeDao.delete(sysOprationtype);
	}

	/**
	 * �����в������ܴ���Map�У�
	 * 
	 * @return
	 */
	public Map<String, SysOprationtype> findSysOprationtypeByIdMap() {
		Map<String, SysOprationtype> oprationTypByIdMap = new HashMap<String, SysOprationtype>();
		// ���в�������
		List<SysOprationtype> oprationTypeList = sysOprationtypeDao.findAll();
		if (null != oprationTypeList && !oprationTypeList.isEmpty()) {
			for (SysOprationtype sysOprationtype : oprationTypeList) {
				oprationTypByIdMap.put(sysOprationtype.getOperid(), sysOprationtype);
			}
		}
		return oprationTypByIdMap;
	}

	/**
	 * ���ݲ˵�ID�Ͳ���ID��ȡ�˵����������¼
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
	 * ���ݲ˵�ID��ȡ���в˵���������SysMenuopra
	 * 
	 * @param menuId
	 * @return
	 */
	public List<SysMenuopra> findSysMenuopraByMenuId(int menuId) {
		return sysMenuopraDao.findSysMenuopraByMenuId(menuId);
	}

	/**
	 * ���ݲ˵�id���б��ѯ�˵��б�
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
	 * ���¼��ز˵���Ӧ�˵�ID���������ڴ�
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
	 * ���ݵײ�˵�id��ȡ���ϲ�˵�id(����ڵ�ID)
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
	 * ���ݲ˵������ȡ����˵�Map
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
	 * �����û����ܲ����Ĳ˵���������һ���˵�
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
	 * �����û����ܲ����Ĳ˵��б�������˵��µ������Ӳ˵�
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
	 * ���ݲ˵������ȡ�û��ɲ�����һ���˵�ID�Ͷ����˵������ ���ز˵�ID�����
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
	 * add by fengym ���ݴ���Ĳ˵��������鼰��ǰ�û������ɵ�ǰ�û��Զ�Ӧ�˵���Ȩ�����
	 * 
	 * @param menuCodeArr
	 * @param curuser
	 * @return
	 */
	public Map<String, String> bdMenushowidMap(String[] menuCodeArr) {
		HashMap authMap = new HashMap();

		// ���´����session�еõ���ǰ�û��в���Ȩ�޵İ�ť�б�������б��Ȳ�Ϊ0���������ǰ�û���Ȩ��
		// �ο���SysUserService.initMenuByRoles(SysUser sysuser)�����������������
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
			 * "22_2";//�˴��������ṩ�ķ���������menucode��������ʾ˳��ID) }
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
