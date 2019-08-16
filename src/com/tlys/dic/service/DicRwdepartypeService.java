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
import com.tlys.dic.model.DicRwdepartype;

@Service
public class DicRwdepartypeService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRwbureauDao dicRwbureauDao;
	@Autowired
	DicRwdepartypeDao dicRwdepartypeDao;
	@Autowired
	DicRwdepartmentDao dicRwdepartmentDao;
	@Autowired
	DicMap dicMap;

	/**
	 * 查找所有铁路类型信息
	 * 
	 * @return
	 */
	public List<DicRwdepartype> findAll() {
		return dicRwdepartypeDao.findAll();
	}

	/**
	 * 根据ID查找单个铁路类型信息
	 * 
	 * @return
	 */
	public DicRwdepartype load(String id) {
		return dicRwdepartypeDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新铁路类型信息
	 * 
	 * @param DicRwdepartype
	 * @param isNew
	 */
	public void save(DicRwdepartype DicRwdepartype, boolean isNew) {
		if (true == isNew) {
			dicRwdepartypeDao.saveOrUpdate(DicRwdepartype);
		} else {
			dicRwdepartypeDao.updateEntity(DicRwdepartype, DicRwdepartype
					.getTpyeid());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个铁路类型信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicRwdepartypeDao.delete(id);
		setAlterFlag();
	}

	public int findTotalCount(String condition) {

		return dicRwdepartypeDao.findTotalCount(condition, null);
	}

	public List<DicRwdepartype> find(DicRwdepartype dicRwdepartype) {
		return dicRwdepartypeDao.find(dicRwdepartype);
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRwdepartype");
	}

	public Map<String, List> findSuborgsMap(String id) {
		HashMap subMap = new HashMap();
		List corpList = dicRwbureauDao.findAll();
		List depList = dicRwdepartmentDao.findByTypeid(id);
		subMap.put("corp", corpList);
		subMap.put("dep", depList);
		return subMap;
	}

	public void expExcel(List<DicRwdepartype> DicRwdepartypes,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("铁路类型列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", DicRwdepartypes);
		// 标题
		sheetMap.put("title", "铁路类型列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "tpyeid", "name", "isactiveDIC",
				"description" };
		headArr[1] = new String[] { "类型ID", "名称", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "铁路类型列表");

	}
}
