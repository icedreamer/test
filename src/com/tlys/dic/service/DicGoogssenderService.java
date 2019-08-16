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
import com.tlys.dic.dao.DicGoogssenderDao;
import com.tlys.dic.dao.DicSinocorpDao;
import com.tlys.dic.dao.DicSinodepartmentDao;
import com.tlys.dic.model.DicCustomer;
import com.tlys.dic.model.DicGoogssender;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;

/**
 * @author fengym
 *
 */
@Service
public class DicGoogssenderService{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	DicGoogssenderDao dicGoogssenderDao;

	@Autowired
	DicSinocorpDao dicSinocorpDao;
	
	
	@Autowired
	DicSinodepartmentDao dicSinodepartmentDao;
	
	@Autowired
	DicCustomerDao dicCustomerDao;
	
	public List<DicGoogssender> findAll(){
		return dicGoogssenderDao.findAll();
	}
	
	public List<DicGoogssender> find(DicGoogssender dac){
		return dicGoogssenderDao.find(dac);
	}
	
	
	public DicGoogssender load(String id) {
		return dicGoogssenderDao.load(id);
	}

	/**
	 * 更新isSender字段，将sinocorp加入发货人视图
	 * @param srcorg
	 */
	public void updateSendersFromSinocorp(String srcorg) {
		List srcorgs = dicSinocorpDao.findAll(" where corpid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicSinocorp sinocorp = (DicSinocorp) iter.next();
			sinocorp.setIssender("1");
		}

		dicSinocorpDao.saveOrUpdateAll(srcorgs);
		
	}

	/**
	 * 更新isSender字段，将Sinodepartment加入发货人视图
	 * @param srcorg
	 */
	public void updateSendersFromSinodepartment(String srcorg) {
		List srcorgs = dicSinodepartmentDao.findAll(" where sinodepaid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicSinodepartment dicSinodepartment = (DicSinodepartment) iter.next();
			dicSinodepartment.setIssender("1");
		}
		dicSinodepartmentDao.saveOrUpdateAll(srcorgs);
		
	}

	/**
	 * 更新isSender字段，将Customer加入发货人视图
	 * @param srcorg
	 */
	public void updateSendersFromCustomer(String srcorg) {
		List srcorgs = dicCustomerDao.findAll(" where customerid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicCustomer dicCustomer = (DicCustomer) iter.next();
			dicCustomer.setIssender("1");
		}

		dicCustomerDao.saveOrUpdateAll(srcorgs);
		
	}

	public void delete(String id) {
		DicGoogssender dicGoogssender = dicGoogssenderDao.load(id);
		String src = dicGoogssender.getSourcetab();
		String srcid = dicGoogssender.getId();
		if("COR".equals(src)){
			DicSinocorp dicSinocorp=dicSinocorpDao.load(srcid);
			dicSinocorp.setIssender("0");
			dicSinocorpDao.saveOrUpdate(dicSinocorp);
		}else if("DEP".equals(src)){
			DicSinodepartment dicSinodepartment = dicSinodepartmentDao.load(srcid);
			dicSinodepartment.setIssender("0");
			dicSinodepartmentDao.saveOrUpdate(dicSinodepartment);
		}else if("CUS".equals(src)){
			DicCustomer dicCustomer = dicCustomerDao.load(srcid);
			dicCustomer.setIssender("0");
			dicCustomerDao.saveOrUpdate(dicCustomer);
		}
		
	}
	
	/**
	 * 输出EXCEL表
	 * @param dicGoogssenders
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicGoogssender> dicGoogssenders,
			HttpServletResponse response) throws Exception{
		Map tabDefineMap = new HashMap();
		
		//增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("发货人", sheetMap);
		
		//在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", dicGoogssenders);
		//标题
		sheetMap.put("title", "发货人列表");
		
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
		
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "发货人");
		
	}

	
}
