package com.tlys.exe.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCarmaker;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicEntprStn;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicOverTime;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.common.util.GlobalConst;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeAeiSearchCarField;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeStncar;
import com.tlys.exe.model.ExeStncarId;
import com.tlys.exe.model.ExeStntrn;
import com.tlys.exe.service.ExeAeiService;
import com.tlys.exe.service.ExeDicService;
import com.tlys.exe.service.ExeEquipMonitorVO;
import com.tlys.exe.service.ExeZbclylVO;
import com.tlys.exe.service.ExeZqjcclVO;
import com.tlys.exe.service.ExeZqxcDistVO;
import com.tlys.sys.model.SysUser;

/**
 * Aei查询action
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( {
		@Result(name = "listStntrn", location = "/exe/exe-aei-listStntrn.jsp"),
		@Result(name = "listStncar", location = "/exe/exe-aei-listStncar.jsp"),
		@Result(name = "addStntrn", location = "/exe/exe-aei-addStntrn.jsp"),
		@Result(name = "updateStntrn", location = "/exe/exe-aei-updateStntrn.jsp"),
		@Result(name = "addStncar", location = "/exe/exe-aei-addStncar.jsp"),
		@Result(name = "updateStncar", location = "/exe/exe-aei-updateStncar.jsp"),
		@Result(name = "carFrame", location = "/exe/exe-aei-carFrame.jsp"),
		@Result(name = "carLeft", location = "/exe/exe-aei-carLeft.jsp"),
		@Result(name = "carMain", location = "/exe/exe-aei-carMain.jsp"),
		@Result(name = "zbclyl", location = "/exe/exe-aei-zbclyl.jsp"), // 自备车利用率
		@Result(name = "zqxcDist", location = "/exe/exe-aei-zqxcDist.jsp"), // 站区现车分布
		@Result(name = "zqjcclStat", location = "/exe/exe-aei-zqjcclStat.jsp"),// 站区进出车辆货物信息匹配统计表
		@Result(name = "equipMonitor", location = "/exe/exe-aei-equipMonitor.jsp"),// 站区AEI设备监控
		@Result(name = "zyxFrame", location = "/exe/exe-aei-zyxFrame.jsp"),// 专用线停靠时间
		@Result(name = "zyxLeft", location = "/exe/exe-aei-zyxLeft.jsp"),
		@Result(name = "zyxMain", location = "/exe/exe-aei-zyxMain.jsp") })
public class ExeAeiAction extends _BaseAction {
	@Autowired
	ExeAeiService exeAeiService;
	@Autowired
	ExeDicService exeDicService;

	private List<DicSinocorp> listDicSinocorp;// 报告站所属企业
	private List<DicCarkind> listDicCarkind;// 车种列表
	private List<DicCartype> listDicCartype;// 车型列表
	private List<DicCarmaker> listDicCarmaker;// 车辆制造企业列表
	private List<DicEntprStn> listDicEntprStn;// 根据企业id查找对应的企业站区列表
	private List<DicAreacorp> listDicAreaCorp;// 区域列表
	private List<DicGoodscategory> listDicGoodscategory;// 充装介质列表

	private List<ExeStntrn> listStntrn;// 列车出入信息列表
	private List<ExeStncar> listStncar;// 车辆信息列表
	private ExeStntrn exeStntrn;// 列车出入信息model
	private ExeStncar exeStncar;// 车辆信息model
	private List<Integer> listAei_no;// Aei_no列表

	private List<ExeZbclylVO> listZbclyl;// 自备车利用率
	private List<ExeZqxcDistVO> listZqxcDist;// 站区现车分布
	private List<ExeDcarStat> listDcarStat;// 车辆动态列表
	private List<ExeZqjcclVO> listZqjccl;// 站区进出车辆
	private List<ExeEquipMonitorVO> listEquipMonitor;// 设备监控

	private List<int[]> listOverdue;// 专用线停靠定义查询列表
	private List<DicOverTime> listDicOvertime;// 专用线停靠定义超时阶段列表

	private int[] rowspan;// 定义区域公司包含的子公司数

	// 查询条件
	private String s_date;// 查询列车起始日期
	private String e_date;// 查询列车终止日期
	private String max_date;// 自备车利用率最大日期
	private String zbc_flag;// 是否是自备车，1是，0否
	private String car_no;// 车号
	private String stn_entpr_id;// 所属企业id
	private String area_id;// 所属区域id
	private String car_medium_id;// 充装介质

	private String arr_time_hour;// 定义到达小时
	private String arr_time_min;// 定义到达分钟

	private HashMap<String, String> mapStnName;// 页面站区id对应站区名称map

	private HashMap<String, String> mapCorpName;// 页面车号对应的所属企业
	private HashMap<String, String> mapCarMedium;// 页面车号对应的充装介质
	private HashMap<String, String> mapInOutFlag;// 页面车号对应的进出标识

	public List<int[]> getListOverdue() {
		return listOverdue;
	}

	public void setListOverdue(List<int[]> listOverdue) {
		this.listOverdue = listOverdue;
	}

	public HashMap<String, String> getMapCorpName() {
		return mapCorpName;
	}

	public void setMapCorpName(HashMap<String, String> mapCorpName) {
		this.mapCorpName = mapCorpName;
	}

	public HashMap<String, String> getMapCarMedium() {
		return mapCarMedium;
	}

	public void setMapCarMedium(HashMap<String, String> mapCarMedium) {
		this.mapCarMedium = mapCarMedium;
	}

	public HashMap<String, String> getMapInOutFlag() {
		return mapInOutFlag;
	}

	public void setMapInOutFlag(HashMap<String, String> mapInOutFlag) {
		this.mapInOutFlag = mapInOutFlag;
	}

	public List<DicGoodscategory> getListDicGoodscategory() {
		return listDicGoodscategory;
	}

	public void setListDicGoodscategory(List<DicGoodscategory> listDicGoodscategory) {
		this.listDicGoodscategory = listDicGoodscategory;
	}

	/**
	 * 数据权限控制
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_AEI");
	}

	public String getArr_time_hour() {
		return arr_time_hour;
	}

	public void setArr_time_hour(String arr_time_hour) {
		this.arr_time_hour = arr_time_hour;
	}

	public String getArr_time_min() {
		return arr_time_min;
	}

	public void setArr_time_min(String arr_time_min) {
		this.arr_time_min = arr_time_min;
	}

	/**
	 * AEI查询车辆页面
	 * 
	 * @return
	 */
	public String carFrame() {
		return "carFrame";
	}

	/**
	 * AEI查询条件，车辆
	 * 
	 * @return
	 */
	public String carLeft() {
		s_date = StringUtil.getOptSystemDate(new Date(), -30);
		e_date = StringUtil.getSystemDate();
		listDicGoodscategory = exeDicService.listDicGoodscategory();// 充装介质
		listDicAreaCorp = exeDicService.listDicAreacorp("dataAutho");// 区域
		// listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");// 公司
		listDicSinocorp = exeDicService.listZqqy(1);// 公司
		return "carLeft";
	}

	/**
	 * AEI查询车辆结果,默认界面
	 * 
	 * @return
	 */
	public String carMain() {
		initOpraMap("EXE_AEI");
		s_date = StringUtil.getOptSystemDate(new Date(), -30);
		e_date = StringUtil.getSystemDate();
		car_no = "";
		stn_entpr_id = "";
		area_id = "";
		car_medium_id = "";
		zbc_flag = "1";
		ExeAeiSearchCarField field = new ExeAeiSearchCarField();
		field.setS_date(s_date);
		field.setE_date(e_date);
		field.setCar_no(car_no);
		field.setStn_entpr_id(stn_entpr_id);
		field.setArea_id(area_id);
		field.setCar_medium_id(car_medium_id);
		field.setZbc_flag(zbc_flag);
		String params = "?s_date=" + s_date + "&e_date=" + e_date + "&zbc_flag=" + zbc_flag + "&car_no=" + car_no
				+ "&stn_entpr_id=" + stn_entpr_id + "&area_id=" + area_id + "&car_medium_id=" + car_medium_id;
		String pageUrl = "/exe/exe-aei!carMain.action" + params;
		int totalRecord = exeAeiService.getListStncarCount(field);
		pageView = exeAeiService.searchStncar(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : 10));
		listStncar = pageView.getRecords();

		String car_noArr = "";
		for (ExeStncar car : listStncar) {
			car_noArr += car.getCar_no() + ",";
		}
		String rptnameArr = "";
		for (ExeStncar car : listStncar) {
			rptnameArr += car.getId().getRptname() + ",";
		}
		String stnIdArr = "";
		for (ExeStncar car : listStncar) {
			stnIdArr += car.getStn_name() + ",";
		}
		mapCorpName = exeDicService.getCorpNameByCar_noArr2(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapCarMedium = exeDicService.getMediumIdByCar_noArr(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapInOutFlag = exeDicService.getInOutFlagByRptNameArr(rptnameArr.equals("") ? rptnameArr : rptnameArr
				.substring(0, rptnameArr.length() - 1));
		mapStnName = exeDicService.getStnNameByStnIdArr(stnIdArr.equals("") ? stnIdArr : stnIdArr.substring(0, stnIdArr
				.length() - 1));

		return "carMain";
	}

	/**
	 * AEI查询车辆，查询结果
	 * 
	 * @return
	 */
	public String carSearchResult() {
		initOpraMap("EXE_AEI");
		ExeAeiSearchCarField field = new ExeAeiSearchCarField();
		field.setS_date(s_date);
		field.setE_date(e_date);
		field.setCar_no(car_no);
		field.setStn_entpr_id(stn_entpr_id);
		field.setArea_id(area_id);
		field.setCar_medium_id(car_medium_id);
		field.setZbc_flag(zbc_flag);
		String params = "?s_date=" + s_date + "&e_date=" + e_date + "&zbc_flag=" + zbc_flag + "&car_no=" + car_no
				+ "&stn_entpr_id=" + stn_entpr_id + "&area_id=" + area_id + "&car_medium_id=" + car_medium_id;
		String pageUrl = "/exe/exe-aei!carSearchResult.action" + params;
		int totalRecord = exeAeiService.getListStncarCount(field);
		pageView = exeAeiService.searchStncar(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : 10));
		listStncar = pageView.getRecords();

		String car_noArr = "";
		for (ExeStncar car : listStncar) {
			car_noArr += car.getCar_no() + ",";
		}
		String rptnameArr = "";
		for (ExeStncar car : listStncar) {
			rptnameArr += car.getId().getRptname() + ",";
		}
		String stnIdArr = "";
		for (ExeStncar car : listStncar) {
			stnIdArr += car.getStn_name() + ",";
		}
		mapCorpName = exeDicService.getCorpNameByCar_noArr2(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapCarMedium = exeDicService.getMediumIdByCar_noArr(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapInOutFlag = exeDicService.getInOutFlagByRptNameArr(rptnameArr.equals("") ? rptnameArr : rptnameArr
				.substring(0, rptnameArr.length() - 1));
		mapStnName = exeDicService.getStnNameByStnIdArr(stnIdArr.equals("") ? stnIdArr : stnIdArr.substring(0, stnIdArr
				.length() - 1));

		return "carMain";
	}

	/**
	 * 进入列车信息查询界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchStntrn() throws Exception {
		s_date = StringUtil.getOptSystemDate(new Date(), -30);
		e_date = StringUtil.getSystemDate();
		initOpraMap("EXE_AEI");
		String pageUrl = "/exe/exe-aei!searchStntrn.action?s_date=" + s_date + "&e_date=" + e_date;
		int totalRecord = exeAeiService.getListStntrnCount(s_date, e_date);
		pageView = exeAeiService.listStntrn(s_date, e_date, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : GlobalConst.pageSize));
		listStntrn = pageView.getRecords();

		String stnIdArr = "";
		for (ExeStntrn trn : listStntrn) {
			stnIdArr += trn.getStn_name() + ",";
		}
		mapStnName = exeDicService.getStnNameByStnIdArr(stnIdArr.equals("") ? stnIdArr : stnIdArr.substring(0, stnIdArr
				.length() - 1));
		return "listStntrn";
	}

	/**
	 * 查询列车信息结果
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchStntrnResult() throws Exception {
		initOpraMap("EXE_AEI");
		String pageUrl = "/exe/exe-aei!searchStntrnResult.action?s_date=" + s_date + "&e_date=" + e_date;
		int totalRecord = exeAeiService.getListStntrnCount(s_date, e_date);
		pageView = exeAeiService.listStntrn(s_date, e_date, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : GlobalConst.pageSize));
		listStntrn = pageView.getRecords();
		String stnIdArr = "";
		for (ExeStntrn trn : listStntrn) {
			stnIdArr += trn.getStn_name() + ",";
		}
		mapStnName = exeDicService.getStnNameByStnIdArr(stnIdArr.equals("") ? stnIdArr : stnIdArr.substring(0, stnIdArr
				.length() - 1));
		return "listStntrn";
	}

	/**
	 * 根据列车报文名查找车辆信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchStncarResult() throws Exception {
		initOpraMap("EXE_AEI");
		listStncar = exeAeiService.listStncar(exeStntrn.getRptname());
		String car_noArr = "";
		for (ExeStncar car : listStncar) {
			car_noArr += car.getCar_no() + ",";
		}
		String rptnameArr = "";
		for (ExeStncar car : listStncar) {
			rptnameArr += car.getId().getRptname() + ",";
		}

		mapCorpName = exeDicService.getCorpNameByCar_noArr2(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapCarMedium = exeDicService.getMediumIdByCar_noArr(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapInOutFlag = exeDicService.getInOutFlagByRptNameArr(rptnameArr.equals("") ? rptnameArr : rptnameArr
				.substring(0, rptnameArr.length() - 1));
		return "listStncar2";
	}

	/**
	 * 到达人工补录列车信息界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddStntrn() throws Exception {
		initOpraMap("EXE_AEI");
		listDicEntprStn = exeDicService.listDicEntprStn(((SysUser) getSessionAttr("sysUserSess")).getCorpid());// 企业站区
		return "addStntrn";
	}

	/**
	 * 人工补录列车信息 报文名格式：DXXXLNNN.MDD。 例：DLAJ1001.603，首字母D表示该文件存储的是列车报文；
	 * 第2~4字母XXX表示该列车报文所在站的电报略号；第5个字母L表示该列车报文所在站的AEI设备号（格式为1~9，A~F)；第6~8字母NNN表示某天某AEI设备收到的该列车报文的序号，由3位数字组成，如003；MDD表示收到该列车报文的日期(以AEI过车的通过日期为准)，其中M表示月份，由数字1~9表示1~9月份，A、B、C分别表示10、11、12月份。
	 * 原报文名+YYYY+N，N为0-9表示重报次数，前台将N都置成0。 报文总辆数=可识别车辆数 可识别车=自备车+部属车+不可识别车
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addStntrn() throws Exception {
		if (exeStntrn.getStn_name().indexOf(":") > -1)
			exeStntrn.setStn_name(exeStntrn.getStn_name().substring(0, exeStntrn.getStn_name().indexOf(":")));// 拆分站名，把代码赋给
		exeStntrn.setStn_entpr(exeDicService.loadDicSinocorp(((SysUser) getSessionAttr("sysUserSess")).getCorpid()));// 设置报告站所属企业ID
		String rptname = "D" + exeStntrn.getStn_name() + dealAei_no(exeStntrn.getAei_no());// 拼写报文名
		rptname = rptname + exeAeiService.getMaxRptId(rptname) + ".";
		String date = StringUtil.getFormatDate(exeStntrn.getArr_time());
		date = dealMonth(date.substring(5, 7)) + date.substring(8, 10) + date.substring(0, 4) + "0";
		rptname = rptname + date;
		// 根据小时 分钟拼写到达日期
		String arr_time = StringUtil.getFormatDate(exeStntrn.getArr_time()) + " " + arr_time_hour + ":" + arr_time_min
				+ ":00";
		exeStntrn.setArr_time(StringUtil.StringToDate(arr_time, "yyyy-MM-dd HH:mm:ss"));
		ExeStntrn stntrn = exeAeiService.loadExeStntrn(rptname);
		if (stntrn == null) {
			exeStntrn.setRptname(rptname);// 设置报文名，主键
			exeStntrn.setMsg_flag("1");// 信息来源标识，人工补录
			exeStntrn.setMsg_status("0");// 信息状态标识，已录入
			exeStntrn.setTrain_dir(exeDicService.getTrain_dirByEntprstn(((SysUser) getSessionAttr("sysUserSess"))
					.getCorpid(), exeStntrn.getStn_name(), exeStntrn.getAei_no(), exeStntrn.getIn_out_flag()));

			// 报文总辆数、可识别车辆数、可识别车、自备车、部属车、不可识别车都设为0
			exeStntrn.setRpt_car_number(0);
			exeStntrn.setCar_number(0);
			exeStntrn.setT_number(0);
			exeStntrn.setQ_number(0);
			exeStntrn.setUncar_number(0);
			exeAeiService.saveStntrn(exeStntrn);
			msg = new Msg(Msg.SUCCESS, "人工补录列车信息成功！", "");
			return MSG;
		} else {
			msg = new Msg(Msg.FAILURE, "该列车信息已存在！", "");
			return MSG;
		}
	}

	/**
	 * 处理月份
	 * 
	 * @param month
	 * @return
	 */
	private String dealMonth(String month) {
		if (month.equals("01"))
			return "1";
		else if (month.equals("02"))
			return "2";
		else if (month.equals("03"))
			return "3";
		else if (month.equals("04"))
			return "4";
		else if (month.equals("05"))
			return "5";
		else if (month.equals("06"))
			return "6";
		else if (month.equals("07"))
			return "7";
		else if (month.equals("08"))
			return "8";
		else if (month.equals("09"))
			return "9";
		else if (month.equals("10"))
			return "A";
		else if (month.equals("11"))
			return "B";
		else if (month.equals("12"))
			return "C";
		else
			return "";
	}

	/**
	 * 处理Aei_no
	 * 
	 * @param aei_no
	 * @return
	 */
	private String dealAei_no(int aei_no) {
		String strAei_no = "";
		switch (aei_no) {
		case 1:
			strAei_no = "1";
			break;
		case 2:
			strAei_no = "2";
			break;
		case 3:
			strAei_no = "3";
			break;
		case 4:
			strAei_no = "4";
			break;
		case 5:
			strAei_no = "5";
			break;
		case 6:
			strAei_no = "6";
			break;
		case 7:
			strAei_no = "7";
			break;
		case 8:
			strAei_no = "8";
			break;
		case 9:
			strAei_no = "9";
			break;
		case 10:
			strAei_no = "A";
			break;
		case 11:
			strAei_no = "B";
			break;
		case 12:
			strAei_no = "C";
			break;
		case 13:
			strAei_no = "D";
			break;
		case 14:
			strAei_no = "E";
			break;
		case 15:
			strAei_no = "F";
			break;
		default:
			break;
		}
		return strAei_no;
	}

	/**
	 * 到达修改列车信息界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toUpdateStntrn() throws Exception {
		initOpraMap("EXE_AEI");
		listDicEntprStn = exeDicService.listDicEntprStn(((SysUser) getSessionAttr("sysUserSess")).getCorpid());// 企业站区
		exeStntrn = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		// 给页面的小时分钟赋值
		String arr_time = StringUtil.dateToString(exeStntrn.getArr_time(), "yyyy-MM-dd HH:mm:ss");
		arr_time_hour = arr_time.substring(11, 13);
		arr_time_min = arr_time.substring(14, 16);
		listAei_no = exeDicService.getAei_noByEntpr(((SysUser) getSessionAttr("sysUserSess")).getCorpid(), exeStntrn
				.getStn_name());
		return "updateStntrn";
	}

	/**
	 * 修改列车信息
	 * 
	 * @return
	 */
	public String updateStntrn() {
		ExeStntrn stntrnLoad = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		if (exeStntrn.getStn_name().indexOf(":") > -1)
			exeStntrn.setStn_name(exeStntrn.getStn_name().substring(0, exeStntrn.getStn_name().indexOf(":")));// 拆分站名，把代码赋给
		// 根据小时 分钟拼写到达日期
		String arr_time = StringUtil.getFormatDate(exeStntrn.getArr_time()) + " " + arr_time_hour + ":" + arr_time_min
				+ ":00";
		exeStntrn.setArr_time(StringUtil.StringToDate(arr_time, "yyyy-MM-dd HH:mm:ss"));
		exeStntrn.setStn_entpr(stntrnLoad.getStn_entpr());
		exeStntrn.setMsg_flag(stntrnLoad.getMsg_flag());
		exeStntrn.setMsg_status(stntrnLoad.getMsg_status());
		exeStntrn.setTrain_dir(exeDicService.getTrain_dirByEntprstn(((SysUser) getSessionAttr("sysUserSess"))
				.getCorpid(), exeStntrn.getStn_name(), exeStntrn.getAei_no(), exeStntrn.getIn_out_flag()));
		// 报文总辆数、可识别车辆数、可识别车、自备车、部属车、不可识别车都默认，不做修改
		exeStntrn.setRpt_car_number(stntrnLoad.getRpt_car_number());
		exeStntrn.setCar_number(stntrnLoad.getCar_number());
		exeStntrn.setT_number(stntrnLoad.getT_number());
		exeStntrn.setQ_number(stntrnLoad.getQ_number());
		exeStntrn.setUncar_number(stntrnLoad.getUncar_number());
		exeAeiService.updateStntrn(exeStntrn);
		msg = new Msg(Msg.SUCCESS, "修改列车信息成功！", "");
		return MSG;
	}

	/**
	 * 提交人工补录列车信息
	 * 
	 * @return
	 */
	public String commitStntrn() {
		exeStntrn = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		exeStntrn.setMsg_status("1");// 信息状态标识，已录入
		exeAeiService.updateStntrn(exeStntrn);
		msg = new Msg(Msg.SUCCESS, "提交列车信息成功！", "");
		return MSG;
	}

	/**
	 * 删除列车信息
	 * 
	 * @return
	 */
	public String deleteStntrn() {
		exeAeiService.deleteStntrn(exeAeiService.loadExeStntrn(exeStntrn.getRptname()));
		msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		return MSG;
	}

	/**
	 * 到增加车辆页面
	 * 
	 * @return
	 */
	public String toAddStncar() {
		initOpraMap("EXE_AEI");
		exeStncar = new ExeStncar();
		exeStntrn = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		// 给车辆赋值
		exeStncar.setTrain_nbr(exeStntrn.getTrain_nbr());
		exeStncar.setStn_name(exeStntrn.getStn_name());
		exeStncar.setArr_time(exeStntrn.getArr_time());
		exeStncar.setUncar_ident("1");// 不可识别车
		listDicCarkind = exeDicService.listDicCarkind();// 车种
		listDicCartype = exeDicService.listDicCartype();// 车型
		listDicCarmaker = exeDicService.listDicCarmaker();// 车辆制造企业
		return "addStncar";
	}

	/**
	 * 增加车辆
	 * 
	 * @return
	 */
	public String addStncar() {
		exeStncar.setMsg_flag("1");// 信息来源0-自动扫描；1-人工修改或补录
		int car_position = exeAeiService.getCar_position(exeStncar.getId().getRptname());
		exeStncar.setId(new ExeStncarId(exeStncar.getId().getRptname(), car_position));
		exeStncar.setCar_flag("");
		exeAeiService.saveStncar(exeStncar);
		msg = new Msg(Msg.SUCCESS, "增加车辆信息成功！", "");
		return MSG;
	}

	/**
	 * 到修改车辆界面
	 * 
	 * @return
	 */
	public String toUpdateStncar() {
		initOpraMap("EXE_AEI");
		listDicCarkind = exeDicService.listDicCarkind();// 车种
		listDicCartype = exeDicService.listDicCartype();// 车型
		listDicCarmaker = exeDicService.listDicCarmaker();// 车辆制造企业
		exeStncar = exeAeiService.loadExeStncar(exeStncar.getId().getRptname(), exeStncar.getId().getCar_position());
		return "updateStncar";
	}

	/**
	 * 修改车辆
	 * 
	 * @return
	 */
	public String updateStncar() {
		ExeStncar stncarLoad = exeAeiService.loadExeStncar(exeStncar.getId().getRptname(), exeStncar.getId()
				.getCar_position());
		exeStncar.setMsg_flag(stncarLoad.getMsg_flag());
		exeStncar.setTrain_dir(stncarLoad.getTrain_dir());
		exeStncar.setUncar_ident(stncarLoad.getUncar_ident());
		exeStncar.setCar_flag(stncarLoad.getCar_flag());
		exeStncar.setCar_flag(stncarLoad.getCar_flag());
		exeAeiService.updateStncar(exeStncar);
		msg = new Msg(Msg.SUCCESS, "修改车辆信息成功！", "");
		return MSG;
	}

	/**
	 * 删除车辆
	 * 
	 * @return
	 */
	public String deleteStncar() {
		exeStncar = exeAeiService.loadExeStncar(exeStncar.getId().getRptname(), exeStncar.getId().getCar_position());
		exeAeiService.deleteStncar(exeStncar);
		msg = new Msg(Msg.SUCCESS, "删除车辆信息成功！", "");
		return MSG;
	}

	/**
	 * 自备车利用率
	 * 
	 * @return
	 */
	public String zbclyl() {
		s_date = StringUtil.getOptSystemDate(new Date(), -32);
		e_date = StringUtil.getOptSystemDate(new Date(), -2);
		max_date = StringUtil.getOptSystemDate(new Date(), -2);
		listZbclyl = exeAeiService.getZbclyl(s_date, e_date);
		rowspan = exeAeiService.getListRowspan();
		return "zbclyl";
	}

	/**
	 * 自备车利用率查询结果
	 * 
	 * @return
	 */
	public String zbclylSearch() {
		max_date = StringUtil.getOptSystemDate(new Date(), -2);
		listZbclyl = exeAeiService.getZbclyl(s_date, e_date);
		rowspan = exeAeiService.getListRowspan();
		return "zbclyl";
	}

	/**
	 * 站区现车分布
	 */
	public String zqxcDist() {
		listZqxcDist = exeAeiService.getZqxcDist();
		return "zqxcDist";
	}

	/**
	 * 站区现车分布明细
	 * 
	 * @return
	 */
	public String zqxcDistDetail() {
		String zbc_flag = getRequest().getParameter("zbc_flag");
		String rpt_stn_code = getRequest().getParameter("rpt_stn_code");
		listDcarStat = exeAeiService.getZqxcDistDetail(rpt_stn_code, zbc_flag);
		getRequest().setAttribute("zbc_flag", zbc_flag);
		getRequest().setAttribute("rpt_stn_code", rpt_stn_code);
		return "zqxcDistDetail";
	}

	/**
	 * 导出现车分布信息
	 * 
	 * @return
	 */
	public String zqxcExport() {
		String zbc_flag = getRequest().getParameter("zbc_flag");
		String rpt_stn_code = getRequest().getParameter("rpt_stn_code");
		listDcarStat = exeAeiService.getZqxcDistDetail(rpt_stn_code, zbc_flag);
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		HttpServletResponse response = ServletActionContext.getResponse();
		exeAeiService.exportZqxcfbExcel(listDcarStat, sysUser.getCorpid(), response);
		return null;
	}

	/**
	 * 站区进出车辆货物信息匹配统计表，默认进入
	 * 
	 * @return
	 */
	public String zqjcclStat() {
		s_date = StringUtil.getOptSystemDate(new Date(), -30);
		e_date = StringUtil.getSystemDate();
		zbc_flag = "0";
		listZqjccl = exeAeiService.getZqjccl(s_date, e_date, zbc_flag);
		return "zqjcclStat";
	}

	/**
	 * 站区进出车辆货物信息匹配统计表，查询
	 * 
	 * @return
	 */
	public String zqjcclStatSearch() {
		listZqjccl = exeAeiService.getZqjccl(s_date, e_date, zbc_flag);
		return "zqjcclStat";
	}

	/**
	 * 站区进出车辆货物信息匹配明细
	 * 
	 * @return
	 */
	public String zqjcclStatDetail() {
		String in_out_flag = getRequest().getParameter("in_out_flag");
		String match = getRequest().getParameter("match");
		String stnid = getRequest().getParameter("stnid");
		listStncar = exeAeiService.zqjcclStatDetail(in_out_flag, match, stnid, zbc_flag, s_date, e_date);
		String car_noArr = "";
		for (ExeStncar car : listStncar) {
			car_noArr += car.getCar_no() + ",";
		}
		String rptnameArr = "";
		for (ExeStncar car : listStncar) {
			rptnameArr += car.getId().getRptname() + ",";
		}
		String stnIdArr = "";
		for (ExeStncar car : listStncar) {
			stnIdArr += car.getStn_name() + ",";
		}
		mapCorpName = exeDicService.getCorpNameByCar_noArr2(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapCarMedium = exeDicService.getMediumIdByCar_noArr(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		mapInOutFlag = exeDicService.getInOutFlagByRptNameArr(rptnameArr.equals("") ? rptnameArr : rptnameArr
				.substring(0, rptnameArr.length() - 1));
		mapStnName = exeDicService.getStnNameByStnIdArr(stnIdArr.equals("") ? stnIdArr : stnIdArr.substring(0, stnIdArr
				.length() - 1));

		return "zqjcclStatDetail";
	}

	/**
	 * 站区AEI设备监控
	 * 
	 * @return
	 */
	public String equipMonitor() {
		String date = StringUtil.getSystemDate();
		listEquipMonitor = exeAeiService.getEquipMonitor(date);
		rowspan = exeDicService.getListRowspan();
		return "equipMonitor";
	}

	/**
	 * 专用线停靠时间
	 * 
	 * @return
	 */
	public String zyxFrame() {
		return "zyxFrame";

	}

	/**
	 * 专用线停靠时间查询条件
	 * 
	 * @return
	 */
	public String zyxLeft() {
		listDicAreaCorp = exeDicService.listDicAreacorp("dataAutho");// 区域
		// listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");// 公司
		listDicSinocorp = exeDicService.listZqqy(2);// 公司
		return "zyxLeft";
	}

	/**
	 * 专用线停靠站默认查询界面
	 * 
	 * @return
	 */
	public String zyxMain() {
		zbc_flag = "1";
		// listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");
		listDicSinocorp = exeDicService.listZqqy(3);
		listDicOvertime = exeDicService.listDicOverTimeByZbc_flag("1");
		listOverdue = exeAeiService.listZyx(zbc_flag, area_id, stn_entpr_id);
		return "zyxMain";
	}

	/**
	 * 专用线停靠站查询
	 * 
	 * @return
	 */
	public String zyxSearch() {
		listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");
		listDicOvertime = exeDicService.listDicOverTimeByZbc_flag(zbc_flag);
		listOverdue = exeAeiService.listZyx(zbc_flag, area_id, stn_entpr_id);

		return "zyxMain";
	}

	/**
	 * 专用线停靠站 取得明细
	 * 
	 * @return
	 */
	public String zyxGetDetail() {
		String over_id = getRequest().getParameter("over_id");
		String entpr_id = getRequest().getParameter("entpr_id");
		listDcarStat = exeAeiService.getZyxDetail(entpr_id, over_id);
		return "zyxDetail";
	}

	public List<ExeStntrn> getListStntrn() {
		return listStntrn;
	}

	public void setListStntrn(List<ExeStntrn> listStntrn) {
		this.listStntrn = listStntrn;
	}

	public List<ExeStncar> getListStncar() {
		return listStncar;
	}

	public void setListStncar(List<ExeStncar> listStncar) {
		this.listStncar = listStncar;
	}

	public ExeStntrn getExeStntrn() {
		return exeStntrn;
	}

	public void setExeStntrn(ExeStntrn exeStntrn) {
		this.exeStntrn = exeStntrn;
	}

	public ExeStncar getExeStncar() {
		return exeStncar;
	}

	public void setExeStncar(ExeStncar exeStncar) {
		this.exeStncar = exeStncar;
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

	public List<DicSinocorp> getListDicSinocorp() {
		return listDicSinocorp;
	}

	public void setListDicSinocorp(List<DicSinocorp> listDicSinocorp) {
		this.listDicSinocorp = listDicSinocorp;
	}

	public List<DicCarkind> getListDicCarkind() {
		return listDicCarkind;
	}

	public void setListDicCarkind(List<DicCarkind> listDicCarkind) {
		this.listDicCarkind = listDicCarkind;
	}

	public List<DicCartype> getListDicCartype() {
		return listDicCartype;
	}

	public void setListDicCartype(List<DicCartype> listDicCartype) {
		this.listDicCartype = listDicCartype;
	}

	public List<DicCarmaker> getListDicCarmaker() {
		return listDicCarmaker;
	}

	public void setListDicCarmaker(List<DicCarmaker> listDicCarmaker) {
		this.listDicCarmaker = listDicCarmaker;
	}

	public List<DicEntprStn> getListDicEntprStn() {
		return listDicEntprStn;
	}

	public void setListDicEntprStn(List<DicEntprStn> listDicEntprStn) {
		this.listDicEntprStn = listDicEntprStn;
	}

	public static void main(String args[]) {
		String date = StringUtil.getFormatDate(new Date());
		date = date.substring(5, 7) + date.substring(7, 10) + date.substring(0, 4) + "0";
		System.out.println(date);
	}

	public List<Integer> getListAei_no() {
		return listAei_no;
	}

	public void setListAei_no(List<Integer> listAei_no) {
		this.listAei_no = listAei_no;
	}

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getCar_medium_id() {
		return car_medium_id;
	}

	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}

	public List<DicAreacorp> getListDicAreaCorp() {
		return listDicAreaCorp;
	}

	public void setListDicAreaCorp(List<DicAreacorp> listDicAreaCorp) {
		this.listDicAreaCorp = listDicAreaCorp;
	}

	public HashMap<String, String> getMapStnName() {
		return mapStnName;
	}

	public void setMapStnName(HashMap<String, String> mapStnName) {
		this.mapStnName = mapStnName;
	}

	public String getStn_entpr_id() {
		return stn_entpr_id;
	}

	public void setStn_entpr_id(String stn_entpr_id) {
		this.stn_entpr_id = stn_entpr_id;
	}

	public List<ExeZqxcDistVO> getListZqxcDist() {
		return listZqxcDist;
	}

	public void setListZqxcDist(List<ExeZqxcDistVO> listZqxcDist) {
		this.listZqxcDist = listZqxcDist;
	}

	public List<ExeDcarStat> getListDcarStat() {
		return listDcarStat;
	}

	public void setListDcarStat(List<ExeDcarStat> listDcarStat) {
		this.listDcarStat = listDcarStat;
	}

	public List<ExeZqjcclVO> getListZqjccl() {
		return listZqjccl;
	}

	public void setListZqjccl(List<ExeZqjcclVO> listZqjccl) {
		this.listZqjccl = listZqjccl;
	}

	public List<ExeEquipMonitorVO> getListEquipMonitor() {
		return listEquipMonitor;
	}

	public void setListEquipMonitor(List<ExeEquipMonitorVO> listEquipMonitor) {
		this.listEquipMonitor = listEquipMonitor;
	}

	public int[] getRowspan() {
		return rowspan;
	}

	public void setRowspan(int[] rowspan) {
		this.rowspan = rowspan;
	}

	public List<ExeZbclylVO> getListZbclyl() {
		return listZbclyl;
	}

	public void setListZbclyl(List<ExeZbclylVO> listZbclyl) {
		this.listZbclyl = listZbclyl;
	}

	public List<DicOverTime> getListDicOvertime() {
		return listDicOvertime;
	}

	public void setListDicOvertime(List<DicOverTime> listDicOvertime) {
		this.listDicOvertime = listDicOvertime;
	}

	public String getMax_date() {
		return max_date;
	}

	public void setMax_date(String max_date) {
		this.max_date = max_date;
	}
}
