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

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicCarmakerDao;
import com.tlys.dic.model.DicCarmaker;

/**
 * @author 郭建军
 * 
 */
@Service
public class DicCarmakerService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicCarmakerDao dicCarmarkerDao;

	@Autowired
	DicMap dicMap;

	public List<DicCarmaker> findDICAll() throws Exception {
		List<DicCarmaker> list = dicCarmarkerDao.findAll();
		dicMap.bdProv(list);
		return list;
	}

	public List<DicCarmaker> findAll() {
		return dicCarmarkerDao.findAll();
	}

	public List<DicCarmaker> find(DicCarmaker dac) throws Exception {
		List<DicCarmaker> list = dicCarmarkerDao.find(dac);
		dicMap.bdProv(list);
		return list;
	}

	public DicCarmaker load(String id) {
		return dicCarmarkerDao.load(id);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicCarmarker,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicCarmaker dicCarmarker, boolean isNew) {
		if (isNew) {
			dicCarmarker.setCreatedtime(new Date());
			dicCarmarkerDao.saveOrUpdate(dicCarmarker);
		} else {
			dicCarmarkerDao.updateEntity(dicCarmarker, dicCarmarker
					.getCarmakerid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicCarmarkerDao.delete(id);
		setAlterFlag();
	}

	public List<DicCarmaker> findByProvids(String provids) throws Exception {
		String hql = " where provinceid in(" + CommUtils.addQuotes(provids)
				+ ")";
		List trList = dicCarmarkerDao.findAll(hql);
		dicMap.bdProv(trList);
		return trList;
	}

	public void expExcel(List<DicCarmaker> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("车辆制造商列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "车辆制造商列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "carmakerid", "fullname", "shortname",
				"provinceidDIC", "sinocode", "isactiveDIC", "description" };
		headArr[1] = new String[] { "制造厂家ID", "全称", "简称", "省份", "标准代码", "是否启用",
				"描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "车辆制造商列表");

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCarmaker");
	}
}
