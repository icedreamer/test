package com.tlys.bit.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.bit.dao.BitMareactamountDao;
import com.tlys.bit.dao.BitMareasctamountDao;
import com.tlys.bit.dao.BitMcarsrequestDao;
import com.tlys.bit.dao.BitMitamountDao;
import com.tlys.bit.dao.BitMotamountDao;
import com.tlys.bit.dao.BitMscitamountDao;
import com.tlys.bit.dao.BitMscotamountDao;
import com.tlys.bit.dao.BitMsinoctamountDao;
import com.tlys.bit.dao.BitMsinosctamountDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.sys.model.SysUser;

@Service
public class BitService {
	@Autowired
	BitMcarsrequestDao bitMcarsrequestDao;
	@Autowired
	BitMsinosctamountDao bitMsinosctamountDao;

	@Autowired
	BitMscotamountDao bitMscotamountDao;
	@Autowired
	BitMscitamountDao bitMscitamountDao;
	@Autowired
	BitMareasctamountDao bitMareasctamountDao;
	@Autowired
	BitMsinoctamountDao bitMsinoctamountDao;
	@Autowired
	BitMareactamountDao bitMareactamountDao;
	@Autowired
	BitMitamountDao bitMitamountDao;
	@Autowired
	BitMotamountDao bitMotamountDao;

	/**
	 * 计划及批复情况
	 * 
	 * @return
	 */
	public List<Object[]> findBitMcarsrequests(SysUser sysUser, String datamonth) {
		return bitMcarsrequestDao.findBitMcarsrequests(sysUser, datamonth);
	}

	public String getFixMonth() {
		String month = bitMcarsrequestDao.getFixMonth();
		Calendar calendar = Calendar.getInstance();
		String nowMonth = CommUtils.shortMonthFormat(calendar.getTime());
		if (month.compareTo(nowMonth) <= 0) {
			return month;
		} else if (month.compareTo(nowMonth) > 0) {
			return nowMonth;
		}
		return null;
	}

	/**
	 * 总部用户自备车运量情况(新页面)
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinosctamount(String month) {
		return bitMsinosctamountDao.findBitMsinosctamount(month);
	}

	public List<Object[]> findBitMsinosctamounts(String month) {
		return bitMsinosctamountDao.findBitMsinosctamounts(month);
	}

	/**
	 * 获取铁路出厂数据
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamounts(String month, String corpid) {
		return bitMscotamountDao.findBitMscotamounts(month, corpid);
	}

	/**
	 * 获取铁路出厂数据 新加
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamount(String month, String corpid) {
		return bitMscotamountDao.findBitMscotamount(month, corpid);
	}

	/**
	 * 获取铁路入厂数据
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscitamounts(String month, String corpid) {
		return bitMscitamountDao.findBitMscitamounts(month, corpid);
	}

	/**
	 * 获取铁路入厂数据 新加
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscitamount(String month, String corpid) {
		return bitMscitamountDao.findBitMscitamount(month, corpid);
	}

	/**
	 * 区域公司权限的用户 获取运量数据
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamounts(String month, String areaid) {
		return bitMareasctamountDao.findBitMareasctamounts(month, areaid);
	}

	/**
	 * 区域公司权限的用户 获取运量数据 新加
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamount(String month, String areaid) {
		return bitMareasctamountDao.findBitMareasctamount(month, areaid);
	}

	/**
	 * 铁路运量 总部用户
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinoctamount(String month) {
		return bitMsinoctamountDao.findBitMsinoctamount(month);
	}

	/**
	 * 铁路运量 区域公司数据
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareactamount(String month, String areaid) {
		return bitMareactamountDao.findBitMareactamount(month, areaid);
	}

	/**
	 * 铁路运量 企业出厂数据
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMotamount(String month, String corpid) {
		return bitMotamountDao.findBitMotamount(month, corpid);
	}

	/**
	 * 铁路运量 企业入厂数据
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMitamount(String month, String corpid) {
		return bitMitamountDao.findBitMitamount(month, corpid);
	}

	/**
	 * 自备车运量构成数据 总部权限使用
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinosctamountByKind(List<String> monthList) {
		return bitMsinosctamountDao.findBitMsinosctamountByKind(monthList);
	}

	/**
	 * 区域公司权限的用户 自备车运量构成数据
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamountByKind(List<String> monthList, String areaid) {
		return bitMareasctamountDao.findBitMareasctamountByKind(monthList, areaid);
	}

	/**
	 * 出厂数据构成 企业权限
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamountByKind(final List<String> monthList, String corpid) {
		return bitMscotamountDao.findBitMscotamountByKind(monthList, corpid);
	}

	/**
	 * 入厂数据 企业权限
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscitamountByKind(final List<String> monthList, String corpid) {
		return bitMscitamountDao.findBitMscitamountByKind(monthList, corpid);
	}

	/**
	 * 铁路运量 构成数据 总部用户权限
	 * 
	 * @param monthList
	 * @return
	 */
	public List<Object[]> findBitMsinoctamountByKind(final List<String> monthList) {

		return bitMsinoctamountDao.findBitMsinoctamountByKind(monthList);
	}

	/**
	 * 铁路运量 构成数据 区域公司权限
	 * 
	 * @param monthList
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareactamountByKind(final List<String> monthList, String areaid) {
		return bitMareactamountDao.findBitMareactamountByKind(monthList, areaid);
	}

	/**
	 * 铁路运量 构成数据 企业出厂数据
	 * 
	 * @param monthList
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMotamountByKind(final List<String> monthList, String corpid) {
		return bitMotamountDao.findBitMotamountByKind(monthList, corpid);
	}

	/**
	 * 铁路运量 明细数据 企业入厂数据
	 * 
	 * @param monthList
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMitamountByKind(final List<String> monthList, String corpid) {
		return bitMitamountDao.findBitMitamountByKind(monthList, corpid);
	}

	public String getKindname(String kind) {
		String kindname = "";
		if (null == kind || "".equals(kind)) {
			return kindname;
		}
		if ("0".equals(kind)) {
			kindname = "非统销化工产品";
		} else if ("1".equals(kind)) {
			kindname = "统销化工产品";
		} else if ("3".equals(kind)) {
			kindname = "成品油";
		} else if ("4".equals(kind)) {
			kindname = "煤";
		} else if ("5".equals(kind)) {
			kindname = "原油";
		} else if ("6".equals(kind)) {
			kindname = "沥青";
		} else if ("9".equals(kind)) {
			kindname = "其他";
		}
		return kindname;
	}
}
