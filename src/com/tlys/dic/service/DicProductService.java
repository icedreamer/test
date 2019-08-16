/**
 * @author ������
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
import com.tlys.dic.dao.DicProductDao;
import com.tlys.dic.model.DicProduct;

@Service
public class DicProductService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProductDao dicProductDao;
	@Autowired
	DicMap dicMap;

	/**
	 * ����������Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicProduct> findAll() {
		String sql = "select obj.* from tb_zbc_dic_product obj start with obj.parentid='0' connect by prior obj.prodid = obj.parentid";
		List<DicProduct> scList = dicProductDao.findTreeAll(sql);
		dicMap.bdDicProductTree(scList);
		return scList;
	}

	/**
	 * רΪ�����ֵ�Map�ã��������ֵ����
	 * 
	 * @return
	 */
	public List<DicProduct> findAll4Dic() {
		List<DicProduct> scList =dicProductDao.findAll();
		return scList;
	}

	/**
	 * ����ID���ҵ�����Ϣ
	 * 
	 * @return
	 */
	public DicProduct load(String id) {
		return dicProductDao.load(id);
	}

	/**
	 * ����isNew �����������Ϣ
	 * 
	 * @param DicProduct
	 * @param isNew
	 */
	public void save(DicProduct dicProduct, boolean isNew) {
		if (true == isNew) {
			dicProduct.setCreatedtime(new Date());
			dicProductDao.saveOrUpdate(dicProduct);
		} else {
			dicProductDao.updateEntity(dicProduct, dicProduct.getProdid());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������Ϣ
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicProductDao.delete(id);
		setAlterFlag();
	}

	public List<DicProduct> find(DicProduct dicProduct) {
		List<DicProduct> list = dicProductDao.find(dicProduct);
		dicMap.bdDicProductTree(list);
		return list;
	}

	public void expExcel(List<DicProduct> list, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��Ʒ�ֵ��б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "��Ʒ�ֵ��б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "prodid", "fullname", "shortname",
				"categoryidDIC", "secondkindidDIC", "rwkindidDIC", "kindDIC",
				"isactiveDIC", "searchcode", "liscode", "sinocode",
				"description" };
		headArr[1] = new String[] { "��ƷID", "ȫ��", "���", "����", "С��", "��·Ʒ��",
				"���", "�Ƿ�����", "������", "LIS����", "��׼����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��Ʒ�ֵ��б�");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicProduct");
	}
}
