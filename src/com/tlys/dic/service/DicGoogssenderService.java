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
	 * ����isSender�ֶΣ���sinocorp���뷢������ͼ
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
	 * ����isSender�ֶΣ���Sinodepartment���뷢������ͼ
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
	 * ����isSender�ֶΣ���Customer���뷢������ͼ
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
	 * ���EXCEL��
	 * @param dicGoogssenders
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicGoogssender> dicGoogssenders,
			HttpServletResponse response) throws Exception{
		Map tabDefineMap = new HashMap();
		
		//����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("������", sheetMap);
		
		//��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", dicGoogssenders);
		//����
		sheetMap.put("title", "�������б�");
		
		//��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		//������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[]{
				"id","shortname","address",	"dutyperson","telephones",
				"fax","description"
				};
		headArr[1] = new String[]{
				"ID","���","��ַ","������","�绰",
				"����","����"};
		sheetMap.put("headArr", headArr);
		
		//Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);
		
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "������");
		
	}

	
}
