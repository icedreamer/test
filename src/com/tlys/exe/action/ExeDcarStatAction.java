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
import com.tlys.exe.dao.ExeDcarStatSearchField;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeEstArrival;
import com.tlys.exe.service.ExeDcarStatService;
import com.tlys.exe.service.ExeDicService;
import com.tlys.sys.model.SysUser;

/**
 * 车辆动态查询Action
 * 
 * @author 孔垂云
 * 
 */

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "search", location = "/exe/exe-dcarStat-search.jsp"),
		@Result(name = "frame", location = "/exe/exe-dcarStat-frame.jsp"),
		@Result(name = "left", location = "/exe/exe-dcarStat-left.jsp"),
		@Result(name = "main", location = "/exe/exe-dcarStat-main.jsp"),
		@Result(name = "detail", location = "/exe/exe-dcarStat-detail.jsp") })
public class ExeDcarStatAction extends _BaseAction {

	@Autowired
	protected ExeDcarStatService dcarStatService;

	@Autowired
	protected ExeDicService dicService;

	public DicDBUtil dicDBUtil;

	private List<ExeDcarStat> listDcarStat;// 定义查询结果列表
	private List<DicGoods> listDicGoods;// 定义品名列表
	private List<DicGoodscategory> listDicGoodsCategory;// 定义充装介质字典
	private List<DicCartype> listDicCartype;// /定义车型字典
	private List<DicSinocorp> listDicSinocorp;// 定义车辆使用企业
	private ExeDcarStat exeDcarStat;// 定义ExeDcarStat，双击弹出详细信息
	private Integer estarrCount;// 定义预计到达车辆数
	private List<ExeEstArrival> listEstArrival;// 预计到达信息列表

