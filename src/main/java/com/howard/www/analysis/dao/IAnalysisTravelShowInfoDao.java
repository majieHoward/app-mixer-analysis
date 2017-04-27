package com.howard.www.analysis.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IAnalysisTravelShowInfoDao {
	public JSONObject obtainTravelShowServiceInfo(IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainExhibitionInfo(IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainMarkPoints(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtaincurrentOperIdInformation(IDataTransferObject queryParameters) throws Exception;

	public JSONObject obtainNextOperIdInformation(IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainTravelBetweenAdjacentNodes(IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainExcelDataItems(IDataTransferObject queryParameters)throws Exception;
}
