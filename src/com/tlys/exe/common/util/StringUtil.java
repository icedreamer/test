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
 * �ַ���������
 * 
 * @author �״���
 * 
 */
public class StringUtil {

	// ȡ��ϵͳʱ��
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
	 * ��ʽ������
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
	 * ����ת�ַ���
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
	 * �ַ���ת����ʱ���ʽ
	 * 
	 * @param dateStr
	 *            ��Ҫת�����ַ���
	 * @param formatStr
	 *            ��Ҫ��ʽ��Ŀ���ַ��� ���� yyyy-MM-dd
	 * @return Date ����ת�����ʱ��
	 * @throws ParseException
	 *             ת���쳣
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
	 * ȡ�ø��ݵ�ǰ���ڼӼ��������
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
	 * ȡ���ض����ڼӼ��������
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
	 * ȡ���������ڵ�ʱ���
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
	 * ȡ���������ڵ�ʱ���
	 * 
	 */
	public static long getDateDifferentFromToday(Date dt1) {
		long l = (dt1.getTime() - new Date().getTime()) / (3600 * 24 * 1000);
		return l;
	}

	/**
	 * ��ǰ̨�������ĺ����ĵ�url�ַ���ת���ɱ�׼���ģ�����%25E5%258C%2597%25E4%25BA%25ACת���ɱ���
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
	 * ��stuts2ȡ�õ�����ת����url��ʶ����ַ���������%E5%8C%97%E4%BA%ACת����%25E5%258C%2597%25E4%25BA%25AC
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
	 * ���Ų�ȫ�����Ϊ6Ϊ��ǰ�油0
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
	 * 0����ͳ�Ʊ�Ϊ0����ʾΪ��
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
	 * ���ڴ������Ϊ1949-10-01����ת��Ϊ��
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

	// ��ʽ��2λС��
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
	 * ȡ�������İٷ���
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
	 * Servlet��ӡ�ַ���
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
