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
 * @author ������
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
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicCarmarker,isNew:��־��ǰ���������������޸Ĳ���
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

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�����������б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "�����������б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "carmakerid", "fullname", "shortname",
				"provinceidDIC", "sinocode", "isactiveDIC", "description" };
		headArr[1] = new String[] { "���쳧��ID", "ȫ��", "���", "ʡ��", "��׼����", "�Ƿ�����",
				"����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�����������б�");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCarmaker");
	}
}
