package com.tlys.exe.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.exe.dao.ExeGcarEvtaDao;
import com.tlys.exe.dao.ExeTransportDao;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeGcarEvta;
import com.tlys.exe.model.ExeTransport;

/**
 * ������̬�ӿڷ���
 * 
 * @author �״���
 * 
 */
@Service
public class ExeInterfaceService {

	@Autowired
	private ExeDcarStatDao exeDcarStatDao;

	@Autowired
	private ExeGcarEvtaDao exeGcarEvtaDao;

	@Autowired
	private ExeTransportDao exeTransportDao;

	

	/**
	 * ���������е�ʵ���������ݽӿ�(���š���վ���롢�������ڡ���Ʊ�š�ʵ������ )
	 * ��һ�ΰ��ճ��š���վ���롢��Ʊ�Ų飬���û�еĻ����ճ��š���վ���롢�������ڲ飬��������ǰ��8�졣�����¼���ڸ���ʵ��������cdy_date��Ϊ��Ʊ����
	 * 
	 * @return
	 */
	public String updateTransActualWgtInterface(String car_no, String cdy_org_stn, String cdy_date, String wb_nbr,
			Double car_actual_wgt) {
		String strRet = "";
		ExeTransport transport = exeTransportDao.queryExeTransport(car_no, cdy_org_stn, wb_nbr);// ���Ȱ��ճ��š���վ���롢��Ʊ�Ų�ѯ
		if (transport == null)// ��������ڣ����ճ��š���վ���롢�������ڲ飬��������ǰ��8��飬
		{
			String s_date = StringUtil.getOptSystemDate(StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), -8);
			String e_date = StringUtil.getOptSystemDate(StringUtil.StringToDate(cdy_date, "yyyy-MM-dd"), 8);
			transport = exeTransportDao.queryExeTransport(car_no, cdy_org_stn, wb_nbr, s_date, e_date);
		}
		if (transport == null) {
			strRet = "N;-1@@";
		} else {// �����¼���ڸ���ʵ������
			String wb_date = StringUtil.dateToString(transport.getWb_date(), "yyyy-MM-dd");
			String wb_id = wb_date.substring(8, 10) + wb_date.substring(5, 7) + wb_date.substring(0, 4)
					+ transport.getCdy_org_stn() + "  " + transport.getWb_nbr();
			ExeTransport load = exeTransportDao.load(wb_id);
			load.setCar_actual_wgt(car_actual_wgt);
			load.setCap_wgt_editor("LIS");
			load.setCapw_edit_time(new Date());
			exeTransportDao.updateExeTransport(load);
			strRet = "Y;" + load.getWb_nbr() + "," + StringUtil.dateToString(load.getWb_date(), "yyyy-MM-dd") + "@@";
		}
		// strRet = "N;-2@@";
		return strRet;
	}

	/**
	 * ���ж�̬���ݽӿ�
	 */
	public String getExeDcarStatInterface(String car_no) {
		String strRet = "";
		if (car_no.length() != 7)
			strRet = "N;-2@@";
		else {
			ExeDcarStat dcarStat = exeDcarStatDao.loadExeDcarStat(car_no);
			if (dcarStat != null) {
				strRet = "Y;" + StringUtil.dateToString(dcarStat.getA_d_time(), "yyyy-MM-dd HH:mm:ss") + ","
						+ dcarStat.getA_d_stn() + "," + dcarStat.getA_d_stn_name() + "@@";
			} else {
				strRet = "N;-1@@";
			}
		}
		return strRet;
	}

	/**
	 * �����켣���ݽӿ�
	 * 
	 * @return
	 */
	public String getExeGcarEvtaInterface(String car_no, String s_date, String e_date) {
		String strRet = "";
		if (car_no.length() != 7)
			strRet = "N;-2@@";
		else {
			List<ExeGcarEvta> list = exeGcarEvtaDao.getGcarEvta(car_no, s_date, e_date);
			if (list.size() == 0) {
				strRet = "N;-1@@";
			} else {
				strRet = "Y;";
				for (ExeGcarEvta gcarEvta : list) {
					strRet += StringUtil.dateToString(gcarEvta.getA_d_time(), "yyyy-MM-dd HH:mm:ss") + ","
							+ gcarEvta.getA_d_stn() + "," + gcarEvta.getA_d_stn_name() + "##";
				}
				strRet += "@@";
			}
		}
		return strRet;
	}
}
