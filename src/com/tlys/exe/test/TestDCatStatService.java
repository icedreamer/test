package com.tlys.exe.test;

import java.util.Iterator;
import java.util.List;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.comm.util.page.PageView;
import com.tlys.exe.dao.ExeAreaDistDao;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.exe.dao.ExeDicDao;
import com.tlys.exe.dao.ExeGcarEvtaDao;
import com.tlys.exe.dao.ExeOverdueDao;
import com.tlys.exe.model.ExeGcarEvta;
import com.tlys.exe.model.ExeStntrn;
import com.tlys.exe.service.ExeAeiService;
import com.tlys.exe.service.ExeAllocationService;
import com.tlys.exe.service.ExeAreaDistService;
import com.tlys.exe.service.ExeOverdueService;

public class TestDCatStatService extends BaseTest {

	@Autowired
	ExeDcarStatDao dCarStatDao;
	@Autowired
	ExeGcarEvtaDao gcarEvtaDao;

	@Autowired
	ExeAllocationService allocationService;

	@Autowired
	ExeDicDao dicdao;
	@Autowired
	ExeAreaDistService distService;

	@Autowired
	ExeAreaDistDao areaDistDao;

	@Autowired
	ExeOverdueDao overdueDao;
	@Autowired
	ExeOverdueService exeOverdueService;
	@Autowired
	ExeAeiService exeAeiService;

	@Test()
	@Check(constraints = "testListDCarStat")
	public void testListDCarStat() {
		// DCarStatSearchField field = new DCarStatSearchField();
		// field.setCar_no("4890015");
		// field.setCar_type("C64K");
		// field.setCon_name(" 中国石油化工股份有限公司");
		// field.setShipper_name("天津钢管集团");
		// field.setCdy_org_stn("JLP");
		// field.setDest_stn("LUR");
		//
		// List list = dCarStatService.listDCarStat(field);
		// System.out.println(list.size());
		// int count = dCarStatService.getListDCarStat(field);
		// System.out.println(count);

		// List<DicRwstation> list2 = dicdao.filterRwstation("");
		// System.out.println(list2.size());
		// for (DicRwstation rw : list2) {
		// System.out.println("\"" + rw.getStationpycode() + ":" +
		// rw.getShortname() + "\",");
		// }
		// System.out.println(dicdao.getRwStationCode("BJ",
		// "北京").getTelegramid());
		List<ExeGcarEvta> list = gcarEvtaDao.listGcarEvta("0693840", "2012-01-10", "2012-02-10");
		for (ExeGcarEvta g : list) {
			System.out.println(g.getA_d_time());
		}
	}

	@Test
	public void testListAreaDist() {
		// List<ExeAreaDist> listAreaDist =
		// areaDistDao.listAreaDist("2012-01-18", 15, 2);
		// System.out.println(listAreaDist.size());
		// for (ExeAreaDist e : listAreaDist) {
		// // System.out.println(e.getCar_lessee().getShortname());
		// }
		List list = areaDistDao.listAreaDist("2012-01-18", 15);
		System.out.println(list.size());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object o[] = (Object[]) it.next();
			System.out.println(o[0] + "-" + o[1]);
		}
	}

	@Test
	public void testListOverdue() {
		// List<ExeOverdue> list = overdueDao.listOverdue("'35000000'",
		// "'028','010'", "'1','2','3'", "0");
		// System.out.println(list.size());

		List list2 = dicdao.listDicGoodscategoryByIdArr("'028', '010'");
	}

	@Test
	public void testListOverdue2() {
		// List<int[]> list = exeOverdueService.listOverdue("35000000",
		// "'028','010','041'", "'1','2','3'", "0");
		// System.out.println(list.size());
		// for (int[] count : list) {
		// for (int m : count) {
		// System.out.println(m);
		// }
		//		}
	}

	@Test
	public void testListStntrn() {
		PageView<ExeStntrn> page = exeAeiService.listStntrn("2012-01-01", "2012-02-20", "", 100, 1, 20);
		System.out.println(page.getRecords().size());

	}

	@Test
	public void testGetEstarr_flagCount() {
		System.out.println(dCarStatDao.getEstarr_flagCount());
	}

	@Test
	public void testloadExeDcarStat2() {
		// System.out.println(dCarStatDao.loadExeDcarStat2("4890015"));
		System.out.println(gcarEvtaDao.getGcarEvta2("4890015", "2012-03-01", "2012-03-30"));
	}

}
