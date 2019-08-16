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
import com.tlys.pla.dao.PlaMupstransportDao;
import com.tlys.pla.model.PlaMupstransport;
import com.tlys.pla.model.PlaMupstransportdet;

@Service
public class PlaMupstransportService extends _GenericService {
	@Autowired
	PlaMupstransportDao plaMupstransportDao;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicSinodepartmentService dicSinodepartmentService;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	PlaMupstransportdetService plaMupstransportdetService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;
	@Autowired
	DicMap dicMap;

	public PlaMupstransport load(String id) throws Exception {
		PlaMupstransport plaMupstransport = plaMupstransportDao.load(id);
		return plaMupstransport;
	}

	public void save(PlaMupstransport plaMupstransport) throws Exception {
		plaMupstransportDao.save(plaMupstransport);
	}

	public void update(PlaMupstransport plaMupstransport) throws Exception {
		plaMupstransportDao.updateEntity(plaMupstransport, plaMupstransport
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
		if (null != curCorptab && !"0".equals(curCorptab)) {
			if ("1".equals(curCorptab)) {// ����˾
				corpid = curCorpid;
			} else if ("2".equals(curCorptab)) {// ��ҵ
				DicSinocorp obj = dicSinocorpService.load(curCorpid);
				corpid = obj.getAreaid();
			} else if ("3".equals(curCorptab)) {// פ����
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

	/**
	 * @param plaMupstransport
	 */
	public List<PlaMupstransport> find(PlaMupstransport plaMupstransport)
			throws Exception {
		String monthStart = plaMupstransport.getMonthStart() != null ? plaMupstransport
				.getMonthStart().trim()
				: "";
		String monthEnd = plaMupstransport.getMonthEnd() != null ? plaMupstransport
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
		plaMupstransport.setMonthStart(monthStart);
		plaMupstransport.setMonthEnd(monthEnd);
		List<PlaMupstransport> trainList = plaMupstransportDao
				.find(plaMupstransport);

		dicMap.bdPlaShowDic(trainList);
		return trainList;
	}

	public List<PlaMupstransport> findAll() throws Exception {
		List<PlaMupstransport> list = plaMupstransportDao.findAll();
		dicMap.bdPlaShowDic(list);
		return list;
	}

	public void delete(PlaMupstransport plaMupstransport) {
		if (null == plaMupstransport) {
			return;
		}
		plaMupstransportDao.delete(plaMupstransport);
	}

	/**
	 * ������ѯ
	 * 
	 * @param curCorpid
	 * @param plaMupstransport
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMupstransport plaMupstransport)
			throws Exception {
		Map map = new HashMap();
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
		PlaMupstransportdet plaMupstransportdet = new PlaMupstransportdet();
		plaMupstransportdet.setReceiverids(receiverids);
		List plaMupstransports = plaMupstransportdetService
				.find(plaMupstransportdet);
		String plannos = CommUtils.getRestrictionsIn(plaMupstransports,
				"planno");
		if("".equals(plannos)){
			plannos="99999999";
		}
		// ��ѯ��������ڵļƻ�
		plaMupstransport.setPlannos(plannos);
		List list = this.find(plaMupstransport);
		map.put("list", list);
		return map;
	}
}
