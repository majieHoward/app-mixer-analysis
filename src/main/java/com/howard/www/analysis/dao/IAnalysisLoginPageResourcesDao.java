package com.howard.www.analysis.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONObject;

public interface IAnalysisLoginPageResourcesDao {
	public JSONObject obtainAnalysisLoginPageResourcesFromDB(
			IDataTransferObject queryParameters) throws Exception;
}
