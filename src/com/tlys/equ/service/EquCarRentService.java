/**
 * 
 */
package com.tlys.equ.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.equ.dao.EquCarRentDao;
import com.tlys.equ.model.EquCarRent;

/**
 * @author fengym
 * 
 */
@Service
public class EquCarRentService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EquCarRentDao equCarRentDao;

	public List<EquCarRent> findAll() {
		return equCarRentDao.findAll();
	}

	public List<EquCarRent> find(EquCarRent obj) {
		return equCarRentDao.find(obj);
	}

	public EquCarRent load(Long id) {
		return equCarRentDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param equCarRent
	 *            ,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(EquCarRent equCarRent, boolean isNew) {
		if (isNew) {
			equCarRentDao.saveOrUpdate(equCarRent);
		} else {
			equCarRentDao.updateEntity(equCarRent, equCarRent.getId());
		}
	}

	public void delete(Long id) {
		equCarRentDao.delete(id);

	}

	public void deleteByCarno(String carno) {
		String extHql = " where carno='" + carno + "'";
		List<EquCarRent> list = equCarRentDao.findAll(extHql);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			EquCarRent obj = (EquCarRent) iter.next();
			equCarRentDao.delete(obj);
		}
	}

	/**
	 * ��ȡ���޳���������
	 * 
	 * @return
	 */
	public int getEquRentCars() {
		return equCarRentDao.getEquRentCars();
	}

	/**
	 * ��ȡ���⳵��������
	 * 
	 * @return
	 */
	public int getEquCorpCars() {
		return equCarRentDao.getEquCorpCars();
	}
}
