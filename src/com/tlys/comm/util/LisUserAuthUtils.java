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
 * רΪlisϵͳ��ѯ���û�Ȩ�޽���������
 * 
 * @author
 * 
 */
@Component
public class LisUserAuthUtils {

	private Log log = LogFactory.getLog(LisUserAuthUtils.class);

	// ������ʱ���� ,������и��ײ˵�
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
	 * @param loginid:�û���¼�������ڵ�¼��ǰͳһ����lis_���ǰ׺
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
				List<String> roleIdsThroughGroupList = sysRoleService
						.findRoleIdByGroupIdList(groupIdList);
				roleIdsThroughGroupSet = new HashSet<String>(
						roleIdsThroughGroupList);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("roleIdsThroughGroupSet : " + roleIdsThroughGroupSet);
		}
		// 2.�����û�ID��ȡ��ɫ
		List<String> roleIdList = sysRoleService.findRoleIdByUserId(userid);
		if (log.isDebugEnabled()) {
			log.debug("roleIdList : " + roleIdList);
		}
		// ��1��2�����ֵĽ�ɫ����Ϊһ����ɫ�б����ص���rolesThroughGroupList�б���ɫID)
		if (null != roleIdList && !roleIdList.isEmpty()) {
			if (null == roleIdsThroughGroupSet) {
				roleIdsThroughGroupSet = new HashSet<String>();
			}
			roleIdsThroughGroupSet.addAll(new HashSet<String>(roleIdList));
		}
		sysUser.setRoles(roleIdsThroughGroupSet);
		// �û����Բ����Ĳ˵��б�
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
