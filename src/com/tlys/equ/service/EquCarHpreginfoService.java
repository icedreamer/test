/**
 * 
 */
package com.tlys.equ.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.equ.dao.EquCarHpreginfoDao;
import com.tlys.equ.model.EquCarHpreginfo;

/**
 * @author fengym
 *
 */
@Service
public class EquCarHpreginfoService{
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EquCarHpreginfoDao equCarHpreginfoDao;
	
	public List<EquCarHpreginfo> findAll(){
		return equCarHpreginfoDao.findAll();
	}
	
	public List<EquCarHpreginfo> find(EquCarHpreginfo obj){
		return equCarHpreginfoDao.find(obj);
	}
	
	
	public EquCarHpreginfo load(String id) {
		return equCarHpreginfoDao.load(id);
	}
	
	
	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * @param equCarHpreginfo,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(EquCarHpreginfo equCarHpreginfo,boolean isNew) {
		if(isNew){
			equCarHpreginfoDao.saveOrUpdate(equCarHpreginfo);
		}else{
			equCarHpreginfoDao.update(equCarHpreginfo);//.updateEntity(equCarHpreginfo, equCarHpreginfo.getCarno());
		}
	}
	
	public void delete(String id) {
		equCarHpreginfoDao.delete(id);
		
	}
}
