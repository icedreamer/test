package com.tlys.exe.action;

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
import com.tlys.exe.model.ExeGcarEvta;
import com.tlys.exe.service.ExeDcarStatService;
import com.tlys.exe.service.ExeGcarEvtaService;

/**
 * �����켣��ѯAction
 * 
 * @author �״���
 * 
 */

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
@Results( { @Result(name = "search", location = "/exe/exe-gcarEvta-search.jsp"),
		@Result(name = "frame", location = "/exe/exe-gcarEvta-frame.jsp"),
		@Result(name = "left", location = "/exe/exe-gcarEvta-left.jsp"),
		@Result(name = "main", location = "/exe/exe-gcarEvta-main.jsp") })
public class ExeGcarEvtaAction extends _BaseAction {

	// ע��service
	@Autowired
	ExeGcarEvtaService gcarEvtaService;

	@Autowired
	ExeDcarStatService dcarStatService;

	/**
	 * ����Ȩ�޿���
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("EXE_GCAR");
	}
	
	public ExeDcarStatService getDcarStatService() {
		return dcarStatService;
	}

	public void setDcarStatService(ExeDcarStatService dcarStatService) {
		this.dcarStatService = dcarStatService;
	}

	public ExeGcarEvtaService getGcarEvtaService() {
		return gcarEvtaService;
	}

	public void setGcarEvtaService(ExeGcarEvtaService gcarEvtaService) {
		this.gcarEvtaService = gcarEvtaService;
	}

	// �����ѯ�������
	private List<ExeGcarEvta> listGcarEvta;// �����ѯ����б�
	private ExeGcarEvta gCarEvta;
	private ExeDcarStat dcarStat;// ���峵����ϸ��Ϣ

	public ExeDcarStat getDcarStat() {
		return dcarStat;
	}

	public void setDcarStat(ExeDcarStat dcarStat) {
		this.dcarStat = dcarStat;
	}

	public List<ExeGcarEvta> getListGcarEvta() {
		return listGcarEvta;
	}

	public void setListGcarEvta(List<ExeGcarEvta> listGcarEvta) {
		this.listGcarEvta = listGcarEvta;
	}

	// �����ѯ�ֶ�
	private String car_no;
	private String s_date;
	private String e_date;

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
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
		e_date = StringUtil.getSystemDate();
		s_date = StringUtil.getOptSystemDate(-30);
		return "left";
	}

	/**
	 * ���main����
	 * 
	 * @return
	 */
	public String main() {
		return "main";
	}

	/**
	 * �����ѯ����
	 * 
	 * @return
	 */
	public String search() {
		initOpraMap("EXE_GCAR");
		return "search";
	}

	/**
	 * ��ʾ��ѯ���
	 * 
	 * @return
	 */
	public String searchResult() {
		initOpraMap("EXE_GCAR");
		if (car_no.length() == 7) {
			if (e_date == null || e_date.equals("")) {
				e_date = StringUtil.getSystemDate();
				s_date = StringUtil.getOptSystemDate(-30);
			}
			listGcarEvta = gcarEvtaService.listGcarEvta(car_no, s_date, e_date);
			dcarStat = dcarStatService.loadExeDcarStat(car_no);
		}
		return "main";
	}

	public ExeGcarEvta getGCarEvta() {
		return gCarEvta;
	}

	public void setGCarEvta(ExeGcarEvta carEvta) {
		gCarEvta = carEvta;
	}

}
