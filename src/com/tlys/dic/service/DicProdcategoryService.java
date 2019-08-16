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
import com.tlys.dic.dao.DicProdcategoryDao;
import com.tlys.dic.model.DicProdcategory;

@Service
public class DicProdcategoryService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProdcategoryDao DicProdcategoryDao;
	@Autowired
	DicMap dicMap;



	/**
	 * 查找所有信息
	 * 
	 * @return
	 */
	public List<DicProdcategory> findAll() {
		return DicProdcategoryDao.findAll(" order by orderno");
	}

	/**
	 * 根据ID查找单个信息
	 * 
	 * @return
	 */
	public DicProdcategory load(String id) {
		return DicProdcategoryDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新信息
	 * 
	 * @param DicProdcategory
	 * @param isNew
	 */
	public void save(DicProdcategory DicProdcategory, boolean isNew) {
		if (true == isNew) {
			DicProdcategoryDao.saveOrUpdate(DicProdcategory);
		} else {
			DicProdcategoryDao.updateEntity(DicProdcategory, DicProdcategory
					.getCategoryid());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		DicProdcategoryDao.delete(id);
		setAlterFlag();

	}

	public List<DicProdcategory> find(DicProdcategory DicProdcategory) {
		return DicProdcategoryDao.find(DicProdcategory);
	}

	public void expExcel(List<DicProdcategory> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("产品大类列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "产品大类列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "categoryid", "fullname", "shortname",
				"orderno", "isactiveDIC" };
		headArr[1] = new String[] { "大类代码", "全称", "简称", "顺序号", "是否启用" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "产品大类列表");

	}
	
	/**
	 * 由于当前对象充当字典使用
	 * 所以当前对象变动（保存，更新，删除)时
	 * 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag(){
		dicMap.dicAlter("dicProdcategory");
	}
}
