package com.howard.www.analysis.service;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONObject;

public interface IAnalysisRealTimeAcquisitionService {
	public JSONObject obtainRealTimeLocationInfoMonitor(
			IDataTransferObject queryParameters) throws Exception;
}
