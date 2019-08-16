/**
 * 
 */
package com.tlys.sys.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.DateCalendar;
import com.tlys.sys.dao.SysSessionDao;

/**
 * @author fengym
 * 
 */
@Service
public class SysSessionService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	SysSessionDao sysSessionDao;
	/**
	 * 获取用户登录次数
	 * @param period:时间范围：y年，m月
	 * @return
	 */
	public Integer getUserLogCount(String userid,String period){
		Date stDate = null;
		if("y".equals(period)){
			stDate = DateCalendar.getNowYearStartDate();
		}else if("m".equals(period)){
			stDate = DateCalendar.getNowMonthStartDate();
		}
		
		//System.out.println("SysSessionService.getUserLogCount->stDate=="+stDate);
		Integer logCnt = sysSessionDao.getLoginCount(userid, stDate);
		
		return logCnt;
	}
}
