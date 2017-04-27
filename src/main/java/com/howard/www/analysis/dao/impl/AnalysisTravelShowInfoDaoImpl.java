package com.howard.www.analysis.dao.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisTravelShowInfoDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("analysisTravelShowInfoDaoImpl")
public class AnalysisTravelShowInfoDaoImpl extends BaseDaoImpl implements IAnalysisTravelShowInfoDao {

	public JSONObject obtainTravelShowServiceInfo(IDataTransferObject queryParameters) throws Exception {
		queryParameters.obtainRequestParamsMap().put("paramSqlNameOfValue", "namespaceAnalysis.historicalJourney");
		return qryJqGridTable(queryParameters);
	}

	public JSONArray obtainExhibitionInfo(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.sqlname18")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

	public JSONArray obtainMarkPoints(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.sqlname19")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

	public JSONObject obtaincurrentOperIdInformation(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.historicalJourney")
				.evaluateIDataTransferObject(queryParameters).forJsonObject();
	}

	public JSONObject obtainNextOperIdInformation(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.nextOperIdInformation")
				.evaluateIDataTransferObject(queryParameters).forJsonObject();
	}

	public JSONArray obtainTravelBetweenAdjacentNodes(IDataTransferObject queryParameters) throws Exception {
		
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.travelBetweenAdjacent")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

	public JSONArray obtainExcelDataItems(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateJsonNamedParameterJdbcTemplate("analysisNamedParameterJdbcTemplate").evaluateSqlResource(FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("exportSql"))).forJsonArray();
	}



}
