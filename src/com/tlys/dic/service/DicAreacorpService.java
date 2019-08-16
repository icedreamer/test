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

import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicAreacorpDao;
import com.tlys.dic.dao.DicSinocorpDao;
import com.tlys.dic.dao.DicSinodepartmentDao;
import com.tlys.dic.model.DicAreacorp;

/**
 * @author fengym
 * 
 */
@Service
public class DicAreacorpService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicAreacorpDao dicAreacorpDao;

	@Autowired
	DicSinocorpDao dicSinocorpDao;

	@Autowired
	DicSinodepartmentDao dicSinodepartmentDao;

	@Autowired
	DicMap dicMap;

	public List<DicAreacorp> findAll() throws Exception {
		return dicAreacorpDao.findAll(" order by areaid");
	}

	public List<DicAreacorp> findByAreaid(DicAreacorp dac) throws Exception {
		List<DicAreacorp> acList = dicAreacorpDao.findById(dac);
		return acList;
	}

	public List<DicAreacorp> findDICAll() throws Exception {
		List<DicAreacorp> acList = dicAreacorpDao.findAll(" order by areaid");
		dicMap.bdProv(acList);
		return acList;
	}

	public List<DicAreacorp> find(DicAreacorp dac) throws Exception {
		List<DicAreacorp> acList = dicAreacorpDao.find(dac);
		dicMap.bdProv(acList);
		return acList;
	}

	public DicAreacorp load(String id) {
		return dicAreacorpDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicAreacorp,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicAreacorp dicAreacorp, boolean isNew) {
		if (isNew) {
			dicAreacorpDao.saveOrUpdate(dicAreacorp);
		} else {
			dicAreacorpDao.updateEntity(dicAreacorp, dicAreacorp.getAreaid());
		}
		setAlterFlag();
	}

	/**
	 * ���ҵ�ǰ����˾������ҵ��������λ�б���װ��map:key[corp,dep]
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, List> findSuborgsMap(String id) {
		HashMap subMap = new HashMap();
		List corpList = dicSinocorpDao.findByAreaid(id);
		List depList = dicSinodepartmentDao.findByParentid(id);
		subMap.put("corp", corpList);
		subMap.put("dep", depList);
		return subMap;
	}

	public void delete(String id) {
		dicAreacorpDao.delete(id);
		setAlterFlag();

	}

	public void expExcel(List<DicAreacorp> dicAreacorps,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("����˾�б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", dicAreacorps);
		// ����
		sheetMap.put("title", "����˾�б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaid", "fullname", "shortname",
				"address", "provinceidDIC", "dutyperson", "telephones", "fax",
				"contactperson", "orderno", "isactiveStr", "description" };
		headArr[1] = new String[] { "����˾ID", "ȫ��", "���", "��ַ", "ʡ��", "������",
				"�绰", "����", "��ϵ��", "����", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "����˾�б�");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicAreacorp");
	}

}
