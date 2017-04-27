package com.howard.www.core.base.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.howard.www.core.base.dao.IQuery;
import com.howard.www.core.dao.support.JsonNamedParameterJdbcTemplate;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 * 
 */
@Repository("baseQuery")
@Scope("prototype")
public class BaseQueryImpl extends AbstractSqlImpl<IQuery> implements IQuery {

	protected final Logger log = LoggerFactory.getLogger(BaseQueryImpl.class);

	public JSONArray forJsonArray() {

		try {
			obtainRunTimeSql(this.primitiveSqlResource);
		} catch (Exception e) {

		}
		ConcurrentHashMap<String, Object> mapOfRequiredParameter = obtainMapOfRequiredParameter();
		log.info(
				"SQL statement that performs the update operation is {} sql parameter is {}",
				this.sqlResource);
		JsonNamedParameterJdbcTemplate paramOfJsonNamedParameterJdbcTemplate = obtainJsonNamedParameterJdbcTemplate(this.jsonNamedParameterJdbcTemplate);
		if(!"".equals(FrameworkStringUtil.asString(this.sqlResource))){
			List<Map<String, Object>> queryResultSet = paramOfJsonNamedParameterJdbcTemplate
					.queryForList(this.sqlResource,
							super.obtainMapOfRequiredParameter());
			if (queryResultSet != null && queryResultSet.size() > 0) {
				// evaluateQueryResult(queryResultSet);
				return JSONArray.fromObject(queryResultSet);
			}
		}else{
			log.error("SQL statement is empty");
		}
		return null;
	}

	public JSONObject forJsonObject() {
		JSONArray queryResultSet = forJsonArray();
		if (queryResultSet != null && queryResultSet.size() > 0) {
			JSONObject itemOfQueryResult = queryResultSet.getJSONObject(0);
			// evaluateQueryResult(itemOfQueryResult);
			return itemOfQueryResult;
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void evaluateQueryResult(Object resultSet) {
		if (resultSet != null) {
			this.iDataTransferObject.evaluateResultSet(resultSet);
		}
	}

	public IQuery evaluateIDataTransferObject(
			IDataTransferObject iDataTransferObject) {
		this.iDataTransferObject = iDataTransferObject;
		return this;
	}

	public IQuery evaluateSqlResourceKey(String sqlResourceKey)
			throws Exception {
		this.sqlResourceKey = sqlResourceKey;
		super.evaluateSqlResourceStatement(sqlResourceKey);
		return this;
	}

	public IQuery evaluateSqlResource(String sqlResource) {
		this.sqlResource = sqlResource;
		return this;
	}

	public IQuery evaluateJsonNamedParameterJdbcTemplate(
			String jsonNamedParameterJdbcTemplate) throws Exception {
		this.jsonNamedParameterJdbcTemplate = jsonNamedParameterJdbcTemplate;
		return this;
	}

	public IQuery evaluetePrimitiveSqlResource(String primitiveSqlResource) {
		this.primitiveSqlResource = primitiveSqlResource;
		return this;
	}

	public int forCount() {
		int conutOfNum=0;
		try {
			obtainRunTimeSql(this.primitiveSqlResource);
		} catch (Exception e) {

		}
		ConcurrentHashMap<String, Object> mapOfRequiredParameter = obtainMapOfRequiredParameter();
		log.info(
				"SQL statement that performs the update operation is {} sql parameter is",
				this.sqlResource);
		JsonNamedParameterJdbcTemplate paramOfJsonNamedParameterJdbcTemplate = obtainJsonNamedParameterJdbcTemplate(this.jsonNamedParameterJdbcTemplate);
		if(!"".equals(FrameworkStringUtil.asString(this.sqlResource))){
			StringBuffer sqlSb=new StringBuffer();
			sqlSb.append("select count(*) from(").append(this.sqlResource).append(")");
			conutOfNum= paramOfJsonNamedParameterJdbcTemplate.queryForInt(sqlSb.toString(),super.obtainMapOfRequiredParameter());
	        sqlSb.setLength(0);
		}else{
			log.error("SQL statement is empty");
		}
		return conutOfNum;
	}

	public String obtainSqlSentence() {
		try {
			obtainRunTimeSql(this.primitiveSqlResource);
		} catch (Exception e) {

		}
		return this.sqlResource;
	}

	public JSONArray forJSONArrayPage(int startPage, int pageSize) {
		try {
			obtainRunTimeSql(this.primitiveSqlResource);
		} catch (Exception e) {

		}
		ConcurrentHashMap<String, Object> mapOfRequiredParameter = obtainMapOfRequiredParameter();
		log.info(
				"SQL statement that performs the update operation is {} sql parameter is",
				this.sqlResource);
		JsonNamedParameterJdbcTemplate paramOfJsonNamedParameterJdbcTemplate = obtainJsonNamedParameterJdbcTemplate(this.jsonNamedParameterJdbcTemplate);
		if(!"".equals(FrameworkStringUtil.asString(this.sqlResource))){
			StringBuffer pageStr = new StringBuffer();
			pageStr.append("select  " 
					+ " * from ( select row_limit.*, rownum rownum_ from (");
			pageStr.append(this.sqlResource);
			pageStr.append(" ) row_limit where rownum <= ");
			pageStr.append(startPage * pageSize);
			pageStr.append(" ) where rownum_ >");
			pageStr.append((startPage - 1) * pageSize);
			List<Map<String, Object>> queryResultSet = paramOfJsonNamedParameterJdbcTemplate
					.queryForList(pageStr.toString(),
							super.obtainMapOfRequiredParameter());
			if (queryResultSet != null && queryResultSet.size() > 0) {
				// evaluateQueryResult(queryResultSet);
				return JSONArray.fromObject(queryResultSet);
			}
		}else{
			log.error("SQL statement is empty");
		}
		return null;
	}
	
}
