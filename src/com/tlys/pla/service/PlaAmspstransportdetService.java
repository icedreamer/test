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
import com.tlys.pla.dao.PlaAmspstransportdetDao;
import com.tlys.pla.model.PlaAmspstransportdet;

@Service
public class PlaAmspstransportdetService extends _GenericService {

	@Autowired
	PlaAmspstransportdetDao PlaAmspstransportdetDao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;

	public PlaAmspstransportdet load(Long id) {
		return PlaAmspstransportdetDao.load(id);
	}

	public void save(PlaAmspstransportdet PlaAmspstransportdet)
			throws Exception {
		PlaAmspstransportdetDao.save(PlaAmspstransportdet);
	}

	public void update(PlaAmspstransportdet PlaAmspstransportdet)
			throws Exception {
		PlaAmspstransportdetDao.update(PlaAmspstransportdet);
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaAmspstransportdet PlaAmspstransportdet,
			PageCtr<PlaAmspstransportdet> pageCtr) throws Exception {
		int totalRecord = getPlaAmspstransportdetCount(PlaAmspstransportdet);
		pageCtr.setTotalRecord(totalRecord);
		PlaAmspstransportdetDao.find(PlaAmspstransportdet, pageCtr);

	}

	public List<PlaAmspstransportdet> findAll() {
		return PlaAmspstransportdetDao.findAll();
	}

	public void delete(PlaAmspstransportdet PlaAmspstransportdet) {
		if (null == PlaAmspstransportdet) {
			return;
		}
		PlaAmspstransportdetDao.delete(PlaAmspstransportdet);
	}

	private int getPlaAmspstransportdetCount(
			final PlaAmspstransportdet PlaAmspstransportdet) {
		int count = PlaAmspstransportdetDao
				.getPlaAmspstransportdetCount(PlaAmspstransportdet);
		return count;
	}

	public List<PlaAmspstransportdet> find(
			PlaAmspstransportdet PlaAmspstransportdet) throws Exception {
		return PlaAmspstransportdetDao.find(PlaAmspstransportdet);
	}

	public void expExcel(PlaAmspstransportdet plaAmspstransportdet,
			HttpServletResponse response) throws Exception {
		// 获取计划编号（planno必定存在于查询条件中:35000030-201201)
		String planno = plaAmspstransportdet.getPlanno();
		String[] ss = planno.split("-");
		// 查询汉字说明
		DicSinocorp dic = dicSinocorpService.load(ss[0]);
		// 年月份格式
		String month = ss[1].substring(0, 4) + "年" + ss[1].substring(4) + "月";
		// EXCEL名称、SHEET名称、内容抬头
		planno = dic.getShortname() + month + "非统销调整计划列表";
		// 查询详细计划数据
		List<PlaAmspstransportdet> list = this.find(plaAmspstransportdet);
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
}
