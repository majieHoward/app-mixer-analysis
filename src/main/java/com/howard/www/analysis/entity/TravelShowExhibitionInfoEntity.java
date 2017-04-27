package com.howard.www.analysis.entity;

import net.sf.json.JSONObject;

import com.howard.www.analysis.util.AnalysisCoordinateTransformationUtil;
import com.howard.www.core.web.util.FrameworkStringUtil;

public class TravelShowExhibitionInfoEntity {
	private String longitudeShow;
	private String latitudeShow;


	public void initTravelShowExhibitionInfoEntity(
			JSONObject travelShowExhibitionInfoEntity) {
		try {
			double lat = Double.parseDouble(FrameworkStringUtil
					.asString(travelShowExhibitionInfoEntity.get("LATITUDE")));
			double lon = Double.parseDouble(FrameworkStringUtil
					.asString(travelShowExhibitionInfoEntity.get("LONGITUDE")));
			double[] points = AnalysisCoordinateTransformationUtil.wgs2gcj(lat, lon);
			setLatitudeShow(FrameworkStringUtil.asString(points[0]));
			setLongitudeShow(FrameworkStringUtil.asString(points[1]));
		} catch (Exception e) {
			setLatitudeShow(FrameworkStringUtil
					.asString(travelShowExhibitionInfoEntity.get("LATITUDE")));
			setLongitudeShow(FrameworkStringUtil
					.asString(travelShowExhibitionInfoEntity.get("LONGITUDE")));
		}
	}

	public String getLongitudeShow() {
		return longitudeShow;
		
	}

	public void setLongitudeShow(String longitudeShow) {
		this.longitudeShow = longitudeShow;
	}

	public String getLatitudeShow() {
		return latitudeShow;
	}

	public void setLatitudeShow(String latitudeShow) {
		this.latitudeShow = latitudeShow;
	}

}
