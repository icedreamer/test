package com.tlys.comm.bas;

import javax.servlet.ServletContext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ccsong 2012-3-22 ÏÂÎç12:35:22
 */
@Service
public class CacheService {
	boolean flag = false;

	public Object get(String k) {
		if (flag) {
			return null;
		}
		ServletContext context = ServletActionContext.getServletContext();
		Cache cache = (Cache) context.getAttribute("tlysCache");
		Element element = cache.get(k);
		if (element != null) {
			return element.getObjectValue();
		}
		return null;
	}

	public void put(String k, Object v) {
		ServletContext context = ServletActionContext.getServletContext();
		Cache cache = (Cache) context.getAttribute("tlysCache");
		Element element = new Element(k, v);
		element.setTimeToLive(60);
		element.setTimeToIdle(30);
		cache.put(element);
	}

	public void clearAllCache() {
		ServletContext context = ServletActionContext.getServletContext();
		Cache cache = (Cache) context.getAttribute("tlysCache");
		cache.removeAll();
	}
}
