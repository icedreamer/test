package com.tlys.sys.model.job;

import java.lang.reflect.Method;

public class TimerTask {
	Class<Object> clazz;
	Object object;
	Method method;
	String cronExpression;

	public Class<Object> getClazz() {
		return clazz;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public Method getMethod() {
		return method;
	}

	public Object getObject() {
		return object;
	}

	public void setClazz(Class<Object> clazz) {
		this.clazz = clazz;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
