/**
 * 
 */
package com.tlys.dic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicSinodepartment;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class DicSinodepartmentDao extends _GenericDao<DicSinodepartment> {

	public DicSinodepartment load(String id) {
		DicSinodepartment dicSinodepartment = super.load(id);
		return dicSinodepartment;
	}
	
	public List<DicSinodepartment> findByParentid(String pid){
		return super.findByProperty("parentid", pid,"sinodepaid");
	}
	
	/**
	 * 查找所有未列为视图项的下属部门
	 * @param viewname
	 * @return
	 */
	public List<DicSinodepartment> find4view(String viewname){
		String hql = "from DicSinodepartment d where d.isactive='1' and d.is"+viewname+"='0'";
		return this.getHibernateTemplate().find(hql);
	}
	
	public void saveOrUpdateAll(List sinodeps){
		getHibernateTemplate().saveOrUpdateAll(sinodeps);
	}

}
