package com.howard.www.core.web.security.authentication.service;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

public interface IFrameworkAuthenticationInfoService {
	public void initializeUserInformation(SimpleAuthorizationInfo userInfo)
			throws Exception;
}
