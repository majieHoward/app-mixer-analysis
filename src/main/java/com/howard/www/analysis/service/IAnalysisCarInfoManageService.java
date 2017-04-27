package com.howard.www.analysis.service;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONObject;

public interface IAnalysisCarInfoManageService {
	public JSONObject obtainRealTimePositionOfCar(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainCarRealTimePositioning(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainRealTimePositionOfCarByCarId(IDataTransferObject queryParameters) throws Exception;
}
