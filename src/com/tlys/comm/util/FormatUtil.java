/**
 * 
 */
package com.tlys.comm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fengym ����ר�����ڸ��ָ�ʽת�����ر������ڸ�ʽת��
 */
public class FormatUtil {

	/** yyyy-MM-dd */
	public final static int NORMAL = 1;
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public final static int NORMAL_SECOND = 2;
	/** yyyyMMdd */
	public final static int COMPACT = 3;
	/** yyyyMMddHHmmss */
	public final static int COMPACT_SECOND = 4;
	/** yyyyMM */
	public final static int COMPACT_MONTH = 5;
	/** MM */
	public final static int MONTH_ONLY = 6;
	/** yyyy */
	public final static int YEAR_ONLY = 7;
	/** MM��dd�� */
	public final static int MONTH_DAY_ZH = 8;

	/**
	 * �����ڸ�ʽ������Ӧ���ַ���
	 * 
	 * @param d
	 * @param mod:����ģʽ������������ṩ�ĳ���
	 * @return
	 */
	public static String fmtDate(Date d, int mod) {
		if (d == null)
			return "0000-00-00";
		String modStr = bdModStr(mod);
		String fmtStr = new SimpleDateFormat(modStr).format(d);
		return fmtStr;
	}

	/**
	 * ���ַ�������������
	 * 
	 * @param dstr
	 * @param mod������ģʽ������������ṩ�ĳ���
	 * @return
	 */
	public static Date parseStr(String dstr, int mod) {
		Date d = null;
		String modStr = bdModStr(mod);
		DateFormat df = new SimpleDateFormat(modStr);
		try {
			d = df.parse(dstr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	/**
	 * ��ǰʱ�䣺yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String nowDateNormalStr() {
		return fmtDate(new Date(), 1);
	}

	/**
	 * ��ǰ���ڣ�yyyyMMdd
	 * 
	 * @return
	 */
	public static String nowDateStr() {
		return fmtDate(new Date(), 3);
	}

	/**
	 * ���������:yyyyMMdd
	 * 
	 * @return
	 */
	public static String tomorrowDateStr() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return fmtDate(cal.getTime(), 3);
	}

	/**
	 * ����������ڸ�ʽ���ͷ��ص�ǰ����
	 * 
	 * @param dateStyle
	 * @return
	 */
	public static String nowDateStr(int dateStyle) {
		return fmtDate(new Date(), dateStyle);
	}

	/**
	 * ���ص�ǰ����yyyyMM
	 * 
	 * @return
	 */
	public static String nowYearMonthStr() {
		return fmtDate(new Date(), 5);
	}

	/**
	 * ���ص�ǰ���ڼ�ʱ�� yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String nowSecondStr() {
		return fmtDate(new Date(), 4);
	}

	/**
	 * ���ص�ǰ�·�MM
	 * 
	 * @return
	 */
	public static String nowMonthOnly() {
		return fmtDate(new Date(), 6);
	}

	/**
	 * ���ص�ǰ���yyyy
	 * 
	 * @return
	 */
	public static String nowYearOnly() {
		return fmtDate(new Date(), 7);
	}

	/**
	 * ���ؽ�����������飬�������0�㵽23��59
	 * 
	 * @return
	 */
	public static Date[] dateArrToday() {
		Date[] dse = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		dse[0] = cal.getTime();

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dse[1] = cal.getTime();

		return dse;
	}

	/**
	 * ��������һ�����������
	 * 
	 * @param dayStr
	 * @return �������飬�ӵ���0�㵽23��59
	 */
	public static Date[] dateArrAnyDay(String dayStr) {

		Date[] dse = new Date[2];
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dayStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		dse[0] = cal.getTime();

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dse[1] = cal.getTime();

		return dse;
	}

	/**
	 * ����ʱ���ʽģʽ�ַ���
	 * 
	 * @return
	 */
	private static String bdModStr(int dateStyle) {
		String modStr = "yyyy-MM-dd HH:mm:ss";
		switch (dateStyle) {
		case 1:
			modStr = "yyyy-MM-dd";
			break;
		case 2:
			modStr = "yyyy-MM-dd HH:mm:ss";
			break;
		case 3:
			modStr = "yyyyMMdd";
			break;
		case 4:
			modStr = "yyyyMMddHHmmss";
			break;
		case 5:
			modStr = "yyyyMM";
			break;
		case 6:
			modStr = "MM";
			break;
		case 7:
			modStr = "yyyy";
			break;
		case 8:
			modStr = "MM��dd��";
			break;
		case 9:
			modStr = "yyyy-MM";
			break;
		default:
			modStr = "yyyy-MM-dd HH:mm:ss";
		}
		return modStr;
	}

	/**
	 * �����ַ���������ת�� �����yyyy-mm-dd(bdModStr(1))��ʽ��תΪyyyymmdd(bdModStr(3));��֮����תΪ��֮��
	 * 
	 * @return dateStr
	 * @throws ParseException
	 */
	public static String rectDate(String dateStr) throws ParseException {
		if(null==dateStr){
			return null;
		}
		SimpleDateFormat shortFormat = new SimpleDateFormat(bdModStr(3));
		SimpleDateFormat normalFormat = new SimpleDateFormat(bdModStr(1));
		if (dateStr.trim().length() > 0) {
			if (dateStr.indexOf("-") > -1) {
				dateStr = shortFormat.format(normalFormat.parse(dateStr));
			} else {
				dateStr = normalFormat.format(shortFormat.parse(dateStr));
			}
		}
		return dateStr;
	}
	
	/**
	 * �����ַ���������ת�� �����yyyy-mm(bdModStr(9))��ʽ��תΪyyyymm(bdModStr(5));��֮����תΪ��֮��
	 * 
	 * @return dateStr
	 * @throws ParseException
	 */
	public static String rectDate59(String dateStr) throws ParseException {
		SimpleDateFormat shortFormat = new SimpleDateFormat(bdModStr(5));
		SimpleDateFormat normalFormat = new SimpleDateFormat(bdModStr(9));
		if (dateStr != null && dateStr.trim().length() > 0) {
			if (dateStr.indexOf("-") > -1) {
				dateStr = shortFormat.format(normalFormat.parse(dateStr));
			} else {
				dateStr = normalFormat.format(shortFormat.parse(dateStr));
			}
		}
		return dateStr;
	}
}
