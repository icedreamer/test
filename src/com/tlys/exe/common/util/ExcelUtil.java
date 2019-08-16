package com.tlys.exe.common.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {

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

	private static CellStyle getDefaultTitleStyle(Workbook workbook) {
		Font fontTitle = createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		CellStyle style = createBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, fontTitle);
		return style;
	}

	public static Font createFont(Workbook wb, short boldweight, short color, short size) {
		Font font = wb.createFont();
		font.setBoldweight(boldweight);
		font.setColor(color);
		font.setFontHeightInPoints(size);
		return font;
	}
}
