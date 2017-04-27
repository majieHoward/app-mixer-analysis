package com.howard.www.core.web.security.authorizing.service.impl;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.Filter;

import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.PathConfigProcessor;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.SimpleNamedFilterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.web.security.authorizing.authorization.filter.mgt.FrameworkDefaultFilterChainManager;
import com.howard.www.core.web.security.authorizing.dao.IAuthorizationListInitDao;
import com.howard.www.core.web.security.authorizing.service.IAssemblingChainDefinition;
import com.howard.www.core.web.security.authorizing.service.IAuthorizationListInit;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

public class AuthorizationListInitImpl implements IAuthorizationListInit {
	protected final Logger log = LoggerFactory.getLogger(AuthorizationListInitImpl.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	private String defaultFilterChainManagerBeanName;

	public String getDefaultFilterChainManagerBeanName() {
		return defaultFilterChainManagerBeanName;
	}

	public void setDefaultFilterChainManagerBeanName(String defaultFilterChainManagerBeanName) {
		this.defaultFilterChainManagerBeanName = defaultFilterChainManagerBeanName;
	}

	public void structureAuthorizationList(Vector<String> chainNames,
			ConcurrentMap<String, NamedFilterList> filterChains) throws Exception {
		IDataTransferObject queryParameters = new DataTransferObject();
		List<JSONObject> listOfAuthorization = obtainIAuthorizationListInitDao("authorizationListInitDao")
				.obtainAllAuthorizationListFromDB(queryParameters);
		if (listOfAuthorization != null && listOfAuthorization.size() > 0) {
			for (JSONObject itemOfAuthorization : listOfAuthorization) {
				String filterToken = obtainIAssemblingChainDefinition("assemblingChainDefinition"
						+ FrameworkStringUtil.asString(itemOfAuthorization.get("LIMIT_TYPE_ID")))
								.assemblingChainDefinition(itemOfAuthorization);
				/**
				 * 采用多线程进行处理构造出chainNames和filterChains
				 */
				String[] filterTokens = splitChainDefinition(filterToken);
				log.info("filterTokens这个字符串数组的值为:{}" + filterTokens);
				log.info("defaultFilterChainManagerBeanName的值为:" + this.defaultFilterChainManagerBeanName);
				for (String token : filterTokens) {
					String[] nameConfigPair = obtainFrameworkDefaultFilterChainManager(
							this.defaultFilterChainManagerBeanName).toNameConfigPairBySelf(token);
					log.info("nameConfigPair这个字符串数组的值为:" + nameConfigPair);
					Filter filter = obtainFrameworkDefaultFilterChainManager(this.defaultFilterChainManagerBeanName)
							.getFilter(nameConfigPair[0]);
					if (filter instanceof PathConfigProcessor) {
						((PathConfigProcessor) filter).processPathConfig(
								FrameworkStringUtil.asString(itemOfAuthorization.get("FILE_PATH")), nameConfigPair[1]);
					}
					NamedFilterList chain = new SimpleNamedFilterList(filterToken);
					filterChains.put(FrameworkStringUtil.asString(itemOfAuthorization.get("FILE_PATH")), chain);
					chain.add(filter);
				}
			}

		}
	}

	private IAssemblingChainDefinition obtainIAssemblingChainDefinition(String beanName) throws Exception {
		return (IAssemblingChainDefinition) cApplicationContext.getBean(beanName);
	}

	private FrameworkDefaultFilterChainManager obtainFrameworkDefaultFilterChainManager(
			String frameworkDefaultFilterChainManagerBeanName) {
		log.info("包含 DefaultFilterChainManagerBeanName(默认拦截过滤器链) 实例Bean 的名字是   {}",
				this.defaultFilterChainManagerBeanName);
		return (FrameworkDefaultFilterChainManager) cApplicationContext
				.getBean(frameworkDefaultFilterChainManagerBeanName);
	}

	private String[] splitChainDefinition(String chainDefinition) {
		return StringUtils.split(chainDefinition, StringUtils.DEFAULT_DELIMITER_CHAR, '[', ']', true, true);
	}

	private IAuthorizationListInitDao obtainIAuthorizationListInitDao(String beanName) throws Exception {
		return (IAuthorizationListInitDao) cApplicationContext.getBean(beanName);
	}
}
