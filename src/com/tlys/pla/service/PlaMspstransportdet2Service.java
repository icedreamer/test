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
import com.tlys.pla.dao.PlaMspstransportdet2Dao;
import com.tlys.pla.model.PlaMspstransportdet2;

@Service
public class PlaMspstransportdet2Service extends _GenericService {

	@Autowired
	PlaMspstransportdet2Dao PlaMspstransportdet2Dao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;

	public PlaMspstransportdet2 load(Long id) {
		return PlaMspstransportdet2Dao.load(id);
	}

	public void save(PlaMspstransportdet2 PlaMspstransportdet2)
			throws Exception {
		PlaMspstransportdet2Dao.save(PlaMspstransportdet2);
	}

	public void update(PlaMspstransportdet2 PlaMspstransportdet2)
			throws Exception {
		PlaMspstransportdet2Dao.update(PlaMspstransportdet2);
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMspstransportdet2 PlaMspstransportdet2, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaMspstransportdet2Count(PlaMspstransportdet2);
		pageCtr.setTotalRecord(totalRecord);
		PlaMspstransportdet2Dao.find(PlaMspstransportdet2, pageCtr);

	}

	public List<PlaMspstransportdet2> findAll() {
		return PlaMspstransportdet2Dao.findAll();
	}

	public void delete(PlaMspstransportdet2 PlaMspstransportdet2) {
		if (null == PlaMspstransportdet2) {
			return;
		}
		PlaMspstransportdet2Dao.delete(PlaMspstransportdet2);
	}

	private int getPlaMspstransportdet2Count(
			final PlaMspstransportdet2 PlaMspstransportdet2) {
		int count = PlaMspstransportdet2Dao
				.getPlaMspstransportdet2Count(PlaMspstransportdet2);
		return count;
	}

	public List<PlaMspstransportdet2> find(
			PlaMspstransportdet2 PlaMspstransportdet2) throws Exception {
		return PlaMspstransportdet2Dao.find(PlaMspstransportdet2);
	}

	public void expExcel(PlaMspstransportdet2 plaMspstransportdet2,
			HttpServletResponse response) throws Exception {
		// ��ȡ�ƻ���ţ�planno�ض������ڲ�ѯ������:35000030-201201)
		String planno = plaMspstransportdet2.getPlanno();
		String[] ss = planno.split("-");
		// ��ѯ����˵��
		DicSinocorp dic = dicSinocorpService.load(ss[0]);
		// ���·ݸ�ʽ
		String month = ss[1].substring(0, 4) + "��" + ss[1].substring(4) + "��";
		// EXCEL���ơ�SHEET���ơ�����̧ͷ
		planno = dic.getShortname() + month + "��ͳ���ƻ��б�";
		// ��ѯ��ϸ�ƻ�����
		List<PlaMspstransportdet2> list = this.find(plaMspstransportdet2);
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
				"badjustamount", "badjustcarnumber","aadjustamount", "aadjustcarnumber", "carkindidDIC", "startstationidDIC",
				"senderidDIC", "receivername", "endstationidDIC", "rkindname",
				"requestdate", "remarks" };
		headArr[1] = new String[] { "������ҵ", "�ͻ�����", "��Ʒ����", "��ƷС��", "��Ʒ����",
				"����ǰ����", "����ǰ����","����������", "��������", "����", "��վ", "������", "�ջ���", "��վ", "��·Ʒ��", "��������",
				"��ע" };
		sheetMap.put("headArr", headArr);
		// ������ҵ �ͻ����� ��Ʒ���� ��ƷС�� ��Ʒ���� ȫ������ ���� ���� ��վ ������ �ջ��� ��վ ��·Ʒ�� �������� ��ע
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, planno);

	}

	public List<PlaMspstransportdet2> findPlaMspstransportdet2ByIds(
			List<Long> planIds) {
		return PlaMspstransportdet2Dao.findPlaMspstransportdet2ByIds(planIds);
	}
}
