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
import com.tlys.dic.dao.DicCorprepdepotDao;
import com.tlys.dic.model.DicCorprepdepot;

@Service
public class DicCorprepdepotService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicCorprepdepotDao DicCorprepdepotDao;
	@Autowired
	DicMap dicMap;

	/**
	 * 翻译后列表信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicCorprepdepot> findDICAll() throws Exception {
		List<DicCorprepdepot> list = DicCorprepdepotDao.findAll();
		dicMap.bdCorprepdepotDic(list);
		return list;
	}

	/**
	 * 查找所有信息
	 * 
	 * @return
	 */
	public List<DicCorprepdepot> findAll() {
		return DicCorprepdepotDao.findAll();
	}

	/**
	 * 根据ID查找单个信息
	 * 
	 * @return
	 */
	public DicCorprepdepot load(Long id) {
		return DicCorprepdepotDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新信息
	 * 
	 * @param DicCorprepdepot
	 * @param isNew
	 */
	public void save(DicCorprepdepot DicCorprepdepot, boolean isNew) {
		if (true == isNew) {
			DicCorprepdepotDao.saveOrUpdate(DicCorprepdepot);
		} else {
			DicCorprepdepotDao.updateEntity(DicCorprepdepot, DicCorprepdepot
					.getId());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个信息
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		DicCorprepdepotDao.delete(id);
		setAlterFlag();
	}

	public List<DicCorprepdepot> find(DicCorprepdepot DicCorprepdepot) {
		List<DicCorprepdepot> list = DicCorprepdepotDao.find(DicCorprepdepot);
		dicMap.bdCorprepdepotDic(list);
		return list;
	}

	public void expExcel(List<DicCorprepdepot> DicCorprepdepots,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("检修车辆段列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", DicCorprepdepots);
		// 标题
		sheetMap.put("title", "检修车辆段列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "id", "corpidDIC", "shortname",
				"stockdepotid", "isactiveDIC", "bureauidDIC", "description" };
		headArr[1] = new String[] { "检修车辆段ID", "企业", "检修车辆段名称", "检修车辆段代码",
				"是否启用", "路局", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "检修车辆段列表");

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCorprepdepot");
	}
}
