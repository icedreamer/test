/**
 * @author ������
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
import com.tlys.dic.dao.DicRwbureauDao;
import com.tlys.dic.dao.DicRwdepartmentDao;
import com.tlys.dic.dao.DicRwdepartypeDao;
import com.tlys.dic.model.DicRwbureau;

@Service
public class DicRwbureauService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRwbureauDao DicRwbureauDao;
	@Autowired
	DicRwdepartypeDao dicRwdepartypeDao;
	@Autowired
	DicRwdepartmentDao dicRwdepartmentDao;
	@Autowired
	DicMap dicMap;

	/**
	 * ����������·����Ϣ
	 * 
	 * @return
	 */
	public List<DicRwbureau> findAll() {
		return DicRwbureauDao.findAlls("shortname");
	}

	/**
	 * ����ID���ҵ�����·����Ϣ
	 * 
	 * @return
	 */
	public DicRwbureau load(String id) {
		return DicRwbureauDao.load(id);
	}

	/**
	 * ����isNew �����������·����Ϣ
	 * 
	 * @param DicRwbureau
	 * @param isNew
	 */
	public void save(DicRwbureau DicRwbureau, boolean isNew) {
		if (true == isNew) {
			DicRwbureauDao.saveOrUpdate(DicRwbureau);
		} else {
			DicRwbureauDao.updateEntity(DicRwbureau, DicRwbureau.getBureauid());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������·����Ϣ
	 * 
	 * @param id
	 */
	public void delete(String id) {
		DicRwbureauDao.delete(id);
		setAlterFlag();
	}

	public int findTotalCount(String condition) {

		return DicRwbureauDao.findTotalCount(condition, null);
	}

	public List<DicRwbureau> find(DicRwbureau dicRwbureau) {
		return DicRwbureauDao.find(dicRwbureau);
	}

	public Map<String, List> findSuborgsMap(String id) {
		HashMap subMap = new HashMap();
		List corpList = dicRwdepartypeDao.findAll();
		List depList = dicRwdepartmentDao.findByBureauid(id);
		subMap.put("corp", corpList);
		subMap.put("dep", depList);
		return subMap;
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRwbureau");
	}

	public void expExcel(List<DicRwbureau> DicRwbureaus,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��·���б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", DicRwbureaus);
		// ����
		sheetMap.put("title", "��·���б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "bureauid", "fullname", "shortname",
				"shrinkname", "address", "provinceidDIC", "dutyperson",
				"telephones", "fax", "contactperson", "orderno", "pycode",
				"rwcode", "isactiveDIC", "description" };
		headArr[1] = new String[] { "·��ID", "ȫ��", "���", "��д", "��ַ", "ʡ��",
				"������", "��ϵ�绰", "����", "��ϵ��", "˳���", "ƴ����", "��·����", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��·���б�");

	}
}
