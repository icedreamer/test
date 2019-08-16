package com.tlys.equ.action;

import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;

@Controller
public class EquRepYplanAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8213796333177145838L;

	public String list() throws Exception {
		
		return "list";
	}
}
