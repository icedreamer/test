package com.tlys.exe.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeGcarEvta;
import com.tlys.exe.service.ExeDcarStatService;
import com.tlys.exe.service.ExeGcarEvtaService;

/**
 * 车辆轨迹查询Action
 * 
 * @author 孔垂云
 * 
 */

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "search", location = "/exe/exe-gcarEvta-search.jsp"),
		@Result(name = "frame", location = "/exe/exe-gcarEvta-frame.jsp"),
		@Result(name = "left", location = "/exe/exe-gcarEvta-left.jsp"),
		@Result(name = "main", location = "/exe/exe-gcarEvta-main.jsp") })
public class ExeGcarEvtaAction extends _BaseAction {

	// 注入service
	@Autowired
	ExeGcarEvtaService gcarEvtaService;

	@Autowired
	ExeDcarStatService dcarStatService;

	/**
	 * 数据权限控制
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_GCAR");
	}
	
	public ExeDcarStatService getDcarStatService() {
		return dcarStatService;
	}

	public void setDcarStatService(ExeDcarStatService dcarStatService) {
		this.dcarStatService = dcarStatService;
	}

	public ExeGcarEvtaService getGcarEvtaService() {
		return gcarEvtaService;
	}

	public void setGcarEvtaService(ExeGcarEvtaService gcarEvtaService) {
		this.gcarEvtaService = gcarEvtaService;
	}

	// 定义查询结果返回
	private List<ExeGcarEvta> listGcarEvta;// 定义查询结果列表
	private ExeGcarEvta gCarEvta;
	private ExeDcarStat dcarStat;// 定义车号详细信息

	public ExeDcarStat getDcarStat() {
		return dcarStat;
	}

	public void setDcarStat(ExeDcarStat dcarStat) {
		this.dcarStat = dcarStat;
	}

	public List<ExeGcarEvta> getListGcarEvta() {
		return listGcarEvta;
	}

	public void setListGcarEvta(List<ExeGcarEvta> listGcarEvta) {
		this.listGcarEvta = listGcarEvta;
	}

	// 定义查询字段
	private String car_no;
	private String s_date;
	private String e_date;

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getS_date() {
		return s_date;
	}

	public void setS_date(String s_date) {
		this.s_date = s_date;
	}

	public String getE_date() {
		return e_date;
	}

	public void setE_date(String e_date) {
		this.e_date = e_date;
	}

	/**
	 * 进入框架
	 * 
	 * @return
	 */
	public String frame() {
		return "frame";
	}

	/**
	 * 框架左边内容
	 * 
	 * @return
	 */
	public String left() {
		e_date = StringUtil.getSystemDate();
		s_date = StringUtil.getOptSystemDate(-30);
		return "left";
	}

	/**
	 * 框架main内容
	 * 
	 * @return
	 */
	public String main() {
		return "main";
	}

	/**
	 * 进入查询界面
	 * 
	 * @return
	 */
	public String search() {
		initOpraMap("EXE_GCAR");
		return "search";
	}

	/**
	 * 显示查询结果
	 * 
	 * @return
	 */
	public String searchResult() {
		initOpraMap("EXE_GCAR");
		if (car_no.length() == 7) {
			if (e_date == null || e_date.equals("")) {
				e_date = StringUtil.getSystemDate();
				s_date = StringUtil.getOptSystemDate(-30);
			}
			listGcarEvta = gcarEvtaService.listGcarEvta(car_no, s_date, e_date);
			dcarStat = dcarStatService.loadExeDcarStat(car_no);
		}
		return "main";
	}

	public ExeGcarEvta getGCarEvta() {
		return gCarEvta;
	}

	public void setGCarEvta(ExeGcarEvta carEvta) {
		gCarEvta = carEvta;
	}

}
