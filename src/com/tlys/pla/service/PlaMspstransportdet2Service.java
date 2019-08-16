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
import com.tlys.pla.dao.PlaMspstransportdet2Dao;
import com.tlys.pla.model.PlaMspstransportdet2;

@Service
public class PlaMspstransportdet2Service extends _GenericService {

	@Autowired
	PlaMspstransportdet2Dao PlaMspstransportdet2Dao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;

	public PlaMspstransportdet2 load(Long id) {
		return PlaMspstransportdet2Dao.load(id);
	}

	public void save(PlaMspstransportdet2 PlaMspstransportdet2)
			throws Exception {
		PlaMspstransportdet2Dao.save(PlaMspstransportdet2);
	}

	public void update(PlaMspstransportdet2 PlaMspstransportdet2)
			throws Exception {
		PlaMspstransportdet2Dao.update(PlaMspstransportdet2);
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMspstransportdet2 PlaMspstransportdet2, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaMspstransportdet2Count(PlaMspstransportdet2);
		pageCtr.setTotalRecord(totalRecord);
		PlaMspstransportdet2Dao.find(PlaMspstransportdet2, pageCtr);

	}

	public List<PlaMspstransportdet2> findAll() {
		return PlaMspstransportdet2Dao.findAll();
	}

	public void delete(PlaMspstransportdet2 PlaMspstransportdet2) {
		if (null == PlaMspstransportdet2) {
			return;
		}
		PlaMspstransportdet2Dao.delete(PlaMspstransportdet2);
	}

	private int getPlaMspstransportdet2Count(
			final PlaMspstransportdet2 PlaMspstransportdet2) {
		int count = PlaMspstransportdet2Dao
				.getPlaMspstransportdet2Count(PlaMspstransportdet2);
		return count;
	}

	public List<PlaMspstransportdet2> find(
			PlaMspstransportdet2 PlaMspstransportdet2) throws Exception {
		return PlaMspstransportdet2Dao.find(PlaMspstransportdet2);
	}

	public void expExcel(PlaMspstransportdet2 plaMspstransportdet2,
			HttpServletResponse response) throws Exception {
		// 获取计划编号（planno必定存在于查询条件中:35000030-201201)
		String planno = plaMspstransportdet2.getPlanno();
		String[] ss = planno.split("-");
		// 查询汉字说明
		DicSinocorp dic = dicSinocorpService.load(ss[0]);
		// 年月份格式
		String month = ss[1].substring(0, 4) + "年" + ss[1].substring(4) + "月";
		// EXCEL名称、SHEET名称、内容抬头
		planno = dic.getShortname() + month + "非统销计划列表";
		// 查询详细计划数据
		List<PlaMspstransportdet2> list = this.find(plaMspstransportdet2);
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
				"badjustamount", "badjustcarnumber","aadjustamount", "aadjustcarnumber", "carkindidDIC", "startstationidDIC",
				"senderidDIC", "receivername", "endstationidDIC", "rkindname",
				"requestdate", "remarks" };
		headArr[1] = new String[] { "生产企业", "客户名称", "产品大类", "产品小类", "产品名称",
				"调整前数量", "调整前车数","调整后数量", "调整后车数", "车种", "发站", "发货人", "收货人", "到站", "铁路品名", "提请日期",
				"备注" };
		sheetMap.put("headArr", headArr);
		// 生产企业 客户名称 产品大类 产品小类 产品名称 全月数量 车数 车种 发站 发货人 收货人 到站 铁路品名 提请日期 备注
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, planno);

	}

	public List<PlaMspstransportdet2> findPlaMspstransportdet2ByIds(
			List<Long> planIds) {
		return PlaMspstransportdet2Dao.findPlaMspstransportdet2ByIds(planIds);
	}
}
