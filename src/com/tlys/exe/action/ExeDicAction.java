package com.tlys.exe.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicOverTime;
import com.tlys.dic.model.DicRwstation;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.service.ExeDicService;
import com.tlys.sys.model.SysUser;

/**
 * �ֵ��ѯAction�����ڸ�������ģ����ֵ����
 * 
 * @author �״���
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/exe")
public class ExeDicAction extends _BaseAction {
	@Autowired
	ExeDicService dicService;

	// �������ʼ���յ�ƴ���롢
	private String mask;
	private String term;// ��վ�����Զ����ȡֵ

	/**
	 * ����ʼ���յ�վ
	 * 
	 * @return
	 */
	public String filterRwstation() {
		List<DicRwstation> listRwstation = dicService.filterRwstation(mask.toUpperCase());
		String str = "<?xml version=\"1.0\" ?><complete>";
		for (DicRwstation rw : listRwstation) {
			str += "<option value=\"" + rw.getStationid() + "\">" + rw.getStationpycode() + ":" + rw.getShortname()
					+ "</option>";
		}
		str += "</complete>";
		this.out(ServletActionContext.getResponse(), str);
		return null;
	}

	/**
	 * ����Ʒ���ѯƷ��
	 * 
	 * @return
	 */
	public String getCdy_codeByCdy_type() {
		List<DicGoods> listDicGoods = dicService.listDicGoodsByCdy_type(getRequest().getParameter("cdy_type"));
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (DicGoods goods : listDicGoods) {
			sb.append("{\"dm\":\"").append(goods.getDm()).append("\",\"pmhz\":\"").append(goods.getPmhz()).append(
					"\"},");
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		str = str + "]";
		out(ServletActionContext.getResponse(), str);
		return null;
	}

	/**
	 * ��վ�Զ���ɣ���վ����:��վ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getCz() throws Exception {
		List<DicRwstation> list = dicService.filterRwstation(StringUtil.decodeUrl(term));
		StringBuffer sb = new StringBuffer("");
		sb.append("[");
		for (DicRwstation rw : list) {
			sb.append("\"").append(rw.getTelegramid()).append(":").append(rw.getShortname()).append("\",");
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		out(ServletActionContext.getResponse(), str + "]");
		return null;
	}

	/**
	 * ��վ�Զ���ɣ�ֻ������վ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getCz2() throws Exception {
		List<DicRwstation> list = dicService.filterRwstation(StringUtil.decodeUrl(term));
		StringBuffer sb = new StringBuffer("");
		sb.append("[");
		for (DicRwstation rw : list) {
			sb.append("\"").append(rw.getShortname()).append("\",");
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		out(ServletActionContext.getResponse(), str + "]");
		return null;
	}

	/**
	 * Ʒ���Զ���ɣ�����ƴ����ͺ���
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDicGoods() throws Exception {
		List<DicGoods> list = dicService.listDicGoodsByPymAndHz(StringUtil.decodeUrl(term));
		StringBuffer sb = new StringBuffer("");
		sb.append("[");
		for (DicGoods dg : list) {
			sb.append("\"").append(dg.getDm()).append(":").append(dg.getPmhz()).append("\",");
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		out(ServletActionContext.getResponse(), str + "]");
		return null;
	}

	/**
	 * ����ƴ�������ģ����ѯƷ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDicGoods2() throws Exception {
		List<DicGoods> list = dicService.listDicGoodsByPymAndHz(getRequest().getParameter("cdy_code"));
		StringBuffer sb = new StringBuffer("");
		sb.append("[");
		for (DicGoods goods : list) {
			sb.append("{\"dm\":\"").append(goods.getDm()).append("\",\"pmhz\":\"").append(goods.getPmhz()).append(
					"\"},");
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		out(ServletActionContext.getResponse(), str + "]");
		return null;
	}

	/**
	 * ����Ԥ����ʶ��ѯ��ʱ����
	 * 
	 * @return
	 */
	public String getOverIdByOver_type() {
		List<DicOverTime> listDicOverTime = dicService.listDicOverTime(getRequest().getParameter("over_type"));
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (DicOverTime overTime : listDicOverTime) {
			sb.append("{\"over_id\":\"").append(overTime.getOver_id()).append("\",\"over_time\":\"").append(
					overTime.getOver_time()).append("\"},");
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		str = str + "]";
		out(ServletActionContext.getResponse(), str);
		return null;
	}

	/**
	 * ����վ�����Ʋ�Aei_no
	 * 
	 * @return
	 */
	public String getAei_noByEntpr_stn() {
		List<Integer> list = dicService.getAei_noByEntpr(((SysUser) getSessionAttr("sysUserSess")).getCorpid(),
				getRequest().getParameter("entpr_stn_code"));
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (Integer i : list) {
			sb.append("{\"code\":\"").append(i).append("\"},");
		}
		String str = sb.toString();
		if (str.length() > 1)
			str = str.substring(0, str.length() - 1);
		str = str + "]";
		out(ServletActionContext.getResponse(), str);
		return null;
	}

	/**
	 * Servlet��ӡ�ַ���
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

	public void setMask(String mask) {
		this.mask = mask;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getMask() {
		return mask;
	}

	public String getTerm() {
		return term;
	}
}
