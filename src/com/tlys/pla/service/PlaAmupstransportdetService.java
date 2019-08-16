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
import com.tlys.dic.dao.ctl.CtlCorpsendwarehouseDao;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.ctl.CtlCorpsendwarehouse;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.pla.dao.PlaAmupstransportdetDao;
import com.tlys.pla.model.PlaAmupstransportdet;

@Service
public class PlaAmupstransportdetService extends _GenericService {

	@Autowired
	PlaAmupstransportdetDao plaAmupstransportdetDao;
	@Autowired
	CtlCorpsendwarehouseDao ctlCorpsendwarehouseDao;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicMap dicMap;

	public PlaAmupstransportdet load(Long id) {
		return plaAmupstransportdetDao.load(id);
	}

	public void save(PlaAmupstransportdet PlaAmupstransportdet) throws Exception {
		plaAmupstransportdetDao.save(PlaAmupstransportdet);
	}

	public void update(PlaAmupstransportdet PlaAmupstransportdet) throws Exception {
		plaAmupstransportdetDao.update(PlaAmupstransportdet);
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaAmupstransportdet PlaAmupstransportdet, PageCtr pageCtr) throws Exception {
		int totalRecord = getPlaAmupstransportdetCount(PlaAmupstransportdet);
		pageCtr.setTotalRecord(totalRecord);
		plaAmupstransportdetDao.find(PlaAmupstransportdet, pageCtr);

	}

	public List<PlaAmupstransportdet> find(PlaAmupstransportdet PlaAmupstransportdet) throws Exception {
		return plaAmupstransportdetDao.find(PlaAmupstransportdet);
	}

	public List<PlaAmupstransportdet> findAll() {
		return plaAmupstransportdetDao.findAll();
	}

	public void delete(PlaAmupstransportdet PlaAmupstransportdet) {
		if (null == PlaAmupstransportdet) {
			return;
		}
		plaAmupstransportdetDao.delete(PlaAmupstransportdet);
	}

	private int getPlaAmupstransportdetCount(final PlaAmupstransportdet PlaAmupstransportdet) {
		int count = plaAmupstransportdetDao.getPlaAmupstransportdetCount(PlaAmupstransportdet);
		return count;
	}

	public List<CtlCorpsendwarehouse> findCtlCorpsendwarehouse(String corpid) {
		return ctlCorpsendwarehouseDao.findCtlCorpsendwarehouse(corpid);
	}

	public void expExcel(PlaAmupstransportdet plaAmupstransportdet,
			HttpServletResponse response) throws Exception {
		// 获取计划编号（planno必定存在于查询条件中:35000030-201201)
		String planno = plaAmupstransportdet.getPlanno();
		String[] ss = planno.split("-");
		// 查询汉字说明
		DicAreacorp dic = dicAreacorpService.load(ss[0]);
		// 年月份格式
		String month = ss[1].substring(0, 4) + "年" + ss[1].substring(4) + "月";
		// EXCEL名称、SHEET名称、内容抬头
		planno = dic.getShortname() + month + "统销调整计划列表";
		// 查询详细计划数据
		List<PlaAmupstransportdet> list = this.find(plaAmupstransportdet);
		// 字典翻译
		dicMap.bdPlaMupsDic(list);
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
				"saler", "saledepartmentDIC", "remarks" };
		headArr[1] = new String[] { "生产企业", "客户名称", "产品大类", "产品小类", "产品名称",
				"全月数量", "车数", "车种", "发站", "发货人", "收货人", "到站", "铁路品名", "客户经理",
				"销售机构", "备注" };
		sheetMap.put("headArr", headArr);
		// 生产企业 客户名称 产品大类 产品小类 产品名称 全月数量 车数 车种 发站 发货人 收货人 到站 铁路品名 客户经理 销售机构 备注
		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, planno);
		
	}
}
