package com.tlys.exe.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.service.ExeDicService;
import com.tlys.exe.service.ExeTransportService;
import com.tlys.exe.service.ExeYyStat;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
public class ExeYystatAction extends _BaseAction {

	@Autowired
	private ExeTransportService exeTransportService;
	@Autowired
	private ExeDicService exeDicService;// 注入DIc Service

	private List<ExeYyStat> list;// 统计结果列表
	private List<DicSinocorp> listDicSinocorp;// 定义公司列表
	private List<DicGoodscategory> listDicGoodscategory;// 定义充装介质列表
	private List<String> listYear;// 年份列表
	private List<Object[]> listNoCycle;// 未运行车辆详细
	private String year;// 年
	private String month;// 月
	private String quarter;// 季度

	private String stat_type;// 统计类型
	private String entpr_id;// 企业id
	private String medium_id;// 充装介质id
	private String area_id;// 区域id

	/**
	 * 数据权限控制
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_YYS");
	}
	
	
	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getStat_type() {
		return stat_type;
	}

	public void setStat_type(String stat_type) {
		this.stat_type = stat_type;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public List<String> getListYear() {
		return listYear;
	}

	public void setListYear(List<String> listYear) {
		this.listYear = listYear;
	}

	public List<ExeYyStat> getList() {
		return list;
	}

	public void setList(List<ExeYyStat> list) {
		this.list = list;
	}

	public String frame() {
		return "frame";

	}

	public String left() {
		listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");
		listDicGoodscategory = exeDicService.listDicGoodscategory();
		int yearInt = Integer.parseInt(StringUtil.dateToString(new Date(), "yyyy"));
		listYear = new ArrayList<String>();
		for (int i = yearInt; i > yearInt - 10; i--) {
			listYear.add(String.valueOf(i));
		}
		String date = StringUtil.dateToString(new Date(), "yyyy-MM");
		year = date.substring(0, 4);
		month = date.substring(5, 7);
		if (month.equals("01") || month.equals("02") || month.equals("03"))
			quarter = "1";
		else if (month.equals("04") || month.equals("05") || month.equals("06"))
			quarter = "2";
		else if (month.equals("07") || month.equals("08") || month.equals("09"))
			quarter = "3";
		else if (month.equals("10") || month.equals("11") || month.equals("12"))
			quarter = "4";
		list = exeTransportService.getYyStatistics("0", year, "", month, "", "");
		return "left";
	}

	/**
	 * 运用统计列表
	 * 
	 * @return
	 */
	public String main() {
		stat_type = "0";
		String date = StringUtil.dateToString(new Date(), "yyyy-MM");
		year = date.substring(0, 4);
		month = date.substring(5, 7);
		list = exeTransportService.getYyStatistics(stat_type, year, "", month, "", "");
		return "main";
	}

	/**
	 * 运用统计查询结果
	 * 
	 * @return
	 */
	public String searchResult() {
		list = exeTransportService.getYyStatistics(stat_type, year, quarter, month, entpr_id, medium_id);
		return "main";
	}

	/**
	 * 取得未运行车辆详细信息
	 * 
	 * @return
	 */
	public String noCycleDetail() {
		listNoCycle = exeTransportService.getTransYYStatNoCycleDetail(stat_type, year, quarter, month, entpr_id,
				medium_id, area_id);
		return "noCycle";

	}

	public List<DicSinocorp> getListDicSinocorp() {
		return listDicSinocorp;
	}

	public void setListDicSinocorp(List<DicSinocorp> listDicSinocorp) {
		this.listDicSinocorp = listDicSinocorp;
	}

	public List<DicGoodscategory> getListDicGoodscategory() {
		return listDicGoodscategory;
	}

	public void setListDicGoodscategory(List<DicGoodscategory> listDicGoodscategory) {
		this.listDicGoodscategory = listDicGoodscategory;
	}

	public List<Object[]> getListNoCycle() {
		return listNoCycle;
	}

	public void setListNoCycle(List<Object[]> listNoCycle) {
		this.listNoCycle = listNoCycle;
	}
}
