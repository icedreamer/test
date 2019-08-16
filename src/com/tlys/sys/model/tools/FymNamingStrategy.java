/**
 * 
 */
package com.tlys.sys.model.tools;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * @author fengym
 *
 */
public class FymNamingStrategy extends DefaultNamingStrategy {
	
	 public static final FymNamingStrategy INSTANCE = new FymNamingStrategy(); 
     public String classToTableName(String className) { 
    	 
    	 return "TB_"+StringHelper.unqualify(className).toUpperCase(); 
     } 
}
