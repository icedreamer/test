package com.tlys.test;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.comm.util.CommUtils;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;

public class TestCtlCorpreceiverDao extends BaseTest {

	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;

	@Test()
	@Rollback(value = true)
	@Check(constraints = "testGetNextReq")
	public void testGetNextReq() {
		String nextReq = ctlCorpreceiverService.getReceiverNextReq();
		logger.debug("nextReq : " + nextReq);
	}
	public static void main(String[] args) {
		System.out.println(CommUtils.getSeqStr("2", 6));
	}
}
