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
 * ������̬Service
 * 
 * @author �״���
 * 
 */
@Service
public class ExeDcarStatService {

	@Autowired
	private ExeDcarStatDao dCarStatDao;

	@Autowired
	private ExeEstArrivalDao exeEstArrivalDao;

	/**
	 * ��ϲ�ѯ������̬
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
	 * ��ϲ�ѯ������̬����������)
	 * 
	 * @param field
	 * @return
	 */
	public PageView<ExeDcarStat> listDcarStatLx(ExeDcarStatSearchField field, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return dCarStatDao.listDcarStatLx(field, pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * ȡ����ϲ�ѯ����
	 * 
	 * @param field
	 * @return
	 */
	public int getListDcarStatCount(ExeDcarStatSearchField field) {
		return dCarStatDao.getListDcarStatCount(field);
	}
	
	/**
	 * 2015-12-10 Ӣ������
	 * ȡ����ϲ�ѯ����
	 * 
	 * @param field
	 * @return
	 */
	public int getListDcarStatCountLx(ExeDcarStatSearchField field) {
		return dCarStatDao.getListDcarStatCountLx(field);
	}

	/**
	 * ����excelʱ��ѯ
	 * 
	 * @param field
	 * @return
	 */
	public List<ExeDcarStat> exportDcarStat(ExeDcarStatSearchField field) {
		return dCarStatDao.exportDcarStat(field);
	}
	
	/**
	 * 2015-12-10 ��������
	 * ����excelʱ��ѯ
	 * 
	 * @param field
	 * @return
	 */
	public List<ExeDcarStat> exportDcarStatLx(ExeDcarStatSearchField field) {
		return dCarStatDao.exportDcarStatLx(field);
	}

	/**
	 * ����excelʱ��ѯ��Ԥ�Ƶ����ʶ��
	 * 
	 * @return
	 */
	public List<ExeDcarStat> exportEstarr() {
		return dCarStatDao.exportEstarr();
	}

	/**
	 * ���ݳ��Ų�ѯ���������ϸ��Ϣ
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeDcarStat loadExeDcarStat(String car_no) {
		return dCarStatDao.loadExeDcarStat(car_no);
	}

	/**
	 * ȡ��Ԥ�Ƶ���ʱ����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public List<ExeEstArrival> listEstArrival(String car_no, Date evt_time) {
		return exeEstArrivalDao.listEstArrival(car_no, evt_time);
	}

	/**
	 * ���ص�ǰ�û���Ԥ�Ƶ����¼�ļ�ʱͳ�������鶯̬������ESTARR_FLAGΪ1������
	 * 
	 */
	public Integer getEstarr_flagCount() {
		return dCarStatDao.getEstarr_flagCount();
	}

	/**
	 * ȡ��������Ԥ�Ƶ���ʱ�����Ϣ
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageView<ExeDcarStat> getEstarr_flagRec(String pageUrl, int totalRecord, int currentPage, int pageSize) {
		return dCarStatDao.getEstarr_flagRec(pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * ����excel
	 * 
	 * @param listDcatStat
	 * @param corpid�û���Ӧ��˾id��session��
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
		excel[0][0] = "����";
		excel[0][1] = "���ֳ���";
		excel[0][2] = "���ñ�ʾ";
		excel[0][3] = "��ǰվ";
		excel[0][4] = "��ǰ·��";
		excel[0][5] = "����ʱ��";
		excel[0][6] = "��װ����";
		excel[0][7] = "ʼ��վ";
		excel[0][8] = "ʼ��·��";
		excel[0][9] = "�յ�վ";
		excel[0][10] = "�յ�·��";
		excel[0][11] = "���ر�ʶ";
		excel[0][12] = "Ԥ�Ƶ����ʶ";
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
		// sheet����һ������ҳ
		HSSFSheet sheet = wb.createSheet("������̬��Ϣ");
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
			// HSSFRow,��Ӧһ��
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length - 1; j++) {
				// HSSFCell��Ӧһ��
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

			String fileName = "������̬��Ϣ�����б�";
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
	 * ����excel���û�����)
	 * 
	 * @param listDcatStat
	 * @param corpid�û���Ӧ��˾id��session��
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
		excel[0][0] = "���";
		excel[0][1] = "����";
		excel[0][2] = "��������";
		excel[0][3] = "����ʱ��";
		excel[0][4] = "����վ";
		excel[0][5] = "��ǰ·��";
		excel[0][6] = "��/�ձ�ʶ";
		excel[0][7] = "����";
		excel[0][8] = "����˳��";
		excel[0][9] = "��Ʊ��";
		excel[0][10] = "����Ʒ��";
		excel[0][11] = "ʼ��վ";
		excel[0][12] = "�յ�վ";
		excel[0][13] = "������";
		excel[0][14] = "�ջ���";
		excel[0][15] = "Ԥ�Ƶ����ʶ";
		
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
			/*  ȡ����������
			 * 
			 */
			//if (ii > 100)
			//	break;
				
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		// sheet����һ������ҳ
		HSSFSheet sheet = wb.createSheet("������̬��Ϣ");
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
			// HSSFRow,��Ӧһ��
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length - 1; j++) {
				// HSSFCell��Ӧһ��
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

			String fileName = "������̬��Ϣ�����б�";
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
