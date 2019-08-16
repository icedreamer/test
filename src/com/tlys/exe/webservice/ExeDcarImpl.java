package com.tlys.exe.webservice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.LisUserAuthUtils;
import com.tlys.dic.model.DicRwstation;
import com.tlys.equ.model.EquCar;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.exe.dao.ExeDicDao;
import com.tlys.exe.dao.ExeGcarEvtaDao;
import com.tlys.exe.dao.ExeTransportDao;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeGcarEvta;
import com.tlys.exe.model.ExeTransport;

@Service
public class ExeDcarImpl implements IExeDcar {

	@Autowired
	private ExeDcarStatDao exeDcarStatDao;

	@Autowired
	private ExeGcarEvtaDao exeGcarEvtaDao;

	@Autowired
	private ExeTransportDao exeTransportDao;

	@Autowired
	private ExeDicDao dicDao;

	@Autowired
	private LisUserAuthUtils listUserAuthUtils;// 校验用户是否有访问权限的类

	/**
	 * 货物运输中的实际运量数据接口(车号、发站代码、发货日期、货票号、实际运量 )
	 * 第一次按照车号、发站代码、货票号查，如果没有的话按照车号、发站代码、发货日期查，发货日期前后8天。如果记录存在更新实际运量。cdy_date即为制票日期
	 * 
	 * @param car_no车号
	 * @param cdy_org_stn发站代码
	 * @param dest_stn终到站代码
	 * @param cdy_date发货日期
	 * @param wb_nbr货票号
	 * @param car_actual_wgt实际运量
	 * @param shipper_name发货人汉字
	 * @param con_name收货人汉字
	 * @param cdy_code货物品名代码，需要转化为铁路品名代码
	 * @param login_name调用接口用户代码，暂不用
	 * @param report_num记录重复匹配的次数
	 */
	public String updateTransActualWgt(String car_no, String cdy_org_stn, String dest_stn, String cdy_date,
			String wb_nbr, Double car_actual_wgt, String shipper_name, String con_name, String cdy_code, int repeat_num) {
		String note = car_no + "-" + cdy_org_stn + "-" + dest_stn + "-" + cdy_date + "-" + wb_nbr + "-"
				+ car_actual_wgt + "-" + shipper_name + "-" + con_name + "-" + cdy_code + "-" + repeat_num;// 定义存入日志的备注
		String strRet = "";
		String logFlag = "";

		DicRwstation dicRwstation = dicDao.getRwStationByStationid(cdy_org_stn);
		DicRwstation dicDestStation = dicDao.getRwStationByStationid(dest_stn);
		String railCdy_code = dicDao.getCdy_codeByList(cdy_code);
		if (dicRwstation == null)// //发站码不存在
		{
			strRet = "N;-3@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS用户", note);
		} else if (dicDestStation == null)// //到站码不存在
		{
			strRet = "N;-4@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS用户", note);
		} else if (railCdy_code.equals("")) {
			strRet = "N;-5@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS用户", note);
		} else {
			// /计算发货日期起止范围
			int date_diff[] = dicDao.getCdy_date_diff();
			String cdy_date_pre = StringUtil.getOptSystemDate(StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"),
					-date_diff[0]);
			String cdy_date_last = StringUtil.getOptSystemDate(StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"),
					date_diff[1]);
			ExeTransport transport = exeTransportDao.queryExeTransport(car_no, dicRwstation.getTelegramid(),
					cdy_date_pre, cdy_date_last);// 首先按照车号、发站代码、发货日期前后推几天查询
			if (transport == null) {
				if (repeat_num < 7) {
					strRet = "N;-1@@";
					logFlag = "N";
				} else {
					String realCdy_date = StringUtil.dateToString(transport.getWb_date(), "yyyy-MM-dd");// 定义实际货票日期
					// 增加这条记录至货物运输表
					ExeTransport exeTransport = new ExeTransport();
					exeTransport.setWb_date(StringUtil.StringToDate(realCdy_date, "yyyy-MM-dd"));// 制票日期
					exeTransport.setWb_nbr(transport.getWb_nbr());// 货票号
					exeTransport.setCar_no(car_no);// 车号
					exeTransport.setCdy_org_stn(cdy_org_stn);// 始发站
					exeTransport.setCdy_o_stn_name(dicRwstation.getShortname());
					exeTransport.setDest_stn(dest_stn);// 终到站
					exeTransport.setDest_stn_name(dicDestStation.getShortname());
					exeTransport.setOrg_adm(dicRwstation.getPycode());// 始发局
					exeTransport.setDest_adm(dicDestStation.getPycode());// 终到局
					exeTransport.setShipper_name(shipper_name);// 发货人
					exeTransport.setCon_name(con_name);// 收货人
					exeTransport.setCar_actual_wgt(car_actual_wgt);// 实际载重
					exeTransport.setMsg_type("L");// 信息来源，手工录入
					exeTransport.setMsg_status("0");// 状态
					exeTransport.setLe_code("1");// 重空，默认为重
					exeTransport.setHp_editor("LIS");// 记录增改用户ID
					exeTransport.setHp_edit_time(new Date());// 记录增改时间
					String wb_id = realCdy_date.substring(8, 10) + realCdy_date.substring(5, 7)
							+ realCdy_date.substring(0, 4) + cdy_org_stn + "  " + transport.getWb_nbr();
					exeTransport.setWb_id(wb_id);// wb_id
					exeTransport.setWb_date_str(realCdy_date);
					EquCar equCar = dicDao.getEquCarByCarno(exeTransport.getCar_no());
					if (equCar == null)
						exeTransport.setZbc_flag("0");
					else
						exeTransport.setZbc_flag("1");
					exeTransportDao.saveTransportInWebService(exeTransport);// 增加货物运输信息
					strRet = "A;" + wb_nbr + "," + realCdy_date + "@@";
					logFlag = "A";
				}
				exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
						cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS用户", note);
			} else {// 如果记录存在更新实际运量
				int flag = exeTransportDao.updateAct_wgt(transport.getRec_id(), car_actual_wgt);
				if (flag == 1) {
					strRet = "Y;" + transport.getWb_nbr() + ","
							+ StringUtil.dateToString(transport.getWb_date(), "yyyy-MM-dd") + "@@";
					logFlag = "Y";
				} else {
					strRet = "N;-2@@";
					logFlag = "N";
				}
				exeTransportDao.addWebServiceOperaLog(transport.getCar_no(), StringUtil.StringToDate(cdy_date,
						"yyyy-MM-dd"), transport.getWb_date(), cdy_org_stn, car_actual_wgt, transport.getWb_nbr(),
						logFlag, strRet, "LIS用户", note);
			}
		}
		return strRet;
	}

	/**
	 * 运行动态数据接口 判断当前时间，如果在2-4点之间，进行查询，否则返回错误码N;-3@@
	 */
	public String getExeDcarStat(String car_no, String login_name) {
		String strRet = "";
		if (listUserAuthUtils.hasPermission("lis_" + login_name)) {
			if (car_no.length() != 7)
				strRet = "N;-2@@";
			else {
				ExeDcarStat dcarStat = exeDcarStatDao.loadExeDcarStat2(car_no);
				if (dcarStat != null) {
					strRet = "Y;" + StringUtil.dateToString(dcarStat.getA_d_time(), "yyyy-MM-dd HH:mm:ss") + ","
							+ dicDao.getRwStationByTelegramid(dcarStat.getA_d_stn()).getStationid() + ","
							+ dcarStat.getA_d_stn_name() + "@@";
				} else {
					strRet = "N;-1@@";
				}
			}
		} else {
			strRet = "N;-3@@";
		}
		return strRet;
	}

	/**
	 * 车辆轨迹数据接口，如果e_date大于等于当天，则当前日期的凌晨3点，如果小于当天，小于等于e_date
	 * 如果e_date-s_date大于30天，则按照30天算，如果小于等于30天，则按照两个日期间查
	 * 
	 * @return
	 */
	public String getExeGcarEvta(String car_no, String s_date, String e_date, String login_name) {
		String strRet = "";
		if (listUserAuthUtils.hasPermission("lis_" + login_name)) {
			if (car_no.length() != 7)
				strRet = "N;-2@@";
			else {
				if (StringUtil.getDateDifferent(StringUtil.getSystemDate(), e_date) >= 0) {
					e_date = StringUtil.getSystemDate() + " 03:00:00";
				} else {
					e_date = e_date + " 23:59:59";
				}
				if (StringUtil.getDateDifferent(s_date, e_date) > 30) {
					s_date = StringUtil.getOptSystemDate(StringUtil.StringToDate(e_date, "yyyy-MM-dd"), -30)
							+ " 00:00:00";
				} else {
					s_date = s_date + " 00:00:00";
				}
				List<ExeGcarEvta> list = exeGcarEvtaDao.getGcarEvta2(car_no, s_date, e_date);
				if (list.size() == 0) {
					strRet = "N;-1@@";
				} else {
					strRet = "Y;";
					for (ExeGcarEvta gcarEvta : list) {
						if (gcarEvta.getA_d_stn() != null && !gcarEvta.getA_d_stn().equals(""))
							strRet += StringUtil.dateToString(gcarEvta.getA_d_time(), "yyyy-MM-dd HH:mm:ss") + ","
									+ dicDao.getRwStationByTelegramid(gcarEvta.getA_d_stn()).getStationid() + ","
									+ gcarEvta.getA_d_stn_name() + "##";
					}
					strRet += "@@";
				}
			}
		} else {
			strRet = "N;-3@@";
		}
		return strRet;
	}

}
