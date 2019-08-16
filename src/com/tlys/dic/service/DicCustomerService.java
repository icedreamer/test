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
import com.tlys.dic.dao.DicCustomerDao;
import com.tlys.dic.model.DicCustomer;

/**
 * @author fengym
 * 
 */
@Service
public class DicCustomerService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DicCustomerDao dicCustomerDao;

	@Autowired
	DicMap dicMap;

	/**
	 * 纯粹生成字典键值对，不解析本身的字典字段
	 * (与findAll方法相对少了dicMap.bdProv(cmList);)
	 * @return
	 * @throws Exception
	 */
	public List<DicCustomer> findAll4Dic(){
		List<DicCustomer> cmList = dicCustomerDao.findAll();
		return cmList;
	}
	
	public List<DicCustomer> findAll() throws Exception {
		List<DicCustomer> cmList = dicCustomerDao.findAll();
		dicMap.bdProv(cmList);
		return cmList;
	}

	public List<DicCustomer> find(DicCustomer dac) throws Exception {
		List<DicCustomer> cmList = dicCustomerDao.find(dac);
		dicMap.bdProv(cmList);
		return cmList;
	}

	public DicCustomer load(String id) {
		return dicCustomerDao.load(id);
	}

	/**
	 * 得到列表，专为添加视图时所用，参数为当前视图名 {salecorp,receiver,sender},注意后面两个和本来的视图名有区别
	 * 
	 * @param viewname
	 * @return
	 */
	public List<DicCustomer> find4view(String viewname) {
		return dicCustomerDao.find4view(viewname);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicCustomer
	 *            ,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicCustomer dicCustomer, boolean isNew) {
		if (isNew) {
			dicCustomer.setCustomerid(getCustomerSeqStr());
			dicCustomerDao.saveOrUpdate(dicCustomer);
		} else {
			dicCustomerDao.updateEntity(dicCustomer, dicCustomer.getCustomerid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicCustomerDao.delete(id);
		setAlterFlag();
	}

	public List<DicCustomer> findByProvids(String provids) throws Exception {
		String hql = " where provinceid in(" + CommUtils.addQuotes(provids) + ")";
		List cmList = dicCustomerDao.findAll(hql);
		dicMap.bdProv(cmList);
		return cmList;
	}

	/**
	 * 输出EXCEL表
	 * 
	 * @param DicTransporters
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicCustomer> dicCustomers, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("化工销售客户", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", dicCustomers);
		// 标题
		sheetMap.put("title", "化工销售客户列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "customerid", "fullname", "shortname", "address", "provinceidDIC", "dutyperson",
				"telephones", "fax", "contactperson", "isbelongsinoStr", "issenderStr", "isreceiverStr", "isactiveStr",
				"description" };
		headArr[1] = new String[] { "客户ID", "全称", "简称", "地址", "省份", "负责人", "电话", "传真", "联系人", "是否内部企业", "是否发货人",
				"是否收货人", "是否启用", "描述" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "化工销售客户");

	}
	
	public List<DicCustomer> findDicCustomer(String corpid) {
		return dicCustomerDao.findDicCustomer(corpid);
	}
	/**
	 * 由于当前对象充当字典使用
	 * 所以当前对象变动（保存，更新，删除)时
	 * 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag(){
		dicMap.dicAlter("dicCustomer");
	}
	
	public DicCustomer getDicCustomerByName(String customerName){
		return dicCustomerDao.getDicCustomerByName(customerName);
	}
	
	public String getCustomerSeqStr(){
		return CommUtils.getSeqStr(dicCustomerDao.getSeq(), 8);
	}
}
