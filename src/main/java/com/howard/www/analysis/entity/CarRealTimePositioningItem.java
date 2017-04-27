package com.howard.www.analysis.entity;

import java.util.Vector;

public class CarRealTimePositioningItem {
	private Vector<CarRealTimePositioning> carRealTimePositioningItems = new Vector<CarRealTimePositioning>();

	public Vector<CarRealTimePositioning> getCarRealTimePositioningItems() {
		return carRealTimePositioningItems;
	}

	public void setCarRealTimePositioningItems(CarRealTimePositioning carRealTimePositioning) {
		this.carRealTimePositioningItems.add(carRealTimePositioning);
	}

}
