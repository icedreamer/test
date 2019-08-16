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
	 * ����sinocorp��ʹѡ�еĵ�λ��Ϊreceiver
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
	 * ����Sinodepartment��ʹѡ�еĵ�λ��Ϊreceiver
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
	 * ����Customer��ʹѡ�еĵ�λ��Ϊreceiver
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
	 * ɾ���ջ��ˣ�ʵ�ʲ����Ǹ�����Ӧ���е�isreceiver�ֶ�
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
	 * ���EXCEL��
	 * @param dicGoogssenders
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicGoogsreceiver> dicGoogsreceivers,
			HttpServletResponse response) throws Exception{
		Map tabDefineMap = new HashMap();
		
		//����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�ջ���", sheetMap);
		
		//��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", dicGoogsreceivers);
		//����
		sheetMap.put("title", "�ջ����б�");
		
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
		
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�ջ���");
		
	}
}
