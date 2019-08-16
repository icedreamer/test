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
import com.tlys.dic.dao.DicProdcategoryDao;
import com.tlys.dic.model.DicProdcategory;

@Service
public class DicProdcategoryService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProdcategoryDao DicProdcategoryDao;
	@Autowired
	DicMap dicMap;



	/**
	 * ����������Ϣ
	 * 
	 * @return
	 */
	public List<DicProdcategory> findAll() {
		return DicProdcategoryDao.findAll(" order by orderno");
	}

	/**
	 * ����ID���ҵ�����Ϣ
	 * 
	 * @return
	 */
	public DicProdcategory load(String id) {
		return DicProdcategoryDao.load(id);
	}

	/**
	 * ����isNew �����������Ϣ
	 * 
	 * @param DicProdcategory
	 * @param isNew
	 */
	public void save(DicProdcategory DicProdcategory, boolean isNew) {
		if (true == isNew) {
			DicProdcategoryDao.saveOrUpdate(DicProdcategory);
		} else {
			DicProdcategoryDao.updateEntity(DicProdcategory, DicProdcategory
					.getCategoryid());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������Ϣ
	 * 
	 * @param id
	 */
	public void delete(String id) {
		DicProdcategoryDao.delete(id);
		setAlterFlag();

	}

	public List<DicProdcategory> find(DicProdcategory DicProdcategory) {
		return DicProdcategoryDao.find(DicProdcategory);
	}

	public void expExcel(List<DicProdcategory> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��Ʒ�����б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "��Ʒ�����б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "categoryid", "fullname", "shortname",
				"orderno", "isactiveDIC" };
		headArr[1] = new String[] { "�������", "ȫ��", "���", "˳���", "�Ƿ�����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��Ʒ�����б�");

	}
	
	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ��
	 * ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ
	 * Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag(){
		dicMap.dicAlter("dicProdcategory");
	}
}
