package com.tlys.comm.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.tlys.annotation.Timer;
import com.tlys.sys.model.job.TimerJob;
import com.tlys.sys.model.job.TimerTask;

public class QuartzListener implements ServletContextListener {
	final static List<TimerTask> list = new ArrayList<TimerTask>();
	private Log logger = LogFactory.getLog(this.getClass());
	ApplicationContext ctx;

	SchedulerFactory schedulerFactory;

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			Collection<Scheduler> schedulers = schedulerFactory.getAllSchedulers();
			Iterator<Scheduler> it = schedulers.iterator();
			while (it.hasNext()) {
				Scheduler scheduler = it.next();
				scheduler.shutdown(true);
			}
		} catch (Exception e) {
			logger.debug("shutdown Scheduler fail!", e);
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		schedulerFactory = new StdSchedulerFactory();
		scanBeans();
		Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (Exception e) {
			logger.error("get scheduler fail!", e);
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			TimerTask timerTask = list.get(i);
			String clazzName = timerTask.getClazz().getName();
			String methodName = timerTask.getMethod().getName();
			String cronExpression = timerTask.getCronExpression();

			try {

				JobDetail jobDetail = new JobDetail(clazzName, methodName, TimerJob.class);

				jobDetail.getJobDataMap().put(TimerTask.class.getName(), timerTask);
				CronTrigger cronTrigger = new CronTrigger(clazzName, methodName, cronExpression);

				scheduler.scheduleJob(jobDetail, cronTrigger);
				logger.info(clazzName + "." + methodName + " timer start!" + cronExpression);
			} catch (Exception e) {
				logger.error(clazzName + "." + methodName + " timer error!" + cronExpression, e);
			}
		}
		try {
			scheduler.start();
		} catch (Exception e) {
			logger.error("start schuduler fail!", e);
		}
		long end = System.currentTimeMillis();
		logger.info("init quartz success in " + (end - start) + " ms.");
	}

	private void scanBeans() {
		SysInitListener listener = new SysInitListener();
		Class clazz = listener.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Timer timer = methods[i].getAnnotation(Timer.class);
			if (timer != null) {
				String cronExpression = timer.value();
				TimerTask timerTask = new TimerTask();
				timerTask.setClazz(clazz);
				timerTask.setMethod(methods[i]);
				timerTask.setCronExpression(cronExpression);
				timerTask.setObject(listener);
				list.add(timerTask);
			}
		}
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		this.ctx = ctx;
	}

}
