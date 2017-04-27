package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;
import com.howard.www.analysis.dao.IAnalysisDataStatisticsDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

import net.sf.json.JSONArray;

@Repository("analysisDataStatisticsDaoImpl")
public class AnalysisDataStatisticsDaoImpl extends BaseDaoImpl implements IAnalysisDataStatisticsDao {

	public JSONArray obtainMonthlyTrafficVolumeByCarId(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.monthlyTrafficVolumeByCarId")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

	public JSONArray obtainMonthlyTrafficVolumeByBssOrg(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.monthlyTrafficVolumeByBssOrg")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
