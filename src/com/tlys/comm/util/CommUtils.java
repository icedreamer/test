/**
 * 
 */
package com.tlys.comm.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysOprationtype;
import com.tlys.sys.model.SysUser;

/**
 * @author fengym
 * 
 *         һ��Ĺ����࣬��̬�� һ����˵��ĳ������������һ��һ�ϣ���ҵ������û����Ϲ�ϵ�ģ���Ӧ�÷�������
 */
public class CommUtils {
	private static Log log = LogFactory.getLog(CommUtils.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	// Sun Dec 31 00:00:00 CST 1899
	private final static SimpleDateFormat cst = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.US);

	private final static SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
	private final static SimpleDateFormat shortMonthFormat = new SimpleDateFormat("yyyyMM");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * �����ŷָ����ַ����б��У�ÿ���ַ������ϵ�����
	 * 
	 * @param strlist
	 * @return
	 */
	public static String addQuotes(String strlist) {
		String[] rpArr = strlist.split(", ");
		StringBuffer resb = new StringBuffer("");
		for (int i = 0; i < rpArr.length; i++) {
			if (resb.length() > 0) {
				resb.append(",");
			}
			resb.append("'" + rpArr[i] + "'");
		}
		return resb.toString();
	}

	/**
	 * ���ַ������int
	 * 
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		if (str == null)
			return 0;
		int si = 0;
		byte[] sc = str.getBytes();
		for (int i = 0; i < sc.length; i++) {
			si += sc[i];
		}
		return si;
	}

	/**
	 * ��ȡԶ���û�IP��ַ
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteIP(HttpServletRequest request) {
		// Enumeration en = request.getHeaders("X-Forwarded-For");
		// String remoteIP = "";
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && ip.trim().length() > 0 && !ip.equalsIgnoreCase("unknown")) {
			if (ip.indexOf(".") != -1) {
				if (ip.indexOf(",") != -1) {
					ip = ip.trim().replace("'", "");
					String[] temparyip = ip.split(",");
					for (int i = 0; i < temparyip.length; i++) {
						if (temparyip[i] != null
								&& (temparyip[i].trim().length() > 7 || temparyip[i].trim().length() < 15)
								&& !temparyip[i].substring(0, 3).equals("10.")
								&& !temparyip[i].substring(0, 7).equals("192.168")
								&& !temparyip[i].substring(0, 7).equals("172.16.")) {
							ip = temparyip[i];
						}
					}
				}
			}
		} else {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		return ip;
	}

	/**
	 * ��ʾһ��ĵڼ�������
	 * 
	 * @param date
	 * @return
	 */
	public static String formatQuarter(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		String quarterStr = String.valueOf(year);
		int month = ca.get(Calendar.MONTH) + 1;
		if (4 > month) {
			quarterStr += "1";
		} else if (3 < month && 7 > month) {
			quarterStr += "2";
		} else if (6 < month && 10 > month) {
			quarterStr += "3";
		} else if (9 < month && 12 >= month) {
			quarterStr += "4";
		}
		return quarterStr;
	}

	/**
	 * ��ʱ���ʽ��Ϊyyyy-MM-dd HH:mm:ss�����ַ���
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return sdf.format(date);
	}

	/**
	 * ��ʱ���ʽ��Ϊ����Ŀ�ʼ yyyy-MM-dd 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDay(Date date) {
		return format.format(date);
	}

	public static String format(Date date, String formatStr) {
		if (null == formatStr || "".equals(formatStr)) {
			formatStr = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}

	/**
	 * ���·ݸ�ʽ������ yyyy-MM
	 * 
	 * @param date
	 * @return
	 */
	public static String monthFormat(Date date) {
		return monthFormat.format(date);
	}

	/**
	 * ���·ݸ�ʽ������ yyyyMM
	 * 
	 * @param date
	 * @return
	 */
	public static String shortMonthFormat(Date date) {
		return shortMonthFormat.format(date);
	}

	/**
	 * �����ڸ�ʽ��(yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * �����ڸ�ʽ��(yyyyMMdd)
	 * 
	 * @param date
	 * @return
	 */
	public static String shortDateFormat(Date date) {
		return shortDateFormat.format(date);
	}

