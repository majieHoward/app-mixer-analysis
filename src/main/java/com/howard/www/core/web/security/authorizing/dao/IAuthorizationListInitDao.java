package com.howard.www.core.web.security.authorizing.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

public interface IAuthorizationListInitDao {
	public JSONArray obtainAllAuthorizationListFromDB(IDataTransferObject queryParameters) throws Exception;
}
