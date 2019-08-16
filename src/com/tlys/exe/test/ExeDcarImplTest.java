package com.tlys.exe.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.exe.webservice.ExeDcarImpl;

public class ExeDcarImplTest extends BaseTest {

	@Autowired
	ExeDcarImpl exeDcarImpl;

	@Test
	public void testUpdateTransActualWgt() {
	}

	@Test
	public void testGetExeDcarStat() {
		//System.out.println(exeDcarImpl.getExeDcarStat("4890015"));
	}

	@Test
	public void testGetExeGcarEvta() {
		//System.out.println(exeDcarImpl.getExeGcarEvta("1664797", "2012-03-09", "2012-03-13"));
	}

}
