package com.howard.www.analysis.dao;

import net.sf.json.JSONArray;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IAnalysisRealTimeAcquisitionDao {
	public JSONArray obtainRealTimeLocationInfoMonitor(
			IDataTransferObject queryParameters) throws Exception;
}
