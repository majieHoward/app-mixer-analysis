package com.howard.www.core.web.security.authorizing.service.impl;

import org.springframework.stereotype.Repository;

import com.howard.www.core.web.security.authorizing.service.IAssemblingChainDefinition;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

@Repository("assemblingChainDefinitionS")
public class AssemblingChainDefinitionS implements IAssemblingChainDefinition {

	public String assemblingChainDefinition(JSONObject itemOfAuthorization) throws Exception {
		return FrameworkStringUtil.asString(itemOfAuthorization.get("LIMIT_NAME"));
	}

}
