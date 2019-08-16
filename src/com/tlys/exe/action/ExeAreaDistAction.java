package com.tlys.exe.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.service.ExeAreaDistService;
import com.tlys.exe.service.ExeAreaDistVO;

/**
 * ����ֲ�Action
 * 
 * @author �״���
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "search", location = "/exe/exe-areaDist-main.jsp"),
		@Result(name = "detail", location = "/exe/exe-areaDist-detail.jsp"),
		@Result(name = "inteUse", location = "/exe/exe-areaDist-inteUse.jsp") })
public class ExeAreaDistAction extends _BaseAction {

	@Autowired
	private ExeAreaDistService areaDistService;

	private List<ExeAreaDistVO> listAreaDist;// �����ѯ����б�

	private List<ExeDcarStat> listExeDcarStat;// �����ѯ��ϸ��Ϣ�б�

	private int[] rowspan;// ��������˾�������ӹ�˾��

	
	/**
	 * ����Ȩ�޿���
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_AREA");
	}
	
	public int[] getRowspan() {
		return rowspan;
	}

	public void setRowspan(int[] rowspan) {
		this.rowspan = rowspan;
	}

	public List<ExeAreaDistVO> getListAreaDist() {
		return listAreaDist;
	}

	public void setListAreaDist(List<ExeAreaDistVO> listAreaDist) {
		this.listAreaDist = listAreaDist;
	}

	private String stat_date;// �����ѯ����
	private int stat_point;// �����ѯʱ���
	private String le_code;// �ؿ�

	public String getLe_code() {
		return le_code;
	}

	public void setLe_code(String le_code) {
		this.le_code = le_code;
	}

	public void setStat_point(int stat_point) {
		this.stat_point = stat_point;
	}

	public int getStat_point() {
		return stat_point;
	}

	public String getStat_date() {
		return stat_date;
	}

	public void setStat_date(String stat_date) {
		this.stat_date = stat_date;
	}

	/**
	 * �����ѯ���棬�и�Ĭ�ϲ�ѯ���
	 * 
	 * @return
	 */
	public String search() {
		initOpraMap("EXE_AREA");
		stat_date = StringUtil.getSystemDate();
		le_code = "3";
		stat_point = calStat_point();
		listAreaDist = areaDistService.listAreaDist(stat_date, stat_point, le_code);
		rowspan = areaDistService.getListRowspan();
		getRequest().setAttribute("link", "Y");
		return "search";
	}

	public String searchResult() {
		initOpraMap("EXE_AREA");
		String link = "N";// �����Ƿ�������
		if (stat_date.equals(StringUtil.getSystemDate()) && stat_point == calStat_point())
			link = "Y";
		getRequest().setAttribute("link", link);
		listAreaDist = areaDistService.listAreaDist(stat_date, stat_point, le_code);
		rowspan = areaDistService.getListRowspan();
		return "search";
	}

	public String getDetail() {
		String cur_adm = getRequest().getParameter("cur_adm");
		String flag = getRequest().getParameter("flag");// ����ҵ������������A����ҵC
		String car_user = getRequest().getParameter("car_user");// ��ҵ�����������
		String area = getRequest().getParameter("area");// ȡ��������
		listExeDcarStat = areaDistService.getAreaDetail(flag, area, car_user, le_code, cur_adm);
		return "detail";
	}

	/**
	 * �ۺ�������
	 * 
	 * @return
	 */
	public String inteUse() {
		return "inteUse";
	}

	/**
	 * ���ݵ�ǰ���ڼ���ͳ��ʱ���
	 * 
	 * @return
	 */
	private int calStat_point() {
		int hour = Integer.parseInt(StringUtil.dateToString(new Date(), "HH"));
		return hour / 1 * 1;
	}

	public static void main(String[] args) {
		ExeAreaDistAction action = new ExeAreaDistAction();
		System.out.println(action.calStat_point());
	}

	public List<ExeDcarStat> getListExeDcarStat() {
		return listExeDcarStat;
	}

	public void setListExeDcarStat(List<ExeDcarStat> listExeDcarStat) {
		this.listExeDcarStat = listExeDcarStat;
	}
}
