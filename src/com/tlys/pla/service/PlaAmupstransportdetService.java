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
import com.tlys.dic.dao.ctl.CtlCorpsendwarehouseDao;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.ctl.CtlCorpsendwarehouse;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.pla.dao.PlaAmupstransportdetDao;
import com.tlys.pla.model.PlaAmupstransportdet;

@Service
public class PlaAmupstransportdetService extends _GenericService {

	@Autowired
	PlaAmupstransportdetDao plaAmupstransportdetDao;
	@Autowired
	CtlCorpsendwarehouseDao ctlCorpsendwarehouseDao;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicMap dicMap;

	public PlaAmupstransportdet load(Long id) {
		return plaAmupstransportdetDao.load(id);
	}

	public void save(PlaAmupstransportdet PlaAmupstransportdet) throws Exception {
		plaAmupstransportdetDao.save(PlaAmupstransportdet);
	}

	public void update(PlaAmupstransportdet PlaAmupstransportdet) throws Exception {
		plaAmupstransportdetDao.update(PlaAmupstransportdet);
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaAmupstransportdet PlaAmupstransportdet, PageCtr pageCtr) throws Exception {
		int totalRecord = getPlaAmupstransportdetCount(PlaAmupstransportdet);
		pageCtr.setTotalRecord(totalRecord);
		plaAmupstransportdetDao.find(PlaAmupstransportdet, pageCtr);

	}

	public List<PlaAmupstransportdet> find(PlaAmupstransportdet PlaAmupstransportdet) throws Exception {
		return plaAmupstransportdetDao.find(PlaAmupstransportdet);
	}

	public List<PlaAmupstransportdet> findAll() {
		return plaAmupstransportdetDao.findAll();
	}

	public void delete(PlaAmupstransportdet PlaAmupstransportdet) {
		if (null == PlaAmupstransportdet) {
			return;
		}
		plaAmupstransportdetDao.delete(PlaAmupstransportdet);
	}

	private int getPlaAmupstransportdetCount(final PlaAmupstransportdet PlaAmupstransportdet) {
		int count = plaAmupstransportdetDao.getPlaAmupstransportdetCount(PlaAmupstransportdet);
		return count;
	}

	public List<CtlCorpsendwarehouse> findCtlCorpsendwarehouse(String corpid) {
		return ctlCorpsendwarehouseDao.findCtlCorpsendwarehouse(corpid);
	}

	public void expExcel(PlaAmupstransportdet plaAmupstransportdet,
			HttpServletResponse response) throws Exception {
		// ��ȡ�ƻ���ţ�planno�ض������ڲ�ѯ������:35000030-201201)
		String planno = plaAmupstransportdet.getPlanno();
		String[] ss = planno.split("-");
		// ��ѯ����˵��
		DicAreacorp dic = dicAreacorpService.load(ss[0]);
		// ���·ݸ�ʽ
		String month = ss[1].substring(0, 4) + "��" + ss[1].substring(4) + "��";
		// EXCEL���ơ�SHEET���ơ�����̧ͷ
		planno = dic.getShortname() + month + "ͳ�������ƻ��б�";
		// ��ѯ��ϸ�ƻ�����
		List<PlaAmupstransportdet> list = this.find(plaAmupstransportdet);
		// �ֵ䷭��
		dicMap.bdPlaMupsDic(list);
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
				"saler", "saledepartmentDIC", "remarks" };
		headArr[1] = new String[] { "������ҵ", "�ͻ�����", "��Ʒ����", "��ƷС��", "��Ʒ����",
				"ȫ������", "����", "����", "��վ", "������", "�ջ���", "��վ", "��·Ʒ��", "�ͻ�����",
				"���ۻ���", "��ע" };
		sheetMap.put("headArr", headArr);
		// ������ҵ �ͻ����� ��Ʒ���� ��ƷС�� ��Ʒ���� ȫ������ ���� ���� ��վ ������ �ջ��� ��վ ��·Ʒ�� �ͻ����� ���ۻ��� ��ע
		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, planno);
		
	}
}
