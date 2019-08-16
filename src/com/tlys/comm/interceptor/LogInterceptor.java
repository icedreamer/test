package com.tlys.comm.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.tlys.sys.model.SysUser;

@Component
public class LogInterceptor extends MethodFilterInterceptor {

	/**
	 * 日志拦截器
	 */
	private static final long serialVersionUID = 1L;

	// private SysLogService sysLogService;

	private String methodFilterString;

	// 被拦截的ACTION 的名称

	private static final Logger logger = Logger.getLogger(LogInterceptor.class.getName());

	public String doIntercept(ActionInvocation invocation) throws Exception {
		String rst = "msg";
		ActionSupport action = (ActionSupport) invocation.getAction();
		String methodName = invocation.getProxy().getMethod();
		String action_name = action.getText(invocation.getInvocationContext().getName());// 资源化Action名称
		String method_name = action.getText(methodName);// 资源化执行方法名称
		rst = invocation.invoke();
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		Map paraMap = request.getParameterMap();
		ActionContext ctx = invocation.getInvocationContext();
		Map<?, ?> session = ctx.getSession();
		SysUser sysUser = (SysUser) session.get("sysUserSess");
		String username = "GUEST";
		String corpid = "";
		if (null != sysUser) {
			username = sysUser.getUsername();
			corpid = sysUser.getCorpid();
		}
		StringBuffer logDesc = new StringBuffer();
		logDesc.append(username);
		logDesc.append("\" \"");
		logDesc.append(corpid);
		logDesc.append("\" \"");
		logDesc.append(action_name);
		logDesc.append("\" \"");
		logDesc.append(method_name);
		logDesc.append("\" \"");
		logDesc.append(uri);
		logDesc.append("\" \"");
		StringBuffer buffer = new StringBuffer();
		try {
			for (Iterator iter = paraMap.keySet().iterator(); iter.hasNext();) {
				String pname = (String) iter.next();
				String pval = arr2Str((String[]) paraMap.get(pname));
				buffer.append(pname + "=" + pval + ";");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logDesc.append(buffer.toString());
		logDesc.append("\" \"");
		logDesc.append(rst);
		logDesc.append("\" \"");

		logDesc.append("\"");
		logDesc.append("\r\n");
		logger.info(logDesc.toString());
		return rst;
	}

	private String arr2Str(String[] arr) {
		String arrStr = "";
		if (null != arr) {
			for (int i = 0; i < arr.length; i++) {
				if (!"".equals(arrStr)) {
					arrStr += ",";
				}
				arrStr += arr[i];
			}
		}
		return arrStr;
	}
}
