package com.howard.www.analysis.entity;

import net.sf.json.JSONObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

public class CarInfoEntity {
	private String carId;
	private String carNbr;
	private String carWeight;
	private String carState;
	private String carModifyDate;
	private String carIdHide;

	public void initCarInfo(JSONObject carInfo) {
		setCarId(FrameworkStringUtil.asString(carInfo.get("CARID")));
		setCarIdHide(FrameworkStringUtil.asString(carInfo.get("CARID")));
		setCarNbr(FrameworkStringUtil.asString(carInfo.get("CARNBR")));
		setCarWeight(FrameworkStringUtil.asString(carInfo.get("CARWEIGHT")));
		setCarState(FrameworkStringUtil.asString(carInfo.get("STATE")));
		setCarModifyDate(FrameworkStringUtil.asString(carInfo.get("MODIDATE")));
	}

	public String getCarIdHide() {
		return carIdHide;
	}

	public void setCarIdHide(String carIdHide) {
		this.carIdHide = carIdHide;
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

	public String getCarWeight() {
		return carWeight;
	}

	public void setCarWeight(String carWeight) {
		this.carWeight = carWeight;
	}

	public String getCarState() {
		return carState;
	}

	public void setCarState(String carState) {
		this.carState = carState;
	}

	public String getCarModifyDate() {
		return carModifyDate;
	}

	public void setCarModifyDate(String carModifyDate) {
		this.carModifyDate = carModifyDate;
	}

}
