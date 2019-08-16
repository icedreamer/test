package com.tlys.test;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.comm.util.LisUserAuthUtils;
import com.tlys.exe.webservice.ExeDcarImpl;

public class TestLisUserPermission extends BaseTest {

//	@Autowired
//	LisUserAuthUtils lisUserAuthUtils;

	@Autowired
	ExeDcarImpl exeDcarImpl;

	@Test()
	@Rollback(value = true)
	@Check(constraints = "testLisPermission")
	public void testLisPermission() {
		try {
			String str = exeDcarImpl.getExeGcarEvta("0693863", "2014-03-01", "2014-04-14", "lis_WLYM");
			// boolean permission = getExeGcarEvta.hasPermission("lis_WLYM");
			 logger.debug("str : " + str);
		} catch (Exception e) {
			logger.error("Error.", e);
		}

	}
}
