package com.tlys.pla.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.dao.PlaMspstransportdetDao;
import com.tlys.pla.model.PlaMspstransportdet;

@Service
public class PlaMspstransportdetService extends _GenericService {

	@Autowired
	PlaMspstransportdetDao PlaMspstransportdetDao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;

	public PlaMspstransportdet load(Long id) {
		return PlaMspstransportdetDao.load(id);
	}

	public void save(PlaMspstransportdet PlaMspstransportdet) throws Exception {
		PlaMspstransportdetDao.save(PlaMspstransportdet);
	}

	public void update(PlaMspstransportdet PlaMspstransportdet)
			throws Exception {
		PlaMspstransportdetDao.update(PlaMspstransportdet);
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMspstransportdet PlaMspstransportdet, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaMspstransportdetCount(PlaMspstransportdet);
		pageCtr.setTotalRecord(totalRecord);
		PlaMspstransportdetDao.find(PlaMspstransportdet, pageCtr);

	}

	public List<PlaMspstransportdet> findAll() {
		return PlaMspstransportdetDao.findAll();
	}

	public void delete(PlaMspstransportdet PlaMspstransportdet) {
		if (null == PlaMspstransportdet) {
			return;
		}
		PlaMspstransportdetDao.delete(PlaMspstransportdet);
	}

	private int getPlaMspstransportdetCount(
			final PlaMspstransportdet PlaMspstransportdet) {
		int count = PlaMspstransportdetDao
				.getPlaMspstransportdetCount(PlaMspstransportdet);
		return count;
	}

	public List<PlaMspstransportdet> find(
			PlaMspstransportdet PlaMspstransportdet) throws Exception {
		return PlaMspstransportdetDao.find(PlaMspstransportdet);
	}

	public void expExcel(PlaMspstransportdet plaMspstransportdet,
			HttpServletResponse response) throws Exception {
		// ��ȡ�ƻ���ţ�planno�ض������ڲ�ѯ������:35000030-201201)
		String planno = plaMspstransportdet.getPlanno();
		String[] ss = planno.split("-");
		// ��ѯ����˵��
		DicSinocorp dic = dicSinocorpService.load(ss[0]);
		// ���·ݸ�ʽ
		String month = ss[1].substring(0, 4) + "��" + ss[1].substring(4) + "��";
		// EXCEL���ơ�SHEET���ơ�����̧ͷ
		planno = dic.getShortname() + month + "��ͳ���ƻ��б�";
		// ��ѯ��ϸ�ƻ�����
		List<PlaMspstransportdet> list = this.find(plaMspstransportdet);
		// �ֵ䷭��
		dicMap.bdPlaMspsDic(list);
		Map tabDefineMap = new HashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put(planno, sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", planno);

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "corpshortname", "coustomershortname",
				"productcategoryidDIC", "productsecondidDIC", "prodductidDIC",
				"amount", "carnumber", "carkindidDIC", "startstationidDIC",
				"senderidDIC", "receivername", "endstationidDIC", "rkindname",
				"requestdate", "remarks" };
		headArr[1] = new String[] { "������ҵ", "�ͻ�����", "��Ʒ����", "��ƷС��", "��Ʒ����",
				"ȫ������", "����", "����", "��վ", "������", "�ջ���", "��վ", "��·Ʒ��", "��������",
				"��ע" };
		sheetMap.put("headArr", headArr);
		// ������ҵ �ͻ����� ��Ʒ���� ��ƷС�� ��Ʒ���� ȫ������ ���� ���� ��վ ������ �ջ��� ��վ ��·Ʒ�� �������� ��ע
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, planno);

	}

	public List<PlaMspstransportdet> findPlaMspstransportdetByIds(
			List<Long> planIds) {
		return PlaMspstransportdetDao.findPlaMspstransportdetByIds(planIds);
	}
}
