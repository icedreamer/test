package com.tlys.sys.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tlys.bit.model.BitCarcu;
import com.tlys.bit.service.BitCarcuService;
import com.tlys.bit.service.BitService;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.service.EquCarRentService;
import com.tlys.equ.service.EquCarService;
import com.tlys.equ.service.EquRepPlanService;
import com.tlys.exe.service.ExeAreaDistService;
import com.tlys.sys.model.Contact;
import com.tlys.sys.model.SysBulletin;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysBulletinService;
import com.tlys.sys.service.SysDatafillinfoService;
import com.tlys.sys.service.SysMailService;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysSessionService;
import com.tlys.sys.service.SysUserService;

@Controller
// by spring,����bean
@Scope("prototype")
// by struts2������actionΪԭ��ģʽ
@ParentPackage("tlys-default")
// //by struts2�������pachage�̳�tlys-default(tlys-default����struts.xml�ж���)
@Namespace("/sys")
public class SysAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1601674069565584981L;
	private List<SysNavimenu> navimenuList;
	private List<SysNavimenu> sysManageNavimenuList;
	private SysNavimenu sysNavimenu;
	private List<SysBulletin> sysBulletins;
	// ��ϵ��ʽ��json��
	private String contactStr;
	// չ����ʱ��ǰ�˴��ݵĲ���id(��ʱ�����޸�)
	private String id;
	/**
	 * ���⳵�������޳�������
	 */
	int cars;

	@Autowired
	SysMenuService menuService;

	@Autowired
	ExeAreaDistService exeAreaDistService;
	@Autowired
	BitService bitService;
	@Autowired
	EquRepPlanService equRepPlanService;
	@Autowired
	EquCarService equCarService;
	@Autowired
	SysBulletinService sysBulletinService;
	@Autowired
	EquCarRentService equCarRentService;

	@Autowired
	SysSessionService sysSessionService;
	@Autowired
	SysDatafillinfoService sysDatafillinfoService;
	@Autowired
	SysMenuService sysMenuService;

	@Autowired
	SysMailService sysMailService;
	@Autowired
	SysUserService sysUserService;

	@Autowired
	BitCarcuService bitCarcuService;

	// ���ݸ�ID��ȡ�Ӳ˵���map
	Map<Integer, List<SysNavimenu>> sysNavimenuListByPmenuIdMap;

	// ����û���ص���Ϣ
	Map userinfoMap;

	// �������֪ͨ�����б�
	List btList;

	// ��ŵ�����������
	Map curMonthMap;

	// ����복�������ƻ�����
	Map prMap;

	// ����������ݣ���databus��Ϊ��ֵ
	Map dfMap;

	// ��ŵ�ǰ�û�����ҳ�������ݵ�Ȩ��������˴�Ȩ��һ����˵ֻ��ָ�Ƿ���ж�ȡȨ�޾͹���)
	// menucode=22_2(���û��Ȩ�ޣ���=noauth)
	Map mshowidMap;

	// ��ŵ�ǰ�û����ʼ����ʼ���
	Map mailMap;

	String topage;

	Contact contact;

	public Map<Integer, List<SysNavimenu>> getSysNavimenuListByPmenuIdMap() {
		return sysNavimenuListByPmenuIdMap;
	}

	public void setSysNavimenuListByPmenuIdMap(
			Map<Integer, List<SysNavimenu>> sysNavimenuListByPmenuIdMap) {
		this.sysNavimenuListByPmenuIdMap = sysNavimenuListByPmenuIdMap;
	}

	public List<SysNavimenu> getNavimenuList() {
		return navimenuList;
	}

	public SysNavimenu getSysNavimenu() {
		return sysNavimenu;
	}

	public String left() {

		return "left";
	}

	public String list() {
		return "list";
	}

	public void setNavimenuList(List<SysNavimenu> navimenuList) {
		this.navimenuList = navimenuList;
	}

	public void setSysNavimenu(SysNavimenu sysNavimenu) {
		this.sysNavimenu = sysNavimenu;
	}

	public String subtop() {
		int menuid = sysNavimenu.getMenuid();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		List<SysNavimenu> menuList = (List<SysNavimenu>) session
				.getAttribute("menuList");
		if (null != menuList && !menuList.isEmpty()) {
			if (null == navimenuList) {
				navimenuList = new ArrayList<SysNavimenu>();
			}
			for (SysNavimenu sysNavimenu : menuList) {
				if (menuid == sysNavimenu.getPmenuid()) {
					navimenuList.add(sysNavimenu);
				}
			}
		}
		return "subtop";
	}

	public String top() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// SysUser sysUser = getSysUserSess();
		List<SysNavimenu> userOfAllMenuList = (List<SysNavimenu>) session
				.getAttribute("menuList");
		navimenuList = sysMenuService.findUserOfParentMenus(userOfAllMenuList);
		sysNavimenuListByPmenuIdMap = sysMenuService
				.getSysNavimenuListByPmenuIdMap(userOfAllMenuList);
		setSessionAttr("sysNavimenuListByPmenuIdMap",
				sysNavimenuListByPmenuIdMap);
		setSessionAttr("navimenuList", navimenuList);
		// �����ѷ����Ĺ���
		SysUser sysUser = getCurUser();
		sysBulletins = sysBulletinService.findSysBulletin(sysUser.getUserid(),
				"2");
		renderSysManage();
		int rentcars = equCarRentService.getEquRentCars();
		int corpcars = equCarRentService.getEquCorpCars();
		cars = rentcars + corpcars;
		return "top";
	}

	private void renderSysManage() {
		String menuname = "ϵͳ����";
		if (null != navimenuList && !navimenuList.isEmpty()) {
			for (SysNavimenu menu : navimenuList) {
				if (menu.getMenuname() != null
						&& menu.getMenuname().equals(menuname)) {
					sysNavimenu = menu;
				}
			}
		}
		if (null != sysNavimenuListByPmenuIdMap && null != sysNavimenu) {
			sysManageNavimenuList = sysNavimenuListByPmenuIdMap.get(sysNavimenu
					.getMenuid());
		}
	}

	/**
	 * ϵͳ��ҳ
	 * 
	 * @return
	 * @throws Exception
	 */

	public String index() throws Exception {
		return "index";
	}

	public String index_new() throws Exception {

		// ����ҳ��֮ǰ�ȵ���һ�δ洢����
		// �ȸ�Ϊ��SysUserAction�У�login�ɹ�������¿��̵߳���
		// sysDatafillinfoService.exeProc();

		curUser = getCurUser();

		/**
		 * �˵���ʶ����Ҫ����ȷ��ĳ���û��Դ˲˵��Ƿ���Ȩ�� ͬʱ�������Ȩ�ޣ��򷵻ش˲˵���cookie��
		 */
		String[] mcodeArr = new String[] { "BULLETIN_AND_MESSAGE",// ��Ϣ����
				"BI_PLA", // BI����
				"SYS_MAIL", // �ڲ��ʼ�
				"PLA_MUPS2", // ͳ���ƻ�
				"PLA_MSPS2", // ��ͳ���ƻ�
				"PLA_MUPR", // �복������
				"EQU_REP", // ��������
				"EXE_TRAN" // ��������
		};
		mshowidMap = menuService.bdMenushowidMap(mcodeArr);

		// �����û���Ϣ��������ݣ�ҳ�����Ͻ�)
		userinfoMap = new HashMap();
		Integer logCntYear = sysSessionService.getUserLogCount(curUser
				.getUserid(), "y");
		Integer logCntMonth = sysSessionService.getUserLogCount(curUser
				.getUserid(), "m");
		userinfoMap.put("logCntYear", logCntYear);
		userinfoMap.put("logCntMonth", logCntMonth);

		// ����֪ͨ������Ϣ
		btList = sysBulletinService.findSysBulletin4Index(curUser, 6);

		/*
		 * //����������� curMonthMap = new HashMap(); curMonthMap.put("uspMap",
		 * sysDatafillinfoService.getUspMap()); curMonthMap.put("auspMap",
		 * sysDatafillinfoService.getAuspMap()); curMonthMap.put("sspMap",
		 * sysDatafillinfoService.getSspMap()); curMonthMap.put("asspMap",
		 * sysDatafillinfoService.getAsspMap()); curMonthMap.put("scrMap",
		 * sysDatafillinfoService.getScrMap());
		 * 
		 * //�����복�������ƻ����� prMap = new HashMap(); prMap.put("muraMap",
		 * sysDatafillinfoService.getMuraMap()); prMap.put("duraMap",
		 * sysDatafillinfoService.getDuraMap()); prMap.put("msraMap",
		 * sysDatafillinfoService.getMsraMap()); prMap.put("dsraMap",
		 * sysDatafillinfoService.getDsraMap()); prMap.put("moraMap",
		 * sysDatafillinfoService.getMoraMap()); prMap.put("doraMap",
		 * sysDatafillinfoService.getDoraMap());
		 */

		dfMap = sysDatafillinfoService.getDfMap();

		mailMap = sysMailService.findUserMailCounts(curUser);

		if (null == topage) {
			return "index_new_new";
		}
		return topage;
	}
	/**
	 *������ҳ�Ա������ܴ��� 
	 * @return
	 * @throws Exception
	 */
	public String initZBC()throws Exception{
		PrintWriter out = null;
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("model");
			SysUser sysUser = getCurUser();
			BitCarcu bitCarcu = bitCarcuService.getBitCarcu(sysUser);
			Element dataset = root.addElement("dataset");
			if (logger.isDebugEnabled()) {
				logger.debug("datadate ==============================: " + bitCarcu.getDatadate());
				logger.debug("MCU ==============================: " + bitCarcu.getMcu());
				logger.debug("QCU ==============================: " + bitCarcu.getQcu());
				logger.debug("YCU ==============================: " + bitCarcu.getYcu());
			}
			dataset.addAttribute("MCU", bitCarcu == null ? "0" : String
					.valueOf(bitCarcu.getMcu()));
			dataset.addAttribute("QCU", bitCarcu == null ? "0" : String
					.valueOf(bitCarcu.getQcu()));
			dataset.addAttribute("YCU", bitCarcu == null ? "0" : String
					.valueOf(bitCarcu.getYcu()));
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Pragma", "no-cache");
			OutputFormat of = new OutputFormat(" ", true);
			of.setEncoding("UTF-8");
			out = response.getWriter();
			XMLWriter xmlWriter = new XMLWriter(out, of);
			xmlWriter.write(document);
			xmlWriter.close();
			out.flush();
		}catch (Exception e) {
			logger.error("write xml error.", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
		
		return null;
	}
	

	public String indexdata() throws Exception {
		PrintWriter out = null;
		try {
			ActionContext context = ServletActionContext.getContext();
			context.getActionInvocation().getProxy().setExecuteResult(false);
			HttpServletResponse response = ServletActionContext.getResponse();

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("model");
			EquCar equCar = new EquCar();
			equCar.setIsexpire("0");
			int totalCars = equCarService.getObjCount(equCar);
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			String date = CommUtils.shortDateFormat(calendar.getTime());
			// �����ֲ�(�ؿճ�)
			writeAreaCarsPieXml(root, totalCars, date, hour);
			// �����ֲ�(����ͼ)
			writeCarnumsLineXml(root, totalCars, date, hour);
			// ��������ֲ�(��ͼ)
			SysUser sysUser = getCurUser();
			writeCarnumsOfAreaXml(root, totalCars, date, hour, sysUser);
			// �ƻ�����
			writePlanXml(root, calendar);
			// ���޵���״ͼ
			writeRepairXml(root);
			// ����
			writeMsinosctamountsXml(root);
			/** ---------------------------������---------------------------------- */
			// �Ա����꣬������ʹ���ʲ�ѯ
			writeDQMYcuAreaXml(root, sysUser);

			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Pragma", "no-cache");
			OutputFormat of = new OutputFormat(" ", true);
			of.setEncoding("UTF-8");
			out = response.getWriter();
			XMLWriter xmlWriter = new XMLWriter(out, of);
			xmlWriter.write(document);
			xmlWriter.close();
			out.flush();
		} catch (Exception e) {
			logger.error("write xml error.", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * �ƻ���״ͼ
	 * 
	 * @param root
	 */
	private void writePlanXml(Element root, Calendar calendar) {
		Element elePlanData = root.addElement("planData");
		SysUser sysUser = getCurUser();
		// ���� ��³
		// ���� ï��
		// ���� ����
		// ��������

		String month = bitService.getFixMonth();
		if (logger.isDebugEnabled()) {
			logger.debug("month : " + month);
		}
		List<Object[]> planList = bitService.findBitMcarsrequests(sysUser,
				month);

		if (null != planList && !planList.isEmpty()) {
			LinkedList<Object[]> planLinkedList = new LinkedList<Object[]>();
			for (int i = 0; i < planList.size(); i++) {
				Object[] o = planList.get(i);
				String corpid = o[4].toString();
				if (corpid.equals("31600000") || corpid.equals("31750000")
						|| corpid.equals("30600000")
						|| corpid.equals("32100000")) {
					planLinkedList.addFirst(o);
				} else {
					planLinkedList.add(o);
				}
			}
			for (Object[] objects : planLinkedList) {
				String corpName = objects[0].toString();
				if ("1".equals(corpName)) {
					corpName = "ͳ��";
				} else if ("0".equals(corpName)) {
					corpName = "��ͳ��";
				}
				Element eleDataset = elePlanData.addElement("dataset");
				eleDataset.addAttribute("label", corpName);
				eleDataset.addAttribute("pNums", objects[1].toString());
				eleDataset.addAttribute("aNums", objects[2].toString());
				eleDataset.addAttribute("lNums", objects[3].toString());
			}
		} else {
			Element eleDataset = elePlanData.addElement("dataset");
			eleDataset.addAttribute("label", "");
			eleDataset.addAttribute("pNums", "0");
			eleDataset.addAttribute("aNums", "0");
			eleDataset.addAttribute("lNums", "0");
		}
	}

	/**
	 * ���޵���״ͼ
	 * 
	 * @param root
	 */
	private void writeRepairXml(Element root) {
		Element eleRepairData = root.addElement("repairData");
		List<Object[]> equRepCarsList = equRepPlanService.findThisMonthReps();
		if (null != equRepCarsList && !equRepCarsList.isEmpty()) {
			LinkedList<Object[]> equRepCarsLinkedList = new LinkedList<Object[]>();
			for (int i = 0; i < equRepCarsList.size(); i++) {
				Object[] o = equRepCarsList.get(i);
				String corpid = o[3].toString();
				if (corpid.equals("31600000") || corpid.equals("31750000")
						|| corpid.equals("30600000")
						|| corpid.equals("32100000")) {
					equRepCarsLinkedList.addFirst(o);
				} else {
					equRepCarsLinkedList.add(o);
				}
			}
			// ������ҵID��ȡ��ҵ���
			// ����corpid_repkind��ֵ��count
			LinkedHashMap<String, Integer> countByCorpNameAndRepkindMap = new LinkedHashMap<String, Integer>();
			LinkedHashMap<String, String> corpNameMap = new LinkedHashMap<String, String>();
			for (Object[] objects : equRepCarsLinkedList) {
				// A.id.corpid, A.id.corpshortname,A.id.repkind, COUNT(*)
				String corpname = objects[0].toString();
				String repkind = objects[1].toString();
				int count = Integer.parseInt(objects[2].toString());
				String key = CommUtils.getString(corpname, "_", repkind);
				countByCorpNameAndRepkindMap.put(key, count);
				corpNameMap.put(corpname, corpname);
			}
			Collection<String> corpNames = corpNameMap.values();
			for (String corpName : corpNames) {
				Element eleDataset = eleRepairData.addElement("dataset");
				eleDataset.addAttribute("label", corpName);
				// ���´���
				Integer MP = countByCorpNameAndRepkindMap.get(corpName + "_MP");
				eleDataset.addAttribute("mNums", String.valueOf(null == MP ? 0
						: MP));
				// ����δ��
				Integer EP = countByCorpNameAndRepkindMap.get(corpName + "_EP");
				eleDataset.addAttribute("eNums", String.valueOf(null == EP ? 0
						: EP));
			}
		} else {
			Element eleDataset = eleRepairData.addElement("dataset");
			eleDataset.addAttribute("label", "");
			// ���´���
			eleDataset.addAttribute("mNums", "0");
			// ����δ��
			eleDataset.addAttribute("eNums", "0");
		}
	}

	/**
	 * �Ա�����������ͼ���¼�
	 * 
	 * @param root
	 */
	private void writeMsinosctamountXml(Element root, List<String> monthList) {
		Element eleSelfCarData = root.addElement("selfCarData");
		SysUser sysUser = getCurUser();
		String corptab = sysUser.getCorptab();
		if (null == corptab) {
			return;
		}
		String corpid = sysUser.getCorpid();
		String monthStr = CommUtils.shortMonthFormat(new Date());
		LinkedHashMap<String, String> numsByMonthMap = new LinkedHashMap<String, String>();
		// פ���죬��ҵ����
		if ("2".equals(corptab) || "3".equals(corptab)) {
			List<Object[]> bitMscotamountList = bitService.findBitMscotamount(
					monthStr, corpid);

			if (null != bitMscotamountList && !bitMscotamountList.isEmpty()) {
				for (Object[] objects : bitMscotamountList) {
					String month = objects[0].toString();
					numsByMonthMap.put(month, CommUtils.getString(objects[1],
							"_", objects[2], "_", objects[3]));
				}
			}

			List<Object[]> bitMscitamountList = bitService.findBitMscitamount(
					monthStr, corpid);

			if (null != bitMscitamountList && !bitMscitamountList.isEmpty()) {
				for (Object[] objects : bitMscitamountList) {
					String month = objects[0].toString();
					String numsStr = numsByMonthMap.get(month);

					String[] arrays = numsStr.split("_");
					double first = Double.parseDouble(arrays[0])
							+ Double.parseDouble(objects[1].toString());
					double second = Double.parseDouble(arrays[1])
							+ Double.parseDouble(objects[2].toString());
					double third = Double.parseDouble(arrays[2])
							+ Double.parseDouble(objects[3].toString());
					numsByMonthMap.put(month, CommUtils.getString(first, "_",
							second, "_", third));
				}
			}

		} else {
			List<Object[]> list = null;
			// �ܲ�
			if ("0".equals(corptab)) {
				list = bitService.findBitMsinosctamount(monthStr);
			} else if ("1".equals(corptab)) {
				// ����˾
				list = bitService.findBitMareasctamount(monthStr, corpid);
			}
			if (null != list && !list.isEmpty()) {
				for (Object[] objects : list) {
					String month = objects[0].toString();
					String mtamount = objects[1].toString();
					String mt = objects[2].toString();
					String my = objects[3].toString();
					numsByMonthMap.put(month, CommUtils.getString(mtamount,
							"_", mt, "_", my));
				}
			}
		}

		for (String month : monthList) {
			String numsStr = numsByMonthMap.get(month);
			String[] arrays = null == numsStr ? new String[] { "0", "0", "0" }
					: numsStr.split("_");
			Element eledataset = eleSelfCarData.addElement("dataset");
			String m = CommUtils.format(CommUtils.parseDate(month, "yyyyMM"),
					"yyyy.MM");
			eledataset.addAttribute("id", "S_" + m);
			eledataset.addAttribute("datadate", m);
			eledataset.addAttribute("nums", String.valueOf(Double
					.parseDouble(arrays[0]) / 10000));
			eledataset.addAttribute("tbNums", String.valueOf(Double
					.parseDouble(arrays[2]) / 10000));
			eledataset.addAttribute("hbNums", String.valueOf(Double
					.parseDouble(arrays[1]) / 10000));
		}

		// for (Object[] objects : list) {
		// String month = objects[0].toString();
		// String mtamount = objects[1].toString();
		// String mt = objects[2].toString();
		// String my = objects[3].toString();
		// Element eledataset = eleSelfCarData.addElement("dataset");
		// String m = CommUtils.format(CommUtils.parseDate(month, "yyyyMM"),
		// "yyyy.MM");
		// eledataset.addAttribute("id", "S_" + m);
		// eledataset.addAttribute("datadate", m);
		// eledataset.addAttribute("nums",
		// String.valueOf(Double.parseDouble(mtamount) / 10000));
		// eledataset.addAttribute("tbNums",
		// String.valueOf(Double.parseDouble(mt) / 10000));
		// eledataset.addAttribute("hbNums",
		// String.valueOf(Double.parseDouble(my) / 10000));
		// if (!monthList.contains(month)) {
		// monthList.add(month);
		// }
		// }
		// �Ա������� ��ϸ���ݵ�xml
		writeMsinosctDetailXml(root, sysUser, monthList);
	}

	/**
	 * �Ա������� ��ϸ���� ����
	 * 
	 * @param monthList
	 */
	private void writeMsinosctDetailXml(Element root, SysUser sysUser,
			List<String> monthList) {
		Element eleSelfCarDataCompose = root.addElement("selfCarDataCompose");
		String corptab = sysUser.getCorptab();
		String corpid = sysUser.getCorpid();
		List<Object[]> list = null;
		// �¶ȶ�Ӧ�ĸ���kind�б�
		Map<String, List<Object[]>> datasByMonthMap = new HashMap<String, List<Object[]>>();
		// �¶ȶ�Ӧ����kind�ļӺ�ֵ(��ҵȨ��)
		LinkedHashMap<String, Double> numsByMonthAndKindMap = new LinkedHashMap<String, Double>();
		if ("2".equals(corptab) || "3".equals(corptab)) {
			// פ���죬��ҵ���� ��������
			List<Object[]> outList = bitService.findBitMscotamountByKind(
					monthList, corpid);
			if (null != outList && !outList.isEmpty()) {
				for (Object[] objects : outList) {
					String month = objects[0].toString();
					String kind = objects[1].toString();
					String num = objects[2].toString();
					String key = CommUtils.getString(month, "_", kind);
					numsByMonthAndKindMap.put(key, Double.parseDouble(num));
				}
			}
			List<Object[]> inList = bitService.findBitMscitamountByKind(
					monthList, corpid);
			if (null != inList && !inList.isEmpty()) {
				for (Object[] objects : inList) {
					String month = objects[0].toString();
					String kind = objects[1].toString();
					double num = Double.parseDouble(objects[2].toString());
					String key = CommUtils.getString(month, "_", kind);
					Double nums = numsByMonthAndKindMap.get(key);
					if (null == nums) {
						nums = 0d;
					}
					numsByMonthAndKindMap.put(key, nums + num);
				}
			}
			Set<String> monthAndkindSet = numsByMonthAndKindMap.keySet();
			for (Iterator<String> iterator = monthAndkindSet.iterator(); iterator
					.hasNext();) {
				String monthAndKind = (String) iterator.next();
				String month = monthAndKind.split("_")[0];
				String kind = monthAndKind.split("_")[1];
				String[] o = new String[3];
				o[0] = month;
				o[1] = kind;
				o[2] = String.valueOf(numsByMonthAndKindMap.get(monthAndKind));
				List<Object[]> oList = datasByMonthMap.get(month);
				if (null == oList) {
					oList = new ArrayList<Object[]>();
				}
				oList.add(o);
				datasByMonthMap.put(month, oList);
			}
		} else {
			if ("1".equals(corptab)) {
				// ����˾
				list = bitService
						.findBitMareasctamountByKind(monthList, corpid);
			} else if ("0".equals(corptab)) {
				// �ܲ�
				list = bitService.findBitMsinosctamountByKind(monthList);
			}
			if (null != list && !list.isEmpty()) {
				for (Object[] o : list) {
					String month = o[0].toString();
					List<Object[]> oList = datasByMonthMap.get(month);
					if (null == oList) {
						oList = new ArrayList<Object[]>();
					}
					oList.add(o);
					datasByMonthMap.put(month, oList);
				}
			}
		}
		for (String month : monthList) {
			Element elecompose = eleSelfCarDataCompose.addElement("compose");
			elecompose.addAttribute("id", "S_"
					+ CommUtils.getString(month.substring(0, 4), "."
							+ month.substring(4)));
			List<Object[]> oList = datasByMonthMap.get(month);
			if (null == oList || oList.isEmpty()) {
				Element eledataset = elecompose.addElement("dataset");
				eledataset.addAttribute("label", "0");
				eledataset.addAttribute("nums", "0");
				continue;
			}
			for (Object[] objects : oList) {
				Element eledataset = elecompose.addElement("dataset");
				eledataset.addAttribute("label", bitService
						.getKindname(objects[1].toString()));
				eledataset.addAttribute("nums", String.valueOf(Double
						.parseDouble(objects[2].toString()) / 10000));
			}
		}
	}

	/**
	 * ��·��������ͼ ����
	 * 
	 * @param root
	 */
	private void writeCardataXml(Element root, List<String> monthList) {
		Element eleAllCarData = root.addElement("allCarData");
		SysUser sysUser = getCurUser();
		String corptab = sysUser.getCorptab();
		if (null == corptab) {
			return;
		}
		LinkedHashMap<String, String> numsByMonthMap = new LinkedHashMap<String, String>();
		String corpid = sysUser.getCorpid();
		String currentMonth = CommUtils.shortMonthFormat(new Date());
		// פ���죬��ҵ����
		if ("2".equals(corptab) || "3".equals(corptab)) {
			List<Object[]> bitMotamountList = bitService.findBitMotamount(
					currentMonth, corpid);

			if (null != bitMotamountList && !bitMotamountList.isEmpty()) {
				for (Object[] objects : bitMotamountList) {
					String month = objects[0].toString();
					numsByMonthMap.put(month, CommUtils.getString(objects[1],
							"_", objects[2], "_", objects[3]));
				}
			}

			List<Object[]> bitMitamountList = bitService.findBitMitamount(
					currentMonth, corpid);

			if (null != bitMitamountList && !bitMitamountList.isEmpty()) {
				for (Object[] objects : bitMitamountList) {
					String month = objects[0].toString();
					String numsStr = numsByMonthMap.get(month);
					String[] arrays = numsStr.split("_");
					double first = Double.parseDouble(arrays[0])
							+ Double.parseDouble(objects[1].toString());
					double second = Double.parseDouble(arrays[1])
							+ Double.parseDouble(objects[2].toString());
					double third = Double.parseDouble(arrays[2])
							+ Double.parseDouble(objects[3].toString());
					numsByMonthMap.put(month, CommUtils.getString(first, "_",
							second, "_", third));
				}
			}

		} else {
			List<Object[]> list = null;
			// �ܲ�
			if ("0".equals(corptab)) {
				list = bitService.findBitMsinoctamount(currentMonth);
			} else if ("1".equals(corptab)) {
				// ����˾
				list = bitService.findBitMareactamount(currentMonth, corpid);
			}
			if (null != list && !list.isEmpty()) {
				for (Object[] objects : list) {
					String month = objects[0].toString();
					String mtamount = objects[1].toString();
					String mt = objects[2].toString();
					String my = objects[3].toString();
					numsByMonthMap.put(month, CommUtils.getString(mtamount,
							"_", mt, "_", my));
				}
			}
		}
		for (String month : monthList) {
			String numsStr = numsByMonthMap.get(month);
			String[] arrays = null == numsStr ? new String[] { "0", "0", "0" }
					: numsStr.split("_");
			Element eledataset = eleAllCarData.addElement("dataset");
			String m = CommUtils.format(CommUtils.parseDate(month, "yyyyMM"),
					"yyyy.MM");
			eledataset.addAttribute("id", "A_" + m);
			eledataset.addAttribute("datadate", m);
			eledataset.addAttribute("nums", String.valueOf(Double
					.parseDouble(arrays[0]) / 10000));
			eledataset.addAttribute("tbNums", String.valueOf(Double
					.parseDouble(arrays[2]) / 10000));
			eledataset.addAttribute("hbNums", String.valueOf(Double
					.parseDouble(arrays[1]) / 10000));
		}
		writeCardataDetailXml(root, sysUser, monthList);
	}

	private void writeCardataDetailXml(Element root, SysUser sysUser,
			List<String> monthList) {
		String corptab = sysUser.getCorptab();
		String corpid = sysUser.getCorpid();
		Map<String, List<Object[]>> datasByMonthMap = new HashMap<String, List<Object[]>>();
		// �¶ȶ�Ӧ����kind�ļӺ�ֵ(��ҵȨ��)
		LinkedHashMap<String, Double> numsByMonthAndKindMap = new LinkedHashMap<String, Double>();
		if ("2".equals(corptab) || "3".equals(corptab)) {
			List<Object[]> outList = bitService.findBitMotamountByKind(
					monthList, corpid);
			if (null != outList && !outList.isEmpty()) {
				for (Object[] objects : outList) {
					String month = objects[0].toString();
					String kind = objects[1].toString();
					String num = objects[2].toString();
					String key = CommUtils.getString(month, "_", kind);
					numsByMonthAndKindMap.put(key, Double.parseDouble(num));
				}
			}
			List<Object[]> inList = bitService.findBitMitamountByKind(
					monthList, corpid);
			if (null != inList && !inList.isEmpty()) {
				for (Object[] objects : inList) {
					String month = objects[0].toString();
					String kind = objects[1].toString();
					double num = Double.parseDouble(objects[2].toString());
					String key = CommUtils.getString(month, "_", kind);
					Double nums = numsByMonthAndKindMap.get(key);
					if (null == nums) {
						nums = 0d;
					}
					numsByMonthAndKindMap.put(key, nums + num);
				}
			}
			Set<String> monthAndkindSet = numsByMonthAndKindMap.keySet();
			for (Iterator<String> iterator = monthAndkindSet.iterator(); iterator
					.hasNext();) {
				String monthAndKind = (String) iterator.next();
				String month = monthAndKind.split("_")[0];
				String kind = monthAndKind.split("_")[1];
				String[] o = new String[3];
				o[0] = month;
				o[1] = kind;
				o[2] = String.valueOf(numsByMonthAndKindMap.get(monthAndKind));
				List<Object[]> oList = datasByMonthMap.get(month);
				if (null == oList) {
					oList = new ArrayList<Object[]>();
				}
				oList.add(o);
				datasByMonthMap.put(month, oList);
			}
		} else {
			List<Object[]> list = null;
			if ("1".equals(corptab)) {
				list = bitService.findBitMareactamountByKind(monthList, corpid);
			} else if ("0".equals(corptab)) {
				list = bitService.findBitMsinoctamountByKind(monthList);
			}

			if (null != list && !list.isEmpty()) {
				for (Object[] objects : list) {
					String month = objects[0].toString();
					List<Object[]> oList = datasByMonthMap.get(month);
					if (null == oList) {
						oList = new ArrayList<Object[]>();
					}
					oList.add(objects);
					datasByMonthMap.put(month, oList);
				}
			}
		}

		Element eleAllCarDataCompose = root.addElement("allCarDataCompose");
		for (String month : monthList) {
			Element elecompose = eleAllCarDataCompose.addElement("compose");
			elecompose.addAttribute("id", "A_"
					+ CommUtils.getString(month.substring(0, 4), ".", month
							.substring(4)));
			List<Object[]> oList = datasByMonthMap.get(month);
			if (null == oList || oList.isEmpty()) {
				continue;
			}
			for (Object[] o : oList) {
				String kind = o[1].toString();
				String nums = o[2].toString();
				Element eledataset = elecompose.addElement("dataset");
				eledataset.addAttribute("label", bitService.getKindname(kind));
				eledataset.addAttribute("nums", String.valueOf(Double
						.parseDouble(nums) / 10000));
			}
		}
	}

	/**
	 * ��������ͼ
	 * 
	 * @param root
	 */
	private void writeMsinosctamountsXml(Element root) {
		Element eleSelfCarData = root.addElement("selfCarData");
		SysUser sysUser = getCurUser();
		String corptab = sysUser.getCorptab();
		if (null == corptab) {
			return;
		}
		String corpid = sysUser.getCorpid();
		String monthStr = CommUtils.shortMonthFormat(new Date());
		LinkedHashMap<String, String> numsByMonthMap = new LinkedHashMap<String, String>();
		List<String> monthList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -12);
		int currentMonth = calendar.get(Calendar.MONTH);
		for (int i = currentMonth - 12; i < currentMonth; i++) {
			calendar.add(Calendar.MONTH, 1);
			if (CommUtils.shortMonthFormat(calendar.getTime()).compareTo(
					"201201") < 0) {
				continue;
			}
			monthList.add(CommUtils.shortMonthFormat(calendar.getTime()));
		}
		// פ���죬��ҵ����
		if ("2".equals(corptab) || "3".equals(corptab)) {

			List<Object[]> bitMscotamountList = bitService.findBitMscotamounts(
					monthStr, corpid);

			if (null != bitMscotamountList && !bitMscotamountList.isEmpty()) {
				for (Object[] objects : bitMscotamountList) {
					String month = objects[0].toString();
					numsByMonthMap.put(month, CommUtils.getString(objects[1],
							"_", objects[2], "_", objects[3]));
				}
			}

			List<Object[]> bitMscitamountList = bitService.findBitMscitamounts(
					monthStr, corpid);

			if (null != bitMscitamountList && !bitMscitamountList.isEmpty()) {
				for (Object[] objects : bitMscitamountList) {
					String month = objects[0].toString();
					String numsStr = numsByMonthMap.get(month);
					String[] arrays = numsStr.split("_");
					double first = Double.parseDouble(arrays[0])
							+ Double.parseDouble(objects[1].toString());
					double second = Double.parseDouble(arrays[1])
							+ Double.parseDouble(objects[2].toString());
					double third = Double.parseDouble(arrays[2])
							+ Double.parseDouble(objects[3].toString());
					numsByMonthMap.put(month, CommUtils.getString(first, "_",
							second, "_", third));
				}
			}
		} else {
			List<Object[]> list = null;
			// �ܲ�
			if ("0".equals(corptab)) {
				list = bitService.findBitMsinosctamounts(monthStr);
			} else if ("1".equals(corptab)) {
				// ����˾
				list = bitService.findBitMareasctamounts(monthStr, corpid);
			}

			if (null != list && !list.isEmpty()) {
				for (Object[] objects : list) {
					String month = objects[0].toString();
					String mtamount = objects[1].toString();
					String mt = objects[2].toString();
					String my = objects[3].toString();
					numsByMonthMap.put(month, CommUtils.getString(mtamount,
							"_", mt, "_", my));
				}
			}

			// if (null != list && !list.isEmpty()) {
			// for (Object[] objects : list) {
			// String month = objects[0].toString();
			// String mtamount = objects[1].toString();
			// String mt = objects[2].toString();
			// String my = objects[3].toString();
			// Element eledataset = eleSelfCarData.addElement("dataset");
			// eledataset.addAttribute("datadate",
			// CommUtils.format(CommUtils.parseDate(month,
			// "yyyyMM"), "yyyy.MM"));
			// eledataset.addAttribute("nums", String
			// .valueOf(Double.parseDouble(mtamount) / 10000));
			// eledataset.addAttribute("tbNums", String
			// .valueOf(Double.parseDouble(mt) / 10000));
			// eledataset.addAttribute("hbNums", String
			// .valueOf(Double.parseDouble(my) / 10000));
			// }
			// }
		}
		for (String month : monthList) {
			String numsStr = numsByMonthMap.get(month);
			String[] arrays = null == numsStr ? new String[] { "0", "0", "0" }
					: numsStr.split("_");
			Element eledataset = eleSelfCarData.addElement("dataset");
			eledataset.addAttribute("datadate", CommUtils.format(CommUtils
					.parseDate(month, "yyyyMM"), "yyyy.MM"));
			eledataset.addAttribute("nums", String.valueOf(Double
					.parseDouble(arrays[0]) / 10000));
			eledataset.addAttribute("tbNums", String.valueOf(Double
					.parseDouble(arrays[2]) / 10000));
			eledataset.addAttribute("hbNums", String.valueOf(Double
					.parseDouble(arrays[1]) / 10000));
		}
	}

	/**
	 * �����ֲ�(�ؿճ�)
	 * 
	 * @param root
	 */
	private void writeAreaCarsPieXml(Element root, int totalCars, String date,
			int hour) {
		Element elefirstPieData = root.addElement("firstPieData");

		List<Object[]> list = exeAreaDistService.findCarsOfCode(date, hour);

		int totalLecode = 0;
		if (null != list && !list.isEmpty()) {
			for (Object[] objects : list) {
				String lecode = objects[0].toString();
				String label = "";
				String dataId = "";
				if (null != lecode) {
					if ("0".equals(lecode)) {
						label = "��";
						dataId = "emptyCar";
					} else if ("1".equals(lecode)) {
						label = "��";
						dataId = "weightCar";
					} else if ("2".equals(lecode)) {
						label = "δʶ��";
						dataId = "undisCar";
					}
				}
				totalLecode += Integer.parseInt(objects[1].toString());
				Element eledataset = elefirstPieData.addElement("dataset");
				eledataset.addAttribute("label", label);
				eledataset.addAttribute("nums", objects[1].toString());
				eledataset.addAttribute("dataID", dataId);
			}
		} else {
			for (int i = 0; i < 3; i++) {
				String label = "";
				String dataId = "";
				if (i == 0) {
					label = "��";
					dataId = "emptyCar";
				} else if (i == 1) {
					label = "��";
					dataId = "weightCar";
				} else if (i == 2) {
					label = "δʶ��";
					dataId = "undisCar";
				}
				Element eledataset = elefirstPieData.addElement("dataset");
				eledataset.addAttribute("label", label);
				eledataset.addAttribute("nums", "0");
				eledataset.addAttribute("dataID", dataId);
			}
		}
		Element element = elefirstPieData.addElement("dataset");
		element.addAttribute("label", "��ʱͣ��");
		element.addAttribute("nums", String.valueOf(totalCars - totalLecode));
		element.addAttribute("dataID", "onroadCar");
	}

	/**
	 * �����ֲ�(����ͼ)
	 * 
	 * @param root
	 */
	private void writeCarnumsLineXml(Element root, int totalCars, String date,
			int hour) {
		Element eleLineChartData = root.addElement("lineChartData");
		Calendar calendar = Calendar.getInstance();

		List<Object[]> list = exeAreaDistService.findWeekCarsOfLecode(date,
				hour);
		Map<String, Integer> carnumsByDayMap = new HashMap<String, Integer>();
		if (null != list && !list.isEmpty()) {
			for (Object[] objects : list) {
				String day = objects[0].toString();
				String lecode = objects[1].toString();
				String dataID = "";
				String linechart = "";
				if (null != lecode) {
					if ("0".equals(lecode)) {
						dataID = "emptyCar";
						linechart = "E";
					} else if ("1".equals(lecode)) {
						dataID = "weightCar";
						linechart = "W";
					} else if ("2".equals(lecode)) {
						dataID = "undisCar";
						linechart = "U";
					}
				}
				String carnum = objects[2].toString();
				Element eledataset = eleLineChartData.addElement("dataset");

				eledataset.addAttribute("dataID", dataID);
				eledataset.addAttribute("datadate", CommUtils.format(CommUtils
						.parseDate(day, "yyyyMMdd"), "MM.dd"));
				eledataset.addAttribute("nums", carnum);
				eledataset.addAttribute("linechartID", CommUtils.getString(
						linechart, "_", day.substring(4)));

				Integer nums = carnumsByDayMap.get(day);
				if (null == nums) {
					nums = 0;
				}
				carnumsByDayMap.put(day, nums + Integer.parseInt(carnum));
			}
		}

		calendar.add(Calendar.DAY_OF_YEAR, -6);
		for (int i = 0; i < 7; i++) {
			String cusordate = CommUtils.shortDateFormat(calendar.getTime());
			Integer carnums = carnumsByDayMap.get(cusordate);
			if (null == carnums) {
				carnums = 0;
			}
			Element eledataset = eleLineChartData.addElement("dataset");
			eledataset.addAttribute("dataID", "onroadCar");
			eledataset.addAttribute("datadate", CommUtils.format(calendar
					.getTime(), "MM.dd"));
			eledataset
					.addAttribute("nums", String.valueOf(totalCars - carnums));
			eledataset.addAttribute("linechartID", "O_"
					+ cusordate.substring(4));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

	/**
	 * �����ֲ��ڶ�����ͼ
	 * 
	 * @param root
	 * @param totalCars
	 * @param date
	 * @param hour
	 */
	private void writeCarnumsOfAreaXml(Element root, int totalCars,
			String date, int hour, SysUser sysUser) {
		Element eleSecondPieData = root.addElement("secondPieData");
		List<Object[]> areaList = equCarService.findCarsGroupByAreaId(sysUser);
		Map<String, Integer> carnumsByAreaMap = new HashMap<String, Integer>();
		if (null != areaList && !areaList.isEmpty()) {
			for (Object[] objects : areaList) {
				// String areaid = objects[0].toString();
				String areaname = objects[1].toString().replace("����", "");
				Integer carnums = Integer.parseInt(objects[2].toString());
				carnumsByAreaMap.put(areaname, carnums);
			}
		} else {
			carnumsByAreaMap.put("", 0);
		}
		List<Object[]> list = exeAreaDistService.findDayCarsOfAreas(date, hour,
				sysUser);
		LinkedHashMap<String, Integer> carnumsByDateAndAreaAndLecodeMap = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, String> arealecodeMap = new LinkedHashMap<String, String>();
		if (null != list && !list.isEmpty()) {
			for (Object[] objects : list) {
				String lecode = objects[3].toString();
				String linechart = "";
				if (null != lecode) {
					if ("0".equals(lecode)) {
						linechart = "E";
					} else if ("1".equals(lecode)) {
						linechart = "W";
					} else if ("2".equals(lecode)) {
						linechart = "U";
					}
				}
				String day = objects[0].toString();
				String areaname = objects[1].toString().replace("����", "");
				String nums = objects[4].toString();
				if (logger.isDebugEnabled()) {
					logger.debug(day + "-" + areaname + "-" + linechart + " : "
							+ nums);
				}
				Element eledataset = eleSecondPieData.addElement("dataset");
				eledataset.addAttribute("label", areaname);
				eledataset.addAttribute("linechartID", CommUtils.getString(
						linechart, "_", day.substring(4)));
				eledataset.addAttribute("nums", nums);
				String key = CommUtils.getString(day, "_", areaname);
				Integer carnums = carnumsByDateAndAreaAndLecodeMap.get(key);
				if (null == carnums) {
					carnums = 0;
				}
				carnumsByDateAndAreaAndLecodeMap.put(key, carnums
						+ Integer.parseInt(nums));
				arealecodeMap.put(areaname, areaname);
			}
		} else {
			Element eledataset = eleSecondPieData.addElement("dataset");
			eledataset.addAttribute("label", "");
			eledataset.addAttribute("linechartID", "_");
			eledataset.addAttribute("nums", "0");
		}

		// ������ʾ��ʱͣ��������
		Collection<String> arealecodes = arealecodeMap.values();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -6);
		for (int i = 0; i < 7; i++) {
			if (!arealecodes.isEmpty()) {
				for (String areaname : arealecodes) {
					String day = CommUtils.shortDateFormat(calendar.getTime());
					Integer totalNums = carnumsByAreaMap.get(areaname);
					if (null == totalNums) {
						totalNums = 0;
					}
					String key = CommUtils.getString(day, "_", areaname);
					Integer carnums = carnumsByDateAndAreaAndLecodeMap.get(key);
					if (null == carnums) {
						carnums = 0;
					}
					Element eledataset = eleSecondPieData.addElement("dataset");
					eledataset.addAttribute("label", areaname);
					eledataset.addAttribute("linechartID", "O_"
							+ day.substring(4));
					eledataset.addAttribute("nums", String.valueOf(totalNums
							- carnums < 0 ? 0 : totalNums - carnums));
				}
			} else {
				Element eledataset = eleSecondPieData.addElement("dataset");
				eledataset.addAttribute("label", "");
				eledataset.addAttribute("linechartID", "O_");
				eledataset.addAttribute("nums", "0");
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

	public String yunliang() throws Exception {
		PrintWriter out = null;
		try {
			ActionContext context = ServletActionContext.getContext();
			context.getActionInvocation().getProxy().setExecuteResult(false);
			HttpServletResponse response = ServletActionContext.getResponse();
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("model");
			List<String> monthList = new ArrayList<String>();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -12);
			int currentMonth = calendar.get(Calendar.MONTH);
			for (int i = currentMonth - 12; i < currentMonth; i++) {
				calendar.add(Calendar.MONTH, 1);
				if (CommUtils.shortMonthFormat(calendar.getTime()).compareTo(
						"201201") < 0) {
					continue;
				}
				monthList.add(CommUtils.shortMonthFormat(calendar.getTime()));
			}
			// �Ա�������
			writeMsinosctamountXml(root, monthList);
			// ��·����
			writeCardataXml(root, monthList);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Pragma", "no-cache");
			OutputFormat of = new OutputFormat(" ", true);
			of.setEncoding("UTF-8");
			out = response.getWriter();
			XMLWriter xmlWriter = new XMLWriter(out, of);
			xmlWriter.write(document);
			xmlWriter.close();
			out.flush();
		} catch (Exception e) {
			logger.error("write xml error.", e);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}
	/**
	 * �����¶��Ա���������
	 * 
	 * @return
	 */
	public void writeDQMYcuAreaXml(Element root, SysUser sysUser) {
		try {
			Element eleLineCUData = root.addElement("CUData");

			BitCarcu bitCarcu = bitCarcuService.getBitCarcu(sysUser);
			Element dataset = eleLineCUData.addElement("dataset");

			dataset.addAttribute("MCU", bitCarcu == null ? "0" : String
					.valueOf(bitCarcu.getMcu()));
			dataset.addAttribute("QCU", bitCarcu == null ? "0" : String
					.valueOf(bitCarcu.getQcu()));
			dataset.addAttribute("YCU", bitCarcu == null ? "0" : String
					.valueOf(bitCarcu.getYcu()));
			// ���xml�ļ�

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SysBulletin> getSysBulletins() {
		return sysBulletins;
	}

	public void setSysBulletins(List<SysBulletin> sysBulletins) {
		this.sysBulletins = sysBulletins;
	}

	public int getCars() {
		return cars;
	}

	public void setCars(int cars) {
		this.cars = cars;
	}

	public Map getUserinfoMap() {
		return userinfoMap;
	}

	public void setUserinfoMap(Map userinfoMap) {
		this.userinfoMap = userinfoMap;
	}

	public List getBtList() {
		return btList;
	}

	public void setBtList(List btList) {
		this.btList = btList;
	}

	public Map getCurMonthMap() {
		return curMonthMap;
	}

	public void setCurMonthMap(Map curMonthMap) {
		this.curMonthMap = curMonthMap;
	}

	public Map getPrMap() {
		return prMap;
	}

	public void setPrMap(Map prMap) {
		this.prMap = prMap;
	}

	public Map getMshowidMap() {
		return mshowidMap;
	}

	public void setMshowidMap(Map mshowidMap) {
		this.mshowidMap = mshowidMap;
	}

	public Map getDfMap() {
		return dfMap;
	}

	public void setDfMap(Map dfMap) {
		this.dfMap = dfMap;
	}

	public Map getMailMap() {
		return mailMap;
	}

	public void setMailMap(Map mailMap) {
		this.mailMap = mailMap;
	}

	public String getTopage() {
		return topage;
	}

	public void setTopage(String topage) {
		this.topage = topage;
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -12);
		String month = CommUtils.shortMonthFormat(calendar.getTime());
		System.out.println(month);
		System.out.println(month.compareTo("201201"));
	}

	public List<SysNavimenu> getSysManageNavimenuList() {
		return sysManageNavimenuList;
	}

	public void setSysManageNavimenuList(List<SysNavimenu> sysManageNavimenuList) {
		this.sysManageNavimenuList = sysManageNavimenuList;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getContactStr() {
		return contactStr;
	}

	public void setContactStr(String contactStr) {
		this.contactStr = contactStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
