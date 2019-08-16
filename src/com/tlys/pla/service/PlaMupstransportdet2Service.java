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
import com.tlys.pla.dao.PlaMupstransportdet2Dao;
import com.tlys.pla.model.PlaMupstransportdet2;

@Service
public class PlaMupstransportdet2Service extends _GenericService {
	@Autowired
	PlaMupstransportdet2Dao plaMupstransportdet2Dao;
	@Autowired
	CtlCorpsendwarehouseDao ctlCorpsendwarehouseDao;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicMap dicMap;

	public PlaMupstransportdet2 load(Long id) {
		return plaMupstransportdet2Dao.load(id);
	}

	public void save(PlaMupstransportdet2 plaMupstransportdet2) throws Exception {
		plaMupstransportdet2Dao.save(plaMupstransportdet2);
	}

	public void update(PlaMupstransportdet2 plaMupstransportdet2)
			throws Exception {
		// plaMupstransportdet2Dao.updateEntity(plaMupstransportdet2,
		// plaMupstransportdet2.getPlanno());
		plaMupstransportdet2Dao.update(plaMupstransportdet2);
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMupstransportdet2 plaMupstransportdet2, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaMupstransportdet2Count(plaMupstransportdet2);
		pageCtr.setTotalRecord(totalRecord);
		plaMupstransportdet2Dao.find(plaMupstransportdet2, pageCtr);

	}

	public List<PlaMupstransportdet2> find(
			PlaMupstransportdet2 plaMupstransportdet2) throws Exception {
		return plaMupstransportdet2Dao.find(plaMupstransportdet2);
	}

	public List<PlaMupstransportdet2> findPlaMupstransportdet2ByIds(
			List<Long> planIds) {
		return plaMupstransportdet2Dao.findPlaMupstransportdet2ByIds(planIds);
	}

	public List<PlaMupstransportdet2> findAll() {
		return plaMupstransportdet2Dao.findAll();
	}

	public void delete(PlaMupstransportdet2 plaMupstransportdet2) {
		if (null == plaMupstransportdet2) {
			return;
		}
		plaMupstransportdet2Dao.delete(plaMupstransportdet2);
	}

	private int getPlaMupstransportdet2Count(
			final PlaMupstransportdet2 plaMupstransportdet2) {
		int count = plaMupstransportdet2Dao
				.getPlaMupstransportdet2Count(plaMupstransportdet2);
		return count;
	}

	public List<CtlCorpsendwarehouse> findCtlCorpsendwarehouse(String corpid) {
		return ctlCorpsendwarehouseDao.findCtlCorpsendwarehouse(corpid);
	}

	public void expExcel(PlaMupstransportdet2 plaMupstransportdet2,
			HttpServletResponse response) throws Exception {
		// ��ȡ�ƻ���ţ�planno�ض������ڲ�ѯ������:35000030-201201)
		String planno = plaMupstransportdet2.getPlanno();
		String[] ss = planno.split("-");
		// ��ѯ����˵��
		DicAreacorp dic = dicAreacorpService.load(ss[0]);
		// ���·ݸ�ʽ
		String month = ss[1].substring(0, 4) + "��" + ss[1].substring(4) + "��";
		// EXCEL���ơ�SHEET���ơ�����̧ͷ
		planno = dic.getShortname() + month + "ͳ���ƻ��б�";
		// ��ѯ��ϸ�ƻ�����
		List<PlaMupstransportdet2> list = this.find(plaMupstransportdet2);
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
				"badjustamount", "badjustcarnumber","aadjustamount", "aadjustcarnumber", "carkindidDIC", "startstationidDIC",
				"senderidDIC", "receivername", "endstationidDIC", "rkindname",
				"saler", "saledepartmentDIC", "remarks" };
		headArr[1] = new String[] { "������ҵ", "�ͻ�����", "��Ʒ����", "��ƷС��", "��Ʒ����",
				"����ǰ����", "����ǰ����","����������", "��������", "����", "��վ", "������", "�ջ���", "��վ", "��·Ʒ��", "�ͻ�����",
				"���ۻ���", "��ע" };
		sheetMap.put("headArr", headArr);
		// ������ҵ �ͻ����� ��Ʒ���� ��ƷС�� ��Ʒ���� ȫ������ ���� ���� ��վ ������ �ջ��� ��վ ��·Ʒ�� �ͻ����� ���ۻ��� ��ע
		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, planno);

	}

}
