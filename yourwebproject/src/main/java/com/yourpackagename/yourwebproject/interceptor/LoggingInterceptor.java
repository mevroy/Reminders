package com.yourpackagename.yourwebproject.interceptor;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;










import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;










import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.service.LoggerService;



public class LoggingInterceptor implements HandlerInterceptor {
	@Autowired
	public LoggerService loggerService;
	private Logger log = LoggerFactory
			.getLogger(LoggingInterceptor.class);
			
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception { }

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3) throws Exception {
		
		if(handler!=null && handler instanceof HandlerMethod){	
			HandlerMethod handlerMethod =(HandlerMethod) handler;	
			EnableLogging enableLogging =handlerMethod.getMethodAnnotation(EnableLogging.class);
			if(enableLogging==null)
			{
				enableLogging =  handlerMethod.getBeanType().getAnnotation(EnableLogging.class);

			}
			if(enableLogging!=null && StringUtils.isNotBlank(enableLogging.loggerClass()))
			{
				long startTime = (Long)request.getAttribute(Key.startTime);
				long endTime = System.currentTimeMillis();
				long executeTime = endTime - startTime;
				
				AuditLog alog = new AuditLog(request);
				alog.setClazz(enableLogging.loggerClass());
				alog.setExecutionTimeMillis(executeTime);
				try{
					loggerService.insertOrUpdate(alog);
				} catch (Exception ex) {
					log.error("Unable to create entry into Audit Log");
				}
			}

		}
	 }
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object handler) throws Exception { return true; }
}
