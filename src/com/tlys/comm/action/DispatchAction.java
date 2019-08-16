package com.tlys.comm.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;

/**
 * 万用转发Action
 * 使用其代替直接写指向jsp的URL。这样可以统一控制权限等操作。
 * 调用方法：/dispatch.action?target=dic/dic-frame
 * 表示此次调用的目标页面是：/dic/dic-frame.jsp
 * @author fengym
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/")
public class DispatchAction extends _BaseAction {
	private String target;
	private String param;
	
	@Action(results={@Result(name = "success", location="/${target}.jsp")	}) 
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
