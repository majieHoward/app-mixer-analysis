package com.howard.www.core.web.security.authorizing.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

public interface ISystemAuthorizingRealmDao {
	public JSONArray obtainAllPermissionsFromDB(IDataTransferObject queryParameters) throws Exception;
}
