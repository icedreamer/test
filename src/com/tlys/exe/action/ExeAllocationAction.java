package com.tlys.exe.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTankrepcorp;
import com.tlys.equ.model.EquCar;
import com.tlys.exe.common.util.GlobalConst;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeAllocSearchField;
import com.tlys.exe.dao.ExeAllocationStatVO;
import com.tlys.exe.model.ExeAllocation;
import com.tlys.exe.model.ExeAllocationId;
import com.tlys.exe.service.ExeAllocationService;
import com.tlys.exe.service.ExeDicService;
import com.tlys.sys.model.SysUser;

/**
 * 调配信息Action
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Action(results = { @Result(name = "frame", location = "/exe/exe-allocation-frame.jsp"),
		@Result(name = "left", location = "/exe/exe-allocation-left.jsp"),
		@Result(name = "main", location = "/exe/exe-allocation-main.jsp"),
		@Result(name = "stat", location = "/exe/exe-allocation-stat.jsp") })
public class ExeAllocationAction extends _BaseAction {

	@Autowired
	ExeAllocationService exeAllocationService;
	@Autowired
	ExeDicService dicService;

	List<ExeAllocation> listExeAllocations;
	private List<ExeAllocationStatVO> listStat;// 调配信息统计列表
	private List<DicSinocorp> listSinoCorp;
	private List<DicRwdepartment> listRwdepartment;
	private List<DicTankrepcorp> listDicTankrepcord;// 罐体检修单位
	private ExeAllocation exeAllocation;
	private ExeAllocationId exeAllocationId;
	private DicSinocorp dicSinocorp;
	private List<DicGoodscategory> listDicGoodsCategory;// 充装介质

	private List<String> listContract_no;// 续签时合同号列表
	private String carLessor;// 租赁方（多选)
	private String carLessee;// 租用方（多选)
	private String car_medium_id;// 充装介质
	private String rentStartDate;// 开始日期
	private String rentEndDate;// 结束日期
	private String carNo;// 车号
	private String contractNo;// 合同号
	private String key_car_no;
	private String key_contract_no;
	private String submit_status;// 提交状态
	private int show;// 是否显示续签按钮

	/**
	 * 数据权限控制
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_ALLO");
	}

	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}

	public List<DicTankrepcorp> getListDicTankrepcord() {
		return listDicTankrepcord;
	}

	public void setListDicTankrepcord(List<DicTankrepcorp> listDicTankrepcord) {
		this.listDicTankrepcord = listDicTankrepcord;
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
		listSinoCorp = dicService.listDicSinocorp();
		listRwdepartment = dicService.listDicRwdepartment();
		rentEndDate = StringUtil.getOptSystemDate(365 * 3);
		rentStartDate = StringUtil.getOptSystemDate(-365 * 3);
		listDicGoodsCategory = dicService.listDicGoodscategory();// 充装介质字典
		return "left";
	}

	/**
	 * 框架main内容
	 * 
	 * @return
	 */
	public String main() {
		initOpraMap("EXE_ALLO");
		listStat = exeAllocationService.statisticsAllocation();
		return "stat";
	}

	/**
	 * 查询调配信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String searchResult() {
		initOpraMap("EXE_ALLO");
		ExeAllocSearchField esf = new ExeAllocSearchField();
		esf.setCarNo(carNo);
		esf.setContractNo(contractNo);
		esf.setCarLessee(carLessee);
		esf.setCarLessor(carLessor);
		esf.setRentEndDate(rentEndDate);
		esf.setRentStartDate(rentStartDate);
		esf.setCar_medium_id(car_medium_id);
		String pageUrl = "/exe/exe-allocation!searchResult.action?carNo=" + carNo + "&contractNo=" + contractNo
				+ "&carLessee=" + carLessee + "&carLessor=" + carLessor + "&rentEndDate=" + rentEndDate
				+ "&rentStartDate=" + rentStartDate + "&car_medium_id=" + car_medium_id;
		int totalRecord = exeAllocationService.getExeAllocationCount(esf);
		pageView = exeAllocationService.findExeAllocations(esf, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : GlobalConst.pageSize));
		listExeAllocations = pageView.getRecords();

		listContract_no = exeAllocationService.getAllContract_no(getCurUser().getCorpid());// 取得该用户的合同号列表
		if (listContract_no.size() == 0)
			show = 0;// 是否显示续签按钮
		else
			show = 1;
		return "main";
	}

	public String edit() {
		initOpraMap("EXE_ALLO");
		listSinoCorp = dicService.listDicSinocorp();
		listRwdepartment = dicService.listDicRwdepartment();
		return "edit";
	}

	/**
	 * 到录入调配信息界面
	 * 
	 * @return
	 */
	public String toAdd() {
		initOpraMap("EXE_ALLO");
		listSinoCorp = dicService.listDicSinocorp();
		listRwdepartment = dicService.listDicRwdepartment();
		listDicTankrepcord = dicService.listDicTankrepcord();
		// 获得当前用户所在公司和公司corpid
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		// 定义当前用户
		String corpid = sysUser.getCorpid();
		String corpshortname = "";
		DicSinocorp dicSinocorp = dicService.loadDicSinocorp(corpid);// 根据公司id查公司，如果公司为空，给公司名赋为空，如果不为空，去shortname
		if (dicSinocorp == null)
			corpshortname = "";
		else
			corpshortname = dicSinocorp.getShortname();
		getRequest().setAttribute("corpid", corpid);
		getRequest().setAttribute("corpshortname", corpshortname);
		return "add";
	}

	public String add() {
		if (null == exeAllocation) {
			msg = new Msg(Msg.FAILURE, "未接到车辆调配信息！");
		} else {
			// exeAllocation.setId(exeAllocationId);
			boolean isNew = true;
			EquCar equCar = dicService.getEquCarByCarno(exeAllocation.getId().getCar_no());
			if (equCar == null) {
				msg = new Msg(Msg.FAILURE, "车号不存在！", "");
			} else {
				List<ExeAllocation> list = exeAllocationService.getAllocationByContract_no(exeAllocation.getId()
						.getContract_no(), exeAllocation.getId().getCar_no());
				if (list.size() > 0) {
					msg = new Msg(Msg.FAILURE, "车号和合同号输入错误！", "");
				} else {
					SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
					DicGoodscategory goodscategory = new DicGoodscategory();
					goodscategory.setGoodsid(equCar.getGoodsid());
					exeAllocation.setGoodscategory(goodscategory);// 从自备车台账表里取得充装介质
					exeAllocation.setCreate_user(sysUser.getUserid());// 设置创建用户名，从session中取得
					exeAllocation.setCreate_date(new Date());
					exeAllocation.setSubmit_status("0");// 初始状态为0－录入。
					exeAllocationService.save(exeAllocation, isNew);
					msg = new Msg(Msg.SUCCESS, "新增车辆调配信息成功！", "");
				}
			}
		}
		return MSG;

	}

	/**
	 * 进入修改调配信息界面
	 * 
	 * @return
	 */
	public String toUpdate() {
		initOpraMap("EXE_ALLO");
		ExeAllocationId eai = new ExeAllocationId(key_car_no, key_contract_no);
		exeAllocation = exeAllocationService.findExeAllocation(eai);
		listSinoCorp = dicService.listDicSinocorp();
		listRwdepartment = dicService.listDicRwdepartment();
		listDicTankrepcord = dicService.listDicTankrepcord();
		return "update";
	}

	/**
	 * 修改调配信息 合同号和车号不修改
	 * 
	 * @return
	 */
	public String update() throws Exception {
		ExeAllocationId eai = new ExeAllocationId(key_car_no, key_contract_no);
		ExeAllocation ea = exeAllocationService.findExeAllocation(eai);
		ea.setCar_lessee(exeAllocation.getCar_lessee());
		ea.setTank_maint_dept(exeAllocation.getTank_maint_dept());
		ea.setCar_maint_dept(exeAllocation.getCar_maint_dept());
		ea.setRent_start_date(exeAllocation.getRent_start_date());
		ea.setRent_end_date(exeAllocation.getRent_end_date());
		SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
		ea.setUpdate_user(sysUser.getUserid());
		ea.setUpdate_date(new Date());
		exeAllocationService.saveExeAllocation(ea);
		msg = new Msg(Msg.SUCCESS, "修改调配信息成功！", "");
		return MSG;
	}

	/**
	 * 进入续签页面
	 * 
	 * @return
	 */
	public String toCarryon() {
		initOpraMap("EXE_ALLO");
		listSinoCorp = dicService.listDicSinocorp();
		listRwdepartment = dicService.listDicRwdepartment();
		listDicTankrepcord = dicService.listDicTankrepcord();
		listContract_no = exeAllocationService.getAllContract_no(getCurUser().getCorpid());// 取得该用户的合同号列表
		// ExeAllocationId eai = new ExeAllocationId(key_car_no,
		// key_contract_no);
		// exeAllocation = exeAllocationService.findExeAllocation(eai);
		// exeAllocationId = eai;
		getRequest().setAttribute("corpName", dicService.loadDicSinocorp(getCurUser().getCorpid()).getShortname());
		return "carryon";
	}

	/**
	 * 续签
	 * 
	 * @return
	 * @throws Exception
	 */
	public String carryon() throws Exception {
		String car_noArr[] = getRequest().getParameterValues("car_noArr");
		for (int i = 0; i < car_noArr.length; i++) {
			// 获取续签信息
			ExeAllocationId eai = new ExeAllocationId(car_noArr[i], exeAllocation.getId().getContract_no());
			ExeAllocation ea = new ExeAllocation();
			// ea.setcarLessee(exeAllocation.getcarLessee());

			ea.setCar_lessee(exeAllocation.getCar_lessee());

			DicSinocorp sinocorp = new DicSinocorp();
			sinocorp.setCorpid(getCurUser().getCorpid());
			ea.setCar_lessor(sinocorp);// 租赁方默认为当前用户的corpid
			ea.setTank_maint_dept(exeAllocation.getTank_maint_dept());
			ea.setCar_maint_dept(exeAllocation.getCar_maint_dept());
			ea.setRent_start_date(exeAllocation.getRent_start_date());
			ea.setRent_end_date(exeAllocation.getRent_end_date());

			ea.setRent_end_date(ea.getRent_end_date());
			ea.setRent_start_date(ea.getRent_start_date());
			SysUser sysUser = (SysUser) getSessionAttr("sysUserSess");
			ea.setCreate_user(sysUser.getUserid());
			ea.setCreate_date(new Date());
			ea.setSubmit_status("0");// 初始状态为0－录入。
			ea.setId(eai);
			exeAllocationService.saveExeAllocation(ea);// 新插入一条记录
		}
		msg = new Msg(Msg.SUCCESS, "续签成功！", "");
		return MSG;
	}

	public String delete() {
		ExeAllocationId eai = new ExeAllocationId(key_car_no, key_contract_no);
		exeAllocationService.delete(eai);
		return MSG;
	}

	public String updateStatus() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = sdf.parse(sdf.format(new java.util.Date()));
		ExeAllocationId eai = new ExeAllocationId(key_car_no, key_contract_no);
		exeAllocationService.updateStatus("1", today, eai);
		return MSG;
	}

	/**
	 * 增加时 校验调配信息是否存在
	 * 
	 * @return
	 */
	public String checkAlloExist() throws Exception {
		List<ExeAllocation> list = exeAllocationService.getAllocationByContract_no(getRequest().getParameter(
				"exeAllocation.id.contract_no"));
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("GBK");
		if (list.size() == 0)
			ServletActionContext.getResponse().getWriter().write("1");
		else
			ServletActionContext.getResponse().getWriter().write("0");
		return null;
	}

	/**
	 * 统计信息详细
	 * 
	 * @return
	 * @throws Exception
	 */
	public String statDetail() throws Exception {
		initOpraMap("EXE_ALLO");
		String lessor_id = getRequest().getParameter("lessor_id");
		String lessee_id = getRequest().getParameter("lessee_id");
		String medium_id = getRequest().getParameter("medium_id");
		listExeAllocations = exeAllocationService.listAllocationsStatDetail(lessor_id, lessee_id, medium_id);
		return "statDetail";
	}

	/**
	 * 根据合同号取得该合同号的所有详细信息,ajax获取
	 * 
	 * @return
	 */
	public String getAllocationByContract_no() {
		List<ExeAllocation> list = exeAllocationService.getAllocationByContract_no(getRequest().getParameter(
				"contract_no"));
		StringBuffer sb = new StringBuffer("");
		sb.append("{");
		String car_noArr = "";
		for (ExeAllocation allocation : list) {
			car_noArr += allocation.getId().getCar_no() + "|";
		}
		if (car_noArr.length() > 0)
			car_noArr = car_noArr.substring(0, car_noArr.length() - 1);
		if (list.size() > 0)
			exeAllocation = list.get(0);
		sb.append("\"car_no\":").append("\"").append(car_noArr).append("\",");
		sb.append("\"car_lessee\":\"").append(exeAllocation.getCar_lessee().getCorpid()).append("\",");
		sb.append("\"rent_start_date\":\"").append(exeAllocation.getRent_start_date()).append("\",");
		sb.append("\"rent_end_date\":\"").append(exeAllocation.getRent_end_date()).append("\",");
		sb.append("\"tank_maint_dept\":\"").append(
				exeAllocation.getTank_maint_dept() == null ? "" : exeAllocation.getTank_maint_dept().getRepcorpid())
				.append("\",");
		sb.append("\"car_maint_dept\":\"").append(
				exeAllocation.getCar_maint_dept() == null ? "" : exeAllocation.getCar_maint_dept().getRwdepaid())
				.append("\"");
		sb.append("}");
		out(ServletActionContext.getResponse(), sb.toString());
		return null;
	}

	/**
	 * Servlet打印字符串
	 * 
	 * @param response
	 * @param str
	 */
	public void out(HttpServletResponse response, String str) {
		try {
			response.setContentType("text/xml; charset=GBK");
			response.getWriter().println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ExeAllocationService getExeAlloctionService() {
		return exeAllocationService;
	}

	public void setExeAlloctionService(ExeAllocationService exeAllocationService) {
		this.exeAllocationService = exeAllocationService;
	}

	public List<ExeAllocation> getListExeAllocations() {
		return listExeAllocations;
	}

	public void setListExeAllocations(List<ExeAllocation> listExeAllocations) {
		this.listExeAllocations = listExeAllocations;
	}

	public List<DicSinocorp> getListSinoCorp() {
		return listSinoCorp;
	}

	public void setListSinoCorp(List<DicSinocorp> listSinoCorp) {
		this.listSinoCorp = listSinoCorp;
	}

	public List<DicRwdepartment> getListRwdepartment() {
		return listRwdepartment;
	}

	public void setListRwdepartment(List<DicRwdepartment> listRwdepartment) {
		this.listRwdepartment = listRwdepartment;
	}

	public ExeAllocation getExeAllocation() {
		return exeAllocation;
	}

	public void setExeAllocation(ExeAllocation exeAllocation) {
		this.exeAllocation = exeAllocation;
	}

	public ExeAllocationId getExeAllocationId() {
		return exeAllocationId;
	}

	public void setExeAllocationId(ExeAllocationId exeAllocationId) {
		this.exeAllocationId = exeAllocationId;
	}

	public String getRentStartDate() {
		return rentStartDate;
	}

	public void setRentStartDate(String rent_start_date) {
		rentStartDate = rent_start_date;
	}

	public String getRentEndDate() {
		return rentEndDate;
	}

	public void setRentEndDate(String rent_end_date) {
		rentEndDate = rent_end_date;
	}

	public ExeDicService getDicService() {
		return dicService;
	}

	public void setDicService(ExeDicService dicService) {
		this.dicService = dicService;
	}

	public ExeAllocationService getExeAllocationService() {
		return exeAllocationService;
	}

	public void setExeAllocationService(ExeAllocationService exeAllocationService) {
		this.exeAllocationService = exeAllocationService;
	}

	public String getKey_car_no() {
		return key_car_no;
	}

	public void setKey_car_no(String key_car_no) {
		this.key_car_no = key_car_no;
	}

	public String getKey_contract_no() {
		return key_contract_no;
	}

	public void setKey_contract_no(String key_contract_no) {
		this.key_contract_no = key_contract_no;
	}

	public String getSubmit_status() {
		return submit_status;
	}

	public void setSubmit_status(String submit_status) {
		this.submit_status = submit_status;
	}

	public DicSinocorp getDicSinocorp() {
		return dicSinocorp;
	}

	public void setDicSinocorp(DicSinocorp dicSinocorp) {
		this.dicSinocorp = dicSinocorp;
	}

	public String getCarLessor() {
		return carLessor;
	}

	public void setCarLessor(String carLessor) {
		this.carLessor = carLessor;
	}

	public String getCarLessee() {
		return carLessee;
	}

	public void setCarLessee(String carLessee) {
		this.carLessee = carLessee;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public List<DicGoodscategory> getListDicGoodsCategory() {
		return listDicGoodsCategory;
	}

	public void setListDicGoodsCategory(List<DicGoodscategory> listDicGoodsCategory) {
		this.listDicGoodsCategory = listDicGoodsCategory;
	}

	public String getCar_medium_id() {
		return car_medium_id;
	}

	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}

	public List<ExeAllocationStatVO> getListStat() {
		return listStat;
	}

	public void setListStat(List<ExeAllocationStatVO> listStat) {
		this.listStat = listStat;
	}

	public List<String> getListContract_no() {
		return listContract_no;
	}

	public void setListContract_no(List<String> listContract_no) {
		this.listContract_no = listContract_no;
	}

}
