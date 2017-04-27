package com.howard.www.core.web.security.authorizing.authorization.filter.mgt;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;

import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Nameable;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.SimpleNamedFilterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkDefaultFilterChainManager extends
		DefaultFilterChainManager implements InitializingBean, DisposableBean,
		IFrameworkDefaultFilterChainManager {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkDefaultFilterChainManager.class);
	private Map<String, String> filterChainDefinitionMap;
	private String loginUrl;
	private String successUrl;
	private String unauthorizedUrl;

	public Set<String> getChainNames() {
		
		return super.getChainNames();
	}

	public FrameworkDefaultFilterChainManager() {
		setFilters(new ConcurrentHashMap<String, Filter>());
		setFilterChains(new ConcurrentHashMap<String, NamedFilterList>());
		addDefaultFilters(false);
	}

	@Override
	protected void applyChainConfig(String chainName, Filter filter,
			String chainSpecificFilterConfig) {
		// TODO Auto-generated method stub
		super.applyChainConfig(chainName, filter, chainSpecificFilterConfig);
	}

	/**
	 * Override the parent class method
	 */
	public NamedFilterList getChain(String chainName) {
		log.info("包含的 {} 拦截链项目列表", chainName);
		NamedFilterList paramOfChainFilter = super.getChain(chainName);

		if (paramOfChainFilter != null && paramOfChainFilter.size() > 0) {
			for (Filter filter : paramOfChainFilter) {
				log.info(""+filter.getClass().getSimpleName());
			}
		} else {
			log.info("obtain {} Intercept chain items Is empty", chainName);
		}
		return paramOfChainFilter;
	}

	@Override
	public void createChain(String chainName, String chainDefinition) {
		// TODO Auto-generated method stub
		super.createChain(chainName, chainDefinition);
	}

	/**
	 * 
	 */
	protected void initFilter(Filter filter) {
		log.info("execute initFilter method ", filter.toString());
	}

	public void destroy() throws Exception {

	}

	public void afterPropertiesSet() throws Exception {
		// 应用启动时执行
		// Apply the acquired and/or configured filters:
		Map<String, Filter> filters = getFilters();
		if (!CollectionUtils.isEmpty(filters)) {
			for (Map.Entry<String, Filter> entry : filters.entrySet()) {
				String name = entry.getKey();
				Filter filter = entry.getValue();
				log.info("初始化拦截器的拦截名称是 {} , 制定的拦截器是 {}", name,
						filter.toString());
				applyGlobalPropertiesIfNecessary(filter);
				if (filter instanceof Nameable) {
					((Nameable) filter).setName(name);
				}
			}
		}

		// build up the chains:
		Map<String, String> chains = getFilterChainDefinitionMap();
		if (!CollectionUtils.isEmpty(chains)) {
			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue();
				log.info("初始化链的定义的URL地址是 {} , 链定义 {}", url, chainDefinition);
				createChain(url, chainDefinition);
			}
		}
	}

	private void applyGlobalPropertiesIfNecessary(Filter filter) {
		applyLoginUrlIfNecessary(filter);
		applySuccessUrlIfNecessary(filter);
		applyUnauthorizedUrlIfNecessary(filter);
	}

	private void applySuccessUrlIfNecessary(Filter filter) {
		String successUrl = getSuccessUrl();
		if (StringUtils.hasText(successUrl)
				&& (filter instanceof AuthenticationFilter)) {
			AuthenticationFilter authcFilter = (AuthenticationFilter) filter;
			// only apply the successUrl if they haven't explicitly configured
			// one already:
			String existingSuccessUrl = authcFilter.getSuccessUrl();
			if (AuthenticationFilter.DEFAULT_SUCCESS_URL
					.equals(existingSuccessUrl)) {
				authcFilter.setSuccessUrl(successUrl);
			}
		}
	}

	private void applyUnauthorizedUrlIfNecessary(Filter filter) {
		String unauthorizedUrl = getUnauthorizedUrl();
		if (StringUtils.hasText(unauthorizedUrl)
				&& (filter instanceof AuthorizationFilter)) {
			AuthorizationFilter authzFilter = (AuthorizationFilter) filter;
			// only apply the unauthorizedUrl if they haven't explicitly
			// configured one already:
			String existingUnauthorizedUrl = authzFilter.getUnauthorizedUrl();
			if (existingUnauthorizedUrl == null) {
				authzFilter.setUnauthorizedUrl(unauthorizedUrl);
			}
		}
	}

	private void applyLoginUrlIfNecessary(Filter filter) {
		String loginUrl = getLoginUrl();
		if (StringUtils.hasText(loginUrl)
				&& (filter instanceof AccessControlFilter)) {
			AccessControlFilter acFilter = (AccessControlFilter) filter;
			// only apply the login url if they haven't explicitly configured
			// one already:
			String existingLoginUrl = acFilter.getLoginUrl();
			if (AccessControlFilter.DEFAULT_LOGIN_URL.equals(existingLoginUrl)) {
				acFilter.setLoginUrl(loginUrl);
			}
		}
	}

	public FilterChain proxy(FilterChain original, List<String> chainNames) {
		NamedFilterList configured = new SimpleNamedFilterList(
				chainNames.toString());
		for (String chainName : chainNames) {
			configured.addAll(getChain(chainName));
		}
		return configured.proxy(original);
	}

	public FilterChain proxyBySelf(ConcurrentMap<String, NamedFilterList> filterChains,
			FilterChain original, Vector<String> chainNames) {
		NamedFilterList configured = new SimpleNamedFilterList(
				chainNames.toString());
		for (String chainName : chainNames) {
			configured.addAll(getChainSelf(filterChains, chainName));
		}
		return configured.proxy(original);
	}

	private NamedFilterList getChainSelf(
			Map<String, NamedFilterList> filterChain, String chainName) {
		log.info("包含的 {} 拦截链项目列表", chainName);
		NamedFilterList paramOfChainFilter = filterChain.get(chainName);

		if (paramOfChainFilter != null && paramOfChainFilter.size() > 0) {
			for (Filter filter : paramOfChainFilter) {
				log.info("调用的过滤器的类名" + filter.getClass().getSimpleName());
			}
		} else {
			log.info("obtain {} Intercept chain items Is empty", chainName);
		}
		return paramOfChainFilter;
	}

	public String[] toNameConfigPairBySelf(String token)
			throws ConfigurationException{
		try {
			String[] pair = token.split("\\[", 2);
			String name = StringUtils.clean(pair[0]);

			if (name == null) {
				throw new IllegalArgumentException(
						"Filter name not found for filter chain definition token: "
								+ token);
			}
			String config = null;

			if (pair.length == 2) {
				config = StringUtils.clean(pair[1]);

				config = config.substring(0, config.length() - 1);
				config = StringUtils.clean(config);

				if ((config != null) && (config.startsWith("\""))
						&& (config.endsWith("\""))) {
					String stripped = config.substring(1, config.length() - 1);
					stripped = StringUtils.clean(stripped);

					if ((stripped != null) && (stripped.indexOf(34) == -1)) {
						config = stripped;
					}

				}

			}

			return new String[] { name, config };
		} catch (Exception e) {
			String msg = "Unable to parse filter chain definition token: "
					+ token;
			throw new ConfigurationException(msg, e);
		}
	}
	@Override
	protected String[] toNameConfigPair(String token)
			throws ConfigurationException {
		return toNameConfigPairBySelf(token);
	}

	public Map<String, String> getFilterChainDefinitionMap() {
		return filterChainDefinitionMap;
	}

	public void setFilterChainDefinitionMap(
			Map<String, String> filterChainDefinitionMap) {
		this.filterChainDefinitionMap = filterChainDefinitionMap;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

}
