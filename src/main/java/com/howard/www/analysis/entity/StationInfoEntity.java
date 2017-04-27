package com.howard.www.analysis.entity;

import com.howard.www.core.web.util.FrameworkStringUtil;

public class StationInfoEntity {
	private String longitude; // 经度
	private String latitude; // 纬度
	private String stationOfLocation;
	private String streetOfLocation;
	private String bssOrgId;
	private String bssOrgName;
	
	public String getBssOrgName() {
		return bssOrgName;
	}

	public void setBssOrgName(String bssOrgName) {
		this.bssOrgName = bssOrgName;
	}

	public String getLongitude() {
		return FrameworkStringUtil.asString(Float.parseFloat(longitude)+0.0089);
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return FrameworkStringUtil.asString(Float.parseFloat(latitude)+0.00359);

	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStationOfLocation() {
		return stationOfLocation;
	}

	public void setStationOfLocation(String stationOfLocation) {
		this.stationOfLocation = stationOfLocation;
	}

	public String getStreetOfLocation() {
		return streetOfLocation;
	}

	public void setStreetOfLocation(String streetOfLocation) {
		this.streetOfLocation = streetOfLocation;
	}

	public String getBssOrgId() {
		return bssOrgId;
	}

	public void setBssOrgId(String bssOrgId) {
		this.bssOrgId = bssOrgId;
	}

}
