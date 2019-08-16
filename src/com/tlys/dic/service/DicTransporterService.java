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
	 * Ϊ�����ֵ�Map���ã�������ʡ�ݵȽ���
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
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicTransporter
	 *            ,isNew:��־��ǰ���������������޸Ĳ���
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
	 * ���EXCEL��
	 * 
	 * @param dicTransporters
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicTransporter> dicTransporters,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��·���������", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", dicTransporters);
		// ����
		sheetMap.put("title", "��·����������б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "transportid", "fullname", "shortname",
				"address", "provinceidDIC", "dutyperson", "telephones", "fax",
				"contactperson", "isbelongsinoStr", "isactiveStr",
				"description" };
		headArr[1] = new String[] { "������ID", "ȫ��", "���", "��ַ", "ʡ��", "������",
				"�绰", "����", "��ϵ��", "�Ƿ��ڲ���ҵ", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��·���������");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicTransporter");
	}

	/**
	 * ������ҵ���ͻ��ͳ����˵ļ�¼�������Ʊ�ʹ��Ƶ�ʻ�ȡ�������б�
	 * 
	 * @param customerid
	 * @param corpid
	 * @return
	 */
	public List<DicTransporter> findTransporter(String customerid, String corpid) {
		return dicTransporterDao.findTransporter(customerid, corpid);
	}
}
