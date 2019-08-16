/**
 * 
 */
package com.tlys.dic.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicSinocorpDao;
import com.tlys.dic.dao.DicSinodepartmentDao;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.ctl.CtlLiaisonocorpService;
import com.tlys.sys.model.SysUser;

/**
 * @author fengym
 * 
 */
@Service
public class DicSinocorpService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicSinocorpDao dicSinocorpDao;

	@Autowired
	DicSinodepartmentDao dicSinodepartmentDao;
	@Autowired
	CtlLiaisonocorpService ctlLiaisonocorpService;

	@Autowired
	DicMap dicMap;

	/**
	 * רΪ�����ֵ��ã���ע��ʡ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicSinocorp> findAll4Map() {
		List<DicSinocorp> scList = dicSinocorpDao.findAll(" order by shortname");
		return scList;
	}

	public List<DicSinocorp> findByCorpid(DicSinocorp dac) throws Exception {
		List<DicSinocorp> scList = dicSinocorpDao.findById(dac);
		return scList;
	}

	/**
	 * ��������ʯ����ҵ���������ܲ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicSinocorp> findAll() throws Exception {
		List<DicSinocorp> scList = dicSinocorpDao
				.findAll(" where corptype is null or corptype='1' order by areaid,shortname");
		dicMap.bdProv(scList);
		return scList;
	}

	/**
	 * ���������ܲ�����
	 * 
	 * @return
	 */
	public List<DicSinocorp> findAllHead() throws Exception {
		List<DicSinocorp> scList = dicSinocorpDao.findAll(" where corptype='0'");
		dicMap.bdProv(scList);
		return scList;
	}

	/**
	 * ����������ҵ
	 * 
	 * @param dac
	 * @return
	 * @throws Exception
	 */
	public List<DicSinocorp> find(DicSinocorp dac) throws Exception {
		List<DicSinocorp> scList = dicSinocorpDao.find(dac);
		dicMap.bdProv(scList);
		return scList;
	}

	/**
	 * ���������ܲ�����
	 * 
	 * @param dicSinocorp
	 * @return
	 */
	public List<DicSinocorp> findHead(DicSinocorp dac) throws Exception {
		dac.setCorptype("0");
		List<DicSinocorp> scList = dicSinocorpDao.find(dac);
		dicMap.bdProv(scList);
		return scList;
	}

	/**
	 * ��ʡ�ݲ��ң�ͬ��ֻ������ʯ����ҵ(�������ܲ�����)
	 * 
	 * @param provids
	 * @return
	 * @throws Exception
	 */
	public List<DicSinocorp> findByProvids(String provids) throws Exception {
		String hql = " where (corptype is null or corptype='1') and provinceid in(" + CommUtils.addQuotes(provids)
				+ ") order by shortname";
		List trList = dicSinocorpDao.findAll(hql);
		dicMap.bdProv(trList);
		return trList;
	}

	public List<DicSinocorp> findByProvidsHead(String provids) throws Exception {
		String hql = " where (corptype='1') and provinceid in(" + CommUtils.addQuotes(provids) + ")";
		List trList = dicSinocorpDao.findAll(hql);
		dicMap.bdProv(trList);
		return trList;
	}

	public List<DicSinocorp> findDicSinocorp() {
		return dicSinocorpDao.findDicSinocorp();
	}

	/**
	 * �����ܲ���ҵ
	 * 
	 * @return
	 */
	public List<DicSinocorp> findDicSinocorpAll() {
		return dicSinocorpDao.findDicSinocorpAll();
	}

	public DicSinocorp load(String id) {
		return dicSinocorpDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicSinocorp
	 *            ,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicSinocorp dicSinocorp, boolean isNew) {
		if (isNew) {
			dicSinocorpDao.saveOrUpdate(dicSinocorp);
		} else {
			dicSinocorpDao.updateEntity(dicSinocorp, dicSinocorp.getCorpid());
		}
		setAlterFlag();
	}

	/**
	 * ���ұ���ҵ�����Ĳ���
	 * 
	 * @return
	 */
	public List findDepartments(String id) {
		return dicSinodepartmentDao.findByParentid(id);
	}

	/**
	 * �õ��б�רΪ�����ͼʱ���ã�����Ϊ��ǰ��ͼ��
	 * {salecorp,receiver,sender},ע����������ͱ�������ͼ��������Ϊ��ƴ�ַ���is***������д
	 * 
	 * @param viewname
	 * @return
	 */
	public List<DicSinocorp> find4view(String viewname) {
		return dicSinocorpDao.find4view(viewname);
	}

	public List<DicSinocorp> find4usercorpauth(String userid) {
		List<DicSinocorp> dsList = dicSinocorpDao.find4usercorpauth();
		List<DicSinocorp> sList = dicSinocorpDao.findSinocorps(userid);
		for (DicSinocorp dsp : dsList) {
			for (Iterator it = sList.iterator(); it.hasNext();) {
				DicSinocorp ds = (DicSinocorp) it.next();
				if (dsp.getCorpid().equals(ds.getCorpid())) {
					dsp.setFlag("checked");
					break;
				}
			}
		}
		return dsList;
	}

	public void delete(String id) {
		dicSinocorpDao.delete(id);
		setAlterFlag();
	}

	/**
	 * ����Excel��,����ExcelUtilNew��
	 * 
	 * @param corpList
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void expExcel(List<DicSinocorp> corpList, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		String titlename = "�ܲ�����";
		if (null != corpList && corpList.size() > 0) {
			DicSinocorp corp = corpList.get(0);
			String ctp = corp.getCorptype();
			if (null == ctp || ctp.equals("1")) {
				titlename = "������ҵ";
			}
		}
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put(titlename + "�б�", sheetMap);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", corpList);
		// ����
		sheetMap.put("title", titlename + "�б�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		/*
		 * headArr[0] = new String[] { "corpid", "fullname", "shortname",
		 * "address", "dutyperson", "telephones", "fax", "contactperson",
		 * "issalecorpDIC", "isreceiverDIC", "issenderDIC", "provinceidDIC",
		 * "areaidDIC", "inareaorder", "bureauidDIC", "corptype", "rwcode",
		 * "isactiveDIC", "description" }; headArr[1] = new String[] { "��ҵID",
		 * "ȫ��", "���", "��ַ", "������", "�绰", "����", "��ϵ��", "�Ƿ�������ҵ", "�Ƿ��ջ���",
		 * "�Ƿ񷢻���", "ʡ��", "����", "˳���", "·��", "��·�¼ƻ���λ", "��λ����", "�Ƿ�����", "����" };
		 */

		headArr[0] = new String[] { "corpid", "fullname", "shortname", "address", "dutyperson", "telephones", "fax",
				"contactperson", "description" };
		headArr[1] = new String[] { "��ҵID", "ȫ��", "���", "��ַ", "������", "�绰", "����", "��ϵ��", "����" };

		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "ʯ����λ�б�");
	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicSinocorp");
	}

	public DicSinocorp getDicSinocorpByName(String corpName) {
		return dicSinocorpDao.getDicSinocorpByName(corpName);
	}

	public List<DicSinocorp> findDicSinocorp(final String areaid, final String corpid) {
		return dicSinocorpDao.findDicSinocorp(areaid, corpid);
	}

	/**
	 * ��ȡ�û�Ĭ�Ͽ���ʹ�õ���ҵ
	 * 
	 * @param sysUser
	 * @return -1��ʾû������Ȩ��(������������Ч���������˾û������������ҵ��פ����û�й�����ҵ�����) 0���ܲ�Ĭ��������ҵ����Ȩ�ޣ�
	 *         1������˾Ĭ��������ҵ����Ȩ�ޣ� 2����ҵĬ�ϵ�ǰ������ҵ����Ȩ�ޣ� 3��פ����Ĭ��������ҵ����Ȩ�ޣ�
	 */
	public String[] findDefaultCorpidsByUserId(SysUser sysUser) {
		String corptab = sysUser.getCorptab();
		String[] corpids = null;
		if (corptab != null) {
			if ("1".equals(corptab)) {
				// ����˾
				String areaid = sysUser.getCorpid();
				List<String> corpidList = dicSinocorpDao.findCorpIdsByAreaId(areaid);
				if (null != corpidList && !corpidList.isEmpty()) {
					corpids = (String[]) corpidList.toArray(new String[0]);
				} else {
					corpids = new String[1];
					corpids[0] = "-1";
				}
			} else if ("2".equals(corptab)) {
				// ��ҵ
				String corpid = sysUser.getCorpid();
				corpids = new String[1];
				corpids[0] = corpid;
			} else if ("3".equals(corptab)) {
				String branchid = sysUser.getCorpid();
				List<String> corpidList = ctlLiaisonocorpService.findCorpidsByBranchid(branchid);
				if (null != corpidList && !corpidList.isEmpty()) {
					corpids = (String[]) corpidList.toArray(new String[0]);
				} else {
					corpids = new String[1];
					corpids[0] = "-1";
				}
			}
		}
		return corpids;
	}
}
