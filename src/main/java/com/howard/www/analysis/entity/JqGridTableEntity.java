package com.howard.www.analysis.entity;

import java.util.Vector;

import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

public class JqGridTableEntity<T> {
	private String currPage;
	private String totalPages;
	private String totalRecords;
	private Vector<T> gridData = new Vector<T>();
	private JqGridTableUserData userdata;

	public void initJqGridTableEntity(JSONObject paramObject){
		userdata=new JqGridTableUserData();
		userdata.setSql(FrameworkStringUtil.asString(paramObject.get("sqlSentenceValue")));
		setCurrPage(FrameworkStringUtil.asString(paramObject.get("currPageValue")));
	    setTotalPages(FrameworkStringUtil.asString(paramObject.get("totalPagesValue")));
	    setTotalRecords(FrameworkStringUtil.asString(paramObject.get("countOfValue")));
	}
	

	public String getCurrPage() {
		return currPage;
	}


	public void setCurrPage(String currPage) {
		this.currPage = currPage;
	}


	public String getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}


	public String getTotalRecords() {
		return totalRecords;
	}


	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}


	public void setGridData(Vector<T> gridData) {
		this.gridData = gridData;
	}


	public Vector<T> getGridData() {
		return gridData;
	}

	public void setGridData(T gridDataItem) {
		this.gridData.add(gridDataItem);
	}

	public JqGridTableUserData getUserdata() {
		return userdata;
	}

	public void setUserdata(JqGridTableUserData userdata) {
		this.userdata = userdata;
	}

}
