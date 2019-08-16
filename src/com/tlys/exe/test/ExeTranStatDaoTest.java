package com.tlys.exe.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.exe.dao.ExeTranStatDao;

public class ExeTranStatDaoTest extends BaseTest {

	@Autowired
	ExeTranStatDao exeTranStatDao;

	@Test
	public void testTranStatictics() {
		try {
			exeTranStatDao.tranStatictics("2012-02-28", "35000032");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTransStat() {
		// List<Object[]> list = exeTranStatDao.getTransStatCar_noCount();
		// System.out.println(list.size());
		// List<Object[]> list2 = exeTranStatDao.getTransStatCycleCount();
		// System.out.println(list2.size());
		System.out.println(exeTranStatDao.getTransYYStatNoCycle("0", "2012", "", "03", "", "").size());
	}
}
