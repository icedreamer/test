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

import com.tlys.equ.dao.EquCarCertificateDao;
import com.tlys.equ.model.EquCarCertificate;

/**
 * @author fengym
 * 
 */
@Service
public class EquCarCertificateService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EquCarCertificateDao equCarCertificateDao;

	public List<EquCarCertificate> findAll() {
		return equCarCertificateDao.findAll();
	}

	public List<EquCarCertificate> find(EquCarCertificate equCarCertificate) {
		return equCarCertificateDao.find(equCarCertificate);
	}

	public EquCarCertificate load(Long id) {
		return equCarCertificateDao.load(id);
	}

	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param equCarCertificate
	 *            ,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(EquCarCertificate equCarCertificate, boolean isNew) {
		if (isNew) {
			equCarCertificateDao.saveOrUpdate(equCarCertificate);
		} else {
			equCarCertificateDao.updateEntity(equCarCertificate, equCarCertificate.getId());
		}
	}

	public void save(EquCarCertificate equCarCertificate) {
		equCarCertificateDao.save(equCarCertificate);
	}

	public void update(EquCarCertificate equCarCertificate) {
		equCarCertificateDao.update(equCarCertificate);
	}

	public void saveOrUpdate(EquCarCertificate equCarCertificate) {
		equCarCertificateDao.saveOrUpdate(equCarCertificate);
	}

	public void delete(Long id) {
		equCarCertificateDao.delete(id);

	}

	public void deleteByCarno(String carno) {
		String extHql = " where trainno='" + carno + "'";
		List<EquCarCertificate> list = equCarCertificateDao.findAll(extHql);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			EquCarCertificate obj = (EquCarCertificate) iter.next();
			equCarCertificateDao.delete(obj);
		}
	}

	public void updateOther(Long id, String carno) {
		String extHql = " where trainno='" + carno + "' and id != " + id;
		List<EquCarCertificate> otherList = equCarCertificateDao.findAll(extHql);
		for (Iterator iter = otherList.iterator(); iter.hasNext();) {
			EquCarCertificate obj = (EquCarCertificate) iter.next();
			obj.setIsvalid("0");
			equCarCertificateDao.saveOrUpdate(obj);
		}
	}

	public List<EquCarCertificate> findEquCarCertificate(final String corpid) {
		return equCarCertificateDao.findEquCarCertificate(corpid);
	}
}
