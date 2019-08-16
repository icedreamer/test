package com.tlys.comm.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlys.sys.service.SysMenuService;

@Component
public class MapUtil extends HashMap<String, String> {
	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	SysMenuService menuService;
	String isNew;

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public MapUtil(String isNew) {
		this.isNew = isNew;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return super.containsKey(key);
	}

	public String get(String menuopercode) {
		String tmpSuffix = "";
		if (null != isNew && "true".equals(isNew)) {
			tmpSuffix = "A";
			String addkey = CommUtils.getString(menuopercode, "_", tmpSuffix);
			if (super.containsKey(addkey)) {
				return get(addkey);
			}
		}
		if (null != isNew && "false".equals(isNew)) {
			tmpSuffix = "U";
			String modifyKey = CommUtils.getString(menuopercode, "_", tmpSuffix);
			if (super.containsKey(modifyKey)) {
				return get(modifyKey);
			}
		}
		return super.get(menuopercode);
	}

}
