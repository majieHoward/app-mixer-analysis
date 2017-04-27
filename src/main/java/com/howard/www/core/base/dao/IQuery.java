package com.howard.www.core.base.dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IQuery extends IHandleSql<IQuery> {
	public JSONArray forJsonArray();

	public JSONObject forJsonObject();
	
	public int forCount();
	
	public String obtainSqlSentence();
	
	public JSONArray forJSONArrayPage(int startPage,int pageSize);

}
