package com.howard.www.core.web.startup.certificate.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Enumeration;
import javax.crypto.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.howard.www.core.web.security.service.IByteArrayConversionDecimalService;
import com.howard.www.core.web.security.service.IModifyTheStringService;
import com.howard.www.core.web.util.FrameworkStringUtil;

public class BaseOfPublicPrivateKey {
	@Autowired
	private ApplicationContext cApplicationContext;
	private String passwordOfKeyStore;
	private PublicKey valueOfPublicKey;
	private PrivateKey valueOfPrivateKey;

	public void evaluatePasswordOfKeyStore(String passwordOfKeyStore) {
		this.passwordOfKeyStore = passwordOfKeyStore;
	}

	public void evaluatePublicKey(PublicKey valueOfPublickey) {
		this.valueOfPublicKey = valueOfPublickey;
	}

	public void evaluatePrivateKey(PrivateKey valueOfPrivateKey) {
		this.valueOfPrivateKey = valueOfPrivateKey;
	}

	public PublicKey obtainPublicKeyValue() {
		return this.valueOfPublicKey;
	}

	public PrivateKey obtainPrivateKeyValue() {
		return this.valueOfPrivateKey;
	}

	public String obtainPasswordOfKeyStore() {
		return this.passwordOfKeyStore;
	}

	public void structurePublicPrivateKey(String nameOfCertificatePath) {
		KeyStore paramKeyStore = null;
		FileInputStream certificateFileInputStream = null;
		try {
			paramKeyStore = KeyStore.getInstance("PKCS12");
			certificateFileInputStream = new FileInputStream(
					nameOfCertificatePath);
			char[] keyStorePasswordOfChar = null;
			if ("".equals(FrameworkStringUtil.asString(this.passwordOfKeyStore))) {
				keyStorePasswordOfChar = null;
			} else {
				keyStorePasswordOfChar = this.passwordOfKeyStore.toCharArray();
			}
			paramKeyStore.load(certificateFileInputStream,
					keyStorePasswordOfChar);
			certificateFileInputStream.close();
			Enumeration<String> paramEnumeration = paramKeyStore.aliases();
			String keyAlias = null;
			if (paramEnumeration.hasMoreElements()) {
				keyAlias = FrameworkStringUtil.asString(paramEnumeration
						.nextElement());
			}
			if (!"".equals(FrameworkStringUtil.asString(keyAlias))) {
				PrivateKey paramOfPrivateKey = (PrivateKey) paramKeyStore
						.getKey(keyAlias, keyStorePasswordOfChar);
				this.evaluatePrivateKey(paramOfPrivateKey);
				Certificate cert = paramKeyStore.getCertificate(keyAlias);
				PublicKey paramOfPublicKey = cert.getPublicKey();
				this.evaluatePublicKey(paramOfPublicKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (certificateFileInputStream != null) {
					certificateFileInputStream.close();
				}
			} catch (IOException e) {

			}

		}
	}

	private IModifyTheStringService obtainModifyTheStringService(
			String nameOfModifyTheStringService) throws Exception {
		return (IModifyTheStringService) cApplicationContext
				.getBean(nameOfModifyTheStringService);
	}

	private IByteArrayConversionDecimalService obtainByteArrayConversionDecimalService(
			String nameOfByteArrayConversionDecimalServiceBean)
			throws Exception {
		return (IByteArrayConversionDecimalService) cApplicationContext
				.getBean(nameOfByteArrayConversionDecimalServiceBean);
	}

	public String obtainDecryptionViaPrivateKeyValue(String originalCipherText)
			throws Exception {
		Cipher paramCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		paramCipher.init(Cipher.DECRYPT_MODE, this.valueOfPrivateKey);
		String dataOfExtracted = obtainModifyTheStringService(
				"throughGzipModifyTheString").decompressionOriginalString(
				originalCipherText);
		byte[] characterArray = obtainByteArrayConversionDecimalService(
				"byteArrayConversionHexaDecimal").decimalStringConversionBytes(
				dataOfExtracted);
		byte[] characterArrayAfterDecryption = paramCipher
				.doFinal(characterArray);
		return new String(characterArrayAfterDecryption, "UTF8");
	}
}
