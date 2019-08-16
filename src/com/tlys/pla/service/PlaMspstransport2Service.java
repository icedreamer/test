package com.tlys.pla.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DateCalendar;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaMspstransport2Dao;
import com.tlys.pla.model.PlaMspstransport2;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMenuService;

@Service
public class PlaMspstransport2Service extends _GenericService {
	@Autowired
	PlaMspstransport2Dao plaMspstransport2Dao;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;

	@Autowired
	PlaMspstransportdetService plaMspstransportdetService;
	@Autowired
	SysMenuService menuService;
	@Autowired
	DicMap dicMap;

	public void find(PlaMspstransport2 plaMspstransport2, PageCtr pageCtr) throws Exception {
		if (null == plaMspstransport2) {
			plaMspstransport2 = new PlaMspstransport2();
		}

		// 时间处理（12个月)
		String monthStart = plaMspstransport2.getMonthStart() != null ? plaMspstransport2
				.getMonthStart().trim() : "";
		String monthEnd = plaMspstransport2.getMonthEnd() != null ? plaMspstransport2.getMonthEnd()
				.trim() : "";
		// 两个时间全为空时以当前时间前推12个月
		if ("".equals(monthStart) && "".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth("", -12, "yyyy-MM");
			monthEnd = DateCalendar.getNowDate("yyyy-MM");
		}
		// 超始时间不为空时后推12个月
		if (!"".equals(monthStart) && "".equals(monthEnd)) {
			monthEnd = DateCalendar.getIMonth(monthStart, 12, "yyyy-MM");
		}
		// 结束时间不为空时前推12个月
		if ("".equals(monthStart) && !"".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth(monthEnd, -12, "yyyy-MM");
		}

		plaMspstransport2.setMonthStart(monthStart);
		plaMspstransport2.setMonthEnd(monthEnd);

		// 权限处理
		String curCanStatuss = dicMap.getStatuss(plaMspstransport2.getStatuss(), "PLA_MSPS2");
		plaMspstransport2.setStatuss(curCanStatuss);

		// System.out.println("PlaMspstransport2Service.find->==plaMspstransport2.getCorpids()"+plaMspstransport2.getCorpids());
		// 企业数据权限
		// if(null==plaMspstransport2.getCorpids() ||
		// "".equals(plaMspstransport2.getCorpids())){
		// log.debug("CommUtils.getCorpIds() : " + CommUtils.getCorpIds());
		// plaMspstransport2.setCorpids(CommUtils.getCorpIds());
		// }

		int totalRecord = plaMspstransport2Dao.getCount(plaMspstransport2);

		pageCtr.setTotalRecord(totalRecord);

		plaMspstransport2Dao.find(plaMspstransport2, pageCtr);

		List<Object[]> list = pageCtr.getRecords();

		List pmspList = new ArrayList();
		for (Object[] objArr : list) {
			PlaMspstransport2 o2 = new PlaMspstransport2();
			o2.setCorpid((String) objArr[0]);
			o2.setMonth((String) objArr[1]);
			o2.setStatuss(curCanStatuss);
			List<PlaMspstransport2> objs = findByAdjust(o2);
			o2.setObjs(objs);
			pmspList.add(o2);
		}
		bdPlaMspDic(pmspList);
		pageCtr.setRecords(pmspList);
	}

	public List<PlaMspstransport2> findByAdjust(PlaMspstransport2 plaMspstransport2)
			throws Exception {
		List<PlaMspstransport2> list = plaMspstransport2Dao.findByAdjust(plaMspstransport2);
		bdPlaAmspDic(list);
		return list;
	}

	public PlaMspstransport2 load(String id) throws Exception {
		PlaMspstransport2 plaMspstransport2 = plaMspstransport2Dao.load(id);
		return plaMspstransport2;
	}

	public void save(PlaMspstransport2 plaMspstransport2) throws Exception {
		plaMspstransport2Dao.save(plaMspstransport2);
	}

	// public void update(PlaMspstransport2 plaMspstransport2) throws Exception
	// {
	// plaMspstransport2Dao.updateEntity(plaMspstransport2, plaMspstransport2
	// .getPlanno());
	// }

	public String update(PlaMspstransport2 plaMspstransport2) throws Exception {
		// 要转换成的目标状态
		String toStatus = plaMspstransport2.getStatus();
		String planno = plaMspstransport2.getPlanno();
		String oStatuss = "";

		String reMsg = null;

		// 当前操作是否可以执行，正好和hasPrePlaStatus方法的返回值相反
		boolean canop = false;
		if (toStatus.equals("3")) {
			// 如果是要发布计划，则此计划当前状态一定是这两种：填报0
			oStatuss = "0";
			canop = !plaMspstransport2Dao.hasPrePlaStatus(planno, oStatuss);
		} else {
			reMsg = "所要改变的计划目标状态不对（必须为发布3)。";
		}

		if (canop) {
			plaMspstransport2Dao.updateEntity(plaMspstransport2, planno);
			reMsg = "当前操作成功执行！";
		} else {
			reMsg = "由于本条调整计划之前的相关计划还没有发布，所以本次操作不能执行，请检查！";
		}
		return reMsg;
	}

	public String delete(PlaMspstransport2 plaMspstransport2) {
		String reStr = "当前计划删除成功！";
		if (null == plaMspstransport2) {
			return "当前计划不存在！";
		}
		String planno = plaMspstransport2.getPlanno();
		String maxPlanno = plaMspstransport2Dao.getMaxPlanno(planno);

		if (maxPlanno.equals(planno)) {
			plaMspstransport2Dao.delete(plaMspstransport2);
		} else {
			reStr = "本条计划之后有调整计划，不允许进行删除操作，请先删除本条计划之后的调整计划。";
		}
		return reStr;
	}

