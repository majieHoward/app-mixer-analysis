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
import com.howard.www.analysis.service.IAnalysisCarInfoManageService;
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
public class AnalysisCarInfoAction extends BaseController {
	@Autowired
	private ApplicationContext cApplicationContext;

	/**
	 * 通过车辆的编号查询车辆的实时位置
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/car/realTimePositionOfCar.html")
	public void realTimePositionOfCar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDataTransferObject paramOfDto = this.getParamOfDto();
		JSONObject carInfoItems = obtainIAnalysisCarInfoManageService().obtainCarRealTimePositioning(paramOfDto);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(carInfoItems));
		writer.flush();
	}

	/**
	 * 根据车的Id查询某辆车实时位置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/realTimePositionOfCarByCarId.html")
	public void realTimePositionOfCarByCarId(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IDataTransferObject paramOfDto = this.getParamOfDto();
		JSONObject carInfoItems = obtainIAnalysisCarInfoManageService().obtainRealTimePositionOfCarByCarId(paramOfDto);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(FrameworkStringUtil.asString(carInfoItems));
		writer.flush();
	}

	/**
	 * 查询车辆实时位置
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/carRealTimePositioning.html")
	public String carRealTimePositioning(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) cApplicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMap(this.getParamOfDto(), model);
		return "car/carRealTimePositioning";
	}

	private IAnalysisCarInfoManageService obtainIAnalysisCarInfoManageService() {
		return (IAnalysisCarInfoManageService) cApplicationContext.getBean("analysisCarInfoManageService");
	}

}
