package com.howard.www.analysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisStationRelationshipDao;
import com.howard.www.analysis.entity.CarRealtimeInformation;
import com.howard.www.analysis.entity.CarRealtimeInformationEntity;
import com.howard.www.analysis.entity.StationRelationshipBean;
import com.howard.www.analysis.entity.StationRelationshipBeanOfMobile;
import com.howard.www.analysis.entity.StationRelationshipEntity;
import com.howard.www.analysis.service.IAnalysisStationRelationshipService;
import com.howard.www.analysis.util.AnalysisCoordinateTransformationUtil;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("analysisStationRelationshipService")
public class AnalysisStationRelationshipServiceImpl implements IAnalysisStationRelationshipService {
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 查询公司所包含的所有的车辆
	 */
	public JSONObject obtainAnalysisStationRelationship(IDataTransferObject queryParameters) throws Exception {
		/**
		 * 首先判断是否传递了bssOrgId这个参数如果包含这个参数那么就查询当前这个公司下的所有车辆
		 * 如果bssOrgId为空那么就查询出当前登录用户能够查询的公司的所有车辆
		 */
		structureQueryStationRelationshipInfo(queryParameters);
		JSONObject stationRelationship = getAnalysisStationRelationship()
				.obtainAnalysisStationRelationshipInfo(queryParameters);
		StationRelationshipBean stationRelationshipBean = new StationRelationshipBean();
		structureAnalysisStationRelationshipBeanItem((JSONArray) stationRelationship.get("itemsOfDataValue"),
				stationRelationshipBean);
		/**
		 * 将查询到的结果集转换成JqGrid需要的格式
		 */
		stationRelationshipBean.initJqGridTableEntity(stationRelationship);
		return JSONObject.fromObject(stationRelationshipBean);
	}

	private void structureQueryStationRelationshipInfo(IDataTransferObject queryParameters) throws Exception {
		List<String> paramsList =new ArrayList<String>();
		String bssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		if (!"".equals(bssOrgId)) {
			paramsList.add(bssOrgId);
		} else {

		}
		queryParameters.obtainMapOfRequiredParameter().put("list", paramsList);
	}

	private void structureAnalysisStationRelationshipBeanItem(JSONArray stationRelationship,
			StationRelationshipBean stationRelationshipBean) {
		if (stationRelationship != null && stationRelationship.size() > 0) {
			StationRelationshipEntity stationRelationshipEntity = null;
			for (int i = 0; i < stationRelationship.size(); i++) {
				stationRelationshipEntity = new StationRelationshipEntity();
				stationRelationshipEntity.initStationRelationshipEntity(stationRelationship.getJSONObject(i));
				stationRelationshipBean.setGridData(stationRelationshipEntity);
			}
		}

	}

	public JSONArray obtainEffectiveStationRelationship(IDataTransferObject queryParameters) throws Exception {
		return getAnalysisStationRelationship().obtainAnalysisStationRelationshipID(queryParameters);
	}

