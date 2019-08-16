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

		// ʱ�䴦��12����)
		String monthStart = plaMupstransport2.getMonthStart() != null ? plaMupstransport2
				.getMonthStart().trim() : "";
		String monthEnd = plaMupstransport2.getMonthEnd() != null ? plaMupstransport2.getMonthEnd()
				.trim() : "";
		// ����ʱ��ȫΪ��ʱ�Ե�ǰ�·ݼ�һ��ǰ��12����
		if ("".equals(monthStart) && "".equals(monthEnd)) {
			String monthNext = DateCalendar.getIMonth("", 1, "yyyy-MM");
			monthStart = DateCalendar.getIMonth(monthNext, -12, "yyyy-MM");
			monthEnd = monthNext;
		}
		// ��ʼʱ�䲻Ϊ��ʱ����12����
		if (!"".equals(monthStart) && "".equals(monthEnd)) {
			monthEnd = DateCalendar.getIMonth(monthStart, 12, "yyyy-MM");
		}
		// ����ʱ�䲻Ϊ��ʱǰ��12����
		if ("".equals(monthStart) && !"".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth(monthEnd, -12, "yyyy-MM");
		}

		plaMupstransport2.setMonthStart(monthStart);
		plaMupstransport2.setMonthEnd(monthEnd);

		// Ȩ�޴���,��ǰ�û����ܲ鿴�ļƻ�״̬����
		String curCanStatuss = dicMap.getStatuss(plaMupstransport2.getStatuss(), "PLA_MUPS2");
		plaMupstransport2.setStatuss(curCanStatuss);

		// ����˾���ݲ�ѯȨ��
		if (null == plaMupstransport2.getAreaids() || "".equals(plaMupstransport2.getAreaids())) {// ����˾id�б�û�д�ǰ̨������
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
		// Ҫת���ɵ�Ŀ��״̬
		String toStatus = plaMupstransport2.getStatus();
		String planno = plaMupstransport2.getPlanno();
		String oStatuss = "";

		String reMsg = null;

		// ��ǰ�����Ƿ����ִ�У����ú�hasPrePlaStatus�����ķ���ֵ�෴
		boolean canop = false;
		if (toStatus.equals("1")) {
			// �����Ҫ�ύ�ƻ�����˼ƻ���ǰ״̬һ���������֣��0������2
			oStatuss = "0,2";
			canop = !plaMupstransport2Dao.hasPrePlaStatus(planno, oStatuss);
		} else if (toStatus.equals("2") || toStatus.equals("3")) {
			// �����Ҫ���ػ򷢲��ƻ�����˼ƻ���ǰ״̬һ�������֣��ύ1
			oStatuss = "1";
			canop = !plaMupstransport2Dao.hasPrePlaStatus(planno, oStatuss);
		}

		if (canop) {
			plaMupstransport2Dao.updateEntity(plaMupstransport2, planno);
			reMsg = "��ǰ�����ɹ�ִ�У�";
		} else {
			reMsg = "���ڱ��������ƻ�֮ǰ����ؼƻ���δ��ɵĲ��������Ա��β�������ִ�У����飡";
		}
		return reMsg;
	}

	/**
	 * ɾ��һ�ƻ���ɾ�������ӱ��ɾ���ɴ���������� ɾ��ǰҪ����Ƿ�ǰ�����һ�ε���������ִ��
	 * 
	 * @param plaMupstransport2
	 */
	public String delete(PlaMupstransport2 plaMupstransport2) {

		String reStr = "��ǰ�ƻ�ɾ���ɹ���";
		if (null == plaMupstransport2) {
			return "��ǰ�ƻ������ڣ���ˢ�����ԣ�";
		}
		String plano = plaMupstransport2.getPlanno();
		String maxPlano = plaMupstransport2Dao.getMaxPlano(plano);

		if (maxPlano.equals(plano)) {
			plaMupstransport2Dao.delete(plaMupstransport2);
		} else {
			reStr = "��Ҫɾ���ļƻ����ǵ�ǰ�ƻ������һ�ε��������������ɾ��������";
		}
		return reStr;
	}

	/**
	 * �����û���λ��ȡ��ҵID �Ȳ����ڽ�����null���Ե�λ���й���
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getCorpid(String curCorpid, String curCorptab) throws Exception {
		String corpid = "";
		if (null != curCorptab && !"0".equals(curCorptab) && !"1".equals(curCorptab)) {
			if ("2".equals(curCorptab)) {// ��ҵ
				corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// פ����
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
	 * �����û���λ��ȡ����˾ID �Ȳ����ڽ�����null���Ե�λ���й���
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getAreaid(String curCorpid, String curCorptab) throws Exception {
		String areaid = "";
		if (null != curCorptab) {
			if ("1".equals(curCorptab)) {// ����˾
				areaid = curCorpid;
			}
			// ��ҵ�� פ���� ����Ŀǰֻ�ܿ�������ҵ����
			// else if ("2".equals(curCorptab)) {// ��ҵ
			// DicSinocorp obj = dicSinocorpService.load(curCorpid);
			// areaid = obj.getAreaid();
			// } else if ("3".equals(curCorptab)) {// פ����
			// DicSinodepartment obj = dicSinodepartmentService
			// .load(curCorpid);
			// areaid = obj.getParentid();
			// }
		}
		return areaid;
	}

	/**
	 * ������ѯ
	 * 
	 * @param curCorpid
	 * @param plaMupstransport2
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMupstransport2 plaMupstransport2) throws Exception {
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
		// ��ѯ��������ڵļƻ�
		plaMupstransport2.setPlannos(plannos);
		List list = plaMupstransport2Dao.find(plaMupstransport2);
		map.put("list", list);
		return map;
	}

	/**
	 * ����ƻ�����
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
			String monthEnd = month.substring(0, 4) + "��" + month.substring(4) + "��";
			plaMup.setMonthEnd(monthEnd);
		}
	}

	/**
	 * ����ƻ�������Ϣ
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

			// �������ƻ�ǰһ�������ƻ���״̬��Ϣע��
			if (i < list.size() - 1) {
				PlaMupstransport2 plaMupPre = (PlaMupstransport2) list.get(i + 1);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->plaMupPre.getAreaid()=="+plaMupPre.getAreaid());
				String preStatus = plaMupPre.getStatus();
				plaMup.setPreStatus(preStatus);
				// System.out.println("PlaMupstransport2Service.bdPlaMupDic->preStatus=="+preStatus);
			}

			String adjustnumber = plaMup.getAdjustnumber().toString();// BeanUtils.getProperty(plaMup,
																		// "adjustnumber");
			String adjustnumberDIC = "��" + adjustnumber + "�ε���";
			if ("0".equals(adjustnumber)) {
				adjustnumberDIC = "ԭ�ƻ�";
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
	 * ��õ�ǰ�û��в���Ȩ�޵�����˾ID�� ע�⣬����û�����ĳ����˾��Ӧ��ĳ����ҵ��Ȩ�ޣ���˴��б��м�����������˾��ID
	 * ��������ζ�Ŵ��û�������˾����Ӧ��������ҵ����Ȩ�� ����Ϊ���ַ���������ζ��û��Ȩ��
	 * 
	 * @return
	 */
	public String getAuthAreaids() throws Exception {
		String areaids = "";
		Object[] corpidArr = CommUtils.getCorpId();
		Map corpMap = dicMap.getCorpMap();
		HashSet areaidSet = null;
		if (null == corpidArr || corpidArr.length == 0) {// ӵ��ȫ��Ȩ��
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
