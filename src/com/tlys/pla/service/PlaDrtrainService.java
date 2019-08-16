package com.tlys.pla.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.pla.dao.PlaDrtrainDao;
import com.tlys.pla.model.PlaDrtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaDrtrainService {

	@Autowired
	private PlaDrtrainDao plaDrtrainDao;
	@Autowired
	DicMap dicMap;

	public void findPlaDrtrain(PlaDrtrain plaDrtrain, PageCtr<PlaDrtrain> pageCtr) {
		pageCtr.setTotalRecord(plaDrtrainDao.getPlaDrtrainCount(plaDrtrain));
		plaDrtrainDao.findPlaDrtrain(plaDrtrain, pageCtr);
	}

	public PlaDrtrain load(Long id) {
		return plaDrtrainDao.load(id);
	}

	public void exportToExcel1(HSSFWorkbook workbook, PlaDrtrain plaDrtrain, SysUser sysUser) throws Exception {
		// �к�
		int l = 0;
		// �к�
		int j = 0;
		String corpname = "";
		String shortname = "";
		Map<String, DicSinocorp> dicSinocorpMap = dicMap.getCorpAllMap();
		DicSinocorp dicSinocorp = dicSinocorpMap.get(sysUser.getCorpid());
		if (null != dicSinocorp) {
			corpname = dicSinocorp.getFullname();
			shortname = dicSinocorp.getShortname();
		}
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		String month = plaDrtrain.getMonth();
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, shortname + month + "����·�복�������ƻ����˻���)");

		HSSFRow rowHeader = ExcelUtil.createRow(sheet, l++, 800);

		ExcelUtil.createCell(rowHeader, 0, cellStyle, corpname + month + "����·�복�������ƻ����˻���)");

		String[] titles = { "�¶�", "װ������", "����˾", "��ҵ����", "�복���", "������", "��վ", "��վ", "�ջ���", "������", "��ע����", "Ʒ��",
				"ԭ�ᳵ��", "ԭ�����", "��׼����", "��׼����", "��װ����", "��װ����", "���", "������", "�����", "��׼Ҫ���ֺ�", "�복����", "���ʱ��",
				"���ۼ���ɳ���", "����״̬", "����ʱ��", "д��ʱ��", "����" };
		ExcelUtil.mergeCell(sheet, 0, 0, 0, titles.length - 1);
		HSSFRow rowTitle = ExcelUtil.createRow(sheet, l++, 500);
		for (int i = 0; i < titles.length; i++) {
			ExcelUtil.createCell(rowTitle, j++, cellStyle, titles[i]);
		}
		Map<String, DicAreacorp> dicAreaMap = dicMap.getAreaMap();
		Map<String, DicGoodscategory> dicGoodsMap = dicMap.getGoodscategoryMap();
		Map<String, DicCarkind> dicCarkindMap = dicMap.getCarkindMap();
		Map<String, String> dicRwBureauMap = dicMap.getRwbureauMap();
		Map<String, String> rwdepartmentMap = dicMap.getRwdepartmentMap();
		List<PlaDrtrain> plaDrtrains = plaDrtrainDao.findPlaDrtrain(plaDrtrain);
		if (null != plaDrtrains && !plaDrtrains.isEmpty()) {
			for (int i = 0; i < plaDrtrains.size(); i++) {
				PlaDrtrain plaDrtrain2 = plaDrtrains.get(i);
				j = 0;
				HSSFRow row = ExcelUtil.createRow(sheet, l++, -1);
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getMonth());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getLoaddate());
				DicAreacorp dicAreacorp = dicAreaMap.get(plaDrtrain2.getAreaid());
				ExcelUtil.createCell(row, j++, null, null == dicAreacorp ? "" : dicAreacorp.getShortname());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getCorpshortname());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getSerial());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getSendername());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getStartstationname());
				ExcelUtil.createCell(row, j++, null, rwdepartmentMap.get(plaDrtrain2.getEndstationid()));
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getReceivername());
				DicCarkind reqDicCarkind = dicCarkindMap.get(plaDrtrain2.getRequestcarkindid());
				ExcelUtil.createCell(row, j++, null, null == reqDicCarkind ? "" : reqDicCarkind.getShortname());
				DicCarkind acceptDicCarkind = dicCarkindMap.get(plaDrtrain2.getAcceptcarkindid());
				ExcelUtil.createCell(row, j++, null, null == acceptDicCarkind ? "" : acceptDicCarkind.getShortname());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getRwkindname());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getRequestcars());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getRequestamount());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getAcceptcars());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getAcceptamount());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getLoadcars());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getLoadamount());
				String kind = plaDrtrain2.getKind();
				String kindStr = "";
				if (kind != null) {
					if (kind.equals("1")) {
						kindStr = "ͳ��";
					} else if (kind.equals("0")) {
						kindStr = "��ͳ��";
					} else if (kind.equals("3")) {
						kindStr = "��Ʒ��";
					} else if (kind.equals("4")) {
						kindStr = "ú";
					} else if (kind.equals("5")) {
						kindStr = "ԭ��";
					} else if (kind.equals("6")) {
						kindStr = "����";
					} else if (kind.equals("9")) {
						kindStr = "����";
					}
				}
				ExcelUtil.createCell(row, j++, null, kindStr);
				ExcelUtil.createCell(row, j++, null, dicRwBureauMap.get(plaDrtrain2.getSendbureauid()));
				ExcelUtil.createCell(row, j++, null, dicRwBureauMap.get(plaDrtrain2.getArrivalbureauid()));
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getAcceptcarno());
				String requesttype = plaDrtrain2.getRequesttype();
				String requestStr = "";
				if (null != requesttype) {
					if ("0".equals(requesttype)) {
						requestStr = "���복";
					} else if ("1".equals(requesttype)) {
						requestStr = "��ʱ�복";
					}
				}
				ExcelUtil.createCell(row, j++, null, requestStr);
				ExcelUtil.createCell(row, j++, null, CommUtils.dateFormat(plaDrtrain2.getIndbtime()));
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getMtotalcars());
				String checkstatus = plaDrtrain2.getCheckstatus();
				String checkstatusStr = "";
				if (null != checkstatus) {
					if (checkstatus.equals("00")) {
						checkstatusStr = "ԭ��";
					} else if (checkstatus.equals("01")) {
						checkstatusStr = "����";
					} else if (checkstatus.equals("02")) {
						checkstatusStr = "����";
					} else if (checkstatus.equals("03")) {
						checkstatusStr = "�ݲ���";
					} else if (checkstatus.equals("04")) {
						checkstatusStr = "����";
					} else if (checkstatus.equals("07")) {
						checkstatusStr = "����";
					}
				}
				ExcelUtil.createCell(row, j++, null, checkstatusStr);
				ExcelUtil.createCell(row, j++, null, CommUtils.dateFormat(plaDrtrain2.getChecktime()));
				ExcelUtil.createCell(row, j++, null, CommUtils.dateFormat(plaDrtrain2.getWritetime()));
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getRemarks());

			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = CommUtils.getString(corpname + month + "����·�복�������ƻ����˻���)");
		ExcelUtil.writeWorkbook(response, workbook, fileName);
	}

	public void exportToExcel(HSSFWorkbook workbook, PlaDrtrain plaDrtrain, SysUser sysUser) throws Exception {
		// �к�
		int l = 0;
		// �к�
		int j = 0;
		String corpname = "";
		String shortname = "";
		Map<String, DicSinocorp> dicSinocorpMap = dicMap.getCorpAllMap();
		DicSinocorp dicSinocorp = dicSinocorpMap.get(sysUser.getCorpid());
		if (null != dicSinocorp) {
			corpname = dicSinocorp.getFullname();
			shortname = dicSinocorp.getShortname();
		}
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		String month = plaDrtrain.getMonth();
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, shortname + month + "����·�복�������ƻ����˻���)");

		HSSFRow rowHeader = ExcelUtil.createRow(sheet, l++, 800);

		ExcelUtil.createCell(rowHeader, 0, cellStyle, corpname + month + "����·�복�������ƻ����˻���)");

		String[] titles = { "��ҵ����","װ������",  "��վ", "��վ", "����", "������", "�ջ���", "������", "��������", "Ʒ��", "Ʒ��", "�복����",
				"����" };
		ExcelUtil.mergeCell(sheet, 0, 0, 0, titles.length - 1);
		HSSFRow rowTitle = ExcelUtil.createRow(sheet, l++, 500);
		for (int i = 0; i < titles.length; i++) {
			ExcelUtil.createCell(rowTitle, j++, cellStyle, titles[i]);
		}
		sheet.createFreezePane(0, 2);
		Map<String, DicAreacorp> dicAreaMap = dicMap.getAreaMap();
		Map<String, DicGoodscategory> dicGoodsMap = dicMap.getGoodscategoryMap();
		Map<String, DicCarkind> dicCarkindMap = dicMap.getCarkindMap();
		Map<String, String> dicRwBureauMap = dicMap.getRwbureauMap();
		Map<String, String> rwdepartmentMap = dicMap.getRwdepartmentMap();
		List<PlaDrtrain> plaDrtrains = plaDrtrainDao.findPlaDrtrain(plaDrtrain);
		if (null != plaDrtrains && !plaDrtrains.isEmpty()) {
			for (int i = 0; i < plaDrtrains.size(); i++) {
				PlaDrtrain plaDrtrain2 = plaDrtrains.get(i);
				j = 0;
				HSSFRow row = ExcelUtil.createRow(sheet, l++, -1);
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getCorpshortname());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getLoaddate());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getStartstationname());
				ExcelUtil.createCell(row, j++, null, rwdepartmentMap.get(plaDrtrain2.getEndstationid()));
				ExcelUtil.createCell(row, j++, null, dicRwBureauMap.get(plaDrtrain2.getArrivalbureauid()));
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getSendername());
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getReceivername());

				// ������
				DicCarkind reqDicCarkind = dicCarkindMap.get(plaDrtrain2.getRequestcarkindid());
				ExcelUtil.createCell(row, j++, null, null == reqDicCarkind ? "" : reqDicCarkind.getShortname());
				// ��������
				DicCarkind acceptDicCarkind = dicCarkindMap.get(plaDrtrain2.getAcceptcarkindid());
				ExcelUtil.createCell(row, j++, null, null == acceptDicCarkind ? "" : acceptDicCarkind.getShortname());

				// DicCarkind dicCarkind =
				// dicCarkindMap.get(plaDrtrain2.getRequestcarkindid());
				// ExcelUtil.createCell(row, j++, null, null == dicCarkind ? ""
				// : dicCarkind.getShortname());
				// // ��������
				// ExcelUtil.createCell(row, j++, null, "");
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getRwkindname());

				String kind = plaDrtrain2.getKind();
				String kindStr = "";
				if (kind != null) {
					if (kind.equals("1")) {
						kindStr = "ͳ��";
					} else if (kind.equals("0")) {
						kindStr = "��ͳ��";
					} else if (kind.equals("3")) {
						kindStr = "��Ʒ��";
					} else if (kind.equals("4")) {
						kindStr = "ú";
					} else if (kind.equals("5")) {
						kindStr = "ԭ��";
					} else if (kind.equals("6")) {
						kindStr = "����";
					} else if (kind.equals("9")) {
						kindStr = "����";
					}
				}
				ExcelUtil.createCell(row, j++, null, kindStr);

				String requesttype = plaDrtrain2.getRequesttype();
				String requestStr = "";
				if (null != requesttype) {
					if ("0".equals(requesttype)) {
						requestStr = "���복";
					} else if ("1".equals(requesttype)) {
						requestStr = "��ʱ�복";
					}
				}
				ExcelUtil.createCell(row, j++, null, requestStr);
				ExcelUtil.createCell(row, j++, null, plaDrtrain2.getRemarks());

			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = CommUtils.getString(corpname + month + "����·�복�������ƻ����˻���)");
		ExcelUtil.writeWorkbook(response, workbook, fileName);
	}

	public boolean hasGen(String month) {
		return plaDrtrainDao.hasGen(month);
	}

	public Object[] call_P_ZBC_PLAN_GENDRTRAIN(String month) {
		return plaDrtrainDao.call_P_ZBC_PLAN_GENDRTRAIN(month);
	}
}
