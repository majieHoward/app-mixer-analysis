package com.howard.www.core.dao.support;

import javax.sql.DataSource;
/**
 * 
 * @author howard
 *
 */
public class JsonNamedParameterJdbcTemplate extends
		EntityNamedParameterJdbcTemplate {

	public JsonNamedParameterJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

}
