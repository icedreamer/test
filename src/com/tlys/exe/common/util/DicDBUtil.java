package com.tlys.exe.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlys.dic.model.DicRwstation;
import com.tlys.equ.model.EquCar;
import com.tlys.exe.dao.ExeDicDao;

/**
 * ���������ݿ�����йص��ֵ乤�ߣ���ǰ̨ҳ��չʾʱ����
 * 
 * @author �״���
 * 
 */
@Component
public class DicDBUtil {

	@Autowired
	ExeDicDao dicDao;

	/**
	 * ���ݳ�վ��ȡ�ó�վ����
	 * 
	 * @param stationid
	 * @return
	 */
	public String getDicRwstationName(String stationid) {
		DicRwstation station = dicDao.getRwStationByTelegramid(stationid);
		if (station == null)
			return "";
		else
			return station.getShortname();
	}

	/**
	 * ���ݳ���ȡ����ҵ���ƣ����Ա���̨���������,���ڴ�ǰ̨ҳ��ֱ�ӻ�ȡ�Ա��������ʹ����ҵ
	 * 
	 * @param stationid
	 * @return
	 */
	public String getCorpNameByCar_no(String car_no) {
		EquCar equCar = dicDao.getEquCarByCarno(car_no);
		if (equCar == null)
			return "";
		else
			return equCar.getCorpshrinkname();
	}

}
