package com.tlys.equ.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.equ.dao.EquMcarcostDao;
import com.tlys.equ.dao.EquMcarcostdetDao;
import com.tlys.equ.dao.EquMcarcostdetitemDao;
import com.tlys.equ.model.EquMcarcost;
import com.tlys.equ.model.EquMcarcostdet;
import com.tlys.equ.model.EquMcarcostdetitem;

@Service
public class EquMcarcostService {

	private Log log = LogFactory.getLog(EquMcarcostService.class);
	@Autowired
	EquMcarcostDao equMcarcostDao;
	@Autowired
	EquMcarcostdetDao equMcarcostdetDao;
	@Autowired
	EquMcarcostdetitemDao equMcarcostdetitemDao;

	public void findEquMcarcostdet(EquMcarcostdet equMcarcostdet, PageCtr<EquMcarcostdet> pageCtr) {
		pageCtr.setTotalRecord(equMcarcostdetDao.getEquMcarcostdetCount(equMcarcostdet));
		equMcarcostdetDao.findEquMcarcostdet(equMcarcostdet, pageCtr);
	}

	public List<EquMcarcost> findEquMcarcost(List<String> monthlyidList) {
		return equMcarcostDao.findEquMcarcost(monthlyidList);
	}

	public List<EquMcarcostdetitem> findEquMcarcostdetitem(List<String> detidList, String costids) {
		return equMcarcostdetitemDao.findEquMcarcostdetitem(detidList, costids);
	}

	public void update(EquMcarcostdetitem equMcarcostdetitem) {
		equMcarcostdetitemDao.update(equMcarcostdetitem);
	}

	public void update(EquMcarcost equMcarcost) {
		equMcarcostDao.update(equMcarcost);
	}

	public EquMcarcost getEquMcarcost(String corpid, String month) {
		return equMcarcostDao.load(CommUtils.getString(corpid, "-", month));
	}

	public EquMcarcostdetitem getEquMcarcostdetitem(String detid, String costid) {
		return equMcarcostdetitemDao.getEquMcarcostdetitem(detid, costid);
	}

	// public EquMcarcostdetitem load(long detid) {
	// return equMcarcostdetitemDao.load(detid);
	// }

	public Object[] call_P_INIT_MCARCOST(String corpid, String month, String creator) {
		return equMcarcostDao.call_P_INIT_MCARCOST(corpid, month, creator);
	}

	public List<String> findMonthsOfCorp(Set<String> corpidSet) {
		return equMcarcostDao.findMonthsOfCorp(corpidSet);
	}

