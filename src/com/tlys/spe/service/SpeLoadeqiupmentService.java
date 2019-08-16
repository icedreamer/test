package com.tlys.spe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.ExportExcel;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.dao.SpeLoadeqiupmentDao;
import com.tlys.spe.model.SpeLoadeqiupment;

@Service
public class SpeLoadeqiupmentService extends _GenericService<SpeLoadeqiupment> {

	@Autowired
	SpeLoadeqiupmentDao speLoadeqiupmentDao;
	@Autowired
	DicMap dicMap;
	@Autowired
	ExportExcel exportExcel;

	public SpeLoadeqiupment getSpeLoadeqiupment(String id) {
		return speLoadeqiupmentDao.load(id);
	}

	public void save(SpeLoadeqiupment speLoadeqiupment) {
		if (null == speLoadeqiupment.getEquid()
				|| "".equals(speLoadeqiupment.getEquid())) {
			speLoadeqiupment.setEquid(CommUtils.getSeqStr(speLoadeqiupmentDao
					.getSeq(), 6));
		}
		speLoadeqiupmentDao.save(speLoadeqiupment);
	}

	public void update(SpeLoadeqiupment speLoadeqiupment) {
		speLoadeqiupmentDao.updateEntity(speLoadeqiupment, speLoadeqiupment
				.getEquid());
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speLoadeqiupment
	 * @param pageCtr
	 * @throws Exception
	 */
	public void findSpeLoadeqiupment(SpeLoadeqiupment speLoadeqiupment,
			PageCtr pageCtr) throws Exception {
		int totalRecord = getSpeLoadeqiupmentCount(speLoadeqiupment);
		pageCtr.setTotalRecord(totalRecord);
		speLoadeqiupmentDao.findSpeLoadeqiupment(speLoadeqiupment, pageCtr);
		dicMap.bdSpeLoadeqiupment(pageCtr.getRecords());
	}

	public List<SpeLoadeqiupment> findSpeLoadeqiupment()  throws Exception {
		List<SpeLoadeqiupment> list = speLoadeqiupmentDao.findAll();
		dicMap.bdSpeLoadeqiupment(list);
		return list;
	}

	public void delete(SpeLoadeqiupment speLoadeqiupment) {
		if (null == speLoadeqiupment) {
			return;
		}
		speLoadeqiupmentDao.delete(speLoadeqiupment);
	}

	public int getSpeLoadeqiupmentCount(final SpeLoadeqiupment speLoadeqiupment) {
		int count = speLoadeqiupmentDao
				.getSpeLoadeqiupmentCount(speLoadeqiupment);
		return count;
	}

	public void expExcel(List<SpeLoadeqiupment> speLoadeqiupment,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("企业铁路装卸设施列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", speLoadeqiupment);
		// 标题
		sheetMap.put("title", "企业铁路装卸设施列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "equid", "linename", "corpid", "corpname",
				"equname", "crwidDIC", "kindDIC", "pcategoryname",
				"psecondkindname", "positions", "dayjobs", "dayability",
				"meastypeidDIC", "loaddepartmnet", "cardispatcher", "layouttype",
				"distancetohouse", "internallinename", "stationid",
				"stationname", "bureauid", "bureauname", "distancetostation",
				"remarks" };
		headArr[1] = new String[] { "装卸设施ID", "线路名称", "企业ID", "企业名称",
				"铁路装卸点名称", "专用线ID", "装卸类别", "产品大类名称", "产品小类名称", "装卸车货位数",
				"日作业批次", "日装卸能力", "计量方式", "铁路装卸作业部门", "厂内铁路调车作业部门",
				"铁路装卸点与成品库房储罐布局方式", "铁路装卸点与成品库房储罐距离", "所在铁路专用线", "办理车站ID",
				"办理车站名称", "路局ID", "路局名称", "铁路装卸车点与办理站距离", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "企业铁路装卸设施列表");

	}

}
