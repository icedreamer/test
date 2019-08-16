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
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaMspstransportDao;
import com.tlys.pla.model.PlaMspstransport;
import com.tlys.pla.model.PlaMspstransportdet;

@Service
public class PlaMspstransportService extends _GenericService {
	@Autowired
	PlaMspstransportDao PlaMspstransportDao;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;

	@Autowired
	PlaMspstransportdetService plaMspstransportdetService;
	@Autowired
	DicMap dicMap;

	public PlaMspstransport load(String id) throws Exception {
		PlaMspstransport PlaMspstransport = PlaMspstransportDao.load(id);
		return PlaMspstransport;
	}

	public void save(PlaMspstransport PlaMspstransport) throws Exception {
		PlaMspstransportDao.save(PlaMspstransport);
	}

	public void update(PlaMspstransport PlaMspstransport) throws Exception {
		PlaMspstransportDao.updateEntity(PlaMspstransport, PlaMspstransport
				.getPlanno());
	}

	/**
	 * @param PlaMspstransport
	 */
	public List<PlaMspstransport> find(PlaMspstransport PlaMspstransport)
			throws Exception {
		String monthStart = PlaMspstransport.getMonthStart() != null ? PlaMspstransport
				.getMonthStart().trim()
				: "";
		String monthEnd = PlaMspstransport.getMonthEnd() != null ? PlaMspstransport
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

		PlaMspstransport.setMonthStart(monthStart);
		PlaMspstransport.setMonthEnd(monthEnd);
		List<PlaMspstransport> list = PlaMspstransportDao
				.find(PlaMspstransport);
		dicMap.bdPlaMspDic(list);
		return list;
	}

	public List<PlaMspstransport> findAll() throws Exception {
		List<PlaMspstransport> list = PlaMspstransportDao.findAll();
		dicMap.bdPlaMspDic(list);
		return list;
	}

	public void delete(PlaMspstransport PlaMspstransport) {
		if (null == PlaMspstransport) {
			return;
		}
		PlaMspstransportDao.delete(PlaMspstransport);
	}

	/**
	 * 根据用户单位获取企业ID 匀不存在进返回null不对单位进行过滤
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getCorpid(String curCorpid, String curCorptab)
			throws Exception {
		String corpid = "";
		if (null != curCorptab && !"0".equals(curCorptab)&& !"1".equals(curCorptab)) {
			if ("2".equals(curCorptab)) {// 企业
				corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// 驻厂办
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
	 * 根据用户单位获取区域公司ID 匀不存在进返回null不对单位进行过滤
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String getAreaid(String curCorpid, String curCorptab)
			throws Exception {
		String areaid = "";
		if (null != curCorptab) {
			if ("1".equals(curCorptab)) {// 区域公司
				areaid = curCorpid;
			}
//企业或	驻厂办 进入目前只能看到本企业数据	
//			else if ("2".equals(curCorptab)) {// 企业
//				DicSinocorp obj = dicSinocorpService.load(curCorpid);
//				areaid = obj.getAreaid();
//			} else if ("3".equals(curCorptab)) {// 驻厂办
//				DicSinodepartment obj = dicSinodepartmentService
//						.load(curCorpid);
//				areaid = obj.getParentid();
//			}
		}
		return areaid;
	}

	/**
	 * 到货查询
	 * 
	 * @param curCorpid
	 * @param PlaMspstransport
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMspstransport PlaMspstransport)
			throws Exception {
		Map map = new HashMap();
		// 通过单位ID查找所对应收货人
		List dicCorprails = ctlCorpreceiverService
				.findCtlCorpreceiverByCorpid(curCorpid);
		// 收货人数组(包含本企业curCorpid)
		String receiverids = CommUtils.getRestrictionsIn(dicCorprails,
				"receiverid");
		if("".equals(receiverids)){
			receiverids=curCorpid;
		}else{
			receiverids+=","+curCorpid;
		}
		map.put("senders", receiverids);
		// 通过车站ID数组查询所有计划编号
		PlaMspstransportdet plaMspstransportdet = new PlaMspstransportdet();
		plaMspstransportdet.setReceiverids(receiverids);
		List plaMspstransports = plaMspstransportdetService
				.find(plaMspstransportdet);
		String plannos = CommUtils.getRestrictionsIn(plaMspstransports,
				"planno");
		if("".equals(plannos)){
			plannos="99999999";
		}
		// 查询所属编号内的计划
		PlaMspstransport.setPlannos(plannos);
		List list = this.find(PlaMspstransport);
		map.put("list", list);
		return map;
	}

}
