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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.dao.DicCorprailwayDao;
import com.tlys.dic.model.DicCorprailway;

@Service
public class DicCorprailwayService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicCorprailwayDao dicCorprailwayDao;
	@Autowired
	DicMap dicMap;

	public void findPageCtr(DicCorprailway obj, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getObjCount(obj);
		pageCtr.setTotalRecord(totalRecord);
		dicCorprailwayDao.findPageCtr(obj, pageCtr);
		dicMap.bdCorprailwayDic(pageCtr.getRecords());
	}

	public int getObjCount(final DicCorprailway obj) {
		int count = dicCorprailwayDao.getObjCount(obj);
		return count;
	}

	/**
	 * 翻译后企业专用线列表信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicCorprailway> findDICAll() throws Exception {
		List<DicCorprailway> list = dicCorprailwayDao.findAll();
		dicMap.bdCorprailwayDic(list);
		return list;
	}

	/**
	 * 通过企业查询到货站
	 * 
	 * @param DicCorprailway
	 * @return
	 * @throws Exception
	 */
	public List<DicCorprailway> findByCorpid(DicCorprailway DicCorprailway)
			throws Exception {
		List<DicCorprailway> list = dicCorprailwayDao.find(DicCorprailway);
		dicMap.bdCorprailwayDic(list);
		return list;
	}

	public List<DicCorprailway> findAll() {
		List<DicCorprailway> list = dicCorprailwayDao.findAll();
		return list;
	}

	/**
	 * 根据ID查找单个企业专用线
	 * 
	 * @return
	 */
	public DicCorprailway load(String id) {
		return dicCorprailwayDao.load(id);
	}

	/**
	 * 根据isNew 新增或更新企业专用线
	 * 
	 * @param DicCorprailway
	 * @param isNew
	 */
	public void save(DicCorprailway dicCorprailway, boolean isNew) {
		if (true == isNew) {
			dicCorprailway.setIspublic("1");
			dicCorprailwayDao.saveOrUpdate(dicCorprailway);
		} else {
			dicCorprailwayDao.updateEntity(dicCorprailway, dicCorprailway
					.getCrwid());
		}
		setAlterFlag();
	}

	/**
	 * 根据ID删除单个企业专用线
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicCorprailwayDao.delete(id);
		setAlterFlag();
	}

	public List<DicCorprailway> find(DicCorprailway DicCorprailway) {
		return dicCorprailwayDao.find(DicCorprailway);
	}

	public void expExcel(List<DicCorprailway> DicCorprailways,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("企业专用线列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", DicCorprailways);
		// 标题
		sheetMap.put("title", "企业专用线列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "crwid", "corpidDIC", "fullname",
				"shortname", "neareststationDIC", "ispublicDIC", "sinocode",
				"isactiveDIC", "description" };
		headArr[1] = new String[] { "专用线ID", "企业名称", "全称", "简称", "过轨战", "是否共用",
				"标准代码", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "企业专用线列表");

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCorprailway");
	}
	/**
	 * 查询表 TB_ZBC_DIC_CORPRAILWAY 中的CRWID 最大值
	 * @return
	 */
	public String getMaxCrwid(){
		
		return dicCorprailwayDao.getMaxCrwid();
	}
}