	public void exportExcel(HSSFWorkbook workbook, List<EquMcarcostdetitem> equMcarcostdetitems,
			List<String> monthList, List<Object[]> equMcarcostdetList,
			Map<String, DicSinocorp> dicSinocorpMap) {
		// ��������
		Font fontHeader = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD,
				HSSFFont.COLOR_NORMAL, (short) 20);
		// ���е�Ԫ����ʽ
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_CENTER, fontHeader);

		// ����sheet
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "�������޷���");
		sheet.createFreezePane(4, 2, 4, 2);
		sheet.setDefaultColumnWidth(10);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(3, 3000);
		// ����������
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);

		// �ϲ���Ԫ������ 6 + monthList.size() * 2
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 5 + monthList.size() * 2);
		CellRangeAddress cellRangeAddressHeader = new CellRangeAddress(rowHeader.getRowNum(),
				rowHeader.getRowNum(), 0, 5 + monthList.size() * 2);
		ExcelUtil.setRegionStyle(sheet, cellRangeAddressHeader, style);
		// ������Ԫ������
		ExcelUtil.createCell(rowHeader, 0, style, "�������޷���");

		CellStyle styleTitle = workbook.createCellStyle();
		styleTitle.setAlignment(CellStyle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleTitle.setBorderBottom(CellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(CellStyle.BORDER_THIN);
		styleTitle.setBorderRight(CellStyle.BORDER_THIN);
		styleTitle.setBorderTop(CellStyle.BORDER_THIN);
		HSSFFont fontTitle = workbook.createFont();
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styleTitle.setFont(fontTitle);
		styleTitle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

		CellStyle styleDescription = workbook.createCellStyle();
		styleDescription.setAlignment(CellStyle.ALIGN_CENTER);
		styleDescription.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		HSSFRow rowTitle = ExcelUtil.createRow(sheet, 1, 500);
		int j = 0;
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "��ҵ����");
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "����");
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "��֧");
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "��Ŀ");
		for (int i = 0; i < monthList.size(); i++) {
			String month = monthList.get(i);
			ExcelUtil.createCell(rowTitle, j, styleTitle, month);
			CellRangeAddress cellRangeAddress = new CellRangeAddress(rowTitle.getRowNum(), rowTitle
					.getRowNum(), j, j + 1);
			ExcelUtil.setRegionStyle(sheet, cellRangeAddress, styleTitle);
			ExcelUtil.mergeCell(sheet, rowTitle.getRowNum(), rowTitle.getRowNum(), j, j + 1);
			j = j + 2;
		}

		ExcelUtil.createCell(rowTitle, j, styleTitle, "���");
		ExcelUtil.mergeCell(sheet, 1, 1, j, j + 1);
		ExcelUtil.setRegionBorder(workbook, sheet, CellStyle.BORDER_THIN, 1, 1, j, j + 1);
		// ֧��
		List<String> paymentList = new ArrayList<String>();
		// ����
		List<String> incomeList = new ArrayList<String>();
		// �½� (ÿ���¶�Ӧ��Ӧ��С��)
		Map<String, Double> monthNeedBalanceMap = new HashMap<String, Double>();
		// �½� (ÿ���¶�ʵ��ʵ��С��)
		Map<String, Double> monthRealBalanceMap = new HashMap<String, Double>();
		// ���(�½������������Ԫ���ֵ��Ӧ��Ӧ��)
		Map<String, Double> yearNeedBalanceMap = new HashMap<String, Double>();
		// ���(�½������������Ԫ���ֵ��ʵ��ʵ��)
		Map<String, Double> yearRealBalanceMap = new HashMap<String, Double>();
		// �ܼƣ��¶�Ӧ��Ӧ��)
		Map<String, Double> totalDueCostByMonthMap = new HashMap<String, Double>();
		// �ܼƣ��¶�ʵ��ʵ��)
		Map<String, Double> totalRealCostByMonthMap = new HashMap<String, Double>();
		Map<String, EquMcarcostdetitem> equMcarcostdetitemMap = new HashMap<String, EquMcarcostdetitem>();
		for (int i = 0; i < equMcarcostdetitems.size(); i++) {
			EquMcarcostdetitem equMcarcostdetitem = equMcarcostdetitems.get(i);
			String corpid = "";
			DicSinocorp dicSinocorp = equMcarcostdetitem.getDicSinocorp();
			if (null != dicSinocorp) {
				corpid = dicSinocorp.getCorpid();
			}
			String carno = equMcarcostdetitem.getCarno();
			String detid = equMcarcostdetitem.getDetid();
			// String costname =
			// equMcarcostdetitem.getCostname();//.getCostid();
			String costname = equMcarcostdetitem.getCostname();
			String key = CommUtils.getString(detid, "_", costname);
			String month = equMcarcostdetitem.getMonth();
			Double dueamount = equMcarcostdetitem.getDueamount();
			Double realamount = equMcarcostdetitem.getRealamount();
			if (null == dueamount) {
				dueamount = 0d;
			}
			if (null == realamount) {
				realamount = 0d;
			}
			Double monthNeedBalance = monthNeedBalanceMap.get(detid);
			Double monthRealBalance = monthRealBalanceMap.get(detid);
			Double totalDueCost = totalDueCostByMonthMap.get(month);
			Double totalRealCost = totalRealCostByMonthMap.get(month);
			if (null == totalDueCost) {
				totalDueCost = 0d;
			}
			if (null == totalRealCost) {
				totalRealCost = 0d;
			}
			if (null == monthNeedBalance) {
				monthNeedBalance = 0d;
			}
			if (null == monthRealBalance) {
				monthRealBalance = 0d;
			}
			String yearKey = CommUtils.getString(corpid, "_", carno, "_", costname);
			Double yearNeedBalance = yearNeedBalanceMap.get(yearKey);
			Double yearRealBalance = yearRealBalanceMap.get(yearKey);
			if (null == yearNeedBalance) {
				yearNeedBalance = 0d;
			}
			if (null == yearRealBalance) {
				yearRealBalance = 0d;
			}
			yearNeedBalanceMap.put(yearKey, dueamount + yearNeedBalance);
			yearRealBalanceMap.put(yearKey, realamount + yearRealBalance);
			if (null != costname && "�⳵��".equals(costname)) {
				monthNeedBalanceMap.put(detid, dueamount + monthNeedBalance);
				monthRealBalanceMap.put(detid, realamount + monthRealBalance);
				totalDueCostByMonthMap.put(month, dueamount + totalDueCost);
				totalRealCostByMonthMap.put(month, realamount + totalRealCost);
			} else {
				monthNeedBalanceMap.put(detid, monthNeedBalance - dueamount);
				monthRealBalanceMap.put(detid, monthRealBalance - realamount);
				totalDueCostByMonthMap.put(month, totalDueCost - dueamount);
				totalRealCostByMonthMap.put(month, totalRealCost - realamount);
			}
			equMcarcostdetitemMap.put(key, equMcarcostdetitem);
			if ("�⳵��".equals(costname)) {
				if (!incomeList.contains(costname)) {
					incomeList.add(costname);
				}
			} else {
				if (!paymentList.contains(costname)) {
					paymentList.add(costname);
				}
			}
		}

		// ��ҵ���� ���� ��֧ ��Ŀ 201201 201202 ........201212 ��� ��ע

		int m = 2; // �к�
		for (int i = 0; i < equMcarcostdetList.size(); i++) {
			int n = 1; // �к�
			Object[] o = equMcarcostdetList.get(i);
			String cursorCorpid = o[0].toString();
			DicSinocorp dicSinocorp = dicSinocorpMap.get(cursorCorpid);
			String cursorCarno = o[1].toString();
			HSSFRow rowDataIncome = ExcelUtil.createRow(sheet, m++, 400);
			int rowNum = rowDataIncome.getRowNum();
			// ��ҵ��
			ExcelUtil.createCell(rowDataIncome, 0, styleDescription, null == dicSinocorp ? ""
					: dicSinocorp.getShortname());
			ExcelUtil.mergeCell(sheet, m - 1, m + paymentList.size() + incomeList.size() + 1, 0, 0);
			// �ϲ���Ԫ��(���� ���кϲ�)
			ExcelUtil.mergeCell(sheet, m - 1, m + incomeList.size() + paymentList.size() + 1, n, n);
			// ������
			ExcelUtil.createCell(rowDataIncome, n++, styleDescription, cursorCarno);

			// �ϲ���Ԫ��(���� ���кϲ�)
			ExcelUtil.mergeCell(sheet, rowNum, rowNum + incomeList.size(), n, n);
			// ��֧��
			ExcelUtil.createCell(rowDataIncome, n++, styleDescription, "����");

			ExcelUtil.createCell(rowDataIncome, n++, null, "");

			// ��������(+1 ��Ϊ��ʡ������"Ӧ��","ʵ��")
			for (int k2 = 0; k2 < monthList.size() + 1; k2++) {
				ExcelUtil.createCell(rowDataIncome, n++, null, "Ӧ��");
				ExcelUtil.createCell(rowDataIncome, n++, null, "ʵ��");
			}
			// ѭ����ʾÿ���µ���������
			for (int k = 0; k < incomeList.size(); k++) {
				n = 3;
				String costname = incomeList.get(k);
				HSSFRow rowIncome = ExcelUtil.createRow(sheet, m++, 400);
				// // ��ҵ��
				// ExcelUtil.createCell(rowIncome, 0, null, null == dicSinocorp
				// ? "" : dicSinocorp
				// .getShortname());
				ExcelUtil.createCell(rowIncome, n++, null, costname);
				for (int k2 = 0; k2 < monthList.size(); k2++) {
					String month = monthList.get(k2);
					// 32000000-201211-XFB00002
					String detid = CommUtils.getString(cursorCorpid, "-", month, "-", cursorCarno);
					String key = CommUtils.getString(detid, "_", costname);
					EquMcarcostdetitem equMcarcostdetitem = equMcarcostdetitemMap.get(key);
					Double dueamount = null;
					Double realamount = null;
					if (null != equMcarcostdetitem) {
						dueamount = equMcarcostdetitem.getDueamount();
						realamount = equMcarcostdetitem.getRealamount();
					}
					ExcelUtil.createCell(rowIncome, n++, null,
							null == dueamount || 0 == dueamount ? "" : String.valueOf(dueamount));
					ExcelUtil.createCell(rowIncome, n++, null, null == realamount
							|| 0 == realamount ? "" : String.valueOf(realamount));

				}
				String yearKey = CommUtils.getString(cursorCorpid, "_", cursorCarno, "_", costname);
				Double yearNeedBalance = yearNeedBalanceMap.get(yearKey);
				Double yearRealBalance = yearRealBalanceMap.get(yearKey);
				ExcelUtil.createCell(rowIncome, n++, null, null == yearNeedBalance ? "" : String
						.valueOf(yearNeedBalance));
				ExcelUtil.createCell(rowIncome, n++, null, null == yearRealBalance ? "" : String
						.valueOf(yearRealBalance));
			}
			n = 2;
			// ֧�� ��һ��
			HSSFRow rowDataPayment = ExcelUtil.createRow(sheet, m++, 400);
			rowNum = rowDataPayment.getRowNum();

			// // ��ҵ��
			// ExcelUtil.createCell(rowDataPayment, 0, null, null == dicSinocorp
			// ? "" : dicSinocorp
			// .getShortname());

			// �ϲ���Ԫ��(֧�� ���кϲ�)
			ExcelUtil.mergeCell(sheet, rowNum, rowNum + paymentList.size(), n, n);
			ExcelUtil.setRegionBorder(workbook, sheet, CellStyle.BORDER_THIN, rowNum, rowNum
					+ paymentList.size(), n, n);
			// ֧��
			ExcelUtil.createCell(rowDataPayment, n++, styleDescription, "֧��");

			// HSSFRow rowPayment = ExcelUtil.createRow(sheet, m++, 400);
			ExcelUtil.createCell(rowDataPayment, n++, null, "");
			// ֧������
			for (int k2 = 0; k2 < monthList.size() + 1; k2++) {
				ExcelUtil.createCell(rowDataPayment, n++, null, "Ӧ��");
				ExcelUtil.createCell(rowDataPayment, n++, null, "ʵ��");
			}

			for (int k = 0; k < paymentList.size(); k++) {
				n = 3;
				String costname = paymentList.get(k);
				HSSFRow rowPayment = ExcelUtil.createRow(sheet, m++, 400);
				// // ��ҵ��
				// ExcelUtil.createCell(rowPayment, 0, null, null == dicSinocorp
				// ? "" : dicSinocorp
				// .getShortname());
				ExcelUtil.createCell(rowPayment, n++, null, costname);
				for (int l = 0; l < monthList.size(); l++) {
					String month = monthList.get(l);
					String detid = CommUtils.getString(cursorCorpid, "-", month, "-", cursorCarno);
					String key = CommUtils.getString(detid, "_", costname);
					EquMcarcostdetitem equMcarcostdetitem = equMcarcostdetitemMap.get(key);
					Double dueamount = null;
					Double realamount = null;
					if (null != equMcarcostdetitem) {
						dueamount = equMcarcostdetitem.getDueamount();
						realamount = equMcarcostdetitem.getRealamount();
					}
					ExcelUtil.createCell(rowPayment, n++, null,
							null == dueamount || 0 == dueamount ? "" : String.valueOf(dueamount));
					ExcelUtil.createCell(rowPayment, n++, null, null == realamount
							|| 0 == realamount ? "" : String.valueOf(realamount));
				}
				String yearKey = CommUtils.getString(cursorCorpid, "_", cursorCarno, "_", costname);
				Double yearNeedBalance = yearNeedBalanceMap.get(yearKey);
				Double yearRealBalance = yearRealBalanceMap.get(yearKey);
				ExcelUtil.createCell(rowPayment, n++, null, null == yearNeedBalance ? "" : String
						.valueOf(yearNeedBalance));
				ExcelUtil.createCell(rowPayment, n++, null, null == yearRealBalance ? "" : String
						.valueOf(yearRealBalance));
			}
			n = 2;

			HSSFRow rowMonth = ExcelUtil.createRow(sheet, m++, 400);
			// // ��ҵ��
			// ExcelUtil.createCell(rowMonth, 0, null, null == dicSinocorp ? ""
			// : dicSinocorp
			// .getShortname());
			ExcelUtil.createCell(rowMonth, n++, null, "�½�");
			ExcelUtil.createCell(rowMonth, n++, null, "");
			double yearNeedBalance = 0;
			double yearRealBalance = 0;
			for (int k = 0; k < monthList.size(); k++) {
				String month = monthList.get(k);
				String detid = CommUtils.getString(cursorCorpid, "-", month, "-", cursorCarno);
				Double monthNeedBalance = monthNeedBalanceMap.get(detid);
				Double monthRealBalance = monthRealBalanceMap.get(detid);
				if (null == monthNeedBalance) {
					monthNeedBalance = 0d;
				}
				if (null == monthRealBalance) {
					monthRealBalance = 0d;
				}

				yearNeedBalance += monthNeedBalance;
				yearRealBalance += monthRealBalance;

				ExcelUtil.createCell(rowMonth, n++, null, 0d == monthNeedBalance ? "" : String
						.valueOf(monthNeedBalance));
				ExcelUtil.createCell(rowMonth, n++, null, 0d == monthRealBalance ? "" : String
						.valueOf(monthRealBalance));
			}
			ExcelUtil.createCell(rowMonth, n++, null, yearNeedBalance);
			ExcelUtil.createCell(rowMonth, n++, null, yearRealBalance);
		}
		// -----------------------����Ϊ�ܼ���--------------------
		HSSFRow rowZj = ExcelUtil.createRow(sheet, m, 500);
		ExcelUtil.createCell(rowZj, 0, styleTitle, "�ܼ�");

		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowZj.getRowNum(), rowZj
				.getRowNum(), 0, 3);
		ExcelUtil.setRegionStyle(sheet, cellRangeAddress, styleTitle);

		ExcelUtil.mergeCell(sheet, m, m, 0, 3);

		int n = 4;
		double yearDueZj = 0d;
		double yearRealZj = 0d;
		for (int i = 0; i < monthList.size(); i++) {
			String month = monthList.get(i);
			Double totalDueCost = totalDueCostByMonthMap.get(month);
			Double totalRealCost = totalRealCostByMonthMap.get(month);
			if (null == totalDueCost) {
				totalDueCost = 0d;
			}
			if (null == totalRealCost) {
				totalRealCost = 0d;
			}
			BigDecimal due = new BigDecimal(totalDueCost);
			due.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal real = new BigDecimal(totalRealCost);
			real.setScale(2, BigDecimal.ROUND_HALF_UP);
			yearDueZj += totalDueCost;
			yearRealZj += totalRealCost;
			ExcelUtil.createCell(rowZj, n++, styleTitle, due.floatValue());
			ExcelUtil.createCell(rowZj, n++, styleTitle, real.floatValue());
		}
		BigDecimal yearDue = new BigDecimal(yearDueZj);
		yearDue.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal yearReal = new BigDecimal(yearRealZj);
		yearReal.setScale(2, BigDecimal.ROUND_HALF_UP);
		ExcelUtil.createCell(rowZj, n++, styleTitle, yearDue.floatValue());
		ExcelUtil.createCell(rowZj, n++, styleTitle, yearReal.floatValue());

		int lastRowNum = sheet.getLastRowNum();
		String corpname = "";
		for (int i = 0; i < lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			if (null == row) {
				continue;
			}
			HSSFCell cell = row.getCell(0);
			if (null == cell) {
				continue;
			}
			String corpNameOfCell = cell.getStringCellValue();
			if (corpname.equals(corpNameOfCell)) {
				ExcelUtil.mergeCell(sheet, row.getRowNum() - 1, row.getRowNum(), 0, 0);
			}
			corpname = corpNameOfCell;
		}

	}

	public void exportExcel1(HSSFWorkbook workbook, List<EquMcarcostdetitem> equMcarcostdetitems) {
		// ����sheet
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "�������޷���");
		// ����������
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);
		// ��������
		Font fontTitle = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD,
				HSSFFont.COLOR_NORMAL, (short) 20);
		// ���е�Ԫ����ʽ
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_CENTER, fontTitle);
		// �ϲ���Ԫ������10��
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 6);
		// ������Ԫ������
		ExcelUtil.createCell(rowHeader, 0, style, "�������޷���");
		CellStyle styleDescription = workbook.createCellStyle();
		styleDescription.setAlignment(CellStyle.ALIGN_LEFT);
		styleDescription.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleDescription.setBorderBottom(CellStyle.BORDER_THIN);
		styleDescription.setBorderLeft(CellStyle.BORDER_THIN);
		styleDescription.setBorderTop(CellStyle.BORDER_THIN);
		styleDescription.setBorderRight(CellStyle.BORDER_THIN);

		HSSFRow rowTitle = ExcelUtil.createRow(sheet, 1, 500);

		ExcelUtil.createCell(rowTitle, 0, styleDescription, "��ҵ����");
		ExcelUtil.createCell(rowTitle, 1, styleDescription, "����");
		ExcelUtil.createCell(rowTitle, 2, styleDescription, "�¶�");
		ExcelUtil.createCell(rowTitle, 3, styleDescription, "��Ŀ");
		ExcelUtil.createCell(rowTitle, 4, styleDescription, "Ӧ�ս��");
		ExcelUtil.createCell(rowTitle, 5, styleDescription, "ʵ�ս��");
		ExcelUtil.createCell(rowTitle, 6, styleDescription, "��ע");

		String corpid = "";
		String month = "";
		String carno = "";
		for (int i = 0; i < equMcarcostdetitems.size(); i++) {
			EquMcarcostdetitem equMcarcostdetitem = equMcarcostdetitems.get(i);

			int j = 0;
			HSSFRow row = ExcelUtil.createRow(sheet, i + 2, -1);
			ExcelUtil.createCell(row, j++, styleDescription, equMcarcostdetitem.getDicSinocorp()
					.getShortname());
			ExcelUtil.createCell(row, j++, styleDescription, equMcarcostdetitem.getMonth());
			ExcelUtil.createCell(row, j++, styleDescription, equMcarcostdetitem.getCarno());
			ExcelUtil.createCell(row, j++, styleDescription, equMcarcostdetitem.getCostname());
			ExcelUtil.createCell(row, j++, (HSSFCellStyle) styleDescription, equMcarcostdetitem
					.getDueamount());
			ExcelUtil.createCell(row, j++, (HSSFCellStyle) styleDescription, equMcarcostdetitem
					.getRealamount());
			ExcelUtil.createCell(row, j++, styleDescription, equMcarcostdetitem.getRemarks());
			DicSinocorp dicSinocorp = equMcarcostdetitem.getDicSinocorp();
			if (null != dicSinocorp) {
				String cusorCorpid = dicSinocorp.getCorpid();
				if (cusorCorpid.equals(corpid)) {
					CellRangeAddress rageAddress = new CellRangeAddress(row.getRowNum() - 1, row
							.getRowNum(), 0, 0);
					sheet.addMergedRegion(rageAddress);
				}
				corpid = cusorCorpid;
			}
			if (month.equals(equMcarcostdetitem.getMonth())) {
				CellRangeAddress rageAddress = new CellRangeAddress(row.getRowNum() - 1, row
						.getRowNum(), 1, 1);
				sheet.addMergedRegion(rageAddress);
			}
			month = equMcarcostdetitem.getMonth();
			if (carno.equals(equMcarcostdetitem.getCarno())) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(row.getRowNum() - 1, row
						.getRowNum(), 2, 2);
				sheet.addMergedRegion(cellRangeAddress);
			}
			carno = equMcarcostdetitem.getCarno();
		}
	}

	public List<EquMcarcostdetitem> findEquMcarcostdetitem() {
		return equMcarcostdetitemDao.findEquMcarcostdetitem();
	}

	public List<String> findMonths(EquMcarcostdet equMcarcostdet) {
		return equMcarcostDao.findMonths(equMcarcostdet);
	}

	public void findEquMcarcostdets(EquMcarcostdet equMcarcostdet, PageCtr<Object[]> pageCtr) {
		pageCtr.setTotalRecord(equMcarcostdetDao.getEquMcarcostdetsCount(equMcarcostdet));
		equMcarcostdetDao.findEquMcarcostdets(equMcarcostdet, pageCtr);
	}

	public List<EquMcarcostdetitem> findEquMcarcostdetitem(EquMcarcostdet equMcarcostdet,
			List<String> carnoList) {
		return equMcarcostdetitemDao.findEquMcarcostdetitem(equMcarcostdet, carnoList);
	}

	public List<String> findMonthsByCorpid(String corpid) {
		return equMcarcostDao.findMonthsByCorpid(corpid);
	}

	public List<String> findCars(EquMcarcostdet equMcarcostdet) {
		return equMcarcostdetDao.findCars(equMcarcostdet);
	}

	public List<EquMcarcostdetitem> findEquMcarcostdetitem(String detid) {
		return equMcarcostdetitemDao.findEquMcarcostdetitem(detid);
	}

	public EquMcarcostdetitem load(long id) {
		return equMcarcostdetitemDao.load(id);
	}

	/**
	 * ��ѯ�Ѿ����ڵĳ�����ҵ
	 * 
	 * @param equMcarcostdet
	 * @return
	 */
	public List<Object[]> findCorpids() {
		return equMcarcostDao.findCorpids();
	}

	public List<Object[]> findEquMcarcostdets(EquMcarcostdet equMcarcostdet) {
		return equMcarcostdetDao.findEquMcarcostdets(equMcarcostdet);
	}
}
