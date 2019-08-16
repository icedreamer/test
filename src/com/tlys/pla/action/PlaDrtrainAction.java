package com.tlys.pla.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicRwdepartmentService;
import com.tlys.pla.model.PlaDrtrain;
import com.tlys.pla.service.PlaDrtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDrtrainAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2983697538484997977L;
	private String monthStr;
	private PlaDrtrain plaDrtrain;
	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicAreacorp> dicAreaMap;
	private Map<String, DicCarkind> dicCarkindMap;
	private Map<String, DicGoodscategory> dicGoodsMap;
	private Map<String, String> dicRwBureauMap;
	private Map<String, String> rwdepartmentMap;
	// 导出条件
	private String schobjkey;
	private String month;
	// 拼音码
	private String term;
	@Autowired
	private PlaDrtrainService plaDrtrainService;
	@Autowired
	private DicRwdepartmentService dicRwdepartmentService;
	@Autowired
	private DicMap dicMap;

	public String list() throws Exception {

		String pageUrl = "/pla/pla-drtrain!list.action";
		if (null == plaDrtrain) {
			plaDrtrain = new PlaDrtrain();
		}
		String month = plaDrtrain.getMonth();
		if (null == month || "".equals(month)) {
			month = CommUtils.shortMonthFormat(new Date());
			plaDrtrain.setMonth(month);
		}
		// boolean hasGen = plaDrtrainService.hasGen(month);
		// if (logger.isDebugEnabled()) {
		// logger.debug("hasGen : " + hasGen);
		// }
		// if (!hasGen) {
		// if (null != pageCtr && null != pageCtr.getSchObjKey() &&
		// !"".equals(pageCtr.getSchObjKey()) ) {
		//
		// }
		// }
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			String schObjKey = "plaDrtrain_" + new Date().getTime();
			pageCtr.setSchObjKey(schObjKey);
			setSessionAttr(schObjKey, plaDrtrain);
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaDrtrain = (PlaDrtrain) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		plaDrtrainService.findPlaDrtrain(plaDrtrain, pageCtr);
		dicSinocorpMap = dicMap.getCorpAllMap();
		dicAreaMap = dicMap.getAreaMap();
		dicGoodsMap = dicMap.getGoodscategoryMap();
		dicCarkindMap = dicMap.getCarkindMap();
		dicRwBureauMap = dicMap.getRwbureauMap();
		rwdepartmentMap = dicMap.getRwdepartmentMap();
		return "list";
	}

	public String Call() throws Exception {
		long start = System.currentTimeMillis();
		if (month == null || month.equals("")) {
			month = CommUtils.shortMonthFormat(new Date());
		}
		Object[] o = plaDrtrainService.call_P_ZBC_PLAN_GENDRTRAIN(month);
		long end = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("month : " + month);
			logger.debug("call P_ZBC_PLAN_GENDRTRAIN 状态：" + o[0]);
			logger.debug("call P_ZBC_PLAN_GENDRTRAIN 描述：" + o[1]);
			logger.debug("call P_ZBC_PLAN_GENDRTRAIN in " + (end - start) + " ms.");
		}

		msg = new Msg(Msg.SUCCESS, "获取成功");
		return MSG;
	}

	public String detail() throws Exception {

		plaDrtrain = plaDrtrainService.load(plaDrtrain.getId());
		dicSinocorpMap = dicMap.getCorpAllMap();
		dicAreaMap = dicMap.getAreaMap();
		dicGoodsMap = dicMap.getGoodscategoryMap();
		dicCarkindMap = dicMap.getCarkindMap();
		dicRwBureauMap = dicMap.getRwbureauMap();
		rwdepartmentMap = dicMap.getRwdepartmentMap();
		return "detail";
	}

	public String left() throws Exception {
		monthStr = CommUtils.shortMonthFormat(new Date());

		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);

		return "left";
	}

	public String export() throws Exception {
		plaDrtrain = (PlaDrtrain) getSessionAttr(schobjkey);
		HSSFWorkbook workbook = new HSSFWorkbook();
		plaDrtrainService.exportToExcel(workbook, plaDrtrain, getCurUser());

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * 车站自动完成
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getStation() throws Exception {
		ActionContext context = ServletActionContext.getContext();
		context.getActionInvocation().getProxy().setExecuteResult(false);
		HttpServletResponse response = ServletActionContext.getResponse();
		if (logger.isDebugEnabled()) {
			logger.debug("term : " + term);
		}
		List<DicRwdepartment> rwdepartments = dicRwdepartmentService.findRwDepartment(term, "01");
		StringBuffer buffer = new StringBuffer("");
		buffer.append("[");
		if (null != rwdepartments && !rwdepartments.isEmpty()) {
			for (int i = 0; i < rwdepartments.size(); i++) {
				DicRwdepartment dicRwdepartment = rwdepartments.get(i);
				buffer.append("{");
				buffer.append("\"label\" : ").append("\"").append(dicRwdepartment.getShortname()).append("\"")
						.append(",");
				buffer.append("\"id\" : ").append("\"").append(dicRwdepartment.getRwdepaid()).append("\"");
				buffer.append("}");
				if (i + 1 != rwdepartments.size()) {
					buffer.append(",");
				}
			}
		}
		buffer.append("]");
		String sb = buffer.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("rw : " + sb);
		}

		// StringBuffer sb = new StringBuffer("");
		// sb.append("[");
		// for (DicRwdepartment rw : rwdepartments) {
		// sb.append("\"").append(rw.getShortname()).append("\",");
		// }
		// String str = sb.toString();
		// if (str.length() > 1)
		// str = str.substring(0, str.length() - 1);
		// // out(ServletActionContext.getResponse(), str + "]");
		// CommUtils.flushData(str + "]", response);
		CommUtils.flushData(sb, response);
		return null;
	}

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public PlaDrtrain getPlaDrtrain() {
		return plaDrtrain;
	}

	public void setPlaDrtrain(PlaDrtrain plaDrtrain) {
		this.plaDrtrain = plaDrtrain;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public Map<String, DicAreacorp> getDicAreaMap() {
		return dicAreaMap;
	}

	public Map<String, DicCarkind> getDicCarkindMap() {
		return dicCarkindMap;
	}

	public Map<String, DicGoodscategory> getDicGoodsMap() {
		return dicGoodsMap;
	}

	public void setDicAreaMap(Map<String, DicAreacorp> dicAreaMap) {
		this.dicAreaMap = dicAreaMap;
	}

	public void setDicCarkindMap(Map<String, DicCarkind> dicCarkindMap) {
		this.dicCarkindMap = dicCarkindMap;
	}

	public void setDicGoodsMap(Map<String, DicGoodscategory> dicGoodsMap) {
		this.dicGoodsMap = dicGoodsMap;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("PLA_DRTRAIN");
	}

	public Map<String, String> getDicRwBureauMap() {
		return dicRwBureauMap;
	}

	public void setDicRwBureauMap(Map<String, String> dicRwBureauMap) {
		this.dicRwBureauMap = dicRwBureauMap;
	}

	public Map<String, String> getRwdepartmentMap() {
		return rwdepartmentMap;
	}

	public void setRwdepartmentMap(Map<String, String> rwdepartmentMap) {
		this.rwdepartmentMap = rwdepartmentMap;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
