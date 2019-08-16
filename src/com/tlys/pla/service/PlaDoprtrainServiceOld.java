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
import com.tlys.pla.dao.PlaDoprtrainDaoOld;
import com.tlys.pla.model.PlaDoprtrainOld;
import com.tlys.sys.model.SysUser;

@Service
public class PlaDoprtrainServiceOld extends _GenericService {
	@Autowired
	PlaDoprtrainDaoOld plaDoprtrainDaoOld;
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

	/**
	 * 分页查询
	 * 
	 * @param PlaDoprtrainOld
	 * @return
	 * @throws Exception
	 */
	public void find(PlaDoprtrainOld plaDoprtrainOld, PageCtr pageCtr)
			throws Exception {
		rectDate(plaDoprtrainOld);
		int totalRecord = getPlaDoprtrainCount(plaDoprtrainOld);
		pageCtr.setTotalRecord(totalRecord);
		plaDoprtrainDaoOld.find(plaDoprtrainOld, pageCtr);
		bdPlaDoprDic(pageCtr.getRecords());
	}

	public int getPlaDoprtrainCount(final PlaDoprtrainOld plaDoprtrainOld) {
		int count = plaDoprtrainDaoOld.getPlaDoprtrainCount(plaDoprtrainOld);
		return count;
	}

	/**
	 * 根据当前用户身份及前台传过来的查询参数 生成本次查询所需要的corpid和areaid，并放入查询对象中
	 * 
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaDoprtrainOld buildAuthSch(PlaDoprtrainOld plaDoprtrainOld, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaDoprtrainOld) {
			plaDoprtrainOld = new PlaDoprtrainOld();
		}
		// 当前台没有传来查询参数
		if (null == plaDoprtrainOld.getAreaid()) {
			// 根据用户单位生成区域公司权限查询参数
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaDoprtrainOld.setAreaid(areaid);
		}
		if (null == plaDoprtrainOld.getCorpid()) {
			// 根据用户单位查询所属企业
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaDoprtrainOld.setCorpid(corpid);
		}
		return plaDoprtrainOld;
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

	public PlaDoprtrainOld load(String id) throws Exception {
		PlaDoprtrainOld plaDoprtrainOld = plaDoprtrainDaoOld.load(id);
		return plaDoprtrainOld;
	}

	public void save(PlaDoprtrainOld plaDoprtrainOld) throws Exception {
		plaDoprtrainDaoOld.save(plaDoprtrainOld);
	}

	public void update(PlaDoprtrainOld plaDoprtrainOld) throws Exception {
		plaDoprtrainDaoOld.updateEntity(plaDoprtrainOld, plaDoprtrainOld.getId());
	}

	/**
	 * @param PlaDoprtrainOld
	 */
	public List<PlaDoprtrainOld> find(PlaDoprtrainOld plaDoprtrainOld) throws Exception {
		List<PlaDoprtrainOld> trainList = plaDoprtrainDaoOld.find(plaDoprtrainOld);

		bdPlaDoprDic(trainList);
		return trainList;
	}

	public List<PlaDoprtrainOld> findAll() throws Exception {
		List<PlaDoprtrainOld> list = plaDoprtrainDaoOld.findAll();
		return list;
	}

	public void delete(PlaDoprtrainOld plaDoprtrainOld) {
		if (null == plaDoprtrainOld) {
			return;
		}
		plaDoprtrainDaoOld.delete(plaDoprtrainOld);
	}

	/**
	 * 进行字符串型日期转换 如果是yyyy-mm格式，转为yyyymmdd;反之，则转为反之。
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaDoprtrainOld pmt) throws Exception {
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
	private void bdPlaDoprDic(List<PlaDoprtrainOld> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// Map rwdepartmentMap = dicMap.getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaDoprtrainOld obj = (PlaDoprtrainOld) iter.next();
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
			/*
			 * BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC); String
			 * startstationid = BeanUtils .getProperty(obj, "startstationid");
			 * String startstationidDIC = (String) rwdepartmentMap
			 * .get(startstationid); BeanUtils.setProperty(obj,
			 * "startstationidDIC", startstationidDIC); String endstationid =
			 * BeanUtils.getProperty(obj, "endstationid"); String
			 * endstationidDIC = (String) rwdepartmentMap.get(endstationid);
			 * BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
			 */
		}
	}

	public void expExcel(PlaDoprtrainOld plaDoprtrainOld, HttpServletResponse response)
			throws Exception {
		// 查询详细计划数据
		List<PlaDoprtrainOld> list = this.find(plaDoprtrainOld);

		Map tabDefineMap = new LinkedHashMap();
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("其他产品日请车及批复计划", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "其他产品日请车及批复计划");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname", "loadtdate",
				"productkindDIC", "startstationname", "endstationname",
				"sendername", "receivername", "carkindidDIC", "rwkindname",
				"requestcars", "requestamount", "acceptcars", "acceptamount",
				"loadcars", "loadamount","acceptcarno" };
		headArr[1] = new String[] { "区域公司", "企业名称", "装车日期", "产品类别", "发站", "到站",
				"发货人", "收货人", "车种", "铁路品名", "原提车数", "原提吨数", "批准车数", "批准吨数",
				"已装车数", "已装吨数", "批准要车分号" };
		// 区域公司 企业名称 装车日期 产品类别 发站 到站 发货人 收货人 车种 铁路品名 原提车数 原提吨数 批准车数 批准吨数 已装车数
		// 已装吨数
		sheetMap.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "其他产品日请车及批复计划");

	}
}
