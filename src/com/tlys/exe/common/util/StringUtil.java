package com.tlys.exe.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletResponse;

/**
 * 字符串处理工具
 * 
 * @author 孔垂云
 * 
 */
public class StringUtil {

	// 取得系统时间
	public static String getSystemDate() {
		String strTime = "";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			strTime = df.format(new java.util.Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(Date date) {
		String strTime = "";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			strTime = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String formatStr) {
		String strTime = "";
		try {
			DateFormat df = new SimpleDateFormat(formatStr);
			strTime = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 取得根据当前日期加减后的日期
	 * 
	 * @param dayNum
	 * @return
	 */
	public static String getOptSystemDate(int dayNum) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5, dayNum);
		return String.valueOf(df.format(gc.getTime()));
	}

	/**
	 * 取得特定日期加减后的日期
	 * 
	 * @param date
	 * @param dayNum
	 * @return
	 */
	public static String getOptSystemDate(Date date, int dayNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5, dayNum);
		return String.valueOf(df.format(gc.getTime()));
	}

	/**
	 * 取得两个日期的时间差
	 * 
	 */
	public static long getDateDifferent(String date1, String date2) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		java.util.Date dt1 = formatter.parse(date1, pos);
		java.util.Date dt2 = formatter.parse(date2, pos1);
		long l = (dt2.getTime() - dt1.getTime()) / (3600 * 24 * 1000);
		return l;
	}

	/**
	 * 取得两个日期的时间差
	 * 
	 */
	public static long getDateDifferentFromToday(Date dt1) {
		long l = (dt1.getTime() - new Date().getTime()) / (3600 * 24 * 1000);
		return l;
	}

	/**
	 * 把前台传过来的含中文的url字符串转换成标准中文，比如%25E5%258C%2597%25E4%25BA%25AC转换成北京
	 */
	public static String decodeUrl(String url) {
		String str = "";
		try {
			str = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 把stuts2取得的中文转换成url可识别的字符串，比如%E5%8C%97%E4%BA%AC转换成%25E5%258C%2597%25E4%25BA%25AC
	 * 
	 * @param url
	 * @return
	 */
	public static String encodeUrl(String url) {
		String str = "";
		try {
			str = URLEncoder.encode(URLEncoder.encode(URLDecoder.decode(url, "UTF-8"), "UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 车号补全，如果为6为，前面补0
	 * 
	 * @param i
	 * @return
	 */
	public static String get7Car_no(int i) {
		if (i < 10)
			return "000000" + i;
		else if (i >= 10 && i < 100)
			return "00000" + i;
		else if (i >= 100 && i < 1000)
			return "0000" + i;
		else if (i >= 1000 && i < 10000)
			return "000" + i;
		else if (i >= 10000 && i < 100000)
			return "00" + i;
		else if (i >= 100000 && i < 1000000)
			return "0" + i;
		else if (i >= 1000000 && i < 10000000)
			return "" + i;
		else
			return "";
	}

	/**
	 * 0处理，统计表为0的显示为空
	 * 
	 * @param num
	 * @return
	 */
	public static String dealZero(int num) {
		if (num == 0)
			return "";
		else
			return String.valueOf(num);
	}

	/**
	 * 日期处理，如果为1949-10-01，则转换为空
	 * 
	 * @param date
	 * @return
	 */
	public static String dealDate(Date date) {
		if (StringUtil.dateToString(date, "yyyy-MM-dd").equals("1949-10-01")) {
			return "";
		} else
			return StringUtil.dateToString(date, "yyyy-MM-dd");
	}

	// 格式化2位小数
	public static float floatFormat(float value) {
		NumberFormat nformat = NumberFormat.getNumberInstance();
		DecimalFormat df = new DecimalFormat("#####.00");
		df.setMaximumFractionDigits(2);
		nformat.setMaximumFractionDigits(2);
		String temp = df.format(value);
		return Float.parseFloat(temp);
	}

	public static String operStr(String str) {
		String strArr[] = str.split(",");
		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = "'" + strArr[i].trim() + "'";
		}
		str = "";
		for (String s : strArr) {
			str = str + s.trim() + ",";
		}
		str = str.substring(0, str.length() - 1);
		return str;
	}

	/**
	 * 取两个数的百分率
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static float formatBfl(int first, int second) {
		if (second == 0)
			return 0;
		else if (first == 0)
			return 0;
		else {
			NumberFormat nformat = NumberFormat.getNumberInstance();
			DecimalFormat df = new DecimalFormat("#####.00");
			df.setMaximumFractionDigits(2);
			nformat.setMaximumFractionDigits(2);
			String temp = df.format(1.0 * first / second * 100);
			return Float.parseFloat(temp);
		}
	}

	public static float formatBfl(long first, long second) {
		if (second == 0)
			return 0;
		else if (first == 0)
			return 0;
		else {
			NumberFormat nformat = NumberFormat.getNumberInstance();
			DecimalFormat df = new DecimalFormat("#####.00");
			df.setMaximumFractionDigits(2);
			nformat.setMaximumFractionDigits(2);
			String temp = df.format(1.0 * first / second * 100);
			return Float.parseFloat(temp);
		}
	}

	/**
	 * Servlet打印字符串
	 * 
	 * @param response
	 * @param str
	 */
	public static void out(HttpServletResponse response, String str) {
		try {
			response.setContentType("text/html; charset=GBK");
			response.getWriter().println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