	/**
	 * 到货查询
	 * 
	 * @param curCorpid
	 * @param plaMspstransport2
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMspstransport2 plaMspstransport2) throws Exception {
		Map map = new HashMap();
		// 通过单位ID查找所对应收货人
		List dicCorprails = ctlCorpreceiverService.findCtlCorpreceiverByCorpid(curCorpid);
		// 收货人数组(包含本企业curCorpid)
		String receiverids = CommUtils.getRestrictionsIn(dicCorprails, "receiverid");
		if ("".equals(receiverids)) {
			receiverids = curCorpid;
		} else {
			receiverids += "," + curCorpid;
		}
		map.put("senders", receiverids);
		// 通过车站ID数组查询所有计划编号
		// PlaMspstransport2det plaMspstransportdet = new
		// PlaMspstransport2det();
		// plaMspstransportdet.setReceiverids(receiverids);
		// List plaMspstransports =
		// plaMspstransportdetService.find(plaMspstransportdet);
		String plannos = "";// CommUtils.getRestrictionsIn(plaMspstransports,
		// "planno");
		if ("".equals(plannos)) {
			plannos = "99999999";
		}
		// 查询所属编号内的计划
		plaMspstransport2.setPlannos(plannos);
		List list = plaMspstransport2Dao.find(plaMspstransport2);
		map.put("list", list);
		return map;
	}

	/**
	 * 翻译计划名称
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void bdPlaMspDic(List list) throws Exception {
		Map statusMap = dicMap.getStatusMap();
		Map corpMap = dicMap.getCorpMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMspstransport2 plaMsp = (PlaMspstransport2) iter.next();
			String corpid = plaMsp.getCorpid();
			DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
			String corpidDIC = corp != null ? corp.getShrinkname() : corpid;
			plaMsp.setCorpidDIC(corpidDIC);

			String month = plaMsp.getMonth();
			String monthEnd = month.substring(0, 4) + "年" + month.substring(4) + "月";
			plaMsp.setMonthEnd(monthEnd);
		}
	}

	/**
	 * 翻译计划调整信息
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void bdPlaAmspDic(List list) throws Exception {
		Map statusMap = dicMap.getStatusMap();
		Map userMap = dicMap.getAllUserMap();// adjustnumberDIC
		for (int i = 0; i < list.size(); i++) {
			// for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMspstransport2 plaMsp = (PlaMspstransport2) list.get(i);

			// 将本条计划前一条调整计划的状态信息注入
			if (i < list.size() - 1) {
				PlaMspstransport2 plaMspPre = (PlaMspstransport2) list.get(i + 1);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->plaMupPre.getAreaid()=="+plaMupPre.getAreaid());
				String preStatus = plaMspPre.getStatus();
				plaMsp.setPreStatus(preStatus);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->preStatus=="+preStatus);
			}

			String adjustnumber = plaMsp.getAdjustnumber().toString();// BeanUtils.getProperty(obj,
			// "adjustnumber");
			String adjustnumberDIC = "第" + adjustnumber + "次调整";
			if ("0".equals(adjustnumber)) {
				adjustnumberDIC = "原计划";
			}
			plaMsp.setAdjustnumberDIC(adjustnumberDIC);// BeanUtils.setProperty(obj,
			// "adjustnumberDIC",
			// adjustnumberDIC);
			String creator = plaMsp.getCreator();// BeanUtils.getProperty(obj,
			// "creator");
			SysUser sysUser = (SysUser) userMap.get(creator);
			String creatorDIC = sysUser != null ? sysUser.getUsername() : creator;
			plaMsp.setCreatorDIC(creatorDIC);// BeanUtils.setProperty(obj,
			// "creatorDIC", creatorDIC);
			String adjuster = plaMsp.getAdjuster();// BeanUtils.getProperty(obj,
			// "adjuster");
			SysUser sysUser1 = (SysUser) userMap.get(adjuster);
			String adjusterDIC = sysUser1 != null ? sysUser1.getUsername() : adjuster;
			plaMsp.setAdjusterDIC(adjusterDIC);// BeanUtils.setProperty(obj,
			// "adjusterDIC", adjusterDIC);
			// String status = BeanUtils.getProperty(obj, "status");
			// String statusDIC = (String) statusMap.get(status);
			// BeanUtils.setProperty(obj, "statusDIC", statusDIC);
		}
	}

	/**
	 * 获得当前用户有操作权限的区域公司ID串 注意，如果用户具有某区域公司对应的某个企业的权限，则此处列表中即包含此区域公司的ID
	 * 但并不意味着此用户对区域公司所对应的所有企业都有权限 返回为空字符串，则意味着没有权限
	 * 
	 * @return
	 */
	public String getAuthAreaids() throws Exception {
		String areaids = "";
		String[] corpidArr = (String[]) CommUtils.getCorpId();
		Map corpMap = dicMap.getCorpMap();
		HashSet areaidSet = null;
		if (null == corpidArr || corpidArr.length == 0) {// 拥有全部权限
			Map areaMap = dicMap.getAreaMap();
			areaids = areaMap.keySet().toString().replace(" ", "").replace("[", "")
					.replace("]", "");
		} else {
			areaidSet = new HashSet();
			for (int i = 0; i < corpidArr.length; i++) {
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpidArr[i]);
				areaidSet.add(corp.getAreaid());
			}

			areaids = areaidSet.toString().replaceAll(" ", "").replace("[", "").replace("]", "");
		}
		return areaids;
	}
}
