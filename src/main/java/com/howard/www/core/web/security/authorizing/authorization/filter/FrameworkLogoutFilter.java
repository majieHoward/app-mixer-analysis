package com.howard.www.core.web.security.authorizing.authorization.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author howard
 *
 */
public class FrameworkLogoutFilter extends LogoutFilter {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkFormAuthenticationFilter.class);

	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		Subject subject = getSubject(request, response);
		String redirectUrl = getRedirectUrl(request, response, subject);
		try {
			subject.logout();
		} catch (SessionException ise) {
			log.debug(
					"Encountered session exception during logout.  This can generally safely be ignored.",
					ise);
		}
		issueRedirect(request, response, redirectUrl);
		return false;
	}

}
