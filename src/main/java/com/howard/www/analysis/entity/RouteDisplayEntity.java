package com.howard.www.analysis.entity;

import java.util.Vector;

public class RouteDisplayEntity {
	private Vector<TravelShowExhibitionInfoEntity> points = new Vector<TravelShowExhibitionInfoEntity>();

	private Vector<MarkPointEntity> markPoints = new Vector<MarkPointEntity>();

	public Vector<TravelShowExhibitionInfoEntity> getPoints() {
		return points;
	}

	public void setPoints(TravelShowExhibitionInfoEntity point) {
		this.points.add(point);
	}

	public Vector<MarkPointEntity> getMarkPoints() {
		return markPoints;
	}

	public void setMarkPoints(MarkPointEntity markPoint) {
		this.markPoints.add(markPoint);
	}

}
