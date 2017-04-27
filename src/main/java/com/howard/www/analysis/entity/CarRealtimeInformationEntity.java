package com.howard.www.analysis.entity;

import com.howard.www.core.web.util.FrameworkStringUtil;

public class CarRealtimeInformationEntity {
	private String operid;
	private String deviceid;
	private String carid;
	private String weight;
	private String latitude;
	private String longitude;
	private String accuracy;
	private String drivestate;
	private String createdate;
	private String carnbr;
	private RealTimeInfoMonitorBeanOfIcon icon = new RealTimeInfoMonitorBeanOfIcon();

	public RealTimeInfoMonitorBeanOfIcon getIcon() {
		return icon;
	}

	public void setIcon(RealTimeInfoMonitorBeanOfIcon icon) {
		this.icon = icon;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getCarid() {
		return carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getLatitude() {
		return FrameworkStringUtil.asString(Float.parseFloat(latitude)+0.00359
);

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

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getDrivestate() {
		return drivestate;
	}

	public void setDrivestate(String drivestate) {
		this.drivestate = drivestate;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getCarnbr() {
		return carnbr;
	}

	public void setCarnbr(String carnbr) {
		this.carnbr = carnbr;
	}

}
