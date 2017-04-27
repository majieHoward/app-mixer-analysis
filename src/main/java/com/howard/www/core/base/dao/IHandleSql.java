package com.howard.www.core.base.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

/**
 * 
 * @author howard
 * 
 * @param <T>
 */
public interface IHandleSql<T> {
	/**
	 * 
	 * @param sqlResource
	 * @return
	 */
	public T evaluateSqlResource(String sqlResource);

	/**
	 * 
	 * @param iDataTransferObject
	 * @return
	 */
	public T evaluateIDataTransferObject(IDataTransferObject iDataTransferObject);

	/**
	 * 
	 * @param sqlResourceKey
	 * @return
	 */
	public T evaluateSqlResourceKey(String sqlResourceKey) throws Exception;

	public T evaluetePrimitiveSqlResource(String primitiveSqlResource);

	public T evaluateJsonNamedParameterJdbcTemplate(
			String jsonNamedParameterJdbcTemplate) throws Exception;
}
