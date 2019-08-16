package com.tlys.exe.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.common.util.DicUtil;
import com.tlys.exe.common.util.ExcelUtil;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeDicDao;
import com.tlys.exe.dao.ExeTranStasVO;
import com.tlys.exe.dao.ExeTranStatDao;
import com.tlys.exe.dao.ExeTransportDao;
import com.tlys.exe.dao.ExeTransportSearchField;
import com.tlys.exe.model.ExeTransport;

/**
 * ��������Service
 * 
 * @author �״���
 * 
 */
@Service
public class ExeTransportService {

	@Autowired
	private ExeTransportDao transportDao;

	@Autowired
	private ExeTranStatDao exeTranStatDao;

	@Autowired
	private ExeDicDao exeDicDao;

	/**
	 * ��ѯ����������Ϣ
	 * 
	 * @param field
	 * @return
	 */
	public PageView<ExeTransport> listTransport(ExeTransportSearchField field, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return transportDao.listTransport(field, pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * ȡ����ϲ�ѯ����
	 * 
	 * @param field
	 * @return
	 */
	public int getListTransportCount(ExeTransportSearchField field) {
		return transportDao.getListTransportCount(field);
	}

	/**
	 * ȡ�üƷ����غ�ʵ������
	 * 
	 * @param field
	 * @return
	 */
	public double getCap_wgt(ExeTransportSearchField field) {
		return transportDao.getCap_wgt(field);
	}

	/**
	 * ȡ�üƷ����غ�ʵ������
	 * 
	 * @param field
	 * @return
	 */
	public double getActual_wgt(ExeTransportSearchField field) {
		return transportDao.getActual_wgt(field);
	}

	/**
	 * ����excelʱ��ѯ
	 * 
	 * @param field
	 * @return
	 */
	public List<ExeTransport> exportTransport(ExeTransportSearchField field) {
		return transportDao.exportTransport(field);
	}

	/**
	 * ȡ�û�Ʊ��ϸ��Ϣ
	 * 
	 * @param wb_nbr
	 * @return
	 */
	public ExeTransport getExeTransport(Long rec_id) {
		return transportDao.getExtTransport(rec_id);
	}

	/**
	 * �˹����ӻ���������Ϣ��������¼����ʵ������
	 * 
	 * @param exeTransport
	 */
	public void saveExeTransport(ExeTransport exeTransport) {
		transportDao.saveExeTransport(exeTransport);
	}

	/**
	 * �˹�ɾ������������Ϣ
	 * 
	 * @param wb_nbr
	 */
	public void deleteExeTransport(Long rec_id) {
		transportDao.deleteExeTransport(rec_id);
	}

	/*
	 * ���ջ�Ʊ���ڣ�ʼ��վ����Ʊ�Ų�ѯ
	 */
	public List<ExeTransport> queryExeTransport(Date wb_date, String cdy_org_stn, String wb_nbr) {
		return transportDao.queryExeTransport(wb_date, cdy_org_stn, wb_nbr);
	}

	/*
	 * ���ջ�Ʊ��ʾ����ѯ
	 */
	public List<ExeTransport> queryExeTransport(String wb_id) {
		return transportDao.queryExeTransport(wb_id);
	}

	/**
	 * ���ճ��š���վ���롢��Ʊ�Ų�������Ϣ
	 * 
	 * @param car_no
	 * @param cdy_org_stn
	 * @param wb_nbr
	 * @return
	 */
	public ExeTransport queryExeTransport(String car_no, String cdy_org_stn, String wb_nbr) {
		return transportDao.queryExeTransport(car_no, cdy_org_stn, wb_nbr);
	}

	/**
	 * ��������ͳ�Ʊ���
	 * 
	 * @return
	 */
	public List<List<ExeTranStasVO>> tranStatictics(String statDate) throws Exception {
		List<DicAreacorp> listAreacorp = exeDicDao.listDicAreacorp();// ȡ������˾�б�
		List<ExeTranStasVO> listAreaStat = new ArrayList<ExeTranStasVO>();// ��������ϼ��б�
		List<ExeTranStasVO> listCorpStat = new ArrayList<ExeTranStasVO>();// �������й�˾�����б�
		ExeTranStasVO totalVO = new ExeTranStasVO();// �����������ݵĺϼ���
		totalVO.setAreaid("00000000");
		totalVO.setAreaname("����ϼ�");
		totalVO.setCorpid("00000000");
		totalVO.setCorpname("�ϼ�");
		totalVO.setCdy_name("�ϼ�");
		totalVO.setCdy_code("�ϼ�");
		totalVO.setCorprowspan(listAreacorp.size() + 1);
		List<ExeTranStasVO> tempAreaList = new ArrayList<ExeTranStasVO>();// ��ʱ����˾�б�
		for (DicAreacorp area : listAreacorp) {
			List<ExeTranStasVO> listStat = exeTranStatDao.tranStatictics(statDate, area.getAreaid());
			if (listStat.size() > 0) {
				ExeTranStasVO temp = listStat.get(0);
				tempAreaList.add(temp);
				totalVO.setTotal_carnum(totalVO.getTotal_carnum() + temp.getTotal_carnum());
				totalVO.setTotal_wgt(totalVO.getTotal_wgt() + temp.getTotal_wgt());
				totalVO.setCur_carnum(totalVO.getCur_carnum() + temp.getCur_carnum());
				totalVO.setCur_wgt(totalVO.getCur_wgt() + temp.getCur_wgt());
			}
			for (ExeTranStasVO vo : listStat)
				listCorpStat.add(vo);
		}

		listAreaStat.add(totalVO);
		for (ExeTranStasVO vo : tempAreaList)
			listAreaStat.add(vo);

		List<List<ExeTranStasVO>> listRet = new ArrayList<List<ExeTranStasVO>>();
		listRet.add(listAreaStat);
		listRet.add(listCorpStat);

		int i = 1;
		for (ExeTranStasVO vo : listRet.get(1)) {
			System.out.println(i + "----" + vo.getAreaid() + "-" + vo.getAreaname() + "-" + vo.getCorpid() + "-"
					+ vo.getCorpname() + "-" + vo.getCdy_code() + "-" + vo.getCdy_name() + "-" + vo.getDest_stn() + "-"
					+ vo.getDest_name() + "-" + vo.getCur_carnum() + "-" + vo.getCur_wgt() + "-" + vo.getTotal_carnum()
					+ "-" + vo.getTotal_wgt() + "-" + vo.getCorprowspan() + "-" + vo.getCdyrowspan());
			i++;
		}
		return listRet;
	}

	/**
	 * �Ա���������ͳ�Ʊ�
	 * 
	 * @param stat_typeͳ������
	 * @param year��
	 * @param quarter����
	 * @param month��
	 * @param entpr_id��ҵid
	 * @param medium_id��װ����id
	 * @return
	 */
	public List<ExeYyStat> getYyStatistics(String stat_type, String year, String quarter, String month,
			String entpr_id, String medium_id) {
		List<ExeYyStat> list = new ArrayList<ExeYyStat>();
		List<DicAreacorp> listAreacorp = null;

		List<DicSinocorp> listSinocorp = null;
		if (entpr_id.equals("")) {
			listAreacorp = exeDicDao.listDicAreacorp("dataAutho");// ȡ������˾�б�,�����û�����Ȩ��
			listSinocorp = exeDicDao.listDicSinocorp("dataAuthor");// �����˾idΪ�գ�������û�����Ȩ�ޣ���������ݿ�ȡ��ҵ
		} else {
			listAreacorp = exeDicDao.listDicAreacorpByCorpid(StringUtil.operStr(entpr_id));// ȡ������˾�б�,�����û�����Ȩ��
			listSinocorp = exeDicDao.listDicSinocorpByIdArr(StringUtil.operStr(entpr_id));
		}

		List<Object[]> listTransYYStat = exeTranStatDao.getTransYYStat(stat_type, year, quarter, month, entpr_id,
				medium_id);// �Ա���������ͳ�Ʊ�
		List<Object[]> listTransYYStatNoCycle = exeTranStatDao.getTransYYStatNoCycle(stat_type, year, quarter, month,
				entpr_id, medium_id);
		int[] rowspan = this.getListRowspan(listAreacorp, listSinocorp);
		int i = 0;
		for (DicAreacorp area : listAreacorp) {
			ExeYyStat hjStat = new ExeYyStat();// ����ϼ���
			hjStat.setArea_id(area.getAreaid());
			hjStat.setArea_name(area.getShrinkname());
			int hjCar_count = 0, hjCycle_count = 0, hjNoCycle_count = 0;
			for (DicSinocorp sinocorp : listSinocorp) {
				ExeYyStat yyStat = new ExeYyStat();
				if (area.getAreaid().equals(sinocorp.getAreaid())) {
					yyStat.setArea_id(area.getAreaid());
					yyStat.setArea_name(area.getShrinkname());
					yyStat.setCorp_id(sinocorp.getCorpid());
					yyStat.setCorp_name(sinocorp.getShortname());
					for (Object[] obj : listTransYYStat) {
						if (String.valueOf(obj[0]).equals(sinocorp.getCorpid())) {
							yyStat.setCar_count((int) Long.parseLong(obj[1].toString()));
							yyStat.setCycle_count((int) Long.parseLong(obj[2].toString()));
							yyStat.setEver_cycle_count((float) yyStat.getCycle_count() / yyStat.getCar_count());
							hjCar_count += yyStat.getCar_count();
							hjCycle_count += yyStat.getCycle_count();
							break;
						}
					}
					for (Object[] obj2 : listTransYYStatNoCycle) {// ���δ���ó���
						if (obj2[0].toString().equals(yyStat.getCorp_id())) {
							yyStat.setNoCycle_count((int) Long.parseLong(obj2[1].toString()));
							hjNoCycle_count += yyStat.getNoCycle_count();
							break;
						}
					}
					yyStat.setRowspan(rowspan[i]);
					list.add(yyStat);
				}
			}
			hjStat.setCorp_id("HJCorp");
			hjStat.setCorp_name("�ϼ�");
			hjStat.setCar_count(hjCar_count);
			hjStat.setCycle_count(hjCycle_count);
			hjStat.setNoCycle_count(hjNoCycle_count);
			hjStat.setEver_cycle_count((float) hjCycle_count / hjCar_count);
			hjStat.setRowspan(rowspan[i]);
			list.add(hjStat);
			i++;
		}
		return list;
	}

	/**
	 * ȡ��δ���г�������ϸ��Ϣ
	 * 
	 * @param stat_type
	 * @param year
	 * @param quarter
	 * @param month
	 * @param entpr_id
	 * @param medium_id
	 * @return
	 */
	public List<Object[]> getTransYYStatNoCycleDetail(String stat_type, String year, String quarter, String month,
			String entpr_id, String medium_id, String area_id) {
		return exeTranStatDao
				.getTransYYStatNoCycleDetail(stat_type, year, quarter, month, entpr_id, medium_id, area_id);
	}

	/**
	 * ȡ����������Ĺ�˾��
	 * 
	 * @return
	 */
	public int[] getListRowspan(List<DicAreacorp> listAreacorp, List<DicSinocorp> listSinocorp) {
		int[] rowspan = new int[listAreacorp.size() + 1];// ����˾+�ϼ���
		int i = 0;
		for (DicAreacorp area : listAreacorp) {
			int k = 0;
			for (DicSinocorp corp : listSinocorp) {
				if (area.getAreaid().equals(corp.getAreaid())) {
					k++;
				}
			}
			rowspan[i] = k + 1;
			i++;
		}
		rowspan[listAreacorp.size()] = 1;
		return rowspan;
	}

	/**
	 * ����excel
	 * 
	 * @param listTransport
	 * @param response
	 */
	public void exportExcel(List<ExeTransport> listTransport, String zbc_flag, HttpServletResponse response) {
		HashMap<String, String> mapCarCorp = new HashMap<String, String>();
		if (zbc_flag.equals("1")) {// ������Ա���ʱ�������Ա�����Ӧ����ҵ
			String car_noArr = "";
			for (ExeTransport transport : listTransport) {
				if (car_noArr.indexOf(transport.getCar_no()) == -1)
					car_noArr += transport.getCar_no() + ",";
			}
			mapCarCorp = exeDicDao.getCorpNameByCar_noArr(car_noArr.equals("") ? car_noArr : car_noArr.substring(0,
					car_noArr.length() - 1));
		}

		String[][] excel = new String[listTransport.size() + 1][13];
		if (zbc_flag.equals("0"))
			excel[0][0] = "������������ҵ";
		else if (zbc_flag.equals("1"))
			excel[0][0] = "ʹ����ҵ";
		excel[0][1] = "��Ʊ��";
		excel[0][2] = "����";
		excel[0][3] = "����Ʒ��";
		excel[0][4] = "��Ʒ���";
		excel[0][5] = "�Ʒ�����";
		excel[0][6] = "ʵ������";
		excel[0][7] = "��վ��";
		excel[0][8] = "��վ��";
		excel[0][9] = "��Ʊ����";
		excel[0][10] = "������";
		excel[0][11] = "�ջ���";
		excel[0][12] = "��Ϣ��Դ��ʶ";
		int ii = 1;
		for (ExeTransport transport : listTransport) {
			if (zbc_flag.equals("0"))
				excel[ii][0] = transport.getShipper_entpr_name();
			else if (zbc_flag.equals("1"))
				excel[ii][0] = mapCarCorp.get(transport.getCar_no());
			excel[ii][1] = transport.getWb_nbr();
			excel[ii][2] = transport.getCar_no();
			excel[ii][3] = transport.getCdy_name();
			excel[ii][4] = DicUtil.getGoods_type(transport.getGoods_type());
			excel[ii][5] = String.valueOf(transport.getCar_cap_wgt() == null ? "" : transport.getCar_cap_wgt());
			excel[ii][6] = String.valueOf(transport.getCar_actual_wgt() == null ? "" : transport.getCar_actual_wgt());
			excel[ii][7] = transport.getCdy_o_stn_name();
			excel[ii][8] = transport.getDest_stn_name();
			excel[ii][9] = StringUtil.dateToString(transport.getWb_date(), "yyyy-MM-dd");
			excel[ii][10] = transport.getShipper_name();
			excel[ii][11] = transport.getCon_name();
			excel[ii][12] = DicUtil.getMsg_type(transport.getMsg_type());
			ii++;
			if (ii > 500)
				break;
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		// sheet����һ������ҳ
		HSSFSheet sheet = wb.createSheet("����������Ϣ");
		sheet.setDefaultColumnWidth(16);
		sheet.setGridsPrinted(false);
		sheet.setDisplayGridlines(false);
		CellStyle style = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
						(short) 10));
		for (short i = 0; i < excel.length; i++) {
			// HSSFRow,��Ӧһ��
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length; j++) {
				// HSSFCell��Ӧһ��
				HSSFCell cell = row.createCell(j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(excel[i][j]);
				cell.setCellStyle(style);
			}
		}
		OutputStream os = null;
		try {

			String fileName = "����������Ϣ�����б�";
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
