package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.howard.www.analysis.dao.IAnalysisStationRelationshipDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

@Repository("analysisStationRelationshipDao")
public class AnalysisStationRelationshipDaoImpl extends BaseDaoImpl implements
		IAnalysisStationRelationshipDao {

	@Override
	public JSONObject obtainAnalysisStationRelationshipInfo(
			IDataTransferObject queryParameters) throws Exception {
		queryParameters.obtainRequestParamsMap().put("paramSqlNameOfValue", "namespaceAnalysis.sqlname21");
		return qryJqGridTable(queryParameters);
	}

	@Override
	public JSONArray obtainAnalysisStationRelationshipID(
			IDataTransferObject queryParameters) throws Exception {
		return obtainQuery()
				.evaluateSqlResourceKey("namespaceAnalysis.sqlname13")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

	@Override
	public JSONArray obtainRealtimeInformation(
			IDataTransferObject queryParameters) throws Exception {
		return obtainQuery()
				.evaluateSqlResourceKey("namespaceAnalysis.sqlname16")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

	@Override
	public JSONArray obtainCarIdInformation(IDataTransferObject queryParameters)
			throws Exception {
		return obtainQuery()
				.evaluateSqlResourceKey("namespaceAnalysis.sqlname15")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}
}
