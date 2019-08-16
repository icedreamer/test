package com.tlys.pla.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.dic.model.DicProdcategory;
import com.tlys.dic.model.DicProdsecondkind;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicProdsecondkindService;

/**
 * 本Action类用于生成计划管理左导航页面，主要用来控制权限
 * 
 * @author fengym
 * 
 */

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaLeftdetAction extends _BaseAction {
	private static final long serialVersionUID = 431299588520414901L;

	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicProdcategory> dicProdcategoryByIdMap;
	private Map<String, DicProdsecondkind> dicProdsecondkindByIdMap;
	private List<DicProdsecondkind> dicProdsecondkinds;
	private DicProdcategory dicProdcategory;
	private String areaid;
	private String corpid;
	// 判断是否是非统销 确定是否显示企业查询条件
	private String msps;

	@Autowired
	DicProdsecondkindService dicProdsecondkindService;
	@Autowired
	DicMap dicMap;

	public String left() throws Exception {
		dicProdcategoryByIdMap = dicMap.getProductcategoryMap();
		dicProdsecondkindByIdMap = dicMap.getProductsecondMap();
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
		return "left";
	}

	public String secondkinds() throws Exception {
		String categoryid = dicProdcategory.getCategoryid();
		if (logger.isDebugEnabled()) {
			logger.debug("categoryid : " + categoryid);
		}
		String[] categoryids = null;
		if (null != categoryid && !"".equals(categoryid.trim())) {
			categoryids = categoryid.split(",");
		}
		dicProdsecondkinds = dicProdsecondkindService
				.findDicProdsecondkind(categoryids);
		return "secondkinds";
	}

	public void prepare() throws Exception {
		initOpraMap("PLA");
	}

	public List<DicProdsecondkind> getDicProdsecondkinds() {
		return dicProdsecondkinds;
	}

	public void setDicProdsecondkinds(List<DicProdsecondkind> dicProdsecondkinds) {
		this.dicProdsecondkinds = dicProdsecondkinds;
	}

	public DicProdcategory getDicProdcategory() {
		return dicProdcategory;
	}

	public void setDicProdcategory(DicProdcategory dicProdcategory) {
		this.dicProdcategory = dicProdcategory;
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

	public Map<String, DicProdcategory> getDicProdcategoryByIdMap() {
		return dicProdcategoryByIdMap;
	}

	public Map<String, DicProdsecondkind> getDicProdsecondkindByIdMap() {
		return dicProdsecondkindByIdMap;
	}

	public void setDicProdcategoryByIdMap(
			Map<String, DicProdcategory> dicProdcategoryByIdMap) {
		this.dicProdcategoryByIdMap = dicProdcategoryByIdMap;
	}

	public void setDicProdsecondkindByIdMap(
			Map<String, DicProdsecondkind> dicProdsecondkindByIdMap) {
		this.dicProdsecondkindByIdMap = dicProdsecondkindByIdMap;
	}

	public String getMsps() {
		return msps;
	}

	public void setMsps(String msps) {
		this.msps = msps;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}
}
