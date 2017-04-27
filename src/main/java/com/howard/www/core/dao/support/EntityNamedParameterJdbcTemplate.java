package com.howard.www.core.dao.support;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/**
 * 
 * @author howard
 *
 */
public abstract class EntityNamedParameterJdbcTemplate extends
		NamedParameterJdbcTemplate {

    private static final ThreadLocal<String> TARGET_DATA_SOURCE = new ThreadLocal<String>();  

	public EntityNamedParameterJdbcTemplate(DataSource dataSource) {
		super(dataSource);
		
	}

}
