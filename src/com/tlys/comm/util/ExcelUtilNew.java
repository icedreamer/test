/**
 * 
 */
package com.tlys.comm.util;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author fengym
 * Excel导出操作类，使用MAP来组织数据，自动生成需要的Excel
 * 
 */

public class ExcelUtilNew {

	private static final Logger log = Logger.getLogger(ExcelUtilNew.class);

	/**
	 * 导出Excel表的主入口程序（唯一入口)
	 * @param response
	 * @param tabDefineMap
	 * @param fileName
	 * @throws Exception
	 */
	public static void writeWorkbook(HttpServletResponse response, Map tabDefineMap, String fileName) throws Exception{
		OutputStream os = null;
		Workbook wb = genTable(tabDefineMap);
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
	 * tabDefineMap结构：key{sheetname},value{Map:key{"list,title,headArr"},
	 * value{List,"标题（合并单元格)"，String[][]:{{类的属性}，{"属性名称（显示的表头)"}}}
	 * @param tabDefineMap
	 */
	public static Workbook genTable(Map tabDefineMap) throws Exception{
		Workbook workbook = new HSSFWorkbook();
		for (Iterator iter = tabDefineMap.keySet().iterator(); iter.hasNext();) {
			String sheetName = (String) iter.next();
			Map sheetMap = (Map)tabDefineMap.get(sheetName);
			List objList = (List)sheetMap.get("list");
			String title = (String)sheetMap.get("title");
			String[][] headArr = (String[][])sheetMap.get("headArr");
			
			//表头数
			int headSize = headArr[0].length;
			
			HSSFSheet sheet = createHSSFSheet(workbook, sheetName);
			
			//------生成标题区域
			buildRowTitle(workbook,sheet,headSize-1,title);
			
			
			CellStyle hdStyle = getDefaultHeadStyle(workbook);
			Row rowHead = createRow(sheet, 1, -1);
			for (int j = 0; j < headArr[1].length; j++) {
				String propHead = headArr[1][j];
				createCell(rowHead, j, hdStyle, propHead);
			}
			
			CellStyle nmStyle = getDefaultNormalStyle(workbook);
			for (int i = 0; i < objList.size(); i++) {
				Object obj = objList.get(i);
				Row row = createRow(sheet, i + 2, -1);
				for (int j = 0; j < headArr[0].length; j++) {
					String propName = headArr[0][j];
					createCell(row, j, nmStyle, BeanUtils.getProperty(obj, propName));
				}
			}
		}
		return workbook;
	}
	
	private static CellStyle getDefaultNormalStyle(Workbook workbook){
		Font fontDescription = createFont(workbook, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
				(short) 10);
		CellStyle style = createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_LEFT, fontDescription);
		return style;
	}
	
	private static CellStyle getDefaultHeadStyle(Workbook workbook){
		Font fontDescription = createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL,
				(short) 10);
		CellStyle style = createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_CENTER, fontDescription);
		return style;
	}
	
	private static CellStyle getDefaultTitleStyle(Workbook workbook){
		Font fontTitle = createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		CellStyle style = createBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, fontTitle);
		return style;
	}
	
	/**
	 * 生成标题区域
	 * @return
	 */
	private static void buildRowTitle(Workbook workbook,HSSFSheet sheet,int rowspan,String title){
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowspan));
		Row rowTitle = sheet.createRow(0);
		rowTitle.setHeight((short) 750);
		Cell cellTitle = rowTitle.createCell(0);
	    cellTitle.setCellValue(title);
	     
	    cellTitle.setCellStyle(getDefaultTitleStyle(workbook));
	}
	


	/**
	 * 
	 * 功能：创建HSSFSheet工作簿
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * 
	 * @param sheetName
	 *            String
	 * 
	 * @return HSSFSheet
	 */

	public static HSSFSheet createHSSFSheet(Workbook wb, String sheetName) {

		HSSFSheet sheet = (HSSFSheet)wb.createSheet(sheetName);

		sheet.setDefaultColumnWidth(12);

		sheet.setGridsPrinted(false);

		sheet.setDisplayGridlines(false);

		return sheet;

	}

	/**
	 * 
	 * 功能：创建HSSFRow
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
	 * 功能：创建CellStyle样式
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * 
	 * @param backgroundColor
	 *            背景色
	 * 
	 * @param foregroundColor
	 *            前置色
	 * 
	 * @param font
	 *            字体
	 * 
	 * @return CellStyle
	 */

	public static CellStyle createCellStyle(HSSFWorkbook wb, short backgroundColor, short foregroundColor,
			short halign, Font font) {

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
	 * 功能：创建带边框的CellStyle样式
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * 
	 * @param backgroundColor
	 *            背景色
	 * 
	 * @param foregroundColor
	 *            前置色
	 * 
	 * @param font
	 *            字体
	 * 
	 * @return CellStyle
	 */

	public static CellStyle createBorderCellStyle(Workbook wb, short backgroundColor, short foregroundColor,
			short halign, Font font) {

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
	 * 功能：创建CELL
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

	public static Cell createCell(Row row, int cellNum, CellStyle style, String value) {

		Cell cell = row.createCell(cellNum);
		if (style != null) {
			cell.setCellStyle(style);
		}
		if (value != null && !value.equals("")) {
			cell.setCellValue(value);
		}
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}

	public static HSSFCell createCell(HSSFRow row, int cellNum, HSSFCellStyle style, double value) {
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
	 * 功能：合并单元格
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
	 * @return int 合并区域号码
	 */

	public static int mergeCell(HSSFSheet sheet, int firstRow, int lastRow, int firstColumn, int lastColumn) {

		return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));

	}

	/**
	 * 
	 * 功能：创建字体
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

	public static Font createFont(Workbook wb, short boldweight, short color, short size) {

		Font font = wb.createFont();

		font.setBoldweight(boldweight);

		font.setColor(color);

		font.setFontHeightInPoints(size);

		return font;

	}

	/**
	 * 
	 * 设置合并单元格的边框样式
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

}
