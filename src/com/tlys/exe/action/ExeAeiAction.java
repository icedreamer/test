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
 * Aei��ѯaction
 * 
 * @author �״���
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
		@Result(name = "zbclyl", location = "/exe/exe-aei-zbclyl.jsp"), // �Ա���������
		@Result(name = "zqxcDist", location = "/exe/exe-aei-zqxcDist.jsp"), // վ���ֳ��ֲ�
		@Result(name = "zqjcclStat", location = "/exe/exe-aei-zqjcclStat.jsp"),// վ����������������Ϣƥ��ͳ�Ʊ�
		@Result(name = "equipMonitor", location = "/exe/exe-aei-equipMonitor.jsp"),// վ��AEI�豸���
		@Result(name = "zyxFrame", location = "/exe/exe-aei-zyxFrame.jsp"),// ר����ͣ��ʱ��
		@Result(name = "zyxLeft", location = "/exe/exe-aei-zyxLeft.jsp"),
		@Result(name = "zyxMain", location = "/exe/exe-aei-zyxMain.jsp") })
public class ExeAeiAction extends _BaseAction {
	@Autowired
	ExeAeiService exeAeiService;
	@Autowired
	ExeDicService exeDicService;

	private List<DicSinocorp> listDicSinocorp;// ����վ������ҵ
	private List<DicCarkind> listDicCarkind;// �����б�
	private List<DicCartype> listDicCartype;// �����б�
	private List<DicCarmaker> listDicCarmaker;// ����������ҵ�б�
	private List<DicEntprStn> listDicEntprStn;// ������ҵid���Ҷ�Ӧ����ҵվ���б�
	private List<DicAreacorp> listDicAreaCorp;// �����б�
	private List<DicGoodscategory> listDicGoodscategory;// ��װ�����б�

	private List<ExeStntrn> listStntrn;// �г�������Ϣ�б�
	private List<ExeStncar> listStncar;// ������Ϣ�б�
	private ExeStntrn exeStntrn;// �г�������Ϣmodel
	private ExeStncar exeStncar;// ������Ϣmodel
	private List<Integer> listAei_no;// Aei_no�б�

	private List<ExeZbclylVO> listZbclyl;// �Ա���������
	private List<ExeZqxcDistVO> listZqxcDist;// վ���ֳ��ֲ�
	private List<ExeDcarStat> listDcarStat;// ������̬�б�
	private List<ExeZqjcclVO> listZqjccl;// վ����������
	private List<ExeEquipMonitorVO> listEquipMonitor;// �豸���

	private List<int[]> listOverdue;// ר����ͣ�������ѯ�б�
	private List<DicOverTime> listDicOvertime;// ר����ͣ�����峬ʱ�׶��б�

	private int[] rowspan;// ��������˾�������ӹ�˾��

	// ��ѯ����
	private String s_date;// ��ѯ�г���ʼ����
	private String e_date;// ��ѯ�г���ֹ����
	private String max_date;// �Ա����������������
	private String zbc_flag;// �Ƿ����Ա�����1�ǣ�0��
	private String car_no;// ����
	private String stn_entpr_id;// ������ҵid
	private String area_id;// ��������id
	private String car_medium_id;// ��װ����

	private String arr_time_hour;// ���嵽��Сʱ
	private String arr_time_min;// ���嵽�����

	private HashMap<String, String> mapStnName;// ҳ��վ��id��Ӧվ������map

	private HashMap<String, String> mapCorpName;// ҳ�泵�Ŷ�Ӧ��������ҵ
	private HashMap<String, String> mapCarMedium;// ҳ�泵�Ŷ�Ӧ�ĳ�װ����
	private HashMap<String, String> mapInOutFlag;// ҳ�泵�Ŷ�Ӧ�Ľ�����ʶ

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
	 * ����Ȩ�޿���
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
	 * AEI��ѯ����ҳ��
	 * 
	 * @return
	 */
	public String carFrame() {
		return "carFrame";
	}

