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
import com.howard.www.analysis.service.IAnalysisDataStatisticsService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Controller
@Scope("prototype")
public class AnalysisDataStatisticsAction extends BaseController {
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(value = "/dataStatistics/queryStatisticsLineChart.html")
	public String queryStatisticsLineChart(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String bssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		String deviceId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("deviceId"));
		model.put("bssOrgId", bssOrgId);
		model.put("deviceId", deviceId);
		return "dataStatistics/queryStatisticsLineChart";
	}

	@RequestMapping(value = "/dataStatistics/queryStatisticsChartDrawLine.html")
	public String queryStatisticsChartDrawLine(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String bssOrgId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("bssOrgId"));
		String deviceId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("deviceId"));
		model.put("bssOrgId", bssOrgId);
		model.put("deviceId", deviceId);
		return "dataStatistics/queryStatisticsChartDrawLine";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/dataStatistics/monthlyTrafficVolumeByCarId.html")
	public void queryStatisticsDrawLineData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		JSONObject lineChartData = obtainIAnalysisDataStatisticsService()
				.obtainMonthlyTrafficVolumeByCarId(queryParameters);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(lineChartData));
		writer.flush();
	}

	@RequestMapping(value = "/dataStatistics/monthlyTrafficVolumeByBssOrg.html")
	public void monthlyTrafficVolumeByBssOrg(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		JSONObject lineChartData = obtainIAnalysisDataStatisticsService()
				.obtainMonthlyTrafficVolumeByBssOrg(queryParameters);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(lineChartData));
		writer.flush();
	}

	private IAnalysisDataStatisticsService obtainIAnalysisDataStatisticsService() throws Exception {
		return (IAnalysisDataStatisticsService) applicationContext.getBean("analysisDataStatisticsServiceImpl");
	}

}
