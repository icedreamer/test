/**
 * @author 郭建军
 */
package com.tlys.dic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicRwbureauDao;
import com.tlys.dic.dao.DicRwdepartmentDao;
import com.tlys.dic.dao.DicRwdepartypeDao;
import com.tlys.dic.model.DicRwbureau;

@Service
public class DicRwbureauService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRwbureauDao DicRwbureauDao;
	@Autowired
	DicRwdepartypeDao dicRwdepartypeDao;
	@Autowired
	DicRwdepartmentDao dicRwdepartmentDao;
	@Autowired
	DicMap dicMap;

	/**
	 * 查找所有铁路局信息
	 * 
	 * @return
	 */
	public List<DicRwbureau> findAll() {
		return DicRwbureauDao.findAlls("shortname");
	}

	/**
	 * 根据ID查找单个铁路局信息
	 * 
	 * @return
	 */
	public DicRwbureau load(String id) {
		return DicRwbureauDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新铁路局信息
	 * 
	 * @param DicRwbureau
	 * @param isNew
	 */
	public void save(DicRwbureau DicRwbureau, boolean isNew) {
		if (true == isNew) {
			DicRwbureauDao.saveOrUpdate(DicRwbureau);
		} else {
			DicRwbureauDao.updateEntity(DicRwbureau, DicRwbureau.getBureauid());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个铁路局信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		DicRwbureauDao.delete(id);
		setAlterFlag();
	}

	public int findTotalCount(String condition) {

		return DicRwbureauDao.findTotalCount(condition, null);
	}

	public List<DicRwbureau> find(DicRwbureau dicRwbureau) {
		return DicRwbureauDao.find(dicRwbureau);
	}

	public Map<String, List> findSuborgsMap(String id) {
		HashMap subMap = new HashMap();
		List corpList = dicRwdepartypeDao.findAll();
		List depList = dicRwdepartmentDao.findByBureauid(id);
		subMap.put("corp", corpList);
		subMap.put("dep", depList);
		return subMap;
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRwbureau");
	}

	public void expExcel(List<DicRwbureau> DicRwbureaus,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("铁路局列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", DicRwbureaus);
		// 标题
		sheetMap.put("title", "铁路局列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "bureauid", "fullname", "shortname",
				"shrinkname", "address", "provinceidDIC", "dutyperson",
				"telephones", "fax", "contactperson", "orderno", "pycode",
				"rwcode", "isactiveDIC", "description" };
		headArr[1] = new String[] { "路局ID", "全称", "简称", "缩写", "地址", "省份",
				"负责人", "联系电话", "传真", "联系人", "顺序号", "拼音码", "铁路代码", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "铁路局列表");

	}
}
