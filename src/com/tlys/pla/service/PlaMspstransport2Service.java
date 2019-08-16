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

		// ʱ�䴦��12����)
		String monthStart = plaMspstransport2.getMonthStart() != null ? plaMspstransport2
				.getMonthStart().trim() : "";
		String monthEnd = plaMspstransport2.getMonthEnd() != null ? plaMspstransport2.getMonthEnd()
				.trim() : "";
		// ����ʱ��ȫΪ��ʱ�Ե�ǰʱ��ǰ��12����
		if ("".equals(monthStart) && "".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth("", -12, "yyyy-MM");
			monthEnd = DateCalendar.getNowDate("yyyy-MM");
		}
		// ��ʼʱ�䲻Ϊ��ʱ����12����
		if (!"".equals(monthStart) && "".equals(monthEnd)) {
			monthEnd = DateCalendar.getIMonth(monthStart, 12, "yyyy-MM");
		}
		// ����ʱ�䲻Ϊ��ʱǰ��12����
		if ("".equals(monthStart) && !"".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth(monthEnd, -12, "yyyy-MM");
		}

		plaMspstransport2.setMonthStart(monthStart);
		plaMspstransport2.setMonthEnd(monthEnd);

		// Ȩ�޴���
		String curCanStatuss = dicMap.getStatuss(plaMspstransport2.getStatuss(), "PLA_MSPS2");
		plaMspstransport2.setStatuss(curCanStatuss);

		// System.out.println("PlaMspstransport2Service.find->==plaMspstransport2.getCorpids()"+plaMspstransport2.getCorpids());
		// ��ҵ����Ȩ��
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
		// Ҫת���ɵ�Ŀ��״̬
		String toStatus = plaMspstransport2.getStatus();
		String planno = plaMspstransport2.getPlanno();
		String oStatuss = "";

		String reMsg = null;

		// ��ǰ�����Ƿ����ִ�У����ú�hasPrePlaStatus�����ķ���ֵ�෴
		boolean canop = false;
		if (toStatus.equals("3")) {
			// �����Ҫ�����ƻ�����˼ƻ���ǰ״̬һ���������֣��0
			oStatuss = "0";
			canop = !plaMspstransport2Dao.hasPrePlaStatus(planno, oStatuss);
		} else {
			reMsg = "��Ҫ�ı�ļƻ�Ŀ��״̬���ԣ�����Ϊ����3)��";
		}

		if (canop) {
			plaMspstransport2Dao.updateEntity(plaMspstransport2, planno);
			reMsg = "��ǰ�����ɹ�ִ�У�";
		} else {
			reMsg = "���ڱ��������ƻ�֮ǰ����ؼƻ���û�з��������Ա��β�������ִ�У����飡";
		}
		return reMsg;
	}

	public String delete(PlaMspstransport2 plaMspstransport2) {
		String reStr = "��ǰ�ƻ�ɾ���ɹ���";
		if (null == plaMspstransport2) {
			return "��ǰ�ƻ������ڣ�";
		}
		String planno = plaMspstransport2.getPlanno();
		String maxPlanno = plaMspstransport2Dao.getMaxPlanno(planno);

		if (maxPlanno.equals(planno)) {
			plaMspstransport2Dao.delete(plaMspstransport2);
		} else {
			reStr = "�����ƻ�֮���е����ƻ������������ɾ������������ɾ�������ƻ�֮��ĵ����ƻ���";
		}
		return reStr;
	}

	/**
	 * ������ѯ
	 * 
	 * @param curCorpid
	 * @param plaMspstransport2
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMspstransport2 plaMspstransport2) throws Exception {
		Map map = new HashMap();
		// ͨ����λID��������Ӧ�ջ���
		List dicCorprails = ctlCorpreceiverService.findCtlCorpreceiverByCorpid(curCorpid);
		// �ջ�������(��������ҵcurCorpid)
		String receiverids = CommUtils.getRestrictionsIn(dicCorprails, "receiverid");
		if ("".equals(receiverids)) {
			receiverids = curCorpid;
		} else {
			receiverids += "," + curCorpid;
		}
		map.put("senders", receiverids);
		// ͨ����վID�����ѯ���мƻ����
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
		// ��ѯ��������ڵļƻ�
		plaMspstransport2.setPlannos(plannos);
		List list = plaMspstransport2Dao.find(plaMspstransport2);
		map.put("list", list);
		return map;
	}

	/**
	 * ����ƻ�����
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
			String monthEnd = month.substring(0, 4) + "��" + month.substring(4) + "��";
			plaMsp.setMonthEnd(monthEnd);
		}
	}

	/**
	 * ����ƻ�������Ϣ
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

			// �������ƻ�ǰһ�������ƻ���״̬��Ϣע��
			if (i < list.size() - 1) {
				PlaMspstransport2 plaMspPre = (PlaMspstransport2) list.get(i + 1);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->plaMupPre.getAreaid()=="+plaMupPre.getAreaid());
				String preStatus = plaMspPre.getStatus();
				plaMsp.setPreStatus(preStatus);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->preStatus=="+preStatus);
			}

			String adjustnumber = plaMsp.getAdjustnumber().toString();// BeanUtils.getProperty(obj,
			// "adjustnumber");
			String adjustnumberDIC = "��" + adjustnumber + "�ε���";
			if ("0".equals(adjustnumber)) {
				adjustnumberDIC = "ԭ�ƻ�";
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
	 * ��õ�ǰ�û��в���Ȩ�޵�����˾ID�� ע�⣬����û�����ĳ����˾��Ӧ��ĳ����ҵ��Ȩ�ޣ���˴��б��м�����������˾��ID
	 * ��������ζ�Ŵ��û�������˾����Ӧ��������ҵ����Ȩ�� ����Ϊ���ַ���������ζ��û��Ȩ��
	 * 
	 * @return
	 */
	public String getAuthAreaids() throws Exception {
		String areaids = "";
		String[] corpidArr = (String[]) CommUtils.getCorpId();
		Map corpMap = dicMap.getCorpMap();
		HashSet areaidSet = null;
		if (null == corpidArr || corpidArr.length == 0) {// ӵ��ȫ��Ȩ��
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
