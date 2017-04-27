package com.howard.www.analysis.entity;

import java.util.Vector;

public class StationLocationBean {
	private Vector<StationInfoEntity> StationLocationBean = new Vector<StationInfoEntity>();

	public Vector<StationInfoEntity> getStationLocationBean() {
		return StationLocationBean;
	}

	public void setStationLocationBean(StationInfoEntity stationLocationBeanItem) {
		this.StationLocationBean.add(stationLocationBeanItem);
	}
}
