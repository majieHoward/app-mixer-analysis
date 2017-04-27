package com.howard.www.common.web.dao;

import net.sf.json.JSONArray;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface ILoadCodeTableDao {
    public JSONArray obtainCodeTableResultSet(IDataTransferObject dto)throws Exception;
}
