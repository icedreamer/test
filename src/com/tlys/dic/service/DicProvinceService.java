/**
 * 
 */
package com.tlys.dic.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.dic.dao.DicProvinceDao;
import com.tlys.dic.model.DicProvince;

/**
 * @author fengym
 * 
 */
@Service
public class DicProvinceService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProvinceDao dicProvinceDao;

	public List<DicProvince> findAll() {
		return dicProvinceDao.findAll(" order by provinceid");
	}

	public List<DicProvince> find(DicProvince dac) {
		return null;// dicTransporterDao.find(dac);
	}

	public DicProvince load(String id) {
		return dicProvinceDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicProvince
	 *            ,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(DicProvince dicProvince, boolean isNew) {
		if (isNew) {
			dicProvinceDao.saveOrUpdate(dicProvince);
		} else {
			dicProvinceDao.updateEntity(dicProvince, dicProvince.getProvinceid());
		}

	}

	public void delete(String id) {
		dicProvinceDao.delete(id);

	}

	public List<DicProvince> findDicProvince(String customerid) {
		return dicProvinceDao.findDicProvince(customerid);
	}
}
