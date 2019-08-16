/**
 * 
 */
package com.tlys.dic.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.DicMap;
import com.tlys.dic.dao.DicRwstationDao;
import com.tlys.dic.model.DicRwstation;

/**
 * @author fengym
 * 
 */
@Service
public class DicRwstationService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicRwstationDao dicRwstationDao;

	@Autowired
	DicMap dicMap;

	public List<DicRwstation> findAll() {
		List<DicRwstation> acList = dicRwstationDao.findAll();
		return acList;
	}

	public List<DicRwstation> find(DicRwstation dac) throws Exception {
		List<DicRwstation> acList = dicRwstationDao.find(dac);
		dicMap.bdProv(acList);
		return acList;
	}

	public DicRwstation load(String id) {
		return dicRwstationDao.load(id);
	}

	/**
	 * 根据企业和发货站的记录数控制表取得发站
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicRwstation> findDicRwstation(String corpid) {
		return dicRwstationDao.findDicRwstation(corpid);
	}

	/**
	 * 根据客户和到站的记录数控制表 取得 到站
	 * 
	 * @param customerid
	 * @return
	 */
	public List<DicRwstation> findCustomerDicRwstation(String customerid) {
		return dicRwstationDao.findCustomerDicRwstation(customerid);
	}

}
