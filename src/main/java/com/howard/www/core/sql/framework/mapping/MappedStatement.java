package com.howard.www.core.sql.framework.mapping;

import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

/**
 * JAVABEAN
 * 
 * @author howard
 * 
 */
public class MappedStatement {
	/**
	 * Sql id
	 */
	private String id;

	private boolean useCache;

	private String namespace;

	private String resourceKey;
	/**
	 * Sql
	 */
	private String resource;

	/**
	 * 
	 */
	private String jsonNamedParameterJdbcTemplate;

	public MappedStatement(JSONObject mappedStatementObject) {
		if (mappedStatementObject != null) {
			String sqlId = FrameworkStringUtil.asString(mappedStatementObject
					.get("sqlId"));
			String sqlNamespace = FrameworkStringUtil
					.asString(mappedStatementObject.get("sqlNamespace"));
			String sqlResource = FrameworkStringUtil
					.asString(mappedStatementObject.get("sqlResource"));
			String jsonNamedParameterJdbcTemplate = FrameworkStringUtil
					.asString(mappedStatementObject
							.get("jsonNamedParameterJdbcTemplate"));
			initMappedStatement(sqlId, false, sqlNamespace, sqlResource,
					jsonNamedParameterJdbcTemplate);
		}
	}

	public MappedStatement(String id, boolean useCache, String namespace,
			String resource, String jsonNamedParameterJdbcTemplate) {
		initMappedStatement(id, useCache, namespace, resource,
				jsonNamedParameterJdbcTemplate);
	}

	private void initMappedStatement(String id, boolean useCache,
			String namespace, String resource,
			String jsonNamedParameterJdbcTemplate) {
		this.id = id;
		this.useCache = useCache;
		this.namespace = namespace;
		this.resource = resource;
		this.jsonNamedParameterJdbcTemplate = jsonNamedParameterJdbcTemplate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getResourceKey() {
		if (this.resourceKey == null || "".equals(this.resourceKey)) {
			StringBuffer keyOfResource = new StringBuffer();
			keyOfResource.append(this.getNamespace()).append(".")
					.append(this.getId());
			this.setResourceKey(keyOfResource.toString());
			keyOfResource.setLength(0);
		}
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getJsonNamedParameterJdbcTemplate() {
		return jsonNamedParameterJdbcTemplate;
	}

	public void setJsonNamedParameterJdbcTemplate(
			String jsonNamedParameterJdbcTemplate) {
		this.jsonNamedParameterJdbcTemplate = jsonNamedParameterJdbcTemplate;
	}

}
