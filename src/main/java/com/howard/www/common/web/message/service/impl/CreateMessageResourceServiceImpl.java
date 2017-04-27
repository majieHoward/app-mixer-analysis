package com.howard.www.common.web.message.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.howard.www.common.web.message.MessageResource;
import com.howard.www.common.web.message.dao.IMessageResourceFromDatasourceDao;
import com.howard.www.common.web.message.service.ICreateMessageResourceService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

public class CreateMessageResourceServiceImpl implements
		ICreateMessageResourceService, InitializingBean, DisposableBean {
	protected final Logger log = LoggerFactory
			.getLogger(CreateMessageResourceServiceImpl.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	private ConcurrentHashMap<String, String> properties = new ConcurrentHashMap<String, String>();

	protected final String MAP_SPLIT_CODE = "|";

	protected final String DB_SPLIT_CODE = "_";

	private String messageResourceTableName;

	public String getMessageResourceTableName() {
		return messageResourceTableName;
	}

	public void setMessageResourceTableName(String messageResourceTableName) {
		this.messageResourceTableName = messageResourceTableName;
	}

	private IMessageResourceFromDatasourceDao getIMessageResourceFromDatasourceDao(
			String messageResourceFromDatasourceBeanName) throws Exception {
		return (IMessageResourceFromDatasourceDao) cApplicationContext
				.getBean(messageResourceFromDatasourceBeanName);
	}

	private MessageResource getMessageResource(String messageResourceBeanName)
			throws Exception {
		return (MessageResource) cApplicationContext
				.getBean(messageResourceBeanName);
	}

	@SuppressWarnings("unchecked")
	public void evaluateMessageResourceItems() throws Exception {
		log.info("initialize all the page constant data elements");
		List<JSONObject> messageResourceItems = obtainMessageResourceItems(new DataTransferObject());
		if (messageResourceItems != null && messageResourceItems.size() > 0) {
			evaluateProperties(messageResourceItems);
			getMessageResource("messageSource").setProperties(properties);
		}
	}

	private void evaluateProperties(List<JSONObject> messageResourceItems)
			throws Exception {
		if (messageResourceItems != null && messageResourceItems.size() > 0) {
			int taskSize = 10;
			ExecutorService pool = Executors.newFixedThreadPool(taskSize);
			CountDownLatch latch = new CountDownLatch(
					messageResourceItems.size());
			for (JSONObject messageResourceItem : messageResourceItems) {
				StructureMessageResourceItem paramStructureMessageResourceItem = new StructureMessageResourceItem();
				paramStructureMessageResourceItem
						.initStructureMessageResourceItem(latch,
								messageResourceItem, properties, MAP_SPLIT_CODE);
				pool.submit(paramStructureMessageResourceItem);
			}
			latch.await();
			pool.shutdown();
		}
	}

	public void destroy() throws Exception {

	}

	public void afterPropertiesSet() throws Exception {
		evaluateMessageResourceItems();
	}

	public JSONArray obtainMessageResourceItems(IDataTransferObject paramDTO)
			throws Exception {
		if (!"".equals(FrameworkStringUtil
				.asString(this.messageResourceTableName))) {
			paramDTO.evaluteRequiredParameter("messageResourceTableName",
					messageResourceTableName);
			try {
				JSONArray messageResourceItems = getIMessageResourceFromDatasourceDao(
						"messageResourceFromDatasourceDao")
						.obtainMessageResourceResultSet(paramDTO);
				return messageResourceItems;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public JSONObject obtainMessageResourceItem(IDataTransferObject paramDTO)
			throws Exception {
		List<JSONObject> messageResourceItems = obtainMessageResourceItems(paramDTO);
		if (messageResourceItems.size() > 0) {
			return messageResourceItems.get(0);
		}
		return null;
	}

}
