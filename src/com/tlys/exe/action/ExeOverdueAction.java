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
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicOverTime;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.service.ExeDicService;
import com.tlys.exe.service.ExeOverdueService;

/**
 * 运行预警Action
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "frame", location = "/exe/exe-overdue-frame.jsp"),
		@Result(name = "left", location = "/exe/exe-overdue-left.jsp"),
		@Result(name = "main", location = "/exe/exe-overdue-main.jsp"),
		@Result(name = "total", location = "/exe/exe-overdue-total.jsp"),
		@Result(name = "detail", location = "/exe/exe-overdue-detail.jsp") })
public class ExeOverdueAction extends _BaseAction {
	@Autowired
	private ExeOverdueService exeOverdueService;// 注入超时service
	@Autowired
	private ExeDicService exeDicService;// 注入DIc Service

	private String entpr_id;// 企业id
	private String medium_id;// 充装介质id
	private String over_id;// 超时id
	private String over_type;// 超时类型

	private List<DicSinocorp> listDicSinocorp;// 定义公司列表
	private List<DicOverTime> listDicOvertime;// 定义超时阶段列表
	private List<DicGoodscategory> listDicGoodscategory;// 定义充装介质列表

	private List<DicSinocorp> listDicSinocorpRes;// 定义公司列表,查询结果
	private List<DicOverTime> listDicOvertimeRes;// 定义超时阶段列表,查询结果
	private List<DicGoodscategory> listDicGoodscategoryRes;// 定义充装介质列表 ,查询结果
	private List<int[]> listOverdue;// 定义查询列表

	private List<ExeDcarStat> listExeDcarStat;// 动态信息

	/**
	 * 数据权限控制
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_OVER");
	}

	public List<ExeDcarStat> getListExeDcarStat() {
		return listExeDcarStat;
	}

	public void setListExeDcarStat(List<ExeDcarStat> listExeDcarStat) {
		this.listExeDcarStat = listExeDcarStat;
	}

	public String getEntpr_id() {
		return entpr_id;
	}

	public void setEntpr_id(String entpr_id) {
		this.entpr_id = entpr_id;
	}

	public String getMedium_id() {
		return medium_id;
	}

	public void setMedium_id(String medium_id) {
		this.medium_id = medium_id;
	}

	public String getOver_id() {
		return over_id;
	}

	public void setOver_id(String over_id) {
		this.over_id = over_id;
	}

	public String getOver_type() {
		return over_type;
	}

	public void setOver_type(String over_type) {
		this.over_type = over_type;
	}

	public List<int[]> getListOverdue() {
		return listOverdue;
	}

	public void setListOverdue(List<int[]> listOverdue) {
		this.listOverdue = listOverdue;
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
		listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");
		listDicGoodscategory = exeDicService.listDicGoodscategory();
		listDicOvertime = exeDicService.listDicOverTime("0");// 默认从铁路线查询
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
	 * 查询结果
	 * 
	 * @return
	 */
	public String searchResult() {
		// medium_id格式010,028
		// over_id格式1,2,3
		// entpr_id格式3500000,45900000
		// 充装介质和企业id如果为空的话取默认的
		listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");
		listDicGoodscategory = exeDicService.listDicGoodscategory();
		listDicOvertime = exeDicService.listDicOverTime(over_type);
		String entpr_id2 = "";// /zy000010,zy000020格式，用于exeOverdueDao.listOverdue查询
		if (medium_id.equals("")) {
			for (DicGoodscategory dicGoodscategory : listDicGoodscategory) {
				medium_id += dicGoodscategory.getGoodsid() + ",";
			}
			if (!medium_id.equals(""))
				medium_id = medium_id.substring(0, medium_id.length() - 1);
		}
		if (entpr_id.equals("")) {
			for (DicSinocorp dicSinocorp : listDicSinocorp) {
				entpr_id += "'" + dicSinocorp.getCorpid() + "',";
				entpr_id2 += dicSinocorp.getCorpid() + ",";
			}
			if (!entpr_id.equals("")) {
				entpr_id = entpr_id.substring(0, entpr_id.length() - 1);
				entpr_id2 = entpr_id2.substring(0, entpr_id2.length() - 1);
			}
		} else {
			String newEntpr_id = "";
			String[] entprArr = entpr_id.split(",");
			for (int i = 0; i < entprArr.length; i++) {
				newEntpr_id += "'" + entprArr[i] + "',";
				entpr_id2 += "" + entprArr[i] + ",";
			}
			if (!newEntpr_id.equals("")) {
				newEntpr_id = newEntpr_id.substring(0, newEntpr_id.length() - 1);
				entpr_id2 = entpr_id2.substring(0, entpr_id2.length() - 1);
			}
			entpr_id = newEntpr_id;
		}
		listDicSinocorpRes = exeDicService.listDicSinocorpByIdArr(entpr_id);
		listDicGoodscategoryRes = exeDicService.listDicGoodscategoryByIdArr(medium_id);
		listDicOvertimeRes = exeDicService.listDicOverTimeByIdArr(over_id);
		listOverdue = exeOverdueService.listOverdue(entpr_id, entpr_id2, medium_id, over_id, over_type);
		return "main";
	}

	/**
	 * 查询结果
	 * 
	 * @return
	 */
	public String totalResult() {
		listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");
		listDicOvertime = exeDicService.listDicOverTime("0");
		listOverdue = exeOverdueService.listOverdue();
		return "total";
	}

	/**
	 * 取得车辆动态详细信息
	 * 
	 * @return
	 */
	public String getDetail() {
		String medium_id = getRequest().getParameter("medium_id");
		String over_id = getRequest().getParameter("over_id");
		String entpr_id = getRequest().getParameter("entpr_id");
		listExeDcarStat = exeOverdueService.getOverdueDetail(medium_id, entpr_id, over_id);
		return "detail";
	}

	public List<DicSinocorp> getListDicSinocorp() {
		return listDicSinocorp;
	}

	public void setListDicSinocorp(List<DicSinocorp> listDicSinocorp) {
		this.listDicSinocorp = listDicSinocorp;
	}

	public List<DicOverTime> getListDicOvertime() {
		return listDicOvertime;
	}

	public void setListDicOvertime(List<DicOverTime> listDicOvertime) {
		this.listDicOvertime = listDicOvertime;
	}

	public List<DicGoodscategory> getListDicGoodscategory() {
		return listDicGoodscategory;
	}

	public void setListDicGoodscategory(List<DicGoodscategory> listDicGoodscategory) {
		this.listDicGoodscategory = listDicGoodscategory;
	}

	public List<DicSinocorp> getListDicSinocorpRes() {
		return listDicSinocorpRes;
	}

	public void setListDicSinocorpRes(List<DicSinocorp> listDicSinocorpRes) {
		this.listDicSinocorpRes = listDicSinocorpRes;
	}

	public List<DicOverTime> getListDicOvertimeRes() {
		return listDicOvertimeRes;
	}

	public void setListDicOvertimeRes(List<DicOverTime> listDicOvertimeRes) {
		this.listDicOvertimeRes = listDicOvertimeRes;
	}

	public List<DicGoodscategory> getListDicGoodscategoryRes() {
		return listDicGoodscategoryRes;
	}

	public void setListDicGoodscategoryRes(List<DicGoodscategory> listDicGoodscategoryRes) {
		this.listDicGoodscategoryRes = listDicGoodscategoryRes;
	}

}
