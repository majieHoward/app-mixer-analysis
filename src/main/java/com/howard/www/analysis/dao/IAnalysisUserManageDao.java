package com.howard.www.analysis.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IAnalysisUserManageDao {
	public JSONObject obtainTheCurrentUserExists(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainTheCurrentUserRoleItemOfStation(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainTheCurrentUserPermissionOfStation(
			IDataTransferObject queryParameters) throws Exception;

}