	public static Date parseCST(String dateStr) {
		try {
			return cst.parse(dateStr);
		} catch (ParseException e) {
			try {
				return sdf.parse(dateStr);
			} catch (Exception e2) {
				log.error("����ת������:", e);
			}
		} catch (Exception e) {
			log.error("����ת������:", e);
		}
		return null;
	}

	public static Date parseDate(String dateStr, String formatStr) {
		if (formatStr == null || formatStr.equals("")) {
			formatStr = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date parseDate(String dateStr) {
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			log.error("����ת������:", e);
		}
		return null;
	}

	/**
	 * ��������ۼ�
	 * 
	 * @param objects
	 * @return
	 */
	public static String getString(Object... objects) {
		if (null == objects || objects.length == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (Object object : objects) {
			buf.append(object);
		}
		return buf.toString();
	}

	/**
	 * ����ļ���׺
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		if (null == fileName || "".equals(fileName)) {
			return "";
		}
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			return fileName.substring(index + 1);
		}
		return "";
	}

	/**
	 * ���������session
	 * 
	 * @param entity
	 */
	public static <T> void putEntityToSession(T entity) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String classSimpleName = entity.getClass().getSimpleName();
		if (log.isDebugEnabled()) {
			log.debug("classSimpleName : " + classSimpleName);
		}
		session.setAttribute(entity.getClass().getSimpleName(), entity);
	}

	/**
	 * Session �״̬
	 * 
	 * @param status
	 * @return
	 */
	public static String getSessionStatus(String status) {
		if (null == status) {
			return null;
		}
		String statusStr = "";
		if ("A".equalsIgnoreCase(status)) {
			statusStr = "�";
		} else if ("O".equalsIgnoreCase(status)) {
			statusStr = "�˳�";
		} else if ("E".equalsIgnoreCase(status)) {
			statusStr = "����";
		}
		return statusStr;
	}

	public static String getServerNameOnWindow() {
		String s = "";
		try {
			String s1 = "ipconfig /all";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String nextLine;
			for (String line = bufferedreader.readLine(); line != null; line = nextLine) {
				nextLine = bufferedreader.readLine();
				String filter = "Host Name . . . . . . . . . . . . ";
				int index = line.indexOf(filter);
				if (index != -1) {
					int endIndex = line.indexOf(":", index);
					if (endIndex != -1) {
						s = line.substring(endIndex + 1);
						break;
					}
				}
			}
			bufferedreader.close();
			process.waitFor();
		} catch (Exception exception) {
			s = "";
		}
		return s.trim();
	}

	public static Date getDefaultDate() {
		Calendar ca = Calendar.getInstance();
		// ca.setTimeZone(TimeZone.getDefault());
		ca.set(Calendar.YEAR, 1900);
		ca.set(Calendar.MONTH, 0);
		ca.set(Calendar.DAY_OF_MONTH, 0);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/**
	 * ��ȡ�û�����Ȩ����Ҫ����ҵ����(������ҵ�����sql)
	 * 
	 * @param property
	 *            �ֶ�����(POJO������)
	 * @return ���ַ���ֵ�� <br />
	 *         1. 1=1��ʾ����Ȩ�����ݣ�<br />
	 *         2. corpid in (.....) ��Ӧ��ҵ����Ȩ�ޣ� <br />
	 *         3. corpid in (-1) ��ʾû������Ȩ�ޣ� ������Ϊ��������
	 */

	public static String getCorpIds(String property) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// ���ݲ˵�ID��ȡ��ҵ����,SysUserdatas����ȡ��;
		Map<Integer, Set<String>> corpidsByMenuIdMap = (Map<Integer, Set<String>>) session
				.getAttribute("corpidsByMenuIdMap");
		// �û�Ĭ�Ͽ���ʹ�õ���ҵ����
		String[] defaultCorpIds = (String[]) session.getAttribute("defaultCorpIds");
		String corpidsSQL = "";
		// ���û�з�������Ȩ�ޣ���ȡĬ������Ȩ��
		if (null == corpidsByMenuIdMap || corpidsByMenuIdMap.isEmpty()) {
			// ���Ĭ��Ȩ�޷���null������Ϊ���ܲ��û��������������������,����ȡ��Ĭ������(��ҵ����)
			if (null == defaultCorpIds || defaultCorpIds.length == 0) {
				corpidsSQL = " 1=1 ";
			} else {
				// ��ȡĬ����ҵ���뷵��
				String defaultCorps = Arrays.toString(defaultCorpIds);
				defaultCorps = defaultCorps.replace("[", "").replace("]", "").replace(" ", "");
				corpidsSQL = getString(" ", property, " in ( ", StringJoinQuotation(defaultCorps), " )");
			}
		} else {
			// ����Ѿ���������Ȩ��
			ActionContext actionContext = ActionContext.getContext();
			ValueStack valueStack = actionContext.getValueStack();
			// ��ȡ�û���ǰ�����Ĳ˵�����
			String menuCode = valueStack.findString("menuCode");
			Map<String, SysNavimenu> parentNaviMenuMap = (Map<String, SysNavimenu>) session
					.getAttribute("parentNaviMenuMap");
			// ���ݲ˵������ȡ����˵�
			SysNavimenu sysNavimenu = parentNaviMenuMap.get(menuCode);
			int menuId = 0;
			if (null != sysNavimenu) {
				menuId = sysNavimenu.getMenuid();
			}
			// ����ܹ����ݲ˵�����鵽����(��ҵ����)������Щ��ҵ����ֱ�ӷ���
			Set<String> corpIdSet = corpidsByMenuIdMap.get(menuId);
			if (null != corpIdSet && !corpIdSet.isEmpty()) {
				corpIdSet.addAll(Arrays.asList(defaultCorpIds));
				String corps = Arrays.toString(corpIdSet.toArray());
				corps = corps.replace("[", "").replace("]", "").replace(" ", "");
				corpidsSQL = getString(" ", property, " in (", StringJoinQuotation(corps), ")");
			} else {
				if (null == defaultCorpIds || defaultCorpIds.length == 0) {
					corpidsSQL = " 1=1 ";
				} else {
					String defaultCorps = Arrays.toString(defaultCorpIds);
					defaultCorps = defaultCorps.replace("[", "").replace("]", "").replace(" ", "");
					corpidsSQL = getString(" ", property, " in ( ", StringJoinQuotation(defaultCorps), " )");
				}
			}
		}

		return corpidsSQL;
	}

	/**
	 * ��ȡ�û�����Ȩ����Ҫ����ҵ����(������ҵ���������)
	 * 
	 * @param property
	 * @return ���ַ���ֵ��<br />
	 *         1. null���߳���Ϊ0 ��ʾ��������Ȩ�ޣ�<br />
	 *         2. -1��ʾû��Ȩ��,��Ĭ������Ȩ��defaultCorpIds���Ѿ����ú�;<br />
	 *         3. ���ȴ���0������{.....}��Ϊ��Ӧ��ҵȨ�ޣ� ����Ϊ��������
	 */
	public static String[] getCorpId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// ���ݲ˵�ID��ȡ��ҵ����,SysUserdatas����ȡ��;
		Map<Integer, Set<String>> corpidsByMenuIdMap = (Map<Integer, Set<String>>) session
				.getAttribute("corpidsByMenuIdMap");
		// �û�Ĭ�Ͽ���ʹ�õ���ҵ����
		String[] defaultCorpIds = (String[]) session.getAttribute("defaultCorpIds");
		String[] corpids = null;
		// ���û�з�������Ȩ�ޣ���ȡĬ������Ȩ��
		if (null == corpidsByMenuIdMap || corpidsByMenuIdMap.isEmpty()) {
			// ���Ĭ��Ȩ�޷���null������Ϊ���ܲ��û��������������������,����ȡ��Ĭ������(��ҵ����)
			corpids = defaultCorpIds;
		} else {
			// ����Ѿ���������Ȩ��
			ActionContext actionContext = ActionContext.getContext();
			ValueStack valueStack = actionContext.getValueStack();
			// ��ȡ�û���ǰ�����Ĳ˵�����
			String menuCode = valueStack.findString("menuCode");
			Map<String, SysNavimenu> parentNaviMenuMap = (Map<String, SysNavimenu>) session
					.getAttribute("parentNaviMenuMap");
			// ���ݲ˵������ȡ����˵�
			SysNavimenu sysNavimenu = parentNaviMenuMap.get(menuCode);
			int menuId = 0;
			if (null != sysNavimenu) {
				menuId = sysNavimenu.getMenuid();
			}
			// ����ܹ����ݲ˵�����鵽����(��ҵ����)������Щ��ҵ����ֱ�ӷ���
			Set<String> corpIdSet = corpidsByMenuIdMap.get(menuId);
			if (null != corpIdSet && !corpIdSet.isEmpty()) {
				corpids = (String[]) corpIdSet.toArray(new String[0]);
			} else {
				corpids = defaultCorpIds;
			}
		}
		return corpids;
	}

