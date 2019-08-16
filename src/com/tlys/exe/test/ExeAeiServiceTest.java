package com.tlys.exe.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.comm.util.page.PageView;
import com.tlys.exe.dao.ExeAeiDao;
import com.tlys.exe.dao.ExeAeiSearchCarField;
import com.tlys.exe.model.ExeStncar;
import com.tlys.exe.service.ExeAeiService;
import com.tlys.exe.service.ExeZqxcDistVO;

public class ExeAeiServiceTest extends BaseTest {
	@Autowired
	ExeAeiService aeiService;
	@Autowired
	ExeAeiDao exeAeiDao;

	@Test
	public void testListStntrn() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListStntrnCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testListStncar() {
		ExeAeiSearchCarField field = new ExeAeiSearchCarField();
		field.setS_date("2012-10-01");
		field.setE_date("2012-10-31");
		// field.setStn_entpr_id("31550000");
		field.setArea_id("35000010");
		PageView<ExeStncar> pageView = aeiService.searchStncar(field, "111", 200, 1, 10);
	}

	@Test
	public void testGetZqxcDist() {
		List<ExeZqxcDistVO> list = aeiService.getZqxcDist();
		System.out.println(list.size());
	}

	@Test
	public void testSearchStncar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListStncarCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveStntrn() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateStntrn() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteStntrn() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadExeStntrn() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveStncar() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateStncar() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteStncar() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadExeStncar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMaxRptId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCar_position() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetZbclyl() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetZqjccl() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEquipMonitor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTableName() {
		String tableString = exeAeiDao.getTableName();
		System.out.println(tableString);
	}
}
