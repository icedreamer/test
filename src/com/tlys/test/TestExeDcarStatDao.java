package com.tlys.test;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.exe.dao.ExeDcarStatDao;

public class TestExeDcarStatDao extends BaseTest {
	@Autowired
	ExeDcarStatDao exeDcarStatDao;
	@Test()
	@Rollback(value=true)
	@Check(constraints = "testDelZbcExeDcarstatInfo")
	public void testDelZbcExeDcarstatInfo(){
		String carNo = "0076066";
		//exeDcarStatDao.delZbcExeDcarstatInfo("0076066");
		exeDcarStatDao.delZbcExeDcarstatInfo(carNo);
	}

}
