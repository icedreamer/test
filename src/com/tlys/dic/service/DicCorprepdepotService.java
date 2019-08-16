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
import com.tlys.dic.dao.DicCorprepdepotDao;
import com.tlys.dic.model.DicCorprepdepot;

@Service
public class DicCorprepdepotService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicCorprepdepotDao DicCorprepdepotDao;
	@Autowired
	DicMap dicMap;

	/**
	 * ������б���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicCorprepdepot> findDICAll() throws Exception {
		List<DicCorprepdepot> list = DicCorprepdepotDao.findAll();
		dicMap.bdCorprepdepotDic(list);
		return list;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @return
	 */
	public List<DicCorprepdepot> findAll() {
		return DicCorprepdepotDao.findAll();
	}

	/**
	 * ����ID���ҵ�����Ϣ
	 * 
	 * @return
	 */
	public DicCorprepdepot load(Long id) {
		return DicCorprepdepotDao.load(id);
	}

	/**
	 * ����isNew �����������Ϣ
	 * 
	 * @param DicCorprepdepot
	 * @param isNew
	 */
	public void save(DicCorprepdepot DicCorprepdepot, boolean isNew) {
		if (true == isNew) {
			DicCorprepdepotDao.saveOrUpdate(DicCorprepdepot);
		} else {
			DicCorprepdepotDao.updateEntity(DicCorprepdepot, DicCorprepdepot
					.getId());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������Ϣ
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		DicCorprepdepotDao.delete(id);
		setAlterFlag();
	}

	public List<DicCorprepdepot> find(DicCorprepdepot DicCorprepdepot) {
		List<DicCorprepdepot> list = DicCorprepdepotDao.find(DicCorprepdepot);
		dicMap.bdCorprepdepotDic(list);
		return list;
	}

	public void expExcel(List<DicCorprepdepot> DicCorprepdepots,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("���޳������б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", DicCorprepdepots);
		// ����
		sheetMap.put("title", "���޳������б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "id", "corpidDIC", "shortname",
				"stockdepotid", "isactiveDIC", "bureauidDIC", "description" };
		headArr[1] = new String[] { "���޳�����ID", "��ҵ", "���޳���������", "���޳����δ���",
				"�Ƿ�����", "·��", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "���޳������б�");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCorprepdepot");
	}
}
