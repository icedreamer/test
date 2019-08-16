package com.tlys.dic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.dic.dao.DicCarcostDao;
import com.tlys.dic.model.DicCarcost;

@Service
public class DicCarcostService {

	@Autowired
	DicCarcostDao dicCarcostDao;

	public List<DicCarcost> findDicCarcost() {
		return dicCarcostDao.findDicCarcost();
	}

	public DicCarcost load(String costid) {
		return (DicCarcost) dicCarcostDao.load(costid);
	}
}
