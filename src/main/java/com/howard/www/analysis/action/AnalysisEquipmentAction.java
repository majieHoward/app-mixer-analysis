package com.howard.www.analysis.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howard.www.core.base.web.controller.BaseController;

/**
 * 采集设备信息管理
 * 
 * @author howard
 * 
 */
@Controller
@Scope("prototype")
public class AnalysisEquipmentAction extends BaseController {
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipment/equipmentInfoShow.html")
	public String equipmentInfoShow(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "equipment/equipmentInfoShow";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipment/equipmentInfoEnteringModule.html")
	public String equipmentInfoEnteringModule(HttpServletRequest request,
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
	@RequestMapping(value = "/equipment/currentManageTheEquipmentInformation.html")
	public void currentManageTheEquipmentInformation(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {

	}
}
