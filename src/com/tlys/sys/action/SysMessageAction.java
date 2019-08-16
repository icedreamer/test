package com.tlys.sys.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysMessage;
import com.tlys.sys.model.SysMsgtype;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMessageService;
import com.tlys.sys.service.SysUserService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/sys")
public class SysMessageAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6813028000382716036L;
	private String message;
	private List<Object[]> sysMessages;
	private List<SysMsgtype> sysMsgtypes;
	private List<SysMessage> sysMessageList;
	private SysMessage sysMessage;
	private Map<String, SysMsgtype> sysMsgtypeByIdMap = new HashMap<String, SysMsgtype>();
	private Map<String, SysUser> sysUserByIdMap = new HashMap<String, SysUser>();
	@Autowired
	SysMessageService sysMessageService;

	@Autowired
	SysUserService sysUserService;

	public String load() {
		SysUser sysUser = getCurUser();
		sysMessages = sysMessageService.findSysMessage(sysUser.getUserid());
		sysMsgtypeByIdMap = sysMessageService.getSysmsgtypeMap();
		return "load";
	}

	public String send() {
		String[] receiverids = new String[2];
		receiverids[0] = "001";
		receiverids[1] = "000082";
		sysMessageService.sendMessage(receiverids);
		msg = new Msg(Msg.SUCCESS, "1");
		return MSG;
	}

	public String list4corp() throws Exception {

		if (null == pageCtr) {
			pageCtr = new PageCtr();
			if (null != sysMessage) {
				String schObjKey = "sysMessage_Corp_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysMessage);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				sysMessage = (SysMessage) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		// 消息类型的Map
		sysMsgtypeByIdMap = sysMessageService.getSysmsgtypeMap();
		pageCtr.setPageUrl("/sys/sys-message!list.action");
		SysUser sysUser = getCurUser();
		if (null == sysMessage) {
			sysMessage = new SysMessage();
		}
		sysMessage.setReceiverid(sysUser.getUserid());
		sysMessageService.findSysMessage(sysMessage, pageCtr);

		sysMessageList = pageCtr.getRecords();
		// ids，存放显示的list中状态为0的id
		List<Long> ids = null;
		if (null != sysMessageList && !sysMessageList.isEmpty()) {
			ids = new ArrayList<Long>();
			for (SysMessage sysMessage : sysMessageList) {
				String status = sysMessage.getStatus();
				if (null != status && "0".equals(status)) {
					ids.add(sysMessage.getId());
				}
			}
		}
		// 将状态为0的消息更新为1，标记为已读状态
		if (null != ids && !ids.isEmpty()) {
			sysMessageService.updateSysMessages(ids);
		}

		return "list4corp";
	}

	public String list() throws Exception {
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			if (null != sysMessage) {
				String schObjKey = "sysMessage_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysMessage);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				sysMessage = (SysMessage) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		// 消息类型的Map
		sysMsgtypeByIdMap = sysMessageService.getSysmsgtypeMap();
		pageCtr.setPageUrl("/sys/sys-message!list.action");
		if (null == sysMessage) {
			sysMessage = new SysMessage();
		}
		SysUser currentUser = getCurUser();
		sysMessage.setReceiverid(currentUser.getUserid());
		sysMessageService.findSysMessage(sysMessage, pageCtr);

		sysMessageList = pageCtr.getRecords();
		// ids，存放显示的list中状态为0的id
		List<Long> ids = null;
		Set<String> userids = null;
		if (null != sysMessageList && !sysMessageList.isEmpty()) {
			ids = new ArrayList<Long>();
			userids = new HashSet<String>();
			for (SysMessage sysMessage : sysMessageList) {
				String status = sysMessage.getStatus();
				if (null != status && "0".equals(status)) {
					ids.add(sysMessage.getId());
				}
				String senderid = sysMessage.getSenderid();
				String receiverid = sysMessage.getReceiverid();
				if (!userids.contains(senderid)) {
					userids.add(senderid);
				}
				if (!userids.contains(receiverid)) {
					userids.add(receiverid);
				}
			}
		}
		if (null != userids && !userids.isEmpty()) {
			List<SysUser> sysUsers = sysUserService.findSysUserByUserIds(new ArrayList<String>(userids));
			if (null != sysUsers && !sysUsers.isEmpty()) {
				for (SysUser sysUser : sysUsers) {
					sysUserByIdMap.put(sysUser.getUserid(), sysUser);
				}
			}
		}
		// 将状态为0的消息更新为1，标记为已读状态
		if (null != ids && !ids.isEmpty()) {
			sysMessageService.updateSysMessages(ids);
		}
		return "list";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object[]> getSysMessages() {
		return sysMessages;
	}

	public void setSysMessages(List<Object[]> sysMessages) {
		this.sysMessages = sysMessages;
	}

	public List<SysMsgtype> getSysMsgtypes() {
		return sysMsgtypes;
	}

	public void setSysMsgtypes(List<SysMsgtype> sysMsgtypes) {
		this.sysMsgtypes = sysMsgtypes;
	}

	public Map<String, SysMsgtype> getSysMsgtypeByIdMap() {
		return sysMsgtypeByIdMap;
	}

	public void setSysMsgtypeByIdMap(Map<String, SysMsgtype> sysMsgtypeByIdMap) {
		this.sysMsgtypeByIdMap = sysMsgtypeByIdMap;
	}

	public SysMessage getSysMessage() {
		return sysMessage;
	}

	public void setSysMessage(SysMessage sysMessage) {
		this.sysMessage = sysMessage;
	}

	public List<SysMessage> getSysMessageList() {
		return sysMessageList;
	}

	public void setSysMessageList(List<SysMessage> sysMessageList) {
		this.sysMessageList = sysMessageList;
	}

	public Map<String, SysUser> getSysUserByIdMap() {
		return sysUserByIdMap;
	}

	public void setSysUserByIdMap(Map<String, SysUser> sysUserByIdMap) {
		this.sysUserByIdMap = sysUserByIdMap;
	}
}
