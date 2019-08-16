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

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicTransporterDao;
import com.tlys.dic.model.DicTransporter;

/**
 * @author fengym
 * 
 */
@Service
public class DicTransporterService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DicTransporterDao dicTransporterDao;

	@Autowired
	DicMap dicMap;

	/**
	 * 为生成字典Map对用，不进行省份等解析
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicTransporter> findAll4Dic() {
		List<DicTransporter> tsList = dicTransporterDao.findAll();
		return tsList;
	}

	public List<DicTransporter> findAll() throws Exception {
		List<DicTransporter> tsList = dicTransporterDao.findAll();
		dicMap.bdProv(tsList);
		return tsList;
	}

	public List<DicTransporter> find(DicTransporter dac) throws Exception {
		List<DicTransporter> tsList = dicTransporterDao.find(dac);
		dicMap.bdProv(tsList);
		return tsList;
	}

	public List<DicTransporter> findByProvids(String provids) throws Exception {
		String hql = " where provinceid in(" + CommUtils.addQuotes(provids)
				+ ")";
		List trList = dicTransporterDao.findAll(hql);
		dicMap.bdProv(trList);
		return trList;
	}

	public DicTransporter load(String id) {
		return dicTransporterDao.load(id);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicTransporter
	 *            ,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicTransporter dicTransporter, boolean isNew) {
		if (isNew) {
			dicTransporterDao.saveOrUpdate(dicTransporter);
		} else {
			dicTransporterDao.updateEntity(dicTransporter, dicTransporter
					.getTransportid());
		}

		setAlterFlag();
	}

	public void delete(String id) {
		dicTransporterDao.delete(id);
		setAlterFlag();
	}

	/**
	 * 输出EXCEL表
	 * 
	 * @param dicTransporters
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicTransporter> dicTransporters,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("铁路运输承运商", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", dicTransporters);
		// 标题
		sheetMap.put("title", "铁路运输承运商列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "transportid", "fullname", "shortname",
				"address", "provinceidDIC", "dutyperson", "telephones", "fax",
				"contactperson", "isbelongsinoStr", "isactiveStr",
				"description" };
		headArr[1] = new String[] { "承运商ID", "全称", "简称", "地址", "省份", "负责人",
				"电话", "传真", "联系人", "是否内部企业", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "铁路运输承运商");

	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicTransporter");
	}

	/**
	 * 根据企业、客户和承运人的记录数数控制表使用频率获取承运人列表
	 * 
	 * @param customerid
	 * @param corpid
	 * @return
	 */
	public List<DicTransporter> findTransporter(String customerid, String corpid) {
		return dicTransporterDao.findTransporter(customerid, corpid);
	}
}
