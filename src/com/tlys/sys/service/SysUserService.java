/**
 * 
 */
package com.tlys.sys.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.exe.dao.ExeTranStatDao;
import com.tlys.sys.dao.SysUserDao;
import com.tlys.sys.dao.SysUserdatasDao;
import com.tlys.sys.dao.SysUsergroupsDao;
import com.tlys.sys.dao.SysUserrolesDao;
import com.tlys.sys.model.Contact;
import com.tlys.sys.model.SysGroup;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysOprationtype;
import com.tlys.sys.model.SysSession;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.model.SysUserdatas;
import com.tlys.sys.model.SysUsergroups;
import com.tlys.sys.model.SysUserroles;

/**
 * @author fengym
 * 
 */
@Service("userService")
@Qualifier("userService")
public class SysUserService {
	private Log logger = LogFactory.getLog(this.getClass());
	// ������ʱ���� ,������и��ײ˵�
	private List<SysNavimenu> tempParentMenuList = null;
	@Autowired
	SysUserDao sysUserDao;
	@Autowired
	SysUsergroupsDao sysUsergroupsDao;

	@Autowired
	SysUserrolesDao sysUserrolesDao;
	@Autowired
	SysGroupService sysGroupService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	SysMenuService menuService;
	@Autowired
	SysUserdatasDao sysUserdatasDao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	SysLogService sysLogService;
	@Autowired
	ExeTranStatDao exeTranStatDao;
	@Autowired
	DicMap dicMap;

	public String getSeqStr() {
		String seq = sysUserDao.getSeq();
		int seqLen = seq.length();
		if (6 > seq.length()) {
			for (int i = 0; i < 6 - seqLen; i++) {
				seq = "0" + seq;
			}
		}
		return seq;
	}

	public List<SysUser> findAll() {
		List<SysUser> userList = sysUserDao.findAll(" order by corptab,username");
		return userList;
	}

