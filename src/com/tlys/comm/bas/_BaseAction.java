/**
 * 
 */
package com.tlys.comm.bas;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.comm.util.page.PageView;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMenuService;

/**
 * @author ������
 * 
 */


public abstract class _BaseAction extends ActionSupport implements Preparable {
	/******* ���²������ڵ�����msgҳ��ʱ������ *************/
	protected static final String MSG = "msg";

	private static final long serialVersionUID = 1L;

	protected SysUser curUser;

	protected boolean isNew;

	protected Log logger = LogFactory.getLog(this.getClass());

	// MapUtil opraMap;

	public String menuCode;

	public int menuid;

	@Autowired
	public SysMenuService menuService;

	protected Msg msg;

	protected PageCtr pageCtr;
	protected PageView pageView;
	private String title;// ҳ����ʾ����

	/**
	 * ��ȡ application ����.
	 * 
	 * @return
	 */
	public static ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * ��ȡ�������.
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * ��ȡHttpSession����
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * ��ȡ session �е�����ֵ
	 * 
	 * @param name
	 * @return
	 */
	public static Object getSessionAttr(String name) {
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		return session.get(name);
	}

	
	/**
	 * �� session ��������ֵ
	 * 
	 * @param name
	 * @param value
	 */
	public static void setSessionAttr(Object name, Object value) {
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		session.put(name, value);
	}

	/**
	 * @return the curUser
	 */
	public SysUser getCurUser() {
		curUser = (SysUser) getSessionAttr("sysUserSess");
		return curUser;
	}

	public boolean getIsNew() {
		return isNew;
	}

	// public MapUtil getOpraMap() {
	// return opraMap;
	// }
	//
	// public void setOpraMap(MapUtil opraMap) {
	// this.opraMap = opraMap;
	// }

	public String getMenuCode() {
		return menuCode;
	}

	public int getMenuid() {
		return menuid;
	}

	/**
	 * @return the msg
	 */
	public Msg getMsg() {
		return msg;
	}

	public PageCtr getPageCtr() {
		return pageCtr;
	}

	public PageView getPageView() {
		return pageView;
	}

