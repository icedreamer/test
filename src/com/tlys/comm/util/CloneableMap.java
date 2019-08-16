/**
 * 
 */
package com.tlys.comm.util;

import java.util.LinkedHashMap;

/**
 * @author fengym
 * �ɿ�¡��Map
 */
public class CloneableMap extends LinkedHashMap implements Cloneable {
	/**
	 * ����Object�е�clone���� 
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