	/**
	 * AEI��ѯ����������
	 * 
	 * @return
	 */
	public String carLeft() {
		s_date = StringUtil.getOptSystemDate(new Date(), -30);
		e_date = StringUtil.getSystemDate();
		listDicGoodscategory = exeDicService.listDicGoodscategory();// ��װ����
		listDicAreaCorp = exeDicService.listDicAreacorp("dataAutho");// ����
		// listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");// ��˾
		listDicSinocorp = exeDicService.listZqqy(1);// ��˾
		return "carLeft";
	}

	/**
	 * AEI��ѯ�������,Ĭ�Ͻ���
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
	 * AEI��ѯ��������ѯ���
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
	 * �����г���Ϣ��ѯ����
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
	 * ��ѯ�г���Ϣ���
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
	 * �����г����������ҳ�����Ϣ
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
	 * �����˹���¼�г���Ϣ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddStntrn() throws Exception {
		initOpraMap("EXE_AEI");
		listDicEntprStn = exeDicService.listDicEntprStn(((SysUser) getSessionAttr("sysUserSess")).getCorpid());// ��ҵվ��
		return "addStntrn";
	}

	/**
	 * �˹���¼�г���Ϣ ��������ʽ��DXXXLNNN.MDD�� ����DLAJ1001.603������ĸD��ʾ���ļ��洢�����г����ģ�
	 * ��2~4��ĸXXX��ʾ���г���������վ�ĵ籨�Ժţ���5����ĸL��ʾ���г���������վ��AEI�豸�ţ���ʽΪ1~9��A~F)����6~8��ĸNNN��ʾĳ��ĳAEI�豸�յ��ĸ��г����ĵ���ţ���3λ������ɣ���003��MDD��ʾ�յ����г����ĵ�����(��AEI������ͨ������Ϊ׼)������M��ʾ�·ݣ�������1~9��ʾ1~9�·ݣ�A��B��C�ֱ��ʾ10��11��12�·ݡ�
	 * ԭ������+YYYY+N��NΪ0-9��ʾ�ر�������ǰ̨��N���ó�0�� ����������=��ʶ������ ��ʶ��=�Ա���+������+����ʶ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addStntrn() throws Exception {
		if (exeStntrn.getStn_name().indexOf(":") > -1)
			exeStntrn.setStn_name(exeStntrn.getStn_name().substring(0, exeStntrn.getStn_name().indexOf(":")));// ���վ�����Ѵ��븳��
		exeStntrn.setStn_entpr(exeDicService.loadDicSinocorp(((SysUser) getSessionAttr("sysUserSess")).getCorpid()));// ���ñ���վ������ҵID
		String rptname = "D" + exeStntrn.getStn_name() + dealAei_no(exeStntrn.getAei_no());// ƴд������
		rptname = rptname + exeAeiService.getMaxRptId(rptname) + ".";
		String date = StringUtil.getFormatDate(exeStntrn.getArr_time());
		date = dealMonth(date.substring(5, 7)) + date.substring(8, 10) + date.substring(0, 4) + "0";
		rptname = rptname + date;
		// ����Сʱ ����ƴд��������
		String arr_time = StringUtil.getFormatDate(exeStntrn.getArr_time()) + " " + arr_time_hour + ":" + arr_time_min
				+ ":00";
		exeStntrn.setArr_time(StringUtil.StringToDate(arr_time, "yyyy-MM-dd HH:mm:ss"));
		ExeStntrn stntrn = exeAeiService.loadExeStntrn(rptname);
		if (stntrn == null) {
			exeStntrn.setRptname(rptname);// ���ñ�����������
			exeStntrn.setMsg_flag("1");// ��Ϣ��Դ��ʶ���˹���¼
			exeStntrn.setMsg_status("0");// ��Ϣ״̬��ʶ����¼��
			exeStntrn.setTrain_dir(exeDicService.getTrain_dirByEntprstn(((SysUser) getSessionAttr("sysUserSess"))
					.getCorpid(), exeStntrn.getStn_name(), exeStntrn.getAei_no(), exeStntrn.getIn_out_flag()));

			// ��������������ʶ����������ʶ�𳵡��Ա�����������������ʶ�𳵶���Ϊ0
			exeStntrn.setRpt_car_number(0);
			exeStntrn.setCar_number(0);
			exeStntrn.setT_number(0);
			exeStntrn.setQ_number(0);
			exeStntrn.setUncar_number(0);
			exeAeiService.saveStntrn(exeStntrn);
			msg = new Msg(Msg.SUCCESS, "�˹���¼�г���Ϣ�ɹ���", "");
			return MSG;
		} else {
			msg = new Msg(Msg.FAILURE, "���г���Ϣ�Ѵ��ڣ�", "");
			return MSG;
		}
	}

	/**
	 * �����·�
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
	 * ����Aei_no
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
	 * �����޸��г���Ϣ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toUpdateStntrn() throws Exception {
		initOpraMap("EXE_AEI");
		listDicEntprStn = exeDicService.listDicEntprStn(((SysUser) getSessionAttr("sysUserSess")).getCorpid());// ��ҵվ��
		exeStntrn = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		// ��ҳ���Сʱ���Ӹ�ֵ
		String arr_time = StringUtil.dateToString(exeStntrn.getArr_time(), "yyyy-MM-dd HH:mm:ss");
		arr_time_hour = arr_time.substring(11, 13);
		arr_time_min = arr_time.substring(14, 16);
		listAei_no = exeDicService.getAei_noByEntpr(((SysUser) getSessionAttr("sysUserSess")).getCorpid(), exeStntrn
				.getStn_name());
		return "updateStntrn";
	}

	/**
	 * �޸��г���Ϣ
	 * 
	 * @return
	 */
	public String updateStntrn() {
		ExeStntrn stntrnLoad = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		if (exeStntrn.getStn_name().indexOf(":") > -1)
			exeStntrn.setStn_name(exeStntrn.getStn_name().substring(0, exeStntrn.getStn_name().indexOf(":")));// ���վ�����Ѵ��븳��
		// ����Сʱ ����ƴд��������
		String arr_time = StringUtil.getFormatDate(exeStntrn.getArr_time()) + " " + arr_time_hour + ":" + arr_time_min
				+ ":00";
		exeStntrn.setArr_time(StringUtil.StringToDate(arr_time, "yyyy-MM-dd HH:mm:ss"));
		exeStntrn.setStn_entpr(stntrnLoad.getStn_entpr());
		exeStntrn.setMsg_flag(stntrnLoad.getMsg_flag());
		exeStntrn.setMsg_status(stntrnLoad.getMsg_status());
		exeStntrn.setTrain_dir(exeDicService.getTrain_dirByEntprstn(((SysUser) getSessionAttr("sysUserSess"))
				.getCorpid(), exeStntrn.getStn_name(), exeStntrn.getAei_no(), exeStntrn.getIn_out_flag()));
		// ��������������ʶ����������ʶ�𳵡��Ա�����������������ʶ�𳵶�Ĭ�ϣ������޸�
		exeStntrn.setRpt_car_number(stntrnLoad.getRpt_car_number());
		exeStntrn.setCar_number(stntrnLoad.getCar_number());
		exeStntrn.setT_number(stntrnLoad.getT_number());
		exeStntrn.setQ_number(stntrnLoad.getQ_number());
		exeStntrn.setUncar_number(stntrnLoad.getUncar_number());
		exeAeiService.updateStntrn(exeStntrn);
		msg = new Msg(Msg.SUCCESS, "�޸��г���Ϣ�ɹ���", "");
		return MSG;
	}

