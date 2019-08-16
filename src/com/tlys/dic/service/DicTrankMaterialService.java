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

import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicTrankMaterialDao;
import com.tlys.dic.model.DicTrankMaterial;

/**
 * @author ������
 * 
 */
@Service
public class DicTrankMaterialService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicTrankMaterialDao dicTrankMaterialDao;
	@Autowired
	DicMap dicMap;

	public void expExcel(List<DicTrankMaterial> list,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��������б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "��������б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "tankmateid", "mname", "isactiveDIC",
				"description" };
		headArr[1] = new String[] { "�������ID", "����", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��������б�");

	}

	public List<DicTrankMaterial> findDICAll() throws Exception {
		List<DicTrankMaterial> list = dicTrankMaterialDao.findAll();
		//dicMap.bdIsactive(list);
		return list;
	}

	public List<DicTrankMaterial> findAll() {
		return dicTrankMaterialDao.findAll();
	}

	public List<DicTrankMaterial> find(DicTrankMaterial dac) {
		return dicTrankMaterialDao.find(dac);
	}

	public DicTrankMaterial load(String id) {
		return dicTrankMaterialDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicTrankMaterial,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicTrankMaterial dicTrankMaterial, boolean isNew) {
		if (isNew) {
			dicTrankMaterial.setCreatedtime(new Date());
			dicTrankMaterialDao.saveOrUpdate(dicTrankMaterial);
		} else {
			dicTrankMaterialDao.updateEntity(dicTrankMaterial, dicTrankMaterial
					.getTankmateid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicTrankMaterialDao.delete(id);
		setAlterFlag();
	}
	private void setAlterFlag() {
		dicMap.dicAlter("dicTrankMaterial");
	}
}
