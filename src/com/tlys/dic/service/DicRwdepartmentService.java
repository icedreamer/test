/**
 * @author ������
 */
package com.tlys.dic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicRwdepartmentDao;
import com.tlys.dic.model.DicRwdepartment;

@Service
public class DicRwdepartmentService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRwdepartmentDao DicRwdepartmentDao;
	@Autowired
	DicMap dicMap;
	@Autowired
	CacheService cacheService;

	/**
	 * ����������Ϣ
	 * 
	 * @return
	 */
	public List<DicRwdepartment> findAll() {
		String key = "DIC_RW_DEPARTMENT_LIST";
		List<DicRwdepartment> rwdepartmentList = (List<DicRwdepartment>) cacheService.get(key);
		if (null == rwdepartmentList) {
			rwdepartmentList = new ArrayList<DicRwdepartment>();
		}
		if (rwdepartmentList.isEmpty()) {
			rwdepartmentList.addAll(DicRwdepartmentDao.findAlls("shortname"));
			cacheService.put(key, rwdepartmentList);
		}
		return rwdepartmentList;
	}

	/**
	 * ������б���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicRwdepartment> findDICAll() throws Exception {
		List<DicRwdepartment> list = DicRwdepartmentDao.findAll();
		dicMap.bdDicRwdepartmentDic(list);
		return list;
	}

	/**
	 * ����ID���ҵ�����Ϣ
	 * 
	 * @return
	 */
	public DicRwdepartment load(String id) {
		return DicRwdepartmentDao.load(id);
	}

	/**
	 * ����isNew �����������Ϣ
	 * 
	 * @param DicRwdepartment
	 * @param isNew
	 */
	public void save(DicRwdepartment dicRwdepartment, boolean isNew) {
		if (true == isNew) {
			dicRwdepartment.setCreatedtime(new Date());
			DicRwdepartmentDao.saveOrUpdate(dicRwdepartment);
		} else {
			DicRwdepartmentDao.updateEntity(dicRwdepartment, dicRwdepartment.getRwdepaid());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������Ϣ
	 * 
	 * @param id
	 */
	public void delete(String id) {
		DicRwdepartmentDao.delete(id);
		setAlterFlag();
	}

	public int findTotalCount(String condition) {

		return DicRwdepartmentDao.findTotalCount(condition, null);
	}

	public List<DicRwdepartment> find(DicRwdepartment dicRwdepartment) {
		return DicRwdepartmentDao.find(dicRwdepartment);
	}

	public List<DicRwdepartment> findByPycode(String pycode) {
		return DicRwdepartmentDao.findByPycode(pycode);
	}

	public List<DicRwdepartment> findByType(String ids, String type) throws Exception {
		String hql = " where " + type + " in(" + CommUtils.addQuotes(ids) + ")";
		List list = DicRwdepartmentDao.findAll(hql);
		dicMap.bdDicRwdepartmentDic(list);
		return list;
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRwdepartment");
	}

	public void expExcel(List<DicRwdepartment> list, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��·������λ�б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "��·������λ�б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "rwdepaid", "fullname", "shortname", "shrinkname", "bureauidDIC", "tpyeidDIC",
				"parentidDIC", "provinceidDIC", "address", "dutyperson", "telephones", "fax", "contactperson",
				"isactiveDIC", "telegramid", "hasaeiDIC", "israilstnDIC", "timscode", "isboundaryDIC",
				"nextbureauidDIC", "inflagDIC", "outflagDIC", "raillinename", "pycode", "description" };
		headArr[1] = new String[] { "����ID", "ȫ��", "���", "��д", "·��", "����", "��������", "ʡ��", "��ַ", "������", "��ϵ�绰", "����",
				"��ϵ��", "�Ƿ�����", "�籨��", "�з�AEI", "�Ƿ����վ", "TIMS��", "�Ƿ�ֽ�վ", "��һ·��", "��վ��ʾ", "��վ��ʾ", "������·", "ƴ����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��·������λ�б�");

	}

	public List<DicRwdepartment> findRwDepartment(final String pycode, final String typeid) {
		return DicRwdepartmentDao.findRwDepartment(pycode, typeid);
	}
}
