package com.howard.www.core.web.startup.certificate.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public abstract class PropertyFromConfig {
	public abstract List<String> obtainList(String prefix);

	public abstract Set<String> obtainSet(String prefix);

	public abstract Map<String, String> obtainMap(String prefix);

	public abstract Properties obtainProperties(String prefix);

	public abstract String obtainPropertiesString(String prefix);

	public abstract Map<String, Object> obtainBeanMap(String prefix);

	public abstract void initPropertyFromResources() throws Exception;
	
}
