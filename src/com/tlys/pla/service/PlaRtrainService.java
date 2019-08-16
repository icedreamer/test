package com.tlys.pla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.pla.dao.PlaRtrainDao;

@Service
public class PlaRtrainService {
	@Autowired
	PlaRtrainDao plaRtrainDao;
	
	
	/**
	 * ���ô洢���̣�ͬ���복�������ƻ�
	 */
	public void syncPlaRtrain(String monthStr){
		plaRtrainDao.syncPlaRtrain(monthStr);
	}
}
