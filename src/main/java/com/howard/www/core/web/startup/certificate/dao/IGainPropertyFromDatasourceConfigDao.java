package com.howard.www.core.web.startup.certificate.dao;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

public interface IGainPropertyFromDatasourceConfigDao {
	public JSONArray obtainAllTheAppInitparameters(
			IDataTransferObject queryParameters) throws Exception;
}
