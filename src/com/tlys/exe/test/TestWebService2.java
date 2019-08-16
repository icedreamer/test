package com.tlys.exe.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.dic.model.DicRwstation;
import com.tlys.equ.model.EquCar;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeDicDao;
import com.tlys.exe.dao.ExeTransportDao;
import com.tlys.exe.model.ExeTransport;

public class TestWebService2 extends BaseTest {
	// @Autowired
	// ExeDcarImpl exeDcarImpl;
	@Autowired
	ExeDicDao dicDao;
	@Autowired
	ExeTransportDao exeTransportDao;

	@Test()
	public void testUpdateTransActualWgt() {
		// String aString = exeDcarImpl.updateTransActualWgt("0693951",
		// "Q01340", "Q01340", "2012-04-01", "S085334", 50.0,
		// "北京铁路局", "石家庄车站", "111", "LIS", 7);
		// System.out.println(aString);

		String car_no = "0693951";
		String cdy_org_stn = "Q01340";
		String dest_stn = "Q01340";
		String cdy_date = "2012-04-01";
		String wb_nbr = "S085334";
		double car_actual_wgt = 50.0;
		String shipper_name = "北京铁路局";

		String con_name = "石家庄车站";
		String cdy_code = "0000000060005499";
		String login_name = "LIS用户";
		int repeat_num = 1;

		String note = car_no + "-" + cdy_org_stn + "-" + dest_stn + "-" + cdy_date + "-" + wb_nbr + "-"
				+ car_actual_wgt + "-" + shipper_name + "-" + con_name + "-" + cdy_code + "-" + login_name + "-"
				+ repeat_num;// 定义存入日志的备注
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
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, login_name, note);
		} else if (dicDestStation == null)// //到站码不存在
		{
			strRet = "N;-4@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, login_name, note);
		} else if (railCdy_code.equals("")) {
			strRet = "N;-5@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, login_name, note);
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
						cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, login_name, note);
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
						logFlag, strRet, login_name, note);
			}
		}
		System.out.println(strRet);
	}
}
