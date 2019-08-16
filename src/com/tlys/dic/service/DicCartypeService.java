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

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicCartypeDao;
import com.tlys.dic.model.DicCartype;

/**
 * @author fengym
 * 
 */
@Service
public class DicCartypeService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DicCartypeDao dicCartypeDao;
	@Autowired
	DicMap dicMap;

	public List<DicCartype> findDICAll() throws Exception {
		List<DicCartype> list = dicCartypeDao.findAll();
		dicMap.bdCartypeDic(list);
		return list;
	}

	public List<DicCartype> findAll() {
		return dicCartypeDao.findAll();
	}

	public List<DicCartype> find(DicCartype dac) {
		List<DicCartype> list = dicCartypeDao.find(dac);
		dicMap.bdCartypeDic(list);
		return list;
	}

	public DicCartype load(String id) {
		return dicCartypeDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicCartype,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicCartype dicCartype, boolean isNew) {
		if (isNew) {
			dicCartypeDao.saveOrUpdate(dicCartype);
		} else {
			dicCartypeDao.updateEntity(dicCartype, dicCartype.getCartypeid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicCartypeDao.delete(id);
		setAlterFlag();
	}

	public List<DicCartype> findByCarkindid(String id) {
		String hql = " where carkindid in(" + CommUtils.addQuotes(id) + ")";
		List trList = dicCartypeDao.findAll(hql);
		dicMap.bdCartypeDic(trList);
		return trList;
	}

	public void expExcel(List<DicCartype> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�����б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "�����б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "cartypeid", "cartypename",
				"carkindidDIC", "lightweight", "markweight", "capacity",
				"convlength", "carlength", "axisnum", "orderno", "isactiveDIC",
				"description" };
		headArr[1] = new String[] { "����ID", "����", "����", "����", "�������", "�ݻ�",
				"����", "����", "����", "˳���", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�����б�");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCartype");
	}
}
