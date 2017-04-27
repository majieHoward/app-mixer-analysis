package com.howard.www.analysis.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IAnalysisStationRelationshipDao {
	public JSONObject obtainAnalysisStationRelationshipInfo(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainAnalysisStationRelationshipID(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainRealtimeInformation(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainCarIdInformation(IDataTransferObject queryParameters)
			throws Exception;

}
