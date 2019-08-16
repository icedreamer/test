package com.tlys.exe.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.comm.util.page.PageView;
import com.tlys.exe.dao.ExeAllocSearchField;
import com.tlys.exe.dao.ExeAllocationDao;

public class ExeAllocationDaoTest extends BaseTest {

	@Autowired
	ExeAllocationDao exeAllocationDao;

	@Test
	public void testAddorUpdAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveExeAllocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEntityObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindExeAllocationExeAllocationId() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindExeAllocationsExeAllocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindExeAllocationsExeAllocSearchField() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindExeAllocations() {
		ExeAllocSearchField eai = new ExeAllocSearchField();

		PageView p = exeAllocationDao.findExeAllocations(eai, "", 100, 1, 10);

		List list = p.getRecords();
		System.out.println(list.size());
	}

	@Test
	public void testGetExeAllocationCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteExeAllocationId() {
		fail("Not yet implemented");
	}

}
