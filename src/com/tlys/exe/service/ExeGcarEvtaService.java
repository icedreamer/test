package com.tlys.exe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.exe.dao.ExeGcarEvtaDao;
import com.tlys.exe.model.ExeGcarEvta;

/**
 * �����켣Service
 * 
 * @author �״���
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
	 * ��ѯ���й켣
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
	 * ȡ�����й켣����
	 */
	public int getListGcarEvtaCount(String car_no, String s_date, String e_date) {
		return gCarEvtaDao.getListGcarEvtaCount(car_no, s_date, e_date);
	}
}
