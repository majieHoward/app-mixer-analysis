package com.howard.www.core.web.security.service;

public interface IModifyTheStringService {
	public String compressionOriginalString(String originalString);

	public String decompressionOriginalString(String originalString)
			throws Exception;
}
