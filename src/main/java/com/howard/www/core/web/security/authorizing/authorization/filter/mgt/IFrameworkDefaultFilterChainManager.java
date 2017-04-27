package com.howard.www.core.web.security.authorizing.authorization.filter.mgt;

import java.util.Vector;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.FilterChain;

import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.web.filter.mgt.NamedFilterList;

public interface IFrameworkDefaultFilterChainManager {
	public FilterChain proxyBySelf(ConcurrentMap<String, NamedFilterList> filterChains, FilterChain original,
			Vector<String> chainNames);

	public String[] toNameConfigPairBySelf(String token) throws ConfigurationException;
}
