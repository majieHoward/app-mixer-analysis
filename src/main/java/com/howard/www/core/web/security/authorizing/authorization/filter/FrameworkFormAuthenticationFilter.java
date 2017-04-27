package com.howard.www.core.web.security.authorizing.authorization.filter;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * Authc indicates the need for authentication (login) to be used, no parameters
 * 
 * authorization授权
 * 
 * @author howard
 * 
 */
public class FrameworkFormAuthenticationFilter extends FormAuthenticationFilter {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkFormAuthenticationFilter.class);

	/**
	 * AbstractShiroFilter createInstance()
	 * 
	 * Override the parent class method
	 */
	public void setLoginUrl(String loginUrl) {
		/**
		 * attribute of loginUrl this is defined in bean that name is
		 * shiroFilter from application-init-datasource.xml
		 */
		super.setLoginUrl(loginUrl);
	}

	/**
	 * Override the parent class method
	 */
	protected void redirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		String accessUrl = FrameworkStringUtil.asString(WebUtils
				.toHttp(request).getRequestURL());
		/**
		 * 判读是否是手机端访问
		 */
		if(!"".equals(accessUrl)){
			if(accessUrl.indexOf("/analysisadmin/analysis/mobile/")>0){
				log.info("访问的是手机端页面");
				loginUrl="/analysisadmin/analysis/mobile/login.html";
			}
		}
		log.info("重定向到登陆页面,地址是 {}", loginUrl);
		WebUtils.issueRedirect(request, response, loginUrl);
	}

	/**
	 * Override the parent class method
	 */
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		return super.executeLogin(request, response);
	}

	/**
	 * Override the parent class method
	 */
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		return super.createToken(request, response);
	}

	/**
	 * onPreHandle会自动调用isAccessAllowed和onAccessDenied这两个方法决定是否继续处理
	 */
	/**
	 * Override the parent class method
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false
	 */
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		subject.isAuthenticated();
		boolean isAccessAllowed = super.isAccessAllowed(request, response,
				mappedValue);
		log.info(
				"{} AccessAllowed path this is {} authc FrameworkFormAuthenticationFilter intercept ",
				isAccessAllowed, WebUtils.toHttp(request).getRequestURL());
		return isAccessAllowed;
	}

	/**
	 * Override the parent class method
	 * 表示当访问拒绝时是否已经处理了;如果返回true表示需要继续处理;如果返回false表示该拦截器实例已经处理了,将直接返回即可
	 */
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		return super.onAccessDenied(request, response);
	}

}
