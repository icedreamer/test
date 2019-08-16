package com.tlys.equ.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.equ.model.EquRepMplan;
import com.tlys.equ.model.EquRepYplan;
import com.tlys.equ.service.EquRepPlanService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquRepPlanAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4596866549543562142L;
	private EquRepMplan equRepMplan;
	private EquRepYplan equRepYplan;
	private Map<String, Long> amountByCorpidAndRtypeidAndDatatypeMap;
	private Map<String, Long> amountByRtypeidAndMonthMap;
	private Map<String, Long> amountByCorpidRtypeidMap;
	private Map<String, Long> totalAmountMap;
	private List<Object[]> corpList;
	private DicSinocorp dicSinocorp;
	private DicAreacorp dicAreacorp;
	private Map<String, DicAreacorp> dicAreacorpMap;
	private List<DicSinocorp> dicSinocorps;
	private String corps;

	@Autowired
	EquRepPlanService equRepPlanService;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;

	public String list1() throws Exception {
		Calendar calendar = Calendar.getInstance();
		// if (null == year || "".equals(year)) {
		// year = String.valueOf(calendar.get(Calendar.YEAR));
		// }
		String year = "2012";
		Object[] o = equRepPlanService.call_P_ZBC_REP_GENPLAN(year);
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENPLAN ×´Ì¬£º" + o[0]);
			logger.debug("call P_ZBC_REP_GENPLAN ÃèÊö£º" + o[1]);
		}
		String[] corpids = null;
		if (null == corps || "".equals(corps)) {
			corpList = equRepPlanService.findCorps(equRepYplan);
			if (null != corpList && !corpList.isEmpty()) {
				corpids = new String[corpList.size()];
				for (int i = 0; i < corpList.size(); i++) {
					Object[] corpObj = corpList.get(i);
					corpids[i] = (null == corpObj ? "" : corpObj[0].toString());
				}
			}
		} else {
			corpids = corps.split(",");
		}
		if (null == equRepMplan) {
			equRepMplan = new EquRepMplan();
		}
		equRepMplan.setCorpids(corpids);
		List<Object[]> equRepPlans = equRepPlanService.findEquRepMplan(equRepMplan);
		if (null != equRepPlans && !equRepPlans.isEmpty()) {
			amountByCorpidAndRtypeidAndDatatypeMap = new HashMap<String, Long>();
			for (Object[] objects : equRepPlans) {
				// a.Corpid, a.Corpshortname, a.Rtypeid, a.Datatype,
				// Sum(a.Amount)
				String corpid = objects[0].toString();
				String rtypeid = objects[2].toString();
				String datatype = objects[3].toString();
				Long data = Long.valueOf(objects[4].toString());
				String key = CommUtils.getString(corpid, "_", rtypeid, "_", datatype);
				Long amount = amountByCorpidAndRtypeidAndDatatypeMap.get(key);
				if (null == amount) {
					amount = 0l;
				}
				amount += data;
				amountByCorpidAndRtypeidAndDatatypeMap.put(key, amount);
			}
		}

		List<Object[]> totalList = equRepPlanService.findTotalAmountByRtypeidAndDatatype(equRepMplan);
		if (null != totalList && !totalList.isEmpty()) {
			totalAmountMap = new HashMap<String, Long>();
			for (Object[] objects : totalList) {
				String key = CommUtils.getString(objects[0], "_", objects[1]);
				Long data = Long.valueOf(null == objects[2] ? "0" : objects[2].toString());
				Long total = totalAmountMap.get(key);
				if (null == total) {
					total = 0l;
				}
				totalAmountMap.put(key, total + data);
			}
		}
		return "list";
	}

	public String years() throws Exception {
		if (null == equRepYplan) {
			equRepYplan = new EquRepYplan();
		}

		corpList = equRepPlanService.findCorps(equRepYplan);
		List<Object[]> equRepPlans = equRepPlanService.findEquRepYplan(equRepYplan);
		if (null != equRepPlans && !equRepPlans.isEmpty()) {
			amountByCorpidRtypeidMap = new HashMap<String, Long>();
			for (Object[] objects : equRepPlans) {
				// a.Corpid, a.Corpshortname, a.Rtypeid,Sum(a.Amount)
				String corpid = objects[0].toString();
				String rtypeid = objects[2].toString();
				Long data = Long.valueOf(objects[3].toString());
				String key = CommUtils.getString(corpid, "_", rtypeid);
				Long amount = amountByCorpidRtypeidMap.get(key);
				if (null == amount) {
					amount = 0l;
				}
				amount += data;
				amountByCorpidRtypeidMap.put(key, amount);
			}
		}

		List<Object[]> totalList = equRepPlanService.findTotalAmountByRtypeid(equRepYplan);
		if (null != totalList && !totalList.isEmpty()) {
			totalAmountMap = new HashMap<String, Long>();
			for (Object[] objects : totalList) {

				String rtypeid = null == objects[0] ? "" : objects[0].toString();
				Long data = Long.valueOf(null == objects[1] ? "0" : objects[1].toString());
				Long total = totalAmountMap.get(rtypeid);
				if (null == total) {
					total = 0l;
				}
				totalAmountMap.put(rtypeid, total + data);
			}
		}
		dicAreacorpMap = dicMap.getAreaMap();
		return "years";
	}

	public String months() throws Exception {
		if (null == pageCtr) {
			pageCtr = new PageCtr<Object[]>();
			if (null != equRepMplan) {
				String schObjKey = "equ_rep_plan_months_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equRepMplan);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				equRepMplan = (EquRepMplan) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		String pageUrl = "/equ/equ-rep-plan!months.action";
		pageCtr.setPageSize(15);
		pageCtr.setPageUrl(pageUrl);
		if (logger.isDebugEnabled()) {
			logger.debug("equRepMplan : " + equRepMplan);
		}
		equRepPlanService.findMonths(equRepMplan, pageCtr);
		List<Object[]> equRepPlans = equRepPlanService.findMonthsPlan(equRepMplan);
		if (null != equRepPlans && !equRepPlans.isEmpty()) {
			amountByRtypeidAndMonthMap = new HashMap<String, Long>();
			for (Object[] objects : equRepPlans) {
				// a.corpid,a.corpshortname, a.Rtypeid, a.Datatype,a.month,
				// Sum(a.Amount)
				String corpid = objects[0].toString();
				String corpshortname = objects[1].toString();
				String rtypeid = objects[2].toString();
				String datatype = objects[3].toString();
				String month = objects[4].toString();
				Long data = Long.valueOf(objects[5].toString());
				String key = CommUtils.getString(month, "_", rtypeid, "_", datatype);
				Long amount = amountByRtypeidAndMonthMap.get(key);
				if (null == amount) {
					amount = 0l;
				}
				amount += data;
				amountByRtypeidAndMonthMap.put(key, amount);
			}
		}
		List<Object[]> totalList = equRepPlanService.findTotalAmountByRtypeidAndDatatype(equRepMplan);
		if (null != totalList && !totalList.isEmpty()) {
			totalAmountMap = new HashMap<String, Long>();
			for (Object[] objects : totalList) {
				String datatype = objects[1].toString();
				String key = CommUtils.getString(objects[0], "_", objects[1]);
				Long data = Long.valueOf(null == objects[2] ? "0" : objects[2].toString());
				if (null != datatype && !"EP".equals(datatype.trim())) {
					Long total = totalAmountMap.get(key);
					if (null == total) {
						total = 0l;
					}
					totalAmountMap.put(key, total + data);
				}
			}
		}
		dicSinocorp = dicSinocorpService.load(equRepMplan.getCorpid());
		dicAreacorpMap = dicMap.getAreaMap();
		dicAreacorp = dicAreacorpMap.get(dicSinocorp.getAreaid());
		return "months";
	}

	public String years1() throws Exception {
		if (null == pageCtr) {
			pageCtr = new PageCtr<String>();
			if (null != equRepYplan) {
				String schObjKey = "equ_rep_plan_years_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equRepYplan);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				equRepYplan = (EquRepYplan) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		String pageUrl = "/equ/equ-rep-plan!years.action";
		pageCtr.setPageUrl(pageUrl);
		equRepPlanService.findYears(equRepYplan, pageCtr);
		List<Object[]> equRepPlans = equRepPlanService.findYearsPlan(equRepYplan);
		if (null != equRepPlans && !equRepPlans.isEmpty()) {
			amountByRtypeidAndMonthMap = new HashMap<String, Long>();
			for (Object[] objects : equRepPlans) {
				// a.corpid,a.corpshortname, a.Rtypeid, a.year,Sum(a.Amount)
				String rtypeid = objects[2].toString();
				String year = objects[3].toString();
				Long data = Long.valueOf(objects[4].toString());
				String key = CommUtils.getString(year, "_", rtypeid);
				Long amount = amountByRtypeidAndMonthMap.get(key);
				if (null == amount) {
					amount = 0l;
				}
				amount += data;
				amountByRtypeidAndMonthMap.put(key, amount);
			}
		}
		List<Object[]> totalList = equRepPlanService.findTotalAmountByRtypeid(equRepYplan);
		if (null != totalList && !totalList.isEmpty()) {
			totalAmountMap = new HashMap<String, Long>();
			for (Object[] objects : totalList) {
				String rtypeid = null == objects[0] ? "" : objects[0].toString();
				Long data = Long.valueOf(null == objects[1] ? "0" : objects[1].toString());
				Long total = totalAmountMap.get(rtypeid);
				if (null == total) {
					total = 0l;
				}
				totalAmountMap.put(rtypeid, total + data);
			}
		}
		dicSinocorp = dicSinocorpService.load(equRepYplan.getCorpid());
		return "years";
	}

	public Map<String, Long> getAmountByRtypeidAndMonthMap() {
		return amountByRtypeidAndMonthMap;
	}

	public void setAmountByRtypeidAndMonthMap(Map<String, Long> amountByRtypeidAndMonthMap) {
		this.amountByRtypeidAndMonthMap = amountByRtypeidAndMonthMap;
	}

	public List<Object[]> getCorpList() {
		return corpList;
	}

	public void setCorpList(List<Object[]> corpList) {
		this.corpList = corpList;
	}

	public Map<String, Long> getAmountByCorpidAndRtypeidAndDatatypeMap() {
		return amountByCorpidAndRtypeidAndDatatypeMap;
	}

	public void setAmountByCorpidAndRtypeidAndDatatypeMap(Map<String, Long> amountByCorpidAndRtypeidAndDatatypeMap) {
		this.amountByCorpidAndRtypeidAndDatatypeMap = amountByCorpidAndRtypeidAndDatatypeMap;
	}

	public DicSinocorp getDicSinocorp() {
		return dicSinocorp;
	}

	public void setDicSinocorp(DicSinocorp dicSinocorp) {
		this.dicSinocorp = dicSinocorp;
	}

	public Map<String, Long> getTotalAmountMap() {
		return totalAmountMap;
	}

	public void setTotalAmountMap(Map<String, Long> totalAmountMap) {
		this.totalAmountMap = totalAmountMap;
	}

	public String getCorps() {
		return corps;
	}

	public void setCorps(String corps) {
		this.corps = corps;
	}

	public EquRepMplan getEquRepMplan() {
		return equRepMplan;
	}

	public EquRepYplan getEquRepYplan() {
		return equRepYplan;
	}

	public void setEquRepMplan(EquRepMplan equRepMplan) {
		this.equRepMplan = equRepMplan;
	}

	public void setEquRepYplan(EquRepYplan equRepYplan) {
		this.equRepYplan = equRepYplan;
	}

	public Map<String, Long> getAmountByCorpidRtypeidMap() {
		return amountByCorpidRtypeidMap;
	}

	public void setAmountByCorpidRtypeidMap(Map<String, Long> amountByCorpidRtypeidMap) {
		this.amountByCorpidRtypeidMap = amountByCorpidRtypeidMap;
	}

	public Map<String, DicAreacorp> getDicAreacorpMap() {
		return dicAreacorpMap;
	}

	public void setDicAreacorpMap(Map<String, DicAreacorp> dicAreacorpMap) {
		this.dicAreacorpMap = dicAreacorpMap;
	}

	public DicAreacorp getDicAreacorp() {
		return dicAreacorp;
	}

	public void setDicAreacorp(DicAreacorp dicAreacorp) {
		this.dicAreacorp = dicAreacorp;
	}

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

	public void setDicSinocorps(List<DicSinocorp> dicSinocorps) {
		this.dicSinocorps = dicSinocorps;
	}

}
