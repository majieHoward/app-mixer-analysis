package com.howard.www.analysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisCarInfoManageDao;
import com.howard.www.analysis.entity.CarRealTimePositioning;
import com.howard.www.analysis.entity.CarRealTimePositioningItem;
import com.howard.www.analysis.service.IAnalysisCarInfoManageService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository("analysisCarInfoManageService")
public class AnalysisCarInfoManageServiceImpl implements IAnalysisCarInfoManageService {
	@Autowired
	private ApplicationContext cApplicationContext;

	public IAnalysisCarInfoManageDao obtainIAnalysisCarInfoManageDao() throws Exception {
		return (IAnalysisCarInfoManageDao) cApplicationContext.getBean("analysisCarInfoManageDao");
	}

	public JSONObject obtainRealTimePositionOfCar(IDataTransferObject queryParameters) throws Exception {
		JSONArray realTimePositionOfCarItems = obtainIAnalysisCarInfoManageDao()
				.obtainRealTimePositionOfCar(queryParameters);

		return null;
	}

	public JSONObject obtainCarRealTimePositioning(IDataTransferObject queryParameters) throws Exception {
		List<JSONObject> carRealTimePositioningItems = obtainIAnalysisCarInfoManageDao()
				.obtainCarRealTimePositioning(queryParameters);
		if (carRealTimePositioningItems != null && carRealTimePositioningItems.size() > 0) {
			return structureCarRealTimePositioning(carRealTimePositioningItems);
		}
		return null;
	}

	private JSONObject structureCarRealTimePositioning(List<JSONObject> carRealTimePositioningItems) throws Exception {
		if (carRealTimePositioningItems != null && carRealTimePositioningItems.size() > 0) {
			CarRealTimePositioningItem CarRealTimePositioningItem = new CarRealTimePositioningItem();
			CarRealTimePositioning carRealTimePositioning;
			for (JSONObject carRealTimePositioningItem : carRealTimePositioningItems) {
				carRealTimePositioning = new CarRealTimePositioning();
				carRealTimePositioning.initCarRealTimePositioning(carRealTimePositioningItem);
				CarRealTimePositioningItem.setCarRealTimePositioningItems(carRealTimePositioning);
			}
			return JSONObject.fromObject(CarRealTimePositioningItem);
		}
		return null;
	}

	/**
	 * 根据车的Id查询某辆车实时位置
	 */
	public JSONObject obtainRealTimePositionOfCarByCarId(IDataTransferObject queryParameters) throws Exception {
		List<String> carlist = new ArrayList<String>();
		String carId = FrameworkStringUtil.asString(queryParameters.obtainMapOfRequiredParameter().get("carId"));
		carlist.add(carId);
		queryParameters.evaluteRequiredParameter("carlist", carlist);
		return obtainCarRealTimePositioning(queryParameters);
	}

}
