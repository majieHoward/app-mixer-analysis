package com.howard.www.analysis.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howard.www.analysis.service.IAnalysisSystemMenuService;
import com.howard.www.analysis.service.IAnalysisTravelShowService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class AnalysisTravelShowAction extends BaseController {

	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(value = "/travelShow/travelShow.html")
	public String travelShow(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) applicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMap(this.getParamOfDto(), model);
		String carId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		model.put("carId", carId);
		return "travelShow/travelShow";
	}

	/**
	 * 地图展示页面行程选择
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/travelShow/travelSelectItem.html")
	public String travelSelectItem(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String carId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		model.put("carId", carId);
		return "travelShow/travelSelectItem";
	}

	/**
	 * 展示车辆的运行轨迹
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/travelShow/travelCarSelectShow.html")
	public String travelCarSelectShow(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		String operId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("operId"));
		String carId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("carId"));
		String deviceId = FrameworkStringUtil.asString(queryParameters.obtainRequestParamsMap().get("deviceId"));
		model.put("operId", operId);
		model.put("carId", carId);
		model.put("deviceId", deviceId);
		return "travelShow/travelCarSelectShow";
	}

	/**
	 * 获取车辆当前operId对应的信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/travelShow/currentOperIdInformation.html")
	public void currentOperIdInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		response.setContentType("text/plain; charset=UTF-8");
		JSONObject itemsOfTravelShowInfo = AnalysisTravelShowService().obtainCurrentOperIdInformation(queryParameters);
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(itemsOfTravelShowInfo));
		writer.flush();
	}

	/**
	 * 获取两个operId之间的行程信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/travelShow/betweenTheTwoOperIdInformation.html")
	public void betweenTheTwoOperIdInformation(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IDataTransferObject queryParameters = this.getParamOfDto();
		response.setContentType("text/plain; charset=UTF-8");
		JSONObject itemsOfTravelShowInfo = AnalysisTravelShowService().betweenTheTwoOperIdInformation(queryParameters);
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(itemsOfTravelShowInfo));
		writer.flush();
	}

	/**
	 * 展示车辆的实时位置
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/travelShow/travelShowMovingView.html")
	public String travelShowMovingView(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IDataTransferObject paramOfDto = this.getParamOfDto();
		String carId = FrameworkStringUtil.asString(paramOfDto.obtainRequestParamsMap().get("carId"));
		model.put("carId", carId);
		return "travelShow/travelShowMovingView";
	}

	@RequestMapping(value = "/travelShow/travelShowInfoItems.html")
	public void travelShowInfoItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDataTransferObject paramOfDto = this.getParamOfDto();
		JSONObject itemsOfTravelShowInfo = AnalysisTravelShowService().obtainTravelShowInfo(paramOfDto);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(itemsOfTravelShowInfo));
		writer.flush();
	}

	@RequestMapping(value = "/exporting/excel.html")
    public void cell(HttpServletResponse response) throws Exception {
        byte[] bytes = AnalysisTravelShowService().obtainTravelShowInfoExcelFile(this.getParamOfDto());
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=" + FrameworkStringUtil.asString(new Date()) + ".xls");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    } 
	private IAnalysisTravelShowService AnalysisTravelShowService() {
		return (IAnalysisTravelShowService) applicationContext.getBean("analysisTravelShowServiceImpl");
	}
}
