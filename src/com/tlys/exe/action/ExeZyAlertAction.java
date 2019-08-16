package com.tlys.exe.action;

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

import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.common.util.DicDBUtil;
import com.tlys.exe.common.util.GlobalConst;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeZyAlertSearchField;
import com.tlys.exe.model.ExeEstArrival;
import com.tlys.exe.model.ExeZyAlert;
import com.tlys.exe.service.ExeDicService;
import com.tlys.exe.service.ExeZyAlertService;
import com.tlys.sys.model.SysUser;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "search", location = "/exe/exe-zyAlert-search.jsp"),
		@Result(name = "frame", location = "/exe/exe-zyAlert-frame.jsp"),
		@Result(name = "left", location = "/exe/exe-zyAlert-left.jsp"),
		@Result(name = "main", location = "/exe/exe-zyAlert-main.jsp"),
		@Result(name = "detail", location = "/exe/exe-zyAlert-detail.jsp") })
public class ExeZyAlertAction extends _BaseAction {

	@Autowired
	protected ExeZyAlertService zyAlertService;

	@Autowired
	protected ExeDicService dicService;

	public DicDBUtil dicDBUtil;

	private List<ExeZyAlert> listZyAlert;// 定义查询结果列表
	private List<DicGoods> listDicGoods;// 定义品名列表
	private List<DicGoodscategory> listDicGoodsCategory;// 定义充装介质字典
	private List<DicCartype> listDicCartype;// /定义车型字典
	private List<DicSinocorp> listDicSinocorp;// 定义车辆使用企业
	private ExeZyAlert exeZyAlert;// 定义ExeDcarStat，双击弹出详细信息
	private Integer estarrCount;// 定义预计到达车辆数
	private List<ExeEstArrival> listEstArrival;// 预计到达信息列表

	/*
	 * 
	 * 数据权限控制
	 */

	public String getOper_flag() {
		return oper_flag;
	}

	public void setOper_flag(String oper_flag) {
		this.oper_flag = oper_flag;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_ZYALERT");
	}

	public List<ExeEstArrival> getListEstArrival() {
		return listEstArrival;
	}

	public void setListEstArrival(List<ExeEstArrival> listEstArrival) {
		this.listEstArrival = listEstArrival;
	}

	public Integer getEstarrCount() {
		return estarrCount;
	}

	public void setEstarrCount(Integer estarrCount) {
		this.estarrCount = estarrCount;
	}

	public String getCar_user_id() {
		return car_user_id;
	}

	public void setCar_user_id(String car_user_id) {
		this.car_user_id = car_user_id;
	}

	public List<DicSinocorp> getListDicSinocorp() {
		return listDicSinocorp;
	}

	public void setListDicSinocorp(List<DicSinocorp> listDicSinocorp) {
		this.listDicSinocorp = listDicSinocorp;
	}

	public List<DicCartype> getListDicCartype() {
		return listDicCartype;
	}

	public void setListDicCartype(List<DicCartype> listDicCartype) {
		this.listDicCartype = listDicCartype;
	}

	public List<DicGoodscategory> getListDicGoodsCategory() {
		return listDicGoodsCategory;
	}

	public void setListDicGoodsCategory(List<DicGoodscategory> listDicGoodsCategory) {
		this.listDicGoodsCategory = listDicGoodsCategory;
	}

	public List<DicGoods> getListDicGoods() {
		return listDicGoods;
	}

	public void setListDicGoods(List<DicGoods> listDicGoods) {
		this.listDicGoods = listDicGoods;
	}

	// 定义查询条件
	private String oper_flag;// 处理类型（未处理(默认)；已解决；不处理)
	private String car_no;// 车号
	private String cdy_org_stn;// 始发站
	private String dest_stn;// 终到站
	private String car_user_id;// 车辆使用企业
	private String cdy_code;// 货物品名
	private String car_medium_id;// 充装介质

	private String params;// 定义参数，用于导出时根据用户查询的参数来导出
	private String s_date;
	private String e_date;
	private String pbl_reason;

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

	public String getPbl_reason() {
		return pbl_reason;
	}

