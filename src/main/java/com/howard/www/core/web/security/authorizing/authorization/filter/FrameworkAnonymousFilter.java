package com.howard.www.core.web.security.authorizing.authorization.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author howard
 *
 */
public class FrameworkAnonymousFilter extends AnonymousFilter {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkAnonymousFilter.class);

	protected boolean isEnabled(ServletRequest request,
			ServletResponse response, String path, Object mappedValue)
			throws Exception {
		log.info("anon FrameworkAnonymousFilter intercept path this is {}",
				path);
		return super.isEnabled(request, response, path, mappedValue);
	}

	protected boolean pathsMatch(String path, ServletRequest request) {
		// TODO Auto-generated method stub
		return super.pathsMatch(path, request);
	}

	protected boolean pathsMatch(String pattern, String path) {
		// TODO Auto-generated method stub
		return super.pathsMatch(pattern, path);
	}

	protected boolean isEnabled(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {

		return super.isEnabled(request, response);
	}

}
