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
import com.tlys.dic.dao.DicRentmodeDao;
import com.tlys.dic.model.DicRentMode;

/**
 * @author ������
 * 
 */
@Service
public class DicRentmodeService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRentmodeDao dicRentmodeDao;

	@Autowired
	DicMap dicMap;

	public List<DicRentMode> findDICAll() throws Exception {
		List<DicRentMode> list = dicRentmodeDao.findAll();
		//dicMap.bdIsactive(list);
		return list;
	}

	public List<DicRentMode> findAll() {
		return dicRentmodeDao.findAll();
	}

	public List<DicRentMode> find(DicRentMode dac) {
		return dicRentmodeDao.find(dac);
	}

	public DicRentMode load(String id) {
		return dicRentmodeDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicRentmode,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicRentMode dicRentmode, boolean isNew) {
		if (isNew) {
			dicRentmode.setCreatedtime(new Date());
			dicRentmodeDao.saveOrUpdate(dicRentmode);
		} else {
			dicRentmodeDao.updateEntity(dicRentmode, dicRentmode.getRentid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicRentmodeDao.delete(id);
		setAlterFlag();
	}

	private void setAlterFlag() {
		dicMap.dicAlter("dicRentMode");
	}

	public void expExcel(List<DicRentMode> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�������޷�ʽ�б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "�������޷�ʽ�б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "rentid", "rentname", "isactiveDIC",
				"description" };
		headArr[1] = new String[] { "��ʽID", "����", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�������޷�ʽ�б�");

	}
}
