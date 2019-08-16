package com.tlys.exe.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.model.ExeEstArrival;

/**
 * ��ҵǰ����AEI��վ��Ԥ�Ƶ���ʱ��Dao
 * 
 * @author �״���
 * 
 */
@Repository
public class ExeEstArrivalDao extends _GenericDao<ExeEstArrival> {

	/**
	 * ȡ��Ԥ�Ƶ���ʱ����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeEstArrival> listEstArrival(String car_no, Date evt_time) {
		List<ExeEstArrival> list = getHibernateTemplate().find(
				"from ExeEstArrival where id.car_no=? and to_char(id.evt_time,'yyyy-mm-dd hh24:mi:ss')=?",
				new Object[] { car_no, StringUtil.dateToString(evt_time, "yyyy-MM-dd HH:mm:ss") });
		return list;
	}
}