	/**
	 * 数据权限控制
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_DCAR");
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

	public ExeDcarStat getExeDcarStat() {
		return exeDcarStat;
	}

	public void setExeDcarStat(ExeDcarStat exeDcarStat) {
		this.exeDcarStat = exeDcarStat;
	}

	public void setListDcarStat(List<ExeDcarStat> listDcarStat) {
		this.listDcarStat = listDcarStat;
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

	public List<ExeDcarStat> getListDcarStat() {
		return listDcarStat;
	}

	public List<DicGoods> getListDicGoods() {
		return listDicGoods;
	}

	public void setListDicGoods(List<DicGoods> listDicGoods) {
		this.listDicGoods = listDicGoods;
	}

	public void setListDCarStat(List<ExeDcarStat> listDcarStat) {
		this.listDcarStat = listDcarStat;
	}

	// 定义查询条件
	private String zbc_flag;// 查询类型
	private String con_name;// 收货人
	private String shipper_name;// 发货人
	private String cdy_org_stn;// 始发站
	private String dest_stn;// 终到站
	private String car_rent_flag;// 车属
	private String goods_type;// 产品类别
	private String car_no;// 车号
	private String car_user_id;// 车辆使用企业
	private String car_type;// 车型
	private String le_code;// 重空
	private String cdy_code;// 货物品名
	private String car_medium_id;// 充装介质
	private String params;// 定义参数，用于导出时根据用户查询的参数来导出

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCon_name() {
		return con_name;
	}

	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	public String getShipper_name() {
		return shipper_name;
	}

	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
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

	public String getCar_rent_flag() {
		return car_rent_flag;
	}

	public void setCar_rent_flag(String car_rent_flag) {
		this.car_rent_flag = car_rent_flag;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getLe_code() {
		return le_code;
	}

	public void setLe_code(String le_code) {
		this.le_code = le_code;
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

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
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
		listDicGoodsCategory = dicService.listDicGoodscategory();// 充装介质字典
		listDicCartype = dicService.listDicCartype();// 车型字典
		// listDicSinocorp = dicService.listDicSinocorp("dataAutho");//
		// 车辆使用企业，根据用户的数据访问权限来获取
		listDicSinocorp = dicService.listDicSinocorp();// 车辆使用企业，根据用户的数据访问权限来获取
		return "left";
	}

	/**
	 * 框架main内容
	 * 
	 * @return
	 */
	public String main() {
		initOpraMap("EXE_DCAR");
		estarrCount = dcarStatService.getEstarr_flagCount();
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		ExeDcarStatSearchField field = null;
		field = new ExeDcarStatSearchField();
		field.setZbc_flag("1");
		field.setCorpid(sysUser.getCorpid());// 设置用户企业id
		params = "?zbc_flag=" + "1" + "&con_name=" + "" + "&shipper_name=" + "" + "&cdy_org_stn=" + "" + "&dest_stn="
				+ "" + "&car_rent_flag=" + "" + "&goods_type=" + "" + "&car_no=" + "" + "&car_user_id=" + ""
				+ "&car_type=" + "" + "&cdy_code=" + "" + "&le_code=" + "" + "&car_medium_id=" + "";
		String pageUrl = "/exe/exe-dcar-stat!searchResult.action" + params;
		int totalRecord = dcarStatService.getListDcarStatCount(field);
		pageView = dcarStatService.listDcarStat(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : 10));
		listDcarStat = pageView.getRecords();
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
	public String searchResult() throws Exception {
		initOpraMap("EXE_DCAR");
		estarrCount = dcarStatService.getEstarr_flagCount();// 预计到达车辆数
		// listDicGoods = dicService.listDicGoods();// 品名字典
		// listDicGoodsCategory = dicService.listDicGoodscategory();// 充装介质字典
		// listDicCartype = dicService.listDicCartype();// 车型字典
		// 车号用,隔开或用-为车号段，要么为分隔的车号，要么为车号段，比如9999991,99999992，或者9999991-99999995
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		ExeDcarStatSearchField field = null;
		field = new ExeDcarStatSearchField();
		field.setZbc_flag(zbc_flag);
		field.setCon_name(StringUtil.decodeUrl(con_name));
		field.setShipper_name(StringUtil.decodeUrl(shipper_name));
		field.setCdy_org_stn(StringUtil.decodeUrl(cdy_org_stn));
		field.setDest_stn(StringUtil.decodeUrl(dest_stn));
		field.setCar_rent_flag(car_rent_flag);
		field.setGoods_type(goods_type);
		field.setCar_no(car_no);
		field.setCar_user_id(car_user_id);
		field.setCar_type(car_type);
		field.setCdy_code(cdy_code);
		field.setLe_code(le_code);
		field.setCar_medium_id(car_medium_id);
		field.setCorpid(sysUser.getCorpid());// 设置用户企业id
		params = "?zbc_flag=" + zbc_flag + "&con_name=" + StringUtil.encodeUrl(con_name) + "&shipper_name="
				+ StringUtil.encodeUrl(shipper_name) + "&cdy_org_stn=" + StringUtil.encodeUrl(cdy_org_stn)
				+ "&dest_stn=" + StringUtil.encodeUrl(dest_stn) + "&car_rent_flag=" + car_rent_flag + "&goods_type="
				+ goods_type + "&car_no=" + car_no + "&car_user_id=" + car_user_id + "&car_type=" + car_type
				+ "&cdy_code=" + cdy_code + "&le_code=" + le_code + "&car_medium_id=" + car_medium_id;
		String pageUrl = "/exe/exe-dcar-stat!searchResult.action" + params;
		int totalRecord = dcarStatService.getListDcarStatCount(field);
		pageView = dcarStatService.listDcarStat(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : GlobalConst.pageSize));
		listDcarStat = pageView.getRecords();
		// int count = dcarStatService.getListDcarStat(field);
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
		if (zbc_flag == null || zbc_flag.equals("")) {
			listDcarStat = dcarStatService.exportEstarr();
		} else {
			ExeDcarStatSearchField field = null;
			field = new ExeDcarStatSearchField();
			field.setZbc_flag(zbc_flag);
			field.setCon_name(StringUtil.decodeUrl(con_name));
			field.setShipper_name(StringUtil.decodeUrl(shipper_name));
			field.setCdy_org_stn(StringUtil.decodeUrl(cdy_org_stn));
			field.setDest_stn(StringUtil.decodeUrl(dest_stn));
			field.setCar_rent_flag(car_rent_flag);
			field.setGoods_type(goods_type);
			field.setCar_no(car_no);
			field.setCar_user_id(car_user_id);
			field.setCar_type(car_type);
			field.setCdy_code(cdy_code);
			field.setLe_code(le_code);
			field.setCar_medium_id(car_medium_id);
			field.setCorpid(sysUser.getCorpid());// 设置用户企业id
			listDcarStat = dcarStatService.exportDcarStat(field);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		dcarStatService.exportExcel(listDcarStat, sysUser.getCorpid(), response);
		return null;

	}

	/**
	 * 取得车辆动态详细信息
	 * 
	 * @return
	 */
	public String getDetail() {
		exeDcarStat = dcarStatService.loadExeDcarStat(car_no);
		if (exeDcarStat.getEstarr_flag().equals("1"))
			listEstArrival = dcarStatService.listEstArrival(exeDcarStat.getCar_no(), exeDcarStat.getEvt_time());// 预期到达时间
		return "detail";
	}

	/**
	 * 取得预计到达信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getEstarr_flagRec() throws Exception {
		estarrCount = dcarStatService.getEstarr_flagCount();
		String pageUrl = "/exe/exe-dcar-stat!getEstarr_flagRec.action";
		pageView = dcarStatService.getEstarr_flagRec(pageUrl, estarrCount, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : GlobalConst.pageSize));
		listDcarStat = pageView.getRecords();
		return "main";
	}

	public ExeDcarStatService getDcarStatService() {
		return dcarStatService;
	}

	public void setDcarStatService(ExeDcarStatService dcarStatService) {
		this.dcarStatService = dcarStatService;
	}

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	public DicDBUtil getDicDBUtil() {
		return dicDBUtil;
	}

	public void setDicDBUtil(DicDBUtil dicDBUtil) {
		this.dicDBUtil = dicDBUtil;
	}

}
