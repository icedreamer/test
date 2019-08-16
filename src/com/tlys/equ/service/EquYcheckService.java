package com.tlys.equ.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCartype;
import com.tlys.equ.dao.EquCarCertificateDao;
import com.tlys.equ.dao.EquRepRecordDao;
import com.tlys.equ.dao.EquYcheckBureauDao;
import com.tlys.equ.dao.EquYcheckDao;
import com.tlys.equ.dao.EquYcheckDetDao;
import com.tlys.equ.dao.EquYcheckStationDao;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.model.EquYcheck;
import com.tlys.equ.model.EquYcheckBureau;
import com.tlys.equ.model.EquYcheckDet;
import com.tlys.equ.model.EquYcheckStation;

@Service
public class EquYcheckService {
	private Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EquYcheckDao equYcheckDao;
	@Autowired
	EquYcheckDetDao equYcheckDetDao;
	@Autowired
	EquYcheckBureauDao equYcheckBureauDao;
	@Autowired
	EquCarCertificateDao equCarCertificateDao;
	@Autowired
	EquYcheckStationDao equYcheckStationDao;
	@Autowired
	DicMap dicMap;
	@Autowired
	EquCarService equCarService;
	@Autowired
	EquRepRecordDao equRepRecordDao;

	/**
	 * ��ҳ��ѯ�����¼
	 * 
	 * @param equYcheck
	 * @param pageCtr
	 */
	public void findEquYcheck(EquYcheck equYcheck, PageCtr<EquYcheck> pageCtr) {
		pageCtr.setTotalRecord(equYcheckDao.getEquYcheckCount(equYcheck));
		equYcheckDao.findEquYcheck(equYcheck, pageCtr);
	}

	public EquYcheck loadYcheck(String yinspectionno) {
		return equYcheckDao.load(yinspectionno);
	}

	public EquYcheckBureau getEquYcheckBureau(String yinspectionno, String goodsid, String bureauid) {
		return equYcheckBureauDao.getEquYcheckBureau(yinspectionno, goodsid, bureauid);
	}

	public List<EquYcheckStation> getEquYcheckStation(String yinspectionbureauid) {
		return equYcheckStationDao.getEquYcheckStation(yinspectionbureauid);
	}

	public void findEquYcheckDet(EquYcheckDet equYcheckDet, PageCtr<EquYcheckDet> pageCtr) {
		pageCtr.setTotalRecord(getEquYcheckDetCount(equYcheckDet));
		equYcheckDetDao.findEquYcheckDet(equYcheckDet, pageCtr);
	}

	public int getEquYcheckDetCount(EquYcheckDet equYcheckDet) {
		return equYcheckDetDao.getEquYcheckDetCount(equYcheckDet);
	}

	public List<EquYcheckDet> findEquYcheckDet(EquYcheckDet equYcheckDet) {
		return equYcheckDetDao.findEquYcheckDet(equYcheckDet);
	}

	/**
	 * �����ϸ
	 * 
	 * @param yinspectionrelatedno
	 * @return
	 */
	public EquYcheckDet loadYcheckDet(long id) {
		return equYcheckDetDao.load(id);
	}

	public void save(EquYcheck equYcheck) {
		equYcheckDao.save(equYcheck);
	}

	public void save(EquYcheckStation equYcheckStation) {
		equYcheckStationDao.save(equYcheckStation);
	}

	public void save(EquYcheckDet equYcheckDet) {
		equYcheckDetDao.save(equYcheckDet);
	}

	public void update(EquYcheck equYcheck) {
		equYcheckDao.update(equYcheck);
	}

	/**
	 * ������������״̬
	 * 
	 * @param yinspectionnos
	 * @param status
	 */
	public void update(String[] yinspectionnos, String status) {
		if (null != yinspectionnos && yinspectionnos.length > 0) {
			for (String yinspectionno : yinspectionnos) {
				EquYcheck equYcheck = equYcheckDao.load(yinspectionno);
				equYcheck.setStatus(status);
				equYcheckDao.update(equYcheck);
			}
		}
	}

	public void update(EquYcheckDet equYcheckDet) {
		equYcheckDetDao.update(equYcheckDet);
	}

	public void updateEntity(EquYcheckDet equYcheckDet) {
		equYcheckDetDao.updateEntity(equYcheckDet, equYcheckDet.getId());
	}

	// public void update(String yinspectionno, String goodsid, String type,
	// String cardnum) {
	// equYcheckDetDao.update(yinspectionno, goodsid, type, cardnum);
	// }

