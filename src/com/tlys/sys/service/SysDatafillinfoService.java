/**
 * 
 */
package com.tlys.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.FormatUtil;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.sys.dao.SysDatafillinfoDao;
import com.tlys.sys.model.SysDatafillinfo;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author fengym
 * 
 */
@Service
public class SysDatafillinfoService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	SysDatafillinfoDao sysDatafillinfoDao;

	@Autowired
	DicMap dicMap;

	/**
	 * 生成统销计划区域公司本月已填和未填数据
	 * 
	 * @return Map nf=Integer,yf=Integer,arealist=List<SysDatafillinfo>
	 * @throws Exception
	 */
	public Map getUspMap() throws Exception {
		List<SysDatafillinfo> afList = getDfList("USP");
		int nf = 0;// 未填报家数
		int yf = 0;// 已填报家数
		int af = 0;// 总家数
		if (null != afList) {
			Map areaMap = dicMap.getAreaMap();
			for (SysDatafillinfo dfi : afList) {
				yf += dfi.getValuen();
				af += dfi.getValuem();
				String corpid = dfi.getCorpid();
				String areaname = areaMap.get(corpid).toString();
				dfi.setCorpname(areaname);
			}
		}
		Map astMap = new HashMap();

		nf = af - yf;
		astMap.put("nf", nf);
		astMap.put("yf", yf);
		astMap.put("af", af);
		astMap.put("arealist", afList);

		return astMap;
	}

	/**
	 * 生成统销计划区域公司本月调整次数
	 * 
	 * @return 如果各家都没有调整，则返回null，在前台不显示
	 * @throws Exception
	 */
	public Map getAuspMap() throws Exception {
		List<SysDatafillinfo> afList = getDfList("AUSP");
		int tf = 0;// 已调整家数
		Map astMap = null;
		Map areaMap = dicMap.getAreaMap();
		if (null != afList && afList.size() > 0) {
			for (SysDatafillinfo dfi : afList) {
				tf += dfi.getValuen();
				String corpid = dfi.getCorpid();
				String areaname = areaMap.get(corpid).toString();
				dfi.setCorpname(areaname);
			}
			astMap = new HashMap();

			astMap.put("tf", tf);
			astMap.put("arealist", afList);
		}
		// System.out.println("SysDatafillinfoService.getAuspMap->astMap=="+astMap);
		return astMap;
	}

	/**
	 * 生成非统销计划企业本月已填和未填数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getSspMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("SSP");
		int nf = 0;// 未填报家数
		int yf = 0;// 已填报家数
		int af = 0;// 总家数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			// for (SysDatafillinfo dfi : cfList) {
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					yf += dfi.getValuen();
					af += dfi.getValuem();
				}
			}
		}
		Map sstMap = new HashMap();

		nf = af - yf;
		sstMap.put("nf", nf);
		sstMap.put("yf", yf);
		sstMap.put("af", af);
		sstMap.put("corplist", cfList);

		return sstMap;
	}

	/**
	 * 生成非统销计划企业本月调整次数
	 * 
	 * @return 如果各家都没有调整，则返回null，在前台不显示
	 * @throws Exception
	 */
	public Map getAsspMap() throws Exception {
		List<SysDatafillinfo> afList = getDfList("ASSP");
		int tf = 0;// 已调整家数
		Map astMap = null;
		Map corpMap = dicMap.getCorpAllMap();
		if (null != afList && afList.size() > 0) {
			// 与上面不同，这里不用考虑排除总部企业的问题，因为总部企业没有计划，必然也不会有调整计划
			for (SysDatafillinfo dfi : afList) {
				tf += dfi.getValuen();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				String corpname = corp.getShrinkname();
				dfi.setCorpname(corpname);
			}
			astMap = new HashMap();

			astMap.put("tf", tf);
			astMap.put("corplist", afList);
		}
		// System.out.println("SysDatafillinfoService.getAuspMap->astMap=="+astMap);
		return astMap;
	}

	/**
	 * 生成自备车检修情况
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getScrMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("SCR");
		int nf = 0;// 未填报家数
		int yf = 0;// 已填报家数
		int af = 0;// 总家数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			// for (SysDatafillinfo dfi : cfList) {
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					nf += dfi.getValuen();
					af += dfi.getValuem();
				}
			}
		}
		Map sstMap = new HashMap();

		sstMap.put("nf", nf);
		sstMap.put("yf", yf);
		sstMap.put("af", af);
		sstMap.put("corplist", cfList);

		return sstMap;
	}

	/**
	 * 统销产品请车及批复计划数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getMuraMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("MURA");
		int nf = 0;// 本月已装
		int yf = 0;// 本月已批
		int af = 0;// 本月总请车数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			// for (SysDatafillinfo dfi : cfList) {
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					nf += dfi.getValuen();
					yf += dfi.getValuem();
					af += dfi.getValuet();
				}
			}
		}
		Map muraMap = new HashMap();

		muraMap.put("nf", nf);
		muraMap.put("yf", yf);
		muraMap.put("af", af);
		muraMap.put("corplist", cfList);

		return muraMap;
	}

	/**
	 * 统销产品追加请车及批复计划数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getDuraMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("DURA");
		int nf = 0;// 本月已装
		int yf = 0;// 本月已批
		int af = 0;// 本月总请车数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					nf += dfi.getValuen();
					yf += dfi.getValuem();
					af += dfi.getValuet();
				}
			}
		}
		Map duraMap = new HashMap();

		duraMap.put("nf", nf);
		duraMap.put("yf", yf);
		duraMap.put("af", af);
		duraMap.put("corplist", cfList);

		return duraMap;
	}

	/**
	 * 非统销产品请车及批复计划数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getMsraMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("MSRA");
		int nf = 0;// 本月已装
		int yf = 0;// 本月已批
		int af = 0;// 本月总请车数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					nf += dfi.getValuen();
					yf += dfi.getValuem();
					af += dfi.getValuet();
				}
			}
		}
		Map msraMap = new HashMap();

		msraMap.put("nf", nf);
		msraMap.put("yf", yf);
		msraMap.put("af", af);
		msraMap.put("corplist", cfList);

		return msraMap;
	}

	/**
	 * 非统销产品追加请车及批复计划数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getDsraMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("DSRA");
		int nf = 0;// 本月已装
		int yf = 0;// 本月已批
		int af = 0;// 本月总请车数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					nf += dfi.getValuen();
					yf += dfi.getValuem();
					af += dfi.getValuet();
				}
			}
		}
		Map dsraMap = new HashMap();

		dsraMap.put("nf", nf);
		dsraMap.put("yf", yf);
		dsraMap.put("af", af);
		dsraMap.put("corplist", cfList);

		return dsraMap;
	}

	/**
	 * 其他产品请车及批复计划数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getMoraMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("MORA");
		int nf = 0;// 本月已装
		int yf = 0;// 本月已批
		int af = 0;// 本月总请车数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					nf += dfi.getValuen();
					yf += dfi.getValuem();
					af += dfi.getValuet();
				}
			}
		}
		Map moraMap = new HashMap();

		moraMap.put("nf", nf);
		moraMap.put("yf", yf);
		moraMap.put("af", af);
		moraMap.put("corplist", cfList);

		return moraMap;
	}

	/**
	 * 其他产品追加请车及批复计划数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getDoraMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfList("DORA");
		int nf = 0;// 本月已装
		int yf = 0;// 本月已批
		int af = 0;// 本月总请车数
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
				if ("0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					iter.remove();
				} else {
					String corpname = corp.getShrinkname();
					dfi.setCorpname(corpname);
					nf += dfi.getValuen();
					yf += dfi.getValuem();
					af += dfi.getValuet();
				}
			}
		}
		Map doraMap = new HashMap();

		doraMap.put("nf", nf);
		doraMap.put("yf", yf);
		doraMap.put("af", af);
		doraMap.put("corplist", cfList);

		return doraMap;
	}

	/**
	 * 原先设计是各项数据分别取出，那样要多次查询同一个表（SysDatafillinfo) 现将所有当日数据一次取出，再分别组装，这样提高查询效率
	 * 此方法动行后，上面各getXXXXMap方法就不用了
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getDfMap() throws Exception {
		List<SysDatafillinfo> cfList = getDfListAll();
		String[] busAll = new String[] { "MSRA", "MORA", "ASSP", "MURA", "SSP", "DURA", "SCR", "USP", "AUSP", "DSRA", "DORA", "GTP" };
		List busFlagList = new ArrayList(Arrays.asList(busAll));
		// System.out.println("SysDatafillinfoService.getDfMap->busFlagList=="+busFlagList);

		Map dbMap = new HashMap();
		if (null != cfList) {
			Map corpMap = dicMap.getCorpAllMap();
			Map areaMap = dicMap.getAreaMap();
			for (Iterator iter = cfList.iterator(); iter.hasNext();) {
				SysDatafillinfo dfi = (SysDatafillinfo) iter.next();
				String corpid = dfi.getCorpid();
				String databus = dfi.getDatabus();

				// 如果此标识在列表中有，就去除之，最后剩下的就是没有数据的，要在后面特殊处理
				if (busFlagList.contains(databus)) {
					busFlagList.remove(databus);
				}

				Map busListMap = (Map) dbMap.get(databus);
				if (null == busListMap) {
					busListMap = new HashMap();
					dbMap.put(databus, busListMap);
				}
				String corpname = null;
				if ("USP".equals(databus) || "AUSP".equals(databus)) {
					corpname = areaMap.get(corpid).toString();
				} else {
					DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
					if (corp != null) {
						if (corp.getCorptype() != null && "0".equals(corp.getCorptype())) {
							iter.remove();
							continue;
						} else {
							corpname = corp.getShrinkname();
						}
					}
					// if (corp != null && corp.getCorptype() != null &&
					// "0".equals(corp.getCorptype())) {
					// iter.remove();
					// continue;
					// } else {
					// corpname = corp.getShrinkname();
					// }
					// if (corp.getCorptype() != null &&
					// "0".equals(corp.getCorptype())) {// 将总部单位从企业列表中去除
					// iter.remove();
					// continue;
					// } else {
					// corpname = corp.getShrinkname();
					// }
				}
				dfi.setCorpname(corpname);

				Integer nfObj = (Integer) busListMap.get("nf");
				Integer mfObj = (Integer) busListMap.get("mf");
				Integer tfObj = (Integer) busListMap.get("tf");

				int nf = null == nfObj ? 0 : nfObj;
				int mf = null == mfObj ? 0 : mfObj;
				int tf = null == tfObj ? 0 : tfObj;

				nf += dfi.getValuen();
				mf += dfi.getValuem();
				tf += dfi.getValuet();

				busListMap.put("nf", nf);
				busListMap.put("mf", mf);
				busListMap.put("tf", tf);

				List busList = (List) busListMap.get("corplist");
				if (null == busList) {
					busList = new ArrayList();
					busListMap.put("corplist", busList);
				}
				busList.add(dfi);
			}
		}

		// 处理数据库中没有标志，防止页面中取出时为空
		for (Iterator iter = busFlagList.iterator(); iter.hasNext();) {
			String busflag = (String) iter.next();
			// System.out.println("SysDatafillinfoService.getDfMap->busflag=="+busflag);
			if (null == dbMap.get(busflag)) {
				Map busMap = new HashMap();
				busMap.put("nf", "0");
				busMap.put("mf", "0");
				busMap.put("tf", "0");
				dbMap.put(busflag, busMap);
			}

		}
		return dbMap;
	}

	private List getDfList(String flag) {
		SysDatafillinfo dfinfo = new SysDatafillinfo();
		dfinfo.setDatabus(flag);

		String nowMonth = FormatUtil.nowDateStr(FormatUtil.COMPACT_MONTH);
		String nowDay = FormatUtil.nowDateStr(FormatUtil.COMPACT);

		dfinfo.setDatamonth(nowMonth);
		dfinfo.setStatidate(nowDay);

		List<SysDatafillinfo> afList = sysDatafillinfoDao.findAreaStList(dfinfo);
		return afList;
	}

	private List getDfListAll() {
		SysDatafillinfo dfinfo = new SysDatafillinfo();
		// dfinfo.setDatabus(flag);

		String nowMonth = FormatUtil.nowDateStr(FormatUtil.COMPACT_MONTH);
		String nowDay = FormatUtil.nowDateStr(FormatUtil.COMPACT);

		dfinfo.setDatamonth(nowMonth);
		dfinfo.setStatidate(nowDay);

		List<SysDatafillinfo> afList = sysDatafillinfoDao.findAreaStList(dfinfo);
		return afList;
	}

	public void exeProc() {
		String nowStr = FormatUtil.nowDateStr();
		sysDatafillinfoDao.syncDatafill(nowStr);
	}
}
