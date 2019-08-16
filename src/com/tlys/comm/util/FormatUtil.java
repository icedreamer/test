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
 * @author fengym 此类专门用于各种格式转换，特别是日期格式转换
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
	/** MM月dd日 */
	public final static int MONTH_DAY_ZH = 8;

	/**
	 * 将日期格式化成相应的字符串
	 * 
	 * @param d
	 * @param mod:日期模式，详见本工具提供的常量
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
	 * 将字符串解析成日期
	 * 
	 * @param dstr
	 * @param mod：日期模式，详见本工具提供的常量
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
	 * 当前时间：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String nowDateNormalStr() {
		return fmtDate(new Date(), 1);
	}

	/**
	 * 当前日期：yyyyMMdd
	 * 
	 * @return
	 */
	public static String nowDateStr() {
		return fmtDate(new Date(), 3);
	}

	/**
	 * 明天的日期:yyyyMMdd
	 * 
	 * @return
	 */
	public static String tomorrowDateStr() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return fmtDate(cal.getTime(), 3);
	}

	/**
	 * 按传入的日期格式类型返回当前日期
	 * 
	 * @param dateStyle
	 * @return
	 */
	public static String nowDateStr(int dateStyle) {
		return fmtDate(new Date(), dateStyle);
	}

	/**
	 * 返回当前年月yyyyMM
	 * 
	 * @return
	 */
	public static String nowYearMonthStr() {
		return fmtDate(new Date(), 5);
	}

	/**
	 * 返回当前日期加时间 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String nowSecondStr() {
		return fmtDate(new Date(), 4);
	}

	/**
	 * 返回当前月份MM
	 * 
	 * @return
	 */
	public static String nowMonthOnly() {
		return fmtDate(new Date(), 6);
	}

	/**
	 * 返回当前年份yyyy
	 * 
	 * @return
	 */
	public static String nowYearOnly() {
		return fmtDate(new Date(), 7);
	}

	/**
	 * 返回今天的日期数组，即今天的0点到23：59
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
	 * 生成任意一天的日期数组
	 * 
	 * @param dayStr
	 * @return 日期数组，从当日0点到23：59
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
	 * 生成时间格式模式字符串
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
			modStr = "MM月dd日";
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
	 * 进行字符串型日期转换 如果是yyyy-mm-dd(bdModStr(1))格式，转为yyyymmdd(bdModStr(3));反之，则转为反之。
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
	 * 进行字符串型日期转换 如果是yyyy-mm(bdModStr(9))格式，转为yyyymm(bdModStr(5));反之，则转为反之。
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
