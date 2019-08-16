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
	private LisUserAuthUtils listUserAuthUtils;// У���û��Ƿ��з���Ȩ�޵���

	/**
	 * ���������е�ʵ���������ݽӿ�(���š���վ���롢�������ڡ���Ʊ�š�ʵ������ )
	 * ��һ�ΰ��ճ��š���վ���롢��Ʊ�Ų飬���û�еĻ����ճ��š���վ���롢�������ڲ飬��������ǰ��8�졣�����¼���ڸ���ʵ��������cdy_date��Ϊ��Ʊ����
	 * 
	 * @param car_no����
	 * @param cdy_org_stn��վ����
	 * @param dest_stn�յ�վ����
	 * @param cdy_date��������
	 * @param wb_nbr��Ʊ��
	 * @param car_actual_wgtʵ������
	 * @param shipper_name�����˺���
	 * @param con_name�ջ��˺���
	 * @param cdy_code����Ʒ�����룬��Ҫת��Ϊ��·Ʒ������
	 * @param login_name���ýӿ��û����룬�ݲ���
	 * @param report_num��¼�ظ�ƥ��Ĵ���
	 */
	public String updateTransActualWgt(String car_no, String cdy_org_stn, String dest_stn, String cdy_date,
			String wb_nbr, Double car_actual_wgt, String shipper_name, String con_name, String cdy_code, int repeat_num) {
		String note = car_no + "-" + cdy_org_stn + "-" + dest_stn + "-" + cdy_date + "-" + wb_nbr + "-"
				+ car_actual_wgt + "-" + shipper_name + "-" + con_name + "-" + cdy_code + "-" + repeat_num;// ���������־�ı�ע
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
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS�û�", note);
		} else if (dicDestStation == null)// //��վ�벻����
		{
			strRet = "N;-4@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS�û�", note);
		} else if (railCdy_code.equals("")) {
			strRet = "N;-5@@";
			logFlag = "N";
			exeTransportDao.addWebServiceOperaLog(car_no, StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), null,
					cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS�û�", note);
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
						cdy_org_stn, car_actual_wgt, wb_nbr, logFlag, strRet, "LIS�û�", note);
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
						logFlag, strRet, "LIS�û�", note);
			}
		}
		return strRet;
	}

	/**
	 * ���ж�̬���ݽӿ� �жϵ�ǰʱ�䣬�����2-4��֮�䣬���в�ѯ�����򷵻ش�����N;-3@@
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
	 * �����켣���ݽӿڣ����e_date���ڵ��ڵ��죬��ǰ���ڵ��賿3�㣬���С�ڵ��죬С�ڵ���e_date
	 * ���e_date-s_date����30�죬����30���㣬���С�ڵ���30�죬�����������ڼ��
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
