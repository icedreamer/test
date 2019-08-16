package com.tlys.exe.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.exe.service.ExeInterfaceService;

public class ExeInterfaceServiceTest extends BaseTest {

	@Autowired
	private ExeInterfaceService exeInterfaceService;

	@Test
	public void testGetEstarr_flagCount() {
		//System.out.println(exeInterfaceService.getEstarr_flagCountInterface());
	}

	@Test
	@Rollback(value = false)
	public void testUpdateTransActualWgtInterface() {
		System.out.println(exeInterfaceService.updateTransActualWgtInterface("0779136", "XTJ", "2012-02-28", "B045661",
				300.0));
	}

	@Test
	public void testGetExeDcarStatInterface() {
		System.out.println(exeInterfaceService.getExeDcarStatInterface("1664797"));
	}

	@Test
	public void testGetExeGcarEvtaInterface() {
		System.out.println(exeInterfaceService.getExeGcarEvtaInterface("0779138", "2012-03-01", "2012-03-03"));
	}

}