	public void deleteEquYcheckDet(List<String> carnoList, String year, String corpid) {
		equYcheckDetDao.delete(carnoList, year, corpid);
	}

	/**
	 * ɾ����������ʱ��ͬʱɾ��4��������Ӧ�ļ�¼
	 * 
	 * @param yinspectionnos
	 */
	public void deleteEquYcheck(String[] yinspectionnos) {
		if (null != yinspectionnos && yinspectionnos.length > 0) {
			List<String> yinspectionnoList = Arrays.asList(yinspectionnos);
			equYcheckStationDao.delete(yinspectionnoList);
			equYcheckBureauDao.delete(yinspectionnoList);
			equYcheckDetDao.delete(yinspectionnoList);
			equYcheckDao.delete(yinspectionnos);

			// EquYcheckDet equYcheckDet = new EquYcheckDet();
			// // ����yinspectionnos �ǽ�yinspectionno��Ϊ��ѯ�������в�ѯ
			// equYcheckDet.setYinspectionnoList(Arrays.asList(yinspectionnos));
			// List<String> yinspectionrelatednoList = new ArrayList<String>();
			// List<EquYcheckDet> equYcheckDetList =
			// equYcheckDetDao.findEquYcheckDet(equYcheckDet);
			// if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
			// for (EquYcheckDet det : equYcheckDetList) {
			// // if
			// //
			// (!yinspectionrelatednoList.contains(det.getYinspectionrelatedno()))
			// // {
			// // yinspectionrelatednoList.add(det.getYinspectionrelatedno());
			// // }
			// }
			// }
			// // ����yinspectionrelatednoList �� �û� ��yinspectionrelatedno ��Ϊɾ��������
			// //
			// equYcheckDet.setYinspectionrelatednoList(yinspectionrelatednoList);
			// // deleteEquYcheckDet(equYcheckDet);
			// // ɾ����������
			// for (String yinspectionno : yinspectionnos) {
			// EquYcheck equYcheck = equYcheckDao.load(yinspectionno);
			// equYcheckDao.delete(equYcheck);
			// }
		}
	}

	public void delete(Long[] ids) {
		equYcheckDetDao.delete(ids);
	}

	/**
	 * ɾ�������ϸ���ݶ�Ӧ��֤����Ϣ
	 * 
	 * @param carnos
	 */
	public void deleteEquCarCertificate(List<String> carnos) {
		if (null == carnos || carnos.isEmpty()) {
			return;
		}
		equCarCertificateDao.delete(carnos);
	}

	/**
	 * ɾ�������ϸ��Ӧ��·����Ϣ
	 * 
	 * @param yinspectionnos
	 */
	public void deleteEquYcheckBureau(List<String> yinspectionnos) {
		equYcheckBureauDao.delete(yinspectionnos);
	}

	public void deleteEquYcheckBureau(String yinspectionno) {
		List<String> yinspectionnos = new ArrayList<String>();
		yinspectionnos.add(yinspectionno);
		equYcheckBureauDao.delete(yinspectionnos);
	}

	public void deleteEquYcheckStation(EquYcheckStation equYcheckStation) {
		equYcheckStationDao.delete(equYcheckStation);
	}

	public void deleteEquYcheckStation(String yinspectionno) {
		equYcheckStationDao.delete(yinspectionno);
	}

	/**
	 * ɾ�������ϸ����
	 * 
	 * @param yinspectionnos
	 */
	public void deleteEquYcheckDet(EquYcheckDet equYcheckDet) {
		if (log.isDebugEnabled()) {
			log.debug("equYcheckDet : " + equYcheckDet);
		}
		if (null == equYcheckDet) {
			return;
		}

		List<String> carnos = new ArrayList<String>();

		List<String> yinspectionrelatednoList = null;// equYcheckDet.getYinspectionrelatednoList();
		if (null != yinspectionrelatednoList && !yinspectionrelatednoList.isEmpty()) {
			for (String yinspectionrelatedno : yinspectionrelatednoList) {
				if (log.isDebugEnabled()) {
					log.debug("yinspectionrelatedno : " + yinspectionrelatedno);
				}
				String[] array = yinspectionrelatedno.split("-");
				String carno = array[2];
				if (!carnos.contains(carno)) {
					carnos.add(carno);
				}
			}
		}
		// ɾ��֤����Ϣ
		deleteEquCarCertificate(carnos);
		// ɾ�������ϸ��Ӧ·����Ϣ
		deleteEquYcheckBureau(yinspectionrelatednoList);
		// ɾ����ϸ������
		equYcheckDetDao.delete(yinspectionrelatednoList);
	}

