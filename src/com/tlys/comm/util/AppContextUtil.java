/**
 * 
 */
package com.tlys.comm.util;

/**
 * @author fengym
 * �������������룺
 * �ڳ�������ʱ��ȡspring�����bean
 * �����й�������˵��,����ʵ����ApplicationContextAware����ʵ����setApplicationContext������
 * �Ϳ���������ط�����AppContextUtil.getContext�õ�ApplicationContext
 * Ȼ��Ϳ���ͨ��ApplicationContext�õ�bean
 * Ҳ�Թ���applicationContext-resourse.xml�ļ���ע������bean
 * ��Ҳ�������ã�ϵͳ����ʱspring��û��Ϊ�����ע��ApplicationContext
 *
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContextUtil implements ApplicationContextAware {
	private static ApplicationContext appContext;// ����һ����̬��������


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