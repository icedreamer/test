package com.tlys.test;

import java.util.List;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.dic.dao.ctl.CtlCustomerreceiverDao;
import com.tlys.dic.model.ctl.CtlCustomerreceiver;

public class TestCtlCustormereceiverDao extends BaseTest {

	@Autowired
	CtlCustomerreceiverDao ctlCustomerreceiverDao;

	@Test
	@Rollback(value = true)
	@Check(constraints = "testGetCustomerreceiver")
	public void testGetCustomerreceiver() {
		List<CtlCustomerreceiver> list = ctlCustomerreceiverDao.findAll();

		logger.debug("list.size : " + list.size());
		for (int i = 0; i < list.size(); i++) {
			CtlCustomerreceiver ctlCustomerreceiver = list.get(i);
			logger.debug("ctlCustomerreceiver : " + ctlCustomerreceiver);
		}
	}
}
