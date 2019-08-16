package com.tlys.pla.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.dao.PlaMspstransportdetDao;
import com.tlys.pla.model.PlaMspstransportdet;

@Service
public class PlaMspstransportdetService extends _GenericService {

	@Autowired
	PlaMspstransportdetDao PlaMspstransportdetDao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;

	public PlaMspstransportdet load(Long id) {
		return PlaMspstransportdetDao.load(id);
	}

	public void save(PlaMspstransportdet PlaMspstransportdet) throws Exception {
		PlaMspstransportdetDao.save(PlaMspstransportdet);
	}

	public void update(PlaMspstransportdet PlaMspstransportdet)
			throws Exception {
		PlaMspstransportdetDao.update(PlaMspstransportdet);
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMspstransportdet PlaMspstransportdet, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaMspstransportdetCount(PlaMspstransportdet);
		pageCtr.setTotalRecord(totalRecord);
		PlaMspstransportdetDao.find(PlaMspstransportdet, pageCtr);

	}

	public List<PlaMspstransportdet> findAll() {
		return PlaMspstransportdetDao.findAll();
	}

	public void delete(PlaMspstransportdet PlaMspstransportdet) {
		if (null == PlaMspstransportdet) {
			return;
		}
		PlaMspstransportdetDao.delete(PlaMspstransportdet);
	}

	private int getPlaMspstransportdetCount(
			final PlaMspstransportdet PlaMspstransportdet) {
		int count = PlaMspstransportdetDao
				.getPlaMspstransportdetCount(PlaMspstransportdet);
		return count;
	}

	public List<PlaMspstransportdet> find(
			PlaMspstransportdet PlaMspstransportdet) throws Exception {
		return PlaMspstransportdetDao.find(PlaMspstransportdet);
	}

	public void expExcel(PlaMspstransportdet plaMspstransportdet,
			HttpServletResponse response) throws Exception {
		// 获取计划编号（planno必定存在于查询条件中:35000030-201201)
		String planno = plaMspstransportdet.getPlanno();
		String[] ss = planno.split("-");
		// 查询汉字说明
		DicSinocorp dic = dicSinocorpService.load(ss[0]);
		// 年月份格式
		String month = ss[1].substring(0, 4) + "年" + ss[1].substring(4) + "月";
		// EXCEL名称、SHEET名称、内容抬头
		planno = dic.getShortname() + month + "非统销计划列表";
		// 查询详细计划数据
		List<PlaMspstransportdet> list = this.find(plaMspstransportdet);
		// 字典翻译
		dicMap.bdPlaMspsDic(list);
		Map tabDefineMap = new HashMap();
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put(planno, sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", planno);

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "corpshortname", "coustomershortname",
				"productcategoryidDIC", "productsecondidDIC", "prodductidDIC",
				"amount", "carnumber", "carkindidDIC", "startstationidDIC",
				"senderidDIC", "receivername", "endstationidDIC", "rkindname",
				"requestdate", "remarks" };
		headArr[1] = new String[] { "生产企业", "客户名称", "产品大类", "产品小类", "产品名称",
				"全月数量", "车数", "车种", "发站", "发货人", "收货人", "到站", "铁路品名", "提请日期",
				"备注" };
		sheetMap.put("headArr", headArr);
		// 生产企业 客户名称 产品大类 产品小类 产品名称 全月数量 车数 车种 发站 发货人 收货人 到站 铁路品名 提请日期 备注
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, planno);

	}

	public List<PlaMspstransportdet> findPlaMspstransportdetByIds(
			List<Long> planIds) {
		return PlaMspstransportdetDao.findPlaMspstransportdetByIds(planIds);
	}
}
