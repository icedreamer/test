package com.tlys.exe.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicGoodsType;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.equ.model.EquCar;
import com.tlys.exe.common.util.DicDBUtil;
import com.tlys.exe.common.util.GlobalConst;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeTranStasVO;
import com.tlys.exe.dao.ExeTransportSearchField;
import com.tlys.exe.model.ExeTransport;
import com.tlys.exe.service.ExeDicService;
import com.tlys.exe.service.ExeTransportService;
import com.tlys.sys.model.SysUser;

/**
 * ������ϢAction
 * 
 * @author �״���
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "search", location = "/exe/exe-transport-search.jsp"),
		@Result(name = "main", location = "/exe/exe-transport-main.jsp"),
		@Result(name = "left", location = "/exe/exe-transport-left.jsp"),
		@Result(name = "detail", location = "/exe/exe-transport-detail.jsp"),
		@Result(name = "tranStat", location = "/exe/exe-transport-tranStat.jsp") })
public class ExeTransportAction extends _BaseAction {

	@Autowired
	ExeTransportService transportService;

	@Autowired
	ExeDicService dicService;

	private DicDBUtil dicDBUtil;
	// �����ѯ����
	private String zbc_flag;// ��ѯ����
	private String shipper_name;// ������
	private String shipper_entpr_id;// /��������ҵ
	private String con_name;// �ջ���
	private String con_entpr_id;// �ջ�����ҵ
	private String cdy_org_stn;// ʼ��վ
	private String dest_stn;// �յ�վ
	private String goods_type;// ��Ʒ���
	private String cdy_code;// ����Ʒ��
	private String car_no;// ���ţ��೵����,����
	private String wb_date_s;// ��Ʊ������ʼ
	private String wb_date_e;// ��Ʊ������ֹ
	private String msg_type;// ��Ϣ��Դ
	private int pageSize;// ÿҳ����
	private String params;// ������������ڵ���ʱ�����û���ѯ�Ĳ���������
	private HashMap<String, String> mapCarCorp;// ҳ�泵��x��Ӧ����ҵmap

	/**
	 * ����Ȩ�޿���
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_TRAN");
	}

	public HashMap<String, String> getMapCarCorp() {
		return mapCarCorp;
	}

	public void setMapCarCorp(HashMap<String, String> mapCarCorp) {
		this.mapCarCorp = mapCarCorp;
	}

	public Long getRec_id() {
		return rec_id;
	}

	public void setRec_id(Long rec_id) {
		this.rec_id = rec_id;
	}

	public String getShipper_name() {
		return shipper_name;
	}

	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}

	public String getShipper_entpr_id() {
		return shipper_entpr_id;
	}

	public void setShipper_entpr_id(String shipper_entpr_id) {
		this.shipper_entpr_id = shipper_entpr_id;
	}

	public String getCdy_org_stn() {
		return cdy_org_stn;
	}

	public void setCdy_org_stn(String cdy_org_stn) {
		this.cdy_org_stn = cdy_org_stn;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getCdy_code() {
		return cdy_code;
	}

	public void setCdy_code(String cdy_code) {
		this.cdy_code = cdy_code;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getWb_date_s() {
		return wb_date_s;
	}

	public void setWb_date_s(String wb_date_s) {
		this.wb_date_s = wb_date_s;
	}

	public String getWb_date_e() {
		return wb_date_e;
	}

	public void setWb_date_e(String wb_date_e) {
		this.wb_date_e = wb_date_e;
	}

	private List<ExeTransport> listTransport;// �����ѯ����б�
	private List<DicGoods> listDicGoods;// ����Ʒ���б�
	private List<DicSinocorp> listDicSinocorp;// �����շ�������ҵ
	private ExeTransport exeTransport;// ���嵥�����������Ϣ;
	private Long rec_id;// ����
	private double car_actual_wgt;// ʵ������
	private List<DicRwbureau> listAdm;// ·���ֵ�
	private List<DicGoodsType> listDicGoodsType;// Ʒ�����
	private List<ExeTranStasVO> listTranAreaStat;// ������������ͳ���б�
	private List<ExeTranStasVO> listTranCorpStat;// �������乫˾ͳ���б�
	private String statDate;// ����ͳ�����ڣ�Ĭ��Ϊ��ǰ����
	private List<DicSinocorp> listShipperEntpr;// ��������ҵ

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public List<ExeTranStasVO> getListTranAreaStat() {
		return listTranAreaStat;
	}

	public void setListTranAreaStat(List<ExeTranStasVO> listTranAreaStat) {
		this.listTranAreaStat = listTranAreaStat;
	}

	public List<ExeTranStasVO> getListTranCorpStat() {
		return listTranCorpStat;
	}

	public void setListTranCorpStat(List<ExeTranStasVO> listTranCorpStat) {
		this.listTranCorpStat = listTranCorpStat;
	}

	public List<DicGoodsType> getListDicGoodsType() {
		return listDicGoodsType;
	}

	public void setListDicGoodsType(List<DicGoodsType> listDicGoodsType) {
		this.listDicGoodsType = listDicGoodsType;
	}

	public List<DicRwbureau> getListAdm() {
		return listAdm;
	}

	public void setListAdm(List<DicRwbureau> listAdm) {
		this.listAdm = listAdm;
	}

	public double getCar_actual_wgt() {
		return car_actual_wgt;
	}

	public void setCar_actual_wgt(double car_actual_wgt) {
		this.car_actual_wgt = car_actual_wgt;
	}

	public ExeTransport getExeTransport() {
		return exeTransport;
	}

	public void setExeTransport(ExeTransport exeTransport) {
		this.exeTransport = exeTransport;
	}

	public List<ExeTransport> getListTransport() {
		return listTransport;
	}

	public void setListTransport(List<ExeTransport> listTransport) {
		this.listTransport = listTransport;
	}

	public List<DicGoods> getListDicGoods() {
		return listDicGoods;
	}

	public void setListDicGoods(List<DicGoods> listDicGoods) {
		this.listDicGoods = listDicGoods;
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
		listDicSinocorp = dicService.listDicSinocorp("dataAutho");// ����ʹ����ҵ�������û������ݷ���Ȩ������ȡ
		// listDicSinocorp = dicService.listDicSinocorp();//
		// ����ʹ����ҵ�������û������ݷ���Ȩ������ȡ
		wb_date_s = StringUtil.getOptSystemDate(-30);
		wb_date_e = StringUtil.getSystemDate();
		return "left";
	}

	/**
	 * ���main����
	 * 
	 * @return
	 */
	public String main() {
		initOpraMap("EXE_TRAN");
		zbc_flag = "1";
		pageSize = GlobalConst.pageSize;
		ExeTransportSearchField field = new ExeTransportSearchField();
		field.setWb_date_s(StringUtil.getOptSystemDate(-30));
		field.setWb_date_e(StringUtil.getSystemDate());
		field.setZbc_flag("1");
		field.setPageSize(pageSize);
		params = "?zbc_flag=" + field.getZbc_flag() + "&shipper_entpr_id=" + "" + "&shipper_name=" + "" + "&con_name="
				+ "" + "&con_entpr_id=" + "" + "&cdy_code=" + "" + "&cdy_org_stn=" + "" + "&dest_stn=" + ""
				+ "&goods_type=" + "" + "&car_no=" + "" + "&wb_date_s=" + field.getWb_date_s() + "&wb_date_e="
				+ field.getWb_date_e() + "&msg_type=&pageSize=10";
		String pageUrl = "/exe/exe-transport!searchResult.action" + params;
		int totalRecord = transportService.getListTransportCount(field);
		pageView = transportService.listTransport(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : pageSize));
		listTransport = pageView.getRecords();
		String car_noArr = "";
		for (ExeTransport transport : listTransport) {
			car_noArr += transport.getCar_no() + ",";
		}
		mapCarCorp = dicService.getCorpNameByCar_noArr(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
				car_noArr.length() - 1));
		double cap_wgt = transportService.getCap_wgt(field);// �����������ϼ���
		double actual_wgt = transportService.getActual_wgt(field);
		getRequest().setAttribute("cap_wgt", cap_wgt);
		getRequest().setAttribute("actual_wgt", actual_wgt);
		return "main";
	}

	/**
	 * �����ѯ����
	 * 
	 * @return
	 */

	public String search() {
		initOpraMap("EXE_TRAN");
		listDicGoods = dicService.listDicGoods();// Ʒ���ֵ�
		return "search";
	}

	/**
	 * ��ʾ��ѯ���
	 * 
	 * @return
	 */
	public String searchResult() throws Exception {
		initOpraMap("EXE_TRAN");
		// Ϊ��ѯ������ֵ
		ExeTransportSearchField field = new ExeTransportSearchField();
		field.setZbc_flag(zbc_flag);
		field.setShipper_entpr_id(shipper_entpr_id);
		field.setShipper_name(StringUtil.decodeUrl(shipper_name));
		field.setCon_name(StringUtil.decodeUrl(con_name));
		field.setCon_entpr_id(con_entpr_id);
		field.setCdy_code(cdy_code);
		// /cdy_org_stnȡ������ʽBJ:��������Ҫת��ΪBJP
		field.setCdy_org_stn(StringUtil.decodeUrl(cdy_org_stn));
		field.setDest_stn(StringUtil.decodeUrl(dest_stn));
		field.setGoods_type(goods_type);
		field.setCar_no(car_no);
		field.setWb_date_s(wb_date_s);
		field.setWb_date_e(wb_date_e);
		field.setMsg_type(msg_type);
		field.setPageSize(pageSize);
		params = "?zbc_flag=" + zbc_flag + "&shipper_entpr_id=" + shipper_entpr_id + "&shipper_name="
				+ StringUtil.encodeUrl(shipper_name) + "&con_name=" + StringUtil.encodeUrl(con_name) + "&con_entpr_id="
				+ con_entpr_id + "&cdy_code=" + cdy_code + "&cdy_org_stn=" + StringUtil.encodeUrl(cdy_org_stn)
				+ "&dest_stn=" + StringUtil.encodeUrl(dest_stn) + "&goods_type=" + goods_type + "&car_no=" + car_no
				+ "&wb_date_s=" + wb_date_s + "&wb_date_e=" + wb_date_e + "&msg_type=" + msg_type;
		String pageUrl = "/exe/exe-transport!searchResult.action" + params;
		int totalRecord = transportService.getListTransportCount(field);
		pageView = transportService.listTransport(field, pageUrl, totalRecord, (null != pageView ? pageView
				.getCurrentPage() : 1), (null != pageView ? pageView.getPageSize() : pageSize));
		listTransport = pageView.getRecords();
		if (zbc_flag.equals("1")) {// ������Ա���ʱ�������Ա�����Ӧ����ҵ
			String car_noArr = "";
			for (ExeTransport transport : listTransport) {
				car_noArr += transport.getCar_no() + ",";
			}
			mapCarCorp = dicService.getCorpNameByCar_noArr(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
					car_noArr.length() - 1));
		}
		double cap_wgt = transportService.getCap_wgt(field);// �����������ϼ���
		double actual_wgt = transportService.getActual_wgt(field);
		getRequest().setAttribute("cap_wgt", cap_wgt);
		getRequest().setAttribute("actual_wgt", actual_wgt);
		return "main";
	}

	/**
	 * �����˹���¼ʵ�����ؽ���
	 * 
	 * @return
	 */
	public String toPlusInput() {
		initOpraMap("EXE_TRAN");
		exeTransport = transportService.getExeTransport(rec_id);
		return "plusInput";
	}

	/**
	 * ��¼ʵ��������Ϣ
	 * 
	 * @return
	 */
	public String plusInput() {
		ExeTransport exeTransport2 = transportService.getExeTransport(rec_id);
		exeTransport2.setCar_actual_wgt(exeTransport.getCar_actual_wgt());
		exeTransport2.setCapw_edit_time(new Date());// �Ʒ���������ʱ��
		exeTransport2.setCap_wgt_editor(((SysUser) getSessionAttr("sysUserSess")).getUserid());// �Ʒ����������û�ID
		transportService.saveExeTransport(exeTransport2);
		msg = new Msg(Msg.SUCCESS, "��¼����ʵ��������Ϣ�ɹ���", "");
		return MSG;
	}

	/**
	 * �������ӻ�����Ϣ����
	 * 
	 * @return
	 */
	public String toAdd() {
		initOpraMap("EXE_TRAN");
		listDicSinocorp = dicService.listDicSinocorp();// �ջ�����ҵ
		listShipperEntpr = dicService.listDicSinocorp("dataAutho");// ��������ҵ,������Ȩ������ȡ��
		return "add";
	}

	/**
	 * ���ӻ��޸Ļ���������Ϣ
	 * 
	 * @return
	 */
	public String add() {
		String cdyString = exeTransport.getCdy_code();// 1500001:����
		String cdy_o_stn_name = exeTransport.getCdy_o_stn_name();// ����JMD:���Ŷ�
		String dest_stn_name = exeTransport.getDest_stn_name();
		if (cdyString.indexOf(":") == -1 || cdy_o_stn_name.indexOf(":") == -1 || dest_stn_name.indexOf(":") == -1) {
			msg = new Msg(Msg.FAILURE, "������Ϣ�д���", "");
			return MSG;
		}
		exeTransport.setCdy_code(cdyString.substring(0, cdyString.indexOf(":")));// Ʒ������
		exeTransport.setCdy_name(cdyString.substring(cdyString.indexOf(":") + 1));// Ʒ������
		DicGoodsType goodsType = new DicGoodsType();
		goodsType.setDm(exeTransport.getCdy_code().substring(0, 2));
		exeTransport.setDicGoodsType(goodsType);// Ʒ�����

		exeTransport.setCdy_org_stn(cdy_o_stn_name.substring(0, cdy_o_stn_name.indexOf(":")));// ȡ���籨��JME
		exeTransport.setCdy_o_stn_name(cdy_o_stn_name.substring(cdy_o_stn_name.indexOf(":") + 1));// �������־��Ŷ�

		exeTransport.setDest_stn(dest_stn_name.substring(0, dest_stn_name.indexOf(":")));
		exeTransport.setDest_stn_name(dest_stn_name.substring(dest_stn_name.indexOf(":") + 1));

		exeTransport.setOrg_adm(dicService.getRwStationByTelegramid(exeTransport.getCdy_org_stn()).getPycode());// ʼ����
		exeTransport.setDest_adm(dicService.getRwStationByTelegramid(exeTransport.getDest_stn()).getPycode());// �յ���

		DicSinocorp corp1 = dicService.loadDicSinocorp(exeTransport.getCon_entpr_id());
		if (corp1 == null)
			exeTransport.setCon_entpr_name("");
		else
			exeTransport.setCon_entpr_name(corp1.getFullname());
		DicSinocorp corp2 = dicService.loadDicSinocorp(exeTransport.getShipper_entpr_id());
		if (corp2 == null)
			exeTransport.setShipper_entpr_name("");
		else
			exeTransport.setShipper_entpr_name(corp2.getFullname());
		exeTransport.setMsg_type("R");// ��Ϣ��Դ���ֹ�¼��
		exeTransport.setMsg_status("0");// ״̬
		exeTransport.setLe_code("1");// �ؿգ�Ĭ��Ϊ��
		exeTransport.setHp_editor(((SysUser) getSessionAttr("sysUserSess")).getUserid());// ��¼�����û�ID
		exeTransport.setHp_edit_time(new Date());// ��¼����ʱ��
		// exeTransport.setCar_user_id(exeTransport.getShipper_entpr_id());//
		// ʹ����ҵid
		exeTransport.setCapw_edit_time(new Date());// �Ʒ���������ʱ��
		exeTransport.setCap_wgt_editor(((SysUser) getSessionAttr("sysUserSess")).getUserid());// �Ʒ����������û�ID
		// ƴдwb_id//��վ���棨Ddmmyyyy(8)+��վ(3)+�ո�(2)+Ʊ��Ʊ�ţ�7)��Ddmmyyyy(8)+��վ(3)
		// +װ���嵥��(8) +�ո�(1))
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String wb_date = df.format(exeTransport.getWb_date());
		String wb_id = wb_date.substring(8, 10) + wb_date.substring(5, 7) + wb_date.substring(0, 4)
				+ exeTransport.getCdy_org_stn() + "  " + exeTransport.getWb_nbr();
		exeTransport.setWb_id(wb_id);
		exeTransport.setWb_date_str(wb_date);
		exeTransport.setCapw_edit_time(new Date());// �Ʒ���������ʱ��
		exeTransport.setCap_wgt_editor(((SysUser) getSessionAttr("sysUserSess")).getUserid());// �Ʒ����������û�ID
		EquCar equCar = dicService.getEquCarByCarno(exeTransport.getCar_no());
		if (equCar == null)
			exeTransport.setZbc_flag("0");
		else
			exeTransport.setZbc_flag("1");

		List<ExeTransport> transports = transportService.queryExeTransport(exeTransport.getWb_date(), exeTransport
				.getCdy_org_stn(), exeTransport.getWb_nbr());
		if (transports.size() == 0) {
			transportService.saveExeTransport(exeTransport);
			msg = new Msg(Msg.SUCCESS, "��¼������Ϣ�ɹ���", "");
		} else {
			msg = new Msg(Msg.FAILURE, "�û�Ʊ��Ϣ�Ѵ��ڣ�", "");
		}
		return MSG;
	}

	/**
	 * �����޸Ļ�����Ϣ����
	 * 
	 * @return
	 */
	public String toUpdate() {
		initOpraMap("EXE_TRAN");
		listDicSinocorp = dicService.listDicSinocorp();// �շ�����ҵ
		exeTransport = transportService.getExeTransport(rec_id);
		listShipperEntpr = dicService.listDicSinocorp("dataAutho");// ��������ҵ,������Ȩ������ȡ��
		return "update";
	}

	/**
	 * �޸Ļ���������Ϣ
	 * 
	 * @return
	 */
	public String update() {
		ExeTransport load = transportService.getExeTransport(exeTransport.getRec_id());
		String cdyString = exeTransport.getCdy_code();// 1500001:����
		String cdy_o_stn_name = exeTransport.getCdy_o_stn_name();// ����JMD:���Ŷ�
		String dest_stn_name = exeTransport.getDest_stn_name();
		if (cdyString.indexOf(":") == -1 || cdy_o_stn_name.indexOf(":") == -1 || dest_stn_name.indexOf(":") == -1) {
			msg = new Msg(Msg.FAILURE, "������Ϣ�д���", "");
			return MSG;
		}
		load.setCdy_code(cdyString.substring(0, cdyString.indexOf(":")));// Ʒ������
		load.setCdy_name(cdyString.substring(cdyString.indexOf(":") + 1));// Ʒ������
		DicGoodsType goodsType = new DicGoodsType();
		goodsType.setDm(load.getCdy_code().substring(0, 2));
		load.setDicGoodsType(goodsType);// Ʒ�����

		load.setWb_nbr(exeTransport.getWb_nbr());
		load.setCar_no(exeTransport.getCar_no());
		load.setLe_code(exeTransport.getLe_code());
		load.setGoods_type(exeTransport.getGoods_type());
		load.setCar_cap_wgt(exeTransport.getCar_cap_wgt());
		load.setCar_actual_wgt(exeTransport.getCar_actual_wgt());
		load.setCdy_org_stn(cdy_o_stn_name.substring(0, cdy_o_stn_name.indexOf(":")));// ȡ���籨��JME
		load.setCdy_o_stn_name(cdy_o_stn_name.substring(cdy_o_stn_name.indexOf(":") + 1));// �������־��Ŷ�
		load.setDest_stn(dest_stn_name.substring(0, dest_stn_name.indexOf(":")));
		load.setDest_stn_name(dest_stn_name.substring(dest_stn_name.indexOf(":") + 1));

		load.setOrg_adm(dicService.getRwStationByTelegramid(load.getCdy_org_stn()).getPycode());// ʼ����
		load.setDest_adm(dicService.getRwStationByTelegramid(load.getDest_stn()).getPycode());// �յ���

		load.setCon_name(exeTransport.getCon_name());
		load.setShipper_name(exeTransport.getShipper_name());
		load.setCon_entpr_id(exeTransport.getCon_entpr_id());
		load.setShipper_entpr_id(exeTransport.getShipper_entpr_id());
		DicSinocorp corp1 = dicService.loadDicSinocorp(exeTransport.getCon_entpr_id());
		if (corp1 == null)
			exeTransport.setCon_entpr_name("");
		else
			exeTransport.setCon_entpr_name(corp1.getFullname());
		DicSinocorp corp2 = dicService.loadDicSinocorp(exeTransport.getShipper_entpr_id());
		if (corp2 == null)
			exeTransport.setShipper_entpr_name("");
		else
			exeTransport.setShipper_entpr_name(corp2.getFullname());
		load.setHp_editor(((SysUser) getSessionAttr("sysUserSess")).getUserid());// ��¼�����û�ID
		load.setHp_edit_time(new Date());// ��¼����ʱ��
		load.setCapw_edit_time(new Date());// �Ʒ���������ʱ��
		load.setCap_wgt_editor(((SysUser) getSessionAttr("sysUserSess")).getUserid());// �Ʒ����������û�ID
		transportService.saveExeTransport(load);
		msg = new Msg(Msg.SUCCESS, "�޸�������Ϣ�ɹ���", "");
		return MSG;
	}

	/**
	 * ɾ������������Ϣ
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		if (null == rec_id) {
			throw new Exception("����ȷ��Ҫɾ���Ķ���");
		} else {
			transportService.deleteExeTransport(rec_id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���");
		}
		return MSG;
	}

	/**
	 * �˹���¼���ύ����������Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String commit() throws Exception {
		if (null == rec_id) {
			throw new Exception("����ȷ��Ҫ�ύ�Ķ���");
		} else {
			// �ύʱ���������ж�wb_id�����ݿ����Ƿ���ڣ���������ڣ����ж�wb_date,wb_nbr,stn_code���ж��Ƿ���ڣ����ȫ�������ڣ����ύ

			ExeTransport load = transportService.getExeTransport(rec_id);
			load.setMsg_status("1");
			List<ExeTransport> newTransports = null;
			newTransports = transportService.queryExeTransport(load.getWb_id());
			if (newTransports.size() == 1)
				newTransports = transportService.queryExeTransport(load.getWb_date(), load.getCdy_org_stn(), load
						.getWb_nbr());
			if (newTransports.size() <= 1) {
				transportService.saveExeTransport(load);
				msg = new Msg(Msg.SUCCESS, "�˹���¼��Ϣ�ύ�ɹ���");
			} else {
				msg = new Msg(Msg.SUCCESS, "��������Ϣ����ϵͳ�Զ�¼�룬�����ύ");
			}

		}
		return MSG;
	}

	/**
	 * ȡ�ó�����̬��ϸ��Ϣ
	 * 
	 * @return
	 */
	public String getDetail() {
		initOpraMap("EXE_TRAN");
		exeTransport = transportService.getExeTransport(rec_id);
		return "detail";
	}

	/**
	 * ��������ͳ�Ʊ���
	 * 
	 * @return
	 */
	public String tranStatictics() throws Exception {
		if (statDate == null || statDate.equals("")) {
			statDate = StringUtil.getFormatDate(new Date());
			return "tranStat";
		} else {
			List<List<ExeTranStasVO>> listTrans = transportService.tranStatictics(statDate);
			listTranAreaStat = listTrans.get(0);
			listTranCorpStat = listTrans.get(1);
			return "tranStat";
		}
	}

	/**
	 * ����excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportExcel() throws Exception {
		// Ϊ��ѯ������ֵ
		ExeTransportSearchField field = new ExeTransportSearchField();
		field.setZbc_flag(zbc_flag);
		field.setShipper_entpr_id(shipper_entpr_id);
		field.setShipper_name(StringUtil.decodeUrl(shipper_name));
		field.setCon_name(StringUtil.decodeUrl(con_name));
		field.setCon_entpr_id(con_entpr_id);
		field.setCdy_code(cdy_code);
		// /cdy_org_stnȡ������ʽBJ:��������Ҫת��ΪBJP
		field.setCdy_org_stn(StringUtil.decodeUrl(cdy_org_stn));
		field.setDest_stn(StringUtil.decodeUrl(dest_stn));
		field.setGoods_type(goods_type);
		field.setCar_no(car_no);
		field.setWb_date_s(wb_date_s);
		field.setWb_date_e(wb_date_e);
		field.setMsg_type(msg_type);
		listTransport = transportService.exportTransport(field);

		HttpServletResponse response = ServletActionContext.getResponse();
		transportService.exportExcel(listTransport, zbc_flag, response);
		return null;

	}

	public List<DicSinocorp> getListDicSinocorp() {
		return listDicSinocorp;
	}

	public void setListDicSinocorp(List<DicSinocorp> listDicSinocorp) {
		this.listDicSinocorp = listDicSinocorp;
	}

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	public String getCon_name() {
		return con_name;
	}

	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	public String getCon_entpr_id() {
		return con_entpr_id;
	}

	public void setCon_entpr_id(String con_entpr_id) {
		this.con_entpr_id = con_entpr_id;
	}

	public DicDBUtil getDicDBUtil() {
		return dicDBUtil;
	}

	public void setDicDBUtil(DicDBUtil dicDBUtil) {
		this.dicDBUtil = dicDBUtil;
	}

	public String getDest_stn() {
		return dest_stn;
	}

	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	public List<DicSinocorp> getListShipperEntpr() {
		return listShipperEntpr;
	}

	public void setListShipperEntpr(List<DicSinocorp> listShipperEntpr) {
		this.listShipperEntpr = listShipperEntpr;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
