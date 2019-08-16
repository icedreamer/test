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
import com.tlys.dic.dao.DicRentmodeDao;
import com.tlys.dic.model.DicRentMode;

/**
 * @author 郭建军
 * 
 */
@Service
public class DicRentmodeService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRentmodeDao dicRentmodeDao;

	@Autowired
	DicMap dicMap;

	public List<DicRentMode> findDICAll() throws Exception {
		List<DicRentMode> list = dicRentmodeDao.findAll();
		//dicMap.bdIsactive(list);
		return list;
	}

	public List<DicRentMode> findAll() {
		return dicRentmodeDao.findAll();
	}

	public List<DicRentMode> find(DicRentMode dac) {
		return dicRentmodeDao.find(dac);
	}

	public DicRentMode load(String id) {
		return dicRentmodeDao.load(id);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicRentmode,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicRentMode dicRentmode, boolean isNew) {
		if (isNew) {
			dicRentmode.setCreatedtime(new Date());
			dicRentmodeDao.saveOrUpdate(dicRentmode);
		} else {
			dicRentmodeDao.updateEntity(dicRentmode, dicRentmode.getRentid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicRentmodeDao.delete(id);
		setAlterFlag();
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRentMode");
	}

	public void expExcel(List<DicRentMode> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("车辆租赁方式列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "车辆租赁方式列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "rentid", "rentname", "isactiveDIC",
				"description" };
		headArr[1] = new String[] { "方式ID", "名称", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "车辆租赁方式列表");

	}
}