	public void setPbl_reason(String pbl_reason) {
		this.pbl_reason = pbl_reason;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCdy_org_stn() {
		return cdy_org_stn;
	}

	public void setCdy_org_stn(String cdy_org_stn) {
		this.cdy_org_stn = cdy_org_stn;
	}

	public String getDest_stn() {
		return dest_stn;
	}

	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	public String getCdy_code() {
		return cdy_code;
	}

	public void setCdy_code(String cdy_code) {
		this.cdy_code = cdy_code;
	}

	public String getCar_medium_id() {
		return car_medium_id;
	}

	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
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
		// listDicGoods = dicService.listDicGoods();// 品名字典
		e_date = StringUtil.getSystemDate();
		s_date = StringUtil.getOptSystemDate(-30);
		listDicGoodsCategory = dicService.listDicGoodscategory();// 充装介质字典
		return "left";
	}

	/**
	 * 框架main内容
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String main() {
		initOpraMap("EXE_DCAR");
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		e_date = StringUtil.getSystemDate();
		s_date = StringUtil.getOptSystemDate(-30);
		ExeZyAlertSearchField field = null;
		field = new ExeZyAlertSearchField();
		field.setCorpid(sysUser.getCorpid());// 设置用户企业id
		field.setS_date(s_date);
		field.setE_date(e_date);
		params = "?oper_flag=&cdy_org_stn=&dest_stn=&car_no=&car_user_id=&cdy_code=&car_medium_id=&pbl_reason=&s_date="
				+ s_date + "&e_date=" + e_date;
		String pageUrl = "/exe/exe-zy-alert!main.action" + params;
		int totalRecord = zyAlertService.getListZyAlertCount(field);
		pageView = zyAlertService.listZyAlert(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : 10));
		listZyAlert = pageView.getRecords();
		return "main";
	}

	/**
	 * 进入查询界面
	 * 
	 * @return
	 */
	public String search() {
		initOpraMap("EXE_DCAR");
		listDicGoods = dicService.listDicGoods();// 品名字典
		listDicGoodsCategory = dicService.listDicGoodscategory();// 充装介质字典
		listDicCartype = dicService.listDicCartype();// 车型字典
		return "search";
	}

