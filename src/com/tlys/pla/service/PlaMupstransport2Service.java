package com.tlys.pla.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DateCalendar;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaMupstransport2Dao;
import com.tlys.pla.model.PlaMupstransport2;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMenuService;

@Service
public class PlaMupstransport2Service extends _GenericService {
	@Autowired
	PlaMupstransport2Dao plaMupstransport2Dao;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;

	@Autowired
	PlaMupstransportdetService plaMupstransportdetService;
	@Autowired
	SysMenuService menuService;
	@Autowired
	DicMap dicMap;

	public void find(PlaMupstransport2 plaMupstransport2, PageCtr pageCtr) throws Exception {
		if (null == plaMupstransport2) {
			plaMupstransport2 = new PlaMupstransport2();
		}

		// 时间处理（12个月)
		String monthStart = plaMupstransport2.getMonthStart() != null ? plaMupstransport2
				.getMonthStart().trim() : "";
		String monthEnd = plaMupstransport2.getMonthEnd() != null ? plaMupstransport2.getMonthEnd()
				.trim() : "";
		// 两个时间全为空时以当前月份加一再前推12个月
		if ("".equals(monthStart) && "".equals(monthEnd)) {
			String monthNext = DateCalendar.getIMonth("", 1, "yyyy-MM");
			monthStart = DateCalendar.getIMonth(monthNext, -12, "yyyy-MM");
			monthEnd = monthNext;
		}
		// 超始时间不为空时后推12个月
		if (!"".equals(monthStart) && "".equals(monthEnd)) {
			monthEnd = DateCalendar.getIMonth(monthStart, 12, "yyyy-MM");
		}
		// 结束时间不为空时前推12个月
		if ("".equals(monthStart) && !"".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth(monthEnd, -12, "yyyy-MM");
		}

		plaMupstransport2.setMonthStart(monthStart);
		plaMupstransport2.setMonthEnd(monthEnd);

		// 权限处理,当前用户所能查看的计划状态解析
		String curCanStatuss = dicMap.getStatuss(plaMupstransport2.getStatuss(), "PLA_MUPS2");
		plaMupstransport2.setStatuss(curCanStatuss);

		// 区域公司数据查询权限
		if (null == plaMupstransport2.getAreaids() || "".equals(plaMupstransport2.getAreaids())) {// 区域公司id列表没有从前台传过来
			String areaids = getAuthAreaids();
			plaMupstransport2.setAreaids(areaids);
		}

		int totalRecord = plaMupstransport2Dao.getCount(plaMupstransport2);
		// System.out.println("PlaMupstransport2Service.find->totalRecord=="+totalRecord);

		pageCtr.setTotalRecord(totalRecord);
		plaMupstransport2Dao.find(plaMupstransport2, pageCtr);

		List<Object[]> list = pageCtr.getRecords();

		List pmusList = new ArrayList();
		for (Object[] objArr : list) {
			PlaMupstransport2 o2 = new PlaMupstransport2();
			o2.setAreaid((String) objArr[0]);
			o2.setMonth((String) objArr[1]);
			o2.setStatuss(curCanStatuss);
			List<PlaMupstransport2> objs = findByAdjust(o2);
			o2.setObjs(objs);
			pmusList.add(o2);
		}
		bdPlaMupDic(pmusList);
		pageCtr.setRecords(pmusList);
	}

	public List<PlaMupstransport2> findByAdjust(PlaMupstransport2 plaMupstransport2)
			throws Exception {

		List<PlaMupstransport2> list = plaMupstransport2Dao.findByAdjust(plaMupstransport2);
		bdPlaAmupDic(list);
		return list;
	}

	public PlaMupstransport2 load(String id) throws Exception {
		PlaMupstransport2 plaMupstransport2 = plaMupstransport2Dao.load(id);
		return plaMupstransport2;
	}

	public void save(PlaMupstransport2 plaMupstransport2) throws Exception {
		plaMupstransport2Dao.save(plaMupstransport2);
	}

