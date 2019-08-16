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
		// "������·��", "ʯ��ׯ��վ", "111", "LIS", 7);
		// System.out.println(aString);

		String car_no = "0693951";
		String cdy_org_stn = "Q01340";
		String dest_stn = "Q01340";
		String cdy_date = "2012-04-01";
		String wb_nbr = "S085334";
		double car_actual_wgt = 50.0;
		String shipper_name = "������·��";

		String con_name = "ʯ��ׯ��վ";
		String cdy_code = "0000000060005499";
		String login_name = "LIS�û�";
		int repeat_num = 1;

		String note = car_no + "-" + cdy_org_stn + "-" + dest_stn + "-" + cdy_date + "-" + wb_nbr + "-"
				+ car_actual_wgt + "-" + shipper_name + "-" + con_name + "-" + cdy_code + "-" + login_name + "-"
				+ repeat_num;// ���������־�ı�ע
		String strRet = "";
		String logFlag = "";

		DicRwstation dicRwstation = dicDao.getRwStationByStationid(cdy_org_stn);
		DicRwstation dicDestStation = dicDao.getRwStationByStationid(dest_stn);
		String railCdy_code = dicDao.getCdy_codeByList(cdy_code);
		if (dicRwstation == null)// //��վ�벻����
		{
			strRet = "N;-3@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, login_name, note);
		} else if (dicDestStation == null)// //��վ�벻����
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
			// /���㷢��������ֹ��Χ
			int date_diff[] = dicDao.getCdy_date_diff();
			String cdy_date_pre = StringUtil.getOptSystemDate(StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"),
					-date_diff[0]);
			String cdy_date_last = StringUtil.getOptSystemDate(StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"),
					date_diff[1]);
			ExeTransport transport = exeTransportDao.queryExeTransport(car_no, dicRwstation.getTelegramid(),
					cdy_date_pre, cdy_date_last);// ���Ȱ��ճ��š���վ���롢��������ǰ���Ƽ����ѯ
			if (transport == null) {
				if (repeat_num < 7) {
					strRet = "N;-1@@";
					logFlag = "N";
				} else {
					String realCdy_date = StringUtil.dateToString(transport.getWb_date(), "yyyy-MM-dd");// ����ʵ�ʻ�Ʊ����
					// ����������¼�����������
					ExeTransport exeTransport = new ExeTransport();
					exeTransport.setWb_date(StringUtil.StringToDate(realCdy_date, "yyyy-MM-dd"));// ��Ʊ����
					exeTransport.setWb_nbr(transport.getWb_nbr());// ��Ʊ��
					exeTransport.setCar_no(car_no);// ����
					exeTransport.setCdy_org_stn(cdy_org_stn);// ʼ��վ
					exeTransport.setCdy_o_stn_name(dicRwstation.getShortname());
					exeTransport.setDest_stn(dest_stn);// �յ�վ
					exeTransport.setDest_stn_name(dicDestStation.getShortname());
					exeTransport.setOrg_adm(dicRwstation.getPycode());// ʼ����
					exeTransport.setDest_adm(dicDestStation.getPycode());// �յ���
					exeTransport.setShipper_name(shipper_name);// ������
					exeTransport.setCon_name(con_name);// �ջ���
					exeTransport.setCar_actual_wgt(car_actual_wgt);// ʵ������
					exeTransport.setMsg_type("L");// ��Ϣ��Դ���ֹ�¼��
					exeTransport.setMsg_status("0");// ״̬
					exeTransport.setLe_code("1");// �ؿգ�Ĭ��Ϊ��
					exeTransport.setHp_editor("LIS");// ��¼�����û�ID
					exeTransport.setHp_edit_time(new Date());// ��¼����ʱ��
					String wb_id = realCdy_date.substring(8, 10) + realCdy_date.substring(5, 7)
							+ realCdy_date.substring(0, 4) + cdy_org_stn + "  " + transport.getWb_nbr();
					exeTransport.setWb_id(wb_id);// wb_id
					exeTransport.setWb_date_str(realCdy_date);
					EquCar equCar = dicDao.getEquCarByCarno(exeTransport.getCar_no());
					if (equCar == null)
						exeTransport.setZbc_flag("0");
					else
						exeTransport.setZbc_flag("1");
					exeTransportDao.saveTransportInWebService(exeTransport);// ���ӻ���������Ϣ
					strRet = "A;" + wb_nbr + "," + realCdy_date + "@@";
					logFlag = "A";
				}
				exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
						cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, login_name, note);
			} else {// �����¼���ڸ���ʵ������
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
