package com.howard.www.core.web.util;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.howard.www.core.tool.encoding.HandleChineseHelper;

public class RequestHelper {
	public static ConcurrentHashMap<String, Object> conversionRequestParameter(
			HttpServletRequest request) {
		ConcurrentHashMap<String, Object> mapOfRequestParameter = new ConcurrentHashMap<String, Object>();
		Enumeration<String> paramEnumsOfRequest = request.getParameterNames();
		String paramOfName = null;
		String paramOfValue = null;
		while (paramEnumsOfRequest.hasMoreElements()) {
			paramOfName = FrameworkStringUtil.asString(paramEnumsOfRequest
					.nextElement());
			paramOfValue = FrameworkStringUtil.asString(request
					.getParameter(paramOfName));
			if (!"".equals(paramOfValue) && !"".equals(paramOfName)) {
				mapOfRequestParameter.put(paramOfName,
						HandleChineseHelper.toChinese(paramOfValue));
			}
		}
		return mapOfRequestParameter;
	}
}
