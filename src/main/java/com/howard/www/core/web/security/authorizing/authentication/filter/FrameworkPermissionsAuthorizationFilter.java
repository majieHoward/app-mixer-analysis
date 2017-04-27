package com.howard.www.core.web.security.authorizing.authentication.filter;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkPermissionsAuthorizationFilter extends
		PermissionsAuthorizationFilter {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkPermissionsAuthorizationFilter.class);

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		/**
		 * appliedPaths表示已经申请过页面地址
		 * 
		 */
		if ((this.appliedPaths == null) || (this.appliedPaths.isEmpty())) {
			if (log.isTraceEnabled()) {
				log.trace("appliedpaths中保存了已经授权过链接地址,如果为appliedpaths空,证明此链接地址不需要进行权限控制,此过滤器将立即通过。");
			}
			return true;
		}
		log.info("已经申请访问过的地址列表{}", appliedPaths);
		String requestURI = getPathWithinApplication(request);
		log.info("正在进行访问的地址为:{}", requestURI);
		for (String path : this.appliedPaths.keySet()) {
			if (pathsMatch(path, request)) {
				log.trace(
						"Current requestURI matches pattern '{}'.  Determining filter chain execution...",
						path);
				Object config = this.appliedPaths.get(path);
				return isFilterChainContinued(request, response, path, config);
			}

		}

		return true;
	}

	private boolean isFilterChainContinued(ServletRequest request,
			ServletResponse response, String path, Object pathConfig)
			throws Exception {
		if (isEnabled(request, response, path, pathConfig)) {
			if (log.isTraceEnabled()) {
				log.trace(
						"Filter '{}' is enabled for the current request under path '{}' with config [{}].  Delegating to subclass implementation for 'onPreHandle' check.",
						new Object[] { getName(), path, pathConfig });
			}

			return onPreHandle(request, response, pathConfig);
		}

		if (log.isTraceEnabled()) {
			log.trace(
					"Filter '{}' is disabled for the current request under path '{}' with config [{}].  The next element in the FilterChain will be called immediately.",
					new Object[] { getName(), path, pathConfig });
		}

		return true;
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

	/**
	 * onPreHandle会自动调用isAccessAllowed和onAccessDenied这两个方法决定是否继续处理
	 */
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

	/**
	 * Override the parent class method
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false
	 */
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		Subject subject = getSubject(request, response);

		String[] perms = (String[]) (String[]) mappedValue;
		boolean isPermitted = true;
		if ((perms != null) && (perms.length > 0)) {
			if (perms.length == 1) {
				if (!(subject.isPermitted(perms[0]))) {
					isPermitted = false;
				}
			} else if (!(subject.isPermittedAll(perms))) {
				isPermitted = false;
			}

		}
		log.info(
				"{} AccessAllowed  need perms of {} FrameworkPermissionsAuthorizationFilter path this is {}",
				isPermitted, mappedValue, WebUtils.toHttp(request)
						.getRequestURL());
		return isPermitted;
	}
}
