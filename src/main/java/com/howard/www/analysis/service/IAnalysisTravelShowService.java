package com.howard.www.analysis.service;

import net.sf.json.JSONObject;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IAnalysisTravelShowService {

	public JSONObject obtainTravelShowInfo(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainCurrentOperIdInformation(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainNextOperIdInformation(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainTravelBetweenAdjacentNodes(IDataTransferObject queryParameters) throws Exception;

	public JSONObject betweenTheTwoOperIdInformation(IDataTransferObject queryParameters) throws Exception;

	public byte[] obtainTravelShowInfoExcelFile(IDataTransferObject queryParameters)throws Exception;
}
