package com.tlys.pla.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DateCalendar;
import com.tlys.comm.util.DicMap;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;

/**
 * 本Action类用于生成计划管理左导航页面，主要用来控制权限
 * 
 * @author fengym
 * 
 */

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaLeft2Action extends _BaseAction {
	private static final long serialVersionUID = 431299588520414901L;

	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicAreacorp> dicAreacorpMap;
	// 统销非统销标识
	private String mps;
	private String stauss;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;
	String monthStart;
	String monthEnd;

	public String left() throws Exception {
		Object[] corpidArr = CommUtils.getCorpId();
		if (null == corpidArr) {
			dicSinocorpMap = dicMap.getCorpMap();
		} else {
			dicSinocorpMap = new LinkedHashMap();
			Map corpMapGen = dicMap.getCorpMap();
			for (int i = 0; i < corpidArr.length; i++) {
				DicSinocorp corp = (DicSinocorp) corpMapGen.get(corpidArr[i]);
				if (null != corp) {
					dicSinocorpMap.put((String) corpidArr[i], corp);
				}
			}
		}
//		dicAreacorpMap = getAreaidMap();
		dicAreacorpMap = CommUtils.getUserAreaMap(getCurUser(), dicMap);
		
		if ("msps".equals(mps)) {//非统销
			stauss = dicMap.getStatuss(null, "PLA_MSPS2");
		} else if ("mups".equals(mps)) {
			stauss = dicMap.getStatuss(null, "PLA_MUPS2");
		}
		
		String monthNext = DateCalendar.getIMonth("", 1, "yyyy-MM");
		monthStart = DateCalendar.getIMonth(monthNext, -12, "yyyy-MM");
		monthEnd = monthNext;
		
		return "left2";
	}

	public Map<String, DicAreacorp> getAreaidMap() throws Exception {
		Object[] corpidArr = CommUtils.getCorpId();
		Map corpMap = dicMap.getCorpMap();
		Map areaMap = dicMap.getAreaMap();
		Map<String, DicAreacorp> map = new LinkedHashMap();
		if (null == corpidArr || corpidArr.length == 0) {// 拥有全部权限
			map = areaMap;
		} else {
			for (int i = 0; i < corpidArr.length; i++) {
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpidArr[i]);
				if (null != corp) {
					String areaid = corp.getAreaid();
					map.put(areaid, (DicAreacorp) areaMap.get(areaid));					
				}
			}
		}
		return map;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA");
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public Map<String, DicAreacorp> getDicAreacorpMap() {
		return dicAreacorpMap;
	}

	public String getMps() {
		return mps;
	}

	public void setMps(String mps) {
		this.mps = mps;
	}

	public String getStauss() {
		return stauss;
	}

	public void setStauss(String stauss) {
		this.stauss = stauss;
	}

	public String getMonthStart() {
		return monthStart;
	}

	public void setMonthStart(String monthStart) {
		this.monthStart = monthStart;
	}

	public String getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}
}
