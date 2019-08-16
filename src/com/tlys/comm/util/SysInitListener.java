package com.tlys.comm.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tlys.annotation.Timer;

public class SysInitListener implements ServletContextListener {

	private Log logger = LogFactory.getLog(this.getClass());
	static ServletContext sc;
	long lastModified = 0;
	CacheManager cm;
	Cache cache = null;
	

	public SysInitListener() {

	}

	public void contextInitialized(ServletContextEvent e) {
		sc = e.getServletContext();
		// HashMap conHash = getSysConfig();
		// Iterator it = conHash.keySet().iterator();
		// while (it.hasNext()) {
		// String key = (String) it.next();
		// String value = (String) conHash.get(key);
		// sc.setAttribute(key, value);
		// }
		// String cxtpath = (String) sc.getAttribute("cxtpath");
		// if (logger.isDebugEnabled()) {
		// logger.debug("TLYS-WEB启动成功 (" + cxtpath + ") : " + new
		// java.util.Date());
		// }
		// 加载缓存
		long start = System.currentTimeMillis();
		cm = CacheManager.create();// new CacheManager();
		cache = cm.getCache("tlysCache");
		sc.setAttribute("tlysCache", cache);
		long end = System.currentTimeMillis();
		logger.info("init cache:" + cache + " in " + (end - start) + " ms");
	}

	

	public void contextDestroyed(ServletContextEvent sce) {
		if (cm != null) {
			cache.flush();
			cm.shutdown();
			logger.info("tlysCache缓存被释放!");
		}
	}

	@Timer("0/20 * * * * ?")
	public void load() {
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("_config/config.inf");
			String filePath = url.getFile();
			File file = new File(filePath);
			if (lastModified == file.lastModified()) {
				return;
			}
			lastModified = file.lastModified();
			HashMap conHash = getSysConfig();
			Iterator it = conHash.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) conHash.get(key);
				sc.setAttribute(key, value);
			}
			logger.info("reload config.inf success.");
		} catch (Exception e) {
			logger.error("load config.inf error.", e);
		}

	}

	private HashMap getSysConfig() {
		HashMap configHash = new HashMap();
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("_config/config.inf");
			// FymLog.logInf(this.getClass(), "in", in);
			Properties props = new Properties();
			props.load(in);
			configHash.putAll(props);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return configHash;
	}
}
