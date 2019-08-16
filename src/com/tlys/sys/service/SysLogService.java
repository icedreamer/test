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
	 * 更新会话过程中的操作，在关闭浏览器访问时将更新原来的sessionid为当前sessionid
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
		// 客户端浏览器
		sysSession.setCbrower(browser);
		// 客户端操作系统
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
		// 季度数据
		sysSession.setQuarter(CommUtils.formatQuarter(new Date()));
		sysSession.setSessionid(sessionid);
		sysSession.setStarttime(new Date());
		sysSession.setUserid(userid);
		sysSession.setSessionstatus("A");
		sysSession.setEndtime(null);
		sessionDao.save(sysSession);
	}

	/**
	 * 获取错误日志列表
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
	 * 查询所有符合该时间段的错误日志记录,导出Excel时用到
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<SysBkerrorlog> findBkErrorLog(String startTime, String endTime) {
		return bkerrorlogDao.findBkErrorLog(startTime, endTime);
	}

	/**
	 * 查询所有符合该时间段的会话日志记录,导出Excel时用到
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
	 * 根据日期查询记录总数（错误日志记录)
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
	 * 分页查询会话日志
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
	 * 查询符合条件会话总记录数，缓存到tlysCache中，默认1分钟后消失重新获取
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
	 * 根据sessionID获取会话记录（数据库)
	 * 
	 * @param sessionID
	 * @return
	 */
	public SysSession getSysSession(String sessionID) {
		return sessionDao.getSysSession(sessionID);
	}

	/**
	 * 导出Excel操作(错误日志)
	 * 
	 * @param logList
	 */
	public void exportSysBkErrorLogExcel(HSSFWorkbook workbook, List<SysBkerrorlog> logList) {
		if (null == logList || logList.isEmpty()) {
			return;
		}
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "错误日志");
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);
		Font fontTitle = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, fontTitle);
		CellRangeAddress rageAddress = new CellRangeAddress(0, 0, 0, 3);
		sheet.addMergedRegion(rageAddress);
		ExcelUtil.createCell(rowHeader, 0, style, "错误日志记录");
		Font fontDescription = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
				(short) 10);
		CellStyle styleDescription = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_LEFT, fontDescription);
		HSSFRow rowTitle = ExcelUtil.createRow(sheet, 1, 500);
		ExcelUtil.createCell(rowTitle, 0, styleDescription, "对象名称");
		ExcelUtil.createCell(rowTitle, 1, styleDescription, "对象类型");
		ExcelUtil.createCell(rowTitle, 2, styleDescription, "时间");
		ExcelUtil.createCell(rowTitle, 3, styleDescription, "描述");
		for (int i = 0; i < logList.size(); i++) {
			SysBkerrorlog sysBkerrorlog = logList.get(i);
			HSSFRow row = ExcelUtil.createRow(sheet, i + 2, -1);
			ExcelUtil.createCell(row, 0, styleDescription, sysBkerrorlog.getObjname());
			ExcelUtil.createCell(row, 1, styleDescription, sysBkerrorlog.getObjtype());
			ExcelUtil.createCell(row, 2, styleDescription, sysBkerrorlog.getRectime().toString());
			// 描述列
			ExcelUtil.createCell(row, 3, styleDescription, sysBkerrorlog.getDescription());
		}
		return;
	}

	/**
	 * 导出Excel操作(错误日志)
	 * 
	 * @param logList
	 */
	public void exportSysSessionExcel(HSSFWorkbook workbook, List<SysSession> sysSessionList,
			List<SysFraccessLog> fraccessLogList, List<SysFraccessLogOpr> fraccessLogOprs) {
		// if (null == sysSessionList || sysSessionList.isEmpty()) {
		// return;
		// }
		// 将fraccessLogList（根据会话的列表获取的)用Map拆分开， 根据sessionID获取SysFraccessLog的列表
		Map<String, List<SysFraccessLog>> fraccessLogsBySessionIdMap = getFraccessLogsBySessionIdMap(fraccessLogList);
		// 根据会话产生的菜单浏览记录获取操作记录 Map存储，键为菜单浏览的logId 值为按钮动作的列表
		Map<Long, List<SysFraccessLogOpr>> fraccessLogOprsByLogIdMap = getFraccessLogOprsByLogIdMap(fraccessLogOprs);
		Map<Integer, SysNavimenu> navimenuByIdMap = menuService.findSysMenuMap();// .findSysNavimenuByIdMap();
		// 创建sheet
		HSSFSheet sheet = ExcelUtil.createSheet(workbook, "会话日志");
		// 创建标题行
		HSSFRow rowHeader = ExcelUtil.createRow(sheet, 0, 800);
		// 字体设置
		Font fontTitle = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
		// 首行单元格样式
		CellStyle style = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, fontTitle);
		// 合并单元格至第10列
		ExcelUtil.mergeCell(sheet, 0, 0, 0, 9);
		// 创建单元格内容
		ExcelUtil.createCell(rowHeader, 0, style, "会话日志记录");
		// 列表字体
		// Font fontDescription = ExcelUtil.createFont(workbook,
		// HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
		// (short) 10);
		// 列表样式
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
		ExcelUtil.createCell(rowTitle, 0, styleDescription, "季度");
		ExcelUtil.createCell(rowTitle, 1, styleDescription, "会话ID");
		ExcelUtil.createCell(rowTitle, 2, styleDescription, "开始时间");
		ExcelUtil.createCell(rowTitle, 3, styleDescription, "结束时间");
		ExcelUtil.createCell(rowTitle, 4, styleDescription, "用户ID");
		ExcelUtil.createCell(rowTitle, 5, styleDescription, "客户端IP");
		ExcelUtil.createCell(rowTitle, 6, styleDescription, "客户机名称");
		ExcelUtil.createCell(rowTitle, 7, styleDescription, "客户机浏览器");
		ExcelUtil.createCell(rowTitle, 8, styleDescription, "操作系统");
		ExcelUtil.createCell(rowTitle, 9, styleDescription, "会话状态");
		ExcelUtil.createCell(rowTitle, 10, styleDescription, "菜单");
		ExcelUtil.createCell(rowTitle, 11, styleDescription, "数据库表明");
		ExcelUtil.createCell(rowTitle, 12, styleDescription, "操作ID");
		ExcelUtil.createCell(rowTitle, 13, styleDescription, "操作代码");
		int rowNum = 2;
		for (int i = 0; i < sysSessionList.size(); i++) {
			SysSession sysSession = sysSessionList.get(i);
			String sessionid = sysSession.getSessionid();
			List<SysFraccessLog> fraccessLogs = fraccessLogsBySessionIdMap.get(sessionid);
			int logSize = (null == fraccessLogs ? 0 : fraccessLogs.size());
			HSSFRow row = ExcelUtil.createRow(sheet, rowNum, -1);
			// 当前行号
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
						(null == sysNavimenu ? "未知菜单" : sysNavimenu.getMenuname()));

			}

			// -------------将这部分放在后面为了防止在显示菜单操作记录创建行的时候，将左侧合并单元格的部分的内容冲掉-------

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

			// 季度，数据存储区
			ExcelUtil.createCell(row, 0, styleDescription, sysSession.getQuarter()).setCellStyle(styleDescription);
			// sessionid
			ExcelUtil.createCell(row, 1, styleDescription, sysSession.getSessionid());
			// 会话开始时间
			ExcelUtil.createCell(row, 2, styleDescription, sysSession.getStarttime().toString());
			// 会话结束时间
			ExcelUtil.createCell(row, 3, styleDescription, sysSession.getEndtime() == null ? "" : sysSession
					.getEndtime().toString());
			// 会话用户id
			ExcelUtil.createCell(row, 4, styleDescription, sysSession.getUserid());
			// 客户端用户ip地址
			ExcelUtil.createCell(row, 5, styleDescription, sysSession.getCip());
			// 客户端用户名称
			ExcelUtil.createCell(row, 6, styleDescription, sysSession.getCcname());
			// 客户端浏览器
			ExcelUtil.createCell(row, 7, styleDescription, sysSession.getCbrower());
			// 客户端操作系统
			ExcelUtil.createCell(row, 8, styleDescription, sysSession.getCos());
			// 会话状态
			ExcelUtil.createCell(row, 9, styleDescription, CommUtils.getSessionStatus(sysSession.getSessionstatus()));

			rowNum++;
		}
		return;
	}

	/**
	 * 将fraccessLogList（根据会话的列表获取的)用Map拆分开， 根据sessionID获取SysFraccessLog的列表
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
	 * 根据会话产生的菜单浏览记录获取操作记录 Map存储，键为菜单浏览的logId 值为按钮动作的列表
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
		// 当前会话对所有菜单的操作记录
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
	 * 根据ID获取SysBkerrorlog
	 * 
	 * @param bkerrorLogId
	 * @return
	 */
	public SysBkerrorlog getSysBkerrorlogById(long bkerrorLogId) {
		return bkerrorlogDao.load(bkerrorLogId);
	}

	/**
	 * 根据ID获取SysSession
	 * 
	 * @param sysSessionId
	 * @return
	 */
	public SysSession getSysSessionById(long sysSessionId) {
		return sessionDao.load(sysSessionId);
	}

	/**
	 * 根据时间段删除访问日志
	 * 
	 * @param sysSession
	 */
	public void deleteAccessLog(SysSession sysSession) {
		// 删除菜单操作日志
		fraccessLogOprDao.deleteAccessLog(sysSession);
		fraccessLogDao.deleteAccessLog(sysSession);
		// 删除会话日志
		sessionDao.deleteAccessLog(sysSession);
	}

	/**
	 * 根据时间段删除错误日志
	 * 
	 * @param sysBkerrorlog
	 */
	public void deleteErrorLog(SysBkerrorlog sysBkerrorlog) {
		bkerrorlogDao.deleteErrorLog(sysBkerrorlog);
	}

	/**
	 * 根据sessionID（当前会话)获取操作列表
	 * 
	 * @param sessionid
	 * @return
	 */
	public List<SysFraccessLog> findSysFraccessLog(String sessionid) {
		return fraccessLogDao.findSysFraccessLog(sessionid);
	}

	/**
	 * 根据logid列表查询对应操作
	 * 
	 * @param logIdList
	 * @return
	 */
	public List<SysFraccessLogOpr> findSysFraccessLogOpr(List<Long> logIdList) {
		return fraccessLogOprDao.findSysFraccessLogOpr(logIdList);
	}

	/**
	 * 根据会话ID的列表查询会话操作
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
