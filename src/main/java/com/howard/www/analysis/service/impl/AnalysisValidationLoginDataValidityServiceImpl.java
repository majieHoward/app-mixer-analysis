package com.howard.www.analysis.service.impl;

import org.springframework.stereotype.Repository;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.security.authorizing.service.IValidationLoginDataValidity;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("analysisValidationLoginDataValidityService")
public class AnalysisValidationLoginDataValidityServiceImpl implements
		IValidationLoginDataValidity {
    
	public String validationDataValidity(IDataTransferObject queryParameters)
			throws Exception {
		String loginUserName = FrameworkStringUtil.asString(queryParameters
				.obtainMapOfRequiredParameter().get("userName"));
		String loginUserPassword = FrameworkStringUtil.asString(queryParameters
				.obtainMapOfRequiredParameter().get("passWord"));
		if ("".equals(loginUserName) && "".equals(loginUserPassword)) {
			return "用户名密码为空";
		} else {
			/**
			 * 特殊的验证
			 */
			return customValidation(queryParameters);
		}
	}

	public String customValidation(IDataTransferObject queryParameters) {
		return null;
	};
}
