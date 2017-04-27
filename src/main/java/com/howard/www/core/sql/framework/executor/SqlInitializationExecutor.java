package com.howard.www.core.sql.framework.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.howard.www.core.sql.framework.executor.service.IQuerySqlStatementFormSource;
import com.howard.www.core.sql.framework.mapping.MappedStatement;
import com.howard.www.core.sql.framework.sqltemplate.Configuration;
import com.howard.www.core.sql.framework.sqltemplate.SqlMeta;
import com.howard.www.core.sql.framework.sqltemplate.SqlTemplate;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
public class SqlInitializationExecutor extends BaseSqlInitialization implements
		InitializingBean, DisposableBean {
	protected final Logger log = LoggerFactory
			.getLogger(SqlInitializationExecutor.class);
	/**
	 * configuration
	 */
	private Configuration configuration = new Configuration();
	/**
	 * querySqlStatementFormSource
	 */
	private IQuerySqlStatementFormSource querySqlStatementFormSource;
	/**
	 * sqlTableName
	 */
	private String sqlTableName;
	/**
	 * sqlSource
	 */
	private ConcurrentHashMap<String, MappedStatement> sqlSource = new ConcurrentHashMap<String, MappedStatement>();

	public IQuerySqlStatementFormSource getQuerySqlStatementFormSource() {
		return querySqlStatementFormSource;
	}

	public void setQuerySqlStatementFormSource(
			IQuerySqlStatementFormSource querySqlStatementFormSource) {
		this.querySqlStatementFormSource = querySqlStatementFormSource;
	}

	public ConcurrentHashMap<String, MappedStatement> getSqlSource() {
		return sqlSource;
	}

	public void setSqlSource(
			ConcurrentHashMap<String, MappedStatement> sqlSource) {
		this.sqlSource = sqlSource;
	}

	public String getSqlTableName() {
		if (this.sqlTableName == null || "".equals(sqlTableName)) {
			setSqlTableName("");
		}
		return sqlTableName;
	}

	public void setSqlTableName(String sqlTableName) {
		this.sqlTableName = sqlTableName;
	}

	/**
	 * query
	 */
	public void structureSql() throws Exception {
		log.info("initialization structure sql resource from \"{}\" table",
				sqlTableName);
		querySqlStatementFormSource.queryDefinitionSqlStatement(sqlTableName,
				sqlSource);
	}

	public String structureOneSql(String sqlId, String namespace)
			throws Exception {
		querySqlStatementFormSource.queryDefinitionSqlStatement(sqlId, sqlId,
				namespace, sqlSource);
		return null;
	}

	public String obtainASqlThroughKey(String sqlKey) throws Exception {
		MappedStatement paramMappedStatement = obtainAMappedStatementThroughKey(sqlKey);
		if (paramMappedStatement != null) {
			return paramMappedStatement.getResource();
		}
		return null;
	}

	public MappedStatement obtainAMappedStatementThroughKey(String sqlKey)
			throws Exception {
		log.info("obtain sqlKey equal {}", this.getClass().hashCode());
		return sqlSource.get(sqlKey);
	}

	public String updateASqlThroughNamespaceAndId(String sqlNamespace,
			String sqlId) throws Exception {
		querySqlStatementFormSource.replaceDefinitionSqlStatement(sqlTableName,
				sqlId, sqlNamespace, sqlSource);
		return null;
	}

	/**
     * 
     */
	public String obtainAsSqlPrototypeThroughkey(String sqlKey,
			Map<String, Object> parameter) throws Exception {
		String sqlStatement = FrameworkStringUtil.asString(this
				.obtainASqlThroughKey(sqlKey));
		if ("".equals(sqlStatement)) {
			return null;
		}
		return structureStatementThroughParam(sqlStatement, parameter);
	}

	/**
	 * 
	 * @param sqlStatement
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public String structureStatementThroughParam(String sqlStatement,
			Map<String, Object> parameter) throws Exception {
		/**
		 * templateOfSqlStatement
		 */
		SqlTemplate templateOfSqlStatement = configuration
				.getTemplate(sqlStatement);
		SqlMeta process = templateOfSqlStatement.process(parameter);

		if (process != null) {
			log.info("get this sql statement is \"{}\"", process.toString());
			return process.getSql();
		}
		return null;
	}

	public void destroy() throws Exception {

	}

	public void afterPropertiesSet() throws Exception {
		structureSql();
	}
}
