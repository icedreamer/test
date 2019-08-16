package com.tlys.test;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.bi.service.BiDataService;

public class TestBiDataService extends BaseTest {
	@Autowired
	BiDataService biDataService;


	@Test()
	@Rollback(value = true)
	@Check(constraints = "testfindEquRepRecord")
	public void testGenXML() {
		biDataService.genXML();
	}
}
