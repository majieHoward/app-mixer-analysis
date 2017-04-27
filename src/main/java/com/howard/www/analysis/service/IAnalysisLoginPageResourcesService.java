package com.howard.www.analysis.service;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONObject;

public interface IAnalysisLoginPageResourcesService {
	public JSONObject structureAnalysisLoginPageSources(
			IDataTransferObject queryParameters) throws Exception;
	
	public String structureAnalysisLoginPage(
			IDataTransferObject queryParameters) throws Exception;
}
