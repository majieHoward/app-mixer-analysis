package com.howard.www.core.web.security.authorizing.authorization.filter.mgt;

import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.web.security.authorizing.service.IAuthorizationListInit;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver
		implements InitializingBean, DisposableBean {
	protected final Logger log = LoggerFactory.getLogger(FrameworkPathMatchingFilterChainResolver.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	private String defaultFilterChainManagerBeanName;

	public String getDefaultFilterChainManagerBeanName() {
		return defaultFilterChainManagerBeanName;
	}

	public void setDefaultFilterChainManagerBeanName(String defaultFilterChainManagerBeanName) {
		this.defaultFilterChainManagerBeanName = defaultFilterChainManagerBeanName;
	}

	private IAuthorizationListInit obtainIAuthorizationListInit(){
		return (IAuthorizationListInit) cApplicationContext.getBean("authorizationListInit");
	}

	private FrameworkDefaultFilterChainManager obtainFrameworkDefaultFilterChainManager(
			String frameworkDefaultFilterChainManagerBeanName) {
		log.info("包含 DefaultFilterChainManagerBeanName(默认拦截过滤器链) 实例Bean 的名字是   {}",
				this.defaultFilterChainManagerBeanName);
		return (FrameworkDefaultFilterChainManager) cApplicationContext
				.getBean(frameworkDefaultFilterChainManagerBeanName);
	}

	public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
		/**
		 * 拦截链开始处
		 */
		FilterChainManager filterChainManager = getFilterChainManager();
		log.info("是否包含有拦截过滤器链 {}", filterChainManager.hasChains());
		if (!filterChainManager.hasChains()) {
			return null;
		}
		/**
		 * 修改getPathWithinApplication方法
		 */
		String requestURI = getPathWithinApplication(request);
		log.info("开始构造整个系统的权限列表");
		Vector<String> chainNames = new Vector<String>();
		ConcurrentMap<String, NamedFilterList> filterChains = new ConcurrentHashMap<String, NamedFilterList>();
		/**
		 * 从数据库中查询出权限列表
		 */
		try {
			obtainIAuthorizationListInit().structureAuthorizationList(chainNames, filterChains);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set<String> pathPatternList = filterChainManager.getChainNames();
		Set<String> pathPatternList = filterChains.keySet();
		for (String pathPattern : pathPatternList) {
			if (pathMatches(pathPattern, requestURI)) {
				log.info("匹配的路径模式 [" + pathPattern + "] 对应的 requestURI [" + requestURI + "].  " + "利用相应的过滤器链...");
				chainNames.add(pathPattern);
			}
		}
		if (chainNames.size() == 0) {
			return null;
		}
		log.info("过滤器链的名字包含 {}", chainNames);
		return obtainFrameworkDefaultFilterChainManager(this.defaultFilterChainManagerBeanName)
				.proxyBySelf(filterChains, originalChain, chainNames);
		// return obtainFrameworkDefaultFilterChainManager(
		// this.defaultFilterChainManagerBeanName).proxy(originalChain,
		// chainNames);
	}

	public void destroy() throws Exception {

	}

	public void afterPropertiesSet() throws Exception {
		this.setFilterChainManager(obtainFrameworkDefaultFilterChainManager(this.defaultFilterChainManagerBeanName));
	}

	protected String getPathWithinApplication(ServletRequest request) {
		return super.getPathWithinApplication(request);
	}

}
