package com.howard.www.core.web.startup.certificate.service;

public interface IInitializeSystemStartUpConfigService {
	public void loadingBootConfigurationFile() throws Exception;

	public String obtainItemOfInitConfigParameter(String attributeName)
			throws Exception;
}
