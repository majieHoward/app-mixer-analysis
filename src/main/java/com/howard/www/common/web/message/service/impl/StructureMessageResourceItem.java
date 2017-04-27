package com.howard.www.common.web.message.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
public class StructureMessageResourceItem extends Thread {
	protected final Logger log = LoggerFactory
			.getLogger(StructureMessageResourceItem.class);
	/**
	 * latch
	 */
	private CountDownLatch latch;
	/**
	 * valueOfMappedStatement
	 */
	private JSONObject valueOfMessageResourceItem;

	private ConcurrentHashMap<String, String> propertiesOfMessageResource;

	private String mapSplitCode;

	public StructureMessageResourceItem() {

	}

	public StructureMessageResourceItem(CountDownLatch latch,
			JSONObject valueOfMessageResourceItem,
			ConcurrentHashMap<String, String> propertiesOfMessageResource,
			String mapSplitCode) {
		initStructureMessageResourceItem(latch, valueOfMessageResourceItem,
				propertiesOfMessageResource, mapSplitCode);
	}

	public void initStructureMessageResourceItem(CountDownLatch latch,
			JSONObject valueOfMessageResourceItem,
			ConcurrentHashMap<String, String> propertiesOfMessageResource,
			String mapSplitCode) {
		this.latch = latch;
		this.valueOfMessageResourceItem = valueOfMessageResourceItem;
		this.propertiesOfMessageResource = propertiesOfMessageResource;
		this.mapSplitCode = mapSplitCode;
	}

	private void structMessageResourceObject() throws Exception {
		if (valueOfMessageResourceItem != null) {
			String pageElementName = FrameworkStringUtil
					.asString(this.valueOfMessageResourceItem
							.get("pageElementName"));
			String pageElementValue = FrameworkStringUtil
					.asString(this.valueOfMessageResourceItem
							.get("pageElementValue"));
			String pageElementLocalEcode = FrameworkStringUtil
					.asString(this.valueOfMessageResourceItem
							.get("pageElementLocalEcode"));
			if (!"".equals(pageElementName) && !"".equals(pageElementValue)) {
				if ("".equals(pageElementLocalEcode)) {
					pageElementLocalEcode = "zh_CN";
				}
				StringBuffer paramOfPageElementName = new StringBuffer();
				paramOfPageElementName.append(pageElementName)
						.append(this.mapSplitCode)
						.append(pageElementLocalEcode);
				if (!"".equals(FrameworkStringUtil
						.asString(paramOfPageElementName.toString()))) {
					this.propertiesOfMessageResource.put(FrameworkStringUtil
							.asString(paramOfPageElementName.toString()),
							pageElementValue);
					log.info(
							"initialize one of the page constant data element name is {} value is {}",
							FrameworkStringUtil.asString(paramOfPageElementName
									.toString()), pageElementValue);
				}

				paramOfPageElementName.setLength(0);
			}
		}
	}

	public void run() {
		if (this.propertiesOfMessageResource != null) {
			try {
				structMessageResourceObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		latch.countDown();
	}

}
