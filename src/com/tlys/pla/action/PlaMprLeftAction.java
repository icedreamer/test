package com.tlys.pla.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.bi.service.BiDataService;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.FormatUtil;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicRwstation;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.ctl.CtlCorpsender;
import com.tlys.dic.service.DicRwstationService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.service.ExeDicService;
import com.tlys.pla.service.PlaMuprtrainService;

/**
 * ��Action���������ɼƻ������󵼺�ҳ�棬��Ҫ��������Ȩ��
 * 
 * @author fengym
 * 
 */

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMprLeftAction extends _BaseAction {
	private static final long serialVersionUID = 431299588520414901L;
	protected final Log log = LogFactory.getLog(this.getClass());

	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicAreacorp> dicAreacorpMap;
	
	private List<DicRwstation> dicRwstations;
	private List<CtlCorpsender> corpsenders;

	private String areaid;
	private String corpid;

	private String monthStr;
	
	private String term;// ��վ�����Զ����ȡֵ

	@Autowired
	PlaMuprtrainService plaMuprtrainService;

	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	DicRwstationService dicRwstationService;
	
	
	@Autowired
	ExeDicService dicService;

	@Autowired
	DicMap dicMap;
	
	@Autowired
	BiDataService biDataService;
	
	
	public String list() throws Exception {
		monthStr = FormatUtil.nowDateStr(9);
		
		
		//getCurUser();

		//String curCorpid = curUser.getCorpid();
		//String curCorptab = curUser.getCorptab();
		//String schAreaid = plaMuprtrainService.bdAreaid(curCorpid, curCorptab);
		//String schCorpid = plaMuprtrainService.bdCorpid(curCorpid, curCorptab);

		// ������Ե�ǰ��ѯ�������ƣ���areaid=null,corpid=null
		//areaid = schAreaid;
		//corpid = schCorpid;
		
		
		/**
		 * ����ͳһ��Ȩ�޽�������corpidArrΪnullʱ����ʾ�û�ӵ��ȫ��Ȩ��
		 */
		Object[] corpidArr = CommUtils.getCorpId();
		
		if(null == corpidArr){
			dicSinocorpMap = dicMap.getCorpMap(); 
		}else{
			dicSinocorpMap = new HashMap();
			Map corpMapGen = dicMap.getCorpMap();
			for (int i = 0; i < corpidArr.length; i++) {
				DicSinocorp corp = (DicSinocorp)corpMapGen.get(corpidArr[i]);
				if(null!=corp){
					dicSinocorpMap.put((String)corpidArr[i], corp);
				}
			}
		}
		
		//��areaidΪnullʱ��ȡ��ȫ����ҵ
		/*
		dicSinocorpMap = dicMap.getAreaCorpMap(areaid);
		if(null != corpid){
			DicSinocorp curCorp = dicSinocorpMap.get(corpid);
			dicSinocorpMap = new HashMap();
			dicSinocorpMap.put(corpid, curCorp);
		}
		*/
		

		return "left";
	}
	
	/**
	 * ��վ�Զ���ɣ�ֻ������վ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getStation() throws Exception {
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




	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public Map<String, DicAreacorp> getDicAreacorpMap() {
		return dicAreacorpMap;
	}

	public void setDicAreacorpMap(Map<String, DicAreacorp> dicAreacorpMap) {
		this.dicAreacorpMap = dicAreacorpMap;
	}

	public List<DicRwstation> getDicRwstations() {
		return dicRwstations;
	}

	public void setDicRwstations(List<DicRwstation> dicRwstations) {
		this.dicRwstations = dicRwstations;
	}

	public List<CtlCorpsender> getCorpsenders() {
		return corpsenders;
	}

	public void setCorpsenders(List<CtlCorpsender> corpsenders) {
		this.corpsenders = corpsenders;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	/**
	 * 
	 */
	public void prepare() throws Exception {
		initOpraMap("PLA_MUPR");
	}

}
