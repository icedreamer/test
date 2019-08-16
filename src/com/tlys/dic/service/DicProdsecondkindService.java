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
import com.tlys.dic.dao.DicProdsecondkindDao;
import com.tlys.dic.model.DicProdsecondkind;

@Service
public class DicProdsecondkindService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProdsecondkindDao dicProdsecondkindDao;
	@Autowired
	DicMap dicMap;

	/**
	 * 查找所有信息
	 * 
	 * @return
	 */
	public List<DicProdsecondkind> findAll() {
		return dicProdsecondkindDao.findAll();
	}

	public List<DicProdsecondkind> findDICAll() {
		List<DicProdsecondkind> list = dicProdsecondkindDao.findAll();
		dicMap.bdProdsecondkindDic(list);
		return list;
	}

	/**
	 * 根据ID查找单个信息
	 * 
	 * @return
	 */
	public DicProdsecondkind load(String id) {
		return dicProdsecondkindDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新信息
	 * 
	 * @param DicProdsecondkind
	 * @param isNew
	 */
	public void save(DicProdsecondkind DicProdsecondkind, boolean isNew) {
		if (true == isNew) {
			dicProdsecondkindDao.saveOrUpdate(DicProdsecondkind);
		} else {
			dicProdsecondkindDao.updateEntity(DicProdsecondkind,
					DicProdsecondkind.getSecondkindid());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicProdsecondkindDao.delete(id);
		setAlterFlag();

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicProdsecondkind");
	}

	public List<DicProdsecondkind> find(DicProdsecondkind dicProdsecondkind) {
		List<DicProdsecondkind> list = dicProdsecondkindDao
				.find(dicProdsecondkind);
		dicMap.bdProdsecondkindDic(list);
		return list;
	}

	public void expExcel(List<DicProdsecondkind> list,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("产品小类列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "产品小类列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "secondkindid", "categoryidDIC",
				"fullname", "shortname", "orderno", "rwkindidDIC", "kindDIC", "isactiveDIC" };
		headArr[1] = new String[] { "小类代码", "大类", "全称", "简称", "顺序号", "铁路品名",
				"类别",  "是否启用" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "产品小类列表");

	}

	public List<DicProdsecondkind> findDicProdsecondkind(String[] categoryids) {
		return dicProdsecondkindDao.findDicProdsecondkind(categoryids);
	}
}
