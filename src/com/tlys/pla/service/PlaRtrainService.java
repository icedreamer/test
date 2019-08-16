package com.tlys.pla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.pla.dao.PlaRtrainDao;

@Service
public class PlaRtrainService {
	@Autowired
	PlaRtrainDao plaRtrainDao;
	
	
	/**
	 * 调用存储过程，同步请车及批复计划
	 */
	public void syncPlaRtrain(String monthStr){
		plaRtrainDao.syncPlaRtrain(monthStr);
	}
}
