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
import com.tlys.dic.dao.DicGoodscategoryDao;
import com.tlys.dic.model.DicGoodscategory;

/**
 * @author ������
 * 
 */
@Service
public class DicGoodscategoryService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicGoodscategoryDao dicGoodscategoryDao;
	@Autowired
	DicMap dicMap;

	public List<DicGoodscategory> findDICAll() throws Exception {
		List<DicGoodscategory> list = dicGoodscategoryDao.findAll();
		dicMap.bdGoodscategoryDic(list);
		return list;
	}

	public List<DicGoodscategory> findAll() {
		return dicGoodscategoryDao.findAll(" order by orderno");
	}

	public List<DicGoodscategory> find(DicGoodscategory dac) {
		List<DicGoodscategory> list = dicGoodscategoryDao.find(dac);
		dicMap.bdGoodscategoryDic(list);
		return list;
	}

	public DicGoodscategory load(String id) {
		return dicGoodscategoryDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicGoodscategory,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicGoodscategory dicGoodscategory, boolean isNew) {
		if (isNew) {
			dicGoodscategory.setCreatedtime(new Date());
			dicGoodscategoryDao.saveOrUpdate(dicGoodscategory);
		} else {
			dicGoodscategoryDao.updateEntity(dicGoodscategory, dicGoodscategory.getGoodsid());
		}
		setAlterFlag();
	}

	public void delete(String id) {
		dicGoodscategoryDao.delete(id);
		setAlterFlag();
	}

	public void expExcel(List<DicGoodscategory> DicGoodscategorys, HttpServletResponse response)
			throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��װ�����б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", DicGoodscategorys);
		// ����
		sheetMap.put("title", "��װ�����б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "goodsid", "goodsname", "parentidDIC", "orderno",
				"isactiveDIC", "description" };
		headArr[1] = new String[] { "��·����ID", "����", "��������", "˳���", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��װ�����б�");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicGoodscategory");
	}

	public List<DicGoodscategory> findDicGoodscategory(List<String> goodsIdList) {
		return dicGoodscategoryDao.findDicGoodscategory(goodsIdList);
	}
}
