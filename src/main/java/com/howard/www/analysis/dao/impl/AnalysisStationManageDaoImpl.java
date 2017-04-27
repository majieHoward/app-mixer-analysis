package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;

import com.howard.www.analysis.dao.IAnalysisStationManageDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

@Repository("analysisStationManageDao")
public class AnalysisStationManageDaoImpl extends BaseDaoImpl implements IAnalysisStationManageDao {

	@Override
	public JSONArray obtainStationLocationInformationDisplay(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.sqlname12")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
