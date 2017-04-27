package com.howard.www.core.base.dao.impl;

import java.util.concurrent.ConcurrentHashMap;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.howard.www.core.base.dao.IDelete;
import com.howard.www.core.dao.support.JsonNamedParameterJdbcTemplate;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
/**
 * 
 * @author howard
 *
 */
@Repository("baseDelete")
@Scope("prototype")
public class BaseDeleteImpl extends AbstractSqlImpl<IDelete> implements IDelete {
	protected final Logger log = LoggerFactory.getLogger(BaseDeleteImpl.class);

	public IDelete evaluateSqlResource(String sqlResource) {
		this.sqlResource = sqlResource;
		return this;
	}

	public IDelete evaluateJsonNamedParameterJdbcTemplate(
			String jsonNamedParameterJdbcTemplate) throws Exception {
		this.jsonNamedParameterJdbcTemplate = jsonNamedParameterJdbcTemplate;
		return this;
	}

	public IDelete evaluetePrimitiveSqlResource(String primitiveSqlResource) {
		this.primitiveSqlResource = primitiveSqlResource;
		return this;
	}

	public IDelete evaluateIDataTransferObject(
			IDataTransferObject iDataTransferObject) {
		this.iDataTransferObject = iDataTransferObject;
		return this;
	}

	public IDelete evaluateSqlResourceKey(String sqlResourceKey)
			throws Exception {
		this.sqlResourceKey = sqlResourceKey;
		super.evaluateSqlResourceStatement(sqlResourceKey);
		return this;
	}

	/**
	 * remove a record in the table
	 */
	public int removeRecord() {
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
