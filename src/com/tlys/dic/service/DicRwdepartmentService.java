/**
 * @author 郭建军
 */
package com.tlys.dic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicRwdepartmentDao;
import com.tlys.dic.model.DicRwdepartment;

@Service
public class DicRwdepartmentService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRwdepartmentDao DicRwdepartmentDao;
	@Autowired
	DicMap dicMap;
	@Autowired
	CacheService cacheService;

	/**
	 * 查找所有信息
	 * 
	 * @return
	 */
	public List<DicRwdepartment> findAll() {
		String key = "DIC_RW_DEPARTMENT_LIST";
		List<DicRwdepartment> rwdepartmentList = (List<DicRwdepartment>) cacheService.get(key);
		if (null == rwdepartmentList) {
			rwdepartmentList = new ArrayList<DicRwdepartment>();
		}
		if (rwdepartmentList.isEmpty()) {
			rwdepartmentList.addAll(DicRwdepartmentDao.findAlls("shortname"));
			cacheService.put(key, rwdepartmentList);
		}
		return rwdepartmentList;
	}

	/**
	 * 翻译后列表信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicRwdepartment> findDICAll() throws Exception {
		List<DicRwdepartment> list = DicRwdepartmentDao.findAll();
		dicMap.bdDicRwdepartmentDic(list);
		return list;
	}

	/**
	 * 根据ID查找单个信息
	 * 
	 * @return
	 */
	public DicRwdepartment load(String id) {
		return DicRwdepartmentDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新信息
	 * 
	 * @param DicRwdepartment
	 * @param isNew
	 */
	public void save(DicRwdepartment dicRwdepartment, boolean isNew) {
		if (true == isNew) {
			dicRwdepartment.setCreatedtime(new Date());
			DicRwdepartmentDao.saveOrUpdate(dicRwdepartment);
		} else {
			DicRwdepartmentDao.updateEntity(dicRwdepartment, dicRwdepartment.getRwdepaid());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		DicRwdepartmentDao.delete(id);
		setAlterFlag();
	}

	public int findTotalCount(String condition) {

		return DicRwdepartmentDao.findTotalCount(condition, null);
	}

	public List<DicRwdepartment> find(DicRwdepartment dicRwdepartment) {
		return DicRwdepartmentDao.find(dicRwdepartment);
	}

	public List<DicRwdepartment> findByPycode(String pycode) {
		return DicRwdepartmentDao.findByPycode(pycode);
	}

	public List<DicRwdepartment> findByType(String ids, String type) throws Exception {
		String hql = " where " + type + " in(" + CommUtils.addQuotes(ids) + ")";
		List list = DicRwdepartmentDao.findAll(hql);
		dicMap.bdDicRwdepartmentDic(list);
		return list;
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRwdepartment");
	}

	public void expExcel(List<DicRwdepartment> list, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("铁路生产单位列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "铁路生产单位列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "rwdepaid", "fullname", "shortname", "shrinkname", "bureauidDIC", "tpyeidDIC",
				"parentidDIC", "provinceidDIC", "address", "dutyperson", "telephones", "fax", "contactperson",
				"isactiveDIC", "telegramid", "hasaeiDIC", "israilstnDIC", "timscode", "isboundaryDIC",
				"nextbureauidDIC", "inflagDIC", "outflagDIC", "raillinename", "pycode", "description" };
		headArr[1] = new String[] { "部门ID", "全称", "简称", "缩写", "路局", "类型", "父层名称", "省份", "地址", "负责人", "联系电话", "传真",
				"联系人", "是否启用", "电报码", "有否AEI", "是否过轨站", "TIMS码", "是否分界站", "下一路局", "进站标示", "出站标示", "所在线路", "拼音码", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "铁路生产单位列表");

	}

	public List<DicRwdepartment> findRwDepartment(final String pycode, final String typeid) {
		return DicRwdepartmentDao.findRwDepartment(pycode, typeid);
	}
}
