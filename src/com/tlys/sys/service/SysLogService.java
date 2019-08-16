package com.tlys.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.dao.SysBkerrorlogDao;
import com.tlys.sys.dao.SysFraccessLogDao;
import com.tlys.sys.dao.SysFraccessLogOprDao;
import com.tlys.sys.dao.SysSessionDao;
import com.tlys.sys.model.SysBkerrorlog;
import com.tlys.sys.model.SysFraccessLog;
import com.tlys.sys.model.SysFraccessLogOpr;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysSession;
import com.tlys.sys.model.SysUser;

@Service("logService")
@Qualifier("logService")
public class SysLogService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	SysBkerrorlogDao bkerrorlogDao;
	@Autowired
	SysSessionDao sessionDao;
	@Autowired
	SysFraccessLogDao fraccessLogDao;
	@Autowired
	SysFraccessLogOprDao fraccessLogOprDao;
	@Autowired
	CacheService cacheService;
	@Autowired
	SysMenuService menuService;

	public void save(SysBkerrorlog bkerrorlog) {
		bkerrorlogDao.save(bkerrorlog);
	}

	/**
	 * ���»Ự�����еĲ������ڹر����������ʱ������ԭ����sessionidΪ��ǰsessionid
	 * 
	 * @param fromSessionID
	 * @param toSessionID
	 */
	public void updateSysFraccessLogBySessionID(String fromSessionID, String toSessionID) {
		fraccessLogDao.updateSysFraccessLogBySessionID(fromSessionID, toSessionID);
	}

	public void save(SysSession sysSession) {
		if (null == sysSession) {
			sysSession = new SysSession();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		String jsessionID = session.getId();
		Cookie cookie = new Cookie("JSESSIONID_USER", jsessionID);
		cookie.setMaxAge(60 * 60 * 24 * 365);
		cookie.setPath("/");
		cookie.setValue(jsessionID);
		response.addCookie(cookie);
		String ccname = request.getRemoteHost();
		String cip = CommUtils.getRemoteIP(request);
		String agent = request.getHeader("user-agent");
		String browser = request.getHeader("user-agent");
		String sessionid = session.getId();
		SysUser sysUser = (SysUser) session.getAttribute("sysUserSess");
		String userid = sysUser == null ? "anonym" : sysUser.getUserid();
		if (logger.isDebugEnabled()) {
			logger.debug("ccname : " + ccname);
			logger.debug("cip : " + cip);
			logger.debug("agent : " + agent);
			logger.debug("browser : " + browser);
			logger.debug("sessionid : " + sessionid);
			logger.debug("userid : " + userid);
		}
		sysSession.setCcname(ccname);
		sysSession.setCip(cip);
		StringTokenizer st = new StringTokenizer(agent, "");
		// st.nextToken();
		// �ͻ��������
		sysSession.setCbrower(browser);
		// �ͻ��˲���ϵͳ
		Cookie[] cookieCos = request.getCookies();

		// String cos = st.nextToken();

		String cos = "";

		for (int i = 0; i < cookieCos.length; i++) {
			Cookie cook = cookieCos[i];
			if (cook.getName().equals("deteOS")) {
				cos = cook.getValue();
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cos : " + cos);
		}
		sysSession.setCos(cos);
		// ��������
		sysSession.setQuarter(CommUtils.formatQuarter(new Date()));
		sysSession.setSessionid(sessionid);
		sysSession.setStarttime(new Date());
		sysSession.setUserid(userid);
		sysSession.setSessionstatus("A");
		sysSession.setEndtime(null);
		sessionDao.save(sysSession);
	}

	/**
	 * ��ȡ������־�б�
	 * 
	 * @param off
	 * @param max
	 * @return
	 */
	public void findBkErrorLog(SysBkerrorlog sysBkerrorlog, PageCtr<SysBkerrorlog> pageCtr) {
		int totalRecord = getSysBkErrorLogCount(sysBkerrorlog);
		pageCtr.setTotalRecord(totalRecord);
		bkerrorlogDao.findBkErrorLog(sysBkerrorlog, pageCtr);
	}

	/**
	 * ��ѯ���з��ϸ�ʱ��εĴ�����־��¼,����Excelʱ�õ�
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<SysBkerrorlog> findBkErrorLog(String startTime, String endTime) {
		return bkerrorlogDao.findBkErrorLog(startTime, endTime);
	}

	/**
	 * ��ѯ���з��ϸ�ʱ��εĻỰ��־��¼,����Excelʱ�õ�
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<SysSession> findSysSession(String startTime, String endTime) {
		return sessionDao.findSysSession(startTime, endTime);
	}

	public List<SysSession> findSysSession(List<String> sessionIdList) {
		return sessionDao.findSysSession(sessionIdList);
	}

	/**
	 * �������ڲ�ѯ��¼������������־��¼)
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int getSysBkErrorLogCount(SysBkerrorlog sysBkerrorlog) {
		String key = CommUtils.getString("SYS_BK_ERROR_LOG", sysBkerrorlog.getStartTime(), sysBkerrorlog.getEndTime(),
				sysBkerrorlog.getObjtype());
		Integer count = (Integer) cacheService.get(key);
		if (null == count) {
			count = bkerrorlogDao.getSysBkErrorLogCount(sysBkerrorlog);
			cacheService.put(key, count);
		}
		return count;
	}

	/**
	 * ��ҳ��ѯ�Ự��־
	 * 
	 * @param pageView
	 * @param sysSession
	 * @return
	 */
	public void findSysSession(SysSession sysSession, PageCtr<SysSession> pageCtr) {
		int totalRecord = getSysSessionCount(sysSession);
		pageCtr.setTotalRecord(totalRecord);
		sessionDao.findSysSession(sysSession, pageCtr);
	}

	/**
	 * ��ѯ���������Ự�ܼ�¼�������浽tlysCache�У�Ĭ��1���Ӻ���ʧ���»�ȡ
	 * 
	 * @param sysSession
	 * @return
	 */
	public int getSysSessionCount(SysSession sysSession) {
		String startTime = sysSession.getStartDateStr();
		String endTime = sysSession.getEndDateStr();
		String key = CommUtils.getString("SYS_SESSION_LOG", startTime, endTime);
		Integer count = (Integer) cacheService.get(key);
		if (null == count) {
			count = sessionDao.getSysSessionCount(sysSession);
			cacheService.put(key, count);
		}
		return count;
	}

	public void saveOrUpdate(SysSession sysSession) {
		sessionDao.saveOrUpdate(sysSession);
	}

	/**
	 * ����sessionID��ȡ�Ự��¼�����ݿ�)
	 * 
	 * @param sessionID
	 * @return
	 */
	public SysSession getSysSession(String sessionID) {
		return sessionDao.getSysSession(sessionID);
	}

	/**
	 * ����Excel����(������־)
	 * 
	 * @param logList
	 */
	public void exportSysBkErrorLogExcel(HSSFWorkbook workbook, List<SysBkerrorlog> logList) {
		if (null == logList || logList.isEmpty()) {
			return;
		}
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "������־");
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);
		Font fontTitle = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, fontTitle);
		CellRangeAddress rageAddress = new CellRangeAddress(0, 0, 0, 3);
		sheet.addMergedRegion(rageAddress);
		ExcelUtil.createCell(rowHeader, 0, style, "������־��¼");
		Font fontDescription = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
				(short) 10);
		CellStyle styleDescription = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_LEFT, fontDescription);
		HSSFRow rowTitle = ExcelUtil.createRow(sheet, 1, 500);
		ExcelUtil.createCell(rowTitle, 0, styleDescription, "��������");
		ExcelUtil.createCell(rowTitle, 1, styleDescription, "��������");
		ExcelUtil.createCell(rowTitle, 2, styleDescription, "ʱ��");
		ExcelUtil.createCell(rowTitle, 3, styleDescription, "����");
		for (int i = 0; i < logList.size(); i++) {
			SysBkerrorlog sysBkerrorlog = logList.get(i);
			HSSFRow row = ExcelUtil.createRow(sheet, i + 2, -1);
			ExcelUtil.createCell(row, 0, styleDescription, sysBkerrorlog.getObjname());
			ExcelUtil.createCell(row, 1, styleDescription, sysBkerrorlog.getObjtype());
			ExcelUtil.createCell(row, 2, styleDescription, sysBkerrorlog.getRectime().toString());
			// ������
			ExcelUtil.createCell(row, 3, styleDescription, sysBkerrorlog.getDescription());
		}
		return;
	}

	/**
	 * ����Excel����(������־)
	 * 
	 * @param logList
	 */
	public void exportSysSessionExcel(HSSFWorkbook workbook, List<SysSession> sysSessionList,
			List<SysFraccessLog> fraccessLogList, List<SysFraccessLogOpr> fraccessLogOprs) {
		// if (null == sysSessionList || sysSessionList.isEmpty()) {
		// return;
		// }
		// ��fraccessLogList�����ݻỰ���б��ȡ��)��Map��ֿ��� ����sessionID��ȡSysFraccessLog���б�
		Map<String, List<SysFraccessLog>> fraccessLogsBySessionIdMap = getFraccessLogsBySessionIdMap(fraccessLogList);
		// ���ݻỰ�����Ĳ˵������¼��ȡ������¼ Map�洢����Ϊ�˵������logId ֵΪ��ť�������б�
		Map<Long, List<SysFraccessLogOpr>> fraccessLogOprsByLogIdMap = getFraccessLogOprsByLogIdMap(fraccessLogOprs);
		Map<Integer, SysNavimenu> navimenuByIdMap = menuService.findSysMenuMap();// .findSysNavimenuByIdMap();
		// ����sheet
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "�Ự��־");
		// ����������
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);
		// ��������
		Font fontTitle = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		// ���е�Ԫ����ʽ
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, fontTitle);
		// �ϲ���Ԫ������10��
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 9);
		// ������Ԫ������
		ExcelUtil.createCell(rowHeader, 0, style, "�Ự��־��¼");
		// �б�����
		// Font fontDescription = ExcelUtil.createFont(workbook,
		// HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
		// (short) 10);
		// �б���ʽ
		// CellStyle styleDescription =
		// ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index,
		// HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_LEFT, fontDescription);

		CellStyle styleDescription = workbook.createCellStyle();
		styleDescription.setAlignment(CellStyle.ALIGN_LEFT);
		styleDescription.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleDescription.setBorderBottom(CellStyle.BORDER_THIN);
		styleDescription.setBorderLeft(CellStyle.BORDER_THIN);
		styleDescription.setBorderTop(CellStyle.BORDER_THIN);
		styleDescription.setBorderRight(CellStyle.BORDER_THIN);

		HSSFRow rowTitle = ExcelUtil.createRow(sheet, 1, 500);
		ExcelUtil.createCell(rowTitle, 0, styleDescription, "����");
		ExcelUtil.createCell(rowTitle, 1, styleDescription, "�ỰID");
		ExcelUtil.createCell(rowTitle, 2, styleDescription, "��ʼʱ��");
		ExcelUtil.createCell(rowTitle, 3, styleDescription, "����ʱ��");
		ExcelUtil.createCell(rowTitle, 4, styleDescription, "�û�ID");
		ExcelUtil.createCell(rowTitle, 5, styleDescription, "�ͻ���IP");
		ExcelUtil.createCell(rowTitle, 6, styleDescription, "�ͻ�������");
		ExcelUtil.createCell(rowTitle, 7, styleDescription, "�ͻ��������");
		ExcelUtil.createCell(rowTitle, 8, styleDescription, "����ϵͳ");
		ExcelUtil.createCell(rowTitle, 9, styleDescription, "�Ự״̬");
		ExcelUtil.createCell(rowTitle, 10, styleDescription, "�˵�");
		ExcelUtil.createCell(rowTitle, 11, styleDescription, "���ݿ����");
		ExcelUtil.createCell(rowTitle, 12, styleDescription, "����ID");
		ExcelUtil.createCell(rowTitle, 13, styleDescription, "��������");
		int rowNum = 2;
		for (int i = 0; i < sysSessionList.size(); i++) {
			SysSession sysSession = sysSessionList.get(i);
			String sessionid = sysSession.getSessionid();
			List<SysFraccessLog> fraccessLogs = fraccessLogsBySessionIdMap.get(sessionid);
			int logSize = (null == fraccessLogs ? 0 : fraccessLogs.size());
			HSSFRow row = ExcelUtil.createRow(sheet, rowNum, -1);
			// ��ǰ�к�
			int rowNumCursor = row.getRowNum();

			for (int j = 0; j < logSize; j++) {
				SysFraccessLog sysFraccessLog = fraccessLogs.get(j);
				Long logId = sysFraccessLog.getId();
				List<SysFraccessLogOpr> oprList = (null == fraccessLogOprsByLogIdMap ? null : fraccessLogOprsByLogIdMap
						.get(logId));
				int size = (null == oprList ? 0 : oprList.size());
				HSSFRow rowFraccessLog = null;
				if (j > 0) {
					rowNum++;
				}
				if (0 == j) {
					rowFraccessLog = row;
				} else {
					rowFraccessLog = ExcelUtil.createRow(sheet, rowNum, -1);
				}
				int fromRowNum = rowFraccessLog.getRowNum();
				int toRowNum = fromRowNum + (0 == size ? 0 : size - 1);

				for (int k = 0; k < size; k++) {
					SysFraccessLogOpr fraccessLogOpr = oprList.get(k);
					if (k > 0) {
						rowNum++;
					}
					HSSFRow rowOpr = ExcelUtil.createRow(sheet, rowNum, -1);
					ExcelUtil.createCell(rowOpr, 11, styleDescription, fraccessLogOpr.getDbtname());
					ExcelUtil.createCell(rowOpr, 12, styleDescription, fraccessLogOpr.getOperid());
					ExcelUtil.createCell(rowOpr, 13, styleDescription, fraccessLogOpr.getOpercode());
				}
				String menuid = sysFraccessLog.getMenuid();
				SysNavimenu sysNavimenu = navimenuByIdMap.get(Integer.parseInt(menuid));
				CellRangeAddress cellRangeMenu = new CellRangeAddress(fromRowNum, toRowNum, 10, 10);
				ExcelUtil.setRegionStyle(sheet, cellRangeMenu, styleDescription);
				sheet.addMergedRegion(cellRangeMenu);
				ExcelUtil.createCell(rowFraccessLog, 10, styleDescription,
						(null == sysNavimenu ? "δ֪�˵�" : sysNavimenu.getMenuname()));

			}

			// -------------���ⲿ�ַ��ں���Ϊ�˷�ֹ����ʾ�˵�������¼�����е�ʱ�򣬽����ϲ���Ԫ��Ĳ��ֵ����ݳ��-------

			CellRangeAddress cellRangeJd = new CellRangeAddress(rowNumCursor, rowNum, 0, 0);
			ExcelUtil.setRegionStyle(sheet, cellRangeJd, styleDescription);
			sheet.addMergedRegion(cellRangeJd);

			CellRangeAddress cellRangeSessionId = new CellRangeAddress(rowNumCursor, rowNum, 1, 1);
			ExcelUtil.setRegionStyle(sheet, cellRangeSessionId, styleDescription);
			sheet.addMergedRegion(cellRangeSessionId);

			CellRangeAddress cellRangeStartTime = new CellRangeAddress(rowNumCursor, rowNum, 2, 2);
			ExcelUtil.setRegionStyle(sheet, cellRangeStartTime, styleDescription);
			sheet.addMergedRegion(cellRangeStartTime);

			CellRangeAddress cellRangeEndTime = new CellRangeAddress(rowNumCursor, rowNum, 3, 3);
			ExcelUtil.setRegionStyle(sheet, cellRangeEndTime, styleDescription);
			sheet.addMergedRegion(cellRangeEndTime);

			CellRangeAddress cellRangeUser = new CellRangeAddress(rowNumCursor, rowNum, 4, 4);
			ExcelUtil.setRegionStyle(sheet, cellRangeUser, styleDescription);
			sheet.addMergedRegion(cellRangeUser);

			CellRangeAddress cellRangeCip = new CellRangeAddress(rowNumCursor, rowNum, 5, 5);
			ExcelUtil.setRegionStyle(sheet, cellRangeCip, styleDescription);
			sheet.addMergedRegion(cellRangeCip);

			CellRangeAddress cellRangeCcname = new CellRangeAddress(rowNumCursor, rowNum, 6, 6);
			ExcelUtil.setRegionStyle(sheet, cellRangeCcname, styleDescription);
			sheet.addMergedRegion(cellRangeCcname);

			CellRangeAddress cellRangeCbrowser = new CellRangeAddress(rowNumCursor, rowNum, 7, 7);
			ExcelUtil.setRegionStyle(sheet, cellRangeCbrowser, styleDescription);
			sheet.addMergedRegion(cellRangeCbrowser);

			CellRangeAddress cellRangeCos = new CellRangeAddress(rowNumCursor, rowNum, 8, 8);
			ExcelUtil.setRegionStyle(sheet, cellRangeCos, styleDescription);
			sheet.addMergedRegion(cellRangeCos);

			CellRangeAddress cellRangeStatus = new CellRangeAddress(rowNumCursor, rowNum, 9, 9);
			ExcelUtil.setRegionStyle(sheet, cellRangeStatus, styleDescription);
			sheet.addMergedRegion(cellRangeStatus);

			// ���ȣ����ݴ洢��
			ExcelUtil.createCell(row, 0, styleDescription, sysSession.getQuarter()).setCellStyle(styleDescription);
			// sessionid
			ExcelUtil.createCell(row, 1, styleDescription, sysSession.getSessionid());
			// �Ự��ʼʱ��
			ExcelUtil.createCell(row, 2, styleDescription, sysSession.getStarttime().toString());
			// �Ự����ʱ��
			ExcelUtil.createCell(row, 3, styleDescription, sysSession.getEndtime() == null ? "" : sysSession
					.getEndtime().toString());
			// �Ự�û�id
			ExcelUtil.createCell(row, 4, styleDescription, sysSession.getUserid());
			// �ͻ����û�ip��ַ
			ExcelUtil.createCell(row, 5, styleDescription, sysSession.getCip());
			// �ͻ����û�����
			ExcelUtil.createCell(row, 6, styleDescription, sysSession.getCcname());
			// �ͻ��������
			ExcelUtil.createCell(row, 7, styleDescription, sysSession.getCbrower());
			// �ͻ��˲���ϵͳ
			ExcelUtil.createCell(row, 8, styleDescription, sysSession.getCos());
			// �Ự״̬
			ExcelUtil.createCell(row, 9, styleDescription, CommUtils.getSessionStatus(sysSession.getSessionstatus()));

			rowNum++;
		}
		return;
	}

	/**
	 * ��fraccessLogList�����ݻỰ���б��ȡ��)��Map��ֿ��� ����sessionID��ȡSysFraccessLog���б�
	 * 
	 * @param fraccessLogList
	 * @return
	 */
	private Map<String, List<SysFraccessLog>> getFraccessLogsBySessionIdMap(List<SysFraccessLog> fraccessLogList) {
		if (null == fraccessLogList || fraccessLogList.isEmpty()) {
			return null;
		}
		Map<String, List<SysFraccessLog>> fraccessLogsBySessionIdMap = new HashMap<String, List<SysFraccessLog>>();
		for (SysFraccessLog sysFraccessLog : fraccessLogList) {
			String sessionid = sysFraccessLog.getSessionid();
			List<SysFraccessLog> fraccessLogs = fraccessLogsBySessionIdMap.get(sessionid);
			if (null == fraccessLogs) {
				fraccessLogs = new ArrayList<SysFraccessLog>();
			}
			fraccessLogs.add(sysFraccessLog);
			fraccessLogsBySessionIdMap.put(sessionid, fraccessLogs);
		}
		return fraccessLogsBySessionIdMap;
	}

	/**
	 * ���ݻỰ�����Ĳ˵������¼��ȡ������¼ Map�洢����Ϊ�˵������logId ֵΪ��ť�������б�
	 * 
	 * @param fraccessLogList
	 * @return
	 */
	private Map<Long, List<SysFraccessLogOpr>> getFraccessLogOprsByLogIdMap(List<SysFraccessLogOpr> oprList) {
		// List<Long> logIdList = null;
		// if (null != fraccessLogList && !fraccessLogList.isEmpty()) {
		// logIdList = new ArrayList<Long>();
		// for (SysFraccessLog sysFraccessLog : fraccessLogList) {
		// logIdList.add(sysFraccessLog.getId());
		// }
		// }
		// ��ǰ�Ự�����в˵��Ĳ�����¼
		// List<SysFraccessLogOpr> oprList =
		// fraccessLogOprDao.findSysFraccessLogOpr(logIdList);
		if (null == oprList || oprList.isEmpty()) {
			return null;
		}

		Map<Long, List<SysFraccessLogOpr>> fraccessLogOprsByLogIdMap = new HashMap<Long, List<SysFraccessLogOpr>>();
		for (SysFraccessLogOpr sysFraccessLogOpr : oprList) {
			Long logId = sysFraccessLogOpr.getLogid();
			List<SysFraccessLogOpr> fraccessLogOprs = fraccessLogOprsByLogIdMap.get(logId);
			if (null == fraccessLogOprs) {
				fraccessLogOprs = new ArrayList<SysFraccessLogOpr>();
			}
			fraccessLogOprs.add(sysFraccessLogOpr);
			fraccessLogOprsByLogIdMap.put(logId, fraccessLogOprs);
		}
		return fraccessLogOprsByLogIdMap;
	}

	public void save(SysFraccessLog sysFraccessLog) {
		fraccessLogDao.save(sysFraccessLog);
	}

	public void save(SysFraccessLogOpr sysFraccessLogOpr) {
		fraccessLogOprDao.save(sysFraccessLogOpr);
	}

	/**
	 * ����ID��ȡSysBkerrorlog
	 * 
	 * @param bkerrorLogId
	 * @return
	 */
	public SysBkerrorlog getSysBkerrorlogById(long bkerrorLogId) {
		return bkerrorlogDao.load(bkerrorLogId);
	}

	/**
	 * ����ID��ȡSysSession
	 * 
	 * @param sysSessionId
	 * @return
	 */
	public SysSession getSysSessionById(long sysSessionId) {
		return sessionDao.load(sysSessionId);
	}

	/**
	 * ����ʱ���ɾ��������־
	 * 
	 * @param sysSession
	 */
	public void deleteAccessLog(SysSession sysSession) {
		// ɾ���˵�������־
		fraccessLogOprDao.deleteAccessLog(sysSession);
		fraccessLogDao.deleteAccessLog(sysSession);
		// ɾ���Ự��־
		sessionDao.deleteAccessLog(sysSession);
	}

	/**
	 * ����ʱ���ɾ��������־
	 * 
	 * @param sysBkerrorlog
	 */
	public void deleteErrorLog(SysBkerrorlog sysBkerrorlog) {
		bkerrorlogDao.deleteErrorLog(sysBkerrorlog);
	}

	/**
	 * ����sessionID����ǰ�Ự)��ȡ�����б�
	 * 
	 * @param sessionid
	 * @return
	 */
	public List<SysFraccessLog> findSysFraccessLog(String sessionid) {
		return fraccessLogDao.findSysFraccessLog(sessionid);
	}

	/**
	 * ����logid�б��ѯ��Ӧ����
	 * 
	 * @param logIdList
	 * @return
	 */
	public List<SysFraccessLogOpr> findSysFraccessLogOpr(List<Long> logIdList) {
		return fraccessLogOprDao.findSysFraccessLogOpr(logIdList);
	}

	/**
	 * ���ݻỰID���б��ѯ�Ự����
	 * 
	 * @param sessionidList
	 * @return
	 */
	public List<SysFraccessLog> findSysFraccessLog(String startTime, String endTime) {
		return fraccessLogDao.findSysFraccessLog(startTime, endTime);
	}

	public List<SysFraccessLogOpr> findSysFraccessLogOpr(String startTime, String endTime) {
		return fraccessLogOprDao.findSysFraccessLogOpr(startTime, endTime);
	}
}
