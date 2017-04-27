package com.howard.www.analysis.service.impl;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import com.howard.www.analysis.dao.IAnalysisLoginPageResourcesDao;
import com.howard.www.analysis.service.IAnalysisLoginPageResourcesService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.security.service.IModifyTheStringService;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("analysisLoginPageResourcesService")
public class AnalysisLoginPageResourcesServiceImpl implements
		IAnalysisLoginPageResourcesService {
	@Autowired
	private ApplicationContext cApplicationContext;

	private IModifyTheStringService obtainIModifyTheStringService(
			String modifyTheStringServiceBeanName) throws Exception {
		return (IModifyTheStringService) cApplicationContext
				.getBean(modifyTheStringServiceBeanName);
	}

	private IAnalysisLoginPageResourcesDao obtainIAnalysisLoginPageResourcesDao(
			String analysisLoginPageResourcesDaoBeanName) throws Exception {
		return (IAnalysisLoginPageResourcesDao) cApplicationContext
				.getBean(analysisLoginPageResourcesDaoBeanName);
	}

	public JSONObject structureAnalysisLoginPageSources(
			IDataTransferObject queryParameters) throws Exception {
		queryParameters.evaluteRequiredParameter("pageClassification", "login");
		queryParameters
				.evaluteRequiredParameter("pageSequenceNumber", "login1");
		JSONObject analysisLoginPageResources = obtainIAnalysisLoginPageResourcesDao(
				"analysisLoginPageResourcesDao")
				.obtainAnalysisLoginPageResourcesFromDB(queryParameters);
		if (analysisLoginPageResources != null) {
			return analysisLoginPageResources;
		}
		return null;
	}

	public String structureAnalysisLoginPage(IDataTransferObject queryParameters)
			throws Exception {
		JSONObject analysisLoginPageResources = structureAnalysisLoginPageSources(queryParameters);
		if (analysisLoginPageResources != null) {
			String pageContentResources = FrameworkStringUtil
					.asString(analysisLoginPageResources.get("pageContent"));
			if ("".equals(pageContentResources)) {
				return FrameworkStringUtil.asString(null);
			}
			return FrameworkStringUtil.asString(obtainIModifyTheStringService(
					"throughGzipModifyTheString").decompressionOriginalString(
					pageContentResources));
		}
		return FrameworkStringUtil.asString(null);
	}

}
