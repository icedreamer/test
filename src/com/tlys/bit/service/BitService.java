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
	 * �ƻ����������
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
	 * �ܲ��û��Ա����������(��ҳ��)
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
	 * ��ȡ��·��������
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamounts(String month, String corpid) {
		return bitMscotamountDao.findBitMscotamounts(month, corpid);
	}

	/**
	 * ��ȡ��·�������� �¼�
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamount(String month, String corpid) {
		return bitMscotamountDao.findBitMscotamount(month, corpid);
	}

	/**
	 * ��ȡ��·�볧����
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscitamounts(String month, String corpid) {
		return bitMscitamountDao.findBitMscitamounts(month, corpid);
	}

	/**
	 * ��ȡ��·�볧���� �¼�
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscitamount(String month, String corpid) {
		return bitMscitamountDao.findBitMscitamount(month, corpid);
	}

	/**
	 * ����˾Ȩ�޵��û� ��ȡ��������
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamounts(String month, String areaid) {
		return bitMareasctamountDao.findBitMareasctamounts(month, areaid);
	}

	/**
	 * ����˾Ȩ�޵��û� ��ȡ�������� �¼�
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamount(String month, String areaid) {
		return bitMareasctamountDao.findBitMareasctamount(month, areaid);
	}

	/**
	 * ��·���� �ܲ��û�
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinoctamount(String month) {
		return bitMsinoctamountDao.findBitMsinoctamount(month);
	}

	/**
	 * ��·���� ����˾����
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareactamount(String month, String areaid) {
		return bitMareactamountDao.findBitMareactamount(month, areaid);
	}

	/**
	 * ��·���� ��ҵ��������
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMotamount(String month, String corpid) {
		return bitMotamountDao.findBitMotamount(month, corpid);
	}

	/**
	 * ��·���� ��ҵ�볧����
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMitamount(String month, String corpid) {
		return bitMitamountDao.findBitMitamount(month, corpid);
	}

	/**
	 * �Ա��������������� �ܲ�Ȩ��ʹ��
	 * 
	 * @param month
	 * @return
	 */
	public List<Object[]> findBitMsinosctamountByKind(List<String> monthList) {
		return bitMsinosctamountDao.findBitMsinosctamountByKind(monthList);
	}

	/**
	 * ����˾Ȩ�޵��û� �Ա���������������
	 * 
	 * @param month
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareasctamountByKind(List<String> monthList, String areaid) {
		return bitMareasctamountDao.findBitMareasctamountByKind(monthList, areaid);
	}

	/**
	 * �������ݹ��� ��ҵȨ��
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscotamountByKind(final List<String> monthList, String corpid) {
		return bitMscotamountDao.findBitMscotamountByKind(monthList, corpid);
	}

	/**
	 * �볧���� ��ҵȨ��
	 * 
	 * @param month
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMscitamountByKind(final List<String> monthList, String corpid) {
		return bitMscitamountDao.findBitMscitamountByKind(monthList, corpid);
	}

	/**
	 * ��·���� �������� �ܲ��û�Ȩ��
	 * 
	 * @param monthList
	 * @return
	 */
	public List<Object[]> findBitMsinoctamountByKind(final List<String> monthList) {

		return bitMsinoctamountDao.findBitMsinoctamountByKind(monthList);
	}

	/**
	 * ��·���� �������� ����˾Ȩ��
	 * 
	 * @param monthList
	 * @param areaid
	 * @return
	 */
	public List<Object[]> findBitMareactamountByKind(final List<String> monthList, String areaid) {
		return bitMareactamountDao.findBitMareactamountByKind(monthList, areaid);
	}

	/**
	 * ��·���� �������� ��ҵ��������
	 * 
	 * @param monthList
	 * @param corpid
	 * @return
	 */
	public List<Object[]> findBitMotamountByKind(final List<String> monthList, String corpid) {
		return bitMotamountDao.findBitMotamountByKind(monthList, corpid);
	}

	/**
	 * ��·���� ��ϸ���� ��ҵ�볧����
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
			kindname = "��ͳ��������Ʒ";
		} else if ("1".equals(kind)) {
			kindname = "ͳ��������Ʒ";
		} else if ("3".equals(kind)) {
			kindname = "��Ʒ��";
		} else if ("4".equals(kind)) {
			kindname = "ú";
		} else if ("5".equals(kind)) {
			kindname = "ԭ��";
		} else if ("6".equals(kind)) {
			kindname = "����";
		} else if ("9".equals(kind)) {
			kindname = "����";
		}
		return kindname;
	}
}
