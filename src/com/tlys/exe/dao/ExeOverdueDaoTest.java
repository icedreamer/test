package com.tlys.exe.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.exe.model.ExeOverdue;
import com.tlys.exe.test.BaseTest;

public class ExeOverdueDaoTest extends BaseTest {

	@Autowired
	ExeOverdueDao exeOverdueDao;

	@Test
	public void testListOverdueStringStringStringString() {
		String entpr_id = "10550807,zy000020";
		String over_id = "04";

		List<ExeOverdue> list = exeOverdueDao.listOverdue(entpr_id, over_id, "0");
		System.out.println(list.size());
	}

	@Test
	public void testListOverdueStringStringString() {
		fail("Not yet implemented");
	}

}
