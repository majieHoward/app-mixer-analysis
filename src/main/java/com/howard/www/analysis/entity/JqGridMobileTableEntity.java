package com.howard.www.analysis.entity;

import java.util.Vector;

import net.sf.json.JSONObject;

import com.howard.www.core.web.util.FrameworkStringUtil;

public class JqGridMobileTableEntity<T> {
	private String records;
	private int page;
	private int total;
	private Vector<T> rows = new Vector<T>();
	
	public void initJqGridTableMobileEntity(JSONObject paramObject){
//		userdata=new JqGridTableUserData();
//		userdata.setSql(FrameworkStringUtil.asString(paramObject.get("sqlSentenceValue")));
		setPage(Integer.parseInt(FrameworkStringUtil.asString(paramObject.get("currPageValue"))));
	    setTotal(Integer.parseInt(FrameworkStringUtil.asString(paramObject.get("totalPagesValue"))));
	    setRecords(FrameworkStringUtil.asString(paramObject.get("countOfValue")));
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Vector<T> getRows() {
		return rows;
	}

	public void setRows(T row) {
		this.rows.add(row);
	}

}
