package com.howard.www.core.base.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.howard.www.core.base.dao.ISequence;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

/**
 * 
 * @author howard
 * 
 */
@Repository("baseSequence")
@Scope("prototype")
public class BaseSequenceImpl extends AbstractSqlImpl<ISequence> implements
		ISequence {

	public Long obtainValueOfSequence() {
		return null;
	}

	public ISequence evaluateSqlResource(String sqlResource) {
		this.sqlResource = sqlResource;
		return this;
	}

	public ISequence evaluateJsonNamedParameterJdbcTemplate(
			String jsonNamedParameterJdbcTemplate) throws Exception {
		this.jsonNamedParameterJdbcTemplate = jsonNamedParameterJdbcTemplate;
		return this;
	}

	public ISequence evaluetePrimitiveSqlResource(String primitiveSqlResource) {
		this.primitiveSqlResource = primitiveSqlResource;
		return this;
	}

	public ISequence evaluateIDataTransferObject(
			IDataTransferObject iDataTransferObject) {
		this.iDataTransferObject = iDataTransferObject;
		return this;
	}

	public ISequence evaluateSqlResourceKey(String sqlResourceKey)
			throws Exception {
		this.sqlResourceKey = sqlResourceKey;
		super.evaluateSqlResourceStatement(sqlResourceKey);
		return this;
	}

}
