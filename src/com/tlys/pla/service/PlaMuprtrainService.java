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
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaMuprtrainDao;
import com.tlys.pla.model.PlaDuprtrain;
import com.tlys.pla.model.PlaMuprtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaMuprtrainService extends _GenericService {
	@Autowired
	PlaMuprtrainDao plaMuprtrainDao;
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
	PlaDuprtrainService plaDuprtrainService;

	
	public PlaMuprtrain buildAuthSch(PlaMuprtrain plaMuprtrain)
			throws Exception {
		if (null == plaMuprtrain) {
			plaMuprtrain = new PlaMuprtrain();
		}

		if (null == plaMuprtrain.getCorpids() || "".equals(plaMuprtrain.getCorpids())) {
			String corpids = CommUtils.getCorpIds(); 
			plaMuprtrain.setCorpids(corpids);
			//System.out.println("PlaMuprtrainService.buildAuthSch->corpids=="+corpids);
		}
		
		return plaMuprtrain;
	}
	
	/**
	 * 根据当前用户身份及前台传过来的查询参数 生成本次查询所需要的corpid和areaid，并放入查询对象中
	 * 此方法已废止
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaMuprtrain buildAuthSchOld(PlaMuprtrain plaMuprtrain, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaMuprtrain) {
			plaMuprtrain = new PlaMuprtrain();
		}
		// 当前台没有传来查询参数
		if (null == plaMuprtrain.getAreaid()) {
			// 根据用户单位生成区域公司权限查询参数
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaMuprtrain.setAreaid(areaid);
		}
		if (null == plaMuprtrain.getCorpid()) {
			// 根据用户单位查询所属企业
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaMuprtrain.setCorpid(corpid);
		}
		return plaMuprtrain;
	}

	/**
	 * <li>根据用户单位获取区域公司ID</li>
	 * <li>当前用户属于总部用户时：返回null,表示不按区域公司进行过滤，查询的区域公司列表为全部</li>
	 * <li>当前用户属于区域公司时：返回用户当前所在区域公司 的ID，查询的区域公司只返回一个</li>
	 * <li>当前用户属于企业时：返回用户当前企业所对应的区域公司的ID，查询的区域公司也只返回一个</li>
	 * <li>当前用户属于驻厂办时：返回当前单位对应的区域公司ID，查询的区域公司列表为一个</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdAreaid(String curCorpid, String curCorptab)
			throws Exception {
		String areaid = "99999999";// 当当前用户类型为null时，没有查询条件
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// 总部机关
				areaid = null;
			} else if ("1".equals(curCorptab)) {// 区域公司,则当前用户的单位ID即为areaid
				areaid = curCorpid;
			} else if ("2".equals(curCorptab)) {// 企业,则要查找当前用户企业所对应的areaid
				DicSinocorp corpObj = dicSinocorpService.load(curCorpid);
				areaid = corpObj.getAreaid();
			} else if ("3".equals(curCorptab)) {// 驻厂办,则同样要查找当前单位对应的areaid
				DicSinodepartment obj = dicSinodepartmentService
						.load(curCorpid);
				areaid = obj.getParentid();
			}
		}
		return areaid;
	}

	/**
	 * <li>根据用户单位获取企业ID</li>
	 * <li>总部用户：null</li>
	 * <li>区域公司用户：null，但在查询列表时，应考虑当前所在的区域公司</li>
	 * <li>企业：当前所在的企业ID</li>
	 * <li>驻厂办：当前单位所对应的企业ID</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdCorpid(String curCorpid, String curCorptab)
			throws Exception {
		
		String corpid = "99999999";// 当当前用户类型为null时，没有查询条件
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// 总部机关
				corpid = null;
			} else if ("1".equals(curCorptab)) {// 区域公司
				corpid = null;
			} else if ("2".equals(curCorptab)) {// 企业
				corpid = curCorpid;
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

	public PlaMuprtrain load(String id) throws Exception {
		PlaMuprtrain plaMuprtrain = plaMuprtrainDao.load(id);
		return plaMuprtrain;
	}

	public void save(PlaMuprtrain plaMuprtrain) throws Exception {
		plaMuprtrainDao.save(plaMuprtrain);
	}

	public void update(PlaMuprtrain plaMuprtrain) throws Exception {
		plaMuprtrainDao.updateEntity(plaMuprtrain, plaMuprtrain.getId());
	}

	/**
	 * @param PlaMuprtrain
	 */
	public List<PlaMuprtrain> find(PlaMuprtrain plaMuprtrain) throws Exception {
		List<PlaMuprtrain> trainList = plaMuprtrainDao.find(plaMuprtrain);

		bdPlaMuprDic(trainList);
		return trainList;
	}

	/**
	 * 分页查询
	 * 
	 * @param PlaMuprtrain
	 * @return
	 * @throws Exception
	 */
	public void find(PlaMuprtrain plaMuprtrain, PageCtr pageCtr)
			throws Exception {
		rectDate(plaMuprtrain);
		int totalRecord = getPlaMuprtrainCount(plaMuprtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaMuprtrainDao.find(plaMuprtrain, pageCtr);
		bdPlaMuprDic(pageCtr.getRecords());
	}

	public int getPlaMuprtrainCount(final PlaMuprtrain plaMuprtrain) {
		int count = plaMuprtrainDao.getPlaMuprtrainCount(plaMuprtrain);
		return count;
	}

	/**
	 * @param PlaMuprtrain
	 */
	public List<PlaMuprtrain> findDistinct(PlaMuprtrain PlaMuprtrain)
			throws Exception {
		String hql = "select distinct new PlaMuprtrain(areaid,month) from "
				+ PlaMuprtrain.class.getName();
		hql += getCondition(PlaMuprtrain) + " order by month desc";
		List<PlaMuprtrain> trainList = plaMuprtrainDao.find(hql);
		bdPlaMuprDic(trainList);
		return trainList;
	}

	private String getCondition(PlaMuprtrain PlaMuprtrain) {
		String condition = " where 1=1 ";
		String areaid = null;
		String corpid = null;
		String month = null;
		// 收货人
		String receiverids = null;
		if (null != PlaMuprtrain) {
			month = PlaMuprtrain.getMonth();
			areaid = PlaMuprtrain.getAreaid();
			corpid = PlaMuprtrain.getCorpid();
			receiverids = PlaMuprtrain.getReceiverids();
		}
		if (null != month && !"".equals(month)) {
			month = month.replace("-", "");
			condition += " and month='" + month + "'";
		}
		if (null != areaid && !"".equals(areaid)) {
			condition += " and areaid='" + areaid + "'";
		}
		if (null != corpid && !"".equals(corpid)) {
			condition += " and corpid='" + corpid + "'";
		}
		if (null != receiverids && receiverids.length() > 0) {
			condition += " and receiverid in (" + receiverids + ")";
		}
		return condition;
	}

	public List<PlaMuprtrain> findAll() throws Exception {
		List<PlaMuprtrain> list = plaMuprtrainDao.findAll();
		return list;
	}

	public void delete(PlaMuprtrain PlaMuprtrain) {
		if (null == PlaMuprtrain) {
			return;
		}
		plaMuprtrainDao.delete(PlaMuprtrain);
	}

	/**
	 * 到货查询
	 * 
	 * @param curCorpid
	 * @param PlaMuprtrain
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMuprtrain PlaMuprtrain)
			throws Exception {
		Map map = new HashMap();
		// 通过单位ID查找所对应收货人
		List dicCorprails = ctlCorpreceiverService
				.findCtlCorpreceiverByCorpid(curCorpid);
		// 收货人数组
		String receiverids = CommUtils.getSqlIn(dicCorprails, "receiverid");
		map.put("senders", receiverids);
		// 通过车站ID数组查询所有计划编号
		PlaMuprtrain.setReceiverids(receiverids);
		List list = this.findDistinct(PlaMuprtrain);
		map.put("list", list);
		return map;
	}

	/**
	 * 进行字符串型日期转换 如果是yyyy-mm格式，转为yyyymmdd;反之，则转为反之。
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaMuprtrain pmt) throws Exception {
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

	/**
	 * 批复计划页面显示翻译
	 * 
	 * @throws Exception
	 */
	public void bdPlaMuprDic(List<PlaMuprtrain> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMuprtrain obj = (PlaMuprtrain) iter.next();
			String areaid = obj.getAreaid();
			DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			String areaidDIC = area != null ? area.getShortname() : areaid;
			obj.setAreaidDIC(areaidDIC);
			String month = obj.getMonth();// BeanUtils.getProperty(obj,
			// "month");
			String monthEnd = month.substring(0, 4) + "年" + month.substring(4)
					+ "月";
			obj.setMonthEnd(monthEnd);

			String carkindid = obj.getCarkindid();
			DicCarkind carkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = carkind != null ? carkind.getShortname()
					: carkindid;
			obj.setCarkindidDIC(carkindidDIC);

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

	public void expExcel(PlaMuprtrain plaMuprtrain, HttpServletResponse response)
			throws Exception {
		// 查询详细计划数据
		List<PlaMuprtrain> list = this.find(plaMuprtrain);

		Map tabDefineMap = new LinkedHashMap();
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("月统销请车及批复计划", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "月统销请车及批复计划");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname",
				"checkstatusDIC", "startstationname", "endstationname",
				"sendername", "receivername", "carkindidDIC", "rwkindname",
				"requestcars", "requestamount", "acceptcars", "acceptamount",
				"acceptcarno" };
		headArr[1] = new String[] { "区域公司", "生产企业", "审批状态", "发站", "到站", "发货人",
				"收货人", "车种", "铁路品名", "原提车数", "原提吨数", "批准车数", "批准吨数", "批准要车分号" };
		// 区域公司 企业名称 审批状态 发站 到站 发货人 收货人 车种 铁路品名 原提车数 原提吨数 批准车数 批准吨数 批准要车分号
		sheetMap.put("headArr", headArr);

		// 批准要车分号数组
		String acceptcarnos = CommUtils.getRestrictionsIn(list, "acceptcarno");
		PlaDuprtrain plaDuprtrain = new PlaDuprtrain();
		plaDuprtrain.setAcceptcarnos(acceptcarnos);
		List<PlaDuprtrain> dups = plaDuprtrainService.find(plaDuprtrain);

		Map sheetMap1 = new HashMap();
		tabDefineMap.put("日统销请车及批复计划", sheetMap1);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap1.put("list", dups);
		// 标题
		sheetMap1.put("title", "日统销请车及批复计划");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		headArr = new String[2][];
		headArr[0] = new String[] { "requestdate", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "loadcars",
				"loadamount", "mtotalcars", "acceptcarno" };
		headArr[1] = new String[] { "提请日期", "原提车数", "原提吨数", "批准车数", "批准吨数",
				"已装车数", "已装吨数", "月累计完成车数", "批准要车分号" };
		// 装车日期 原提车数 原提吨数 批准车数 批准吨数 已装车数 已装吨数 月累计完成车数
		sheetMap1.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "月统销请车及批复计划");

	}

}
