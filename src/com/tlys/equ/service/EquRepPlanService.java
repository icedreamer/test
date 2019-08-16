package com.tlys.equ.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.dao.EquRepMplanDao;
import com.tlys.equ.dao.EquRepYplanDao;
import com.tlys.equ.model.EquRepMplan;
import com.tlys.equ.model.EquRepYplan;

@Service
public class EquRepPlanService {

	@Autowired
	EquRepMplanDao equRepMplanDao;
	@Autowired
	EquRepYplanDao equRepYplanDao;

	public Object[] call_P_ZBC_REP_GENPLAN(String year) {
		return equRepMplanDao.call_P_ZBC_REP_GENPLAN(year);
	}

	public List<Object[]> findEquRepMplan(EquRepMplan equRepPlan) {
		return equRepMplanDao.findEquRepMplan(equRepPlan);
	}

	public List<Object[]> findEquRepYplan(EquRepYplan equRepYplan) {
		return equRepYplanDao.findEquRepYplan(equRepYplan);
	}

	public List<Object[]> findMonthsPlan(EquRepMplan equRepPlan) {
		return equRepMplanDao.findMonthsPlan(equRepPlan);
	}

	public List<Object[]> findYearsPlan(EquRepYplan equRepYplan) {
		return equRepYplanDao.findYearsPlan(equRepYplan);
	}

	public List<Object[]> findCorps(EquRepYplan equRepYplan) {
		return equRepYplanDao.findCorps(equRepYplan);
	}

	/**
	 * 月度计划列表
	 * 
	 * @param equRepPlan
	 * @param pageCtr
	 */
	public void findMonths(EquRepMplan equRepPlan, PageCtr<String> pageCtr) {
		int totalRecord = equRepMplanDao.getMonthsCount(equRepPlan);
		pageCtr.setTotalRecord(totalRecord);
		equRepMplanDao.findMonths(equRepPlan, pageCtr);
	}

	/**
	 * 年度计划列表
	 * 
	 * @param equRepYplan
	 * @param pageCtr
	 */
	public void findYears(EquRepYplan equRepYplan, PageCtr<String> pageCtr) {
		int totalRecord = equRepYplanDao.getYearsCount(equRepYplan);
		pageCtr.setTotalRecord(totalRecord);
		equRepYplanDao.findYears(equRepYplan, pageCtr);
	}

	/**
	 * 年度计划合计
	 * 
	 * @param equRepYplan
	 * @return
	 */
	public List<Object[]> findTotalAmountByRtypeid(EquRepYplan equRepYplan) {
		return equRepYplanDao.findTotalAmountByRtypeid(equRepYplan);
	}

	/**
	 * 月度计划合计
	 * 
	 * @param equRepPlan
	 * @return
	 */
	public List<Object[]> findTotalAmountByRtypeidAndDatatype(EquRepMplan equRepPlan) {
		return equRepMplanDao.findTotalAmountByRtypeidAndDatatype(equRepPlan);
	}

	/**
	 * 本月待检，超期未检柱状图
	 * 
	 * @return
	 */
	public List<Object[]> findThisMonthReps() {
		return equRepMplanDao.findThisMonthReps();
	}
}
