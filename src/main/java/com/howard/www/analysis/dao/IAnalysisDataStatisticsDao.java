package com.howard.www.analysis.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

public interface IAnalysisDataStatisticsDao {
	public JSONArray obtainMonthlyTrafficVolumeByCarId(IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainMonthlyTrafficVolumeByBssOrg(IDataTransferObject queryParameters) throws Exception;
}
