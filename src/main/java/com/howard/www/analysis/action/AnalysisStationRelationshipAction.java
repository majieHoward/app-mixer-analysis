package com.howard.www.analysis.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howard.www.analysis.service.IAnalysisStationRelationshipService;
import com.howard.www.analysis.service.IAnalysisSystemMenuService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Controller
@Scope("prototype")
public class AnalysisStationRelationshipAction extends BaseController {
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 查询所有机构中所包含的车辆
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationEquipment/analysisStationRelationship.html")
	public void analysisStationRelationship(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		JSONObject stationRelationship = obtainIAnalysisStationRelationshipService()
				.obtainAnalysisStationRelationship(queryParameters);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(stationRelationship));
		writer.flush();
	}

	@RequestMapping(value = "/stationEquipment/stationEquipment.html")
	public String stationEquipment(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String bssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) applicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMap(this.getParamOfDto(), model);
		model.put("bssOrgId", bssOrgId);
		return "stationEquipment/stationEquipment";
	}

	@RequestMapping(value = "/stationEquipment/stationEquipmentOfRealTimeMonitor.html")
	public String stationEquipmentOfRealTimeMonitor(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String bssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		String carId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		model.put("bssOrgId", bssOrgId);
		model.put("carId", carId);
		return "stationEquipment/stationEquipmentOfRealTimeMonitor";
	}

	@RequestMapping(value = "/analysisRealtimeInformation/analysisRealtimeInformation.html")
	public void analysisRealtimeInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		JSONObject stationRelationship = obtainIAnalysisStationRelationshipService()
				.obtainaAnalysisRealtimeInformation(queryParameters);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(stationRelationship));
		writer.flush();
	}

	private IAnalysisStationRelationshipService obtainIAnalysisStationRelationshipService() throws Exception {
		return (IAnalysisStationRelationshipService) applicationContext.getBean("analysisStationRelationshipService");
	}
}
