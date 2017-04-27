package com.howard.www.analysis.service;

import net.sf.json.JSONObject;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IAnalysisStationManageService {
	public JSONObject obtainStationLocationInformationDisplay(
			IDataTransferObject queryParameters) throws Exception;
}
