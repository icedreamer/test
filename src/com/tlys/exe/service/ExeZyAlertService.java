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
import com.tlys.exe.dao.ExeEstArrivalDao;
import com.tlys.exe.dao.ExeZyAlertDao;
import com.tlys.exe.dao.ExeZyAlertSearchField;
import com.tlys.exe.model.ExeEstArrival;
import com.tlys.exe.model.ExeZyAlert;

@Service
public class ExeZyAlertService {
	@Autowired
	ExeZyAlertDao zyAlertDao;
	@Autowired
	private ExeEstArrivalDao exeEstArrivalDao;

	/**
	 * ��ϲ�ѯ����Ԥ��
	 * 
	 * @param field
	 * @return
	 */
	public PageView<ExeZyAlert> listZyAlert(ExeZyAlertSearchField field, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return zyAlertDao.listZyAlert(field, pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * ȡ���������Ԥ����ѯ����
	 * 
	 * @param field
	 * @return
	 */
	public int getListZyAlertCount(ExeZyAlertSearchField field) {
		return zyAlertDao.getListZyAlertCount(field);
	}

	/**
	 * ����excelʱ��ѯ
	 * 
	 * @param field
	 * @return
	 */
	public List<ExeZyAlert> exportZyAlert(ExeZyAlertSearchField field) {
		return zyAlertDao.exportZyAlert(field);
	}

	/**
	 * ���ݳ��Ų�ѯ���������ϸ��Ϣ
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeZyAlert loadExeZyAlert(String car_no, Date evt_time) {
		return zyAlertDao.loadExeZyAlert(car_no, evt_time);
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
	 * ����excel
	 * 
	 * @param listDcatStat
	 * @param corpid�û���Ӧ��˾id��session��
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	public void exportExcel(List<ExeZyAlert> listZyAlert, String corpid, HttpServletResponse response) {
		int count = 0;
		if (listZyAlert.size() > 1000)
			count = 1000;
		else
			count = listZyAlert.size();
		String[][] excel = new String[count + 1][14];
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
		excel[0][12] = "ƥ������";
		excel[0][13] = "Ԥ��ԭ��";
		int ii = 1;

		for (ExeZyAlert zyAlert : listZyAlert) {
			excel[ii][0] = zyAlert.getId().getCar_no();
			excel[ii][1] = zyAlert.getCar_type();
			excel[ii][2] = DicUtil.getCar_rent_flag(zyAlert.getCar_rent_flag(), corpid, zyAlert.getCar_owner_id(),
					zyAlert.getCar_user_id());
			excel[ii][3] = zyAlert.getCur_stn_name();
			excel[ii][4] = DicUtil.getAdm(zyAlert.getCur_adm());
			excel[ii][5] = StringUtil.dateToString(zyAlert.getRpt_time(), "yyyy-MM-dd HH:mm:ss");
			excel[ii][6] = zyAlert.getCar_fill_medium();
			excel[ii][7] = zyAlert.getCdy_o_stn_name();
			excel[ii][8] = DicUtil.getAdm(zyAlert.getOrg_adm());
			excel[ii][9] = zyAlert.getDest_stn_name();
			excel[ii][10] = DicUtil.getAdm(zyAlert.getDest_adm());
			excel[ii][11] = DicUtil.getLe_code(zyAlert.getLe_code());
			excel[ii][12] = DicUtil.getPbl_reason(zyAlert.getPbl_reason());
			excel[ii][13] = DicUtil.getOper_flag(zyAlert.getOper_flag());
			ii++;
			if (ii > 1000)
				break;
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		// sheet����һ������ҳ
		HSSFSheet sheet = wb.createSheet("��������Ԥ����Ϣ");
		sheet.setDefaultColumnWidth(16);
		sheet.setGridsPrinted(false);
		sheet.setDisplayGridlines(false);
		CellStyle style = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
						(short) 10));
		for (short i = 0; i < excel.length; i++) {
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(excel[i][j]);
				cell.setCellStyle(style);
			}
		}
		OutputStream os = null;
		try {

			String fileName = "��������Ԥ����Ϣ�����б�";
			response.setContentType("application/msexcel");
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GBK"), "ISO-8859-1") + ".xls");
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

	public void updateZyAlert(ExeZyAlert zyAlert) {
		zyAlertDao.update(zyAlert);
	}
}