	public String update(PlaMupstransport2 plaMupstransport2) throws Exception {
		// 要转换成的目标状态
		String toStatus = plaMupstransport2.getStatus();
		String planno = plaMupstransport2.getPlanno();
		String oStatuss = "";

		String reMsg = null;

		// 当前操作是否可以执行，正好和hasPrePlaStatus方法的返回值相反
		boolean canop = false;
		if (toStatus.equals("1")) {
			// 如果是要提交计划，则此计划当前状态一定是这两种：填报0，驳回2
			oStatuss = "0,2";
			canop = !plaMupstransport2Dao.hasPrePlaStatus(planno, oStatuss);
		} else if (toStatus.equals("2") || toStatus.equals("3")) {
			// 如果是要驳回或发布计划，则此计划当前状态一定是这种：提交1
			oStatuss = "1";
			canop = !plaMupstransport2Dao.hasPrePlaStatus(planno, oStatuss);
		}

		if (canop) {
			plaMupstransport2Dao.updateEntity(plaMupstransport2, planno);
			reMsg = "当前操作成功执行！";
		} else {
			reMsg = "由于本条调整计划之前的相关计划有未完成的操作，所以本次操作不能执行，请检查！";
		}
		return reMsg;
	}

	/**
	 * 删除一计划，删除主表，从表的删除由触发器来完成 删除前要检查是否当前是最后一次调整，否则不执行
	 * 
	 * @param plaMupstransport2
	 */
	public String delete(PlaMupstransport2 plaMupstransport2) {

		String reStr = "当前计划删除成功！";
		if (null == plaMupstransport2) {
			return "当前计划不存在，请刷新重试！";
		}
		String plano = plaMupstransport2.getPlanno();
		String maxPlano = plaMupstransport2Dao.getMaxPlano(plano);

		if (maxPlano.equals(plano)) {
			plaMupstransport2Dao.delete(plaMupstransport2);
		} else {
			reStr = "所要删除的计划不是当前计划的最后一次调整，不允许进行删除操作！";
		}
		return reStr;
	}

