package com.tlys.comm.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlys.sys.model.SysGroup;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysGroupService;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysRoleService;
import com.tlys.sys.service.SysUserService;

/**
 * 专为lis系统查询的用户权限解析工具类
 * 
 * @author
 * 
 */
@Component
public class LisUserAuthUtils {

	private Log log = LogFactory.getLog(LisUserAuthUtils.class);

	// 定义临时变量 ,存放所有父亲菜单
	private List<SysNavimenu> tempParentMenuList = null;

	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysMenuService sysMenuService;
	@Autowired
	SysGroupService sysGroupService;
	@Autowired
	SysRoleService sysRoleService;

	/**
	 * 
	 * @param loginid:用户登录名，请在登录名前统一加上lis_这个前缀
	 * @return
	 */
	public boolean hasPermission(String loginid) {
		SysUser sysUser = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("loginid : " + loginid);
			}
			sysUser = sysUserService.loadByLoginid(loginid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == sysUser) {
			return false;
		}
		if (log.isDebugEnabled()) {
			log.debug("sysUser : " + sysUser.getUsername());
		}
		String userid = sysUser.getUserid();
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
				List<String> roleIdsThroughGroupList = sysRoleService
						.findRoleIdByGroupIdList(groupIdList);
				roleIdsThroughGroupSet = new HashSet<String>(
						roleIdsThroughGroupList);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("roleIdsThroughGroupSet : " + roleIdsThroughGroupSet);
		}
		// 2.根据用户ID获取角色
		List<String> roleIdList = sysRoleService.findRoleIdByUserId(userid);
		if (log.isDebugEnabled()) {
			log.debug("roleIdList : " + roleIdList);
		}
		// 将1和2两部分的角色整合为一个角色列表，返回的是rolesThroughGroupList列表（角色ID)
		if (null != roleIdList && !roleIdList.isEmpty()) {
			if (null == roleIdsThroughGroupSet) {
				roleIdsThroughGroupSet = new HashSet<String>();
			}
			roleIdsThroughGroupSet.addAll(new HashSet<String>(roleIdList));
		}
		sysUser.setRoles(roleIdsThroughGroupSet);
		// 用户可以操作的菜单列表
		List<SysNavimenu> menuList = sysMenuService
				.findSysNavimenuByRoles(roleIdsThroughGroupSet);

		if (null != menuList && !menuList.isEmpty()) {
			for (int i = 0; i < menuList.size(); i++) {
				SysNavimenu traverseMenu = menuList.get(i);
				String menucode = traverseMenu.getMenucode();
				if (log.isDebugEnabled()) {
					log.debug("menucode : " + menucode);
				}
				if (null != menucode && "LIS_USER".equals(menucode)) {
					return true;
				}
			}
		}
		return false;
	}
}
