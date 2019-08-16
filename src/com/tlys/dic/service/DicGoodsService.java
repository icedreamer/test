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
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("dicGoods");
	}
}
