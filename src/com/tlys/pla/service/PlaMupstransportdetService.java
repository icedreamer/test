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
import com.tlys.pla.dao.PlaMupstransportdetDao;
import com.tlys.pla.model.PlaMupstransportdet;

@Service
public class PlaMupstransportdetService extends _GenericService {
	@Autowired
	PlaMupstransportdetDao plaMupstransportdetDao;
	@Autowired
	CtlCorpsendwarehouseDao ctlCorpsendwarehouseDao;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicMap dicMap;

	public PlaMupstransportdet load(Long id) {
		return plaMupstransportdetDao.load(id);
	}

	public void save(PlaMupstransportdet plaMupstransportdet) throws Exception {
		plaMupstransportdetDao.save(plaMupstransportdet);
	}

	public void update(PlaMupstransportdet plaMupstransportdet)
			throws Exception {
		// plaMupstransportdetDao.updateEntity(plaMupstransportdet,
		// plaMupstransportdet.getPlanno());
		plaMupstransportdetDao.update(plaMupstransportdet);
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMupstransportdet plaMupstransportdet, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaMupstransportdetCount(plaMupstransportdet);
		pageCtr.setTotalRecord(totalRecord);
		plaMupstransportdetDao.find(plaMupstransportdet, pageCtr);

	}

	public List<PlaMupstransportdet> find(
			PlaMupstransportdet plaMupstransportdet) throws Exception {
		return plaMupstransportdetDao.find(plaMupstransportdet);
	}

	public List<PlaMupstransportdet> findPlaMupstransportdetByIds(
			List<Long> planIds) {
		return plaMupstransportdetDao.findPlaMupstransportdetByIds(planIds);
	}

	public List<PlaMupstransportdet> findAll() {
		return plaMupstransportdetDao.findAll();
	}

	public void delete(PlaMupstransportdet plaMupstransportdet) {
		if (null == plaMupstransportdet) {
			return;
		}
		plaMupstransportdetDao.delete(plaMupstransportdet);
	}

	private int getPlaMupstransportdetCount(
			final PlaMupstransportdet plaMupstransportdet) {
		int count = plaMupstransportdetDao
				.getPlaMupstransportdetCount(plaMupstransportdet);
		return count;
	}

	public List<CtlCorpsendwarehouse> findCtlCorpsendwarehouse(String corpid) {
		return ctlCorpsendwarehouseDao.findCtlCorpsendwarehouse(corpid);
	}

	public void expExcel(PlaMupstransportdet plaMupstransportdet,
			HttpServletResponse response) throws Exception {
		// ��ȡ�ƻ���ţ�planno�ض������ڲ�ѯ������:35000030-201201)
		String planno = plaMupstransportdet.getPlanno();
		String[] ss = planno.split("-");
		// ��ѯ����˵��
		DicAreacorp dic = dicAreacorpService.load(ss[0]);
		// ���·ݸ�ʽ
		String month = ss[1].substring(0, 4) + "��" + ss[1].substring(4) + "��";
		// EXCEL���ơ�SHEET���ơ�����̧ͷ
		planno = dic.getShortname() + month + "ͳ���ƻ��б�";
		// ��ѯ��ϸ�ƻ�����
		List<PlaMupstransportdet> list = this.find(plaMupstransportdet);
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