	/**
	 * ��ȡ������
	 * 
	 * @param name
	 * @return
	 */
	public String getPara(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	/**
	 * ���ݲ˵�ID��ȡ�ò˵���Ӧ�����в������ܵļ��ϣ�����opraMap��,��ҳ��ֱ�ӵ���
	 */
	// public void initOpraMap() {
	// HttpServletRequest request = ServletActionContext.getRequest();
	// HttpSession session = request.getSession();
	// // ���ݲ˵�ID��ȡȨ�޲����İ�ť��MAP
	// Map<Integer, List<SysMenuopra>> oprasByMenuIdMap = (Map<Integer,
	// List<SysMenuopra>>) session
	// .getAttribute("hasOpraByMenuIdMap");
	// // ����operid��ȡ��ť����SysOprationtype��MAP
	// Map<String, SysOprationtype> sysOpraTypeByIdMap = (Map<String,
	// SysOprationtype>) session
	// .getAttribute("oprationTypByIdMap");
	// // ���ݲ˵�ID��ȡȨ�޲����İ�ť�б�
	// List<SysMenuopra> opras = null;
	// if (null != oprasByMenuIdMap) {
	// opras = oprasByMenuIdMap.get(menuid);
	// }
	// Map<String, String> opraMap = new HashMap<String, String>();
	// if (null != opras && !opras.isEmpty()) {
	// for (SysMenuopra sysMenuopra : opras) {
	// // ��ť����
	// String buttonType = sysMenuopra.getButtontype();
	// String operid = sysMenuopra.getOperid();
	// SysOprationtype sysOprationtype = null;
	// if (null != sysOpraTypeByIdMap) {
	// sysOprationtype = sysOpraTypeByIdMap.get(operid);
	// }
	//
	// // ��ǩID
	// String markId = sysMenuopra.getMenuopercode();
	// // ��ǩ�ĵ�Value����ֵ
	// String markText = sysMenuopra.getDispname();
	// // �����ʾ���Ʋ���������ʾsysOprationtype������
	// if (null == markText || "".equals(markText)) {
	// markText = sysOprationtype == null ? "" : (sysOprationtype.getOpername()
	// == null ? ""
	// : sysOprationtype.getOpername());
	// }
	// if (null == markId || "".equals(markId)) {
	// markId = operid;
	// }
	// String button = "";
	// if (null != buttonType) {
	// // 1ΪSubmit�ύ��ť
	// if ("1".equals(buttonType)) {
	// button = "<input type=\"Submit\" value=\"" + markText + "\" id=\"" +
	// markId + "\" />";
	// // 2ΪButton����Ҫ����ť�����¼������ύ
	// } else if ("2".equals(buttonType)) {
	// button = "<input type=\"Button\" value=\"" + markText + "\" id=\"" +
	// markId + "\" />";
	// // 3Ϊ���ӣ�
	// } else if ("3".equals(buttonType)) {
	// button = "<a href=\"javascript:void(0);\" id=\"" + markId +
	// "\" class=\"linkTobutton\">"
	// + markText + "</a>";
	// }
	// }
	// opraMap.put(markId, button);
	// }
	// }
	// this.opraMap = new MapUtil();
	// this.opraMap.setOpraMap(opraMap);
	// }

	/**
	 * ����������Ϊ��������.
	 * 
	 * @param name
	 *            ��������
	 * @return
	 */
	public int getParaInt(String name) {
		String s = getPara(name);
		if (s == null) {
			return 0;
		} else {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * ����������Ϊ����������.
	 * 
	 * @param name
	 *            ��������
	 * @return
	 */
	public long getParaLong(String name) {
		String s = getPara(name);
		if (s == null) {
			return 0L;
		} else {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0L;
	}

	/**
	 * ��ȡ request ������.
	 * 
	 * @param name
	 *            ������
	 */
	public Object getRequestAttr(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	/**
	 * ��ȡ��ǰ�Ự�ĵ�¼�û���Ϣ
	 * 
	 * @return User
	 */
	public SysUser getSysUserSess() {
		SysUser us = (SysUser) getSessionAttr("sysUserSess");
		return us;
	}

	public String getTitle() {
		return title;
	}

	/**
	 * ���ݲ˵������ȡ�ò˵���Ӧ�����в������ܵļ��ϣ�����opraMap��,��ҳ��ֱ�ӵ���
	 * 
	 * @param menuCode
	 */
	public void initOpraMap(String menuCode) {
		// ����ȫ�ֲ˵����룬����ҳ���е��ð�ť
		this.menuCode = menuCode;
	}

	/**
	 * ������URL�����ϲ���һ��URL�ַ���(page��������), �ṩ��ҳʱ��ʾ.
	 * 
	 * @return �ַ���, ��: para1=11&para2=bb
	 */
	public String mergeParamsAsURI() {
		Map<String, String[]> params = getRequest().getParameterMap();
		StringBuffer reParmSb = new StringBuffer();
		Set<String> keys = params.keySet();// �г����б�����
		for (String key : keys) {
			if (!"page".equals(key)) {
				// TODO ����Mapֵ������ --> Tomcat
				String[] values = params.get(key);// ���Ի�ȡ�������
				// ���ܵ����������Ƕ������ͳһת��������
				if (values.length > 1) {
					values = getRequest().getParameterValues(key);
				} else {
					values = new String[] { getPara(key) };
				}
				try {
					for (String value : values) {
						// System.out.println("value=" + value);
						reParmSb.append(java.net.URLEncoder.encode(key, "UTF-8") + "=");
						reParmSb.append(java.net.URLEncoder.encode(value, "UTF-8") + "&");
						// key=value&
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		// ɾ��ĩβ����� & �ַ�
		if (reParmSb.toString().endsWith("&")) {
			reParmSb.deleteCharAt(reParmSb.length() - 1);
		}
		return reParmSb.toString();
	}

	public void prepare() throws Exception {
		// ���ţ���Ҫ������ʵ��
	}

	/**
	 * @param curUser
	 *            the curUser to set
	 */
	public void setCurUser(SysUser curUser) {
		this.curUser = curUser;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(Msg msg) {
		this.msg = msg;
	}

	public void setPageCtr(PageCtr pageCtr) {
		this.pageCtr = pageCtr;
	}

	public void setPageView(PageView pageView) {
		this.pageView = pageView;
	}

	/**
	 * ���� request ������.
	 * 
	 * @param name
	 *            ������
	 * @param value
	 *            ����ֵ
	 */
	public void setRequestAttr(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
