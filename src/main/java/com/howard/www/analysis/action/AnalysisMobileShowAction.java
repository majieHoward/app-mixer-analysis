package com.howard.www.analysis.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howard.www.analysis.service.IAnalysisSystemMenuService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
@Controller
@Scope("prototype")
public class AnalysisMobileShowAction extends BaseController {
	@Autowired
	private ApplicationContext cApplicationContext;

	@RequestMapping(value = "/mobile/index.html")
	public String indexMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/index";
	}

	@RequestMapping(value = "/mobile/indexMain.html")
	public String indexMainMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		/**
		 * 加载菜单
		 */
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) cApplicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMapByMobile(this.getParamOfDto(), model);
		return "mobile/indexMain";
	}

	@RequestMapping(value = "/mobile/indexOrgSelect.html")
	public String indexOrgSelectMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/indexOrgSelect";
	}

	@RequestMapping(value = "/mobile/stationEquipment/stationEquipment.html")
	public String stationEquipmentMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String bssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		model.put("bssOrgId", bssOrgId);
		return "mobile/stationEquipment/stationEquipment";
	}


	@RequestMapping(value = "/mobile/dataStatistic/queryStatisticsBarCharts.html")
	public String queryStatisticsBarChart(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/dataStatistic/queryStatisticsBarCharts";
	}

	@RequestMapping(value = "/mobile/login.html")
	public String loginMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/login";
	}

	@RequestMapping(value = "/mobile/stationEquipment/stationEquipmentItem.html")
	public String stationEquipmentMobileItem(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/stationEquipment/stationEquipmentItem";
	}

	@RequestMapping(value = "/mobile/stationEquipment/stationEquipmentOrgSelect.html")
	public String stationEquipmentOrgSelectMobileItem(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		return "mobile/stationEquipment/stationEquipmentOrgSelect";
	}

	@RequestMapping(value = "/mobile/stationEquipment/stationEquipmentIndex.html")
	public String stationEquipmentIndexMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		/**
		 * 加载菜单
		 */
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) cApplicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMapByMobile(this.getParamOfDto(), model);
		return "mobile/stationEquipment/stationEquipmentIndex";
	}

	@RequestMapping(value = "/mobile/travelShow/travelShow.html")
	public String travelShowMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String carId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		model.put("carId", carId);
		String bssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		model.put("bssOrgId", bssOrgId);
		String servId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("servId"));
		model.put("servId", servId);
		return "mobile/travelShow/travelShow";
	}

	@RequestMapping(value = "/mobile/travelShow/travelShowIndex.html")
	public String travelShowIndexMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		/**
		 * 加载菜单
		 */
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) cApplicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMapByMobile(this.getParamOfDto(), model);
		return "mobile/travelShow/travelShowIndex";
	}

	@RequestMapping(value = "/mobile/travelShow/travelShowOrgSelect.html")
	public String travelShowOrgSelectMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/travelShow/travelShowOrgSelect";
	}

	@RequestMapping(value = "/mobile/travelShow/travelShowTripItem.html")
	public String travelShowTripItemMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String carId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		model.put("carId", carId);
		return "mobile/travelShow/travelShowTripItem";
	}

	@RequestMapping(value = "/mobile/travelShow/travelShowTripRunning.html")
	public String travelShowTripRunningMobile(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String carId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		model.put("carId", carId);
		String deviceId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("deviceId"));
		model.put("deviceId", deviceId);
		String operId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("operId"));
		model.put("operId", operId);
		return "mobile/travelShow/travelShowTripRunning";
	}

	@RequestMapping(value = "/mobile/travelShow/jqgridMobile.html")
	public String travelShowTripjqgridMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/travelShow/jqgridMobile";
	}
	
	@RequestMapping(value = "/mobile/dataStatistic/queryStatisticsLineCharts.html")
	public String queryStatisticsLineCharts(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "mobile/dataStatistic/queryStatisticsLineCharts";
	}
}
