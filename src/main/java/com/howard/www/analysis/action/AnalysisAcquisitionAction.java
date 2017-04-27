package com.howard.www.analysis.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howard.www.core.base.web.controller.BaseController;

/**
 * 
 * @author howard
 * 
 */
@Controller
@Scope("prototype")
public class AnalysisAcquisitionAction extends BaseController {
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/excavate/acquisitionEquipmentOfData.html")
	public String acquisitionEquipmentOfDataShow(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "excavate/acquisitionEquipmentOfData";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/excavate/acquisitionEquipmentOfRealTimeData.html")
	public String acquisitionEquipmentOfRealTimeData(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		return "excavate/acquisitionEquipmentOfRealTimeData";
	}
}
