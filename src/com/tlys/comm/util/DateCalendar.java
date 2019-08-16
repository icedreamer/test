package com.tlys.comm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalendar {
	/**
	 * 获取当前日期或固定日期几天后的日期
	 * 
	 * @param dateType
	 *            日期格式
	 * @return
	 */
	public static String getAddDays(String startTime, int i, String dateType) {
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		Date d = null;
		try {
			if (startTime != "" && startTime.length() > 0) {
				d = format.parse(startTime);
			} else {
				d = new Date();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(cal.DAY_OF_YEAR, i);
		String date = format.format(cal.getTime()).toString();
		return date;
	}

	/**
	 * 获取两个日期的间隔天数
	 * 
	 * @param dateType
	 *            日期格式
	 * @return
	 */
	public static int getBetweenToDays(String startTime, String endTime,
			String dateType) {
		int days = 0;
		int sDays = getInYearDays(startTime, dateType);
		int eDays = getInYearDays(endTime, dateType);
		days = eDays - sDays;
		return days;
	}

	/**
	 * 获取当前日期或固定日期是今年的第几天
	 * 
	 * @param dateType
	 *            日期格式
	 * @return
	 */
	public static int getInYearDays(String date, String dateType) {
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		Date d = null;
		try {
			d = format.parse(date);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int days = cal.get(Calendar.DAY_OF_YEAR);
		return days;
	}

	/**
	 * 获取当前日期
	 * 
	 * @param dateType
	 *            日期格式
	 * @return
	 */
	public static String getNowDate(String dateType) {
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return format.format(cal.getTime());
	}

	/**
	 * 获取指定日期的String
	 * 
	 * @param dateType
	 *            日期格式
	 * @return
	 */
	public static String getTheDate(java.util.Date d, String dateType) {
		String str = null;
		if (d != null) {
			SimpleDateFormat format = new SimpleDateFormat(dateType);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			str = format.format(cal.getTime());
		}
		return str;
	}

	/**
	 * 获取今年的第一天
	 * 
	 * @param dateType
	 *            日期格式
	 * @return
	 */
	public static String getNowYearStart(String dateType) {
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int days = cal.get(Calendar.DAY_OF_YEAR) - 1;
		cal.add(cal.DAY_OF_YEAR, -days);
		String date = format.format(cal.getTime()).toString();
		return date;
	}
	
	/**
	 * 获取今年第一天的日期类型值
	 * @param dateType
	 * @return
	 */
	public static Date getNowYearStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		int days = cal.get(Calendar.DAY_OF_YEAR) - 1;
		cal.add(cal.DAY_OF_YEAR, -days);
		Date date = cal.getTime();
		
		return date;
	}
	
	/**
	 * 获取本月第一天的日期类型
	 * @return
	 */
	public static Date getNowMonthStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		int days = cal.get(Calendar.DAY_OF_MONTH) - 1;
		cal.add(cal.DAY_OF_MONTH, -days);
		Date date = cal.getTime();
		return date;
	}

	/**
	 * @return java.sql.Date (format:yy-MM-dd)
	 */
	public static java.sql.Date toDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	/**
	 * @param String
	 * @return java.sql.Date (format:yy-MM-dd)
	 */
	public static java.sql.Date toDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		try {
			dateFormat.applyPattern("yyyy-MM-dd");
			java.util.Date vDate = dateFormat.parse(date);
			return new java.sql.Date(vDate.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取i个月前的时间
	 * 
	 * @param startTime
	 * @param dateType
	 * @return
	 */
	public static String getIMonth(String startTime, int i, String dateType) {
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		Date d = null;
		try {
			if (startTime != "" && startTime.length() > 0) {
				d = format.parse(startTime);
			} else {
				d = new Date();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(cal.MONTH, i);
		String date = format.format(cal.getTime()).toString();
		return date;
	}
}
