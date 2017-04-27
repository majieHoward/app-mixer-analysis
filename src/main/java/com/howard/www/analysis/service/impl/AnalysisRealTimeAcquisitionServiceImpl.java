package com.howard.www.analysis.service.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisRealTimeAcquisitionDao;
import com.howard.www.analysis.service.IAnalysisRealTimeAcquisitionService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

@Repository("analysisRealTimeAcquisitionService")
public class AnalysisRealTimeAcquisitionServiceImpl implements
		IAnalysisRealTimeAcquisitionService {

	@Autowired
	private ApplicationContext cApplicationContext;

	private IAnalysisRealTimeAcquisitionDao getIAnalysisRealTimeAcquisitionDao()
			throws Exception {
		return (IAnalysisRealTimeAcquisitionDao) cApplicationContext
				.getBean("analysisRealTimeAcquisitionDao");
	}

	public JSONObject obtainRealTimeLocationInfoMonitor(
			IDataTransferObject queryParameters) throws Exception {
		return null;
	}


}
