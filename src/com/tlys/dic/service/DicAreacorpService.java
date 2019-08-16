/**
 * 
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
import com.tlys.dic.dao.DicAreacorpDao;
import com.tlys.dic.dao.DicSinocorpDao;
import com.tlys.dic.dao.DicSinodepartmentDao;
import com.tlys.dic.model.DicAreacorp;

/**
 * @author fengym
 * 
 */
@Service
public class DicAreacorpService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicAreacorpDao dicAreacorpDao;

	@Autowired
	DicSinocorpDao dicSinocorpDao;

	@Autowired
	DicSinodepartmentDao dicSinodepartmentDao;

	@Autowired
	DicMap dicMap;

	public List<DicAreacorp> findAll() throws Exception {
		return dicAreacorpDao.findAll(" order by areaid");
	}

	public List<DicAreacorp> findByAreaid(DicAreacorp dac) throws Exception {
		List<DicAreacorp> acList = dicAreacorpDao.findById(dac);
		return acList;
	}

	public List<DicAreacorp> findDICAll() throws Exception {
		List<DicAreacorp> acList = dicAreacorpDao.findAll(" order by areaid");
		dicMap.bdProv(acList);
		return acList;
	}

	public List<DicAreacorp> find(DicAreacorp dac) throws Exception {
		List<DicAreacorp> acList = dicAreacorpDao.find(dac);
		dicMap.bdProv(acList);
		return acList;
	}

	public DicAreacorp load(String id) {
		return dicAreacorpDao.load(id);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicAreacorp,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicAreacorp dicAreacorp, boolean isNew) {
		if (isNew) {
			dicAreacorpDao.saveOrUpdate(dicAreacorp);
		} else {
			dicAreacorpDao.updateEntity(dicAreacorp, dicAreacorp.getAreaid());
		}
		setAlterFlag();
	}

	/**
	 * 查找当前区域公司下属企业及下属单位列表，组装成map:key[corp,dep]
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, List> findSuborgsMap(String id) {
		HashMap subMap = new HashMap();
		List corpList = dicSinocorpDao.findByAreaid(id);
		List depList = dicSinodepartmentDao.findByParentid(id);
		subMap.put("corp", corpList);
		subMap.put("dep", depList);
		return subMap;
	}

	public void delete(String id) {
		dicAreacorpDao.delete(id);
		setAlterFlag();

	}

	public void expExcel(List<DicAreacorp> dicAreacorps,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("区域公司列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", dicAreacorps);
		// 标题
		sheetMap.put("title", "区域公司列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaid", "fullname", "shortname",
				"address", "provinceidDIC", "dutyperson", "telephones", "fax",
				"contactperson", "orderno", "isactiveStr", "description" };
		headArr[1] = new String[] { "区域公司ID", "全称", "简称", "地址", "省份", "负责人",
				"电话", "传真", "联系人", "排序", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "区域公司列表");

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicAreacorp");
	}

}
