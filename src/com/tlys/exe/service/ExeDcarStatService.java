package com.tlys.exe.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.page.PageView;
import com.tlys.exe.common.util.DicUtil;
import com.tlys.exe.common.util.ExcelUtil;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.exe.dao.ExeDcarStatSearchField;
import com.tlys.exe.dao.ExeEstArrivalDao;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeEstArrival;

/**
 * 车辆动态Service
 * 
 * @author 孔垂云
 * 
 */
@Service
public class ExeDcarStatService {

	@Autowired
	private ExeDcarStatDao dCarStatDao;

	@Autowired
	private ExeEstArrivalDao exeEstArrivalDao;

	/**
	 * 组合查询车辆动态
	 * 
	 * @param field
	 * @return
	 */
	public PageView<ExeDcarStat> listDcarStat(ExeDcarStatSearchField field, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return dCarStatDao.listDcarStat(field, pageUrl, totalRecord, currentPage, pageSize);
	}
	
	/**
	 * 2015-12-10 
	 * 组合查询车辆动态（用于炼销)
	 * 
	 * @param field
	 * @return
	 */
	public PageView<ExeDcarStat> listDcarStatLx(ExeDcarStatSearchField field, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return dCarStatDao.listDcarStatLx(field, pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * 取得组合查询总数
	 * 
	 * @param field
	 * @return
	 */
	public int getListDcarStatCount(ExeDcarStatSearchField field) {
		return dCarStatDao.getListDcarStatCount(field);
	}
	
	/**
	 * 2015-12-10 英语炼销
	 * 取得组合查询总数
	 * 
	 * @param field
	 * @return
	 */
	public int getListDcarStatCountLx(ExeDcarStatSearchField field) {
		return dCarStatDao.getListDcarStatCountLx(field);
	}

	/**
	 * 导出excel时查询
	 * 
	 * @param field
	 * @return
	 */
	public List<ExeDcarStat> exportDcarStat(ExeDcarStatSearchField field) {
		return dCarStatDao.exportDcarStat(field);
	}
	
	/**
	 * 2015-12-10 用于炼销
	 * 导出excel时查询
	 * 
	 * @param field
	 * @return
	 */
	public List<ExeDcarStat> exportDcarStatLx(ExeDcarStatSearchField field) {
		return dCarStatDao.exportDcarStatLx(field);
	}

	/**
	 * 导出excel时查询有预计到达标识的
	 * 
	 * @return
	 */
	public List<ExeDcarStat> exportEstarr() {
		return dCarStatDao.exportEstarr();
	}

	/**
	 * 根据车号查询这个车号详细信息
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeDcarStat loadExeDcarStat(String car_no) {
		return dCarStatDao.loadExeDcarStat(car_no);
	}

	/**
	 * 取得预计到达时间信息
	 * 
	 * @param id
	 * @return
	 */
	public List<ExeEstArrival> listEstArrival(String car_no, Date evt_time) {
		return exeEstArrivalDao.listEstArrival(car_no, evt_time);
	}

	/**
	 * 返回当前用户有预计到达记录的及时统计数，查动态库里面ESTARR_FLAG为1的总数
	 * 
	 */
	public Integer getEstarr_flagCount() {
		return dCarStatDao.getEstarr_flagCount();
	}

	/**
	 * 取得所有有预计到达时间的信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageView<ExeDcarStat> getEstarr_flagRec(String pageUrl, int totalRecord, int currentPage, int pageSize) {
		return dCarStatDao.getEstarr_flagRec(pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * 导出excel
	 * 
	 * @param listDcatStat
	 * @param corpid用户对应公司id，session中
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	public void exportExcel(List<ExeDcarStat> listDcatStat, String corpid, HttpServletResponse response) {
		int count = 0;
		if (listDcatStat.size() > 1000)
			count = 1000;
		else
			count = listDcatStat.size();
		String[][] excel = new String[count + 1][13];
		excel[0][0] = "车号";
		excel[0][1] = "车种车型";
		excel[0][2] = "租用标示";
		excel[0][3] = "当前站";
		excel[0][4] = "当前路局";
		excel[0][5] = "报告时间";
		excel[0][6] = "充装介质";
		excel[0][7] = "始发站";
		excel[0][8] = "始发路局";
		excel[0][9] = "终到站";
		excel[0][10] = "终到路局";
		excel[0][11] = "空重标识";
		excel[0][12] = "预计到达标识";
		int ii = 1;

		for (ExeDcarStat dcarStat : listDcatStat) {
			excel[ii][0] = dcarStat.getCar_no();
			excel[ii][1] = dcarStat.getCar_type();
			excel[ii][2] = DicUtil.getCar_rent_flag(dcarStat.getCar_rent_flag(), corpid, dcarStat.getCar_owner_id(),
					dcarStat.getCar_user_id());
			excel[ii][3] = dcarStat.getCur_stn_name();
			excel[ii][4] = DicUtil.getAdm(dcarStat.getCur_adm());
			excel[ii][5] = StringUtil.dateToString(dcarStat.getRpt_time(), "yyyy-MM-dd HH:mm:ss");
			excel[ii][6] = dcarStat.getCar_fill_medium();
			excel[ii][7] = dcarStat.getCdy_o_stn_name();
			excel[ii][8] = DicUtil.getAdm(dcarStat.getOrg_adm());
			excel[ii][9] = dcarStat.getDest_stn_name();
			excel[ii][10] = DicUtil.getAdm(dcarStat.getDest_adm());
			excel[ii][11] = DicUtil.getLe_code(dcarStat.getLe_code());
			excel[ii][12] = dcarStat.getEstarr_flag();
			ii++;
			if (ii > 1000)
				break;
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		// sheet创建一个工作页
		HSSFSheet sheet = wb.createSheet("车辆动态信息");
		sheet.setDefaultColumnWidth(16);
		sheet.setGridsPrinted(false);
		sheet.setDisplayGridlines(false);
		CellStyle style = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
						(short) 10));
		CellStyle styleRed = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_RED,
						(short) 10));
		for (short i = 0; i < excel.length; i++) {
			// System.out.println("i====" + i);
			// HSSFRow,对应一行
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length - 1; j++) {
				// HSSFCell对应一格
				HSSFCell cell = row.createCell(j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(excel[i][j]);
				if (excel[i][12] != null && excel[i][12].equals("1"))
					cell.setCellStyle(styleRed);
				else
					cell.setCellStyle(style);
			}
		}
		OutputStream os = null;
		try {

			String fileName = "车辆动态信息导出列表";
			response.setContentType("application/msexcel");
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
			System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				os = null;
				response.flushBuffer();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 2015-12-8 
	 * 导出excel（用户炼销)
	 * 
	 * @param listDcatStat
	 * @param corpid用户对应公司id，session中
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	public void exportExcelLx(List<ExeDcarStat> listDcatStat, String corpid, HttpServletResponse response) {
		int count = 0;
		//if (listDcatStat.size() > 1000)
		//	count = 1000;
		//else
			count = listDcatStat.size();
			
		String[][] excel = new String[count + 1][16];
		excel[0][0] = "序号";
		excel[0][1] = "车号";
		excel[0][2] = "报告日期";
		excel[0][3] = "报告时间";
		excel[0][4] = "报告站";
		excel[0][5] = "当前路局";
		excel[0][6] = "重/空标识";
		excel[0][7] = "车次";
		excel[0][8] = "编组顺序";
		excel[0][9] = "货票号";
		excel[0][10] = "货物品名";
		excel[0][11] = "始发站";
		excel[0][12] = "终到站";
		excel[0][13] = "发货人";
		excel[0][14] = "收货人";
		excel[0][15] = "预计到达标识";
		
		int ii = 1;

		for (ExeDcarStat dcarStat : listDcatStat) {
			
			excel[ii][0] = Integer.toString(ii);
			excel[ii][1] = dcarStat.getCar_no();
			excel[ii][2] = StringUtil.dateToString(dcarStat.getRpt_time(), "yyyy-MM-dd");
			excel[ii][3] = StringUtil.dateToString(dcarStat.getRpt_time(), "HH:mm:ss");
			excel[ii][4] = dcarStat.getCur_stn_name();
			excel[ii][5] = DicUtil.getAdm(dcarStat.getCur_adm());
			excel[ii][6] = DicUtil.getLe_code(dcarStat.getLe_code());
			excel[ii][7] = dcarStat.getTrain_no();
			excel[ii][8] = dcarStat.getCar_order().toString();
			excel[ii][9] = dcarStat.getWb_nbr();
			excel[ii][10] = dcarStat.getCdy_name();
			excel[ii][11] = dcarStat.getCdy_o_stn_name();
			excel[ii][12] = dcarStat.getDest_stn_name();
			excel[ii][13] = dcarStat.getShipper_name();
			excel[ii][14] = dcarStat.getCon_name();
			
			excel[ii][15] = dcarStat.getEstarr_flag();
			
			ii++;
			/*  取消行数限制
			 * 
			 */
			//if (ii > 100)
			//	break;
				
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		// sheet创建一个工作页
		HSSFSheet sheet = wb.createSheet("车辆动态信息");
		sheet.setDefaultColumnWidth(16);
		sheet.setGridsPrinted(false);
		sheet.setDisplayGridlines(false);
		CellStyle style = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
						(short) 10));
		CellStyle styleRed = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_RED,
						(short) 10));
		for (short i = 0; i < excel.length; i++) {
			// System.out.println("i====" + i);
			// HSSFRow,对应一行
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length - 1; j++) {
				// HSSFCell对应一格
				HSSFCell cell = row.createCell(j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(excel[i][j]);
				if (excel[i][15] != null && excel[i][15].equals("1"))
					cell.setCellStyle(styleRed);
				else
					cell.setCellStyle(style);
			}
		}
		OutputStream os = null;
		try {

			String fileName = "车辆动态信息导出列表";
			response.setContentType("application/msexcel");
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
			System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				os = null;
				response.flushBuffer();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
