package com.howard.www.analysis.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisStationManageDao;
import com.howard.www.analysis.entity.StationInfoEntity;
import com.howard.www.analysis.entity.StationLocationBean;
import com.howard.www.analysis.service.IAnalysisStationManageService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("analysisStationManageService")
public class AnalysisStationManageServiceImpl implements
		IAnalysisStationManageService {
	@Autowired
	private ApplicationContext cApplicationContext;

	private IAnalysisStationManageDao getIAnalysisStationManageDao()
			throws Exception {
		return (IAnalysisStationManageDao) cApplicationContext
				.getBean("analysisStationManageDao");
	}

	public JSONObject obtainStationLocationInformationDisplay(
			IDataTransferObject queryParameters) throws Exception {
		JSONArray itemsOfRealTimeAcquisition = getIAnalysisStationManageDao()
				.obtainStationLocationInformationDisplay(queryParameters);
		StationLocationBean stationLocationBean = new StationLocationBean();
		structureStationLocationBeanItem(itemsOfRealTimeAcquisition,
				stationLocationBean);
		return JSONObject.fromObject(stationLocationBean);
	}

	private void structureStationLocationBeanItem(
			JSONArray itemsOfRealTimeAcquisition,
			StationLocationBean stationLocationBean) {
		if (itemsOfRealTimeAcquisition != null
				&& itemsOfRealTimeAcquisition.size() > 0) {
			StationInfoEntity stationInfoEntity = null;
			for (int i = 0; i < itemsOfRealTimeAcquisition.size(); i++) {
				JSONObject itemsListOfRealTime = itemsOfRealTimeAcquisition
						.getJSONObject(i);
				stationInfoEntity = new StationInfoEntity();
				stationInfoEntity.setBssOrgId(FrameworkStringUtil
						.asString(itemsListOfRealTime.get("BSS_ORG_ID")));
				stationInfoEntity.setLatitude(FrameworkStringUtil
						.asString(itemsListOfRealTime.get("LONGITUDE")));
				stationInfoEntity.setLongitude(FrameworkStringUtil
						.asString(itemsListOfRealTime.get("LATITUDE")));
				stationInfoEntity.setBssOrgName(FrameworkStringUtil
						.asString(itemsListOfRealTime.get("BSS_ORG_NAME")));
				stationInfoEntity.setStreetOfLocation(FrameworkStringUtil
						.asString(itemsListOfRealTime.get("ADDR")));
				stationLocationBean.setStationLocationBean(stationInfoEntity);
			}
		}

	}
}
