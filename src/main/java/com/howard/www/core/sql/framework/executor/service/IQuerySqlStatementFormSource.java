package com.howard.www.core.sql.framework.executor.service;

import java.util.concurrent.ConcurrentHashMap;

import com.howard.www.core.sql.framework.mapping.MappedStatement;
/**
 * 
 * @author howard
 *
 */
public interface IQuerySqlStatementFormSource {
	public void queryDefinitionSqlStatement(String sqlTableName,
			ConcurrentHashMap<String, MappedStatement> sqlSource)
			throws Exception;

	public void queryDefinitionSqlStatement(String sqlTableName, String sqlId,
			String namespace,
			ConcurrentHashMap<String, MappedStatement> sqlSource)
			throws Exception;
	
	public void replaceDefinitionSqlStatement(String sqlTableName, String sqlId,
			String namespace,
			ConcurrentHashMap<String, MappedStatement> sqlSource)throws Exception;
}
