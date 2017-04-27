package com.howard.www.core.data.transfer.dto;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

public interface IViewObject {
	public void evaluateInteractiveData(Object paramOfInteractiveData);
	
	public Object obtainInteractiveData();

	public ConcurrentHashMap<String, Object> obtainRequestParamsMap();

	public void evaluateRequestParamsMap(ConcurrentHashMap<String, Object> requestParamsMap);

	public void evaluateRequestParams(HttpServletRequest request);
}