	/**
	 * 显示查询结果
	 * 
	 * @return
	 */
	// 车属对应动态里的CAR_RENT_FLAG字段，0-自用、1-出租、2-部属车。
	// 当值为1时，需要根据当前登录用户所在企业，对照产权企业ID(CAR_OWNER_ID)和使用企业ID(CAR_USER_ID)，来动态显示是租赁还是租用。
	// 在显示记录的时候，如果登录用户是产权企业，需要再判断产权企业查询标识(CAR_OWNER_FLAG)是否为(1-可查，0-不可查)；
	// 如果登录用户是使用企业，需判断使用企业查询标识(CAR_USER_FLAG)是否为(1-可查，0-不可查)。
	// 如果既不是产权企业也不是使用企业，则不能查看当前记录。
	// 查询字段中增加corpid字段，在dao里面先判断car_rent_flag是否为1，如果为1再判断corpid是否为00000000，如果不是这个，则是企业用户，然后是一个或查询：corpid=car_user_flag
	// and CAR_USER_FLAG='1' || corpid=CAR_OWNER_ID and car_owner_flag='1'
	@SuppressWarnings("unchecked")
	public String searchResult() throws Exception {
		initOpraMap("EXE_ZYALERT");
		// listDicGoods = dicService.listDicGoods();// 品名字典
		// listDicGoodsCategory = dicService.listDicGoodscategory();// 充装介质字典
		// listDicCartype = dicService.listDicCartype();// 车型字典
		// 车号用,隔开或用-为车号段，要么为分隔的车号，要么为车号段，比如9999991,99999992，或者9999991-99999995
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		ExeZyAlertSearchField field = null;
		field = new ExeZyAlertSearchField();
		field.setCdy_org_stn(StringUtil.decodeUrl(cdy_org_stn));
		field.setDest_stn(StringUtil.decodeUrl(dest_stn));
		field.setCar_no(car_no);
		field.setCar_user_id(car_user_id);
		field.setCdy_code(cdy_code);
		field.setCar_medium_id(car_medium_id);
		field.setOper_flag(oper_flag);
		field.setCorpid(sysUser.getCorpid());// 设置用户企业id
		field.setPbl_reason(pbl_reason);
		field.setS_date(s_date);
		field.setE_date(e_date);
		params = "?oper_flag=" + oper_flag + "&cdy_org_stn=" + StringUtil.encodeUrl(cdy_org_stn) + "&dest_stn="
				+ StringUtil.encodeUrl(dest_stn) + "&car_no=" + car_no + "&car_user_id=" + car_user_id + "&cdy_code="
				+ cdy_code + "&car_medium_id=" + car_medium_id + "&pbl_reason=" + pbl_reason + "&s_date=" + s_date
				+ "&e_date=" + e_date;
		String pageUrl = "/exe/exe-zy-alert!searchResult.action" + params;
		int totalRecord = zyAlertService.getListZyAlertCount(field);
		pageView = zyAlertService.listZyAlert(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : GlobalConst.pageSize));
		listZyAlert = pageView.getRecords();
		return "main";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportExcel() throws Exception {
		// 为查询条件赋值
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		ExeZyAlertSearchField field = null;
		field = new ExeZyAlertSearchField();
		field.setCdy_org_stn(StringUtil.decodeUrl(cdy_org_stn));
		field.setDest_stn(StringUtil.decodeUrl(dest_stn));
		field.setCar_no(car_no);
		field.setCar_user_id(car_user_id);
		field.setCdy_code(cdy_code);
		field.setCar_medium_id(car_medium_id);
		field.setOper_flag(oper_flag);
		field.setCorpid(sysUser.getCorpid());// 设置用户企业id
		field.setPbl_reason(pbl_reason);
		field.setS_date(s_date);
		field.setE_date(e_date);
		listZyAlert = zyAlertService.exportZyAlert(field);
		HttpServletResponse response = ServletActionContext.getResponse();
		zyAlertService.exportExcel(listZyAlert, sysUser.getCorpid(), response);
		return null;

	}

	/**
	 * 取得车辆动态详细信息
	 * 
	 * @return
	 */
	public String getDetail() {
		String evt_timeStr = getRequest().getParameter("evt_time");
		exeZyAlert = zyAlertService.loadExeZyAlert(car_no, StringUtil.StringToDate(evt_timeStr, "yyyy-MM-dd HH:mm:ss"));
		if (exeZyAlert.getEstarr_flag().equals("1"))
			listEstArrival = zyAlertService.listEstArrival(exeZyAlert.getId().getCar_no(), exeZyAlert.getId()
					.getEvt_time());// 预期到达时间
		return "detail";
	}

	/**
	 * 修改状态
	 * 
	 * @return
	 */
	public String updateOper_flag() {
		String evt_timeStr = getRequest().getParameter("evt_time");
		exeZyAlert = zyAlertService.loadExeZyAlert(car_no, StringUtil.StringToDate(evt_timeStr, "yyyy-MM-dd HH:mm:ss"));
		exeZyAlert.setOper_flag(getRequest().getParameter("oper_flag"));
		zyAlertService.updateZyAlert(exeZyAlert);
		StringUtil.out(ServletActionContext.getResponse(), "{\"flag\":true}");
		return null;
	}

	public DicDBUtil getDicDBUtil() {
		return dicDBUtil;
	}

	public void setDicDBUtil(DicDBUtil dicDBUtil) {
		this.dicDBUtil = dicDBUtil;
	}

	public List<ExeZyAlert> getListZyAlert() {
		return listZyAlert;
	}

	public void setListZyAlert(List<ExeZyAlert> listZyAlert) {
		this.listZyAlert = listZyAlert;
	}

	public ExeZyAlert getExeZyAlert() {
		return exeZyAlert;
	}

	public void setExeZyAlert(ExeZyAlert exeZyAlert) {
		this.exeZyAlert = exeZyAlert;
	}

}
