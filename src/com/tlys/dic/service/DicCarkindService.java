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
import com.tlys.dic.dao.DicCarkindDao;
import com.tlys.dic.model.DicCarkind;

/**
 * @author fengym
 * 
 */
@Service
public class DicCarkindService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DicCarkindDao dicCarkindDao;

	@Autowired
	DicMap dicMap;

	public List<DicCarkind> findAll(){
		return dicCarkindDao.findAll();
	}

	public List<DicCarkind> find(DicCarkind dac) throws Exception {
		return dicCarkindDao.find(dac);
	}

	public DicCarkind load(String id) {
		return dicCarkindDao.load(id);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicCarkind,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicCarkind dicCarkind, boolean isNew) {
		if (isNew) {
			dicCarkindDao.saveOrUpdate(dicCarkind);
		} else {
			dicCarkindDao.updateEntity(dicCarkind, dicCarkind.getCarkindid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicCarkindDao.delete(id);
		setAlterFlag();
	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCarkind");
	}

	public void expExcel(List<DicCarkind> DicCarkinds,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("车种列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", DicCarkinds);
		// 标题
		sheetMap.put("title", "车种列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "carkindid", "fullname", "shortname",
				"isactiveStr", "description" };
		headArr[1] = new String[] { "车种ID", "全称", "简称", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "车种列表");

	}
}
