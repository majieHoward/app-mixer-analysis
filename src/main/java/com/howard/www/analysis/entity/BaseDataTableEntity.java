package com.howard.www.analysis.entity;

import java.util.Vector;

public class BaseDataTableEntity<T> {
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private Vector<T> aaData = new Vector<T>();

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public Vector<T> getAaData() {
		return aaData;
	}

	public void setAaData(T aaDataItem) {
		aaData.add(aaDataItem);
	}

}
