package com.howard.www.analysis.service;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONObject;

public interface IAnalysisDataStatisticsService {
	public JSONObject obtainMonthlyTrafficVolumeByCarId(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainMonthlyTrafficVolumeByBssOrg(IDataTransferObject queryParameters) throws Exception;
}
