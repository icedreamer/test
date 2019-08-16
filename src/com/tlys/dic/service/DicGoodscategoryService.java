/**
 * 
 */
package com.tlys.dic.service;

import java.util.Date;
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
import com.tlys.dic.dao.DicGoodscategoryDao;
import com.tlys.dic.model.DicGoodscategory;

/**
 * @author 郭建军
 * 
 */
@Service
public class DicGoodscategoryService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicGoodscategoryDao dicGoodscategoryDao;
	@Autowired
	DicMap dicMap;

	public List<DicGoodscategory> findDICAll() throws Exception {
		List<DicGoodscategory> list = dicGoodscategoryDao.findAll();
		dicMap.bdGoodscategoryDic(list);
		return list;
	}

	public List<DicGoodscategory> findAll() {
		return dicGoodscategoryDao.findAll(" order by orderno");
	}

	public List<DicGoodscategory> find(DicGoodscategory dac) {
		List<DicGoodscategory> list = dicGoodscategoryDao.find(dac);
		dicMap.bdGoodscategoryDic(list);
		return list;
	}

	public DicGoodscategory load(String id) {
		return dicGoodscategoryDao.load(id);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicGoodscategory,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicGoodscategory dicGoodscategory, boolean isNew) {
		if (isNew) {
			dicGoodscategory.setCreatedtime(new Date());
			dicGoodscategoryDao.saveOrUpdate(dicGoodscategory);
		} else {
			dicGoodscategoryDao.updateEntity(dicGoodscategory, dicGoodscategory.getGoodsid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicGoodscategoryDao.delete(id);
		setAlterFlag();
	}

	public void expExcel(List<DicGoodscategory> DicGoodscategorys, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("充装介质列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", DicGoodscategorys);
		// 标题
		sheetMap.put("title", "充装介质列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "goodsid", "goodsname", "parentidDIC", "orderno",
				"isactiveDIC", "description" };
		headArr[1] = new String[] { "铁路货物ID", "名称", "父层名称", "顺序号", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "充装介质列表");

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicGoodscategory");
	}

	public List<DicGoodscategory> findDicGoodscategory(List<String> goodsIdList) {
		return dicGoodscategoryDao.findDicGoodscategory(goodsIdList);
	}
}
