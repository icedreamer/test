/**
 * 
 */
package com.tlys.comm.util;

import java.util.LinkedHashMap;

/**
 * @author fengym
 * 可克隆的Map
 */
public class CloneableMap extends LinkedHashMap implements Cloneable {
	/**
	 * 重置Object中的clone方法 
	 */
	public CloneableMap clone(){
		CloneableMap newMap = null;
		try {
			newMap = (CloneableMap)super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newMap;
	}
}
