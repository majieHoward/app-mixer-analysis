package com.howard.www.core.web.startup.certificate.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.howard.www.core.web.startup.certificate.service.ICalculationPublicPrivateKeyService;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * Structure startup parameters
 * 
 * @author howard
 * 
 */
@Repository("structureParameterOfBootConfig")
@Scope("prototype")
public class StructureParameterOfBootConfigServiceImpl extends Thread {
	protected final Logger log = LoggerFactory
			.getLogger(StructureParameterOfBootConfigServiceImpl.class);
	@Autowired
	private ApplicationContext cApplicationContext;
	/**
	 * latch
	 */
	private CountDownLatch latch;

	private Element configOfElementItem;

	private ConcurrentHashMap<String, String> initializationParameters;

	public StructureParameterOfBootConfigServiceImpl() {

	}

	public StructureParameterOfBootConfigServiceImpl(CountDownLatch latch,
			Element configOfElementItem,
			ConcurrentHashMap<String, String> initializationParameters) {
		this.initParameterOfBootConfig(latch, configOfElementItem,
				initializationParameters);
	}

	public void initParameterOfBootConfig(CountDownLatch latch,
			Element configOfElementItem,
			ConcurrentHashMap<String, String> initializationParameters) {
		this.latch = latch;
		this.configOfElementItem = configOfElementItem;
		this.initializationParameters = initializationParameters;
	}

	private void decryptionViaPrivateKey() throws Exception {
		if (this.configOfElementItem != null) {
			log.info("get parameters and values from the elements in XML");
			String bootParameterName = FrameworkStringUtil
					.asString(configOfElementItem
							.elementText("informationValue"));
			String bootParameterValue = FrameworkStringUtil
					.asString(configOfElementItem.elementText("informationKey"));
			String parameterEncryption = FrameworkStringUtil
					.asString(configOfElementItem.elementText("analyticalKey"));
			if (!"".equals(bootParameterName) && !"".equals(bootParameterValue)
					&& !"".equals(parameterEncryption)) {
				ICalculationPublicPrivateKeyService calculationPublicPrivateKeyService = obtainCalculationPublicPrivateKeyService(parameterEncryption);
				String attributeName = calculationPublicPrivateKeyService
						.obtainDecryptionViaPrivateKeyValue(bootParameterName
								.trim());
				String attributeValue = calculationPublicPrivateKeyService
						.obtainDecryptionViaPrivateKeyValue(bootParameterValue
								.trim());
				log.info("decryption one of initialization parameters,parameter name is {},parameter value is {}", attributeName, attributeValue);
				this.initializationParameters
						.put(attributeName, attributeValue);
			}
		}
	}

	private ICalculationPublicPrivateKeyService obtainCalculationPublicPrivateKeyService(
			String analyticalKeyBeanName) throws Exception {
		return (ICalculationPublicPrivateKeyService) cApplicationContext
				.getBean(analyticalKeyBeanName);
	}

	public void run() {
		try {
			decryptionViaPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		latch.countDown();
	}

}
