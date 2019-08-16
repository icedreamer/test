package com.tlys.comm.util;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hwpf.HWPFDocument;

/**
 * 
 * @author ccsong 2012-11-23 ÏÂÎç4:43:55
 */
public class WordUtil {

	private static Log log = LogFactory.getLog(WordUtil.class);

	public static void writeWordDocument(HttpServletResponse response, HWPFDocument document, String fileName) {
		OutputStream os = null;
		try {
			response.setContentType("application/msword");
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".doc");
			System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
			os = response.getOutputStream();
			document.write(os);
			os.flush();
		} catch (Exception e) {
			log.error("write word document error.", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}
