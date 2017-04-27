package com.howard.www.common.web.dao.impl;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Repository;

import com.howard.www.common.web.dao.ILoadCodeTableDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

@Repository("loadCodeTableDao")
public class LoadCodeTableDaoImpl extends BaseDaoImpl implements
		ILoadCodeTableDao {

	public JSONArray obtainCodeTableResultSet(IDataTransferObject dto)
			throws Exception {
		JSONArray itemsOfChannel=obtainQuery().evaluateSqlResourceKey("namespace.sqlname3")
				.evaluateIDataTransferObject(dto).forJsonArray();
		System.out.println(JSONArray.fromObject(itemsOfChannel));
		obtainUpdate().evaluateSqlResourceKey("namespace.sqlname4")
				.evaluateIDataTransferObject(dto).modifyRecord();
		return null;
	}

}
