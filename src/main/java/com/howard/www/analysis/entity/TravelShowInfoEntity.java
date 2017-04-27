package com.howard.www.analysis.entity;

import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

public class TravelShowInfoEntity {
	private String operId;
	private String deviceId;
	private String longitude;
	private String latitude;
	private String weightOne;
	private String dateOne;
	private String weigthTwo;
	private String dateTwo;
	private String weigthDiff;
	private String nodeSign;
	private String jbbs;
	private String carId;

	public void initTravelShowInfoEntity(JSONObject itemOfTravelShowInfo) {
		setOperId(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("OPER_ID")));
		setDeviceId(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("DEVICE_ID")));
		setLongitude(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("LONGITUDE")));
		setLatitude(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("LATITUDE")));
		setWeightOne(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("WEIGHT01")));
		setDateOne(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("DATE01")));
		setWeightOne(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("WEIGHT02")));
		setDateTwo(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("DATE02")));
		setWeigthDiff(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("WEIGHT_DIFF")));
		setNodeSign(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("NODE_SIGN")));
		setJbbs(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("JDBS")));
		setCarId(FrameworkStringUtil.asString(itemOfTravelShowInfo.get("CAR_ID")));
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

	public String getLongitude() {
		return FrameworkStringUtil.asString(Float.parseFloat(longitude)+0.0089);
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return FrameworkStringUtil.asString(Float.parseFloat(latitude)+0.00359
);

	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getWeightOne() {
		return weightOne;
	}

	public void setWeightOne(String weightOne) {
		this.weightOne = weightOne;
	}

	public String getDateOne() {
		return dateOne;
	}

	public void setDateOne(String dateOne) {
		this.dateOne = dateOne;
	}

	public String getWeigthTwo() {
		return weigthTwo;
	}

	public void setWeigthTwo(String weigthTwo) {
		this.weigthTwo = weigthTwo;
	}

	
	public String getDateTwo() {
		return dateTwo;
	}

	public void setDateTwo(String dateTwo) {
		this.dateTwo = dateTwo;
	}

	public String getWeigthDiff() {
		return weigthDiff;
	}

	public void setWeigthDiff(String weigthDiff) {
		this.weigthDiff = weigthDiff;
	}

	public String getNodeSign() {
		return nodeSign;
	}

	public void setNodeSign(String nodeSign) {
		this.nodeSign = nodeSign;
	}

	public String getJbbs() {
		return jbbs;
	}

	public void setJbbs(String jbbs) {
		this.jbbs = jbbs;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}


}
