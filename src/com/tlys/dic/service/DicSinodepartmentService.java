/**
 * 
 */
package com.tlys.dic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.DicMap;
import com.tlys.dic.dao.DicAreacorpDao;
import com.tlys.dic.dao.DicSinodepartmentDao;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinodepartment;

/**
 * @author fengym
 *
 */
@Service
public class DicSinodepartmentService{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	DicSinodepartmentDao dicSinodepartmentDao;

	@Autowired
	DicAreacorpDao dicAreacorpDao;
	
	@Autowired
	DicMap dicMap;

	
	public List<DicSinodepartment> findAll(){
		return dicSinodepartmentDao.findAll(" order by shortname");
	}
	
	public List<DicSinodepartment> find(DicSinodepartment dac){
		return null;
	}
	
	/**
	 * 得到列表，专为添加视图时所用，参数为当前视图名
	 * {salecorp,receiver,sender},注意后面两个和本来的视图名有区别
	 * @param viewname
	 * @return
	 */
	public Map<String,List<DicSinodepartment>> find4view(String viewname){
		//生成区域公司的id-shortname字典对
		List<DicAreacorp> acList = dicAreacorpDao.findAll();
		Map<String,String> acMapDic = new HashMap<String,String>();
		for (Iterator it = acList.iterator(); it.hasNext();) {
			DicAreacorp dicAreacorp = (DicAreacorp) it.next();
			acMapDic.put(dicAreacorp.getAreaid(), dicAreacorp.getShortname());
		}
		
		
		List<DicSinodepartment> depList = dicSinodepartmentDao.find4view(viewname);
		
		//将所有未列为视图项的下属部门按区域公司分类组织
		Map<String,List<DicSinodepartment>> acMap = new LinkedHashMap<String,List<DicSinodepartment>>();
		List<DicSinodepartment> acDepList = null;
		for (Iterator it = depList.iterator(); it.hasNext();) {
			DicSinodepartment dep = (DicSinodepartment) it.next();
			String areaid = dep.getParentid();
			String areaname = acMapDic.get(areaid);
			
			//当当前部门的parentid在区域公司中找不到时，忽略
			if(areaname==null) continue;
			acDepList = (List<DicSinodepartment>) acMap.get(areaname);
			if (acDepList == null) {
				acDepList = new ArrayList<DicSinodepartment>();
				acMap.put(areaname, acDepList);
			}
			acDepList.add(dep);
		}
			
		return acMap;
	}
	
	
	public DicSinodepartment load(String id) {
		return dicSinodepartmentDao.load(id);
	}
	
	
	/**
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * @param dicSinodepartment,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(DicSinodepartment dicSinodepartment,boolean isNew) {
		if(isNew){
			dicSinodepartmentDao.saveOrUpdate(dicSinodepartment);
		}else{
			dicSinodepartmentDao.updateEntity(dicSinodepartment, dicSinodepartment.getSinodepaid());
		}
		setAlterFlag();
	}
	
	
	public void delete(String id) {
		dicSinodepartmentDao.delete(id);
		setAlterFlag();
		
	}
	
	/**
	 * 由于当前对象充当字典使用
	 * 所以当前对象变动（保存，更新，删除)时
	 * 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag(){
		dicMap.dicAlter("dicSinodepartment");
	}
}
