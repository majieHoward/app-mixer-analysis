package com.howard.www.analysis.entity;

import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

public class CarRealTimePositioning {
	private String operId;
	private String deviceId;
	private String carId;
	private String latitude;
	private String longitude;
	private String bssOrgId;
	private String carNbr;
	private String bssOrgName;

	public void initCarRealTimePositioning(JSONObject carRealTimePositioning) throws Exception {
		setOperId(FrameworkStringUtil.asString(carRealTimePositioning.get("OPER_ID")));
	    setDeviceId(FrameworkStringUtil.asString(carRealTimePositioning.get("DEVICE_ID")));
	    setCarId(FrameworkStringUtil.asString(carRealTimePositioning.get("CAR_ID")));
	    setLatitude(FrameworkStringUtil.asString(carRealTimePositioning.get("LATITUDE")));
	    setLongitude(FrameworkStringUtil.asString(carRealTimePositioning.get("LONGITUDE")));
	    setBssOrgId(FrameworkStringUtil.asString(carRealTimePositioning.get("BSS_ORG_ID")));
	    setCarNbr(FrameworkStringUtil.asString(carRealTimePositioning.get("CAR_NBR")));
	    setBssOrgName(FrameworkStringUtil.asString(carRealTimePositioning.get("BSS_ORG_NAME")));
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getLatitude() {
		return FrameworkStringUtil.asString(Float.parseFloat(latitude)+0.00359);

	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return FrameworkStringUtil.asString(Float.parseFloat(longitude)+0.0089);
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getBssOrgId() {
		return bssOrgId;
	}

	public void setBssOrgId(String bssOrgId) {
		this.bssOrgId = bssOrgId;
	}

	public String getCarNbr() {
		return carNbr;
	}

	public void setCarNbr(String carNbr) {
		this.carNbr = carNbr;
	}

	public String getBssOrgName() {
		return bssOrgName;
	}

	public void setBssOrgName(String bssOrgName) {
		this.bssOrgName = bssOrgName;
	}

}
