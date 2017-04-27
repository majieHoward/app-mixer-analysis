package com.howard.www.core.web.startup.certificate.service;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface ICalculationPublicPrivateKeyService {
	public void calculationPublicPrivateKey(String nameOfCertificatePath)
			throws Exception;

	public void evaluatePublicKey(PublicKey valueOfPublickey);

	public void evaluatePrivateKey(PrivateKey valueOfPrivateKey);

	public PublicKey obtainPublicKeyValue();

	public PrivateKey obtainPrivateKeyValue();

	public String obtainDecryptionViaPrivateKeyValue(String originalCipherText)
			throws Exception;
}
