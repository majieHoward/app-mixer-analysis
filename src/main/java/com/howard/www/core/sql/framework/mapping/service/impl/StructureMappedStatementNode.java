package com.howard.www.core.sql.framework.mapping.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;
import com.howard.www.core.sql.framework.mapping.MappedStatement;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * Construct database SQL statement object
 * 
 * @author howard
 * 
 */
public class StructureMappedStatementNode extends Thread {
	protected final Logger log = LoggerFactory
			.getLogger(StructureMappedStatementNode.class);
	/**
	 * latch
	 */
	private CountDownLatch latch;
	/**
	 * valueOfMappedStatement
	 */
	private JSONObject valueOfMappedStatement;
	/**
	 * sqlSourceMap
	 */
	private ConcurrentHashMap<String, MappedStatement> sqlSourceMap;

	public StructureMappedStatementNode() {

	}

	public StructureMappedStatementNode(CountDownLatch latch,
			JSONObject valueOfMappedStatement,
			ConcurrentHashMap<String, MappedStatement> sqlSourceMap) {
		this.initMappedStatementNode(latch, valueOfMappedStatement,
				sqlSourceMap);
	}

	public void initMappedStatementNode(CountDownLatch latch,
			JSONObject valueOfMappedStatement,
			ConcurrentHashMap<String, MappedStatement> sqlSourceMap) {
		this.latch = latch;
		this.valueOfMappedStatement = valueOfMappedStatement;
		this.sqlSourceMap = sqlSourceMap;
	}

	private MappedStatement initMappedStatementObject() throws Exception {
		if (valueOfMappedStatement == null) {
			return null;
		}
		/**
		 * paramMappedStatement
		 */
		MappedStatement paramMappedStatement = new MappedStatement(
				this.valueOfMappedStatement);
		return paramMappedStatement;
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void structMappedStatementObject() throws Exception {
		/**
		 * paramMappedStatement
		 */
		MappedStatement paramMappedStatement = initMappedStatementObject();
		if (paramMappedStatement != null) {
			/**
			 * 
			 */
			String sqlStatementKey = FrameworkStringUtil
					.asString(paramMappedStatement.getResourceKey());
			if (!"".equals(sqlStatementKey)) {
				sqlSourceMap.put(sqlStatementKey, paramMappedStatement);
				log.info(
						"initialization one of sqlStatement key is \"{}\" value is \"{}\"",
						sqlStatementKey, paramMappedStatement);
			}
		}
	}

	public void run() {
		if (valueOfMappedStatement != null) {
			try {
				structMappedStatementObject();
			} catch (Exception e) {

			}
		}
		latch.countDown();
	}

}