	/**
	 * �ύ�˹���¼�г���Ϣ
	 * 
	 * @return
	 */
	public String commitStntrn() {
		exeStntrn = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		exeStntrn.setMsg_status("1");// ��Ϣ״̬��ʶ����¼��
		exeAeiService.updateStntrn(exeStntrn);
		msg = new Msg(Msg.SUCCESS, "�ύ�г���Ϣ�ɹ���", "");
		return MSG;
	}

	/**
	 * ɾ���г���Ϣ
	 * 
	 * @return
	 */
	public String deleteStntrn() {
		exeAeiService.deleteStntrn(exeAeiService.loadExeStntrn(exeStntrn.getRptname()));
		msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���");
		return MSG;
	}

	/**
	 * �����ӳ���ҳ��
	 * 
	 * @return
	 */
	public String toAddStncar() {
		initOpraMap("EXE_AEI");
		exeStncar = new ExeStncar();
		exeStntrn = exeAeiService.loadExeStntrn(exeStntrn.getRptname());
		// ��������ֵ
		exeStncar.setTrain_nbr(exeStntrn.getTrain_nbr());
		exeStncar.setStn_name(exeStntrn.getStn_name());
		exeStncar.setArr_time(exeStntrn.getArr_time());
		exeStncar.setUncar_ident("1");// ����ʶ��
		listDicCarkind = exeDicService.listDicCarkind();// ����
		listDicCartype = exeDicService.listDicCartype();// ����
		listDicCarmaker = exeDicService.listDicCarmaker();// ����������ҵ
		return "addStncar";
	}

