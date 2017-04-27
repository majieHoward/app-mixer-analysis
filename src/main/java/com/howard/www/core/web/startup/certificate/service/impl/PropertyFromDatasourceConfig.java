package com.howard.www.core.web.startup.certificate.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.web.startup.certificate.dao.IGainPropertyFromDatasourceConfigDao;
import com.howard.www.core.web.startup.certificate.service.PropertyFromConfig;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */

public class PropertyFromDatasourceConfig extends PropertyFromConfig implements
		BeanFactoryAware, InitializingBean, DisposableBean {
	protected final Logger log = LoggerFactory
			.getLogger(PropertyFromDatasourceConfig.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	private BeanFactory beanFactory;
	private Properties properties = new Properties();

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public List<String> obtainList(String prefix) {
		if (properties == null || prefix == null) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<String>();
		Enumeration<?> en = properties.propertyNames();
		String key;
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				list.add(properties.getProperty(key));
			}
		}
		return list;
	}

	public Set<String> obtainSet(String prefix) {
		if (properties == null || prefix == null) {
			return Collections.emptySet();
		}
		Set<String> set = new TreeSet<String>();
		Enumeration<?> en = properties.propertyNames();
		String key;
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				set.add(properties.getProperty(key));
			}
		}
		return set;
	}

	public Map<String, String> obtainMap(String prefix) {
		if (properties == null || prefix == null) {
			return Collections.emptyMap();
		}
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> en = properties.propertyNames();
		String key;
		int len = prefix.length();
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				map.put(key.substring(len), properties.getProperty(key));
			}
		}
		return map;
	}

	public Properties obtainProperties(String prefix) {
		Properties props = new Properties();
		if (properties == null || prefix == null) {
			return props;
		}
		Enumeration<?> en = properties.propertyNames();
		String key;
		int len = prefix.length();
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.startsWith(prefix)) {
				props.put(key.substring(len), properties.getProperty(key));
			}
		}
		return props;
	}

	public String obtainPropertiesString(String prefix) {
		String property = "";
		if (properties == null || prefix == null) {
			return property;
		}
		Enumeration<?> en = properties.propertyNames();
		String key;
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			if (key.equals(prefix)) {
				return properties.getProperty(key);
			}
		}
		return property;
	}

	public Map<String, Object> obtainBeanMap(String prefix) {
		Map<String, String> keyMap = obtainMap(prefix);
		if (keyMap.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>(
				keyMap.size());
		String key, value;
		for (Map.Entry<String, String> entry : keyMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			resultMap.put(key, beanFactory.getBean(value, Object.class));
		}
		return resultMap;
	}

	/**
	 * initialization Property object from datasource
	 */
	@SuppressWarnings("unchecked")
	public void initPropertyFromResources() throws Exception {
		log.info("obtain system configuration parameters");
		IGainPropertyFromDatasourceConfigDao gainPropertyFromDatasourceConfigDao = (IGainPropertyFromDatasourceConfigDao) cApplicationContext
				.getBean("gainPropertyFromDatasourceConfigDao");
		IDataTransferObject queryParameters = new DataTransferObject();
		List<JSONObject> appInitparameters = gainPropertyFromDatasourceConfigDao
				.obtainAllTheAppInitparameters(queryParameters);
		if (appInitparameters != null && appInitparameters.size() > 0) {
			evaluateParameterList(appInitparameters);
		}
	}

	private void evaluateParameterList(List<JSONObject> appInitparameters)
			throws Exception {
		/**
		 * Properties extends Hashtable
		 * 
		 * public synchronized Object setProperty(String key, String value)
		 * 
		 * Do not use multiple threads
		 */
		for (JSONObject appInitparameter : appInitparameters) {
			String keyOfAppInitparameter = FrameworkStringUtil
					.asString(appInitparameter.get("PARAMETERNAME"));
			String ValueOfAppInitparameter = FrameworkStringUtil
					.asString(appInitparameter.get("PARAMETERVALUE"));
			if (!"".equals(keyOfAppInitparameter)) {
				this.properties.setProperty(keyOfAppInitparameter,
						ValueOfAppInitparameter);
			}
		}
	}

	public void destroy() throws Exception {
		
	}

	public void afterPropertiesSet() throws Exception {
		initPropertyFromResources();		
	}
}
