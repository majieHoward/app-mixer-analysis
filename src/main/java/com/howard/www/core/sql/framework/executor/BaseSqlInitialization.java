package com.howard.www.core.sql.framework.executor;

import java.util.Map;

public abstract class BaseSqlInitialization {
	public abstract void structureSql() throws Exception;

	public abstract String structureOneSql(String sqlId, String namespace)
			throws Exception;

	public abstract String obtainASqlThroughKey(String sqlKey) throws Exception;

	public abstract String updateASqlThroughNamespaceAndId(String sqlNamespace,
			String sqlId) throws Exception;

	public abstract String obtainAsSqlPrototypeThroughkey(String sqlKey,
			Map<String, Object> parameter) throws Exception;
	
	public abstract String structureStatementThroughParam(String sqlStatement,
			Map<String, Object> parameter) throws Exception;
}
