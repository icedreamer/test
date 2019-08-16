package com.tlys.dic.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.DicMap;

/**
 * @author fengym
 * ����һ��ʼ��֪Ϊ�ζ���
 * Ŀǰ��֪������ջ��棬dataCacheClear()����
 */



@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicAction extends _BaseAction {
	
	@Autowired
	DicMap dicMap;
	
	String globalFlag;
	
	
	@Action(results={@Result(name = "success", location="/${target}.jsp")	}) 
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String dataCacheClear() throws Exception{
		dicMap.setGlobalRefreshFlag();
		msg = new Msg("success","���ݻ�����ȫ��ˢ�£�");
		return preDataref();
	}
	
	public String preDataref(){
		globalFlag = (String)getApplication().getAttribute("globalRefreshFlag");
		return "pre-dataref";
	}


	@Override
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

	public String getGlobalFlag() {
		return globalFlag;
	}

	public void setGlobalFlag(String globalFlag) {
		this.globalFlag = globalFlag;
	}
	
	
}
