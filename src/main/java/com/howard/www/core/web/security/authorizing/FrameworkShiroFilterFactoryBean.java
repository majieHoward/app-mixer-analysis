package com.howard.www.core.web.security.authorizing;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.web.security.authorizing.authorization.filter.mgt.FrameworkPathMatchingFilterChainResolver;
import com.howard.www.core.web.security.util.FrameworkShiroFilter;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkShiroFilterFactoryBean extends ShiroFilterFactoryBean {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkShiroFilterFactoryBean.class);
	@Autowired
	private ApplicationContext cApplicationContext;
	private AbstractShiroFilter instance;

	private FrameworkPathMatchingFilterChainResolver obtainFrameworkPathMatchingFilterChainResolver(
			String beanName) throws Exception {
		return (FrameworkPathMatchingFilterChainResolver) cApplicationContext
				.getBean(beanName);
	}

	public Map<String, String> getFilterChainDefinitionMap() {
		log.info("obtain Chain of filter definition");
		return super.getFilterChainDefinitionMap();
	}

	@SuppressWarnings("rawtypes")
	public void setFilterChainDefinitionMap(
			Map<String, String> filterChainDefinitionMap) {
		log.info("evaluate Chain of filter definition items");
		Iterator<Entry<String, String>> entries = filterChainDefinitionMap
				.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String keyOfFilterChainDefinition = (String) entry.getKey();
			String valueOfFilterChainDefinition = (String) entry.getValue();
			log.info("Chain of filter definition item key is {} value is {}",
					keyOfFilterChainDefinition, valueOfFilterChainDefinition);
		}
		super.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

	/**
	 * initialization
	 */
	public Object getObject() throws Exception {
		if (this.instance == null) {
			this.instance = createInstance();
		}
		return this.instance;
	}

	protected AbstractShiroFilter createInstance() throws Exception {
		log.debug("在系统开始创建框架Shiro滤波器实例");
		SecurityManager securityManager = super.getSecurityManager();
		if (securityManager == null) {
			String msg = "SecurityManager property must be set.";
			throw new BeanInitializationException(msg);
		}

		if (!(securityManager instanceof WebSecurityManager)) {
			String msg = "The security manager does not implement the WebSecurityManager interface.";
			throw new BeanInitializationException(msg);
		}

		return new FrameworkShiroFilter(
				(WebSecurityManager) securityManager,
				obtainFrameworkPathMatchingFilterChainResolver("frameworkPathMatchingFilterChainResolver"));

	}

	public Class getObjectType() {
		return FrameworkShiroFilter.class;
	}

}
