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
	 * ���������ֵ��ֵ�ԣ�������������ֵ��ֶ�
	 * (��findAll�����������dicMap.bdProv(cmList);)
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
	 * �õ��б�רΪ�����ͼʱ���ã�����Ϊ��ǰ��ͼ�� {salecorp,receiver,sender},ע����������ͱ�������ͼ��������
	 * 
	 * @param viewname
	 * @return
	 */
	public List<DicCustomer> find4view(String viewname) {
		return dicCustomerDao.find4view(viewname);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicCustomer
	 *            ,isNew:��־��ǰ���������������޸Ĳ���
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
	 * ���EXCEL��
	 * 
	 * @param DicTransporters
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicCustomer> dicCustomers, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�������ۿͻ�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", dicCustomers);
		// ����
		sheetMap.put("title", "�������ۿͻ��б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "customerid", "fullname", "shortname", "address", "provinceidDIC", "dutyperson",
				"telephones", "fax", "contactperson", "isbelongsinoStr", "issenderStr", "isreceiverStr", "isactiveStr",
				"description" };
		headArr[1] = new String[] { "�ͻ�ID", "ȫ��", "���", "��ַ", "ʡ��", "������", "�绰", "����", "��ϵ��", "�Ƿ��ڲ���ҵ", "�Ƿ񷢻���",
				"�Ƿ��ջ���", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�������ۿͻ�");

	}
	
	public List<DicCustomer> findDicCustomer(String corpid) {
		return dicCustomerDao.findDicCustomer(corpid);
	}
	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ��
	 * ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ
	 * Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
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
