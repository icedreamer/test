package com.tlys.exe.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.model.ExeEstArrival;

/**
 * 企业前方有AEI车站及预计到达时间Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeEstArrivalDao extends _GenericDao<ExeEstArrival> {

	/**
	 * 取得预计到达时间信息
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
