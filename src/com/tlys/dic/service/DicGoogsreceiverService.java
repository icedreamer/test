/**
 * 
 */
package com.tlys.dic.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicCustomerDao;
import com.tlys.dic.dao.DicGoogsreceiverDao;
import com.tlys.dic.dao.DicSinocorpDao;
import com.tlys.dic.dao.DicSinodepartmentDao;
import com.tlys.dic.model.DicCustomer;
import com.tlys.dic.model.DicGoogsreceiver;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;

/**
 * @author fengym
 *
 */
@Service
public class DicGoogsreceiverService{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	DicGoogsreceiverDao dicGoogsreceiverDao;
	
	@Autowired
	DicSinocorpDao dicSinocorpDao;
	
	
	@Autowired
	DicSinodepartmentDao dicSinodepartmentDao;
	
	@Autowired
	DicCustomerDao dicCustomerDao;

	
	public List<DicGoogsreceiver> findAll(){
		return dicGoogsreceiverDao.findAll();
	}
	
	public List<DicGoogsreceiver> find(DicGoogsreceiver dac){
		return dicGoogsreceiverDao.find(dac);
	}
	
	
	public DicGoogsreceiver load(String id) {
		return dicGoogsreceiverDao.load(id);
	}
	
	/**
	 * 更新sinocorp，使选中的单位变为receiver
	 * 
	 * @param 
	 */
	public void updateReceiversFromSinocorp(String srcorg) {
		List srcorgs = dicSinocorpDao.findAll(" where corpid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicSinocorp sinocorp = (DicSinocorp) iter.next();
			sinocorp.setIsreceiver("1");
		}

		dicSinocorpDao.saveOrUpdateAll(srcorgs);
	}
	
	/**
	 * 更新Sinodepartment，使选中的单位变为receiver
	 * 
	 * @param 
	 */
	public void updateReceiversFromSinodepartment(String srcorg) {
		List srcorgs = dicSinodepartmentDao.findAll(" where sinodepaid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicSinodepartment dicSinodepartment = (DicSinodepartment) iter.next();
			dicSinodepartment.setIsreceiver("1");
		}
		//System.out.println("DicGoogsreceiverService.updateReceiversFromSinodepartment->srcorgs=="+srcorgs.size());
		dicSinodepartmentDao.saveOrUpdateAll(srcorgs);

	}
	
	/**
	 * 更新Customer，使选中的单位变为receiver
	 * 
	 * @param 
	 */
	public void updateReceiversFromCustomer(String srcorg) {
		List srcorgs = dicCustomerDao.findAll(" where customerid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicCustomer dicCustomer = (DicCustomer) iter.next();
			dicCustomer.setIsreceiver("1");
		}

		dicCustomerDao.saveOrUpdateAll(srcorgs);
	}
	
	/**
	 * 删除收货人，实际操作是更新相应表中的isreceiver字段
	 * @param id
	 */
	public void delete(String id) {
		DicGoogsreceiver dicGoogsreceiver = dicGoogsreceiverDao.load(id);
		String src = dicGoogsreceiver.getSourcetab();
		String srcid = dicGoogsreceiver.getId();
		if("COR".equals(src)){
			DicSinocorp dicSinocorp=dicSinocorpDao.load(srcid);
			dicSinocorp.setIsreceiver("0");
			dicSinocorpDao.saveOrUpdate(dicSinocorp);
		}else if("DEP".equals(src)){
			DicSinodepartment dicSinodepartment = dicSinodepartmentDao.load(srcid);
			dicSinodepartment.setIsreceiver("0");
			dicSinodepartmentDao.saveOrUpdate(dicSinodepartment);
		}else if("CUS".equals(src)){
			DicCustomer dicCustomer = dicCustomerDao.load(srcid);
			dicCustomer.setIsreceiver("0");
			dicCustomerDao.saveOrUpdate(dicCustomer);
		}
	}
	
	/**
	 * 输出EXCEL表
	 * @param dicGoogssenders
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicGoogsreceiver> dicGoogsreceivers,
			HttpServletResponse response) throws Exception{
		Map tabDefineMap = new HashMap();
		
		//增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("收货人", sheetMap);
		
		//在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", dicGoogsreceivers);
		//标题
		sheetMap.put("title", "收货人列表");
		
		//表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		//这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[]{
				"id","shortname","address",	"dutyperson","telephones",
				"fax","description"
				};
		headArr[1] = new String[]{
				"ID","简称","地址","负责人","电话",
				"传真","描述"};
		sheetMap.put("headArr", headArr);
		
		//Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);
		
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "收货人");
		
	}
}
