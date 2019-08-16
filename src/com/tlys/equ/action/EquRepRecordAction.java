package com.tlys.equ.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicReparetype;
import com.tlys.dic.model.DicRwstockdepot;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTankrepcorp;
import com.tlys.dic.model.DicTrankMaterial;
import com.tlys.dic.service.DicCarkindService;
import com.tlys.dic.service.DicCartypeService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.model.EquRepCars;
import com.tlys.equ.model.EquRepRecord;
import com.tlys.equ.model.EquRepYplan;
import com.tlys.equ.service.EquCarService;
import com.tlys.equ.service.EquRepPlanService;
import com.tlys.equ.service.EquRepRecordService;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMessageService;
import com.tlys.sys.service.SysUserService;

/**
 * 
 * @author ccsong 2012-1-5 下午12:58:18
 */
@Controller
@Namespace("/equ")
@ParentPackage("tlys-default")
public class EquRepRecordAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4417305611327993928L;
	/**
	 * 检修记录对象
	 */
	private EquRepRecord equRepRecord;
	/**
	 * 根据检修类型获取检修记录列表
	 */
	// private Map<String, List<EquRepRecord>> equRepRecordByRtypeidMap = new
	// HashMap<String, List<EquRepRecord>>();
	private Map<String, EquRepRecord> equRepRecordByRtypeidMap = new HashMap<String, EquRepRecord>();
	private Map<String, List<EquRepRecord>> equRepRecordsByRtypeidMap = new HashMap<String, List<EquRepRecord>>();

	/**
	 * 根据检修适用类型获取检修类型列表
	 */
	private Map<String, List<DicReparetype>> dicReparetypeBySuitkindMap = new HashMap<String, List<DicReparetype>>();
	/**
	 * 根据检修类型id获取检修类型对象
	 */
	private Map<String, DicReparetype> dicRepareTypeByRtypeIdMap = new HashMap<String, DicReparetype>();

	/**
	 * 适用类型 A:车体、罐体 C:车体 T:罐体
	 */
	private String suitkind;

	/**
	 * 使用检修类型列表A或者T
	 */
	private List<String> suitkinds;
	private List<Integer> yearList;
	private List<String> monthList;
	private int year;
	private EquRepYplan equRepYplan;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	/**
	 * 检修类型列表
	 */
	private List<DicReparetype> dicReparetypes;

	private DicReparetype dicReparetype;

	private List<DicRwstockdepot> dicRwstockdepots;

	private List<DicTankrepcorp> dicTankrepcorps;
	private List<EquRepRecord> equRepRecordList;
	private Map<String, DicTankrepcorp> dicTankrepcorpMap;
	private Map<String, DicRwstockdepot> dicRwstockdepotMap;
	private EquRepCars equRepCars;

	private String corpid;
	private Map<String, DicCarkind> carkindMap;
	private Map<String, DicCartype> cartypeMap;
	private Map<String, DicGoodscategory> goodscategoryMap;
	private Map<String, DicTrankMaterial> tankmaterialMap;
	private Map<String, DicSinocorp> corpMap;
	private String toPage;
	private boolean hasRepRecord = false;

	/**
	 * 根據適用類型計算，該類型下的檢修記錄總行數，合併單元格使用
	 */
	private Map<String, Integer> rowspanBySuitkindMap = new HashMap<String, Integer>();

	/**
	 * 企业列表
	 */
	private Map<String, DicSinocorp> dicSinocorpMap;
	/**
	 * 区域公司
	 */
	private Map<String, DicAreacorp> dicAreacorpMap;
	/**
	 * 车型
	 */
	private Map<String, DicCartype> dicCartypeMap;
	/**
	 * 车种
	 */
	private Map<String, DicCarkind> dicCarkindMap;

	private Map<String, DicGoodscategory> dicGoodsMap;

	private String now;

	@Autowired
	DicCartypeService dicCartypeService;

	@Autowired
	DicCarkindService dicCarkindService;
	/**
	 * 车辆信息
	 */
	private List<EquCar> equCars;

	private EquCar equCar;

	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	EquRepRecordService equRepRecordService;
	@Autowired
	EquRepPlanService equRepPlanService;

	@Autowired
	EquCarService equCarService;
	@Autowired
	DicMap dicMap;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysMessageService sysMessageService;

	public String delete() {
		equRepRecord = equRepRecordService.getEquRepRecord(equRepRecord.getId());
		DicReparetype dicReparetype = equRepRecord.getDicReparetype();
		String rtypeid = dicReparetype.getRtypeid();
		String carno = equRepRecord.getCarno();
		equRepRecordService.delete(equRepRecord);
		EquRepRecord preEquRepRecord = equRepRecordService.getPrevEquRepRecord(rtypeid, carno);
		if (null != preEquRepRecord) {
			preEquRepRecord.setIsrecently("1");
			equRepRecordService.updateEquRepRecord(preEquRepRecord);
		}
		long start = System.currentTimeMillis();
		Object[] o = equRepRecordService.call_P_ZBC_REP_GENCAR();
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENCAR 状态：" + o[0]);
			logger.debug("call P_ZBC_REP_GENCAR 描述：" + o[1]);
		}
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		Object[] oPlan = equRepPlanService.call_P_ZBC_REP_GENPLAN(year);
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENPLAN 状态：" + oPlan[0]);
			logger.debug("call P_ZBC_REP_GENPLAN 描述：" + oPlan[1]);
		}

		long end = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENCAR and P_ZBC_REP_GENPLAN in " + (end - start) + " ms.");
		}
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	/**
	 * 编辑或新增页面
	 * 
	 * @return
	 */
	public String edit() {
		dicReparetypes = equRepRecordService.findDicReparetype(suitkind, null);
		// dicReparetype =
		// equRepRecordService.getDicReparetype(equRepRecord.getRtypeid());
		dicReparetype = equRepRecord.getDicReparetype();
		if (null != suitkind) {
			if ("C".equals(suitkind.trim())) {
				dicRwstockdepots = equRepRecordService.findDicRwstockdepot();
			} else if ("T".equals(suitkind.trim())) {
				dicTankrepcorps = equRepRecordService.findDicTankrepcorp();
			}
		}
		if (!isNew) {
			equRepRecord = equRepRecordService.getEquRepRecord(equRepRecord.getId());
		}
		return "edit";
	}

	public String export() {
		List<DicReparetype> dicReparetypes = equRepRecordService.findDicReparetype(suitkind, null);
		Map<String, DicReparetype> dicReparetypeByRtypeidMap = new HashMap<String, DicReparetype>();
		if (null != dicReparetypes && !dicReparetypes.isEmpty()) {
			for (DicReparetype dicReparetype : dicReparetypes) {
				String rtypeid = dicReparetype.getRtypeid();
				dicReparetypeByRtypeidMap.put(rtypeid, dicReparetype);
			}
		}
		List<EquRepRecord> equRepRecords = equRepRecordService.findEquRepRecord();
		// Map<String, List<EquRepRecord>> equRpeRecordsByCo
		// 企业
		List<DicSinocorp> dicSinocorps = dicSinocorpService.findDicSinocorp();
		if (null != equRepRecords && !equRepRecords.isEmpty()) {
			for (EquRepRecord equRepRecord : equRepRecords) {

			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		equRepRecordService.exportToExcel(equRepRecords, dicReparetypeByRtypeidMap, workbook);
		HttpServletResponse response = ServletActionContext.getResponse();
		ExcelUtil.writeWorkbook(response, workbook, "车辆检修记录情况");
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * 左侧查询条件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String left() throws Exception {

		SysUser sysUser = getCurUser();
		// 企業
		dicSinocorpMap = CommUtils.getUserCorpMap(sysUser, dicMap);
		// 區域公司
		dicAreacorpMap = CommUtils.getUserAreaMap(sysUser, dicMap);

		// 车种
		dicCarkindMap = dicMap.getCarkindMap();
		// 车型
		dicCartypeMap = dicMap.getCartypeMap();
		// 充装介质
		dicGoodsMap = dicMap.getGoodscategoryMap();
		if (0 == year) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		yearList = new ArrayList<Integer>();
		for (int i = year; i > year - 10; i--) {
			yearList.add(i);
		}
		monthList = new ArrayList<String>();
		for (int i = 0; i < 12; i++) {
			String month = String.valueOf(i + 1);
			if (month.length() == 1) {
				month = "0" + month;
			}
			monthList.add(year + month);
		}
		return "left";
	}

	/**
	 * 检修记录右侧显示区，主要显示车辆信息
	 * 
	 * @return
	 * @throws Exception
	 */

	public String list() throws Exception {
		if (null == equRepCars) {
			equRepCars = new EquRepCars();
		}
		String pageUrl = "/equ/equ-rep-record!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<EquRepCars>();
			if (null != equRepCars) {
				String schObjKey = "equ_rep_cars_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equRepCars);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equRepCars = (EquRepCars) getSessionAttr(pageCtr.getSchObjKey());
			}
		}

		pageCtr.setPageUrl(pageUrl);
		equRepRecordService.findEquRepCars(equRepCars, pageCtr);
		dicAreacorpMap = dicMap.getAreaMap();
		return "list";
	}

	public String records() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("equRepRecord : " + equRepRecord);
		}
		if (null == equCar) {
			equCar = new EquCar();
		}
		String month = "";
		String year = "";
		String repkind = "";
		String rtypeid = "";
		String corpid = "";
		if (null != equRepRecord) {
			List<Object> carnos = equRepRecordService.findCarnos(equRepRecord);
			if (null == carnos || carnos.isEmpty()) {
				// 该设置 目的是为了防止在没有查询到相关车号时 也进行车号关联，该查询结果始终为null;
				carnos = new ArrayList<Object>();
				carnos.add("null");
			}
			equCar.setCarnos(carnos.toArray());
			month = equRepRecord.getMonth() == null ? "" : equRepRecord.getMonth();
			year = equRepRecord.getYear() == null ? "" : equRepRecord.getYear();
			repkind = equRepRecord.getRepkind() == null ? "" : equRepRecord.getRepkind();
			corpid = equRepRecord.getCorpid() == null ? "" : equRepRecord.getCorpid();
			DicReparetype dicReparetype = equRepRecord.getDicReparetype();
			if (logger.isDebugEnabled()) {
				logger.debug("dicReparetype : " + dicReparetype);
				logger.debug("rtypeid : " + dicReparetype.getRtypeid());
			}
			rtypeid = dicReparetype.getRtypeid() == null ? "" : dicReparetype.getRtypeid();
		}

		String pageUrl = "/equ/equ-rep-record!records.action?equRepRecord.corpid=" + corpid + "&equRepRecord.month="
				+ month + "&equRepRecord.year=" + year + "&equRepRecord.repkind=" + repkind
				+ "&equRepRecord.dicReparetype.rtypeid=" + rtypeid;
		if (null == pageCtr) {
			pageCtr = new PageCtr<EquCar>();
			if (null != equCar) {
				String schObjKey = "equ_rep_record_records_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equCar);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equCar = (EquCar) getSessionAttr(pageCtr.getSchObjKey());
			}
		}

		pageCtr.setPageUrl(pageUrl);
		equCarService.findEquCar(equCar, pageCtr);
		carkindMap = dicMap.getCarkindMap();
		cartypeMap = dicMap.getCartypeMap();
		goodscategoryMap = dicMap.getGoodscategoryMap();
		tankmaterialMap = dicMap.getTankmaterialMap();
		corpMap = dicMap.getCorpMap();
		return "records";
	}

	/**
	 * 选择一台车后显示的检修记录
	 * 
	 * @return
	 */

	public String listreprecord() {
		// 车号
		String carno = equRepRecord.getCarno();
		String pressuretype = equRepCars.getId().getPressuretype();
		if (logger.isDebugEnabled()) {
			logger.debug("carno : " + carno);
			logger.debug("pressuretype : " + pressuretype);
		}
		// 所有检修类型
		List<DicReparetype> dicReparetypeList = equRepRecordService.findDicReparetype(null, null);
		dicReparetypes = new ArrayList<DicReparetype>();
		if (null != dicReparetypeList && !dicReparetypeList.isEmpty()) {
			for (DicReparetype dicReparetype : dicReparetypeList) {
				String suitkind = dicReparetype.getSuitkind();
				if (null != pressuretype && "NP".equals(pressuretype) && "T".equals(suitkind)) {
					continue;
				}
				String rtypeid = dicReparetype.getRtypeid();
				List<EquRepRecord> equRepRecords = equRepRecordService.getEquRepRecord(carno, rtypeid);
				if (null != equRepRecords && !equRepRecords.isEmpty()) {
					hasRepRecord = true;
				}
				equRepRecordsByRtypeidMap.put(rtypeid, equRepRecords);
				dicReparetypes.add(dicReparetype);
			}
		}
		// equRepRecordList = equRepRecordService.findEquRepRecord(carno);
		dicTankrepcorps = equRepRecordService.findDicTankrepcorp();
		if (null != dicTankrepcorps && !dicTankrepcorps.isEmpty()) {
			dicTankrepcorpMap = new HashMap<String, DicTankrepcorp>();
			for (DicTankrepcorp dicTankrepcorp : dicTankrepcorps) {
				dicTankrepcorpMap.put(dicTankrepcorp.getRepcorpid(), dicTankrepcorp);
			}
		}
		dicRwstockdepots = equRepRecordService.findDicRwstockdepot();
		if (null != dicRwstockdepots && !dicRwstockdepots.isEmpty()) {
			dicRwstockdepotMap = new HashMap<String, DicRwstockdepot>();
			for (DicRwstockdepot dicRwstockdepot : dicRwstockdepots) {
				dicRwstockdepotMap.put(dicRwstockdepot.getStockdepotid(), dicRwstockdepot);
			}
		}
		now = CommUtils.monthFormat(new Date());
		return "listreprecord";
	}

	public String listreprecord_20120510() {
		// 车号
		String carno = equRepRecord.getCarno();
		if (logger.isDebugEnabled()) {
			logger.debug("carno : " + carno);
		}
		// 所有检修类型
		dicReparetypes = equRepRecordService.findDicReparetype(suitkind, null);
		if (null != dicReparetypes && !dicReparetypes.isEmpty()) {
			for (DicReparetype dicReparetype : dicReparetypes) {
				EquRepRecord cursorEquRepRecord = equRepRecordService.getEquRepRecord(dicReparetype.getRtypeid(),
						carno, equRepRecord.getRepkind());
				if (null != cursorEquRepRecord) {
					cursorEquRepRecord.setRepkind(equRepRecord.getRepkind());
				}
				equRepRecordByRtypeidMap.put(dicReparetype.getRtypeid(), cursorEquRepRecord);
			}
		}
		dicTankrepcorps = equRepRecordService.findDicTankrepcorp();
		dicRwstockdepots = equRepRecordService.findDicRwstockdepot();
		now = CommUtils.monthFormat(new Date());
		return "listreprecord";
	}

	public String repare() {
		// 车号
		String carno = equRepRecord.getCarno();
		DicReparetype dicReparetype = equRepRecord.getDicReparetype();
		String rtypeid = dicReparetype.getRtypeid();
		dicReparetype = equRepRecordService.getDicReparetype(rtypeid);
		if (logger.isDebugEnabled()) {
			logger.debug("carno : " + carno);
			logger.debug("rtypeid : " + rtypeid);
		}
		List<EquRepRecord> equRepRecords = equRepRecordService.getEquRepRecord(carno, rtypeid);
		equRepRecordsByRtypeidMap.put(rtypeid, equRepRecords);
		dicReparetypes = new ArrayList<DicReparetype>();
		dicReparetypes.add(dicReparetype);
		dicTankrepcorps = equRepRecordService.findDicTankrepcorp();
		dicRwstockdepots = equRepRecordService.findDicRwstockdepot();

		dicTankrepcorps = equRepRecordService.findDicTankrepcorp();
		if (null != dicTankrepcorps && !dicTankrepcorps.isEmpty()) {
			dicTankrepcorpMap = new HashMap<String, DicTankrepcorp>();
			for (DicTankrepcorp dicTankrepcorp : dicTankrepcorps) {
				dicTankrepcorpMap.put(dicTankrepcorp.getRepcorpid(), dicTankrepcorp);
			}
		}
		dicRwstockdepots = equRepRecordService.findDicRwstockdepot();
		if (null != dicRwstockdepots && !dicRwstockdepots.isEmpty()) {
			dicRwstockdepotMap = new HashMap<String, DicRwstockdepot>();
			for (DicRwstockdepot dicRwstockdepot : dicRwstockdepots) {
				dicRwstockdepotMap.put(dicRwstockdepot.getStockdepotid(), dicRwstockdepot);
			}
		}

		now = CommUtils.monthFormat(new Date());
		return "repare";
	}

	/**
	 * 历史检修记录
	 * 
	 * @return
	 */

	public String listhistoryreprecord() {
		// 车号
		String carno = equRepRecord.getCarno();
		if (logger.isDebugEnabled()) {
			logger.debug("carno : " + carno);
		}
		// 根据车号获取所有检修记录
		equRepRecordList = equRepRecordService.findEquRepRecord(carno, suitkind);
		dicSinocorpMap = dicMap.getCorpMap();
		return "listhistoryreprecord";
	}

	public String listhistoryreprecord1() {

		// 车号
		String carno = equCar.getCarno();
		if (logger.isDebugEnabled()) {
			logger.debug("carno : " + carno);
		}
		// 根据车号获取所有检修记录
		List<EquRepRecord> equRepRecordList = equRepRecordService.findEquRepRecord(carno, suitkind);
		if (null != equRepRecordList && !equRepRecordList.isEmpty()) {
			for (EquRepRecord equRepRecord : equRepRecordList) {
				String rtypeid = equRepRecord.getDicReparetype().getRtypeid();
				List<EquRepRecord> equRepRecords = equRepRecordsByRtypeidMap.get(rtypeid);
				if (null == equRepRecords) {
					equRepRecords = new ArrayList<EquRepRecord>();
				}
				equRepRecords.add(equRepRecord);
				// 将检修类型ID，检修记录存入equRepRecordByRtypeidMap中
				equRepRecordsByRtypeidMap.put(rtypeid, equRepRecords);
			}
		}
		// 所有检修类型
		dicReparetypes = equRepRecordService.findDicReparetype(suitkind, null);
		if (null != dicReparetypes && !dicReparetypes.isEmpty()) {
			suitkinds = new ArrayList<String>();
			for (DicReparetype dicReparetype : dicReparetypes) {
				String suitkind = dicReparetype.getSuitkind();
				String rtypeid = dicReparetype.getRtypeid();
				if (!suitkinds.contains(suitkind)) {
					// 适用类型
					suitkinds.add(suitkind);
				}
				List<DicReparetype> dicReparetypes = dicReparetypeBySuitkindMap.get(suitkind);
				if (null == dicReparetypes) {
					dicReparetypes = new ArrayList<DicReparetype>();
				}
				dicReparetypes.add(dicReparetype);
				// 将适用类型，检修类型列表存入dicReparetypeBySuitkindMap中
				dicReparetypeBySuitkindMap.put(suitkind, dicReparetypes);
				dicRepareTypeByRtypeIdMap.put(rtypeid, dicReparetype);
			}
		}
		Set<String> keySet = dicReparetypeBySuitkindMap.keySet();
		for (String suitkind : keySet) {

			int rowspan = 0;
			List<DicReparetype> dicReparetypes = dicReparetypeBySuitkindMap.get(suitkind);
			if (null != dicReparetypes && !dicReparetypes.isEmpty()) {
				for (DicReparetype dicReparetype : dicReparetypes) {
					String rtypeid = dicReparetype.getRtypeid();
					List<EquRepRecord> equRepRecords = equRepRecordsByRtypeidMap.get(rtypeid);
					if (null != equRepRecords) {
						rowspan += equRepRecords.size();
					} else {
						rowspan++;
					}
				}
			}
			rowspanBySuitkindMap.put(suitkind, rowspan);
		}

		return "listhistoryreprecord";
	}

	/**
	 * 加载上次检修数据
	 * 
	 * @return
	 */
	public String loadPreData() {
		String carno = equRepRecord.getCarno();
		carno = null == carno ? "" : carno.trim();
		dicReparetype = equRepRecord.getDicReparetype();
		String rtypeid = dicReparetype.getRtypeid();
		if (logger.isDebugEnabled()) {
			logger.debug("carno : " + carno);
			logger.debug("suitkind : " + suitkind);
			logger.debug("rtypeid : " + rtypeid);
		}
		dicReparetypes = equRepRecordService.findDicReparetype(suitkind, null);
		// dicReparetype = equRepRecordService.getDicReparetype(rtypeid);
		if (null != suitkind) {
			if ("C".equals(suitkind.trim())) {
				dicRwstockdepots = equRepRecordService.findDicRwstockdepot();
			} else if ("T".equals(suitkind.trim())) {
				dicTankrepcorps = equRepRecordService.findDicTankrepcorp();
			}
		}
		equRepRecord = equRepRecordService.getPrevEquRepRecord(rtypeid, carno);
		if (logger.isDebugEnabled()) {
			logger.debug("equRepRecord : " + equRepRecord);
		}
		if (equRepRecord == null) {
			equRepRecord = new EquRepRecord();
			equRepRecord.setCarno(carno);
			equRepRecord.setDicReparetype(dicReparetype);// .setRtypeid(rtypeid);
		}
		return "load";
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("EQU_REP");
	}

	public String refer() {
		equRepRecord = equRepRecordService.getEquRepRecord(equRepRecord.getId());
		equRepRecord.setActive("1");
		equRepRecordService.update(equRepRecord);
		long start = System.currentTimeMillis();
		Object[] o = equRepRecordService.call_P_ZBC_REP_GENCAR();
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENCAR 状态：" + o[0]);
			logger.debug("call P_ZBC_REP_GENCAR 描述：" + o[1]);
		}
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		Object[] oPlan = equRepPlanService.call_P_ZBC_REP_GENPLAN(year);
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENPLAN 状态：" + oPlan[0]);
			logger.debug("call P_ZBC_REP_GENPLAN 描述：" + oPlan[1]);
		}

		long end = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENCAR and P_ZBC_REP_GENPLAN in " + (end - start) + " ms.");
		}

		SysUser sysUser = getCurUser();
		equRepRecordService.sendMessage(equRepRecord, sysUser);

		msg = new Msg(Msg.SUCCESS, o[1].toString());
		return MSG;
	}

	public String save() {
		long id = equRepRecord.getId();
		if (logger.isDebugEnabled()) {
			logger.debug("equRepRecord.getId() : " + id);
			logger.debug("equRepRecord.getCarno() : " + equRepRecord.getCarno());
		}
		// 新增记录
		if (isNew) {
			EquRepRecord currentRepRecord = equRepRecordService.getEquRepRecord(id);
			if (null == currentRepRecord) {
				currentRepRecord = equRepRecordService.getPrevEquRepRecord(null, equRepRecord.getCarno());
				if (logger.isDebugEnabled()) {
					logger.debug("currentRepRecord : " + currentRepRecord);
				}
				if (null == currentRepRecord) {
					currentRepRecord = new EquRepRecord();
					currentRepRecord.setActive("0");
					currentRepRecord.setIsrecently("1");
					DicReparetype dicReparetype = equRepRecord.getDicReparetype();
					currentRepRecord.setDicReparetype(dicReparetype);
					dicReparetype = equRepRecordService.getDicReparetype(dicReparetype.getRtypeid());
					currentRepRecord.setRtypename(dicReparetype.getRtypename());
					currentRepRecord.setCorpid(equRepRecord.getCorpid());
					currentRepRecord.setCreatedtime(new Date());
					currentRepRecord.setDeliverdate(new Date());
					currentRepRecord.setWarnstatus("0");
					currentRepRecord.setNextrepdate(new Date());
					currentRepRecord.setCarno(equRepRecord.getCarno());
					DicRwstockdepot dicRwstockdepot = equRepRecordService.getFirstDicRwstockdepot();
					currentRepRecord.setRepcorpid(dicRwstockdepot.getStockdepotid());
					equRepRecordService.save(currentRepRecord);
				} else {
					EquRepRecord destEquRepRecord = new EquRepRecord();
					CommUtils.copyProperties(currentRepRecord, destEquRepRecord);
					destEquRepRecord.setActive("0");
					destEquRepRecord.setIsrecently("1");
					DicReparetype dicReparetype = equRepRecord.getDicReparetype();
					String rtypeid = dicReparetype.getRtypeid();
					dicReparetype = equRepRecordService.getDicReparetype(rtypeid);
					if (logger.isDebugEnabled()) {
						logger.debug("dicReparetype : " + dicReparetype);
						logger.debug("rtypeid : " + rtypeid);
						logger.debug("dicReparetype.name : " + dicReparetype.getRtypename());
					}
					destEquRepRecord.setDicReparetype(dicReparetype);
					destEquRepRecord.setRtypename(dicReparetype.getRtypename());
					equRepRecordService.save(destEquRepRecord);
					// currentRepRecord.setIsrecently("0");
					// equRepRecordService.updateEquRepRecord(currentRepRecord);
				}
			} else {
				EquRepRecord destEquRepRecord = new EquRepRecord();
				CommUtils.copyProperties(currentRepRecord, destEquRepRecord);
				destEquRepRecord.setActive("0");
				destEquRepRecord.setIsrecently("1");
				equRepRecordService.save(destEquRepRecord);
				currentRepRecord.setIsrecently("0");
				equRepRecordService.updateEquRepRecord(currentRepRecord);
			}
		} else {
			// 修改记录
			String remarks = equRepRecord.getRemarks();
			String distrainaddress = equRepRecord.getDistrainaddress();
			equRepRecord.setRemarks(null == remarks ? "" : CommUtils.decode(remarks));
			equRepRecord.setDistrainaddress(null == distrainaddress ? "" : CommUtils.decode(distrainaddress));
			String rtypeid = equRepRecord.getDicReparetype().getRtypeid();
			DicReparetype dicReparetype = equRepRecordService.getDicReparetype(rtypeid);
			if (null != dicReparetype) {
				equRepRecord.setRtypename(dicReparetype.getRtypename());
			}
			equRepRecord.setActive("0");
			equRepRecord.setIsrecently("1");
			if (null == equRepRecord.getDeliverdate()) {
				equRepRecord.setDeliverdate(CommUtils.getDefaultDate());
			}
			if (null == equRepRecord.getDistraindate()) {
				equRepRecord.setDistraindate(CommUtils.getDefaultDate());
			}
			// if (id == 0) {
			// equRepRecordService.save(equRepRecord);
			// } else {
			equRepRecordService.update(equRepRecord);
			// }
		}

		msg = new Msg(Msg.SUCCESS, "操作成功");
		return MSG;
	}

	public String save1() {
		if (logger.isDebugEnabled()) {
			logger.debug("equRepRecord.getId() : " + equRepRecord.getId());
		}
		String repcorpid = equRepRecord.getRepcorpid();
		if (logger.isDebugEnabled()) {
			logger.debug("repcorpid : " + repcorpid);
		}
		DicSinocorp dicSinocorp = dicSinocorpService.load(repcorpid);
		if (null != dicSinocorp) {
			equRepRecord.setRepcorpname(dicSinocorp.getShortname());
		}
		// DicReparetype dicReparetype =
		// equRepRecordService.getDicReparetype(equRepRecord.getRtypeid());
		DicReparetype dicReparetype = equRepRecord.getDicReparetype();
		if (null != dicReparetype) {
			equRepRecord.setRtypename(dicReparetype.getRtypename());
		}
		if (null != suitkind) {
			if ("C".equals(suitkind)) {
				DicRwstockdepot dicRwstockdepot = equRepRecordService.getDicRwstockdepot(repcorpid);
				if (null != dicRwstockdepot) {
					equRepRecord.setRepcorpname(dicRwstockdepot.getShortname());
				}
			} else if ("T".equals(suitkind)) {
				DicTankrepcorp dicTankrepcorp = equRepRecordService.getDicTankrepcorp(repcorpid);
				if (null != dicTankrepcorp) {
					equRepRecord.setRepcorpname(dicTankrepcorp.getShortname());
				}
			}
		}
		if (isNew) {
			// 将上一条记录的isrecently更新为0
			EquRepRecord preEquRepRecord = equRepRecordService.getEquRepRecord(equRepRecord.getId());
			preEquRepRecord.setIsrecently("0");
			equRepRecordService.update(preEquRepRecord);

			equRepRecord.setCreatedtime(new Date());
			equRepRecord.setActive("0");
			equRepRecord.setIsrecently("1");
			equRepRecordService.save(equRepRecord);
		} else {
			equRepRecordService.update(equRepRecord);
		}
		msg = new Msg(Msg.SUCCESS, "操作成功");
		return MSG;
	}

	public String corps() throws Exception {
		if (null != suitkind) {
			if ("C".equals(suitkind.trim())) {
				dicRwstockdepots = equRepRecordService.findDicRwstockdepot();
			} else if ("T".equals(suitkind.trim())) {
				dicTankrepcorps = equRepRecordService.findDicTankrepcorp();
			}
		}

		return "corps";

	}

	public String areacorps() throws Exception {
		String areaidsStr = equRepYplan.getAreaids();
		String[] areaids = null;
		if (null != areaidsStr && !"".equals(areaidsStr)) {
			areaids = areaidsStr.split(",");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("areaids : " + areaids);
		}
		SysUser sysUser = getCurUser();
		if (null != areaids && areaids.length > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("areaids.lenfth : " + areaids.length);
			}
			dicSinocorpMap = new LinkedHashMap<String, DicSinocorp>();
			for (int i = 0; i < areaids.length; i++) {
				if (logger.isDebugEnabled()) {
					logger.debug("areaid : " + areaids[i]);
				}
				Map<String, DicSinocorp> corpsMap = CommUtils.getUserCorpByAreaId(sysUser, dicMap, areaids[i].trim());
				dicSinocorpMap.putAll(corpsMap);
			}
		} else {
			dicSinocorpMap = CommUtils.getUserCorpMap(sysUser, dicMap);
		}
		if (null != toPage && "1".equals(toPage)) {
			return "areacorps";
		}
		return "corpsOfArea";
	}

	public String tabs() {
		long start = System.currentTimeMillis();
		// Object[] o = equRepRecordService.call_P_ZBC_REP_GENCAR();
		// if (logger.isDebugEnabled()) {
		// logger.debug("call P_ZBC_REP_GENCAR 状态：" + o[0]);
		// logger.debug("call P_ZBC_REP_GENCAR 描述：" + o[1]);
		// }
		long end = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("call P_ZBC_REP_GENCAR in " + (end - start) + " ms.");
		}
		return "tabs";
	}

	public String importRepRecord() throws Exception {

		return "importRepRecord";
	}

	public String importRepRec() throws Exception {
		PrintWriter out = null;
		try {
			ActionContext context = ServletActionContext.getContext();
			context.getActionInvocation().getProxy().setExecuteResult(false);
			equRepRecordService.importRepRec(upload);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Pragma", "no-cache");
			out = response.getWriter();
			out.write("保存成功");
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		return null;
	}

	public EquRepRecord getEquRepRecord() {
		return equRepRecord;
	}

	public Map<String, EquRepRecord> getEquRepRecordByRtypeidMap() {
		return equRepRecordByRtypeidMap;
	}

	public Map<String, List<EquRepRecord>> getEquRepRecordsByRtypeidMap() {
		return equRepRecordsByRtypeidMap;
	}

	public Map<String, List<DicReparetype>> getDicReparetypeBySuitkindMap() {
		return dicReparetypeBySuitkindMap;
	}

	public Map<String, DicReparetype> getDicRepareTypeByRtypeIdMap() {
		return dicRepareTypeByRtypeIdMap;
	}

	public String getSuitkind() {
		return suitkind;
	}

	public List<String> getSuitkinds() {
		return suitkinds;
	}

	public List<Integer> getYearList() {
		return yearList;
	}

	public List<String> getMonthList() {
		return monthList;
	}

	public int getYear() {
		return year;
	}

	public List<DicReparetype> getDicReparetypes() {
		return dicReparetypes;
	}

	public DicReparetype getDicReparetype() {
		return dicReparetype;
	}

	public List<DicRwstockdepot> getDicRwstockdepots() {
		return dicRwstockdepots;
	}

	public List<DicTankrepcorp> getDicTankrepcorps() {
		return dicTankrepcorps;
	}

	public List<EquRepRecord> getEquRepRecordList() {
		return equRepRecordList;
	}

	public EquRepCars getEquRepCars() {
		return equRepCars;
	}

	public String getCorpid() {
		return corpid;
	}

	public Map<String, DicCarkind> getCarkindMap() {
		return carkindMap;
	}

	public Map<String, DicCartype> getCartypeMap() {
		return cartypeMap;
	}

	public Map<String, DicGoodscategory> getGoodscategoryMap() {
		return goodscategoryMap;
	}

	public Map<String, DicTrankMaterial> getTankmaterialMap() {
		return tankmaterialMap;
	}

	public Map<String, DicSinocorp> getCorpMap() {
		return corpMap;
	}

	public Map<String, Integer> getRowspanBySuitkindMap() {
		return rowspanBySuitkindMap;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public Map<String, DicAreacorp> getDicAreacorpMap() {
		return dicAreacorpMap;
	}

	public Map<String, DicCartype> getDicCartypeMap() {
		return dicCartypeMap;
	}

	public Map<String, DicCarkind> getDicCarkindMap() {
		return dicCarkindMap;
	}

	public String getNow() {
		return now;
	}

	public List<EquCar> getEquCars() {
		return equCars;
	}

	public EquCar getEquCar() {
		return equCar;
	}

	public void setEquRepRecord(EquRepRecord equRepRecord) {
		this.equRepRecord = equRepRecord;
	}

	public void setEquRepRecordByRtypeidMap(Map<String, EquRepRecord> equRepRecordByRtypeidMap) {
		this.equRepRecordByRtypeidMap = equRepRecordByRtypeidMap;
	}

	public void setEquRepRecordsByRtypeidMap(Map<String, List<EquRepRecord>> equRepRecordsByRtypeidMap) {
		this.equRepRecordsByRtypeidMap = equRepRecordsByRtypeidMap;
	}

	public void setDicReparetypeBySuitkindMap(Map<String, List<DicReparetype>> dicReparetypeBySuitkindMap) {
		this.dicReparetypeBySuitkindMap = dicReparetypeBySuitkindMap;
	}

	public void setDicRepareTypeByRtypeIdMap(Map<String, DicReparetype> dicRepareTypeByRtypeIdMap) {
		this.dicRepareTypeByRtypeIdMap = dicRepareTypeByRtypeIdMap;
	}

	public void setSuitkind(String suitkind) {
		this.suitkind = suitkind;
	}

	public void setSuitkinds(List<String> suitkinds) {
		this.suitkinds = suitkinds;
	}

	public void setYearList(List<Integer> yearList) {
		this.yearList = yearList;
	}

	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setDicReparetypes(List<DicReparetype> dicReparetypes) {
		this.dicReparetypes = dicReparetypes;
	}

	public void setDicReparetype(DicReparetype dicReparetype) {
		this.dicReparetype = dicReparetype;
	}

	public void setDicRwstockdepots(List<DicRwstockdepot> dicRwstockdepots) {
		this.dicRwstockdepots = dicRwstockdepots;
	}

	public void setDicTankrepcorps(List<DicTankrepcorp> dicTankrepcorps) {
		this.dicTankrepcorps = dicTankrepcorps;
	}

	public void setEquRepRecordList(List<EquRepRecord> equRepRecordList) {
		this.equRepRecordList = equRepRecordList;
	}

	public void setEquRepCars(EquRepCars equRepCars) {
		this.equRepCars = equRepCars;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public void setCarkindMap(Map<String, DicCarkind> carkindMap) {
		this.carkindMap = carkindMap;
	}

	public void setCartypeMap(Map<String, DicCartype> cartypeMap) {
		this.cartypeMap = cartypeMap;
	}

	public void setGoodscategoryMap(Map<String, DicGoodscategory> goodscategoryMap) {
		this.goodscategoryMap = goodscategoryMap;
	}

	public void setTankmaterialMap(Map<String, DicTrankMaterial> tankmaterialMap) {
		this.tankmaterialMap = tankmaterialMap;
	}

	public void setCorpMap(Map<String, DicSinocorp> corpMap) {
		this.corpMap = corpMap;
	}

	public void setRowspanBySuitkindMap(Map<String, Integer> rowspanBySuitkindMap) {
		this.rowspanBySuitkindMap = rowspanBySuitkindMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public void setDicAreacorpMap(Map<String, DicAreacorp> dicAreacorpMap) {
		this.dicAreacorpMap = dicAreacorpMap;
	}

	public void setDicCartypeMap(Map<String, DicCartype> dicCartypeMap) {
		this.dicCartypeMap = dicCartypeMap;
	}

	public void setDicCarkindMap(Map<String, DicCarkind> dicCarkindMap) {
		this.dicCarkindMap = dicCarkindMap;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public void setEquCars(List<EquCar> equCars) {
		this.equCars = equCars;
	}

	public void setEquCar(EquCar equCar) {
		this.equCar = equCar;
	}

	public EquRepYplan getEquRepYplan() {
		return equRepYplan;
	}

	public void setEquRepYplan(EquRepYplan equRepYplan) {
		this.equRepYplan = equRepYplan;
	}

	public String getToPage() {
		return toPage;
	}

	public void setToPage(String toPage) {
		this.toPage = toPage;
	}

	public Map<String, DicGoodscategory> getDicGoodsMap() {
		return dicGoodsMap;
	}

	public void setDicGoodsMap(Map<String, DicGoodscategory> dicGoodsMap) {
		this.dicGoodsMap = dicGoodsMap;
	}

	public Map<String, DicTankrepcorp> getDicTankrepcorpMap() {
		return dicTankrepcorpMap;
	}

	public Map<String, DicRwstockdepot> getDicRwstockdepotMap() {
		return dicRwstockdepotMap;
	}

	public void setDicTankrepcorpMap(Map<String, DicTankrepcorp> dicTankrepcorpMap) {
		this.dicTankrepcorpMap = dicTankrepcorpMap;
	}

	public void setDicRwstockdepotMap(Map<String, DicRwstockdepot> dicRwstockdepotMap) {
		this.dicRwstockdepotMap = dicRwstockdepotMap;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public boolean getHasRepRecord() {
		return hasRepRecord;
	}

	public void setHasRepRecord(boolean hasRepRecord) {
		this.hasRepRecord = hasRepRecord;
	}

}
