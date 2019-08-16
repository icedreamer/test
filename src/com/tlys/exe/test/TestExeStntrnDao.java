package com.tlys.exe.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.exe.dao.ExeStntrnDao;

public class TestExeStntrnDao extends BaseTest {

	@Autowired
	ExeStntrnDao exeStntrnDao;

	@Test
	public void testGetMaxRptId() {
		System.out.println(exeStntrnDao.getMaxRptId("DYSZ2"));
	}
}
