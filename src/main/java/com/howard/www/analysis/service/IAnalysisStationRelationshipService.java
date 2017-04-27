package com.howard.www.analysis.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IAnalysisStationRelationshipService {

	public JSONObject obtainAnalysisStationRelationship(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainEffectiveStationRelationship(
			IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainEffectiveStationRelationshipItems(
			IDataTransferObject queryParameters) throws Exception;


	public JSONObject obtainaAnalysisRealtimeInformation(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainCarIdInformation(IDataTransferObject queryParameters)
			throws Exception;
}
