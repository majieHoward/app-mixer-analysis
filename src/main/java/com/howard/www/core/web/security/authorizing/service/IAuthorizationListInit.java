package com.howard.www.core.web.security.authorizing.service;

import java.util.Vector;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.web.filter.mgt.NamedFilterList;

public interface IAuthorizationListInit {
	public void structureAuthorizationList(Vector<String> chainNames,
			ConcurrentMap<String, NamedFilterList> filterChains) throws Exception;
}
