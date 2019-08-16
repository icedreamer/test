package com.tlys.exe.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.dic.model.DicRwstation;
import com.tlys.exe.dao.ExeDicDao;

public class ExeDicDaoTest extends BaseTest {

	@Autowired
	ExeDicDao exeDicDao;

	@Test
	public void testGetAei_noByEntpr() {
		System.out.println(exeDicDao.getAei_noByEntpr("31550000", "YSZ").size());
	}

	@Test
	public void testGetTrain_dirByEntprstn() {
		System.out.println(exeDicDao.getTrain_dirByEntprstn("31550000", "YSZ", 1, "1"));
	}

	@Test
	public void testGetRwStationByTelegramid() {
		DicRwstation rwstation = exeDicDao.getRwStationByTelegramid("ACB");
		System.out.println(rwstation.getStationid());
	}

	@Test
	public void testListDicAreacorp() {
		List list = exeDicDao.listDicAreacorp("");
		System.out.println(list.size());
	}

	@Test
	public void testListDicGoodscategoryByIdArr() {
		System.out.println(exeDicDao.listDicGoodscategoryByIdArr("028,010,041,003").size());
	}
}
