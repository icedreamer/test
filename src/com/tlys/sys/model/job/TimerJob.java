package com.tlys.sys.model.job;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TimerJob implements Job {

	final static Log logger = LogFactory.getLog(TimerJob.class);

	Object object = null;

	Method method = null;

	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		// TODO Auto-generated method stub
		if (object == null) {
			JobDetail jobDetail = ctx.getJobDetail();
			TimerTask timerTask = (TimerTask) jobDetail.getJobDataMap().get(
					TimerTask.class.getName());
			object = timerTask.getObject();
			method = timerTask.getMethod();
		}
		try {
			method.invoke(object);
		} catch (Exception e) {
			logger.error("invote fail!", e);
		}
	}
}
