package com.tlys.pla.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DateCalendar;
import com.tlys.comm.util.DicMap;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaAmspstransportDao;
import com.tlys.pla.model.PlaAmspstransport;
import com.tlys.pla.model.PlaAmspstransportdet;

@Service
public class PlaAmspstransportService extends _GenericService {
	@Autowired
	PlaAmspstransportDao plaAmspstransportDao;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;

	@Autowired
	PlaAmspstransportdetService plaAmspstransportdetService;
	@Autowired
	DicMap dicMap;

	public PlaAmspstransport load(String id) throws Exception {
		PlaAmspstransport PlaAmspstransport = plaAmspstransportDao.load(id);
		return PlaAmspstransport;
	}

	public void save(PlaAmspstransport plaAmspstransport) throws Exception {
		plaAmspstransportDao.save(plaAmspstransport);
	}

	public void update(PlaAmspstransport plaAmspstransport) throws Exception {
		plaAmspstransportDao.updateEntity(plaAmspstransport, plaAmspstransport
				.getPlanno());
	}

	/**
	 * @param PlaAmspstransport
	 */
	public List<PlaAmspstransport> find(PlaAmspstransport plaAmspstransport)
			throws Exception {
		String monthStart = plaAmspstransport.getMonthStart() != null ? plaAmspstransport
				.getMonthStart().trim()
				: "";
		String monthEnd = plaAmspstransport.getMonthEnd() != null ? plaAmspstransport
				.getMonthEnd().trim()
				: "";
		if ("".equals(monthStart) && "".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth("", -2, "yyyy-MM");
			monthEnd = DateCalendar.getNowDate("yyyy-MM");
		}
		if (!"".equals(monthStart) && "".equals(monthEnd)) {
			monthEnd = DateCalendar.getIMonth(monthStart, 2, "yyyy-MM");
		}
		if ("".equals(monthStart) && !"".equals(monthEnd)) {
			monthStart = DateCalendar.getIMonth(monthEnd, -2, "yyyy-MM");
		}
		plaAmspstransport.setMonthStart(monthStart);
		plaAmspstransport.setMonthEnd(monthEnd);
		List<PlaAmspstransport> list = plaAmspstransportDao
				.find(plaAmspstransport);
		dicMap.bdPlaMspDic(list);
		return list;
	}

	public List<PlaAmspstransport> findAll() throws Exception {
		List<PlaAmspstransport> list = plaAmspstransportDao.findAll();
		dicMap.bdPlaMspDic(list);
		return list;
	}

	public void delete(PlaAmspstransport plaAmspstransport) {
		if (null == plaAmspstransport) {
			return;
		}
		plaAmspstransportDao.delete(plaAmspstransport);
	}

	/**
	 * �����û���λ��ȡ��ҵID �Ȳ����ڽ�����null���Ե�λ���й���
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getCorpid(String curCorpid, String curCorptab)
			throws Exception {
		String corpid = "";
		if (null != curCorptab && !"0".equals(curCorptab)
				&& !"1".equals(curCorptab)) {
			if ("2".equals(curCorptab)) {// ��ҵ
				corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// פ����
				List<CtlLiaisonocorp> ctlLiaisonocorpList = ctlLiaisonocorpService
						.findAll();
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

	public String getAreaid(String curCorpid, String curCorptab)
			throws Exception {
		String areaid = "";
		if (null != curCorptab) {
			if ("1".equals(curCorptab)) {// ����˾
				areaid = curCorpid;
			}
		}
		return areaid;
	}

	/**
	 * ������ѯ
	 * 
	 * @param curCorpid
	 * @param PlaAmspstransport
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid,
			PlaAmspstransport plaAmspstransport) throws Exception {
		Map map = new HashMap();
		List list = new ArrayList();
		// ͨ����λID��������Ӧ�ջ���
		List dicCorprails = ctlCorpreceiverService
				.findCtlCorpreceiverByCorpid(curCorpid);
		if (dicCorprails.size() == 0) {
			map.put("senders", "");
			map.put("list", list);
			return map;
		}
		// �ջ�������
		String receiverids = CommUtils.getRestrictionsIn(dicCorprails,
				"receiverid");
		if("".equals(receiverids)){
			receiverids=curCorpid;
		}else{
			receiverids+=","+curCorpid;
		}
		map.put("senders", receiverids);
		// ͨ����վID�����ѯ���мƻ����
		PlaAmspstransportdet plaAmspstransportdet = new PlaAmspstransportdet();
		plaAmspstransportdet.setReceiverids(receiverids);
		List plaAmspstransports = plaAmspstransportdetService
				.find(plaAmspstransportdet);
		if (plaAmspstransports.size() == 0) {
			map.put("list", list);
			return map;
		}
		String plannos = CommUtils.getRestrictionsIn(plaAmspstransports,
				"planno");
		if("".equals(plannos)){
			plannos="99999999";
		}
		// ��ѯ��������ڵļƻ�
		plaAmspstransport.setPlannos(plannos);
		list = this.find(plaAmspstransport);
		map.put("list", list);
		return map;
	}

}
