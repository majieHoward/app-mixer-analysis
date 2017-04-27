package com.howard.www.core.web.security.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.json.JSONArray;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.howard.www.core.web.security.service.IFrameworkShiroFilerChainManager;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkShiroFilerChainManager implements InitializingBean,
		DisposableBean, IFrameworkShiroFilerChainManager {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkShiroFilerChainManager.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	private ConcurrentHashMap<String, NamedFilterList> defaultFilterChains;

	private DefaultFilterChainManager obtainDefaultFilterChainManager(
			String defaultFilterChainManagerBeanName) {
		return (DefaultFilterChainManager) cApplicationContext
				.getBean(defaultFilterChainManagerBeanName);
	}

	public void initializationFilterChains() throws Exception {
		// 1、首先删除以前老的filter chain并注册默认的
		obtainDefaultFilterChainManager("frameworkDefaultFilterChainManager")
				.getFilterChains().clear();
		if (defaultFilterChains != null) {
			obtainDefaultFilterChainManager(
					"frameworkDefaultFilterChainManager").getFilterChains()
					.putAll(defaultFilterChains);
		}

	}

	public void evaluateFilterChains(JSONArray itemsOfFilterChain)
			throws Exception {
		// 1、首先删除以前老的filter chain并注册默认的
		obtainDefaultFilterChainManager("frameworkDefaultFilterChainManager")
				.getFilterChains().clear();
		ConcurrentHashMap<String, String> authorizationOfItems = new ConcurrentHashMap<String, String>();
		authorizationOfItems.put("/analysisadmin/analysis/login.html",
				"frameworkanon");
		authorizationOfItems.put("/analysisadmin/analysis/doLogin.html",
				"frameworkanon,frameworkperms[demo:xxxxxxxxx]");
		authorizationOfItems.put("/analysisadmin/analysis/updateChain.html",
				"frameworkauthc");
		authorizationOfItems.put("/assets/**", "frameworkanon");
		authorizationOfItems
				.put("/analysisadmin/analysis/index.html",
						"frameworkperms[demo:ddd],frameworkroles[admin]");
		authorizationOfItems.put("/analysisadmin/**", "frameworkauthc");
		if (!CollectionUtils.isEmpty(authorizationOfItems)) {
			for (Map.Entry<String, String> entry : authorizationOfItems.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue();
				log.info(
						"Initialize the definition of chain The url is {} , the chainDefinition is {}",
						url, chainDefinition);
				obtainDefaultFilterChainManager(
						"frameworkDefaultFilterChainManager").createChain(url, chainDefinition);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		defaultFilterChains = new ConcurrentHashMap<String, NamedFilterList>(
				obtainDefaultFilterChainManager(
						"frameworkDefaultFilterChainManager").getFilterChains());
	}

	public void destroy() throws Exception {

	}
}
