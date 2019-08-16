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
import com.tlys.pla.dao.PlaAmspstransportdetDao;
import com.tlys.pla.model.PlaAmspstransportdet;

@Service
public class PlaAmspstransportdetService extends _GenericService {

	@Autowired
	PlaAmspstransportdetDao PlaAmspstransportdetDao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicMap dicMap;

	public PlaAmspstransportdet load(Long id) {
		return PlaAmspstransportdetDao.load(id);
	}

	public void save(PlaAmspstransportdet PlaAmspstransportdet)
			throws Exception {
		PlaAmspstransportdetDao.save(PlaAmspstransportdet);
	}

	public void update(PlaAmspstransportdet PlaAmspstransportdet)
			throws Exception {
		PlaAmspstransportdetDao.update(PlaAmspstransportdet);
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaAmspstransportdet PlaAmspstransportdet,
			PageCtr<PlaAmspstransportdet> pageCtr) throws Exception {
		int totalRecord = getPlaAmspstransportdetCount(PlaAmspstransportdet);
		pageCtr.setTotalRecord(totalRecord);
		PlaAmspstransportdetDao.find(PlaAmspstransportdet, pageCtr);

	}

	public List<PlaAmspstransportdet> findAll() {
		return PlaAmspstransportdetDao.findAll();
	}

	public void delete(PlaAmspstransportdet PlaAmspstransportdet) {
		if (null == PlaAmspstransportdet) {
			return;
		}
		PlaAmspstransportdetDao.delete(PlaAmspstransportdet);
	}

	private int getPlaAmspstransportdetCount(
			final PlaAmspstransportdet PlaAmspstransportdet) {
		int count = PlaAmspstransportdetDao
				.getPlaAmspstransportdetCount(PlaAmspstransportdet);
		return count;
	}

	public List<PlaAmspstransportdet> find(
			PlaAmspstransportdet PlaAmspstransportdet) throws Exception {
		return PlaAmspstransportdetDao.find(PlaAmspstransportdet);
	}

	public void expExcel(PlaAmspstransportdet plaAmspstransportdet,
			HttpServletResponse response) throws Exception {
		// ��ȡ�ƻ���ţ�planno�ض������ڲ�ѯ������:35000030-201201)
		String planno = plaAmspstransportdet.getPlanno();
		String[] ss = planno.split("-");
		// ��ѯ����˵��
		DicSinocorp dic = dicSinocorpService.load(ss[0]);
		// ���·ݸ�ʽ
		String month = ss[1].substring(0, 4) + "��" + ss[1].substring(4) + "��";
		// EXCEL���ơ�SHEET���ơ�����̧ͷ
		planno = dic.getShortname() + month + "��ͳ�������ƻ��б�";
		// ��ѯ��ϸ�ƻ�����
		List<PlaAmspstransportdet> list = this.find(plaAmspstransportdet);
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
}
