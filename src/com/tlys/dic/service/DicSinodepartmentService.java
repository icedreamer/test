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
	 * �õ��б�רΪ�����ͼʱ���ã�����Ϊ��ǰ��ͼ��
	 * {salecorp,receiver,sender},ע����������ͱ�������ͼ��������
	 * @param viewname
	 * @return
	 */
	public Map<String,List<DicSinodepartment>> find4view(String viewname){
		//��������˾��id-shortname�ֵ��
		List<DicAreacorp> acList = dicAreacorpDao.findAll();
		Map<String,String> acMapDic = new HashMap<String,String>();
		for (Iterator it = acList.iterator(); it.hasNext();) {
			DicAreacorp dicAreacorp = (DicAreacorp) it.next();
			acMapDic.put(dicAreacorp.getAreaid(), dicAreacorp.getShortname());
		}
		
		
		List<DicSinodepartment> depList = dicSinodepartmentDao.find4view(viewname);
		
		//������δ��Ϊ��ͼ����������Ű�����˾������֯
		Map<String,List<DicSinodepartment>> acMap = new LinkedHashMap<String,List<DicSinodepartment>>();
		List<DicSinodepartment> acDepList = null;
		for (Iterator it = depList.iterator(); it.hasNext();) {
			DicSinodepartment dep = (DicSinodepartment) it.next();
			String areaid = dep.getParentid();
			String areaname = acMapDic.get(areaid);
			
			//����ǰ���ŵ�parentid������˾���Ҳ���ʱ������
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
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * @param dicSinodepartment,isNew:��־��ǰ���������������޸Ĳ���
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
	 * ���ڵ�ǰ����䵱�ֵ�ʹ��
	 * ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ
	 * Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag(){
		dicMap.dicAlter("dicSinodepartment");
	}
}