	/**
	 * ��ѯ���� ���
	 * 
	 * @return
	 */
	public List<String> getYears() {
		return equYcheckDao.getYears();
	}

	public List<EquYcheckBureau> findEquYcheckBureau(String yinspectionno) {
		return equYcheckBureauDao.findEquYcheckBureau(yinspectionno);
	}

	public List<EquYcheckBureau> findEquYcheckBureau(String yinspectionno, List<String> goodsIdList) {
		return equYcheckBureauDao.findEquYcheckBureau(yinspectionno, goodsIdList);
	}

	public List<EquYcheckStation> findEquYcheckStation(String yinspectionno) {
		return equYcheckStationDao.findEquYcheckStation(yinspectionno);
	}

	public void save(EquYcheckBureau equYcheckBureau) {
		equYcheckBureauDao.save(equYcheckBureau);
	}

	public void update(EquYcheckBureau equYcheckBureau) {
		equYcheckBureauDao.update(equYcheckBureau);
	}

	/**
	 * ������ҳ
	 * 
	 * @param workbook
	 *            ȫ����ϸ�б�
	 * @param equYcheckDetList
	 *            ��������¼
	 * @param equYcheck
	 * 
	 * @param sheetName
	 *            ��ҳ14����¼����ҳ��ʾȫ����¼
	 * @param pageSize
	 *            ����Ʒ��չ�����з�Χ(��·�֡���վ)
	 * @param equYcheckBureauListByGoodsidMap
	 *            װ��վ ����Ʒ�����·��
	 * @param loadstationsByGoodsIdAndBureauidMap
	 *            ж��վ ����Ʒ�����·��
	 * @param unloadstationsByGoodsIdAndBureauidMap
	 *            true ÿҳ��ʾҳ��ǩ�� false ��ҳ��ʾҳ��ǩ��
	 * @param footer
	 */

