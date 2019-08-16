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
 * 车辆运输Service
 * 
 * @author 孔垂云
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
	 * 查询货物运输信息
	 * 
	 * @param field
	 * @return
	 */
	public PageView<ExeTransport> listTransport(ExeTransportSearchField field, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return transportDao.listTransport(field, pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * 取得组合查询总数
	 * 
	 * @param field
	 * @return
	 */
	public int getListTransportCount(ExeTransportSearchField field) {
		return transportDao.getListTransportCount(field);
	}

	/**
	 * 取得计费载重和实际载重
	 * 
	 * @param field
	 * @return
	 */
	public double getCap_wgt(ExeTransportSearchField field) {
		return transportDao.getCap_wgt(field);
	}

	/**
	 * 取得计费载重和实际载重
	 * 
	 * @param field
	 * @return
	 */
	public double getActual_wgt(ExeTransportSearchField field) {
		return transportDao.getActual_wgt(field);
	}

	/**
	 * 导出excel时查询
	 * 
	 * @param field
	 * @return
	 */
	public List<ExeTransport> exportTransport(ExeTransportSearchField field) {
		return transportDao.exportTransport(field);
	}

	/**
	 * 取得货票详细信息
	 * 
	 * @param wb_nbr
	 * @return
	 */
	public ExeTransport getExeTransport(Long rec_id) {
		return transportDao.getExtTransport(rec_id);
	}

	/**
	 * 人工增加货物运输信息，包括补录货物实际载重
	 * 
	 * @param exeTransport
	 */
	public void saveExeTransport(ExeTransport exeTransport) {
		transportDao.saveExeTransport(exeTransport);
	}

	/**
	 * 人工删除货物运输信息
	 * 
	 * @param wb_nbr
	 */
	public void deleteExeTransport(Long rec_id) {
		transportDao.deleteExeTransport(rec_id);
	}

	/*
	 * 按照货票日期，始发站，货票号查询
	 */
	public List<ExeTransport> queryExeTransport(Date wb_date, String cdy_org_stn, String wb_nbr) {
		return transportDao.queryExeTransport(wb_date, cdy_org_stn, wb_nbr);
	}

	/*
	 * 按照货票标示来查询
	 */
	public List<ExeTransport> queryExeTransport(String wb_id) {
		return transportDao.queryExeTransport(wb_id);
	}

	/**
	 * 按照车号、发站代码、货票号查运输信息
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
	 * 车辆运输统计报表
	 * 
	 * @return
	 */
	public List<List<ExeTranStasVO>> tranStatictics(String statDate) throws Exception {
		List<DicAreacorp> listAreacorp = exeDicDao.listDicAreacorp();// 取得区域公司列表
		List<ExeTranStasVO> listAreaStat = new ArrayList<ExeTranStasVO>();// 定义区域合计列表
		List<ExeTranStasVO> listCorpStat = new ArrayList<ExeTranStasVO>();// 定义所有公司数据列表
		ExeTranStasVO totalVO = new ExeTranStasVO();// 定义所有数据的合计数
		totalVO.setAreaid("00000000");
		totalVO.setAreaname("区域合计");
		totalVO.setCorpid("00000000");
		totalVO.setCorpname("合计");
		totalVO.setCdy_name("合计");
		totalVO.setCdy_code("合计");
		totalVO.setCorprowspan(listAreacorp.size() + 1);
		List<ExeTranStasVO> tempAreaList = new ArrayList<ExeTranStasVO>();// 临时区域公司列表
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
	 * 自备车运用量统计表
	 * 
	 * @param stat_type统计类型
	 * @param year年
	 * @param quarter季度
	 * @param month月
	 * @param entpr_id企业id
	 * @param medium_id充装介质id
	 * @return
	 */
	public List<ExeYyStat> getYyStatistics(String stat_type, String year, String quarter, String month,
			String entpr_id, String medium_id) {
		List<ExeYyStat> list = new ArrayList<ExeYyStat>();
		List<DicAreacorp> listAreacorp = null;

		List<DicSinocorp> listSinocorp = null;
		if (entpr_id.equals("")) {
			listAreacorp = exeDicDao.listDicAreacorp("dataAutho");// 取得区域公司列表,根据用户数据权限
			listSinocorp = exeDicDao.listDicSinocorp("dataAuthor");// 如果公司id为空，则根据用户数据权限，否则从数据库取企业
		} else {
			listAreacorp = exeDicDao.listDicAreacorpByCorpid(StringUtil.operStr(entpr_id));// 取得区域公司列表,根据用户数据权限
			listSinocorp = exeDicDao.listDicSinocorpByIdArr(StringUtil.operStr(entpr_id));
		}

		List<Object[]> listTransYYStat = exeTranStatDao.getTransYYStat(stat_type, year, quarter, month, entpr_id,
				medium_id);// 自备车运用量统计表
		List<Object[]> listTransYYStatNoCycle = exeTranStatDao.getTransYYStatNoCycle(stat_type, year, quarter, month,
				entpr_id, medium_id);
		int[] rowspan = this.getListRowspan(listAreacorp, listSinocorp);
		int i = 0;
		for (DicAreacorp area : listAreacorp) {
			ExeYyStat hjStat = new ExeYyStat();// 定义合计数
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
					for (Object[] obj2 : listTransYYStatNoCycle) {// 获得未运用车辆
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
			hjStat.setCorp_name("合计");
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
	 * 取得未运行车辆的详细信息
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
	 * 取得所有区域的公司数
	 * 
	 * @return
	 */
	public int[] getListRowspan(List<DicAreacorp> listAreacorp, List<DicSinocorp> listSinocorp) {
		int[] rowspan = new int[listAreacorp.size() + 1];// 区域公司+合计数
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
	 * 导出excel
	 * 
	 * @param listTransport
	 * @param response
	 */
	public void exportExcel(List<ExeTransport> listTransport, String zbc_flag, HttpServletResponse response) {
		HashMap<String, String> mapCarCorp = new HashMap<String, String>();
		if (zbc_flag.equals("1")) {// 如果是自备车时，查找自备车对应的企业
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
			excel[0][0] = "发货人所在企业";
		else if (zbc_flag.equals("1"))
			excel[0][0] = "使用企业";
		excel[0][1] = "货票号";
		excel[0][2] = "车号";
		excel[0][3] = "货物品名";
		excel[0][4] = "产品类别";
		excel[0][5] = "计费重量";
		excel[0][6] = "实际载重";
		excel[0][7] = "发站名";
		excel[0][8] = "到站名";
		excel[0][9] = "制票日期";
		excel[0][10] = "发货人";
		excel[0][11] = "收货人";
		excel[0][12] = "信息来源标识";
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
		// sheet创建一个工作页
		HSSFSheet sheet = wb.createSheet("货物运输信息");
		sheet.setDefaultColumnWidth(16);
		sheet.setGridsPrinted(false);
		sheet.setDisplayGridlines(false);
		CellStyle style = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
						(short) 10));
		for (short i = 0; i < excel.length; i++) {
			// HSSFRow,对应一行
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length; j++) {
				// HSSFCell对应一格
				HSSFCell cell = row.createCell(j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(excel[i][j]);
				cell.setCellStyle(style);
			}
		}
		OutputStream os = null;
		try {

			String fileName = "货物运输信息导出列表";
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
