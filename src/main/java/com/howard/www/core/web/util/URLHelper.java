package com.howard.www.core.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UrlPathHelper;
/**
 * 
 * @author howard
 *
 */
public class URLHelper {
	public static String getURI(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctx = helper.getOriginatingContextPath(request);
		if (!StringUtils.isBlank(ctx)) {
			return uri.substring(ctx.length());
		} else {
			return uri;
		}
	}
}
