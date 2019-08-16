package com.tlys.sys.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.dao.SysMessageDao;
import com.tlys.sys.dao.SysMsgtypeDao;
import com.tlys.sys.model.SysMessage;
import com.tlys.sys.model.SysMsgtype;

/**
 * 
 * @author ccsong 2012-3-22 下午12:35:03
 */
@Service
public class SysMessageService {

	private Log logger = LogFactory.getLog(this.getClass());

	String[] messageAction = { "填报", "提交", "增加", "删除", "审核驳回", "审核通过" };

	@Autowired
	SysMessageDao sysMessageDao;
	@Autowired
	SysMsgtypeDao sysMsgtypeDao;
	@Autowired
	CacheService cacheService;
	@Autowired
	SysLogService sysLogService;

	/**
	 * 发送消息
	 * 
	 */
	public void sendMessage(Object[] receiverids) {
		ServletContext context = ServletActionContext.getServletContext();
		ServerContext wctx = ServerContextFactory.get(context);
		Collection<ScriptSession> sessions = wctx.getAllScriptSessions();
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("showMessage('"
				+ Arrays.toString(receiverids).replace(" ", "").replace("[", "").replace("]", "") + "');");
		for (ScriptSession session : sessions) {
			if (logger.isDebugEnabled()) {
				logger.debug("session : " + session.getId());
			}
			session.addScript(script);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("推送消息完成!");
		}
	}

	public void sendMessage() {
		ServletContext context = ServletActionContext.getServletContext();
		ServerContext wctx = ServerContextFactory.get(context);
		Collection<ScriptSession> sessions = wctx.getAllScriptSessions();
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("showMessage();");
		for (ScriptSession session : sessions) {
			if (logger.isDebugEnabled()) {
				logger.debug("session : " + session.getId());
			}
			session.addScript(script);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("推送消息完成!");
		}
	}

	public static void main(String[] args) {
		String[] receiverids = new String[2];
		receiverids[0] = "001";
		receiverids[1] = "002";
		System.out.println(Arrays.toString(receiverids));
	}

	public void sendMessage(String receiverid) {
		ServletContext context = ServletActionContext.getServletContext();
		ServerContext wctx = ServerContextFactory.get(context);

		Collection<ScriptSession> sessions = wctx.getAllScriptSessions();
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("showMessage('" + receiverid + "');");
		ScriptBuffer scriptIndex = new ScriptBuffer();
		for (ScriptSession session : sessions) {
			if (logger.isDebugEnabled()) {
				logger.debug("session : " + session.getId());
			}
			session.addScript(script);
			session.addScript(scriptIndex);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("推送完成");
		}
	}

	/**
	 * 所有消息类型的MAP
	 * 
	 * @return
	 */
	public Map<String, SysMsgtype> getSysmsgtypeMap() {
		Map<String, SysMsgtype> sysMsgtypeByIdMap = new HashMap<String, SysMsgtype>();
		List<SysMsgtype> sysMsgtypes = sysMsgtypeDao.findSysMsgtype();
		if (null != sysMsgtypes && !sysMsgtypes.isEmpty()) {
			for (SysMsgtype sysMsgtype : sysMsgtypes) {
				sysMsgtypeByIdMap.put(sysMsgtype.getMsgtypeid(), sysMsgtype);
			}
		}
		return sysMsgtypeByIdMap;
	}

	/**
	 * 查询每种消息类型的消息记录数,在弹出时使用
	 * 
	 * @return
	 */
	public List<Object[]> findSysMessage(String userid) {
		return sysMessageDao.findSysMessage(userid);
	}

	/**
	 * 分页查询所有系统消息
	 * 
	 * @param sysMessage
	 * @param pageCtr
	 */
	public void findSysMessage(SysMessage sysMessage, PageCtr<SysMessage> pageCtr) {
		String key = "SYS_MESSAGE_COUNT_" + (null == sysMessage ? "" : sysMessage).toString();
		Integer count = (Integer) cacheService.get(key);
		if (null == count) {
			count = sysMessageDao.getSysMessageCount(sysMessage);
			cacheService.put(key, count);
		}
		pageCtr.setTotalPage(count);
		sysMessageDao.findSysMessage(sysMessage, pageCtr);
	}

	/**
	 * 获取所有消息类型
	 * 
	 * @return
	 */
	public List<SysMsgtype> findSysMsgtype() {
		return sysMsgtypeDao.findSysMsgtype();
	}

	public void save(SysMessage sysMessage) {
		sysMessageDao.save(sysMessage);
	}

	public void update(SysMessage sysMessage) {
		sysMessageDao.update(sysMessage);
	}

	public void updateSysMessages(List<Long> ids) {
		sysMessageDao.updateSysMessages(ids);
	}

	public void delete(SysMessage sysMessage) {
		sysMessageDao.delete(sysMessage);
	}

	public SysMessage load(Long id) {
		return sysMessageDao.load(id);
	}

	public void save(SysMessage sysMessage, String senderid, String[] receiverids) {
		if (null == sysMessage) {
			return;
		}
		for (int i = 0; i < receiverids.length; i++) {
			sysMessage.setReceiverid(receiverids[i]);
			sysMessage.setSenderid(senderid);
			sysMessageDao.save(sysMessage);
		}
		sendMessage(receiverids);
	}

}
