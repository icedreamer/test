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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.dao.DicCorprailwayDao;
import com.tlys.dic.model.DicCorprailway;

@Service
public class DicCorprailwayService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicCorprailwayDao dicCorprailwayDao;
	@Autowired
	DicMap dicMap;

	public void findPageCtr(DicCorprailway obj, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getObjCount(obj);
		pageCtr.setTotalRecord(totalRecord);
		dicCorprailwayDao.findPageCtr(obj, pageCtr);
		dicMap.bdCorprailwayDic(pageCtr.getRecords());
	}

	public int getObjCount(final DicCorprailway obj) {
		int count = dicCorprailwayDao.getObjCount(obj);
		return count;
	}

	/**
	 * �������ҵר�����б���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicCorprailway> findDICAll() throws Exception {
		List<DicCorprailway> list = dicCorprailwayDao.findAll();
		dicMap.bdCorprailwayDic(list);
		return list;
	}

	/**
	 * ͨ����ҵ��ѯ����վ
	 * 
	 * @param DicCorprailway
	 * @return
	 * @throws Exception
	 */
	public List<DicCorprailway> findByCorpid(DicCorprailway DicCorprailway)
			throws Exception {
		List<DicCorprailway> list = dicCorprailwayDao.find(DicCorprailway);
		dicMap.bdCorprailwayDic(list);
		return list;
	}

	public List<DicCorprailway> findAll() {
		List<DicCorprailway> list = dicCorprailwayDao.findAll();
		return list;
	}

	/**
	 * ����ID���ҵ�����ҵר����
	 * 
	 * @return
	 */
	public DicCorprailway load(String id) {
		return dicCorprailwayDao.load(id);
	}

	/**
	 * ����isNew �����������ҵר����
	 * 
	 * @param DicCorprailway
	 * @param isNew
	 */
	public void save(DicCorprailway dicCorprailway, boolean isNew) {
		if (true == isNew) {
			dicCorprailway.setIspublic("1");
			dicCorprailwayDao.saveOrUpdate(dicCorprailway);
		} else {
			dicCorprailwayDao.updateEntity(dicCorprailway, dicCorprailway
					.getCrwid());
		}
		setAlterFlag();
	}

	/**
	 * ����IDɾ��������ҵר����
	 * 
	 * @param id
	 */
	public void delete(String id) {
		dicCorprailwayDao.delete(id);
		setAlterFlag();
	}

	public List<DicCorprailway> find(DicCorprailway DicCorprailway) {
		return dicCorprailwayDao.find(DicCorprailway);
	}

	public void expExcel(List<DicCorprailway> DicCorprailways,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��ҵר�����б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", DicCorprailways);
		// ����
		sheetMap.put("title", "��ҵר�����б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "crwid", "corpidDIC", "fullname",
				"shortname", "neareststationDIC", "ispublicDIC", "sinocode",
				"isactiveDIC", "description" };
		headArr[1] = new String[] { "ר����ID", "��ҵ����", "ȫ��", "���", "����ս", "�Ƿ���",
				"��׼����", "�Ƿ�����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��ҵר�����б�");

	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicCorprailway");
	}
	/**
	 * ��ѯ�� TB_ZBC_DIC_CORPRAILWAY �е�CRWID ���ֵ
	 * @return
	 */
	public String getMaxCrwid(){
		
		return dicCorprailwayDao.getMaxCrwid();
	}
}
