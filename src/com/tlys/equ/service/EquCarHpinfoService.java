/**
 * 
 */
package com.tlys.equ.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.equ.dao.EquCarHpinfoDao;
import com.tlys.equ.model.EquCarHpinfo;

/**
 * @author fengym
 *
 */
@Service
public class EquCarHpinfoService{
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EquCarHpinfoDao equCarHpinfoDao;
	
	public List<EquCarHpinfo> findAll(){
		return equCarHpinfoDao.findAll();
	}
	
	public List<EquCarHpinfo> find(EquCarHpinfo obj){
		return equCarHpinfoDao.find(obj);
	}
	
	
	public EquCarHpinfo load(String id) {
		return equCarHpinfoDao.load(id);
	}
	
	
	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * @param equCarHpinfo,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(EquCarHpinfo equCarHpinfo,boolean isNew) {
		if(isNew){
			equCarHpinfoDao.saveOrUpdate(equCarHpinfo);
		}else{
			equCarHpinfoDao.update(equCarHpinfo);//.updateEntity(equCarHpinfo, equCarHpinfo.getCarno());
		}
	}
	
	public void delete(String id) {
		equCarHpinfoDao.delete(id);
		
	}
}
