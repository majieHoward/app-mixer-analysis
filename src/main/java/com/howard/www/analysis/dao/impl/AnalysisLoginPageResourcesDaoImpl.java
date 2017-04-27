package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;

import com.howard.www.analysis.dao.IAnalysisLoginPageResourcesDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
@Repository("analysisLoginPageResourcesDao")
public class AnalysisLoginPageResourcesDaoImpl extends BaseDaoImpl implements
		IAnalysisLoginPageResourcesDao {

	public JSONObject obtainAnalysisLoginPageResourcesFromDB(
			IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespace.sqlname5")
				.evaluateIDataTransferObject(queryParameters).forJsonObject();
	}

}
