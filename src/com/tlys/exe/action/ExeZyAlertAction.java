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

	private List<ExeZyAlert> listZyAlert;// �����ѯ����б�
	private List<DicGoods> listDicGoods;// ����Ʒ���б�
	private List<DicGoodscategory> listDicGoodsCategory;// �����װ�����ֵ�
	private List<DicCartype> listDicCartype;// /���峵���ֵ�
	private List<DicSinocorp> listDicSinocorp;// ���峵��ʹ����ҵ
	private ExeZyAlert exeZyAlert;// ����ExeDcarStat��˫��������ϸ��Ϣ
	private Integer estarrCount;// ����Ԥ�Ƶ��ﳵ����
	private List<ExeEstArrival> listEstArrival;// Ԥ�Ƶ�����Ϣ�б�

	/*
	 * 
	 * ����Ȩ�޿���
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

	// �����ѯ����
	private String oper_flag;// �������ͣ�δ����(Ĭ��)���ѽ����������)
	private String car_no;// ����
	private String cdy_org_stn;// ʼ��վ
	private String dest_stn;// �յ�վ
	private String car_user_id;// ����ʹ����ҵ
	private String cdy_code;// ����Ʒ��
	private String car_medium_id;// ��װ����

	private String params;// ������������ڵ���ʱ�����û���ѯ�Ĳ���������
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
	 * ������
	 * 
	 * @return
	 */
	public String frame() {
		return "frame";
	}

	/**
	 * ����������
	 * 
	 * @return
	 */
	public String left() {
		// listDicGoods = dicService.listDicGoods();// Ʒ���ֵ�
		e_date = StringUtil.getSystemDate();
		s_date = StringUtil.getOptSystemDate(-30);
		listDicGoodsCategory = dicService.listDicGoodscategory();// ��װ�����ֵ�
		return "left";
	}

	/**
	 * ���main����
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
		field.setCorpid(sysUser.getCorpid());// �����û���ҵid
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
	 * �����ѯ����
	 * 
	 * @return
	 */
	public String search() {
		initOpraMap("EXE_DCAR");
		listDicGoods = dicService.listDicGoods();// Ʒ���ֵ�
		listDicGoodsCategory = dicService.listDicGoodscategory();// ��װ�����ֵ�
		listDicCartype = dicService.listDicCartype();// �����ֵ�
		return "search";
	}

	/**
	 * ��ʾ��ѯ���
	 * 
	 * @return
	 */
	// ������Ӧ��̬���CAR_RENT_FLAG�ֶΣ�0-���á�1-���⡢2-��������
	// ��ֵΪ1ʱ����Ҫ���ݵ�ǰ��¼�û�������ҵ�����ղ�Ȩ��ҵID(CAR_OWNER_ID)��ʹ����ҵID(CAR_USER_ID)������̬��ʾ�����޻������á�
	// ����ʾ��¼��ʱ�������¼�û��ǲ�Ȩ��ҵ����Ҫ���жϲ�Ȩ��ҵ��ѯ��ʶ(CAR_OWNER_FLAG)�Ƿ�Ϊ(1-�ɲ飬0-���ɲ�)��
	// �����¼�û���ʹ����ҵ�����ж�ʹ����ҵ��ѯ��ʶ(CAR_USER_FLAG)�Ƿ�Ϊ(1-�ɲ飬0-���ɲ�)��
	// ����Ȳ��ǲ�Ȩ��ҵҲ����ʹ����ҵ�����ܲ鿴��ǰ��¼��
	// ��ѯ�ֶ�������corpid�ֶΣ���dao�������ж�car_rent_flag�Ƿ�Ϊ1�����Ϊ1���ж�corpid�Ƿ�Ϊ00000000��������������������ҵ�û���Ȼ����һ�����ѯ��corpid=car_user_flag
	// and CAR_USER_FLAG='1' || corpid=CAR_OWNER_ID and car_owner_flag='1'
	@SuppressWarnings("unchecked")
	public String searchResult() throws Exception {
		initOpraMap("EXE_ZYALERT");
		// listDicGoods = dicService.listDicGoods();// Ʒ���ֵ�
		// listDicGoodsCategory = dicService.listDicGoodscategory();// ��װ�����ֵ�
		// listDicCartype = dicService.listDicCartype();// �����ֵ�
		// ������,��������-Ϊ���ŶΣ�ҪôΪ�ָ��ĳ��ţ�ҪôΪ���ŶΣ�����9999991,99999992������9999991-99999995
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
		field.setCorpid(sysUser.getCorpid());// �����û���ҵid
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
	 * ����excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportExcel() throws Exception {
		// Ϊ��ѯ������ֵ
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
		field.setCorpid(sysUser.getCorpid());// �����û���ҵid
		field.setPbl_reason(pbl_reason);
		field.setS_date(s_date);
		field.setE_date(e_date);
		listZyAlert = zyAlertService.exportZyAlert(field);
		HttpServletResponse response = ServletActionContext.getResponse();
		zyAlertService.exportExcel(listZyAlert, sysUser.getCorpid(), response);
		return null;

	}

	/**
	 * ȡ�ó�����̬��ϸ��Ϣ
	 * 
	 * @return
	 */
	public String getDetail() {
		String evt_timeStr = getRequest().getParameter("evt_time");
		exeZyAlert = zyAlertService.loadExeZyAlert(car_no, StringUtil.StringToDate(evt_timeStr, "yyyy-MM-dd HH:mm:ss"));
		if (exeZyAlert.getEstarr_flag().equals("1"))
			listEstArrival = zyAlertService.listEstArrival(exeZyAlert.getId().getCar_no(), exeZyAlert.getId()
					.getEvt_time());// Ԥ�ڵ���ʱ��
		return "detail";
	}

	/**
	 * �޸�״̬
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
