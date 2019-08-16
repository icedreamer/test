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
import com.tlys.pla.dao.PlaDtrtrainDao;
import com.tlys.pla.model.PlaDtrtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaDtrtrainService extends _GenericService {
	@Autowired
	PlaDtrtrainDao plaDtrtrainDao;
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
	
	
	public PlaDtrtrain buildAuthSch(PlaDtrtrain plaDtrtrain)
			throws Exception {

		if (null == plaDtrtrain) {
			plaDtrtrain = new PlaDtrtrain();
		}
		
		if (null == plaDtrtrain.getCorpids() || "".equals(plaDtrtrain.getCorpids())) {
			String corpids = CommUtils.getCorpIds(); 
			plaDtrtrain.setCorpids(corpids);
		}
		return plaDtrtrain;
	}


	/**
	 * ���ݵ�ǰ�û���ݼ�ǰ̨�������Ĳ�ѯ���� ���ɱ��β�ѯ����Ҫ��corpid��areaid���������ѯ������
	 * �˷����ѷ�ֹ
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaDtrtrain buildAuthSchOld(PlaDtrtrain plaDtrtrain, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaDtrtrain) {
			plaDtrtrain = new PlaDtrtrain();
		}
		// ��ǰ̨û�д�����ѯ����
		if (null == plaDtrtrain.getAreaid()) {
			// �����û���λ��������˾Ȩ�޲�ѯ����
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaDtrtrain.setAreaid(areaid);
		}
		if (null == plaDtrtrain.getCorpid()) {
			// �����û���λ��ѯ������ҵ
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaDtrtrain.setCorpid(corpid);
		}
		return plaDtrtrain;
	}

	/**
	 * �����û���λ��ȡ����˾ID �Ȳ����ڽ�����null���Ե�λ���й���
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
	 * �����û���λ��ȡ��ҵID �Ȳ����ڽ�����null���Ե�λ���й���
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

	public PlaDtrtrain load(String id) throws Exception {
		PlaDtrtrain plaDtrtrain = plaDtrtrainDao.load(id);
		return plaDtrtrain;
	}

	public void save(PlaDtrtrain plaDtrtrain) throws Exception {
		plaDtrtrainDao.save(plaDtrtrain);
	}

	public void update(PlaDtrtrain plaDtrtrain) throws Exception {
		plaDtrtrainDao.updateEntity(plaDtrtrain, plaDtrtrain.getId());
	}

	/**
	 * @param PlaDtrtrain
	 */
	public List<PlaDtrtrain> find(PlaDtrtrain plaDtrtrain) throws Exception {
		List<PlaDtrtrain> trainList = plaDtrtrainDao.find(plaDtrtrain);
		bdPlaDtrDic(trainList);
		return trainList;
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param PlaDtrtrain
	 * @return
	 * @throws Exception
	 */
	public void find(PlaDtrtrain plaDtrtrain, PageCtr pageCtr) throws Exception {
		rectDate(plaDtrtrain);
		int totalRecord = getPlaDtrtrainCount(plaDtrtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaDtrtrainDao.find(plaDtrtrain, pageCtr);
		bdPlaDtrDic(pageCtr.getRecords());
	}

	public int getPlaDtrtrainCount(final PlaDtrtrain plaDtrtrain) {
		int count = plaDtrtrainDao.getPlaDtrtrainCount(plaDtrtrain);
		return count;
	}

	/**
	 * @param plaDtrtrain
	 */
	public List<PlaDtrtrain> findDistinct(PlaDtrtrain plaDtrtrain)
			throws Exception {
		String hql = "select distinct new PlaDtrtrain(areaid,month) from "
				+ PlaDtrtrain.class.getName();
		hql += getCondition(plaDtrtrain) + " order by month desc";
		List<PlaDtrtrain> trainList = plaDtrtrainDao.find(hql);
		// dicMap.bdPlaMuprDic(trainList);
		return trainList;
	}

	private String getCondition(PlaDtrtrain plaDtrtrain) {
		String condition = " where 1=1 ";
		String areaid = null;
		String corpid = null;
		String month = null;
		// �ջ���
		String receiverids = null;
		if (null != plaDtrtrain) {
			month = plaDtrtrain.getMonth();
			areaid = plaDtrtrain.getAreaid();
			corpid = plaDtrtrain.getCorpid();
			receiverids = plaDtrtrain.getReceiverids();
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

	public List<PlaDtrtrain> findAll() throws Exception {
		List<PlaDtrtrain> list = plaDtrtrainDao.findAll();
		return list;
	}

	public void delete(PlaDtrtrain plaDtrtrain) {
		if (null == plaDtrtrain) {
			return;
		}
		plaDtrtrainDao.delete(plaDtrtrain);
	}

	/**
	 * �����ַ���������ת�� �����yyyy-mm��ʽ��תΪyyyymmdd;��֮����תΪ��֮��
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaDtrtrain pmt) throws Exception {
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
	private void bdPlaDtrDic(List<PlaDtrtrain> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// Map rwdepartmentMap = dicMap.getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaDtrtrain obj = (PlaDtrtrain) iter.next();
			// String areaid = obj.getAreaid();
			// DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			// String areaidDIC = area != null ? area.getShortname() : areaid;
			// obj.setAreaidDIC(areaidDIC);
			String month = obj.getMonth();// BeanUtils.getProperty(obj,
			// "month");
			String monthEnd = month.substring(0, 4) + "��" + month.substring(4)
					+ "��";
			// BeanUtils.setProperty(obj, "monthEnd", monthEnd);
			obj.setMonthEnd(monthEnd);
			String carkindid = obj.getCarkindid();// BeanUtils.getProperty(obj,
			// "carkindid");
			DicCarkind carkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = carkind != null ? carkind.getShortname()
					: carkindid;
			obj.setCarkindidDIC(carkindidDIC);
			obj.setAreaidDIC(((DicAreacorp) areaMap.get(obj.getAreaid()))
					.getShortname());
			// BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC);
			// String startstationid = BeanUtils.getProperty(obj,
			// "startstationid");
			// String startstationidDIC = (String)
			// rwdepartmentMap.get(startstationid);
			// BeanUtils.setProperty(obj, "startstationidDIC",
			// startstationidDIC);
			// String endstationid = BeanUtils.getProperty(obj, "endstationid");
			// String endstationidDIC = (String)
			// rwdepartmentMap.get(endstationid);
			// BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
		}
	}

	public void expExcel(PlaDtrtrain plaDtrtrain, HttpServletResponse response)
			throws Exception {
		String kind = "�շ�ͳ��";
		if ("1".equals(plaDtrtrain.getProductkind())) {
			kind = "��ͳ��";
		}
		// ��ѯ��ϸ�ƻ�����
		List<PlaDtrtrain> list = this.find(plaDtrtrain);

		Map tabDefineMap = new LinkedHashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put(kind + "׷�Ӽ������ƻ�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", kind + "׷�Ӽ������ƻ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname", "loadtdate",
				"startstationname", "endstationname", "sendername",
				"receivername", "carkindidDIC", "rwkindname", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "loadcars",
				"loadamount", "acceptcarno" };
		headArr[1] = new String[] { "����˾", "��ҵ����", "װ������", "��վ", "��վ", "������",
				"�ջ���", "����", "��·Ʒ��", "ԭ�ᳵ��", "ԭ�����", "��׼����", "��׼����", "��װ����",
				"��װ����", "��׼Ҫ���ֺ�"  };

		// ��ҵ��� װ������ ��վ ��վ ������ �ջ��� ���� ��·Ʒ�� ԭ�ᳵ�� ԭ����� ��׼���� ��׼���� ��װ���� ��װ����
		sheetMap.put("headArr", headArr);
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, kind + "׷�Ӽ������ƻ�");

	}

}
