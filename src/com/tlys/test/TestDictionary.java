package com.tlys.test;

import java.util.Map;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.comm.util.DicMap;

public class TestDictionary extends BaseTest {

	@Autowired
	DicMap dicMap;

	@Test()
	@Rollback(value = true)
	@Check(constraints = "testDicReceiverMap")
	public void testDicReceiverMap() {
		String cid = "40055370";
		Map<String, Object> map = dicMap.getReceiverMap(cid);
		System.out.println("TestDictionary.testDicReceiverMap->map=="+map);
	}
}
