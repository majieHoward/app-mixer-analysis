package com.howard.www.common.web.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.howard.www.common.web.message.service.ICreateMessageResourceService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
public class MessageResource extends AbstractMessageSource implements
		ResourceLoaderAware {

	protected final Logger log = LoggerFactory.getLogger(MessageResource.class);

	@Autowired
	private ApplicationContext cApplicationContext;

	private ResourceLoader resourceLoader;

	/**
	 * Map
	 */
	protected final String MAP_SPLIT_CODE = "|";

	protected final String DB_SPLIT_CODE = "_";

	/**
	 * 当前代码中使用的是对象来保存码表中的值需要修改成从缓存服务器中查找
	 */
	private ConcurrentHashMap<String, String> properties;

	public ConcurrentHashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(ConcurrentHashMap<String, String> properties) {
		this.properties = null;
		this.properties = properties;
	}

	/**
	 * 
	 * @return
	 */
	protected void loadTexts() {

	}

	private String getText(String code, Locale locale) {
		String localeCode = locale.getLanguage() + DB_SPLIT_CODE
				+ locale.getCountry();
		String key = code + MAP_SPLIT_CODE + localeCode;
		String localeText = properties.get(key);
		String resourceText = code;
		if (localeText != null) {
			resourceText = localeText;
		} else {
			localeCode = Locale.ENGLISH.getLanguage();
			key = code + MAP_SPLIT_CODE + localeCode;
			localeText = properties.get(key);
			if (localeText != null) {
				resourceText = localeText;
			} else {
				try {
					if (getParentMessageSource() != null) {
						resourceText = getParentMessageSource().getMessage(
								code, null, locale);
					} else {
						resourceText = structureOneOfMessageElement(code, key);
					}
				} catch (Exception e) {
					logger.error("Cannot find message with code: " + code);
				}
			}
		}
		return resourceText;
	}

	private String structureOneOfMessageElement(String code, String key)
			throws Exception {
		IDataTransferObject queryParameters = new DataTransferObject();
		queryParameters.evaluteRequiredParameter("elementName", code);
		ICreateMessageResourceService messageResource = (ICreateMessageResourceService) cApplicationContext
				.getBean("createMessageResourceService");
		JSONObject messageElementObject = messageResource
				.obtainMessageResourceItem(queryParameters);
		if (messageElementObject != null) {
			String pageElementValue = FrameworkStringUtil
					.asString(messageElementObject.get("pageElementValue"));
			if (!"".equals(pageElementValue)) {
				properties.put(key, pageElementValue);
				return pageElementValue;
			}
		}
		return null;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = (resourceLoader != null ? resourceLoader
				: new DefaultResourceLoader());
	}

	protected MessageFormat resolveCode(String code, Locale locale) {
		String msg = getText(code, locale);
		MessageFormat result = createMessageFormat(msg, locale);
		return result;
	}

	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		String result = getText(code, locale);
		return result;
	}

	protected Properties getCommonMessages() {
		return super.getCommonMessages();
	}

	protected String getMessageInternal(String code, Object[] args,
			Locale locale) {
		return super.getMessageInternal(code, args, locale);
	}

	protected String getMessageFromParent(String code, Object[] args,
			Locale locale) {
		return super.getMessageFromParent(code, args, locale);
	}

	protected String getDefaultMessage(String code) {
		return super.getDefaultMessage(code);
	}

	protected String formatMessage(String msg, Object[] args, Locale locale) {
		return super.formatMessage(msg, args, locale);
	}

}
