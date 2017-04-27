package com.howard.www.analysis.dao;

import net.sf.json.JSONArray;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IAnalysisCarInfoManageDao {
	public JSONArray obtainRealTimePositionOfCar(IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainCarRealTimePositioning(IDataTransferObject queryParameters) throws Exception;
}
