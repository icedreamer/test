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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaYuprtamountDao;
import com.tlys.pla.model.PlaYuprtamount;
import com.tlys.sys.model.SysUser;

@Service
public class PlaYuprtamountService extends _GenericService {
	@Autowired
	PlaYuprtamountDao plaYuprtamountDao;
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
	 * ���ݵ�ǰ�û���ݼ�ǰ̨�������Ĳ�ѯ���� ���ɱ��β�ѯ����Ҫ��corpid��areaid���������ѯ������
	 * 
	 * @param areaid
	 * @param corpid
	 * @param curUser
	 * @return
	 */
	public PlaYuprtamount buildAuthSch(PlaYuprtamount plaYuprtamount, SysUser curUser) throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaYuprtamount) {
			plaYuprtamount = new PlaYuprtamount();
		}
		// ��ǰ̨û�д�����ѯ����
		if (null == plaYuprtamount.getAreaid()) {
			// �����û���λ��������˾Ȩ�޲�ѯ����
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaYuprtamount.setAreaid(areaid);
		}
		if (null == plaYuprtamount.getCorpid()) {
			// �����û���λ��ѯ������ҵ
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaYuprtamount.setCorpid(corpid);
		}
		return plaYuprtamount;
	}

	/**
	 * <li>�����û���λ��ȡ����˾ID</li> <li>��ǰ�û������ܲ��û�ʱ������null,��ʾ��������˾���й��ˣ���ѯ������˾�б�Ϊȫ��</li>
	 * <li>��ǰ�û���������˾ʱ�������û���ǰ��������˾ ��ID����ѯ������˾ֻ����һ��</li> <li>
	 * ��ǰ�û�������ҵʱ�������û���ǰ��ҵ����Ӧ������˾��ID����ѯ������˾Ҳֻ����һ��</li> <li>
	 * ��ǰ�û�����פ����ʱ�����ص�ǰ��λ��Ӧ������˾ID����ѯ������˾�б�Ϊһ��</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdAreaid(String curCorpid, String curCorptab) throws Exception {
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
				DicSinodepartment obj = dicSinodepartmentService.load(curCorpid);
				areaid = obj.getParentid();
			}
		}
		return areaid;
	}

	/**
	 * <li>�����û���λ��ȡ��ҵID</li> <li>�ܲ��û���null</li> <li>
	 * ����˾�û���null�����ڲ�ѯ�б�ʱ��Ӧ���ǵ�ǰ���ڵ�����˾</li> <li>��ҵ����ǰ���ڵ���ҵID</li> <li>
	 * פ���죺��ǰ��λ����Ӧ����ҵID</li>
	 * 
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	public String bdCorpid(String curCorpid, String curCorptab) throws Exception {
		String corpid = "99999999";// ����ǰ�û�����Ϊnullʱ��û�в�ѯ����
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// �ܲ�����
				corpid = null;
			} else if ("1".equals(curCorptab)) {// ����˾
				corpid = null;
			} else if ("2".equals(curCorptab)) {// ��ҵ
				corpid = curCorpid;
			} else if ("3".equals(curCorptab)) {// פ����,��Ҫ���ҵ�ǰ��λ��Ӧ��corpid
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

	public PlaYuprtamount load(String id) throws Exception {
		PlaYuprtamount plaYuprtamount = plaYuprtamountDao.load(id);
		return plaYuprtamount;
	}

	public void save(PlaYuprtamount plaYuprtamount) throws Exception {
		plaYuprtamountDao.save(plaYuprtamount);
	}

	public void update(PlaYuprtamount plaYuprtamount) throws Exception {
		plaYuprtamountDao.updateEntity(plaYuprtamount, plaYuprtamount.getId());
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param PlaYuprtamount
	 * @return
	 * @throws Exception
	 */
	public void find(PlaYuprtamount plaYuprtamount, PageCtr pageCtr) throws Exception {
		// rectDate(plaYuprtamount);
		int totalRecord = getPlaYuprtamountCount(plaYuprtamount);
		pageCtr.setTotalRecord(totalRecord);
		plaYuprtamountDao.find(plaYuprtamount, pageCtr);
		bdPlaDic(pageCtr.getRecords());

	}

	public Object[] findSum(PlaYuprtamount plaYuprtamount) {
		List olist = plaYuprtamountDao.findSum(plaYuprtamount);
		Object[] oarr = null;
		if (null != olist && olist.size() > 0) {
			oarr = (Object[]) olist.get(0);
		} else {
			oarr = new String[] { "", "", "" };
		}
		return oarr;
	}

	public List<Object[]> findYuprtAmountSum(PlaYuprtamount plaYuprtamount) {
		return plaYuprtamountDao.findYuprtAmountSum(plaYuprtamount);
	}

	public int getPlaYuprtamountCount(final PlaYuprtamount plaYuprtamount) {
		int count = plaYuprtamountDao.getPlaYuprtamountCount(plaYuprtamount);
		return count;
	}

	public List<PlaYuprtamount> findAll() throws Exception {
		List<PlaYuprtamount> list = plaYuprtamountDao.findAll();
		return list;
	}

	public void delete(PlaYuprtamount plaYuprtamount) {
		if (null == plaYuprtamount) {
			return;
		}
		plaYuprtamountDao.delete(plaYuprtamount);
	}

	/**
	 * �����ַ���������ת�� �����yyyy-mm��ʽ��תΪyyyymmdd;��֮����תΪ��֮��
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	/*
	 * private void rectDate(PlaYuprtamount pmt) throws Exception{ if(null==pmt)
	 * return; String month = pmt.getMonth(); String monthStr =
	 * pmt.getMonthStr();
	 * 
	 * if(null != month){ monthStr = FormatUtil.rectDate59(month);
	 * pmt.setMonthStr(monthStr); } if(null != monthStr){ month =
	 * FormatUtil.rectDate59(monthStr); pmt.setMonth(month); } }
	 */

	/**
	 * �ƻ�ҳ����ʾ����
	 * 
	 * @throws Exception
	 */
	private void bdPlaDic(List<PlaYuprtamount> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map goodsMap = dicMap.getDicGoodsMap();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaYuprtamount obj = (PlaYuprtamount) iter.next();
			String areaid = obj.getAreaid();
			String areaidDIC = "";
			if (areaid.startsWith("VSMF_")) {
				// ��xml�ж�ȡ
			} else {
				DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
				areaidDIC = area.getShortname();
			}

			obj.setAreaidDIC(areaidDIC);

			String rwkindid = obj.getRwkindid();
			String rkindname = obj.getRkindname();
			if (null == rkindname) {
				DicGoods dg = (DicGoods) goodsMap.get(rwkindid);
				if (null != dg) {
					rkindname = dg.getPmhz();
					obj.setRkindname(rkindname);
				}
			}
		}
	}

	/**
	 * @param PlaDtrtrain
	 */
	public List<PlaYuprtamount> find(PlaYuprtamount plaYuprtamount) throws Exception {
		List<PlaYuprtamount> list = plaYuprtamountDao.find(plaYuprtamount);
		bdPlaDic(list);
		return list;
	}

	public void expExcel(PlaYuprtamount plaYuprtamount, HttpServletResponse response) throws Exception {
		// ��ѯ��ϸ�ƻ�����
		List<PlaYuprtamount> list = this.find(plaYuprtamount);
		String year = plaYuprtamount.getYear();
		Map tabDefineMap = new LinkedHashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put(year + "�����·����ָ��", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", year + "�����·����ָ��");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "areaidDIC", "corpshortname", "productcategoryname", "productcategoryname",
				"rkindname", "amount", "lyamount", "varyamount" };
		headArr[1] = new String[] { "����˾", "��ҵ����", "��Ʒ����", "��ƷС��", "��·Ʒ��", "����", "���������", "����������" };

		// ����˾ ��ҵ���� ��Ʒ���� ��ƷС�� ��·Ʒ�� ���� ��������� ����������
		sheetMap.put("headArr", headArr);
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, year + "�����·����ָ��");
	}

	public List<PlaYuprtamount> findPlaYuprtamount(final String year, final List<String> corpIdList,
			final List<String> productCategoryIdList, final List<String> productSecondIdList,
			final List<String> rwkindIdList) {
		return plaYuprtamountDao.findPlaYuprtamount(year, corpIdList, productCategoryIdList, productSecondIdList,
				rwkindIdList);
	}

	public List<Object[]> findPlaYuprtamountSubTotal(String year) {
		return plaYuprtamountDao.findPlaYuprtamountSubTotal(year);
	}

	public Object[] findSum(final String year) {
		return plaYuprtamountDao.findSum(year);
	}
}
