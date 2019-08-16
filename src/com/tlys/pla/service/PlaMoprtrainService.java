package com.tlys.pla.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaMoprtrainDao;
import com.tlys.pla.model.PlaDsprtrain;
import com.tlys.pla.model.PlaMoprtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaMoprtrainService extends _GenericService {
	@Autowired
	PlaMoprtrainDao plaMoprtrainDao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicSinodepartmentService dicSinodepartmentService;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;
	@Autowired
	DicMap dicMap;
	@Autowired
	PlaDsprtrainService plaDsprtrainService;

	public PlaMoprtrain load(String id) throws Exception {
		PlaMoprtrain plaMoprtrain = plaMoprtrainDao.load(id);
		return plaMoprtrain;
	}

	public void save(PlaMoprtrain plaMoprtrain) throws Exception {
		plaMoprtrainDao.save(plaMoprtrain);
	}

	public void update(PlaMoprtrain plaMoprtrain) throws Exception {
		plaMoprtrainDao.updateEntity(plaMoprtrain, plaMoprtrain.getId());
	}

	public List<PlaMoprtrain> findAll() throws Exception {
		List<PlaMoprtrain> list = plaMoprtrainDao.findAll();
		return list;
	}

	public void delete(PlaMoprtrain plaMoprtrain) {
		if (null == plaMoprtrain) {
			return;
		}
		plaMoprtrainDao.delete(plaMoprtrain);
	}

	private int getPlaMoprtrainCount(final PlaMoprtrain plaMoprtrain) {
		int count = plaMoprtrainDao.getPlaMoprtrainCount(plaMoprtrain);
		return count;
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMoprtrain plaMoprtrain, PageCtr pageCtr)
			throws Exception {
		rectDate(plaMoprtrain);
		int totalRecord = getPlaMoprtrainCount(plaMoprtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaMoprtrainDao.find(plaMoprtrain, pageCtr);
		bdPlaMsprDic(pageCtr.getRecords());
	}

	public List<PlaMoprtrain> find(PlaMoprtrain plaMoprtrain) throws Exception {
		List<PlaMoprtrain> list = plaMoprtrainDao.find(plaMoprtrain);
		bdPlaMsprDic(list);
		return list;
	}

	
	/**
	 * 此方法已作废，放在此处备查，此处权限使用统一的架构处理
	 * @param plaMoprtrain
	 * @param curUser
	 * @return
	 * @throws Exception
	 */
	public PlaMoprtrain buildAuthSchOld(PlaMoprtrain plaMoprtrain, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaMoprtrain) {
			plaMoprtrain = new PlaMoprtrain();
		}
		// 当前台没有传来查询参数
		if (null == plaMoprtrain.getAreaid()) {
			// 根据用户单位生成区域公司权限查询参数
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaMoprtrain.setAreaid(areaid);
		}
		if (null == plaMoprtrain.getCorpid()) {
			// 根据用户单位查询所属企业
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaMoprtrain.setCorpid(corpid);
		}
		
		return plaMoprtrain;
	}
	
	/**
	 * 此方法是重写过的方法，原方法见buildAuthSchOld 
	 * @param plaMoprtrain
	 * @param curUser
	 * @return
	 * @throws Exception
	 */
	public PlaMoprtrain buildAuthSch(PlaMoprtrain plaMoprtrain)
			throws Exception {
		//String userCorpid = curUser.getCorpid();
		//String userCorptab = curUser.getCorptab();
		if (null == plaMoprtrain) {
			plaMoprtrain = new PlaMoprtrain();
		}
		
		if (null == plaMoprtrain.getCorpids() || "".equals(plaMoprtrain.getCorpids())) {
			// 取得当前用户在本模块中的企业权限
			/**
			 * 目前先按如下规则理解：默认权限－总部用户返回空（"")，区域公司用户返回对应企业列表，企业自身则返回本企业ID
			 * 当一旦重新设定本模块的数据权限，则所有默认权限失效
			 */
			String corpids = CommUtils.getCorpIds(); 
			
			plaMoprtrain.setCorpids(corpids);
		}
		//System.out.println("PlaMoprtrainService.buildAuthSch->plaMoprtrain.getCorpids()=="+plaMoprtrain.getCorpids());
		return plaMoprtrain;
	}
	

	/**
	 * 根据用户单位获取区域公司ID 匀不存在进返回null不对单位进行过滤
	 * 此方法已废弃
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	private String bdAreaid(String curCorpid, String curCorptab)
			throws Exception {
		/**
		 * 此方法已经不起作用，统一返回null，权限统一放在企业这个字段进行
		 * 但此方法的处理过程放在此，提供修改的历史线索
		 */
		if(true){
			return null;
		}
		String areaid = "99999999";// 当当前用户类型为null时，没有查询条件
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// 总部机关
				areaid = null;
			} else if ("1".equals(curCorptab)) {// 区域公司,则当前用户的单位ID即为areaid
				/**
				 * 此处因企业字段采用统一权限架构处理，故areaid不进行限制
				 */
				//areaid = curCorpid;
				areaid = null;
			} else if ("2".equals(curCorptab)) {// 企业,则要查找当前用户企业所对应的areaid
				/*
				 * 注：此处改为如果是企业用户，则同样不对areaid进行限制，即少一个查询条件
				 * 因为此时在corpid里必然会进行限制,另外，当使用了用户数据权限时
				 * 如果用户的数据权限是跨区域公司的，则如果在这里限制了areaid，就会引起冲突
				 */
				//DicSinocorp corpObj = dicSinocorpService.load(curCorpid);
				//areaid = corpObj.getAreaid();
				areaid = null;
			} else if ("3".equals(curCorptab)) {// 驻厂办,则同样要查找当前单位对应的areaid
				/*
				 * 注：此处同样改为不进行限制，原因同上面的企业
				 */
				//DicSinodepartment obj = dicSinodepartmentService.load(curCorpid);
				//areaid = obj.getParentid();
				areaid = null;
			}
		}
		return areaid;
	}

	/**
	 * 根据用户单位获取企业ID 匀不存在进返回null不对单位进行过滤
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	private String bdCorpid(String curCorpid, String curCorptab)
			throws Exception {
		String corpid = "99999999";// 当当前用户类型为null时，没有查询条件
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// 总部机关
				corpid = null;
			} else if ("1".equals(curCorptab)) {// 区域公司用户,其数据权限由areaid来控制
				corpid = null;
			} else if ("2".equals(curCorptab)) {// 企业用户
				/**
				 * 此时，采用统一权限架构处理
				 */
				//corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// 驻厂办,则要查找当前单位对应的corpid
				List<CtlLiaisonocorp> ctlLiaisonocorpList = ctlLiaisonocorpService
						.findAll();
				for (CtlLiaisonocorp obj : ctlLiaisonocorpList) {
					if (curCorpid.equals(obj.getBranchid())) {
						corpid = obj.getCorpid();
						break;
					}
				}
			}
		}
		return corpid;
	}
	
	

	/**
	 * 批复计划页面显示翻译
	 * 
	 * @throws Exception
	 */
	private void bdPlaMsprDic(List<PlaMoprtrain> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// Map rwdepartmentMap = dicMap.getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMoprtrain obj = (PlaMoprtrain) iter.next();
			String areaid = obj.getAreaid();
			DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			String areaidDIC = area != null ? area.getShortname() : areaid;
			obj.setAreaidDIC(areaidDIC);
			String month = obj.getMonth();// BeanUtils.getProperty(obj,
			// "month");
			String monthEnd = month.substring(0, 4) + "年" + month.substring(4)
					+ "月";
			obj.setMonthEnd(monthEnd);
			// BeanUtils.setProperty(obj, "monthEnd", monthEnd);
			String carkindid = obj.getCarkindid();// BeanUtils.getProperty(obj,
			// "carkindid");
			DicCarkind carkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = carkind != null ? carkind.getShortname()
					: carkindid;
			obj.setCarkindidDIC(carkindidDIC);
			// BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC);
			/*
			 * String startstationid = BeanUtils .getProperty(obj,
			 * "startstationid"); String startstationidDIC = (String)
			 * rwdepartmentMap .get(startstationid); BeanUtils.setProperty(obj,
			 * "startstationidDIC", startstationidDIC); String endstationid =
			 * BeanUtils.getProperty(obj, "endstationid"); String
			 * endstationidDIC = (String) rwdepartmentMap.get(endstationid);
			 * BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
			 */
		}
	}

	/**
	 * 进行字符串型日期转换 如果是yyyy-mm格式，转为yyyymmdd;反之，则转为反之。
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaMoprtrain pmt) throws Exception {
		if (null == pmt)
			return;
		String month = pmt.getMonth();
		String monthStr = pmt.getMonthStr();

		if (null != month) {
			monthStr = FormatUtil.rectDate59(month);
			pmt.setMonthStr(monthStr);
		}
		if (null != monthStr) {
			month = FormatUtil.rectDate59(monthStr);
			pmt.setMonth(month);
		}
	}

	public void expExcel(PlaMoprtrain plaMoprtrain, HttpServletResponse response)
			throws Exception {
		// 查询详细计划数据
		List<PlaMoprtrain> list = this.find(plaMoprtrain);

		Map tabDefineMap = new LinkedHashMap();
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("月其他产品请车及批复计划", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "月其他产品请车及批复计划");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "corpshortname", "checkstatusDIC",
				"startstationname", "endstationname", "sendername",
				"receivername", "carkindidDIC", "rwkindname", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "acceptcarno" };
		headArr[1] = new String[] { "企业名称", "审批状态", "发站", "到站", "发货人", "收货人",
				"车种", "铁路品名", "原提车数", "原提吨数", "批准车数", "批准吨数", "批准要车分号" };
		// 企业名称 审批状态 发站 到站 发货人 收货人 车种 铁路品名 原提车数 原提吨数 批准车数 批准吨数 批准要车分号
		sheetMap.put("headArr", headArr);

		// 批准要车分号数组
		String acceptcarnos = CommUtils.getRestrictionsIn(list, "acceptcarno");
		PlaDsprtrain plaDsprtrain = new PlaDsprtrain();
		plaDsprtrain.setAcceptcarnos(acceptcarnos);
		List<PlaDsprtrain> dups = plaDsprtrainService.find(plaDsprtrain);

		Map sheetMap1 = new HashMap();
		tabDefineMap.put("日其他产品请车及批复计划", sheetMap1);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap1.put("list", dups);
		// 标题
		sheetMap1.put("title", "日其他产品请车及批复计划");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		headArr = new String[2][];
		headArr[0] = new String[] { "requestdate", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "loadcars",
				"loadamount", "mtotalcars", "acceptcarno" };
		headArr[1] = new String[] { "提请日期", "原提车数", "原提吨数", "批准车数", "批准吨数",
				"已装车数", "已装吨数", "月累计完成车数", "批准要车分号" };
		// 提请日期 原提车数 原提吨数 批准车数 批准吨数 已装车数 已装吨数 月累计完成车数
		sheetMap1.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "其他产品请车及批复计划");

	}
}
