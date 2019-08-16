package com.tlys.comm.interceptor;

/**
 * 描述：用户登录校验拦截器
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
		// 季度
		String quarter = CommUtils.formatQuarter(new Date());
		// 当前uri请求的Action名称
		String actionName = invocation.getProxy().getActionName();
		// 当前uri请求的方法名
		String methodName = invocation.getProxy().getMethod();
		// methodName = methodName.toUpperCase();
		// 根据菜单ID获取所有操作功能的列表，(如果没有所属功能按钮，则认为没有授权或者不是最末级按钮，返回)
		// 据此判断是否是最末一级菜单，如果是最末一级菜单，则将此次访问存入表TB_ZBC_SYS_FRACCESSLOG,否则返回
		// 1。用户拥有的所有操作权限（操作按钮)
		Map<Integer, List<SysMenuopra>> hasOpraByMenuIdMap = (Map<Integer, List<SysMenuopra>>) session
				.getAttribute("hasOpraByMenuIdMap");
		// // 2.操作类型，键为类型ID，值SysOprationtype实例
		// Map<String, SysOprationtype> oprationTypByIdMap = (Map<String,
		// SysOprationtype>) session
		// .getAttribute("oprationTypByIdMap");
		// 用户每次请求时的菜单ID，用Set保存到session中，避免重复。
		Set<Integer> menuIdSet = (Set<Integer>) session.getAttribute("menuIdSet");
		// 根据菜单ID获取操作源表数据
		Map<Integer, SysFraccessLog> fraccessLogByMenuIdMap = (Map<Integer, SysFraccessLog>) session
				.getAttribute("fraccessLogByMenuIdMap");
		if (null == menuIdSet) {
			menuIdSet = new HashSet<Integer>();
		}
		// 从值的栈中获取menuCode的值

		String menuCode = valueStack.findString("menuCode");
		// 获取菜单对象
		SysNavimenu sysNavimenu = menuService.getSysNavimenuByMenuCode(menuCode);
		int menuid = sysNavimenu == null ? 0 : sysNavimenu.getMenuid();
		if (!menuIdSet.contains(menuid) && 0 != menuid) {
			// 该步为保存菜单操作源表，将菜单ID存入menuIdSet，保持menuIdSet在Session中，
			// 并将该源表的数据存入fraccessLogByMenuIdMap中，
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
		// 循环用户权限中的操作功能按钮
		for (SysMenuopra sysMenuopra : sysMenuopras) {
			String dbtname = sysMenuopra.getDbtname();
			String operid = sysMenuopra.getOperid().toUpperCase();
			// 如果方法名中以该按钮的大写开头，则返回true，此时将此次操作存入数据库
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
			// 创建操作日志
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
		// 如果用户session没有丢失，将直接返回true
		if (sysUser != null) {
			return true;
		}
		// 当前SessionID
		String jsessionID = session.getId();
		// 获取活着的Session列表
		ServletContext context = session.getServletContext();
		Map<String, HttpSession> userSessionMap = (Map<String, HttpSession>) context.getAttribute("userSessionMap");
		// 在所有cookie中查找名字为JSESSIONID_USER的记录，
		// 如果某个cookie中存在一个键为JSESSIONID_USER的值，且存在与活着的session列表中，则返回该cookie
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
		// 如果存在有效的cookie，则从数据库表SYS_SESSION中查找键为JSESSIONID_USER的cookie值的记录，
		if (cookie != null) {
			// 从当前在线用户中清除上次的jsessionID值
			userSessionMap.remove(cookie.getValue());
			// cookie中的jsessionID的值
			String cookieJsessionID = cookie.getValue();
			sysSession = logService.getSysSession(cookieJsessionID);
			// 设置cookie有效时间
			cookie.setMaxAge(60 * 60 * 24 * 365);
			// 设置cookie路径
			cookie.setPath("/");
			// 重新设置cookie的值为当前sesionID的值
			cookie.setValue(jsessionID);
			response.addCookie(cookie);

			// 如果SYS_SESSION表中不存在该session记录，则直接返回false，认为改session无效
			if (sysSession == null) {
				return false;
			}
			// 如果SYS_SESSION中存在记录，则重新这是session为当前sessionID，并将日期设置为当前时间
			sysSession.setSessionid(jsessionID);
			sysSession.setStarttime(new Date());
			String userid = sysSession.getUserid();

			if (userid != null && userid.equalsIgnoreCase("anonym")) {
				return false;
			}
			sysUser = userService.load(userid);
			session.setAttribute("sysUserSess", sysUser);
			// 获取用户权限，将权限保存在Session中
			userService.initMenuByRoles(sysUser);
			logService.saveOrUpdate(sysSession);
			// 更新会话过程中的操作，在关闭浏览器访问时将更新原来的sessionid为当前sessionid
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
