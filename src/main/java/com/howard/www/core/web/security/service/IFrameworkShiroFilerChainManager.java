package com.howard.www.core.web.security.service;

import net.sf.json.JSONArray;

public interface IFrameworkShiroFilerChainManager {

	public abstract void initializationFilterChains() throws Exception;

	public abstract void evaluateFilterChains(JSONArray itemsOfFilterChain)
			throws Exception;

}