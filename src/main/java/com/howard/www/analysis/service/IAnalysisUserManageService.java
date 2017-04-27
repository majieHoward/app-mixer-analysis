package com.howard.www.analysis.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IAnalysisUserManageService {
	public JSONObject verifyUserLogin(IDataTransferObject queryParameters)
			throws Exception;

	public JSONArray obtainTheCurrentUserRoleItemsOfStation(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainTheCurrentUserPermissionOfStation(
			IDataTransferObject queryParameters) throws Exception;

	public JSONObject executeTheCurrentUserExit(
			IDataTransferObject queryParameters) throws Exception;

	public JSONArray obtainTheCurrentUserStationItem(
			IDataTransferObject queryParameters) throws Exception;
}
