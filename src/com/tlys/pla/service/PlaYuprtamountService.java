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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaYuprtamountDao;
import com.tlys.pla.model.PlaYuprtamount;
import com.tlys.sys.model.SysUser;

@Service
public class PlaYuprtamountService extends _GenericService {
	@Autowired
	PlaYuprtamountDao plaYuprtamountDao;
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
	 * 根据当前用户身份及前台传过来的查询参数 生成本次查询所需要的corpid和areaid，并放入查询对象中
	 * 
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaYuprtamount buildAuthSch(PlaYuprtamount plaYuprtamount, SysUser curUser) throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaYuprtamount) {
			plaYuprtamount = new PlaYuprtamount();
		}
		// 当前台没有传来查询参数
		if (null == plaYuprtamount.getAreaid()) {
			// 根据用户单位生成区域公司权限查询参数
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaYuprtamount.setAreaid(areaid);
		}
		if (null == plaYuprtamount.getCorpid()) {
			// 根据用户单位查询所属企业
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaYuprtamount.setCorpid(corpid);
		}
		return plaYuprtamount;
	}

	/**
	 * <li>根据用户单位获取区域公司ID</li> <li>当前用户属于总部用户时：返回null,表示不按区域公司进行过滤，查询的区域公司列表为全部</li>
	 * <li>当前用户属于区域公司时：返回用户当前所在区域公司 的ID，查询的区域公司只返回一个</li> <li>
	 * 当前用户属于企业时：返回用户当前企业所对应的区域公司的ID，查询的区域公司也只返回一个</li> <li>
	 * 当前用户属于驻厂办时：返回当前单位对应的区域公司ID，查询的区域公司列表为一个</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdAreaid(String curCorpid, String curCorptab) throws Exception {
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
				DicSinodepartment obj = dicSinodepartmentService.load(curCorpid);
				areaid = obj.getParentid();
			}
		}
		return areaid;
	}

	/**
	 * <li>根据用户单位获取企业ID</li> <li>总部用户：null</li> <li>
	 * 区域公司用户：null，但在查询列表时，应考虑当前所在的区域公司</li> <li>企业：当前所在的企业ID</li> <li>
	 * 驻厂办：当前单位所对应的企业ID</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdCorpid(String curCorpid, String curCorptab) throws Exception {
		String corpid = "99999999";// 当当前用户类型为null时，没有查询条件
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// 总部机关
				corpid = null;
			} else if ("1".equals(curCorptab)) {// 区域公司
				corpid = null;
			} else if ("2".equals(curCorptab)) {// 企业
				corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// 驻厂办,则要查找当前单位对应的corpid
				List<CtlLiaisonocorp> ctlLiaisonocorpList = ctlLiaisonocorpService.findAll();
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

	public PlaYuprtamount load(String id) throws Exception {
		PlaYuprtamount plaYuprtamount = plaYuprtamountDao.load(id);
		return plaYuprtamount;
	}

	public void save(PlaYuprtamount plaYuprtamount) throws Exception {
		plaYuprtamountDao.save(plaYuprtamount);
	}

	public void update(PlaYuprtamount plaYuprtamount) throws Exception {
		plaYuprtamountDao.updateEntity(plaYuprtamount, plaYuprtamount.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @param PlaYuprtamount
	 * @return
	 * @throws Exception
	 */
	public void find(PlaYuprtamount plaYuprtamount, PageCtr pageCtr) throws Exception {
		// rectDate(plaYuprtamount);
		int totalRecord = getPlaYuprtamountCount(plaYuprtamount);
		pageCtr.setTotalRecord(totalRecord);
		plaYuprtamountDao.find(plaYuprtamount, pageCtr);
		bdPlaDic(pageCtr.getRecords());

	}

	public Object[] findSum(PlaYuprtamount plaYuprtamount) {
		List olist = plaYuprtamountDao.findSum(plaYuprtamount);
		Object[] oarr = null;
		if (null != olist && olist.size() > 0) {
			oarr = (Object[]) olist.get(0);
		} else {
			oarr = new String[] { "", "", "" };
		}
		return oarr;
	}

	public List<Object[]> findYuprtAmountSum(PlaYuprtamount plaYuprtamount) {
		return plaYuprtamountDao.findYuprtAmountSum(plaYuprtamount);
	}

	public int getPlaYuprtamountCount(final PlaYuprtamount plaYuprtamount) {
		int count = plaYuprtamountDao.getPlaYuprtamountCount(plaYuprtamount);
		return count;
	}

	public List<PlaYuprtamount> findAll() throws Exception {
		List<PlaYuprtamount> list = plaYuprtamountDao.findAll();
		return list;
	}

	public void delete(PlaYuprtamount plaYuprtamount) {
		if (null == plaYuprtamount) {
			return;
		}
		plaYuprtamountDao.delete(plaYuprtamount);
	}

	/**
	 * 进行字符串型日期转换 如果是yyyy-mm格式，转为yyyymmdd;反之，则转为反之。
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	/*
	 * private void rectDate(PlaYuprtamount pmt) throws Exception{ if(null==pmt)
	 * return; String month = pmt.getMonth(); String monthStr =
	 * pmt.getMonthStr();
	 * 
	 * if(null != month){ monthStr = FormatUtil.rectDate59(month);
	 * pmt.setMonthStr(monthStr); } if(null != monthStr){ month =
	 * FormatUtil.rectDate59(monthStr); pmt.setMonth(month); } }
	 */

	/**
	 * 计划页面显示翻译
	 * 
	 * @throws Exception
	 */
	private void bdPlaDic(List<PlaYuprtamount> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map goodsMap = dicMap.getDicGoodsMap();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaYuprtamount obj = (PlaYuprtamount) iter.next();
			String areaid = obj.getAreaid();
			String areaidDIC = "";
			if (areaid.startsWith("VSMF_")) {
				// 从xml中读取
			} else {
				DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
				areaidDIC = area.getShortname();
			}

			obj.setAreaidDIC(areaidDIC);

			String rwkindid = obj.getRwkindid();
			String rkindname = obj.getRkindname();
			if (null == rkindname) {
				DicGoods dg = (DicGoods) goodsMap.get(rwkindid);
				if (null != dg) {
					rkindname = dg.getPmhz();
					obj.setRkindname(rkindname);
				}
			}
		}
	}

	/**
	 * @param PlaDtrtrain
	 */
	public List<PlaYuprtamount> find(PlaYuprtamount plaYuprtamount) throws Exception {
		List<PlaYuprtamount> list = plaYuprtamountDao.find(plaYuprtamount);
		bdPlaDic(list);
		return list;
	}

	public void expExcel(PlaYuprtamount plaYuprtamount, HttpServletResponse response) throws Exception {
		// 查询详细计划数据
		List<PlaYuprtamount> list = this.find(plaYuprtamount);
		String year = plaYuprtamount.getYear();
		Map tabDefineMap = new LinkedHashMap();
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put(year + "年度铁路运量指标", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", year + "年度铁路运量指标");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname", "productcategoryname", "productcategoryname",
				"rkindname", "amount", "lyamount", "varyamount" };
		headArr[1] = new String[] { "区域公司", "企业名称", "产品大类", "产品小类", "铁路品名", "数量", "上年度数量", "比上年增减" };

		// 区域公司 企业名称 产品大类 产品小类 铁路品名 数量 上年度数量 比上年增减
		sheetMap.put("headArr", headArr);
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, year + "年度铁路运量指标");
	}

	public List<PlaYuprtamount> findPlaYuprtamount(final String year, final List<String> corpIdList,
			final List<String> productCategoryIdList, final List<String> productSecondIdList,
			final List<String> rwkindIdList) {
		return plaYuprtamountDao.findPlaYuprtamount(year, corpIdList, productCategoryIdList, productSecondIdList,
				rwkindIdList);
	}

	public List<Object[]> findPlaYuprtamountSubTotal(String year) {
		return plaYuprtamountDao.findPlaYuprtamountSubTotal(year);
	}

	public Object[] findSum(final String year) {
		return plaYuprtamountDao.findSum(year);
	}
}
