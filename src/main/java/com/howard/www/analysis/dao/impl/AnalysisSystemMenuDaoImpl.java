package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisSystemMenuDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

@Repository("analysisSystemMenuDao")
public class AnalysisSystemMenuDaoImpl extends BaseDaoImpl implements IAnalysisSystemMenuDao {

	public JSONArray obtainSystemMenuItems(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.sqlMenu")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
