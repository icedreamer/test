package com.tlys.comm.interceptor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;
import com.tlys.sys.service.SysLogService;

/**
 * �쳣������
 * 
 * @author CCSONG
 * 
 */
@Component
public class ExceptionInterceptor extends ExceptionMappingInterceptor {

	private static final long serialVersionUID = -7804199502997066622L;
	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	SysLogService logService;

	@Override
	protected ExceptionMappingConfig findMappingFromExceptions(List<ExceptionMappingConfig> exceptionMappingList, Throwable arg1) {
		// TODO Auto-generated method stub
		return super.findMappingFromExceptions(exceptionMappingList, arg1);
	}

	@Override
	protected void handleLogging(Exception e) {
		// TODO Auto-generated method stub
		super.handleLogging(e);
	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		return super.intercept(arg0);
	}

	@Override
	protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder) {
		if (logger.isDebugEnabled()) {
			logger.debug("�����ࣺ" + invocation.getProxy().getActionName());
			logger.debug("���󷽷���" + invocation.getProxy().getMethod());
		}
		// ���������־���
		// SysBkerrorlog bkerrorlog = new SysBkerrorlog();
		// Exception exception = exceptionHolder.getException();
		// bkerrorlog.setDescription(exception.toString());
		// bkerrorlog.setObjname(invocation.getProxy().getActionName());
		// bkerrorlog.setObjtype("FUNCTION");
		// bkerrorlog.setRectime(new Date());
		// logService.save(bkerrorlog);
		super.publishException(invocation, exceptionHolder);
	}
}
