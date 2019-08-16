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
	 * 专为生成字典用，不注入省份名
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
	 * 查找所有石化企业，不包含总部机关
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
	 * 查找所有总部机关
	 * 
	 * @return
	 */
	public List<DicSinocorp> findAllHead() throws Exception {
		List<DicSinocorp> scList = dicSinocorpDao.findAll(" where corptype='0'");
		dicMap.bdProv(scList);
		return scList;
	}

	/**
	 * 查找所有企业
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
	 * 查找所有总部机关
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
	 * 按省份查找，同样只查正常石化企业(不包含总部机关)
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
	 * 包括总部企业
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
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicSinocorp
	 *            ,isNew:标志当前是新增操作还是修改操作
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
	 * 查找本企业下属的部门
	 * 
	 * @return
	 */
	public List findDepartments(String id) {
		return dicSinodepartmentDao.findByParentid(id);
	}

	/**
	 * 得到列表，专为添加视图时所用，参数为当前视图名
	 * {salecorp,receiver,sender},注意后面两个和本来的视图名有区别，为了拼字符串is***而这样写
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
	 * 生成Excel表,利用ExcelUtilNew类
	 * 
	 * @param corpList
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void expExcel(List<DicSinocorp> corpList, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new HashMap();

		String titlename = "总部机关";
		if (null != corpList && corpList.size() > 0) {
			DicSinocorp corp = corpList.get(0);
			String ctp = corp.getCorptype();
			if (null == ctp || ctp.equals("1")) {
				titlename = "化工企业";
			}
		}
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put(titlename + "列表", sheetMap);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", corpList);
		// 标题
		sheetMap.put("title", titlename + "列表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		/*
		 * headArr[0] = new String[] { "corpid", "fullname", "shortname",
		 * "address", "dutyperson", "telephones", "fax", "contactperson",
		 * "issalecorpDIC", "isreceiverDIC", "issenderDIC", "provinceidDIC",
		 * "areaidDIC", "inareaorder", "bureauidDIC", "corptype", "rwcode",
		 * "isactiveDIC", "description" }; headArr[1] = new String[] { "企业ID",
		 * "全称", "简称", "地址", "负责人", "电话", "传真", "联系人", "是否销售企业", "是否收货人",
		 * "是否发货人", "省份", "区域", "顺序号", "路局", "铁路月计划单位", "单位类型", "是否启用", "描述" };
		 */

		headArr[0] = new String[] { "corpid", "fullname", "shortname", "address", "dutyperson", "telephones", "fax",
				"contactperson", "description" };
		headArr[1] = new String[] { "企业ID", "全称", "简称", "地址", "负责人", "电话", "传真", "联系人", "描述" };

		sheetMap.put("headArr", headArr);

		// Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "石化单位列表");
	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
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
	 * 获取用户默认可以使用的企业
	 * 
	 * @param sysUser
	 * @return -1表示没有数据权限(特殊设置下生效，针对区域公司没有设置下属企业和驻厂办没有关联企业的情况) 0：总部默认所有企业数据权限；
	 *         1：区域公司默认下属企业数据权限； 2：企业默认当前所在企业数据权限； 3：驻厂办默认所在企业数据权限；
	 */
	public String[] findDefaultCorpidsByUserId(SysUser sysUser) {
		String corptab = sysUser.getCorptab();
		String[] corpids = null;
		if (corptab != null) {
			if ("1".equals(corptab)) {
				// 区域公司
				String areaid = sysUser.getCorpid();
				List<String> corpidList = dicSinocorpDao.findCorpIdsByAreaId(areaid);
				if (null != corpidList && !corpidList.isEmpty()) {
					corpids = (String[]) corpidList.toArray(new String[0]);
				} else {
					corpids = new String[1];
					corpids[0] = "-1";
				}
			} else if ("2".equals(corptab)) {
				// 企业
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
