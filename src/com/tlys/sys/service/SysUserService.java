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
	// 定义临时变量 ,存放所有父亲菜单
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
	 * 分单位用户列表，用于在前端左侧分层显示用户
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
			// 获取分公司名称及对应关系
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
		 * 先与id相同，如果corptab未定义，就用这个值 String curtab = user.getCorptab(); Map
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
	 * 分新增和修改调用不同的dao方法
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
	 * 直接调用DAO方法，不管id的值，均全部进行保存 只有先load对象后，再改字段值，再调用此方法时才是安全的
	 * 
	 * @param sysUser
	 */
	public void saveOrUpdate(SysUser sysUser) {
		sysUserDao.saveOrUpdate(sysUser);
	}

	/**
	 * 更新user-group表中的记录
	 * 
	 * @param gsel
	 *            :组中的用户，或用户所属的组列表；fkval：外键值；fkname：外键名称
	 */
	public void updateUserGroup(String[] gsel, String fkval, String fkname) {
		// 先删除所有此用户的组信息/或者删除某组下的全部用户信息
		sysUsergroupsDao.delByFk(fkname, fkval);
		// 再插入新的用户组信息/或者组的用户信息
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
	 * 更新user-role表中的记录
	 * 
	 * @param gsel
	 */
	public void updateUserRole(String[] rsel, String userid) {
		// 先删除所有此用户的角色信息
		sysUserrolesDao.delByUserid(userid);
		// 再插入新的用户角色信息
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
		// 先删除所有此用户的角色信息
		sysUserdatasDao.delByFk("userid", userid);
		// 再插入新的用户角色信息
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
	 * 查找当前组里所包含的用户
	 * 
	 * @param groupid
	 * @return
	 */
	public List<SysUser> findGroupUsers(String groupid) {
		return sysUserDao.findGroupUsers(groupid);
	}

	/**
	 * 查找所有用户，同时标记属于当前组的用户
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
	 * 查找当前角色包含的用户
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
		// 用户所属组的列表
		List<SysGroup> groupList = sysGroupService.findGroupByUserId(userid);
		// 用户所属组的ID列表
		List<String> groupIdList = null;
		// 1.通过用户已经授予的组获取用户的角色
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
		// 2.根据用户ID获取角色
		List<String> roleIdList = sysRoleService.findRoleIdByUserId(userid);
		if (logger.isDebugEnabled()) {
			logger.debug("roleIdList : " + roleIdList);
		}
		// 将1和2两部分的角色整合为一个角色列表，返回的是rolesThroughGroupList列表（角色ID)
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
		// 用户可以操作的菜单列表
		List<SysNavimenu> menuList = menuService.findSysNavimenuByRoles(roleIdsThroughGroupSet);
		// 用户所能操作的菜单对应的功能操作有哪些
		List<SysMenuopra> opraList = menuService.findSysMenuopraByRoles(roleIdsThroughGroupSet);
		// 根据菜单ID获取操作列表,用Map存储,保存在Session中， 供用户登录后直接使用
		Map<Integer, List<SysMenuopra>> hasOpraByMenuIdMap = menuService.findOpraByMenuIdMap(opraList);
		// 将所有菜单对象根据菜单ID存入HashMap中
		Map<Integer, SysNavimenu> nvaimenuByIdMap = menuService.findSysMenuMap();
		// 根据子菜单获取所有父菜单
		tempParentMenuList = new ArrayList<SysNavimenu>();
		List<SysNavimenu> parentMenuList = getParentMenu(nvaimenuByIdMap, menuList);
		// 将所有拥有权限的菜单追加
		if (null != parentMenuList && !parentMenuList.isEmpty()) {
			parentMenuList.addAll(menuList);
			// 将所有菜单列表排序，去重！
			parentMenuList = menuService.orderMenuByLevel(parentMenuList);
		}
		// 根据菜单代码获取顶层菜单Map
		Map<String, SysNavimenu> parentNaviMenuMap = menuService.findParentSysnavimenuByMenucodeMap();

		// List<String> sysUserdatasCorpIdList =
		// sysUserdatasDao.findSysUserdataCorpIds(userid);
		if (logger.isDebugEnabled()) {
			logger.debug("userid : " + userid);
		}
		List<SysUserdatas> sysUserdatasList = sysUserdatasDao.findSysUserdatas(userid);
		// 根据菜单ID获取企业代码
		Map<Integer, Set<String>> corpidsByMenuIdMap = getCorpidsByMenuIdMap(sysUserdatasList);
		// 获取用户默认可以使用的企业
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
		// 菜单及操作功能存入Session中
		session.setAttribute("hasOpraByMenuIdMap", hasOpraByMenuIdMap);
		session.setAttribute("oprationTypByIdMap", oprationTypByIdMap);
		session.setAttribute("parentNaviMenuMap", parentNaviMenuMap);
		session.setAttribute("menuList", parentMenuList);
		session.setAttribute("defaultCorpIds", defaultCorpIds);
		session.setAttribute("corpidsByMenuIdMap", corpidsByMenuIdMap);
	}

	/**
	 * 根据所有叶子菜单获取所有相关父菜单
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
	 * 根据菜单ID获取企业代码
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
	 * 不分页查询
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
	 * 去除list用户后的用户列表
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
	 * 翻译用户中的字典值，针对pageCtr
	 * 
	 * @param pageCtr
	 * @throws Exception
	 */
	private void bdUserDic(PageCtr pageCtr) throws Exception {
		List userList = pageCtr.getRecords();
		bdUserDic(userList);
	}

	/**
	 * 翻译用户中的字典值，针对list
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
	 * 翻译用户中的字典值，针对User
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
				// 在sinocorp表中找
			} else if ("1".equals(corptab)) {
				corpMap = dicMap.getAreaMap();
				// 在areacorp表中找
			} else if ("3".equals(corptab)) {
				corpMap = dicMap.getDeptMap();
				// 在sinodepartment表中找
			} else if ("9".equals(corptab)) {
				corpMap = new LinkedHashMap();
				corpMap.put("lis00000", "LIS系统查询用户");
			} else {
				corpMap = new LinkedHashMap();// 为了处理一致,不会导致报null异常
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

		// 查询详细计划数据
		List<SysUser> list = find(sysUser);

		Map tabDefineMap = new LinkedHashMap();
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("用户列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "用户列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "userid", "username", "loginid", "corpname", "corptabname", "description" };
		headArr[1] = new String[] { "用户ID", "姓名", "登录名", "单位", "用户类型", "描述" };

		// 企业简称 装车日期 发站 到站 发货人 收货人 车种 铁路品名 原提车数 原提吨数 批准车数 批准吨数 已装车数 已装吨数
		sheetMap.put("headArr", headArr);
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "用户列表");

	}

	private void setAlterFlag() {
		dicMap.dicAlter("SysUser");
	}

	// 检查用户登录名是否已存在
	public boolean isexist(String loginid) throws Exception {
		SysUser user = loadByLoginid(loginid);
		if (null == user) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据用户所能操作的企业权限获取这些企业下的所有用户Id
	 * 
	 * @param corpids
	 * @return
	 */
	public List<String> getUserIdsByCorpids(Object[] corpids) {
		return sysUserDao.getUserIdsByCorpids(corpids);
	}

	/**
	 * 重新加载权限，处理用户单处登录问题，保存用户登录日志
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
		// 获取用户权限，将权限保存在Session中
		initMenuByRoles(sysUser);
		ServletContext context = session.getServletContext();
		// 所有在线用户列表
		Map<String, HttpSession> userSessionMap = (Map<String, HttpSession>) context.getAttribute("userSessionMap");
		// 用户Id:用户SessionID;解决只能允许loginid在一处登录的问题
		Map<String, String> userIdSessionMap = (Map<String, String>) context.getAttribute("userIdSessionMap");
		// 新登录的userid，所在的sessionID；
		String sessionOverId = "";
		if (null != userIdSessionMap && !userIdSessionMap.isEmpty()) {
			// 根据用户userid获取所在sessionID；
			sessionOverId = userIdSessionMap.get(userid);
		}
		// 根据sessionID 获取之前登录过的Session对象
		HttpSession sessionOver = userSessionMap.get(sessionOverId);
		if (null != sessionOver) {
			// 清楚session
			String loginid = sysUser.getLoginid();
			// 对admin和tlys两个用户特殊处理
			if (!"admin".equalsIgnoreCase(loginid) && !"tlys".equalsIgnoreCase(loginid)) {
				sessionOver.invalidate();
			}
		}
		if (null == userIdSessionMap) {
			userIdSessionMap = new HashMap<String, String>();
		}
		// 将新登录的userid和当前的sessionID重新保存进userIdSessionMap；
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
			// 王可勋 齐鲁
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
