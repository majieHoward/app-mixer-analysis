package com.howard.www.core.sql.framework.executor.dao.impl;

import org.springframework.stereotype.Repository;
import net.sf.json.JSONArray;

import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.sql.framework.executor.dao.IQuerySqlStatementFormSourceFromDatabaseDao;

/**
 * 
 * @author howard
 * 
 */
@Repository("querySqlStatementFormSourceFromDatabaseDao")
public class QuerySqlStatementFormSourceFromDatabaseDaoImpl extends BaseDaoImpl
		implements IQuerySqlStatementFormSourceFromDatabaseDao {

	public JSONArray obtainOriginalSqlStatementItems(
			IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluetePrimitiveSqlResource("select * from ${sqlTableName}")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