	/**
	 * ��ȡ�û�����Ȩ����Ҫ����ҵ����(������ҵ������ַ�������,����)
	 * 
	 * @return ���ַ���ֵ�� <br />
	 *         1. null����ַ�������ʾ����Ȩ�ޣ�<br />
	 *         2. -1 ��ʾû��Ȩ�ޣ�<br />
	 *         3. corpid,corpid,corpid �ö��Ÿ������ַ����� ����Ϊ��������
	 */
	public static String getCorpIds() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// ���ݲ˵�ID��ȡ��ҵ����,SysUserdatas����ȡ��;
		Map<Integer, Set<String>> corpidsByMenuIdMap = (Map<Integer, Set<String>>) session
				.getAttribute("corpidsByMenuIdMap");
		// �û�Ĭ�Ͽ���ʹ�õ���ҵ����
		String[] defaultCorpIds = (String[]) session.getAttribute("defaultCorpIds");
		String corpids = "";
		// ���û�з�������Ȩ�ޣ���ȡĬ������Ȩ��
		if (null == corpidsByMenuIdMap || corpidsByMenuIdMap.isEmpty()) {
			// ���Ĭ��Ȩ�޷���null������Ϊ���ܲ��û��������������������,����ȡ��Ĭ������(��ҵ����)
			if (null != defaultCorpIds && defaultCorpIds.length >= 0) {
				corpids = StringJoinQuotation(Arrays.toString(defaultCorpIds).replace("[", "").replace("]", ""));
			}
		} else {
			// ����Ѿ���������Ȩ��
			ActionContext actionContext = ActionContext.getContext();
			ValueStack valueStack = actionContext.getValueStack();
			// ��ȡ�û���ǰ�����Ĳ˵�����
			String menuCode = valueStack.findString("menuCode");
			Map<String, SysNavimenu> parentNaviMenuMap = (Map<String, SysNavimenu>) session
					.getAttribute("parentNaviMenuMap");
			// ���ݲ˵������ȡ����˵�
			SysNavimenu sysNavimenu = parentNaviMenuMap.get(menuCode);
			int menuId = 0;
			if (null != sysNavimenu) {
				menuId = sysNavimenu.getMenuid();
			}
			// ����ܹ����ݲ˵�����鵽����(��ҵ����)������Щ��ҵ����ֱ�ӷ���
			Set<String> corpIdSet = corpidsByMenuIdMap.get(menuId);
			if (null != corpIdSet && !corpIdSet.isEmpty()) {
				corpids = StringJoinQuotation(Arrays.toString(corpIdSet.toArray()).replace("[", "").replace("]", ""));
			} else {
				if (null != defaultCorpIds && defaultCorpIds.length >= 0) {
					corpids = StringJoinQuotation(Arrays.toString(defaultCorpIds).replace("[", "").replace("]", ""));
				}
			}
		}
		return corpids.replace(" ", "");
	}

	/**
	 * ��һ���ļ�����չ������ǿ�ƽ���չ����ΪСд
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExt(String filename) {
		String ext = "";
		ext = filename.substring(filename.lastIndexOf(".") + 1);
		ext = ext.toLowerCase();
		return ext;
	}

	/**
	 * ��һ���ַ���ǰ�油0,��һ���������ַ����ַ������ܳ��� (�����ڽ����к�ת�ɶ���)
	 * 
	 * @param seq
	 * @param len
	 * @return
	 */
	public static String getSeqStr(String seq, int len) {
		int seqLen = seq.length();
		StringBuilder buf = new StringBuilder(seq);
		if (len > seq.length()) {
			for (int i = 0; i < len - seqLen; i++) {
				buf.insert(0, "0");
			}
		}

		return buf.toString();
	}

	/**
	 * 
	 * @param menuId
	 * @param isNew
	 * @return
	 */
	public static MapUtil initOpraMap(int menuId, String isNew) {
		MapUtil opraMap = new MapUtil(isNew);
		// ����ȫ�ֲ˵����룬����ҳ���е��ð�ť
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// ���ݲ˵�ID��ȡȨ�޲����İ�ť��MAP
		Map<Integer, List<SysMenuopra>> oprasByMenuIdMap = (Map<Integer, List<SysMenuopra>>) session
				.getAttribute("hasOpraByMenuIdMap");
		// ����operid��ȡ��ť����SysOprationtype��MAP
		Map<String, SysOprationtype> sysOpraTypeByIdMap = (Map<String, SysOprationtype>) session
				.getAttribute("oprationTypByIdMap");
		// ���ݲ˵�ID��ȡȨ�޲����İ�ť�б�
		List<SysMenuopra> opras = null;
		if (null != oprasByMenuIdMap) {
			opras = oprasByMenuIdMap.get(menuId);
		}
		if (null != opras && !opras.isEmpty()) {
			for (SysMenuopra sysMenuopra : opras) {
				// ��ť����
				String buttonType = sysMenuopra.getButtontype();
				String operid = sysMenuopra.getOperid();
				SysOprationtype sysOprationtype = null;
				// ��ǩ��valueֵ
				String markText = sysMenuopra.getDispname();
				// ��ǩ��ID
				String markId = sysMenuopra.getMenuopercode();

				if (null != sysOpraTypeByIdMap) {
					sysOprationtype = sysOpraTypeByIdMap.get(operid);
				}
				// �����ʾ���Ʋ���������ʾsysOprationtype������
				if (null == markText || "".equals(markText)) {
					markText = sysOprationtype == null ? "" : (sysOprationtype.getOpername() == null ? ""
							: sysOprationtype.getOpername());
				}
				// ����˵��������벻���ڣ�����ʾΪԭʼ�˵�����ID��operid)
				if (null == markId || "".equals(markId)) {
					markId = operid;
				}
				String elementId = markId;
				if ((markId.endsWith("_U") || markId.endsWith("_A")) && !"EDI".equals(operid)) {
					elementId = markId.substring(0, markId.length() - 2);
				}

				String button = "";
				if (null != buttonType) {
					// 1ΪSubmit�ύ��ť
					if ("1".equals(buttonType)) {
						button = "<input type=\"Submit\" value=\"" + markText + "\" id=\"" + elementId + "\" />";
						// 2ΪButton����Ҫ����ť�����¼������ύ
					} else if ("2".equals(buttonType)) {
						button = "<input type=\"Button\" value=\"" + markText + "\" id=\"" + elementId + "\" />";
						// 3Ϊ���ӣ�
					} else if ("3".equals(buttonType)) {
						button = "<a href=\"javascript:void(0);\" id=\"" + elementId + "\" rel=\"" + operid
								+ "\" class=\"linkTobutton\">" + markText + "</a>";
					}
				}
				opraMap.put(markId, button);
			}
		}
		return opraMap;
		// valueStack.set("opraMap", opraMap);
	}

	/**
	 * ��ȡList����������ָ������������ֵ������SQL�ж������in�Ĳ���(,,,)
	 * ��Criteria��Restrictions.inʹ��ʱͨ��splitת��ΪString[] in�Ĳ�����Ҫ��ҳ���д����ڸ÷����в�ֱ��ת��Ϊ����
	 * 
	 * @param list
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public static String getRestrictionsIn(List list, String columnName) throws Exception {
		StringBuffer vals = new StringBuffer("");
		Object object = new Object();// �ж��Ƿ��ظ�
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			Class c = obj.getClass();
			// ����������ĸ��д
			String column = CommUtils.getString(columnName.substring(0, 1).toUpperCase(), columnName.substring(1)
					.toLowerCase());
			// ��ȡget��������
			String methodNameOfGet = CommUtils.getString("get", column);
			// get���Է���
			Method methodOfGetView = c.getMethod(methodNameOfGet, null);
			// ��ȡ��ֵ
			Object val = methodOfGetView.invoke(obj, null);
			// �Կ����ݺ��ظ����ݹ���
			if (null != val && !object.equals(val)) {
				vals.append(val);
				vals.append(",");
				object = val;
			}
		}
		String str = vals.toString();
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static String decode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			log.error("encode String error.", e);
		}
		return null;
	}

	/**
	 * ��in �Ӿ� ��ѯʱ��Ҫ��ÿ���ַ����ӵ�����
	 * 
	 * @param str
	 * @return
	 */
	public static String StringJoinQuotation(String str) {
		if (null == str || "".equals(str)) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		String[] array = str.split(",");
		if (null != array && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				buffer.append("'" + array[i] + "'").append(",");
			}
		}
		String quotationStr = buffer.toString();
		if (quotationStr.endsWith(",")) {
			quotationStr = quotationStr.substring(0, quotationStr.length() - 1);
		}
		return quotationStr;
	}

	/**
	 * ԭ��SQL IN����
	 * 
	 * @param str
	 */
	public static String getSqlIn(String[] str) {
		StringBuffer vals = new StringBuffer("");
		for (String val : str) {
			vals.append("'" + val + "',");
		}
		String ss = vals.toString();
		return ss.substring(0, ss.length() - 1);
	}

	public static String getSqlIn(List<String> strList) {
		StringBuffer vals = new StringBuffer();
		for (String val : strList) {
			vals.append("'" + val + "',");
		}
		String ss = vals.toString();
		return ss.substring(0, ss.length() - 1);
	}

	/**
	 * 
	 * @param curUser
	 * @return
	 */
	private String userCorpAuthHqlb(SysUser curUser) {
		/** ����ѯ����Ȩ�ޣ�����ǰ�û�������ҵ����ѯ��add by fengym */

		Set<String> roleids = curUser.getRoles();

		String curCorpid = curUser.getCorpid();
		String authSql = "";
		if (!roleids.contains("000103") && !roleids.contains("000221")) {
			authSql = "A.corpid='" + curCorpid + "' ";
		}
		// authSql = " and " + authSql;
		return authSql;
	}

	/**
	 * <li>�����û���ȡ��ǰ�û�������Ȩ�ޣ�����ҵID����</li> <li>�ܲ��û���ȫ��Ȩ��</li> <li>����˾�û�����Ӧ��ȫ����ҵ</li>
	 * <li>��ҵ�û�����ǰ���ڵ���ҵID</li>
	 * 
	 * prefix:�����ǰ��ѯ���Ҫ��ǰ׺
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	static public String userCorpAuthHql(SysUser curUser, DicMap dicMap, String prefix) throws Exception {
		String curCorptab = curUser.getCorptab();
		String authHql = "";

		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// �ܲ�����
				authHql = "1=1";
			} else if ("1".equals(curCorptab)) {// ����˾
				String areaid = curUser.getCorpid();
				if (log.isDebugEnabled()) {
					log.debug("CommUtils.userCorpAuthHql->areaid==" + areaid);
				}
				Map corpMap = dicMap.getAreaCorpMap(areaid);

				for (Iterator iter = corpMap.keySet().iterator(); iter.hasNext();) {
					String corpid = (String) iter.next();
					if (log.isDebugEnabled()) {
						log.debug("CommUtils.userCorpAuthHql->corpid==" + corpid);
					}
					if ("".equals(authHql)) {
						authHql += "(";
					} else {
						authHql += " or ";
					}
					authHql += prefix + "corpid='" + corpid + "'";
					if (log.isDebugEnabled()) {
						log.debug("CommUtils.userCorpAuthHql->authHql==" + authHql);
					}
				}
				authHql += ")";
			} else if ("2".equals(curCorptab)) {// ��ҵ
				String corpid = curUser.getCorpid();
				authHql += prefix + "corpid='" + corpid + "'";
			}
		}

		if (authHql.equals("")) {
			authHql = "1=2";
		}

		return authHql;
	}

	/**
	 * <li>�����û���ȡ��ǰ�û�������Ȩ�ޣ�����ҵID����</li> <li>�ܲ��û���ȫ��Ȩ��</li> <li>����˾�û�����Ӧ��ȫ����ҵ</li>
	 * <li>��ҵ�û�����ǰ���ڵ���ҵID</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	static public String userCorpAuthHql(SysUser curUser, DicMap dicMap) {
		try {
			return userCorpAuthHql(curUser, dicMap, "");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * �����û����͙��޻�ȡ��ҵ
	 * 
	 * @param sysUser
	 * @param dicMap
	 * @return
	 */
	public static Map<String, DicSinocorp> getUserCorpMap(SysUser sysUser, DicMap dicMap) {

		Map<String, DicSinocorp> dicSinocorpMap = new LinkedHashMap<String, DicSinocorp>();
		// �û�����(0�ܲ���1����˾��2��ҵ��3��������)
		String corptype = sysUser.getCorptab();
		if ("0".equals(corptype)) {
			dicSinocorpMap = dicMap.getCorpAllMap();
		} else {
			Map<String, DicSinocorp> corpMap = dicMap.getCorpMap();
			if ("1".equals(corptype)) {
				String areaid = sysUser.getCorpid();
				if (log.isDebugEnabled()) {
					log.debug("areaid : " + areaid);
				}
				Collection<DicSinocorp> corps = corpMap.values();
				if (log.isDebugEnabled()) {
					log.debug("corps.size : " + corps.size());
				}
				for (DicSinocorp dicSinocorp : corps) {
					if (dicSinocorp.getAreaid().equals(areaid)) {
						if (log.isDebugEnabled()) {
							log.debug("dicSinocorp.name : " + dicSinocorp.getShortname());
						}
						dicSinocorpMap.put(dicSinocorp.getCorpid(), dicSinocorp);
					}
				}
			} else if ("2".equals(corptype)) {
				String corpid = sysUser.getCorpid();
				if (corpMap.containsKey(corpid)) {
					dicSinocorpMap.put(corpid, corpMap.get(corpid));
				}
			}
		}
		return dicSinocorpMap;
	}

	/**
	 * �����û����͙��޻�ȡ�^��˾
	 * 
	 * @param sysUser
	 * @param dicMap
	 * @return
	 * @throws Exception
	 */
	public static Map<String, DicAreacorp> getUserAreaMap(SysUser sysUser, DicMap dicMap) throws Exception {
		Map<String, DicAreacorp> dicAreacorpMap = new LinkedHashMap<String, DicAreacorp>();
		String corptype = sysUser.getCorptab();
		// �ܲ�
		Map<String, DicAreacorp> areacorpMap = dicMap.getAreaMap();
		if ("0".equals(corptype)) {
			dicAreacorpMap = areacorpMap;
		} else {
			// ����˾
			Collection<DicAreacorp> areacorps = areacorpMap.values();
			if ("1".equals(corptype)) {
				String areaid = sysUser.getCorpid();
				for (DicAreacorp dicAreacorp : areacorps) {
					if (dicAreacorp.getAreaid().equals(areaid)) {
						dicAreacorpMap.put(areaid, dicAreacorp);
					}
				}
				// ��ҵ
			} else if ("2".equals(corptype)) {
				String corpid = sysUser.getCorpid();
				Map<String, DicSinocorp> dicSinocorpMap = dicMap.getCorpMap();
				DicSinocorp dicSinocorp = dicSinocorpMap.get(corpid);
				String areaid = "";
				if (null != dicSinocorp) {
					areaid = dicSinocorp.getAreaid();
				}
				if (areacorpMap.containsKey(areaid)) {
					dicAreacorpMap.put(areaid, areacorpMap.get(areaid));
				}
			}
		}
		return dicAreacorpMap;
	}

	/**
	 * �����û����͙��ޅ^��˾��ȡ������I
	 * 
	 * @param sysUser
	 * @param dicMap
	 * @return
	 */
	public static Map<String, DicSinocorp> getUserCorpByAreaId(SysUser sysUser, DicMap dicMap, String areaCorpId) {
		String corptype = sysUser.getCorptab();
		Map<String, DicSinocorp> dicSinocorpMap = new LinkedHashMap<String, DicSinocorp>();
		// ��ҵ
		if ("2".equals(corptype)) {
			String corpid = sysUser.getCorpid();
			Map<String, DicSinocorp> corpsMap = dicMap.getCorpMap();
			DicSinocorp dicSinocorp = corpsMap.get(corpid);
			String areaid = dicSinocorp.getAreaid();
			Map<String, DicSinocorp> areacorpsMap = dicMap.getAreaCorpMap(areaid);
			if (areaCorpId.equals(areaid)) {
				dicSinocorpMap.put(corpid, corpsMap.get(corpid));
			}
		} else {
			dicSinocorpMap = dicMap.getAreaCorpMap(areaCorpId);
		}
		return dicSinocorpMap;
	}

	public static SysUser getCurUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		return (SysUser) session.getAttribute("sysUserSess");
	}

	/**
	 * ��������ֵ
	 * 
	 * @param src
	 * @param dest
	 */
	public static void copyProperties(Object src, Object dest) {

		try {

			Class srcClass = src.getClass();
			Class destClass = dest.getClass();

			Map<String, Field> srcFieldMap = new HashMap<String, Field>();
			getFields(srcClass, srcFieldMap);
			Map<String, Field> destFieldMap = new HashMap<String, Field>();
			getFields(destClass, destFieldMap);
			Iterator<String> it = srcFieldMap.keySet().iterator();
			while (it.hasNext()) {
				String name = it.next();
				Field srcField = srcFieldMap.get(name);
				Field destField = destFieldMap.get(name);
				if (destField != null) {
					srcField.setAccessible(true);
					destField.setAccessible(true);
					destField.set(dest, srcField.get(src));

				}
			}

		} catch (Exception e) {
			log.error("error.", e);
		}
	}

	public static void getFields(Class clazz, Map<String, Field> fieldMap) {
		String className = clazz.getCanonicalName();
		String objectName = Object.class.getCanonicalName();
		if (className.equals(objectName)) {
			return;
		}
		Field[] fields = clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			if (!fieldMap.containsKey(name)) {
				fieldMap.put(name, fields[i]);
			}
		}
		getFields(clazz.getSuperclass(), fieldMap);

	}

	/**
	 * �������б���ĳ�����������Զ�����SQL��IN���� �����������ܳ���1000
	 * ������������1000ʱʹ��Restrictions.sqlRestriction
	 * 
	 * @param list
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public static String getSqlIn(List list, String columnName) throws Exception {
		StringBuffer vals = new StringBuffer("");
		Object object = new Object();// �ж��Ƿ��ظ�
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			Class c = obj.getClass();
			// ����������ĸ��д
			String column = CommUtils.getString(columnName.substring(0, 1).toUpperCase(), columnName.substring(1)
					.toLowerCase());
			// ��ȡget��������
			String methodNameOfGet = CommUtils.getString("get", column);
			// get���Է���
			Method methodOfGetView = c.getMethod(methodNameOfGet, null);
			// ��ȡ��ֵ
			Object val = methodOfGetView.invoke(obj, null);
			// �Կ����ݺ��ظ����ݹ���
			if (null != val && !object.equals(val)) {
				vals.append("'" + val + "',");
				object = val;
			}
		}
		String str = vals.toString();
		return str.substring(0, str.length() - 1);
	}

	/**
	 * ��ȡ�����û�loginid
	 * 
	 * @return
	 */
	public static String getSuperUser() {
		ServletContext context = ServletActionContext.getServletContext();
		String username = (String) context.getAttribute("superUser");
		return username == null ? "super" : username;
	}

	public static void flushData(String str, HttpServletResponse response) {
		OutputStream out = null;
		try {
			response.setContentType("text/plain;charset=utf-8");
			byte[] data = str.getBytes("UTF-8");
			response.setContentLength(data.length);
			out = response.getOutputStream();
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			String start = "F600E1324";
			for (int i = 0; i < 100000; i++) {
				start = getNext(start);
				System.out.println(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getNext(String currentValue) {
		int index = currentValue.length() - 1;
		char[] chars = currentValue.toCharArray();
		while (true) {
			if (chars[index] == '9') {
				chars[index--] = '0';
				continue;
			} else {
				chars[index]++;
				break;
			}
		}
		return new String(chars);
	}
}
