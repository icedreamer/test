package com.tlys.sys.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/sys")
public class SysSecurityAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1714973786139205180L;

	public String info() throws Exception {

		return "info";
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("BI_SECURITY");
	}

}
