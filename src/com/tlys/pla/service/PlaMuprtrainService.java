package com.tlys.pla.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaMuprtrainDao;
import com.tlys.pla.model.PlaDuprtrain;
import com.tlys.pla.model.PlaMuprtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaMuprtrainService extends _GenericService {
	@Autowired
	PlaMuprtrainDao plaMuprtrainDao;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicSinodepartmentService dicSinodepartmentService;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;
	@Autowired
	DicMap dicMap;
	@Autowired
	PlaDuprtrainService plaDuprtrainService;

	
	public PlaMuprtrain buildAuthSch(PlaMuprtrain plaMuprtrain)
			throws Exception {
		if (null == plaMuprtrain) {
			plaMuprtrain = new PlaMuprtrain();
		}

		if (null == plaMuprtrain.getCorpids() || "".equals(plaMuprtrain.getCorpids())) {
			String corpids = CommUtils.getCorpIds(); 
			plaMuprtrain.setCorpids(corpids);
			//System.out.println("PlaMuprtrainService.buildAuthSch->corpids=="+corpids);
		}
		
		return plaMuprtrain;
	}
	
	/**
	 * ���ݵ�ǰ�û���ݼ�ǰ̨�������Ĳ�ѯ���� ���ɱ��β�ѯ����Ҫ��corpid��areaid���������ѯ������
	 * �˷����ѷ�ֹ
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaMuprtrain buildAuthSchOld(PlaMuprtrain plaMuprtrain, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaMuprtrain) {
			plaMuprtrain = new PlaMuprtrain();
		}
		// ��ǰ̨û�д�����ѯ����
		if (null == plaMuprtrain.getAreaid()) {
			// �����û���λ��������˾Ȩ�޲�ѯ����
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaMuprtrain.setAreaid(areaid);
		}
		if (null == plaMuprtrain.getCorpid()) {
			// �����û���λ��ѯ������ҵ
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaMuprtrain.setCorpid(corpid);
		}
		return plaMuprtrain;
	}

	/**
	 * <li>�����û���λ��ȡ����˾ID</li>
	 * <li>��ǰ�û������ܲ��û�ʱ������null,��ʾ��������˾���й��ˣ���ѯ������˾�б�Ϊȫ��</li>
	 * <li>��ǰ�û���������˾ʱ�������û���ǰ��������˾ ��ID����ѯ������˾ֻ����һ��</li>
	 * <li>��ǰ�û�������ҵʱ�������û���ǰ��ҵ����Ӧ������˾��ID����ѯ������˾Ҳֻ����һ��</li>
	 * <li>��ǰ�û�����פ����ʱ�����ص�ǰ��λ��Ӧ������˾ID����ѯ������˾�б�Ϊһ��</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdAreaid(String curCorpid, String curCorptab)
			throws Exception {
		String areaid = "99999999";// ����ǰ�û�����Ϊnullʱ��û�в�ѯ����
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// �ܲ�����
				areaid = null;
			} else if ("1".equals(curCorptab)) {// ����˾,��ǰ�û��ĵ�λID��Ϊareaid
				areaid = curCorpid;
			} else if ("2".equals(curCorptab)) {// ��ҵ,��Ҫ���ҵ�ǰ�û���ҵ����Ӧ��areaid
				DicSinocorp corpObj = dicSinocorpService.load(curCorpid);
				areaid = corpObj.getAreaid();
			} else if ("3".equals(curCorptab)) {// פ����,��ͬ��Ҫ���ҵ�ǰ��λ��Ӧ��areaid
				DicSinodepartment obj = dicSinodepartmentService
						.load(curCorpid);
				areaid = obj.getParentid();
			}
		}
		return areaid;
	}

	/**
	 * <li>�����û���λ��ȡ��ҵID</li>
	 * <li>�ܲ��û���null</li>
	 * <li>����˾�û���null�����ڲ�ѯ�б�ʱ��Ӧ���ǵ�ǰ���ڵ�����˾</li>
	 * <li>��ҵ����ǰ���ڵ���ҵID</li>
	 * <li>פ���죺��ǰ��λ����Ӧ����ҵID</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdCorpid(String curCorpid, String curCorptab)
			throws Exception {
		
		String corpid = "99999999";// ����ǰ�û�����Ϊnullʱ��û�в�ѯ����
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// �ܲ�����
				corpid = null;
			} else if ("1".equals(curCorptab)) {// ����˾
				corpid = null;
			} else if ("2".equals(curCorptab)) {// ��ҵ
				corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// פ����,��Ҫ���ҵ�ǰ��λ��Ӧ��corpid
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

	public PlaMuprtrain load(String id) throws Exception {
		PlaMuprtrain plaMuprtrain = plaMuprtrainDao.load(id);
		return plaMuprtrain;
	}

	public void save(PlaMuprtrain plaMuprtrain) throws Exception {
		plaMuprtrainDao.save(plaMuprtrain);
	}

	public void update(PlaMuprtrain plaMuprtrain) throws Exception {
		plaMuprtrainDao.updateEntity(plaMuprtrain, plaMuprtrain.getId());
	}

	/**
	 * @param PlaMuprtrain
	 */
	public List<PlaMuprtrain> find(PlaMuprtrain plaMuprtrain) throws Exception {
		List<PlaMuprtrain> trainList = plaMuprtrainDao.find(plaMuprtrain);

		bdPlaMuprDic(trainList);
		return trainList;
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param PlaMuprtrain
	 * @return
	 * @throws Exception
	 */
	public void find(PlaMuprtrain plaMuprtrain, PageCtr pageCtr)
			throws Exception {
		rectDate(plaMuprtrain);
		int totalRecord = getPlaMuprtrainCount(plaMuprtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaMuprtrainDao.find(plaMuprtrain, pageCtr);
		bdPlaMuprDic(pageCtr.getRecords());
	}

	public int getPlaMuprtrainCount(final PlaMuprtrain plaMuprtrain) {
		int count = plaMuprtrainDao.getPlaMuprtrainCount(plaMuprtrain);
		return count;
	}

	/**
	 * @param PlaMuprtrain
	 */
	public List<PlaMuprtrain> findDistinct(PlaMuprtrain PlaMuprtrain)
			throws Exception {
		String hql = "select distinct new PlaMuprtrain(areaid,month) from "
				+ PlaMuprtrain.class.getName();
		hql += getCondition(PlaMuprtrain) + " order by month desc";
		List<PlaMuprtrain> trainList = plaMuprtrainDao.find(hql);
		bdPlaMuprDic(trainList);
		return trainList;
	}

	private String getCondition(PlaMuprtrain PlaMuprtrain) {
		String condition = " where 1=1 ";
		String areaid = null;
		String corpid = null;
		String month = null;
		// �ջ���
		String receiverids = null;
		if (null != PlaMuprtrain) {
			month = PlaMuprtrain.getMonth();
			areaid = PlaMuprtrain.getAreaid();
			corpid = PlaMuprtrain.getCorpid();
			receiverids = PlaMuprtrain.getReceiverids();
		}
		if (null != month && !"".equals(month)) {
			month = month.replace("-", "");
			condition += " and month='" + month + "'";
		}
		if (null != areaid && !"".equals(areaid)) {
			condition += " and areaid='" + areaid + "'";
		}
		if (null != corpid && !"".equals(corpid)) {
			condition += " and corpid='" + corpid + "'";
		}
		if (null != receiverids && receiverids.length() > 0) {
			condition += " and receiverid in (" + receiverids + ")";
		}
		return condition;
	}

	public List<PlaMuprtrain> findAll() throws Exception {
		List<PlaMuprtrain> list = plaMuprtrainDao.findAll();
		return list;
	}

	public void delete(PlaMuprtrain PlaMuprtrain) {
		if (null == PlaMuprtrain) {
			return;
		}
		plaMuprtrainDao.delete(PlaMuprtrain);
	}

	/**
	 * ������ѯ
	 * 
	 * @param curCorpid
	 * @param PlaMuprtrain
	 * @return
	 * @throws Exception
	 */
	public Map findByAccept(String curCorpid, PlaMuprtrain PlaMuprtrain)
			throws Exception {
		Map map = new HashMap();
		// ͨ����λID��������Ӧ�ջ���
		List dicCorprails = ctlCorpreceiverService
				.findCtlCorpreceiverByCorpid(curCorpid);
		// �ջ�������
		String receiverids = CommUtils.getSqlIn(dicCorprails, "receiverid");
		map.put("senders", receiverids);
		// ͨ����վID�����ѯ���мƻ����
		PlaMuprtrain.setReceiverids(receiverids);
		List list = this.findDistinct(PlaMuprtrain);
		map.put("list", list);
		return map;
	}

	/**
	 * �����ַ���������ת�� �����yyyy-mm��ʽ��תΪyyyymmdd;��֮����תΪ��֮��
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaMuprtrain pmt) throws Exception {
		if (null == pmt)
			return;
		String month = pmt.getMonth();
		String monthStr = pmt.getMonthStr();

		if (null != month) {
			monthStr = FormatUtil.rectDate59(month);
			pmt.setMonthStr(monthStr);
		}
		if (null != monthStr) {
			month = FormatUtil.rectDate59(monthStr);
			pmt.setMonth(month);
		}
	}

	/**
	 * �����ƻ�ҳ����ʾ����
	 * 
	 * @throws Exception
	 */
	public void bdPlaMuprDic(List<PlaMuprtrain> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMuprtrain obj = (PlaMuprtrain) iter.next();
			String areaid = obj.getAreaid();
			DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			String areaidDIC = area != null ? area.getShortname() : areaid;
			obj.setAreaidDIC(areaidDIC);
			String month = obj.getMonth();// BeanUtils.getProperty(obj,
			// "month");
			String monthEnd = month.substring(0, 4) + "��" + month.substring(4)
					+ "��";
			obj.setMonthEnd(monthEnd);

			String carkindid = obj.getCarkindid();
			DicCarkind carkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = carkind != null ? carkind.getShortname()
					: carkindid;
			obj.setCarkindidDIC(carkindidDIC);

			/*
			 * String startstationid = BeanUtils .getProperty(obj,
			 * "startstationid"); String startstationidDIC = (String)
			 * rwdepartmentMap .get(startstationid); BeanUtils.setProperty(obj,
			 * "startstationidDIC", startstationidDIC); String endstationid =
			 * BeanUtils.getProperty(obj, "endstationid"); String
			 * endstationidDIC = (String) rwdepartmentMap.get(endstationid);
			 * BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
			 */
		}
	}

	public void expExcel(PlaMuprtrain plaMuprtrain, HttpServletResponse response)
			throws Exception {
		// ��ѯ��ϸ�ƻ�����
		List<PlaMuprtrain> list = this.find(plaMuprtrain);

		Map tabDefineMap = new LinkedHashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��ͳ���복�������ƻ�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "��ͳ���복�������ƻ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname",
				"checkstatusDIC", "startstationname", "endstationname",
				"sendername", "receivername", "carkindidDIC", "rwkindname",
				"requestcars", "requestamount", "acceptcars", "acceptamount",
				"acceptcarno" };
		headArr[1] = new String[] { "����˾", "������ҵ", "����״̬", "��վ", "��վ", "������",
				"�ջ���", "����", "��·Ʒ��", "ԭ�ᳵ��", "ԭ�����", "��׼����", "��׼����", "��׼Ҫ���ֺ�" };
		// ����˾ ��ҵ���� ����״̬ ��վ ��վ ������ �ջ��� ���� ��·Ʒ�� ԭ�ᳵ�� ԭ����� ��׼���� ��׼���� ��׼Ҫ���ֺ�
		sheetMap.put("headArr", headArr);

		// ��׼Ҫ���ֺ�����
		String acceptcarnos = CommUtils.getRestrictionsIn(list, "acceptcarno");
		PlaDuprtrain plaDuprtrain = new PlaDuprtrain();
		plaDuprtrain.setAcceptcarnos(acceptcarnos);
		List<PlaDuprtrain> dups = plaDuprtrainService.find(plaDuprtrain);

		Map sheetMap1 = new HashMap();
		tabDefineMap.put("��ͳ���복�������ƻ�", sheetMap1);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap1.put("list", dups);
		// ����
		sheetMap1.put("title", "��ͳ���복�������ƻ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		headArr = new String[2][];
		headArr[0] = new String[] { "requestdate", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "loadcars",
				"loadamount", "mtotalcars", "acceptcarno" };
		headArr[1] = new String[] { "��������", "ԭ�ᳵ��", "ԭ�����", "��׼����", "��׼����",
				"��װ����", "��װ����", "���ۼ���ɳ���", "��׼Ҫ���ֺ�" };
		// װ������ ԭ�ᳵ�� ԭ����� ��׼���� ��׼���� ��װ���� ��װ���� ���ۼ���ɳ���
		sheetMap1.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "��ͳ���복�������ƻ�");

	}

}