	private void writePositive(HSSFWorkbook workbook, List<EquYcheckDet> equYcheckDetList, EquYcheck equYcheck,
			String sheetName, int pageSize, Map<String, List<EquYcheckBureau>> equYcheckBureauListByGoodsidMap,
			Map<String, List<String>> loadstationsByGoodsIdAndBureauidMap,
			Map<String, List<String>> unloadstationsByGoodsIdAndBureauidMap, boolean footer) {
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, sheetName);
		// �������� ͷ��
		Font fontHeader = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		// ���е�Ԫ����ʽ
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(fontHeader);
		// ���з�Χʹ�õ���ʽwrapStyle
		HSSFCellStyle wrapStyle = workbook.createCellStyle();
		// �����û���
		wrapStyle.setWrapText(true);
		// ��ֱ����
		wrapStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// ˮƽ����
		wrapStyle.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle styleTitle = workbook.createCellStyle();
		styleTitle.setAlignment(CellStyle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		HSSFFont fontTitle = workbook.createFont();
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styleTitle.setFont(fontTitle);
		int j = 0;
		// ͷ���� �����
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, j++, 800);
		// �ϲ���Ԫ��
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 7);
		// ������Ԫ������
		// String titleName = CommUtils.getString("(", equYcheck.getYear(),
		// ") ���", equYcheck.getFilename(), " (",
		// sheetName, ")");
		String titleName = CommUtils.getString("(", equYcheck.getYear(), ") ��� �������� ");
		ExcelUtil.createCell(rowHeader, 0, style, titleName);
		HSSFRow rowDate = ExcelUtil.createRow(sheet, j++, 600);

		ExcelUtil.createCell(rowDate, 0, styleTitle, "��ҵ����");
		ExcelUtil.mergeCell(sheet, rowDate.getRowNum(), rowDate.getRowNum(), 0, 3);

		ExcelUtil.createCell(rowDate, 4, styleTitle, CommUtils.format(new Date(), "yyyy-MM-dd"));
		ExcelUtil.mergeCell(sheet, rowDate.getRowNum(), rowDate.getRowNum(), 4, 7);

		String corpname = equYcheck.getCorpfullname();
		HSSFRow rowCorp = ExcelUtil.createRow(sheet, j++, 600);
		ExcelUtil.createCell(rowCorp, 0, styleTitle, "��ҵ����");
		ExcelUtil.mergeCell(sheet, rowCorp.getRowNum(), rowCorp.getRowNum(), 0, 1);

		ExcelUtil.createCell(rowCorp, 2, styleTitle, corpname);
		ExcelUtil.mergeCell(sheet, rowCorp.getRowNum(), rowCorp.getRowNum(), 2, 7);
		HSSFRow rowCardno = ExcelUtil.createRow(sheet, j++, 600);
		int rowCardNum = rowCardno.getRowNum();
		ExcelUtil.createCell(rowCardno, 0, styleTitle, "Ӫҵִ��ע���");
		ExcelUtil.mergeCell(sheet, rowCardNum, rowCardNum, 0, 1);

		ExcelUtil.createCell(rowCardno, 2, styleTitle, equYcheck.getRegiternumber());
		ExcelUtil.mergeCell(sheet, rowCardNum, rowCardNum, 2, 3);

		ExcelUtil.createCell(rowCardno, 4, styleTitle, "��ҵ���");
		ExcelUtil.mergeCell(sheet, rowCardno.getRowNum(), rowCardno.getRowNum(), 4, 5);

		ExcelUtil.createCell(rowCardno, 6, styleTitle, equYcheck.getCorpcode());
		ExcelUtil.mergeCell(sheet, rowCardNum, rowCardNum, 6, 7);

		HSSFRow rowCars = ExcelUtil.createRow(sheet, j++, 600);
		ExcelUtil.createCell(rowCars, 0, styleTitle, "�����������");

		ExcelUtil.createCell(rowCars, 1, styleTitle, equYcheckDetList.size());
		// ����
		ExcelUtil.createCell(rowCars, 2, styleTitle, "����");

		ExcelUtil.createCell(rowCars, 3, styleTitle, "");
		ExcelUtil.mergeCell(sheet, rowCars.getRowNum(), rowCars.getRowNum(), 3, 7);

		HSSFRow rowTitle = ExcelUtil.createRow(sheet, j++, 600);
		int k = 0;
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "���");
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "����");
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "����");
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "����");
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "���֤��");
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "��ȫ�ϸ�֤��");
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "����Ʒ��");
		ExcelUtil.createCell(rowTitle, k++, styleTitle, "���з�Χ");
		// ���͡����֡�Ʒ�ࡢ����վ �ֵ�
		Map<String, DicCartype> dicCartypeMap = dicMap.getCartypeMap();
		Map<String, DicCarkind> dicCarkindMap = dicMap.getCarkindMap();
		Map<String, String> dicGoodsCategoryMap = dicMap.getGoodscategoryMap();
		Map<String, String> dicRwdepartmentMap = dicMap.getRwdepartmentMap();
		if (null != equYcheckDetList || !equYcheckDetList.isEmpty()) {
			String carsDes = getCarsDes(equYcheckDetList, dicCarkindMap);
			// ���ø��ֳ�������
			HSSFCell qizhongCell = rowCars.getCell(3);
			qizhongCell.setCellValue(carsDes.toString());
			String goodsid = "";

			for (int i = 0; i < pageSize; i++) {
				k = 0;
				EquYcheckDet equYcheckDet = equYcheckDetList.get(i);
				String yinspectionno = equYcheckDet.getYinspectionno();
				// ����������
				HSSFRow row = ExcelUtil.createRow(sheet, j++, 400);
				DicCartype dicCartype = dicCartypeMap.get(equYcheckDet.getCartypeid());
				DicCarkind dicCarkind = dicCarkindMap.get(equYcheckDet.getCarkindid());
				String goodsname = dicGoodsCategoryMap.get(equYcheckDet.getGoodsid());
				// �����
				ExcelUtil.createCell(row, k++, null, i + 1);
				// ����
				ExcelUtil.createCell(row, k++, null, equYcheckDet.getCarno());
				// ����
				ExcelUtil.createCell(row, k++, null,
						(null == dicCartype ? equYcheckDet.getCartypeid() : dicCartype.getCartypename()));
				// ����
				ExcelUtil.createCell(row, k++, null, (null == dicCarkind ? "" : dicCarkind.getShortname()));
				// �������֤
				ExcelUtil.createCell(row, k++, null, equYcheckDet.getTrancertcode());
				// ��ȫ�ϸ�֤
				ExcelUtil.createCell(row, k++, null, equYcheckDet.getSaftcertcode());
				// Ʒ������
				ExcelUtil.createCell(row, k++, wrapStyle, goodsname);
				StringBuffer buffer = new StringBuffer();
				// Ʒ���Ӧ��·����Ϣ(���з�Χ)
				List<EquYcheckBureau> equYcheckBureaus = equYcheckBureauListByGoodsidMap.get(equYcheckDet.getGoodsid());
				if (null != equYcheckBureaus && !equYcheckBureaus.isEmpty()) {
					for (EquYcheckBureau equYcheckBureau : equYcheckBureaus) {
						buffer.append(equYcheckBureau.getBureaushortname()).append("��\r\n");
						String kies = CommUtils.getString(yinspectionno, "-", equYcheckDet.getGoodsid(), "-",
								equYcheckBureau.getBureauid(), "-L");
						List<String> loadstationList = loadstationsByGoodsIdAndBureauidMap.get(kies);
						if (null != loadstationList) {
							buffer.append("    װ��վ��");
							for (String stationid : loadstationList) {
								String stationName = dicRwdepartmentMap.get(stationid);
								buffer.append(stationName).append(" ");
							}
							buffer.append("\r\n");
						}
						kies = CommUtils.getString(yinspectionno, "-", equYcheckDet.getGoodsid(), "-",
								equYcheckBureau.getBureauid(), "-U");
						List<String> unloadstationList = unloadstationsByGoodsIdAndBureauidMap.get(kies);
						if (null != unloadstationList && !unloadstationList.isEmpty()) {
							buffer.append("    ж��վ��");
							for (String stationid : unloadstationList) {
								String stationName = dicRwdepartmentMap.get(stationid);
								buffer.append(stationName).append(" ");
							}
							buffer.append("\r\n");
						}
					}
				}
				String range = buffer.toString();
				HSSFCell cell = row.createCell(k++);
				cell.setCellValue(new HSSFRichTextString(range));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(wrapStyle);
				// ���Ϊͬ�ϵ�Ʒ�� �ϲ���Ԫ��
				if (goodsid.equals(equYcheckDet.getGoodsid())) {
					ExcelUtil.mergeCell(sheet, row.getRowNum() - 1, row.getRowNum(), 6, 6);
					ExcelUtil.mergeCell(sheet, row.getRowNum() - 1, row.getRowNum(), 7, 7);
				}
				goodsid = equYcheckDet.getGoodsid();
			}
		}

		sheet.setColumnWidth(7, 11000);
		// �Ƿ���ʾҳ��ǩ��
		if (footer) {
			CellStyle footerStyle = workbook.createCellStyle();
			footerStyle.setAlignment(CellStyle.ALIGN_CENTER);
			footerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			footerStyle.setWrapText(true);
			footerStyle.setAlignment(CellStyle.ALIGN_CENTER);
			Font fontFooter = workbook.createFont();
			fontFooter.setBoldweight(Font.BOLDWEIGHT_BOLD);
			fontFooter.setFontHeight((short) 250);
			footerStyle.setFont(fontFooter);
			HSSFRow row = ExcelUtil.createRow(sheet, j++, -1);
			String left = "��             ·           ��\r\n\r\n\r\n                         �����\r\n\r\n\r\n\r\n"
					+ "            ��    ��    ��";
			String right = "��             ��           ��\r\n\r\n\r\n                         �����\r\n\r\n\r\n\r\n"
					+ "            ��    ��    ��";
			ExcelUtil.createCell(row, 0, footerStyle, left);
			ExcelUtil.mergeCell(sheet, row.getRowNum(), row.getRowNum() + 10, 0, 3);
			ExcelUtil.createCell(row, 4, footerStyle, right);
			ExcelUtil.mergeCell(sheet, row.getRowNum(), row.getRowNum() + 10, 4, 7);
		}
	}

	public void exportToExcel(EquYcheck equYcheck, List<EquYcheckDet> equYcheckDetList, HSSFWorkbook workbook,
			int doctype) {
		String yinspectionno = equYcheck.getYinspectionno();
		String footer = equYcheck.getFooter();
		List<EquYcheckBureau> equYcheckBureauList = equYcheckBureauDao.findEquYcheckBureau(yinspectionno);

		Object[] o = buildBureau(equYcheckBureauList);
		Map<String, List<EquYcheckBureau>> equYcheckBureauListByGoodsidMap = (Map<String, List<EquYcheckBureau>>) o[0];
		Map<String, List<String>> loadstationsByGoodsIdAndBureauidMap = (Map<String, List<String>>) o[1];
		Map<String, List<String>> unloadstationsByGoodsIdAndBureauidMap = (Map<String, List<String>>) o[2];
		if (doctype != 0) {
			// ��ҳ ���14��
			int pageSize = null == equYcheckDetList || equYcheckDetList.isEmpty() ? 0
					: (equYcheckDetList.size() > 14 ? 14 : equYcheckDetList.size());
			writePositive(workbook, equYcheckDetList, equYcheck, "��ҳ", pageSize, equYcheckBureauListByGoodsidMap,
					loadstationsByGoodsIdAndBureauidMap, unloadstationsByGoodsIdAndBureauidMap, footer == null
							|| footer.equals("") ? false : true);
		}
		// ��ҳ ȫ����¼
		writePositive(workbook, equYcheckDetList, equYcheck, "��ҳ", equYcheckDetList.size(),
				equYcheckBureauListByGoodsidMap, loadstationsByGoodsIdAndBureauidMap,
				unloadstationsByGoodsIdAndBureauidMap, footer == null || footer.equals("") ? false
						: footer.equals("1") ? false : true);
	}

	public HWPFDocument down(EquYcheck equYcheck) throws Exception {
		ServletContext context = ServletActionContext.getServletContext();
		String filerealpath = context.getRealPath("/equ/template/" + equYcheck.getCorpid() + ".doc");
		File file = new File(filerealpath);
		FileInputStream fis = new FileInputStream(file);
		// WordExtractor doc = new WordExtractor(fis);
		HWPFDocument document = new HWPFDocument(fis);
		// ����
		Range bodyRange = document.getRange();// /.getDocumentText();
		String yinspectionno = equYcheck.getYinspectionno();
		if (log.isDebugEnabled()) {
			log.debug("yinspectionno : " + yinspectionno);
		}
		EquYcheckDet equYcheckDet = new EquYcheckDet();
		equYcheckDet.setYinspectionno(yinspectionno);
		List<EquYcheckDet> equYcheckDets = equYcheckDetDao.findEquYcheckDet(equYcheckDet);

		Map<String, DicCarkind> dicCarkindMap = dicMap.getCarkindMap();
		String carsDes = getCarsDes(equYcheckDets, dicCarkindMap);
		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("YEAR", equYcheck.getYear());
		replaceMap.put("AMOUNT", String.valueOf(null == equYcheckDets ? 0 : equYcheckDets.size()));
		replaceMap.put("AMONG", carsDes);
		// Date contractdate = equYcheck.getContractdate();
		// if (null != contractdate) {
		// replaceMap.put("DATE", CommUtils.format(equYcheck.getContractdate(),
		// "yyyy��MM��dd��"));
		// }
		replaceMap.put("CONTRACTNO", equYcheck.getContractno());
		replaceText(bodyRange, replaceMap);
		if (log.isDebugEnabled()) {
			log.debug("body : " + document.getDocumentText());
		}
		return document;
	}

	public Object[] buildBureau(List<EquYcheckBureau> equYcheckBureauList) {
		Map<String, List<EquYcheckBureau>> equYcheckBureauListByGoodsidMap = null;
		Map<String, List<String>> loadstationsByGoodsIdAndBureauidMap = null;
		Map<String, List<String>> unloadstationsByGoodsIdAndBureauidMap = null;
		equYcheckBureauListByGoodsidMap = new HashMap<String, List<EquYcheckBureau>>();
		loadstationsByGoodsIdAndBureauidMap = new HashMap<String, List<String>>();
		unloadstationsByGoodsIdAndBureauidMap = new HashMap<String, List<String>>();
		if (null != equYcheckBureauList && !equYcheckBureauList.isEmpty()) {
			for (EquYcheckBureau equYcheckBureau : equYcheckBureauList) {
				String goodsid = equYcheckBureau.getGoodsid();
				List<EquYcheckBureau> bureauList = equYcheckBureauListByGoodsidMap.get(goodsid);
				if (null == bureauList) {
					bureauList = new ArrayList<EquYcheckBureau>();
				}
				bureauList.add(equYcheckBureau);
				equYcheckBureauListByGoodsidMap.put(goodsid, bureauList);
				String yinspectionbureauid = CommUtils.getString(equYcheckBureau.getYinspectionno(), "-",
						equYcheckBureau.getGoodsid(), "-", equYcheckBureau.getBureauid());
				List<EquYcheckStation> equYcheckStations = getEquYcheckStation(yinspectionbureauid);
				if (null != equYcheckStations && !equYcheckStations.isEmpty()) {
					for (EquYcheckStation equYcheckStation : equYcheckStations) {
						List<String> loadstationids = loadstationsByGoodsIdAndBureauidMap.get(equYcheckStation
								.getYinspectionbureauid() + "-L");
						List<String> unloadstationids = unloadstationsByGoodsIdAndBureauidMap.get(equYcheckStation
								.getYinspectionbureauid() + "-U");
						if (null == loadstationids) {
							loadstationids = new ArrayList<String>();
						}
						if (null == unloadstationids) {
							unloadstationids = new ArrayList<String>();
						}
						String datatype = equYcheckStation.getDatatype();
						if (datatype.equals("L")) {
							loadstationids.add(equYcheckStation.getStationid());
							loadstationsByGoodsIdAndBureauidMap.put(equYcheckStation.getYinspectionbureauid() + "-L",
									loadstationids);
						} else if (datatype.equals("U")) {
							unloadstationids.add(equYcheckStation.getStationid());
							unloadstationsByGoodsIdAndBureauidMap.put(equYcheckStation.getYinspectionbureauid() + "-U",
									unloadstationids);
						}
					}
				}
			}
		}
		Object[] arry = new Object[3];
		arry[0] = equYcheckBureauListByGoodsidMap;
		arry[1] = loadstationsByGoodsIdAndBureauidMap;
		arry[2] = unloadstationsByGoodsIdAndBureauidMap;
		return arry;
	}

	private void replaceText(Range bodyRange, Map<String, String> replaceMap) {
		if (null == replaceMap || replaceMap.isEmpty()) {
			// return docText;
			return;
		}
		Set<String> keySet = replaceMap.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			// docText = docText.replaceAll(key, replaceMap.get(key));
			bodyRange.replaceText(key, replaceMap.get(key));
		}
		// return docText;
	}

	private String getCarsDes(List<EquYcheckDet> equYcheckDetList, Map<String, DicCarkind> dicCarkindMap) {
		// ����Ʒ���ȡ���������У��޳�...��)
		Map<String, List<String>> carsByCarkindIdMap = new HashMap<String, List<String>>();
		for (int i = 0; i < equYcheckDetList.size(); i++) {
			EquYcheckDet equYcheckDet = equYcheckDetList.get(i);
			String carno = equYcheckDet.getCarno();
			String carkindid = equYcheckDet.getCarkindid();
			List<String> carnoList = carsByCarkindIdMap.get(carkindid);
			if (null == carnoList) {
				carnoList = new ArrayList<String>();
			}
			carnoList.add(carno);
			carsByCarkindIdMap.put(carkindid, carnoList);
		}
		StringBuffer carsDes = new StringBuffer();
		if (null != carsByCarkindIdMap && !carsByCarkindIdMap.isEmpty()) {
			Set<String> carKindSet = carsByCarkindIdMap.keySet();
			Iterator<String> iter = carKindSet.iterator();
			while (iter.hasNext()) {
				String carkind = iter.next();
				DicCarkind dicCarkind = dicCarkindMap.get(carkind);
				carsDes.append((null == dicCarkind ? "" : dicCarkind.getShortname()));
				List<String> cars = carsByCarkindIdMap.get(carkind);
				carsDes.append((null == cars ? 0 : cars.size())).append("��").append(" ");
			}
		}
		return carsDes.toString();
	}

	public void exportHazardous(HSSFWorkbook workbook, EquYcheck equYcheck, List<EquYcheckDet> equYcheckDetList) {
		String year = equYcheck.getYear();
		String corpid = equYcheck.getCorpid();
		String fullname = equYcheck.getCorpfullname();
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, year + "��" + fullname + " Σ�������");
		// �������� ͷ��
		Font fontHeader = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		// ���е�Ԫ����ʽ
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(fontHeader);

		CellStyle styleTitle = workbook.createCellStyle();
		styleTitle.setAlignment(CellStyle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		HSSFFont fontTitle = workbook.createFont();
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styleTitle.setFont(fontTitle);
		int j = 0;
		// ͷ���� �����
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, j++, 800);
		ExcelUtil.createCell(rowHeader, 0, styleTitle, sheet.getSheetName());
		// �ϲ���Ԫ��
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 22);

		String[] titles = { "���", "�ϸ�֤��", "����վ", "����վ��", "��λ", "����֤��", "����֤��", "����", "����", "����", "Ѻ�� ��", "װ�˻���",
				"����(T)", "�������(T)", "��Ч�ݻ�(M3)", "�춨֤��(��Ч����)", "ѹ�����֤(��Ч����)", "���쵥λ", "��������", "�������ռ�¼������ʱ��)",
				"�������޺ϸ�֤��(��������)" };
		HSSFRow rowTitle = ExcelUtil.createRow(sheet, j++, 800);
		int m = 0;
		for (int i = 0; i < titles.length; i++) {
			ExcelUtil.createCell(rowTitle, m++, styleTitle, titles[i]);
		}

		List<Object[]> equRepRecords = equRepRecordDao.findRepRecord(corpid, year);
		Map<String, Date> finishdateByCarnoMap = new HashMap<String, Date>();
		if (null != equRepRecords && !equRepRecords.isEmpty()) {
			for (Object[] objects : equRepRecords) {
				String carno = objects[1].toString();
				// finishdateByCarnoMap
				String dateStr = objects[2].toString();
				Date finishdate = null;
				if (null != dateStr && !"".equals(dateStr)) {
					finishdate = CommUtils.parseDate(dateStr, "yyyy-MM-dd");
				}
				if (null != finishdate) {
					Date vDate = finishdateByCarnoMap.get(carno);
					if (vDate == null) {
						finishdateByCarnoMap.put(carno, finishdate);
					} else {
						if (vDate.after(finishdate)) {
							finishdateByCarnoMap.put(carno, vDate);
						}
					}
				}
			}
		}

		if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
			List<String> carnoList = new ArrayList<String>();
			for (EquYcheckDet equYcheckDet : equYcheckDetList) {
				if (!carnoList.contains(equYcheckDet.getCarno())) {
					carnoList.add(equYcheckDet.getCarno());
				}
			}
			List<EquCar> equCarList = equCarService.findEquCar(carnoList);

			Map<String, EquCar> equCarMap = new HashMap<String, EquCar>();
			if (null != equCarList && !equCarList.isEmpty()) {
				for (EquCar equCar : equCarList) {
					equCarMap.put(equCar.getCarno(), equCar);
				}
			}
			Map<String, String> dicGoodsMap = dicMap.getGoodscategoryMap();
			Map<String, String> carmakerMap = dicMap.getCarmakerMap();
			for (int i = 0; i < equYcheckDetList.size(); i++) {
				m = 0;
				EquYcheckDet equYcheckDet = equYcheckDetList.get(i);
				String carno = equYcheckDet.getCarno();
				EquCar equCar = equCarMap.get(carno);
				HSSFRow row = ExcelUtil.createRow(sheet, j++, 400);

				ExcelUtil.createCell(row, m++, null, i + 1);
				// �ϸ�֤��
				ExcelUtil.createCell(row, m++, null, equYcheckDet.getSaftcertcode());
				// ����վ
				ExcelUtil.createCell(row, m++, null, null == equCar ? "" : equCar.getStnshrinkname());
				// ����վ��
				ExcelUtil.createCell(row, m++, null, equYcheckDet.getStationdepot());
				// ��λ
				ExcelUtil.createCell(row, m++, null, fullname);
				// ����֤��(Ŀǰδ֪)
				ExcelUtil.createCell(row, m++, null, equYcheckDet.getTrancertcode());
				// ����֤��
				ExcelUtil.createCell(row, m++, null, "");

				// ����
				ExcelUtil.createCell(row, m++, null, equYcheckDet.getCarkindid());
				// ����
				ExcelUtil.createCell(row, m++, null, equYcheckDet.getCartypeid());
				// ����
				ExcelUtil.createCell(row, m++, null, equYcheckDet.getCarno());

				// Ѻ�˼�(��ѹ������Ѻ�˼䣬���峵)
				String escort = "";
				if (null != equCar) {
					String pressuretype = equCar.getPressuretype();
					if (null != pressuretype) {
						if ("NP".equals(pressuretype.toUpperCase())) {
							escort = "��";
						} else {
							escort = "��";
						}
					}
				}
				ExcelUtil.createCell(row, m++, null, escort);
				// װ�˻���
				ExcelUtil.createCell(row, m++, null, dicGoodsMap.get(equYcheckDet.getGoodsid()));
				// ����
				ExcelUtil.createCell(row, m++, null, equCar == null ? "" : String.valueOf(equCar.getLightweight()));
				// �������
				ExcelUtil.createCell(row, m++, null, equCar == null ? "" : String.valueOf(equCar.getMarkloadweight()));

				// ��Ч�ݻ�
				ExcelUtil.createCell(row, m++, null, equCar == null ? "" : String.valueOf(equCar.getCapacity()));

				// �춨֤��(��Ч����)
				ExcelUtil.createCell(row, m++, null, "");

				// ѹ�����֤(��Ч����,Ŀǰû������)
				ExcelUtil.createCell(row, m++, null, "");
				// ���쵥λ
				ExcelUtil.createCell(row, m++, null, equCar == null ? "" : carmakerMap.get(equCar.getCarmakerid()));
				// ��������
				ExcelUtil.createCell(row, m++, null, equCar == null ? "" : equCar.getMadedate());
				// �������ռ�¼(����ʱ��)
				Date date = finishdateByCarnoMap.get(carno);
				ExcelUtil.createCell(row, m++, null, null == date ? "" : CommUtils.format(date, "yyyyMMdd"));
				// �������޺ϸ�֤��(��������)
				ExcelUtil.createCell(row, m++, null, null == date ? "" : CommUtils.format(date, "yyyyMMdd"));

			}
		}

	}

}
