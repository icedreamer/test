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
import com.tlys.dic.model.ctl.CtlLiaisonocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.pla.dao.PlaMoprtrainDao;
import com.tlys.pla.model.PlaDsprtrain;
import com.tlys.pla.model.PlaMoprtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaMoprtrainService extends _GenericService {
	@Autowired
	PlaMoprtrainDao plaMoprtrainDao;
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
	PlaDsprtrainService plaDsprtrainService;

	public PlaMoprtrain load(String id) throws Exception {
		PlaMoprtrain plaMoprtrain = plaMoprtrainDao.load(id);
		return plaMoprtrain;
	}

	public void save(PlaMoprtrain plaMoprtrain) throws Exception {
		plaMoprtrainDao.save(plaMoprtrain);
	}

	public void update(PlaMoprtrain plaMoprtrain) throws Exception {
		plaMoprtrainDao.updateEntity(plaMoprtrain, plaMoprtrain.getId());
	}

	public List<PlaMoprtrain> findAll() throws Exception {
		List<PlaMoprtrain> list = plaMoprtrainDao.findAll();
		return list;
	}

	public void delete(PlaMoprtrain plaMoprtrain) {
		if (null == plaMoprtrain) {
			return;
		}
		plaMoprtrainDao.delete(plaMoprtrain);
	}

	private int getPlaMoprtrainCount(final PlaMoprtrain plaMoprtrain) {
		int count = plaMoprtrainDao.getPlaMoprtrainCount(plaMoprtrain);
		return count;
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMoprtrain plaMoprtrain, PageCtr pageCtr)
			throws Exception {
		rectDate(plaMoprtrain);
		int totalRecord = getPlaMoprtrainCount(plaMoprtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaMoprtrainDao.find(plaMoprtrain, pageCtr);
		bdPlaMsprDic(pageCtr.getRecords());
	}

	public List<PlaMoprtrain> find(PlaMoprtrain plaMoprtrain) throws Exception {
		List<PlaMoprtrain> list = plaMoprtrainDao.find(plaMoprtrain);
		bdPlaMsprDic(list);
		return list;
	}

	
	/**
	 * �˷��������ϣ����ڴ˴����飬�˴�Ȩ��ʹ��ͳһ�ļܹ�����
	 * @param plaMoprtrain
	 * @param curUser
	 * @return
	 * @throws Exception
	 */
	public PlaMoprtrain buildAuthSchOld(PlaMoprtrain plaMoprtrain, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaMoprtrain) {
			plaMoprtrain = new PlaMoprtrain();
		}
		// ��ǰ̨û�д�����ѯ����
		if (null == plaMoprtrain.getAreaid()) {
			// �����û���λ��������˾Ȩ�޲�ѯ����
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaMoprtrain.setAreaid(areaid);
		}
		if (null == plaMoprtrain.getCorpid()) {
			// �����û���λ��ѯ������ҵ
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaMoprtrain.setCorpid(corpid);
		}
		
		return plaMoprtrain;
	}
	
	/**
	 * �˷�������д���ķ�����ԭ������buildAuthSchOld 
	 * @param plaMoprtrain
	 * @param curUser
	 * @return
	 * @throws Exception
	 */
	public PlaMoprtrain buildAuthSch(PlaMoprtrain plaMoprtrain)
			throws Exception {
		//String userCorpid = curUser.getCorpid();
		//String userCorptab = curUser.getCorptab();
		if (null == plaMoprtrain) {
			plaMoprtrain = new PlaMoprtrain();
		}
		
		if (null == plaMoprtrain.getCorpids() || "".equals(plaMoprtrain.getCorpids())) {
			// ȡ�õ�ǰ�û��ڱ�ģ���е���ҵȨ��
			/**
			 * Ŀǰ�Ȱ����¹�����⣺Ĭ��Ȩ�ޣ��ܲ��û����ؿգ�"")������˾�û����ض�Ӧ��ҵ�б���ҵ�����򷵻ر���ҵID
			 * ��һ�������趨��ģ�������Ȩ�ޣ�������Ĭ��Ȩ��ʧЧ
			 */
			String corpids = CommUtils.getCorpIds(); 
			
			plaMoprtrain.setCorpids(corpids);
		}
		//System.out.println("PlaMoprtrainService.buildAuthSch->plaMoprtrain.getCorpids()=="+plaMoprtrain.getCorpids());
		return plaMoprtrain;
	}
	

	/**
	 * �����û���λ��ȡ����˾ID �Ȳ����ڽ�����null���Ե�λ���й���
	 * �˷����ѷ���
	 * @param curCorpid
	 * @return
	 * @throws Exception
	 */
	private String bdAreaid(String curCorpid, String curCorptab)
			throws Exception {
		/**
		 * �˷����Ѿ��������ã�ͳһ����null��Ȩ��ͳһ������ҵ����ֶν���
		 * ���˷����Ĵ�����̷��ڴˣ��ṩ�޸ĵ���ʷ����
		 */
		if(true){
			return null;
		}
		String areaid = "99999999";// ����ǰ�û�����Ϊnullʱ��û�в�ѯ����
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// �ܲ�����
				areaid = null;
			} else if ("1".equals(curCorptab)) {// ����˾,��ǰ�û��ĵ�λID��Ϊareaid
				/**
				 * �˴�����ҵ�ֶβ���ͳһȨ�޼ܹ�������areaid����������
				 */
				//areaid = curCorpid;
				areaid = null;
			} else if ("2".equals(curCorptab)) {// ��ҵ,��Ҫ���ҵ�ǰ�û���ҵ����Ӧ��areaid
				/*
				 * ע���˴���Ϊ�������ҵ�û�����ͬ������areaid�������ƣ�����һ����ѯ����
				 * ��Ϊ��ʱ��corpid���Ȼ���������,���⣬��ʹ�����û�����Ȩ��ʱ
				 * ����û�������Ȩ���ǿ�����˾�ģ������������������areaid���ͻ������ͻ
				 */
				//DicSinocorp corpObj = dicSinocorpService.load(curCorpid);
				//areaid = corpObj.getAreaid();
				areaid = null;
			} else if ("3".equals(curCorptab)) {// פ����,��ͬ��Ҫ���ҵ�ǰ��λ��Ӧ��areaid
				/*
				 * ע���˴�ͬ����Ϊ���������ƣ�ԭ��ͬ�������ҵ
				 */
				//DicSinodepartment obj = dicSinodepartmentService.load(curCorpid);
				//areaid = obj.getParentid();
				areaid = null;
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
	private String bdCorpid(String curCorpid, String curCorptab)
			throws Exception {
		String corpid = "99999999";// ����ǰ�û�����Ϊnullʱ��û�в�ѯ����
		if (null != curCorptab) {
			if ("0".equals(curCorptab)) {// �ܲ�����
				corpid = null;
			} else if ("1".equals(curCorptab)) {// ����˾�û�,������Ȩ����areaid������
				corpid = null;
			} else if ("2".equals(curCorptab)) {// ��ҵ�û�
				/**
				 * ��ʱ������ͳһȨ�޼ܹ�����
				 */
				//corpid = curCorpid;
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
	
	

	/**
	 * �����ƻ�ҳ����ʾ����
	 * 
	 * @throws Exception
	 */
	private void bdPlaMsprDic(List<PlaMoprtrain> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// Map rwdepartmentMap = dicMap.getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMoprtrain obj = (PlaMoprtrain) iter.next();
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
			// BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC);
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

	/**
	 * �����ַ���������ת�� �����yyyy-mm��ʽ��תΪyyyymmdd;��֮����תΪ��֮��
	 * 
	 * @param pmt
	 * @throws Exception
	 */
	private void rectDate(PlaMoprtrain pmt) throws Exception {
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

	public void expExcel(PlaMoprtrain plaMoprtrain, HttpServletResponse response)
			throws Exception {
		// ��ѯ��ϸ�ƻ�����
		List<PlaMoprtrain> list = this.find(plaMoprtrain);

		Map tabDefineMap = new LinkedHashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("��������Ʒ�복�������ƻ�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "��������Ʒ�복�������ƻ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "corpshortname", "checkstatusDIC",
				"startstationname", "endstationname", "sendername",
				"receivername", "carkindidDIC", "rwkindname", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "acceptcarno" };
		headArr[1] = new String[] { "��ҵ����", "����״̬", "��վ", "��վ", "������", "�ջ���",
				"����", "��·Ʒ��", "ԭ�ᳵ��", "ԭ�����", "��׼����", "��׼����", "��׼Ҫ���ֺ�" };
		// ��ҵ���� ����״̬ ��վ ��վ ������ �ջ��� ���� ��·Ʒ�� ԭ�ᳵ�� ԭ����� ��׼���� ��׼���� ��׼Ҫ���ֺ�
		sheetMap.put("headArr", headArr);

		// ��׼Ҫ���ֺ�����
		String acceptcarnos = CommUtils.getRestrictionsIn(list, "acceptcarno");
		PlaDsprtrain plaDsprtrain = new PlaDsprtrain();
		plaDsprtrain.setAcceptcarnos(acceptcarnos);
		List<PlaDsprtrain> dups = plaDsprtrainService.find(plaDsprtrain);

		Map sheetMap1 = new HashMap();
		tabDefineMap.put("��������Ʒ�복�������ƻ�", sheetMap1);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap1.put("list", dups);
		// ����
		sheetMap1.put("title", "��������Ʒ�복�������ƻ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		headArr = new String[2][];
		headArr[0] = new String[] { "requestdate", "requestcars",
				"requestamount", "acceptcars", "acceptamount", "loadcars",
				"loadamount", "mtotalcars", "acceptcarno" };
		headArr[1] = new String[] { "��������", "ԭ�ᳵ��", "ԭ�����", "��׼����", "��׼����",
				"��װ����", "��װ����", "���ۼ���ɳ���", "��׼Ҫ���ֺ�" };
		// �������� ԭ�ᳵ�� ԭ����� ��׼���� ��׼���� ��װ���� ��װ���� ���ۼ���ɳ���
		sheetMap1.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "������Ʒ�복�������ƻ�");

	}
}
