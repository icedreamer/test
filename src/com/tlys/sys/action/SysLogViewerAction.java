package com.tlys.sys.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysBkerrorlog;
import com.tlys.sys.model.SysFraccessLog;
import com.tlys.sys.model.SysFraccessLogOpr;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysOprationtype;
import com.tlys.sys.model.SysSession;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysLogService;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysUserService;

/**
 * ��־�鿴������
 * 
 * @author CCSONG
 * 
 */
@Controller
@ParentPackage("tlys-default")
// by struts2������action��namespace
@Namespace("/sys")
public class SysLogViewerAction extends _BaseAction {

	private Log logger = LogFactory.getLog(this.getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -6366922101347248132L;
	@Autowired
	SysLogService logService;
	@Autowired
	SysUserService userService;
	@Autowired
	SysMenuService menuService;
	/**
	 * ������־�б�
	 */
	List<SysBkerrorlog> bkErrorLogList;

	SysBkerrorlog sysBkerrorlog;
	SysSession sysSession;
	// ����userID��ȡ�û�����
	Map<String, SysUser> userByIdMap;
	// ����SessionID����ǰ�Ự)��ȡ�����б�
	List<SysFraccessLog> fraccessLogList;
	// ��ǰ�Ự�����в˵��Ĳ�����¼����ΪfraccessLog��ID��ֵΪ�����б�
	Map<Long, List<SysFraccessLogOpr>> opraByLogIdMap;
	// ��ǰ�Ự�������Ĳ˵�����ΪmenuId��ֵSysNavimenu����
	Map<String, SysNavimenu> sysNavimenuByIdMap;
	private Map<String, SysOprationtype> sysOprationtypeByIdMap;

	/**
	 * ����ʱ���ɾ��������־
	 * 
	 * @return
	 */
	public String deleteAccessLog() {
		initOpraMap("LOGVIEWER");
		logService.deleteAccessLog(sysSession);
		msg = new Msg(Msg.SUCCESS, "�ɹ�ɾ����");
		return MSG;
	}

	public String confirmDeleteErrorLog() {
		return "confirmErrorLog";
	}

	public String confirmDeleteAccessLog() {
		return "confirmAccessLog";
	}

	/**
	 * ����ʱ���ɾ��������־
	 * 
	 * @return
	 */
	public String deleteErrorLog() {
		msg = new Msg(Msg.SUCCESS, "�ɹ�ɾ����");
		logService.deleteErrorLog(sysBkerrorlog);
		return MSG;
	}

	/**
	 * ������־��ϸ��Ϣ
	 * 
	 * @return
	 */
	public String detailAccessLog() {
		sysSession = logService.getSysSessionById(sysSession.getId());
		return "accessdetail";
	}

	/**
	 * ������־��ϸ��Ϣ
	 * 
	 * @return
	 */
	public String detailErrorLog() {
		sysBkerrorlog = logService.getSysBkerrorlogById(sysBkerrorlog.getId());
		return "errordetail";
	}

	/**
	 * ��������־������Excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportError() throws Exception {
		if (null == sysBkerrorlog) {
			sysBkerrorlog = new SysBkerrorlog();
		}
		String startTime = sysBkerrorlog.getStartTime();
		String endTime = sysBkerrorlog.getEndTime();
		List<SysBkerrorlog> bkerrorlogList = logService.findBkErrorLog(startTime, endTime);

		HSSFWorkbook workbook = new HSSFWorkbook();
		logService.exportSysBkErrorLogExcel(workbook, bkerrorlogList);
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = CommUtils.getString(startTime, "��", endTime, "������־");
		ExcelUtil.writeWorkbook(response, workbook, fileName);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * ���Ự��־������Excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportSysSession() throws Exception {
		if (null == sysSession) {
			sysSession = new SysSession();
		}
		String startTime = sysSession.getStartDateStr();
		String endTime = sysSession.getEndDateStr();
		List<SysSession> sysSessionList = logService.findSysSession(startTime, endTime);
		HSSFWorkbook workbook = new HSSFWorkbook();
		List<SysFraccessLog> fraccessLogList = logService.findSysFraccessLog(startTime, endTime);
		List<SysFraccessLogOpr> fraccessLogOprs = logService.findSysFraccessLogOpr(startTime, endTime);
		logService.exportSysSessionExcel(workbook, sysSessionList, fraccessLogList, fraccessLogOprs);
		HttpServletResponse response = ServletActionContext.getResponse();
		ExcelUtil.writeWorkbook(response, workbook, startTime + "��" + endTime + " �Ự��־");
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public List<SysBkerrorlog> getBkErrorLogList() {
		return bkErrorLogList;
	}

	public List<SysFraccessLog> getFraccessLogList() {
		return fraccessLogList;
	}

	public Map<Long, List<SysFraccessLogOpr>> getOpraByLogIdMap() {
		return opraByLogIdMap;
	}

	/**
	 * @return the sysBkerrorlog
	 */
	public SysBkerrorlog getSysBkerrorlog() {
		return sysBkerrorlog;
	}

	public Map<String, SysNavimenu> getSysNavimenuByIdMap() {
		return sysNavimenuByIdMap;
	}

	/**
	 * @return the sysSession
	 */
	public SysSession getSysSession() {
		return sysSession;
	}

	public Map<String, SysUser> getUserByIdMap() {
		return userByIdMap;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String left() throws Exception {
		return "left";
	}

	/**
	 * ��־�鿴�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		return "list";
	}

	public String listaccess() throws Exception {

		if (null == sysSession) {
			sysSession = new SysSession();
		}

		Calendar calendar = Calendar.getInstance();
		String rqlx = sysSession.getRqlx();
		String startTime = "";
		String endTime = "";
		if (null != rqlx) {
			if (rqlx.equals("1")) {
				// ����
				startTime = CommUtils.formatDay(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				endTime = CommUtils.formatDay(calendar.getTime());
			} else if (rqlx.equals("2")) {
				// ����
				endTime = CommUtils.formatDay(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				startTime = CommUtils.formatDay(calendar.getTime());
			} else if (rqlx.equals("3")) {
				// һ��
				endTime = CommUtils.formatDay(calendar.getTime());
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				startTime = CommUtils.formatDay(calendar.getTime());
			} else if (rqlx.equals("4")) {
				// ȫ��
				startTime = null;
				endTime = null;
			} else if (rqlx.equals("5")) {
				startTime = sysSession.getStartDateStr();
				endTime = sysSession.getEndDateStr();
			}
		} else {
			startTime = sysSession.getStartDateStr();
			endTime = sysSession.getEndDateStr();
		}
		sysSession.setStartDateStr(startTime);
		sysSession.setEndDateStr(endTime);
		String pageUrl = "/sys/sys-log-viewer!listaccess.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<SysSession>();
			if (null != sysSession) {
				String schObjKey = "plaMupstransportdet_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysSession);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				sysSession = (SysSession) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		logService.findSysSession(sysSession, pageCtr);

		if (logger.isDebugEnabled()) {
			logger.debug("sysSession.startDateStr : " + sysSession.getStartDateStr());
			logger.debug("sysSession.endDateStr : " + sysSession.getEndDateStr());
		}
		// ���β�ѯ�л�ȡ�����лỰ��¼
		List<SysSession> sessionList = pageCtr.getRecords();
		// �Ự�����˵�ID�б�
		List<String> userIdList = null;
		if (null != sessionList && !sessionList.isEmpty()) {
			userIdList = new ArrayList<String>();
			for (SysSession sysSession : sessionList) {
				if (!userIdList.contains(sysSession.getUserid())) {
					userIdList.add(sysSession.getUserid());
				}
			}
		}
		// �����û�ID�б��ѯ�û�
		List<SysUser> userList = null;
		if (null != userIdList && !userIdList.isEmpty()) {
			userList = userService.findSysUserByUserIds(userIdList);
		}
		if (null != userList && !userList.isEmpty()) {
			userByIdMap = new HashMap<String, SysUser>();
			for (SysUser sysUser : userList) {
				// ���û���Ϣ����userByIdMap�У���ҳ���е���
				userByIdMap.put(sysUser.getUserid(), sysUser);
			}
		}
		return "listaccess";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listerror() throws Exception {
		if (null == sysBkerrorlog) {
			sysBkerrorlog = new SysBkerrorlog();
		}
		String rqlx = sysBkerrorlog.getRqlx();
		String startTime = "";
		String endTime = "";
		Calendar calendar = Calendar.getInstance();
		if (null == rqlx || "".equals(rqlx)) {
			startTime = sysBkerrorlog.getStartTime();
			endTime = sysBkerrorlog.getEndTime();
		} else {
			if (rqlx.equals("1")) {
				// ����
				startTime = CommUtils.dateFormat(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				endTime = CommUtils.dateFormat(calendar.getTime());
			} else if (rqlx.equals("2")) {
				// ����
				endTime = CommUtils.dateFormat(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				startTime = CommUtils.dateFormat(calendar.getTime());
			} else if (rqlx.equals("3")) {
				// һ��
				endTime = CommUtils.dateFormat(calendar.getTime());
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				startTime = CommUtils.dateFormat(calendar.getTime());
			} else if (rqlx.equals("4")) {
				// ȫ��
				startTime = sysBkerrorlog.getStartTime();
				endTime = sysBkerrorlog.getEndTime();
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("rqlx : " + rqlx);
			logger.debug("startTime : " + startTime);
			logger.debug("endTime : " + endTime);
		}
		sysBkerrorlog.setEndTime(endTime);
		sysBkerrorlog.setStartTime(startTime);

		String pageUrl = "/sys/sys-log-viewer!listerror.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<SysBkerrorlog>();
			if (null != sysBkerrorlog) {
				String schObjKey = "sysBkerrorlog_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysBkerrorlog);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				sysBkerrorlog = (SysBkerrorlog) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		logService.findBkErrorLog(sysBkerrorlog, pageCtr);
		return "listerror";
	}

	public String listerror1() throws Exception {
		Calendar calendar = Calendar.getInstance();
		String rqlx = null;
		if (null != sysBkerrorlog) {
			rqlx = sysBkerrorlog.getRqlx();
		}
		String startTime = "";
		String endTime = "";
		if (null != rqlx) {
			if (rqlx.equals("1")) {
				// ����
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				startTime = CommUtils.format(calendar.getTime());
				sysBkerrorlog.setStartTime(startTime);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				endTime = CommUtils.format(calendar.getTime());
				sysBkerrorlog.setEndTime(endTime);
			} else if (rqlx.equals("2")) {
				// ����
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				endTime = CommUtils.format(calendar.getTime());
				sysBkerrorlog.setEndTime(endTime);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				startTime = CommUtils.format(calendar.getTime());
				sysBkerrorlog.setStartTime(startTime);
			} else if (rqlx.equals("3")) {
				// һ��
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				endTime = CommUtils.format(calendar.getTime());
				sysBkerrorlog.setEndTime(endTime);
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				startTime = CommUtils.format(calendar.getTime());
				sysBkerrorlog.setStartTime(startTime);
			} else if (rqlx.equals("4")) {
				// ȫ��
				sysBkerrorlog.setEndTime(null);
				sysBkerrorlog.setStartTime(null);
			}
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			SysBkerrorlog bkerrorlog = (SysBkerrorlog) session.getAttribute("SysBkerrorlog");
			if (null == sysBkerrorlog) {
				sysBkerrorlog = new SysBkerrorlog();
			}
			sysBkerrorlog.setStartTime(bkerrorlog.getStartTime());
			sysBkerrorlog.setEndTime(bkerrorlog.getEndTime());
			sysBkerrorlog.setObjtype(bkerrorlog.getObjtype());
		}

		String pageUrl = "/sys/sys-log-viewer!listerror.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			if (null != sysBkerrorlog) {
				String schObjKey = "sysBkerrorlog_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysBkerrorlog);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				sysBkerrorlog = (SysBkerrorlog) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		logService.findBkErrorLog(sysBkerrorlog, pageCtr);
		return "listerror";
	}

	/**
	 * ����sessionID��ȡ�����б�
	 * 
	 * @return
	 */
	public String listsessiondetail() {
		String sessionid = sysSession.getSessionid();
		if (logger.isDebugEnabled()) {
			logger.debug("sessionid : " + sessionid);
		}
		// ��ǰ�Ự�Ĳ����˵��б�
		fraccessLogList = logService.findSysFraccessLog(sessionid);
		// ��ǰ�Ự��fraccessLog��Ӧ��ID�б�
		List<Long> logIdList = null;
		// ��ǰ�Ự��Ӧ�Ĳ˵�ID�б�
		List<Integer> menuIdList = null;
		if (null != fraccessLogList && !fraccessLogList.isEmpty()) {
			logIdList = new ArrayList<Long>();
			menuIdList = new ArrayList<Integer>();
			for (SysFraccessLog fraccessLog : fraccessLogList) {
				logIdList.add(fraccessLog.getId());
				String menuid = fraccessLog.getMenuid();
				if (null != menuid && !"".equals(menuid) && !"NONE".equals(menuid)) {
					menuIdList.add(Integer.valueOf(fraccessLog.getMenuid()));
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("menuIdList : " + menuIdList);
		}
		// ��ǰ�Ự�������Ĳ˵��б�
		List<SysNavimenu> menuList = menuService.findSysNavimenuByMenuIdList(menuIdList);
		if (logger.isDebugEnabled()) {
			logger.debug("menuList : " + menuList);
		}
		if (null != menuList && !menuList.isEmpty()) {
			sysNavimenuByIdMap = new HashMap<String, SysNavimenu>();
			for (SysNavimenu sysNavimenu : menuList) {
				// ��ǰ�Ự�������Ĳ˵�����ΪmenuId��ֵSysNavimenu����
				sysNavimenuByIdMap.put(String.valueOf(sysNavimenu.getMenuid()), sysNavimenu);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("sysNavimenuByIdMap : " + sysNavimenuByIdMap);
		}
		// ��ǰ�Ự�����в˵��Ĳ�����¼
		List<SysFraccessLogOpr> oprList = logService.findSysFraccessLogOpr(logIdList);
		if (null == opraByLogIdMap) {
			opraByLogIdMap = new HashMap<Long, List<SysFraccessLogOpr>>();
		}
		if (null != oprList && !oprList.isEmpty()) {
			for (SysFraccessLogOpr sysFraccessLogOpr : oprList) {
				long logid = sysFraccessLogOpr.getLogid();
				List<SysFraccessLogOpr> tmpList = opraByLogIdMap.get(logid);
				if (null == tmpList) {
					tmpList = new ArrayList<SysFraccessLogOpr>();
				}
				tmpList.add(sysFraccessLogOpr);
				opraByLogIdMap.put(logid, tmpList);
			}
		}
		List<SysOprationtype> sysOprationtypes = menuService.findSysOprationType();
		if (null != sysOprationtypes && !sysOprationtypes.isEmpty()) {
			sysOprationtypeByIdMap = new HashMap<String, SysOprationtype>();
			for (SysOprationtype sysOprationtype : sysOprationtypes) {
				sysOprationtypeByIdMap.put(sysOprationtype.getOperid(), sysOprationtype);
			}
		}
		return "listsessiondetail";
	}

	public void setBkErrorLogList(List<SysBkerrorlog> bkErrorLogList) {
		this.bkErrorLogList = bkErrorLogList;
	}

	public void setFraccessLogList(List<SysFraccessLog> fraccessLogList) {
		this.fraccessLogList = fraccessLogList;
	}

	public void setOpraByLogIdMap(Map<Long, List<SysFraccessLogOpr>> opraByLogIdMap) {
		this.opraByLogIdMap = opraByLogIdMap;
	}

	/**
	 * @param sysBkerrorlog
	 *            the sysBkerrorlog to set
	 */
	public void setSysBkerrorlog(SysBkerrorlog sysBkerrorlog) {
		this.sysBkerrorlog = sysBkerrorlog;
	}

	public void setSysNavimenuByIdMap(Map<String, SysNavimenu> sysNavimenuByIdMap) {
		this.sysNavimenuByIdMap = sysNavimenuByIdMap;
	}

	/**
	 * @param sysSession
	 *            the sysSession to set
	 */
	public void setSysSession(SysSession sysSession) {
		this.sysSession = sysSession;
	}

	public void setUserByIdMap(Map<String, SysUser> userByIdMap) {
		this.userByIdMap = userByIdMap;
	}

	/**
	 * ��־�鿴������������
	 * 
	 * @return
	 * @throws Exception
	 */
	public String top() throws Exception {
		return "top";
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("LOGVIEWER");
	}

	public Map<String, SysOprationtype> getSysOprationtypeByIdMap() {
		return sysOprationtypeByIdMap;
	}

	public void setSysOprationtypeByIdMap(Map<String, SysOprationtype> sysOprationtypeByIdMap) {
		this.sysOprationtypeByIdMap = sysOprationtypeByIdMap;
	}
}
