package com.howard.www.core.sql.framework.executor.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.sql.framework.executor.dao.IQuerySqlStatementFormSourceFromDatabaseDao;
import com.howard.www.core.sql.framework.executor.service.IQuerySqlStatementFormSource;
import com.howard.www.core.sql.framework.mapping.MappedStatement;
import com.howard.www.core.sql.framework.mapping.service.impl.StructureMappedStatementNode;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
public class QuerySqlStatementFormSourceFromDatabase implements
		IQuerySqlStatementFormSource {
	protected final Logger log = LoggerFactory
			.getLogger(QuerySqlStatementFormSourceFromDatabase.class);

	@Autowired
	private ApplicationContext cApplicationContext;

	private IQuerySqlStatementFormSourceFromDatabaseDao obtainIQuerySqlStatementFormSourceFromDatabaseDao(
			String querySqlStatementFormSourceFromDatabaseDaoBeanName)
			throws Exception {
		return (IQuerySqlStatementFormSourceFromDatabaseDao) cApplicationContext
				.getBean(querySqlStatementFormSourceFromDatabaseDaoBeanName);
	}

	@SuppressWarnings("unchecked")
	public void queryDefinitionSqlStatement(String sqlTableName,
			ConcurrentHashMap<String, MappedStatement> sqlSource)
			throws Exception {
		if (!"".equals(FrameworkStringUtil.asString(sqlTableName))) {
			IDataTransferObject queryParameters = new DataTransferObject();
			queryParameters.evaluteRequiredParameter("sqlTableName",
					sqlTableName);
			List<JSONObject> sqlStatementItems = null;
			log.info(
					"Query the native SQL statement from the table that name is {}",
					sqlTableName);
			sqlStatementItems = obtainIQuerySqlStatementFormSourceFromDatabaseDao(
					"querySqlStatementFormSourceFromDatabaseDao")
					.obtainOriginalSqlStatementItems(queryParameters);
			log.info("Construct SQL statement object");
			this.structureMappermentObject(sqlStatementItems, sqlSource);
		}
	}

	public void queryDefinitionSqlStatement(String sqlTableName, String sqlId,
			String namespace,
			ConcurrentHashMap<String, MappedStatement> sqlSource)
			throws Exception {

	}

	public void replaceDefinitionSqlStatement(String sqlTableName,
			String sqlId, String namespace,
			ConcurrentHashMap<String, MappedStatement> sqlSource)
			throws Exception {

	}

	/**
	 * 
	 * @param sqlStatementItems
	 * @param sqlSource
	 * @throws Exception
	 */
	private void structureMappermentObject(List<JSONObject> sqlStatementItems,
			ConcurrentHashMap<String, MappedStatement> sqlSource)
			throws Exception {
		if (sqlStatementItems.size() > 0) {
			int taskSize = 20;
			ExecutorService pool = Executors.newFixedThreadPool(taskSize);
			CountDownLatch latch = new CountDownLatch(sqlStatementItems.size());
			for (JSONObject sqlStatementItem : sqlStatementItems) {
				StructureMappedStatementNode paramStructureMappedStatementNode = new StructureMappedStatementNode();
				paramStructureMappedStatementNode.initMappedStatementNode(
						latch, sqlStatementItem, sqlSource);
				pool.submit(paramStructureMappedStatementNode);
			}
			latch.await();
			pool.shutdown();
		}
	}
}
