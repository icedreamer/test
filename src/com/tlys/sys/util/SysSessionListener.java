package com.tlys.sys.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tlys.sys.model.SysSession;
import com.tlys.sys.service.SysLogService;

@Component
public class SysSessionListener implements HttpSessionListener {
	// 在线用户列表 键：sessionID；值：session对象
	Map<String, HttpSession> userSessionMap = new HashMap<String, HttpSession>();
	private Log logger = LogFactory.getLog(this.getClass());

	public void sessionCreated(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		HttpSession session = e.getSession();
		String sessionID = session.getId();

		if (logger.isDebugEnabled()) {
			logger.debug("sessionID : " + sessionID + " begin access.");
		}

		ServletContext sc = session.getServletContext();
		userSessionMap.put(sessionID, session);
		if (logger.isDebugEnabled()) {
			logger.debug("当前在线人数：" + userSessionMap.size());
		}
		sc.setAttribute("userSessionMap", userSessionMap);
	}

	public void sessionDestroyed(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		HttpSession session = e.getSession();
		String sessionID = session.getId();
		// 从在线列表中删除sessionID
		userSessionMap.remove(sessionID);
		long creationTime = session.getCreationTime();
		long lastAccessdTime = session.getLastAccessedTime();
		// session超时时间
		int timeOut = session.getMaxInactiveInterval();
		ServletContext sc = session.getServletContext();
		sc.setAttribute("userSessionMap", userSessionMap);
		if (logger.isDebugEnabled()) {
			logger.debug("sessionID getCreationTime : " + new Date(creationTime));
			logger.debug(sessionID + " 最后访问时间：" + new Date(lastAccessdTime) + " 已经退出！");
			logger.debug("当前在线人数：" + userSessionMap.size());
			logger.debug("当前用户在线时长：" + (lastAccessdTime - creationTime) + " ms ");
		}
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		SysLogService logService = (SysLogService) context.getBean("logService");
		SysSession sysSession = logService.getSysSession(sessionID);
		if (sysSession != null) {
			// 过期的会话
			if (lastAccessdTime - creationTime < timeOut * 1000) {
				sysSession.setSessionstatus("O");
			} else {
				sysSession.setSessionstatus("E");
			}
			sysSession.setEndtime(new Date());
			logService.saveOrUpdate(sysSession);
		}
	}

}
