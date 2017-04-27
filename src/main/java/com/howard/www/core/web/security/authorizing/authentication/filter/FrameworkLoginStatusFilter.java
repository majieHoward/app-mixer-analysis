package com.howard.www.core.web.security.authorizing.authentication.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * Access to the login page Verify if login
 * 
 * @author howard
 * 
 */
public class FrameworkLoginStatusFilter extends AccessControlFilter {

	private String pageWithALoginStatus;

	public String getPageWithALoginStatus() {
		return pageWithALoginStatus;
	}

	public void setPageWithALoginStatus(String pageWithALoginStatus) {
		this.pageWithALoginStatus = pageWithALoginStatus;
	}

	protected boolean isAccessAllowed(ServletRequest paramServletRequest,
			ServletResponse paramServletResponse, Object paramObject)
			throws Exception {
		return false;
	}

	protected boolean onAccessDenied(ServletRequest paramServletRequest,
			ServletResponse paramServletResponse) throws Exception {
		Subject subject = getSubject(paramServletRequest, paramServletResponse);
		if (!subject.isAuthenticated()) {
			return true;
		} else {
			WebUtils.issueRedirect(paramServletRequest, paramServletResponse,
					pageWithALoginStatus);
			return false;
		}
	}

}
