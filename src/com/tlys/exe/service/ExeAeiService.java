package com.tlys.exe.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.page.PageView;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicEntprStn;
import com.tlys.dic.model.DicOverTime;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.common.util.DicUtil;
import com.tlys.exe.common.util.ExcelUtil;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeAeiDao;
import com.tlys.exe.dao.ExeAeiSearchCarField;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.exe.dao.ExeDicDao;
import com.tlys.exe.dao.ExeStncarDao;
import com.tlys.exe.dao.ExeStntrnDao;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeOverdue;
import com.tlys.exe.model.ExeStncar;
import com.tlys.exe.model.ExeStntrn;

/**
 * AEI service
 * 
 * @author 孔垂云
 * 
 */
@Service
public class ExeAeiService {
	@Autowired
	ExeStntrnDao exeStntrnDao;
	@Autowired
	ExeStncarDao exeStncarDao;
	@Autowired
	ExeDicDao exeDicDao;
	@Autowired
	ExeAeiDao exeAeiDao;

	@Autowired
	ExeDcarStatDao exeDcarStatDao;

	/**
	 * 车站（企业站区)列车出入信息列表
	 */
	public PageView<ExeStntrn> listStntrn(String s_date, String e_date, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return exeStntrnDao.listStntrn(s_date, e_date, pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * 车站（企业站区)列车出入信息数量
	 * 
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public int getListStntrnCount(String s_date, final String e_date) {
		return exeStntrnDao.getListStntrnCount(s_date, e_date);
	}

	/**
	 * 根据报文名查询车站（企业站区)车辆出入信息
	 * 
	 * @param rptname
	 * @param pageUrl
	 * @param totalRecord
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<ExeStncar> listStncar(String rptname) {
		return exeStncarDao.listStncar(rptname);
	}

	/**
	 * AEI车辆信息查询
	 * 
	 * @param field
	 * @return
	 */
	public PageView<ExeStncar> searchStncar(ExeAeiSearchCarField field, String pageUrl, int totalRecord,
			int currentPage, int pageSize) {
		return exeStncarDao.searchStncar(field, pageUrl, totalRecord, currentPage, pageSize);
	}

	/**
	 * AEI车辆信息总数
	 * 
	 * @param field
	 * @return
	 */
	public int getListStncarCount(ExeAeiSearchCarField field) {
		return exeStncarDao.getListStncarCount(field);
	}

	/**
	 * 增加列车信息
	 * 
	 * @param exeStntrn
	 */
	public void saveStntrn(ExeStntrn exeStntrn) {
		exeStntrnDao.addExeStntrn(exeStntrn);
	}

	/**
	 * 修改列车信息
	 * 
	 * @param exeStntrn
	 */
	public void updateStntrn(ExeStntrn exeStntrn) {
		exeStntrnDao.updateExeStntrn(exeStntrn);
	}

	/**
	 * 删除列车信息，同时删除对应的车辆信息
	 * 
	 * @param exeStntrn
	 */
	public void deleteStntrn(ExeStntrn exeStntrn) {
		exeStntrnDao.deleteExeStntrn(exeStntrn);
		List<ExeStncar> list = listStncar(exeStntrn.getRptname());
		for (ExeStncar stncar : list) {
			deleteStncar(stncar);
		}
	}

	/**
	 * 根据rptname查找该列车信息
	 * 
	 * @param rptname
	 * @return
	 */
	public ExeStntrn loadExeStntrn(String rptname) {
		return exeStntrnDao.loadExeStntrn(rptname);
	}

	/**
	 * 增加车辆信息
	 * 
	 * @param exeStncar
	 */
	public void saveStncar(ExeStncar exeStncar) {
		exeStncarDao.addExeStncar(exeStncar);
	}

	/**
	 * 修改车辆信息
	 * 
	 * @param exeStncar
	 */
	public void updateStncar(ExeStncar exeStncar) {
		exeStncarDao.updateExeStncar(exeStncar);
	}

	/**
	 * 删除车辆信息
	 * 
	 * @param exeStncar
	 */
	public void deleteStncar(ExeStncar exeStncar) {
		exeStncarDao.deleteExeStncar(exeStncar);
	}

	/**
	 * 根据报文名、顺位查车辆信息
	 * 
	 * @param rptname
	 * @param car_position
	 * @return
	 */
	public ExeStncar loadExeStncar(String rptname, Integer car_position) {
		return exeStncarDao.loadExeStncar(rptname, car_position);
	}

	/**
	 * 取得最大的报文序号
	 * 
	 * @return
	 */
	public String getMaxRptId(String rptName) {
		return exeStntrnDao.getMaxRptId(rptName);
	}

	/**
	 * 根据报文名获取车辆顺位
	 * 
	 * @param rptname
	 * @return
	 */
	public int getCar_position(String rptname) {
		return exeStncarDao.getCar_position(rptname);
	}

	/**
	 * 自备车利用率
	 * 
	 * @return
	 */
	public List<ExeZbclylVO> getZbclyl(String s_date, String e_date) {
		long timeDiff = (StringUtil.getDateDifferent(s_date, e_date) + 1) * 1440;
		List<ExeZbclylVO> list = new ArrayList<ExeZbclylVO>();
		List<DicAreacorp> listAreacorp = null;
		List<DicSinocorp> listSinocorp = null;
		listAreacorp = exeDicDao.listDicAreacorp("dataAutho");// 取得区域公司列表,根据用户数据权限
		listSinocorp = exeDicDao.listDicSinocorp("dataAuthor");// 取得公司列表，根据用户权限
		List<Object[]> listCarCountByCorp = exeAeiDao.getZbclylByCorp();// 取得各个公司的自备车统计数
		listSinocorp = dealSinoCorp(listSinocorp, listCarCountByCorp);
		List<Object[]> listRunTime = exeAeiDao.getZbclylRuntime(s_date, e_date);// 取得总运行时间统计数
		for (DicAreacorp area : listAreacorp) {
			for (DicSinocorp corp : listSinocorp) {
				if (area.getAreaid().equals(corp.getAreaid())) {
					ExeZbclylVO zbclylVO = new ExeZbclylVO();
					zbclylVO.setAreaid(area.getAreaid());
					zbclylVO.setAreaname(area.getShortname());
					zbclylVO.setCorpid(corp.getCorpid());
					zbclylVO.setCorpname(corp.getShortname());
					for (Object[] obj : listCarCountByCorp) {
						if (obj[0].toString().equals(corp.getCorpid())) {
							zbclylVO.setZcs(Integer.parseInt(obj[1].toString()));// 总车数
							break;
						}
					}

					for (Object[] obj : listRunTime) {
						if (obj[0].toString().equals(corp.getCorpid())) {
							zbclylVO.setZyxsj(Long.parseLong(obj[1].toString()));
							zbclylVO.setZzqsj(timeDiff * zbclylVO.getZcs() - zbclylVO.getZyxsj());
							zbclylVO.setLyl(StringUtil.formatBfl(zbclylVO.getZyxsj(), (zbclylVO.getZcs() * timeDiff)));
							break;
						}
					}
					list.add(zbclylVO);
				}
			}
		}

		return list;
	}

	/**
	 * 处理要显示的企业，把自备车数为0的过滤掉,另外再去掉东方石化化工四厂和扬子江乙酰
	 * 
	 * @return
	 */
	public List<DicSinocorp> dealSinoCorp(List<DicSinocorp> listSinocorp, List<Object[]> listCarCountByCorp) {
		List<DicSinocorp> list = new ArrayList<DicSinocorp>();
		for (DicSinocorp corp : listSinocorp) {
			for (Object[] obj : listCarCountByCorp) {
				if (obj[0].toString().equals(corp.getCorpid())) {
					if (!obj[1].toString().equals("0") && !corp.getShortname().equals("东方石化化工四厂")
							&& !corp.getShortname().equals("扬子江乙酰"))
						list.add(corp);
					break;
				}

			}
		}
		return list;
	}

	/**
	 * 取得所有区域的公司数
	 * 
	 * @return
	 */
	public int[] getListRowspan() {
		List<DicAreacorp> listAreacorp = exeDicDao.listDicAreacorp("dataAutho");
		List<DicSinocorp> listSinocorp = exeDicDao.listDicSinocorp("dataAutho");
		List<Object[]> listCarCountByCorp = exeAeiDao.getZbclylByCorp();// 取得各个公司的自备车统计数
		listSinocorp = dealSinoCorp(listSinocorp, listCarCountByCorp);
		int[] rowspan = new int[listAreacorp.size()];// 区域公司
		int i = 0;
		for (DicAreacorp area : listAreacorp) {
			int k = 0;
			for (DicSinocorp corp : listSinocorp) {
				if (area.getAreaid().equals(corp.getAreaid())) {
					k++;
				}
			}
			rowspan[i] = k;
			i++;
		}
		return rowspan;
	}

	/**
	 * 站区现车分布
	 * 
	 * @return
	 */
	public List<ExeZqxcDistVO> getZqxcDist() {
		List<ExeZqxcDistVO> list = new ArrayList<ExeZqxcDistVO>();
		List<DicSinocorp> listSinocorp = null;
		List<DicEntprStn> listStn = null;
		// listSinocorp = exeDicDao.listDicSinocorp("dataAuthor");//
		// 取得公司列表，根据用户权限
		listSinocorp = exeDicDao.listZqqy(3);// 取得公司列表，根据用户权限
		listStn = exeDicDao.listEntprStn();// 取得所有企业站区

		List<Object[]> listStat = exeAeiDao.getZqxcDist();// 取得所有统计数
		// 拼成要显示的列表
		for (DicSinocorp sinocorp : listSinocorp) {
			ExeZqxcDistVO zqxcDistVO = new ExeZqxcDistVO();
			zqxcDistVO.setCorpid(sinocorp.getCorpid());// 企业id
			zqxcDistVO.setCorpname(sinocorp.getShortname());
			for (DicEntprStn stn : listStn) {
				if (stn.getEntpr_id().equals(sinocorp.getCorpid())) {
					zqxcDistVO.setStnid(stn.getEntpr_stn_code());// 站区id
					zqxcDistVO.setStnname(stn.getEntpr_stn_name());
					break;
				}
			}
			if (zqxcDistVO.getStnid() != null && !zqxcDistVO.getStnid().equals("")) {
				for (Object[] obj : listStat) {
					if (obj[0].toString().equals(zqxcDistVO.getStnid()) && obj[1].toString().equals(("1"))) {
						zqxcDistVO.setZbc(Integer.parseInt(obj[2].toString()));// 自备车数
					}
					if (obj[0].toString().equals(zqxcDistVO.getStnid()) && obj[1].toString().equals(("0"))) {
						zqxcDistVO.setBsc(Integer.parseInt(obj[2].toString()));// 部属车数
					}
				}
			}
			list.add(zqxcDistVO);
		}

		return list;
	}

	/**
	 * 站区现车分布明细
	 * 
	 * @param rpt_stn_code
	 * @param zbc_flag
	 * @return
	 */
	public List<ExeDcarStat> getZqxcDistDetail(String rpt_stn_code, String zbc_flag) {
		return exeDcarStatDao.zqxcDistDetail(rpt_stn_code, zbc_flag);
	}

	/**
	 * 站区进出车辆货物信息匹配统计表
	 * 
	 * @param s_date
	 * @param e_date
	 * @param zbc_flag
	 * @return
	 */
	public List<ExeZqjcclVO> getZqjccl(String s_date, String e_date, String zbc_flag) {
		List<ExeZqjcclVO> list = new ArrayList<ExeZqjcclVO>();
		List<DicSinocorp> listSinocorp = null;
		List<DicEntprStn> listStn = null;
		// listSinocorp = exeDicDao.listDicSinocorp("dataAuthor");//
		// 取得公司列表，根据用户权限
		listSinocorp = exeDicDao.listZqqy(3);// 取得公司列表，根据用户权限
		listStn = exeDicDao.listEntprStn();// 取得所有企业站区
		List<Object[]> listStat = exeAeiDao.getZqjcclList(s_date, e_date, zbc_flag);// 取得所有统计数
		// 拼成要显示的列表
		for (DicSinocorp sinocorp : listSinocorp) {
			ExeZqjcclVO zqjcclVO = new ExeZqjcclVO();
			zqjcclVO.setCorpid(sinocorp.getCorpid());
			zqjcclVO.setCorpname(sinocorp.getShortname());
			for (DicEntprStn stn : listStn) {
				if (stn.getEntpr_id().equals(sinocorp.getCorpid())) {
					zqjcclVO.setStnid(stn.getEntpr_stn_code());// 站区id
					zqjcclVO.setStnname(stn.getEntpr_stn_name());
					break;
				}
			}
			if (zqjcclVO.getStnid() != null && !zqjcclVO.getStnid().equals("")) {
				for (Object[] obj : listStat) {
					if (obj[0].toString().equals(zqjcclVO.getStnid())) {
						if (Integer.parseInt(obj[1].toString()) == 0)// 驶入
						{
							if (Integer.parseInt(obj[2].toString()) == 0)// 未匹配
							{
								zqjcclVO.setIn_wppcls(Integer.parseInt(obj[3].toString()));
							} else if (Integer.parseInt(obj[2].toString()) == 1)// 已匹配
							{
								zqjcclVO.setIn_yppcls(Integer.parseInt(obj[3].toString()));
							}
						} else if (Integer.parseInt(obj[1].toString()) == 1)// 驶出
						{
							if (Integer.parseInt(obj[2].toString()) == 0)// 未匹配
							{
								zqjcclVO.setOut_wppcls(Integer.parseInt(obj[3].toString()));
							} else if (Integer.parseInt(obj[2].toString()) == 1)// 已匹配
							{
								zqjcclVO.setOut_yppcls(Integer.parseInt(obj[3].toString()));
							}
						}
					}
				}
			}
			list.add(zqjcclVO);
		}
		return list;
	}

	/**
	 * 站区车辆进出统计明细
	 * 
	 * @param in_out_flag
	 * @param match
	 * @param stn_code
	 * @param zbc_flag
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public List<ExeStncar> zqjcclStatDetail(String in_out_flag, String match, String stn_code, String zbc_flag,
			String s_date, String e_date) {
		return exeAeiDao.zqjcclStatDetail(in_out_flag, match, stn_code, zbc_flag, s_date, e_date);
	}

	/**
	 * 站区AEI设备监控
	 * 
	 * @return
	 */
	public List<ExeEquipMonitorVO> getEquipMonitor(String date) {
		List<ExeEquipMonitorVO> list = new ArrayList<ExeEquipMonitorVO>();
		List<DicSinocorp> listSinocorp = null;
		List<DicEntprStn> listStn = null;
		List<DicAreacorp> listDicAreacorp = exeDicDao.listDicAreacorp("dataAutho");// 定义区域
		listSinocorp = exeDicDao.listDicSinocorp("dataAuthor");// 取得公司列表，根据用户权限
		listStn = exeDicDao.listEntprStn();// 取得所有企业站区
		List<Object[]> listStat = exeAeiDao.getEquipMonitor(date);// 取得所有统计数
		// 拼成要显示的列表
		for (DicAreacorp area : listDicAreacorp) {
			for (DicSinocorp sinocorp : listSinocorp) {
				if (area.getAreaid().equals(sinocorp.getAreaid())) {
					ExeEquipMonitorVO equipMonitorVO = new ExeEquipMonitorVO();
					int rowspan = 0;
					equipMonitorVO.setAreaname(area.getShortname());
					equipMonitorVO.setAreaid(area.getAreaid());
					equipMonitorVO.setCorpid(sinocorp.getCorpid());
					equipMonitorVO.setCorpname(sinocorp.getShortname());
					for (DicEntprStn stn : listStn) {
						if (stn.getEntpr_id().equals(sinocorp.getCorpid())) {
							equipMonitorVO.setStnid(stn.getEntpr_stn_code());// 站区id
							equipMonitorVO.setStnname(stn.getEntpr_stn_name());
							break;
						}
					}
					if (equipMonitorVO.getStnid() != null && !equipMonitorVO.getStnid().equals("")) {
						for (Object[] obj : listStat) {
							if (obj[0].toString().equals(equipMonitorVO.getStnid())) {
								equipMonitorVO.setRptCount(Integer.parseInt(obj[1].toString()));
								break;
							}
						}
					}
					if (equipMonitorVO.getRptCount() > 0)// 是否故障
						equipMonitorVO.setGz("<font color=green>正常</font>");
					else
						equipMonitorVO.setGz("<font color=red>故障</font>");
					list.add(equipMonitorVO);
				}
			}
		}
		return list;
	}

	/**
	 * 取得自备车停靠统计
	 * 
	 * @param entpr_id
	 * @param over_id
	 * @param over_type
	 * @return
	 */
	public List<int[]> listZyx(String zbc_flag, String area_idArr, String corp_idArr) {
		String over_type = "1";
		String over_id = "";
		String entpr_id = "";// 'zy000010','zy000020'格式，用于exeDicDao.listDicSinocorpByIdArr查询
		String entpr_id2 = "";// ///zy000010,zy000020格式，用于exeOverdueDao.listOverdue查询
		List<DicOverTime> listDicOvertime = exeDicDao.listDicOverTimeByZbc_flag(zbc_flag);// 默认查自备车信息
		// List<DicSinocorp> listDicSinocorp2 =
		// exeDicDao.listDicSinocorp("dataAutho");
		List<DicSinocorp> listDicSinocorp2 = exeDicDao.listZqqy(3);

		if (null != area_idArr && !area_idArr.equals("")) {
			List<DicSinocorp> listSinocorpByAreaid = exeDicDao.listDicSinocorpByAreaIdArr(area_idArr);// 根据区域查询公司
			for (DicSinocorp corp : listSinocorpByAreaid) {
				corp_idArr += corp.getCorpid() + ",";
			}
		}
		for (DicOverTime overTime : listDicOvertime) {
			over_id += overTime.getOver_id() + ",";
		}
		if (over_id.length() > 0)
			over_id = over_id.substring(0, over_id.length() - 1);

		for (DicSinocorp sinocorp : listDicSinocorp2) {
			if (null != corp_idArr && !corp_idArr.equals("")) {
				if (corp_idArr.indexOf(sinocorp.getCorpid()) > -1) {
					entpr_id += "'" + sinocorp.getCorpid() + "',";
					entpr_id2 += sinocorp.getCorpid() + ",";
				}
			} else {
				entpr_id += "'" + sinocorp.getCorpid() + "',";
				entpr_id2 += sinocorp.getCorpid() + ",";
			}
		}
		if (entpr_id.length() > 0) {
			entpr_id = entpr_id.substring(0, entpr_id.length() - 1);
			entpr_id2 = entpr_id2.substring(0, entpr_id2.length() - 1);
		}
		List<int[]> listRet = new ArrayList<int[]>();
		List<ExeOverdue> listExeOverdue = exeAeiDao.listOverdue(entpr_id2, over_id, over_type);// 根据企业id、超时id，超时类型查询数据
		List<DicSinocorp> listDicSinocorp = exeDicDao.listDicSinocorpByIdArr(entpr_id); // 定义企业列表
		List<DicOverTime> listDicOverTime = exeDicDao.listDicOverTimeByIdArr(over_id);// 定义超时id列表
		for (DicSinocorp dicSinocorp : listDicSinocorp) {// 公司循环
			int[] intCount = new int[listDicOverTime.size()];
			int i = 0;
			for (DicOverTime dicOverTime : listDicOverTime) {// 超时id循环
				for (ExeOverdue exeOverdue : listExeOverdue) {
					if (exeOverdue.getId().getEntpr_id().equals(dicSinocorp.getCorpid())
							&& exeOverdue.getId().getOver_id().equals(dicOverTime.getOver_id())) {
						intCount[i] += exeOverdue.getOver_car_num();
					}
				}
				i++;
			}
			listRet.add(intCount);
		}
		return listRet;
	}

	/**
	 * 取得车辆动态详细
	 * 
	 * @param medium_id充装介质id
	 * @param entpr_id企业id
	 * @param over_id超时编号
	 * @return
	 */
	public List<ExeDcarStat> getZyxDetail(String entpr_id, String over_id) {
		DicOverTime dicOverTime = exeDicDao.loadDicOverTime(over_id);
		String s_date = StringUtil.getOptSystemDate(new Date(), 0 - dicOverTime.getMax_days());
		String e_date = StringUtil.getOptSystemDate(new Date(), 0 - dicOverTime.getMin_days());
		return exeDcarStatDao.getZyxDetail(entpr_id, s_date, e_date);
	}

	public void exportZqxcfbExcel(List<ExeDcarStat> listDcatStat, String corpid, HttpServletResponse response) {
		int count = 0;
		if (listDcatStat.size() > 1000)
			count = 1000;
		else
			count = listDcatStat.size();
		String[][] excel = new String[count + 1][13];
		excel[0][0] = "车号";
		excel[0][1] = "车种车型";
		excel[0][2] = "租用标示";
		excel[0][3] = "当前站";
		excel[0][4] = "当前路局";
		excel[0][5] = "报告时间";
		excel[0][6] = "充装介质";
		excel[0][7] = "始发站";
		excel[0][8] = "始发路局";
		excel[0][9] = "终到站";
		excel[0][10] = "终到路局";
		excel[0][11] = "空重标识";
		int ii = 1;

		for (ExeDcarStat dcarStat : listDcatStat) {
			excel[ii][0] = dcarStat.getCar_no();
			excel[ii][1] = dcarStat.getCar_type();
			excel[ii][2] = DicUtil.getCar_rent_flag(dcarStat.getCar_rent_flag(), corpid, dcarStat.getCar_owner_id(),
					dcarStat.getCar_user_id());
			excel[ii][3] = dcarStat.getCur_stn_name();
			excel[ii][4] = DicUtil.getAdm(dcarStat.getCur_adm());
			excel[ii][5] = StringUtil.dateToString(dcarStat.getRpt_time(), "yyyy-MM-dd HH:mm:ss");
			excel[ii][6] = dcarStat.getCar_fill_medium();
			excel[ii][7] = dcarStat.getCdy_o_stn_name();
			excel[ii][8] = DicUtil.getAdm(dcarStat.getOrg_adm());
			excel[ii][9] = dcarStat.getDest_stn_name();
			excel[ii][10] = DicUtil.getAdm(dcarStat.getDest_adm());
			excel[ii][11] = DicUtil.getLe_code(dcarStat.getLe_code());
			ii++;
			if (ii > 1000)
				break;
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		// sheet创建一个工作页
		HSSFSheet sheet = wb.createSheet("车辆动态信息");
		sheet.setDefaultColumnWidth(16);
		sheet.setGridsPrinted(false);
		sheet.setDisplayGridlines(false);
		CellStyle style = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_NORMAL,
						(short) 10));
		CellStyle styleRed = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFFont.COLOR_RED,
						(short) 10));
		for (short i = 0; i < excel.length; i++) {
			// System.out.println("i====" + i);
			// HSSFRow,对应一行
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length - 1; j++) {
				// HSSFCell对应一格
				HSSFCell cell = row.createCell(j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(excel[i][j]);
				if (excel[i][12] != null && excel[i][12].equals("1"))
					cell.setCellStyle(styleRed);
				else
					cell.setCellStyle(style);
			}
		}
		OutputStream os = null;
		try {

			String fileName = "站区现车分布动态详细导出列表";
			response.setContentType("application/msexcel");
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
			System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				os = null;
				response.flushBuffer();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
