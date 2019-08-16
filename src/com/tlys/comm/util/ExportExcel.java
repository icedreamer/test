package com.tlys.comm.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.tlys.spe.model.SpeLoadeqiupment;

/**
 * @author 郭建军
 * 
 */
@Component
public class ExportExcel {
	/**
	 * 生成初始EXCEL文件
	 * 
	 * @param fileName
	 * @param sheets
	 * @param list
	 * @throws Exception
	 */
	public void createExcel(String[] sheets, List titles, List list)
			throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel");
		ExcelBean excel = new ExcelBean();
		String fileName = sheets[0] + excel.getSystemTime();
		try {
			fileName = new String(fileName.getBytes(), "iso8859-1");
			response.addHeader("Content-Disposition", "attachment;fileName="
					+ fileName + ".xls");
			OutputStream outStream = response.getOutputStream();
			// 输出EXCEL内容
			createExcel(sheets, titles, list, excel, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出EXCEL内容
	 * 
	 * @param excel
	 * @param tables
	 * @param sheets
	 * @param outStream
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void createExcel(String[] sheets, List titles, List tables,
			ExcelBean excel, OutputStream outStream) throws IOException,
			SQLException {
		HSSFWorkbook wb = new HSSFWorkbook();
		List list = new ArrayList();
		try {
			// 输出EXCEL内容
			for (int i = 0; i < tables.size(); i++) {
				HSSFSheet sheet = wb.createSheet(sheets[i]);
				List objs = (List) tables.get(i);
				String[] title = (String[]) titles.get(i);
				Map map = createPojoList(excel, objs, title, sheet);
				list.add(map);
			}
			// 设置样式：主表中添加关联子表数据批注
			// LIST长度大于一说明有多个子表SHEET
			if (list.size() > 1) {
				// 主表SHEET
				HSSFSheet mainSheet = wb.getSheetAt(0);
				// 主表主键MAP(主键数据,标记行数)
				Map mainMap = (Map) list.get(0);
				// 循环mainMap在主键数据上添加批注
				Iterator iter = mainMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					// 行数存放在LIST中（一对多子表中对应主表数据有多行)
					int r = (Integer) ((List) entry.getValue()).get(0);
					// 批注内容数组
					String text = "";
					// 循环子表sheet生成对应的批注内容
					for (int i = 1; i < list.size(); i++) {
						Map subMap = (Map) list.get(i);
						if (subMap != null && subMap.size() > 0) {
							String str = sheets[i] + ":";
							List rs = (List) subMap.get(key);
							if (rs != null) {
								// a0表示EXCEL中显示位置
								int a0 = ((Integer) rs.get(0)).intValue() + 1;
								if (rs.size() > 1) {
									int a1 = ((Integer) rs.get(rs.size() - 1))
											.intValue() + 1;
									str += "A" + a0 + "--A" + a1;
								} else
									str += "A" + a0;
							}
							text += str + ",";
						}
					}
					if (!"".equals(text)) {
						text = text.substring(0, text.length() - 1);
						createComment(text, mainSheet, r, 0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (outStream != null) {
					wb.write(outStream);
					wb = null;
				}
				if (outStream != null) {
					outStream.close();
					outStream = null;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Map createPojoList(ExcelBean excel, List objs,
			String[] titles, HSSFSheet sheet) throws Exception {
		Map<String, List> map = new HashMap();
		if (null == titles || titles.length == 0) {
			String title = "";
			SpeLoadeqiupment obj = new SpeLoadeqiupment();
			Class c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().startsWith("get")) {
					String att = m[i].getName().replace("get", "");
					Object object = m[i].invoke(obj, null);
					System.out.println(att + ":" + object);
					title += att + ",";
				}
			}
			titles = title.split(",");
		}
		excel.setTitleValue(0, sheet, titles);
		try {
			String str = "";
			List rs = new ArrayList();
			for (int i = 0; i < objs.size(); i++) {
				Class c = objs.get(i).getClass();
				Method m[] = c.getDeclaredMethods();
				for (int j = 0; j < m.length; j++) {
					String primary = "";
					if (m[j].getName().startsWith("get")) {
						Object object = m[j].invoke(objs.get(i), null);
						String ss = object != null ? object.toString() : "";
						excel.setCellValue(i + 1, j, sheet, ss.trim());
						if (j == 0) {
							primary = ss.trim();
						}
					}
					if (!str.equals(primary)) {
						if (!"".equals(str))
							map.put(str, rs);
						str = primary;
						rs = new ArrayList();
						// i+1SHEET行数
						rs.add(i + 1);
					} else {
						rs.add(i + 1);
					}

				}
				if (!"".equals(str))
					map.put(str, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return map;
	}

	/**
	 * 添加批注
	 * 
	 * @param sheet
	 * @param sheets
	 * @param r
	 * @param c
	 * @throws IOException
	 */
	public static void createComment(String text, HSSFSheet sheet, int r, int c)
			throws IOException {
		String[] sheets = text.split(",");
		String txt = "";
		int y = sheets.length * 2;
		int x = 0;
		for (int i = 0; i < sheets.length; i++) {
			txt += sheets[i] + "\n";
			if (x < sheets[i].length())
				x = sheets[i].length();
		}
		x = x / 2;
		// 创建绘图对象
		HSSFPatriarch p = sheet.createDrawingPatriarch();
		// 创建单元格对象,批注插入到r行,c列
		HSSFCell cell = sheet.getRow(r).getCell(c);
		// 插入单元格内容
		// cell.setCellValue(new HSSFRichTextString("批注"));
		// 获取批注对象
		// (int dx1, int dy1, int dx2, int dy2, short col1, int row1, short
		// col2, int row2)
		// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
		HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0,
				(short) 2, 0, (short) x, y));
		// 输入批注信息
		comment.setString(new HSSFRichTextString(txt));
		// 添加批注说明,看状态栏
		// comment.setAuthor("toad");
		// 将批注添加到单元格对象中
		cell.setCellComment(comment);
	}

	/**
	 * 生成EXCEL文件
	 * 
	 * @param fileName
	 * @param sheets
	 * @param list
	 * @throws Exception
	 */
	public void createStream(String fileName, String[] sheets, List list)
			throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel");
		ExcelBean excel = new ExcelBean();
		fileName = fileName + excel.getSystemTime();
		try {
			fileName = new String(fileName.getBytes(), "iso8859-1");
			response.addHeader("Content-Disposition", "attachment;fileName="
					+ fileName + ".xls");
			OutputStream outStream = response.getOutputStream();
			// 输出EXCEL内容
			createExcel(excel, list, sheets, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出EXCEL内容
	 * 
	 * @param excel
	 * @param tables
	 * @param sheets
	 * @param outStream
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void createExcel(ExcelBean excel, List tables,
			String[] sheets, OutputStream outStream) throws IOException,
			SQLException {
		HSSFWorkbook wb = new HSSFWorkbook();
		List list = new ArrayList();
		try {
			// 输出EXCEL内容
			for (int i = 0; i < tables.size(); i++) {
				HSSFSheet sheet = wb.createSheet(sheets[i]);
				RowSetDynaClass rowSetDynaClass = (RowSetDynaClass) tables
						.get(i);
				Map map = createRowSetDynaClass(excel, rowSetDynaClass, sheet);
				list.add(map);
			}
			// 设置样式：主表中添加关联子表数据批注
			// LIST长度大于一说明有多个子表SHEET
			if (list.size() > 1) {
				// 主表SHEET
				HSSFSheet mainSheet = wb.getSheetAt(0);
				// 主表主键MAP(主键数据,标记行数)
				Map mainMap = (Map) list.get(0);
				// 循环mainMap在主键数据上添加批注
				Iterator iter = mainMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					// 行数存放在LIST中（一对多子表中对应主表数据有多行)
					int r = (Integer) ((List) entry.getValue()).get(0);
					// 批注内容数组
					String text = "";
					// 循环子表sheet生成对应的批注内容
					for (int i = 1; i < list.size(); i++) {
						Map subMap = (Map) list.get(i);
						if (subMap != null && subMap.size() > 0) {
							String str = sheets[i] + ":";
							List rs = (List) subMap.get(key);
							if (rs != null) {
								// a0表示EXCEL中显示位置
								int a0 = ((Integer) rs.get(0)).intValue() + 1;
								if (rs.size() > 1) {
									int a1 = ((Integer) rs.get(rs.size() - 1))
											.intValue() + 1;
									str += "A" + a0 + "--A" + a1;
								} else
									str += "A" + a0;
							}
							text += str + ",";
						}
					}
					if (!"".equals(text)) {
						text = text.substring(0, text.length() - 1);
						createComment(text, mainSheet, r, 0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (outStream != null) {
					wb.write(outStream);
					wb = null;
				}
				if (outStream != null) {
					outStream.close();
					outStream = null;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加RowSetDynaClass数据
	 * 
	 * @param excel
	 * @param rowSetDynaClass
	 * @param sheet
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static Map createRowSetDynaClass(ExcelBean excel,
			RowSetDynaClass rowSetDynaClass, HSSFSheet sheet)
			throws IOException, SQLException {
		Map<String, List> map = new HashMap();
		DynaProperty[] names = rowSetDynaClass.getDynaProperties();
		String str = "";
		for (int i = 0; i < names.length; i++) {
			String title = names[i].getName();
			if (i == names.length - 1) {
				str += title;
			} else {
				str += title + ",";
			}
		}
		String[] reportTitles = str.split(",");
		excel.setTitleValue(0, sheet, reportTitles);
		List list = rowSetDynaClass.getRows();
		try {
			str = "";
			List rs = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String primary = "";
				for (int j = 0; j < reportTitles.length; j++) {
					String ss = bean.get(reportTitles[j]) != null ? bean.get(
							reportTitles[j]).toString() : "";
					excel.setCellValue(i + 1, j, sheet, ss.trim());
					if (j == 0) {
						primary = ss.trim();
					}
				}
				if (!str.equals(primary)) {
					if (!"".equals(str))
						map.put(str, rs);
					str = primary;
					rs = new ArrayList();
					// i+1SHEET行数
					rs.add(i + 1);
				} else {
					rs.add(i + 1);
				}
			}
			if (!"".equals(str))
				map.put(str, rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return map;
	}

}
