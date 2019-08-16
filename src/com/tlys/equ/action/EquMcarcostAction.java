package com.tlys.equ.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicCarcost;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicCarcostService;
import com.tlys.equ.model.EquMcarcost;
import com.tlys.equ.model.EquMcarcostdet;
import com.tlys.equ.model.EquMcarcostdetitem;
import com.tlys.equ.service.EquCarService;
import com.tlys.equ.service.EquMcarcostService;
import com.tlys.sys.model.SysUser;

@Controller
@Component
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquMcarcostAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4317147244413661764L;

	private EquMcarcostdet equMcarcostdet;
	private EquMcarcostdetitem equMcarcostdetitem;

	private EquMcarcost equMcarcost;
	private Map<String, List<EquMcarcostdetitem>> equMcarcostdetitemsByCarnoMap;
	private Map<String, List<EquMcarcostdetitem>> equMcarcostdetitemsMap;
	private Map<String, EquMcarcostdetitem> equMcarcostdetitemMap;
	// 合计应收应付
	private Map<String, Double> totalDueCostByMonthMap;
	// 合计实收实付
	private Map<String, Double> totalRealCostByMonthMap;
	/**
	 * 月度应收应付结余
	 */
	private Map<String, Double> monthNeedBalanceMap;
	/**
	 * 月度实收实付结余
	 */
	private Map<String, Double> monthRealBalanceMap;

	// private Map<String, Double> yearNeedBalanceMap;
	// private Map<String, Double> yearRealBalanceMap;
	private Map<String, EquMcarcost> equMcarcostMap;

	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicCartype> dicCartypeMap;
	private List<DicCarcost> dicCarcosts;
	private Map<String, DicCarcost> dicCarcostMap;
	private List<String> monthList;

	private List<String> carnoList;
	private String pagetype;
	private List<EquMcarcostdetitem> equMcarcostdetitems;
	private List<String> incomeList;
	private List<String> paymentList;
	private List<Object[]> corpidList;
	private DicCarcost dicCarcost;
	@Autowired
	EquMcarcostService equMcarcostService;
	@Autowired
	DicMap dicMap;
	@Autowired
	DicCarcostService dicCarcostService;
	@Autowired
	EquCarService equCarService;

	public String list() throws Exception {
		String pageUrl = "/equ/equ-mcarcost!list.action";
		if (null == equMcarcostdet) {
			equMcarcostdet = new EquMcarcostdet();
		}
		// Calendar calendar = Calendar.getInstance();
		// equMcarcostdet.setYear(String.valueOf(calendar.get(Calendar.YEAR)));
		if (null == pageCtr) {
			pageCtr = new PageCtr<Object[]>();
			if (null != equMcarcostdet) {
				String schObjKey = "equMcarcostdet_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equMcarcostdet);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equMcarcostdet = (EquMcarcostdet) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		// 分页查询
		equMcarcostService.findEquMcarcostdets(equMcarcostdet, pageCtr);

		monthList = equMcarcostService.findMonths(equMcarcostdet);
		List<DicCarcost> dicCarcosts = dicCarcostService.findDicCarcost();
		if (null != dicCarcosts && !dicCarcosts.isEmpty()) {
			dicCarcostMap = new HashMap<String, DicCarcost>();
			for (int i = 0; i < dicCarcosts.size(); i++) {
				DicCarcost dicCarcost = dicCarcosts.get(i);
				dicCarcostMap.put(dicCarcost.getCostid(), dicCarcost);
			}
		}
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		dicCartypeMap = dicMap.getCartypeMap();
		List<Object[]> detList = pageCtr.getRecords();
		List<String> carnoList = null;
		if (null != detList && !detList.isEmpty()) {
			carnoList = new ArrayList<String>();
			for (Object[] objects : detList) {
				String carno = objects[1].toString();
				if (!carnoList.contains(carno)) {
					carnoList.add(carno);
				}
			}
		}
		List<EquMcarcostdetitem> equMcarcostdetitems = null;
		if (null != carnoList && !carnoList.isEmpty()) {
			equMcarcostdetitems = equMcarcostService.findEquMcarcostdetitem(equMcarcostdet,
					carnoList);
		}

		if (null != equMcarcostdetitems && !equMcarcostdetitems.isEmpty()) {
			monthNeedBalanceMap = new HashMap<String, Double>();
			monthRealBalanceMap = new HashMap<String, Double>();
			// yearNeedBalanceMap = new HashMap<String, Double>();
			// yearRealBalanceMap = new HashMap<String, Double>();
			totalDueCostByMonthMap = new HashMap<String, Double>();
			totalRealCostByMonthMap = new HashMap<String, Double>();
			paymentList = new ArrayList<String>();
			incomeList = new ArrayList<String>();
			equMcarcostdetitemMap = new HashMap<String, EquMcarcostdetitem>();
			for (EquMcarcostdetitem equMcarcostdetitem : equMcarcostdetitems) {
				String detid = equMcarcostdetitem.getDetid();
				String costid = equMcarcostdetitem.getCostid();
				String costname = equMcarcostdetitem.getCostname();
				String key = CommUtils.getString(detid, "_", costid);
				String month = equMcarcostdetitem.getMonth();
				Double dueamount = equMcarcostdetitem.getDueamount();
				Double realamount = equMcarcostdetitem.getRealamount();
				if (null == dueamount) {
					dueamount = 0d;
				}
				if (null == realamount) {
					realamount = 0d;
				}
				Double monthNeedBalance = monthNeedBalanceMap.get(detid);
				Double monthRealBalance = monthRealBalanceMap.get(detid);
				// String yearKey =
				// CommUtils.getString(equMcarcostdetitem.getDicSinocorp()
				// .getCorpid(), "_", equMcarcostdetitem.getCarno());
				// Double yearNeedBalance = yearNeedBalanceMap.get(yearKey);
				// Double yearRealBalance = yearRealBalanceMap.get(yearKey);

				Double totalDueCost = totalDueCostByMonthMap.get(month);
				Double totalRealCost = totalRealCostByMonthMap.get(month);
				if (null == totalDueCost) {
					totalDueCost = 0d;
				}
				if (null == totalRealCost) {
					totalRealCost = 0d;
				}
				if (null == monthNeedBalance) {
					monthNeedBalance = 0d;
				}
				if (null == monthRealBalance) {
					monthRealBalance = 0d;
				}
				// if (null == yearNeedBalance) {
				// yearNeedBalance = 0d;
				// }
				// if (null == yearRealBalance) {
				// yearRealBalance = 0d;
				// }
				if (null != costname && "租车费".equals(costname)) {
					monthNeedBalanceMap.put(detid, dueamount + monthNeedBalance);
					monthRealBalanceMap.put(detid, realamount + monthRealBalance);
					// yearNeedBalanceMap.put(yearKey, dueamount -
					// yearNeedBalance);
					// yearRealBalanceMap.put(yearKey, realamount -
					// yearRealBalance);
					totalDueCostByMonthMap.put(month, dueamount + totalDueCost);
					totalRealCostByMonthMap.put(month, realamount + totalRealCost);
				} else {
					monthNeedBalanceMap.put(detid, monthNeedBalance - dueamount);
					monthRealBalanceMap.put(detid, monthRealBalance - realamount);
					// yearNeedBalanceMap.put(yearKey, yearNeedBalance +
					// dueamount);
					// yearRealBalanceMap.put(yearKey, yearRealBalance +
					// realamount);
					totalDueCostByMonthMap.put(month, totalDueCost - dueamount);
					totalRealCostByMonthMap.put(month, totalRealCost - realamount);
				}
				equMcarcostdetitemMap.put(key, equMcarcostdetitem);
				if ("租车费".equals(costname)) {
					if (!incomeList.contains(costid)) {
						incomeList.add(costid);
					}
				} else {
					if (!paymentList.contains(costid)) {
						paymentList.add(costid);
					}
				}
			}
		}

		return "list";
	}

	public String list2() throws Exception {
		String pageUrl = "/equ/equ-mcarcost!list.action";
		if (null == equMcarcostdet) {
			equMcarcostdet = new EquMcarcostdet();
		}
		// Calendar calendar = Calendar.getInstance();
		// equMcarcostdet.setYear(String.valueOf(calendar.get(Calendar.YEAR)));
		if (null == pageCtr) {
			pageCtr = new PageCtr<Object[]>();
			if (null != equMcarcostdet) {
				String schObjKey = "equMcarcostdet_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equMcarcostdet);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equMcarcostdet = (EquMcarcostdet) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		// 分页查询
		equMcarcostService.findEquMcarcostdets(equMcarcostdet, pageCtr);

		monthList = equMcarcostService.findMonths(equMcarcostdet);
		// dicCarcosts = dicCarcostService.findDicCarcost();
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		dicCartypeMap = dicMap.getCartypeMap();
		List<Object[]> detList = pageCtr.getRecords();
		List<String> carnoList = null;
		if (null != detList && !detList.isEmpty()) {
			carnoList = new ArrayList<String>();
			for (Object[] objects : detList) {
				String carno = objects[1].toString();
				if (!carnoList.contains(carno)) {
					carnoList.add(carno);
				}
			}
		}
		List<EquMcarcostdetitem> equMcarcostdetitems = null;
		if (null != carnoList && !carnoList.isEmpty()) {
			equMcarcostdetitems = equMcarcostService.findEquMcarcostdetitem(equMcarcostdet,
					carnoList);
		}

		if (null != equMcarcostdetitems && !equMcarcostdetitems.isEmpty()) {
			monthNeedBalanceMap = new HashMap<String, Double>();
			monthRealBalanceMap = new HashMap<String, Double>();
			// yearNeedBalanceMap = new HashMap<String, Double>();
			// yearRealBalanceMap = new HashMap<String, Double>();
			paymentList = new ArrayList<String>();
			incomeList = new ArrayList<String>();
			equMcarcostdetitemsMap = new HashMap<String, List<EquMcarcostdetitem>>();
			for (EquMcarcostdetitem equMcarcostdetitem : equMcarcostdetitems) {
				String detid = equMcarcostdetitem.getDetid();
				String costname = equMcarcostdetitem.getCostname();
				String key = CommUtils.getString(detid, "_", costname);
				List<EquMcarcostdetitem> equMcarcostdetitemList = equMcarcostdetitemsMap.get(key);
				if (null == equMcarcostdetitemList) {
					equMcarcostdetitemList = new ArrayList<EquMcarcostdetitem>();
				}
				equMcarcostdetitemList.add(equMcarcostdetitem);
				Double monthNeedBalance = monthNeedBalanceMap.get(detid);
				Double monthRealBalance = monthRealBalanceMap.get(detid);
				String yearKey = CommUtils.getString(equMcarcostdetitem.getDicSinocorp()
						.getCorpid(), "_", equMcarcostdetitem.getCarno());
				// Double yearNeedBalance = yearNeedBalanceMap.get(yearKey);
				// Double yearRealBalance = yearRealBalanceMap.get(yearKey);
				if (null == monthNeedBalance) {
					monthNeedBalance = 0d;
				}
				if (null == monthRealBalance) {
					monthRealBalance = 0d;
				}
				// if (null == yearNeedBalance) {
				// yearNeedBalance = 0d;
				// }
				// if (null == yearRealBalance) {
				// yearRealBalance = 0d;
				// }
				if (null != costname && "租车费".equals(costname)) {
					monthNeedBalanceMap.put(detid, monthNeedBalance
							- equMcarcostdetitem.getDueamount());
					monthRealBalanceMap.put(detid, monthRealBalance
							- equMcarcostdetitem.getRealamount());
					// yearNeedBalanceMap.put(yearKey, yearNeedBalance
					// - equMcarcostdetitem.getDueamount());
					// yearRealBalanceMap.put(yearKey, yearRealBalance
					// - equMcarcostdetitem.getRealamount());
					if (!incomeList.contains(costname)) {
						incomeList.add(costname);
					}
				} else {
					monthNeedBalanceMap.put(detid, monthNeedBalance
							+ equMcarcostdetitem.getDueamount());
					monthRealBalanceMap.put(detid, monthRealBalance
							+ equMcarcostdetitem.getRealamount());
					// yearNeedBalanceMap.put(yearKey, yearNeedBalance
					// + equMcarcostdetitem.getDueamount());
					// yearRealBalanceMap.put(yearKey, yearRealBalance
					// + equMcarcostdetitem.getRealamount());
					if (!paymentList.contains(costname)) {
						paymentList.add(costname);
					}
				}
				equMcarcostdetitemsMap.put(key, equMcarcostdetitemList);
			}
		}
		return "list";
	}

	public String list1() throws Exception {
		String pageUrl = "/equ/equ-mcarcost!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<EquMcarcostdet>();
			if (null != equMcarcostdet) {
				String schObjKey = "equMcarcostdet_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equMcarcostdet);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equMcarcostdet = (EquMcarcostdet) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		// 分页查询通知公告
		equMcarcostService.findEquMcarcostdet(equMcarcostdet, pageCtr);
		List<EquMcarcostdet> equMcarcostdets = pageCtr.getRecords();
		List<String> detidList = null;
		List<String> monthlyidList = null;
		if (null != equMcarcostdets && !equMcarcostdets.isEmpty()) {
			detidList = new ArrayList<String>();
			monthlyidList = new ArrayList<String>();
			for (int i = 0; i < equMcarcostdets.size(); i++) {
				EquMcarcostdet equMcarcostdet = equMcarcostdets.get(i);
				String detid = equMcarcostdet.getDetid();
				if (!detidList.contains(detid)) {
					detidList.add(detid);
				}
				if (!monthlyidList.contains(equMcarcostdet.getMonthlyid())) {
					monthlyidList.add(equMcarcostdet.getMonthlyid());
				}
			}
		}
		String costids = null;
		if (null != equMcarcostdet) {
			costids = equMcarcostdet.getCostids();
		}
		List<EquMcarcostdetitem> equMcarcostdetitems = equMcarcostService.findEquMcarcostdetitem(
				detidList, costids);
		if (null != equMcarcostdetitems && !equMcarcostdetitems.isEmpty()) {
			equMcarcostdetitemsByCarnoMap = new HashMap<String, List<EquMcarcostdetitem>>();
			for (int i = 0; i < equMcarcostdetitems.size(); i++) {
				EquMcarcostdetitem equMcarcostdetitem = equMcarcostdetitems.get(i);
				String detid = equMcarcostdetitem.getDetid();
				List<EquMcarcostdetitem> equMcarcostdetitemList = equMcarcostdetitemsByCarnoMap
						.get(detid);
				if (null == equMcarcostdetitemList) {
					equMcarcostdetitemList = new ArrayList<EquMcarcostdetitem>();
				}
				equMcarcostdetitemList.add(equMcarcostdetitem);
				equMcarcostdetitemsByCarnoMap.put(detid, equMcarcostdetitemList);
			}
		}
		if (null != monthlyidList && !monthlyidList.isEmpty()) {
			List<EquMcarcost> equMcarcosts = equMcarcostService.findEquMcarcost(monthlyidList);
			if (null != equMcarcosts && !equMcarcosts.isEmpty()) {
				equMcarcostMap = new HashMap<String, EquMcarcost>();
				for (EquMcarcost equMcarcost : equMcarcosts) {
					equMcarcostMap.put(equMcarcost.getMonthlyid(), equMcarcost);
				}
			}
		}
		return "list";
	}

	/**
	 * 编辑页面，暂时取消使用！编辑页面改版
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		if (isNew) {
			return "input";
		}
		dicCarcosts = dicCarcostService.findDicCarcost();
		return "edit";
	}

	/**
	 * 新增费用 在选择页面输入企业及月度 后执行存储过程P_INIT_MCARCOST生成数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			SysUser sysUser = getCurUser();
			String corpid = equMcarcost.getCorpid();
			String month = equMcarcost.getMonth();
			String creator = sysUser.getLoginid();
			Object[] o = equMcarcostService.call_P_INIT_MCARCOST(corpid, month, creator);
			if (logger.isDebugEnabled()) {
				logger.debug("call P_INIT_MCARCOST 状态：" + o[0]);
				logger.debug("call P_INIT_MCARCOST 描述：" + o[1]);
			}
			String msgStr = "";
			if (null != o && o.length == 2) {
				msgStr = o[1].toString();
			}
			msg = new Msg(Msg.SUCCESS, msgStr);
		} else {
			long[] ids = equMcarcostdetitem.getIds();
			double[] dueamounts = equMcarcostdetitem.getDueamounts();
			double[] realmounts = equMcarcostdetitem.getRealamounts();
			if (null != ids && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					EquMcarcostdetitem equMcarcostdetitem = equMcarcostService.load(ids[i]);
					logger.debug("realamount : " + realmounts[i]);
					logger.debug("dueamount : " + dueamounts[i]);
					equMcarcostdetitem.setRealamount(realmounts[i]);
					equMcarcostdetitem.setDueamount(dueamounts[i]);
					equMcarcostService.update(equMcarcostdetitem);
				}
			}
			msg = new Msg(Msg.SUCCESS, "保存成功");

		}

		return MSG;
	}

	/**
	 * 修改表单的值
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		// checkbox选中的记录进行修改
		String[] carnos = request.getParameterValues("carnos");
		Enumeration<String> e = request.getParameterNames();
		if (null != carnos && carnos.length > 0) {
			for (int i = 0; i < carnos.length; i++) {
				String value = carnos[i];
				// 32000000_XFB00001_01(checkbox value)
				// String value = request.getParameter(carsinfo);
				if (logger.isDebugEnabled()) {
					logger.debug("value : " + value);
				}
				if (null == value || "".equals(value)) {
					continue;
				}
				String[] checkboxValueArry = value.split("_");
				if (checkboxValueArry.length != 3) {
					continue;
				}
				String costid = checkboxValueArry[2];
				String corpid = checkboxValueArry[0];
				String carno = checkboxValueArry[1];
				if (logger.isDebugEnabled()) {
					logger.debug("costid : " + costid);
					logger.debug("carno : " + carno);
					logger.debug("corpid : " + corpid);
				}
				// corpid_carno_rentcorpid_costid
				while (e.hasMoreElements()) {
					String elem = e.nextElement();
					// 32350000-201209-XFB00001_03_due
					// 32000000-201209-XFB00001_01_real
					if (null == elem || "".equals(elem)) {
						continue;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("params : " + elem);
					}
					String[] inputValueArray = elem.split("-");
					if (inputValueArray.length != 3) {
						continue;
					}
					String eleCorpid = inputValueArray[0];
					String eleMonth = inputValueArray[1];
					String eleCarnoAndCost = inputValueArray[2];
					String eleCarno = eleCarnoAndCost.split("_")[0];
					String eleRealAmount = eleCarnoAndCost.split("_")[2];
					// 将checkbox的value中的企业编号，车号及费用项目
					// 分别作为整个表单中的输入框名字，值用request.getParameter获取
					if (eleCorpid.equals(corpid) && eleCarno.equals(carno)) {
						String detid = CommUtils.getString(eleCorpid, "-", eleMonth, "-",
								eleCarnoAndCost.split("_")[0]);
						EquMcarcostdetitem equMcarcostdetitem = equMcarcostService
								.getEquMcarcostdetitem(detid, costid);

						if (null != equMcarcostdetitem) {
							// 实际值
							if (eleRealAmount.equals("real")) {
								equMcarcostdetitem.setRealamount(Double.parseDouble(request
										.getParameter(elem)));
							} else if (eleRealAmount.equals("due")) {
								// 应收应付值
								equMcarcostdetitem.setDueamount(Double.parseDouble(request
										.getParameter(elem)));
							}
							equMcarcostService.update(equMcarcostdetitem);
						}
					}
				}
			}
		}
		msg = new Msg(Msg.SUCCESS, "操作成功");
		return MSG;
	}

	/**
	 * 左侧查询面板
	 * 
	 * @return
	 * @throws Exception
	 */
	public String left() throws Exception {
		corpidList = equMcarcostService.findCorpids();
		dicCartypeMap = dicMap.getCartypeMap();
		dicCarcosts = dicCarcostService.findDicCarcost();
		return "left";
	}

	/**
	 * 提交(提交后，将不能修改编辑)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String refer() throws Exception {
		String corpid = equMcarcost.getCorpid();
		String month = equMcarcost.getMonth();
		EquMcarcost equMcarcost = equMcarcostService.getEquMcarcost(corpid, month);
		equMcarcost.setCommittime(new Date());
		equMcarcost.setStatus("1");
		equMcarcostService.update(equMcarcost);
		msg = new Msg(Msg.SUCCESS, "提交成功");
		return MSG;
	}

	/**
	 * 点击进入提交页面，选择企业和月度后 执行refer方法提交
	 * 
	 * @return
	 * @throws Exception
	 */
	public String redercondi() throws Exception {
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		Set<String> corpidSet = dicSinocorpMap.keySet();
		monthList = equMcarcostService.findMonthsOfCorp(corpidSet);
		return "condition";
	}

	/**
	 * 导出
	 * 
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HSSFWorkbook workbook = new HSSFWorkbook();
		List<EquMcarcostdetitem> equMcarcostdetitems = equMcarcostService.findEquMcarcostdetitem();
		List<String> monthList = equMcarcostService.findMonths(equMcarcostdet);
		List<Object[]> equMcarcostdetList = equMcarcostService.findEquMcarcostdets(equMcarcostdet);
		Map<String, DicSinocorp> dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		equMcarcostService.exportExcel(workbook, equMcarcostdetitems, monthList,
				equMcarcostdetList, dicSinocorpMap);
		ExcelUtil.writeWorkbook(response, workbook, "车辆租赁费用表");
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * 根据企业查询车号，暂时取消该方法，根据要求编辑页面已改版
	 * 
	 * @return
	 * @throws Exception
	 */
	public String carnos() throws Exception {
		carnoList = equMcarcostService.findCars(equMcarcostdet);
		if (null != pagetype && !"".equals(pagetype)) {
			return "carnos";
		}
		return "carnosel";
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("EQU_MCARCOST");
	}

	public String loadItem() throws Exception {
		equMcarcostdetitems = equMcarcostService.findEquMcarcostdetitem(equMcarcostdet.getDetid());
		dicCarcosts = dicCarcostService.findDicCarcost();
		return "items";
	}

	/**
	 * 根据企业查询月份 暂时取消该方法，根据要求编辑页面已改版
	 * 
	 * @return
	 * @throws Exception
	 */
	public String months() throws Exception {
		String corpid = equMcarcost.getCorpid();
		monthList = equMcarcostService.findMonthsByCorpid(corpid);
		return "months";
	}

	/**
	 * 点击进入批量更新页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String batch() throws Exception {
		List<DicCarcost> dicCarcosts = dicCarcostService.findDicCarcost();
		if (null != dicCarcosts && !dicCarcosts.isEmpty()) {
			dicCarcostMap = new HashMap<String, DicCarcost>();
			for (int i = 0; i < dicCarcosts.size(); i++) {
				DicCarcost dicCarcost = dicCarcosts.get(i);
				dicCarcostMap.put(dicCarcost.getCostid(), dicCarcost);
			}
		}
		return "batch";
	}

	public EquMcarcostdet getEquMcarcostdet() {
		return equMcarcostdet;
	}

	public void setEquMcarcostdet(EquMcarcostdet equMcarcostdet) {
		this.equMcarcostdet = equMcarcostdet;
	}

	public Map<String, List<EquMcarcostdetitem>> getEquMcarcostdetitemsByCarnoMap() {
		return equMcarcostdetitemsByCarnoMap;
	}

	public void setEquMcarcostdetitemsByCarnoMap(
			Map<String, List<EquMcarcostdetitem>> equMcarcostdetitemsByCarnoMap) {
		this.equMcarcostdetitemsByCarnoMap = equMcarcostdetitemsByCarnoMap;
	}

	public EquMcarcostdetitem getEquMcarcostdetitem() {
		return equMcarcostdetitem;
	}

	public void setEquMcarcostdetitem(EquMcarcostdetitem equMcarcostdetitem) {
		this.equMcarcostdetitem = equMcarcostdetitem;
	}

	public EquMcarcost getEquMcarcost() {
		return equMcarcost;
	}

	public void setEquMcarcost(EquMcarcost equMcarcost) {
		this.equMcarcost = equMcarcost;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public Map<String, DicCartype> getDicCartypeMap() {
		return dicCartypeMap;
	}

	public void setDicCartypeMap(Map<String, DicCartype> dicCartypeMap) {
		this.dicCartypeMap = dicCartypeMap;
	}

	public List<DicCarcost> getDicCarcosts() {
		return dicCarcosts;
	}

	public void setDicCarcosts(List<DicCarcost> dicCarcosts) {
		this.dicCarcosts = dicCarcosts;
	}

	public List<String> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}

	public Map<String, EquMcarcost> getEquMcarcostMap() {
		return equMcarcostMap;
	}

	public void setEquMcarcostMap(Map<String, EquMcarcost> equMcarcostMap) {
		this.equMcarcostMap = equMcarcostMap;
	}

	public Map<String, List<EquMcarcostdetitem>> getEquMcarcostdetitemsMap() {
		return equMcarcostdetitemsMap;
	}

	public void setEquMcarcostdetitemsMap(
			Map<String, List<EquMcarcostdetitem>> equMcarcostdetitemsMap) {
		this.equMcarcostdetitemsMap = equMcarcostdetitemsMap;
	}

	public Map<String, Double> getMonthNeedBalanceMap() {
		return monthNeedBalanceMap;
	}

	public Map<String, Double> getMonthRealBalanceMap() {
		return monthRealBalanceMap;
	}

	public void setMonthNeedBalanceMap(Map<String, Double> monthNeedBalanceMap) {
		this.monthNeedBalanceMap = monthNeedBalanceMap;
	}

	public void setMonthRealBalanceMap(Map<String, Double> monthRealBalanceMap) {
		this.monthRealBalanceMap = monthRealBalanceMap;
	}

	// public Map<String, Double> getYearNeedBalanceMap() {
	// return yearNeedBalanceMap;
	// }
	//
	// public void setYearNeedBalanceMap(Map<String, Double> yearNeedBalanceMap)
	// {
	// this.yearNeedBalanceMap = yearNeedBalanceMap;
	// }

	// public Map<String, Double> getYearRealBalanceMap() {
	// return yearRealBalanceMap;
	// }
	//
	// public void setYearRealBalanceMap(Map<String, Double> yearRealBalanceMap)
	// {
	// this.yearRealBalanceMap = yearRealBalanceMap;
	// }

	public List<String> getCarnoList() {
		return carnoList;
	}

	public void setCarnoList(List<String> carnoList) {
		this.carnoList = carnoList;
	}

	public String getPagetype() {
		return pagetype;
	}

	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}

	public List<EquMcarcostdetitem> getEquMcarcostdetitems() {
		return equMcarcostdetitems;
	}

	public void setEquMcarcostdetitems(List<EquMcarcostdetitem> equMcarcostdetitems) {
		this.equMcarcostdetitems = equMcarcostdetitems;
	}

	public List<String> getIncomeList() {
		return incomeList;
	}

	public void setIncomeList(List<String> incomeList) {
		this.incomeList = incomeList;
	}

	public List<String> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<String> paymentList) {
		this.paymentList = paymentList;
	}

	public List<Object[]> getCorpidList() {
		return corpidList;
	}

	public void setCorpidList(List<Object[]> corpidList) {
		this.corpidList = corpidList;
	}

	public Map<String, EquMcarcostdetitem> getEquMcarcostdetitemMap() {
		return equMcarcostdetitemMap;
	}

	public void setEquMcarcostdetitemMap(Map<String, EquMcarcostdetitem> equMcarcostdetitemMap) {
		this.equMcarcostdetitemMap = equMcarcostdetitemMap;
	}

	public DicCarcost getDicCarcost() {
		return dicCarcost;
	}

	public void setDicCarcost(DicCarcost dicCarcost) {
		this.dicCarcost = dicCarcost;
	}

	public Map<String, DicCarcost> getDicCarcostMap() {
		return dicCarcostMap;
	}

	public void setDicCarcostMap(Map<String, DicCarcost> dicCarcostMap) {
		this.dicCarcostMap = dicCarcostMap;
	}

	public Map<String, Double> getTotalDueCostByMonthMap() {
		return totalDueCostByMonthMap;
	}

	public void setTotalDueCostByMonthMap(Map<String, Double> totalDueCostByMonthMap) {
		this.totalDueCostByMonthMap = totalDueCostByMonthMap;
	}

	public Map<String, Double> getTotalRealCostByMonthMap() {
		return totalRealCostByMonthMap;
	}

	public void setTotalRealCostByMonthMap(Map<String, Double> totalRealCostByMonthMap) {
		this.totalRealCostByMonthMap = totalRealCostByMonthMap;
	}

}
