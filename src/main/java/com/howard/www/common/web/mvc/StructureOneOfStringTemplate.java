package com.howard.www.common.web.mvc;

import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.howard.www.core.web.util.FrameworkStringUtil;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class StructureOneOfStringTemplate extends Thread {
	protected final Logger log = LoggerFactory
			.getLogger(StructureOneOfStringTemplate.class);
	/**
	 * latch
	 */
	private CountDownLatch latch;

	private String keyOfUsingStringTemplate;

	private ConcurrentHashMap<String, Object> model;

	private Template template;

	public StructureOneOfStringTemplate() {

	}

	public StructureOneOfStringTemplate(CountDownLatch latch,
			String keyOfUsingStringTemplate,
			ConcurrentHashMap<String, Object> model, Template template) {
		initStringTemplate(latch, keyOfUsingStringTemplate, model, template);
	}

	public void initStringTemplate(CountDownLatch latch,
			String keyOfUsingStringTemplate,
			ConcurrentHashMap<String, Object> model, Template template) {
		this.latch = latch;
		this.keyOfUsingStringTemplate = keyOfUsingStringTemplate;
		this.model = model;
		this.template = template;
	}

	private void structureConfiguration(Object valueOfUsingStringTemplate,
			Configuration paramOfConfig) throws Exception {
		StringTemplateLoader stringTemplateLoader;
		stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate("paramOfStringTemplate",
				FrameworkStringUtil.asString(valueOfUsingStringTemplate));
		TemplateLoader[] loaders = new TemplateLoader[] { stringTemplateLoader,
				paramOfConfig.getTemplateLoader() };
		MultiTemplateLoader multimedia = new MultiTemplateLoader(loaders);
		paramOfConfig.setTemplateLoader(multimedia);
	}

	private void analyzeStringTemplate() throws Exception {
		StringWriter paramOfWriter = new StringWriter();
		Object valueOfUsingStringTemplate = model
				.get(this.keyOfUsingStringTemplate);
		if (valueOfUsingStringTemplate != null) {
			Configuration paramOfConfig = (Configuration) template
					.getConfiguration().clone();
			structureConfiguration(valueOfUsingStringTemplate, paramOfConfig);
			Template paramTemplate = paramOfConfig.getTemplate(
					"paramOfStringTemplate", "utf-8");
			paramTemplate.process(model, paramOfWriter);
			if (!"".equals(FrameworkStringUtil.asString(paramOfWriter.toString()))) {
				model.put(this.keyOfUsingStringTemplate,
						paramOfWriter.toString());
			}
			paramOfConfig = null;
		}
	}

	public void run() {
		try {
			analyzeStringTemplate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		latch.countDown();
	}

}
