/**
 * 
 */
package com.tlys.comm.util;

/**
 * @author fengym
 * 这个类的作用是想：
 * 在程序中随时获取spring管理的bean
 * 按照有关资料上说法,此类实现了ApplicationContextAware，并实现了setApplicationContext方法后
 * 就可以在任意地方调用AppContextUtil.getContext得到ApplicationContext
 * 然后就可以通过ApplicationContext拿到bean
 * 也试过在applicationContext-resourse.xml文件中注册过这个bean
 * 但也不起作用，系统启动时spring并没有为这个类注入ApplicationContext
 *
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContextUtil implements ApplicationContextAware {
	private static ApplicationContext appContext;// 声明一个静态变量保存


	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		AppContextUtil.appContext = contex;
	}

	public static ApplicationContext getContext() {
		return appContext;
	}
	
	public static Object getBean(String paramString)
	  {
	    return appContext.getBean(paramString);
	  }

}