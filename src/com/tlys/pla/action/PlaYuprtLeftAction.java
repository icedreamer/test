package com.tlys.pla.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.FormatUtil;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicProdcategory;
import com.tlys.dic.model.DicProdsecondkind;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.service.PlaYuprtamountService;

/**
 * 本Action类用于生成计划管理左导航页面，主要用来控制权限
 * 
 * @author fengym
 * 
 */

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaYuprtLeftAction extends _BaseAction {
	private static final long serialVersionUID = 431299588520414901L;
	private Log log = LogFactory.getLog(this.getClass());

	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicAreacorp> dicAreacorpMap;
	
	private Map<String,DicProdcategory> dicProdcategoryMap;
	private Map<String, DicProdsecondkind> dicProductsecondMap;


	private String areaid;
	private String corpid;

	private String year;
	private List yearList;

	@Autowired
	PlaYuprtamountService plaYuprtamountService;

	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	DicMap dicMap;
	
	
	public String execute() throws Exception {
		year = FormatUtil.nowDateStr(FormatUtil.YEAR_ONLY);
		int yearInt = Integer.parseInt(year);
		yearList = new ArrayList();
		for(int i=-1;i<8;i++){

			yearList.add(yearInt-i);
			System.out.println("PlaYuprtLeftAction.execute->yearInt=="+yearInt);
		}
		
		getCurUser();

		String curCorpid = curUser.getCorpid();
		String curCorptab = curUser.getCorptab();
		String schAreaid = plaYuprtamountService.bdAreaid(curCorpid, curCorptab);
		String schCorpid = plaYuprtamountService.bdCorpid(curCorpid, curCorptab);

		// 如果不对当前查询进行限制，则areaid=null,corpid=null
		areaid = schAreaid;
		corpid = schCorpid;
		
		
		
		dicAreacorpMap = dicMap.getAreaMap();
		if(null!=areaid){
			DicAreacorp curArea = dicAreacorpMap.get(areaid);
			dicAreacorpMap = new HashMap();
			dicAreacorpMap.put(areaid, curArea);
		}
		
		//当areaid为null时，取出全部企业
		dicSinocorpMap = dicMap.getAreaCorpMap(areaid);
		if(null != corpid){
			DicSinocorp curCorp = dicSinocorpMap.get(corpid);
			dicSinocorpMap = new HashMap();
			dicSinocorpMap.put(corpid, curCorp);
		}
		
		dicProdcategoryMap = dicMap.getProductcategoryMap();
		dicProductsecondMap = dicMap.getProductsecondMap();
		return "left";
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




	public Map<String, DicProdcategory> getDicProdcategoryMap() {
		return dicProdcategoryMap;
	}




	public void setDicProdcategoryMap(
			Map<String, DicProdcategory> dicProdcategoryMap) {
		this.dicProdcategoryMap = dicProdcategoryMap;
	}


	public Map<String, DicProdsecondkind> getDicProductsecondMap() {
		return dicProductsecondMap;
	}


	public void setDicProductsecondMap(Map<String, DicProdsecondkind> dicProductsecondMap) {
		this.dicProductsecondMap = dicProductsecondMap;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public List getYearList() {
		return yearList;
	}


	public void setYearList(List yearList) {
		this.yearList = yearList;
	}

}
