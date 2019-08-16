package com.tlys.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.equ.dao.EquRepMplanDao;

public class TestEquRepPlanDao extends BaseTest {
	@Autowired
	EquRepMplanDao equRepPlanDao;

	//
	// @Test()
	// @Rollback(value = true)
	// @Check(constraints = "findEquRepPlanCorps")
	// public void findEquRepPlanCorps() {
	// List<Object[]> oList = equRepPlanDao.findCorps(new EquRepYplan());
	// for (Object[] objects : oList) {
	// logger.debug("objects[0] : " + objects[0]);
	// logger.debug("objects[1] : " + objects[1]);
	// }
	// }
	//
	// @Test()
	// @Rollback(value = true)
	// @Check(constraints = "findEquRepPlan")
	// public void findEquRepPlan() {
	// List<Object[]> oList = equRepPlanDao.findEquRepPlan(new EquRepMplan());
	// logger.debug("oList.size : " + oList.size());
	// }
	public static void main1(String[] args) {

		int k = 0;
		for (int i = 0; i < 200; i = i + k) {
			k++;
		}
		System.out.println(k);
	}

	public static void main(String[] args) {
		// 当 2 + 4 + 6 + .... k < 200时，求k最大的值
		int k = 2;
		for (int i = 0; i < 200; i = i + k + 2) {
			k = k + 2;
			System.out.println(i + " + " + k);
		}
		System.out.println(k);
	}

}
