package com.tlys.test;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.comm.util.LisUserAuthUtils;

public class TestUtils extends BaseTest {

	@Autowired
	LisUserAuthUtils lisUserAuthUtils;

	@Test()
	@Rollback(value = true)
	@Check(constraints = "hasPermission")
	public void hasPermission() {
		lisUserAuthUtils.hasPermission("songchangcheng");
	}
}
