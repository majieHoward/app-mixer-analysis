package com.howard.www.core.web.startup.certificate.service.impl;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.web.startup.certificate.service.IInitializeSystemStartUpConfigService;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
public class InitializeSystemStartUpConfigServiceImpl implements
		IInitializeSystemStartUpConfigService, InitializingBean, DisposableBean {
	protected final Logger log = LoggerFactory
			.getLogger(InitializeSystemStartUpConfigServiceImpl.class);
	@Autowired
	private ApplicationContext cApplicationContext;
	/**
     * 
     */
	private String bootConfigurationFilePath;

	private ConcurrentHashMap<String, String> initializationParameters = new ConcurrentHashMap<String, String>();

	public String getBootConfigurationFilePath() {
		return bootConfigurationFilePath;
	}

	public void setBootConfigurationFilePath(String bootConfigurationFilePath) {
		StringBuffer paramOfConfigFilePath = new StringBuffer();
		paramOfConfigFilePath
				.append(InitializeSystemStartUpConfigServiceImpl.class
						.getClassLoader().getResource("").getPath())
				.append("/").append(bootConfigurationFilePath);
		this.bootConfigurationFilePath = paramOfConfigFilePath.toString();
		paramOfConfigFilePath.setLength(0);
	}

	@SuppressWarnings("unchecked")
	public void loadingBootConfigurationFile() throws Exception {
		SAXReader reader = new SAXReader();
		File configurationFile = new File(this.bootConfigurationFilePath);
		Document documentOfConfigurationFile = reader.read(configurationFile);
		Element rootInitializationNode = documentOfConfigurationFile
				.getRootElement();
		List<Element> configOfElementItems = rootInitializationNode.elements();
		if (configOfElementItems.size() > 0) {
			int taskSize = 5;
			ExecutorService pool = Executors.newFixedThreadPool(taskSize);
			CountDownLatch latch = new CountDownLatch(
					configOfElementItems.size());
			for (Element configOfElementItem : configOfElementItems) {
				StructureParameterOfBootConfigServiceImpl structureParameterOfBootConfig = (StructureParameterOfBootConfigServiceImpl) cApplicationContext
						.getBean("structureParameterOfBootConfig");
				structureParameterOfBootConfig.initParameterOfBootConfig(latch,
						configOfElementItem, initializationParameters);
				pool.submit(structureParameterOfBootConfig);
			}
			latch.await();
			pool.shutdown();
		}
	}

	public String obtainItemOfInitConfigParameter(String attributeName)
			throws Exception {
		if (!"".equals(FrameworkStringUtil.asString(attributeName))) {
			return FrameworkStringUtil.asString(initializationParameters
					.get(attributeName));
		}
		return null;
	}

	public void destroy() throws Exception {
		
	}

	public void afterPropertiesSet() throws Exception {
		loadingBootConfigurationFile();		
	}
}
