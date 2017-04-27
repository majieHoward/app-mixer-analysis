package com.howard.www.analysis.dao.impl;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;

import com.howard.www.analysis.dao.IAnalysisRealTimeAcquisitionDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

@Repository("analysisRealTimeAcquisitionDao")
public class AnalysisRealTimeAcquisitionDaoImpl extends BaseDaoImpl implements
		IAnalysisRealTimeAcquisitionDao {

	@Override
	public JSONArray obtainRealTimeLocationInfoMonitor(
			IDataTransferObject queryParameters) throws Exception {
		return null;
	}

}
