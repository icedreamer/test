package com.tlys.pla.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DateCalendar;
import com.tlys.comm.util.DicMap;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaAmupstransportDao;
import com.tlys.pla.model.PlaAmupstransport;
import com.tlys.pla.model.PlaAmupstransportdet;

@Service
public class PlaAmupstransportService extends _GenericService {
	@Autowired
	PlaAmupstransportDao plaAmupstransportDao;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicSinodepartmentService dicSinodepartmentService;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	PlaAmupstransportdetService PlaAmupstransportdetService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;
	@Autowired
	DicMap dicMap;

	public PlaAmupstransport load(String id) throws Exception {
		PlaAmupstransport PlaAmupstransport = plaAmupstransportDao.load(id);
		return PlaAmupstransport;
	}

	public void save(PlaAmupstransport PlaAmupstransport) throws Exception {
		plaAmupstransportDao.save(PlaAmupstransport);
	}

	public void update(PlaAmupstransport PlaAmupstransport) throws Exception {
		plaAmupstransportDao.updateEntity(PlaAmupstransport, PlaAmupstransport
				.getPlanno());
	}

	/**
	 * �����û���λ��ȡ����˾ID �Ȳ����ڽ�����null���Ե�λ���й���
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getAreaid(String curCorpid, String curCorptab)
			throws Exception {
		String corpid = "";
		if (null != curCorptab) {
			if("0".equals(curCorptab)) {// �ܲ�����
				corpid = curCorpid;
			}else if ("1".equals(curCorptab)) {// ����˾
				corpid = curCorpid;
			}else if ("2".equals(curCorptab)) {// ��ҵ
				DicSinocorp obj = dicSinocorpService.load(curCorpid);
				corpid = obj.getAreaid();
			}else if ("3".equals(curCorptab)) {// פ����
				DicSinodepartment obj = dicSinodepartmentService
						.load(curCorpid);
				corpid = obj.getParentid();
			}
		}
		return corpid;
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
		if (null != curCorptab && !"0".equals(curCorptab)&& !"1".equals(curCorptab)) {
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

	/**
	 * @param PlaAmupstransport
	 */
	public List<PlaAmupstransport> find(PlaAmupstransport PlaAmupstransport)
			throws Exception {
		String monthStart = PlaAmupstransport.getMonthStart() != null ? PlaAmupstransport
				.getMonthStart().trim()
				: "";
		String monthEnd = PlaAmupstransport.getMonthEnd() != null ? PlaAmupstransport
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

		PlaAmupstransport.setMonthStart(monthStart);
		PlaAmupstransport.setMonthEnd(monthEnd);
		List<PlaAmupstransport> trainList = plaAmupstransportDao
				.find(PlaAmupstransport);
		dicMap.bdPlaShowDic(trainList);
		return trainList;
	}

	public List<PlaAmupstransport> findAll() throws Exception {
		List<PlaAmupstransport> list = plaAmupstransportDao.findAll();
		dicMap.bdPlaShowDic(list);
		return list;
	}

	public void delete(PlaAmupstransport PlaAmupstransport) {
		if (null == PlaAmupstransport) {
			return;
		}
		plaAmupstransportDao.delete(PlaAmupstransport);
	}

	/**
	 * ������ѯ
	 * 
	 * @param curCorpid
	 * @param PlaAmupstransport
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid,
			PlaAmupstransport PlaAmupstransport) throws Exception {
		Map map = new HashMap();
		if (null == PlaAmupstransport.getPlannos()) {
			// ͨ����λID��������Ӧ�ջ���
			List dicCorprails = ctlCorpreceiverService
					.findCtlCorpreceiverByCorpid(curCorpid);
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
			PlaAmupstransportdet PlaAmupstransportdet = new PlaAmupstransportdet();
			PlaAmupstransportdet.setReceiverids(receiverids);
			List PlaAmupstransports = PlaAmupstransportdetService
					.find(PlaAmupstransportdet);
			String plannos = CommUtils.getRestrictionsIn(PlaAmupstransports,
					"planno");
			if("".equals(plannos)){
				plannos="99999999";
			}
			// ��ѯ��������ڵļƻ�
			PlaAmupstransport.setPlannos(plannos);
		}
		List list = this.find(PlaAmupstransport);
		map.put("list", list);
		return map;
	}

	/**
	 * ���ô洢���̣�ͬ��������ƻ���ԭ�ƶȶ�Ӧ��ID��
	 * ��Ӱ��ǰ̨���飬�����¿��̵߳ķ�ʽ
	 * @param monthStr
	 */
	public void syncPlaAmup(final String monthStr) {
		try {
			new Thread(){
				public void run(){
					plaAmupstransportDao.syncPlaAmup(monthStr);
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