	private boolean isContainsBssOrgId(IDataTransferObject queryParameters) throws Exception {
		String paramBssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		JSONArray bssOrgIdItems = obtainEffectiveStationRelationship(queryParameters);
		if (bssOrgIdItems != null && bssOrgIdItems.size() > 0) {
			JSONObject bssOrgIdItem;
			String bssOrgIdParam;
			for (int i = 0; i < bssOrgIdItems.size(); i++) {
				bssOrgIdItem = bssOrgIdItems.getJSONObject(i);
				bssOrgIdParam = FrameworkStringUtil.asString(bssOrgIdItem.get("BSS_ORG_ID"));
				if (bssOrgIdParam.equals(paramBssOrgId)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isContainsCarId(IDataTransferObject queryParameters) throws Exception {
		String paramCarId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		JSONArray carIdItems = getAnalysisStationRelationship().obtainCarIdInformation(queryParameters);
		if (carIdItems != null && carIdItems.size() > 0) {
			JSONObject carIdItem;
			String bssOrgIdParam;
			for (int i = 0; i < carIdItems.size(); i++) {
				carIdItem = carIdItems.getJSONObject(i);
				bssOrgIdParam = FrameworkStringUtil.asString(carIdItem.get("CAR_ID"));
				if (bssOrgIdParam.equals(paramCarId)) {
					return true;
				}
			}
		}
		return false;

	}

	public JSONObject obtainEffectiveStationRelationshipItems(IDataTransferObject queryParameters) throws Exception {
		String paramBssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		if (!"".equals(paramBssOrgId)) {
			queryParameters.evaluteRequiredParameter("bssOrgId", paramBssOrgId);
			return obtainAnalysisStationRelationshipOfMobile(queryParameters);
		}
		return null;
	}

	public JSONObject obtainAnalysisStationRelationshipOfMobile(IDataTransferObject queryParameters) throws Exception {
		JSONObject stationRelationshipMobile = getAnalysisStationRelationship()
				.obtainAnalysisStationRelationshipInfo(queryParameters);
		StationRelationshipBeanOfMobile stationRelationshipMobileBean = new StationRelationshipBeanOfMobile();
		structureAnalysisStationRelationshipBeanMobileItem(
				(JSONArray) stationRelationshipMobile.get("itemsOfDataValue"), stationRelationshipMobileBean);
		stationRelationshipMobileBean.initJqGridTableMobileEntity(stationRelationshipMobile);
		return JSONObject.fromObject(stationRelationshipMobileBean);

	}

	private void structureAnalysisStationRelationshipBeanMobileItem(JSONArray stationRelationshipMobile,
			StationRelationshipBeanOfMobile stationRelationshipMobileBean) {
		if (stationRelationshipMobile != null && stationRelationshipMobile.size() > 0) {
			StationRelationshipEntity stationRelationshipEntity = null;
			for (int i = 0; i < stationRelationshipMobile.size(); i++) {
				stationRelationshipEntity = new StationRelationshipEntity();
				stationRelationshipEntity.initStationRelationshipEntity(stationRelationshipMobile.getJSONObject(i));
				stationRelationshipMobileBean.setRows(stationRelationshipEntity);
			}
		}

	}

	public JSONObject obtainaAnalysisRealtimeInformation(IDataTransferObject queryParameters) throws Exception {
		if (isContainsBssOrgId(queryParameters) && isContainsCarId(queryParameters)) {
			JSONArray realtimeInfo = obtainCarIdInformation(queryParameters);
			CarRealtimeInformation carRealtimeInformation = new CarRealtimeInformation();
			structureAnalysisCarIdInformationItem(realtimeInfo, carRealtimeInformation);
			carRealtimeInformation.setiTotalDisplayRecords(5);
			carRealtimeInformation.setiTotalRecords(5);
			return JSONObject.fromObject(carRealtimeInformation);
		}
		return null;
	}

	private void structureAnalysisCarIdInformationItem(JSONArray realtimeInfo,
			CarRealtimeInformation carRealtimeInformation) {
		if (realtimeInfo != null && realtimeInfo.size() > 0) {
			CarRealtimeInformationEntity carRealtimeInformationEntity = null;
			JSONObject realtimeInfoItem;
			for (int i = 0; i < realtimeInfo.size(); i++) {
				realtimeInfoItem = realtimeInfo.getJSONObject(i);
				carRealtimeInformationEntity = new CarRealtimeInformationEntity();
				carRealtimeInformationEntity.setOperid(FrameworkStringUtil.asString(realtimeInfoItem.get("OPER_ID")));
				carRealtimeInformationEntity
						.setDeviceid(FrameworkStringUtil.asString(realtimeInfoItem.get("DEVICE_ID")));
				carRealtimeInformationEntity.setCarid(FrameworkStringUtil.asString(realtimeInfoItem.get("CAR_ID")));
				carRealtimeInformationEntity.setWeight(FrameworkStringUtil.asString(realtimeInfoItem.get("WEIGHT")));
				carRealtimeInformationEntity
						.setAccuracy(FrameworkStringUtil.asString(realtimeInfoItem.get("ACCURACY")));
				carRealtimeInformationEntity
						.setDrivestate(FrameworkStringUtil.asString(realtimeInfoItem.get("DRIVE_STATE")));
				carRealtimeInformationEntity
						.setCreatedate(FrameworkStringUtil.asString(realtimeInfoItem.get("CREATE_DATE")));
				carRealtimeInformationEntity.setCarnbr(FrameworkStringUtil.asString(realtimeInfoItem.get("CAR_NBR")));
				try {
					double lat = Double.parseDouble(FrameworkStringUtil.asString(realtimeInfoItem.get("LATITUDE")));
					double lon = Double.parseDouble(FrameworkStringUtil.asString(realtimeInfoItem.get("LONGITUDE")));
					double[] points = AnalysisCoordinateTransformationUtil.wgs2gcj(lat, lon);
					carRealtimeInformationEntity
							.setLatitude(FrameworkStringUtil.asString(FrameworkStringUtil.asString(points[0])));
					carRealtimeInformationEntity
							.setLongitude(FrameworkStringUtil.asString(FrameworkStringUtil.asString(points[1])));
				} catch (Exception e) {
					carRealtimeInformationEntity
							.setLatitude(FrameworkStringUtil.asString(realtimeInfoItem.get("LATITUDE")));
					carRealtimeInformationEntity
							.setLongitude(FrameworkStringUtil.asString(realtimeInfoItem.get("LONGITUDE")));
				}

				carRealtimeInformation.setAaData(carRealtimeInformationEntity);
			}

		}
	}

	public JSONArray obtainCarIdInformation(IDataTransferObject queryParameters) throws Exception {
		return getAnalysisStationRelationship().obtainRealtimeInformation(queryParameters);
	}

	private IAnalysisStationRelationshipDao getAnalysisStationRelationship() {
		return (IAnalysisStationRelationshipDao) applicationContext.getBean("analysisStationRelationshipDao");
	}

}
