/**
 * 
 */
package com.tlys.comm.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * @author fengym
 * 
 */

/**
 * 
 * ������Excelд����������
 * 
 * @author ALEX
 * 
 * @since 2010-11-24
 * 
 * @version 1.0v
 */

public class ExcelUtil {

	private static final Logger log = Logger.getLogger(ExcelUtil.class);

	/**
	 * 
	 * ���ܣ���HSSFWorkbookд��Excel�ļ�
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param absPath
	 *            д���ļ������·��
	 * @param wbName
	 *            �ļ���
	 */

	public static void writeWorkbook(HSSFWorkbook wb, String fileName) {

		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(fileName);

			wb.write(fos);

		} catch (FileNotFoundException e) {

			log
					.error(new StringBuffer("[").append(e.getMessage()).append("]").append(
							e.getCause()));

		} catch (IOException e) {

			log
					.error(new StringBuffer("[").append(e.getMessage()).append("]").append(
							e.getCause()));

		} finally {

			try {

				if (fos != null) {

					fos.close();

				}

			} catch (IOException e) {

				log.error(new StringBuffer("[").append(e.getMessage()).append("]").append(
						e.getCause()));

			}

		}

	}

	public static void writeWorkbook(HttpServletResponse response, HSSFWorkbook wb, String fileName) {
		OutputStream os = null;
		try {
			response.setContentType("application/msexcel");
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
			System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			log.error("write workbook error.", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * 
	 * ���ܣ�����HSSFSheet������
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * 
	 * @param sheetName
	 *            String
	 * 
	 * @return HSSFSheet
	 */

	public static HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {

		HSSFSheet sheet = wb.createSheet(sheetName);

		sheet.setDefaultColumnWidth(12);

		sheet.setGridsPrinted(true);

		sheet.setDisplayGridlines(true);

		return sheet;

	}

	/**
	 * 
	 * ���ܣ�����HSSFRow
	 * 
	 * @param sheet
	 *            HSSFSheet
	 * 
	 * @param rowNum
	 *            int
	 * 
	 * @param height
	 *            int
	 * 
	 * @return HSSFRow
	 */

	public static HSSFRow createRow(HSSFSheet sheet, int rowNum, int height) {

		HSSFRow row = sheet.createRow(rowNum);
		if (height != -1) {
			row.setHeight((short) height);
		}

		return row;

	}

	/**
	 * 
	 * ���ܣ�����CellStyle��ʽ
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * 
	 * @param backgroundColor
	 *            ����ɫ
	 * 
	 * @param foregroundColor
	 *            ǰ��ɫ
	 * 
	 * @param font
	 *            ����
	 * 
	 * @return CellStyle
	 */

	public static CellStyle createCellStyle(HSSFWorkbook wb, short backgroundColor,
			short foregroundColor, short halign, Font font) {

		CellStyle cs = wb.createCellStyle();

		cs.setAlignment(halign);

		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		cs.setFillBackgroundColor(backgroundColor);

		cs.setFillForegroundColor(foregroundColor);

		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);

		cs.setFont(font);

		return cs;

	}

	/**
	 * 
	 * ���ܣ��������߿��CellStyle��ʽ
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * 
	 * @param backgroundColor
	 *            ����ɫ
	 * 
	 * @param foregroundColor
	 *            ǰ��ɫ
	 * 
	 * @param font
	 *            ����
	 * 
	 * @return CellStyle
	 */

	public static CellStyle createBorderCellStyle(HSSFWorkbook wb, short backgroundColor,
			short foregroundColor, short halign, Font font) {

		CellStyle cs = wb.createCellStyle();

		cs.setAlignment(halign);

		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		cs.setFillBackgroundColor(backgroundColor);

		cs.setFillForegroundColor(foregroundColor);

		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);

		cs.setFont(font);

		cs.setBorderLeft(CellStyle.SOLID_FOREGROUND);

		cs.setBorderRight(CellStyle.SOLID_FOREGROUND);

		cs.setBorderTop(CellStyle.SOLID_FOREGROUND);

		cs.setBorderBottom(CellStyle.SOLID_FOREGROUND);

		return cs;

	}

	/**
	 * 
	 * ���ܣ�����CELL
	 * 
	 * @param row
	 *            HSSFRow
	 * 
	 * @param cellNum
	 *            int
	 * 
	 * @param style
	 *            HSSFStyle
	 * 
	 * @return HSSFCell
	 */

	public static HSSFCell createCell(HSSFRow row, int cellNum, CellStyle style, String value) {

		HSSFCell cell = row.createCell(cellNum);
		if (style != null) {
			cell.setCellStyle(style);
		}
		if (value != null && !value.equals("")) {
			cell.setCellValue(value);
		}
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}

	public static HSSFCell createCell(HSSFRow row, int cellNum, CellStyle style, double value) {
		HSSFCell cell = row.createCell(cellNum);
		if (style != null) {
			cell.setCellStyle(style);
		}
		cell.setCellValue(value);

		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}

	/**
	 * 
	 * ���ܣ��ϲ���Ԫ��
	 * 
	 * @param sheet
	 *            HSSFSheet
	 * 
	 * @param firstRow
	 *            int
	 * 
	 * @param lastRow
	 *            int
	 * 
	 * @param firstColumn
	 *            int
	 * 
	 * @param lastColumn
	 *            int
	 * 
	 * @return int �ϲ��������
	 */

	public static int mergeCell(HSSFSheet sheet, int firstRow, int lastRow, int firstColumn,
			int lastColumn) {

		return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn,
				lastColumn));

	}

	/**
	 * 
	 * ���ܣ���������
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * 
	 * @param boldweight
	 *            short
	 * 
	 * @param color
	 *            short
	 * 
	 * @return Font
	 */

	public static Font createFont(HSSFWorkbook wb, short boldweight, short color, short size) {

		Font font = wb.createFont();

		font.setBoldweight(boldweight);

		font.setColor(color);

		font.setFontHeightInPoints(size);

		return font;

	}

	/**
	 * 
	 * ���úϲ���Ԫ��ı߿���ʽ
	 * 
	 * @param sheet
	 *            HSSFSheet
	 * 
	 * @param ca
	 *            CellRangAddress
	 * 
	 * @param style
	 *            CellStyle
	 */

	public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress ca, CellStyle style) {
		for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
			HSSFRow row = HSSFCellUtil.getRow(i, sheet);
			for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
				HSSFCell cell = HSSFCellUtil.getCell(row, j);
				cell.setCellStyle(style);
			}
		}
	}

	/**
	 * ���úϲ���Ԫ���ı߿��С
	 * 
	 * @param border
	 * @param region
	 * @param sheet
	 * @param workbook
	 */
	public static void setRegionBorder(int border, CellRangeAddress region, Sheet sheet,
			Workbook workbook) {
		RegionUtil.setBorderBottom(border, region, sheet, workbook);
	}

	/**
	 * ���úϲ���Ԫ���ı߿��С
	 * 
	 * @param workbook
	 * @param sheet
	 * @param border
	 * @param firstRow
	 * @param lastRow
	 * @param firstCell
	 * @param lastCell
	 */
	public static void setRegionBorder(Workbook workbook, Sheet sheet, int border, int firstRow,
			int lastRow, int firstCell, int lastCell) {
		CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCell, lastCell);
		RegionUtil.setBorderBottom(border, region, sheet, workbook);
		RegionUtil.setBorderLeft(border, region, sheet, workbook);
		RegionUtil.setBorderRight(border, region, sheet, workbook);
		RegionUtil.setBorderTop(border, region, sheet, workbook);
	}

}
