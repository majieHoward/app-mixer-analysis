package com.howard.www.common.web.message.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

public interface IMessageResourceFromDatasourceDao {
	public JSONArray obtainMessageResourceResultSet(IDataTransferObject queryParameters)
			throws Exception;
}
