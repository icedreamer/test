package com.tlys.dic.service.ctl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.DicMap;
import com.tlys.dic.dao.ctl.CtlCustomerreceiverDao;
import com.tlys.dic.model.ctl.CtlCustomerreceiver;

@Service
public class CtlCustomerreceiverService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	CtlCustomerreceiverDao ctlCustomerreceiverDao;

	@Autowired
	DicMap dicMap;

	public List<CtlCustomerreceiver> findAll() {
		return ctlCustomerreceiverDao.findAll();
	}

	public Map getMap() throws Exception {
		return dicMap.getCustomerreceiverMap();
	}

	public List<CtlCustomerreceiver> findCtlCustomerreceiver(String customerid) {
		return ctlCustomerreceiverDao.findCtlCustomerreceiver(customerid);
	}

	public CtlCustomerreceiver getCtlCustomerreceiver(final String receiverid) {
		return ctlCustomerreceiverDao.getCtlCustomerreceiver(receiverid);
	}
}
