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
import com.tlys.dic.dao.DicCarkindDao;
import com.tlys.dic.model.DicCarkind;

/**
 * @author fengym
 * 
 */
@Service
public class DicCarkindService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DicCarkindDao dicCarkindDao;

	@Autowired
	DicMap dicMap;

	public List<DicCarkind> findAll(){
		return dicCarkindDao.findAll();
	}

	public List<DicCarkind> find(DicCarkind dac) throws Exception {
		return dicCarkindDao.find(dac);
	}

	public DicCarkind load(String id) {
		return dicCarkindDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicCarkind,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicCarkind dicCarkind, boolean isNew) {
		if (isNew) {
			dicCarkindDao.saveOrUpdate(dicCarkind);
		} else {
			dicCarkindDao.updateEntity(dicCarkind, dicCarkind.getCarkindid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicCarkindDao.delete(id);
		setAlterFlag();
	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCarkind");
	}

	public void expExcel(List<DicCarkind> DicCarkinds,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�����б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", DicCarkinds);
		// ����
		sheetMap.put("title", "�����б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "carkindid", "fullname", "shortname",
				"isactiveStr", "description" };
		headArr[1] = new String[] { "����ID", "ȫ��", "���", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�����б�");

	}
}
