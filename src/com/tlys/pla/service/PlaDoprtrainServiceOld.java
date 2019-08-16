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
import com.tlys.pla.dao.PlaDoprtrainDaoOld;
import com.tlys.pla.model.PlaDoprtrainOld;
import com.tlys.sys.model.SysUser;

@Service
public class PlaDoprtrainServiceOld extends _GenericService {
	@Autowired
	PlaDoprtrainDaoOld plaDoprtrainDaoOld;
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

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param PlaDoprtrainOld
	 * @return
	 * @throws Exception
	 */
	public void find(PlaDoprtrainOld plaDoprtrainOld, PageCtr pageCtr)
			throws Exception {
		rectDate(plaDoprtrainOld);
		int totalRecord = getPlaDoprtrainCount(plaDoprtrainOld);
		pageCtr.setTotalRecord(totalRecord);
		plaDoprtrainDaoOld.find(plaDoprtrainOld, pageCtr);
		bdPlaDoprDic(pageCtr.getRecords());
	}

	public int getPlaDoprtrainCount(final PlaDoprtrainOld plaDoprtrainOld) {
		int count = plaDoprtrainDaoOld.getPlaDoprtrainCount(plaDoprtrainOld);
		return count;
	}

	/**
	 * ���ݵ�ǰ�û���ݼ�ǰ̨�������Ĳ�ѯ���� ���ɱ��β�ѯ����Ҫ��corpid��areaid���������ѯ������
	 * 
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaDoprtrainOld buildAuthSch(PlaDoprtrainOld plaDoprtrainOld, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaDoprtrainOld) {
			plaDoprtrainOld = new PlaDoprtrainOld();
		}
		// ��ǰ̨û�д�����ѯ����
		if (null == plaDoprtrainOld.getAreaid()) {
			// �����û���λ��������˾Ȩ�޲�ѯ����
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaDoprtrainOld.setAreaid(areaid);
		}
		if (null == plaDoprtrainOld.getCorpid()) {
			// �����û���λ��ѯ������ҵ
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaDoprtrainOld.setCorpid(corpid);
		}
		return plaDoprtrainOld;
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

	public PlaDoprtrainOld load(String id) throws Exception {
		PlaDoprtrainOld plaDoprtrainOld = plaDoprtrainDaoOld.load(id);
		return plaDoprtrainOld;
	}

	public void save(PlaDoprtrainOld plaDoprtrainOld) throws Exception {
		plaDoprtrainDaoOld.save(plaDoprtrainOld);
	}

	public void update(PlaDoprtrainOld plaDoprtrainOld) throws Exception {
		plaDoprtrainDaoOld.updateEntity(plaDoprtrainOld, plaDoprtrainOld.getId());
	}

	/**
	 * @param PlaDoprtrainOld
	 */
	public List<PlaDoprtrainOld> find(PlaDoprtrainOld plaDoprtrainOld) throws Exception {
		List<PlaDoprtrainOld> trainList = plaDoprtrainDaoOld.find(plaDoprtrainOld);

		bdPlaDoprDic(trainList);
		return trainList;
	}

	public List<PlaDoprtrainOld> findAll() throws Exception {
		List<PlaDoprtrainOld> list = plaDoprtrainDaoOld.findAll();
		return list;
	}

	public void delete(PlaDoprtrainOld plaDoprtrainOld) {
		if (null == plaDoprtrainOld) {
			return;
		}
		plaDoprtrainDaoOld.delete(plaDoprtrainOld);
	}

	/**
	 * �����ַ���������ת�� �����yyyy-mm��ʽ��תΪyyyymmdd;��֮����תΪ��֮��
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaDoprtrainOld pmt) throws Exception {
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
	private void bdPlaDoprDic(List<PlaDoprtrainOld> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// Map rwdepartmentMap = dicMap.getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaDoprtrainOld obj = (PlaDoprtrainOld) iter.next();
			String areaid = obj.getAreaid();
			DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			String areaidDIC = area != null ? area.getShortname() : areaid;
			obj.setAreaidDIC(areaidDIC);
			String month = obj.getMonth();// BeanUtils.getProperty(obj,
			// "month");
			String monthEnd = month.substring(0, 4) + "��" + month.substring(4)
					+ "��";
			obj.setMonthEnd(monthEnd);
			// BeanUtils.setProperty(obj, "monthEnd", monthEnd);

			String carkindid = obj.getCarkindid();// BeanUtils.getProperty(obj,
			// "carkindid");
			DicCarkind carkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = carkind != null ? carkind.getShortname()
					: carkindid;
			obj.setCarkindidDIC(carkindidDIC);
			/*
			 * BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC); String
			 * startstationid = BeanUtils .getProperty(obj, "startstationid");
			 * String startstationidDIC = (String) rwdepartmentMap
			 * .get(startstationid); BeanUtils.setProperty(obj,
			 * "startstationidDIC", startstationidDIC); String endstationid =
			 * BeanUtils.getProperty(obj, "endstationid"); String
			 * endstationidDIC = (String) rwdepartmentMap.get(endstationid);
			 * BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
			 */
		}
	}

	public void expExcel(PlaDoprtrainOld plaDoprtrainOld, HttpServletResponse response)
			throws Exception {
		// ��ѯ��ϸ�ƻ�����
		List<PlaDoprtrainOld> list = this.find(plaDoprtrainOld);

		Map tabDefineMap = new LinkedHashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("������Ʒ���복�������ƻ�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "������Ʒ���복�������ƻ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname", "loadtdate",
				"productkindDIC", "startstationname", "endstationname",
				"sendername", "receivername", "carkindidDIC", "rwkindname",
				"requestcars", "requestamount", "acceptcars", "acceptamount",
				"loadcars", "loadamount","acceptcarno" };
		headArr[1] = new String[] { "����˾", "��ҵ����", "װ������", "��Ʒ���", "��վ", "��վ",
				"������", "�ջ���", "����", "��·Ʒ��", "ԭ�ᳵ��", "ԭ�����", "��׼����", "��׼����",
				"��װ����", "��װ����", "��׼Ҫ���ֺ�" };
		// ����˾ ��ҵ���� װ������ ��Ʒ��� ��վ ��վ ������ �ջ��� ���� ��·Ʒ�� ԭ�ᳵ�� ԭ����� ��׼���� ��׼���� ��װ����
		// ��װ����
		sheetMap.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "������Ʒ���복�������ƻ�");

	}
}
