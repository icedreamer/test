package com.tlys.comm.util.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiWriter {

	public static void main(String[] args) throws IOException {

		String[] sheets = { "企业自备车台账", "常压罐车技术性能表", "高压罐车技术性能表",
				"高压罐车压力容器使用登记证", "企业自备车证书", "自备车租用信息表" };
		int y = sheets.length*2;
		int x=0;
		String text = "";
		for(int i=1;i<sheets.length;i++){
			text += sheets[i]+"\n";
			if(x<sheets[i].length())
				x=sheets[i].length();
		}		
		x = x/2;
		// 创建工作簿对象
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建工作表对象
		HSSFSheet sheet = wb.createSheet("我的工作表");
		// 创建绘图对象
		HSSFPatriarch p = sheet.createDrawingPatriarch();
		// 创建单元格对象,批注插入到4行,1列,B5单元格
		HSSFCell cell = sheet.createRow(4).createCell(1);
		// 插入单元格内容
		cell.setCellValue(new HSSFRichTextString("批注"));
		// 获取批注对象
		// (int dx1, int dy1, int dx2, int dy2, short col1, int row1, short
		// col2, int row2)
		// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
		HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0,
				(short) 2,6, (short) x, y));
		// 输入批注信息
		comment.setString(new HSSFRichTextString(text));
		// 添加作者,选中B5单元格,看状态栏
		comment.setAuthor("toad");
		// 将批注添加到单元格对象中
		cell.setCellComment(comment);
		// 创建输出流
		FileOutputStream out = new FileOutputStream("writerPostil.xls");

		wb.write(out);
		// 关闭流对象
		out.close();
	}

}
