package com.howard.www.common.web.message.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface ICreateMessageResourceService {
	public void evaluateMessageResourceItems() throws Exception;

	public JSONArray obtainMessageResourceItems(IDataTransferObject paramDTO)
			throws Exception;
	
	public JSONObject obtainMessageResourceItem(IDataTransferObject paramDTO)
			throws Exception;

}
