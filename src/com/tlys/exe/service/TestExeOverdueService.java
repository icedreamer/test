package com.tlys.exe.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.exe.test.BaseTest;

public class TestExeOverdueService extends BaseTest {

	@Autowired
	ExeOverdueService exeOverdueService;

	@Test
	public void testlistOverdue2() {
		List<int[]> list = exeOverdueService.listOverdue();
		System.out.println(list.size());

	}
}
