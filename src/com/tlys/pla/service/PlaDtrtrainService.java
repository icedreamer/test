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
import com.tlys.pla.dao.PlaDtrtrainDao;
import com.tlys.pla.model.PlaDtrtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaDtrtrainService extends _GenericService {
	@Autowired
	PlaDtrtrainDao plaDtrtrainDao;
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
	
	
	public PlaDtrtrain buildAuthSch(PlaDtrtrain plaDtrtrain)
			throws Exception {

		if (null == plaDtrtrain) {
			plaDtrtrain = new PlaDtrtrain();
		}
		
		if (null == plaDtrtrain.getCorpids() || "".equals(plaDtrtrain.getCorpids())) {
			String corpids = CommUtils.getCorpIds(); 
			plaDtrtrain.setCorpids(corpids);
		}
		return plaDtrtrain;
	}


	/**
	 * 根据当前用户身份及前台传过来的查询参数 生成本次查询所需要的corpid和areaid，并放入查询对象中
	 * 此方法已废止
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaDtrtrain buildAuthSchOld(PlaDtrtrain plaDtrtrain, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaDtrtrain) {
			plaDtrtrain = new PlaDtrtrain();
		}
		// 当前台没有传来查询参数
		if (null == plaDtrtrain.getAreaid()) {
			// 根据用户单位生成区域公司权限查询参数
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaDtrtrain.setAreaid(areaid);
		}
		if (null == plaDtrtrain.getCorpid()) {
			// 根据用户单位查询所属企业
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaDtrtrain.setCorpid(corpid);
		}
		return plaDtrtrain;
	}

	/**
	 * 根据用户单位获取区域公司ID 匀不存在进返回null不对单位进行过滤
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
	 * 根据用户单位获取企业ID 匀不存在进返回null不对单位进行过滤
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

	public PlaDtrtrain load(String id) throws Exception {
		PlaDtrtrain plaDtrtrain = plaDtrtrainDao.load(id);
		return plaDtrtrain;
	}

	public void save(PlaDtrtrain plaDtrtrain) throws Exception {
		plaDtrtrainDao.save(plaDtrtrain);
	}

	public void update(PlaDtrtrain plaDtrtrain) throws Exception {
		plaDtrtrainDao.updateEntity(plaDtrtrain, plaDtrtrain.getId());
	}

	/**
	 * @param PlaDtrtrain
	 */
	public List<PlaDtrtrain> find(PlaDtrtrain plaDtrtrain) throws Exception {
		List<PlaDtrtrain> trainList = plaDtrtrainDao.find(plaDtrtrain);
		bdPlaDtrDic(trainList);
		return trainList;
	}

	/**
	 * 分页查询
	 * 
	 * @param PlaDtrtrain
	 * @return
	 * @throws Exception
	 */
	public void find(PlaDtrtrain plaDtrtrain, PageCtr pageCtr) throws Exception {
		rectDate(plaDtrtrain);
		int totalRecord = getPlaDtrtrainCount(plaDtrtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaDtrtrainDao.find(plaDtrtrain, pageCtr);
		bdPlaDtrDic(pageCtr.getRecords());
	}

	public int getPlaDtrtrainCount(final PlaDtrtrain plaDtrtrain) {
		int count = plaDtrtrainDao.getPlaDtrtrainCount(plaDtrtrain);
		return count;
	}

	/**
	 * @param plaDtrtrain
	 */
	public List<PlaDtrtrain> findDistinct(PlaDtrtrain plaDtrtrain)
			throws Exception {
		String hql = "select distinct new PlaDtrtrain(areaid,month) from "
				+ PlaDtrtrain.class.getName();
		hql += getCondition(plaDtrtrain) + " order by month desc";
		List<PlaDtrtrain> trainList = plaDtrtrainDao.find(hql);
		// dicMap.bdPlaMuprDic(trainList);
		return trainList;
	}

	private String getCondition(PlaDtrtrain plaDtrtrain) {
		String condition = " where 1=1 ";
		String areaid = null;
		String corpid = null;
		String month = null;
		// 收货人
		String receiverids = null;
		if (null != plaDtrtrain) {
			month = plaDtrtrain.getMonth();
			areaid = plaDtrtrain.getAreaid();
			corpid = plaDtrtrain.getCorpid();
			receiverids = plaDtrtrain.getReceiverids();
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

	public List<PlaDtrtrain> findAll() throws Exception {
		List<PlaDtrtrain> list = plaDtrtrainDao.findAll();
		return list;
	}

	public void delete(PlaDtrtrain plaDtrtrain) {
		if (null == plaDtrtrain) {
			return;
		}
		plaDtrtrainDao.delete(plaDtrtrain);
	}

	/**
	 * 进行字符串型日期转换 如果是yyyy-mm格式，转为yyyymmdd;反之，则转为反之。
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaDtrtrain pmt) throws Exception {
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
	private void bdPlaDtrDic(List<PlaDtrtrain> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// Map rwdepartmentMap = dicMap.getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaDtrtrain obj = (PlaDtrtrain) iter.next();
			// String areaid = obj.getAreaid();
			// DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			// String areaidDIC = area != null ? area.getShortname() : areaid;
			// obj.setAreaidDIC(areaidDIC);
			String month = obj.getMonth();// BeanUtils.getProperty(obj,
			// "month");
			String monthEnd = month.substring(0, 4) + "年" + month.substring(4)
					+ "月";
			// BeanUtils.setProperty(obj, "monthEnd", monthEnd);
			obj.setMonthEnd(monthEnd);
			String carkindid = obj.getCarkindid();// BeanUtils.getProperty(obj,
			// "carkindid");
			DicCarkind carkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = carkind != null ? carkind.getShortname()
					: carkindid;
			obj.setCarkindidDIC(carkindidDIC);
			obj.setAreaidDIC(((DicAreacorp) areaMap.get(obj.getAreaid()))
					.getShortname());
			// BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC);
			// String startstationid = BeanUtils.getProperty(obj,
			// "startstationid");
			// String startstationidDIC = (String)
			// rwdepartmentMap.get(startstationid);
			// BeanUtils.setProperty(obj, "startstationidDIC",
			// startstationidDIC);
			// String endstationid = BeanUtils.getProperty(obj, "endstationid");
			// String endstationidDIC = (String)
			// rwdepartmentMap.get(endstationid);
			// BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
		}
	}

	public void expExcel(PlaDtrtrain plaDtrtrain, HttpServletResponse response)
			throws Exception {
		String kind = "日非统销";
		if ("1".equals(plaDtrtrain.getProductkind())) {
			kind = "日统销";
		}
		// 查询详细计划数据
		List<PlaDtrtrain> list = this.find(plaDtrtrain);

		Map tabDefineMap = new LinkedHashMap();
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put(kind + "追加及批复计划", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", kind + "追加及批复计划");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname", "loadtdate",
				"startstationname", "endstationname", "sendername",
				"receivername", "carkindidDIC", "rwkindname", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "loadcars",
				"loadamount", "acceptcarno" };
		headArr[1] = new String[] { "区域公司", "企业名称", "装车日期", "发站", "到站", "发货人",
				"收货人", "车种", "铁路品名", "原提车数", "原提吨数", "批准车数", "批准吨数", "已装车数",
				"已装吨数", "批准要车分号"  };

		// 企业简称 装车日期 发站 到站 发货人 收货人 车种 铁路品名 原提车数 原提吨数 批准车数 批准吨数 已装车数 已装吨数
		sheetMap.put("headArr", headArr);
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, kind + "追加及批复计划");

	}

}
