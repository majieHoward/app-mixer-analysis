package com.howard.www.core.web.security.authorizing.saltencryption;

public interface IFrameworkSaltEncryption {
	public String encryptionThroughAlgorithm(String originalText)
			throws Exception;
}
