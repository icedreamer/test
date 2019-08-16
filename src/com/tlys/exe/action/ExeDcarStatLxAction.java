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
 * ������̬��ѯAction
 * 
 * @author �״���
 * 
 */

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "search", location = "/exe/exe-dcarStat-lx-search.jsp"),
		@Result(name = "frame", location = "/exe/exe-dcarStat-lx-frame.jsp"),
		@Result(name = "left", location = "/exe/exe-dcarStat-lx-left.jsp"),
		@Result(name = "main", location = "/exe/exe-dcarStat-lx-main.jsp"),
		@Result(name = "detail", location = "/exe/exe-dcarStat-lx-detail.jsp") })
		
public class ExeDcarStatLxAction extends _BaseAction {

	@Autowired
	protected ExeDcarStatService dcarStatService;

	@Autowired
	protected ExeDicService dicService;

	public DicDBUtil dicDBUtil;

	private List<ExeDcarStat> listDcarStat;// �����ѯ����б�
	private List<DicGoods> listDicGoods;// ����Ʒ���б�
	private List<DicGoodscategory> listDicGoodsCategory;// �����װ�����ֵ�
	private List<DicCartype> listDicCartype;// /���峵���ֵ�
	private List<DicSinocorp> listDicSinocorp;// ���峵��ʹ����ҵ
	private ExeDcarStat exeDcarStat;// ����ExeDcarStat��˫��������ϸ��Ϣ
	private Integer estarrCount;// ����Ԥ�Ƶ��ﳵ����
	private List<ExeEstArrival> listEstArrival;// Ԥ�Ƶ�����Ϣ�б�

	/**
	 * ����Ȩ�޿���
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

	// �����ѯ����
	private String zbc_flag;// ��ѯ����
	private String con_name;// �ջ���
	private String shipper_name;// ������
	private String cdy_org_stn;// ʼ��վ
	private String dest_stn;// �յ�վ
	private String car_rent_flag;// ����
	private String goods_type;// ��Ʒ���
	private String car_no;// ����
	private String car_user_id;// ����ʹ����ҵ
	private String car_type;// ����
	private String le_code;// �ؿ�
	private String cdy_code;// ����Ʒ��
	private String car_medium_id;// ��װ����
	private String params;// ������������ڵ���ʱ�����û���ѯ�Ĳ���������

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
		listDicGoodsCategory = dicService.listDicGoodscategory();// ��װ�����ֵ�
		listDicCartype = dicService.listDicCartype();// �����ֵ�
		// listDicSinocorp = dicService.listDicSinocorp("dataAutho");//
		// ����ʹ����ҵ�������û������ݷ���Ȩ������ȡ
		listDicSinocorp = dicService.listDicSinocorp();// ����ʹ����ҵ�������û������ݷ���Ȩ������ȡ
		return "left";
	}

	/**
	 * ���main����
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
		field.setCorpid(sysUser.getCorpid());// �����û���ҵid
		params = "?zbc_flag=" + "1" + "&con_name=" + "" + "&shipper_name=" + "" + "&cdy_org_stn=" + "" + "&dest_stn="
				+ "" + "&car_rent_flag=" + "" + "&goods_type=" + "" + "&car_no=" + "" + "&car_user_id=" + ""
				+ "&car_type=" + "" + "&cdy_code=" + "" + "&le_code=" + "" + "&car_medium_id=" + "";
		String pageUrl = "/exe/exe-dcar-stat-lx!searchResult.action" + params;
		int totalRecord = dcarStatService.getListDcarStatCountLx(field);
		pageView = dcarStatService.listDcarStatLx(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : 10));
		listDcarStat = pageView.getRecords();
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
	public String searchResult() throws Exception {
		initOpraMap("EXE_DCAR");
		estarrCount = dcarStatService.getEstarr_flagCount();// Ԥ�Ƶ��ﳵ����
		// listDicGoods = dicService.listDicGoods();// Ʒ���ֵ�
		// listDicGoodsCategory = dicService.listDicGoodscategory();// ��װ�����ֵ�
		// listDicCartype = dicService.listDicCartype();// �����ֵ�
		// ������,��������-Ϊ���ŶΣ�ҪôΪ�ָ��ĳ��ţ�ҪôΪ���ŶΣ�����9999991,99999992������9999991-99999995
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
		field.setCorpid(sysUser.getCorpid());// �����û���ҵid
		params = "?zbc_flag=" + zbc_flag + "&con_name=" + StringUtil.encodeUrl(con_name) + "&shipper_name="
				+ StringUtil.encodeUrl(shipper_name) + "&cdy_org_stn=" + StringUtil.encodeUrl(cdy_org_stn)
				+ "&dest_stn=" + StringUtil.encodeUrl(dest_stn) + "&car_rent_flag=" + car_rent_flag + "&goods_type="
				+ goods_type + "&car_no=" + car_no + "&car_user_id=" + car_user_id + "&car_type=" + car_type
				+ "&cdy_code=" + cdy_code + "&le_code=" + le_code + "&car_medium_id=" + car_medium_id;
		String pageUrl = "/exe/exe-dcar-stat-lx!searchResult.action" + params;
		int totalRecord = dcarStatService.getListDcarStatCountLx(field);
		pageView = dcarStatService.listDcarStatLx(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : GlobalConst.pageSize));
		listDcarStat = pageView.getRecords();
		// int count = dcarStatService.getListDcarStat(field);
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
			field.setCorpid(sysUser.getCorpid());// �����û���ҵid
			listDcarStat = dcarStatService.exportDcarStatLx(field);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		
		dcarStatService.exportExcelLx(listDcarStat, sysUser.getCorpid(), response);
		return null;

	}

	/**
	 * ȡ�ó�����̬��ϸ��Ϣ
	 * 
	 * @return
	 */
	public String getDetail() {
		exeDcarStat = dcarStatService.loadExeDcarStat(car_no);
		if (exeDcarStat.getEstarr_flag().equals("1"))
			listEstArrival = dcarStatService.listEstArrival(exeDcarStat.getCar_no(), exeDcarStat.getEvt_time());// Ԥ�ڵ���ʱ��
		return "detail";
	}

	/**
	 * ȡ��Ԥ�Ƶ�����Ϣ�б�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getEstarr_flagRec() throws Exception {
		estarrCount = dcarStatService.getEstarr_flagCount();
		String pageUrl = "/exe/exe-dcar-stat-lx!getEstarr_flagRec.action";
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
