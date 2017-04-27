package com.howard.www.core.web.security.authorizing.service.impl;

import org.springframework.stereotype.Repository;

import com.howard.www.core.web.security.authorizing.service.IAssemblingChainDefinition;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

@Repository("assemblingChainDefinitionM")
public class AssemblingChainDefinitionM implements IAssemblingChainDefinition {

	public String assemblingChainDefinition(JSONObject itemOfAuthorization) throws Exception {
		StringBuffer chainDefinition = new StringBuffer();
		chainDefinition.append("frameworkperms[");
		chainDefinition.append(FrameworkStringUtil.asString(itemOfAuthorization.get("LIMIT_NAME")));
		chainDefinition.append("]");
		return chainDefinition.toString();
	}

}
