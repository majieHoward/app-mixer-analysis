package com.howard.www.analysis.entity;

import net.sf.json.JSONObject;

import com.howard.www.core.web.util.FrameworkStringUtil;

public class StationRelationshipEntity {
	private String servId;
	private String state;
	private String serialId;
	private String carId;
	private String carNbr;
	private String carState;
	private String bssOrg;
	private String longitude;
	private String latitude;
	private String addr;
	private String bssOrgName;
	private String orgState;

	public void initStationRelationshipEntity(JSONObject itemsListOfRealTime) {
		setAddr(FrameworkStringUtil.asString(itemsListOfRealTime.get("ADDR")));
		setBssOrgName(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("BSS_ORG_NAME")));
		setBssOrg(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("BSS_ORG_ID")));
		setCarId(FrameworkStringUtil
				.asString(itemsListOfRealTime.get("CAR_ID")));
		setCarNbr(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("CAR_NBR")));
		setCarState(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("CARSTATE")));
		setLatitude(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("LATITUDE")));
		setLongitude(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("LONGITUDE")));
		setOrgState(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("ORGSTATE")));
		setSerialId(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("SERIAL_ID")));
		setServId(FrameworkStringUtil.asString(itemsListOfRealTime
				.get("SERV_ID")));
		setState(FrameworkStringUtil.asString(itemsListOfRealTime.get("STATE")));

	}

	public String getServId() {
		return servId;
	}

	public void setServId(String servId) {
		this.servId = servId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getCarNbr() {
		return carNbr;
	}

	public void setCarNbr(String carNbr) {
		this.carNbr = carNbr;
	}

	public String getCarState() {
		return carState;
	}

	public void setCarState(String carState) {
		this.carState = carState;
	}

	public String getBssOrg() {
		return bssOrg;
	}

	public void setBssOrg(String bssOrg) {
		this.bssOrg = bssOrg;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBssOrgName() {
		return bssOrgName;
	}

	public void setBssOrgName(String bssOrgName) {
		this.bssOrgName = bssOrgName;
	}

	public String getOrgState() {
		return orgState;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

}
