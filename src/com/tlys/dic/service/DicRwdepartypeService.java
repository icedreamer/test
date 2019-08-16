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
import com.tlys.dic.model.DicRwdepartype;

@Service
public class DicRwdepartypeService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRwbureauDao dicRwbureauDao;
	@Autowired
	DicRwdepartypeDao dicRwdepartypeDao;
	@Autowired
	DicRwdepartmentDao dicRwdepartmentDao;
	@Autowired
	DicMap dicMap;

	/**
	 * ����������·������Ϣ
	 * 
	 * @return
	 */
	public List<DicRwdepartype> findAll() {
		return dicRwdepartypeDao.findAll();
	}

	/**
	 * ����ID���ҵ�����·������Ϣ
	 * 
	 * @return
	 */
	public DicRwdepartype load(String id) {
		return dicRwdepartypeDao.load(id);
	}

	/**
	 * ����isNew �����������·������Ϣ
	 * 
	 * @param DicRwdepartype
	 * @param isNew
	 */
	public void save(DicRwdepartype DicRwdepartype, boolean isNew) {
		if (true == isNew) {
			dicRwdepartypeDao.saveOrUpdate(DicRwdepartype);
		} else {
			dicRwdepartypeDao.updateEntity(DicRwdepartype, DicRwdepartype
					.getTpyeid());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������·������Ϣ
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicRwdepartypeDao.delete(id);
		setAlterFlag();
	}

	public int findTotalCount(String condition) {

		return dicRwdepartypeDao.findTotalCount(condition, null);
	}

	public List<DicRwdepartype> find(DicRwdepartype dicRwdepartype) {
		return dicRwdepartypeDao.find(dicRwdepartype);
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRwdepartype");
	}

	public Map<String, List> findSuborgsMap(String id) {
		HashMap subMap = new HashMap();
		List corpList = dicRwbureauDao.findAll();
		List depList = dicRwdepartmentDao.findByTypeid(id);
		subMap.put("corp", corpList);
		subMap.put("dep", depList);
		return subMap;
	}

	public void expExcel(List<DicRwdepartype> DicRwdepartypes,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��·�����б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", DicRwdepartypes);
		// ����
		sheetMap.put("title", "��·�����б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "tpyeid", "name", "isactiveDIC",
				"description" };
		headArr[1] = new String[] { "����ID", "����", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��·�����б�");

	}
}
