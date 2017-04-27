package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;

import com.howard.www.analysis.dao.IAnalysisCarInfoManageDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

@Repository("analysisCarInfoManageDao")
public class AnalysisCarInfoManageDaoImpl extends BaseDaoImpl implements IAnalysisCarInfoManageDao {
	public JSONArray obtainRealTimePositionOfCar(IDataTransferObject queryParameters) throws Exception {
		return null;
	}
    
	/**
	 * 查询车辆的实时位置
	 */
	public JSONArray obtainCarRealTimePositioning(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.carRealTimePositioning")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
