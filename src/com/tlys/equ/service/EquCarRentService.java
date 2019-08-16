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
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param equCarRent
	 *            ,isNew:标志当前是新增操作还是修改操作
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
	 * 获取租赁车辆的数量
	 * 
	 * @return
	 */
	public int getEquRentCars() {
		return equCarRentDao.getEquRentCars();
	}

	/**
	 * 获取出租车辆的数量
	 * 
	 * @return
	 */
	public int getEquCorpCars() {
		return equCarRentDao.getEquCorpCars();
	}
}
