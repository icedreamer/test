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
import com.tlys.dic.dao.DicProdsecondkindDao;
import com.tlys.dic.model.DicProdsecondkind;

@Service
public class DicProdsecondkindService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProdsecondkindDao dicProdsecondkindDao;
	@Autowired
	DicMap dicMap;

	/**
	 * ����������Ϣ
	 * 
	 * @return
	 */
	public List<DicProdsecondkind> findAll() {
		return dicProdsecondkindDao.findAll();
	}

	public List<DicProdsecondkind> findDICAll() {
		List<DicProdsecondkind> list = dicProdsecondkindDao.findAll();
		dicMap.bdProdsecondkindDic(list);
		return list;
	}

	/**
	 * ����ID���ҵ�����Ϣ
	 * 
	 * @return
	 */
	public DicProdsecondkind load(String id) {
		return dicProdsecondkindDao.load(id);
	}

	/**
	 * ����isNew �����������Ϣ
	 * 
	 * @param DicProdsecondkind
	 * @param isNew
	 */
	public void save(DicProdsecondkind DicProdsecondkind, boolean isNew) {
		if (true == isNew) {
			dicProdsecondkindDao.saveOrUpdate(DicProdsecondkind);
		} else {
			dicProdsecondkindDao.updateEntity(DicProdsecondkind,
					DicProdsecondkind.getSecondkindid());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������Ϣ
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicProdsecondkindDao.delete(id);
		setAlterFlag();

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicProdsecondkind");
	}

	public List<DicProdsecondkind> find(DicProdsecondkind dicProdsecondkind) {
		List<DicProdsecondkind> list = dicProdsecondkindDao
				.find(dicProdsecondkind);
		dicMap.bdProdsecondkindDic(list);
		return list;
	}

	public void expExcel(List<DicProdsecondkind> list,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��ƷС���б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "��ƷС���б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "secondkindid", "categoryidDIC",
				"fullname", "shortname", "orderno", "rwkindidDIC", "kindDIC", "isactiveDIC" };
		headArr[1] = new String[] { "С�����", "����", "ȫ��", "���", "˳���", "��·Ʒ��",
				"���",  "�Ƿ�����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��ƷС���б�");

	}

	public List<DicProdsecondkind> findDicProdsecondkind(String[] categoryids) {
		return dicProdsecondkindDao.findDicProdsecondkind(categoryids);
	}
}