	/**
	 * ���ӳ���
	 * 
	 * @return
	 */
	public String addStncar() {
		exeStncar.setMsg_flag("1");// ��Ϣ��Դ0-�Զ�ɨ�裻1-�˹��޸Ļ�¼
		int car_position = exeAeiService.getCar_position(exeStncar.getId().getRptname());
		exeStncar.setId(new ExeStncarId(exeStncar.getId().getRptname(), car_position));
		exeStncar.setCar_flag("");
		exeAeiService.saveStncar(exeStncar);
		msg = new Msg(Msg.SUCCESS, "���ӳ�����Ϣ�ɹ���", "");
		return MSG;
	}

	/**
	 * ���޸ĳ�������
	 * 
	 * @return
	 */
	public String toUpdateStncar() {
		initOpraMap("EXE_AEI");
		listDicCarkind = exeDicService.listDicCarkind();// ����
		listDicCartype = exeDicService.listDicCartype();// ����
		listDicCarmaker = exeDicService.listDicCarmaker();// ����������ҵ
		exeStncar = exeAeiService.loadExeStncar(exeStncar.getId().getRptname(), exeStncar.getId().getCar_position());
		return "updateStncar";
	}

	/**
	 * �޸ĳ���
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
		msg = new Msg(Msg.SUCCESS, "�޸ĳ�����Ϣ�ɹ���", "");
		return MSG;
	}

	/**
	 * ɾ������
	 * 
	 * @return
	 */
	public String deleteStncar() {
		exeStncar = exeAeiService.loadExeStncar(exeStncar.getId().getRptname(), exeStncar.getId().getCar_position());
		exeAeiService.deleteStncar(exeStncar);
		msg = new Msg(Msg.SUCCESS, "ɾ��������Ϣ�ɹ���", "");
		return MSG;
	}

	/**
	 * �Ա���������
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
	 * �Ա��������ʲ�ѯ���
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
	 * վ���ֳ��ֲ�
	 */
	public String zqxcDist() {
		listZqxcDist = exeAeiService.getZqxcDist();
		return "zqxcDist";
	}

	/**
	 * վ���ֳ��ֲ���ϸ
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
	 * �����ֳ��ֲ���Ϣ
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
	 * վ����������������Ϣƥ��ͳ�Ʊ�Ĭ�Ͻ���
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
	 * վ����������������Ϣƥ��ͳ�Ʊ���ѯ
	 * 
	 * @return
	 */
	public String zqjcclStatSearch() {
		listZqjccl = exeAeiService.getZqjccl(s_date, e_date, zbc_flag);
		return "zqjcclStat";
	}

	/**
	 * վ����������������Ϣƥ����ϸ
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
	 * վ��AEI�豸���
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
	 * ר����ͣ��ʱ��
	 * 
	 * @return
	 */
	public String zyxFrame() {
		return "zyxFrame";

	}

	/**
	 * ר����ͣ��ʱ���ѯ����
	 * 
	 * @return
	 */
	public String zyxLeft() {
		listDicAreaCorp = exeDicService.listDicAreacorp("dataAutho");// ����
		// listDicSinocorp = exeDicService.listDicSinocorp("dataAutho");// ��˾
		listDicSinocorp = exeDicService.listZqqy(2);// ��˾
		return "zyxLeft";
	}

	/**
	 * ר����ͣ��վĬ�ϲ�ѯ����
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
	 * ר����ͣ��վ��ѯ
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
	 * ר����ͣ��վ ȡ����ϸ
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
