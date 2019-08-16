package com.tlys.comm.interceptor;

/**
 * �������û���¼У��������
 */
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.MapUtil;
import com.tlys.sys.model.SysFraccessLog;
import com.tlys.sys.model.SysFraccessLogOpr;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysSession;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysLogService;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysUserService;

@Component
public class AuthorityInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;
	SysNavimenu sysNavimenu;
	@Autowired
	SysLogService logService;

	@Autowired
	SysUserService userService;
	@Autowired
	SysMenuService menuService;

	private void createOprationLog(HttpServletRequest request, ActionInvocation invocation) throws Exception {
		String currentPage = request.getParameter("pageView.currentPage");
		if (currentPage != null && !currentPage.equals("") && Integer.parseInt(currentPage) > 1) {
			return;
		}
		HttpSession session = request.getSession();
		ValueStack valueStack = invocation.getStack();

		String sessionID = session.getId();
		// ����
		String quarter = CommUtils.formatQuarter(new Date());
		// ��ǰuri�����Action����
		String actionName = invocation.getProxy().getActionName();
		// ��ǰuri����ķ�����
		String methodName = invocation.getProxy().getMethod();
		// methodName = methodName.toUpperCase();
		// ���ݲ˵�ID��ȡ���в������ܵ��б�(���û���������ܰ�ť������Ϊû����Ȩ���߲�����ĩ����ť������)
		// �ݴ��ж��Ƿ�����ĩһ���˵����������ĩһ���˵����򽫴˴η��ʴ����TB_ZBC_SYS_FRACCESSLOG,���򷵻�
		// 1���û�ӵ�е����в���Ȩ�ޣ�������ť)
		Map<Integer, List<SysMenuopra>> hasOpraByMenuIdMap = (Map<Integer, List<SysMenuopra>>) session
				.getAttribute("hasOpraByMenuIdMap");
		// // 2.�������ͣ���Ϊ����ID��ֵSysOprationtypeʵ��
		// Map<String, SysOprationtype> oprationTypByIdMap = (Map<String,
		// SysOprationtype>) session
		// .getAttribute("oprationTypByIdMap");
		// �û�ÿ������ʱ�Ĳ˵�ID����Set���浽session�У������ظ���
		Set<Integer> menuIdSet = (Set<Integer>) session.getAttribute("menuIdSet");
		// ���ݲ˵�ID��ȡ����Դ������
		Map<Integer, SysFraccessLog> fraccessLogByMenuIdMap = (Map<Integer, SysFraccessLog>) session
				.getAttribute("fraccessLogByMenuIdMap");
		if (null == menuIdSet) {
			menuIdSet = new HashSet<Integer>();
		}
		// ��ֵ��ջ�л�ȡmenuCode��ֵ

		String menuCode = valueStack.findString("menuCode");
		// ��ȡ�˵�����
		SysNavimenu sysNavimenu = menuService.getSysNavimenuByMenuCode(menuCode);
		int menuid = sysNavimenu == null ? 0 : sysNavimenu.getMenuid();
		if (!menuIdSet.contains(menuid) && 0 != menuid) {
			// �ò�Ϊ����˵�����Դ�����˵�ID����menuIdSet������menuIdSet��Session�У�
			// ������Դ������ݴ���fraccessLogByMenuIdMap�У�
			SysFraccessLog fraccessLog = new SysFraccessLog();
			fraccessLog.setMenuid(String.valueOf(menuid));
			fraccessLog.setOprtime(new Date());
			fraccessLog.setQuarter(quarter);
			fraccessLog.setSessionid(sessionID);
			logService.save(fraccessLog);
			menuIdSet.add(menuid);
			session.setAttribute("menuIdSet", menuIdSet);
			if (null == fraccessLogByMenuIdMap) {
				fraccessLogByMenuIdMap = new HashMap<Integer, SysFraccessLog>();
			}
			fraccessLogByMenuIdMap.put(menuid, fraccessLog);
			session.setAttribute("fraccessLogByMenuIdMap", fraccessLogByMenuIdMap);
		}
		List<SysMenuopra> sysMenuopras = null;
		if (null != hasOpraByMenuIdMap) {
			sysMenuopras = hasOpraByMenuIdMap.get(menuid);
		}
		if (null == sysMenuopras || sysMenuopras.isEmpty()) {
			return;
		}
		// ѭ���û�Ȩ���еĲ������ܰ�ť
		for (SysMenuopra sysMenuopra : sysMenuopras) {
			String dbtname = sysMenuopra.getDbtname();
			String operid = sysMenuopra.getOperid().toUpperCase();
			// ������������Ըð�ť�Ĵ�д��ͷ���򷵻�true����ʱ���˴β����������ݿ�
			String menuopercode = sysMenuopra.getMenuopercode();
			if (null != menuopercode && !"".equals(menuopercode)) {
				String[] codeArray = menuopercode.split("_");
				if (null != codeArray && 2 <= codeArray.length) {
					if (actionName.equals(codeArray[0]) && methodName.equals(codeArray[1])) {
						if ("RED".equals(operid)) {
							break;
						}
						if ("EDI".equals(operid)) {
							break;
						}
						SysFraccessLogOpr fraccessLogOpr = new SysFraccessLogOpr();
						fraccessLogOpr.setDbtname(dbtname);
						SysFraccessLog fraccessLog = fraccessLogByMenuIdMap.get(menuid);
						fraccessLogOpr.setLogid(null == fraccessLog ? 0l : fraccessLog.getId());
						fraccessLogOpr.setOperid(operid);
						String oprtype = "";
						if (operid.equals("DEL")) {
							oprtype = "D";
						} else if (operid.equals("SAV") || operid.equals("CMT") || operid.equals("MOD")) {
							oprtype = "U";
						} else if (operid.equals("PRT") || operid.equals("EXP")) {
							oprtype = "R";
						} else if (operid.equals("ADD") || operid.equals("IMP")) {
							oprtype = "A";
						}
						fraccessLogOpr.setOprtype(oprtype);
						fraccessLogOpr.setQuarter(quarter);
						fraccessLogOpr.setOpercode(menuopercode);
						logService.save(fraccessLogOpr);
						break;
					}
				}
			}
		}
	}

	public String doIntercept(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();

		HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);
		String result = null;
		boolean hasSession = hasSession(request, response);
		if (hasSession) {
			invocation.addPreResultListener(new PreResultListener() {

				public void beforeResult(ActionInvocation actionInvocation, String arg1) {
					// TODO Auto-generated method stub
					ValueStack valueStack = actionInvocation.getStack();
					String isNew = valueStack.findString("isNew");
					// initOpraMap(valueStack, isNew);
					Map<String, SysNavimenu> sysMenuMap = menuService.findSysMenuByMenuCodeMap();
					String menuCode = valueStack.findString("menuCode");
					SysNavimenu sysNavimenu = sysMenuMap.get(menuCode);
					int menuId = (null == sysNavimenu ? 0 : sysNavimenu.getMenuid());
					MapUtil opraMap = CommUtils.initOpraMap(menuId, isNew);
					valueStack.set("opraMap", opraMap);
				}
			});
			result = invocation.invoke();
			// ����������־
			createOprationLog(request, invocation);
			// initOpraMap(invocation);
		} else {
			result = "sessionOutTime";
		}
		return result;
	}

	private boolean hasSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("sysUserSess");
		// ����û�sessionû�ж�ʧ����ֱ�ӷ���true
		if (sysUser != null) {
			return true;
		}
		// ��ǰSessionID
		String jsessionID = session.getId();
		// ��ȡ���ŵ�Session�б�
		ServletContext context = session.getServletContext();
		Map<String, HttpSession> userSessionMap = (Map<String, HttpSession>) context.getAttribute("userSessionMap");
		// ������cookie�в�������ΪJSESSIONID_USER�ļ�¼��
		// ���ĳ��cookie�д���һ����ΪJSESSIONID_USER��ֵ���Ҵ�������ŵ�session�б��У��򷵻ظ�cookie
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookiey : cookies) {
				String cookieName = cookiey.getName();
				String cookieValue = cookiey.getValue();
				if (cookieName != null && cookieName.equals("JSESSIONID_USER") && cookieValue != null
						&& null != userSessionMap && userSessionMap.containsKey(cookieValue)) {
					cookie = cookiey;
				}
			}
		}
		SysSession sysSession = null;
		// ���������Ч��cookie��������ݿ��SYS_SESSION�в��Ҽ�ΪJSESSIONID_USER��cookieֵ�ļ�¼��
		if (cookie != null) {
			// �ӵ�ǰ�����û�������ϴε�jsessionIDֵ
			userSessionMap.remove(cookie.getValue());
			// cookie�е�jsessionID��ֵ
			String cookieJsessionID = cookie.getValue();
			sysSession = logService.getSysSession(cookieJsessionID);
			// ����cookie��Чʱ��
			cookie.setMaxAge(60 * 60 * 24 * 365);
			// ����cookie·��
			cookie.setPath("/");
			// ��������cookie��ֵΪ��ǰsesionID��ֵ
			cookie.setValue(jsessionID);
			response.addCookie(cookie);

			// ���SYS_SESSION���в����ڸ�session��¼����ֱ�ӷ���false����Ϊ��session��Ч
			if (sysSession == null) {
				return false;
			}
			// ���SYS_SESSION�д��ڼ�¼������������sessionΪ��ǰsessionID��������������Ϊ��ǰʱ��
			sysSession.setSessionid(jsessionID);
			sysSession.setStarttime(new Date());
			String userid = sysSession.getUserid();

			if (userid != null && userid.equalsIgnoreCase("anonym")) {
				return false;
			}
			sysUser = userService.load(userid);
			session.setAttribute("sysUserSess", sysUser);
			// ��ȡ�û�Ȩ�ޣ���Ȩ�ޱ�����Session��
			userService.initMenuByRoles(sysUser);
			logService.saveOrUpdate(sysSession);
			// ���»Ự�����еĲ������ڹر����������ʱ������ԭ����sessionidΪ��ǰsessionid
			logService.updateSysFraccessLogBySessionID(cookieJsessionID, jsessionID);
			return true;
		}
		return false;
	}

	public SysNavimenu getSysNavimenu() {
		return sysNavimenu;
	}

	public void setSysNavimenu(SysNavimenu sysNavimenu) {
		this.sysNavimenu = sysNavimenu;
	}
}
