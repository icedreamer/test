package com.tlys.exe.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.exe.dao.ExeTransportDao;
import com.tlys.exe.dao.ExeTransportSearchField;
import com.tlys.exe.service.ExeTransportService;

public class ExeTransportServiceTest extends BaseTest {

	@Autowired
	ExeTransportService transportService;

	@Autowired
	ExeTransportDao transportDao;

	@Test
	public void testTranStatictics() throws Exception {
		ExeTransportSearchField field = new ExeTransportSearchField();
		field.setCar_no("0693951");
		field.setWb_date_s("2012-03-10");
		field.setWb_date_e("2012-05-01");
		transportDao.listTransport(field, "fsdf", 100, 1, 10);
	}

	// @Test
	// public void testUpdateTransport() {
	// try {
	// ExeTransport transport = transportDao.load("28022012ZJZ Q080539");
	// transport.setCar_actual_wgt(200.0);
	// transportDao.updateExeTransport(transport);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	@Test
	public void testGetYyStatistics() {
		// List<ExeYyStat> list = transportService.getYyStatistics();
		// for (ExeYyStat stat : list) {
		// System.out.println(stat.getArea_id() + "-" + stat.getCorp_id() + "-"
		// + stat.getEver_cycle_count());
		// }

	}

	@Test
	public void testGetZZ() {
		ExeTransportSearchField field = new ExeTransportSearchField();
		double aa = transportService.getCap_wgt(field);
		System.out.println(aa);
	}

}
