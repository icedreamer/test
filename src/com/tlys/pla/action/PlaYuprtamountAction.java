package com.tlys.pla.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.model.PlaYuprtamount;
import com.tlys.pla.service.PlaYuprtamountService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaYuprtamountAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private final Logger log = Logger.getLogger(this.getClass());
	private String id;
	private PlaYuprtamount plaYuprtamount;
	private List<PlaYuprtamount> mstList;
	private List<DicAreacorp> areacorpList;
	private List<DicSinocorp> sinocorpList;
	// 导出条件
	private String schobjkey;

	private Object[] sumarr;
//	// 上年企业小计
//	private List<String[]> lastYearSumArr;
	private Map<String, Double[]> lastYearCorpSumMap;
	
	private Map<String, Double[]> lastYearAreacorpSumMap;
	// 上年总计
	private Object[] lastYearSum;

	private Map<String, Double[]> corpOfYuprtamountSumMap;

	private Map<String, Double[]> areaOfYuprtamountSumMap;

	private Map<String, Double> amountMap;
	/**
	 * 企业收货查询条件
	 */
	private String accept = "";
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaYuprtamountService plaYuprtamountService;

	public String list() throws Exception {
		String pageUrl = "/pla/pla-yuprtamount!list.action";

		if (null == pageCtr) {
			pageCtr = new PageCtr();
			getCurUser();

			// 根据当前用户及前台传来的参数对查询条件进行组装
			// 出来的plaYuprtamount一定不为null
			plaYuprtamount = plaYuprtamountService.buildAuthSch(plaYuprtamount, curUser);
			if (null == plaYuprtamount.getYear()) {
				String thisyear = FormatUtil.nowYearOnly();
				plaYuprtamount.setYear(thisyear);
			}

			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != plaYuprtamount) {
				String schObjKey = "plaYuprtamount_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaYuprtamount);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaYuprtamount = (PlaYuprtamount) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		plaYuprtamountService.find(plaYuprtamount, pageCtr);
		if (pageCtr.getCurrentPage() == 1) {
			sumarr = plaYuprtamountService.findSum(plaYuprtamount);
		} else {
			sumarr = null;
		}
		// 按企业分组查询每个企业的加和数据
		List<Object[]> yuprtamountSumList = plaYuprtamountService.findYuprtAmountSum(plaYuprtamount);

		if (null != yuprtamountSumList && !yuprtamountSumList.isEmpty()) {
			corpOfYuprtamountSumMap = new HashMap<String, Double[]>();
			areaOfYuprtamountSumMap = new HashMap<String, Double[]>();
			for (Object[] objects : yuprtamountSumList) {
				String areaid = objects[0].toString();
				double amount = null == objects[2] ? 0 : Double.parseDouble(objects[2].toString());
				double lamount = null == objects[3] ? 0 : Double.parseDouble(objects[3].toString());
				double varyamount = null == objects[4] ? 0 : Double.parseDouble(objects[4].toString());
				Double[] areaAmount = areaOfYuprtamountSumMap.get(areaid);
				if (null == areaAmount) {
					areaAmount = new Double[3];
					areaAmount[0] = amount;
					areaAmount[1] = lamount;
					areaAmount[2] = varyamount;
				} else {
					areaAmount[0] += amount;
					areaAmount[1] += lamount;
					areaAmount[2] += varyamount;
				}
				areaOfYuprtamountSumMap.put(areaid, areaAmount);
				String corpid = objects[1].toString();

				Double[] corpAmount = corpOfYuprtamountSumMap.get(corpid);
				if (null == corpAmount) {
					corpAmount = new Double[3];
					corpAmount[0] = amount;
					corpAmount[1] = lamount;
					corpAmount[2] = varyamount;
				}
				corpAmount[0] = amount;
				corpAmount[1] = lamount;
				corpAmount[2] = varyamount;
				corpOfYuprtamountSumMap.put(corpid, corpAmount);
			}
		}
		List<PlaYuprtamount> plaYuprtamounts = pageCtr.getRecords();
		List<String> corpIdList = null;
		List<String> productCategoryIdList = null;
		List<String> productSecondIdList = null;
		List<String> rwkindIdList = null;
		if (plaYuprtamounts != null && !plaYuprtamounts.isEmpty()) {
			corpIdList = new ArrayList<String>();
			productCategoryIdList = new ArrayList<String>();
			productSecondIdList = new ArrayList<String>();
			rwkindIdList = new ArrayList<String>();
			for (PlaYuprtamount plaYuprtamount : plaYuprtamounts) {
				if (!corpIdList.contains(plaYuprtamount.getCorpid())) {
					corpIdList.add(plaYuprtamount.getCorpid());
				}
				if (!productCategoryIdList.contains(plaYuprtamount.getProductcategoryid())) {
					productCategoryIdList.add(plaYuprtamount.getProductcategoryid());
				}
				if (!productSecondIdList.contains(plaYuprtamount.getProductsecondid())) {
					productSecondIdList.add(plaYuprtamount.getProductsecondid());
				}
				if (!rwkindIdList.contains(plaYuprtamount.getRwkindid())) {
					rwkindIdList.add(plaYuprtamount.getRwkindid());
				}
			}
		}
		String lastYear = String.valueOf(Integer.parseInt(plaYuprtamount.getYear()) - 1);
		if (log.isDebugEnabled()) {
			log.debug("lastYear : " + lastYear);
		}
		List<PlaYuprtamount> plaYuprtamountList = plaYuprtamountService.findPlaYuprtamount(lastYear, corpIdList,
				productCategoryIdList, productSecondIdList, rwkindIdList);

		if (plaYuprtamountList != null && !plaYuprtamountList.isEmpty()) {
			amountMap = new HashMap<String, Double>();
			for (PlaYuprtamount plaYuprtamount : plaYuprtamountList) {
				String key = CommUtils.getString(plaYuprtamount.getCorpid(), "_",
						plaYuprtamount.getProductcategoryid(), "_", plaYuprtamount.getProductsecondid());
				amountMap.put(key, plaYuprtamount.getAmount());
			}
		}
		// 上年小计
		List<Object[]> lastYearSumArr = plaYuprtamountService.findPlaYuprtamountSubTotal(lastYear);
		
		if (lastYearSumArr != null && !lastYearSumArr.isEmpty()) {
			lastYearAreacorpSumMap = new HashMap<String, Double[]>();
			lastYearCorpSumMap = new HashMap<String, Double[]>();
			for (Object[] objects : lastYearSumArr) {
				String areaid = objects[0].toString();
				double amount = null == objects[2] ? 0 : Double.parseDouble(objects[2].toString());
				double lamount = null == objects[3] ? 0 : Double.parseDouble(objects[3].toString());
				double varyamount = null == objects[4] ? 0 : Double.parseDouble(objects[4].toString());
				Double[] areaAmount = lastYearAreacorpSumMap.get(areaid);
				if (null == areaAmount) {
					areaAmount = new Double[3];
					areaAmount[0] = amount;
					areaAmount[1] = lamount;
					areaAmount[2] = varyamount;
				} else {
					areaAmount[0] += amount;
					areaAmount[1] += lamount;
					areaAmount[2] += varyamount;
				}
				lastYearAreacorpSumMap.put(areaid, areaAmount);
				String corpid = objects[1].toString();

				Double[] corpAmount = lastYearCorpSumMap.get(corpid);
				if (null == corpAmount) {
					corpAmount = new Double[3];
					corpAmount[0] = amount;
					corpAmount[1] = lamount;
					corpAmount[2] = varyamount;
				}
				corpAmount[0] = amount;
				corpAmount[1] = lamount;
				corpAmount[2] = varyamount;
				lastYearCorpSumMap.put(corpid, corpAmount);
			}
		}
		
		// 上年总计
		lastYearSum = plaYuprtamountService.findSum(lastYear);

		if (log.isDebugEnabled()) {
			log.debug("amountMap : " + amountMap);
		}
		return "list";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaYuprtamount = (PlaYuprtamount) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaYuprtamountService.expExcel(plaYuprtamount, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaYuprtamount = plaYuprtamountService.load(id);
		plaYuprtamountService.delete(plaYuprtamount);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	// -------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlaYuprtamount getPlaYuprtamount() {
		return plaYuprtamount;
	}

	public void setPlaYuprtamount(PlaYuprtamount plaYuprtamount) {
		this.plaYuprtamount = plaYuprtamount;
	}

	public List<PlaYuprtamount> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaYuprtamount> mstList) {
		this.mstList = mstList;
	}

	public List<DicAreacorp> getAreacorpList() {
		return areacorpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("PLA_YUPRT");
	}

	public List<DicSinocorp> getSinocorpList() {
		return sinocorpList;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

	public Object[] getSumarr() {
		return sumarr;
	}

	public void setSumarr(Object[] sumarr) {
		this.sumarr = sumarr;
	}

	public void setAreacorpList(List<DicAreacorp> areacorpList) {
		this.areacorpList = areacorpList;
	}

	public void setSinocorpList(List<DicSinocorp> sinocorpList) {
		this.sinocorpList = sinocorpList;
	}

	public Map<String, Double[]> getCorpOfYuprtamountSumMap() {
		return corpOfYuprtamountSumMap;
	}

	public void setCorpOfYuprtamountSumMap(Map<String, Double[]> corpOfYuprtamountSumMap) {
		this.corpOfYuprtamountSumMap = corpOfYuprtamountSumMap;
	}

	public Map<String, Double[]> getAreaOfYuprtamountSumMap() {
		return areaOfYuprtamountSumMap;
	}

	public void setAreaOfYuprtamountSumMap(Map<String, Double[]> areaOfYuprtamountSumMap) {
		this.areaOfYuprtamountSumMap = areaOfYuprtamountSumMap;
	}

	public Map<String, Double> getAmountMap() {
		return amountMap;
	}

	public void setAmountMap(Map<String, Double> amountMap) {
		this.amountMap = amountMap;
	}


	public Object[] getLastYearSum() {
		return lastYearSum;
	}

	public void setLastYearSum(Object[] lastYearSum) {
		this.lastYearSum = lastYearSum;
	}

	public Map<String, Double[]> getLastYearCorpSumMap() {
		return lastYearCorpSumMap;
	}

	public Map<String, Double[]> getLastYearAreacorpSumMap() {
		return lastYearAreacorpSumMap;
	}

	public void setLastYearCorpSumMap(Map<String, Double[]> lastYearCorpSumMap) {
		this.lastYearCorpSumMap = lastYearCorpSumMap;
	}

	public void setLastYearAreacorpSumMap(Map<String, Double[]> lastYearAreacorpSumMap) {
		this.lastYearAreacorpSumMap = lastYearAreacorpSumMap;
	}
}