	/**
	 * 根据用户单位获取企业ID 匀不存在进返回null不对单位进行过滤
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getCorpid(String curCorpid, String curCorptab) throws Exception {
		String corpid = "";
		if (null != curCorptab && !"0".equals(curCorptab) && !"1".equals(curCorptab)) {
			if ("2".equals(curCorptab)) {// 企业
				corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// 驻厂办
				List<CtlLiaisonocorp> ctlLiaisonocorpList = ctlLiaisonocorpService.findAll();
				for (CtlLiaisonocorp obj : ctlLiaisonocorpList) {
					if (curCorpid.equals(obj.getBranchid())) {
						corpid = obj.getCorpid();
						break;
					}
				}
			}
		}
		return corpid;
	}

	/**
	 * 根据用户单位获取区域公司ID 匀不存在进返回null不对单位进行过滤
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getAreaid(String curCorpid, String curCorptab) throws Exception {
		String areaid = "";
		if (null != curCorptab) {
			if ("1".equals(curCorptab)) {// 区域公司
				areaid = curCorpid;
			}
			// 企业或 驻厂办 进入目前只能看到本企业数据
			// else if ("2".equals(curCorptab)) {// 企业
			// DicSinocorp obj = dicSinocorpService.load(curCorpid);
			// areaid = obj.getAreaid();
			// } else if ("3".equals(curCorptab)) {// 驻厂办
			// DicSinodepartment obj = dicSinodepartmentService
			// .load(curCorpid);
			// areaid = obj.getParentid();
			// }
		}
		return areaid;
	}

	/**
	 * 到货查询
	 * 
	 * @param curCorpid
	 * @param plaMupstransport2
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMupstransport2 plaMupstransport2) throws Exception {
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
		// PlaMupstransport2det plaMupstransportdet = new
		// PlaMupstransport2det();
		// plaMupstransportdet.setReceiverids(receiverids);
		// List plaMupstransports =
		// plaMupstransportdetService.find(plaMupstransportdet);
		String plannos = "";// CommUtils.getRestrictionsIn(plaMupstransports,
		// "planno");
		if ("".equals(plannos)) {
			plannos = "99999999";
		}
		// 查询所属编号内的计划
		plaMupstransport2.setPlannos(plannos);
		List list = plaMupstransport2Dao.find(plaMupstransport2);
		map.put("list", list);
		return map;
	}

	/**
	 * 翻译计划名称
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void bdPlaMupDic(List list) throws Exception {
		Map statusMap = dicMap.getStatusMap();
		Map areaMap = dicMap.getAreaMap();

		for (int i = 0; i < list.size(); i++) {
			// for (Iterator iter = list.iterator(); iter.hasNext();) {

			PlaMupstransport2 plaMup = (PlaMupstransport2) list.get(i);
			// System.out.println("PlaMupstransport2Service.bdPlaMupDic->plaMup.getStatus()=="+plaMup.getStatus());

			String areaid = plaMup.getAreaid();
			DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			String areaidDIC = area != null ? area.getShrinkname() : areaid;
			plaMup.setAreaidDIC(areaidDIC);

			String month = plaMup.getMonth();
			String monthEnd = month.substring(0, 4) + "年" + month.substring(4) + "月";
			plaMup.setMonthEnd(monthEnd);
		}
	}

	/**
	 * 翻译计划调整信息
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void bdPlaAmupDic(List list) throws Exception {
		Map statusMap = dicMap.getStatusMap();
		Map userMap = dicMap.getUserMap();// adjustnumberDIC
		for (int i = 0; i < list.size(); i++) {
			// for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMupstransport2 plaMup = (PlaMupstransport2) list.get(i);

			// 将本条计划前一条调整计划的状态信息注入
			if (i < list.size() - 1) {
				PlaMupstransport2 plaMupPre = (PlaMupstransport2) list.get(i + 1);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->plaMupPre.getAreaid()=="+plaMupPre.getAreaid());
				String preStatus = plaMupPre.getStatus();
				plaMup.setPreStatus(preStatus);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->preStatus=="+preStatus);
			}

			String adjustnumber = plaMup.getAdjustnumber().toString();// BeanUtils.getProperty(plaMup,
																		// "adjustnumber");
			String adjustnumberDIC = "第" + adjustnumber + "次调整";
			if ("0".equals(adjustnumber)) {
				adjustnumberDIC = "原计划";
			}
			plaMup.setAdjustnumberDIC(adjustnumberDIC);// BeanUtils.setProperty(plaMup,
														// "adjustnumberDIC",
														// adjustnumberDIC);
			String creator = plaMup.getCreator();// BeanUtils.getProperty(plaMup,
													// "creator");

			SysUser sysUser = (SysUser) userMap.get(creator);
			String creatorDIC = sysUser != null ? sysUser.getUsername() : creator;
			plaMup.setCreatorDIC(creatorDIC);// BeanUtils.setProperty(plaMup,
												// "creatorDIC", creatorDIC);

			String adjuster = plaMup.getAdjuster();// BeanUtils.getProperty(plaMup,
													// "adjuster");
			SysUser sysUser1 = (SysUser) userMap.get(adjuster);
			String adjusterDIC = sysUser1 != null ? sysUser1.getUsername() : adjuster;
			plaMup.setAdjusterDIC(adjusterDIC);// BeanUtils.setProperty(plaMup,
												// "adjusterDIC", adjusterDIC);
			String status = plaMup.getStatus();// BeanUtils.getProperty(plaMup,
												// "status");
			String statusDIC = (String) statusMap.get(status);
			// BeanUtils.setProperty(plaMup, "statusDIC", statusDIC);
			plaMup.setStatusDIC(statusDIC);
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
		Object[] corpidArr = CommUtils.getCorpId();
		Map corpMap = dicMap.getCorpMap();
		HashSet areaidSet = null;
		if (null == corpidArr || corpidArr.length == 0) {// 拥有全部权限
			Map areaMap = dicMap.getAreaMap();
			areaids = areaMap.keySet().toString().replace(" ", "").replace("[", "")
					.replace("]", "");
		} else {
			if (corpidArr.length == 1 && corpidArr[0].equals("-1")) {
				areaids = "-1";
			} else {
				areaidSet = new HashSet();
				for (int i = 0; i < corpidArr.length; i++) {
					DicSinocorp corp = (DicSinocorp) corpMap.get(corpidArr[i]);
					if (null != corp) {
						areaidSet.add(corp.getAreaid());
					}
				}
				areaids = areaidSet.toString().replaceAll(" ", "").replace("[", "")
						.replace("]", "");
			}
		}

		return areaids;
	}
}
