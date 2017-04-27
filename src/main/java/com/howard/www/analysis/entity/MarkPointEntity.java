package com.howard.www.analysis.entity;

import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

public class MarkPointEntity {
	private String longitudeShow;
	private String latitudeShow;

	public void initMarkPointEntity(JSONObject markPointEntityObject){
		setLatitudeShow(FrameworkStringUtil.asString(markPointEntityObject.get("LATITUDE")));
		setLongitudeShow(FrameworkStringUtil.asString(markPointEntityObject.get("LONGITUDE")));
	}
	public String getLongitudeShow() {
		return FrameworkStringUtil.asString(Float.parseFloat(longitudeShow)+0.0089);
	}

	public void setLongitudeShow(String longitudeShow) {
		this.longitudeShow = longitudeShow;
	}

	public String getLatitudeShow() {
		return FrameworkStringUtil.asString(Float.parseFloat(latitudeShow)+0.00359
);

	}

	public void setLatitudeShow(String latitudeShow) {
		this.latitudeShow = latitudeShow;
	}
}
