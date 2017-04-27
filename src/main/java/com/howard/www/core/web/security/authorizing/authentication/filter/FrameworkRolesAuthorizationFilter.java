package com.howard.www.core.web.security.authorizing.authentication.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.web.security.authorizing.SystemAuthorizingRealmUtil;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkRolesAuthorizationFilter extends RolesAuthorizationFilter {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkRolesAuthorizationFilter.class);

	@Autowired
	private ApplicationContext cApplicationContext;

	private SystemAuthorizingRealmUtil getSystemAuthorizingRealmUtil() {
		return (SystemAuthorizingRealmUtil) cApplicationContext
				.getBean("systemAuthorizingRealmUtil");
	}

	/**
	 * onPreHandle会自动调用isAccessAllowed和onAccessDenied这两个方法决定是否继续处理
	 */
	/**
	 * Override the parent class method
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false
	 */
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		System.out.println(mappedValue.getClass().isArray());
		String[] rolesArray = (String[]) (String[]) mappedValue;
		boolean isAccessAllowed = false;
		if ((rolesArray == null) || (rolesArray.length == 0)) {
			return isAccessAllowed = true;
		} else {
			Stack<String> suffixExpressionItems = getSystemAuthorizingRealmUtil()
					.structureSuffixExpression(rolesArray[0]);
			if (suffixExpressionItems.size() == 1) {
				/**
				 * suffixExpression.pop()移除这个堆栈的顶部对象,并将该对象作为这个函数的返回值
				 */
				isAccessAllowed = super.isAccessAllowed(request, response,
						mappedValue);
			} else if (suffixExpressionItems.size() > 1) {
				List<String> authorizationSuffixResultSet = new ArrayList<String>();
				/**
				 * 将其中的权限字符串解析成true , false
				 */
				for (String suffixExpressionItem : suffixExpressionItems) {
					if (getSystemAuthorizingRealmUtil()
							.getConnectionSymbolSet().contains(
									suffixExpressionItem)) {
						/**
						 * 逻辑判断连接符"not", "!", "and", "&&", "or", "||"
						 */
						authorizationSuffixResultSet.add(suffixExpressionItem);
					} else {
						/**
						 * 获取当前用户是否包含有权限如果有就为"true"如果没有就为"false"
						 */
						String[] paramOfMappedValue = { suffixExpressionItem };
						authorizationSuffixResultSet.add(Boolean.toString(super
								.isAccessAllowed(request, response,
										paramOfMappedValue)));
					}
				}
				isAccessAllowed = getSystemAuthorizingRealmUtil()
						.computeSuffixExpression(authorizationSuffixResultSet);
			}
		}
		log.info(
				"{} AccessAllowed need roles of {} FrameworkRolesAuthorizationFilter path this is {}",
				isAccessAllowed, mappedValue, WebUtils.toHttp(request)
						.getRequestURL());
		return isAccessAllowed;
	}

	/**
	 * Override the parent class method
	 * 表示当访问拒绝时是否已经处理了;如果返回true表示需要继续处理;如果返回false表示该拦截器实例已经处理了,将直接返回即可
	 */
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException {
		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {
			saveRequestAndRedirectToLogin(request, response);
		} else {
			String unauthorizedUrl = getUnauthorizedUrl();

			if (StringUtils.hasText(unauthorizedUrl))
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			else {
				WebUtils.toHttp(response).sendError(401);
			}
		}
		return false;
	}

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

}
