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
import com.tlys.pla.dao.PlaMsprtrainDao;
import com.tlys.pla.model.PlaDsprtrain;
import com.tlys.pla.model.PlaMsprtrain;
import com.tlys.sys.model.SysUser;

@Service
public class PlaMsprtrainService extends _GenericService {
	@Autowired
	PlaMsprtrainDao plaMsprtrainDao;
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

	public PlaMsprtrain load(String id) throws Exception {
		PlaMsprtrain plaMsprtrain = plaMsprtrainDao.load(id);
		return plaMsprtrain;
	}

	public void save(PlaMsprtrain plaMsprtrain) throws Exception {
		plaMsprtrainDao.save(plaMsprtrain);
	}

	public void update(PlaMsprtrain plaMsprtrain) throws Exception {
		plaMsprtrainDao.updateEntity(plaMsprtrain, plaMsprtrain.getId());
	}

	public List<PlaMsprtrain> findAll() throws Exception {
		List<PlaMsprtrain> list = plaMsprtrainDao.findAll();
		return list;
	}

	public void delete(PlaMsprtrain plaMsprtrain) {
		if (null == plaMsprtrain) {
			return;
		}
		plaMsprtrainDao.delete(plaMsprtrain);
	}

	private int getPlaMsprtrainCount(final PlaMsprtrain plaMsprtrain) {
		int count = plaMsprtrainDao.getPlaMsprtrainCount(plaMsprtrain);
		return count;
	}

	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaMsprtrain plaMsprtrain, PageCtr pageCtr)
			throws Exception {
		rectDate(plaMsprtrain);
		int totalRecord = getPlaMsprtrainCount(plaMsprtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaMsprtrainDao.find(plaMsprtrain, pageCtr);
		bdPlaMsprDic(pageCtr.getRecords());
	}

	public List<PlaMsprtrain> find(PlaMsprtrain plaMsprtrain) throws Exception {
		List<PlaMsprtrain> list = plaMsprtrainDao.find(plaMsprtrain);
		bdPlaMsprDic(list);
		return list;
	}

	
	/**
	 * �˷��������ϣ����ڴ˴����飬�˴�Ȩ��ʹ��ͳһ�ļܹ�����
	 * @param plaMsprtrain
	 * @param curUser
	 * @return
	 * @throws Exception
	 */
	public PlaMsprtrain buildAuthSchOld(PlaMsprtrain plaMsprtrain, SysUser curUser)
			throws Exception {
		String userCorpid = curUser.getCorpid();
		String userCorptab = curUser.getCorptab();
		if (null == plaMsprtrain) {
			plaMsprtrain = new PlaMsprtrain();
		}
		// ��ǰ̨û�д�����ѯ����
		if (null == plaMsprtrain.getAreaid()) {
			// �����û���λ��������˾Ȩ�޲�ѯ����
			String areaid = bdAreaid(userCorpid, userCorptab);
			plaMsprtrain.setAreaid(areaid);
		}
		if (null == plaMsprtrain.getCorpid()) {
			// �����û���λ��ѯ������ҵ
			String corpid = bdCorpid(userCorpid, userCorptab);
			plaMsprtrain.setCorpid(corpid);
		}
		
		return plaMsprtrain;
	}
	
	/**
	 * �˷�������д���ķ�����ԭ������buildAuthSchOld 
	 * @param plaMsprtrain
	 * @param curUser
	 * @return
	 * @throws Exception
	 */
	public PlaMsprtrain buildAuthSch(PlaMsprtrain plaMsprtrain)
			throws Exception {
		//String userCorpid = curUser.getCorpid();
		//String userCorptab = curUser.getCorptab();
		if (null == plaMsprtrain) {
			plaMsprtrain = new PlaMsprtrain();
		}
		
		if (null == plaMsprtrain.getCorpids() || "".equals(plaMsprtrain.getCorpids())) {
			// ȡ�õ�ǰ�û��ڱ�ģ���е���ҵȨ��
			/**
			 * Ŀǰ�Ȱ����¹�����⣺Ĭ��Ȩ�ޣ��ܲ��û����ؿգ�"")������˾�û����ض�Ӧ��ҵ�б���ҵ�����򷵻ر���ҵID
			 * ��һ�������趨��ģ�������Ȩ�ޣ�������Ĭ��Ȩ��ʧЧ
			 */
			String corpids = CommUtils.getCorpIds(); 
			
			plaMsprtrain.setCorpids(corpids);
		}
		//System.out.println("PlaMsprtrainService.buildAuthSch->plaMsprtrain.getCorpids()=="+plaMsprtrain.getCorpids());
		return plaMsprtrain;
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
	private void bdPlaMsprDic(List<PlaMsprtrain> list) throws Exception {
		Map areaMap = dicMap.getAreaMap();
		Map carkindMap = dicMap.getCarkindMap();
		// Map rwdepartmentMap = dicMap.getRwdepartmentMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PlaMsprtrain obj = (PlaMsprtrain) iter.next();
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
	private void rectDate(PlaMsprtrain pmt) throws Exception {
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

	public void expExcel(PlaMsprtrain plaMsprtrain, HttpServletResponse response)
			throws Exception {
		// ��ѯ��ϸ�ƻ�����
		List<PlaMsprtrain> list = this.find(plaMsprtrain);

		Map tabDefineMap = new LinkedHashMap();
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�·�ͳ���복�������ƻ�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "�·�ͳ���복�������ƻ�");

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
		tabDefineMap.put("�շ�ͳ���복�������ƻ�", sheetMap1);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap1.put("list", dups);
		// ����
		sheetMap1.put("title", "�շ�ͳ���복�������ƻ�");

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

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�·�ͳ���복�������ƻ�");

	}
}
