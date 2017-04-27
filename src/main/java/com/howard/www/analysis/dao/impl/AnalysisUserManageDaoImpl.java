package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.howard.www.analysis.dao.IAnalysisUserManageDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

@Repository("analysisUserManageDao")
public class AnalysisUserManageDaoImpl extends BaseDaoImpl implements
		IAnalysisUserManageDao {

	@Override
	public JSONObject obtainTheCurrentUserExists(
			IDataTransferObject queryParameters) throws Exception {
		// TODO Auto-generated method stub
		return obtainQuery()
				.evaluateSqlResourceKey("namespaceAnalysis.sqlname9")
				.evaluateIDataTransferObject(queryParameters).forJsonObject();
	}

	@Override
	public JSONArray obtainTheCurrentUserRoleItemOfStation(
			IDataTransferObject queryParameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray obtainTheCurrentUserPermissionOfStation(
			IDataTransferObject queryParameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
