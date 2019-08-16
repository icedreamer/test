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
		// 字体设置
		Font fontHeader = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD,
				HSSFFont.COLOR_NORMAL, (short) 20);
		// 首行单元格样式
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_CENTER, fontHeader);

		// 创建sheet
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "车辆租赁费用");
		sheet.createFreezePane(4, 2, 4, 2);
		sheet.setDefaultColumnWidth(10);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(3, 3000);
		// 创建标题行
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);

		// 合并单元格至第 6 + monthList.size() * 2
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 5 + monthList.size() * 2);
		CellRangeAddress cellRangeAddressHeader = new CellRangeAddress(rowHeader.getRowNum(),
				rowHeader.getRowNum(), 0, 5 + monthList.size() * 2);
		ExcelUtil.setRegionStyle(sheet, cellRangeAddressHeader, style);
		// 创建单元格内容
		ExcelUtil.createCell(rowHeader, 0, style, "车辆租赁费用");

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
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "企业名称");
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "车号");
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "收支");
		ExcelUtil.createCell(rowTitle, j++, styleTitle, "科目");
		for (int i = 0; i < monthList.size(); i++) {
			String month = monthList.get(i);
			ExcelUtil.createCell(rowTitle, j, styleTitle, month);
			CellRangeAddress cellRangeAddress = new CellRangeAddress(rowTitle.getRowNum(), rowTitle
					.getRowNum(), j, j + 1);
			ExcelUtil.setRegionStyle(sheet, cellRangeAddress, styleTitle);
			ExcelUtil.mergeCell(sheet, rowTitle.getRowNum(), rowTitle.getRowNum(), j, j + 1);
			j = j + 2;
		}

		ExcelUtil.createCell(rowTitle, j, styleTitle, "年结");
		ExcelUtil.mergeCell(sheet, 1, 1, j, j + 1);
		ExcelUtil.setRegionBorder(workbook, sheet, CellStyle.BORDER_THIN, 1, 1, j, j + 1);
		// 支出
		List<String> paymentList = new ArrayList<String>();
		// 收入
		List<String> incomeList = new ArrayList<String>();
		// 月结 (每个月度应收应付小结)
		Map<String, Double> monthNeedBalanceMap = new HashMap<String, Double>();
		// 月结 (每个月度实收实付小结)
		Map<String, Double> monthRealBalanceMap = new HashMap<String, Double>();
		// 年结(月结最后面两个单元格的值，应收应付)
		Map<String, Double> yearNeedBalanceMap = new HashMap<String, Double>();
		// 年结(月结最后面两个单元格的值，实收实付)
		Map<String, Double> yearRealBalanceMap = new HashMap<String, Double>();
		// 总计（月度应收应付)
		Map<String, Double> totalDueCostByMonthMap = new HashMap<String, Double>();
		// 总计（月度实收实付)
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
			if (null != costname && "租车费".equals(costname)) {
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
			if ("租车费".equals(costname)) {
				if (!incomeList.contains(costname)) {
					incomeList.add(costname);
				}
			} else {
				if (!paymentList.contains(costname)) {
					paymentList.add(costname);
				}
			}
		}

		// 企业名称 车号 收支 科目 201201 201202 ........201212 年结 备注

		int m = 2; // 行号
		for (int i = 0; i < equMcarcostdetList.size(); i++) {
			int n = 1; // 列号
			Object[] o = equMcarcostdetList.get(i);
			String cursorCorpid = o[0].toString();
			DicSinocorp dicSinocorp = dicSinocorpMap.get(cursorCorpid);
			String cursorCarno = o[1].toString();
			HSSFRow rowDataIncome = ExcelUtil.createRow(sheet, m++, 400);
			int rowNum = rowDataIncome.getRowNum();
			// 企业列
			ExcelUtil.createCell(rowDataIncome, 0, styleDescription, null == dicSinocorp ? ""
					: dicSinocorp.getShortname());
			ExcelUtil.mergeCell(sheet, m - 1, m + paymentList.size() + incomeList.size() + 1, 0, 0);
			// 合并单元格(车号 多行合并)
			ExcelUtil.mergeCell(sheet, m - 1, m + incomeList.size() + paymentList.size() + 1, n, n);
			// 车号列
			ExcelUtil.createCell(rowDataIncome, n++, styleDescription, cursorCarno);

			// 合并单元格(收入 多行合并)
			ExcelUtil.mergeCell(sheet, rowNum, rowNum + incomeList.size(), n, n);
			// 收支列
			ExcelUtil.createCell(rowDataIncome, n++, styleDescription, "收入");

			ExcelUtil.createCell(rowDataIncome, n++, null, "");

			// 收入数据(+1 是为了省略年结的"应收","实收")
			for (int k2 = 0; k2 < monthList.size() + 1; k2++) {
				ExcelUtil.createCell(rowDataIncome, n++, null, "应收");
				ExcelUtil.createCell(rowDataIncome, n++, null, "实收");
			}
			// 循环显示每个月的收入数据
			for (int k = 0; k < incomeList.size(); k++) {
				n = 3;
				String costname = incomeList.get(k);
				HSSFRow rowIncome = ExcelUtil.createRow(sheet, m++, 400);
				// // 企业列
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
			// 支出 第一行
			HSSFRow rowDataPayment = ExcelUtil.createRow(sheet, m++, 400);
			rowNum = rowDataPayment.getRowNum();

			// // 企业列
			// ExcelUtil.createCell(rowDataPayment, 0, null, null == dicSinocorp
			// ? "" : dicSinocorp
			// .getShortname());

			// 合并单元格(支出 多行合并)
			ExcelUtil.mergeCell(sheet, rowNum, rowNum + paymentList.size(), n, n);
			ExcelUtil.setRegionBorder(workbook, sheet, CellStyle.BORDER_THIN, rowNum, rowNum
					+ paymentList.size(), n, n);
			// 支出
			ExcelUtil.createCell(rowDataPayment, n++, styleDescription, "支出");

			// HSSFRow rowPayment = ExcelUtil.createRow(sheet, m++, 400);
			ExcelUtil.createCell(rowDataPayment, n++, null, "");
			// 支出数据
			for (int k2 = 0; k2 < monthList.size() + 1; k2++) {
				ExcelUtil.createCell(rowDataPayment, n++, null, "应付");
				ExcelUtil.createCell(rowDataPayment, n++, null, "实付");
			}

			for (int k = 0; k < paymentList.size(); k++) {
				n = 3;
				String costname = paymentList.get(k);
				HSSFRow rowPayment = ExcelUtil.createRow(sheet, m++, 400);
				// // 企业列
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
			// // 企业列
			// ExcelUtil.createCell(rowMonth, 0, null, null == dicSinocorp ? ""
			// : dicSinocorp
			// .getShortname());
			ExcelUtil.createCell(rowMonth, n++, null, "月结");
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
		// -----------------------以下为总计行--------------------
		HSSFRow rowZj = ExcelUtil.createRow(sheet, m, 500);
		ExcelUtil.createCell(rowZj, 0, styleTitle, "总计");

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
		// 创建sheet
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "车辆租赁费用");
		// 创建标题行
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);
		// 字体设置
		Font fontTitle = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD,
				HSSFFont.COLOR_NORMAL, (short) 20);
		// 首行单元格样式
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_CENTER, fontTitle);
		// 合并单元格至第10列
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 6);
		// 创建单元格内容
		ExcelUtil.createCell(rowHeader, 0, style, "车辆租赁费用");
		CellStyle styleDescription = workbook.createCellStyle();
		styleDescription.setAlignment(CellStyle.ALIGN_LEFT);
		styleDescription.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleDescription.setBorderBottom(CellStyle.BORDER_THIN);
		styleDescription.setBorderLeft(CellStyle.BORDER_THIN);
		styleDescription.setBorderTop(CellStyle.BORDER_THIN);
		styleDescription.setBorderRight(CellStyle.BORDER_THIN);

		HSSFRow rowTitle = ExcelUtil.createRow(sheet, 1, 500);

		ExcelUtil.createCell(rowTitle, 0, styleDescription, "企业名称");
		ExcelUtil.createCell(rowTitle, 1, styleDescription, "车号");
		ExcelUtil.createCell(rowTitle, 2, styleDescription, "月度");
		ExcelUtil.createCell(rowTitle, 3, styleDescription, "科目");
		ExcelUtil.createCell(rowTitle, 4, styleDescription, "应收金额");
		ExcelUtil.createCell(rowTitle, 5, styleDescription, "实收金额");
		ExcelUtil.createCell(rowTitle, 6, styleDescription, "备注");

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
	 * 查询已经存在的出租企业
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
