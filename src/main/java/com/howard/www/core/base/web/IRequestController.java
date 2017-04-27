package com.howard.www.core.base.web;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ui.ModelMap;

public interface IRequestController {
	public ConcurrentHashMap<String, Object> obtainPageDisplayParameters();

	public void evaluatePageDisplayParameter(String keyOfPageDisplayParameter,
			Object valueOfPageDisplayParameter);

	public void evaluateTemplateParameter(ModelMap model,
			String keyOfPageDisplayParameter, Object valueOfPageDisplayParameter);

	public void structurePageTemplate(ModelMap model,
			String keyOfUsingStringTemplate, Object valueOfUsingStringTemplate);

	public void loadTemplateString(ModelMap model, boolean usingStringTemplates);
}
