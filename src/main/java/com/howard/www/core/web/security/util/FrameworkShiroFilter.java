package com.howard.www.core.web.security.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkShiroFilter extends AbstractShiroFilter {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkShiroFilter.class);

	public FrameworkShiroFilter(WebSecurityManager webSecurityManager,
			FilterChainResolver resolver) {
		log.info("Structural FrameworkShiroFilter object");
		if (webSecurityManager == null) {
			throw new IllegalArgumentException(
					"WebSecurityManager property cannot be null.");
		}
		setSecurityManager(webSecurityManager);
		if (resolver != null)
			setFilterChainResolver(resolver);
	}

	@Override
	protected void executeChain(ServletRequest request,
			ServletResponse response, FilterChain origChain)
			throws IOException, ServletException {
		log.info("通过调用dofilterinternal执行滤波方法");
		FilterChain chain = getExecutionChain(request, response, origChain);
		chain.doFilter(request, response);
	}

	@Override
	protected FilterChain getExecutionChain(ServletRequest request,
			ServletResponse response, FilterChain origChain) {
		FilterChain chain = origChain;
		FilterChainResolver resolver = getFilterChainResolver();
		if (resolver == null) {
			log.debug("No FilterChainResolver configured.  Returning original FilterChain.");
			return origChain;
		}
		FilterChain resolved = resolver.getChain(request, response, origChain);
		if (resolved != null) {
			log.trace("Resolved a configured FilterChain for the current request.");
			chain = resolved;
		} else {
			log.trace("No FilterChain configured for the current request.  Using the default.");
		}
		return chain;
	}

	@Override
	protected void doFilterInternal(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doFilterInternal(servletRequest, servletResponse, chain);
	}

}
