package com.tlys.exe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.exe.dao.ExeGcarEvtaDao;
import com.tlys.exe.model.ExeGcarEvta;

/**
 * 车辆轨迹Service
 * 
 * @author 孔垂云
 * 
 */
@Service
public class ExeGcarEvtaService {

	@Autowired
	private ExeGcarEvtaDao gCarEvtaDao;

	public ExeGcarEvtaDao getGCarEvtaDao() {
		return gCarEvtaDao;
	}

	public void setGCarEvtaDao(ExeGcarEvtaDao carEvtaDao) {
		gCarEvtaDao = carEvtaDao;
	}

	/**
	 * 查询运行轨迹
	 * 
	 * @param car_no
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public List<ExeGcarEvta> listGcarEvta(String car_no, String s_date, String e_date) {
		return gCarEvtaDao.listGcarEvta(car_no, s_date, e_date);
	}

	/**
	 * 取得运行轨迹数量
	 */
	public int getListGcarEvtaCount(String car_no, String s_date, String e_date) {
		return gCarEvtaDao.getListGcarEvtaCount(car_no, s_date, e_date);
	}
}
