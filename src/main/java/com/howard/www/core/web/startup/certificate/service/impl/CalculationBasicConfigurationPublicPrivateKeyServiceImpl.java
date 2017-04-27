package com.howard.www.core.web.startup.certificate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.howard.www.core.web.startup.certificate.service.ICalculationPublicPrivateKeyService;

@Repository("basicConfigurationPublicPrivateKey")
public class CalculationBasicConfigurationPublicPrivateKeyServiceImpl extends
		BaseOfPublicPrivateKey implements ICalculationPublicPrivateKeyService {
	protected final Logger log = LoggerFactory
			.getLogger(CalculationBasicConfigurationPublicPrivateKeyServiceImpl.class);
	private static final String KEYSTORE_PASSWORD = "panguopendays";

	public void calculationPublicPrivateKey(String nameOfCertificatePath)
			throws Exception {
		super.evaluatePasswordOfKeyStore(KEYSTORE_PASSWORD);
		super.structurePublicPrivateKey(nameOfCertificatePath);
		log.info(this.obtainPrivateKeyValue().toString());
		log.info(this.obtainPublicKeyValue().toString());

	}
}