	/**
	 * �ֵ�λ�û��б�������ǰ�����ֲ���ʾ�û�
	 * 
	 * @return
	 */
	public Map<String, List<SysUser>> findUserMap() {
		Map<String, List<SysUser>> userMap = new LinkedHashMap<String, List<SysUser>>();
		// Map<String, List<SysUser>> userByCorpIdMap = new
		// LinkedHashMap<String, List<SysUser>>();
		// Map<String, List<SysUser>>
		List<SysUser> userList = sysUserDao.findAll(" order by corptab,username");
		try {
			// ��ȡ�ֹ�˾���Ƽ���Ӧ��ϵ
			List<DicAreacorp> list = exeTranStatDao.getDicAreacorpInfo();
			for (DicAreacorp dicAreacorp : list) {
				dicAreacorp.getAreaid();
				dicAreacorp.getShrinkname();
			}
			for (Iterator iter = userList.iterator(); iter.hasNext();) {
				SysUser sysUser = (SysUser) iter.next();
				String corptabname = sysUser.getCorptabname();
				List<SysUser> suList = (List<SysUser>) userMap.get(corptabname);
				if (null == suList) {
					suList = new ArrayList<SysUser>();
				}
				suList.add(sysUser);
				userMap.put(corptabname, suList);

				// List<SysUser> users =
				// userByCorpIdMap.get(sysUser.getCorpid());
				//
				// if (users == null) {
				// users = new ArrayList<SysUser>();
				// }
				// users.add(sysUser);
				//
				// userByCorpIdMap.put(sysUser.getCorpid(), sysUser);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Map<String, Map<String, List<SysUser>>> map = new HashMap<String,
		// Map<String,List<SysUser>>>();
		// map.put("1", userMap);
		// map.put("2", value);
		return userMap;
	}

	public SysUser load(String id) throws Exception {
		SysUser user = sysUserDao.load(id);
		this.bdUserDic(user);
		/*
		 * String cpid = user.getCorpid(); String corpname = cpid;//
		 * ����id��ͬ�����corptabδ���壬�������ֵ String curtab = user.getCorptab(); Map
		 * cpMap = null; if (null != curtab) { if ("1".equals(curtab)) { cpMap =
		 * dicMap.getAreaMap(); DicAreacorp area = (DicAreacorp)
		 * cpMap.get(cpid); if (null != area) { corpname = area.getShortname();
		 * } } else if ("2".equals(curtab)) { cpMap = dicMap.getCorpMap();
		 * DicSinocorp corp = (DicSinocorp) cpMap.get(cpid); if (null != corp) {
		 * corpname = corp.getShortname(); } } else if ("3".equals(curtab)) {
		 * cpMap = dicMap.getDeptMap(); DicSinodepartment dept =
		 * (DicSinodepartment) cpMap.get(cpid); if (null != dept) { corpname =
		 * dept.getShortname(); } } else if ("0".equals(curtab)) { cpMap =
		 * dicMap.getHeadMap(); DicSinocorp corp = (DicSinocorp)
		 * cpMap.get(cpid); if (null != corp) { corpname = corp.getShortname();
		 * } } } user.setCorpname(corpname);
		 */
		// System.out.println("SysUserService.load->user.getCorpname()=="+user.getCorpname());
		return user;
	}

	public SysUser loadByLoginid(String logid) throws Exception {
		SysUser user = sysUserDao.loadByLoginid(logid);
		if (null != user) {
			this.bdUserDic(user);
		}
		return user;
	}

	/**
	 * ���������޸ĵ��ò�ͬ��dao����
	 * 
	 * @param sysUser
	 */
	public void save(SysUser sysUser, boolean isNew) {
		if (true == isNew) {
			sysUserDao.saveOrUpdate(sysUser);
		} else {
			sysUserDao.updateEntity(sysUser, sysUser.getUserid());
		}
		setAlterFlag();
	}

	/**
	 * ֱ�ӵ���DAO����������id��ֵ����ȫ�����б��� ֻ����load������ٸ��ֶ�ֵ���ٵ��ô˷���ʱ���ǰ�ȫ��
	 * 
	 * @param sysUser
	 */
	public void saveOrUpdate(SysUser sysUser) {
		sysUserDao.saveOrUpdate(sysUser);
	}

	/**
	 * ����user-group���еļ�¼
	 * 
	 * @param gsel
	 *            :���е��û������û����������б�fkval�����ֵ��fkname���������
	 */
	public void updateUserGroup(String[] gsel, String fkval, String fkname) {
		// ��ɾ�����д��û�������Ϣ/����ɾ��ĳ���µ�ȫ���û���Ϣ
		sysUsergroupsDao.delByFk(fkname, fkval);
		// �ٲ����µ��û�����Ϣ/��������û���Ϣ
		ArrayList<SysUsergroups> gsList = new ArrayList<SysUsergroups>();
		for (int i = 0; i < gsel.length; i++) {
			String gs = gsel[i];
			SysUsergroups sug = new SysUsergroups();
			if ("userid".equals(fkname)) {
				sug.setGroupid(gs);
				sug.setUserid(fkval);
			} else {
				sug.setGroupid(fkval);
				sug.setUserid(gs);
			}

			gsList.add(sug);
		}
		sysUsergroupsDao.saveOrUpdateAll(gsList);

	}

	/**
	 * ����user-role���еļ�¼
	 * 
	 * @param gsel
	 */
	public void updateUserRole(String[] rsel, String userid) {
		// ��ɾ�����д��û��Ľ�ɫ��Ϣ
		sysUserrolesDao.delByUserid(userid);
		// �ٲ����µ��û���ɫ��Ϣ
		try {
			ArrayList<SysUserroles> rsList = new ArrayList<SysUserroles>();
			for (int i = 0; i < rsel.length; i++) {
				String rs = rsel[i];
				SysUserroles sur = new SysUserroles();
				sur.setRoleid(rs);
				sur.setUserid(userid);
				rsList.add(sur);
			}
			sysUserrolesDao.saveOrUpdateAll(rsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUserdatas(String corpids, String userid, int menuid) {
		sysUserdatasDao.delete(userid, menuid);
		if (null == corpids || "".equals(corpids)) {
			return;
		}
		String[] corps = corpids.split(",");
		ArrayList<SysUserdatas> sysUserdatases = new ArrayList<SysUserdatas>();
		for (int i = 0; i < corps.length; i++) {
			String corpid = corps[i].trim();
			SysUserdatas sysUserdatas = new SysUserdatas();
			sysUserdatas.setDatacorpid(corpid);
			sysUserdatas.setUserid(userid);
			sysUserdatas.setMenuid(menuid);
			sysUserdatases.add(sysUserdatas);
		}
		sysUserdatasDao.saveOrUpdateAll(sysUserdatases);
	}

	public void updateUserData(String dselStr, String userid) {
		// ��ɾ�����д��û��Ľ�ɫ��Ϣ
		sysUserdatasDao.delByFk("userid", userid);
		// �ٲ����µ��û���ɫ��Ϣ
		String[] dsel = dselStr.split(",");
		try {
			ArrayList<SysUserdatas> rsList = new ArrayList<SysUserdatas>();
			for (int i = 0; i < dsel.length; i++) {
				String rs = dsel[i].trim();
				SysUserdatas sur = new SysUserdatas();
				sur.setDatacorpid(rs);
				sur.setUserid(userid);
				rsList.add(sur);
			}
			sysUserdatasDao.saveOrUpdateAll(rsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ҵ�ǰ�������������û�
	 * 
	 * @param groupid
	 * @return
	 */
	public List<SysUser> findGroupUsers(String groupid) {
		return sysUserDao.findGroupUsers(groupid);
	}

	/**
	 * ���������û���ͬʱ������ڵ�ǰ����û�
	 * 
	 * @param groupid
	 * @return
	 */
	public Map<String, List<SysUser>> findGroupUsersInAll(String groupid) {
		List<SysUser> ul = this.findAll();
		List<SysUser> ugl = this.findGroupUsers(groupid);
		for (Iterator iter = ul.iterator(); iter.hasNext();) {
			SysUser sysUser = (SysUser) iter.next();
			for (Iterator it = ugl.iterator(); it.hasNext();) {
				SysUser sysUserGroup = (SysUser) it.next();
				if (sysUser.getUserid().equals(sysUserGroup.getUserid())) {
					sysUser.setCgroup(true);
				}
			}

		}

		Map<String, List<SysUser>> ugMap = new HashMap<String, List<SysUser>>();
		ugMap.put("usersAll", ul);
		ugMap.put("usersGroup", ugl);

		return ugMap;
	}

	/**
	 * ���ҵ�ǰ��ɫ�������û�
	 * 
	 * @param roleid
	 * @return
	 */
	public List<SysUser> findRoleUsers(String roleid) {
		return sysUserDao.findRoleUsers(roleid);
	}

	public void delete(String id) {
		sysUserDao.delete(id);
		setAlterFlag();
	}

	public List<SysUser> findSysUserByUserIds(List<String> userIdList) {
		return sysUserDao.findSysUserByUserIds(userIdList);
	}

	public void initMenuByRoles(SysUser sysUser) {
		String userid = "";
		if (null != sysUser) {
			userid = sysUser.getUserid();
		}
		// �û���������б�
		List<SysGroup> groupList = sysGroupService.findGroupByUserId(userid);
		// �û��������ID�б�
		List<String> groupIdList = null;
		// 1.ͨ���û��Ѿ���������ȡ�û��Ľ�ɫ
		Set<String> roleIdsThroughGroupSet = null;
		if (null != groupList && !groupList.isEmpty()) {
			groupIdList = new ArrayList<String>();
			for (SysGroup group : groupList) {
				groupIdList.add(group.getGroupid());
			}

			if (null != groupIdList && !groupIdList.isEmpty()) {
				List<String> roleIdsThroughGroupList = sysRoleService.findRoleIdByGroupIdList(groupIdList);
				roleIdsThroughGroupSet = new HashSet<String>(roleIdsThroughGroupList);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("roleIdsThroughGroupSet : " + roleIdsThroughGroupSet);
		}
		// 2.�����û�ID��ȡ��ɫ
		List<String> roleIdList = sysRoleService.findRoleIdByUserId(userid);
		if (logger.isDebugEnabled()) {
			logger.debug("roleIdList : " + roleIdList);
		}
		// ��1��2�����ֵĽ�ɫ����Ϊһ����ɫ�б����ص���rolesThroughGroupList�б���ɫID)
		if (null != roleIdList && !roleIdList.isEmpty()) {
			if (null == roleIdsThroughGroupSet) {
				roleIdsThroughGroupSet = new HashSet<String>();
			}
			roleIdsThroughGroupSet.addAll(new HashSet<String>(roleIdList));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("roleIdsThroughGroupSet1 : " + roleIdsThroughGroupSet);
		}
		sysUser.setRoles(roleIdsThroughGroupSet);
		// �û����Բ����Ĳ˵��б�
		List<SysNavimenu> menuList = menuService.findSysNavimenuByRoles(roleIdsThroughGroupSet);
		// �û����ܲ����Ĳ˵���Ӧ�Ĺ��ܲ�������Щ
		List<SysMenuopra> opraList = menuService.findSysMenuopraByRoles(roleIdsThroughGroupSet);
		// ���ݲ˵�ID��ȡ�����б�,��Map�洢,������Session�У� ���û���¼��ֱ��ʹ��
		Map<Integer, List<SysMenuopra>> hasOpraByMenuIdMap = menuService.findOpraByMenuIdMap(opraList);
		// �����в˵�������ݲ˵�ID����HashMap��
		Map<Integer, SysNavimenu> nvaimenuByIdMap = menuService.findSysMenuMap();
		// �����Ӳ˵���ȡ���и��˵�
		tempParentMenuList = new ArrayList<SysNavimenu>();
		List<SysNavimenu> parentMenuList = getParentMenu(nvaimenuByIdMap, menuList);
		// ������ӵ��Ȩ�޵Ĳ˵�׷��
		if (null != parentMenuList && !parentMenuList.isEmpty()) {
			parentMenuList.addAll(menuList);
			// �����в˵��б�����ȥ�أ�
			parentMenuList = menuService.orderMenuByLevel(parentMenuList);
		}
		// ���ݲ˵������ȡ����˵�Map
		Map<String, SysNavimenu> parentNaviMenuMap = menuService.findParentSysnavimenuByMenucodeMap();

		// List<String> sysUserdatasCorpIdList =
		// sysUserdatasDao.findSysUserdataCorpIds(userid);
		if (logger.isDebugEnabled()) {
			logger.debug("userid : " + userid);
		}
		List<SysUserdatas> sysUserdatasList = sysUserdatasDao.findSysUserdatas(userid);
		// ���ݲ˵�ID��ȡ��ҵ����
		Map<Integer, Set<String>> corpidsByMenuIdMap = getCorpidsByMenuIdMap(sysUserdatasList);
		// ��ȡ�û�Ĭ�Ͽ���ʹ�õ���ҵ
		String[] defaultCorpIds = dicSinocorpService.findDefaultCorpidsByUserId(sysUser);
		Map<String, SysOprationtype> oprationTypByIdMap = menuService.findSysOprationtypeByIdMap();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// Set<String> corpIdSet = null;
		// if (null != sysUserdatasCorpIdList &&
		// !sysUserdatasCorpIdList.isEmpty()) {
		// corpIdSet = new HashSet<String>(sysUserdatasCorpIdList);
		// }
		// if (null == corpIdSet) {
		// corpIdSet = new HashSet<String>();
		// }
		// String corpid = sysUser.getCorpid();
		// if (null != corpid && !"35000000".equals(corpid)) {
		// corpIdSet.add(sysUser.getCorpid());
		// }
		// �˵����������ܴ���Session��
		session.setAttribute("hasOpraByMenuIdMap", hasOpraByMenuIdMap);
		session.setAttribute("oprationTypByIdMap", oprationTypByIdMap);
		session.setAttribute("parentNaviMenuMap", parentNaviMenuMap);
		session.setAttribute("menuList", parentMenuList);
		session.setAttribute("defaultCorpIds", defaultCorpIds);
		session.setAttribute("corpidsByMenuIdMap", corpidsByMenuIdMap);
	}

	/**
	 * ��������Ҷ�Ӳ˵���ȡ������ظ��˵�
	 * 
	 * @param sysMenuByIdMap
	 * @param newList
	 * @return
	 */
	private List<SysNavimenu> getParentMenu(Map<Integer, SysNavimenu> sysMenuByIdMap, List<SysNavimenu> newList) {
		if (null != newList) {
			for (SysNavimenu sysNavimenu : newList) {
				SysNavimenu menu = sysMenuByIdMap.get(sysNavimenu.getPmenuid());
				if (menu != null) {
					tempParentMenuList.add(menu);
					getParentMenu(sysMenuByIdMap, tempParentMenuList);
				} else {
					continue;
				}
			}
		}
		return tempParentMenuList;
	}

	/**
	 * ���ݲ˵�ID��ȡ��ҵ����
	 * 
	 * @param sysUserdatasList
	 * @return
	 */
	private Map<Integer, Set<String>> getCorpidsByMenuIdMap(List<SysUserdatas> sysUserdatasList) {
		Map<Integer, Set<String>> corpidsByMenuIdMap = new HashMap<Integer, Set<String>>();
		if (null != sysUserdatasList && !sysUserdatasList.isEmpty()) {
			for (SysUserdatas sysUserdatas : sysUserdatasList) {
				int menuid = sysUserdatas.getMenuid();
				SysNavimenu parentNavimenu = menuService.getParentSysnavimenu(menuid);
				if (null == parentNavimenu) {
					continue;
				}
				int finalMenuId = parentNavimenu.getMenuid();
				String corpid = sysUserdatas.getDatacorpid();
				Set<String> corpidSet = corpidsByMenuIdMap.get(finalMenuId);
				if (null == corpidSet) {
					corpidSet = new HashSet<String>();
				}
				corpidSet.add(corpid);
				corpidsByMenuIdMap.put(finalMenuId, corpidSet);
			}
		}
		return corpidsByMenuIdMap;
	}

	public Map findCorpMap(String curtab) throws Exception {
		if (null == curtab)
			curtab = "2";
		Map corpMap = null;
		if ("1".equals(curtab)) {
			corpMap = dicMap.getAreaMap();
		} else if ("3".equals(curtab)) {
			corpMap = dicMap.getDeptMap();
		} else if ("0".equals(curtab)) {
			corpMap = dicMap.getHeadMap();
		} else if ("9".equals(curtab)) {
			corpMap = new HashMap();
		} else {
			corpMap = dicMap.getCorpMap();
		}
		return corpMap;
	}

	public void find(SysUser sysUser, PageCtr pageCtr) throws Exception {
		int totalRecord = getSysUserCount(sysUser);
		pageCtr.setTotalRecord(totalRecord);
		sysUserDao.find(sysUser, pageCtr);
		bdUserDic(pageCtr);

	}

	/**
	 * ����ҳ��ѯ
	 * 
	 * @param sysUser
	 * @throws Exception
	 */
	public List find(SysUser sysUser) throws Exception {
		List userList = sysUserDao.find(sysUser);
		bdUserDic(userList);
		return userList;
	}

	public List<SysUser> findSysUser(SysUser sysUser) {
		return sysUserDao.findSysUser(sysUser);
	}

	/**
	 * ȥ��list�û�����û��б�
	 * 
	 * @return
	 */
	public List<SysUser> findUsers() {
		return sysUserDao.findUsers();
	}

	public int getSysUserCount(final SysUser sysUser) {
		int count = sysUserDao.getSysUserCount(sysUser);
		return count;
	}

	public List<SysUserdatas> findSysUserdatas(String userid, int menuid) {
		return sysUserdatasDao.findSysUserdatas(userid, menuid);
	}

	/**
	 * �����û��е��ֵ�ֵ�����pageCtr
	 * 
	 * @param pageCtr
	 * @throws Exception
	 */
	private void bdUserDic(PageCtr pageCtr) throws Exception {
		List userList = pageCtr.getRecords();
		bdUserDic(userList);
	}

	/**
	 * �����û��е��ֵ�ֵ�����list
	 * 
	 * @param userList
	 * @throws Exception
	 */
	private void bdUserDic(List userList) throws Exception {
		for (Iterator iter = userList.iterator(); iter.hasNext();) {
			SysUser user = (SysUser) iter.next();
			bdUserDic(user);
		}

	}

	/**
	 * �����û��е��ֵ�ֵ�����User
	 * 
	 * @param user
	 * @throws Exception
	 */
	private void bdUserDic(SysUser user) throws Exception {
		String corptab = user.getCorptab();
		String corpid = user.getCorpid();
		if (null == corptab) {
			user.setCorpname(corpid);
		} else {
			Map corpMap = null;
			if ("0".equals(corptab) || "2".equals(corptab)) {
				corpMap = dicMap.getCorpAllMap();
				// ��sinocorp������
			} else if ("1".equals(corptab)) {
				corpMap = dicMap.getAreaMap();
				// ��areacorp������
			} else if ("3".equals(corptab)) {
				corpMap = dicMap.getDeptMap();
				// ��sinodepartment������
			} else if ("9".equals(corptab)) {
				corpMap = new LinkedHashMap();
				corpMap.put("lis00000", "LISϵͳ��ѯ�û�");
			} else {
				corpMap = new LinkedHashMap();// Ϊ�˴���һ��,���ᵼ�±�null�쳣
			}
			Object obj = corpMap.get(corpid);
			String corpName = corpid;
			if (null != obj) {
				corpName = obj.toString();
			}
			user.setCorpname(corpName);
		}
	}

	public void expExcel(SysUser sysUser, HttpServletResponse response) throws Exception {

		// ��ѯ��ϸ�ƻ�����
		List<SysUser> list = find(sysUser);

		Map tabDefineMap = new LinkedHashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�û��б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "�û��б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "userid", "username", "loginid", "corpname", "corptabname", "description" };
		headArr[1] = new String[] { "�û�ID", "����", "��¼��", "��λ", "�û�����", "����" };

		// ��ҵ��� װ������ ��վ ��վ ������ �ջ��� ���� ��·Ʒ�� ԭ�ᳵ�� ԭ����� ��׼���� ��׼���� ��װ���� ��װ����
		sheetMap.put("headArr", headArr);
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�û��б�");

	}

	private void setAlterFlag() {
		dicMap.dicAlter("SysUser");
	}

	// ����û���¼���Ƿ��Ѵ���
	public boolean isexist(String loginid) throws Exception {
		SysUser user = loadByLoginid(loginid);
		if (null == user) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �����û����ܲ�������ҵȨ�޻�ȡ��Щ��ҵ�µ������û�Id
	 * 
	 * @param corpids
	 * @return
	 */
	public List<String> getUserIdsByCorpids(Object[] corpids) {
		return sysUserDao.getUserIdsByCorpids(corpids);
	}

	/**
	 * ���¼���Ȩ�ޣ������û�������¼���⣬�����û���¼��־
	 * 
	 * @param sysUser
	 */
	public void renderSysUser(SysUser sysUser) {
		String userid = sysUser.getUserid();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String sessionID = session.getId();
		SysSession sysSession = sysLogService.getSysSession(sessionID);
		if (sysSession != null) {
			sysSession.setUserid(userid);
			if (logger.isDebugEnabled()) {
				logger.debug("sysSession is not null : " + sessionID);
			}
			sysLogService.saveOrUpdate(sysSession);
		} else {
			sysLogService.save(sysSession);
		}
		// ��ȡ�û�Ȩ�ޣ���Ȩ�ޱ�����Session��
		initMenuByRoles(sysUser);
		ServletContext context = session.getServletContext();
		// ���������û��б�
		Map<String, HttpSession> userSessionMap = (Map<String, HttpSession>) context.getAttribute("userSessionMap");
		// �û�Id:�û�SessionID;���ֻ������loginid��һ����¼������
		Map<String, String> userIdSessionMap = (Map<String, String>) context.getAttribute("userIdSessionMap");
		// �µ�¼��userid�����ڵ�sessionID��
		String sessionOverId = "";
		if (null != userIdSessionMap && !userIdSessionMap.isEmpty()) {
			// �����û�userid��ȡ����sessionID��
			sessionOverId = userIdSessionMap.get(userid);
		}
		// ����sessionID ��ȡ֮ǰ��¼����Session����
		HttpSession sessionOver = userSessionMap.get(sessionOverId);
		if (null != sessionOver) {
			// ���session
			String loginid = sysUser.getLoginid();
			// ��admin��tlys�����û����⴦��
			if (!"admin".equalsIgnoreCase(loginid) && !"tlys".equalsIgnoreCase(loginid)) {
				sessionOver.invalidate();
			}
		}
		if (null == userIdSessionMap) {
			userIdSessionMap = new HashMap<String, String>();
		}
		// ���µ�¼��userid�͵�ǰ��sessionID���±����userIdSessionMap��
		userIdSessionMap.put(userid, sessionID);
		context.setAttribute("userIdSessionMap", userIdSessionMap);
	}

	public List<SysUser> findSysUserByNoContain(List<String> sysUserList, String corpid) {
		return sysUserDao.findSysUserByNoContain(sysUserList, corpid);
	}

	public List<SysUser> findSysUser(final List<String> useridList) {
		return sysUserDao.findSysUser(useridList);
	}

	public List<Contact> findContact(Contact contact) {

		String aname = null;
		String username = null;
		if (null != contact) {
			aname = contact.getAreaname();
			username = contact.getName();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("areaname : " + aname);
			logger.debug("name : " + username);
		}
		FileInputStream fis = null;
		List<Contact> contactList = new ArrayList<Contact>();
		Map<String, String> nameMap = new HashMap<String, String>();
		try {
			ServletContext context = ServletActionContext.getServletContext();
			String contactRealPath = context.getRealPath("/Contact.xlsx");
			File file = new File(contactRealPath);
			if (!file.exists()) {
				return null;
			}
			fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int maxRows = sheet.getLastRowNum();
			if (logger.isDebugEnabled()) {
				logger.debug("maxRows : " + (maxRows + 1));
			}
			DecimalFormat df = new DecimalFormat("#");
			String areaname = "";
			String corpname = "";
			String corptype = "";
			String department = "";
			for (int i = 1; i < maxRows; i++) {
				boolean need = false;
				XSSFRow row = sheet.getRow(i);
				Contact newContact = new Contact();
				String cusorAreaname = row.getCell(0).getStringCellValue();
				String cusorCorpname = row.getCell(2).getStringCellValue();
				String cusorCorptype = row.getCell(1).getStringCellValue();
				String cusorDepartment = row.getCell(3).getStringCellValue();
				String name = row.getCell(4).getStringCellValue();

				// if ((null == cusorAreaname || "".equals(cusorAreaname)) &&
				// !"".equals(areaname)) {
				// contact.setAreaname(areaname);
				// } else {
				// contact.setAreaname(cusorAreaname);
				// }
				// if ((null == cusorCorptype || "".equals(cusorCorptype)) &&
				// !"".equals(corptype)) {
				// contact.setCorptype(corptype);
				// } else {
				// contact.setCorptype(cusorCorptype);
				// }

				// if ((null == cusorCorpname || "".equals(cusorCorpname)) &&
				// !"".equals(corpname)) {
				// contact.setCorpname(corpname);
				// } else {
				// contact.setCorpname(cusorCorpname);
				// }
				if ((null == cusorDepartment || "".equals(cusorDepartment)) && !"".equals(department)) {
					newContact.setDepartment(department);
				} else {
					newContact.setDepartment(cusorDepartment);
				}
				newContact.setName(name);
				newContact.setTitle(row.getCell(5).getStringCellValue());
				if (row.getCell(6).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					Double offficephone = row.getCell(6).getNumericCellValue();
					newContact.setOfficephone(null != offficephone ? df.format(offficephone) : "");
				} else {
					String offficephone = row.getCell(6).getStringCellValue();
					newContact.setOfficephone(offficephone);
				}
				if (row.getCell(7).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					Double mobile = row.getCell(7).getNumericCellValue();
					newContact.setMobile(null != mobile ? df.format(mobile) : "");
				} else {
					String mobile = row.getCell(7).getStringCellValue();
					newContact.setMobile(mobile);
				}

				if (null != cusorAreaname && !"".equals(cusorAreaname)) {
					areaname = cusorAreaname;
				}
				if (null != cusorCorpname && !"".equals(cusorCorpname)) {
					corpname = cusorCorpname;
				}
				if (null != cusorCorptype && !"".equals(cusorCorptype)) {
					corptype = cusorCorptype;
				}
				if (null != cusorDepartment && !"".equals(cusorDepartment)) {
					department = cusorDepartment;
				}

				if ((null != username && !"".equals(username)) && (null != aname && !"".equals(aname))) {
					if (name.indexOf(username) != -1 && areaname.indexOf(aname) != -1) {
						need = true;
					}
				} else if ((null == username || "".equals(username)) && (null != aname && !"".equals(aname))) {
					if (areaname.indexOf(aname) != -1) {
						need = true;
					}
				} else if ((null != username && !"".equals(username)) && (null == aname || "".equals(aname))) {
					if (name.indexOf(username) != -1) {
						need = true;
					}
				}
				if ((null == aname || "".equals(aname)) && (null == username || "".equals(username))) {
					need = true;
				}
				if (!nameMap.containsKey(name) && need) {
					nameMap.put(name, name);
					contactList.add(newContact);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("user count : " + contactList.size());
			}
			// ����ѫ ��³
		} catch (Exception e) {
			logger.error("render contact error", e);
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return contactList;
	}
}
