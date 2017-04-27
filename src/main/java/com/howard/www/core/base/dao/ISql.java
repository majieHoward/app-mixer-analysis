package com.howard.www.core.base.dao;

import java.util.concurrent.ConcurrentHashMap;

public interface ISql {
	/**
	 * 
	 * @param dataTransferObject
	 * @return
	 */
	public ConcurrentHashMap<String, Object> obtainMapOfRequiredParameter();

	/**
	 * 
	 * @param sqlResourceKey
	 * @return
	 */
	public void evaluateSqlResourceStatement(String sqlResourceKey)
			throws Exception;

}