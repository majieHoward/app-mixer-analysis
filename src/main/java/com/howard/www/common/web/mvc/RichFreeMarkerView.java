package com.howard.www.common.web.mvc;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import com.howard.www.core.web.util.FrameworkStringUtil;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author majie
 * 
 *         Extended FreemarkerView spring, plus base attributes
 * 
 *         Support JSP tags, Application, Session, Request, RequestParameters
 *         properties
 */
public class RichFreeMarkerView extends FreeMarkerView {
	protected final Logger log = LoggerFactory
			.getLogger(RichFreeMarkerView.class);
	/**
	 * Deployment path attribute name
	 */
	public static final String SCHEMA = "schema";
	public static final String SERVER_NAME = "serverName";
	public static final String SERVER_PORT = "serverPort";
	public static final String CONTEXT_PATH = "contextPath";

	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		model.put(SCHEMA, request.getScheme());
		log.info("Deployment attribute name is {} value is {}", CONTEXT_PATH,
				request.getScheme());
		model.put(SERVER_NAME, request.getServerName());
		log.info("Deployment attribute name is {} value is {}", SERVER_NAME,
				request.getServerName());
		model.put(SERVER_PORT, request.getServerPort());
		log.info("Deployment attribute name is {} value is {}", SERVER_PORT,
				request.getServerPort());
		model.put(CONTEXT_PATH, request.getContextPath());
		log.info("Deployment attribute name is {} value is {}", CONTEXT_PATH,
				request.getContextPath());
		super.exposeHelpers(model, request);

	}

	protected void doRender(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.doRender(model, request, response);
		/**
		 * processTemplate(getTemplate(locale), fmModel, response); from The
		 * parent class FreeMarkerView
		 */
	}

	/**
	 * Override the parent class method processTemplate,method of doRender
	 * Calling method of processTemplate
	 */
	protected void processTemplate(Template template, SimpleHash model,
			HttpServletResponse response) throws IOException, TemplateException {
		Object paramOfUsingStringTemplates = model.toMap().get(
				"usingStringTemplates");
		if (true == whetherUsingStringTemplates(paramOfUsingStringTemplates)) {
			List<String> keyOfUsingStringTemplates = obtainKeyOfUsingStringTemplates(model);
			if (keyOfUsingStringTemplates != null
					&& keyOfUsingStringTemplates.size() > 0) {
				ConcurrentHashMap<String, Object> pageDisplayParameters = new ConcurrentHashMap<String, Object>();
				putParamToNewObject(model.toMap(), pageDisplayParameters);
				int taskSize = 3;
				ExecutorService pool = Executors.newFixedThreadPool(taskSize);
				CountDownLatch latch = new CountDownLatch(
						keyOfUsingStringTemplates.size());
				for (String keyOfUsingStringTemplate : keyOfUsingStringTemplates) {
					StructureOneOfStringTemplate structureOneOfStringTemplate = new StructureOneOfStringTemplate();
					structureOneOfStringTemplate.initStringTemplate(latch,
							keyOfUsingStringTemplate, pageDisplayParameters,
							template);
					pool.submit(structureOneOfStringTemplate);
				}
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pool.shutdown();
				model.putAll(pageDisplayParameters);
				template.process(model, response.getWriter());
			}
		} else {
			template.process(model, response.getWriter());
		}
	}

	@SuppressWarnings("rawtypes")
	private void putParamToNewObject(Map m,
			ConcurrentHashMap<String, Object> pageDisplayParameters) {
		for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			pageDisplayParameters
					.put((String) entry.getKey(), entry.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	private List<String> obtainKeyOfUsingStringTemplates(SimpleHash model)
			throws TemplateException {
		Object keyOfUsingStringTemplates = model.toMap().get(
				"keyOfUsingStringTemplates");
		if (keyOfUsingStringTemplates != null) {
			return (List<String>) keyOfUsingStringTemplates;
		}
		return null;
	}

	/**
	 * To determine whether the template needs to be carried out
	 * 
	 * @param usingStringTemplates
	 * @return
	 */
	private boolean whetherUsingStringTemplates(Object usingStringTemplates) {
		if ("true".equals(FrameworkStringUtil.asString(usingStringTemplates))) {
			return true;
		}
		return false;
	}
}
