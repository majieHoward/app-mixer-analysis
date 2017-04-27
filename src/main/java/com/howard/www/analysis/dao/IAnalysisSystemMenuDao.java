package com.howard.www.analysis.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

public interface IAnalysisSystemMenuDao {
	public JSONArray obtainSystemMenuItems(IDataTransferObject queryParameters) throws Exception;
}
