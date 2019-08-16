package com.tlys.exe.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlys.dic.model.DicRwstation;
import com.tlys.equ.model.EquCar;
import com.tlys.exe.dao.ExeDicDao;

/**
 * 处理与数据库操作有关的字典工具，供前台页面展示时调用
 * 
 * @author 孔垂云
 * 
 */
@Component
public class DicDBUtil {

	@Autowired
	ExeDicDao dicDao;

	/**
	 * 根据车站码取得车站名称
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
	 * 根据车号取得企业名称，从自备车台账里面查找,用于从前台页面直接获取自备车里面的使用企业
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
