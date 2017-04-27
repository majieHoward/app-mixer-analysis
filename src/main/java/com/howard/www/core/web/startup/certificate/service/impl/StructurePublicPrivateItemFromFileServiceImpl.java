package com.howard.www.core.web.startup.certificate.service.impl;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.howard.www.core.web.startup.certificate.service.ICalculationPublicPrivateKeyService;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("structurePublicPrivateItemFromFile")
@Scope("prototype")
public class StructurePublicPrivateItemFromFileServiceImpl extends Thread {
	protected final Logger log = LoggerFactory
			.getLogger(StructurePublicPrivateItemFromFileServiceImpl.class);
	@Autowired
	private ApplicationContext cApplicationContext;
	/**
	 * latch
	 */
	private CountDownLatch latch;

	private String nameOfCertificatePath;

	private String nameOfCertificate;

	private String nameOfSuffix;

	public StructurePublicPrivateItemFromFileServiceImpl() {

	}

	public StructurePublicPrivateItemFromFileServiceImpl(CountDownLatch latch,
			String nameOfCertificatePath, String nameOfCertificate,
			String nameOfSuffix) {
		this.initPublicPrivateItem(latch, nameOfCertificatePath,
				nameOfCertificate, nameOfSuffix);

	}

	public String getNameOfCertificatePath() {
		return nameOfCertificatePath;
	}

	public void setNameOfCertificatePath(String nameOfCertificatePath) {
		this.nameOfCertificatePath = nameOfCertificatePath;
	}

	public String getNameOfCertificate() {
		return nameOfCertificate;
	}

	public void setNameOfCertificate(String nameOfCertificate) {
		this.nameOfCertificate = nameOfCertificate;
	}

	public void initPublicPrivateItem(CountDownLatch latch,
			String nameOfCertificatePath, String nameOfCertificate,
			String nameOfSuffix) {
		this.latch = latch;
		this.nameOfCertificatePath = FrameworkStringUtil
				.asString(nameOfCertificatePath);
		this.nameOfCertificate = FrameworkStringUtil
				.asString(nameOfCertificate);
		this.nameOfSuffix = FrameworkStringUtil.asString(nameOfSuffix);
	}

	private ICalculationPublicPrivateKeyService obtainICalculationPublicPrivateKeyService(
			String nameOfCalculationPublicPrivateKeyService) throws Exception {
		return (ICalculationPublicPrivateKeyService) cApplicationContext
				.getBean(nameOfCalculationPublicPrivateKeyService);
	}

	private void calculationPublicPrivateKey() throws Exception {
		if (!"".equals(nameOfCertificatePath)) {
			StringBuffer nameOfCalculationPublicPrivateKeyBean = new StringBuffer();
			if (this.nameOfCertificate.length() > nameOfSuffix.length()) {
				int lengthOfSuffix = this.nameOfCertificate.length()
						- nameOfSuffix.length();
				String certificateValue = FrameworkStringUtil.splitString(
						this.nameOfCertificate, 0, lengthOfSuffix);
				if (!"".equals(certificateValue)) {
					nameOfCalculationPublicPrivateKeyBean.append(
							certificateValue).append("PublicPrivateKey");
					ICalculationPublicPrivateKeyService calculationPublicPrivateKeyService = obtainICalculationPublicPrivateKeyService(nameOfCalculationPublicPrivateKeyBean
							.toString());
					nameOfCalculationPublicPrivateKeyBean.setLength(0);
					calculationPublicPrivateKeyService
							.calculationPublicPrivateKey(this.nameOfCertificatePath);
				}
			}
		}

	}

	public void run() {
		try {
			calculationPublicPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		latch.countDown();
	}

}
