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

import com.howard.www.analysis.service.IAnalysisStationManageService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 场站信息管理
 * 
 * @author howard
 * 
 */
@Controller
@Scope("prototype")
public class AnalysisStationAction extends BaseController {
	@Autowired
	private ApplicationContext cApplicationContext;

	private IAnalysisStationManageService obtainIAnalysisStationManageService()
			throws Exception {
		return (IAnalysisStationManageService) cApplicationContext
				.getBean("analysisStationManageService");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/station/stationInfoShow.html")
	public String stationInfoShow(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "station/stationInfoShow";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/station/stationInfoEnteringModule.html")
	public String stationInfoEnteringModule(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "";
	}

	public String stationInfoModifyModule(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/station/currentManageTheStationInformation.html")
	public void currentManageTheStationInformation(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

	}

	/**
	 * 查询当前机构下的所有机构
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/station/stationLocationInformationDisplay.html")
	public void stationLocationInformationDisplay(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		JSONObject realTimeAcquisition = obtainIAnalysisStationManageService()
				.obtainStationLocationInformationDisplay(queryParameters);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(realTimeAcquisition));
		writer.flush();
	}
}
