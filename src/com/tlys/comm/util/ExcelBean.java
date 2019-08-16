package com.tlys.comm.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelBean {

	public void setCellValue(int r, int c, HSSFWorkbook wb, String value) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null) {
			cell = row.createCell((short) c);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);

	}

	public void setCellValue(int r, int c, HSSFWorkbook wb, int value) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null) {
			cell = row.createCell((short) c);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);

	}

	/**
	 * ��ȡString���͵�����
	 * 
	 * @param r��Ԫ�����ڵ���
	 * @param c��Ԫ�����ڵ���
	 * @param wb
	 *            excel����
	 * @return
	 */
	public String getStringCellValue(int r, int c, HSSFWorkbook wb) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(r);
		String value = null;
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null)
			cell = row.createCell((short) c);
		else {
			value = cell.getStringCellValue();
		}
		return value;
	}

	/**
	 * ��ȡInteger���͵�����
	 * 
	 * @param r��Ԫ�����ڵ���
	 * @param c��Ԫ�����ڵ���
	 * @param wb
	 *            excel����
	 * @return
	 */
	public Integer getIntegerCellValue(int r, int c, HSSFWorkbook wb) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(r);
		Integer value = null;
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null)
			cell = row.createCell((short) c);
		else {
			value = new Integer((int) cell.getNumericCellValue());
		}
		return value;
	}

	/**
	 * ��ȡFloat���͵�����
	 * 
	 * @param r��Ԫ�����ڵ���
	 * @param c��Ԫ�����ڵ���
	 * @param wb
	 *            excel����
	 * @return
	 */
	public Float getFloatCellValue(int r, int c, HSSFWorkbook wb) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(r);
		Float value = null;
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null)
			cell = row.createCell((short) c);
		else {
			value = Float.valueOf(String.valueOf(cell.getNumericCellValue()));
		}
		return value;
	}

	/**
	 * д��String���͵�����
	 * 
	 * @param r��Ԫ�����ڵ���
	 * @param c��Ԫ�����ڵ���
	 * @param sheet
	 *            excel��
	 * @param value
	 *            д�������
	 */
	public void setCellValue(int r, int c, HSSFSheet sheet, String value) {

		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null) {
			cell = row.createCell((short) c);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);

	}

	/**
	 * д��Integer���͵�����
	 * 
	 * @param r��Ԫ�����ڵ���
	 * @param c��Ԫ�����ڵ���
	 * @param sheet
	 *            excel��
	 * @param value
	 *            д�������
	 */
	public void setCellValue(int r, int c, HSSFSheet sheet, Integer value) {

		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null) {
			cell = row.createCell((short) c);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		if (value == null) {
			cell.setCellValue("");
		} else {
			cell.setCellValue(value.intValue());
		}
	}

	/**
	 * д��Float���͵�����
	 * 
	 * @param r��Ԫ�����ڵ���
	 * @param c��Ԫ�����ڵ���
	 * @param sheet
	 *            excel��
	 * @param value
	 *            д�������
	 */
	public void setCellValue(int r, int c, HSSFSheet sheet, Float value) {

		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null) {
			cell = row.createCell((short) c);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		if (value == null) {
			cell.setCellValue("");
		} else {
			cell.setCellValue(Float.parseFloat(String.valueOf(value)));
		}

	}

	/**
	 * д��excel���ı���
	 * 
	 * @param r�������ڵ���
	 * @param sheet
	 *            excel��
	 * @param reportTitles
	 *            ��������
	 */
	public void setTitleValue(int r, HSSFSheet sheet, String[] reportTitles) {
		for (int i = 0; i < reportTitles.length; i++) {
			this.setCellValue(r, i, sheet, reportTitles[i]);
		}
	}

	/**
	 * �õ�ϵͳʱ��
	 * 
	 * @return
	 */
	public String getSystemTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		Date date = new Date();
		String systemtime = sdf.format(date);

		return systemtime;
	}

	public void setCellValue(int r, int c, HSSFWorkbook wb, Long value) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null) {
			cell = row.createCell((short) c);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		if (value == null) {
			cell.setCellValue("");
		} else {
			cell.setCellValue(value.longValue());
		}

	}

	public void setCellValue(int r, int c, HSSFWorkbook wb, BigDecimal value) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			row = sheet.createRow(r);
		}
		HSSFCell cell = row.getCell((short) c);
		if (cell == null) {
			cell = row.createCell((short) c);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		if (value == null) {
			cell.setCellValue("");
		} else {
			cell.setCellValue(value.doubleValue());
		}

	}

}
