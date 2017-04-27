package com.howard.www.analysis.service;

import java.util.Vector;

import org.springframework.ui.ModelMap;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IAnalysisSystemMenuService {
	public Vector obtainSystemMenuItems(IDataTransferObject queryParameters) throws Exception;

	public void obtainSystemMenuItemsModelMap(IDataTransferObject queryParameters, ModelMap model) throws Exception;

	public void obtainSystemMenuItemsModelMapByMobile(IDataTransferObject queryParameters, ModelMap model)
			throws Exception;
}
