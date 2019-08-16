/**
 * @author 郭建军
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
import com.tlys.dic.dao.DicProductDao;
import com.tlys.dic.model.DicProduct;

@Service
public class DicProductService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProductDao dicProductDao;
	@Autowired
	DicMap dicMap;

	/**
	 * 查找所有信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicProduct> findAll() {
		String sql = "select obj.* from tb_zbc_dic_product obj start with obj.parentid='0' connect by prior obj.prodid = obj.parentid";
		List<DicProduct> scList = dicProductDao.findTreeAll(sql);
		dicMap.bdDicProductTree(scList);
		return scList;
	}

	/**
	 * 专为生成字典Map用，本身不做字典解析
	 * 
	 * @return
	 */
	public List<DicProduct> findAll4Dic() {
		List<DicProduct> scList =dicProductDao.findAll();
		return scList;
	}

	/**
	 * 根据ID查找单个信息
	 * 
	 * @return
	 */
	public DicProduct load(String id) {
		return dicProductDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新信息
	 * 
	 * @param DicProduct
	 * @param isNew
	 */
	public void save(DicProduct dicProduct, boolean isNew) {
		if (true == isNew) {
			dicProduct.setCreatedtime(new Date());
			dicProductDao.saveOrUpdate(dicProduct);
		} else {
			dicProductDao.updateEntity(dicProduct, dicProduct.getProdid());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicProductDao.delete(id);
		setAlterFlag();
	}

	public List<DicProduct> find(DicProduct dicProduct) {
		List<DicProduct> list = dicProductDao.find(dicProduct);
		dicMap.bdDicProductTree(list);
		return list;
	}

	public void expExcel(List<DicProduct> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("产品字典列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "产品字典列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "prodid", "fullname", "shortname",
				"categoryidDIC", "secondkindidDIC", "rwkindidDIC", "kindDIC",
				"isactiveDIC", "searchcode", "liscode", "sinocode",
				"description" };
		headArr[1] = new String[] { "产品ID", "全称", "简称", "大类", "小类", "铁路品名",
				"类别", "是否启用", "检索码", "LIS代码", "标准代码", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "产品字典列表");

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicProduct");
	}
}
