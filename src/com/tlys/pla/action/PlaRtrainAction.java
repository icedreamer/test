package com.tlys.pla.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.FormatUtil;
import com.tlys.pla.service.PlaRtrainService;

/**
 * 此action及其对应的service,dao均服务于请车及批复计划模块
 * 只用来调用存储过程
 * P_ZBC_PLAN_GENMUPRTRAIN、P_ZBC_PLAN_GENMSPRTRAIN、P_ZBC_PLAN_GENMOPRTRAIN
 * @author fengym
 *
 */



@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaRtrainAction extends _BaseAction {
	private String month;
	
	@Autowired
	PlaRtrainService plaRtrainService;

	
	public String execute() throws Exception {
		final String monthStr = FormatUtil.nowDateStr(FormatUtil.COMPACT_MONTH);
		//plaRtrainService.syncPlaRtrain();
		//由于更新时间长，新开线程
		new Thread(){
			public void run(){
				plaRtrainService.syncPlaRtrain(monthStr);
			}
		}.start();
		return SUCCESS;
	}
	
	
	
	
	//-------------------------------------


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}



}
