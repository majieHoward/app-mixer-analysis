package com.howard.www.common.web.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.web.util.PojoAssignmentHelper;

/**
 * 
 * @author howard
 * 
 */
public class CommonContextInterceptor extends HandlerInterceptorAdapter {
	
	protected final Logger log = LoggerFactory
			.getLogger(CommonContextInterceptor.class);

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/**
		 * handler is access Action Controller Class Object
		 */
		IDataTransferObject paramOfDto = new DataTransferObject();
		paramOfDto.evaluateRequestParams(request);
		PojoAssignmentHelper.evaluateOneOfValueToParameter(handler,
				"paramOfDto", paramOfDto);
		return super.preHandle(request, response, handler);
	}

}
