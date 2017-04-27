package com.howard.www.core.web.security.authorizing.dao;

import net.sf.json.JSONArray;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IChainDefinitionSectionMetaSourceDao {
	public JSONArray obtainAllMenuItemResourcesFromDB(
			IDataTransferObject queryParameters) throws Exception;
}
