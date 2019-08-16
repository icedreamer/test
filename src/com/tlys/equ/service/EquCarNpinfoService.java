/**
 * 
 */
package com.tlys.equ.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.equ.dao.EquCarNpinfoDao;
import com.tlys.equ.model.EquCarNpinfo;

/**
 * @author fengym
 *
 */
@Service
public class EquCarNpinfoService{
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EquCarNpinfoDao equCarNpinfoDao;
	
	public List<EquCarNpinfo> findAll(){
		return equCarNpinfoDao.findAll();
	}
	
	public List<EquCarNpinfo> find(EquCarNpinfo obj){
		return equCarNpinfoDao.find(obj);
	}
	
	
	public EquCarNpinfo load(String id) {
		return equCarNpinfoDao.load(id);
	}
	
	
	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * @param equCarNpinfo,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(EquCarNpinfo equCarNpinfo,boolean isNew) {
		if(isNew){
			equCarNpinfoDao.saveOrUpdate(equCarNpinfo);
		}else{
			equCarNpinfoDao.update(equCarNpinfo);
		}
	}
	
	public void update(EquCarNpinfo equCarNpinfo){
		equCarNpinfoDao.update(equCarNpinfo);
	}
	
	public void delete(String id) {
		equCarNpinfoDao.delete(id);
		
	}
}
