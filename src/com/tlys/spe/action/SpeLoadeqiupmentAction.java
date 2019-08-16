package com.tlys.spe.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicCorprailway;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicCorprailwayService;
import com.tlys.dic.service.DicProdcategoryService;
import com.tlys.dic.service.DicProductService;
import com.tlys.dic.service.DicRwbureauService;
import com.tlys.dic.service.DicRwdepartmentService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.spe.model.SpeLoadMeastype;
import com.tlys.spe.model.SpeLoadeqiupment;
import com.tlys.spe.service.SpeLoadMeastypeService;
import com.tlys.spe.service.SpeLoadeqiupmentService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpeLoadeqiupmentAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private String id;
	private SpeLoadeqiupment speLoadeqiupment;
	private List<DicSinocorp> corpList;
	// 企业
	private List<DicSinocorp> sinocorpList;
	private Map<String, DicSinocorp> dicSinocorpMap;
	// 铁路专用线
	private List<DicCorprailway> corprailwayList;
	private Map<String, DicCorprailway> corprailwayMap;
	// 车站
	private List<DicRwdepartment> rwdepartments;
	private Map<String, String> rwdepartmentMap;
	// 路局
	private List<DicRwbureau> rwbureaus;
	private Map<String, String> rwbureauMap;
	// 计量方式
	private List<SpeLoadMeastype> loadMeastypeList;
	private Map<String, SpeLoadMeastype> loadMeastypeMap;

	@Autowired
	SpeLoadeqiupmentService speLoadeqiupmentService;

	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	DicCorprailwayService dicCorprailwayService;

	@Autowired
	DicProdcategoryService dicProdcategoryService;

	@Autowired
	DicProductService dicProductService;

	@Autowired
	DicRwdepartmentService dicRwdepartmentService;

	@Autowired
	DicRwbureauService dicRwbureauService;

	@Autowired
	SpeLoadMeastypeService speLoadMeastypeService;
	@Autowired
	DicMap dicMap;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/spe/spe-loadeqiupment!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageViewNew中
			// 在生成页码跳转html时，应带上此参数
			if (null != speLoadeqiupment) {
				String schObjKey = "speLoadeqiupment_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, speLoadeqiupment);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				speLoadeqiupment = (SpeLoadeqiupment) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		speLoadeqiupmentService.findSpeLoadeqiupment(speLoadeqiupment, pageCtr);
		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		speLoadeqiupment = speLoadeqiupmentService.getSpeLoadeqiupment(id);
		speLoadeqiupmentService.delete(speLoadeqiupment);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		// sinocorpList = dicSinocorpService.findAll();
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		// corprailwayList = dicCorprailwayService.findAll();
		corprailwayMap = dicMap.getCrwMap();
		// rwdepartments = dicRwdepartmentService.findAll();
		rwdepartmentMap = dicMap.getRwdepartmentMap();
		// rwbureaus = dicRwbureauService.findAll();
		rwbureauMap = dicMap.getRwbureauMap();
		// loadMeastypeList = speLoadMeastypeService.findAll();
		loadMeastypeMap = dicMap.getLoadMeastypeMap();
		if (null != id) {
			speLoadeqiupment = speLoadeqiupmentService.getSpeLoadeqiupment(id);
			isNew = false;
		} else {
			speLoadeqiupment = new SpeLoadeqiupment();
			isNew = true;
		}
		return "input";
	}

	/**
	 * 保存信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		// 处理计量方式
		if (null == speLoadeqiupment.getMeastypeid() || "".equals(speLoadeqiupment.getMeastypeid())) {
			SpeLoadMeastype speLoadMeastype = new SpeLoadMeastype();
			getCurUser();
			speLoadMeastype.setCreator(curUser.getUserid());
			speLoadMeastype.setName(speLoadeqiupment.getMeastypeidDIC());
			speLoadMeastype.setCreatedtime(new Date());
			speLoadMeastypeService.save(speLoadMeastype);
			speLoadeqiupment.setMeastypeid(speLoadMeastype.getMeastypeid());
			speLoadeqiupment.setCreatedtime(new Date());
		}
		if (isNew) {

			// 添加默认值
			if (null == speLoadeqiupment.getPositions())
				speLoadeqiupment.setPositions(new Long(1));
			if (null == speLoadeqiupment.getDayjobs())
				speLoadeqiupment.setDayjobs(new Long(1));
			if (null == speLoadeqiupment.getDayability())
				speLoadeqiupment.setDayability(new Double(0));
			if ("".equals(speLoadeqiupment.getDistancetohouse()))
				speLoadeqiupment.setDistancetohouse("0");
			if ("".equals(speLoadeqiupment.getDistancetostation()))
				speLoadeqiupment.setDistancetostation("0");
			speLoadeqiupmentService.save(speLoadeqiupment);
		} else {
			// 修改记录
			speLoadeqiupmentService.update(speLoadeqiupment);
		}
		msg = new Msg(Msg.SUCCESS, "操作成功!");
		return edit();
	}

	public String searchpage() throws Exception {
		// sinocorpList = dicSinocorpService.findAll();
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		// rwdepartments = dicRwdepartmentService.findAll();
		// 车站
		rwdepartmentMap = dicMap.getRwdepartmentMap();
		// 路局
		// rwbureaus = dicRwbureauService.findAll();
		rwbureauMap = dicMap.getRwbureauMap();
		// loadMeastypeList = speLoadMeastypeService.findAll();
		// 计量方式
		loadMeastypeMap = dicMap.getLoadMeastypeMap();
		return "searchpage";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String export() throws Exception {
		List<SpeLoadeqiupment> list = speLoadeqiupmentService.findSpeLoadeqiupment();
		HttpServletResponse response = ServletActionContext.getResponse();
		speLoadeqiupmentService.expExcel(list, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public void setCorpList(List<DicSinocorp> corpList) {
		this.corpList = corpList;
	}

	public void setSpeLoadeqiupment(SpeLoadeqiupment speLoadeqiupment) {
		this.speLoadeqiupment = speLoadeqiupment;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_CRW");
	}

	public List<DicSinocorp> getCorpList() {
		return corpList;
	}

	public String getId() {
		return id;
	}

	public SpeLoadeqiupment getSpeLoadeqiupment() {
		return speLoadeqiupment;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<DicSinocorp> getSinocorpList() {
		return sinocorpList;
	}

	public List<DicCorprailway> getCorprailwayList() {
		return corprailwayList;
	}

	public List<SpeLoadMeastype> getLoadMeastypeList() {
		return loadMeastypeList;
	}

	public List<DicRwdepartment> getRwdepartments() {
		return rwdepartments;
	}

	public List<DicRwbureau> getRwbureaus() {
		return rwbureaus;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public Map<String, SpeLoadMeastype> getLoadMeastypeMap() {
		return loadMeastypeMap;
	}

	public void setLoadMeastypeMap(Map<String, SpeLoadMeastype> loadMeastypeMap) {
		this.loadMeastypeMap = loadMeastypeMap;
	}

	public void setSinocorpList(List<DicSinocorp> sinocorpList) {
		this.sinocorpList = sinocorpList;
	}

	public void setCorprailwayList(List<DicCorprailway> corprailwayList) {
		this.corprailwayList = corprailwayList;
	}

	public void setRwdepartments(List<DicRwdepartment> rwdepartments) {
		this.rwdepartments = rwdepartments;
	}

	public void setRwbureaus(List<DicRwbureau> rwbureaus) {
		this.rwbureaus = rwbureaus;
	}

	public Map<String, String> getRwbureauMap() {
		return rwbureauMap;
	}

	public void setRwbureauMap(Map<String, String> rwbureauMap) {
		this.rwbureauMap = rwbureauMap;
	}

	public Map<String, String> getRwdepartmentMap() {
		return rwdepartmentMap;
	}

	public void setRwdepartmentMap(Map<String, String> rwdepartmentMap) {
		this.rwdepartmentMap = rwdepartmentMap;
	}

	public Map<String, DicCorprailway> getCorprailwayMap() {
		return corprailwayMap;
	}

	public void setCorprailwayMap(Map<String, DicCorprailway> corprailwayMap) {
		this.corprailwayMap = corprailwayMap;
	}
}
