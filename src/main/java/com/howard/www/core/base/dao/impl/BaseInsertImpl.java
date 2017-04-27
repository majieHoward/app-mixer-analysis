package com.howard.www.core.base.dao.impl;

import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.howard.www.core.base.dao.IInsert;
import com.howard.www.core.dao.support.JsonNamedParameterJdbcTemplate;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
/**
 * 
 * @author howard
 *
 */
@Repository("baseInsert")
@Scope("prototype")
public class BaseInsertImpl extends AbstractSqlImpl<IInsert> implements IInsert {

	public IInsert evaluateSqlResource(String sqlResource) {
		this.sqlResource = sqlResource;
		return this;
	}

	public IInsert evaluateJsonNamedParameterJdbcTemplate(
			String jsonNamedParameterJdbcTemplate) throws Exception {
		this.jsonNamedParameterJdbcTemplate = jsonNamedParameterJdbcTemplate;
		return this;
	}

	public IInsert evaluetePrimitiveSqlResource(String primitiveSqlResource) {
		this.primitiveSqlResource = primitiveSqlResource;
		return this;
	}

	public IInsert evaluateIDataTransferObject(
			IDataTransferObject iDataTransferObject) {
		this.iDataTransferObject = iDataTransferObject;
		return this;
	}

	public IInsert evaluateSqlResourceKey(String sqlResourceKey)
			throws Exception {
		this.sqlResourceKey = sqlResourceKey;
		super.evaluateSqlResourceStatement(sqlResourceKey);
		return this;
	}

	public int appendRecord() {
		try {
			obtainRunTimeSql(this.primitiveSqlResource);
		} catch (Exception e) {

		}
		ConcurrentHashMap<String, Object> mapOfRequiredParameter = obtainMapOfRequiredParameter();
		log.info(
				"SQL statement that performs the update operation is {} sql parameter is {}",
				this.sqlResource, JSONArray.fromObject(mapOfRequiredParameter));
		JsonNamedParameterJdbcTemplate paramOfJsonNamedParameterJdbcTemplate = obtainJsonNamedParameterJdbcTemplate(this.jsonNamedParameterJdbcTemplate);
		int numberOfAffectedRows = paramOfJsonNamedParameterJdbcTemplate
				.update(this.sqlResource, mapOfRequiredParameter);
		log.info("perform update operation number of rows affected is {}",
				numberOfAffectedRows);
		return numberOfAffectedRows;
	}

}
