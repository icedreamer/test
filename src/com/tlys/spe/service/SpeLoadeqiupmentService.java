package com.tlys.spe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.ExportExcel;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.dao.SpeLoadeqiupmentDao;
import com.tlys.spe.model.SpeLoadeqiupment;

@Service
public class SpeLoadeqiupmentService extends _GenericService<SpeLoadeqiupment> {

	@Autowired
	SpeLoadeqiupmentDao speLoadeqiupmentDao;
	@Autowired
	DicMap dicMap;
	@Autowired
	ExportExcel exportExcel;

	public SpeLoadeqiupment getSpeLoadeqiupment(String id) {
		return speLoadeqiupmentDao.load(id);
	}

	public void save(SpeLoadeqiupment speLoadeqiupment) {
		if (null == speLoadeqiupment.getEquid()
				|| "".equals(speLoadeqiupment.getEquid())) {
			speLoadeqiupment.setEquid(CommUtils.getSeqStr(speLoadeqiupmentDao
					.getSeq(), 6));
		}
		speLoadeqiupmentDao.save(speLoadeqiupment);
	}

	public void update(SpeLoadeqiupment speLoadeqiupment) {
		speLoadeqiupmentDao.updateEntity(speLoadeqiupment, speLoadeqiupment
				.getEquid());
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speLoadeqiupment
	 * @param pageCtr
	 * @throws Exception
	 */
	public void findSpeLoadeqiupment(SpeLoadeqiupment speLoadeqiupment,
			PageCtr pageCtr) throws Exception {
		int totalRecord = getSpeLoadeqiupmentCount(speLoadeqiupment);
		pageCtr.setTotalRecord(totalRecord);
		speLoadeqiupmentDao.findSpeLoadeqiupment(speLoadeqiupment, pageCtr);
		dicMap.bdSpeLoadeqiupment(pageCtr.getRecords());
	}

	public List<SpeLoadeqiupment> findSpeLoadeqiupment()  throws Exception {
		List<SpeLoadeqiupment> list = speLoadeqiupmentDao.findAll();
		dicMap.bdSpeLoadeqiupment(list);
		return list;
	}

	public void delete(SpeLoadeqiupment speLoadeqiupment) {
		if (null == speLoadeqiupment) {
			return;
		}
		speLoadeqiupmentDao.delete(speLoadeqiupment);
	}

	public int getSpeLoadeqiupmentCount(final SpeLoadeqiupment speLoadeqiupment) {
		int count = speLoadeqiupmentDao
				.getSpeLoadeqiupmentCount(speLoadeqiupment);
		return count;
	}

	public void expExcel(List<SpeLoadeqiupment> speLoadeqiupment,
			HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��ҵ��·װж��ʩ�б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", speLoadeqiupment);
		// ����
		sheetMap.put("title", "��ҵ��·װж��ʩ�б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "equid", "linename", "corpid", "corpname",
				"equname", "crwidDIC", "kindDIC", "pcategoryname",
				"psecondkindname", "positions", "dayjobs", "dayability",
				"meastypeidDIC", "loaddepartmnet", "cardispatcher", "layouttype",
				"distancetohouse", "internallinename", "stationid",
				"stationname", "bureauid", "bureauname", "distancetostation",
				"remarks" };
		headArr[1] = new String[] { "װж��ʩID", "��·����", "��ҵID", "��ҵ����",
				"��·װж������", "ר����ID", "װж���", "��Ʒ��������", "��ƷС������", "װж����λ��",
				"����ҵ����", "��װж����", "������ʽ", "��·װж��ҵ����", "������·������ҵ����",
				"��·װж�����Ʒ�ⷿ���޲��ַ�ʽ", "��·װж�����Ʒ�ⷿ���޾���", "������·ר����", "����վID",
				"����վ����", "·��ID", "·������", "��·װж���������վ����", "����" };
		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��ҵ��·װж��ʩ�б�");

	}

}
