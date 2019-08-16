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
import com.tlys.dic.dao.DicGoodsDao;
import com.tlys.dic.model.DicGoods;

/**
 * @author fengym
 * 
 */
@Service
public class DicGoodsService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DicGoodsDao dicGoodsDao;
	@Autowired
	DicMap dicMap;

	public List<DicGoods> findAll() {
		return dicGoodsDao.findAlls("pmhz");//findAll();
		
	}

	public DicGoods getDicGoods(String dm) {
		return dicGoodsDao.load(dm);
	}

	public List<DicGoods> find(DicGoods dac) {
		return dicGoodsDao.find(dac);
	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicGoods");
	}
}
